package android.support.v7.internal.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.ColorFilter;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Build.VERSION;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.ColorUtils;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v4.util.LruCache;
import android.support.v7.appcompat.R;
import android.util.SparseArray;
import android.view.View;
import java.lang.ref.WeakReference;
import java.util.WeakHashMap;

public final class TintManager {
    private static final int[] COLORFILTER_COLOR_BACKGROUND_MULTIPLY = new int[]{R.drawable.abc_popup_background_mtrl_mult, R.drawable.abc_cab_background_internal_bg, R.drawable.abc_menu_hardkey_panel_mtrl_mult};
    private static final int[] COLORFILTER_COLOR_CONTROL_ACTIVATED = new int[]{R.drawable.abc_textfield_activated_mtrl_alpha, R.drawable.abc_textfield_search_activated_mtrl_alpha, R.drawable.abc_cab_background_top_mtrl_alpha, R.drawable.abc_text_cursor_mtrl_alpha};
    private static final int[] COLORFILTER_TINT_COLOR_CONTROL_NORMAL = new int[]{R.drawable.abc_textfield_search_default_mtrl_alpha, R.drawable.abc_textfield_default_mtrl_alpha, R.drawable.abc_ab_share_pack_mtrl_alpha};
    private static final ColorFilterLruCache COLOR_FILTER_CACHE = new ColorFilterLruCache(6);
    private static final boolean DEBUG = false;
    private static final Mode DEFAULT_MODE = Mode.SRC_IN;
    private static final WeakHashMap<Context, TintManager> INSTANCE_CACHE = new WeakHashMap();
    public static final boolean SHOULD_BE_USED = (VERSION.SDK_INT < 21);
    private static final String TAG = "TintManager";
    private static final int[] TINT_CHECKABLE_BUTTON_LIST = new int[]{R.drawable.abc_btn_check_material, R.drawable.abc_btn_radio_material};
    private static final int[] TINT_COLOR_CONTROL_NORMAL = new int[]{R.drawable.abc_ic_ab_back_mtrl_am_alpha, R.drawable.abc_ic_go_search_api_mtrl_alpha, R.drawable.abc_ic_search_api_mtrl_alpha, R.drawable.abc_ic_commit_search_api_mtrl_alpha, R.drawable.abc_ic_clear_mtrl_alpha, R.drawable.abc_ic_menu_share_mtrl_alpha, R.drawable.abc_ic_menu_copy_mtrl_am_alpha, R.drawable.abc_ic_menu_cut_mtrl_alpha, R.drawable.abc_ic_menu_selectall_mtrl_alpha, R.drawable.abc_ic_menu_paste_mtrl_am_alpha, R.drawable.abc_ic_menu_moreoverflow_mtrl_alpha, R.drawable.abc_ic_voice_search_api_mtrl_alpha};
    private static final int[] TINT_COLOR_CONTROL_STATE_LIST = new int[]{R.drawable.abc_edit_text_material, R.drawable.abc_tab_indicator_material, R.drawable.abc_textfield_search_material, R.drawable.abc_spinner_mtrl_am_alpha, R.drawable.abc_spinner_textfield_background_material, R.drawable.abc_ratingbar_full_material, R.drawable.abc_switch_track_mtrl_alpha, R.drawable.abc_switch_thumb_material, R.drawable.abc_btn_default_mtrl_shape, R.drawable.abc_btn_borderless_material};
    private final WeakReference<Context> mContextRef;
    private ColorStateList mDefaultColorStateList;
    private SparseArray<ColorStateList> mTintLists;

    static class ColorFilterLruCache extends LruCache<Integer, PorterDuffColorFilter> {
        public ColorFilterLruCache(int i) {
            super(i);
        }

        private static int generateCacheKey(int i, Mode mode) {
            return ((i + 31) * 31) + mode.hashCode();
        }

        PorterDuffColorFilter get(int i, Mode mode) {
            return (PorterDuffColorFilter) get(Integer.valueOf(generateCacheKey(i, mode)));
        }

        PorterDuffColorFilter put(int i, Mode mode, PorterDuffColorFilter porterDuffColorFilter) {
            return (PorterDuffColorFilter) put(Integer.valueOf(generateCacheKey(i, mode)), porterDuffColorFilter);
        }
    }

    private TintManager(Context context) {
        this.mContextRef = new WeakReference(context);
    }

    private static boolean arrayContains(int[] iArr, int i) {
        for (int i2 : iArr) {
            if (i2 == i) {
                return true;
            }
        }
        return false;
    }

