package com.thewingitapp.thirdparties.wingitlib.model;

import android.location.Location;
import android.support.annotation.NonNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class WGTimelineParameters implements Serializable {
    public List<WGCategory> categories;
    public Double lat;
    public Double lng;
    public Day offsetDay;
    public Integer radius;
    public SortType sortType;

    public enum Day {
        TODAY(0),
        TOMORROW(1);
        
        private final int day;

        private Day(int i) {
            this.day = i;
        }

        public final int getDay() {
            return this.day;
        }

        public final String toString() {
            return String.valueOf(this.day);
        }
    }

    public enum SortType {
        BUZZ("Buzz"),
        DATE("Date"),
        DISTANCE("Distance");
        
        private final String name;

        private SortType(String str) {
            this.name = str;
        }

        public final String toString() {
            return this.name;
        }
    }

    public WGTimelineParameters() {
        this.categories = new ArrayList();
        this.offsetDay = Day.TODAY;
        this.lat = null;
        this.lng = null;
        this.sortType = SortType.DATE;
    }

    public WGTimelineParameters(@NonNull Location location) {
        this.categories = new ArrayList();
        this.offsetDay = Day.TODAY;
        this.lat = Double.valueOf(location.getLatitude());
        this.lng = Double.valueOf(location.getLongitude());
        this.sortType = SortType.DISTANCE;
    }

    public String getCategoryIds() {
        String str = "";
        if (this.categories == null || this.categories.size() <= 0) {
            return str;
        }
        String str2 = "";
        for (WGCategoryDTO wGCategoryDTO : this.categories) {
            str2 = str2 + wGCategoryDTO.categoryId + "|";
        }
        return str2.substring(0, str2.length() - 1);
    }

    public void setCurrentLocation(Location location) {
        if (location == null) {
            if (this.sortType == SortType.DISTANCE) {
                this.sortType = SortType.DATE;
            }
            this.lat = null;
            this.lng = null;
            return;
        }
        this.lat = Double.valueOf(location.getLatitude());
        this.lng = Double.valueOf(location.getLongitude());
        this.sortType = SortType.DISTANCE;
    }
}
