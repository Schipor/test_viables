package com.test.viableapp.http.impl;

import android.util.Log;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;


public class LoggerInterceptor implements Interceptor {

    private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy'-'MM'-'dd' 'HH':'mm':'ss'.'SSS");
    private final String LOG_TAG_RESPONSE = "Server Response ";
    private final String LOG_TAG_REQUEST = "Request ";
    private final String PRINT_RESPONSE_FORMAT = "Code %s, %s";

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request originalRequest = chain.request();
        Response originalResponse = chain.proceed(originalRequest);
        ResponseBody responseBody = originalResponse.body();
        String responseBodyString = originalResponse.body().string();
        logRequest(originalRequest);
        logResponse(originalResponse, responseBodyString);
        return originalResponse.newBuilder().body(ResponseBody.create(responseBody.contentType(), responseBodyString.getBytes())).build();
    }

    private void logResponse(Response originalResponse, String responseString) {
        //// TODO: 24/11/2017 Disable logs on release
        Log.w(LOG_TAG_RESPONSE + dateFormat.format(new Date()), String.format(PRINT_RESPONSE_FORMAT, originalResponse.code(), originalResponse.message()));
        Log.w(LOG_TAG_RESPONSE + dateFormat.format(new Date()), responseString);
    }

    private void logRequest(Request original) {
        //// TODO: 24/11/2017 Disable logs on release
        Log.w(LOG_TAG_REQUEST, original.url().toString());
        Log.w(LOG_TAG_REQUEST, original.headers().toString());
        Log.w(LOG_TAG_REQUEST, original.method());

        Buffer buffer = new Buffer();
        try {
            RequestBody body = original.body();
            if (body != null) {
                body.writeTo(buffer);
            }
            Log.w(LOG_TAG_REQUEST, buffer.readUtf8());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
