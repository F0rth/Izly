package defpackage;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.text.format.Time;

public final class hx extends if {
    private Time a;

    public static hx a(Time time, if$a if_a, ie ieVar) {
        hx hxVar = new hx();
        Bundle a = hxVar.a(if_a, ieVar);
        if (time != null) {
            a.putLong("Argument.DialogTime", time.toMillis(false));
        }
        hxVar.setArguments(a);
        return hxVar;
    }

    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        Long valueOf = Long.valueOf(getArguments().getLong("Argument.DialogTime"));
        this.a = new Time();
        if (valueOf != null) {
            this.a.set(valueOf.longValue());
        } else {
            this.a.setToNow();
        }
    }

    public final Dialog onCreateDialog(Bundle bundle) {
        return new DatePickerDialog(getActivity(), new hx$1(this), this.a.year, this.a.month, this.a.monthDay);
    }
}
