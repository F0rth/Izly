package android.support.v4.media;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.support.annotation.Nullable;
import android.text.TextUtils;

public final class MediaDescriptionCompat implements Parcelable {
    public static final Creator<MediaDescriptionCompat> CREATOR = new Creator<MediaDescriptionCompat>() {
        public final MediaDescriptionCompat createFromParcel(Parcel parcel) {
            return VERSION.SDK_INT < 21 ? new MediaDescriptionCompat(parcel) : MediaDescriptionCompat.fromMediaDescription(MediaDescriptionCompatApi21.fromParcel(parcel));
        }

        public final MediaDescriptionCompat[] newArray(int i) {
            return new MediaDescriptionCompat[i];
        }
    };
    public static final String DESCRIPTION_KEY_MEDIA_URI = "android.support.v4.media.description.MEDIA_URI";
    public static final String DESCRIPTION_KEY_NULL_BUNDLE_FLAG = "android.support.v4.media.description.NULL_BUNDLE_FLAG";
    private final CharSequence mDescription;
    private Object mDescriptionObj;
    private final Bundle mExtras;
    private final Bitmap mIcon;
    private final Uri mIconUri;
    private final String mMediaId;
    private final Uri mMediaUri;
    private final CharSequence mSubtitle;
    private final CharSequence mTitle;

    public static final class Builder {
        private CharSequence mDescription;
        private Bundle mExtras;
        private Bitmap mIcon;
        private Uri mIconUri;
        private String mMediaId;
        private Uri mMediaUri;
        private CharSequence mSubtitle;
        private CharSequence mTitle;

        public final MediaDescriptionCompat build() {
            return new MediaDescriptionCompat(this.mMediaId, this.mTitle, this.mSubtitle, this.mDescription, this.mIcon, this.mIconUri, this.mExtras, this.mMediaUri);
        }

        public final Builder setDescription(@Nullable CharSequence charSequence) {
            this.mDescription = charSequence;
            return this;
        }

        public final Builder setExtras(@Nullable Bundle bundle) {
            this.mExtras = bundle;
            return this;
        }

        public final Builder setIconBitmap(@Nullable Bitmap bitmap) {
            this.mIcon = bitmap;
            return this;
        }

        public final Builder setIconUri(@Nullable Uri uri) {
            this.mIconUri = uri;
            return this;
        }

        public final Builder setMediaId(@Nullable String str) {
            this.mMediaId = str;
            return this;
        }

        public final Builder setMediaUri(@Nullable Uri uri) {
            this.mMediaUri = uri;
            return this;
        }

        public final Builder setSubtitle(@Nullable CharSequence charSequence) {
            this.mSubtitle = charSequence;
            return this;
        }

        public final Builder setTitle(@Nullable CharSequence charSequence) {
            this.mTitle = charSequence;
            return this;
        }
    }

    private MediaDescriptionCompat(Parcel parcel) {
        this.mMediaId = parcel.readString();
        this.mTitle = (CharSequence) TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(parcel);
        this.mSubtitle = (CharSequence) TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(parcel);
        this.mDescription = (CharSequence) TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(parcel);
        this.mIcon = (Bitmap) parcel.readParcelable(null);
        this.mIconUri = (Uri) parcel.readParcelable(null);
        this.mExtras = parcel.readBundle();
        this.mMediaUri = (Uri) parcel.readParcelable(null);
    }

    private MediaDescriptionCompat(String str, CharSequence charSequence, CharSequence charSequence2, CharSequence charSequence3, Bitmap bitmap, Uri uri, Bundle bundle, Uri uri2) {
        this.mMediaId = str;
        this.mTitle = charSequence;
        this.mSubtitle = charSequence2;
        this.mDescription = charSequence3;
        this.mIcon = bitmap;
        this.mIconUri = uri;
        this.mExtras = bundle;
        this.mMediaUri = uri2;
    }

