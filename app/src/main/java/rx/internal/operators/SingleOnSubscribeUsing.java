package rx.internal.operators;

import java.util.Arrays;
import rx.Single;
import rx.Single.OnSubscribe;
import rx.SingleSubscriber;
import rx.exceptions.CompositeException;
import rx.exceptions.Exceptions;
import rx.functions.Action1;
import rx.functions.Func0;
import rx.functions.Func1;
import rx.plugins.RxJavaPlugins;

public final class SingleOnSubscribeUsing<T, Resource> implements OnSubscribe<T> {
    final Action1<? super Resource> disposeAction;
    final boolean disposeEagerly;
    final Func0<Resource> resourceFactory;
    final Func1<? super Resource, ? extends Single<? extends T>> singleFactory;

    public SingleOnSubscribeUsing(Func0<Resource> func0, Func1<? super Resource, ? extends Single<? extends T>> func1, Action1<? super Resource> action1, boolean z) {
        this.resourceFactory = func0;
        this.singleFactory = func1;
        this.disposeAction = action1;
        this.disposeEagerly = z;
    }

    public final void call(final SingleSubscriber<? super T> singleSubscriber) {
        try {
            final Object call = this.resourceFactory.call();
            try {
                Single single = (Single) this.singleFactory.call(call);
                if (single == null) {
                    handleSubscriptionTimeError(singleSubscriber, call, new NullPointerException("The single"));
                    return;
                }
                SingleSubscriber anonymousClass1 = new SingleSubscriber<T>() {
                    public void onError(Throwable th) {
                        SingleOnSubscribeUsing.this.handleSubscriptionTimeError(singleSubscriber, call, th);
                    }

                    public void onSuccess(T t) {
                        if (SingleOnSubscribeUsing.this.disposeEagerly) {
                            try {
                                SingleOnSubscribeUsing.this.disposeAction.call(call);
                            } catch (Throwable th) {
                                Exceptions.throwIfFatal(th);
                                singleSubscriber.onError(th);
                                return;
                            }
                        }
                        singleSubscriber.onSuccess(t);
                        if (!SingleOnSubscribeUsing.this.disposeEagerly) {
                            try {
                                SingleOnSubscribeUsing.this.disposeAction.call(call);
                            } catch (Throwable th2) {
                                Exceptions.throwIfFatal(th2);
                                RxJavaPlugins.getInstance().getErrorHandler().handleError(th2);
                            }
                        }
                    }
                };
                singleSubscriber.add(anonymousClass1);
                single.subscribe(anonymousClass1);
            } catch (Throwable th) {
                handleSubscriptionTimeError(singleSubscriber, call, th);
            }
        } catch (Throwable th2) {
            Exceptions.throwIfFatal(th2);
            singleSubscriber.onError(th2);
        }
    }

    final void handleSubscriptionTimeError(SingleSubscriber<? super T> singleSubscriber, Resource resource, Throwable th) {
        Exceptions.throwIfFatal(th);
        if (this.disposeEagerly) {
            try {
                this.disposeAction.call(resource);
            } catch (Throwable th2) {
                Exceptions.throwIfFatal(th2);
                th = new CompositeException(Arrays.asList(new Throwable[]{th, r1}));
            }
        }
        singleSubscriber.onError(th);
        if (!this.disposeEagerly) {
            try {
                this.disposeAction.call(resource);
            } catch (Throwable th22) {
                Exceptions.throwIfFatal(th22);
                RxJavaPlugins.getInstance().getErrorHandler().handleError(th22);
            }
        }
    }
}
