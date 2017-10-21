package fr.smoney.android.izly.ui.view;

import android.animation.TimeInterpolator;
import android.content.Context;
import android.os.Build.VERSION;
import android.support.v7.widget.AppCompatTextView;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.animation.LinearInterpolator;
import android.widget.TextView.BufferType;

import defpackage.ji;
import fr.smoney.android.izly.ui.activity.WebViewActivity;

import java.lang.reflect.Field;

public class ExpandableTextView extends AppCompatTextView {
    public b a;
    public TimeInterpolator b;
    public TimeInterpolator c;
    public final int d;
    public long e;
    public boolean f;
    public boolean g;
    public int h;
    private String i;
    private String j;

    public interface b {
        void a();

        void b();
    }

    public static final class a extends ClickableSpan {
        private int a = 1;
        private String b;
        private Context c;

        public a(Context context, String str, int i) {
            this.b = str;
            this.c = context;
        }

        public final void onClick(View view) {
            if (this.a == 1) {
                this.c.startActivity(WebViewActivity.a(this.c, this.b, fr.smoney.android.izly.ui.activity.WebViewActivity.a.SOCIAL_URL));
            }
        }

        public final void updateDrawState(TextPaint textPaint) {
            if (this.a == 2 || this.a == 3) {
                textPaint.setFakeBoldText(true);
            } else if (this.a == 4) {
                textPaint.setUnderlineText(false);
                return;
            }
            textPaint.setUnderlineText(true);
        }
    }

    public ExpandableTextView(Context context) {
        this(context, null);
    }

    public ExpandableTextView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public ExpandableTextView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.e = 400;
        this.d = getMaxLines();
        this.b = new LinearInterpolator();
        this.c = new LinearInterpolator();
    }

    public int getMaxLines() {
        if (VERSION.SDK_INT >= 16) {
            return super.getMaxLines();
        }
        try {
            Field field = AppCompatTextView.class.getField("mMaxMode");
            field.setAccessible(true);
            Field field2 = AppCompatTextView.class.getField("mMaximum");
            field2.setAccessible(true);
            return ((Integer) field.get(this)).intValue() != 1 ? -1 : ((Integer) field2.get(this)).intValue();
        } catch (Exception e) {
            return -1;
        }
    }

    public void setLinkifiedText(String str, String str2) {
        super.setText(str);
        this.i = str2;
        this.j = str;
        getViewTreeObserver().addOnGlobalLayoutListener(new OnGlobalLayoutListener(this) {
            final /* synthetic */ ExpandableTextView a;

            {
                this.a = r1;
            }

            public final void onGlobalLayout() {
                ViewTreeObserver viewTreeObserver = this.a.getViewTreeObserver();
                if (VERSION.SDK_INT >= 16) {
                    viewTreeObserver.removeOnGlobalLayoutListener(this);
                } else {
                    viewTreeObserver.removeGlobalOnLayoutListener(this);
                }
                this.a.setMovementMethod(LinkMovementMethod.getInstance());
                if (!TextUtils.isEmpty(this.a.j)) {
                    CharSequence spannableStringBuilder = new SpannableStringBuilder(this.a.j);
                    if (this.a.i != null) {
                        this.a.setOnClickListener(new OnClickListener(this) {
                            final /* synthetic */ AnonymousClass1 a;

                            {
                                this.a = r1;
                            }

                            public final void onClick(View view) {
                                this.a.a.getContext().startActivity(WebViewActivity.a(this.a.a.getContext(), this.a.a.i, fr.smoney.android.izly.ui.activity.WebViewActivity.a.SOCIAL_URL));
                            }
                        });
                        this.a.setText(spannableStringBuilder, BufferType.SPANNABLE);
                        return;
                    }
                    this.a.setText(ji.a(this.a.getContext(), new SpannableString(spannableStringBuilder)), BufferType.SPANNABLE);
                }
            }
        });
    }

    public void setOnExpandListener(b bVar) {
        this.a = bVar;
    }
}
