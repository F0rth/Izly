package defpackage;

import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Base64;
import com.slidingmenu.lib.BuildConfig;
import fr.smoney.android.izly.data.model.SendAttachmentData;
import fr.smoney.android.izly.data.model.ServerError;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Locale;
import org.apache.http.client.ClientProtocolException;
import org.json.JSONException;
import org.json.JSONObject;
import org.spongycastle.asn1.cmp.PKIFailureInfo;

public final class fv {
    public static Bundle a(String str, String str2, String str3, String str4, String str5) throws ClientProtocolException, IOException, JSONException {
        Parcelable sendAttachmentData;
        Parcelable parcelable = null;
        Object obj = "";
        int i = 0;
        if (str5 != null) {
            Bitmap a = iw.a(new File(str5), PKIFailureInfo.badRecipientNonce, 768);
            if (a != null) {
                OutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                a.compress(CompressFormat.JPEG, 90, byteArrayOutputStream);
                i = byteArrayOutputStream.toByteArray().length;
                obj = Base64.encodeToString(byteArrayOutputStream.toByteArray(), 8).replace("\n", "");
                byteArrayOutputStream.flush();
                byteArrayOutputStream.close();
                a.recycle();
            }
        }
        cq cqVar = new cq("https://rest.izly.fr/Service/PublicService.svc/restfile/SendAttachment");
        cqVar.a(BuildConfig.VERSION_NAME);
        cqVar.a("language", Locale.getDefault().getLanguage());
        cqVar.a("userId", str);
        cqVar.a("sessionId", str2);
        cqVar.a("operationId", str3);
        cqVar.a("mime", "image/jpeg");
        cqVar.a("filename", str4);
        cqVar.a("filesize", String.valueOf(i));
        JSONObject jSONObject = new JSONObject();
        jSONObject.put("attachment", obj);
        cqVar.a = jSONObject.toString();
        jSONObject = new JSONObject(cqVar.a(1));
        if (jSONObject.isNull("ErrorMessage")) {
            sendAttachmentData = new SendAttachmentData();
            sendAttachmentData.a = jSONObject.getBoolean("SendAttachmentResult");
        } else {
            ServerError serverError = new ServerError();
            serverError.b = jSONObject.getInt("Code");
            serverError.c = jSONObject.getString("ErrorMessage");
            serverError.e = jSONObject.getInt("Priority");
            serverError.d = jSONObject.getString("Title");
            parcelable = serverError;
            sendAttachmentData = null;
        }
        Bundle bundle = new Bundle();
        if (parcelable != null) {
            bundle.putParcelable("fr.smoney.android.izly.extras.serverError", parcelable);
        }
        if (sendAttachmentData != null) {
            bundle.putParcelable("fr.smoney.android.izly.extras.sendAttachementData", sendAttachmentData);
        }
        return bundle;
    }
}
