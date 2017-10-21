package defpackage;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Base64;
import com.slidingmenu.lib.BuildConfig;
import fr.smoney.android.izly.data.model.GetAttachmentData;
import fr.smoney.android.izly.data.model.ServerError;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Locale;
import org.apache.http.client.ClientProtocolException;
import org.json.JSONException;
import org.json.JSONObject;

public final class dp {
    public static Bundle a(Context context, String str, String str2, String str3) throws ClientProtocolException, IOException, RuntimeException, JSONException {
        Parcelable parcelable;
        Parcelable parcelable2 = null;
        cq cqVar = new cq("https://rest.izly.fr/Service/PublicService.svc/restfile/GetAttachment");
        cqVar.a(BuildConfig.VERSION_NAME);
        cqVar.a("language", Locale.getDefault().getLanguage());
        cqVar.a("userId", str);
        cqVar.a("sessionId", str2);
        cqVar.a("Content-Type", kh.ACCEPT_JSON_VALUE);
        JSONObject jSONObject = new JSONObject();
        jSONObject.put("attachmentId", str3);
        cqVar.a = jSONObject.toString();
        jSONObject = new JSONObject(cqVar.a(1));
        if (jSONObject.isNull("ErrorMessage")) {
            byte[] decode = Base64.decode(jSONObject.getString("attachment"), 8);
            Bitmap decodeByteArray = BitmapFactory.decodeByteArray(decode, 0, decode.length);
            GetAttachmentData getAttachmentData = new GetAttachmentData();
            GetAttachmentData getAttachmentData2;
            Object obj;
            if (decodeByteArray != null) {
                OutputStream fileOutputStream = new FileOutputStream(iw.a(context));
                decodeByteArray.compress(CompressFormat.JPEG, 90, fileOutputStream);
                fileOutputStream.flush();
                fileOutputStream.close();
                getAttachmentData.c = false;
                getAttachmentData.b = iw.a(context);
                getAttachmentData2 = getAttachmentData;
                parcelable = null;
                obj = getAttachmentData2;
            } else {
                getAttachmentData.c = true;
                getAttachmentData2 = getAttachmentData;
                parcelable = null;
                obj = getAttachmentData2;
            }
        } else {
            parcelable = new ServerError();
            parcelable.b = jSONObject.getInt("Code");
            parcelable.c = jSONObject.getString("ErrorMessage");
            parcelable.e = jSONObject.getInt("Priority");
            parcelable.d = jSONObject.getString("Title");
        }
        Bundle bundle = new Bundle();
        if (parcelable != null) {
            bundle.putParcelable("fr.smoney.android.izly.extras.serverError", parcelable);
        }
        if (parcelable2 != null) {
            bundle.putParcelable("fr.smoney.android.izly.extras.getAttachementData", parcelable2);
        }
        return bundle;
    }
}
