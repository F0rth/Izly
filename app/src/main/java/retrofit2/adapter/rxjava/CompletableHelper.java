package retrofit2.adapter.rxjava;

import java.lang.reflect.Type;
import retrofit2.Call;
import retrofit2.CallAdapter;
import retrofit2.Response;
import rx.Completable;
import rx.Completable$CompletableOnSubscribe;
import rx.Completable$CompletableSubscriber;
import rx.Scheduler;
import rx.Subscription;
import rx.exceptions.Exceptions;
import rx.functions.Action0;
import rx.subscriptions.Subscriptions;

final class CompletableHelper {

    static class CompletableCallAdapter implements CallAdapter<Completable> {
        private final Scheduler scheduler;

        CompletableCallAdapter(Scheduler scheduler) {
            this.scheduler = scheduler;
        }

        public Completable adapt(Call call) {
            Completable create = Completable.create(new CompletableCallOnSubscribe(call));
            return this.scheduler != null ? create.subscribeOn(this.scheduler) : create;
        }

        public Type responseType() {
            return Void.class;
        }
    }

    static final class CompletableCallOnSubscribe implements Completable$CompletableOnSubscribe {
        private final Call originalCall;

        CompletableCallOnSubscribe(Call call) {
            this.originalCall = call;
        }

        public final void call(Completable$CompletableSubscriber completable$CompletableSubscriber) {
            final Call clone = this.originalCall.clone();
            Subscription create = Subscriptions.create(new Action0() {
                public void call() {
                    clone.cancel();
                }
            });
            completable$CompletableSubscriber.onSubscribe(create);
            try {
                Response execute = clone.execute();
                if (!create.isUnsubscribed()) {
                    if (execute.isSuccessful()) {
                        completable$CompletableSubscriber.onCompleted();
                    } else {
                        completable$CompletableSubscriber.onError(new HttpException(execute));
                    }
                }
            } catch (Throwable th) {
                Exceptions.throwIfFatal(th);
                if (!create.isUnsubscribed()) {
                    completable$CompletableSubscriber.onError(th);
                }
            }
        }
    }

    CompletableHelper() {
    }

    static CallAdapter<Completable> createCallAdapter(Scheduler scheduler) {
        return new CompletableCallAdapter(scheduler);
    }
}
