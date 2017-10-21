package com.google.android.gms.location.places.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import android.support.annotation.Nullable;
import android.text.style.CharacterStyle;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.common.internal.zzw;
import com.google.android.gms.common.internal.zzx;
import com.google.android.gms.location.places.AutocompletePrediction;
import com.google.android.gms.location.places.AutocompletePrediction.Substring;
import java.util.Collections;
import java.util.List;

public class AutocompletePredictionEntity implements SafeParcelable, AutocompletePrediction {
    public static final Creator<AutocompletePredictionEntity> CREATOR = new zza();
    private static final List<SubstringEntity> zzaQc = Collections.emptyList();
    final int mVersionCode;
    final String zzaPH;
    final List<Integer> zzaPd;
    final String zzaQd;
    final List<SubstringEntity> zzaQe;
    final int zzaQf;
    final String zzaQg;
    final List<SubstringEntity> zzaQh;
    final String zzaQi;
    final List<SubstringEntity> zzaQj;

    public static class SubstringEntity implements SafeParcelable, Substring {
        public static final Creator<SubstringEntity> CREATOR = new zzu();
        final int mLength;
        final int mOffset;
        final int mVersionCode;

        public SubstringEntity(int i, int i2, int i3) {
            this.mVersionCode = i;
            this.mOffset = i2;
            this.mLength = i3;
        }

        public int describeContents() {
            return 0;
        }

        public boolean equals(Object obj) {
            if (this != obj) {
                if (!(obj instanceof SubstringEntity)) {
                    return false;
                }
                SubstringEntity substringEntity = (SubstringEntity) obj;
                if (!zzw.equal(Integer.valueOf(this.mOffset), Integer.valueOf(substringEntity.mOffset))) {
                    return false;
                }
                if (!zzw.equal(Integer.valueOf(this.mLength), Integer.valueOf(substringEntity.mLength))) {
                    return false;
                }
            }
            return true;
        }

        public int getLength() {
            return this.mLength;
        }

        public int getOffset() {
            return this.mOffset;
        }

        public int hashCode() {
            return zzw.hashCode(Integer.valueOf(this.mOffset), Integer.valueOf(this.mLength));
        }

        public String toString() {
            return zzw.zzy(this).zzg("offset", Integer.valueOf(this.mOffset)).zzg("length", Integer.valueOf(this.mLength)).toString();
        }

        public void writeToParcel(Parcel parcel, int i) {
            zzu.zza(this, parcel, i);
        }
    }

    AutocompletePredictionEntity(int i, String str, List<Integer> list, int i2, String str2, List<SubstringEntity> list2, String str3, List<SubstringEntity> list3, String str4, List<SubstringEntity> list4) {
        this.mVersionCode = i;
        this.zzaPH = str;
        this.zzaPd = list;
        this.zzaQf = i2;
        this.zzaQd = str2;
        this.zzaQe = list2;
        this.zzaQg = str3;
        this.zzaQh = list3;
        this.zzaQi = str4;
        this.zzaQj = list4;
    }

    public static AutocompletePredictionEntity zza(String str, List<Integer> list, int i, String str2, List<SubstringEntity> list2, String str3, List<SubstringEntity> list3, String str4, List<SubstringEntity> list4) {
        return new AutocompletePredictionEntity(0, str, list, i, (String) zzx.zzz(str2), list2, str3, list3, str4, list4);
    }

    public int describeContents() {
        return 0;
    }

    public boolean equals(Object obj) {
        if (this != obj) {
            if (!(obj instanceof AutocompletePredictionEntity)) {
                return false;
            }
            AutocompletePredictionEntity autocompletePredictionEntity = (AutocompletePredictionEntity) obj;
            if (!zzw.equal(this.zzaPH, autocompletePredictionEntity.zzaPH) || !zzw.equal(this.zzaPd, autocompletePredictionEntity.zzaPd) || !zzw.equal(Integer.valueOf(this.zzaQf), Integer.valueOf(autocompletePredictionEntity.zzaQf)) || !zzw.equal(this.zzaQd, autocompletePredictionEntity.zzaQd) || !zzw.equal(this.zzaQe, autocompletePredictionEntity.zzaQe) || !zzw.equal(this.zzaQg, autocompletePredictionEntity.zzaQg) || !zzw.equal(this.zzaQh, autocompletePredictionEntity.zzaQh) || !zzw.equal(this.zzaQi, autocompletePredictionEntity.zzaQi)) {
                return false;
            }
            if (!zzw.equal(this.zzaQj, autocompletePredictionEntity.zzaQj)) {
                return false;
            }
        }
        return true;
    }

    public /* synthetic */ Object freeze() {
        return zzzf();
    }

    public String getDescription() {
        return this.zzaQd;
    }

    public CharSequence getFullText(@Nullable CharacterStyle characterStyle) {
        return zzc.zza(this.zzaQd, this.zzaQe, characterStyle);
    }

    public List<? extends Substring> getMatchedSubstrings() {
        return this.zzaQe;
    }

    @Nullable
    public String getPlaceId() {
        return this.zzaPH;
    }

    public List<Integer> getPlaceTypes() {
        return this.zzaPd;
    }

    public CharSequence getPrimaryText(@Nullable CharacterStyle characterStyle) {
        return zzc.zza(this.zzaQg, this.zzaQh, characterStyle);
    }

    public CharSequence getSecondaryText(@Nullable CharacterStyle characterStyle) {
        return zzc.zza(this.zzaQi, this.zzaQj, characterStyle);
    }

    public int hashCode() {
        return zzw.hashCode(this.zzaPH, this.zzaPd, Integer.valueOf(this.zzaQf), this.zzaQd, this.zzaQe, this.zzaQg, this.zzaQh, this.zzaQi, this.zzaQj);
    }

    public boolean isDataValid() {
        return true;
    }

    public String toString() {
        return zzw.zzy(this).zzg("placeId", this.zzaPH).zzg("placeTypes", this.zzaPd).zzg("fullText", this.zzaQd).zzg("fullTextMatchedSubstrings", this.zzaQe).zzg("primaryText", this.zzaQg).zzg("primaryTextMatchedSubstrings", this.zzaQh).zzg("secondaryText", this.zzaQi).zzg("secondaryTextMatchedSubstrings", this.zzaQj).toString();
    }

    public void writeToParcel(Parcel parcel, int i) {
        zza.zza(this, parcel, i);
    }

    public AutocompletePrediction zzzf() {
        return this;
    }
}
