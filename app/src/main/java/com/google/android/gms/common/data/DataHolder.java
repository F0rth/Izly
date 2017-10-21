package com.google.android.gms.common.data;

import android.database.CharArrayBuffer;
import android.database.CursorIndexOutOfBoundsException;
import android.database.CursorWindow;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcel;
import android.util.Log;
import com.google.android.gms.common.annotation.KeepName;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.common.internal.zzx;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@KeepName
public final class DataHolder implements SafeParcelable {
    public static final zze CREATOR = new zze();
    private static final zza zzajq = new zza(new String[0], null) {
    };
    boolean mClosed;
    private final int mVersionCode;
    private final int zzade;
    private final String[] zzaji;
    Bundle zzajj;
    private final CursorWindow[] zzajk;
    private final Bundle zzajl;
    int[] zzajm;
    int zzajn;
    private Object zzajo;
    private boolean zzajp;

    public static class zza {
        private final String[] zzaji;
        private final ArrayList<HashMap<String, Object>> zzajr;
        private final String zzajs;
        private final HashMap<Object, Integer> zzajt;
        private boolean zzaju;
        private String zzajv;

        private zza(String[] strArr, String str) {
            this.zzaji = (String[]) zzx.zzz(strArr);
            this.zzajr = new ArrayList();
            this.zzajs = str;
            this.zzajt = new HashMap();
            this.zzaju = false;
            this.zzajv = null;
        }
    }

    public static class zzb extends RuntimeException {
        public zzb(String str) {
            super(str);
        }
    }

    DataHolder(int i, String[] strArr, CursorWindow[] cursorWindowArr, int i2, Bundle bundle) {
        this.mClosed = false;
        this.zzajp = true;
        this.mVersionCode = i;
        this.zzaji = strArr;
        this.zzajk = cursorWindowArr;
        this.zzade = i2;
        this.zzajl = bundle;
    }

    private DataHolder(zza com_google_android_gms_common_data_DataHolder_zza, int i, Bundle bundle) {
        this(com_google_android_gms_common_data_DataHolder_zza.zzaji, zza(com_google_android_gms_common_data_DataHolder_zza, -1), i, bundle);
    }

    public DataHolder(String[] strArr, CursorWindow[] cursorWindowArr, int i, Bundle bundle) {
        this.mClosed = false;
        this.zzajp = true;
        this.mVersionCode = 1;
        this.zzaji = (String[]) zzx.zzz(strArr);
        this.zzajk = (CursorWindow[]) zzx.zzz(cursorWindowArr);
        this.zzade = i;
        this.zzajl = bundle;
        zzqd();
    }

    public static DataHolder zza(int i, Bundle bundle) {
        return new DataHolder(zzajq, i, bundle);
    }

