package com.thewingitapp.thirdparties.wingitlib.model;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

public class WGCountryDTO implements Serializable {
    @SerializedName("countryId")
    public Integer countryId;
    @SerializedName("countryName")
    public String countryName;
    @SerializedName("defaultName")
    public String defaultName;
}
