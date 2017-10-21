package com.google.android.gms.ads.internal.request;

import android.os.Parcel;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import com.google.android.gms.ads.internal.reward.mediation.client.RewardItemParcel;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.internal.zzhb;
import java.util.Collections;
import java.util.List;

@zzhb
public final class AdResponseParcel implements SafeParcelable {
    public static final zzh CREATOR = new zzh();
    public String body;
    public final int errorCode;
    public final int orientation;
    public final int versionCode;
    public final List<String> zzBQ;
    public final List<String> zzBR;
    public final long zzBU;
    private AdRequestInfoParcel zzCu;
    public final String zzEF;
    public final boolean zzHB;
    public final long zzHS;
    public final boolean zzHT;
    public final long zzHU;
    public final List<String> zzHV;
    public final String zzHW;
    public final long zzHX;
    public final String zzHY;
    public final boolean zzHZ;
    public final String zzIa;
    public final String zzIb;
    public final boolean zzIc;
    public final boolean zzId;
    public final boolean zzIe;
    public final int zzIf;
    public LargeParcelTeleporter zzIg;
    public String zzIh;
    public String zzIi;
    @Nullable
    public RewardItemParcel zzIj;
    @Nullable
    public List<String> zzIk;
    @Nullable
    public List<String> zzIl;
    @Nullable
    public boolean zzIm;
    public final boolean zzuk;
    public boolean zzul;
    public boolean zzum;

    public AdResponseParcel(int i) {
        this(16, null, null, null, i, null, -1, false, -1, null, -1, -1, null, -1, null, false, null, null, false, false, false, true, false, 0, null, null, null, false, false, null, null, null, false);
    }

    public AdResponseParcel(int i, long j) {
        this(16, null, null, null, i, null, -1, false, -1, null, j, -1, null, -1, null, false, null, null, false, false, false, true, false, 0, null, null, null, false, false, null, null, null, false);
    }

    AdResponseParcel(int i, String str, String str2, List<String> list, int i2, List<String> list2, long j, boolean z, long j2, List<String> list3, long j3, int i3, String str3, long j4, String str4, boolean z2, String str5, String str6, boolean z3, boolean z4, boolean z5, boolean z6, boolean z7, int i4, LargeParcelTeleporter largeParcelTeleporter, String str7, String str8, boolean z8, boolean z9, RewardItemParcel rewardItemParcel, List<String> list4, List<String> list5, boolean z10) {
        this.versionCode = i;
        this.zzEF = str;
        this.body = str2;
        this.zzBQ = list != null ? Collections.unmodifiableList(list) : null;
        this.errorCode = i2;
        this.zzBR = list2 != null ? Collections.unmodifiableList(list2) : null;
        this.zzHS = j;
        this.zzHT = z;
        this.zzHU = j2;
        this.zzHV = list3 != null ? Collections.unmodifiableList(list3) : null;
        this.zzBU = j3;
        this.orientation = i3;
        this.zzHW = str3;
        this.zzHX = j4;
        this.zzHY = str4;
        this.zzHZ = z2;
        this.zzIa = str5;
        this.zzIb = str6;
        this.zzIc = z3;
        this.zzuk = z4;
        this.zzHB = z5;
        this.zzId = z6;
        this.zzIe = z7;
        this.zzIf = i4;
        this.zzIg = largeParcelTeleporter;
        this.zzIh = str7;
        this.zzIi = str8;
        if (this.body == null && this.zzIg != null) {
            StringParcel stringParcel = (StringParcel) this.zzIg.zza(StringParcel.CREATOR);
            if (!(stringParcel == null || TextUtils.isEmpty(stringParcel.zzgz()))) {
                this.body = stringParcel.zzgz();
            }
        }
        this.zzul = z8;
        this.zzum = z9;
        this.zzIj = rewardItemParcel;
        this.zzIk = list4;
        this.zzIl = list5;
        this.zzIm = z10;
    }

    public AdResponseParcel(AdRequestInfoParcel adRequestInfoParcel, String str, String str2, List<String> list, List<String> list2, long j, boolean z, long j2, List<String> list3, long j3, int i, String str3, long j4, String str4, String str5, boolean z2, boolean z3, boolean z4, boolean z5, boolean z6, int i2, String str6, boolean z7, boolean z8, RewardItemParcel rewardItemParcel, List<String> list4, List<String> list5, boolean z9) {
        this(16, str, str2, list, -2, list2, j, z, j2, list3, j3, i, str3, j4, str4, false, null, str5, z2, z3, z4, z5, z6, i2, null, null, str6, z7, z8, rewardItemParcel, list4, list5, z9);
        this.zzCu = adRequestInfoParcel;
    }

    public AdResponseParcel(AdRequestInfoParcel adRequestInfoParcel, String str, String str2, List<String> list, List<String> list2, long j, boolean z, long j2, List<String> list3, long j3, int i, String str3, long j4, String str4, boolean z2, String str5, String str6, boolean z3, boolean z4, boolean z5, boolean z6, boolean z7, int i2, String str7, boolean z8, boolean z9, RewardItemParcel rewardItemParcel, List<String> list4, List<String> list5, boolean z10) {
        this(16, str, str2, list, -2, list2, j, z, j2, list3, j3, i, str3, j4, str4, z2, str5, str6, z3, z4, z5, z6, z7, i2, null, null, str7, z8, z9, rewardItemParcel, list4, list5, z10);
        this.zzCu = adRequestInfoParcel;
    }

    public final int describeContents() {
        return 0;
    }

    public final void writeToParcel(Parcel parcel, int i) {
        if (!(this.zzCu == null || this.zzCu.versionCode < 9 || TextUtils.isEmpty(this.body))) {
            this.zzIg = new LargeParcelTeleporter(new StringParcel(this.body));
            this.body = null;
        }
        zzh.zza(this, parcel, i);
    }
}
