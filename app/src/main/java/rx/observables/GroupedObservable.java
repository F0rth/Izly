package rx.observables;

import rx.Observable;
import rx.Observable$OnSubscribe;
import rx.Subscriber;

public class GroupedObservable<K, T> extends Observable<T> {
    private final K key;

    protected GroupedObservable(K k, Observable$OnSubscribe<T> observable$OnSubscribe) {
        super(observable$OnSubscribe);
        this.key = k;
    }

    public static <K, T> GroupedObservable<K, T> create(K k, Observable$OnSubscribe<T> observable$OnSubscribe) {
        return new GroupedObservable(k, observable$OnSubscribe);
    }

    public static <K, T> GroupedObservable<K, T> from(K k, final Observable<T> observable) {
        return new GroupedObservable(k, new Observable$OnSubscribe<T>() {
            public final void call(Subscriber<? super T> subscriber) {
                observable.unsafeSubscribe(subscriber);
            }
        });
    }

    public K getKey() {
        return this.key;
    }
}
