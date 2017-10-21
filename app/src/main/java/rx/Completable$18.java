package rx;

import rx.functions.Action0;
import rx.functions.Action1;

class Completable$18 implements Action1<Throwable> {
    final /* synthetic */ Completable this$0;
    final /* synthetic */ Action0 val$onTerminate;

    Completable$18(Completable completable, Action0 action0) {
        this.this$0 = completable;
        this.val$onTerminate = action0;
    }

    public void call(Throwable th) {
        this.val$onTerminate.call();
    }
}
