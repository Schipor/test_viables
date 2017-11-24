package com.test.viableapp.http.impl;

import com.test.viableapp.http.models.HttpError;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public abstract class HttpCallback<T> implements Callback<T> {

    public abstract void onSuccess(T response);

    public abstract void onError(HttpError error);

    @Override
    public void onResponse(Call<T> call, Response<T> response) {

        if (response.isSuccessful()) {
            //send callback
            onSuccess(response.body());
        } else {
            //handle error
            try {
                onError(new HttpError(response.errorBody().string()));
            } catch (IOException e) {
                // something went wrong -- send generic error
                onError(new HttpError());
            }
        }

    }

    @Override
    public void onFailure(Call<T> call, Throwable t) {
        onError(new HttpError());
    }
}
