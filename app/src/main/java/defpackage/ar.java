package defpackage;

import android.os.Bundle;
import android.os.Parcelable;
import android.text.Html;
import fr.smoney.android.izly.data.model.BalanceData;
import fr.smoney.android.izly.data.model.GetNewsFeedData;
import fr.smoney.android.izly.data.model.NewsFeedChatItem;
import fr.smoney.android.izly.data.model.NewsFeedCommissionOrPass;
import fr.smoney.android.izly.data.model.NewsFeedContactLight;
import fr.smoney.android.izly.data.model.NewsFeedEcommerce;
import fr.smoney.android.izly.data.model.NewsFeedItem;
import fr.smoney.android.izly.data.model.NewsFeedItem.a;
import fr.smoney.android.izly.data.model.NewsFeedMoneyDemandFeedItem;
import fr.smoney.android.izly.data.model.NewsFeedMoneyOperationFeedItem;
import fr.smoney.android.izly.data.model.NewsFeedPaymentFeedItem;
import fr.smoney.android.izly.data.model.NewsFeedPreAuthorization;
import fr.smoney.android.izly.data.model.NewsFeedPromoOfferFeedItem;
import fr.smoney.android.izly.data.model.ServerError;
import java.text.ParseException;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public final class ar {
    public static Bundle a(String str, boolean z) throws JSONException, ParseException {
        Parcelable parcelable;
        Parcelable parcelable2 = null;
        Bundle bundle = new Bundle();
        JSONObject jSONObject = new JSONObject(str);
        if (jSONObject.isNull("ErrorMessage")) {
            GetNewsFeedData getNewsFeedData = new GetNewsFeedData();
            jSONObject = jSONObject.optJSONObject("GetNewsFeedResult");
            if (jSONObject != null) {
                if (!jSONObject.isNull("UP")) {
                    getNewsFeedData.a = new BalanceData();
                    JSONObject jSONObject2 = jSONObject.getJSONObject("UP");
                    getNewsFeedData.a.a = Double.parseDouble(jSONObject2.getString("BAL"));
                    getNewsFeedData.a.b = Double.parseDouble(jSONObject2.getString("CASHBAL"));
                    getNewsFeedData.a.c = ae.a.parse(jSONObject2.getString("LUD")).getTime();
                }
                JSONArray optJSONArray = jSONObject.optJSONArray("Result");
                if (optJSONArray != null) {
                    int length = optJSONArray.length();
                    for (int i = 0; i < length; i++) {
                        JSONObject optJSONObject = optJSONArray.optJSONObject(i);
                        a b = NewsFeedItem.b(optJSONObject.optInt("OperationType"));
                        if (b != null) {
                            List list;
                            NewsFeedItem newsFeedChatItem;
                            NewsFeedContactLight newsFeedContactLight;
                            NewsFeedPaymentFeedItem newsFeedPaymentFeedItem;
                            switch (ar$1.a[b.ordinal()]) {
                                case 1:
                                    list = getNewsFeedData.f;
                                    newsFeedChatItem = new NewsFeedChatItem();
                                    ar.a(newsFeedChatItem, optJSONObject);
                                    newsFeedChatItem.a = NewsFeedChatItem.a(optJSONObject.optInt("Direction"));
                                    optJSONObject = optJSONObject.optJSONObject("Contact");
                                    if (optJSONObject != null) {
                                        newsFeedContactLight = new NewsFeedContactLight();
                                        newsFeedContactLight.b = jd.a(optJSONObject, "DisplayName");
                                        newsFeedContactLight.a = jd.a(optJSONObject, "Identifier");
                                        newsFeedContactLight.d = optJSONObject.optBoolean("IsSmoneyPro");
                                        newsFeedContactLight.c = optJSONObject.optBoolean("IsSmoneyUser");
                                        newsFeedChatItem.e = newsFeedContactLight;
                                    }
                                    list.add(newsFeedChatItem);
                                    break;
                                case 2:
                                case 3:
                                case 4:
                                case 5:
                                case 6:
                                case 7:
                                case 8:
                                    list = getNewsFeedData.f;
                                    newsFeedChatItem = new NewsFeedMoneyOperationFeedItem();
                                    ar.a(newsFeedChatItem, optJSONObject);
                                    if (!optJSONObject.isNull("Amount")) {
                                        newsFeedChatItem.a = optJSONObject.optDouble("Amount");
                                    }
                                    list.add(newsFeedChatItem);
                                    break;
                                case 9:
                                case 10:
                                case 11:
                                case 12:
                                case 13:
                                    list = getNewsFeedData.f;
                                    newsFeedPaymentFeedItem = new NewsFeedPaymentFeedItem();
                                    ar.a((NewsFeedItem) newsFeedPaymentFeedItem, optJSONObject);
                                    ar.a(newsFeedPaymentFeedItem, optJSONObject);
                                    list.add(newsFeedPaymentFeedItem);
                                    break;
                                case 14:
                                    list = getNewsFeedData.f;
                                    newsFeedPaymentFeedItem = new NewsFeedMoneyDemandFeedItem();
                                    ar.a((NewsFeedItem) newsFeedPaymentFeedItem, optJSONObject);
                                    ar.a(newsFeedPaymentFeedItem, optJSONObject);
                                    newsFeedPaymentFeedItem.a = optJSONObject.optInt("NbRequests");
                                    list.add(newsFeedPaymentFeedItem);
                                    break;
                                case 15:
                                    list = getNewsFeedData.f;
                                    newsFeedChatItem = new NewsFeedPromoOfferFeedItem();
                                    ar.a(newsFeedChatItem, optJSONObject);
                                    newsFeedChatItem.g = Html.fromHtml(jd.a(optJSONObject, "Activity")).toString();
                                    newsFeedChatItem.h = Html.fromHtml(jd.a(optJSONObject, "SubTitle")).toString();
                                    newsFeedChatItem.a = Html.fromHtml(jd.a(optJSONObject, "Title")).toString();
                                    newsFeedChatItem.i = jd.a(optJSONObject, "Url");
                                    newsFeedChatItem.j = NewsFeedPromoOfferFeedItem.a(optJSONObject.optInt("OfferType"));
                                    list.add(newsFeedChatItem);
                                    break;
                                case 16:
                                case 17:
                                    list = getNewsFeedData.f;
                                    newsFeedPaymentFeedItem = new NewsFeedEcommerce();
                                    ar.a((NewsFeedItem) newsFeedPaymentFeedItem, optJSONObject);
                                    ar.a(newsFeedPaymentFeedItem, optJSONObject);
                                    newsFeedPaymentFeedItem.a = jd.a(optJSONObject, "Reference");
                                    list.add(newsFeedPaymentFeedItem);
                                    break;
                                case 18:
                                case 19:
                                case 20:
                                    list = getNewsFeedData.f;
                                    newsFeedChatItem = new NewsFeedCommissionOrPass();
                                    ar.a(newsFeedChatItem, optJSONObject);
                                    if (!optJSONObject.isNull("Amount")) {
                                        newsFeedChatItem.a = optJSONObject.optDouble("Amount");
                                    }
                                    list.add(newsFeedChatItem);
                                    break;
                                case 21:
                                    list = getNewsFeedData.f;
                                    newsFeedChatItem = new NewsFeedPreAuthorization();
                                    ar.a(newsFeedChatItem, optJSONObject);
                                    optJSONObject = optJSONObject.optJSONObject("Contact");
                                    if (optJSONObject != null) {
                                        newsFeedContactLight = new NewsFeedContactLight();
                                        newsFeedContactLight.b = jd.a(optJSONObject, "DisplayName");
                                        newsFeedContactLight.a = jd.a(optJSONObject, "Identifier");
                                        newsFeedContactLight.d = optJSONObject.optBoolean("IsSmoneyPro");
                                        newsFeedContactLight.c = optJSONObject.optBoolean("IsSmoneyUser");
                                        newsFeedChatItem.e = newsFeedContactLight;
                                    }
                                    list.add(newsFeedChatItem);
                                    break;
                                default:
                                    break;
                            }
                        }
                    }
                }
            }
            parcelable2 = getNewsFeedData;
            parcelable = null;
        } else {
            parcelable = new ServerError();
            parcelable.b = jSONObject.getInt("Code");
            parcelable.c = jSONObject.getString("ErrorMessage");
            parcelable.e = jSONObject.getInt("Priority");
            parcelable.d = jSONObject.getString("Title");
        }
        if (parcelable != null) {
            bundle.putParcelable("fr.smoney.android.izly.extras.serverError", parcelable);
        }
        if (parcelable2 != null) {
            bundle.putParcelable("fr.smoney.android.izly.extras.GetNewsFeed", parcelable2);
            bundle.putBoolean("fr.smoney.android.izly.extras.GetNewsFeedIsRefresh", z);
        }
        return bundle;
    }

    private static void a(NewsFeedItem newsFeedItem, JSONObject jSONObject) {
        newsFeedItem.c = jSONObject.optLong("OperationId");
        newsFeedItem.d = NewsFeedItem.b(jSONObject.optInt("OperationType"));
        newsFeedItem.f = jk.a(jSONObject.optString("OperationDate"));
    }

    private static void a(NewsFeedPaymentFeedItem newsFeedPaymentFeedItem, JSONObject jSONObject) {
        if (!jSONObject.isNull("Amount")) {
            newsFeedPaymentFeedItem.g = jSONObject.optDouble("Amount");
        }
        newsFeedPaymentFeedItem.i = jSONObject.optBoolean("HasMessages");
        newsFeedPaymentFeedItem.h = jSONObject.optBoolean("HasAttachment");
        JSONObject optJSONObject = jSONObject.optJSONObject("Contact");
        if (optJSONObject != null) {
            NewsFeedContactLight newsFeedContactLight = new NewsFeedContactLight();
            newsFeedContactLight.b = jd.a(optJSONObject, "DisplayName");
            newsFeedContactLight.a = jd.a(optJSONObject, "Identifier");
            newsFeedContactLight.d = optJSONObject.optBoolean("IsSmoneyPro");
            newsFeedContactLight.c = optJSONObject.optBoolean("IsSmoneyUser");
            newsFeedPaymentFeedItem.e = newsFeedContactLight;
        }
    }
}
