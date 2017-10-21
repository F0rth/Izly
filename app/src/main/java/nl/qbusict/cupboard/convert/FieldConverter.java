package nl.qbusict.cupboard.convert;

import android.content.ContentValues;
import android.database.Cursor;
import nl.qbusict.cupboard.convert.EntityConverter.ColumnType;

public interface FieldConverter<T> {
    T fromCursorValue(Cursor cursor, int i);

    ColumnType getColumnType();

    void toContentValue(T t, String str, ContentValues contentValues);
}
