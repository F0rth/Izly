package rx.android.schedulers;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import java.util.concurrent.TimeUnit;
import rx.Scheduler;
import rx.Scheduler.Worker;
import rx.Subscription;
import rx.android.plugins.RxAndroidPlugins;
import rx.android.plugins.RxAndroidSchedulersHook;
import rx.exceptions.OnErrorNotImplementedException;
import rx.functions.Action0;
import rx.plugins.RxJavaPlugins;
import rx.subscriptions.Subscriptions;

class LooperScheduler extends Scheduler {
    private final Handler handler;

    static class HandlerWorker extends Worker {
        private final Handler handler;
        private final RxAndroidSchedulersHook hook = RxAndroidPlugins.getInstance().getSchedulersHook();
        private volatile boolean unsubscribed;

        HandlerWorker(Handler handler) {
            this.handler = handler;
        }

        public boolean isUnsubscribed() {
            return this.unsubscribed;
        }

        public Subscription schedule(Action0 action0) {
            return schedule(action0, 0, TimeUnit.MILLISECONDS);
        }

        public Subscription schedule(Action0 action0, long j, TimeUnit timeUnit) {
            if (this.unsubscribed) {
                return Subscriptions.unsubscribed();
            }
            Runnable scheduledAction = new ScheduledAction(this.hook.onSchedule(action0), this.handler);
            Message obtain = Message.obtain(this.handler, scheduledAction);
            obtain.obj = this;
            this.handler.sendMessageDelayed(obtain, timeUnit.toMillis(j));
            if (!this.unsubscribed) {
                return scheduledAction;
            }
            this.handler.removeCallbacks(scheduledAction);
            return Subscriptions.unsubscribed();
        }

        public void unsubscribe() {
            this.unsubscribed = true;
            this.handler.removeCallbacksAndMessages(this);
        }
    }

    static final class ScheduledAction implements Runnable, Subscription {
        private final Action0 action;
        private final Handler handler;
        private volatile boolean unsubscribed;

        ScheduledAction(Action0 action0, Handler handler) {
            this.action = action0;
            this.handler = handler;
        }

        public final boolean isUnsubscribed() {
            return this.unsubscribed;
        }

        public final void run() {
            try {
                this.action.call();
            } catch (Throwable th) {
                Throwable th2 = th;
                Throwable th3 = th2 instanceof OnErrorNotImplementedException ? new IllegalStateException("Exception thrown on Scheduler.Worker thread. Add `onError` handling.", th2) : new IllegalStateException("Fatal Exception thrown on Scheduler.Worker thread.", th2);
                RxJavaPlugins.getInstance().getErrorHandler().handleError(th3);
                Thread currentThread = Thread.currentThread();
                currentThread.getUncaughtExceptionHandler().uncaughtException(currentThread, th3);
            }
        }

        public final void unsubscribe() {
            this.unsubscribed = true;
            this.handler.removeCallbacks(this);
        }
    }

    LooperScheduler(Handler handler) {
        this.handler = handler;
    }

    LooperScheduler(Looper looper) {
        this.handler = new Handler(looper);
    }

    public Worker createWorker() {
        return new HandlerWorker(this.handler);
    }
}
