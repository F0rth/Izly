package com.thewingitapp.thirdparties.wingitlib.model;

public class WGCity extends WGCityDTO {
    public /* bridge */ /* synthetic */ boolean equals(Object obj) {
        return super.equals(obj);
    }

    public Integer getCityId() {
        return this.cityId;
    }

    public Long getCountryId() {
        return this.countryId;
    }

    public String getCountryLocalizedName() {
        return this.countryName;
    }

    public String getLocalizedName() {
        return this.defaultName;
    }

    public String getName() {
        return this.cityName;
    }

    public /* bridge */ /* synthetic */ int hashCode() {
        return super.hashCode();
    }

    public Boolean useMiles() {
        return Boolean.valueOf(this.defaultUnit.equals("Imperial"));
    }
}
