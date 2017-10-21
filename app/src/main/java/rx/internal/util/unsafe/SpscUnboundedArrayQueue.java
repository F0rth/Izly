package rx.internal.util.unsafe;

import java.util.Iterator;
import org.spongycastle.asn1.cmp.PKIFailureInfo;

public class SpscUnboundedArrayQueue<E> extends SpscUnboundedArrayQueueConsumerField<E> implements QueueProgressIndicators {
    private static final long C_INDEX_OFFSET;
    private static final Object HAS_NEXT = new Object();
    static final int MAX_LOOK_AHEAD_STEP = Integer.getInteger("jctools.spsc.max.lookahead.step", PKIFailureInfo.certConfirmed).intValue();
    private static final long P_INDEX_OFFSET;
    private static final long REF_ARRAY_BASE = ((long) UnsafeAccess.UNSAFE.arrayBaseOffset(Object[].class));
    private static final int REF_ELEMENT_SHIFT;

    static {
        int arrayIndexScale = UnsafeAccess.UNSAFE.arrayIndexScale(Object[].class);
        if (4 == arrayIndexScale) {
            REF_ELEMENT_SHIFT = 2;
        } else if (8 == arrayIndexScale) {
            REF_ELEMENT_SHIFT = 3;
        } else {
            throw new IllegalStateException("Unknown pointer size");
        }
        try {
            P_INDEX_OFFSET = UnsafeAccess.UNSAFE.objectFieldOffset(SpscUnboundedArrayQueueProducerFields.class.getDeclaredField("producerIndex"));
            try {
                C_INDEX_OFFSET = UnsafeAccess.UNSAFE.objectFieldOffset(SpscUnboundedArrayQueueConsumerField.class.getDeclaredField("consumerIndex"));
            } catch (Throwable e) {
                throw new RuntimeException(e);
            }
        } catch (Throwable e2) {
            throw new RuntimeException(e2);
        }
    }

    public SpscUnboundedArrayQueue(int i) {
        int roundToPowerOfTwo = Pow2.roundToPowerOfTwo(i);
        long j = (long) (roundToPowerOfTwo - 1);
        Object[] objArr = new Object[(roundToPowerOfTwo + 1)];
        this.producerBuffer = objArr;
        this.producerMask = j;
        adjustLookAheadStep(roundToPowerOfTwo);
        this.consumerBuffer = objArr;
        this.consumerMask = j;
        this.producerLookAhead = j - 1;
        soProducerIndex(0);
    }

    private void adjustLookAheadStep(int i) {
        this.producerLookAheadStep = Math.min(i / 4, MAX_LOOK_AHEAD_STEP);
    }

    private static long calcDirectOffset(long j) {
        return REF_ARRAY_BASE + (j << REF_ELEMENT_SHIFT);
    }

    private static long calcWrappedOffset(long j, long j2) {
        return calcDirectOffset(j & j2);
    }

    private long lvConsumerIndex() {
        return UnsafeAccess.UNSAFE.getLongVolatile(this, C_INDEX_OFFSET);
    }

    private static <E> Object lvElement(E[] eArr, long j) {
        return UnsafeAccess.UNSAFE.getObjectVolatile(eArr, j);
    }

    private E[] lvNext(E[] eArr) {
        return (Object[]) lvElement(eArr, calcDirectOffset((long) (eArr.length - 1)));
    }

    private long lvProducerIndex() {
        return UnsafeAccess.UNSAFE.getLongVolatile(this, P_INDEX_OFFSET);
    }

    private E newBufferPeek(E[] eArr, long j, long j2) {
        this.consumerBuffer = eArr;
        return lvElement(eArr, calcWrappedOffset(j, j2));
    }

    private E newBufferPoll(E[] eArr, long j, long j2) {
        this.consumerBuffer = eArr;
        long calcWrappedOffset = calcWrappedOffset(j, j2);
        E lvElement = lvElement(eArr, calcWrappedOffset);
        if (lvElement == null) {
            return null;
        }
        soConsumerIndex(1 + j);
        soElement(eArr, calcWrappedOffset, null);
        return lvElement;
    }

