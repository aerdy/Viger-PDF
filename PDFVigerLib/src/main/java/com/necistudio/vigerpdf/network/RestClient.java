package com.necistudio.vigerpdf.network;


import android.content.Context;
import android.util.Log;


import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Streaming;
import retrofit2.http.Url;


public class RestClient  {

    private static ApiInterface ApiInterface;

    public static ApiInterface getClient() {
        if (ApiInterface == null) {
            OkHttpClient okclient = new OkHttpClient.Builder()
                    .readTimeout(30, TimeUnit.SECONDS)
                    .connectTimeout(50,TimeUnit.SECONDS)
                    .addInterceptor(new Interceptor() {
                        @Override
                        public Response intercept(Chain chain) throws IOException {
                            Request request = chain.request().newBuilder()
                                    .addHeader("Accept", "Application/JSON").build();
                            return chain.proceed(request);
                        }
                    })
                    .build();

            Retrofit client = new Retrofit.Builder()
                    .baseUrl("http://necistudio.com")
                    .client(okclient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            ApiInterface = client.create(ApiInterface.class);

        }
        return ApiInterface;
    }


    public interface ApiInterface {
        @Streaming
        @GET
        Call<ResponseBody> streamFile(@Url String fileUrl);
    }


}

