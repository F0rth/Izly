package com.thewingitapp.thirdparties.wingitlib.model;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

class WGCategoryDTO implements Serializable {
    @SerializedName("categoryId")
    Long categoryId;
    @SerializedName("categoryName")
    String categoryName;
    @SerializedName("cityId")
    Long cityId;
    @SerializedName("defaultName")
    String defaultName;

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        WGCategoryDTO wGCategoryDTO = (WGCategoryDTO) obj;
        if (!this.categoryId.equals(wGCategoryDTO.categoryId)) {
            return false;
        }
        if (this.cityId != null) {
            if (this.cityId.equals(wGCategoryDTO.cityId)) {
                return true;
            }
        } else if (wGCategoryDTO.cityId == null) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return (this.cityId != null ? this.cityId.hashCode() : 0) + (this.categoryId.hashCode() * 31);
    }
}
