package fr.smoney.android.izly.data.model;

import android.os.Parcel;
import android.os.Parcelable.Creator;

final class OAuthData$1 implements Creator<OAuthData> {
    OAuthData$1() {
    }

    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        return new OAuthData(parcel);
    }

    public final /* bridge */ /* synthetic */ Object[] newArray(int i) {
        return new OAuthData[i];
    }
}
