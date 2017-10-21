package rx;

class Completable$21 implements Completable$CompletableOnSubscribe {
    final /* synthetic */ Completable this$0;
    final /* synthetic */ Completable$CompletableOperator val$onLift;

    Completable$21(Completable completable, Completable$CompletableOperator completable$CompletableOperator) {
        this.this$0 = completable;
        this.val$onLift = completable$CompletableOperator;
    }

    public void call(Completable$CompletableSubscriber completable$CompletableSubscriber) {
        NullPointerException e;
        try {
            this.this$0.subscribe((Completable$CompletableSubscriber) this.val$onLift.call(completable$CompletableSubscriber));
        } catch (NullPointerException e2) {
            throw e2;
        } catch (Throwable th) {
            e2 = Completable.toNpe(th);
        }
    }
}
