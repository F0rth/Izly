package com.ezeeworld.b4s.android.sdk.server;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Converter.Factory;
import retrofit2.Retrofit;

class a {
    private static final MediaType b = MediaType.parse("application/plain; charset=UTF-8");
    public String a;

    static class a extends Factory {
        a() {
        }

        public Converter<?, RequestBody> requestBodyConverter(Type type, Annotation[] annotationArr, Annotation[] annotationArr2, Retrofit retrofit) {
            return a.class.equals(type) ? new Converter<a, RequestBody>(this) {
                final /* synthetic */ a a;

                {
                    this.a = r1;
                }

                public RequestBody a(a aVar) throws IOException {
                    return RequestBody.create(a.b, aVar.a);
                }

                public /* synthetic */ Object convert(Object obj) throws IOException {
                    return a((a) obj);
                }
            } : null;
        }

        public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotationArr, Retrofit retrofit) {
            return a.class.equals(type) ? new Converter<ResponseBody, a>(this) {
                final /* synthetic */ a a;

                {
                    this.a = r1;
                }

                public a a(ResponseBody responseBody) throws IOException {
                    return new a(responseBody.string());
                }

                public /* synthetic */ Object convert(Object obj) throws IOException {
                    return a((ResponseBody) obj);
                }
            } : null;
        }
    }

    public a(String str) {
        this.a = str;
    }
}