    private static CursorWindow[] zza(zza com_google_android_gms_common_data_DataHolder_zza, int i) {
        int size;
        int i2 = 0;
        if (com_google_android_gms_common_data_DataHolder_zza.zzaji.length == 0) {
            return new CursorWindow[0];
        }
        List zzb = (i < 0 || i >= com_google_android_gms_common_data_DataHolder_zza.zzajr.size()) ? com_google_android_gms_common_data_DataHolder_zza.zzajr : com_google_android_gms_common_data_DataHolder_zza.zzajr.subList(0, i);
        int size2 = zzb.size();
        CursorWindow cursorWindow = new CursorWindow(false);
        ArrayList arrayList = new ArrayList();
        arrayList.add(cursorWindow);
        cursorWindow.setNumColumns(com_google_android_gms_common_data_DataHolder_zza.zzaji.length);
        int i3 = 0;
        int i4 = 0;
        while (i4 < size2) {
            if (!cursorWindow.allocRow()) {
                Log.d("DataHolder", "Allocating additional cursor window for large data set (row " + i4 + ")");
                cursorWindow = new CursorWindow(false);
                cursorWindow.setStartPosition(i4);
                cursorWindow.setNumColumns(com_google_android_gms_common_data_DataHolder_zza.zzaji.length);
                arrayList.add(cursorWindow);
                if (!cursorWindow.allocRow()) {
                    Log.e("DataHolder", "Unable to allocate row to hold data.");
                    arrayList.remove(cursorWindow);
                    return (CursorWindow[]) arrayList.toArray(new CursorWindow[arrayList.size()]);
                }
            }
            try {
                CursorWindow cursorWindow2;
                int i5;
                int i6;
                Map map = (Map) zzb.get(i4);
                boolean z = true;
                for (int i7 = 0; i7 < com_google_android_gms_common_data_DataHolder_zza.zzaji.length && z; i7++) {
                    String str = com_google_android_gms_common_data_DataHolder_zza.zzaji[i7];
                    Object obj = map.get(str);
                    if (obj == null) {
                        z = cursorWindow.putNull(i4, i7);
                    } else if (obj instanceof String) {
                        z = cursorWindow.putString((String) obj, i4, i7);
                    } else if (obj instanceof Long) {
                        z = cursorWindow.putLong(((Long) obj).longValue(), i4, i7);
                    } else if (obj instanceof Integer) {
                        z = cursorWindow.putLong((long) ((Integer) obj).intValue(), i4, i7);
                    } else if (obj instanceof Boolean) {
                        z = cursorWindow.putLong(((Boolean) obj).booleanValue() ? 1 : 0, i4, i7);
                    } else if (obj instanceof byte[]) {
                        z = cursorWindow.putBlob((byte[]) obj, i4, i7);
                    } else if (obj instanceof Double) {
                        z = cursorWindow.putDouble(((Double) obj).doubleValue(), i4, i7);
                    } else if (obj instanceof Float) {
                        z = cursorWindow.putDouble((double) ((Float) obj).floatValue(), i4, i7);
                    } else {
                        throw new IllegalArgumentException("Unsupported object for column " + str + ": " + obj);
                    }
                }
                if (z) {
                    cursorWindow2 = cursorWindow;
                    i5 = i4;
                    i6 = 0;
                } else if (i3 != 0) {
                    throw new zzb("Could not add the value to a new CursorWindow. The size of value may be larger than what a CursorWindow can handle.");
                } else {
                    Log.d("DataHolder", "Couldn't populate window data for row " + i4 + " - allocating new window.");
                    cursorWindow.freeLastRow();
                    cursorWindow2 = new CursorWindow(false);
                    cursorWindow2.setStartPosition(i4);
                    cursorWindow2.setNumColumns(com_google_android_gms_common_data_DataHolder_zza.zzaji.length);
                    arrayList.add(cursorWindow2);
                    i5 = i4 - 1;
                    i6 = 1;
                }
                i4 = i5 + 1;
                i3 = i6;
                cursorWindow = cursorWindow2;
            } catch (RuntimeException e) {
                RuntimeException runtimeException = e;
                size = arrayList.size();
                while (i2 < size) {
                    ((CursorWindow) arrayList.get(i2)).close();
                    i2++;
                }
                throw runtimeException;
            }
        }
        return (CursorWindow[]) arrayList.toArray(new CursorWindow[arrayList.size()]);
    }

    public static DataHolder zzbI(int i) {
        return zza(i, null);
    }

    private void zzg(String str, int i) {
        if (this.zzajj == null || !this.zzajj.containsKey(str)) {
            throw new IllegalArgumentException("No such column: " + str);
        } else if (isClosed()) {
            throw new IllegalArgumentException("Buffer is closed.");
        } else if (i < 0 || i >= this.zzajn) {
            throw new CursorIndexOutOfBoundsException(i, this.zzajn);
        }
    }

    public final void close() {
        synchronized (this) {
            if (!this.mClosed) {
                this.mClosed = true;
                for (CursorWindow close : this.zzajk) {
                    close.close();
                }
            }
        }
    }

