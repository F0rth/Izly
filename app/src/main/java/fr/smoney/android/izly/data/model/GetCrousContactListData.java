package fr.smoney.android.izly.data.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

import java.util.ArrayList;
import java.util.List;

public class GetCrousContactListData implements Parcelable {
    public static final Creator<GetCrousContactListData> CREATOR = new Creator<GetCrousContactListData>() {
        public final /* synthetic */ Object createFromParcel(Parcel parcel) {
            return new GetCrousContactListData(parcel);
        }

        public final /* bridge */ /* synthetic */ Object[] newArray(int i) {
            return new GetCrousContactListData[i];
        }
    };
    public String a;
    public String b;
    public String c;
    public List<CrousData> d;

    public GetCrousContactListData() {
        this.d = new ArrayList();
    }

    public GetCrousContactListData(Parcel parcel) {
        parcel.readTypedList(this.d, CrousData.CREATOR);
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeTypedList(this.d);
    }
}
