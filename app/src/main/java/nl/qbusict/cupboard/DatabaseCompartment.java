package nl.qbusict.cupboard;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import nl.qbusict.cupboard.annotation.Index;
import nl.qbusict.cupboard.convert.EntityConverter;
import nl.qbusict.cupboard.convert.EntityConverter.Column;
import nl.qbusict.cupboard.convert.EntityConverter.ColumnType;
import nl.qbusict.cupboard.internal.IndexStatement;
import nl.qbusict.cupboard.internal.IndexStatement.Builder;

@SuppressLint({"DefaultLocale"})
public class DatabaseCompartment extends BaseCompartment {
    private static final String QUERY_BY_ID = "_id = ?";
    private final SQLiteDatabase mDatabase;

    public static class QueryBuilder<T> {
        private final DatabaseCompartment mCompartment;
        private boolean mDistinct = false;
        private final Class<T> mEntityClass;
        private String mGroup;
        private String mHaving;
        private String mLimit = null;
        private String mOffset = null;
        private String mOrder;
        private String[] mProjection;
        private String mSelection;
        private String[] mSelectionArgs;

        QueryBuilder(Class<T> cls, DatabaseCompartment databaseCompartment) {
            this.mEntityClass = cls;
            this.mCompartment = databaseCompartment;
        }

        public QueryBuilder<T> byId(long j) {
            this.mSelection = DatabaseCompartment.QUERY_BY_ID;
            this.mSelectionArgs = new String[]{String.valueOf(j)};
            limit(1);
            return this;
        }

        public QueryBuilder<T> distinct() {
            this.mDistinct = true;
            return this;
        }

        public T get() {
            return query().get();
        }

        public Cursor getCursor() {
            return query().getCursor();
        }

        public QueryBuilder<T> groupBy(String str) {
            this.mGroup = str;
            return this;
        }

        public QueryBuilder<T> having(String str) {
            this.mHaving = str;
            return this;
        }

        public QueryBuilder<T> limit(int i) {
            if (i <= 0) {
                throw new IllegalArgumentException("Limit must be greater or equal to 1");
            }
            this.mLimit = String.valueOf(i);
            return this;
        }

        public List<T> list() {
            return query().list();
        }

        public QueryBuilder<T> offset(int i) {
            if (i <= 0) {
                throw new IllegalArgumentException("Offset must be greater or equal to 1");
            }
            this.mOffset = String.valueOf(i);
            return this;
        }

        public QueryBuilder<T> orderBy(String str) {
            this.mOrder = str;
            return this;
        }

        public QueryResultIterable<T> query() {
            if (this.mLimit != null && this.mOffset != null) {
                this.mLimit = String.format("%s,%s", new Object[]{this.mOffset, this.mLimit});
            } else if (this.mOffset != null) {
                this.mLimit = String.format("%s,%d", new Object[]{this.mOffset, Long.valueOf(Long.MAX_VALUE)});
            }
            return this.mCompartment.query(this.mEntityClass, this.mProjection, this.mSelection, this.mSelectionArgs, this.mGroup, this.mHaving, this.mOrder, this.mLimit, this.mDistinct);
        }

        public QueryBuilder<T> withProjection(String... strArr) {
            this.mProjection = strArr;
            return this;
        }

        public QueryBuilder<T> withSelection(String str, String... strArr) {
            this.mSelection = str;
            this.mSelectionArgs = strArr;
            return this;
        }
    }

    protected DatabaseCompartment(Cupboard cupboard, SQLiteDatabase sQLiteDatabase) {
        super(cupboard);
        this.mDatabase = sQLiteDatabase;
    }

