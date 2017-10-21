package com.google.android.gms.common.data;

import java.util.ArrayList;

public abstract class zzf<T> extends AbstractDataBuffer<T> {
    private boolean zzajw = false;
    private ArrayList<Integer> zzajx;

    protected zzf(DataHolder dataHolder) {
        super(dataHolder);
    }

    private void zzqh() {
        synchronized (this) {
            if (!this.zzajw) {
                int count = this.zzahi.getCount();
                this.zzajx = new ArrayList();
                if (count > 0) {
                    this.zzajx.add(Integer.valueOf(0));
                    String zzqg = zzqg();
                    String zzd = this.zzahi.zzd(zzqg, 0, this.zzahi.zzbH(0));
                    int i = 1;
                    while (i < count) {
                        int zzbH = this.zzahi.zzbH(i);
                        String zzd2 = this.zzahi.zzd(zzqg, i, zzbH);
                        if (zzd2 == null) {
                            throw new NullPointerException("Missing value for markerColumn: " + zzqg + ", at row: " + i + ", for window: " + zzbH);
                        }
                        if (zzd2.equals(zzd)) {
                            zzd2 = zzd;
                        } else {
                            this.zzajx.add(Integer.valueOf(i));
                        }
                        i++;
                        zzd = zzd2;
                    }
                }
                this.zzajw = true;
            }
        }
    }

    public final T get(int i) {
        zzqh();
        return zzk(zzbK(i), zzbL(i));
    }

    public int getCount() {
        zzqh();
        return this.zzajx.size();
    }

    int zzbK(int i) {
        if (i >= 0 && i < this.zzajx.size()) {
            return ((Integer) this.zzajx.get(i)).intValue();
        }
        throw new IllegalArgumentException("Position " + i + " is out of bounds for this buffer");
    }

    protected int zzbL(int i) {
        int i2;
        if (i < 0 || i == this.zzajx.size()) {
            i2 = 0;
        } else {
            i2 = i == this.zzajx.size() + -1 ? this.zzahi.getCount() - ((Integer) this.zzajx.get(i)).intValue() : ((Integer) this.zzajx.get(i + 1)).intValue() - ((Integer) this.zzajx.get(i)).intValue();
            if (i2 == 1) {
                int zzbK = zzbK(i);
                int zzbH = this.zzahi.zzbH(zzbK);
                String zzqi = zzqi();
                if (zzqi != null && this.zzahi.zzd(zzqi, zzbK, zzbH) == null) {
                    return 0;
                }
            }
        }
        return i2;
    }

    protected abstract T zzk(int i, int i2);

    protected abstract String zzqg();

    protected String zzqi() {
        return null;
    }
}
