package com.thewingitapp.thirdparties.wingitlib.model;

import com.thewingitapp.thirdparties.wingitlib.WINGiTDataHolder;
import com.thewingitapp.thirdparties.wingitlib.exception.WGErrorCodeUnknownCity;

public class WGPlace extends WGPlaceDTO {
    private WGCity getCity() throws WGErrorCodeUnknownCity {
        return WINGiTDataHolder.getCity(this.cityId.intValue());
    }

    public String getAddress() {
        return this.address;
    }

    public String getCityName() throws WGErrorCodeUnknownCity {
        return getCity().getName();
    }

    public Double getLat() {
        return this.lat;
    }

    public Double getLon() {
        return this.lon;
    }

    public String getName() {
        return this.name;
    }

    public boolean useMiles() {
        try {
            return getCity().useMiles().booleanValue();
        } catch (WGErrorCodeUnknownCity e) {
            e.printStackTrace();
            return false;
        }
    }
}