    private ColorStateList createButtonColorStateList(Context context) {
        int themeAttrColor = ThemeUtils.getThemeAttrColor(context, R.attr.colorButtonNormal);
        int themeAttrColor2 = ThemeUtils.getThemeAttrColor(context, R.attr.colorControlHighlight);
        int[] iArr = ThemeUtils.DISABLED_STATE_SET;
        int disabledThemeAttrColor = ThemeUtils.getDisabledThemeAttrColor(context, R.attr.colorButtonNormal);
        int[] iArr2 = ThemeUtils.PRESSED_STATE_SET;
        int compositeColors = ColorUtils.compositeColors(themeAttrColor2, themeAttrColor);
        int[] iArr3 = ThemeUtils.FOCUSED_STATE_SET;
        themeAttrColor2 = ColorUtils.compositeColors(themeAttrColor2, themeAttrColor);
        return new ColorStateList(new int[][]{iArr, iArr2, iArr3, ThemeUtils.EMPTY_STATE_SET}, new int[]{disabledThemeAttrColor, compositeColors, themeAttrColor2, themeAttrColor});
    }

    private ColorStateList createCheckableButtonColorStateList(Context context) {
        int[] iArr = ThemeUtils.DISABLED_STATE_SET;
        int disabledThemeAttrColor = ThemeUtils.getDisabledThemeAttrColor(context, R.attr.colorControlNormal);
        int[] iArr2 = ThemeUtils.CHECKED_STATE_SET;
        int themeAttrColor = ThemeUtils.getThemeAttrColor(context, R.attr.colorControlActivated);
        int[] iArr3 = ThemeUtils.EMPTY_STATE_SET;
        int themeAttrColor2 = ThemeUtils.getThemeAttrColor(context, R.attr.colorControlNormal);
        return new ColorStateList(new int[][]{iArr, iArr2, iArr3}, new int[]{disabledThemeAttrColor, themeAttrColor, themeAttrColor2});
    }

    private ColorStateList createEditTextColorStateList(Context context) {
        int[] iArr = ThemeUtils.DISABLED_STATE_SET;
        int disabledThemeAttrColor = ThemeUtils.getDisabledThemeAttrColor(context, R.attr.colorControlNormal);
        int[] iArr2 = ThemeUtils.NOT_PRESSED_OR_FOCUSED_STATE_SET;
        int themeAttrColor = ThemeUtils.getThemeAttrColor(context, R.attr.colorControlNormal);
        int[] iArr3 = ThemeUtils.EMPTY_STATE_SET;
        int themeAttrColor2 = ThemeUtils.getThemeAttrColor(context, R.attr.colorControlActivated);
        return new ColorStateList(new int[][]{iArr, iArr2, iArr3}, new int[]{disabledThemeAttrColor, themeAttrColor, themeAttrColor2});
    }

    private ColorStateList createSpinnerColorStateList(Context context) {
        int[] iArr = ThemeUtils.DISABLED_STATE_SET;
        int disabledThemeAttrColor = ThemeUtils.getDisabledThemeAttrColor(context, R.attr.colorControlNormal);
        int[] iArr2 = ThemeUtils.NOT_PRESSED_OR_FOCUSED_STATE_SET;
        int themeAttrColor = ThemeUtils.getThemeAttrColor(context, R.attr.colorControlNormal);
        int[] iArr3 = ThemeUtils.EMPTY_STATE_SET;
        int themeAttrColor2 = ThemeUtils.getThemeAttrColor(context, R.attr.colorControlActivated);
        return new ColorStateList(new int[][]{iArr, iArr2, iArr3}, new int[]{disabledThemeAttrColor, themeAttrColor, themeAttrColor2});
    }

    private ColorStateList createSwitchThumbColorStateList(Context context) {
        int[][] iArr = new int[3][];
        int[] iArr2 = new int[3];
        ColorStateList themeAttrColorStateList = ThemeUtils.getThemeAttrColorStateList(context, R.attr.colorSwitchThumbNormal);
        if (themeAttrColorStateList == null || !themeAttrColorStateList.isStateful()) {
            iArr[0] = ThemeUtils.DISABLED_STATE_SET;
            iArr2[0] = ThemeUtils.getDisabledThemeAttrColor(context, R.attr.colorSwitchThumbNormal);
            iArr[1] = ThemeUtils.CHECKED_STATE_SET;
            iArr2[1] = ThemeUtils.getThemeAttrColor(context, R.attr.colorControlActivated);
            iArr[2] = ThemeUtils.EMPTY_STATE_SET;
            iArr2[2] = ThemeUtils.getThemeAttrColor(context, R.attr.colorSwitchThumbNormal);
        } else {
            iArr[0] = ThemeUtils.DISABLED_STATE_SET;
            iArr2[0] = themeAttrColorStateList.getColorForState(iArr[0], 0);
            iArr[1] = ThemeUtils.CHECKED_STATE_SET;
            iArr2[1] = ThemeUtils.getThemeAttrColor(context, R.attr.colorControlActivated);
            iArr[2] = ThemeUtils.EMPTY_STATE_SET;
            iArr2[2] = themeAttrColorStateList.getDefaultColor();
        }
        return new ColorStateList(iArr, iArr2);
    }

