package fr.smoney.android.izly.data.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class PostDealsCodeResponse implements Parcelable {
    public static final Creator<PostDealsCodeResponse> CREATOR = new Creator<PostDealsCodeResponse>() {
        public final /* synthetic */ Object createFromParcel(Parcel parcel) {
            return new PostDealsCodeResponse(parcel);
        }

        public final /* bridge */ /* synthetic */ Object[] newArray(int i) {
            return new PostDealsCodeResponse[i];
        }
    };
    public int a;

    public PostDealsCodeResponse(int i) {
        this.a = i;
    }

    public PostDealsCodeResponse(Parcel parcel) {
        this.a = parcel.readInt();
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.a);
    }
}
