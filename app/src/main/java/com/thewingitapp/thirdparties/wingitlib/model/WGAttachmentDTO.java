package com.thewingitapp.thirdparties.wingitlib.model;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

public class WGAttachmentDTO implements Serializable {
    @SerializedName("type")
    public String type;
    @SerializedName("url")
    public String url;
}
