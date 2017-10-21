package com.thewingitapp.thirdparties.wingitlib.model;

public class WGCountry extends WGCountryDTO {
    public Integer getCountryId() {
        return this.countryId;
    }

    public String getLocalizedName() {
        return this.defaultName;
    }

    public String getName() {
        return this.countryName;
    }
}
