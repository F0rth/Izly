package com.thewingitapp.thirdparties.wingitlib.network.api;

import android.support.annotation.Nullable;
import rx.Subscriber;

class WINGiTManager$DefaultSubscriber<T> extends Subscriber<T> {
    final Subscriber<T> subscriber;

    public WINGiTManager$DefaultSubscriber(@Nullable Subscriber<T> subscriber) {
        this.subscriber = subscriber;
    }

    public void onCompleted() {
        if (this.subscriber != null) {
            this.subscriber.onCompleted();
        }
    }

    public void onError(Throwable th) {
        if (this.subscriber != null) {
            this.subscriber.onError(WINGiTManager.access$000(th));
        }
    }

    public void onNext(T t) {
        if (this.subscriber != null) {
            this.subscriber.onNext(t);
        }
    }
}
