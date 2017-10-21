package fr.smoney.android.izly.data.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

import java.util.ArrayList;

public class CurrentUserSubscriptionPlan implements Parcelable {
    public static final Creator<CurrentUserSubscriptionPlan> CREATOR = new Creator<CurrentUserSubscriptionPlan>() {
        public final /* synthetic */ Object createFromParcel(Parcel parcel) {
            return new CurrentUserSubscriptionPlan(parcel);
        }

        public final /* bridge */ /* synthetic */ Object[] newArray(int i) {
            return new CurrentUserSubscriptionPlan[i];
        }
    };
    public ArrayList<PlanAvailability> a = new ArrayList();
    public int b = -1;
    public long c;
    public long d;
    public float e;

    public CurrentUserSubscriptionPlan(Parcel parcel) {
        parcel.readTypedList(this.a, PlanAvailability.CREATOR);
        this.b = parcel.readInt();
        this.c = parcel.readLong();
        this.d = parcel.readLong();
        this.e = parcel.readFloat();
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeTypedList(this.a);
        parcel.writeInt(this.b);
        parcel.writeLong(this.c);
        parcel.writeLong(this.d);
        parcel.writeFloat(this.e);
    }
}
