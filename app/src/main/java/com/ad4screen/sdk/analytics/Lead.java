package com.ad4screen.sdk.analytics;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

import com.ad4screen.sdk.common.annotations.API;
import com.ad4screen.sdk.common.c.d;

import org.json.JSONException;
import org.json.JSONObject;

@API
public class Lead implements Parcelable, d, Cloneable {
    public static final Creator<Lead> CREATOR = new Creator<Lead>() {
        public final Lead a(Parcel parcel) {
            return new Lead(parcel);
        }

        public final Lead[] a(int i) {
            return new Lead[i];
        }

        public final /* synthetic */ Object createFromParcel(Parcel parcel) {
            return a(parcel);
        }

        public final /* synthetic */ Object[] newArray(int i) {
            return a(i);
        }
    };
    public static final String KEY_LABEL = "label";
    public static final String KEY_VALUE = "value";
    private String a;
    private String b;

    private Lead(Parcel parcel) {
        this.a = parcel.readString();
        this.b = parcel.readString();
    }

    public Lead(String str, String str2) {
        setValue(str2);
        setLabel(str);
    }

    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public int describeContents() {
        return 0;
    }

    public String getLabel() {
        return this.a;
    }

    public String getValue() {
        return this.b;
    }

    public void setLabel(String str) {
        if (str == null) {
            throw new IllegalArgumentException("Lead label cannot be null");
        }
        this.a = str;
    }

    public void setValue(String str) {
        if (str == null) {
            throw new IllegalArgumentException("Lead value cannot be null");
        }
        this.b = str;
    }

    public JSONObject toJSON() throws JSONException {
        JSONObject jSONObject = new JSONObject();
        jSONObject.put("label", this.a);
        jSONObject.put(KEY_VALUE, this.b);
        return jSONObject;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.a);
        parcel.writeString(this.b);
    }
}
