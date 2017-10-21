package nl.qbusict.cupboard;

import android.database.Cursor;
import android.database.CursorWrapper;
import java.util.Arrays;
import java.util.List;
import nl.qbusict.cupboard.convert.EntityConverter.Column;

class PreferredColumnOrderCursorWrapper extends CursorWrapper {
    private final int[] mColumnMap;
    private String[] mColumns;

    public PreferredColumnOrderCursorWrapper(Cursor cursor, List<Column> list) {
        this(cursor, toColumNames(list));
    }

    public PreferredColumnOrderCursorWrapper(Cursor cursor, String[] strArr) {
        super(cursor);
        this.mColumns = strArr;
        this.mColumnMap = new int[strArr.length];
        Arrays.fill(this.mColumnMap, -1);
        this.mColumns = remapColumns(cursor.getColumnNames(), strArr);
    }

    private String[] remapColumns(String[] strArr, String[] strArr2) {
        int i = 0;
        for (int i2 = 0; i2 < strArr2.length; i2++) {
            int columnIndex = super.getColumnIndex(strArr2[i2]);
            this.mColumnMap[i2] = columnIndex;
            if (columnIndex != -1) {
                i = i2;
            }
        }
        if (i + 1 >= strArr2.length) {
            return strArr2;
        }
        Object obj = new String[(i + 1)];
        System.arraycopy(strArr2, 0, obj, 0, i + 1);
        return obj;
    }

    private static String[] toColumNames(List<Column> list) {
        String[] strArr = new String[list.size()];
        for (int length = strArr.length - 1; length >= 0; length--) {
            strArr[length] = ((Column) list.get(length)).name;
        }
        return strArr;
    }

    public byte[] getBlob(int i) {
        int i2 = this.mColumnMap[i];
        return i2 == -1 ? null : super.getBlob(i2);
    }

    public int getColumnCount() {
        return this.mColumns.length;
    }

    public int getColumnIndex(String str) {
        throw new RuntimeException("Don't use getColumnIndex(), but use the indices supplied in the constructor.\nFor use in an EntityConverter, the columns and indices are always in the same order as returned from EntityConverter.getColumns()");
    }

    public int getColumnIndexOrThrow(String str) throws IllegalArgumentException {
        throw new RuntimeException("Don't use getColumnIndex(), but use the indices supplied in the constructor.\nFor use in an EntityConverter, the columns and indices are always in the same order as returned from EntityConverter.getColumns()");
    }

    public String[] getColumnNames() {
        return this.mColumns;
    }

    public double getDouble(int i) {
        int i2 = this.mColumnMap[i];
        return i2 == -1 ? 0.0d : super.getDouble(i2);
    }

    public float getFloat(int i) {
        int i2 = this.mColumnMap[i];
        return i2 == -1 ? 0.0f : super.getFloat(i2);
    }

    public int getInt(int i) {
        int i2 = this.mColumnMap[i];
        return i2 == -1 ? 0 : super.getInt(i2);
    }

    public long getLong(int i) {
        int i2 = this.mColumnMap[i];
        return i2 == -1 ? 0 : super.getLong(i2);
    }

    public short getShort(int i) {
        int i2 = this.mColumnMap[i];
        return i2 == -1 ? (short) 0 : super.getShort(i2);
    }

    public String getString(int i) {
        int i2 = this.mColumnMap[i];
        return i2 == -1 ? null : super.getString(i2);
    }

    public boolean isNull(int i) {
        int i2 = this.mColumnMap[i];
        return i2 == -1 ? true : super.isNull(i2);
    }
}
