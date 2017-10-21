package com.thewingitapp.thirdparties.wingitlib.model;

public class WGDetectedCity extends WGDetectedCityDTO {
    public Double getDistance() {
        return this.distance;
    }

    public Boolean isInsideCity() {
        return this.insideCity;
    }
}
