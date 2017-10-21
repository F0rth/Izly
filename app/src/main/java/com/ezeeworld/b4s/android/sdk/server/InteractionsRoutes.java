package com.ezeeworld.b4s.android.sdk.server;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

interface InteractionsRoutes {
    @POST("api/v1/sdk/appInfos")
    @Headers({"x-b4s-call-group: cached"})
    Call<AppInfoWrapper> getAppInfos(@Body Request request);

    @POST("api/v2/sdk/environmentParams")
    @Headers({"x-b4s-call-group: cached"})
    Call<EnvironmentInfo> getEnvironmentInfo(@Body Request request);

    @POST("api/v2/sdk/interactionsV2")
    @Headers({"x-b4s-call-group: cached"})
    Call<SpotWrapper> getInteractionsSpot(@Body Request request);

    @POST("api/v1/sdk/nearestShops")
    @Headers({"x-b4s-call-group: cached"})
    Call<List<NearbyShop>> getNearbyShops(@Body Request request);

    @POST("api/v1/sdk/manualTag")
    @Headers({"x-b4s-call-group: tracking"})
    Call<Void> manualTag(@Body ManualTag manualTag);

    @POST("sdk/v6/devices/properties")
    @Headers({"x-b4s-call-group: tracking"})
    Call<a> registerProperties(@Body Properties properties);

    @POST("api/v1/sdk/newSession")
    @Headers({"x-b4s-call-group: sessions"})
    Call<Void> registerSession(@Body SessionRegistration sessionRegistration);

    @POST("api/v1/sdk/{logLevel}")
    @Headers({"x-b4s-call-group: logging"})
    Call<Void> remoteLog(@Path("logLevel") String str, @Body RemoteLog remoteLog);

    @POST("sdk/v6/counting/coordinates")
    @Headers({"x-b4s-call-group: tracking"})
    Call<Void> sendCoordinates(@Body TrackingCoordinates trackingCoordinates);

    @POST("api/v1/sdk/tracking")
    @Headers({"x-b4s-call-group: tracking"})
    Call<a> trackBeacons(@Body TrackingBeacons trackingBeacons);

    @POST("api/v1/sdk/registerCustomer")
    @Headers({"x-b4s-call-group: tracking"})
    Call<a> trackDevice(@Body TrackingUser trackingUser);

    @POST("api/v1/sdk/setSessionStatus")
    @Headers({"x-b4s-call-group: sessions"})
    Call<Void> updateSession(@Body SessionUpdate sessionUpdate);
}
