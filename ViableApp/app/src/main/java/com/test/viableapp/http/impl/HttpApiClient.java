package com.test.viableapp.http.impl;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HttpApiClient {

    private String baseUrl = "http://randomuser.me/";
    private static HttpApiClient INSTANCE;
    private HttpApiService service;

    public static HttpApiClient instance() {
        if (INSTANCE == null) {
            INSTANCE = new HttpApiClient();
        }
        return INSTANCE;
    }

    public HttpApiClient() {
        service = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(new OkHttpClient.Builder()
                        .addInterceptor(new LoggerInterceptor()).build())
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(HttpApiService.class);
    }

    protected HttpApiService getClient() {
        return service;
    }
}
