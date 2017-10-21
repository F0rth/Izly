package com.google.android.gms.dynamic;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.internal.zzg;
import com.google.android.gms.common.zze;
import java.util.Iterator;
import java.util.LinkedList;

public abstract class zza<T extends LifecycleDelegate> {
    private final zzf<T> zzavA = new zzf<T>(this) {
        final /* synthetic */ zza zzavB;

        {
            this.zzavB = r1;
        }

        public void zza(T t) {
            this.zzavB.zzavx = t;
            Iterator it = this.zzavB.zzavz.iterator();
            while (it.hasNext()) {
                ((zza) it.next()).zzb(this.zzavB.zzavx);
            }
            this.zzavB.zzavz.clear();
            this.zzavB.zzavy = null;
        }
    };
    private T zzavx;
    private Bundle zzavy;
    private LinkedList<zza> zzavz;

    interface zza {
        int getState();

        void zzb(LifecycleDelegate lifecycleDelegate);
    }

    private void zza(Bundle bundle, zza com_google_android_gms_dynamic_zza_zza) {
        if (this.zzavx != null) {
            com_google_android_gms_dynamic_zza_zza.zzb(this.zzavx);
            return;
        }
        if (this.zzavz == null) {
            this.zzavz = new LinkedList();
        }
        this.zzavz.add(com_google_android_gms_dynamic_zza_zza);
        if (bundle != null) {
            if (this.zzavy == null) {
                this.zzavy = (Bundle) bundle.clone();
            } else {
                this.zzavy.putAll(bundle);
            }
        }
        zza(this.zzavA);
    }

    public static void zzb(FrameLayout frameLayout) {
        final Context context = frameLayout.getContext();
        final int isGooglePlayServicesAvailable = GooglePlayServicesUtil.isGooglePlayServicesAvailable(context);
        CharSequence zzc = zzg.zzc(context, isGooglePlayServicesAvailable, zze.zzao(context));
        CharSequence zzh = zzg.zzh(context, isGooglePlayServicesAvailable);
        View linearLayout = new LinearLayout(frameLayout.getContext());
        linearLayout.setOrientation(1);
        linearLayout.setLayoutParams(new LayoutParams(-2, -2));
        frameLayout.addView(linearLayout);
        View textView = new TextView(frameLayout.getContext());
        textView.setLayoutParams(new LayoutParams(-2, -2));
        textView.setText(zzc);
        linearLayout.addView(textView);
        if (zzh != null) {
            View button = new Button(context);
            button.setLayoutParams(new LayoutParams(-2, -2));
            button.setText(zzh);
            linearLayout.addView(button);
            button.setOnClickListener(new OnClickListener() {
                public final void onClick(View view) {
                    context.startActivity(GooglePlayServicesUtil.zzbv(isGooglePlayServicesAvailable));
                }
            });
        }
    }

    private void zzeJ(int i) {
        while (!this.zzavz.isEmpty() && ((zza) this.zzavz.getLast()).getState() >= i) {
            this.zzavz.removeLast();
        }
    }

    public void onCreate(final Bundle bundle) {
        zza(bundle, new zza(this) {
            final /* synthetic */ zza zzavB;

            public int getState() {
                return 1;
            }

            public void zzb(LifecycleDelegate lifecycleDelegate) {
                this.zzavB.zzavx.onCreate(bundle);
            }
        });
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        final FrameLayout frameLayout = new FrameLayout(layoutInflater.getContext());
        final LayoutInflater layoutInflater2 = layoutInflater;
        final ViewGroup viewGroup2 = viewGroup;
        final Bundle bundle2 = bundle;
        zza(bundle, new zza(this) {
            final /* synthetic */ zza zzavB;

            public int getState() {
                return 2;
            }

            public void zzb(LifecycleDelegate lifecycleDelegate) {
                frameLayout.removeAllViews();
                frameLayout.addView(this.zzavB.zzavx.onCreateView(layoutInflater2, viewGroup2, bundle2));
            }
        });
        if (this.zzavx == null) {
            zza(frameLayout);
        }
        return frameLayout;
    }

    public void onDestroy() {
        if (this.zzavx != null) {
            this.zzavx.onDestroy();
        } else {
            zzeJ(1);
        }
    }

    public void onDestroyView() {
        if (this.zzavx != null) {
            this.zzavx.onDestroyView();
        } else {
            zzeJ(2);
        }
    }

    public void onInflate(final Activity activity, final Bundle bundle, final Bundle bundle2) {
        zza(bundle2, new zza(this) {
            final /* synthetic */ zza zzavB;

            public int getState() {
                return 0;
            }

            public void zzb(LifecycleDelegate lifecycleDelegate) {
                this.zzavB.zzavx.onInflate(activity, bundle, bundle2);
            }
        });
    }

    public void onLowMemory() {
        if (this.zzavx != null) {
            this.zzavx.onLowMemory();
        }
    }

    public void onPause() {
        if (this.zzavx != null) {
            this.zzavx.onPause();
        } else {
            zzeJ(5);
        }
    }

    public void onResume() {
        zza(null, new zza(this) {
            final /* synthetic */ zza zzavB;

            {
                this.zzavB = r1;
            }

            public int getState() {
                return 5;
            }

            public void zzb(LifecycleDelegate lifecycleDelegate) {
                this.zzavB.zzavx.onResume();
            }
        });
    }

    public void onSaveInstanceState(Bundle bundle) {
        if (this.zzavx != null) {
            this.zzavx.onSaveInstanceState(bundle);
        } else if (this.zzavy != null) {
            bundle.putAll(this.zzavy);
        }
    }

    public void onStart() {
        zza(null, new zza(this) {
            final /* synthetic */ zza zzavB;

            {
                this.zzavB = r1;
            }

            public int getState() {
                return 4;
            }

            public void zzb(LifecycleDelegate lifecycleDelegate) {
                this.zzavB.zzavx.onStart();
            }
        });
    }

    public void onStop() {
        if (this.zzavx != null) {
            this.zzavx.onStop();
        } else {
            zzeJ(4);
        }
    }

    protected void zza(FrameLayout frameLayout) {
        zzb(frameLayout);
    }

    public abstract void zza(zzf<T> com_google_android_gms_dynamic_zzf_T);

    public T zztU() {
        return this.zzavx;
    }
}
