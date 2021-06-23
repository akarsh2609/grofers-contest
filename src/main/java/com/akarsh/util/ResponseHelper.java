package com.akarsh.util;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ResponseHelper {

    private String message;
    private boolean isError;

    public ResponseHelper() {
    }

    ResponseHelper(String message, boolean isError) {
        this.message = message;
        this.isError = isError;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isError() {
        return isError;
    }

    public void setError(boolean error) {
        isError = error;
    }


    public static ResponseHelper getSuccessResponse(String message, boolean isError) {
        return new ResponseHelper(message, isError);
    }

    public static ResponseEntity<ResponseHelper> getErrorResponse(String message, HttpStatus httpStatus) {
        return ResponseEntity.status(httpStatus).body(getSuccessResponse(message, true));
    }
}
