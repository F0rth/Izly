package com.thewingitapp.thirdparties.wingitlib.model;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

class WGCityDTO implements Serializable {
    @SerializedName("cityId")
    Integer cityId;
    @SerializedName("cityName")
    String cityName;
    @SerializedName("countryId")
    Long countryId;
    @SerializedName("countryName")
    String countryName;
    @SerializedName("defaultName")
    String defaultName;
    @SerializedName("defaultUnit")
    String defaultUnit;

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        return this.cityId.equals(((WGCityDTO) obj).cityId);
    }

    public int hashCode() {
        return this.cityId.hashCode();
    }
}
