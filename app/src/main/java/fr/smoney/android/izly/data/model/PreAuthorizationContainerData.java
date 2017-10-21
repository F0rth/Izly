package fr.smoney.android.izly.data.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.util.SparseArray;

public class PreAuthorizationContainerData implements Parcelable {
    public static final Creator<PreAuthorizationContainerData> CREATOR = new Creator<PreAuthorizationContainerData>() {
        public final /* synthetic */ Object createFromParcel(Parcel parcel) {
            return new PreAuthorizationContainerData(parcel);
        }

        public final /* bridge */ /* synthetic */ Object[] newArray(int i) {
            return new PreAuthorizationContainerData[i];
        }
    };
    private static final SparseArray<a> f;
    public Contact a;
    public double b;
    public long c;
    public PreAuthorization d;
    public a e;

    public static class PreAuthorization implements Parcelable {
        public static final Creator<PreAuthorization> CREATOR = new Creator<PreAuthorization>() {
            public final /* synthetic */ Object createFromParcel(Parcel parcel) {
                return new PreAuthorization(parcel);
            }

            public final /* bridge */ /* synthetic */ Object[] newArray(int i) {
                return new PreAuthorization[i];
            }
        };
        public String a;
        public int b;

        public PreAuthorization(Parcel parcel) {
            this.a = parcel.readString();
            this.b = parcel.readInt();
        }

        public int describeContents() {
            return 0;
        }

        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeString(this.a);
            parcel.writeInt(this.b);
        }
    }

    public enum a {
        Pending(0),
        Canceled(1),
        Expired(2),
        Accepted(3);

        int e;

        private a(int i) {
            this.e = i;
        }
    }

    static {
        SparseArray sparseArray = new SparseArray();
        for (a aVar : a.values()) {
            sparseArray.append(aVar.e, aVar);
        }
        f = sparseArray;
    }

    public PreAuthorizationContainerData(Parcel parcel) {
        this();
        this.b = parcel.readDouble();
        this.c = parcel.readLong();
        this.a = (Contact) parcel.readParcelable(getClass().getClassLoader());
        this.d = (PreAuthorization) parcel.readParcelable(getClass().getClassLoader());
        this.e = (a) parcel.readSerializable();
    }

    public static a a(int i) {
        return (a) f.get(i);
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeDouble(this.b);
        parcel.writeLong(this.c);
        parcel.writeParcelable(this.a, i);
        parcel.writeParcelable(this.d, i);
        parcel.writeSerializable(this.e);
    }
}