    private boolean diffAndUpdateIndexes(SQLiteDatabase sQLiteDatabase, String str, List<Column> list) {
        String creationSql;
        Cursor rawQuery = sQLiteDatabase.rawQuery("select name, sql from sqlite_master where type = 'index' and tbl_name = '" + str + "' and name like '_cb" + "%'", null);
        Map hashMap = new HashMap();
        while (rawQuery.moveToNext()) {
            hashMap.put(rawQuery.getString(0), rawQuery.getString(1));
        }
        rawQuery.close();
        Builder builder = new Builder();
        for (Column column : list) {
            if (column.type != ColumnType.JOIN) {
                Index index = column.index;
                if (index != null) {
                    builder.addIndexedColumn(str, column.name, index);
                }
            }
        }
        Map buildAsMap = builder.buildAsMap();
        Collection keySet = hashMap.keySet();
        Collection keySet2 = buildAsMap.keySet();
        Set<String> hashSet = new HashSet(keySet);
        hashSet.removeAll(keySet2);
        boolean z = false;
        for (String creationSql2 : hashSet) {
            sQLiteDatabase.execSQL("drop index if exists " + creationSql2);
            z = true;
        }
        Set<String> hashSet2 = new HashSet(keySet2);
        hashSet2.removeAll(keySet);
        boolean z2 = z;
        for (String creationSql22 : hashSet2) {
            sQLiteDatabase.execSQL(((IndexStatement) buildAsMap.get(creationSql22)).getCreationSql(str));
            z2 |= 1;
        }
        hashSet = new HashSet(keySet2);
        hashSet.retainAll(keySet);
        boolean z3 = z2;
        for (String str2 : hashSet) {
            String str3 = (String) hashMap.get(str2);
            creationSql22 = ((IndexStatement) buildAsMap.get(str2)).getCreationSql(str, false);
            if (str3.equalsIgnoreCase(creationSql22)) {
                z = z3;
            } else {
                sQLiteDatabase.execSQL("drop index if exists " + str2);
                sQLiteDatabase.execSQL(creationSql22);
                z = z3 | 1;
            }
            z3 = z;
        }
        return z3;
    }

    private void dropIndices(Class<?> cls) {
        Cursor rawQuery = this.mDatabase.rawQuery("select name, sql from sqlite_master where type = 'index' and tbl_name = '" + this.mCupboard.getTable(cls) + '\'', null);
        while (rawQuery.moveToNext()) {
            try {
                this.mDatabase.execSQL("drop index '" + rawQuery.getString(0) + "'");
            } finally {
                rawQuery.close();
            }
        }
    }

    private <T> QueryResultIterable<T> query(Class<T> cls, String[] strArr, String str, String[] strArr2, String str2, String str3, String str4, String str5, boolean z) {
        EntityConverter converter = getConverter(cls);
        return new QueryResultIterable(this.mDatabase.query(z, quoteTable(converter.getTable()), strArr, str, strArr2, str2, str3, str4, str5), converter);
    }

    private String quoteTable(String str) {
        return "'" + str + "'";
    }

    boolean createNewTable(SQLiteDatabase sQLiteDatabase, String str, List<Column> list) {
        StringBuilder append = new StringBuilder("create table '").append(str).append("' (_id integer primary key autoincrement");
        Builder builder = new Builder();
        for (Column column : list) {
            if (column.type != ColumnType.JOIN) {
                String str2 = column.name;
                if (!str2.equals("_id")) {
                    append.append(", '").append(str2).append("'");
                    append.append(" ").append(column.type.toString());
                }
                Index index = column.index;
                if (index != null) {
                    builder.addIndexedColumn(str, str2, index);
                }
            }
        }
        append.append(");");
        sQLiteDatabase.execSQL(append.toString());
        for (IndexStatement creationSql : builder.build()) {
            sQLiteDatabase.execSQL(creationSql.getCreationSql(str));
        }
        return true;
    }

    public void createTables() {
        for (Class entityConverter : this.mCupboard.getRegisteredEntities()) {
            EntityConverter entityConverter2 = this.mCupboard.getEntityConverter(entityConverter);
            createNewTable(this.mDatabase, entityConverter2.getTable(), entityConverter2.getColumns());
        }
    }

    public int delete(Class<?> cls, String str, String... strArr) {
        return this.mDatabase.delete(quoteTable(getConverter(cls).getTable()), str, strArr);
    }

    public boolean delete(Class<?> cls, long j) {
        return this.mDatabase.delete(quoteTable(getConverter(cls).getTable()), QUERY_BY_ID, new String[]{String.valueOf(j)}) > 0;
    }

    public <T> boolean delete(T t) {
        Class cls = t.getClass();
        if (getConverter(cls).getId(t) == null) {
            return false;
        }
        return delete(cls, QUERY_BY_ID, String.valueOf(getConverter(cls).getId(t))) > 0;
    }

    public void dropAllIndices() {
        for (Class dropIndices : this.mCupboard.getRegisteredEntities()) {
            dropIndices(dropIndices);
        }
    }

    public void dropAllTables() {
        for (Class entityConverter : this.mCupboard.getRegisteredEntities()) {
            this.mDatabase.execSQL("DROP TABLE IF EXISTS " + quoteTable(this.mCupboard.getEntityConverter(entityConverter).getTable()));
        }
    }

    public <T> T get(Class<T> cls, long j) {
        return query(cls).byId(j).get();
    }

