package com.nhaarman.listviewanimations;

import android.database.DataSetObserver;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.SectionIndexer;
import com.nhaarman.listviewanimations.util.AbsListViewWrapper;
import com.nhaarman.listviewanimations.util.Insertable;
import com.nhaarman.listviewanimations.util.ListViewWrapper;
import com.nhaarman.listviewanimations.util.ListViewWrapperSetter;
import com.nhaarman.listviewanimations.util.Swappable;

public abstract class BaseAdapterDecorator extends BaseAdapter implements SectionIndexer, Insertable, ListViewWrapperSetter, Swappable {
    @NonNull
    private final BaseAdapter mDecoratedBaseAdapter;
    @Nullable
    private ListViewWrapper mListViewWrapper;

    protected BaseAdapterDecorator(@NonNull BaseAdapter baseAdapter) {
        this.mDecoratedBaseAdapter = baseAdapter;
    }

    public void add(int i, @NonNull Object obj) {
        if (this.mDecoratedBaseAdapter instanceof Insertable) {
            ((Insertable) this.mDecoratedBaseAdapter).add(i, obj);
        } else {
            Log.w("ListViewAnimations", "Warning: add called on an adapter that does not implement Insertable!");
        }
    }

    public boolean areAllItemsEnabled() {
        return this.mDecoratedBaseAdapter.areAllItemsEnabled();
    }

    public int getCount() {
        return this.mDecoratedBaseAdapter.getCount();
    }

    @NonNull
    public BaseAdapter getDecoratedBaseAdapter() {
        return this.mDecoratedBaseAdapter;
    }

    @NonNull
    public View getDropDownView(int i, @Nullable View view, @NonNull ViewGroup viewGroup) {
        return this.mDecoratedBaseAdapter.getDropDownView(i, view, viewGroup);
    }

    public Object getItem(int i) {
        return this.mDecoratedBaseAdapter.getItem(i);
    }

    public long getItemId(int i) {
        return this.mDecoratedBaseAdapter.getItemId(i);
    }

    public int getItemViewType(int i) {
        return this.mDecoratedBaseAdapter.getItemViewType(i);
    }

    @Nullable
    public ListViewWrapper getListViewWrapper() {
        return this.mListViewWrapper;
    }

    public int getPositionForSection(int i) {
        return this.mDecoratedBaseAdapter instanceof SectionIndexer ? ((SectionIndexer) this.mDecoratedBaseAdapter).getPositionForSection(i) : 0;
    }

    @NonNull
    protected BaseAdapter getRootAdapter() {
        BaseAdapter baseAdapter = this.mDecoratedBaseAdapter;
        while (baseAdapter instanceof BaseAdapterDecorator) {
            baseAdapter = ((BaseAdapterDecorator) baseAdapter).getDecoratedBaseAdapter();
        }
        return baseAdapter;
    }

    public int getSectionForPosition(int i) {
        return this.mDecoratedBaseAdapter instanceof SectionIndexer ? ((SectionIndexer) this.mDecoratedBaseAdapter).getSectionForPosition(i) : 0;
    }

    @NonNull
    public Object[] getSections() {
        return this.mDecoratedBaseAdapter instanceof SectionIndexer ? ((SectionIndexer) this.mDecoratedBaseAdapter).getSections() : new Object[0];
    }

    @NonNull
    public View getView(int i, @Nullable View view, @NonNull ViewGroup viewGroup) {
        return this.mDecoratedBaseAdapter.getView(i, view, viewGroup);
    }

    public int getViewTypeCount() {
        return this.mDecoratedBaseAdapter.getViewTypeCount();
    }

    public boolean hasStableIds() {
        return this.mDecoratedBaseAdapter.hasStableIds();
    }

    public boolean isEmpty() {
        return this.mDecoratedBaseAdapter.isEmpty();
    }

    public boolean isEnabled(int i) {
        return this.mDecoratedBaseAdapter.isEnabled(i);
    }

    public void notifyDataSetChanged() {
        if (!(this.mDecoratedBaseAdapter instanceof ArrayAdapter)) {
            this.mDecoratedBaseAdapter.notifyDataSetChanged();
        }
    }

    public void notifyDataSetChanged(boolean z) {
        if (z || !(this.mDecoratedBaseAdapter instanceof ArrayAdapter)) {
            this.mDecoratedBaseAdapter.notifyDataSetChanged();
        }
    }

    public void notifyDataSetInvalidated() {
        this.mDecoratedBaseAdapter.notifyDataSetInvalidated();
    }

    public void registerDataSetObserver(@NonNull DataSetObserver dataSetObserver) {
        this.mDecoratedBaseAdapter.registerDataSetObserver(dataSetObserver);
    }

    public void setAbsListView(@NonNull AbsListView absListView) {
        setListViewWrapper(new AbsListViewWrapper(absListView));
    }

    public void setListViewWrapper(@NonNull ListViewWrapper listViewWrapper) {
        this.mListViewWrapper = listViewWrapper;
        if (this.mDecoratedBaseAdapter instanceof ListViewWrapperSetter) {
            ((ListViewWrapperSetter) this.mDecoratedBaseAdapter).setListViewWrapper(listViewWrapper);
        }
    }

    public void swapItems(int i, int i2) {
        if (this.mDecoratedBaseAdapter instanceof Swappable) {
            ((Swappable) this.mDecoratedBaseAdapter).swapItems(i, i2);
        } else {
            Log.w("ListViewAnimations", "Warning: swapItems called on an adapter that does not implement Swappable!");
        }
    }

    public void unregisterDataSetObserver(@NonNull DataSetObserver dataSetObserver) {
        this.mDecoratedBaseAdapter.unregisterDataSetObserver(dataSetObserver);
    }
}
