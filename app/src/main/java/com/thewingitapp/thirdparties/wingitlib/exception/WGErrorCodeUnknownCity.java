package com.thewingitapp.thirdparties.wingitlib.exception;

public class WGErrorCodeUnknownCity extends WGException {
    public String getMessage() {
        return "You are told that the specified city is unknown";
    }
}
