package fr.smoney.android.izly.data.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class GetSecretQuestionData implements Parcelable {
    public static final Creator<GetSecretQuestionData> CREATOR = new Creator<GetSecretQuestionData>() {
        public final /* synthetic */ Object createFromParcel(Parcel parcel) {
            return new GetSecretQuestionData(parcel);
        }

        public final /* bridge */ /* synthetic */ Object[] newArray(int i) {
            return new GetSecretQuestionData[i];
        }
    };
    public String a;

    public GetSecretQuestionData(Parcel parcel) {
        this.a = parcel.readString();
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.a);
    }
}
