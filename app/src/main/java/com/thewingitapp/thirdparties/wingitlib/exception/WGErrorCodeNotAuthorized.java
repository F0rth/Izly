package com.thewingitapp.thirdparties.wingitlib.exception;

public class WGErrorCodeNotAuthorized extends WGException {
    public String getMessage() {
        return "You are told that you are not authorized to access this resource";
    }
}