    public <T> T get(T t) throws IllegalArgumentException {
        EntityConverter converter = getConverter(t.getClass());
        if (converter.getId(t) != null) {
            return get(t.getClass(), converter.getId(t).longValue());
        }
        throw new IllegalArgumentException("id of entity " + t.getClass() + " is not set");
    }

    public long put(Class<?> cls, ContentValues contentValues) {
        EntityConverter converter = getConverter(cls);
        Long asLong = contentValues.getAsLong("_id");
        if (asLong == null) {
            return Long.valueOf(this.mDatabase.insertOrThrow(quoteTable(converter.getTable()), "_id", contentValues)).longValue();
        }
        this.mDatabase.replaceOrThrow(quoteTable(converter.getTable()), "_id", contentValues);
        return asLong.longValue();
    }

    public <T> long put(T t) {
        EntityConverter converter = getConverter(t.getClass());
        ContentValues contentValues = new ContentValues();
        converter.toValues(t, contentValues);
        Long asLong = contentValues.getAsLong("_id");
        long put = put(t.getClass(), contentValues);
        if (asLong == null) {
            converter.setId(Long.valueOf(put), t);
        }
        return asLong == null ? put : asLong.longValue();
    }

    public void put(Collection<?> collection) {
        boolean inTransaction = this.mDatabase.inTransaction();
        this.mDatabase.beginTransaction();
        try {
            for (Object put : collection) {
                put(put);
                if (!inTransaction) {
                    this.mDatabase.yieldIfContendedSafely();
                }
            }
            this.mDatabase.setTransactionSuccessful();
        } finally {
            this.mDatabase.endTransaction();
        }
    }

    public void put(Object... objArr) {
        boolean inTransaction = this.mDatabase.inTransaction();
        this.mDatabase.beginTransaction();
        try {
            for (Object put : objArr) {
                put(put);
                if (!inTransaction) {
                    this.mDatabase.yieldIfContendedSafely();
                }
            }
            this.mDatabase.setTransactionSuccessful();
        } finally {
            this.mDatabase.endTransaction();
        }
    }

    public <T> QueryBuilder<T> query(Class<T> cls) {
        return new QueryBuilder(cls, this);
    }

    public int update(Class<?> cls, ContentValues contentValues) {
        EntityConverter converter = getConverter(cls);
        if (!contentValues.containsKey("_id")) {
            return this.mDatabase.update(quoteTable(converter.getTable()), contentValues, null, null);
        }
        return this.mDatabase.update(quoteTable(converter.getTable()), contentValues, QUERY_BY_ID, new String[]{contentValues.getAsString("_id")});
    }

    public int update(Class<?> cls, ContentValues contentValues, String str, String... strArr) {
        return this.mDatabase.update(quoteTable(getConverter(cls).getTable()), contentValues, str, strArr);
    }

    boolean updateTable(SQLiteDatabase sQLiteDatabase, String str, Cursor cursor, List<Column> list) {
        Locale locale = Locale.US;
        Map hashMap = new HashMap(list.size());
        for (Column column : list) {
            if (column.type != ColumnType.JOIN) {
                hashMap.put(column.name.toLowerCase(locale), column);
            }
        }
        int columnIndex = cursor.getColumnIndex("name");
        while (cursor.moveToNext()) {
            hashMap.remove(cursor.getString(columnIndex).toLowerCase(locale));
        }
        columnIndex = 0;
        if (!hashMap.isEmpty()) {
            for (Column column2 : hashMap.values()) {
                sQLiteDatabase.execSQL("alter table '" + str + "' add column '" + column2.name + "' " + column2.type.toString());
            }
            columnIndex = 1;
        }
        return columnIndex | diffAndUpdateIndexes(sQLiteDatabase, str, list);
    }

    boolean updateTable(SQLiteDatabase sQLiteDatabase, String str, List<Column> list) {
        Cursor rawQuery = sQLiteDatabase.rawQuery("pragma table_info('" + str + "')", null);
        try {
            boolean createNewTable;
            if (rawQuery.getCount() == 0) {
                createNewTable = createNewTable(sQLiteDatabase, str, list);
            } else {
                createNewTable = updateTable(sQLiteDatabase, str, rawQuery, list);
                rawQuery.close();
            }
            return createNewTable;
        } finally {
            rawQuery.close();
        }
    }

    public void upgradeTables() {
        for (Class entityConverter : this.mCupboard.getRegisteredEntities()) {
            EntityConverter entityConverter2 = this.mCupboard.getEntityConverter(entityConverter);
            updateTable(this.mDatabase, entityConverter2.getTable(), entityConverter2.getColumns());
        }
    }
}
