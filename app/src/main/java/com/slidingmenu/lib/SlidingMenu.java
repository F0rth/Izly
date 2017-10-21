package com.slidingmenu.lib;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.os.Handler;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.BaseSavedState;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import com.slidingmenu.lib.CustomViewAbove.OnPageChangeListener;
import defpackage.kh;

public class SlidingMenu extends RelativeLayout {
    public static final int LEFT = 0;
    public static final int LEFT_RIGHT = 2;
    public static final int RIGHT = 1;
    public static final int SLIDING_CONTENT = 1;
    public static final int SLIDING_WINDOW = 0;
    private static final String TAG = "SlidingMenu";
    public static final int TOUCHMODE_FULLSCREEN = 1;
    public static final int TOUCHMODE_MARGIN = 0;
    public static final int TOUCHMODE_NONE = 2;
    private boolean mActionbarOverlay;
    private OnCloseListener mCloseListener;
    private Handler mHandler;
    private OnOpenListener mOpenListener;
    private CustomViewAbove mViewAbove;
    private CustomViewBehind mViewBehind;

    public interface CanvasTransformer {
        void transformCanvas(Canvas canvas, float f);
    }

    public interface OnCloseListener {
        void onClose();
    }

    public interface OnClosedListener {
        void onClosed();
    }

    public interface OnOpenListener {
        void onOpen();
    }

    public interface OnOpenedListener {
        void onOpened();
    }

    public static class SavedState extends BaseSavedState {
        public static final Creator<SavedState> CREATOR = new Creator<SavedState>() {
            public final SavedState createFromParcel(Parcel parcel) {
                return new SavedState(parcel);
            }

            public final SavedState[] newArray(int i) {
                return new SavedState[i];
            }
        };
        private final int mItem;

        private SavedState(Parcel parcel) {
            super(parcel);
            this.mItem = parcel.readInt();
        }

        public SavedState(Parcelable parcelable, int i) {
            super(parcelable);
            this.mItem = i;
        }

        public int getItem() {
            return this.mItem;
        }

        public void writeToParcel(Parcel parcel, int i) {
            super.writeToParcel(parcel, i);
            parcel.writeInt(this.mItem);
        }
    }

    public SlidingMenu(Activity activity, int i) {
        this((Context) activity, null);
        attachToActivity(activity, i);
    }

    public SlidingMenu(Context context) {
        this(context, null);
    }

    public SlidingMenu(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public SlidingMenu(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mActionbarOverlay = false;
        this.mHandler = new Handler();
        LayoutParams layoutParams = new RelativeLayout.LayoutParams(-1, -1);
        this.mViewBehind = new CustomViewBehind(context);
        addView(this.mViewBehind, layoutParams);
        layoutParams = new RelativeLayout.LayoutParams(-1, -1);
        this.mViewAbove = new CustomViewAbove(context);
        addView(this.mViewAbove, layoutParams);
        this.mViewAbove.setCustomViewBehind(this.mViewBehind);
        this.mViewBehind.setCustomViewAbove(this.mViewAbove);
        this.mViewAbove.setOnPageChangeListener(new OnPageChangeListener() {
            public static final int POSITION_CLOSE = 1;
            public static final int POSITION_OPEN = 0;

            public void onPageScrolled(int i, float f, int i2) {
            }

            public void onPageSelected(int i) {
                if (i == 0 && SlidingMenu.this.mOpenListener != null) {
                    SlidingMenu.this.mOpenListener.onOpen();
                } else if (i == 1 && SlidingMenu.this.mCloseListener != null) {
                    SlidingMenu.this.mCloseListener.onClose();
                }
            }
        });
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.SlidingMenu);
        setMode(obtainStyledAttributes.getInt(R.styleable.SlidingMenu_mode, 0));
        int resourceId = obtainStyledAttributes.getResourceId(R.styleable.SlidingMenu_viewAbove, -1);
        if (resourceId != -1) {
            setContent(resourceId);
        } else {
            setContent(new FrameLayout(context));
        }
        resourceId = obtainStyledAttributes.getResourceId(R.styleable.SlidingMenu_viewBehind, -1);
        if (resourceId != -1) {
            setMenu(resourceId);
        } else {
            setMenu(new FrameLayout(context));
        }
        setTouchModeAbove(obtainStyledAttributes.getInt(R.styleable.SlidingMenu_touchModeAbove, 0));
        setTouchModeBehind(obtainStyledAttributes.getInt(R.styleable.SlidingMenu_touchModeBehind, 0));
        resourceId = (int) obtainStyledAttributes.getDimension(R.styleable.SlidingMenu_behindOffset, -1.0f);
        int dimension = (int) obtainStyledAttributes.getDimension(R.styleable.SlidingMenu_behindWidth, -1.0f);
        if (resourceId == -1 || dimension == -1) {
            if (resourceId != -1) {
                setBehindOffset(resourceId);
            } else if (dimension != -1) {
                setBehindWidth(dimension);
            } else {
                setBehindOffset(0);
            }
            setBehindScrollScale(obtainStyledAttributes.getFloat(R.styleable.SlidingMenu_behindScrollScale, 0.33f));
            resourceId = obtainStyledAttributes.getResourceId(R.styleable.SlidingMenu_shadowDrawable, -1);
            if (resourceId != -1) {
                setShadowDrawable(resourceId);
            }
            setShadowWidth((int) obtainStyledAttributes.getDimension(R.styleable.SlidingMenu_shadowWidth, 0.0f));
            setFadeEnabled(obtainStyledAttributes.getBoolean(R.styleable.SlidingMenu_fadeEnabled, true));
            setFadeDegree(obtainStyledAttributes.getFloat(R.styleable.SlidingMenu_fadeDegree, 0.33f));
            setSelectorEnabled(obtainStyledAttributes.getBoolean(R.styleable.SlidingMenu_selectorEnabled, false));
            resourceId = obtainStyledAttributes.getResourceId(R.styleable.SlidingMenu_selectorDrawable, -1);
            if (resourceId != -1) {
                setSelectorDrawable(resourceId);
            }
            obtainStyledAttributes.recycle();
            return;
        }
        throw new IllegalStateException("Cannot set both behindOffset and behindWidth for a SlidingMenu");
    }

