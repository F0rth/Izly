package com.ad4screen.sdk.analytics;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

import com.ad4screen.sdk.common.annotations.API;
import com.ad4screen.sdk.common.c.d;

import java.util.Currency;
import java.util.Locale;

import org.json.JSONException;
import org.json.JSONObject;

@API
public class Item implements Parcelable, d, Cloneable {
    public static final Creator<Item> CREATOR = new Creator<Item>() {
        public final Item a(Parcel parcel) {
            return new Item(parcel);
        }

        public final Item[] a(int i) {
            return new Item[i];
        }

        public final /* synthetic */ Object createFromParcel(Parcel parcel) {
            return a(parcel);
        }

        public final /* synthetic */ Object[] newArray(int i) {
            return a(i);
        }
    };
    public static final String KEY_CATEGORY = "category";
    public static final String KEY_CURRENCY = "currency";
    public static final String KEY_ID = "articleId";
    public static final String KEY_LABEL = "label";
    public static final String KEY_PRICE = "price";
    public static final String KEY_QUANTITY = "quantity";
    private String a;
    private String b;
    private String c;
    private String d;
    private double e;
    private int f;

    private Item(Parcel parcel) {
        this.a = parcel.readString();
        this.c = parcel.readString();
        this.d = parcel.readString();
        this.b = parcel.readString();
        this.e = parcel.readDouble();
        this.f = parcel.readInt();
    }

    public Item(String str, String str2, String str3, String str4, double d, int i) throws IllegalArgumentException {
        setId(str);
        setLabel(str2);
        setCategory(str3);
        setCurrency(str4);
        setPrice(d);
        setQuantity(i);
    }

    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public int describeContents() {
        return 0;
    }

    public String getCategory() {
        return this.d;
    }

    public String getCurrency() {
        return this.b;
    }

    public String getId() {
        return this.a;
    }

    public String getLabel() {
        return this.c;
    }

    public double getPrice() {
        return this.e;
    }

    public int getQuantity() {
        return this.f;
    }

    public void setCategory(String str) {
        if (str == null) {
            throw new IllegalArgumentException("Item category cannot be null");
        }
        this.d = str;
    }

    public void setCurrency(String str) throws IllegalArgumentException {
        if (str == null) {
            throw new IllegalArgumentException("Item currency cannot be null and should be in ISO4217 format (3 letters)");
        } else if (str.length() < 3) {
            throw new IllegalArgumentException("Item currency should be in ISO4217 format (3 letters)");
        } else {
            String currencyCode = Currency.getInstance(str.substring(0, 3).toUpperCase(Locale.US)).getCurrencyCode();
            if (currencyCode.equals("XXX")) {
                throw new IllegalArgumentException("Item currency can't be XXX (no currency)");
            }
            this.b = currencyCode;
        }
    }

    public void setId(String str) {
        if (str == null) {
            throw new IllegalArgumentException("Item id cannot be null");
        }
        this.a = str;
    }

    public void setLabel(String str) {
        if (str == null) {
            throw new IllegalArgumentException("Item label cannot be null");
        }
        this.c = str;
    }

    public void setPrice(double d) {
        this.e = d;
    }

    public void setQuantity(int i) {
        if (i < 0) {
            throw new IllegalArgumentException("Item quantity must be positive");
        }
        this.f = i;
    }

    public JSONObject toJSON() throws JSONException {
        JSONObject jSONObject = new JSONObject();
        jSONObject.put(KEY_ID, this.a);
        jSONObject.put("label", this.c);
        jSONObject.put(KEY_CATEGORY, this.d);
        jSONObject.put("currency", this.b);
        jSONObject.put(KEY_PRICE, this.e);
        jSONObject.put(KEY_QUANTITY, this.f);
        return jSONObject;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.a);
        parcel.writeString(this.c);
        parcel.writeString(this.d);
        parcel.writeString(this.b);
        parcel.writeDouble(this.e);
        parcel.writeInt(this.f);
    }
}
