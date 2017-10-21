package nl.qbusict.cupboard;

import android.database.Cursor;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import nl.qbusict.cupboard.convert.EntityConverter;

public class QueryResultIterable<T> implements Iterable<T> {
    private final Cursor mCursor;
    private final int mPosition;
    private final EntityConverter<T> mTranslator;

    static class QueryResultIterator<E> implements Iterator<E> {
        private final Cursor mCursor;
        private boolean mHasNext;
        private final EntityConverter<E> mTranslator;

        public QueryResultIterator(Cursor cursor, EntityConverter<E> entityConverter) {
            this.mCursor = new PreferredColumnOrderCursorWrapper(cursor, entityConverter.getColumns());
            this.mTranslator = entityConverter;
            boolean moveToNext = cursor.getPosition() == -1 ? cursor.moveToNext() : cursor.getPosition() < cursor.getCount();
            this.mHasNext = moveToNext;
        }

        public boolean hasNext() {
            return this.mHasNext;
        }

        public E next() {
            if (this.mHasNext) {
                E fromCursor = this.mTranslator.fromCursor(this.mCursor);
                this.mHasNext = this.mCursor.moveToNext();
                return fromCursor;
            }
            throw new NoSuchElementException();
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    QueryResultIterable(Cursor cursor, EntityConverter<T> entityConverter) {
        if (cursor.getPosition() >= 0) {
            this.mPosition = cursor.getPosition();
        } else {
            this.mPosition = -1;
        }
        this.mCursor = cursor;
        this.mTranslator = entityConverter;
    }

    public void close() {
        if (!this.mCursor.isClosed()) {
            this.mCursor.close();
        }
    }

    public T get() {
        return get(true);
    }

    public T get(boolean z) {
        try {
            Iterator it = iterator();
            if (it.hasNext()) {
                T next = it.next();
                return next;
            }
            if (z) {
                close();
            }
            return null;
        } finally {
            if (z) {
                close();
            }
        }
    }

    public Cursor getCursor() {
        return this.mCursor;
    }

    public Iterator<T> iterator() {
        this.mCursor.moveToPosition(this.mPosition);
        return new QueryResultIterator(this.mCursor, this.mTranslator);
    }

    public List<T> list() {
        return list(true);
    }

    public List<T> list(boolean z) {
        List<T> arrayList = new ArrayList(this.mCursor.getCount());
        try {
            Iterator it = iterator();
            while (it.hasNext()) {
                arrayList.add(it.next());
            }
            return arrayList;
        } finally {
            if (z) {
                close();
            }
        }
    }
}
