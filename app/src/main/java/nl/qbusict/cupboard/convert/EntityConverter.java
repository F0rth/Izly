package nl.qbusict.cupboard.convert;

import android.content.ContentValues;
import android.database.Cursor;
import java.util.List;
import nl.qbusict.cupboard.annotation.Index;

public interface EntityConverter<T> {

    public static class Column {
        public final Index index;
        public final String name;
        public final ColumnType type;

        public Column(String str, ColumnType columnType) {
            this(str, columnType, null);
        }

        public Column(String str, ColumnType columnType, Index index) {
            this.name = str;
            this.type = columnType;
            this.index = index;
        }

        public boolean equals(Object obj) {
            if (!(obj instanceof Column)) {
                return obj instanceof String ? this.name.equals(obj) : super.equals(obj);
            } else {
                Column column = (Column) obj;
                return column.name.equals(this.name) && column.type == this.type;
            }
        }

        public int hashCode() {
            return this.name.hashCode() * 37;
        }
    }

    public enum ColumnType {
        TEXT,
        INTEGER,
        REAL,
        BLOB,
        JOIN
    }

    T fromCursor(Cursor cursor);

    List<Column> getColumns();

    Long getId(T t);

    String getTable();

    void setId(Long l, T t);

    void toValues(T t, ContentValues contentValues);
}
