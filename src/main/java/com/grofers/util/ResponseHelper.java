package com.grofers.util;

public class ResponseHelper {

    private String message;
    private boolean isError;

    public static ResponseHelper getInstance() {
        return new ResponseHelper();
    }

    public ResponseHelper() {
    }

    ;

    ResponseHelper(String message, boolean isError) {
        this.message = message;
        this.isError = isError;
    }

    public ResponseHelper getResponse(String message, boolean isError) {
        return new ResponseHelper(message, isError);
    }
}
