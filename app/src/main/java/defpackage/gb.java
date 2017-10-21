package defpackage;

import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Base64;
import com.slidingmenu.lib.BuildConfig;
import fr.smoney.android.izly.data.model.ServerError;
import fr.smoney.android.izly.data.model.UpdateUserPictureData;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.ParseException;
import java.util.Locale;
import org.apache.http.client.ClientProtocolException;
import org.json.JSONException;
import org.json.JSONObject;

public final class gb {
    public static Bundle a(String str, String str2, Bitmap bitmap) throws ClientProtocolException, IOException, JSONException, ParseException {
        Parcelable updateUserPictureData;
        Parcelable parcelable = null;
        Object obj = "";
        int i = 0;
        if (bitmap != null) {
            OutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            bitmap.compress(CompressFormat.JPEG, 100, byteArrayOutputStream);
            obj = Base64.encodeToString(byteArrayOutputStream.toByteArray(), 8).replace("\n", "");
            i = byteArrayOutputStream.toByteArray().length;
        }
        cq cqVar = new cq("https://rest.izly.fr/Service/PublicService.svc/restfile/UpdateUserPicture");
        cqVar.a(BuildConfig.VERSION_NAME);
        cqVar.a("language", Locale.getDefault().getLanguage());
        cqVar.a("userId", str);
        cqVar.a("sessionId", str2);
        cqVar.a("mime", "image/jpeg");
        cqVar.a("filesize", String.valueOf(i));
        JSONObject jSONObject = new JSONObject();
        jSONObject.put("userPicture", obj);
        cqVar.b(jSONObject.toString());
        jSONObject = new JSONObject(cqVar.a(1));
        if (jSONObject.isNull("ErrorMessage")) {
            updateUserPictureData = new UpdateUserPictureData();
            JSONObject optJSONObject = jSONObject.optJSONObject("UpdateUserPictureResult");
            if (!(optJSONObject == null || jSONObject.isNull("UP"))) {
                jSONObject = optJSONObject.getJSONObject("UP");
                updateUserPictureData.a.a = Double.parseDouble(jSONObject.getString("BAL"));
                updateUserPictureData.a.c = ae.a.parse(jSONObject.getString("LUD")).getTime();
            }
        } else {
            ServerError serverError = new ServerError();
            serverError.b = jSONObject.getInt("Code");
            serverError.c = jSONObject.getString("ErrorMessage");
            serverError.e = jSONObject.getInt("Priority");
            serverError.d = jSONObject.getString("Title");
            parcelable = serverError;
            updateUserPictureData = null;
        }
        Bundle bundle = new Bundle();
        if (parcelable != null) {
            bundle.putParcelable("fr.smoney.android.izly.extras.serverError", parcelable);
        }
        if (updateUserPictureData != null) {
            bundle.putParcelable("fr.smoney.android.izly.extras.updateUserPictureData", updateUserPictureData);
        }
        return bundle;
    }
}
