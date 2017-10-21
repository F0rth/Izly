package com.google.android.gms.common.api;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.IntentSender.SendIntentException;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.common.internal.zzw;

public final class Status implements Result, SafeParcelable {
    public static final Creator<Status> CREATOR = new zzc();
    public static final Status zzagC = new Status(0);
    public static final Status zzagD = new Status(14);
    public static final Status zzagE = new Status(8);
    public static final Status zzagF = new Status(15);
    public static final Status zzagG = new Status(16);
    private final PendingIntent mPendingIntent;
    private final int mVersionCode;
    private final int zzade;
    private final String zzafC;

    public Status(int i) {
        this(i, null);
    }

    Status(int i, int i2, String str, PendingIntent pendingIntent) {
        this.mVersionCode = i;
        this.zzade = i2;
        this.zzafC = str;
        this.mPendingIntent = pendingIntent;
    }

    public Status(int i, String str) {
        this(1, i, str, null);
    }

    public Status(int i, String str, PendingIntent pendingIntent) {
        this(1, i, str, pendingIntent);
    }

    private String zzpd() {
        return this.zzafC != null ? this.zzafC : CommonStatusCodes.getStatusCodeString(this.zzade);
    }

    public final int describeContents() {
        return 0;
    }

    public final boolean equals(Object obj) {
        if (obj instanceof Status) {
            Status status = (Status) obj;
            if (this.mVersionCode == status.mVersionCode && this.zzade == status.zzade && zzw.equal(this.zzafC, status.zzafC) && zzw.equal(this.mPendingIntent, status.mPendingIntent)) {
                return true;
            }
        }
        return false;
    }

    public final PendingIntent getResolution() {
        return this.mPendingIntent;
    }

    public final Status getStatus() {
        return this;
    }

    public final int getStatusCode() {
        return this.zzade;
    }

    public final String getStatusMessage() {
        return this.zzafC;
    }

    final int getVersionCode() {
        return this.mVersionCode;
    }

    public final boolean hasResolution() {
        return this.mPendingIntent != null;
    }

    public final int hashCode() {
        return zzw.hashCode(Integer.valueOf(this.mVersionCode), Integer.valueOf(this.zzade), this.zzafC, this.mPendingIntent);
    }

    public final boolean isCanceled() {
        return this.zzade == 16;
    }

    public final boolean isInterrupted() {
        return this.zzade == 14;
    }

    public final boolean isSuccess() {
        return this.zzade <= 0;
    }

    public final void startResolutionForResult(Activity activity, int i) throws SendIntentException {
        if (hasResolution()) {
            activity.startIntentSenderForResult(this.mPendingIntent.getIntentSender(), i, null, 0, 0, 0);
        }
    }

    public final String toString() {
        return zzw.zzy(this).zzg("statusCode", zzpd()).zzg("resolution", this.mPendingIntent).toString();
    }

    public final void writeToParcel(Parcel parcel, int i) {
        zzc.zza(this, parcel, i);
    }

    final PendingIntent zzpc() {
        return this.mPendingIntent;
    }
}
