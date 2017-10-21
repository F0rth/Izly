package com.thewingitapp.thirdparties.wingitlib.model;

import android.text.TextUtils;
import com.thewingitapp.thirdparties.wingitlib.R;
import com.thewingitapp.thirdparties.wingitlib.WINGiTDataHolder;
import com.thewingitapp.thirdparties.wingitlib.exception.WGErrorCodeUnknownCategory;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class WGEvent extends WGEventDTO {
    private String getImageUrl(WGAttachmentDTO$Image wGAttachmentDTO$Image) {
        if (this.attachments != null) {
            for (int i = 0; i < this.attachments.size(); i++) {
                if (wGAttachmentDTO$Image.value().equals(((WGAttachmentDTO) this.attachments.get(i)).type)) {
                    return ((WGAttachmentDTO) this.attachments.get(i)).url;
                }
            }
            if (wGAttachmentDTO$Image != WGAttachmentDTO$Image.IMAGE_URL) {
                return getImageURL();
            }
        }
        return null;
    }

    public /* bridge */ /* synthetic */ boolean equals(Object obj) {
        return super.equals(obj);
    }

    public String getBookingURL() {
        return this.bookingURL;
    }

    public int getDefaultPlaceholder() {
        try {
            WGCategoryDTO dominantCategory = getDominantCategory();
            return dominantCategory.defaultName.equalsIgnoreCase("Arts & Entertainment") ? R.drawable.categories_default_art : dominantCategory.defaultName.equalsIgnoreCase("Music") ? R.drawable.categories_default_music : (dominantCategory.defaultName.equalsIgnoreCase("Gallery") || dominantCategory.defaultName.equalsIgnoreCase("Theater")) ? R.drawable.categories_default_scenes : R.drawable.categories_default_nightlife;
        } catch (WGErrorCodeUnknownCategory e) {
            e.printStackTrace();
            return R.drawable.categories_default_nightlife;
        }
    }

    public Double getDistance() {
        return this.distance;
    }

    public WGCategoryDTO getDominantCategory() throws WGErrorCodeUnknownCategory {
        return WINGiTDataHolder.getCategory(this.categoryId);
    }

    public Date getEventDate() {
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault());
            simpleDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
            return simpleDateFormat.parse(this.eventDateStr);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    public String getEventDescription() {
        return this.eventDescription;
    }

    public Date getEventEndDate() {
        if (!TextUtils.isEmpty(this.eventEndDateStr)) {
            try {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault());
                simpleDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
                return simpleDateFormat.parse(this.eventEndDateStr);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public String getEventId() {
        return this.eventId;
    }

    public String getHorizontalImage() {
        return getImageUrl(WGAttachmentDTO$Image.HORIZONTAL_IMAGE_URL);
    }

    public String getImageURL() {
        return getImageUrl(WGAttachmentDTO$Image.IMAGE_URL);
    }

    public Double getLat() {
        return this.lat;
    }

    public Double getLon() {
        return this.lon;
    }

    public String getPermalink() {
        return this.permalink;
    }

    public WGPlace getPlace() {
        return this.place;
    }

    public String getPriceMax() {
        return this.priceMax;
    }

    public String getPriceMin() {
        return this.priceMin;
    }

    public int getProviderId() {
        return this.providerId;
    }

    public String getSocialUrl() {
        return this.socialUrl;
    }

    public String getSquareImage() {
        return getImageUrl(WGAttachmentDTO$Image.SQUARE_IMAGE_URL);
    }

    public String getTitle() {
        return this.title;
    }

    public String getType() {
        return this.type;
    }

    public String getVerticalImage() {
        return getImageUrl(WGAttachmentDTO$Image.VERTICAL_IMAGE_URL);
    }

    public /* bridge */ /* synthetic */ int hashCode() {
        return super.hashCode();
    }

    public Boolean isPromoted() {
        return this.isPromoted;
    }

    public boolean isTicketCompliant() {
        return this.ticketCompliant;
    }

    public boolean isUberCompliant() {
        return this.uberCompliant;
    }
}