    public final int describeContents() {
        return 0;
    }

    protected final void finalize() throws Throwable {
        try {
            if (this.zzajp && this.zzajk.length > 0 && !isClosed()) {
                Log.e("DataBuffer", "Internal data leak within a DataBuffer object detected!  Be sure to explicitly call release() on all DataBuffer extending objects when you are done with them. (" + (this.zzajo == null ? "internal object: " + toString() : this.zzajo.toString()) + ")");
                close();
            }
            super.finalize();
        } catch (Throwable th) {
            super.finalize();
        }
    }

    public final int getCount() {
        return this.zzajn;
    }

    public final int getStatusCode() {
        return this.zzade;
    }

    final int getVersionCode() {
        return this.mVersionCode;
    }

    public final boolean isClosed() {
        boolean z;
        synchronized (this) {
            z = this.mClosed;
        }
        return z;
    }

    public final void writeToParcel(Parcel parcel, int i) {
        zze.zza(this, parcel, i);
    }

    public final void zza(String str, int i, int i2, CharArrayBuffer charArrayBuffer) {
        zzg(str, i);
        this.zzajk[i2].copyStringToBuffer(i, this.zzajj.getInt(str), charArrayBuffer);
    }

    public final long zzb(String str, int i, int i2) {
        zzg(str, i);
        return this.zzajk[i2].getLong(i, this.zzajj.getInt(str));
    }

    public final int zzbH(int i) {
        int i2 = 0;
        boolean z = i >= 0 && i < this.zzajn;
        zzx.zzab(z);
        while (i2 < this.zzajm.length) {
            if (i < this.zzajm[i2]) {
                i2--;
                break;
            }
            i2++;
        }
        return i2 == this.zzajm.length ? i2 - 1 : i2;
    }

    public final int zzc(String str, int i, int i2) {
        zzg(str, i);
        return this.zzajk[i2].getInt(i, this.zzajj.getInt(str));
    }

    public final boolean zzcz(String str) {
        return this.zzajj.containsKey(str);
    }

    public final String zzd(String str, int i, int i2) {
        zzg(str, i);
        return this.zzajk[i2].getString(i, this.zzajj.getInt(str));
    }

    public final boolean zze(String str, int i, int i2) {
        zzg(str, i);
        return Long.valueOf(this.zzajk[i2].getLong(i, this.zzajj.getInt(str))).longValue() == 1;
    }

    public final float zzf(String str, int i, int i2) {
        zzg(str, i);
        return this.zzajk[i2].getFloat(i, this.zzajj.getInt(str));
    }

    public final byte[] zzg(String str, int i, int i2) {
        zzg(str, i);
        return this.zzajk[i2].getBlob(i, this.zzajj.getInt(str));
    }

    public final Uri zzh(String str, int i, int i2) {
        String zzd = zzd(str, i, i2);
        return zzd == null ? null : Uri.parse(zzd);
    }

    public final boolean zzi(String str, int i, int i2) {
        zzg(str, i);
        return this.zzajk[i2].isNull(i, this.zzajj.getInt(str));
    }

    public final Bundle zzpZ() {
        return this.zzajl;
    }

    public final void zzqd() {
        int i;
        int i2 = 0;
        this.zzajj = new Bundle();
        for (i = 0; i < this.zzaji.length; i++) {
            this.zzajj.putInt(this.zzaji[i], i);
        }
        this.zzajm = new int[this.zzajk.length];
        for (i = 0; i < this.zzajk.length; i++) {
            this.zzajm[i] = i2;
            i2 += this.zzajk[i].getNumRows() - (i2 - this.zzajk[i].getStartPosition());
        }
        this.zzajn = i2;
    }

    final String[] zzqe() {
        return this.zzaji;
    }

    final CursorWindow[] zzqf() {
        return this.zzajk;
    }

    public final void zzu(Object obj) {
        this.zzajo = obj;
    }
}
