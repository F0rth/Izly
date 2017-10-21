package fr.smoney.android.izly.data.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

import java.util.ArrayList;

public class GetBookmarksData implements Parcelable {
    public static final Creator<GetBookmarksData> CREATOR = new Creator<GetBookmarksData>() {
        public final /* synthetic */ Object createFromParcel(Parcel parcel) {
            return new GetBookmarksData(parcel);
        }

        public final /* bridge */ /* synthetic */ Object[] newArray(int i) {
            return new GetBookmarksData[i];
        }
    };
    public BalanceData a;
    public ArrayList<Bookmark> b;

    public GetBookmarksData() {
        this.a = new BalanceData();
        this.b = new ArrayList();
    }

    public GetBookmarksData(Parcel parcel) {
        this.a = new BalanceData(parcel);
        int readInt = parcel.readInt();
        this.b = new ArrayList();
        for (int i = 0; i < readInt; i++) {
            this.b.add((Bookmark) parcel.readParcelable(getClass().getClassLoader()));
        }
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int i) {
        this.a.writeToParcel(parcel, i);
        parcel.writeInt(this.b.size());
        for (int i2 = 0; i2 < this.b.size(); i2++) {
            parcel.writeParcelable((Parcelable) this.b.get(i2), 0);
        }
    }
}
