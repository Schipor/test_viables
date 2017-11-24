package com.test.viableapp.http;

import com.test.viableapp.http.impl.HttpApiClient;
import com.test.viableapp.http.impl.HttpCallback;
import com.test.viableapp.http.models.RandomUser;

import retrofit2.Call;

public class ApiManager extends HttpApiClient {

    private static ApiManager INSTANCE;

    public static ApiManager instance() {
        if (INSTANCE == null) {
            INSTANCE = new ApiManager();
        }
        return INSTANCE;
    }

    public ApiManager() {
        super();
    }

    public void getUsers(int page, int perPage, HttpCallback<RandomUser.Response> callback) {
        Call<RandomUser.Response> call = getClient().getUsers(page, perPage);
        call.enqueue(callback);
    }
}
