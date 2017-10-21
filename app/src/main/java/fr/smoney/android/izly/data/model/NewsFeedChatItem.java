package fr.smoney.android.izly.data.model;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import android.util.SparseArray;

public class NewsFeedChatItem extends NewsFeedItem {
    public static final Creator<NewsFeedChatItem> CREATOR = new Creator<NewsFeedChatItem>() {
        public final /* synthetic */ Object createFromParcel(Parcel parcel) {
            return new NewsFeedChatItem(parcel);
        }

        public final /* bridge */ /* synthetic */ Object[] newArray(int i) {
            return new NewsFeedChatItem[i];
        }
    };
    private static final SparseArray<a> g;
    public a a;

    public enum a {
        NoneDirection(0),
        InDirection(1),
        OutDirection(2);

        int d;

        private a(int i) {
            this.d = i;
        }
    }

    static {
        SparseArray sparseArray = new SparseArray();
        for (a aVar : a.values()) {
            sparseArray.append(aVar.d, aVar);
        }
        g = sparseArray;
    }

    public NewsFeedChatItem(Parcel parcel) {
        super(parcel);
    }

    public static a a(int i) {
        return (a) g.get(i);
    }

    protected final int a() {
        return 1;
    }

    public final void a(Parcel parcel) {
        super.a(parcel);
        this.a = (a) parcel.readSerializable();
    }

    public void writeToParcel(Parcel parcel, int i) {
        super.writeToParcel(parcel, i);
        parcel.writeSerializable(this.a);
    }
}
