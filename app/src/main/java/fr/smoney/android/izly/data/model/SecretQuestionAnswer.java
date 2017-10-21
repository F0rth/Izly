package fr.smoney.android.izly.data.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class SecretQuestionAnswer implements Parcelable {
    public static final Creator<SecretQuestionAnswer> CREATOR = new Creator<SecretQuestionAnswer>() {
        public final /* synthetic */ Object createFromParcel(Parcel parcel) {
            return new SecretQuestionAnswer(parcel);
        }

        public final /* bridge */ /* synthetic */ Object[] newArray(int i) {
            return new SecretQuestionAnswer[i];
        }
    };
    public String a;

    public SecretQuestionAnswer(Parcel parcel) {
        this.a = parcel.readString();
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.a);
    }
}
