package com.google.android.gms.ads.internal.client;

import android.os.Parcel;
import com.google.android.gms.ads.search.SearchAdRequest;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.internal.zzhb;

@zzhb
public final class SearchAdRequestParcel implements SafeParcelable {
    public static final zzam CREATOR = new zzam();
    public final int backgroundColor;
    public final int versionCode;
    public final int zzvd;
    public final int zzve;
    public final int zzvf;
    public final int zzvg;
    public final int zzvh;
    public final int zzvi;
    public final int zzvj;
    public final String zzvk;
    public final int zzvl;
    public final String zzvm;
    public final int zzvn;
    public final int zzvo;
    public final String zzvp;

    SearchAdRequestParcel(int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8, int i9, String str, int i10, String str2, int i11, int i12, String str3) {
        this.versionCode = i;
        this.zzvd = i2;
        this.backgroundColor = i3;
        this.zzve = i4;
        this.zzvf = i5;
        this.zzvg = i6;
        this.zzvh = i7;
        this.zzvi = i8;
        this.zzvj = i9;
        this.zzvk = str;
        this.zzvl = i10;
        this.zzvm = str2;
        this.zzvn = i11;
        this.zzvo = i12;
        this.zzvp = str3;
    }

    public SearchAdRequestParcel(SearchAdRequest searchAdRequest) {
        this.versionCode = 1;
        this.zzvd = searchAdRequest.getAnchorTextColor();
        this.backgroundColor = searchAdRequest.getBackgroundColor();
        this.zzve = searchAdRequest.getBackgroundGradientBottom();
        this.zzvf = searchAdRequest.getBackgroundGradientTop();
        this.zzvg = searchAdRequest.getBorderColor();
        this.zzvh = searchAdRequest.getBorderThickness();
        this.zzvi = searchAdRequest.getBorderType();
        this.zzvj = searchAdRequest.getCallButtonColor();
        this.zzvk = searchAdRequest.getCustomChannels();
        this.zzvl = searchAdRequest.getDescriptionTextColor();
        this.zzvm = searchAdRequest.getFontFace();
        this.zzvn = searchAdRequest.getHeaderTextColor();
        this.zzvo = searchAdRequest.getHeaderTextSize();
        this.zzvp = searchAdRequest.getQuery();
    }

    public final int describeContents() {
        return 0;
    }

    public final void writeToParcel(Parcel parcel, int i) {
        zzam.zza(this, parcel, i);
    }
}
