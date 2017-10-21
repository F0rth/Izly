package com.nhaarman.listviewanimations;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.BaseAdapter;
import com.nhaarman.listviewanimations.util.Insertable;
import com.nhaarman.listviewanimations.util.Swappable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public abstract class ArrayAdapter<T> extends BaseAdapter implements Insertable<T>, Swappable {
    private BaseAdapter mDataSetChangedSlavedAdapter;
    @NonNull
    private final List<T> mItems;

    protected ArrayAdapter() {
        this(null);
    }

    protected ArrayAdapter(@Nullable List<T> list) {
        if (list != null) {
            this.mItems = list;
        } else {
            this.mItems = new ArrayList();
        }
    }

    public void add(int i, @NonNull T t) {
        this.mItems.add(i, t);
        notifyDataSetChanged();
    }

    public boolean add(@NonNull T t) {
        boolean add = this.mItems.add(t);
        notifyDataSetChanged();
        return add;
    }

    public boolean addAll(@NonNull Collection<? extends T> collection) {
        boolean addAll = this.mItems.addAll(collection);
        notifyDataSetChanged();
        return addAll;
    }

    public void clear() {
        this.mItems.clear();
        notifyDataSetChanged();
    }

    public boolean contains(T t) {
        return this.mItems.contains(t);
    }

    public int getCount() {
        return this.mItems.size();
    }

    @NonNull
    public T getItem(int i) {
        return this.mItems.get(i);
    }

    public long getItemId(int i) {
        return (long) i;
    }

    @NonNull
    public List<T> getItems() {
        return this.mItems;
    }

    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
        if (this.mDataSetChangedSlavedAdapter != null) {
            this.mDataSetChangedSlavedAdapter.notifyDataSetChanged();
        }
    }

    public void propagateNotifyDataSetChanged(@NonNull BaseAdapter baseAdapter) {
        this.mDataSetChangedSlavedAdapter = baseAdapter;
    }

    @NonNull
    public T remove(int i) {
        T remove = this.mItems.remove(i);
        notifyDataSetChanged();
        return remove;
    }

    public boolean remove(@NonNull Object obj) {
        boolean remove = this.mItems.remove(obj);
        notifyDataSetChanged();
        return remove;
    }

    public void swapItems(int i, int i2) {
        Object obj = this.mItems.set(i, getItem(i2));
        notifyDataSetChanged();
        this.mItems.set(i2, obj);
    }
}
