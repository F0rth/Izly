package fr.smoney.android.izly.data.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

import java.util.ArrayList;

public class GetNearProListData implements Parcelable {
    public static final Creator<GetNearProListData> CREATOR = new Creator<GetNearProListData>() {
        public final /* synthetic */ Object createFromParcel(Parcel parcel) {
            return new GetNearProListData(parcel);
        }

        public final /* bridge */ /* synthetic */ Object[] newArray(int i) {
            return new GetNearProListData[i];
        }
    };
    public BalanceData a;
    public ArrayList<NearPro> b;
    public ArrayList<PromotionalOffer> c;
    private int d;
    private int e;

    public GetNearProListData() {
        this.d = -1;
        this.e = 0;
        this.a = new BalanceData();
        this.b = new ArrayList();
        this.c = new ArrayList();
    }

    public GetNearProListData(Parcel parcel) {
        int i;
        int i2 = 0;
        this();
        this.a = (BalanceData) parcel.readParcelable(getClass().getClassLoader());
        int readInt = parcel.readInt();
        for (i = 0; i < readInt; i++) {
            this.b.add((NearPro) parcel.readParcelable(getClass().getClassLoader()));
        }
        i = parcel.readInt();
        while (i2 < i) {
            this.c.add((PromotionalOffer) parcel.readParcelable(getClass().getClassLoader()));
            i2++;
        }
    }

    public final void a() {
        if (this.c != null && this.c.size() > 0) {
            this.d = -1;
        } else if (this.b != null && this.b.size() > 0) {
            this.d = 0;
        }
    }

    public final void a(int i) {
        if (i >= this.b.size() || i < -1) {
            this.d = -1;
        } else {
            this.d = i;
        }
    }

    public final ArrayList<PromotionalOffer> b() {
        return this.d == -1 ? this.c : (this.b == null || this.b.size() <= this.d) ? null : ((NearPro) this.b.get(this.d)).p;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int i) {
        int i2;
        parcel.writeParcelable(this.a, i);
        int size = this.b.size();
        parcel.writeInt(size);
        for (i2 = 0; i2 < size; i2++) {
            parcel.writeParcelable((Parcelable) this.b.get(i2), 0);
        }
        size = this.c.size();
        parcel.writeInt(size);
        for (i2 = 0; i2 < size; i2++) {
            parcel.writeParcelable((Parcelable) this.c.get(i2), 0);
        }
    }
}
