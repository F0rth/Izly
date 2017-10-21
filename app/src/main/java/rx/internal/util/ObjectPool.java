package rx.internal.util;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import org.spongycastle.asn1.cmp.PKIFailureInfo;
import rx.Scheduler.Worker;
import rx.functions.Action0;
import rx.internal.schedulers.SchedulerLifecycle;
import rx.internal.util.unsafe.MpmcArrayQueue;
import rx.internal.util.unsafe.UnsafeAccess;
import rx.schedulers.Schedulers;

public abstract class ObjectPool<T> implements SchedulerLifecycle {
    final int maxSize;
    final int minSize;
    Queue<T> pool;
    private final AtomicReference<Worker> schedulerWorker;
    private final long validationInterval;

    public ObjectPool() {
        this(0, 0, 67);
    }

    private ObjectPool(int i, int i2, long j) {
        this.minSize = i;
        this.maxSize = i2;
        this.validationInterval = j;
        this.schedulerWorker = new AtomicReference();
        initialize(i);
        start();
    }

    private void initialize(int i) {
        if (UnsafeAccess.isUnsafeAvailable()) {
            this.pool = new MpmcArrayQueue(Math.max(this.maxSize, PKIFailureInfo.badRecipientNonce));
        } else {
            this.pool = new ConcurrentLinkedQueue();
        }
        for (int i2 = 0; i2 < i; i2++) {
            this.pool.add(createObject());
        }
    }

    public T borrowObject() {
        T poll = this.pool.poll();
        return poll == null ? createObject() : poll;
    }

    protected abstract T createObject();

    public void returnObject(T t) {
        if (t != null) {
            this.pool.offer(t);
        }
    }

    public void shutdown() {
        Worker worker = (Worker) this.schedulerWorker.getAndSet(null);
        if (worker != null) {
            worker.unsubscribe();
        }
    }

    public void start() {
        Worker createWorker = Schedulers.computation().createWorker();
        if (this.schedulerWorker.compareAndSet(null, createWorker)) {
            createWorker.schedulePeriodically(new Action0() {
                public void call() {
                    int i = 0;
                    int size = ObjectPool.this.pool.size();
                    int i2;
                    if (size < ObjectPool.this.minSize) {
                        i2 = ObjectPool.this.maxSize;
                        while (i < i2 - size) {
                            ObjectPool.this.pool.add(ObjectPool.this.createObject());
                            i++;
                        }
                    } else if (size > ObjectPool.this.maxSize) {
                        i2 = ObjectPool.this.maxSize;
                        while (i < size - i2) {
                            ObjectPool.this.pool.poll();
                            i++;
                        }
                    }
                }
            }, this.validationInterval, this.validationInterval, TimeUnit.SECONDS);
        } else {
            createWorker.unsubscribe();
        }
    }
}
