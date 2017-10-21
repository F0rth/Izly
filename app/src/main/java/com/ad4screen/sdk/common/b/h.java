package com.ad4screen.sdk.common.b;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Environment;
import android.os.HandlerThread;
import android.os.Looper;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.view.Window;
import android.view.WindowManager.LayoutParams;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.webkit.WebView;

import com.ad4screen.sdk.Log;
import com.ad4screen.sdk.R;

import java.io.File;

@TargetApi(4)
public final class h {
    public static File a(Context context) {
        return new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/Android/data/files/" + context.getPackageName() + "/");
    }

    public static String a(byte[] bArr) {
        return new String(bArr);
    }

    public static void a(Activity activity) {
        Window window = activity.getWindow();
        LayoutParams attributes = window.getAttributes();
        DisplayMetrics displayMetrics = activity.getResources().getDisplayMetrics();
        TypedArray obtainStyledAttributes = activity.getTheme().obtainStyledAttributes(R.styleable.Window);
        int i = (displayMetrics.widthPixels < displayMetrics.heightPixels ? 1 : null) != null ? R.styleable.Window_com_ad4screen_sdk_windowWidthMinor : R.styleable.Window_com_ad4screen_sdk_windowWidthMajor;
        if (obtainStyledAttributes != null && obtainStyledAttributes.hasValue(i)) {
            TypedValue typedValue = new TypedValue();
            obtainStyledAttributes.getValue(i, typedValue);
            if (typedValue.type != 0) {
                if (typedValue.type == 5) {
                    attributes.width = (int) typedValue.getDimension(displayMetrics);
                } else if (typedValue.type == 6) {
                    attributes.width = (int) typedValue.getFraction((float) displayMetrics.widthPixels, (float) displayMetrics.widthPixels);
                }
                window.setAttributes(attributes);
            }
            obtainStyledAttributes.recycle();
        }
    }

    @SuppressLint({"SetJavaScriptEnabled"})
    public static void a(WebView webView, boolean z) {
        try {
            webView.getSettings().setJavaScriptEnabled(z);
        } catch (Throwable e) {
            Log.error("Error during enabled Javascript due to TTS feature", e);
        }
    }

    public static boolean a(Context context, View view, String str, AnimationListener animationListener) {
        try {
            int identifier = context.getResources().getIdentifier(str, "anim", context.getPackageName());
            if (identifier != 0) {
                Animation loadAnimation = AnimationUtils.loadAnimation(context, identifier);
                loadAnimation.setAnimationListener(animationListener);
                view.startAnimation(loadAnimation);
                return true;
            }
        } catch (Throwable e) {
            Log.warn("Compatibility|Could not animate view using '" + str + '\'', e);
        }
        Log.warn("Animation|Could not use View Animation : " + str);
        return false;
    }

    public static boolean a(HandlerThread handlerThread) {
        Looper looper = handlerThread.getLooper();
        if (looper == null) {
            return false;
        }
        looper.quit();
        return true;
    }

    public static byte[] a(String str, int i) {
        return str.getBytes();
    }

    public static String b(Context context) {
        return new WebView(context).getSettings().getUserAgentString();
    }
}
