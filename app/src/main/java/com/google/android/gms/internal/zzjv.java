package com.google.android.gms.internal;

import android.annotation.TargetApi;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.DialogInterface.OnClickListener;
import android.os.Message;
import android.view.View;
import android.webkit.ConsoleMessage;
import android.webkit.ConsoleMessage.MessageLevel;
import android.webkit.GeolocationPermissions.Callback;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebChromeClient.CustomViewCallback;
import android.webkit.WebStorage.QuotaUpdater;
import android.webkit.WebView;
import android.webkit.WebView.WebViewTransport;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.google.android.gms.ads.internal.overlay.zzd;
import com.google.android.gms.ads.internal.util.client.zzb;
import com.google.android.gms.ads.internal.zzr;

@TargetApi(11)
@zzhb
public class zzjv extends WebChromeClient {
    private final zzjp zzpD;

    static /* synthetic */ class AnonymousClass7 {
        static final /* synthetic */ int[] zzOk = new int[MessageLevel.values().length];

        static {
            try {
                zzOk[MessageLevel.ERROR.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                zzOk[MessageLevel.WARNING.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                zzOk[MessageLevel.LOG.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                zzOk[MessageLevel.TIP.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
            try {
                zzOk[MessageLevel.DEBUG.ordinal()] = 5;
            } catch (NoSuchFieldError e5) {
            }
        }
    }

    public zzjv(zzjp com_google_android_gms_internal_zzjp) {
        this.zzpD = com_google_android_gms_internal_zzjp;
    }

    private final Context zza(WebView webView) {
        if (!(webView instanceof zzjp)) {
            return webView.getContext();
        }
        zzjp com_google_android_gms_internal_zzjp = (zzjp) webView;
        Context zzhP = com_google_android_gms_internal_zzjp.zzhP();
        return zzhP == null ? com_google_android_gms_internal_zzjp.getContext() : zzhP;
    }

    private static void zza(Builder builder, String str, final JsResult jsResult) {
        builder.setMessage(str).setPositiveButton(17039370, new OnClickListener() {
            public final void onClick(DialogInterface dialogInterface, int i) {
                jsResult.confirm();
            }
        }).setNegativeButton(17039360, new OnClickListener() {
            public final void onClick(DialogInterface dialogInterface, int i) {
                jsResult.cancel();
            }
        }).setOnCancelListener(new OnCancelListener() {
            public final void onCancel(DialogInterface dialogInterface) {
                jsResult.cancel();
            }
        }).create().show();
    }

    private static void zza(Context context, Builder builder, String str, String str2, final JsPromptResult jsPromptResult) {
        View linearLayout = new LinearLayout(context);
        linearLayout.setOrientation(1);
        View textView = new TextView(context);
        textView.setText(str);
        final View editText = new EditText(context);
        editText.setText(str2);
        linearLayout.addView(textView);
        linearLayout.addView(editText);
        builder.setView(linearLayout).setPositiveButton(17039370, new OnClickListener() {
            public final void onClick(DialogInterface dialogInterface, int i) {
                jsPromptResult.confirm(editText.getText().toString());
            }
        }).setNegativeButton(17039360, new OnClickListener() {
            public final void onClick(DialogInterface dialogInterface, int i) {
                jsPromptResult.cancel();
            }
        }).setOnCancelListener(new OnCancelListener() {
            public final void onCancel(DialogInterface dialogInterface) {
                jsPromptResult.cancel();
            }
        }).create().show();
    }

    private final boolean zziv() {
        return zzr.zzbC().zza(this.zzpD.getContext().getPackageManager(), this.zzpD.getContext().getPackageName(), "android.permission.ACCESS_FINE_LOCATION") || zzr.zzbC().zza(this.zzpD.getContext().getPackageManager(), this.zzpD.getContext().getPackageName(), "android.permission.ACCESS_COARSE_LOCATION");
    }

    public final void onCloseWindow(WebView webView) {
        if (webView instanceof zzjp) {
            zzd zzhS = ((zzjp) webView).zzhS();
            if (zzhS == null) {
                zzb.zzaK("Tried to close an AdWebView not associated with an overlay.");
                return;
            } else {
                zzhS.close();
                return;
            }
        }
        zzb.zzaK("Tried to close a WebView that wasn't an AdWebView.");
    }

    public final boolean onConsoleMessage(ConsoleMessage consoleMessage) {
        String str = "JS: " + consoleMessage.message() + " (" + consoleMessage.sourceId() + ":" + consoleMessage.lineNumber() + ")";
        if (str.contains("Application Cache")) {
            return super.onConsoleMessage(consoleMessage);
        }
        switch (AnonymousClass7.zzOk[consoleMessage.messageLevel().ordinal()]) {
            case 1:
                zzb.e(str);
                break;
            case 2:
                zzb.zzaK(str);
                break;
            case 3:
            case 4:
                zzb.zzaJ(str);
                break;
            case 5:
                zzb.zzaI(str);
                break;
            default:
                zzb.zzaJ(str);
                break;
        }
        return super.onConsoleMessage(consoleMessage);
    }

    public final boolean onCreateWindow(WebView webView, boolean z, boolean z2, Message message) {
        WebViewTransport webViewTransport = (WebViewTransport) message.obj;
        WebView webView2 = new WebView(webView.getContext());
        webView2.setWebViewClient(this.zzpD.zzhU());
        webViewTransport.setWebView(webView2);
        message.sendToTarget();
        return true;
    }

    public final void onExceededDatabaseQuota(String str, String str2, long j, long j2, long j3, QuotaUpdater quotaUpdater) {
        long j4 = 5242880 - j3;
        if (j4 <= 0) {
            quotaUpdater.updateQuota(j);
            return;
        }
        if (j != 0) {
            j2 = j2 == 0 ? Math.min(Math.min(131072, j4) + j, 1048576) : j2 <= Math.min(1048576 - j, j4) ? j2 + j : j;
        } else if (j2 > j4 || j2 > 1048576) {
            j2 = 0;
        }
        quotaUpdater.updateQuota(j2);
    }

    public final void onGeolocationPermissionsShowPrompt(String str, Callback callback) {
        if (callback != null) {
            callback.invoke(str, zziv(), true);
        }
    }

    public final void onHideCustomView() {
        zzd zzhS = this.zzpD.zzhS();
        if (zzhS == null) {
            zzb.zzaK("Could not get ad overlay when hiding custom view.");
        } else {
            zzhS.zzfl();
        }
    }

    public final boolean onJsAlert(WebView webView, String str, String str2, JsResult jsResult) {
        return zza(zza(webView), str, str2, null, jsResult, null, false);
    }

    public final boolean onJsBeforeUnload(WebView webView, String str, String str2, JsResult jsResult) {
        return zza(zza(webView), str, str2, null, jsResult, null, false);
    }

    public final boolean onJsConfirm(WebView webView, String str, String str2, JsResult jsResult) {
        return zza(zza(webView), str, str2, null, jsResult, null, false);
    }

    public final boolean onJsPrompt(WebView webView, String str, String str2, String str3, JsPromptResult jsPromptResult) {
        return zza(zza(webView), str, str2, str3, null, jsPromptResult, true);
    }

    public final void onReachedMaxAppCacheSize(long j, long j2, QuotaUpdater quotaUpdater) {
        long j3 = 131072 + j;
        if (5242880 - j2 < j3) {
            quotaUpdater.updateQuota(0);
        } else {
            quotaUpdater.updateQuota(j3);
        }
    }

    public final void onShowCustomView(View view, CustomViewCallback customViewCallback) {
        zza(view, -1, customViewCallback);
    }

    protected final void zza(View view, int i, CustomViewCallback customViewCallback) {
        zzd zzhS = this.zzpD.zzhS();
        if (zzhS == null) {
            zzb.zzaK("Could not get ad overlay when showing custom view.");
            customViewCallback.onCustomViewHidden();
            return;
        }
        zzhS.zza(view, customViewCallback);
        zzhS.setRequestedOrientation(i);
    }

    protected boolean zza(Context context, String str, String str2, String str3, JsResult jsResult, JsPromptResult jsPromptResult, boolean z) {
        try {
            Builder builder = new Builder(context);
            builder.setTitle(str);
            if (z) {
                zza(context, builder, str2, str3, jsPromptResult);
            } else {
                zza(builder, str2, jsResult);
            }
        } catch (Throwable e) {
            zzb.zzd("Fail to display Dialog.", e);
        }
        return true;
    }
}
