package rx.internal.operators;

import java.io.Serializable;
import rx.Notification.Kind;
import rx.Observer;

public final class NotificationLite<T> {
    private static final NotificationLite INSTANCE = new NotificationLite();
    private static final Object ON_COMPLETED_SENTINEL = new Serializable() {
        private static final long serialVersionUID = 1;

        public final String toString() {
            return "Notification=>Completed";
        }
    };
    private static final Object ON_NEXT_NULL_SENTINEL = new Serializable() {
        private static final long serialVersionUID = 2;

        public final String toString() {
            return "Notification=>NULL";
        }
    };

    static class OnErrorSentinel implements Serializable {
        private static final long serialVersionUID = 3;
        final Throwable e;

        public OnErrorSentinel(Throwable th) {
            this.e = th;
        }

        public String toString() {
            return "Notification=>Error:" + this.e;
        }
    }

    private NotificationLite() {
    }

    public static <T> NotificationLite<T> instance() {
        return INSTANCE;
    }

    public final boolean accept(Observer<? super T> observer, Object obj) {
        if (obj == ON_COMPLETED_SENTINEL) {
            observer.onCompleted();
            return true;
        } else if (obj == ON_NEXT_NULL_SENTINEL) {
            observer.onNext(null);
            return false;
        } else if (obj == null) {
            throw new IllegalArgumentException("The lite notification can not be null");
        } else if (obj.getClass() == OnErrorSentinel.class) {
            observer.onError(((OnErrorSentinel) obj).e);
            return true;
        } else {
            observer.onNext(obj);
            return false;
        }
    }

    public final Object completed() {
        return ON_COMPLETED_SENTINEL;
    }

    public final Object error(Throwable th) {
        return new OnErrorSentinel(th);
    }

    public final Throwable getError(Object obj) {
        return ((OnErrorSentinel) obj).e;
    }

    public final T getValue(Object obj) {
        return obj == ON_NEXT_NULL_SENTINEL ? null : obj;
    }

    public final boolean isCompleted(Object obj) {
        return obj == ON_COMPLETED_SENTINEL;
    }

    public final boolean isError(Object obj) {
        return obj instanceof OnErrorSentinel;
    }

    public final boolean isNext(Object obj) {
        return (obj == null || isError(obj) || isCompleted(obj)) ? false : true;
    }

    public final boolean isNull(Object obj) {
        return obj == ON_NEXT_NULL_SENTINEL;
    }

    public final Kind kind(Object obj) {
        if (obj != null) {
            return obj == ON_COMPLETED_SENTINEL ? Kind.OnCompleted : obj instanceof OnErrorSentinel ? Kind.OnError : Kind.OnNext;
        } else {
            throw new IllegalArgumentException("The lite notification can not be null");
        }
    }

    public final Object next(T t) {
        return t == null ? ON_NEXT_NULL_SENTINEL : t;
    }
}
