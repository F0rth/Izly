package com.thewingitapp.thirdparties.wingitlib.model;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;
import java.util.List;

class WGEventDTO implements Serializable {
    @SerializedName("attachments")
    List<WGAttachmentDTO> attachments;
    @SerializedName("bookingURL")
    String bookingURL;
    @SerializedName("categories")
    int[] categories;
    @SerializedName("categoryId")
    Long categoryId;
    @SerializedName("distance")
    Double distance;
    @SerializedName("eventDate")
    String eventDateStr;
    @SerializedName("eventDescription")
    String eventDescription;
    @SerializedName("eventEndDate")
    String eventEndDateStr;
    @SerializedName("eventId")
    String eventId;
    @SerializedName("eventTimeExact")
    Boolean eventTimeExact;
    @SerializedName("imageURL")
    String imageURL;
    @SerializedName("isPromoted")
    Boolean isPromoted;
    @SerializedName("lat")
    Double lat;
    @SerializedName("lon")
    Double lon;
    @SerializedName("permalink")
    String permalink;
    @SerializedName("place")
    WGPlace place;
    @SerializedName("priceMax")
    String priceMax;
    @SerializedName("priceMin")
    String priceMin;
    @SerializedName("providerId")
    int providerId;
    @SerializedName("socialPoints")
    Long socialPoints;
    @SerializedName("socialUrl")
    String socialUrl;
    @SerializedName("ticketCompliant")
    boolean ticketCompliant;
    @SerializedName("title")
    String title;
    @SerializedName("type")
    String type;
    @SerializedName("uberCompliant")
    boolean uberCompliant;

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        return this.eventId.equals(((WGEventDTO) obj).eventId);
    }

    public int hashCode() {
        return this.eventId.hashCode();
    }
}