    public static MediaDescriptionCompat fromMediaDescription(Object obj) {
        if (obj == null || VERSION.SDK_INT < 21) {
            return null;
        }
        Bundle bundle;
        MediaDescriptionCompat build;
        Builder builder = new Builder();
        builder.setMediaId(MediaDescriptionCompatApi21.getMediaId(obj));
        builder.setTitle(MediaDescriptionCompatApi21.getTitle(obj));
        builder.setSubtitle(MediaDescriptionCompatApi21.getSubtitle(obj));
        builder.setDescription(MediaDescriptionCompatApi21.getDescription(obj));
        builder.setIconBitmap(MediaDescriptionCompatApi21.getIconBitmap(obj));
        builder.setIconUri(MediaDescriptionCompatApi21.getIconUri(obj));
        Bundle extras = MediaDescriptionCompatApi21.getExtras(obj);
        Uri uri = extras == null ? null : (Uri) extras.getParcelable(DESCRIPTION_KEY_MEDIA_URI);
        if (uri != null) {
            if (extras.containsKey(DESCRIPTION_KEY_NULL_BUNDLE_FLAG) && extras.size() == 2) {
                bundle = null;
                builder.setExtras(bundle);
                if (uri != null) {
                    builder.setMediaUri(uri);
                } else if (VERSION.SDK_INT >= 23) {
                    builder.setMediaUri(MediaDescriptionCompatApi23.getMediaUri(obj));
                }
                build = builder.build();
                build.mDescriptionObj = obj;
                return build;
            }
            extras.remove(DESCRIPTION_KEY_MEDIA_URI);
            extras.remove(DESCRIPTION_KEY_NULL_BUNDLE_FLAG);
        }
        bundle = extras;
        builder.setExtras(bundle);
        if (uri != null) {
            builder.setMediaUri(uri);
        } else if (VERSION.SDK_INT >= 23) {
            builder.setMediaUri(MediaDescriptionCompatApi23.getMediaUri(obj));
        }
        build = builder.build();
        build.mDescriptionObj = obj;
        return build;
    }

    public final int describeContents() {
        return 0;
    }

    @Nullable
    public final CharSequence getDescription() {
        return this.mDescription;
    }

    @Nullable
    public final Bundle getExtras() {
        return this.mExtras;
    }

    @Nullable
    public final Bitmap getIconBitmap() {
        return this.mIcon;
    }

    @Nullable
    public final Uri getIconUri() {
        return this.mIconUri;
    }

    public final Object getMediaDescription() {
        if (this.mDescriptionObj != null || VERSION.SDK_INT < 21) {
            return this.mDescriptionObj;
        }
        Object newInstance = Builder.newInstance();
        Builder.setMediaId(newInstance, this.mMediaId);
        Builder.setTitle(newInstance, this.mTitle);
        Builder.setSubtitle(newInstance, this.mSubtitle);
        Builder.setDescription(newInstance, this.mDescription);
        Builder.setIconBitmap(newInstance, this.mIcon);
        Builder.setIconUri(newInstance, this.mIconUri);
        Bundle bundle = this.mExtras;
        if (VERSION.SDK_INT < 23 && this.mMediaUri != null) {
            if (bundle == null) {
                bundle = new Bundle();
                bundle.putBoolean(DESCRIPTION_KEY_NULL_BUNDLE_FLAG, true);
            }
            bundle.putParcelable(DESCRIPTION_KEY_MEDIA_URI, this.mMediaUri);
        }
        Builder.setExtras(newInstance, bundle);
        if (VERSION.SDK_INT >= 23) {
            Builder.setMediaUri(newInstance, this.mMediaUri);
        }
        this.mDescriptionObj = Builder.build(newInstance);
        return this.mDescriptionObj;
    }

    @Nullable
    public final String getMediaId() {
        return this.mMediaId;
    }

    @Nullable
    public final Uri getMediaUri() {
        return this.mMediaUri;
    }

    @Nullable
    public final CharSequence getSubtitle() {
        return this.mSubtitle;
    }

    @Nullable
    public final CharSequence getTitle() {
        return this.mTitle;
    }

    public final String toString() {
        return this.mTitle + ", " + this.mSubtitle + ", " + this.mDescription;
    }

    public final void writeToParcel(Parcel parcel, int i) {
        if (VERSION.SDK_INT < 21) {
            parcel.writeString(this.mMediaId);
            TextUtils.writeToParcel(this.mTitle, parcel, i);
            TextUtils.writeToParcel(this.mSubtitle, parcel, i);
            TextUtils.writeToParcel(this.mDescription, parcel, i);
            parcel.writeParcelable(this.mIcon, i);
            parcel.writeParcelable(this.mIconUri, i);
            parcel.writeBundle(this.mExtras);
            parcel.writeParcelable(this.mMediaUri, i);
            return;
        }
        MediaDescriptionCompatApi21.writeToParcel(getMediaDescription(), parcel, i);
    }
}
