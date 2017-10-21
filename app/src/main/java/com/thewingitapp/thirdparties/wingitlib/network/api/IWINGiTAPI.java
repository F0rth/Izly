package com.thewingitapp.thirdparties.wingitlib.network.api;

import com.thewingitapp.thirdparties.wingitlib.model.WGDetectedCity;
import com.thewingitapp.thirdparties.wingitlib.model.WGStartupConfig;
import com.thewingitapp.thirdparties.wingitlib.model.WGTimelineEvents;
import com.thewingitapp.thirdparties.wingitlib.model.trackers.WGTrack;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

public interface IWINGiTAPI {
    @GET("api/v2/Cities/Detect")
    Observable<WGDetectedCity> detectCity(@Query("lat") double d, @Query("lon") double d2);

    @GET("api/v2/Cities/{cityId}/Events")
    Observable<WGTimelineEvents> eventsForCity(@Path("cityId") Integer num, @Query("lat") Double d, @Query("lon") Double d2, @Query("radius") Integer num2, @Query("sortBy") String str, @Query("categoryIds") String str2, @Query("promoted") Boolean bool, @Query("limit") Integer num3, @Query("offset") Integer num4, @Query("offsetDay") Integer num5);

    @GET("api/v2/Countries/{countryId}/Events")
    Observable<WGTimelineEvents> eventsForCountry(@Path("countryId") Integer num, @Query("lat") Double d, @Query("lon") Double d2, @Query("radius") Integer num2, @Query("sortBy") String str, @Query("categoryIds") String str2, @Query("promoted") Boolean bool, @Query("limit") Integer num3, @Query("offset") Integer num4, @Query("offsetDay") Integer num5);

    @GET("api/v2/Startup")
    Observable<Response<WGStartupConfig>> startup();

    @POST("api/v2/Track")
    Observable<Response<Void>> track(@Body WGTrack wGTrack);
}
