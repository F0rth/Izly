package defpackage;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import java.util.ArrayList;
import java.util.Iterator;

public class gg implements LocationListener {
    private static final String g = gg.class.getSimpleName();
    public boolean a = false;
    public boolean b = false;
    public a c;
    public ArrayList<b> d;
    public Location e;
    public LocationManager f;
    private boolean h;

    public gg(Context context) {
        this.f = (LocationManager) context.getSystemService("location");
        this.d = new ArrayList();
    }

    public static void a(b bVar, Location location) {
        bVar.a(location);
    }

    public static void a(b bVar, boolean z) {
        bVar.a(z);
    }

    private void a(ArrayList<b> arrayList, Location location) {
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            ((b) it.next()).a(location);
        }
    }

    private void a(ArrayList<b> arrayList, boolean z) {
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            ((b) it.next()).a(z);
        }
    }

    private boolean b() {
        return this.f.isProviderEnabled("network") || this.f.isProviderEnabled("gps");
    }

    public final void a() {
        this.f.removeUpdates(this);
        if (this.c != null && this.c.hasMessages(1)) {
            this.c.removeMessages(1);
        }
    }

    public void onLocationChanged(Location location) {
        if (this.e == null || ((this.e.getProvider().equals("network") || this.e.getProvider().equals("gps")) && this.d != null)) {
            this.e = location;
            if (this.c != null && this.c.hasMessages(1)) {
                this.c.removeMessages(1);
                this.c = null;
            }
            a(this.d, this.e);
        }
    }

    public void onProviderDisabled(String str) {
        this.h = b();
        if (!this.h && this.d != null) {
            this.e = null;
            a(this.d, this.h);
        }
    }

    public void onProviderEnabled(String str) {
        this.h = b();
        if (this.h && this.d != null) {
            a(this.d, this.h);
        }
    }

    public void onStatusChanged(String str, int i, Bundle bundle) {
    }
}
