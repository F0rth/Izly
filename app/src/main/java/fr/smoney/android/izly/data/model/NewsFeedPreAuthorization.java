package fr.smoney.android.izly.data.model;

import android.os.Parcel;
import android.os.Parcelable.Creator;

public class NewsFeedPreAuthorization extends NewsFeedItem {
    public static final Creator<NewsFeedPreAuthorization> CREATOR = new Creator<NewsFeedPreAuthorization>() {
        public final /* synthetic */ Object createFromParcel(Parcel parcel) {
            return new NewsFeedPreAuthorization(parcel);
        }

        public final /* bridge */ /* synthetic */ Object[] newArray(int i) {
            return new NewsFeedPreAuthorization[i];
        }
    };

    public NewsFeedPreAuthorization(Parcel parcel) {
        super(parcel);
    }

    protected final int a() {
        return 1;
    }

    public final void a(Parcel parcel) {
        super.a(parcel);
    }

    public void writeToParcel(Parcel parcel, int i) {
        super.writeToParcel(parcel, i);
    }
}
