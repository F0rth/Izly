package nl.qbusict.cupboard;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.net.Uri;
import java.util.Collection;
import java.util.List;
import nl.qbusict.cupboard.convert.EntityConverter;

public class ProviderCompartment extends BaseCompartment {
    private static final String QUERY_BY_ID = "_id = ?";
    private final ContentResolver mResolver;

    public static class QueryBuilder<T> {
        private final ProviderCompartment mCompartment;
        private final Class<T> mEntityClass;
        private String mOrder;
        private String[] mProjection;
        private String mSelection;
        private String[] mSelectionArgs;
        private final Uri mUri;

        public QueryBuilder(Uri uri, Class<T> cls, ProviderCompartment providerCompartment) {
            this.mEntityClass = cls;
            this.mCompartment = providerCompartment;
            this.mUri = uri;
        }

        public T get() {
            return query().get();
        }

        public Cursor getCursor() {
            return query().getCursor();
        }

        public List<T> list() {
            return query().list();
        }

        public QueryBuilder<T> orderBy(String str) {
            this.mOrder = str;
            return this;
        }

        public QueryResultIterable<T> query() {
            return this.mCompartment.query(this.mUri, this.mEntityClass, this.mProjection, this.mSelection, this.mSelectionArgs, this.mOrder);
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

    protected ProviderCompartment(Cupboard cupboard, Context context) {
        super(cupboard);
        this.mResolver = context.getContentResolver();
    }

    private <T> QueryResultIterable<T> query(Uri uri, Class<T> cls, String[] strArr, String str, String[] strArr2, String str2) {
        EntityConverter converter = getConverter(cls);
        Cursor query = this.mResolver.query(uri, strArr, str, strArr2, str2);
        if (query == null) {
            query = new MatrixCursor(new String[]{"_id"});
        }
        return new QueryResultIterable(query, converter);
    }

    public <T> int delete(Uri uri, T t) {
        Long id = getConverter(t.getClass()).getId(t);
        return id == null ? 0 : this.mResolver.delete(ContentUris.withAppendedId(uri, id.longValue()), null, null);
    }

    public int delete(Uri uri, String str, String... strArr) {
        return this.mResolver.delete(uri, str, strArr);
    }

    public <T> T get(Uri uri, Class<T> cls) {
        return query(uri, cls).query().get();
    }

    public <T> T get(Uri uri, T t) {
        Long id = getConverter(t.getClass()).getId(t);
        if (id != null) {
            return get(ContentUris.withAppendedId(uri, id.longValue()), t.getClass());
        }
        throw new IllegalArgumentException("entity does not have it's id set");
    }

    public <T> int put(Uri uri, Class<T> cls, Collection<T> collection) {
        return put(uri, (Class) cls, collection.toArray());
    }

    public <T> int put(Uri uri, Class<T> cls, T... tArr) {
        EntityConverter converter = getConverter(cls);
        ContentValues[] contentValuesArr = new ContentValues[tArr.length];
        int size = converter.getColumns().size();
        for (int i = 0; i < tArr.length; i++) {
            contentValuesArr[i] = new ContentValues(size);
            converter.toValues(tArr[i], contentValuesArr[i]);
        }
        return this.mResolver.bulkInsert(uri, contentValuesArr);
    }

    public <T> Uri put(Uri uri, T t) {
        EntityConverter converter = getConverter(t.getClass());
        ContentValues contentValues = new ContentValues(converter.getColumns().size());
        converter.toValues(t, contentValues);
        Long id = converter.getId(t);
        return id == null ? this.mResolver.insert(uri, contentValues) : this.mResolver.insert(ContentUris.withAppendedId(uri, id.longValue()), contentValues);
    }

    public <T> QueryBuilder<T> query(Uri uri, Class<T> cls) {
        return new QueryBuilder(uri, cls, this);
    }

    public int update(Uri uri, ContentValues contentValues) {
        if (!contentValues.containsKey("_id")) {
            return this.mResolver.update(uri, contentValues, null, null);
        }
        return this.mResolver.update(ContentUris.withAppendedId(uri, contentValues.getAsLong("_id").longValue()), contentValues, QUERY_BY_ID, new String[]{contentValues.getAsString("_id")});
    }

    public int update(Uri uri, ContentValues contentValues, String str, String... strArr) {
        return this.mResolver.update(uri, contentValues, str, strArr);
    }
}
