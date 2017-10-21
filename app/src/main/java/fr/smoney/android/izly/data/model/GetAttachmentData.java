package fr.smoney.android.izly.data.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class GetAttachmentData implements Parcelable {
    public static final Creator<GetAttachmentData> CREATOR = new Creator<GetAttachmentData>() {
        public final /* synthetic */ Object createFromParcel(Parcel parcel) {
            return new GetAttachmentData(parcel);
        }

        public final /* bridge */ /* synthetic */ Object[] newArray(int i) {
            return new GetAttachmentData[i];
        }
    };
    public String a;
    public String b = null;
    public boolean c = false;

    public GetAttachmentData(Parcel parcel) {
        boolean z = false;
        this.a = parcel.readString();
        this.b = parcel.readString();
        if (parcel.readInt() == 1) {
            z = true;
        }
        this.c = z;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.a);
        parcel.writeString(this.b);
        parcel.writeInt(this.c ? 1 : 0);
    }
}
