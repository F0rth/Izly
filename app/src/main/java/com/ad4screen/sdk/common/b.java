package com.ad4screen.sdk.common;

import android.os.Bundle;

import com.ad4screen.sdk.Log;
import com.ad4screen.sdk.analytics.Cart;
import com.ad4screen.sdk.analytics.Item;
import com.ad4screen.sdk.analytics.Lead;
import com.ad4screen.sdk.analytics.Purchase;

import java.util.Iterator;

import org.json.JSONArray;
import org.json.JSONObject;

public class b {
    public static Lead a(String str) {
        try {
            JSONObject jSONObject = new JSONObject(str);
            return new Lead(jSONObject.getString("label"), jSONObject.getString(Lead.KEY_VALUE));
        } catch (Throwable e) {
            Log.error("Lead|Error during json parsing", e);
            return null;
        }
    }

    public static Cart b(String str) {
        try {
            JSONObject jSONObject = new JSONObject(str);
            String string = jSONObject.getString(Cart.KEY_ID);
            int i = jSONObject.getInt(Item.KEY_QUANTITY);
            String string2 = jSONObject.getString(Item.KEY_CATEGORY);
            return new Cart(string, new Item(jSONObject.getString(Item.KEY_ID), jSONObject.getString("label"), string2, jSONObject.getString("currency"), jSONObject.getDouble(Item.KEY_PRICE), i));
        } catch (Throwable e) {
            Log.error("Cart|Error during json parsing", e);
            return null;
        }
    }

    public static Purchase c(String str) {
        try {
            JSONObject jSONObject = new JSONObject(str);
            String string = jSONObject.getString(Purchase.KEY_ID);
            double d = jSONObject.getDouble("totalPrice");
            String string2 = jSONObject.getString("currency");
            JSONArray jSONArray = jSONObject.isNull(Purchase.KEY_ITEMS) ? null : jSONObject.getJSONArray(Purchase.KEY_ITEMS);
            if (jSONArray == null) {
                return new Purchase(string, string2, d);
            }
            int length = jSONArray.length();
            Item[] itemArr = new Item[length];
            for (int i = 0; i < length; i++) {
                JSONObject jSONObject2 = jSONArray.getJSONObject(i);
                String string3 = jSONObject2.getString("id");
                int i2 = jSONObject2.getInt(Item.KEY_QUANTITY);
                itemArr[i] = new Item(string3, jSONObject2.getString("label"), jSONObject2.getString(Item.KEY_CATEGORY), jSONObject2.getString("currency"), jSONObject2.getDouble(Item.KEY_PRICE), i2);
            }
            return new Purchase(string, string2, d, itemArr);
        } catch (Throwable e) {
            Log.error("Purchase|Error during json parsing", e);
            return null;
        }
    }

    public static Bundle d(String str) {
        try {
            Bundle bundle = new Bundle();
            JSONObject jSONObject = new JSONObject(str);
            Iterator keys = jSONObject.keys();
            while (keys.hasNext()) {
                String str2 = (String) keys.next();
                bundle.putString(str2, jSONObject.getString(str2));
            }
            return bundle;
        } catch (Throwable e) {
            Log.error("UpdateDeviceInfo|Error during json parsing", e);
            return null;
        }
    }
}
