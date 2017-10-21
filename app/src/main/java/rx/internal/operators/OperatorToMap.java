package rx.internal.operators;

import java.util.HashMap;
import java.util.Map;
import rx.Observable$Operator;
import rx.Observer;
import rx.Subscriber;
import rx.exceptions.Exceptions;
import rx.functions.Func0;
import rx.functions.Func1;
import rx.observers.Subscribers;

public final class OperatorToMap<T, K, V> implements Observable$Operator<Map<K, V>, T> {
    final Func1<? super T, ? extends K> keySelector;
    private final Func0<? extends Map<K, V>> mapFactory;
    final Func1<? super T, ? extends V> valueSelector;

    public static final class DefaultToMapFactory<K, V> implements Func0<Map<K, V>> {
        public final Map<K, V> call() {
            return new HashMap();
        }
    }

    public OperatorToMap(Func1<? super T, ? extends K> func1, Func1<? super T, ? extends V> func12) {
        this(func1, func12, new DefaultToMapFactory());
    }

    public OperatorToMap(Func1<? super T, ? extends K> func1, Func1<? super T, ? extends V> func12, Func0<? extends Map<K, V>> func0) {
        this.keySelector = func1;
        this.valueSelector = func12;
        this.mapFactory = func0;
    }

    public final Subscriber<? super T> call(final Subscriber<? super Map<K, V>> subscriber) {
        try {
            final Map map = (Map) this.mapFactory.call();
            return new Subscriber<T>(subscriber) {
                private Map<K, V> map = map;

                public void onCompleted() {
                    Map map = this.map;
                    this.map = null;
                    subscriber.onNext(map);
                    subscriber.onCompleted();
                }

                public void onError(Throwable th) {
                    this.map = null;
                    subscriber.onError(th);
                }

                public void onNext(T t) {
                    try {
                        this.map.put(OperatorToMap.this.keySelector.call(t), OperatorToMap.this.valueSelector.call(t));
                    } catch (Throwable th) {
                        Exceptions.throwOrReport(th, subscriber);
                    }
                }

                public void onStart() {
                    request(Long.MAX_VALUE);
                }
            };
        } catch (Throwable th) {
            Exceptions.throwOrReport(th, (Observer) subscriber);
            Subscriber<? super T> empty = Subscribers.empty();
            empty.unsubscribe();
            return empty;
        }
    }
}
