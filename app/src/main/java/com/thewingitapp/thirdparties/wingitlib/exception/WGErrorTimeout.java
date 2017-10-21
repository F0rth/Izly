package com.thewingitapp.thirdparties.wingitlib.exception;

public class WGErrorTimeout extends WGException {
    public String getMessage() {
        return "Your network is unavailable";
    }
}
