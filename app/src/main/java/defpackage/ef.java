package defpackage;

import android.os.Bundle;
import android.os.Parcelable;
import com.slidingmenu.lib.BuildConfig;
import fr.smoney.android.izly.data.model.Contact;
import fr.smoney.android.izly.data.model.Contact.b;
import fr.smoney.android.izly.data.model.GetNewsFeedDetailsData;
import fr.smoney.android.izly.data.model.NewsFeedItem;
import fr.smoney.android.izly.data.model.Operation;
import fr.smoney.android.izly.data.model.P2PPayRequest;
import fr.smoney.android.izly.data.model.PreAuthorizationContainerData;
import fr.smoney.android.izly.data.model.PreAuthorizationContainerData.PreAuthorization;
import fr.smoney.android.izly.data.model.PromotionalOffer;
import fr.smoney.android.izly.data.model.PromotionalOffer.a;
import fr.smoney.android.izly.data.model.ServerError;
import java.io.IOException;
import java.util.Locale;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public final class ef {
    public static Bundle a(String str, String str2, NewsFeedItem newsFeedItem) throws IOException, JSONException {
        Parcelable serverError;
        Parcelable parcelable = null;
        cq cqVar = new cq("https://rest.izly.fr/Service/PublicService.svc/rest/GetNewsFeedDetail");
        cqVar.a(BuildConfig.VERSION_NAME);
        cqVar.a("language", Locale.getDefault().getLanguage());
        cqVar.a("userId", str);
        cqVar.a("sessionId", str2);
        JSONObject jSONObject = new JSONObject();
        jSONObject.put("operationId", newsFeedItem.c);
        jSONObject.put("operationType", newsFeedItem.d.x);
        cqVar.b(jSONObject.toString());
        String a = cqVar.a(1);
        Bundle bundle = new Bundle();
        JSONObject jSONObject2 = new JSONObject(a);
        if (!jSONObject2.isNull("ErrorMessage")) {
            serverError = new ServerError();
            serverError.b = jSONObject2.getInt("Code");
            serverError.c = jSONObject2.getString("ErrorMessage");
            serverError.e = jSONObject2.getInt("Priority");
            serverError.d = jSONObject2.getString("Title");
        } else if (jSONObject2.isNull("GetNewsFeedDetailResult")) {
            serverError = null;
        } else {
            GetNewsFeedDetailsData getNewsFeedDetailsData = new GetNewsFeedDetailsData();
            Object obj;
            if (newsFeedItem.c()) {
                getNewsFeedDetailsData.a = aq.a(jSONObject2.getJSONObject("GetNewsFeedDetailResult"));
                serverError = null;
                obj = getNewsFeedDetailsData;
            } else if (newsFeedItem.e()) {
                getNewsFeedDetailsData.a = aq.b(jSONObject2.getJSONObject("GetNewsFeedDetailResult"));
                serverError = null;
                obj = getNewsFeedDetailsData;
            } else if (newsFeedItem.d()) {
                jSONObject2 = jSONObject2.getJSONObject("GetNewsFeedDetailResult");
                Operation p2PPayRequest = new P2PPayRequest();
                p2PPayRequest.a = jSONObject2.getLong("Id");
                p2PPayRequest.b = !jSONObject2.getBoolean("IsNew");
                if (!jSONObject2.isNull("Message")) {
                    p2PPayRequest.k = jSONObject2.getString("Message");
                }
                p2PPayRequest.n = jSONObject2.getInt("Status");
                p2PPayRequest.c = jk.a(jSONObject2.getString("RequestDate"));
                if (jSONObject2.isNull("ResponseDate")) {
                    p2PPayRequest.m = -1;
                } else {
                    p2PPayRequest.m = jk.a(jSONObject2.getString("ResponseDate"));
                }
                if (!jSONObject2.isNull("ResponseMessage")) {
                    p2PPayRequest.l = jSONObject2.getString("ResponseMessage");
                }
                if (jSONObject2.isNull("Amount")) {
                    p2PPayRequest.h = -1.0d;
                    p2PPayRequest.g = true;
                } else {
                    r0 = jSONObject2.getJSONObject("Amount");
                    if (r0.isNull("AmountTTC")) {
                        p2PPayRequest.h = -1.0d;
                        p2PPayRequest.g = true;
                    } else {
                        p2PPayRequest.h = r0.getDouble("AmountTTC");
                        p2PPayRequest.g = false;
                    }
                }
                if (!jSONObject2.isNull("Receiver")) {
                    r0 = jSONObject2.getJSONObject("Receiver");
                    if (!r0.isNull("ProfileFirstName")) {
                        p2PPayRequest.e = r0.getString("ProfileFirstName");
                    }
                    if (!r0.isNull("ProfileLastName")) {
                        p2PPayRequest.f = r0.getString("ProfileLastName");
                    }
                    p2PPayRequest.d = r0.getString("Identifier");
                    p2PPayRequest.q = r0.getBoolean("IsSmoneyUser");
                    if (p2PPayRequest.q) {
                        p2PPayRequest.r = r0.getBoolean("IsSmoneyPro");
                    }
                    r0 = r0.optJSONObject("ProInfos");
                    if (r0 != null) {
                        p2PPayRequest.u = r0.optBoolean("IsCaritative");
                    }
                }
                if (!jSONObject2.isNull("Attachment")) {
                    r0 = jSONObject2.getJSONObject("Attachment");
                    p2PPayRequest.p = r0.getString("Id");
                    p2PPayRequest.o = r0.getString("Name");
                }
                getNewsFeedDetailsData.a = p2PPayRequest;
                serverError = null;
                obj = getNewsFeedDetailsData;
            } else if (newsFeedItem.b()) {
                r0 = jSONObject2.getJSONObject("GetNewsFeedDetailResult");
                PromotionalOffer promotionalOffer = new PromotionalOffer();
                JSONArray optJSONArray = r0.optJSONArray("PromotionalOffers");
                if (optJSONArray != null) {
                    r2 = optJSONArray.optJSONObject(0);
                    promotionalOffer.a = r2.optInt("Id");
                    promotionalOffer.b = r2.optBoolean("IsRead");
                    promotionalOffer.c = jd.a(r2, "Title");
                    promotionalOffer.e = jd.a(r2, "Description");
                    promotionalOffer.d = jd.a(r2, "SubTitle");
                    promotionalOffer.f = jd.a(r2, "Url");
                    promotionalOffer.g = jk.a(jd.a(r2, "PublicationStartDate"));
                    promotionalOffer.h = jk.a(jd.a(r2, "PublicationEndDate"));
                    promotionalOffer.k = jd.a(r0, "DisplayName");
                    promotionalOffer.j = jd.a(r0, "Identifier");
                    promotionalOffer.m = a.GEOLOCALIZED;
                    promotionalOffer.i = jd.a(r2, "Activity");
                    r0 = r0.optJSONObject("Coordinates");
                    if (r0 != null) {
                        promotionalOffer.l = r0.optDouble("Distance");
                    }
                }
                getNewsFeedDetailsData.b = promotionalOffer;
                serverError = null;
                obj = getNewsFeedDetailsData;
            } else {
                if (newsFeedItem.f()) {
                    r0 = jSONObject2.getJSONObject("GetNewsFeedDetailResult");
                    PreAuthorizationContainerData preAuthorizationContainerData = new PreAuthorizationContainerData();
                    if (r0 != null) {
                        preAuthorizationContainerData.b = r0.getDouble("Amount");
                        preAuthorizationContainerData.c = jk.a(r0.getString("ExpiryDate"));
                        r2 = r0.optJSONObject("PreAuthorization");
                        if (r2 != null) {
                            PreAuthorization preAuthorization = new PreAuthorization();
                            preAuthorization.a = jd.a(r2, "Identifier");
                            preAuthorization.b = r2.optInt("IdentifierType");
                            preAuthorizationContainerData.d = preAuthorization;
                        }
                        r2 = r0.optJSONObject("Pro");
                        if (r2 != null) {
                            Contact contact = new Contact();
                            contact.b = jd.a(r2, "DisplayName");
                            contact.a = jd.a(r2, "Identifier");
                            boolean optBoolean = r2.optBoolean("IsSmoneyUser");
                            boolean optBoolean2 = r2.optBoolean("IsSmoneyPro");
                            if (optBoolean) {
                                contact.f = b.SmoneyUserPart;
                            }
                            if (optBoolean2) {
                                contact.f = b.SmoneyUserPro;
                            }
                            preAuthorizationContainerData.a = contact;
                        }
                        preAuthorizationContainerData.e = PreAuthorizationContainerData.a(r0.optInt("Status"));
                    }
                    getNewsFeedDetailsData.c = preAuthorizationContainerData;
                }
                serverError = null;
                obj = getNewsFeedDetailsData;
            }
        }
        if (serverError != null) {
            bundle.putParcelable("fr.smoney.android.izly.extras.serverError", serverError);
        }
        if (parcelable != null) {
            bundle.putParcelable("fr.smoney.android.izly.extras.GetNewsFeedDetails", parcelable);
        }
        return bundle;
    }
}
