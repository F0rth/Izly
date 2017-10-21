package nl.qbusict.cupboard.internal.convert;

import android.content.ContentValues;
import android.database.Cursor;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.HashMap;
import nl.qbusict.cupboard.Cupboard;
import nl.qbusict.cupboard.convert.EntityConverter.ColumnType;
import nl.qbusict.cupboard.convert.FieldConverter;
import nl.qbusict.cupboard.convert.FieldConverterFactory;

public class DefaultFieldConverterFactory implements FieldConverterFactory {
    private static HashMap<Type, FieldConverter<?>> sTypeConverters;

    static class BigDecimalConverter implements FieldConverter<BigDecimal> {
        private BigDecimalConverter() {
        }

        public BigDecimal fromCursorValue(Cursor cursor, int i) {
            return new BigDecimal(cursor.getString(i));
        }

        public ColumnType getColumnType() {
            return ColumnType.TEXT;
        }

        public void toContentValue(BigDecimal bigDecimal, String str, ContentValues contentValues) {
            contentValues.put(str, bigDecimal.toPlainString());
        }
    }

    static class BigIntegerConverter implements FieldConverter<BigInteger> {
        private BigIntegerConverter() {
        }

        public BigInteger fromCursorValue(Cursor cursor, int i) {
            return new BigInteger(cursor.getString(i));
        }

        public ColumnType getColumnType() {
            return ColumnType.TEXT;
        }

        public void toContentValue(BigInteger bigInteger, String str, ContentValues contentValues) {
            contentValues.put(str, bigInteger.toString());
        }
    }

    static class BooleanConverter implements FieldConverter<Boolean> {
        private BooleanConverter() {
        }

        public Boolean fromCursorValue(Cursor cursor, int i) {
            boolean z = true;
            try {
                if (cursor.getInt(i) != 1) {
                    z = false;
                }
                return Boolean.valueOf(z);
            } catch (NumberFormatException e) {
                return Boolean.valueOf("true".equals(cursor.getString(i)));
            }
        }

        public ColumnType getColumnType() {
            return ColumnType.INTEGER;
        }

        public void toContentValue(Boolean bool, String str, ContentValues contentValues) {
            contentValues.put(str, bool);
        }
    }

    static class ByteArrayConverter implements FieldConverter<byte[]> {
        private ByteArrayConverter() {
        }

        public byte[] fromCursorValue(Cursor cursor, int i) {
            return cursor.getBlob(i);
        }

        public ColumnType getColumnType() {
            return ColumnType.BLOB;
        }

        public void toContentValue(byte[] bArr, String str, ContentValues contentValues) {
            contentValues.put(str, bArr);
        }
    }

    static class ByteConverter implements FieldConverter<Byte> {
        private ByteConverter() {
        }

        public Byte fromCursorValue(Cursor cursor, int i) {
            return Byte.valueOf((byte) cursor.getInt(i));
        }

        public ColumnType getColumnType() {
            return ColumnType.INTEGER;
        }

        public void toContentValue(Byte b, String str, ContentValues contentValues) {
            contentValues.put(str, b);
        }
    }

    static class DateConverter implements FieldConverter<Date> {
        private DateConverter() {
        }

        public Date fromCursorValue(Cursor cursor, int i) {
            return new Date(cursor.getLong(i));
        }

        public ColumnType getColumnType() {
            return ColumnType.INTEGER;
        }

        public void toContentValue(Date date, String str, ContentValues contentValues) {
            contentValues.put(str, Long.valueOf(date.getTime()));
        }
    }

    static class DoubleConverter implements FieldConverter<Double> {
        private DoubleConverter() {
        }

        public Double fromCursorValue(Cursor cursor, int i) {
            return Double.valueOf(cursor.getDouble(i));
        }

        public ColumnType getColumnType() {
            return ColumnType.REAL;
        }

        public void toContentValue(Double d, String str, ContentValues contentValues) {
            contentValues.put(str, d);
        }
    }

    static class FloatConverter implements FieldConverter<Float> {
        private FloatConverter() {
        }

        public Float fromCursorValue(Cursor cursor, int i) {
            return Float.valueOf(cursor.getFloat(i));
        }

        public ColumnType getColumnType() {
            return ColumnType.REAL;
        }

        public void toContentValue(Float f, String str, ContentValues contentValues) {
            contentValues.put(str, f);
        }
    }

    static class IntegerConverter implements FieldConverter<Integer> {
        private IntegerConverter() {
        }

        public Integer fromCursorValue(Cursor cursor, int i) {
            return Integer.valueOf(cursor.getInt(i));
        }

        public ColumnType getColumnType() {
            return ColumnType.INTEGER;
        }

        public void toContentValue(Integer num, String str, ContentValues contentValues) {
            contentValues.put(str, num);
        }
    }

    static class LongConverter implements FieldConverter<Long> {
        private LongConverter() {
        }

        public Long fromCursorValue(Cursor cursor, int i) {
            return Long.valueOf(cursor.getLong(i));
        }

        public ColumnType getColumnType() {
            return ColumnType.INTEGER;
        }

        public void toContentValue(Long l, String str, ContentValues contentValues) {
            contentValues.put(str, l);
        }
    }

    static class ShortConverter implements FieldConverter<Short> {
        private ShortConverter() {
        }

        public Short fromCursorValue(Cursor cursor, int i) {
            return Short.valueOf(cursor.getShort(i));
        }

        public ColumnType getColumnType() {
            return ColumnType.REAL;
        }

        public void toContentValue(Short sh, String str, ContentValues contentValues) {
            contentValues.put(str, sh);
        }
    }

    static class StringConverter implements FieldConverter<String> {
        private StringConverter() {
        }

        public String fromCursorValue(Cursor cursor, int i) {
            return cursor.getString(i);
        }

        public ColumnType getColumnType() {
            return ColumnType.TEXT;
        }

        public void toContentValue(String str, String str2, ContentValues contentValues) {
            contentValues.put(str2, str);
        }
    }

    static {
        HashMap hashMap = new HashMap(25);
        sTypeConverters = hashMap;
        hashMap.put(BigDecimal.class, new BigDecimalConverter());
        sTypeConverters.put(BigInteger.class, new BigIntegerConverter());
        sTypeConverters.put(String.class, new StringConverter());
        sTypeConverters.put(Integer.TYPE, new IntegerConverter());
        sTypeConverters.put(Integer.class, new IntegerConverter());
        sTypeConverters.put(Float.TYPE, new FloatConverter());
        sTypeConverters.put(Float.class, new FloatConverter());
        sTypeConverters.put(Short.TYPE, new ShortConverter());
        sTypeConverters.put(Short.class, new ShortConverter());
        sTypeConverters.put(Double.TYPE, new DoubleConverter());
        sTypeConverters.put(Double.class, new DoubleConverter());
        sTypeConverters.put(Long.TYPE, new LongConverter());
        sTypeConverters.put(Long.class, new LongConverter());
        sTypeConverters.put(Byte.TYPE, new ByteConverter());
        sTypeConverters.put(Byte.class, new ByteConverter());
        sTypeConverters.put(byte[].class, new ByteArrayConverter());
        sTypeConverters.put(Boolean.TYPE, new BooleanConverter());
        sTypeConverters.put(Boolean.class, new BooleanConverter());
        sTypeConverters.put(Date.class, new DateConverter());
    }

    public FieldConverter<?> create(Cupboard cupboard, Type type) {
        return !(type instanceof Class) ? null : (FieldConverter) sTypeConverters.get(type);
    }
}
