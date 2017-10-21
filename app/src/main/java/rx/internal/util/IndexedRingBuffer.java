package rx.internal.util;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicIntegerArray;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.atomic.AtomicReferenceArray;
import rx.Subscription;
import rx.functions.Func1;

public final class IndexedRingBuffer<E> implements Subscription {
    private static final ObjectPool<IndexedRingBuffer<?>> POOL = new ObjectPool<IndexedRingBuffer<?>>() {
        protected final IndexedRingBuffer<?> createObject() {
            return new IndexedRingBuffer();
        }
    };
    static final int SIZE = _size;
    static int _size;
    private final ElementSection<E> elements = new ElementSection();
    final AtomicInteger index = new AtomicInteger();
    private final IndexSection removed = new IndexSection();
    final AtomicInteger removedIndex = new AtomicInteger();

    static class ElementSection<E> {
        final AtomicReferenceArray<E> array = new AtomicReferenceArray(IndexedRingBuffer.SIZE);
        final AtomicReference<ElementSection<E>> next = new AtomicReference();

        ElementSection() {
        }

        ElementSection<E> getNext() {
            if (this.next.get() != null) {
                return (ElementSection) this.next.get();
            }
            ElementSection<E> elementSection = new ElementSection();
            return !this.next.compareAndSet(null, elementSection) ? (ElementSection) this.next.get() : elementSection;
        }
    }

    static class IndexSection {
        private final AtomicReference<IndexSection> _next = new AtomicReference();
        private final AtomicIntegerArray unsafeArray = new AtomicIntegerArray(IndexedRingBuffer.SIZE);

        IndexSection() {
        }

        public int getAndSet(int i, int i2) {
            return this.unsafeArray.getAndSet(i, i2);
        }

        IndexSection getNext() {
            if (this._next.get() != null) {
                return (IndexSection) this._next.get();
            }
            IndexSection indexSection = new IndexSection();
            return !this._next.compareAndSet(null, indexSection) ? (IndexSection) this._next.get() : indexSection;
        }

        public void set(int i, int i2) {
            this.unsafeArray.set(i, i2);
        }
    }

    static {
        _size = 256;
        if (PlatformDependent.isAndroid()) {
            _size = 8;
        }
        String property = System.getProperty("rx.indexed-ring-buffer.size");
        if (property != null) {
            try {
                _size = Integer.parseInt(property);
            } catch (Exception e) {
                System.err.println("Failed to set 'rx.indexed-ring-buffer.size' with value " + property + " => " + e.getMessage());
            }
        }
    }

    IndexedRingBuffer() {
    }

    private int forEach(Func1<? super E, Boolean> func1, int i, int i2) {
        ElementSection elementSection;
        int i3;
        int i4;
        int i5 = this.index.get();
        ElementSection elementSection2 = this.elements;
        if (i >= SIZE) {
            elementSection = getElementSection(i);
            i3 = i % SIZE;
            i4 = i;
        } else {
            elementSection = elementSection2;
            i4 = i;
            i3 = i;
        }
        while (elementSection != null) {
            int i6 = i4;
            for (i4 = i3; i4 < SIZE; i4++) {
                if (i6 >= i5 || i6 >= i2) {
                    return i6;
                }
                Object obj = elementSection.array.get(i4);
                if (obj != null && !((Boolean) func1.call(obj)).booleanValue()) {
                    return i6;
                }
                i6++;
            }
            elementSection = (ElementSection) elementSection.next.get();
            i4 = i6;
            i3 = 0;
        }
        return i4;
    }

    private ElementSection<E> getElementSection(int i) {
        if (i < SIZE) {
            return this.elements;
        }
        int i2 = i / SIZE;
        ElementSection<E> elementSection = this.elements;
        for (int i3 = 0; i3 < i2; i3++) {
            elementSection = elementSection.getNext();
        }
        return elementSection;
    }

    private int getIndexForAdd() {
        int indexFromPreviouslyRemoved;
        synchronized (this) {
            indexFromPreviouslyRemoved = getIndexFromPreviouslyRemoved();
            if (indexFromPreviouslyRemoved >= 0) {
                if (indexFromPreviouslyRemoved < SIZE) {
                    indexFromPreviouslyRemoved = this.removed.getAndSet(indexFromPreviouslyRemoved, -1);
                } else {
                    indexFromPreviouslyRemoved = getIndexSection(indexFromPreviouslyRemoved).getAndSet(indexFromPreviouslyRemoved % SIZE, -1);
                }
                if (indexFromPreviouslyRemoved == this.index.get()) {
                    this.index.getAndIncrement();
                }
            } else {
                indexFromPreviouslyRemoved = this.index.getAndIncrement();
            }
        }
        return indexFromPreviouslyRemoved;
    }

    private int getIndexFromPreviouslyRemoved() {
        int i;
        synchronized (this) {
            do {
                i = this.removedIndex.get();
                if (i <= 0) {
                    i = -1;
                    break;
                }
            } while (!this.removedIndex.compareAndSet(i, i - 1));
            i--;
        }
        return i;
    }

    private IndexSection getIndexSection(int i) {
        if (i < SIZE) {
            return this.removed;
        }
        int i2 = i / SIZE;
        IndexSection indexSection = this.removed;
        for (int i3 = 0; i3 < i2; i3++) {
            indexSection = indexSection.getNext();
        }
        return indexSection;
    }

    public static <T> IndexedRingBuffer<T> getInstance() {
        return (IndexedRingBuffer) POOL.borrowObject();
    }

    private void pushRemovedIndex(int i) {
        synchronized (this) {
            int andIncrement = this.removedIndex.getAndIncrement();
            if (andIncrement < SIZE) {
                this.removed.set(andIncrement, i);
            } else {
                getIndexSection(andIncrement).set(andIncrement % SIZE, i);
            }
        }
    }

    public final int add(E e) {
        int indexForAdd = getIndexForAdd();
        if (indexForAdd < SIZE) {
            this.elements.array.set(indexForAdd, e);
        } else {
            getElementSection(indexForAdd).array.set(indexForAdd % SIZE, e);
        }
        return indexForAdd;
    }

    public final int forEach(Func1<? super E, Boolean> func1) {
        return forEach(func1, 0);
    }

    public final int forEach(Func1<? super E, Boolean> func1, int i) {
        int forEach = forEach(func1, i, this.index.get());
        return (i <= 0 || forEach != this.index.get()) ? forEach != this.index.get() ? forEach : 0 : forEach(func1, 0, i);
    }

    public final boolean isUnsubscribed() {
        return false;
    }

    public final void releaseToPool() {
        int i = this.index.get();
        ElementSection elementSection = this.elements;
        int i2 = 0;
        loop0:
        while (elementSection != null) {
            int i3 = i2;
            i2 = 0;
            while (i2 < SIZE) {
                if (i3 >= i) {
                    break loop0;
                }
                elementSection.array.set(i2, null);
                i2++;
                i3++;
            }
            elementSection = (ElementSection) elementSection.next.get();
            i2 = i3;
        }
        this.index.set(0);
        this.removedIndex.set(0);
        POOL.returnObject(this);
    }

    public final E remove(int i) {
        E andSet;
        if (i < SIZE) {
            andSet = this.elements.array.getAndSet(i, null);
        } else {
            andSet = getElementSection(i).array.getAndSet(i % SIZE, null);
        }
        pushRemovedIndex(i);
        return andSet;
    }

    public final void unsubscribe() {
        releaseToPool();
    }
}
