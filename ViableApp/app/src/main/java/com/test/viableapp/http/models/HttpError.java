package com.test.viableapp.http.models;


public class HttpError {

    private String errorMessage = "Something went wrong. Please try again later.";

    public HttpError() {
        //the error response model is unknown -- created basic error message

    }

    public HttpError(String errorBody) {
        //the error response model is unknown -- created basic error message
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
