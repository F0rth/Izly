package fr.smoney.android.izly.data.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

import java.util.ArrayList;

public class GetMySupportListData implements Parcelable {
    public static final Creator<GetMySupportListData> CREATOR = new Creator<GetMySupportListData>() {
        public final /* synthetic */ Object createFromParcel(Parcel parcel) {
            return new GetMySupportListData(parcel);
        }

        public final /* bridge */ /* synthetic */ Object[] newArray(int i) {
            return new GetMySupportListData[i];
        }
    };
    public ArrayList<Support> a = new ArrayList();

    public GetMySupportListData(Parcel parcel) {
        parcel.readTypedList(this.a, Support.CREATOR);
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeTypedList(this.a);
    }
}
