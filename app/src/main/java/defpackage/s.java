package defpackage;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import scanpay.it.SPCreditCard;

public final class s implements Creator {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        return new SPCreditCard(parcel);
    }

    public final /* bridge */ /* synthetic */ Object[] newArray(int i) {
        return new SPCreditCard[i];
    }
}
