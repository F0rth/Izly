package defpackage;

import android.app.DatePickerDialog.OnDateSetListener;
import android.os.Bundle;
import android.text.format.Time;
import android.widget.DatePicker;

final class hx$1 implements OnDateSetListener {
    final /* synthetic */ hx a;

    hx$1(hx hxVar) {
        this.a = hxVar;
    }

    public final void onDateSet(DatePicker datePicker, int i, int i2, int i3) {
        Bundle bundle = new Bundle();
        Time time = new Time();
        time.set(i3, i2, i);
        time.normalize(false);
        bundle.putLong("Data.Date", time.toMillis(false));
        this.a.a(bundle);
    }
}
