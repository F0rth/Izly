package fr.smoney.android.izly.data.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class GetNewsFeedDetailsData implements Parcelable {
    public static final Creator<GetNewsFeedDetailsData> CREATOR = new Creator<GetNewsFeedDetailsData>() {
        public final /* synthetic */ Object createFromParcel(Parcel parcel) {
            return new GetNewsFeedDetailsData(parcel);
        }

        public final /* bridge */ /* synthetic */ Object[] newArray(int i) {
            return new GetNewsFeedDetailsData[i];
        }
    };
    public Operation a;
    public PromotionalOffer b;
    public PreAuthorizationContainerData c;

    public GetNewsFeedDetailsData(Parcel parcel) {
        this.a = (Operation) parcel.readParcelable(getClass().getClassLoader());
        this.c = (PreAuthorizationContainerData) parcel.readParcelable(getClass().getClassLoader());
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeParcelable(this.a, i);
        parcel.writeParcelable(this.c, i);
    }
}
