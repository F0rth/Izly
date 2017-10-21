package defpackage;

import fr.smoney.android.izly.data.model.P2PGet;
import fr.smoney.android.izly.data.model.P2PGetMult;
import fr.smoney.android.izly.data.model.Transaction;
import fr.smoney.android.izly.data.model.TransactionMessage;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public final class aq {
    public static Transaction a(JSONObject jSONObject) throws JSONException {
        JSONObject jSONObject2;
        int i = 0;
        Transaction transaction = new Transaction();
        transaction.o = jSONObject.getInt("Direction");
        transaction.b = jSONObject.getLong("Id");
        transaction.d = !jSONObject.getBoolean("IsNew");
        transaction.n = jSONObject.getInt("OperationType");
        if (!jSONObject.isNull("Message")) {
            transaction.m = jSONObject.getString("Message");
        }
        transaction.s = jSONObject.getInt("Status");
        transaction.k = jk.a(jSONObject.getString("RequestDate"));
        if (jSONObject.isNull("ResponseDate")) {
            transaction.v = -1;
            transaction.l = transaction.k;
        } else {
            transaction.v = jk.a(jSONObject.getString("ResponseDate"));
            transaction.l = transaction.v;
        }
        if (!jSONObject.isNull("ResponseMessage")) {
            transaction.u = jSONObject.getString("ResponseMessage");
        }
        if (jSONObject.isNull("Amount")) {
            transaction.e = -1.0d;
        } else {
            jSONObject2 = jSONObject.getJSONObject("Amount");
            if (jSONObject2.isNull("AmountTTC")) {
                transaction.e = -1.0d;
            } else {
                transaction.e = jSONObject2.getDouble("AmountTTC");
            }
        }
        transaction.g = transaction.e;
        if (!jSONObject.isNull("ReceiverOrSender")) {
            jSONObject2 = jSONObject.getJSONObject("ReceiverOrSender");
            if (!jSONObject2.isNull("ProfileFirstName")) {
                transaction.q = jSONObject2.getString("ProfileFirstName");
            }
            if (!jSONObject2.isNull("ProfileLastName")) {
                transaction.r = jSONObject2.getString("ProfileLastName");
            }
            transaction.p = jSONObject2.getString("Identifier");
            transaction.c = jSONObject2.getBoolean("IsSmoneyUser");
            if (jSONObject2.getBoolean("IsSmoneyPro")) {
                transaction.z = 1;
            } else if (transaction.c) {
                transaction.z = 0;
            }
        } else if (!jSONObject.isNull("AccountName")) {
            transaction.p = jSONObject.getString("AccountName");
        }
        if (!jSONObject.isNull("ChatMessages")) {
            JSONArray jSONArray = jSONObject.getJSONArray("ChatMessages");
            int length = jSONArray.length();
            if (length > 0) {
                transaction.A = new ArrayList();
                while (i < length) {
                    JSONObject jSONObject3 = jSONArray.getJSONObject(i);
                    TransactionMessage transactionMessage = new TransactionMessage();
                    transactionMessage.a = jSONObject3.getLong("operationId");
                    transactionMessage.d = jSONObject3.getString("text");
                    transactionMessage.b = jk.a(jSONObject3.getString("Date"));
                    if (!jSONObject3.isNull("Sender")) {
                        transactionMessage.c = jSONObject3.getJSONObject("Sender").getString("Identifier");
                    }
                    transaction.A.add(transactionMessage);
                    i++;
                }
            }
        }
        if (!jSONObject.isNull("Attachment")) {
            jSONObject2 = jSONObject.getJSONObject("Attachment");
            transaction.y = jSONObject2.getString("Id");
            transaction.x = jSONObject2.getString("Name");
        }
        return transaction;
    }

    public static P2PGetMult b(JSONObject jSONObject) throws JSONException {
        P2PGetMult p2PGetMult = new P2PGetMult();
        p2PGetMult.c = jSONObject.getLong("Id");
        p2PGetMult.d = !jSONObject.getBoolean("IsNew");
        if (!jSONObject.isNull("Message")) {
            p2PGetMult.j = jSONObject.getString("Message");
        }
        p2PGetMult.e = jk.a(jSONObject.getString("RequestDate"));
        if (!jSONObject.isNull("Total")) {
            p2PGetMult.f = jSONObject.getDouble("Total");
        }
        if (!jSONObject.isNull("Attachment")) {
            JSONObject jSONObject2 = jSONObject.getJSONObject("Attachment");
            p2PGetMult.n = jSONObject2.getString("Id");
            p2PGetMult.o = jSONObject2.getString("Name");
        }
        if (!jSONObject.isNull("MoneyDemands")) {
            JSONArray jSONArray = jSONObject.getJSONArray("MoneyDemands");
            int length = jSONArray.length();
            ArrayList arrayList = new ArrayList();
            for (int i = 0; i < length; i++) {
                JSONObject jSONObject3 = jSONArray.getJSONObject(i);
                P2PGet p2PGet = new P2PGet();
                p2PGet.a = jSONObject3.getLong("Id");
                p2PGet.m = jSONObject3.getInt("Status");
                if (jSONObject3.isNull("Amount")) {
                    p2PGet.h = -1.0d;
                } else {
                    jSONObject2 = jSONObject3.getJSONObject("Amount");
                    if (jSONObject2.isNull("AmountTTC")) {
                        p2PGet.h = -1.0d;
                    } else {
                        p2PGet.h = jSONObject2.getDouble("AmountTTC");
                    }
                }
                if (jSONObject3.isNull("ResponseDate")) {
                    p2PGet.l = -1;
                } else {
                    p2PGet.l = jk.a(jSONObject3.getString("ResponseDate"));
                }
                if (!jSONObject3.isNull("ResponseMessage")) {
                    p2PGet.k = jSONObject3.getString("ResponseMessage");
                }
                p2PGet.n = jSONObject3.getInt("RevivalCount");
                if (!jSONObject3.isNull("RevivalWaitTimeDisplay")) {
                    String string = jSONObject3.getString("RevivalWaitTimeDisplay");
                    string = string.substring(0, string.indexOf(":"));
                    if (string.equals("0")) {
                        string = "1";
                    }
                    p2PGet.p = string;
                }
                if (!jSONObject3.isNull("Sender")) {
                    jSONObject2 = jSONObject3.getJSONObject("Sender");
                    p2PGet.c = jSONObject2.getString("Identifier");
                    if (!jSONObject2.isNull("ProfileFirstName")) {
                        p2PGet.f = jSONObject2.getString("ProfileFirstName");
                    }
                    if (!jSONObject2.isNull("ProfileLastName")) {
                        p2PGet.g = jSONObject2.getString("ProfileLastName");
                    }
                    p2PGet.d = jSONObject2.getBoolean("IsSmoneyUser");
                    if (p2PGet.d) {
                        p2PGet.e = jSONObject2.getBoolean("IsSmoneyPro");
                    }
                }
                arrayList.add(p2PGet);
            }
            p2PGetMult.b = arrayList;
        }
        return p2PGetMult;
    }
}
