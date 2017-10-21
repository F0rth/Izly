package defpackage;

import android.app.Activity;
import android.widget.TextView;
import fr.smoney.android.izly.R;

public final class in {
    public static TextView a(Activity activity, int i) {
        switch (in$1.a[i - 1]) {
            case 1:
                return (TextView) activity.getLayoutInflater().inflate(R.layout.status_cancelled, null);
            case 2:
                return (TextView) activity.getLayoutInflater().inflate(R.layout.status_paid, null);
            case 3:
                return (TextView) activity.getLayoutInflater().inflate(R.layout.status_in_progress, null);
            case 4:
                return (TextView) activity.getLayoutInflater().inflate(R.layout.status_refused, null);
            default:
                return null;
        }
    }
}
