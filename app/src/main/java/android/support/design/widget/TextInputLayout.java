package android.support.design.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Message;
import android.support.design.R;
import android.support.v4.view.AccessibilityDelegateCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPropertyAnimatorListenerAdapter;
import android.support.v4.view.accessibility.AccessibilityNodeInfoCompat;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityEvent;
import android.view.animation.AccelerateInterpolator;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

public class TextInputLayout extends LinearLayout {
    private static final int ANIMATION_DURATION = 200;
    private static final int MSG_UPDATE_LABEL = 0;
    private ValueAnimatorCompat mAnimator;
    private final CollapsingTextHelper mCollapsingTextHelper;
    private EditText mEditText;
    private boolean mErrorEnabled;
    private int mErrorTextAppearance;
    private TextView mErrorView;
    private final Handler mHandler;
    private CharSequence mHint;
    private ColorStateList mLabelTextColor;

    class TextInputAccessibilityDelegate extends AccessibilityDelegateCompat {
        private TextInputAccessibilityDelegate() {
        }

        public void onInitializeAccessibilityEvent(View view, AccessibilityEvent accessibilityEvent) {
            super.onInitializeAccessibilityEvent(view, accessibilityEvent);
            accessibilityEvent.setClassName(TextInputLayout.class.getSimpleName());
        }

        public void onInitializeAccessibilityNodeInfo(View view, AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
            super.onInitializeAccessibilityNodeInfo(view, accessibilityNodeInfoCompat);
            accessibilityNodeInfoCompat.setClassName(TextInputLayout.class.getSimpleName());
            CharSequence text = TextInputLayout.this.mCollapsingTextHelper.getText();
            if (!TextUtils.isEmpty(text)) {
                accessibilityNodeInfoCompat.setText(text);
            }
            if (TextInputLayout.this.mEditText != null) {
                accessibilityNodeInfoCompat.setLabelFor(TextInputLayout.this.mEditText);
            }
            text = TextInputLayout.this.mErrorView != null ? TextInputLayout.this.mErrorView.getText() : null;
            if (!TextUtils.isEmpty(text)) {
                accessibilityNodeInfoCompat.setContentInvalid(true);
                accessibilityNodeInfoCompat.setError(text);
            }
        }

        public void onPopulateAccessibilityEvent(View view, AccessibilityEvent accessibilityEvent) {
            super.onPopulateAccessibilityEvent(view, accessibilityEvent);
            CharSequence text = TextInputLayout.this.mCollapsingTextHelper.getText();
            if (!TextUtils.isEmpty(text)) {
                accessibilityEvent.getText().add(text);
            }
        }
    }

    public TextInputLayout(Context context) {
        this(context, null);
    }

