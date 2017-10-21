package com.thewingitapp.thirdparties.wingitlib;

import com.thewingitapp.thirdparties.wingitlib.exception.WGErrorCodeUnknowCountry;
import com.thewingitapp.thirdparties.wingitlib.exception.WGErrorCodeUnknownCategory;
import com.thewingitapp.thirdparties.wingitlib.exception.WGErrorCodeUnknownCity;
import com.thewingitapp.thirdparties.wingitlib.model.WGCategory;
import com.thewingitapp.thirdparties.wingitlib.model.WGCity;
import com.thewingitapp.thirdparties.wingitlib.model.WGCountry;
import com.thewingitapp.thirdparties.wingitlib.model.WGDownloadPage;
import java.util.ArrayList;
import java.util.List;

public final class WINGiTDataHolder {
    private static List<WGCategory> mCategories = new ArrayList();
    private static List<WGCity> mCities = new ArrayList();
    private static List<WGCountry> mCountries = new ArrayList();
    private static WGDownloadPage mDownloadPage;

    public static List<WGCategory> getCategories() {
        return mCategories;
    }

    public static WGCategory getCategory(Long l) throws WGErrorCodeUnknownCategory {
        for (WGCategory wGCategory : mCategories) {
            if (wGCategory.getCategoryId() == l) {
                return wGCategory;
            }
        }
        throw new WGErrorCodeUnknownCategory();
    }

    public static List<WGCity> getCities() {
        return mCities;
    }

    public static WGCity getCity(int i) throws WGErrorCodeUnknownCity {
        for (WGCity wGCity : mCities) {
            if (wGCity.getCityId().intValue() == i) {
                return wGCity;
            }
        }
        throw new WGErrorCodeUnknownCity();
    }

    public static WGCity getCity(String str) throws WGErrorCodeUnknownCity {
        for (WGCity wGCity : mCities) {
            if (wGCity.getLocalizedName().equalsIgnoreCase(str)) {
                return wGCity;
            }
        }
        throw new WGErrorCodeUnknownCity();
    }

    public static List<WGCountry> getCountries() {
        return mCountries;
    }

    public static WGCountry getCountry(String str) throws WGErrorCodeUnknowCountry {
        for (WGCountry wGCountry : mCountries) {
            if (wGCountry.getName().equalsIgnoreCase(str)) {
                return wGCountry;
            }
        }
        throw new WGErrorCodeUnknowCountry();
    }

    public static WGDownloadPage getDownloadPage() {
        return mDownloadPage;
    }

    public static void setCategories(List<WGCategory> list) {
        mCategories.clear();
        mCategories.addAll(list);
    }

    public static void setCities(List<WGCity> list) {
        mCities.clear();
        mCities.addAll(list);
    }

    public static void setCountries(List<WGCountry> list) {
        mCountries.clear();
        mCountries.addAll(list);
    }

    public static void setDownloadPage(WGDownloadPage wGDownloadPage) {
        mDownloadPage = wGDownloadPage;
    }
}
