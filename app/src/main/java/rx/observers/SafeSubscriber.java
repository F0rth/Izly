package rx.observers;

import java.util.Arrays;
import rx.Observer;
import rx.Subscriber;
import rx.exceptions.CompositeException;
import rx.exceptions.Exceptions;
import rx.exceptions.OnErrorFailedException;
import rx.exceptions.UnsubscribeFailedException;
import rx.internal.util.RxJavaPluginUtils;

public class SafeSubscriber<T> extends Subscriber<T> {
    private final Subscriber<? super T> actual;
    boolean done = false;

    public SafeSubscriber(Subscriber<? super T> subscriber) {
        super(subscriber);
        this.actual = subscriber;
    }

    protected void _onError(Throwable th) {
        RxJavaPluginUtils.handleException(th);
        try {
            this.actual.onError(th);
            try {
                unsubscribe();
            } catch (Throwable e) {
                RxJavaPluginUtils.handleException(e);
                throw new OnErrorFailedException(e);
            }
        } catch (Throwable th2) {
            RxJavaPluginUtils.handleException(th2);
            OnErrorFailedException onErrorFailedException = new OnErrorFailedException("Error occurred when trying to propagate error to Observer.onError and during unsubscription.", new CompositeException(Arrays.asList(new Throwable[]{th, e, th2})));
        }
    }

    public Subscriber<? super T> getActual() {
        return this.actual;
    }

    public void onCompleted() {
        UnsubscribeFailedException unsubscribeFailedException;
        if (!this.done) {
            this.done = true;
            try {
                this.actual.onCompleted();
                try {
                    unsubscribe();
                } catch (Throwable th) {
                    RxJavaPluginUtils.handleException(th);
                    unsubscribeFailedException = new UnsubscribeFailedException(th.getMessage(), th);
                }
            } catch (Throwable th2) {
                try {
                    unsubscribe();
                } catch (Throwable th3) {
                    RxJavaPluginUtils.handleException(th3);
                    unsubscribeFailedException = new UnsubscribeFailedException(th3.getMessage(), th3);
                }
            }
        }
    }

    public void onError(Throwable th) {
        Exceptions.throwIfFatal(th);
        if (!this.done) {
            this.done = true;
            _onError(th);
        }
    }

    public void onNext(T t) {
        try {
            if (!this.done) {
                this.actual.onNext(t);
            }
        } catch (Throwable th) {
            Exceptions.throwOrReport(th, (Observer) this);
        }
    }
}
