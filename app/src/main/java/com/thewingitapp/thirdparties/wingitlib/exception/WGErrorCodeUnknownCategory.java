package com.thewingitapp.thirdparties.wingitlib.exception;

public class WGErrorCodeUnknownCategory extends WGException {
    public String getMessage() {
        return "You are told that the specified category is unknown";
    }
}
