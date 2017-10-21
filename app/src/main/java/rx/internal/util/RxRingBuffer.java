package rx.internal.util;

import java.util.Queue;
import rx.Observer;
import rx.Subscription;
import rx.exceptions.MissingBackpressureException;
import rx.internal.operators.NotificationLite;
import rx.internal.util.unsafe.SpmcArrayQueue;
import rx.internal.util.unsafe.SpscArrayQueue;
import rx.internal.util.unsafe.UnsafeAccess;

public class RxRingBuffer implements Subscription {
    public static final int SIZE = _size;
    public static ObjectPool<Queue<Object>> SPMC_POOL = new ObjectPool<Queue<Object>>() {
        protected final SpmcArrayQueue<Object> createObject() {
            return new SpmcArrayQueue(RxRingBuffer.SIZE);
        }
    };
    public static ObjectPool<Queue<Object>> SPSC_POOL = new ObjectPool<Queue<Object>>() {
        protected final SpscArrayQueue<Object> createObject() {
            return new SpscArrayQueue(RxRingBuffer.SIZE);
        }
    };
    static int _size;
    private static final NotificationLite<Object> on = NotificationLite.instance();
    private final ObjectPool<Queue<Object>> pool;
    private Queue<Object> queue;
    private final int size;
    public volatile Object terminalState;

    static {
        _size = 128;
        if (PlatformDependent.isAndroid()) {
            _size = 16;
        }
        String property = System.getProperty("rx.ring-buffer.size");
        if (property != null) {
            try {
                _size = Integer.parseInt(property);
            } catch (Exception e) {
                System.err.println("Failed to set 'rx.buffer.size' with value " + property + " => " + e.getMessage());
            }
        }
    }

    RxRingBuffer() {
        this(new SynchronizedQueue(SIZE), SIZE);
    }

    private RxRingBuffer(Queue<Object> queue, int i) {
        this.queue = queue;
        this.pool = null;
        this.size = i;
    }

    private RxRingBuffer(ObjectPool<Queue<Object>> objectPool, int i) {
        this.pool = objectPool;
        this.queue = (Queue) objectPool.borrowObject();
        this.size = i;
    }

    public static RxRingBuffer getSpmcInstance() {
        return UnsafeAccess.isUnsafeAvailable() ? new RxRingBuffer(SPMC_POOL, SIZE) : new RxRingBuffer();
    }

    public static RxRingBuffer getSpscInstance() {
        return UnsafeAccess.isUnsafeAvailable() ? new RxRingBuffer(SPSC_POOL, SIZE) : new RxRingBuffer();
    }

    public boolean accept(Object obj, Observer observer) {
        return on.accept(observer, obj);
    }

    public Throwable asError(Object obj) {
        return on.getError(obj);
    }

    public int available() {
        return this.size - count();
    }

    public int capacity() {
        return this.size;
    }

    public int count() {
        Queue queue = this.queue;
        return queue == null ? 0 : queue.size();
    }

    public Object getValue(Object obj) {
        return on.getValue(obj);
    }

    public boolean isCompleted(Object obj) {
        return on.isCompleted(obj);
    }

    public boolean isEmpty() {
        Queue queue = this.queue;
        return queue == null ? true : queue.isEmpty();
    }

    public boolean isError(Object obj) {
        return on.isError(obj);
    }

    public boolean isUnsubscribed() {
        return this.queue == null;
    }

    public void onCompleted() {
        if (this.terminalState == null) {
            this.terminalState = on.completed();
        }
    }

    public void onError(Throwable th) {
        if (this.terminalState == null) {
            this.terminalState = on.error(th);
        }
    }

    public void onNext(Object obj) throws MissingBackpressureException {
        Object obj2 = 1;
        Object obj3 = null;
        synchronized (this) {
            Queue queue = this.queue;
            if (queue == null) {
                obj2 = null;
                int i = 1;
            } else if (queue.offer(on.next(obj))) {
                obj2 = null;
            }
        }
        if (obj3 != null) {
            throw new IllegalStateException("This instance has been unsubscribed and the queue is no longer usable.");
        } else if (obj2 != null) {
            throw new MissingBackpressureException();
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.lang.Object peek() {
        /*
        r3 = this;
        monitor-enter(r3);
        r2 = r3.queue;	 Catch:{ all -> 0x001a }
        if (r2 != 0) goto L_0x0008;
    L_0x0005:
        monitor-exit(r3);	 Catch:{ all -> 0x001a }
        r0 = 0;
    L_0x0007:
        return r0;
    L_0x0008:
        r1 = r2.peek();	 Catch:{ all -> 0x001a }
        r0 = r3.terminalState;	 Catch:{ all -> 0x001a }
        if (r1 != 0) goto L_0x001d;
    L_0x0010:
        if (r0 == 0) goto L_0x001d;
    L_0x0012:
        r2 = r2.peek();	 Catch:{ all -> 0x001a }
        if (r2 != 0) goto L_0x001d;
    L_0x0018:
        monitor-exit(r3);	 Catch:{ all -> 0x001a }
        goto L_0x0007;
    L_0x001a:
        r0 = move-exception;
        monitor-exit(r3);	 Catch:{ all -> 0x001a }
        throw r0;
    L_0x001d:
        r0 = r1;
        goto L_0x0018;
        */
        throw new UnsupportedOperationException("Method not decompiled: rx.internal.util.RxRingBuffer.peek():java.lang.Object");
    }

    public Object poll() {
        Object obj = null;
        synchronized (this) {
            Queue queue = this.queue;
            if (queue == null) {
            } else {
                Object poll = queue.poll();
                obj = this.terminalState;
                if (poll == null && obj != null && queue.peek() == null) {
                    this.terminalState = null;
                } else {
                    obj = poll;
                }
            }
        }
        return obj;
    }

    public void release() {
        synchronized (this) {
            Queue queue = this.queue;
            ObjectPool objectPool = this.pool;
            if (!(objectPool == null || queue == null)) {
                queue.clear();
                this.queue = null;
                objectPool.returnObject(queue);
            }
        }
    }

    public void unsubscribe() {
        release();
    }
}