    public void addIgnoredView(View view) {
        this.mViewAbove.addIgnoredView(view);
    }

    public void attachToActivity(Activity activity, int i) {
        attachToActivity(activity, i, false);
    }

    public void attachToActivity(Activity activity, int i, boolean z) {
        if (i != 0 && i != 1) {
            throw new IllegalArgumentException("slideStyle must be either SLIDING_WINDOW or SLIDING_CONTENT");
        } else if (getParent() != null) {
            throw new IllegalStateException("This SlidingMenu appears to already be attached");
        } else {
            TypedArray obtainStyledAttributes = activity.getTheme().obtainStyledAttributes(new int[]{16842836});
            int resourceId = obtainStyledAttributes.getResourceId(0, 0);
            obtainStyledAttributes.recycle();
            ViewGroup viewGroup;
            View view;
            switch (i) {
                case 0:
                    this.mActionbarOverlay = false;
                    viewGroup = (ViewGroup) activity.getWindow().getDecorView();
                    view = (ViewGroup) viewGroup.getChildAt(0);
                    view.setBackgroundResource(resourceId);
                    viewGroup.removeView(view);
                    viewGroup.addView(this);
                    setContent(view);
                    return;
                case 1:
                    this.mActionbarOverlay = z;
                    viewGroup = (ViewGroup) activity.findViewById(16908290);
                    view = viewGroup.getChildAt(0);
                    viewGroup.removeView(view);
                    viewGroup.addView(this);
                    setContent(view);
                    if (view.getBackground() == null) {
                        view.setBackgroundResource(resourceId);
                        return;
                    }
                    return;
                default:
                    return;
            }
        }
    }

    public void clearIgnoredViews() {
        this.mViewAbove.clearIgnoredViews();
    }

    @SuppressLint({"NewApi"})
    protected boolean fitSystemWindows(Rect rect) {
        int i = rect.left;
        int i2 = rect.right;
        int i3 = rect.top;
        int i4 = rect.bottom;
        if (VERSION.SDK_INT >= 21) {
            Resources resources = getContent().getResources();
            if (hasNavBar(resources)) {
                int identifier = resources.getIdentifier("navigation_bar_height", "dimen", kh.ANDROID_CLIENT_TYPE);
                if (identifier > 0) {
                    i4 += resources.getDimensionPixelSize(identifier);
                }
            }
        }
        if (!this.mActionbarOverlay) {
            Log.v(TAG, "setting padding!");
            setPadding(i, i3, i2, i4);
        }
        return true;
    }

    public int getBehindOffset() {
        return ((RelativeLayout.LayoutParams) this.mViewBehind.getLayoutParams()).rightMargin;
    }

