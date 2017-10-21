package nl.qbusict.cupboard;

import android.content.ContentProviderOperation;
import android.content.ContentUris;
import android.content.ContentValues;
import android.net.Uri;
import java.util.ArrayList;
import nl.qbusict.cupboard.convert.EntityConverter;

public class ProviderOperationsCompartment extends BaseCompartment {
    private final ArrayList<ContentProviderOperation> mOperations;
    private int mYieldAfter = -1;
    private boolean mYieldAllowed = false;

    protected ProviderOperationsCompartment(Cupboard cupboard, ArrayList<ContentProviderOperation> arrayList) {
        super(cupboard);
        this.mOperations = arrayList;
    }

    private boolean shouldYield() {
        return this.mYieldAllowed || (this.mYieldAfter > 0 && this.mOperations.size() + 1 >= this.mYieldAfter && (this.mOperations.size() + 1) % this.mYieldAfter == 0);
    }

    public <T> ProviderOperationsCompartment delete(Uri uri, T t) {
        Long id = getConverter(t.getClass()).getId(t);
        if (id != null) {
            this.mOperations.add(ContentProviderOperation.newDelete(ContentUris.withAppendedId(uri, id.longValue())).withYieldAllowed(this.mYieldAllowed).build());
        }
        return this;
    }

    public ArrayList<ContentProviderOperation> getOperations() {
        return this.mOperations;
    }

    public <T> ProviderOperationsCompartment put(Uri uri, Class<T> cls, T... tArr) {
        int i = 0;
        boolean z = this.mYieldAllowed;
        this.mYieldAllowed = false;
        EntityConverter converter = getConverter(cls);
        ContentValues[] contentValuesArr = new ContentValues[tArr.length];
        int size = converter.getColumns().size();
        for (int i2 = 0; i2 < tArr.length; i2++) {
            contentValuesArr[i2] = new ContentValues(size);
            converter.toValues(tArr[i2], contentValuesArr[i2]);
        }
        while (i < tArr.length) {
            if (i == tArr.length - 1) {
                this.mYieldAllowed = z;
            }
            put(uri, tArr[i]);
            i++;
        }
        return this;
    }

    public <T> ProviderOperationsCompartment put(Uri uri, T t) {
        EntityConverter converter = getConverter(t.getClass());
        ContentValues contentValues = new ContentValues(converter.getColumns().size());
        converter.toValues(t, contentValues);
        Long id = converter.getId(t);
        if (id == null) {
            this.mOperations.add(ContentProviderOperation.newInsert(uri).withValues(contentValues).withYieldAllowed(shouldYield()).build());
        } else {
            this.mOperations.add(ContentProviderOperation.newInsert(ContentUris.withAppendedId(uri, id.longValue())).withYieldAllowed(shouldYield()).withValues(contentValues).build());
        }
        this.mYieldAllowed = false;
        return this;
    }

    public ProviderOperationsCompartment yield() {
        this.mYieldAllowed = true;
        return this;
    }

    public ProviderOperationsCompartment yieldAfter(int i) {
        this.mYieldAfter = i;
        return this;
    }
}
