package defpackage;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

public final class le<E extends ld & ln & lk> extends PriorityBlockingQueue<E> {
    final Queue<E> a = new LinkedList();
    private final ReentrantLock b = new ReentrantLock();

    private E a(int i, Long l, TimeUnit timeUnit) throws InterruptedException {
        while (true) {
            ld ldVar;
            switch (i) {
                case 0:
                    ldVar = (ld) super.take();
                    break;
                case 1:
                    ldVar = (ld) super.peek();
                    break;
                case 2:
                    ldVar = (ld) super.poll();
                    break;
                case 3:
                    ldVar = (ld) super.poll(l.longValue(), timeUnit);
                    break;
                default:
                    ldVar = null;
                    break;
            }
            if (ldVar == null || ldVar.areDependenciesMet()) {
                return ldVar;
            }
            a(i, ldVar);
        }
    }

    private boolean a(int i, E e) {
        try {
            this.b.lock();
            if (i == 1) {
                super.remove(e);
            }
            boolean offer = this.a.offer(e);
            return offer;
        } finally {
            this.b.unlock();
        }
    }

    private static <T> T[] a(T[] tArr, T[] tArr2) {
        int length = tArr.length;
        int length2 = tArr2.length;
        Object[] objArr = (Object[]) Array.newInstance(tArr.getClass().getComponentType(), length + length2);
        System.arraycopy(tArr, 0, objArr, 0, length);
        System.arraycopy(tArr2, 0, objArr, length, length2);
        return objArr;
    }

    private E b() {
        E e = null;
        try {
            e = a(1, null, null);
        } catch (InterruptedException e2) {
        }
        return e;
    }

    private E c() {
        E e = null;
        try {
            e = a(2, null, null);
        } catch (InterruptedException e2) {
        }
        return e;
    }

    public final void a() {
        try {
            this.b.lock();
            Iterator it = this.a.iterator();
            while (it.hasNext()) {
                ld ldVar = (ld) it.next();
                if (ldVar.areDependenciesMet()) {
                    super.offer(ldVar);
                    it.remove();
                }
            }
        } finally {
            this.b.unlock();
        }
    }

    public final void clear() {
        try {
            this.b.lock();
            this.a.clear();
            super.clear();
        } finally {
            this.b.unlock();
        }
    }

    public final boolean contains(Object obj) {
        try {
            this.b.lock();
            boolean z = super.contains(obj) || this.a.contains(obj);
            this.b.unlock();
            return z;
        } catch (Throwable th) {
            this.b.unlock();
        }
    }

    public final int drainTo(Collection<? super E> collection) {
        int size;
        try {
            this.b.lock();
            int drainTo = super.drainTo(collection);
            size = this.a.size();
            while (!this.a.isEmpty()) {
                collection.add(this.a.poll());
            }
            return drainTo + size;
        } finally {
            size = this.b;
            size.unlock();
        }
    }

    public final int drainTo(Collection<? super E> collection, int i) {
        try {
            this.b.lock();
            int drainTo = super.drainTo(collection, i);
            while (!this.a.isEmpty() && drainTo <= i) {
                collection.add(this.a.poll());
                drainTo++;
            }
            this.b.unlock();
            return drainTo;
        } catch (Throwable th) {
            this.b.unlock();
        }
    }

    public final /* synthetic */ Object peek() {
        return b();
    }

    public final /* synthetic */ Object poll() {
        return c();
    }

    public final /* synthetic */ Object poll(long j, TimeUnit timeUnit) throws InterruptedException {
        return a(3, Long.valueOf(j), timeUnit);
    }

    public final boolean remove(Object obj) {
        try {
            this.b.lock();
            boolean z = super.remove(obj) || this.a.remove(obj);
            this.b.unlock();
            return z;
        } catch (Throwable th) {
            this.b.unlock();
        }
    }

    public final boolean removeAll(Collection<?> collection) {
        int removeAll;
        try {
            this.b.lock();
            int removeAll2 = super.removeAll(collection);
            removeAll = this.a.removeAll(collection);
            return removeAll2 | removeAll;
        } finally {
            removeAll = this.b;
            removeAll.unlock();
        }
    }

    public final int size() {
        int size;
        try {
            this.b.lock();
            int size2 = this.a.size();
            size = super.size();
            return size2 + size;
        } finally {
            size = this.b;
            size.unlock();
        }
    }

    public final /* synthetic */ Object take() throws InterruptedException {
        return a(0, null, null);
    }

    public final Object[] toArray() {
        try {
            this.b.lock();
            Object[] a = le.a(super.toArray(), this.a.toArray());
            return a;
        } finally {
            this.b.unlock();
        }
    }

    public final <T> T[] toArray(T[] tArr) {
        try {
            this.b.lock();
            T[] a = le.a(super.toArray(tArr), this.a.toArray(tArr));
            return a;
        } finally {
            this.b.unlock();
        }
    }
}
