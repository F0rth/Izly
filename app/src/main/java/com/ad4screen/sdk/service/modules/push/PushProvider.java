package com.ad4screen.sdk.service.modules.push;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.ad4screen.sdk.A4SPopup;
import com.ad4screen.sdk.Constants;
import com.ad4screen.sdk.Log;
import com.ad4screen.sdk.c.a.d;
import com.ad4screen.sdk.c.a.f;
import com.ad4screen.sdk.common.annotations.API;
import com.ad4screen.sdk.common.b.m.j;
import com.ad4screen.sdk.d.i;
import com.ad4screen.sdk.external.shortcutbadger.ShortcutBadger;
import com.ad4screen.sdk.service.modules.d.h;

import java.util.Set;

@API
public abstract class PushProvider implements a {
    protected d a;
    protected com.ad4screen.sdk.A4SService.a b;
    protected b c;
    protected long d = 3000;

    final class a implements com.ad4screen.sdk.service.modules.b.a.c {
        final /* synthetic */ PushProvider a;

        private a(PushProvider pushProvider) {
            this.a = pushProvider;
        }

        public final void a() {
        }

        public final void a(com.ad4screen.sdk.service.modules.b.a.a aVar, boolean z) {
            Log.debug("Push|Received sharedId, starting session");
            this.a.a();
        }
    }

    final class b implements com.ad4screen.sdk.service.modules.push.d.b {
        final /* synthetic */ PushProvider a;
        private final f b;
        private final com.ad4screen.sdk.service.modules.push.a.a c;
        private final Bundle d;

        private b(PushProvider pushProvider, f fVar, com.ad4screen.sdk.service.modules.push.a.a aVar, Bundle bundle) {
            this.a = pushProvider;
            this.b = fVar;
            this.c = aVar;
            this.d = bundle;
        }

        private void b() {
            int i = 2;
            if (!i.a(this.a.b.a()).g()) {
                i = 6;
            }
            Intent build = A4SPopup.build(this.a.b.a(), i, g.a(this.a.b.a(), this.c, this.b), this.c.x);
            build.addFlags(1484783616);
            this.a.b.a().startActivity(build);
        }

        public final void a() {
            ShortcutBadger.applyCount(this.a.b.a(), this.c.t);
            if (this.c.f && com.ad4screen.sdk.common.i.a(this.a.b.a())) {
                b();
            }
            this.a.e(this.d);
        }
    }

    final class c implements com.ad4screen.sdk.service.modules.push.d.a {
        final /* synthetic */ PushProvider a;

        private c(PushProvider pushProvider) {
            this.a = pushProvider;
        }

        public final void a() {
            Log.debug("Push|Token was uploaded");
        }

        public final void b() {
        }
    }

    public PushProvider(com.ad4screen.sdk.A4SService.a aVar) {
        this.b = aVar;
        this.c = new b(aVar.a());
        com.ad4screen.sdk.d.f.a().a(d.d.class, new c());
        com.ad4screen.sdk.d.f.a().a(com.ad4screen.sdk.service.modules.b.a.b.class, new a());
    }

    private void a(com.ad4screen.sdk.service.modules.push.a.a aVar) {
        this.b.d().e(aVar.b);
    }

    private void a(String str, Intent intent) {
        Bundle extras = intent.getExtras();
        if (extras != null) {
            Set<String> keySet = extras.keySet();
            if (keySet != null) {
                Log.debug("Send Custom Params for action " + str);
                for (String str2 : keySet) {
                    Log.debug(str2 + " -> " + extras.get(str2));
                }
            }
        }
    }

    private boolean a(Bundle bundle) {
        return bundle.getBoolean("isDestructive", true);
    }

    private void b(Bundle bundle) {
        String string = bundle.getString("a4ssysid");
        if (string != null) {
            try {
                Integer valueOf = Integer.valueOf(string);
                if (valueOf != null) {
                    ((NotificationManager) this.b.a().getSystemService("notification")).cancel(valueOf.intValue());
                }
            } catch (NumberFormatException e) {
                Log.internal("Could not parse a4ssysid parameter : " + string);
            }
        }
    }

    private void b(com.ad4screen.sdk.service.modules.push.a.a aVar) {
        if (aVar == null) {
            Log.warn("Error while retrieving notification parameters when push was opened, push notification could not be tracked");
        } else if (aVar.a != com.ad4screen.sdk.c.a.d.b.URLConnection) {
            Log.debug("Push|No Tracking on this notification");
        } else if (aVar.A) {
            h.a(this.b, aVar.b, com.ad4screen.sdk.service.modules.d.d.a.CLICK, null);
        } else {
            h.a(this.b, aVar.b);
        }
    }

    private void c(Bundle bundle) {
        Intent intent = new Intent(Constants.ACTION_CLOSED);
        intent.addCategory(Constants.CATEGORY_PUSH_NOTIFICATIONS);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        a(Constants.ACTION_CLOSED, intent);
        com.ad4screen.sdk.common.i.a(this.b.a(), intent);
    }