    public float getBehindScrollScale() {
        return this.mViewBehind.getScrollScale();
    }

    public View getContent() {
        return this.mViewAbove.getContent();
    }

    public View getMenu() {
        return this.mViewBehind.getContent();
    }

    public int getMode() {
        return this.mViewBehind.getMode();
    }

    public View getSecondaryMenu() {
        return this.mViewBehind.getSecondaryContent();
    }

    public int getTouchModeAbove() {
        return this.mViewAbove.getTouchMode();
    }

    public boolean hasNavBar(Resources resources) {
        int identifier = resources.getIdentifier("config_showNavigationBar", "bool", kh.ANDROID_CLIENT_TYPE);
        return identifier > 0 && resources.getBoolean(identifier);
    }

    public boolean isMenuShowing() {
        return this.mViewAbove.getCurrentItem() == 0 || this.mViewAbove.getCurrentItem() == 2;
    }

    public boolean isSecondaryMenuShowing() {
        return this.mViewAbove.getCurrentItem() == 2;
    }

    public boolean isSlidingEnabled() {
        return this.mViewAbove.isSlidingEnabled();
    }

    @TargetApi(11)
    public void manageLayers(float f) {
        int i = 0;
        if (VERSION.SDK_INT >= 11) {
            int i2 = (f <= 0.0f || f >= 1.0f) ? 0 : 1;
            if (i2 != 0) {
                i = 2;
            }
            if (i != getContent().getLayerType()) {
                this.mHandler.post(new Runnable() {
                    public void run() {
                        Log.v(SlidingMenu.TAG, "changing layerType. hardware? " + (i == 2));
                        SlidingMenu.this.getContent().setLayerType(i, null);
                        SlidingMenu.this.getMenu().setLayerType(i, null);
                        if (SlidingMenu.this.getSecondaryMenu() != null) {
                            SlidingMenu.this.getSecondaryMenu().setLayerType(i, null);
                        }
                    }
                });
            }
        }
    }

    protected void onRestoreInstanceState(Parcelable parcelable) {
        SavedState savedState = (SavedState) parcelable;
        super.onRestoreInstanceState(savedState.getSuperState());
        this.mViewAbove.setCurrentItem(savedState.getItem());
    }

    protected Parcelable onSaveInstanceState() {
        return new SavedState(super.onSaveInstanceState(), this.mViewAbove.getCurrentItem());
    }

    public void removeIgnoredView(View view) {
        this.mViewAbove.removeIgnoredView(view);
    }

    public void setAboveOffset(int i) {
        this.mViewAbove.setAboveOffset(i);
    }

    public void setAboveOffsetRes(int i) {
        setAboveOffset((int) getContext().getResources().getDimension(i));
    }

    public void setBehindCanvasTransformer(CanvasTransformer canvasTransformer) {
        this.mViewBehind.setCanvasTransformer(canvasTransformer);
    }

    public void setBehindOffset(int i) {
        this.mViewBehind.setWidthOffset(i);
    }

    public void setBehindOffsetRes(int i) {
        setBehindOffset((int) getContext().getResources().getDimension(i));
    }

    public void setBehindScrollScale(float f) {
        if (f >= 0.0f || f <= 1.0f) {
            this.mViewBehind.setScrollScale(f);
            return;
        }
        throw new IllegalStateException("ScrollScale must be between 0 and 1");
    }

    public void setBehindWidth(int i) {
        int i2;
        Display defaultDisplay = ((WindowManager) getContext().getSystemService("window")).getDefaultDisplay();
        try {
            Point point = new Point();
            Display.class.getMethod("getSize", new Class[]{Point.class}).invoke(defaultDisplay, new Object[]{point});
            i2 = point.x;
        } catch (Exception e) {
            i2 = defaultDisplay.getWidth();
        }
        setBehindOffset(i2 - i);
    }

    public void setBehindWidthRes(int i) {
        setBehindWidth((int) getContext().getResources().getDimension(i));
    }

    public void setContent(int i) {
        setContent(LayoutInflater.from(getContext()).inflate(i, null));
    }

    public void setContent(View view) {
        this.mViewAbove.setContent(view);
        showContent();
    }

    public void setFadeDegree(float f) {
        this.mViewBehind.setFadeDegree(f);
    }

    public void setFadeEnabled(boolean z) {
        this.mViewBehind.setFadeEnabled(z);
    }

