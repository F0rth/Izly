package nl.qbusict.cupboard.internal.convert;

import android.content.ContentValues;
import android.database.Cursor;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import nl.qbusict.cupboard.Cupboard;
import nl.qbusict.cupboard.convert.EntityConverter.ColumnType;
import nl.qbusict.cupboard.convert.FieldConverter;
import nl.qbusict.cupboard.convert.FieldConverterFactory;

public class EnumFieldConverterFactory implements FieldConverterFactory {

    static class EnumConverter<E extends Enum> implements FieldConverter<E> {
        private final Class<E> mEnumClass;

        public EnumConverter(Class<E> cls) {
            this.mEnumClass = cls;
        }

        public E fromCursorValue(Cursor cursor, int i) {
            return Enum.valueOf(this.mEnumClass, cursor.getString(i));
        }

        public ColumnType getColumnType() {
            return ColumnType.TEXT;
        }

        public void toContentValue(E e, String str, ContentValues contentValues) {
            contentValues.put(str, e.toString());
        }
    }

    public FieldConverter<?> create(Cupboard cupboard, Type type) {
        Type type2 = ((type instanceof ParameterizedType) && ((ParameterizedType) type).getRawType() == Enum.class) ? ((ParameterizedType) type).getActualTypeArguments()[0] : type;
        if (!(type2 instanceof Class)) {
            return null;
        }
        Class cls = (Class) type2;
        return cls.isEnum() ? new EnumConverter(cls) : null;
    }
}
