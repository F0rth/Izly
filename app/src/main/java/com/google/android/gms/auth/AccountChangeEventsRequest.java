package com.google.android.gms.auth;

import android.accounts.Account;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import android.text.TextUtils;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;

public class AccountChangeEventsRequest implements SafeParcelable {
    public static final Creator<AccountChangeEventsRequest> CREATOR = new zzb();
    final int mVersion;
    Account zzTI;
    @Deprecated
    String zzVa;
    int zzVc;

    public AccountChangeEventsRequest() {
        this.mVersion = 1;
    }

    AccountChangeEventsRequest(int i, int i2, String str, Account account) {
        this.mVersion = i;
        this.zzVc = i2;
        this.zzVa = str;
        if (account != null || TextUtils.isEmpty(str)) {
            this.zzTI = account;
        } else {
            this.zzTI = new Account(str, "com.google");
        }
    }

    public int describeContents() {
        return 0;
    }

    public Account getAccount() {
        return this.zzTI;
    }

    public String getAccountName() {
        return this.zzVa;
    }

    public int getEventIndex() {
        return this.zzVc;
    }

    public AccountChangeEventsRequest setAccount(Account account) {
        this.zzTI = account;
        return this;
    }

    public AccountChangeEventsRequest setAccountName(String str) {
        this.zzVa = str;
        return this;
    }

    public AccountChangeEventsRequest setEventIndex(int i) {
        this.zzVc = i;
        return this;
    }

    public void writeToParcel(Parcel parcel, int i) {
        zzb.zza(this, parcel, i);
    }
}
