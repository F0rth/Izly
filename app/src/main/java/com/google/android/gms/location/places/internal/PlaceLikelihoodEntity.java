package com.google.android.gms.location.places.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.common.internal.zzw;
import com.google.android.gms.common.internal.zzx;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceLikelihood;

public class PlaceLikelihoodEntity implements SafeParcelable, PlaceLikelihood {
    public static final Creator<PlaceLikelihoodEntity> CREATOR = new zzm();
    final int mVersionCode;
    final PlaceImpl zzaQM;
    final float zzaQN;

    PlaceLikelihoodEntity(int i, PlaceImpl placeImpl, float f) {
        this.mVersionCode = i;
        this.zzaQM = placeImpl;
        this.zzaQN = f;
    }

    public static PlaceLikelihoodEntity zza(PlaceImpl placeImpl, float f) {
        return new PlaceLikelihoodEntity(0, (PlaceImpl) zzx.zzz(placeImpl), f);
    }

    public int describeContents() {
        return 0;
    }

    public boolean equals(Object obj) {
        if (this != obj) {
            if (!(obj instanceof PlaceLikelihoodEntity)) {
                return false;
            }
            PlaceLikelihoodEntity placeLikelihoodEntity = (PlaceLikelihoodEntity) obj;
            if (!this.zzaQM.equals(placeLikelihoodEntity.zzaQM)) {
                return false;
            }
            if (this.zzaQN != placeLikelihoodEntity.zzaQN) {
                return false;
            }
        }
        return true;
    }

    public /* synthetic */ Object freeze() {
        return zzzy();
    }

    public float getLikelihood() {
        return this.zzaQN;
    }

    public Place getPlace() {
        return this.zzaQM;
    }

    public int hashCode() {
        return zzw.hashCode(this.zzaQM, Float.valueOf(this.zzaQN));
    }

    public boolean isDataValid() {
        return true;
    }

    public String toString() {
        return zzw.zzy(this).zzg("place", this.zzaQM).zzg("likelihood", Float.valueOf(this.zzaQN)).toString();
    }

    public void writeToParcel(Parcel parcel, int i) {
        zzm.zza(this, parcel, i);
    }

    public PlaceLikelihood zzzy() {
        return this;
    }
}
