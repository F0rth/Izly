package com.thewingitapp.thirdparties.wingitlib.network.api;

import com.thewingitapp.thirdparties.wingitlib.WINGiTDataHolder;
import com.thewingitapp.thirdparties.wingitlib.model.WGStartupConfig;
import com.thewingitapp.thirdparties.wingitlib.network.security.WINGiTTrustManager;
import java.util.StringTokenizer;
import retrofit2.Response;
import retrofit2.adapter.rxjava.HttpException;
import rx.Subscriber;

final class WINGiTManager$1 extends Subscriber<Response<WGStartupConfig>> {
    final /* synthetic */ Subscriber val$subscriber;

    WINGiTManager$1(Subscriber subscriber) {
        this.val$subscriber = subscriber;
    }

    public final void onCompleted() {
        if (this.val$subscriber != null) {
            this.val$subscriber.onCompleted();
        }
    }

    public final void onError(Throwable th) {
        if (this.val$subscriber != null) {
            this.val$subscriber.onError(WINGiTManager.access$000(th));
        }
    }

    public final void onNext(Response<WGStartupConfig> response) {
        if (response.isSuccessful()) {
            String str = response.headers().get("Public-Key-Pins");
            if (str != null) {
                StringTokenizer stringTokenizer = new StringTokenizer(str, ";");
                StringTokenizer stringTokenizer2 = new StringTokenizer(stringTokenizer.nextToken(), "\"");
                stringTokenizer2.nextToken();
                str = new StringTokenizer(stringTokenizer2.nextToken(), "\"").nextToken();
                new StringTokenizer(stringTokenizer.nextToken(), "\"").nextToken();
                WINGiTTrustManager.updateInstance(new String[]{str, new StringTokenizer(r2.nextToken(), "\"").nextToken()});
            }
            WINGiTDataHolder.setCategories(((WGStartupConfig) response.body()).getCategories());
            WINGiTDataHolder.setCities(((WGStartupConfig) response.body()).getCities());
            WINGiTDataHolder.setCountries(((WGStartupConfig) response.body()).getCountries());
            WINGiTDataHolder.setDownloadPage(((WGStartupConfig) response.body()).getDownloadPage());
            if (this.val$subscriber != null) {
                this.val$subscriber.onNext(response.body());
            }
        } else if (this.val$subscriber != null) {
            this.val$subscriber.onError(WINGiTManager.access$000(new HttpException(response)));
        }
    }
}
