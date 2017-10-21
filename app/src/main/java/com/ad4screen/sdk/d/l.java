package com.ad4screen.sdk.d;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;

import com.ad4screen.sdk.A4SInterstitial;
import com.ad4screen.sdk.A4SPopup;
import com.ad4screen.sdk.A4SPopup.b;
import com.ad4screen.sdk.A4SPopup.c;
import com.ad4screen.sdk.Log;
import com.ad4screen.sdk.R;
import com.ad4screen.sdk.Tag;
import com.ad4screen.sdk.c.a.a;
import com.ad4screen.sdk.c.a.g;
import com.ad4screen.sdk.common.b.m;
import com.ad4screen.sdk.service.modules.inapp.DisplayView;
import com.ad4screen.sdk.service.modules.inapp.e;
import com.ad4screen.sdk.service.modules.inapp.e.d;
import com.ad4screen.sdk.service.modules.inapp.e.f;

import java.util.ArrayList;

public final class l {
    private static l j;
    private ArrayList<DisplayView> a = new ArrayList();
    private A4SPopup b;
    private m c;
    private FrameLayout d;
    private ArrayList<View> e = new ArrayList();
    private ArrayList<View> f = new ArrayList();
    private boolean g = false;
    private boolean h = false;
    private boolean i = false;
    private b k = new b(this) {
        final /* synthetic */ l a;

        {
            this.a = r1;
        }

        public void a(A4SPopup a4SPopup) {
            this.a.b = a4SPopup;
            this.a.i = false;
        }
    };

    private l(Context context) {
        f.a().a(c.class, this.k);
        this.c = new m(context);
    }

    public static l a(Context context) {
        if (j == null) {
            j = new l(context);
        }
        return j;
    }

    public static String a(Activity activity) {
        Class cls = activity.getClass();
        Tag tag = (Tag) cls.getAnnotation(Tag.class);
        return tag != null ? tag.name() : cls.getName();
    }

    private void a(final Activity activity, final a aVar) {
        if (activity == null) {
            Log.debug("UI|Cannot display inapp without activity");
        } else if (aVar.o) {
            f.a().a(new f(aVar.h, -1, aVar.c.d, aVar.e, aVar.o));
            f.a().a(new d(aVar.h, -1, aVar.c.d, false, aVar.o));
            Log.warn("UI|InApp with id #" + aVar.h + " has not be displayed since you're in control group");
        } else {
            int i;
            View view;
            DisplayView displayView;
            View view2;
            ViewGroup viewGroup = (ViewGroup) ((ViewGroup) activity.findViewById(16908290)).getChildAt(0);
            ArrayList arrayList = new ArrayList();
            if (!(aVar.b || aVar.c == null || aVar.c.d == null)) {
                m.a(viewGroup, arrayList, activity.getResources().getIdentifier(aVar.c.d, "layout", activity.getPackageName()));
                if (arrayList.size() > 0) {
                    for (i = 0; i < arrayList.size(); i++) {
                        if (!this.f.contains(arrayList.get(i))) {
                            view = (View) arrayList.get(i);
                            break;
                        }
                    }
                }
            }
            view = null;
            if (view != null) {
                View displayView2 = new DisplayView(activity);
                ((ViewGroup) view).addView(displayView2, new LayoutParams(-1, -2, 80));
                this.f.add(view);
                displayView = (DisplayView) displayView2;
                displayView.a(view);
                displayView.setLayout(activity.getResources().getIdentifier(aVar.c.d, "layout", activity.getPackageName()));
                view2 = displayView2;
                i = 0;
            } else {
                arrayList.clear();
                m.a(viewGroup, arrayList, R.id.com_ad4screen_banner);
                if (arrayList.size() > 0) {
                    for (i = 0; i < arrayList.size(); i++) {
                        view2 = (View) arrayList.get(i);
                        if (!this.f.contains(view2)) {
                            view = view2;
                            break;
                        }
                    }
                }
                view = null;
                if ((view instanceof DisplayView) && !aVar.b && !this.f.contains(view)) {
                    displayView = (DisplayView) view;
                    displayView.a(view);
                    displayView.setLayout(activity.getResources().getIdentifier("com_ad4screen_sdk_banner", "layout", activity.getPackageName()));
                    this.f.add(view);
                    view2 = view;
                    i = 0;
                } else if (!this.h && aVar.g && (this.f.isEmpty() || aVar.b)) {
                    this.h = true;
                    this.d = new FrameLayout(activity);
                    view = new DisplayView(activity);
                    ViewGroup.LayoutParams c = c();
                    ((DisplayView) view).setLayout(activity.getResources().getIdentifier("com_ad4screen_sdk_overlay", "layout", activity.getPackageName()));
                    this.d.addView(view, c);
                    activity.addContentView(this.d, new ViewGroup.LayoutParams(-1, -1));
                    view2 = view;
                    i = 0;
                } else {
                    view2 = view;
                    boolean z = true;
                }
            }
            if (i != 0) {
                Log.debug("InApp|No spaces available to display this banner");
                return;
            }
            displayView = (DisplayView) view2;
            this.a.add(displayView);
            displayView.a(aVar);
            displayView.a(new DisplayView.b(this) {
                final /* synthetic */ l c;

                public void a(DisplayView displayView) {
                    f.a().a(new f(aVar.h, displayView.getLayout(), aVar.c.d, aVar.e, aVar.o));
                }

                public void b(DisplayView displayView) {
                    Log.debug("UI|User closed banner #" + aVar.h);
                    f.a().a(new d(aVar.h, displayView.getLayout(), aVar.c.d, true, aVar.o));
                    this.c.a(activity, displayView);
                }

                public void c(DisplayView displayView) {
                    Log.debug("UI|User clicked banner #" + aVar.h);
                    if (aVar.d == null) {
                        Log.debug("UI|InApp #" + aVar.h + " click tracking will not be sent because target is null");
                        return;
                    }
                    f.a().a(new e.c(aVar.h, displayView.getLayout(), aVar.c.d, null, null));
                    this.c.a(activity, displayView);
                }

                public void d(DisplayView displayView) {
                }

                public void e(DisplayView displayView) {
                    Log.debug("UI|Failed to load banner # " + aVar.h + " webview");
                    f.a().a(new d(aVar.h, displayView.getLayout(), aVar.c.d, false, aVar.o));
                    this.c.a(activity, displayView);
                }
            });
            if (aVar.d != null) {
                displayView.a(aVar.c, 1);
            } else {
                displayView.a(aVar.c, 0);
            }
        }
    }

