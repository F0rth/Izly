package com.thewingitapp.thirdparties.wingitlib.exception;

public class WGErrorCodeUnknowCountry extends WGException {
    public String getMessage() {
        return "You are told that the specified country is unknown";
    }
}