    public TextInputLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        setOrientation(1);
        setWillNotDraw(false);
        this.mCollapsingTextHelper = new CollapsingTextHelper(this);
        this.mHandler = new Handler(new Callback() {
            public boolean handleMessage(Message message) {
                switch (message.what) {
                    case 0:
                        TextInputLayout.this.updateLabelVisibility(true);
                        return true;
                    default:
                        return false;
                }
            }
        });
        this.mCollapsingTextHelper.setTextSizeInterpolator(AnimationUtils.FAST_OUT_SLOW_IN_INTERPOLATOR);
        this.mCollapsingTextHelper.setPositionInterpolator(new AccelerateInterpolator());
        this.mCollapsingTextHelper.setCollapsedTextVerticalGravity(48);
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.TextInputLayout, 0, R.style.Widget_Design_TextInputLayout);
        this.mHint = obtainStyledAttributes.getText(R.styleable.TextInputLayout_android_hint);
        int resourceId = obtainStyledAttributes.getResourceId(R.styleable.TextInputLayout_hintTextAppearance, -1);
        if (resourceId != -1) {
            this.mCollapsingTextHelper.setCollapsedTextAppearance(resourceId);
        }
        this.mErrorTextAppearance = obtainStyledAttributes.getResourceId(R.styleable.TextInputLayout_errorTextAppearance, 0);
        boolean z = obtainStyledAttributes.getBoolean(R.styleable.TextInputLayout_errorEnabled, false);
        this.mLabelTextColor = createLabelTextColorStateList(this.mCollapsingTextHelper.getCollapsedTextColor());
        this.mCollapsingTextHelper.setCollapsedTextColor(this.mLabelTextColor.getDefaultColor());
        this.mCollapsingTextHelper.setExpandedTextColor(this.mLabelTextColor.getDefaultColor());
        obtainStyledAttributes.recycle();
        if (z) {
            setErrorEnabled(true);
        }
        if (ViewCompat.getImportantForAccessibility(this) == 0) {
            ViewCompat.setImportantForAccessibility(this, 1);
        }
        ViewCompat.setAccessibilityDelegate(this, new TextInputAccessibilityDelegate());
    }

    private void animateToExpansionFraction(float f) {
        if (this.mAnimator == null) {
            this.mAnimator = ViewUtils.createAnimator();
            this.mAnimator.setInterpolator(AnimationUtils.LINEAR_INTERPOLATOR);
            this.mAnimator.setDuration(200);
            this.mAnimator.setUpdateListener(new AnimatorUpdateListener() {
                public void onAnimationUpdate(ValueAnimatorCompat valueAnimatorCompat) {
                    TextInputLayout.this.mCollapsingTextHelper.setExpansionFraction(valueAnimatorCompat.getAnimatedFloatValue());
                }
            });
        } else if (this.mAnimator.isRunning()) {
            this.mAnimator.cancel();
        }
        this.mAnimator.setFloatValues(this.mCollapsingTextHelper.getExpansionFraction(), f);
        this.mAnimator.start();
    }

    private void collapseHint(boolean z) {
        if (z) {
            animateToExpansionFraction(1.0f);
        } else {
            this.mCollapsingTextHelper.setExpansionFraction(1.0f);
        }
    }

    private ColorStateList createLabelTextColorStateList(int i) {
        int[] iArr = FOCUSED_STATE_SET;
        int[] iArr2 = EMPTY_STATE_SET;
        int themeAttrColor = getThemeAttrColor(16842906);
        return new ColorStateList(new int[][]{iArr, iArr2}, new int[]{i, themeAttrColor});
    }

    private void expandHint(boolean z) {
        if (z) {
            animateToExpansionFraction(0.0f);
        } else {
            this.mCollapsingTextHelper.setExpansionFraction(0.0f);
        }
    }

    private int getThemeAttrColor(int i) {
        TypedValue typedValue = new TypedValue();
        return getContext().getTheme().resolveAttribute(i, typedValue, true) ? typedValue.data : -65281;
    }

    private LayoutParams setEditText(EditText editText, ViewGroup.LayoutParams layoutParams) {
        if (this.mEditText != null) {
            throw new IllegalArgumentException("We already have an EditText, can only have one");
        }
        this.mEditText = editText;
        this.mCollapsingTextHelper.setExpandedTextSize(this.mEditText.getTextSize());
        this.mEditText.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable editable) {
                TextInputLayout.this.mHandler.sendEmptyMessage(0);
            }

            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }
        });
        this.mEditText.setOnFocusChangeListener(new OnFocusChangeListener() {
            public void onFocusChange(View view, boolean z) {
                TextInputLayout.this.mHandler.sendEmptyMessage(0);
            }
        });
        if (TextUtils.isEmpty(this.mHint)) {
            setHint(this.mEditText.getHint());
            this.mEditText.setHint(null);
        }
        if (this.mErrorView != null) {
            ViewCompat.setPaddingRelative(this.mErrorView, ViewCompat.getPaddingStart(this.mEditText), 0, ViewCompat.getPaddingEnd(this.mEditText), this.mEditText.getPaddingBottom());
        }
        updateLabelVisibility(false);
        LayoutParams layoutParams2 = new LayoutParams(layoutParams);
        Paint paint = new Paint();
        paint.setTextSize(this.mCollapsingTextHelper.getExpandedTextSize());
        layoutParams2.topMargin = (int) (-paint.ascent());
        return layoutParams2;
    }

    private void updateLabelVisibility(boolean z) {
        Object obj = !TextUtils.isEmpty(this.mEditText.getText()) ? 1 : null;
        boolean isFocused = this.mEditText.isFocused();
        this.mCollapsingTextHelper.setCollapsedTextColor(this.mLabelTextColor.getColorForState(isFocused ? FOCUSED_STATE_SET : EMPTY_STATE_SET, this.mLabelTextColor.getDefaultColor()));
        if (obj != null || isFocused) {
            collapseHint(z);
        } else {
            expandHint(z);
        }
    }

    public void addView(View view, int i, ViewGroup.LayoutParams layoutParams) {
        if (view instanceof EditText) {
            super.addView(view, 0, setEditText((EditText) view, layoutParams));
        } else {
            super.addView(view, i, layoutParams);
        }
    }

    public void draw(Canvas canvas) {
        super.draw(canvas);
        this.mCollapsingTextHelper.draw(canvas);
    }

    public EditText getEditText() {
        return this.mEditText;
    }

    protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        this.mCollapsingTextHelper.onLayout(z, i, i2, i3, i4);
        if (this.mEditText != null) {
            int left = this.mEditText.getLeft() + this.mEditText.getPaddingLeft();
            int right = this.mEditText.getRight() - this.mEditText.getPaddingRight();
            this.mCollapsingTextHelper.setExpandedBounds(left, this.mEditText.getTop() + this.mEditText.getPaddingTop(), right, this.mEditText.getBottom() - this.mEditText.getPaddingBottom());
            this.mCollapsingTextHelper.setCollapsedBounds(left, getPaddingTop(), right, (i4 - i2) - getPaddingBottom());
        }
    }

    public void setError(CharSequence charSequence) {
        if (!this.mErrorEnabled) {
            if (!TextUtils.isEmpty(charSequence)) {
                setErrorEnabled(true);
            } else {
                return;
            }
        }
        if (!TextUtils.isEmpty(charSequence)) {
            this.mErrorView.setText(charSequence);
            this.mErrorView.setVisibility(0);
            ViewCompat.setAlpha(this.mErrorView, 0.0f);
            ViewCompat.animate(this.mErrorView).alpha(1.0f).setDuration(200).setInterpolator(AnimationUtils.FAST_OUT_SLOW_IN_INTERPOLATOR).setListener(null).start();
        } else if (this.mErrorView.getVisibility() == 0) {
            ViewCompat.animate(this.mErrorView).alpha(0.0f).setDuration(200).setInterpolator(AnimationUtils.FAST_OUT_SLOW_IN_INTERPOLATOR).setListener(new ViewPropertyAnimatorListenerAdapter() {
                public void onAnimationEnd(View view) {
                    TextInputLayout.this.mErrorView.setText(null);
                    TextInputLayout.this.mErrorView.setVisibility(4);
                }
            }).start();
        }
        sendAccessibilityEvent(2048);
    }

    public void setErrorEnabled(boolean z) {
        if (this.mErrorEnabled != z) {
            if (z) {
                this.mErrorView = new TextView(getContext());
                this.mErrorView.setTextAppearance(getContext(), this.mErrorTextAppearance);
                this.mErrorView.setVisibility(4);
                addView(this.mErrorView);
                if (this.mEditText != null) {
                    ViewCompat.setPaddingRelative(this.mErrorView, ViewCompat.getPaddingStart(this.mEditText), 0, ViewCompat.getPaddingEnd(this.mEditText), this.mEditText.getPaddingBottom());
                }
            } else {
                removeView(this.mErrorView);
                this.mErrorView = null;
            }
            this.mErrorEnabled = z;
        }
    }

    public void setHint(CharSequence charSequence) {
        this.mHint = charSequence;
        this.mCollapsingTextHelper.setText(charSequence);
        sendAccessibilityEvent(2048);
    }
}
