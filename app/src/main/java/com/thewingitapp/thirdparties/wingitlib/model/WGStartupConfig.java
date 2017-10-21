package com.thewingitapp.thirdparties.wingitlib.model;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;
import java.util.List;

public class WGStartupConfig implements Serializable {
    @SerializedName("categories")
    List<WGCategory> mCategories;
    @SerializedName("cities")
    List<WGCity> mCities;
    @SerializedName("countries")
    List<WGCountry> mCountries;
    @SerializedName("downloadPage")
    WGDownloadPage mDownloadPage;

    public List<WGCategory> getCategories() {
        return this.mCategories;
    }

    public List<WGCity> getCities() {
        return this.mCities;
    }

    public List<WGCountry> getCountries() {
        return this.mCountries;
    }

    public WGDownloadPage getDownloadPage() {
        return this.mDownloadPage;
    }
}
