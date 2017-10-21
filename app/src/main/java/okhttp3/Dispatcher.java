package okhttp3;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import okhttp3.internal.Util;

public final class Dispatcher {
    private ExecutorService executorService;
    private int maxRequests = 64;
    private int maxRequestsPerHost = 5;
    private final Deque<AsyncCall> readyAsyncCalls = new ArrayDeque();
    private final Deque<AsyncCall> runningAsyncCalls = new ArrayDeque();
    private final Deque<RealCall> runningSyncCalls = new ArrayDeque();

    public Dispatcher(ExecutorService executorService) {
        this.executorService = executorService;
    }

    private void promoteCalls() {
        if (this.runningAsyncCalls.size() < this.maxRequests && !this.readyAsyncCalls.isEmpty()) {
            Iterator it = this.readyAsyncCalls.iterator();
            while (it.hasNext()) {
                AsyncCall asyncCall = (AsyncCall) it.next();
                if (runningCallsForHost(asyncCall) < this.maxRequestsPerHost) {
                    it.remove();
                    this.runningAsyncCalls.add(asyncCall);
                    executorService().execute(asyncCall);
                }
                if (this.runningAsyncCalls.size() >= this.maxRequests) {
                    return;
                }
            }
        }
    }

    private int runningCallsForHost(AsyncCall asyncCall) {
        int i = 0;
        for (AsyncCall host : this.runningAsyncCalls) {
            i = host.host().equals(asyncCall.host()) ? i + 1 : i;
        }
        return i;
    }

    public final void cancelAll() {
        synchronized (this) {
            for (AsyncCall cancel : this.readyAsyncCalls) {
                cancel.cancel();
            }
            for (AsyncCall cancel2 : this.runningAsyncCalls) {
                cancel2.cancel();
            }
            for (RealCall cancel3 : this.runningSyncCalls) {
                cancel3.cancel();
            }
        }
    }

    final void enqueue(AsyncCall asyncCall) {
        synchronized (this) {
            if (this.runningAsyncCalls.size() >= this.maxRequests || runningCallsForHost(asyncCall) >= this.maxRequestsPerHost) {
                this.readyAsyncCalls.add(asyncCall);
            } else {
                this.runningAsyncCalls.add(asyncCall);
                executorService().execute(asyncCall);
            }
        }
    }

    final void executed(RealCall realCall) {
        synchronized (this) {
            this.runningSyncCalls.add(realCall);
        }
    }

    public final ExecutorService executorService() {
        ExecutorService executorService;
        synchronized (this) {
            if (this.executorService == null) {
                this.executorService = new ThreadPoolExecutor(0, Integer.MAX_VALUE, 60, TimeUnit.SECONDS, new SynchronousQueue(), Util.threadFactory("OkHttp Dispatcher", false));
            }
            executorService = this.executorService;
        }
        return executorService;
    }

    final void finished(Call call) {
        synchronized (this) {
            if (this.runningSyncCalls.remove(call)) {
            } else {
                throw new AssertionError("Call wasn't in-flight!");
            }
        }
    }

    final void finished(AsyncCall asyncCall) {
        synchronized (this) {
            if (this.runningAsyncCalls.remove(asyncCall)) {
                promoteCalls();
            } else {
                throw new AssertionError("AsyncCall wasn't running!");
            }
        }
    }

    public final int getMaxRequests() {
        int i;
        synchronized (this) {
            i = this.maxRequests;
        }
        return i;
    }

    public final int getMaxRequestsPerHost() {
        int i;
        synchronized (this) {
            i = this.maxRequestsPerHost;
        }
        return i;
    }

    public final List<Call> queuedCalls() {
        List<Call> unmodifiableList;
        synchronized (this) {
            List arrayList = new ArrayList();
            for (AsyncCall asyncCall : this.readyAsyncCalls) {
                arrayList.add(asyncCall.get());
            }
            unmodifiableList = Collections.unmodifiableList(arrayList);
        }
        return unmodifiableList;
    }

    public final int queuedCallsCount() {
        int size;
        synchronized (this) {
            size = this.readyAsyncCalls.size();
        }
        return size;
    }

    public final List<Call> runningCalls() {
        List<Call> unmodifiableList;
        synchronized (this) {
            List arrayList = new ArrayList();
            arrayList.addAll(this.runningSyncCalls);
            for (AsyncCall asyncCall : this.runningAsyncCalls) {
                arrayList.add(asyncCall.get());
            }
            unmodifiableList = Collections.unmodifiableList(arrayList);
        }
        return unmodifiableList;
    }

    public final int runningCallsCount() {
        int size;
        int size2;
        synchronized (this) {
            size = this.runningAsyncCalls.size();
            size2 = this.runningSyncCalls.size();
        }
        return size + size2;
    }

    public final void setMaxRequests(int i) {
        synchronized (this) {
            if (i <= 0) {
                throw new IllegalArgumentException("max < 1: " + i);
            }
            this.maxRequests = i;
            promoteCalls();
        }
    }

    public final void setMaxRequestsPerHost(int i) {
        synchronized (this) {
            if (i <= 0) {
                throw new IllegalArgumentException("max < 1: " + i);
            }
            this.maxRequestsPerHost = i;
            promoteCalls();
        }
    }
}
