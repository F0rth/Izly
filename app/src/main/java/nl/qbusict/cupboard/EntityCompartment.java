package nl.qbusict.cupboard;

import android.content.ContentValues;
import nl.qbusict.cupboard.convert.EntityConverter;

public class EntityCompartment<T> extends BaseCompartment {
    private final EntityConverter<T> mConverter;

    protected EntityCompartment(Cupboard cupboard, Class<T> cls) {
        super(cupboard);
        this.mConverter = getConverter(cls);
    }

    public String getTable() {
        return this.mConverter.getTable();
    }

    public ContentValues toContentValues(T t) {
        return toContentValues(t, null);
    }

    public ContentValues toContentValues(T t, ContentValues contentValues) {
        if (contentValues == null) {
            contentValues = new ContentValues(this.mConverter.getColumns().size());
        }
        this.mConverter.toValues(t, contentValues);
        return contentValues;
    }
}
