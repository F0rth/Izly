package rx;

class Completable$30 implements Observable$OnSubscribe<T> {
    final /* synthetic */ Completable this$0;

    Completable$30(Completable completable) {
        this.this$0 = completable;
    }

    public void call(Subscriber<? super T> subscriber) {
        this.this$0.subscribe(subscriber);
    }
}
