package com.test.viableapp.http.impl;

import com.test.viableapp.http.models.RandomUser;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface HttpApiService {

    @GET("api")
    Call<RandomUser.Response> getUsers(
            @Query("page") int page,
            @Query("results") int perPage
    );
}
