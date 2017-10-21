package nl.qbusict.cupboard.internal.convert;

import android.content.ContentValues;
import android.database.Cursor;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import nl.qbusict.cupboard.Cupboard;
import nl.qbusict.cupboard.convert.EntityConverter;
import nl.qbusict.cupboard.convert.EntityConverter.Column;
import nl.qbusict.cupboard.convert.EntityConverter.ColumnType;
import nl.qbusict.cupboard.convert.EntityConverterFactory;
import nl.qbusict.cupboard.convert.FieldConverter;
import nl.qbusict.cupboard.convert.FieldConverterFactory;
import nl.qbusict.cupboard.convert.ReflectiveEntityConverter;

public class ConverterRegistry {
    private Cupboard mCupboard;
    private Map<Class<?>, EntityConverter<?>> mEntityConverterCache = new HashMap(128);
    private final ThreadLocal<Map<Class<?>, EntityConverter<?>>> mEntityConverterCalls = new ThreadLocal();
    private List<EntityConverterFactory> mEntityConverterFactories = new ArrayList(64);
    private Map<Type, FieldConverter<?>> mFieldConverterCache = new HashMap(128);
    private final ThreadLocal<Map<Type, FutureFieldConverter<?>>> mFieldConverterCalls = new ThreadLocal();
    private List<FieldConverterFactory> mFieldConverterFactories = new ArrayList(256);

    static class FutureEntityConverter<T> implements EntityConverter<T> {
        private EntityConverter<T> mDelegate;

        private FutureEntityConverter() {
        }

        public T fromCursor(Cursor cursor) {
            if (this.mDelegate != null) {
                return this.mDelegate.fromCursor(cursor);
            }
            throw new IllegalStateException();
        }

        public List<Column> getColumns() {
            if (this.mDelegate != null) {
                return this.mDelegate.getColumns();
            }
            throw new IllegalStateException();
        }

        public Long getId(T t) {
            if (this.mDelegate != null) {
                return this.mDelegate.getId(t);
            }
            throw new IllegalStateException();
        }

        public String getTable() {
            if (this.mDelegate != null) {
                return this.mDelegate.getTable();
            }
            throw new IllegalStateException();
        }

        void setDelegate(EntityConverter<T> entityConverter) {
            if (this.mDelegate != null) {
                throw new AssertionError();
            }
            this.mDelegate = entityConverter;
        }

        public void setId(Long l, T t) {
            if (this.mDelegate == null) {
                throw new IllegalStateException();
            }
            this.mDelegate.setId(l, t);
        }

        public void toValues(T t, ContentValues contentValues) {
            if (this.mDelegate == null) {
                throw new IllegalStateException();
            }
            this.mDelegate.toValues(t, contentValues);
        }
    }

    static class FutureFieldConverter<T> implements FieldConverter<T> {
        private FieldConverter<T> mDelegate;

        private FutureFieldConverter() {
        }

        public T fromCursorValue(Cursor cursor, int i) {
            if (this.mDelegate != null) {
                return this.mDelegate.fromCursorValue(cursor, i);
            }
            throw new IllegalStateException();
        }

        public ColumnType getColumnType() {
            if (this.mDelegate != null) {
                return this.mDelegate.getColumnType();
            }
            throw new IllegalStateException();
        }

        void setDelegate(FieldConverter<T> fieldConverter) {
            if (this.mDelegate != null) {
                throw new AssertionError();
            }
            this.mDelegate = fieldConverter;
        }

        public void toContentValue(T t, String str, ContentValues contentValues) {
            if (this.mDelegate == null) {
                throw new IllegalStateException();
            }
            this.mDelegate.toContentValue(t, str, contentValues);
        }
    }

    public ConverterRegistry(Cupboard cupboard) {
        this.mCupboard = cupboard;
        addDefaultEntityConverterFactories();
        addDefaultFieldConverterFactories();
    }

    private void addDefaultEntityConverterFactories() {
        this.mEntityConverterFactories.add(new EntityConverterFactory() {
            public <T> EntityConverter<T> create(Cupboard cupboard, Class<T> cls) {
                return new ReflectiveEntityConverter(cupboard, cls);
            }
        });
    }

