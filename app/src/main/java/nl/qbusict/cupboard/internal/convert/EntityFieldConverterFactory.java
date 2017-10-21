package nl.qbusict.cupboard.internal.convert;

import android.content.ContentValues;
import android.database.Cursor;
import java.lang.reflect.Type;
import nl.qbusict.cupboard.Cupboard;
import nl.qbusict.cupboard.convert.EntityConverter;
import nl.qbusict.cupboard.convert.EntityConverter.ColumnType;
import nl.qbusict.cupboard.convert.FieldConverter;
import nl.qbusict.cupboard.convert.FieldConverterFactory;

public class EntityFieldConverterFactory implements FieldConverterFactory {

    static class EntityFieldConverter implements FieldConverter<Object> {
        private final Class<Object> entityClass;
        private final EntityConverter<Object> mEntityConverter;

        public EntityFieldConverter(Class<Object> cls, EntityConverter<?> entityConverter) {
            this.mEntityConverter = entityConverter;
            this.entityClass = cls;
        }

        public Object fromCursorValue(Cursor cursor, int i) {
            long j = cursor.getLong(i);
            try {
                Object newInstance = this.entityClass.newInstance();
                this.mEntityConverter.setId(Long.valueOf(j), newInstance);
                return newInstance;
            } catch (Throwable e) {
                throw new RuntimeException(e);
            } catch (Throwable e2) {
                throw new RuntimeException(e2);
            }
        }

        public ColumnType getColumnType() {
            return ColumnType.INTEGER;
        }

        public void toContentValue(Object obj, String str, ContentValues contentValues) {
            contentValues.put(str, this.mEntityConverter.getId(obj));
        }
    }

    public FieldConverter<?> create(Cupboard cupboard, Type type) {
        if (!(type instanceof Class)) {
            return null;
        }
        if (!cupboard.isRegisteredEntity((Class) type)) {
            return null;
        }
        return new EntityFieldConverter((Class) type, cupboard.getEntityConverter((Class) type));
    }
}