    private ColorStateList createSwitchTrackColorStateList(Context context) {
        int[] iArr = ThemeUtils.DISABLED_STATE_SET;
        int themeAttrColor = ThemeUtils.getThemeAttrColor(context, 16842800, 0.1f);
        int[] iArr2 = ThemeUtils.CHECKED_STATE_SET;
        int themeAttrColor2 = ThemeUtils.getThemeAttrColor(context, R.attr.colorControlActivated, 0.3f);
        int[] iArr3 = ThemeUtils.EMPTY_STATE_SET;
        int themeAttrColor3 = ThemeUtils.getThemeAttrColor(context, 16842800, 0.3f);
        return new ColorStateList(new int[][]{iArr, iArr2, iArr3}, new int[]{themeAttrColor, themeAttrColor2, themeAttrColor3});
    }

    public static TintManager get(Context context) {
        TintManager tintManager = (TintManager) INSTANCE_CACHE.get(context);
        if (tintManager != null) {
            return tintManager;
        }
        tintManager = new TintManager(context);
        INSTANCE_CACHE.put(context, tintManager);
        return tintManager;
    }

    private ColorStateList getDefaultColorStateList(Context context) {
        if (this.mDefaultColorStateList == null) {
            int themeAttrColor = ThemeUtils.getThemeAttrColor(context, R.attr.colorControlNormal);
            int themeAttrColor2 = ThemeUtils.getThemeAttrColor(context, R.attr.colorControlActivated);
            int[] iArr = ThemeUtils.DISABLED_STATE_SET;
            int disabledThemeAttrColor = ThemeUtils.getDisabledThemeAttrColor(context, R.attr.colorControlNormal);
            this.mDefaultColorStateList = new ColorStateList(new int[][]{iArr, ThemeUtils.FOCUSED_STATE_SET, ThemeUtils.ACTIVATED_STATE_SET, ThemeUtils.PRESSED_STATE_SET, ThemeUtils.CHECKED_STATE_SET, ThemeUtils.SELECTED_STATE_SET, ThemeUtils.EMPTY_STATE_SET}, new int[]{disabledThemeAttrColor, themeAttrColor2, themeAttrColor2, themeAttrColor2, themeAttrColor2, themeAttrColor2, themeAttrColor});
        }
        return this.mDefaultColorStateList;
    }

    public static Drawable getDrawable(Context context, int i) {
        return isInTintList(i) ? get(context).getDrawable(i) : ContextCompat.getDrawable(context, i);
    }

    private static boolean isInTintList(int i) {
        return arrayContains(TINT_COLOR_CONTROL_NORMAL, i) || arrayContains(COLORFILTER_TINT_COLOR_CONTROL_NORMAL, i) || arrayContains(COLORFILTER_COLOR_CONTROL_ACTIVATED, i) || arrayContains(TINT_COLOR_CONTROL_STATE_LIST, i) || arrayContains(COLORFILTER_COLOR_BACKGROUND_MULTIPLY, i) || arrayContains(TINT_CHECKABLE_BUTTON_LIST, i) || i == R.drawable.abc_cab_background_top_material;
    }

    private static void setPorterDuffColorFilter(Drawable drawable, int i, Mode mode) {
        if (mode == null) {
            mode = DEFAULT_MODE;
        }
        ColorFilter colorFilter = COLOR_FILTER_CACHE.get(i, mode);
        if (colorFilter == null) {
            colorFilter = new PorterDuffColorFilter(i, mode);
            COLOR_FILTER_CACHE.put(i, mode, colorFilter);
        }
        drawable.setColorFilter(colorFilter);
    }

    public static void tintViewBackground(View view, TintInfo tintInfo) {
        Drawable background = view.getBackground();
        if (tintInfo.mHasTintList) {
            setPorterDuffColorFilter(background, tintInfo.mTintList.getColorForState(view.getDrawableState(), tintInfo.mTintList.getDefaultColor()), tintInfo.mHasTintMode ? tintInfo.mTintMode : null);
        } else {
            background.clearColorFilter();
        }
        if (VERSION.SDK_INT <= 10) {
            view.invalidate();
        }
    }