    private void a(Activity activity, DisplayView displayView) {
        if (displayView.h() != null) {
            this.e.remove(displayView.h());
            this.f.remove(displayView.h());
        } else {
            ViewGroup viewGroup = (ViewGroup) this.d.getParent();
            if (viewGroup != null) {
                viewGroup.removeView(this.d);
            }
            this.h = false;
            this.g = false;
        }
        displayView.d();
        this.a.remove(displayView);
    }

    private void a(Context context, com.ad4screen.sdk.c.a.e eVar) {
        context.startActivity(A4SInterstitial.build(context, 1, eVar));
    }

    private void a(Context context, g gVar) {
        context.startActivity(A4SPopup.build(context, 1, gVar));
    }

    public static String b(Activity activity) {
        return activity.getClass().getCanonicalName();
    }

    private LayoutParams c() {
        return this.c.a();
    }

    public static String c(Activity activity) {
        return Integer.toHexString(System.identityHashCode(activity));
    }

    public final void a() {
        if (this.a.size() > 0) {
            for (int i = 0; i < this.a.size(); i++) {
                f.a().a(new d(((DisplayView) this.a.get(i)).g().h, ((DisplayView) this.a.get(i)).getLayout(), ((DisplayView) this.a.get(i)).g().c.d, false, ((DisplayView) this.a.get(i)).g().o));
                ((DisplayView) this.a.get(i)).d();
            }
        }
        this.e.clear();
        this.f.clear();
        this.a.clear();
        this.g = false;
        this.h = false;
    }

    public final void a(Activity activity, com.ad4screen.sdk.c.a.d dVar) {
        if (dVar == null) {
            Log.error("UI|Unable to display null format");
            return;
        }
        if (activity == null) {
            Log.error("UI|Unable to display format with null context");
        }
        if (dVar instanceof a) {
            Log.debug("UI|Client displaying banner #" + dVar.h);
            a(activity, (a) dVar);
        } else if (dVar instanceof com.ad4screen.sdk.c.a.e) {
            Log.debug("UI|Client displaying interstitial #" + dVar.h);
            a((Context) activity, (com.ad4screen.sdk.c.a.e) dVar);
        } else if (dVar instanceof g) {
            Log.debug("UI|Client displaying popup #" + dVar.h);
            a((Context) activity, (g) dVar);
        }
    }

