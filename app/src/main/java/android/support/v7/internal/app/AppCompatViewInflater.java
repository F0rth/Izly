package android.support.v7.internal.app;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.NonNull;
import android.support.v4.util.ArrayMap;
import android.support.v7.appcompat.R;
import android.support.v7.internal.view.ContextThemeWrapper;
import android.support.v7.widget.AppCompatAutoCompleteTextView;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.AppCompatCheckedTextView;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatMultiAutoCompleteTextView;
import android.support.v7.widget.AppCompatRadioButton;
import android.support.v7.widget.AppCompatRatingBar;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.InflateException;
import android.view.View;
import java.lang.reflect.Constructor;
import java.util.Map;

public class AppCompatViewInflater {
    private static final String LOG_TAG = "AppCompatViewInflater";
    private static final Map<String, Constructor<? extends View>> sConstructorMap = new ArrayMap();
    static final Class<?>[] sConstructorSignature = new Class[]{Context.class, AttributeSet.class};
    private final Object[] mConstructorArgs = new Object[2];

    private View createView(Context context, String str, String str2) throws ClassNotFoundException, InflateException {
        Constructor constructor = (Constructor) sConstructorMap.get(str);
        if (constructor == null) {
            try {
                constructor = context.getClassLoader().loadClass(str2 != null ? str2 + str : str).asSubclass(View.class).getConstructor(sConstructorSignature);
                sConstructorMap.put(str, constructor);
            } catch (Exception e) {
                return null;
            }
        }
        constructor.setAccessible(true);
        return (View) constructor.newInstance(this.mConstructorArgs);
    }

    private View createViewFromTag(Context context, String str, AttributeSet attributeSet) {
        if (str.equals("view")) {
            str = attributeSet.getAttributeValue(null, "class");
        }
        try {
            this.mConstructorArgs[0] = context;
            this.mConstructorArgs[1] = attributeSet;
            View createView;
            if (-1 == str.indexOf(46)) {
                createView = createView(context, str, "android.widget.");
                return createView;
            }
            createView = createView(context, str, null);
            this.mConstructorArgs[0] = null;
            this.mConstructorArgs[1] = null;
            return createView;
        } catch (Exception e) {
            return null;
        } finally {
            this.mConstructorArgs[0] = null;
            this.mConstructorArgs[1] = null;
        }
    }

    private static Context themifyContext(Context context, AttributeSet attributeSet, boolean z, boolean z2) {
        int i;
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.View, 0, 0);
        int resourceId = z ? obtainStyledAttributes.getResourceId(R.styleable.View_android_theme, 0) : 0;
        if (z2 && resourceId == 0) {
            resourceId = obtainStyledAttributes.getResourceId(R.styleable.View_theme, 0);
            if (resourceId != 0) {
                Log.i(LOG_TAG, "app:theme is now deprecated. Please move to using android:theme instead.");
                i = resourceId;
                obtainStyledAttributes.recycle();
                return i == 0 ? ((context instanceof ContextThemeWrapper) || ((ContextThemeWrapper) context).getThemeResId() != i) ? new ContextThemeWrapper(context, i) : context : context;
            }
        }
        i = resourceId;
        obtainStyledAttributes.recycle();
        if (i == 0) {
        }
        if (context instanceof ContextThemeWrapper) {
        }
    }

    public final View createView(View view, String str, @NonNull Context context, @NonNull AttributeSet attributeSet, boolean z, boolean z2, boolean z3) {
        Context context2 = (!z || view == null) ? context : view.getContext();
        if (z2 || z3) {
            context2 = themifyContext(context2, attributeSet, z2, z3);
        }
        Object obj = -1;
        switch (str.hashCode()) {
            case -1946472170:
                if (str.equals("RatingBar")) {
                    obj = 7;
                    break;
                }
                break;
            case -1455429095:
                if (str.equals("CheckedTextView")) {
                    obj = 4;
                    break;
                }
                break;
            case -1346021293:
                if (str.equals("MultiAutoCompleteTextView")) {
                    obj = 6;
                    break;
                }
                break;
            case -938935918:
                if (str.equals("TextView")) {
                    obj = 9;
                    break;
                }
                break;
            case -339785223:
                if (str.equals("Spinner")) {
                    obj = 1;
                    break;
                }
                break;
            case 776382189:
                if (str.equals("RadioButton")) {
                    obj = 3;
                    break;
                }
                break;
            case 1413872058:
                if (str.equals("AutoCompleteTextView")) {
                    obj = 5;
                    break;
                }
                break;
            case 1601505219:
                if (str.equals("CheckBox")) {
                    obj = 2;
                    break;
                }
                break;
            case 1666676343:
                if (str.equals("EditText")) {
                    obj = null;
                    break;
                }
                break;
            case 2001146706:
                if (str.equals("Button")) {
                    obj = 8;
                    break;
                }
                break;
        }
        switch (obj) {
            case null:
                return new AppCompatEditText(context2, attributeSet);
            case 1:
                return new AppCompatSpinner(context2, attributeSet);
            case 2:
                return new AppCompatCheckBox(context2, attributeSet);
            case 3:
                return new AppCompatRadioButton(context2, attributeSet);
            case 4:
                return new AppCompatCheckedTextView(context2, attributeSet);
            case 5:
                return new AppCompatAutoCompleteTextView(context2, attributeSet);
            case 6:
                return new AppCompatMultiAutoCompleteTextView(context2, attributeSet);
            case 7:
                return new AppCompatRatingBar(context2, attributeSet);
            case 8:
                return new AppCompatButton(context2, attributeSet);
            case 9:
                return new AppCompatTextView(context2, attributeSet);
            default:
                return context != context2 ? createViewFromTag(context2, str, attributeSet) : null;
        }
    }
}