    private void addDefaultFieldConverterFactories() {
        this.mFieldConverterFactories.add(new DefaultFieldConverterFactory());
        this.mFieldConverterFactories.add(new EnumFieldConverterFactory());
        this.mFieldConverterFactories.add(new EntityFieldConverterFactory());
    }

    public <T> EntityConverter<T> getDelegateEntityConverter(EntityConverterFactory entityConverterFactory, Class<T> cls) throws IllegalArgumentException {
        Object obj = null;
        for (EntityConverterFactory entityConverterFactory2 : this.mEntityConverterFactories) {
            if (obj != null) {
                EntityConverter<T> create = entityConverterFactory2.create(this.mCupboard, cls);
                if (create != null) {
                    return create;
                }
            } else if (entityConverterFactory2 == entityConverterFactory) {
                obj = 1;
            }
        }
        throw new IllegalArgumentException("Cannot convert entity of type " + cls);
    }

    public FieldConverter getDelegateFieldConverter(FieldConverterFactory fieldConverterFactory, Type type) throws IllegalArgumentException {
        Object obj = null;
        for (FieldConverterFactory fieldConverterFactory2 : this.mFieldConverterFactories) {
            if (obj != null) {
                FieldConverter create = fieldConverterFactory2.create(this.mCupboard, type);
                if (create != null) {
                    return create;
                }
            } else if (fieldConverterFactory2 == fieldConverterFactory) {
                obj = 1;
            }
        }
        throw new IllegalArgumentException("Cannot convert field of type " + type);
    }

