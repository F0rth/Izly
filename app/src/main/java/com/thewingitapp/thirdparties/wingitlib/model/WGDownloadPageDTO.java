package com.thewingitapp.thirdparties.wingitlib.model;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

class WGDownloadPageDTO implements Serializable {
    @SerializedName("buttonLabel")
    String buttonLabel;
    @SerializedName("description")
    String description;
    @SerializedName("imageURL")
    String imageURL;
    @SerializedName("title")
    String title;

    WGDownloadPageDTO() {
    }
}
