package com.test.viableapp.http.impl;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author Sebastian Schipor <sebastian.schipor@osf-global.com> on 24/11/2017.
 */

public class HttpApiClient {

    private String baseUrl = "http://randomuser.me/api";
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
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(HttpApiService.class);
    }

    public HttpApiService getClient() {
        return service;
    }
}
