package rx;

import rx.functions.Func0;

class Completable$32 implements Func0<T> {
    final /* synthetic */ Completable this$0;
    final /* synthetic */ Object val$completionValue;

    Completable$32(Completable completable, Object obj) {
        this.this$0 = completable;
        this.val$completionValue = obj;
    }

    public T call() {
        return this.val$completionValue;
    }
}