    private void d(Bundle bundle) {
        Intent intent = new Intent(Constants.ACTION_CLICKED);
        intent.addCategory(Constants.CATEGORY_PUSH_NOTIFICATIONS);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        a(Constants.ACTION_CLICKED, intent);
        com.ad4screen.sdk.common.i.a(this.b.a(), intent);
    }

    private void e(Bundle bundle) {
        Intent intent = new Intent(Constants.ACTION_DISPLAYED);
        intent.addCategory(Constants.CATEGORY_PUSH_NOTIFICATIONS);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        a(Constants.ACTION_DISPLAYED, intent);
        com.ad4screen.sdk.common.i.a(this.b.a(), intent);
    }

    public abstract void a(Context context);

    public abstract void a(String str);

    public abstract void b(Context context);

    public abstract void b(String str);

    protected void c() {
        this.c.f();
    }

    public abstract void c(String str);

    public void closedPush(Bundle bundle) {
        Log.debug("Push|Notification was closed");
        Log.debug("Push|Sending notification close broadcast");
        c(bundle);
    }

    protected void d(String str) {
        this.c.b(str);
    }

    public String getPushToken() {
        return this.c.e();
    }

    public void handleMessage(Bundle bundle) {
        if (!isEnabled()) {
            Log.debug("Push|Received a Push message, but notifications were explicitely disabled, skipping...");
        } else if (bundle == null) {
            Log.warn("Push|Received a Push message, but no content was provided.");
        } else {
            com.ad4screen.sdk.service.modules.push.a.a a = com.ad4screen.sdk.service.modules.push.a.a.a(bundle);
            if (a == null) {
                Log.debug("Push|Received Push message but no Accengage parameters were found, skipping...");
                return;
            }
            if (this.a != null) {
                if (this.a.l != null) {
                    Log.debug("Push|Triggered by beacon: " + this.a.l);
                    a.a(this.a.l);
                    a.a(this.b.a(), this.a, bundle);
                } else if (this.a.m != null) {
                    Log.debug("Push|Triggered by geofence: " + this.a.m);
                    a.b(this.a.m);
                    a.b(this.b.a(), this.a, bundle);
                }
            }
            if (a.y || !(i.a(this.b.a()).g() || i.a(this.b.a()).j())) {
                Log.debug("Push|Push message received from Ad4Push, displaying notification...");
                try {
                    Intent a2 = g.a(this.b.a());
                    a2.putExtra(Constants.EXTRA_GCM_PAYLOAD, bundle);
                    j.a(PendingIntent.getActivity(this.b.a(), a.x, a2, 134217728));
                    PendingIntent activity = PendingIntent.getActivity(this.b.a(), a.x, a2, 134217728);
                    f fVar = new f();
                    fVar.a = a2;
                    g.a(this.b.a(), activity, a, new b(fVar, a, bundle));
                    return;
                } catch (Throwable e) {
                    Log.error("Push|Error while displaying notification...", e);
                    return;
                }
            }
            Log.debug("Push|Received Push message but application was in foreground, skipping...");
        }
    }

    public boolean isEnabled() {
        return this.c.a();
    }

    public void openedPush(Bundle bundle) {
        Log.debug("Push|Notification was opened");
        Log.debug("Push|Sending notification click broadcast");
        d(bundle);
        Log.debug("Push|Tracking notification click");
        com.ad4screen.sdk.service.modules.push.a.a a = com.ad4screen.sdk.service.modules.push.a.a.a(bundle);
        b(a);
        a(a);
        if (a(bundle)) {
            b(bundle);
        }
    }

    public void setEnabled(boolean z) {
        String pushToken = getPushToken();
        this.c.a(z);
        if (z) {
            Log.debug("Push|Notification are now enabled");
            if (pushToken == null) {
                this.b.a(new Runnable(this) {
                    final /* synthetic */ PushProvider a;

                    {
                        this.a = r1;
                    }

                    public void run() {
                        this.a.a(this.a.b.a());
                    }
                });
                return;
            }
            return;
        }
        Log.debug("Push|Notification are now disabled");
        if (pushToken != null) {
            this.b.a(new Runnable(this) {
                final /* synthetic */ PushProvider a;

                {
                    this.a = r1;
                }

                public void run() {
                    this.a.b(this.a.b.a());
                }
            });
        }
    }

    public void setFormat(d dVar) {
        Log.debug("Push|setFormat");
        this.a = dVar;
    }

    public void updateRegistration(Bundle bundle) {
        if (bundle != null) {
            String string = bundle.getString("registration_id");
            if (string != null) {
                a(string);
            }
            string = bundle.getString("unregistered");
            if (string != null) {
                b(string);
            }
            string = bundle.getString("error");
            if (string != null) {
                c(string);
            }
        }
    }
}
