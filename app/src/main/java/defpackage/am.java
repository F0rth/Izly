package defpackage;

import android.os.Bundle;
import android.os.Parcelable;
import fr.smoney.android.izly.data.model.Contact;
import fr.smoney.android.izly.data.model.GetContactDetailsData;
import fr.smoney.android.izly.data.model.NearPro.Tills;
import fr.smoney.android.izly.data.model.PreAuthorizationContainerData;
import fr.smoney.android.izly.data.model.PreAuthorizationContainerData.PreAuthorization;
import fr.smoney.android.izly.data.model.ProInfos;
import fr.smoney.android.izly.data.model.ProProduct;
import fr.smoney.android.izly.data.model.PromotionalOffer;
import fr.smoney.android.izly.data.model.PromotionalOffer.a;
import fr.smoney.android.izly.data.model.ServerError;
import fr.smoney.android.izly.data.model.Transaction;
import java.text.ParseException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public final class am {
    public static Bundle a(String str) throws JSONException, ParseException {
        Parcelable serverError;
        Parcelable parcelable = null;
        int i = 0;
        JSONObject jSONObject = new JSONObject(str);
        if (!jSONObject.isNull("ErrorMessage")) {
            serverError = new ServerError();
            serverError.b = jSONObject.getInt("Code");
            serverError.c = jSONObject.getString("ErrorMessage");
            serverError.e = jSONObject.getInt("Priority");
            serverError.d = jSONObject.getString("Title");
        } else if (jSONObject.isNull("GetContactDetailsResult")) {
            serverError = null;
        } else {
            jSONObject = jSONObject.getJSONObject("GetContactDetailsResult");
            GetContactDetailsData getContactDetailsData = new GetContactDetailsData();
            JSONObject optJSONObject = jSONObject.optJSONObject("UP");
            if (optJSONObject != null) {
                getContactDetailsData.c.a = optJSONObject.optDouble("BAL");
                getContactDetailsData.c.c = ae.a.parse(optJSONObject.getString("LUD")).getTime();
            }
            optJSONObject = jSONObject.optJSONObject("Result");
            if (optJSONObject != null) {
                int i2;
                JSONObject optJSONObject2;
                if (!optJSONObject.isNull("ActiveAlias")) {
                    getContactDetailsData.d = optJSONObject.getString("ActiveAlias");
                }
                if (!optJSONObject.isNull("Actions")) {
                    jSONObject = optJSONObject.getJSONObject("Actions");
                    getContactDetailsData.m = jSONObject.getBoolean("IsBookmarkable");
                    getContactDetailsData.k = jSONObject.getBoolean("IsBlockable");
                }
                if (!optJSONObject.isNull("ActivePhone")) {
                    jSONObject = optJSONObject.getJSONObject("ActivePhone");
                    getContactDetailsData.e = jSONObject.optString("PhoneDisplayNumber");
                    getContactDetailsData.f = jSONObject.optString("PhoneInternationalNumber");
                }
                getContactDetailsData.g = optJSONObject.optBoolean("CanDisplayPhoto");
                getContactDetailsData.a = optJSONObject.optString("DisplayName");
                getContactDetailsData.i = optJSONObject.optBoolean("HasPhoto");
                getContactDetailsData.b = optJSONObject.optString("Identifier");
                getContactDetailsData.j = optJSONObject.optBoolean("IsBlocked");
                getContactDetailsData.l = optJSONObject.optBoolean("IsBookmarked");
                getContactDetailsData.n = optJSONObject.optBoolean("IsMe");
                getContactDetailsData.o = optJSONObject.optBoolean("IsSmoneyPro");
                getContactDetailsData.p = optJSONObject.optBoolean("IsSmoneyUser");
                getContactDetailsData.F = optJSONObject.optBoolean("OptIn");
                getContactDetailsData.G = optJSONObject.optBoolean("OptInPartners");
                getContactDetailsData.h = optJSONObject.optString("SecondDisplayName");
                JSONArray optJSONArray = optJSONObject.optJSONArray("Operations");
                if (optJSONArray != null) {
                    int length = optJSONArray.length();
                    for (i2 = 0; i2 < length; i2++) {
                        optJSONObject2 = optJSONArray.optJSONObject(i2);
                        if (optJSONObject2 != null) {
                            Transaction transaction = new Transaction();
                            transaction.b = optJSONObject2.optLong("Id");
                            transaction.n = optJSONObject2.optInt("Type");
                            transaction.k = jk.a(optJSONObject2.getString("Date"));
                            transaction.g = optJSONObject2.optDouble("Amount");
                            getContactDetailsData.H.add(transaction);
                        }
                    }
                }
                JSONObject optJSONObject3 = optJSONObject.optJSONObject("ProInfos");
                if (optJSONObject3 != null) {
                    JSONArray jSONArray;
                    int length2;
                    JSONObject jSONObject2;
                    ProInfos proInfos = new ProInfos();
                    if (!optJSONObject3.isNull("Coordinates")) {
                        jSONObject = optJSONObject3.getJSONObject("Coordinates");
                        proInfos.d = jSONObject.optDouble("Distance");
                        proInfos.e = jSONObject.optDouble("Latitude");
                        proInfos.f = jSONObject.optDouble("Longitude");
                    }
                    proInfos.a = optJSONObject3.optBoolean("Accessibility");
                    proInfos.b = jd.a(optJSONObject3, "Activity");
                    proInfos.c = jd.a(optJSONObject3, "CommercialMessage");
                    proInfos.g = optJSONObject3.optBoolean("IsPremium");
                    proInfos.h = optJSONObject3.optBoolean("IsCaritative");
                    proInfos.m = jd.a(optJSONObject3, "Siret");
                    proInfos.n = jd.a(optJSONObject3, "WebSite");
                    proInfos.i = optJSONObject3.optInt("ProActivity");
                    if (!optJSONObject3.isNull("PreAuthorization")) {
                        PreAuthorizationContainerData preAuthorizationContainerData = new PreAuthorizationContainerData();
                        preAuthorizationContainerData.a = new Contact();
                        preAuthorizationContainerData.a.b = getContactDetailsData.a;
                        preAuthorizationContainerData.a.a = getContactDetailsData.b;
                        optJSONObject2 = optJSONObject3.getJSONObject("PreAuthorization");
                        preAuthorizationContainerData.d = new PreAuthorization();
                        preAuthorizationContainerData.d.a = jd.a(optJSONObject2, "Identifier");
                        preAuthorizationContainerData.d.b = optJSONObject2.optInt("IdentifierType");
                        getContactDetailsData.K = preAuthorizationContainerData;
                    }
                    if (!optJSONObject3.isNull("Cashiers")) {
                        jSONArray = optJSONObject3.getJSONArray("Cashiers");
                        length2 = jSONArray.length();
                        for (i2 = 0; i2 < length2; i2++) {
                            jSONObject2 = jSONArray.getJSONObject(i2);
                            Tills tills = new Tills();
                            tills.b = jd.a(jSONObject2, "DisplayName");
                            tills.a = jd.a(jSONObject2, "Identifier");
                            tills.c = jSONObject2.optBoolean("IsSmoneyUser");
                            getContactDetailsData.I.add(tills);
                        }
                    }
                    jSONObject = optJSONObject3.optJSONObject("Schedule");
                    if (jSONObject != null) {
                        proInfos.j = jSONObject.optBoolean("IsOpen");
                        proInfos.l = jSONObject.optInt("OpeningState");
                        proInfos.k = jSONObject.optBoolean("ShowOpening");
                        jSONArray = jSONObject.optJSONArray("OpeningHoursText");
                        if (jSONArray != null) {
                            length2 = jSONArray.length();
                            for (i2 = 0; i2 < length2; i2++) {
                                proInfos.q.add(jSONArray.getString(i2));
                            }
                        }
                    }
                    jSONArray = optJSONObject3.optJSONArray("Products");
                    if (jSONArray != null) {
                        length2 = jSONArray.length();
                        for (i2 = 0; i2 < length2; i2++) {
                            jSONObject2 = jSONArray.optJSONObject(i2);
                            ProProduct proProduct = new ProProduct();
                            proProduct.a = jSONObject2.optInt("Id");
                            proProduct.e = jSONObject2.optString("Label");
                            JSONObject optJSONObject4 = jSONObject2.optJSONObject("Price");
                            if (optJSONObject4 != null) {
                                proProduct.b = optJSONObject4.optString("AmountHT");
                                proProduct.c = optJSONObject4.optString("AmountTTC");
                                proProduct.d = optJSONObject4.optString("Tax");
                            }
                            jSONObject2 = jSONObject2.optJSONObject("Photo");
                            if (jSONObject2 != null) {
                                proProduct.g = jSONObject2.optString("Id");
                                proProduct.f = jSONObject2.optString("Name");
                            }
                            proInfos.p.add(proProduct);
                        }
                    }
                    JSONArray optJSONArray2 = optJSONObject3.optJSONArray("PromotionalOffers");
                    if (optJSONArray2 != null) {
                        int length3 = optJSONArray2.length();
                        while (i < length3) {
                            optJSONObject2 = optJSONArray2.getJSONObject(i);
                            PromotionalOffer promotionalOffer = new PromotionalOffer();
                            promotionalOffer.a = optJSONObject2.optInt("Id");
                            promotionalOffer.b = optJSONObject2.optBoolean("IsRead");
                            promotionalOffer.c = jd.a(optJSONObject2, "Title");
                            promotionalOffer.e = jd.a(optJSONObject2, "Description");
                            promotionalOffer.d = jd.a(optJSONObject2, "SubTitle");
                            promotionalOffer.f = jd.a(optJSONObject2, "Url");
                            promotionalOffer.g = jk.a(jd.a(optJSONObject2, "PublicationStartDate"));
                            promotionalOffer.h = jk.a(jd.a(optJSONObject2, "PublicationEndDate"));
                            promotionalOffer.k = getContactDetailsData.a;
                            promotionalOffer.j = getContactDetailsData.b;
                            promotionalOffer.l = proInfos.d;
                            promotionalOffer.m = a.b;
                            promotionalOffer.i = jd.a(optJSONObject2, "Activity");
                            proInfos.o.add(promotionalOffer);
                            i++;
                        }
                    }
                    getContactDetailsData.J = proInfos;
                }
                JSONObject optJSONObject5 = optJSONObject.optJSONObject("IzlyProfile");
                if (optJSONObject5 != null) {
                    if (!optJSONObject5.isNull("IzlyEmail")) {
                        getContactDetailsData.w = optJSONObject5.getString("IzlyEmail");
                    }
                    if (!optJSONObject5.isNull("CardNumber")) {
                        getContactDetailsData.B = optJSONObject5.getString("CardNumber");
                    }
                    if (!optJSONObject5.isNull("NumeroTarif")) {
                        getContactDetailsData.E = optJSONObject5.getString("NumeroTarif");
                    }
                    if (!optJSONObject5.isNull("CodeSociete")) {
                        getContactDetailsData.C = optJSONObject5.getInt("CodeSociete");
                    }
                    if (!optJSONObject5.isNull("DateValidity")) {
                        getContactDetailsData.D = jk.a(optJSONObject5.getString("DateValidity"));
                    }
                }
                optJSONObject5 = optJSONObject.optJSONObject("Profile");
                if (optJSONObject5 != null) {
                    if (!optJSONObject5.isNull("BirthDate")) {
                        getContactDetailsData.u = jk.a(optJSONObject5.getString("BirthDate"));
                    }
                    getContactDetailsData.v = optJSONObject5.optInt("Civility");
                    if (!optJSONObject5.isNull("Email")) {
                        getContactDetailsData.z = optJSONObject5.getString("Email");
                    }
                    if (!optJSONObject5.isNull("FirstName")) {
                        getContactDetailsData.x = optJSONObject5.getString("FirstName");
                    }
                    if (!optJSONObject5.isNull("LastName")) {
                        getContactDetailsData.y = optJSONObject5.getString("LastName");
                    }
                    if (!optJSONObject5.isNull("TwitterEmail")) {
                        getContactDetailsData.A = optJSONObject5.getString("TwitterEmail");
                    }
                    if (!optJSONObject5.isNull("Address")) {
                        optJSONObject5 = optJSONObject5.optJSONObject("Address");
                        getContactDetailsData.s = jd.a(optJSONObject5, "City");
                        getContactDetailsData.q = jd.a(optJSONObject5, "Name");
                        getContactDetailsData.r = jd.a(optJSONObject5, "ZipCode");
                        getContactDetailsData.t = optJSONObject5.getInt("Country");
                    }
                }
            }
            GetContactDetailsData getContactDetailsData2 = getContactDetailsData;
            serverError = null;
            parcelable = getContactDetailsData2;
        }
        Bundle bundle = new Bundle();
        if (serverError != null) {
            bundle.putParcelable("fr.smoney.android.izly.extras.serverError", serverError);
        }
        if (parcelable != null) {
            bundle.putParcelable("fr.smoney.android.izly.extras.GetContactDetails", parcelable);
        }
        return bundle;
    }
}
