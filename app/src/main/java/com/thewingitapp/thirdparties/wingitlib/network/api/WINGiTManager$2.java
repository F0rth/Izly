package com.thewingitapp.thirdparties.wingitlib.network.api;

import com.thewingitapp.thirdparties.wingitlib.model.WGEvent;
import com.thewingitapp.thirdparties.wingitlib.model.WGTimelineEvents;
import retrofit2.Response;
import rx.Subscriber;

final class WINGiTManager$2 extends WINGiTManager$DefaultSubscriber<WGTimelineEvents> {
    WINGiTManager$2(Subscriber subscriber) {
        super(subscriber);
    }

    public final void onNext(WGTimelineEvents wGTimelineEvents) {
        for (WGEvent wGEvent : wGTimelineEvents.getEvents()) {
            if (wGEvent.isPromoted().booleanValue()) {
                WINGiTManager.access$100(wGEvent, new Subscriber<Response<Void>>() {
                    public void onCompleted() {
                    }

                    public void onError(Throwable th) {
                        th.printStackTrace();
                    }

                    public void onNext(Response<Void> response) {
                    }
                });
            }
        }
        super.onNext(wGTimelineEvents);
    }
}
