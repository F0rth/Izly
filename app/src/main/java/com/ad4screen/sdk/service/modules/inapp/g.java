package com.ad4screen.sdk.service.modules.inapp;

import android.content.Intent;
import android.location.Location;
import android.support.v4.widget.AutoScrollHelper;

import com.ad4screen.sdk.c.a.d;
import com.ad4screen.sdk.d.f;
import com.ad4screen.sdk.plugins.model.Geofence;
import com.ad4screen.sdk.service.modules.a.a.b;
import com.ad4screen.sdk.service.modules.a.a.c;
import com.ad4screen.sdk.service.modules.inapp.a.h;
import com.ad4screen.sdk.service.modules.inapp.a.j;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;

public final class g {
    public static double a(Location location, Geofence geofence) {
        com.ad4screen.sdk.service.modules.inapp.a.g gVar = new com.ad4screen.sdk.service.modules.inapp.a.g();
        gVar.a(geofence.getLatitude());
        gVar.b(geofence.getLongitude());
        gVar.c((double) geofence.getRadius());
        return a(location, gVar);
    }

    public static double a(Location location, com.ad4screen.sdk.service.modules.inapp.a.g gVar) {
        double latitude = location.getLatitude();
        double longitude = location.getLongitude();
        double a = gVar.a();
        double b = (0.017453292519943295d * gVar.b()) - (longitude * 0.017453292519943295d);
        latitude = Math.atan(Math.tan(latitude * 0.017453292519943295d) * 0.996647189328169d);
        longitude = Math.atan(0.996647189328169d * Math.tan(a * 0.017453292519943295d));
        double cos = Math.cos(latitude);
        double cos2 = Math.cos(longitude);
        double sin = Math.sin(latitude);
        double sin2 = Math.sin(longitude);
        double d = cos * cos2;
        double d2 = sin * sin2;
        int i = 0;
        double d3 = b;
        latitude = 0.0d;
        longitude = 0.0d;
        a = 0.0d;
        while (i < 20) {
            latitude = Math.cos(d3);
            longitude = Math.sin(d3);
            a = cos2 * longitude;
            double d4 = (cos * sin2) - ((sin * cos2) * latitude);
            double sqrt = Math.sqrt((a * a) + (d4 * d4));
            double d5 = d2 + (latitude * d);
            a = Math.atan2(sqrt, d5);
            double d6 = sqrt == 0.0d ? 0.0d : (d * longitude) / sqrt;
            longitude = 1.0d - (d6 * d6);
            latitude = longitude == 0.0d ? 0.0d : d5 - ((2.0d * d2) / longitude);
            double d7 = longitude * 0.006739496756586903d;
            d4 = 1.0d + ((d7 / 16384.0d) * (4096.0d + ((-768.0d + ((320.0d - (175.0d * d7)) * d7)) * d7)));
            d7 = (d7 / 1024.0d) * (((-128.0d + ((74.0d - (47.0d * d7)) * d7)) * d7) + 256.0d);
            double d8 = (2.0955066698943685E-4d * longitude) * (((4.0d - (longitude * 3.0d)) * 0.0033528106718309896d) + 4.0d);
            longitude = latitude * latitude;
            longitude = (((((-1.0d + (2.0d * longitude)) * d5) - (((longitude * 4.0d) - 3.0d) * (((d7 / 6.0d) * latitude) * (-3.0d + ((4.0d * sqrt) * sqrt))))) * (d7 / 4.0d)) + latitude) * (d7 * sqrt);
            d6 = ((((latitude + ((d5 * d8) * (-1.0d + ((2.0d * latitude) * latitude)))) * (sqrt * d8)) + a) * (d6 * ((1.0d - d8) * 0.0033528106718309896d))) + b;
            if (Math.abs((d6 - d3) / d6) < 1.0E-12d) {
                latitude = longitude;
                longitude = a;
                a = d4;
                break;
            }
            i++;
            d3 = d6;
            latitude = longitude;
            longitude = a;
            a = d4;
        }
        return (longitude - latitude) * (6356752.3142d * a);
    }

    public static void a(Intent intent, HashMap<String, String> hashMap) {
        for (String str : hashMap.keySet()) {
            intent.putExtra(str, (String) hashMap.get(str));
        }
    }

    public static void a(d dVar) {
        f.a().a(new e.f(dVar.h, -1, "com_ad4screen_sdk_theme_popup", null, dVar.o));
        f.a().a(new e.d(dVar.h, -1, "com_ad4screen_sdk_theme_popup", false, dVar.o));
    }

    public static void a(final com.ad4screen.sdk.service.modules.inapp.a.f fVar) {
        Arrays.sort(fVar.a, new Comparator<j>() {
            public final int a(j jVar, j jVar2) {
                if (jVar == null) {
                    return jVar2 == null ? 0 : 1;
                } else {
                    if (jVar2 == null) {
                        return -1;
                    }
                    h hVar = (h) fVar.b.get(jVar.a());
                    h hVar2 = (h) fVar.b.get(jVar2.a());
                    if (g.c(hVar)) {
                        if (!g.c(hVar2)) {
                            return -1;
                        }
                    } else if (g.c(hVar2)) {
                        return 1;
                    }
                    if (g.d(hVar)) {
                        if (!g.d(hVar2)) {
                            return -1;
                        }
                    } else if (g.d(hVar2)) {
                        return 1;
                    }
                    if (jVar.k() != null || jVar.l() != null) {
                        return -1;
                    }
                    if (jVar2.k() != null || jVar2.l() != null) {
                        return 1;
                    }
                    if (jVar.i() != jVar2.i()) {
                        return jVar.i() - jVar2.i();
                    }
                    if (jVar.d() == null && jVar2.d() == null) {
                        return (hVar2 == null || hVar == null) ? hVar != null ? -1 : hVar2 != null ? 1 : 0 : hVar.b() - hVar2.b();
                    } else {
                        if ((jVar.d() != null && jVar2.d() == null) || (jVar.d() == null && jVar2.d() != null)) {
                            return hVar.c() - hVar2.c();
                        }
                        if (jVar.d() == null || jVar2.d() == null) {
                            return 0;
                        }
                        return (hVar != null ? ((float) hVar.b()) / jVar.d().floatValue() : AutoScrollHelper.NO_MAX) < (hVar2 != null ? ((float) hVar2.b()) / jVar2.d().floatValue() : AutoScrollHelper.NO_MAX) ? -1 : 1;
                    }
                }
            }

            public final /* synthetic */ int compare(Object obj, Object obj2) {
                return a((j) obj, (j) obj2);
            }
        });
    }

    public static void a(boolean z, d dVar) {
        if (!z) {
            f.a().a(new e.f(dVar.h, -1, "com_ad4screen_sdk_theme_interstitial", null, dVar.o));
            f.a().a(new e.d(dVar.h, -1, "com_ad4screen_sdk_theme_interstitial", false, dVar.o));
        }
    }

    private static boolean c(h hVar) {
        return (hVar == null || hVar.a() == null || !(hVar.a() instanceof b)) ? false : true;
    }

    private static boolean d(h hVar) {
        return (hVar == null || hVar.a() == null || !(hVar.a() instanceof c)) ? false : true;
    }
}