    private void resize(E[] eArr, long j, long j2, E e, long j3) {
        Object[] objArr = new Object[eArr.length];
        this.producerBuffer = objArr;
        this.producerLookAhead = (j + j3) - 1;
        soProducerIndex(j + 1);
        soElement(objArr, j2, e);
        soNext(eArr, objArr);
        soElement(eArr, j2, HAS_NEXT);
    }

    private void soConsumerIndex(long j) {
        UnsafeAccess.UNSAFE.putOrderedLong(this, C_INDEX_OFFSET, j);
    }

    private static void soElement(Object[] objArr, long j, Object obj) {
        UnsafeAccess.UNSAFE.putOrderedObject(objArr, j, obj);
    }

    private void soNext(E[] eArr, E[] eArr2) {
        soElement(eArr, calcDirectOffset((long) (eArr.length - 1)), eArr2);
    }

    private void soProducerIndex(long j) {
        UnsafeAccess.UNSAFE.putOrderedLong(this, P_INDEX_OFFSET, j);
    }

    private boolean writeToQueue(E[] eArr, E e, long j, long j2) {
        soProducerIndex(1 + j);
        soElement(eArr, j2, e);
        return true;
    }

    public long currentConsumerIndex() {
        return lvConsumerIndex();
    }

    public long currentProducerIndex() {
        return lvProducerIndex();
    }

    public final Iterator<E> iterator() {
        throw new UnsupportedOperationException();
    }

    public final boolean offer(E e) {
        if (e == null) {
            throw new NullPointerException("Null is not a valid element");
        }
        Object[] objArr = this.producerBuffer;
        long j = this.producerIndex;
        long j2 = this.producerMask;
        long calcWrappedOffset = calcWrappedOffset(j, j2);
        if (j < this.producerLookAhead) {
            return writeToQueue(objArr, e, j, calcWrappedOffset);
        }
        int i = this.producerLookAheadStep;
        if (lvElement(objArr, calcWrappedOffset(((long) i) + j, j2)) == null) {
            this.producerLookAhead = (((long) i) + j) - 1;
            return writeToQueue(objArr, e, j, calcWrappedOffset);
        } else if (lvElement(objArr, calcWrappedOffset(1 + j, j2)) != null) {
            return writeToQueue(objArr, e, j, calcWrappedOffset);
        } else {
            resize(objArr, j, calcWrappedOffset, e, j2);
            return true;
        }
    }

    public final E peek() {
        Object[] objArr = this.consumerBuffer;
        long j = this.consumerIndex;
        long j2 = this.consumerMask;
        E lvElement = lvElement(objArr, calcWrappedOffset(j, j2));
        return lvElement == HAS_NEXT ? newBufferPeek(lvNext(objArr), j, j2) : lvElement;
    }

    public final E poll() {
        Object[] objArr = this.consumerBuffer;
        long j = this.consumerIndex;
        long j2 = this.consumerMask;
        long calcWrappedOffset = calcWrappedOffset(j, j2);
        E lvElement = lvElement(objArr, calcWrappedOffset);
        Object obj = lvElement == HAS_NEXT ? 1 : null;
        if (lvElement == null || obj != null) {
            return obj != null ? newBufferPoll(lvNext(objArr), j, j2) : null;
        } else {
            soConsumerIndex(j + 1);
            soElement(objArr, calcWrappedOffset, null);
            return lvElement;
        }
    }

    public final int size() {
        long lvConsumerIndex = lvConsumerIndex();
        while (true) {
            long lvProducerIndex = lvProducerIndex();
            long lvConsumerIndex2 = lvConsumerIndex();
            if (lvConsumerIndex == lvConsumerIndex2) {
                return (int) (lvProducerIndex - lvConsumerIndex2);
            }
            lvConsumerIndex = lvConsumerIndex2;
        }
    }
}
