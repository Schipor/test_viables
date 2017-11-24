package com.test.viableapp.http.impl;

import com.test.viableapp.http.models.RandomUser;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * @author Sebastian Schipor <sebastian.schipor@osf-global.com> on 24/11/2017.
 */

public interface HttpApiService {

    @GET("api")
    Call<RandomUser.Response> getUsers(
            @Query("page") int page,
            @Query("results") int perPage
    );
}
