package retrofit2;

import defpackage.nw;
import defpackage.ny;
import defpackage.oc;
import defpackage.og;
import java.io.IOException;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.spongycastle.asn1.x509.DisplayText;

final class OkHttpCall<T> implements Call<T> {
    private final Object[] args;
    private volatile boolean canceled;
    private Throwable creationFailure;
    private boolean executed;
    private Call rawCall;
    private final ServiceMethod<T> serviceMethod;

    static final class ExceptionCatchingRequestBody extends ResponseBody {
        private final ResponseBody delegate;
        IOException thrownException;

        ExceptionCatchingRequestBody(ResponseBody responseBody) {
            this.delegate = responseBody;
        }

        public final void close() {
            this.delegate.close();
        }

        public final long contentLength() {
            return this.delegate.contentLength();
        }

        public final MediaType contentType() {
            return this.delegate.contentType();
        }

        public final ny source() {
            return og.a(new oc(this.delegate.source()) {
                public long read(nw nwVar, long j) throws IOException {
                    try {
                        return super.read(nwVar, j);
                    } catch (IOException e) {
                        ExceptionCatchingRequestBody.this.thrownException = e;
                        throw e;
                    }
                }
            });
        }

        final void throwIfCaught() throws IOException {
            if (this.thrownException != null) {
                throw this.thrownException;
            }
        }
    }

    static final class NoContentResponseBody extends ResponseBody {
        private final long contentLength;
        private final MediaType contentType;

        NoContentResponseBody(MediaType mediaType, long j) {
            this.contentType = mediaType;
            this.contentLength = j;
        }

        public final long contentLength() {
            return this.contentLength;
        }

        public final MediaType contentType() {
            return this.contentType;
        }

        public final ny source() {
            throw new IllegalStateException("Cannot read raw response body of a converted body.");
        }
    }

    OkHttpCall(ServiceMethod<T> serviceMethod, Object[] objArr) {
        this.serviceMethod = serviceMethod;
        this.args = objArr;
    }

    private Call createRawCall() throws IOException {
        Call newCall = this.serviceMethod.callFactory.newCall(this.serviceMethod.toRequest(this.args));
        if (newCall != null) {
            return newCall;
        }
        throw new NullPointerException("Call.Factory returned null.");
    }

    public final void cancel() {
        this.canceled = true;
        synchronized (this) {
            Call call = this.rawCall;
        }
        if (call != null) {
            call.cancel();
        }
    }

    public final OkHttpCall<T> clone() {
        return new OkHttpCall(this.serviceMethod, this.args);
    }

    public final void enqueue(final Callback<T> callback) {
        if (callback == null) {
            throw new NullPointerException("callback == null");
        }
        synchronized (this) {
            if (this.executed) {
                throw new IllegalStateException("Already executed.");
            }
            Call createRawCall;
            this.executed = true;
            Call call = this.rawCall;
            Throwable th = this.creationFailure;
            if (call == null && th == null) {
                try {
                    createRawCall = createRawCall();
                    this.rawCall = createRawCall;
                } catch (Throwable th2) {
                    th = th2;
                    this.creationFailure = th;
                    createRawCall = call;
                }
            } else {
                createRawCall = call;
            }
        }
        if (th != null) {
            callback.onFailure(this, th);
            return;
        }
        if (this.canceled) {
            createRawCall.cancel();
        }
        createRawCall.enqueue(new Callback() {
            private void callFailure(Throwable th) {
                try {
                    callback.onFailure(OkHttpCall.this, th);
                } catch (Throwable th2) {
                    th2.printStackTrace();
                }
            }

            private void callSuccess(Response<T> response) {
                try {
                    callback.onResponse(OkHttpCall.this, response);
                } catch (Throwable th) {
                    th.printStackTrace();
                }
            }

            public void onFailure(Call call, IOException iOException) {
                try {
                    callback.onFailure(OkHttpCall.this, iOException);
                } catch (Throwable th) {
                    th.printStackTrace();
                }
            }

            public void onResponse(Call call, Response response) throws IOException {
                try {
                    callSuccess(OkHttpCall.this.parseResponse(response));
                } catch (Throwable th) {
                    callFailure(th);
                }
            }
        });
    }

    public final Response<T> execute() throws IOException {
        Call call;
        Throwable e;
        synchronized (this) {
            if (this.executed) {
                throw new IllegalStateException("Already executed.");
            }
            this.executed = true;
            if (this.creationFailure == null) {
                call = this.rawCall;
                if (call == null) {
                    try {
                        call = createRawCall();
                        this.rawCall = call;
                    } catch (IOException e2) {
                        e = e2;
                        this.creationFailure = e;
                        throw e;
                    } catch (RuntimeException e3) {
                        e = e3;
                        this.creationFailure = e;
                        throw e;
                    }
                }
            } else if (this.creationFailure instanceof IOException) {
                throw ((IOException) this.creationFailure);
            } else {
                throw ((RuntimeException) this.creationFailure);
            }
        }
        if (this.canceled) {
            call.cancel();
        }
        return parseResponse(call.execute());
    }

    public final boolean isCanceled() {
        return this.canceled;
    }

    public final boolean isExecuted() {
        boolean z;
        synchronized (this) {
            z = this.executed;
        }
        return z;
    }

    final Response<T> parseResponse(Response response) throws IOException {
        ResponseBody body = response.body();
        Response build = response.newBuilder().body(new NoContentResponseBody(body.contentType(), body.contentLength())).build();
        int code = build.code();
        if (code < DisplayText.DISPLAY_TEXT_MAXIMUM_SIZE || code >= 300) {
            try {
                Response<T> error = Response.error(Utils.buffer(body), build);
                return error;
            } finally {
                body.close();
            }
        } else if (code == 204 || code == 205) {
            return Response.success(null, build);
        } else {
            ResponseBody exceptionCatchingRequestBody = new ExceptionCatchingRequestBody(body);
            try {
                return Response.success(this.serviceMethod.toResponse(exceptionCatchingRequestBody), build);
            } catch (RuntimeException e) {
                exceptionCatchingRequestBody.throwIfCaught();
                throw e;
            }
        }
    }

    public final Request request() {
        Request request;
        synchronized (this) {
            Call call = this.rawCall;
            if (call != null) {
                request = call.request();
            } else if (this.creationFailure == null) {
                try {
                    call = createRawCall();
                    this.rawCall = call;
                    request = call.request();
                } catch (Throwable e) {
                    this.creationFailure = e;
                    throw e;
                } catch (Throwable e2) {
                    this.creationFailure = e2;
                    throw new RuntimeException("Unable to create request.", e2);
                }
            } else if (this.creationFailure instanceof IOException) {
                throw new RuntimeException("Unable to create request.", this.creationFailure);
            } else {
                throw ((RuntimeException) this.creationFailure);
            }
        }
        return request;
    }
}
