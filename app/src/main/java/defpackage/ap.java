package defpackage;

import android.os.Bundle;
import android.os.Parcelable;
import fr.smoney.android.izly.data.model.Contact;
import fr.smoney.android.izly.data.model.GetNearProListData;
import fr.smoney.android.izly.data.model.NearPro;
import fr.smoney.android.izly.data.model.NearPro.Tills;
import fr.smoney.android.izly.data.model.PreAuthorizationContainerData;
import fr.smoney.android.izly.data.model.PreAuthorizationContainerData.PreAuthorization;
import fr.smoney.android.izly.data.model.PromotionalOffer;
import fr.smoney.android.izly.data.model.PromotionalOffer.a;
import fr.smoney.android.izly.data.model.ServerError;
import java.text.ParseException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public final class ap {
    public static Bundle a(String str) throws JSONException, ParseException {
        Parcelable parcelable = null;
        Parcelable parcelable2 = null;
        JSONObject jSONObject = new JSONObject(str);
        if (jSONObject.isNull("ErrorMessage")) {
            parcelable = new GetNearProListData();
            if (!jSONObject.isNull("GetNearProsResult")) {
                JSONObject jSONObject2;
                jSONObject = jSONObject.getJSONObject("GetNearProsResult");
                if (!jSONObject.isNull("UP")) {
                    jSONObject2 = jSONObject.getJSONObject("UP");
                    parcelable.a.a = Double.parseDouble(jSONObject2.getString("BAL"));
                    parcelable.a.c = ae.a.parse(jSONObject2.getString("LUD")).getTime();
                }
                if (!jSONObject.isNull("Result")) {
                    int i;
                    JSONObject jSONObject3 = jSONObject.getJSONObject("Result");
                    if (!jSONObject3.isNull("Pros")) {
                        JSONArray jSONArray = jSONObject3.getJSONArray("Pros");
                        int length = jSONArray.length();
                        for (i = 0; i < length; i++) {
                            JSONArray jSONArray2;
                            int length2;
                            int i2;
                            JSONObject jSONObject4;
                            JSONObject jSONObject5 = jSONArray.getJSONObject(i);
                            NearPro nearPro = new NearPro();
                            nearPro.d = i;
                            nearPro.a = jd.a(jSONObject5, "DisplayName");
                            nearPro.b = jd.a(jSONObject5, "Identifier");
                            nearPro.c = jSONObject5.optBoolean("IsCaritative");
                            nearPro.e = jSONObject5.optInt("Activity");
                            if (!jSONObject5.isNull("Coordinates")) {
                                jSONObject2 = jSONObject5.getJSONObject("Coordinates");
                                nearPro.f = jSONObject2.getDouble("Latitude");
                                nearPro.g = jSONObject2.getDouble("Longitude");
                                nearPro.h = jSONObject2.getDouble("Distance");
                                nearPro.i = true;
                            }
                            if (!jSONObject5.isNull("Address")) {
                                jSONObject2 = jSONObject5.getJSONObject("Address");
                                if (!jSONObject2.isNull("Name")) {
                                    nearPro.k = jSONObject2.getString("Name");
                                }
                                if (!jSONObject2.isNull("City")) {
                                    nearPro.l = jSONObject2.getString("City");
                                }
                                if (!jSONObject2.isNull("ZipCode")) {
                                    nearPro.m = jSONObject2.getString("ZipCode");
                                }
                                nearPro.n = jSONObject2.getString("Country");
                            }
                            if (!jSONObject5.isNull("Cashiers")) {
                                jSONArray2 = jSONObject5.getJSONArray("Cashiers");
                                length2 = jSONArray2.length();
                                for (i2 = 0; i2 < length2; i2++) {
                                    jSONObject4 = jSONArray2.getJSONObject(i2);
                                    Tills tills = new Tills();
                                    tills.b = jd.a(jSONObject4, "DisplayName");
                                    tills.a = jd.a(jSONObject4, "Identifier");
                                    tills.c = jSONObject4.optBoolean("IsSmoneyUser");
                                    nearPro.q.add(tills);
                                }
                            }
                            if (!jSONObject5.isNull("PromotionalOffers")) {
                                jSONArray2 = jSONObject5.getJSONArray("PromotionalOffers");
                                length2 = jSONArray2.length();
                                for (i2 = 0; i2 < length2; i2++) {
                                    jSONObject4 = jSONArray2.getJSONObject(i2);
                                    PromotionalOffer promotionalOffer = new PromotionalOffer();
                                    promotionalOffer.a = jSONObject4.optInt("Id");
                                    promotionalOffer.b = jSONObject4.optBoolean("IsRead");
                                    promotionalOffer.c = jd.a(jSONObject4, "Title");
                                    promotionalOffer.e = jd.a(jSONObject4, "Description");
                                    promotionalOffer.d = jd.a(jSONObject4, "SubTitle");
                                    promotionalOffer.f = jd.a(jSONObject4, "Url");
                                    promotionalOffer.g = jk.a(jd.a(jSONObject4, "PublicationStartDate"));
                                    promotionalOffer.h = jk.a(jd.a(jSONObject4, "PublicationEndDate"));
                                    promotionalOffer.k = nearPro.a;
                                    promotionalOffer.j = nearPro.b;
                                    promotionalOffer.l = nearPro.h;
                                    promotionalOffer.m = a.b;
                                    if (!jSONObject4.isNull("Activity")) {
                                        promotionalOffer.i = jSONObject4.optString("Activity");
                                    }
                                    nearPro.p.add(promotionalOffer);
                                }
                            }
                            if (!jSONObject5.isNull("PreAuthorization")) {
                                PreAuthorizationContainerData preAuthorizationContainerData = new PreAuthorizationContainerData();
                                preAuthorizationContainerData.a = new Contact();
                                preAuthorizationContainerData.a.b = nearPro.a;
                                preAuthorizationContainerData.a.a = nearPro.b;
                                jSONObject5 = jSONObject5.getJSONObject("PreAuthorization");
                                preAuthorizationContainerData.d = new PreAuthorization();
                                preAuthorizationContainerData.d.a = jd.a(jSONObject5, "Identifier");
                                preAuthorizationContainerData.d.b = jSONObject5.optInt("IdentifierType");
                                nearPro.o = preAuthorizationContainerData;
                            }
                            parcelable.b.add(nearPro);
                        }
                    }
                    if (!jSONObject3.isNull("GlobalPromotionalOffers")) {
                        JSONArray jSONArray3 = jSONObject3.getJSONArray("GlobalPromotionalOffers");
                        int length3 = jSONArray3.length();
                        for (i = 0; i < length3; i++) {
                            JSONObject jSONObject6 = jSONArray3.getJSONObject(i);
                            PromotionalOffer promotionalOffer2 = new PromotionalOffer();
                            promotionalOffer2.a = jSONObject6.optInt("Id");
                            promotionalOffer2.b = jSONObject6.optBoolean("IsRead");
                            promotionalOffer2.c = jd.a(jSONObject6, "Title");
                            promotionalOffer2.e = jd.a(jSONObject6, "Description");
                            promotionalOffer2.d = jd.a(jSONObject6, "SubTitle");
                            promotionalOffer2.f = jd.a(jSONObject6, "Url");
                            promotionalOffer2.g = jk.a(jd.a(jSONObject6, "PublicationStartDate"));
                            promotionalOffer2.h = jk.a(jd.a(jSONObject6, "PublicationEndDate"));
                            if (!jSONObject6.isNull("Activity")) {
                                promotionalOffer2.i = jSONObject6.optString("Activity");
                            }
                            promotionalOffer2.m = a.a;
                            parcelable.c.add(promotionalOffer2);
                        }
                    }
                }
            }
        } else {
            parcelable2 = new ServerError();
            parcelable2.b = jSONObject.getInt("Code");
            parcelable2.c = jSONObject.getString("ErrorMessage");
            parcelable2.e = jSONObject.getInt("Priority");
            parcelable2.d = jSONObject.getString("Title");
        }
        Bundle bundle = new Bundle();
        if (parcelable2 != null) {
            bundle.putParcelable("fr.smoney.android.izly.extras.serverError", parcelable2);
        }
        if (parcelable != null) {
            bundle.putParcelable("fr.smoney.android.izly.extras.GetNearProList", parcelable);
        }
        return bundle;
    }
}
