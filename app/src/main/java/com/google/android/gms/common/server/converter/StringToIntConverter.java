package com.google.android.gms.common.server.converter;

import android.os.Parcel;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.common.server.response.FastJsonResponse.zza;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public final class StringToIntConverter implements SafeParcelable, zza<String, Integer> {
    public static final zzb CREATOR = new zzb();
    private final int mVersionCode;
    private final HashMap<String, Integer> zzamG;
    private final HashMap<Integer, String> zzamH;
    private final ArrayList<Entry> zzamI;

    public static final class Entry implements SafeParcelable {
        public static final zzc CREATOR = new zzc();
        final int versionCode;
        final String zzamJ;
        final int zzamK;

        Entry(int i, String str, int i2) {
            this.versionCode = i;
            this.zzamJ = str;
            this.zzamK = i2;
        }

        Entry(String str, int i) {
            this.versionCode = 1;
            this.zzamJ = str;
            this.zzamK = i;
        }

        public final int describeContents() {
            return 0;
        }

        public final void writeToParcel(Parcel parcel, int i) {
            zzc.zza(this, parcel, i);
        }
    }

    public StringToIntConverter() {
        this.mVersionCode = 1;
        this.zzamG = new HashMap();
        this.zzamH = new HashMap();
        this.zzamI = null;
    }

    StringToIntConverter(int i, ArrayList<Entry> arrayList) {
        this.mVersionCode = i;
        this.zzamG = new HashMap();
        this.zzamH = new HashMap();
        this.zzamI = null;
        zzd(arrayList);
    }

    private void zzd(ArrayList<Entry> arrayList) {
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            Entry entry = (Entry) it.next();
            zzh(entry.zzamJ, entry.zzamK);
        }
    }

    public final /* synthetic */ Object convertBack(Object obj) {
        return zzb((Integer) obj);
    }

    public final int describeContents() {
        return 0;
    }

    final int getVersionCode() {
        return this.mVersionCode;
    }

    public final void writeToParcel(Parcel parcel, int i) {
        zzb.zza(this, parcel, i);
    }

    public final String zzb(Integer num) {
        String str = (String) this.zzamH.get(num);
        return (str == null && this.zzamG.containsKey("gms_unknown")) ? "gms_unknown" : str;
    }

    public final StringToIntConverter zzh(String str, int i) {
        this.zzamG.put(str, Integer.valueOf(i));
        this.zzamH.put(Integer.valueOf(i), str);
        return this;
    }

    final ArrayList<Entry> zzri() {
        ArrayList<Entry> arrayList = new ArrayList();
        for (String str : this.zzamG.keySet()) {
            arrayList.add(new Entry(str, ((Integer) this.zzamG.get(str)).intValue()));
        }
        return arrayList;
    }

    public final int zzrj() {
        return 7;
    }

    public final int zzrk() {
        return 0;
    }
}
