package nl.qbusict.cupboard;

import android.content.ContentProviderOperation;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import nl.qbusict.cupboard.convert.EntityConverter;
import nl.qbusict.cupboard.convert.EntityConverterFactory;
import nl.qbusict.cupboard.convert.FieldConverter;
import nl.qbusict.cupboard.convert.FieldConverterFactory;
import nl.qbusict.cupboard.internal.convert.ConverterRegistry;

public class Cupboard {
    private final ConverterRegistry mConverterRegistry = new ConverterRegistry(this);
    private Set<Class<?>> mEntities = new HashSet(128);
    private boolean mUseAnnotations = false;

    public <T> EntityConverter<T> getDelegateEntityConverter(EntityConverterFactory entityConverterFactory, Class<T> cls) throws IllegalArgumentException {
        return this.mConverterRegistry.getDelegateEntityConverter(entityConverterFactory, cls);
    }

    public FieldConverter<?> getDelegateFieldConverter(FieldConverterFactory fieldConverterFactory, Type type) throws IllegalArgumentException {
        return this.mConverterRegistry.getDelegateFieldConverter(fieldConverterFactory, type);
    }

    public <T> EntityConverter<T> getEntityConverter(Class<T> cls) throws IllegalArgumentException {
        if (isRegisteredEntity(cls)) {
            return this.mConverterRegistry.getEntityConverter(cls);
        }
        throw new IllegalArgumentException("Entity is not registered: " + cls);
    }

    public FieldConverter<?> getFieldConverter(Type type) throws IllegalArgumentException {
        return this.mConverterRegistry.getFieldConverter(type);
    }

    public Collection<Class<?>> getRegisteredEntities() {
        return Collections.unmodifiableSet(this.mEntities);
    }

    public <T> String getTable(Class<T> cls) {
        return withEntity(cls).getTable();
    }

    public boolean isRegisteredEntity(Class<?> cls) {
        return this.mEntities.contains(cls);
    }

    public boolean isUseAnnotations() {
        return this.mUseAnnotations;
    }

    public <T> void register(Class<T> cls) {
        this.mEntities.add(cls);
    }

    void registerEntityConverterFactory(EntityConverterFactory entityConverterFactory) {
        this.mConverterRegistry.registerEntityConverterFactory(entityConverterFactory);
    }

    <T> void registerFieldConverter(Class<T> cls, FieldConverter<T> fieldConverter) {
        this.mConverterRegistry.registerFieldConverter(cls, fieldConverter);
    }

    void registerFieldConverterFactory(FieldConverterFactory fieldConverterFactory) {
        this.mConverterRegistry.registerFieldConverterFactory(fieldConverterFactory);
    }

    void setUseAnnotations(boolean z) {
        this.mUseAnnotations = z;
    }

    public ProviderCompartment withContext(Context context) {
        return new ProviderCompartment(this, context);
    }

    public CursorCompartment withCursor(Cursor cursor) {
        return new CursorCompartment(this, cursor);
    }

    public DatabaseCompartment withDatabase(SQLiteDatabase sQLiteDatabase) {
        return new DatabaseCompartment(this, sQLiteDatabase);
    }

    public <T> EntityCompartment<T> withEntity(Class<T> cls) {
        return new EntityCompartment(this, cls);
    }

    public ProviderOperationsCompartment withOperations(ArrayList<ContentProviderOperation> arrayList) {
        return new ProviderOperationsCompartment(this, arrayList);
    }
}
