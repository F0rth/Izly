package rx;

final class Observable$OnSubscribeExtend<T> implements Observable$OnSubscribe<T> {
    final Observable<T> parent;

    Observable$OnSubscribeExtend(Observable<T> observable) {
        this.parent = observable;
    }

    public final void call(Subscriber<? super T> subscriber) {
        subscriber.add(Observable.subscribe(subscriber, this.parent));
    }
}
