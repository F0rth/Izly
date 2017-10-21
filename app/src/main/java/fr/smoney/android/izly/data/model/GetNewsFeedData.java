package fr.smoney.android.izly.data.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

import java.util.ArrayList;
import java.util.List;

public class GetNewsFeedData implements Parcelable {
    public static final Creator<GetNewsFeedData> CREATOR = new Creator<GetNewsFeedData>() {
        public final /* synthetic */ Object createFromParcel(Parcel parcel) {
            return new GetNewsFeedData(parcel);
        }

        public final /* bridge */ /* synthetic */ Object[] newArray(int i) {
            return new GetNewsFeedData[i];
        }
    };
    public BalanceData a;
    public long b;
    public long c;
    public int d;
    public boolean e;
    public List<NewsFeedItem> f;

    public GetNewsFeedData() {
        this.b = -1;
        this.c = -1;
        this.d = 1;
        this.e = false;
        this.f = new ArrayList();
    }

    public GetNewsFeedData(Parcel parcel) {
        boolean z = true;
        int i = 0;
        this();
        this.b = parcel.readLong();
        this.c = parcel.readLong();
        this.d = parcel.readInt();
        if (parcel.readInt() != 1) {
            z = false;
        }
        this.e = z;
        int readInt = parcel.readInt();
        while (i < readInt) {
            this.f.add((NewsFeedItem) parcel.readParcelable(getClass().getClassLoader()));
            i++;
        }
        this.a = new BalanceData(parcel);
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(this.b);
        parcel.writeLong(this.c);
        parcel.writeInt(this.d);
        parcel.writeInt(this.e ? 1 : 0);
        int size = this.f.size();
        parcel.writeInt(size);
        for (int i2 = 0; i2 < size; i2++) {
            parcel.writeParcelable((Parcelable) this.f.get(i2), 0);
        }
        this.a.writeToParcel(parcel, i);
    }
}
