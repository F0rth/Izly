package com.ezeeworld.b4s.android.sdk.positioning.server;

import com.ezeeworld.b4s.android.sdk.positioning.server.IndoorMap.Request;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

interface PositioningRoutes {
    @POST("http://customerwebs.beaconforstore.com/indoor-tracking/v1/map")
    @Headers({"x-b4s-call-group: live"})
    Call<IndoorMap> getIndoorMap(@Body Request request);

    @POST("http://customerwebs.beaconforstore.com/indoor-tracking/v1/track")
    @Headers({"x-b4s-call-group: tracking"})
    Call<Void> trackUserInZone(@Body TrackUserInZone trackUserInZone);
}
