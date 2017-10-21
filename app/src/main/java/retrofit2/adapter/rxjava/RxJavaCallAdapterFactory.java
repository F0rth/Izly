package retrofit2.adapter.rxjava;

import java.lang.annotation.Annotation;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.concurrent.atomic.AtomicBoolean;
import retrofit2.Call;
import retrofit2.CallAdapter;
import retrofit2.CallAdapter.Factory;
import retrofit2.Response;
import retrofit2.Retrofit;
import rx.Observable;
import rx.Observable$OnSubscribe;
import rx.Producer;
import rx.Scheduler;
import rx.Subscriber;
import rx.Subscription;
import rx.exceptions.Exceptions;
import rx.functions.Func1;

public final class RxJavaCallAdapterFactory extends Factory {
    private final Scheduler scheduler;

    static final class CallOnSubscribe<T> implements Observable$OnSubscribe<Response<T>> {
        private final Call<T> originalCall;

        CallOnSubscribe(Call<T> call) {
            this.originalCall = call;
        }

        public final void call(Subscriber<? super Response<T>> subscriber) {
            Producer requestArbiter = new RequestArbiter(this.originalCall.clone(), subscriber);
            subscriber.add(requestArbiter);
            subscriber.setProducer(requestArbiter);
        }
    }

    static final class RequestArbiter<T> extends AtomicBoolean implements Producer, Subscription {
        private final Call<T> call;
        private final Subscriber<? super Response<T>> subscriber;

        RequestArbiter(Call<T> call, Subscriber<? super Response<T>> subscriber) {
            this.call = call;
            this.subscriber = subscriber;
        }

        public final boolean isUnsubscribed() {
            return this.call.isCanceled();
        }

        public final void request(long j) {
            if (j < 0) {
                throw new IllegalArgumentException("n < 0: " + j);
            } else if (j != 0 && compareAndSet(false, true)) {
                try {
                    Response execute = this.call.execute();
                    if (!this.subscriber.isUnsubscribed()) {
                        this.subscriber.onNext(execute);
                    }
                    if (!this.subscriber.isUnsubscribed()) {
                        this.subscriber.onCompleted();
                    }
                } catch (Throwable th) {
                    Exceptions.throwIfFatal(th);
                    if (!this.subscriber.isUnsubscribed()) {
                        this.subscriber.onError(th);
                    }
                }
            }
        }

        public final void unsubscribe() {
            this.call.cancel();
        }
    }

    static final class ResponseCallAdapter implements CallAdapter<Observable<?>> {
        private final Type responseType;
        private final Scheduler scheduler;

        ResponseCallAdapter(Type type, Scheduler scheduler) {
            this.responseType = type;
            this.scheduler = scheduler;
        }

        public final <R> Observable<Response<R>> adapt(Call<R> call) {
            Observable<Response<R>> create = Observable.create(new CallOnSubscribe(call));
            return this.scheduler != null ? create.subscribeOn(this.scheduler) : create;
        }

        public final Type responseType() {
            return this.responseType;
        }
    }

    static final class ResultCallAdapter implements CallAdapter<Observable<?>> {
        private final Type responseType;
        private final Scheduler scheduler;

        ResultCallAdapter(Type type, Scheduler scheduler) {
            this.responseType = type;
            this.scheduler = scheduler;
        }

        public final <R> Observable<Result<R>> adapt(Call<R> call) {
            Observable<Result<R>> onErrorReturn = Observable.create(new CallOnSubscribe(call)).map(new Func1<Response<R>, Result<R>>() {
                public Result<R> call(Response<R> response) {
                    return Result.response(response);
                }
            }).onErrorReturn(new Func1<Throwable, Result<R>>() {
                public Result<R> call(Throwable th) {
                    return Result.error(th);
                }
            });
            return this.scheduler != null ? onErrorReturn.subscribeOn(this.scheduler) : onErrorReturn;
        }

        public final Type responseType() {
            return this.responseType;
        }
    }

    static final class SimpleCallAdapter implements CallAdapter<Observable<?>> {
        private final Type responseType;
        private final Scheduler scheduler;

        SimpleCallAdapter(Type type, Scheduler scheduler) {
            this.responseType = type;
            this.scheduler = scheduler;
        }

        public final <R> Observable<R> adapt(Call<R> call) {
            Observable<R> lift = Observable.create(new CallOnSubscribe(call)).lift(OperatorMapResponseToBodyOrError.instance());
            return this.scheduler != null ? lift.subscribeOn(this.scheduler) : lift;
        }

        public final Type responseType() {
            return this.responseType;
        }
    }

    private RxJavaCallAdapterFactory(Scheduler scheduler) {
        this.scheduler = scheduler;
    }

    public static RxJavaCallAdapterFactory create() {
        return new RxJavaCallAdapterFactory(null);
    }

    public static RxJavaCallAdapterFactory createWithScheduler(Scheduler scheduler) {
        if (scheduler != null) {
            return new RxJavaCallAdapterFactory(scheduler);
        }
        throw new NullPointerException("scheduler == null");
    }

    private CallAdapter<Observable<?>> getCallAdapter(Type type, Scheduler scheduler) {
        Type parameterUpperBound = Factory.getParameterUpperBound(0, (ParameterizedType) type);
        Class rawType = Factory.getRawType(parameterUpperBound);
        if (rawType == Response.class) {
            if (parameterUpperBound instanceof ParameterizedType) {
                return new ResponseCallAdapter(Factory.getParameterUpperBound(0, (ParameterizedType) parameterUpperBound), scheduler);
            }
            throw new IllegalStateException("Response must be parameterized as Response<Foo> or Response<? extends Foo>");
        } else if (rawType != Result.class) {
            return new SimpleCallAdapter(parameterUpperBound, scheduler);
        } else {
            if (parameterUpperBound instanceof ParameterizedType) {
                return new ResultCallAdapter(Factory.getParameterUpperBound(0, (ParameterizedType) parameterUpperBound), scheduler);
            }
            throw new IllegalStateException("Result must be parameterized as Result<Foo> or Result<? extends Foo>");
        }
    }

    public final CallAdapter<?> get(Type type, Annotation[] annotationArr, Retrofit retrofit) {
        Class rawType = Factory.getRawType(type);
        String canonicalName = rawType.getCanonicalName();
        boolean equals = "rx.Single".equals(canonicalName);
        boolean equals2 = "rx.Completable".equals(canonicalName);
        if (rawType != Observable.class && !equals && !equals2) {
            return null;
        }
        if (!equals2 && !(type instanceof ParameterizedType)) {
            String str = equals ? "Single" : "Observable";
            throw new IllegalStateException(str + " return type must be parameterized as " + str + "<Foo> or " + str + "<? extends Foo>");
        } else if (equals2) {
            return CompletableHelper.createCallAdapter(this.scheduler);
        } else {
            CallAdapter<?> callAdapter = getCallAdapter(type, this.scheduler);
            return equals ? SingleHelper.makeSingle(callAdapter) : callAdapter;
        }
    }
}
