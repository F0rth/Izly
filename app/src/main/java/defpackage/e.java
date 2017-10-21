package defpackage;

import android.app.Activity;
import android.hardware.Camera.Parameters;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import scanpay.it.ScanPayActivity;

public final class e implements Runnable {
    public static Boolean a = Boolean.valueOf(false);
    private final nr b;
    private final Activity c;
    private final byte[] d;

    public e(nr nrVar, Activity activity, byte[] bArr) {
        this.b = nrVar;
        this.c = activity;
        this.d = bArr;
    }

    private static int a(char c) {
        int i = 0;
        if ((c & 128) == 128) {
            i = 1;
        }
        if ((c & 16) == 16) {
            i++;
        }
        if ((c & 64) == 64) {
            i++;
        }
        return (c & 32) == 32 ? i + 1 : i;
    }

    private void a(View view, String str) {
        this.c.runOnUiThread(new h(this, str, view));
    }

    private void a(Boolean bool) {
        this.c.runOnUiThread(new f(bool, (Q) this.c.findViewById(44)));
    }

    private void a(String str) {
        this.c.runOnUiThread(new g((TextView) this.c.findViewById(12), str));
    }

    public final void run() {
        nr nrVar;
        nr nrVar2;
        this.b.b('\u0002');
        char a = ov.a(this.d, 640, 480, ((ScanPayActivity) this.c).a.b);
        if (ov.c()) {
            nrVar = this.b;
            try {
                Parameters parameters = nrVar.e.getParameters();
                if (!(parameters.getSupportedFlashModes() == null || parameters.getSupportedFlashModes().indexOf("torch") == -1 || !parameters.getFlashMode().equals("off"))) {
                    nrVar.e.cancelAutoFocus();
                    String str = "on";
                    if (parameters.getSupportedFlashModes().indexOf("torch") != -1) {
                        str = "torch";
                    }
                    parameters.setFlashMode(str);
                    nrVar.e.setParameters(parameters);
                    nrVar.a();
                }
            } catch (RuntimeException e) {
                e.printStackTrace();
            }
        } else {
            nrVar2 = this.b;
            try {
                Parameters parameters2 = nrVar2.e.getParameters();
                if (!(parameters2.getSupportedFlashModes() == null || parameters2.getSupportedFlashModes().indexOf("torch") == -1 || parameters2.getFlashMode().equals("off"))) {
                    nrVar2.e.cancelAutoFocus();
                    parameters2.setFlashMode("off");
                    nrVar2.e.setParameters(parameters2);
                    nrVar2.a();
                }
            } catch (RuntimeException e2) {
                e2.printStackTrace();
            }
        }
        View view = (LinearLayout) this.c.findViewById(13);
        View view2 = (LinearLayout) this.c.findViewById(14);
        View view3 = (LinearLayout) this.c.findViewById(16);
        View view4 = (LinearLayout) this.c.findViewById(15);
        if (this.b.d == ox.Right) {
            view4 = (LinearLayout) this.c.findViewById(33);
            view3 = (LinearLayout) this.c.findViewById(34);
            view = (LinearLayout) this.c.findViewById(36);
            view2 = (LinearLayout) this.c.findViewById(35);
        } else if (this.b.d == ox.Left) {
            LinearLayout linearLayout = (LinearLayout) this.c.findViewById(35);
            LinearLayout linearLayout2 = (LinearLayout) this.c.findViewById(36);
            LinearLayout linearLayout3 = (LinearLayout) this.c.findViewById(34);
            LinearLayout linearLayout4 = (LinearLayout) this.c.findViewById(33);
        } else {
            View view5 = view4;
            view4 = view;
            view = view3;
            view3 = view2;
            view2 = view5;
        }
        if ((a & 128) == 128) {
            a(view4, "fade_in");
        } else if ((a & 128) != 128) {
            a(view4, "fade_out");
        }
        if ((a & 32) == 32) {
            a(view, "fade_in");
        } else if ((a & 32) != 32) {
            a(view, "fade_out");
        }
        if ((a & 64) == 64) {
            a(view2, "fade_in");
        } else if ((a & 64) != 64) {
            a(view2, "fade_out");
        }
        if ((a & 16) == 16) {
            a(view3, "fade_in");
        } else if ((a & 16) != 16) {
            a(view3, "fade_out");
        }
        if (!(e.a(a) < 2 || a == null || a.booleanValue())) {
            this.b.a();
            a = Boolean.valueOf(true);
        }
        if (e.a(a) == 4) {
            if (this.b.c == 0) {
                this.b.c = System.currentTimeMillis();
            }
            if (ov.b()) {
                a(Boolean.valueOf(true));
                a(null);
                nrVar2 = this.b;
                nrVar2.b.runOnUiThread(new ns(nrVar2, Boolean.valueOf(false)));
            }
            nrVar = this.b;
            if (!nrVar.a('\b').booleanValue() && nrVar.g == null) {
                synchronized (nrVar) {
                    nrVar.g = new i(nrVar);
                }
                nrVar.f.execute(nrVar.g);
            }
        } else {
            a(Boolean.valueOf(false));
            if (((TextView) this.c.findViewById(12)).getText().length() == 0) {
                a(k.a().a("Hold your card here"));
            }
        }
        nrVar = this.b;
        synchronized (nrVar) {
            nrVar.a = (char) (nrVar.a & -3);
        }
        synchronized (this.b) {
            this.b.h = null;
        }
    }
}
