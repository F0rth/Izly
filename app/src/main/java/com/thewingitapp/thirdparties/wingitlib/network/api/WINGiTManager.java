package com.thewingitapp.thirdparties.wingitlib.network.api;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import com.thewingitapp.thirdparties.wingitlib.WINGiTConstant;
import com.thewingitapp.thirdparties.wingitlib.WINGiTTrackers;
import com.thewingitapp.thirdparties.wingitlib.exception.WGErrorCodeNotAuthorized;
import com.thewingitapp.thirdparties.wingitlib.exception.WGErrorCodeNotInitialized;
import com.thewingitapp.thirdparties.wingitlib.exception.WGErrorTimeout;
import com.thewingitapp.thirdparties.wingitlib.model.WGCity;
import com.thewingitapp.thirdparties.wingitlib.model.WGCountry;
import com.thewingitapp.thirdparties.wingitlib.model.WGDetectedCity;
import com.thewingitapp.thirdparties.wingitlib.model.WGEvent;
import com.thewingitapp.thirdparties.wingitlib.model.WGTimelineEvents;
import com.thewingitapp.thirdparties.wingitlib.model.WGTimelineParameters;
import com.thewingitapp.thirdparties.wingitlib.model.trackers.WGTrack;
import com.thewingitapp.thirdparties.wingitlib.model.trackers.WGTrackActionData;
import com.thewingitapp.thirdparties.wingitlib.model.trackers.WGTrackCityData;
import com.thewingitapp.thirdparties.wingitlib.network.WINGiTAPIService;
import java.net.SocketTimeoutException;
import java.util.UUID;
import retrofit2.Response;
import retrofit2.adapter.rxjava.HttpException;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public final class WINGiTManager {
    private static String sID = null;

    private static final String generateAndStoreUserId(SharedPreferences sharedPreferences) {
        synchronized (WINGiTManager.class) {
            Editor edit;
            try {
                String uuid = UUID.randomUUID().toString();
                edit = sharedPreferences.edit();
                edit.putString("UserId", uuid);
                edit.commit();
                return uuid;
            } finally {
                edit = WINGiTManager.class;
            }
        }
    }

    private static final Throwable generateError(Throwable th) {
        return th instanceof HttpException ? ((HttpException) th).code() == 401 ? new WGErrorCodeNotInitialized() : ((HttpException) th).code() == 403 ? new WGErrorCodeNotAuthorized() : th : th instanceof SocketTimeoutException ? new WGErrorTimeout() : th;
    }

    public static final void getEventsForCity(@NonNull WGCity wGCity, @NonNull WGTimelineParameters wGTimelineParameters, @Nullable Boolean bool, @Nullable Integer num, @NonNull Subscriber<WGTimelineEvents> subscriber) {
        if (wGCity != null && wGCity.getCityId() != null && wGTimelineParameters != null) {
            Double d = wGTimelineParameters.lat;
            Double d2 = wGTimelineParameters.lng;
            WINGiTAPIService.getSSLService().eventsForCity(wGCity.getCityId(), d, d2, wGTimelineParameters.radius, wGTimelineParameters.sortType.toString(), wGTimelineParameters.getCategoryIds(), bool, null, num, Integer.valueOf(wGTimelineParameters.offsetDay.getDay())).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).unsubscribeOn(Schedulers.io()).subscribe(new 2(subscriber));
        } else if (subscriber != null) {
            subscriber.onError(new IllegalArgumentException());
        }
    }

    public static final void getEventsForCountry(@NonNull WGCountry wGCountry, @NonNull WGTimelineParameters wGTimelineParameters, @Nullable Boolean bool, @Nullable Integer num, @NonNull Subscriber<WGTimelineEvents> subscriber) {
        if (wGCountry != null && wGTimelineParameters != null) {
            Double d = wGTimelineParameters.lat;
            Double d2 = wGTimelineParameters.lng;
            WINGiTAPIService.getSSLService().eventsForCountry(wGCountry.getCountryId(), d, d2, wGTimelineParameters.radius, wGTimelineParameters.sortType.toString(), wGTimelineParameters.getCategoryIds(), bool, null, num, Integer.valueOf(wGTimelineParameters.offsetDay.getDay())).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).unsubscribeOn(Schedulers.io()).subscribe(new 3(subscriber));
        } else if (subscriber != null) {
            subscriber.onError(new IllegalArgumentException());
        }
    }

    public static final void getNearestSupportedCity(double d, double d2, @Nullable Subscriber<WGDetectedCity> subscriber) {
        WINGiTAPIService.getSSLService().detectCity(d, d2).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).unsubscribeOn(Schedulers.io()).subscribe(new DefaultSubscriber(subscriber));
    }

    public static final String getUserId(Context context) {
        synchronized (WINGiTManager.class) {
            Class isEmpty;
            try {
                if (sID == null) {
                    SharedPreferences sharedPreferences = context.getSharedPreferences("WGT-PREF", 0);
                    CharSequence string = sharedPreferences.getString("UserId", "");
                    sID = string;
                    isEmpty = TextUtils.isEmpty(string);
                    if (isEmpty != null) {
                        sID = generateAndStoreUserId(sharedPreferences);
                    }
                }
                String str = sID;
                return str;
            } finally {
                isEmpty = WINGiTManager.class;
            }
        }
    }

    public static final void initialize(String str, String str2, @NonNull Context context, @Nullable Subscriber subscriber) {
        WINGiTConstant.WINGiT_APP_ID = str;
        WINGiTConstant.WINGiT_APP_SECRET = str2;
        WINGiTConstant.WINGiT_USER_ID = getUserId(context);
        WINGiTTrackers.clearAllDisplayTrackers();
        WINGiTAPIService.getService().startup().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).unsubscribeOn(Schedulers.io()).subscribe(new 1(subscriber));
    }

    public static final void trackDisplay(@NonNull String str, @Nullable Subscriber<Response<Void>> subscriber) {
        if (!WINGiTTrackers.isDisplayTrackerShown(str)) {
            WINGiTTrackers.addDisplayTracker(str);
            IWINGiTAPI sSLService = WINGiTAPIService.getSSLService();
            WGTrack wGTrack = new WGTrack();
            wGTrack.setUserId(WINGiTConstant.WINGiT_USER_ID);
            wGTrack.setURI("https://thirdparties.thewingitapp.com/api/v2/display/" + str);
            sSLService.track(wGTrack).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).unsubscribeOn(Schedulers.io()).subscribe(new DefaultSubscriber(subscriber));
        }
    }

    public static final void trackDownload(@Nullable Subscriber<Response<Void>> subscriber) {
        IWINGiTAPI sSLService = WINGiTAPIService.getSSLService();
        WGTrack wGTrack = new WGTrack();
        wGTrack.setUserId(WINGiTConstant.WINGiT_USER_ID);
        wGTrack.setURI("https://thirdparties.thewingitapp.com/api/v2/download");
        sSLService.track(wGTrack).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).unsubscribeOn(Schedulers.io()).subscribe(new DefaultSubscriber(subscriber));
    }

    public static final void trackEvent(@NonNull WGEvent wGEvent, @Nullable Subscriber<Response<Void>> subscriber) {
        IWINGiTAPI sSLService = WINGiTAPIService.getSSLService();
        WGTrack wGTrack = new WGTrack();
        wGTrack.setUserId(WINGiTConstant.WINGiT_USER_ID);
        wGTrack.setURI("https://thirdparties.thewingitapp.com/api/v2/events/" + wGEvent.getEventId());
        WGTrackActionData wGTrackActionData = new WGTrackActionData();
        wGTrackActionData.setAction("View");
        wGTrack.setData(wGTrackActionData.getJsonString());
        sSLService.track(wGTrack).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).unsubscribeOn(Schedulers.io()).subscribe(new DefaultSubscriber(subscriber));
    }

    public static final void trackPush(@NonNull String str, @Nullable Subscriber<Response<Void>> subscriber) {
        if (!TextUtils.isEmpty(str)) {
            IWINGiTAPI sSLService = WINGiTAPIService.getSSLService();
            WGTrack wGTrack = new WGTrack();
            wGTrack.setUserId(WINGiTConstant.WINGiT_USER_ID);
            wGTrack.setURI("https://thirdparties.thewingitapp.com/api/v2/push/" + str);
            WGTrackCityData wGTrackCityData = new WGTrackCityData();
            wGTrackCityData.setCityname(str);
            wGTrack.setData(wGTrackCityData.getJsonString());
            sSLService.track(wGTrack).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).unsubscribeOn(Schedulers.io()).subscribe(new DefaultSubscriber(subscriber));
        } else if (subscriber != null) {
            subscriber.onError(new IllegalArgumentException());
        }
    }

    private static final void trackSponsoredEvent(@NonNull WGEvent wGEvent, @Nullable Subscriber<Response<Void>> subscriber) {
        IWINGiTAPI sSLService = WINGiTAPIService.getSSLService();
        WGTrack wGTrack = new WGTrack();
        wGTrack.setUserId(WINGiTConstant.WINGiT_USER_ID);
        wGTrack.setURI("https://thirdparties.thewingitapp.com/api/v2/displaysponsored/" + wGEvent.getEventId());
        sSLService.track(wGTrack).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).unsubscribeOn(Schedulers.io()).subscribe(new DefaultSubscriber(subscriber));
    }

    public static final void trackTicket(@NonNull WGEvent wGEvent, @Nullable Subscriber<Response<Void>> subscriber) {
        IWINGiTAPI sSLService = WINGiTAPIService.getSSLService();
        WGTrack wGTrack = new WGTrack();
        wGTrack.setUserId(WINGiTConstant.WINGiT_USER_ID);
        wGTrack.setURI("https://thirdparties.thewingitapp.com/api/v2/events/" + wGEvent.getEventId() + "/ticket");
        WGTrackActionData wGTrackActionData = new WGTrackActionData();
        wGTrackActionData.setAction("Open");
        wGTrackActionData.setProviderId(String.valueOf(wGEvent.getProviderId()));
        wGTrack.setData(wGTrackActionData.getJsonString());
        sSLService.track(wGTrack).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).unsubscribeOn(Schedulers.io()).subscribe(new DefaultSubscriber(subscriber));
    }
}
