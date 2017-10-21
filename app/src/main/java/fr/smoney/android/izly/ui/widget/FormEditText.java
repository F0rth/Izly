package fr.smoney.android.izly.ui.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.Layout;
import android.text.Layout.Alignment;
import android.text.StaticLayout;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.joanzapata.android.iconify.IconDrawable;
import com.joanzapata.android.iconify.Iconify$IconValue;

import defpackage.ik;
import fr.smoney.android.izly.R;

import java.util.ArrayList;
import java.util.Iterator;

import org.spongycastle.asn1.x509.DisplayText;

public class FormEditText extends EditText {
    protected Drawable a;
    protected Drawable b;
    private final int c = 0;
    private Drawable d;
    private Drawable e;
    private Drawable f;
    private String g;
    private String h;
    private CharSequence i;
    private Drawable j;
    private Drawable k;
    private Drawable l;
    private Drawable m;
    private Drawable n;
    private Drawable o;
    private int p;
    private b q;
    private c r;
    private boolean s;
    private boolean t;
    private boolean u;
    private ArrayList<ik> v;

    enum a {
        ;

        static {
            a = 1;
            b = 2;
            c = 3;
            d = new int[]{a, b, c};
        }
    }

    final class b implements TextWatcher {
        final /* synthetic */ FormEditText a;

        public b(FormEditText formEditText) {
            this.a = formEditText;
        }

        public final void afterTextChanged(Editable editable) {
            this.a.a();
            this.a.b();
        }

        public final void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        }

