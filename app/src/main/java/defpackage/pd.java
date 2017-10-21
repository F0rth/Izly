package defpackage;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.view.animation.Animation;
import android.view.animation.CycleInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout.LayoutParams;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public final class pd implements TextWatcher, OnFocusChangeListener {
    public EditText a;
    public EditText b;
    public EditText c;
    public w d;
    public int e = 500;
    public Boolean f = Boolean.valueOf(false);
    public int g = 0;
    private ImageView h;
    private Boolean i = Boolean.valueOf(false);
    private Boolean j = Boolean.valueOf(false);
    private Context k;
    private int l;
    private int m;
    private List n;
    private float o;
    private float p;

    public pd(EditText editText, EditText editText2, EditText editText3, ImageView imageView, Context context) {
        this.a = editText;
        this.b = editText2;
        this.c = editText3;
        this.h = imageView;
        this.k = context;
        this.m = Calendar.getInstance().get(1) - 2000;
        this.a.addTextChangedListener(this);
        this.b.addTextChangedListener(this);
        this.c.addTextChangedListener(this);
        this.a.setOnFocusChangeListener(this);
        this.b.setOnFocusChangeListener(this);
        this.c.setOnFocusChangeListener(this);
        this.n = new ArrayList();
        this.n.add(new b());
        this.n.add(new a());
        this.n.add(new ot());
        this.n.add(new pi());
        this.n.add(new pg());
        this.n.add(new ph());
        this.n.add(new pc());
    }

    private static float a(float f, Context context) {
        return (((float) context.getResources().getDisplayMetrics().densityDpi) / 160.0f) * f;
    }

    private static int a(String str) {
        try {
            return Integer.parseInt(str);
        } catch (Exception e) {
            return -1;
        }
    }

    private void a() {
        if (!this.j.booleanValue()) {
            this.j = Boolean.valueOf(true);
            Boolean bool = this.f;
            Animation translateAnimation = this.i.booleanValue() ? new TranslateAnimation(-pd.a(this.o, this.k), 0.0f, (float) this.a.getTop(), (float) this.a.getTop()) : new TranslateAnimation(0.0f, -pd.a(this.o, this.k), (float) this.a.getTop(), (float) this.a.getTop());
            if (this.e == 0) {
                a(bool);
                return;
            }
            translateAnimation.setDuration((long) this.e);
            translateAnimation.setFillAfter(false);
            translateAnimation.setFillBefore(false);
            translateAnimation.setAnimationListener(new pe(this, bool));
            this.a.startAnimation(translateAnimation);
        }
    }

    private static void a(View view) {
        Animation translateAnimation = new TranslateAnimation(0.0f, 15.0f, (float) view.getTop(), (float) view.getTop());
        translateAnimation.setDuration(500);
        translateAnimation.setInterpolator(new CycleInterpolator(3.0f));
        view.startAnimation(translateAnimation);
    }

    private void a(Boolean bool) {
        if (this.e == 0) {
            this.j = Boolean.valueOf(false);
            this.i = Boolean.valueOf(true);
        }
        this.b.setVisibility(0);
        this.c.setVisibility(0);
        if (bool.booleanValue()) {
            this.c.requestFocus();
        } else {
            this.b.requestFocus();
        }
        LayoutParams layoutParams = (LayoutParams) this.a.getLayoutParams();
        layoutParams.addRule(9);
        layoutParams.setMargins((int) pd.a(this.p, this.k), 0, 0, 0);
        this.a.setLayoutParams(layoutParams);
        this.e = 500;
    }

    public void a(Editable editable) {
        for (pf pfVar : this.n) {
            if (pfVar.a(editable.toString()).booleanValue()) {
                this.a.setTextColor(-16777216);
                this.o = pfVar.b;
                this.p = pfVar.a;
                this.h.setImageBitmap(pfVar.a());
                pfVar.a(editable);
                if (pfVar.c(editable.toString()).booleanValue()) {
                    this.a.setTextColor(-65536);
                    pd.a(this.a);
                }
                if (pfVar.b(editable.toString()).booleanValue()) {
                    if (!this.f.booleanValue()) {
                        this.b.requestFocus();
                    }
                    a();
                    return;
                }
                return;
            }
        }
    }

    public final void afterTextChanged(Editable editable) {
        if (this.l == this.a.getId()) {
            a(editable);
        } else if (this.l == this.b.getId()) {
            b(editable);
        } else if (this.l == this.c.getId()) {
            for (pf pfVar : this.n) {
                if (pfVar.a(this.a.getText().toString()).booleanValue()) {
                    if (editable.length() > pfVar.b()) {
                        editable.delete(pfVar.b(), editable.length());
                    }
                }
            }
        }
        for (pf pfVar2 : this.n) {
            if (pfVar2.a(this.a.getText().toString()).booleanValue() && pfVar2.b(this.a.getText().toString()).booleanValue() && this.c.getText().toString().length() == pfVar2.b() && this.b.getText().toString().length() == 5) {
                this.d.a();
                return;
            }
        }
        this.d.b();
    }

    public void b(Editable editable) {
        int a;
        if (this.g < this.b.length()) {
            if (editable.length() > 5) {
                editable.delete(5, editable.length());
            }
            if (this.g == 0) {
                a = pd.a(editable.toString());
                if (a > 1) {
                    editable.insert(0, "0");
                } else if (a == -1) {
                    editable.delete(this.g, this.g + 1);
                }
            } else if (this.g == 1) {
                a = pd.a(editable.toString());
                if (a <= 1 || a >= 13) {
                    editable.delete(this.g, this.g + 1);
                    pd.a(this.b);
                } else {
                    editable.append("/");
                }
            } else if (this.g == 3) {
                a = pd.a(editable.toString().substring(this.g));
                if (this.m / 10 > a) {
                    editable.delete(this.g, this.g + 1);
                    pd.a(this.b);
                } else if (a == -1) {
                    editable.delete(this.g, this.g + 1);
                }
            } else if (this.g == 4) {
                a = pd.a(editable.toString().substring(editable.length() - 2));
                if (this.m > a) {
                    editable.delete(this.g, this.g + 1);
                    pd.a(this.b);
                } else if (a == -1) {
                    editable.delete(this.g, this.g + 1);
                }
            }
        } else if (this.g == 3) {
            a = pd.a(editable.subSequence(0, 1).toString());
            if (a == 0 || a == -1) {
                editable.clear();
            } else {
                editable.delete(1, 2);
            }
        }
        if (this.b.length() == 5) {
            this.c.requestFocus();
        }
    }

    public final void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        if (this.l == this.a.getId()) {
            this.g = this.a.length();
        }
        if (this.l == this.b.getId()) {
            this.g = this.b.length();
        }
        if (this.l == this.c.getId()) {
            this.g = this.c.length();
        }
    }

    public final void onFocusChange(View view, boolean z) {
        this.l = view.getId();
        if (this.a == view && z) {
            for (pf pfVar : this.n) {
                if (pfVar.a(this.a.getText().toString()).booleanValue() && pfVar.b(this.a.getText().toString()).booleanValue()) {
                    a();
                    return;
                }
            }
        }
    }

    public final void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
    }
}
