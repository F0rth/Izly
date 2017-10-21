package okhttp3;

import java.io.IOException;
import java.net.ProtocolException;
import okhttp3.Interceptor.Chain;
import okhttp3.Request.Builder;
import okhttp3.internal.NamedRunnable;
import okhttp3.internal.Platform;
import okhttp3.internal.http.HttpEngine;
import okhttp3.internal.http.RequestException;
import okhttp3.internal.http.RouteException;
import okhttp3.internal.http.StreamAllocation;

final class RealCall implements Call {
    volatile boolean canceled;
    private final OkHttpClient client;
    HttpEngine engine;
    private boolean executed;
    Request originalRequest;

    class ApplicationInterceptorChain implements Chain {
        private final boolean forWebSocket;
        private final int index;
        private final Request request;

        ApplicationInterceptorChain(int i, Request request, boolean z) {
            this.index = i;
            this.request = request;
            this.forWebSocket = z;
        }

        public Connection connection() {
            return null;
        }

        public Response proceed(Request request) throws IOException {
            if (this.index >= RealCall.this.client.interceptors().size()) {
                return RealCall.this.getResponse(request, this.forWebSocket);
            }
            Interceptor interceptor = (Interceptor) RealCall.this.client.interceptors().get(this.index);
            Response intercept = interceptor.intercept(new ApplicationInterceptorChain(this.index + 1, request, this.forWebSocket));
            if (intercept != null) {
                return intercept;
            }
            throw new NullPointerException("application interceptor " + interceptor + " returned null");
        }

        public Request request() {
            return this.request;
        }
    }

    final class AsyncCall extends NamedRunnable {
        private final boolean forWebSocket;
        private final Callback responseCallback;

        private AsyncCall(Callback callback, boolean z) {
            super("OkHttp %s", r5.redactedUrl().toString());
            this.responseCallback = callback;
            this.forWebSocket = z;
        }

        final void cancel() {
            RealCall.this.cancel();
        }

        protected final void execute() {
            Throwable e;
            Object obj = 1;
            try {
                Response access$100 = RealCall.this.getResponseWithInterceptorChain(this.forWebSocket);
                if (RealCall.this.canceled) {
                    try {
                        this.responseCallback.onFailure(RealCall.this, new IOException("Canceled"));
                    } catch (IOException e2) {
                        e = e2;
                        if (obj == null) {
                            this.responseCallback.onFailure(RealCall.this, e);
                        } else {
                            try {
                                Platform.get().log(4, "Callback failure for " + RealCall.this.toLoggableString(), e);
                            } catch (Throwable th) {
                                RealCall.this.client.dispatcher().finished(this);
                            }
                        }
                        RealCall.this.client.dispatcher().finished(this);
                    }
                }
                this.responseCallback.onResponse(RealCall.this, access$100);
                RealCall.this.client.dispatcher().finished(this);
            } catch (IOException e3) {
                e = e3;
                obj = null;
                if (obj == null) {
                    Platform.get().log(4, "Callback failure for " + RealCall.this.toLoggableString(), e);
                } else {
                    this.responseCallback.onFailure(RealCall.this, e);
                }
                RealCall.this.client.dispatcher().finished(this);
            }
        }

        final RealCall get() {
            return RealCall.this;
        }

        final String host() {
            return RealCall.this.originalRequest.url().host();
        }

        final Request request() {
            return RealCall.this.originalRequest;
        }

        final Object tag() {
            return RealCall.this.originalRequest.tag();
        }
    }

    protected RealCall(OkHttpClient okHttpClient, Request request) {
        this.client = okHttpClient;
        this.originalRequest = request;
    }

    private Response getResponseWithInterceptorChain(boolean z) throws IOException {
        return new ApplicationInterceptorChain(0, this.originalRequest, z).proceed(this.originalRequest);
    }

    private String toLoggableString() {
        return (this.canceled ? "canceled call" : "call") + " to " + redactedUrl();
    }

    public final void cancel() {
        this.canceled = true;
        if (this.engine != null) {
            this.engine.cancel();
        }
    }

    public final void enqueue(Callback callback) {
        enqueue(callback, false);
    }

    final void enqueue(Callback callback, boolean z) {
        synchronized (this) {
            if (this.executed) {
                throw new IllegalStateException("Already Executed");
            }
            this.executed = true;
        }
        this.client.dispatcher().enqueue(new AsyncCall(callback, z));
    }

    public final Response execute() throws IOException {
        synchronized (this) {
            if (this.executed) {
                throw new IllegalStateException("Already Executed");
            }
            this.executed = true;
        }
        try {
            this.client.dispatcher().executed(this);
            Response responseWithInterceptorChain = getResponseWithInterceptorChain(false);
            if (responseWithInterceptorChain != null) {
                return responseWithInterceptorChain;
            }
            throw new IOException("Canceled");
        } finally {
            this.client.dispatcher().finished((Call) this);
        }
    }

    final Response getResponse(Request request, boolean z) throws IOException {
        Request build;
        HttpEngine recover;
        Throwable th;
        Object obj;
        RequestBody body = request.body();
        if (body != null) {
            Builder newBuilder = request.newBuilder();
            MediaType contentType = body.contentType();
            if (contentType != null) {
                newBuilder.header("Content-Type", contentType.toString());
            }
            long contentLength = body.contentLength();
            if (contentLength != -1) {
                newBuilder.header("Content-Length", Long.toString(contentLength));
                newBuilder.removeHeader("Transfer-Encoding");
            } else {
                newBuilder.header("Transfer-Encoding", "chunked");
                newBuilder.removeHeader("Content-Length");
            }
            build = newBuilder.build();
        } else {
            build = request;
        }
        this.engine = new HttpEngine(this.client, build, false, false, z, null, null, null);
        int i = 0;
        while (!this.canceled) {
            try {
                this.engine.sendRequest();
                this.engine.readResponse();
                Response response = this.engine.getResponse();
                build = this.engine.followUpRequest();
                if (build == null) {
                    if (!z) {
                        this.engine.releaseStreamAllocation();
                    }
                    return response;
                }
                StreamAllocation close = this.engine.close();
                int i2 = i + 1;
                if (i2 > 20) {
                    close.release();
                    throw new ProtocolException("Too many follow-up requests: " + i2);
                }
                if (!this.engine.sameConnection(build.url())) {
                    close.release();
                    close = null;
                } else if (close.stream() != null) {
                    throw new IllegalStateException("Closing the body of " + response + " didn't close its backing stream. Bad interceptor?");
                }
                this.engine = new HttpEngine(this.client, build, false, false, z, close, null, response);
                i = i2;
            } catch (RequestException e) {
                throw e.getCause();
            } catch (RouteException e2) {
                recover = this.engine.recover(e2.getLastConnectException(), true, null);
                if (recover != null) {
                    this.engine = recover;
                } else {
                    throw e2.getLastConnectException();
                }
            } catch (IOException e3) {
                recover = this.engine.recover(e3, false, null);
                if (recover != null) {
                    this.engine = recover;
                } else {
                    throw e3;
                }
            } catch (Throwable th2) {
                th = th2;
                obj = null;
            }
        }
        this.engine.releaseStreamAllocation();
        throw new IOException("Canceled");
        if (obj != null) {
            this.engine.close().release();
        }
        throw th;
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

    final HttpUrl redactedUrl() {
        return this.originalRequest.url().resolve("/...");
    }

    public final Request request() {
        return this.originalRequest;
    }

    final Object tag() {
        return this.originalRequest.tag();
    }
}
