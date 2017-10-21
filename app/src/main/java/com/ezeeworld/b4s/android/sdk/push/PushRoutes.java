package com.ezeeworld.b4s.android.sdk.push;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

interface PushRoutes {
    @POST("api/v1/sdk/enableRemoteNotifications")
    Call<Void> registerToken(@Body PushRegistration pushRegistration);
}