        public final void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        }
    }

    final class c extends PopupWindow {
        final /* synthetic */ FormEditText a;
        private boolean b = false;
        private TextView c;

        c(FormEditText formEditText, TextView textView, int i, int i2) {
            this.a = formEditText;
            super(textView, 0, 0);
            this.c = textView;
        }

        final void a(boolean z) {
            this.b = z;
            if (z) {
                if (this.a.p == a.a) {
                    this.c.setBackgroundDrawable(this.a.l);
                } else if (this.a.p == a.c) {
                    this.c.setBackgroundDrawable(this.a.k);
                }
            } else if (this.a.p == a.a) {
                this.c.setBackgroundDrawable(this.a.b);
            } else if (this.a.p == a.c) {
                this.c.setBackgroundDrawable(this.a.a);
            }
        }

        public final void update(int i, int i2, int i3, int i4, boolean z) {
            super.update(i, i2, i3, i4, z);
            boolean isAboveAnchor = isAboveAnchor();
            if (isAboveAnchor != this.b) {
                a(isAboveAnchor);
            }
        }
    }

    public FormEditText(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.FormEditText);
        this.m = obtainStyledAttributes.getDrawable(0);
        if (this.m == null) {
            this.m = new IconDrawable(context, Iconify$IconValue.fa_exclamation_circle).colorRes(R.color.izly_red).sizeDp(30);
        }
        this.n = obtainStyledAttributes.getDrawable(2);
        if (this.n == null) {
            this.n = context.getResources().getDrawable(R.drawable.icon_infos);
        }
        this.o = obtainStyledAttributes.getDrawable(1);
        if (this.o == null) {
            this.o = new IconDrawable(context, Iconify$IconValue.fa_check_circle).colorRes(R.color.izly_green).sizeDp(30);
        }
        this.a = obtainStyledAttributes.getDrawable(3);
        if (this.a == null) {
            this.a = context.getResources().getDrawable(R.drawable.bg_popup_error_down);
        }
        this.k = obtainStyledAttributes.getDrawable(4);
        if (this.k == null) {
            this.k = context.getResources().getDrawable(R.drawable.bg_popup_error);
        }
        this.b = obtainStyledAttributes.getDrawable(5);
        if (this.b == null) {
            this.b = context.getResources().getDrawable(R.drawable.bg_popup_help_down);
        }
        this.l = obtainStyledAttributes.getDrawable(6);
        if (this.l == null) {
            this.l = context.getResources().getDrawable(R.drawable.bg_popup_help);
        }
        this.g = obtainStyledAttributes.getString(9);
        if (this.g == null) {
            this.g = context.getString(R.string.form_edittext_default_help_message);
        }
        this.t = obtainStyledAttributes.getBoolean(11, true);
        this.h = context.getString(R.string.form_edittext_default_error_message);
        this.d = obtainStyledAttributes.getDrawable(7);
        if (this.d == null) {
            this.d = context.getResources().getDrawable(R.drawable.izly_red_stroke);
        }
        this.e = obtainStyledAttributes.getDrawable(8);
        if (this.e == null) {
            this.e = context.getResources().getDrawable(R.drawable.izly_green_stroke);
        }
        this.f = getBackground();
        this.s = obtainStyledAttributes.getBoolean(10, false);
        this.u = obtainStyledAttributes.getBoolean(12, true);
        obtainStyledAttributes.recycle();
        this.v = new ArrayList();
        this.q = new b(this);
        addTextChangedListener(this.q);
        d();
    }

    private void a(String str) {
        this.i = str;
        if (this.p != a.c) {
            this.p = a.c;
            this.j = this.m;
            setCompoundDrawablesWithIntrinsicBounds(null, null, this.m, null);
            setBackgroundDrawable(this.d);
            setPadding(30, 0, 15, 0);
        }
    }

    private void d() {
        if (this.p != a.a) {
            this.p = a.a;
            this.i = this.g;
            this.j = this.n;
            setCompoundDrawablesWithIntrinsicBounds(null, null, this.n, null);
            setBackgroundDrawable(this.f);
            setPadding(30, 0, 15, 0);
        }
    }

    private void e() {
        this.r = new c(this, (TextView) LayoutInflater.from(getContext()).inflate(R.layout.status_popup_textview, null), 0, 0);
        this.r.setFocusable(false);
        this.r.setInputMethodMode(1);
    }

    private void f() {
        float f = 0.0f;
        if (getWindowToken() != null && this.t) {
            int i;
            if (this.r == null) {
                e();
            }
            TextView textView = (TextView) this.r.getContentView();
            PopupWindow popupWindow = this.r;
            CharSequence charSequence = this.i;
            int paddingLeft = textView.getPaddingLeft() + textView.getPaddingRight();
            int paddingTop = textView.getPaddingTop();
            int paddingBottom = textView.getPaddingBottom();
            int width = getWidth() - paddingLeft;
            if (width < 0) {
                width = DisplayText.DISPLAY_TEXT_MAXIMUM_SIZE;
            }
            if (charSequence == null) {
                charSequence = "";
            }
            Layout staticLayout = new StaticLayout(charSequence, textView.getPaint(), width, Alignment.ALIGN_NORMAL, 1.0f, 0.0f, true);
            for (i = 0; i < staticLayout.getLineCount(); i++) {
                f = Math.max(f, staticLayout.getLineWidth(i));
            }
            i = (int) Math.ceil((double) f);
            int height = staticLayout.getHeight();
            popupWindow.setWidth(i + paddingLeft);
            popupWindow.setHeight(height + (paddingTop + paddingBottom));
            textView.setText(this.i);
            this.r.showAsDropDown(this, getWidth(), 0);
            this.r.a(this.r.isAboveAnchor());
        }
    }

    public final void a() {
        if (this.r != null && this.r.isShowing()) {
            this.r.dismiss();
        }
    }

    public final void a(ik ikVar) {
        this.v.add(ikVar);
    }

    public final void b() {
        if (this.u) {
            Object obj = getText().toString();
            if (TextUtils.isEmpty(obj)) {
                d();
                return;
            }
            Iterator it = this.v.iterator();
            while (it.hasNext()) {
                ik ikVar = (ik) it.next();
                if (!ikVar.b(obj)) {
                    String a = ikVar.a();
                    if (a != null) {
                        a(a);
                        return;
                    } else {
                        a(this.h);
                        return;
                    }
                }
            }
            if (this.p != a.b) {
                this.p = a.b;
                this.i = null;
                this.j = this.o;
                setCompoundDrawablesWithIntrinsicBounds(null, null, this.o, null);
                setBackgroundDrawable(this.e);
                setPadding(30, 0, 15, 0);
            }
        }
    }

    public final boolean c() {
        if (this.u) {
            Object obj = getText().toString();
            if (!this.s && TextUtils.isEmpty(obj)) {
                return false;
            }
            Iterator it = this.v.iterator();
            while (it.hasNext()) {
                if (!((ik) it.next()).b(obj)) {
                    return false;
                }
            }
        }
        return true;
    }

    protected void finalize() throws Throwable {
        removeTextChangedListener(this.q);
        this.d = null;
        this.e = null;
        this.f = null;
        this.j = null;
        this.a = null;
        this.b = null;
        this.k = null;
        this.l = null;
        this.m = null;
        this.n = null;
        this.o = null;
        super.finalize();
    }

    protected void onFocusChanged(boolean z, int i, Rect rect) {
        super.onFocusChanged(z, i, rect);
        if (!z) {
            a();
            b();
        }
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (motionEvent.getAction() == 0) {
            int x = (int) motionEvent.getX();
            int y = (int) motionEvent.getY();
            if (this.i != null) {
                if (x >= (getRight() - this.j.getBounds().width()) + 0 && x <= (getRight() - getPaddingRight()) + 0 && y >= getPaddingTop() + 0 && y <= (getHeight() - getPaddingBottom()) + 0) {
                    if (this.r == null || !this.r.isShowing()) {
                        f();
                    } else {
                        a();
                    }
                    requestFocus();
                    return false;
                }
            }
        }
        return super.onTouchEvent(motionEvent);
    }

    protected boolean setFrame(int i, int i2, int i3, int i4) {
        boolean frame = super.setFrame(i, i2, i3, i4);
        if (this.r == null) {
            e();
            this.r.getContentView().setVisibility(8);
            f();
            a();
            this.r.getContentView().setVisibility(0);
        }
        return frame;
    }

    public void setNoIcon() {
        setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
    }
}
