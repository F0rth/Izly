package com.ad4screen.sdk.analytics;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

import com.ad4screen.sdk.common.annotations.API;
import com.ad4screen.sdk.common.c.d;
import com.ad4screen.sdk.common.c.e;

import org.json.JSONException;
import org.json.JSONObject;

@API
public class Cart implements Parcelable, d, Cloneable {
    public static final Creator<Cart> CREATOR = new Creator<Cart>() {
        public final Cart a(Parcel parcel) {
            return new Cart(parcel);
        }

        public final Cart[] a(int i) {
            return new Cart[i];
        }

        public final /* synthetic */ Object createFromParcel(Parcel parcel) {
            return a(parcel);
        }

        public final /* synthetic */ Object[] newArray(int i) {
            return a(i);
        }
    };
    public static final String KEY_ID = "cartId";
    public static final String KEY_UNIT_PRICE = "unitPrice";
    private String a;
    private Item b;

    private Cart(Parcel parcel) {
        this.a = parcel.readString();
        this.b = (Item) parcel.readParcelable(getClass().getClassLoader());
    }

    public Cart(String str, Item item) {
        setId(str);
        setItem(item);
    }

    public Object clone() throws CloneNotSupportedException {
        Cart cart = (Cart) super.clone();
        cart.b = (Item) this.b.clone();
        return cart;
    }

    public int describeContents() {
        return 0;
    }

    public String getId() {
        return this.a;
    }

    public Item getItem() {
        return this.b;
    }

    public void setId(String str) {
        if (str == null) {
            throw new IllegalArgumentException("Cart id cannot be null");
        }
        this.a = str;
    }

    public void setItem(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("Cart Item cannot be null");
        }
        this.b = item;
    }

    public JSONObject toJSON() throws JSONException {
        JSONObject a = new e().a(this.b);
        a.put(KEY_ID, this.a);
        a.remove(Item.KEY_PRICE);
        a.put(KEY_UNIT_PRICE, this.b.getPrice());
        return a;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.a);
        parcel.writeParcelable(this.b, i);
    }
}
