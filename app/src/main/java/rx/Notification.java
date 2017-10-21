package rx;

public final class Notification<T> {
    private static final Notification<Void> ON_COMPLETED = new Notification(Kind.OnCompleted, null, null);
    private final Kind kind;
    private final Throwable throwable;
    private final T value;

    public enum Kind {
        OnNext,
        OnError,
        OnCompleted
    }

    private Notification(Kind kind, T t, Throwable th) {
        this.value = t;
        this.throwable = th;
        this.kind = kind;
    }

    public static <T> Notification<T> createOnCompleted() {
        return ON_COMPLETED;
    }

    public static <T> Notification<T> createOnCompleted(Class<T> cls) {
        return ON_COMPLETED;
    }

    public static <T> Notification<T> createOnError(Throwable th) {
        return new Notification(Kind.OnError, null, th);
    }

    public static <T> Notification<T> createOnNext(T t) {
        return new Notification(Kind.OnNext, t, null);
    }

    public final void accept(Observer<? super T> observer) {
        if (isOnNext()) {
            observer.onNext(getValue());
        } else if (isOnCompleted()) {
            observer.onCompleted();
        } else if (isOnError()) {
            observer.onError(getThrowable());
        }
    }

    public final boolean equals(Object obj) {
        if (obj != null) {
            if (this == obj) {
                return true;
            }
            if (obj.getClass() == getClass()) {
                Notification notification = (Notification) obj;
                if (notification.getKind() == getKind() && ((!hasValue() || getValue().equals(notification.getValue())) && ((!hasThrowable() || getThrowable().equals(notification.getThrowable())) && (hasValue() || hasThrowable() || !notification.hasValue())))) {
                    if (hasValue() || hasThrowable()) {
                        return true;
                    }
                    if (!notification.hasThrowable()) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public final Kind getKind() {
        return this.kind;
    }

    public final Throwable getThrowable() {
        return this.throwable;
    }

    public final T getValue() {
        return this.value;
    }

    public final boolean hasThrowable() {
        return isOnError() && this.throwable != null;
    }

    public final boolean hasValue() {
        return isOnNext() && this.value != null;
    }

    public final int hashCode() {
        int hashCode = getKind().hashCode();
        if (hasValue()) {
            hashCode = (hashCode * 31) + getValue().hashCode();
        }
        return hasThrowable() ? (hashCode * 31) + getThrowable().hashCode() : hashCode;
    }

    public final boolean isOnCompleted() {
        return getKind() == Kind.OnCompleted;
    }

    public final boolean isOnError() {
        return getKind() == Kind.OnError;
    }

    public final boolean isOnNext() {
        return getKind() == Kind.OnNext;
    }

    public final String toString() {
        StringBuilder append = new StringBuilder("[").append(super.toString()).append(" ").append(getKind());
        if (hasValue()) {
            append.append(" ").append(getValue());
        }
        if (hasThrowable()) {
            append.append(" ").append(getThrowable().getMessage());
        }
        append.append("]");
        return append.toString();
    }
}
