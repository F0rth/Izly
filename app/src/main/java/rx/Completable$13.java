package rx;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicBoolean;
import rx.exceptions.CompositeException;
import rx.exceptions.Exceptions;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func0;
import rx.functions.Func1;
import rx.subscriptions.Subscriptions;

final class Completable$13 implements Completable$CompletableOnSubscribe {
    final /* synthetic */ Func1 val$completableFunc1;
    final /* synthetic */ Action1 val$disposer;
    final /* synthetic */ boolean val$eager;
    final /* synthetic */ Func0 val$resourceFunc0;

    Completable$13(Func0 func0, Func1 func1, Action1 action1, boolean z) {
        this.val$resourceFunc0 = func0;
        this.val$completableFunc1 = func1;
        this.val$disposer = action1;
        this.val$eager = z;
    }

    public final void call(final Completable$CompletableSubscriber completable$CompletableSubscriber) {
        try {
            final Object call = this.val$resourceFunc0.call();
            try {
                Completable completable = (Completable) this.val$completableFunc1.call(call);
                if (completable == null) {
                    try {
                        this.val$disposer.call(call);
                        completable$CompletableSubscriber.onSubscribe(Subscriptions.unsubscribed());
                        completable$CompletableSubscriber.onError(new NullPointerException("The completable supplied is null"));
                        return;
                    } catch (Throwable th) {
                        Exceptions.throwIfFatal(th);
                        completable$CompletableSubscriber.onSubscribe(Subscriptions.unsubscribed());
                        completable$CompletableSubscriber.onError(new CompositeException(Arrays.asList(new Throwable[]{new NullPointerException("The completable supplied is null"), th})));
                        return;
                    }
                }
                final AtomicBoolean atomicBoolean = new AtomicBoolean();
                completable.subscribe(new Completable$CompletableSubscriber() {
                    Subscription d;

                    void dispose() {
                        this.d.unsubscribe();
                        if (atomicBoolean.compareAndSet(false, true)) {
                            try {
                                Completable$13.this.val$disposer.call(call);
                            } catch (Throwable th) {
                                Completable.ERROR_HANDLER.handleError(th);
                            }
                        }
                    }

                    public void onCompleted() {
                        if (Completable$13.this.val$eager && atomicBoolean.compareAndSet(false, true)) {
                            try {
                                Completable$13.this.val$disposer.call(call);
                            } catch (Throwable th) {
                                completable$CompletableSubscriber.onError(th);
                                return;
                            }
                        }
                        completable$CompletableSubscriber.onCompleted();
                        if (!Completable$13.this.val$eager) {
                            dispose();
                        }
                    }

                    public void onError(Throwable th) {
                        if (Completable$13.this.val$eager && atomicBoolean.compareAndSet(false, true)) {
                            try {
                                Completable$13.this.val$disposer.call(call);
                            } catch (Throwable th2) {
                                Throwable th3 = th2;
                                th = new CompositeException(Arrays.asList(new Throwable[]{th, th3}));
                            }
                        }
                        completable$CompletableSubscriber.onError(th);
                        if (!Completable$13.this.val$eager) {
                            dispose();
                        }
                    }

                    public void onSubscribe(Subscription subscription) {
                        this.d = subscription;
                        completable$CompletableSubscriber.onSubscribe(Subscriptions.create(new Action0() {
                            public void call() {
                                AnonymousClass1.this.dispose();
                            }
                        }));
                    }
                });
            } catch (Throwable th2) {
                Exceptions.throwIfFatal(th);
                Exceptions.throwIfFatal(th2);
                completable$CompletableSubscriber.onSubscribe(Subscriptions.unsubscribed());
                completable$CompletableSubscriber.onError(new CompositeException(Arrays.asList(new Throwable[]{th, th2})));
            }
        } catch (Throwable th3) {
            completable$CompletableSubscriber.onSubscribe(Subscriptions.unsubscribed());
            completable$CompletableSubscriber.onError(th3);
        }
    }
}
