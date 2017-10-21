package fr.smoney.android.izly.data.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class SendChatMessageData implements Parcelable {
    public static final Creator<SendChatMessageData> CREATOR = new Creator<SendChatMessageData>() {
        public final /* synthetic */ Object createFromParcel(Parcel parcel) {
            return new SendChatMessageData(parcel);
        }

        public final /* bridge */ /* synthetic */ Object[] newArray(int i) {
            return new SendChatMessageData[i];
        }
    };
    public BalanceData a;

    public SendChatMessageData() {
        this.a = new BalanceData();
    }

    public SendChatMessageData(Parcel parcel) {
        this.a = new BalanceData(parcel);
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int i) {
        this.a.writeToParcel(parcel, i);
    }
}
