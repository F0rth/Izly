package com.ad4screen.sdk.service.modules.inapp.c;

import android.content.Context;
import android.location.Location;

import com.ad4screen.sdk.Log;
import com.ad4screen.sdk.d.g;
import com.ad4screen.sdk.plugins.model.Geofence;
import com.ad4screen.sdk.provider.A4SGeofenceResolver;
import com.ad4screen.sdk.service.modules.inapp.a.c;
import com.ad4screen.sdk.service.modules.inapp.a.d;
import com.ad4screen.sdk.service.modules.inapp.k;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

public abstract class i implements m {
    protected Geofence a;
    protected Location b = this.c.d();
    protected g c;
    protected Context d;

    public i(Context context, g gVar) {
        this.d = context;
        this.c = gVar;
    }

    private int a(c cVar, StringBuffer stringBuffer, List<String> list) {
        Set<String> a = cVar.a();
        a("ids: " + a.toString());
        if (!(list.isEmpty() || a.isEmpty())) {
            stringBuffer.append(" OR ");
        }
        int i = 0;
        for (String str : a) {
            stringBuffer.append("geofences.server_id=? OR ");
            list.add(str);
            i++;
        }
        if (i > 0) {
            stringBuffer.delete(stringBuffer.length() - 4, stringBuffer.length());
        }
        return i;
    }

    protected static void a(String str) {
        Log.internal("GeofenceCheck|" + str);
    }

    private boolean a(c cVar) {
        List arrayList = new ArrayList(cVar.b());
        return arrayList.size() == 1 && ((String) arrayList.get(0)).equals("all");
    }

    private int b(c cVar, StringBuffer stringBuffer, List<String> list) {
        Set<String> c = cVar.c();
        a("externalIds: " + c.toString());
        if (!(list.isEmpty() || c.isEmpty())) {
            stringBuffer.append(" OR ");
        }
        int i = 0;
        for (String str : c) {
            stringBuffer.append("geofences.external_id=? OR ");
            list.add(str);
            i++;
        }
        if (i > 0) {
            stringBuffer.delete(stringBuffer.length() - 4, stringBuffer.length());
        }
        return i;
    }

    private int c(c cVar, StringBuffer stringBuffer, List<String> list) {
        Set<String> b = cVar.b();
        a("group ids: " + b.toString());
        if (!(list.isEmpty() || b.isEmpty())) {
            stringBuffer.append(" OR ");
        }
        int i = 0;
        for (String str : b) {
            stringBuffer.append("beacon_geofence_groups.server_id=? OR ");
            list.add(str);
            i++;
        }
        if (i > 0) {
            stringBuffer.delete(stringBuffer.length() - 4, stringBuffer.length());
        }
        return i;
    }

    public void a(Context context, k kVar) {
        this.b = this.c.d();
    }

    protected boolean a(List<c> list) {
        A4SGeofenceResolver a4SGeofenceResolver = new A4SGeofenceResolver(this.d);
        for (c cVar : list) {
            if (cVar.f() == d.ENTER) {
                StringBuffer stringBuffer = new StringBuffer();
                List arrayList = new ArrayList();
                boolean a = a(cVar);
                if (a || (c(cVar, stringBuffer, arrayList) + a(cVar, stringBuffer, arrayList)) + b(cVar, stringBuffer, arrayList) != 0) {
                    List allGeofences;
                    if (a) {
                        allGeofences = a4SGeofenceResolver.getAllGeofences();
                    } else {
                        String[] strArr = new String[arrayList.size()];
                        arrayList.toArray(strArr);
                        allGeofences = cVar.b().isEmpty() ? a4SGeofenceResolver.getAllGeofences(stringBuffer.toString(), strArr) : a4SGeofenceResolver.getGeofencesFilteredByGroups(stringBuffer.toString(), strArr);
                    }
                    if (r11 == null || r11.size() == 0) {
                        Log.warn("GeofenceCheck|check: there is no geofences for geofence rule: " + cVar.toString());
                    } else {
                        int i = 0;
                        for (Geofence geofence : r11) {
                            geofence.setDistance(com.ad4screen.sdk.common.i.a(this.b.getLatitude(), this.b.getLongitude(), geofence.getLatitude(), geofence.getLongitude()));
                            a("isValid geofence[" + i + "]" + geofence.toString());
                            i++;
                        }
                        Collections.sort(r11);
                        for (Geofence geofence2 : r11) {
                            double a2 = com.ad4screen.sdk.service.modules.inapp.g.a(this.b, geofence2);
                            double radius = (double) (geofence2.getRadius() + this.b.getAccuracy());
                            a("isValid nearest geofence" + geofence2);
                            a("isValid location lat: " + this.b.getLatitude());
                            a("isValid location long: " + this.b.getLongitude());
                            a("isValid geofence lat: " + geofence2.getLatitude());
                            a("isValid geofence long: " + geofence2.getLongitude());
                            a("isValid geofence distance: " + a2);
                            a("isValid geofence radius: " + radius);
                            if (a2 > radius) {
                                a("isValid your position is not in the geofence radius");
                            } else {
                                boolean z;
                                a = cVar.d().isEmpty();
                                if (!a) {
                                    for (String equals : cVar.d()) {
                                        if (geofence2.getId().equals(equals)) {
                                            a = true;
                                            break;
                                        }
                                    }
                                }
                                boolean isEmpty = cVar.e().isEmpty();
                                if (!isEmpty) {
                                    for (String equals2 : cVar.e()) {
                                        if (geofence2.getExternalId().equals(equals2)) {
                                            z = true;
                                            break;
                                        }
                                    }
                                }
                                z = isEmpty;
                                if (a && r1) {
                                    Map customParams = geofence2.getCustomParams();
                                    customParams.put("distance", String.format("%.02f", new Object[]{Double.valueOf(a2)}));
                                    customParams.put("deviceLat", String.valueOf(this.b.getLatitude()));
                                    customParams.put("deviceLong", String.valueOf(this.b.getLongitude()));
                                    a4SGeofenceResolver.updateGeofence(geofence2);
                                    this.a = geofence2;
                                    return true;
                                }
                                a("isValid your are not in the personal list of " + (a ? "internal" : "external") + " ids");
                            }
                        }
                        continue;
                    }
                } else {
                    Log.warn("GeofenceCheck|check: there is no any filter arguments ?");
                }
            } else {
                cVar.f();
                d dVar = d.EXIT;
            }
        }
        return false;
    }

    public Geofence b() {
        return this.a;
    }
}