    public <T> EntityConverter<T> getEntityConverter(Class<T> cls) throws IllegalArgumentException {
        Object obj;
        EntityConverter<T> entityConverter = (EntityConverter) this.mEntityConverterCache.get(cls);
        if (entityConverter == null) {
            Map map;
            Map map2 = (Map) this.mEntityConverterCalls.get();
            if (map2 == null) {
                HashMap hashMap = new HashMap(16);
                this.mEntityConverterCalls.set(hashMap);
                obj = 1;
                map = hashMap;
            } else {
                obj = null;
                map = map2;
            }
            FutureEntityConverter futureEntityConverter = (FutureEntityConverter) map.get(cls);
            if (futureEntityConverter == null) {
                try {
                    FutureEntityConverter futureEntityConverter2 = new FutureEntityConverter();
                    map.put(cls, futureEntityConverter2);
                    for (EntityConverterFactory create : this.mEntityConverterFactories) {
                        entityConverter = create.create(this.mCupboard, cls);
                        if (entityConverter != null) {
                            futureEntityConverter2.setDelegate(entityConverter);
                            this.mEntityConverterCache.put(cls, entityConverter);
                            map.remove(cls);
                            if (obj != null) {
                                this.mEntityConverterCalls.remove();
                            }
                        }
                    }
                    throw new IllegalArgumentException("Cannot convert entity of type " + cls);
                } catch (Throwable th) {
                    map.remove(cls);
                    if (obj != null) {
                        this.mEntityConverterCalls.remove();
                    }
                }
            }
        }
        return entityConverter;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public <T> nl.qbusict.cupboard.convert.FieldConverter<T> getFieldConverter(java.lang.reflect.Type r7) throws java.lang.IllegalArgumentException {
        /*
        r6 = this;
        r0 = r6.mFieldConverterCache;
        r0 = r0.get(r7);
        r0 = (nl.qbusict.cupboard.convert.FieldConverter) r0;
        if (r0 == 0) goto L_0x000b;
    L_0x000a:
        return r0;
    L_0x000b:
        r0 = r6.mFieldConverterCalls;
        r0 = r0.get();
        r0 = (java.util.Map) r0;
        if (r0 != 0) goto L_0x00a0;
    L_0x0015:
        r0 = new java.util.HashMap;
        r1 = 16;
        r0.<init>(r1);
        r1 = r6.mFieldConverterCalls;
        r1.set(r0);
        r1 = 1;
        r3 = r0;
        r4 = r1;
    L_0x0024:
        r0 = r3.get(r7);
        r0 = (nl.qbusict.cupboard.internal.convert.ConverterRegistry.FutureFieldConverter) r0;
        if (r0 == 0) goto L_0x0049;
    L_0x002c:
        r1 = r6.mEntityConverterCalls;
        r1 = r1.get();
        r1 = (java.util.Map) r1;
        r2 = r7 instanceof java.lang.Class;
        if (r2 == 0) goto L_0x000a;
    L_0x0038:
        r5 = r6.mCupboard;
        r2 = r7;
        r2 = (java.lang.Class) r2;
        r2 = r5.isRegisteredEntity(r2);
        if (r2 == 0) goto L_0x000a;
    L_0x0043:
        r1 = r1.containsKey(r7);
        if (r1 == 0) goto L_0x000a;
    L_0x0049:
        r1 = new nl.qbusict.cupboard.internal.convert.ConverterRegistry$FutureFieldConverter;	 Catch:{ all -> 0x0094 }
        r0 = 0;
        r1.<init>();	 Catch:{ all -> 0x0094 }
        r3.put(r7, r1);	 Catch:{ all -> 0x0094 }
        r0 = r6.mFieldConverterFactories;	 Catch:{ all -> 0x0094 }
        r2 = r0.iterator();	 Catch:{ all -> 0x0094 }
    L_0x0058:
        r0 = r2.hasNext();	 Catch:{ all -> 0x0094 }
        if (r0 == 0) goto L_0x007f;
    L_0x005e:
        r0 = r2.next();	 Catch:{ all -> 0x0094 }
        r0 = (nl.qbusict.cupboard.convert.FieldConverterFactory) r0;	 Catch:{ all -> 0x0094 }
        r5 = r6.mCupboard;	 Catch:{ all -> 0x0094 }
        r0 = r0.create(r5, r7);	 Catch:{ all -> 0x0094 }
        if (r0 == 0) goto L_0x0058;
    L_0x006c:
        r1.setDelegate(r0);	 Catch:{ all -> 0x0094 }
        r1 = r6.mFieldConverterCache;	 Catch:{ all -> 0x0094 }
        r1.put(r7, r0);	 Catch:{ all -> 0x0094 }
        r3.remove(r7);
        if (r4 == 0) goto L_0x000a;
    L_0x0079:
        r1 = r6.mFieldConverterCalls;
        r1.remove();
        goto L_0x000a;
    L_0x007f:
        r0 = new java.lang.IllegalArgumentException;	 Catch:{ all -> 0x0094 }
        r1 = new java.lang.StringBuilder;	 Catch:{ all -> 0x0094 }
        r2 = "Cannot convert field of type";
        r1.<init>(r2);	 Catch:{ all -> 0x0094 }
        r1 = r1.append(r7);	 Catch:{ all -> 0x0094 }
        r1 = r1.toString();	 Catch:{ all -> 0x0094 }
        r0.<init>(r1);	 Catch:{ all -> 0x0094 }
        throw r0;	 Catch:{ all -> 0x0094 }
    L_0x0094:
        r0 = move-exception;
        r3.remove(r7);
        if (r4 == 0) goto L_0x009f;
    L_0x009a:
        r1 = r6.mFieldConverterCalls;
        r1.remove();
    L_0x009f:
        throw r0;
    L_0x00a0:
        r1 = 0;
        r3 = r0;
        r4 = r1;
        goto L_0x0024;
        */
        throw new UnsupportedOperationException("Method not decompiled: nl.qbusict.cupboard.internal.convert.ConverterRegistry.getFieldConverter(java.lang.reflect.Type):nl.qbusict.cupboard.convert.FieldConverter<T>");
    }

    public void registerEntityConverterFactory(EntityConverterFactory entityConverterFactory) {
        this.mEntityConverterFactories.add(0, entityConverterFactory);
    }

    public <T> void registerFieldConverter(Class<T> cls, FieldConverter<T> fieldConverter) {
        this.mFieldConverterCache.put(cls, fieldConverter);
    }

    public void registerFieldConverterFactory(FieldConverterFactory fieldConverterFactory) {
        this.mFieldConverterFactories.add(0, fieldConverterFactory);
    }
}
