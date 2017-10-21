package com.thewingitapp.thirdparties.wingitlib.exception;

public class WGErrorCodeNotInitialized extends WGException {
    public String getMessage() {
        return "You have tried to use the WINGiT manager without initializing it [or the initialization has failed]";
    }
}
