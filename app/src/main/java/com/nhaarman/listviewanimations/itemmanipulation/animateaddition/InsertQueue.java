package com.nhaarman.listviewanimations.itemmanipulation.animateaddition;

import android.support.annotation.NonNull;
import android.util.Pair;
import com.nhaarman.listviewanimations.util.Insertable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class InsertQueue<T> {
    @NonNull
    private final Collection<AtomicInteger> mActiveIndexes = new HashSet();
    @NonNull
    private final Insertable<T> mInsertable;
    @NonNull
    private final List<Pair<Integer, T>> mPendingItemsToInsert = new ArrayList();

    public InsertQueue(@NonNull Insertable<T> insertable) {
        this.mInsertable = insertable;
    }

    private void insertPending() {
        for (Pair pair : this.mPendingItemsToInsert) {
            for (AtomicInteger atomicInteger : this.mActiveIndexes) {
                if (atomicInteger.intValue() >= ((Integer) pair.first).intValue()) {
                    atomicInteger.incrementAndGet();
                }
            }
            this.mActiveIndexes.add(new AtomicInteger(((Integer) pair.first).intValue()));
            this.mInsertable.add(((Integer) pair.first).intValue(), pair.second);
        }
        this.mPendingItemsToInsert.clear();
    }

    public void clearActive() {
        this.mActiveIndexes.clear();
        insertPending();
    }

    @NonNull
    public Collection<Integer> getActiveIndexes() {
        Collection<Integer> hashSet = new HashSet();
        for (AtomicInteger atomicInteger : this.mActiveIndexes) {
            hashSet.add(Integer.valueOf(atomicInteger.get()));
        }
        return hashSet;
    }

    @NonNull
    public List<Pair<Integer, T>> getPendingItemsToInsert() {
        return this.mPendingItemsToInsert;
    }

    public void insert(int i, @NonNull T t) {
        if (this.mActiveIndexes.isEmpty() && this.mPendingItemsToInsert.isEmpty()) {
            this.mActiveIndexes.add(new AtomicInteger(i));
            this.mInsertable.add(i, t);
            return;
        }
        this.mPendingItemsToInsert.add(new Pair(Integer.valueOf(i), t));
    }

    public void insert(@NonNull Collection<Pair<Integer, T>> collection) {
        if (this.mActiveIndexes.isEmpty() && this.mPendingItemsToInsert.isEmpty()) {
            for (Pair pair : collection) {
                for (AtomicInteger atomicInteger : this.mActiveIndexes) {
                    if (atomicInteger.intValue() >= ((Integer) pair.first).intValue()) {
                        atomicInteger.incrementAndGet();
                    }
                }
                this.mActiveIndexes.add(new AtomicInteger(((Integer) pair.first).intValue()));
                this.mInsertable.add(((Integer) pair.first).intValue(), pair.second);
            }
            return;
        }
        this.mPendingItemsToInsert.addAll(collection);
    }

    public void insert(@NonNull Pair<Integer, T>... pairArr) {
        insert(Arrays.asList(pairArr));
    }

    public void removeActiveIndex(int i) {
        Iterator it = this.mActiveIndexes.iterator();
        Object obj = null;
        while (it.hasNext() && r1 == null) {
            if (((AtomicInteger) it.next()).get() == i) {
                it.remove();
                obj = 1;
            }
        }
        if (this.mActiveIndexes.isEmpty()) {
            insertPending();
        }
    }
}
