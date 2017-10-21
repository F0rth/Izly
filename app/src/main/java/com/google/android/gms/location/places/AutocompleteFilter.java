package com.google.android.gms.location.places;

import android.os.Parcel;
import android.support.annotation.Nullable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.common.internal.zzw;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class AutocompleteFilter implements SafeParcelable {
    public static final zzc CREATOR = new zzc();
    public static final int TYPE_FILTER_ADDRESS = 2;
    public static final int TYPE_FILTER_CITIES = 5;
    public static final int TYPE_FILTER_ESTABLISHMENT = 34;
    public static final int TYPE_FILTER_GEOCODE = 1007;
    public static final int TYPE_FILTER_NONE = 0;
    public static final int TYPE_FILTER_REGIONS = 4;
    final int mVersionCode;
    final boolean zzaPg;
    final List<Integer> zzaPh;
    final int zzaPi;

    public static final class Builder {
        private boolean zzaPg = false;
        private int zzaPi = 0;

        public final AutocompleteFilter build() {
            return new AutocompleteFilter(1, this.zzaPg, AutocompleteFilter.zzhJ(this.zzaPi));
        }

        public final Builder setTypeFilter(int i) {
            this.zzaPi = i;
            return this;
        }
    }

    AutocompleteFilter(int i, boolean z, List<Integer> list) {
        this.mVersionCode = i;
        this.zzaPh = list;
        this.zzaPi = zzg(list);
        if (this.mVersionCode <= 0) {
            this.zzaPg = !z;
        } else {
            this.zzaPg = z;
        }
    }

    @Deprecated
    public static AutocompleteFilter create(@Nullable Collection<Integer> collection) {
        return new Builder().setTypeFilter(zzg(collection)).build();
    }

    private static int zzg(@Nullable Collection<Integer> collection) {
        return (collection == null || collection.isEmpty()) ? 0 : ((Integer) collection.iterator().next()).intValue();
    }

    private static List<Integer> zzhJ(int i) {
        return Arrays.asList(new Integer[]{Integer.valueOf(i)});
    }

    public int describeContents() {
        return 0;
    }

    public boolean equals(Object obj) {
        if (this != obj) {
            if (!(obj instanceof AutocompleteFilter)) {
                return false;
            }
            AutocompleteFilter autocompleteFilter = (AutocompleteFilter) obj;
            if (this.zzaPi != this.zzaPi) {
                return false;
            }
            if (this.zzaPg != autocompleteFilter.zzaPg) {
                return false;
            }
        }
        return true;
    }

    @Deprecated
    public Set<Integer> getPlaceTypes() {
        return new HashSet(zzhJ(this.zzaPi));
    }

    public int getTypeFilter() {
        return this.zzaPi;
    }

    public int hashCode() {
        return zzw.hashCode(Boolean.valueOf(this.zzaPg), Integer.valueOf(this.zzaPi));
    }

    public String toString() {
        return zzw.zzy(this).zzg("includeQueryPredictions", Boolean.valueOf(this.zzaPg)).zzg("typeFilter", Integer.valueOf(this.zzaPi)).toString();
    }

    public void writeToParcel(Parcel parcel, int i) {
        zzc.zza(this, parcel, i);
    }
}