    public final Drawable getDrawable(int i) {
        return getDrawable(i, false);
    }

    public final Drawable getDrawable(int i, boolean z) {
        Context context = (Context) this.mContextRef.get();
        if (context == null) {
            return null;
        }
        Drawable drawable = ContextCompat.getDrawable(context, i);
        if (drawable != null) {
            if (VERSION.SDK_INT >= 8) {
                drawable = drawable.mutate();
            }
            ColorStateList tintList = getTintList(i);
            if (tintList != null) {
                drawable = DrawableCompat.wrap(drawable);
                DrawableCompat.setTintList(drawable, tintList);
                Mode tintMode = getTintMode(i);
                if (tintMode != null) {
                    DrawableCompat.setTintMode(drawable, tintMode);
                }
            } else if (i == R.drawable.abc_cab_background_top_material) {
                return new LayerDrawable(new Drawable[]{getDrawable(R.drawable.abc_cab_background_internal_bg), getDrawable(R.drawable.abc_cab_background_top_mtrl_alpha)});
            } else if (!tintDrawableUsingColorFilter(i, drawable) && z) {
                drawable = null;
            }
        }
        return drawable;
    }

    public final ColorStateList getTintList(int i) {
        ColorStateList colorStateList = null;
        Context context = (Context) this.mContextRef.get();
        if (context == null) {
            return null;
        }
        if (this.mTintLists != null) {
            colorStateList = (ColorStateList) this.mTintLists.get(i);
        }
        if (colorStateList != null) {
            return colorStateList;
        }
        ColorStateList createEditTextColorStateList = i == R.drawable.abc_edit_text_material ? createEditTextColorStateList(context) : i == R.drawable.abc_switch_track_mtrl_alpha ? createSwitchTrackColorStateList(context) : i == R.drawable.abc_switch_thumb_material ? createSwitchThumbColorStateList(context) : (i == R.drawable.abc_btn_default_mtrl_shape || i == R.drawable.abc_btn_borderless_material) ? createButtonColorStateList(context) : (i == R.drawable.abc_spinner_mtrl_am_alpha || i == R.drawable.abc_spinner_textfield_background_material) ? createSpinnerColorStateList(context) : arrayContains(TINT_COLOR_CONTROL_NORMAL, i) ? ThemeUtils.getThemeAttrColorStateList(context, R.attr.colorControlNormal) : arrayContains(TINT_COLOR_CONTROL_STATE_LIST, i) ? getDefaultColorStateList(context) : arrayContains(TINT_CHECKABLE_BUTTON_LIST, i) ? createCheckableButtonColorStateList(context) : colorStateList;
        if (createEditTextColorStateList == null) {
            return createEditTextColorStateList;
        }
        if (this.mTintLists == null) {
            this.mTintLists = new SparseArray();
        }
        this.mTintLists.append(i, createEditTextColorStateList);
        return createEditTextColorStateList;
    }

    final Mode getTintMode(int i) {
        return i == R.drawable.abc_switch_thumb_material ? Mode.MULTIPLY : null;
    }

    public final boolean tintDrawableUsingColorFilter(int i, Drawable drawable) {
        Mode mode = null;
        Context context = (Context) this.mContextRef.get();
        if (context == null) {
            return false;
        }
        int i2;
        int i3;
        Object obj;
        if (arrayContains(COLORFILTER_TINT_COLOR_CONTROL_NORMAL, i)) {
            i2 = R.attr.colorControlNormal;
            i3 = -1;
            obj = 1;
        } else if (arrayContains(COLORFILTER_COLOR_CONTROL_ACTIVATED, i)) {
            i2 = R.attr.colorControlActivated;
            i3 = -1;
            r4 = 1;
        } else if (arrayContains(COLORFILTER_COLOR_BACKGROUND_MULTIPLY, i)) {
            mode = Mode.MULTIPLY;
            i2 = 16842801;
            i3 = -1;
            r4 = 1;
        } else if (i == R.drawable.abc_list_divider_mtrl_alpha) {
            i3 = Math.round(40.8f);
            i2 = 16842800;
            r4 = 1;
        } else {
            obj = null;
            i2 = 0;
            i3 = -1;
        }
        if (obj == null) {
            return false;
        }
        setPorterDuffColorFilter(drawable, ThemeUtils.getThemeAttrColor(context, i2), mode);
        if (i3 != -1) {
            drawable.setAlpha(i3);
        }
        return true;
    }
}