    public final void a(Activity activity, com.ad4screen.sdk.c.a.d dVar, int i) {
        if (activity == null) {
            Log.debug("UI|Cannot cancel inapp template reservation without activity");
        } else if (activity.getResources().getIdentifier("com_ad4screen_sdk_overlay", "layout", activity.getPackageName()) == i) {
            if (!this.h) {
                this.g = false;
            }
        } else if (dVar instanceof g) {
            this.i = false;
        } else if (dVar instanceof a) {
            a aVar = (a) dVar;
            if (aVar.c.d != null) {
                ArrayList arrayList = new ArrayList();
                m.a((ViewGroup) ((ViewGroup) activity.findViewById(16908290)).getChildAt(0), arrayList, activity.getResources().getIdentifier(aVar.c.d, "layout", activity.getPackageName()));
                if (arrayList.size() > 0) {
                    int i2 = 0;
                    while (i2 < arrayList.size()) {
                        if (this.e.contains(arrayList.get(i2)) && !this.f.contains(arrayList.get(i2))) {
                            this.e.remove(arrayList.get(i2));
                        }
                        i2++;
                    }
                }
            }
        }
    }

    public final void a(Activity activity, String str) {
        boolean z;
        if (this.a.size() > 0) {
            z = false;
            for (int i = 0; i < this.a.size(); i++) {
                if (((DisplayView) this.a.get(i)).g().h.equals(str)) {
                    f.a().a(new d(((DisplayView) this.a.get(i)).g().h, ((DisplayView) this.a.get(i)).getLayout(), ((DisplayView) this.a.get(i)).g().c.d, false, ((DisplayView) this.a.get(i)).g().o));
                    a(activity, (DisplayView) this.a.get(i));
                    z = true;
                }
            }
        } else {
            z = false;
        }
        if (!z) {
            b();
        }
    }

    public final void a(LayoutParams layoutParams) {
        this.c.a(layoutParams);
    }

    public final void b() {
        if (this.b != null && !this.b.isFinishing()) {
            this.b.finish();
            this.b = null;
        }
    }

    public final void b(Activity activity, com.ad4screen.sdk.c.a.d dVar) {
        int i = 0;
        View view = null;
        if (activity == null) {
            Log.debug("UI|Cannot display inapp without activity");
        } else if (dVar instanceof a) {
            a aVar = (a) dVar;
            if (aVar.c.d != null) {
                View view2;
                ArrayList arrayList = new ArrayList();
                ViewGroup viewGroup = (ViewGroup) ((ViewGroup) activity.findViewById(16908290)).getChildAt(0);
                m.a(viewGroup, arrayList, activity.getResources().getIdentifier(aVar.c.d, "layout", activity.getPackageName()));
                if (arrayList.size() > 0) {
                    view2 = null;
                    int i2 = 0;
                    while (i2 < arrayList.size()) {
                        view2 = (View) arrayList.get(i2);
                        if (this.e.contains(view2)) {
                            i2++;
                        } else {
                            this.e.add(view2);
                            f.a().a(new e.g(dVar.h, view2.getId(), aVar.c.d, dVar.o));
                            return;
                        }
                    }
                }
                view2 = null;
                if (aVar.b) {
                    view = view2;
                } else {
                    arrayList.clear();
                    m.a(viewGroup, arrayList, R.id.com_ad4screen_banner);
                    if (arrayList.size() > 0) {
                        while (i < arrayList.size()) {
                            View view3 = (View) arrayList.get(i);
                            if (this.e.contains(view3)) {
                                i++;
                                view = view3;
                            } else {
                                this.e.add(view3);
                                f.a().a(new e.g(dVar.h, activity.getResources().getIdentifier("com_ad4screen_sdk_banner", "layout", activity.getPackageName()), aVar.c.d, dVar.o));
                                return;
                            }
                        }
                    }
                }
            }
            if (!this.g && !this.h && r3 == null && aVar.g) {
                this.g = true;
                f.a().a(new e.g(dVar.h, activity.getResources().getIdentifier("com_ad4screen_sdk_overlay", "layout", activity.getPackageName()), aVar.c.d, dVar.o));
            }
        } else if (!(dVar instanceof g)) {
            f.a().a(new e.g(dVar.h, activity.getResources().getIdentifier("com_ad4screen_sdk_overlay", "layout", activity.getPackageName()), "com_ad4screen_sdk_overlay", dVar.o));
        } else if (!this.i) {
            if (this.b == null || this.b.isFinishing()) {
                this.i = true;
                f.a().a(new e.g(dVar.h, -1, "com_ad4screen_sdk_theme_popup", dVar.o));
            }
        }
    }
}
