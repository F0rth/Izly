package nl.qbusict.cupboard;

import android.database.Cursor;
import java.util.List;

public class CursorCompartment extends BaseCompartment {
    private final Cursor mCursor;

    protected CursorCompartment(Cupboard cupboard, Cursor cursor) {
        super(cupboard);
        this.mCursor = cursor;
    }

    public <T> T get(Class<T> cls) {
        return iterate(cls).get(false);
    }

    public <T> QueryResultIterable<T> iterate(Class<T> cls) {
        return new QueryResultIterable(this.mCursor, getConverter(cls));
    }

    public <T> List<T> list(Class<T> cls) {
        return iterate(cls).list(false);
    }
}
