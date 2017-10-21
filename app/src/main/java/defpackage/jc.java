package defpackage;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.widget.Toast;
import fr.smoney.android.izly.R;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import org.spongycastle.crypto.tls.CipherSuite;

public class jc {
    private static final File a = new File(Environment.getExternalStorageDirectory() + "/DCIM/Camera");
    private static final String b = jc.class.getSimpleName();

    public static File a(Activity activity, int i) {
        try {
            a.mkdirs();
            Date date = new Date(System.currentTimeMillis());
            File file = new File(a, new SimpleDateFormat("'IMG'_yyyyMMdd_HHmmss", Locale.getDefault()).format(date) + ".jpg");
            Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
            intent.putExtra("output", Uri.fromFile(file));
            activity.startActivityForResult(intent, 1337);
            return file;
        } catch (ActivityNotFoundException e) {
            Toast.makeText(activity, R.string.subscribe_toast_photo_picker_not_found_message, 1).show();
            return null;
        }
    }

    public static void a(Activity activity, String str, String str2) {
        Intent intent = new Intent("android.intent.action.VIEW");
        StringBuilder stringBuilder = new StringBuilder("geo:0,0?q=");
        stringBuilder.append(str);
        stringBuilder.append("+");
        stringBuilder.append(str2);
        intent.setData(Uri.parse(stringBuilder.toString()));
        activity.startActivity(intent);
    }

    public static void b(Activity activity, int i) {
        try {
            Intent intent = new Intent("android.intent.action.GET_CONTENT", null);
            intent.setType("image/*");
            intent.putExtra("crop", "true");
            intent.putExtra("aspectX", 1);
            intent.putExtra("aspectY", 1);
            intent.putExtra("outputX", CipherSuite.TLS_PSK_WITH_AES_128_CBC_SHA);
            intent.putExtra("outputY", 160);
            intent.putExtra("return-data", true);
            activity.startActivityForResult(intent, 1338);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(activity, R.string.subscribe_toast_photo_picker_not_found_message, 1).show();
        }
    }
}