    public void setMenu(int i) {
        setMenu(LayoutInflater.from(getContext()).inflate(i, null));
    }

    public void setMenu(View view) {
        this.mViewBehind.setContent(view);
    }

    public void setMode(int i) {
        if (i == 0 || i == 1 || i == 2) {
            this.mViewBehind.setMode(i);
            return;
        }
        throw new IllegalStateException("SlidingMenu mode must be LEFT, RIGHT, or LEFT_RIGHT");
    }

    public void setOnCloseListener(OnCloseListener onCloseListener) {
        this.mCloseListener = onCloseListener;
    }

    public void setOnClosedListener(OnClosedListener onClosedListener) {
        this.mViewAbove.setOnClosedListener(onClosedListener);
    }

    public void setOnOpenListener(OnOpenListener onOpenListener) {
        this.mOpenListener = onOpenListener;
    }

    public void setOnOpenedListener(OnOpenedListener onOpenedListener) {
        this.mViewAbove.setOnOpenedListener(onOpenedListener);
    }

    public void setSecondaryMenu(int i) {
        setSecondaryMenu(LayoutInflater.from(getContext()).inflate(i, null));
    }

    public void setSecondaryMenu(View view) {
        this.mViewBehind.setSecondaryContent(view);
    }

    public void setSecondaryShadowDrawable(int i) {
        setSecondaryShadowDrawable(getContext().getResources().getDrawable(i));
    }

    public void setSecondaryShadowDrawable(Drawable drawable) {
        this.mViewBehind.setSecondaryShadowDrawable(drawable);
    }

    public void setSelectedView(View view) {
        this.mViewBehind.setSelectedView(view);
    }

    public void setSelectorBitmap(Bitmap bitmap) {
        this.mViewBehind.setSelectorBitmap(bitmap);
    }

    public void setSelectorDrawable(int i) {
        this.mViewBehind.setSelectorBitmap(BitmapFactory.decodeResource(getResources(), i));
    }

    public void setSelectorEnabled(boolean z) {
        this.mViewBehind.setSelectorEnabled(true);
    }

    public void setShadowDrawable(int i) {
        setShadowDrawable(getContext().getResources().getDrawable(i));
    }

    public void setShadowDrawable(Drawable drawable) {
        this.mViewBehind.setShadowDrawable(drawable);
    }

    public void setShadowWidth(int i) {
        this.mViewBehind.setShadowWidth(i);
    }

    public void setShadowWidthRes(int i) {
        setShadowWidth((int) getResources().getDimension(i));
    }

    public void setSlidingEnabled(boolean z) {
        this.mViewAbove.setSlidingEnabled(z);
    }

    public void setStatic(boolean z) {
        if (z) {
            setSlidingEnabled(false);
            this.mViewAbove.setCustomViewBehind(null);
            this.mViewAbove.setCurrentItem(1);
            return;
        }
        this.mViewAbove.setCurrentItem(1);
        this.mViewAbove.setCustomViewBehind(this.mViewBehind);
        setSlidingEnabled(true);
    }

    public void setTouchModeAbove(int i) {
        if (i == 1 || i == 0 || i == 2) {
            this.mViewAbove.setTouchMode(i);
            return;
        }
        throw new IllegalStateException("TouchMode must be set to eitherTOUCHMODE_FULLSCREEN or TOUCHMODE_MARGIN or TOUCHMODE_NONE.");
    }

    public void setTouchModeBehind(int i) {
        if (i == 1 || i == 0 || i == 2) {
            this.mViewBehind.setTouchMode(i);
            return;
        }
        throw new IllegalStateException("TouchMode must be set to eitherTOUCHMODE_FULLSCREEN or TOUCHMODE_MARGIN or TOUCHMODE_NONE.");
    }

    public void showContent() {
        showContent(true);
    }

    public void showContent(boolean z) {
        this.mViewAbove.setCurrentItem(1, z);
    }

    public void showMenu() {
        showMenu(true);
    }

    public void showMenu(boolean z) {
        this.mViewAbove.setCurrentItem(0, z);
    }

    public void showSecondaryMenu() {
        showSecondaryMenu(true);
    }

    public void showSecondaryMenu(boolean z) {
        this.mViewAbove.setCurrentItem(2, z);
    }

    public void toggle() {
        toggle(true);
    }

    public void toggle(boolean z) {
        if (isMenuShowing()) {
            showContent(z);
        } else {
            showMenu(z);
        }
    }
}
