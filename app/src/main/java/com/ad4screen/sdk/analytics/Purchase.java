package com.ad4screen.sdk.analytics;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

import com.ad4screen.sdk.common.annotations.API;
import com.ad4screen.sdk.common.c.d;
import com.ad4screen.sdk.common.c.e;

import java.util.Currency;
import java.util.Locale;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

@API
public class Purchase implements Parcelable, d, Cloneable {
    public static final Creator<Purchase> CREATOR = new Creator<Purchase>() {
        public final Purchase a(Parcel parcel) {
            return new Purchase(parcel);
        }

        public final Purchase[] a(int i) {
            return new Purchase[i];
        }

        public final /* synthetic */ Object createFromParcel(Parcel parcel) {
            return a(parcel);
        }

        public final /* synthetic */ Object[] newArray(int i) {
            return a(i);
        }
    };
    public static final String KEY_CURRENCY = "currency";
    public static final String KEY_ID = "purchaseId";
    public static final String KEY_ITEMS = "items";
    public static final String KEY_TOTAL_PRICE = "total";
    private String a;
    private String b;
    private double c;
    private Item[] d;

    private Purchase(Parcel parcel) {
        this.a = parcel.readString();
        this.b = parcel.readString();
        Object readArray = parcel.readArray(getClass().getClassLoader());
        if (readArray != null) {
            this.d = new Item[readArray.length];
            System.arraycopy(readArray, 0, this.d, 0, readArray.length);
        }
        this.c = parcel.readDouble();
    }

    public Purchase(String str, String str2, double d) throws IllegalArgumentException {
        setId(str);
        setCurrency(str2);
        setTotalPrice(d);
    }

    public Purchase(String str, String str2, double d, Item[] itemArr) {
        setId(str);
        setCurrency(str2);
        setTotalPrice(d);
        setItems(itemArr);
    }

    public Object clone() throws CloneNotSupportedException {
        Purchase purchase = (Purchase) super.clone();
        if (this.d != null) {
            Item[] itemArr = new Item[this.d.length];
            for (int i = 0; i < this.d.length; i++) {
                itemArr[i] = (Item) this.d[i].clone();
            }
            purchase.d = itemArr;
        }
        return purchase;
    }

    public int describeContents() {
        return 0;
    }

    public String getCurrency() {
        return this.b;
    }

    public String getId() {
        return this.a;
    }

    public Item[] getItems() {
        return this.d;
    }

    public double getTotalPrice() {
        return this.c;
    }

    public void setCurrency(String str) throws IllegalArgumentException {
        if (str == null) {
            throw new IllegalArgumentException("Purchase currency cannot be null and should be in ISO4217 format (3 letters)");
        } else if (str.length() < 3) {
            throw new IllegalArgumentException("Purchase currency should be in ISO4217 format (3 letters)");
        } else {
            String currencyCode = Currency.getInstance(str.substring(0, 3).toUpperCase(Locale.US)).getCurrencyCode();
            if (currencyCode.equals("XXX")) {
                throw new IllegalArgumentException("Purchase currency can't be XXX (no currency)");
            }
            this.b = currencyCode;
        }
    }

    public void setId(String str) {
        if (str == null) {
            throw new IllegalArgumentException("Purchase id cannot be null");
        }
        this.a = str;
    }

    public void setItems(Item[] itemArr) {
        this.d = itemArr;
    }

    public void setTotalPrice(double d) {
        this.c = d;
    }

    public JSONObject toJSON() throws JSONException {
        JSONObject jSONObject = new JSONObject();
        jSONObject.put("currency", this.b);
        if (this.d != null) {
            JSONArray jSONArray = new JSONArray();
            e eVar = new e();
            for (int i = 0; i < this.d.length; i++) {
                JSONObject a = eVar.a(this.d[i]);
                a.remove(Item.KEY_ID);
                a.put("id", this.d[i].getId());
                jSONArray.put(a);
            }
            jSONObject.put(KEY_ITEMS, jSONArray);
        }
        jSONObject.put(KEY_TOTAL_PRICE, this.c);
        jSONObject.put(KEY_ID, this.a);
        return jSONObject;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.a);
        parcel.writeString(this.b);
        parcel.writeArray(this.d);
        parcel.writeDouble(this.c);
    }
}
