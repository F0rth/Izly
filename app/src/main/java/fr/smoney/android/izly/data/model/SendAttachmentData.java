package fr.smoney.android.izly.data.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class SendAttachmentData implements Parcelable {
    public static final Creator<SendAttachmentData> CREATOR = new Creator<SendAttachmentData>() {
        public final /* synthetic */ Object createFromParcel(Parcel parcel) {
            return new SendAttachmentData(parcel);
        }

        public final /* bridge */ /* synthetic */ Object[] newArray(int i) {
            return new SendAttachmentData[i];
        }
    };
    public boolean a;

    public SendAttachmentData(Parcel parcel) {
        boolean z = true;
        if (parcel.readInt() != 1) {
            z = false;
        }
        this.a = z;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.a ? 1 : 0);
    }
}
