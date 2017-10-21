package nl.qbusict.cupboard.convert;

import android.content.ContentValues;
import android.database.Cursor;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import nl.qbusict.cupboard.Cupboard;
import nl.qbusict.cupboard.annotation.Ignore;
import nl.qbusict.cupboard.annotation.Index;
import nl.qbusict.cupboard.convert.EntityConverter.Column;
import nl.qbusict.cupboard.convert.EntityConverter.ColumnType;

public class ReflectiveEntityConverter<T> implements EntityConverter<T> {
    private final Class<T> mClass;
    private final List<Column> mColumns;
    protected final Cupboard mCupboard;
    private Property mIdProperty;
    private final Property[] mProperties;
    private final boolean mUseAnnotations;

    static class Property {
        ColumnType columnType;
        Field field;
        FieldConverter<Object> fieldConverter;
        String name;
        Class<?> type;

        private Property() {
        }
    }

    public ReflectiveEntityConverter(Cupboard cupboard, Class<T> cls) {
        this(cupboard, cls, Collections.emptyList(), Collections.emptyList());
    }

    public ReflectiveEntityConverter(Cupboard cupboard, Class<T> cls, Collection<String> collection) {
        this(cupboard, cls, collection, Collections.emptyList());
    }

    public ReflectiveEntityConverter(Cupboard cupboard, Class<T> cls, Collection<String> collection, Collection<Column> collection2) {
        this.mCupboard = cupboard;
        this.mUseAnnotations = cupboard.isUseAnnotations();
        Field[] allFields = getAllFields(cls);
        List arrayList = new ArrayList(allFields.length);
        this.mClass = cls;
        List arrayList2 = new ArrayList();
        for (Field field : allFields) {
            if (!(collection.contains(field.getName()) || isIgnored(field))) {
                Type genericType = field.getGenericType();
                FieldConverter fieldConverter = getFieldConverter(field);
                if (fieldConverter == null) {
                    throw new IllegalArgumentException("Do not know how to convert field " + field.getName() + " in entity " + cls.getName() + " of type " + genericType);
                } else if (fieldConverter.getColumnType() != null) {
                    Property property = new Property();
                    property.field = field;
                    if (!field.isAccessible()) {
                        field.setAccessible(true);
                    }
                    property.name = getColumn(field);
                    property.type = field.getType();
                    property.fieldConverter = fieldConverter;
                    property.columnType = isReadOnlyColumn(field) ? ColumnType.JOIN : fieldConverter.getColumnType();
                    arrayList2.add(property);
                    if ("_id".equals(property.name)) {
                        this.mIdProperty = property;
                    }
                    arrayList.add(new Column(property.name, property.columnType, getIndexes(field)));
                }
            }
        }
        arrayList.addAll(collection2);
        this.mColumns = Collections.unmodifiableList(arrayList);
        this.mProperties = (Property[]) arrayList2.toArray(new Property[arrayList2.size()]);
    }

    private Field[] getAllFields(Class<?> cls) {
        if (cls.getSuperclass() == null) {
            return cls.getDeclaredFields();
        }
        List arrayList = new ArrayList(256);
        Class superclass;
        do {
            arrayList.addAll(Arrays.asList(superclass.getDeclaredFields()));
            superclass = superclass.getSuperclass();
        } while (superclass != null);
        return (Field[]) arrayList.toArray(new Field[arrayList.size()]);
    }

    private static String getTable(Class<?> cls) {
        return cls.getSimpleName();
    }

    public T fromCursor(Cursor cursor) {
        try {
            T newInstance = this.mClass.newInstance();
            int columnCount = cursor.getColumnCount();
            int i = 0;
            while (i < this.mProperties.length && i < columnCount) {
                Property property = this.mProperties[i];
                Class cls = property.type;
                if (!cursor.isNull(i)) {
                    property.field.set(newInstance, property.fieldConverter.fromCursorValue(cursor, i));
                } else if (!cls.isPrimitive()) {
                    property.field.set(newInstance, null);
                }
                i++;
            }
            return newInstance;
        } catch (Throwable e) {
            throw new RuntimeException(e);
        } catch (Throwable e2) {
            throw new RuntimeException(e2);
        }
    }

    protected String getColumn(Field field) {
        if (this.mUseAnnotations) {
            nl.qbusict.cupboard.annotation.Column column = (nl.qbusict.cupboard.annotation.Column) field.getAnnotation(nl.qbusict.cupboard.annotation.Column.class);
            if (column != null) {
                return column.value();
            }
        }
        return field.getName();
    }

    public List<Column> getColumns() {
        return this.mColumns;
    }

    protected FieldConverter<?> getFieldConverter(Field field) {
        return this.mCupboard.getFieldConverter(field.getGenericType());
    }

    public Long getId(T t) {
        if (this.mIdProperty == null) {
            return null;
        }
        try {
            return (Long) this.mIdProperty.field.get(t);
        } catch (Throwable e) {
            throw new RuntimeException(e);
        } catch (Throwable e2) {
            throw new RuntimeException(e2);
        }
    }

    protected Index getIndexes(Field field) {
        if (this.mUseAnnotations) {
            Index index = (Index) field.getAnnotation(Index.class);
            if (index != null) {
                return index;
            }
        }
        return null;
    }

    public String getTable() {
        return getTable(this.mClass);
    }

    protected boolean isIgnored(Field field) {
        int modifiers = field.getModifiers();
        boolean z = Modifier.isFinal(modifiers) || Modifier.isStatic(modifiers) || Modifier.isTransient(modifiers);
        return this.mUseAnnotations ? z || field.getAnnotation(Ignore.class) != null : z;
    }

    protected boolean isReadOnlyColumn(Field field) {
        return false;
    }

    public void setId(Long l, T t) {
        if (this.mIdProperty != null) {
            try {
                this.mIdProperty.field.set(t, l);
            } catch (Throwable e) {
                throw new RuntimeException(e);
            } catch (Throwable e2) {
                throw new RuntimeException(e2);
            }
        }
    }

    public void toValues(T t, ContentValues contentValues) {
        for (Property property : this.mProperties) {
            if (property.columnType != ColumnType.JOIN) {
                try {
                    Object obj = property.field.get(t);
                    if (obj != null) {
                        property.fieldConverter.toContentValue(obj, property.name, contentValues);
                    } else if (!property.name.equals("_id")) {
                        contentValues.putNull(property.name);
                    }
                } catch (Throwable e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}
