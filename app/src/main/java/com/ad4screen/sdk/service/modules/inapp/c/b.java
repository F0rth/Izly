package com.ad4screen.sdk.service.modules.inapp.c;

import android.content.Context;
import android.support.v4.os.EnvironmentCompat;

import com.ad4screen.sdk.Log;
import com.ad4screen.sdk.plugins.model.Beacon;
import com.ad4screen.sdk.provider.A4SBeaconResolver;
import com.ad4screen.sdk.service.modules.inapp.a.a;
import com.ad4screen.sdk.service.modules.inapp.k;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public abstract class b implements m {
    protected HashMap<String, a> a;
    protected a b;
    protected Context c;

    public b(Context context) {
        this.c = context;
    }

    private int a(com.ad4screen.sdk.service.modules.inapp.a.b bVar, StringBuffer stringBuffer, List<String> list) {
        Set<String> c = bVar.c();
        a("ids: " + c.toString());
        if (!(list.isEmpty() || c.isEmpty())) {
            stringBuffer.append(" OR ");
        }
        int i = 0;
        for (String str : c) {
            stringBuffer.append("beacons.server_id=? OR ");
            list.add(str);
            i++;
        }
        if (i > 0) {
            stringBuffer.delete(stringBuffer.length() - 4, stringBuffer.length());
        }
        return i;
    }

    protected static void a(String str) {
        Log.internal("BeaconCheck|" + str);
    }

    private boolean a(com.ad4screen.sdk.service.modules.inapp.a.b bVar) {
        List arrayList = new ArrayList(bVar.d());
        return arrayList.size() == 1 && ((String) arrayList.get(0)).equals("all");
    }

    private int b(com.ad4screen.sdk.service.modules.inapp.a.b bVar, StringBuffer stringBuffer, List<String> list) {
        Set<String> e = bVar.e();
        a("externalIds: " + e.toString());
        if (!(list.isEmpty() || e.isEmpty())) {
            stringBuffer.append(" OR ");
        }
        int i = 0;
        for (String str : e) {
            stringBuffer.append("beacons.external_id=? OR ");
            list.add(str);
            i++;
        }
        if (i > 0) {
            stringBuffer.delete(stringBuffer.length() - 4, stringBuffer.length());
        }
        return i;
    }

    private int c(com.ad4screen.sdk.service.modules.inapp.a.b bVar, StringBuffer stringBuffer, List<String> list) {
        Set<String> d = bVar.d();
        a("group ids: " + d.toString());
        if (!(list.isEmpty() || d.isEmpty())) {
            stringBuffer.append(" OR ");
        }
        int i = 0;
        for (String str : d) {
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
        this.a = kVar.a();
    }

    protected boolean a(List<com.ad4screen.sdk.service.modules.inapp.a.b> list) {
        A4SBeaconResolver a4SBeaconResolver = new A4SBeaconResolver(this.c);
        for (com.ad4screen.sdk.service.modules.inapp.a.b bVar : list) {
            StringBuffer stringBuffer = new StringBuffer();
            List arrayList = new ArrayList();
            boolean a = a(bVar);
            if (a || (c(bVar, stringBuffer, arrayList) + a(bVar, stringBuffer, arrayList)) + b(bVar, stringBuffer, arrayList) != 0) {
                List allBeacons;
                if (a) {
                    allBeacons = a4SBeaconResolver.getAllBeacons();
                } else {
                    String[] strArr = new String[arrayList.size()];
                    arrayList.toArray(strArr);
                    allBeacons = bVar.d().isEmpty() ? a4SBeaconResolver.getAllBeacons(stringBuffer.toString(), strArr) : a4SBeaconResolver.getBeaconsFilteredByGroups(stringBuffer.toString(), strArr);
                }
                if (r1 == null || r1.size() == 0) {
                    Log.warn("BeaconCheck|check: there is no beacons for beacon rule: " + bVar.toString());
                } else {
                    HashMap hashMap = new HashMap(r1.size());
                    for (Beacon beacon : r1) {
                        hashMap.put(beacon.id, beacon);
                    }
                    List<a> arrayList2 = new ArrayList(this.a.values());
                    Collections.sort(arrayList2);
                    for (a aVar : arrayList2) {
                        if (hashMap.containsKey(aVar.a)) {
                            String a2;
                            a("check detected beacon is found in rules: " + aVar);
                            Beacon beacon2 = (Beacon) hashMap.get(aVar.a);
                            boolean isEmpty = bVar.f().isEmpty();
                            if (!isEmpty) {
                                for (String a22 : bVar.f()) {
                                    if (aVar.a.equals(a22)) {
                                        isEmpty = true;
                                        break;
                                    }
                                }
                            }
                            boolean isEmpty2 = bVar.g().isEmpty();
                            if (!isEmpty2) {
                                for (String a222 : bVar.g()) {
                                    if (beacon2.externalId.equals(a222)) {
                                        a = true;
                                        break;
                                    }
                                }
                            }
                            a = isEmpty2;
                            String str;
                            if (isEmpty && r3) {
                                a222 = bVar.a();
                                if (aVar.c.equalsIgnoreCase(a222)) {
                                    a("check transition is matched!");
                                    beacon2.customs.put("distance", String.valueOf(aVar.e));
                                    a4SBeaconResolver.updateBeacon(beacon2);
                                    this.b = aVar;
                                    if (a222.equalsIgnoreCase("exit")) {
                                        return true;
                                    }
                                    str = aVar.b;
                                    String b = bVar.b();
                                    if (b == null || b.equalsIgnoreCase(EnvironmentCompat.MEDIA_UNKNOWN)) {
                                        return true;
                                    }
                                    if (b.equalsIgnoreCase("far") && !str.equalsIgnoreCase(EnvironmentCompat.MEDIA_UNKNOWN)) {
                                        return true;
                                    }
                                    if (b.equalsIgnoreCase("near") && (str.equalsIgnoreCase("near") || str.equalsIgnoreCase("immediate"))) {
                                        return true;
                                    }
                                    if (b.equalsIgnoreCase("immediate") && str.equalsIgnoreCase("immediate")) {
                                        return true;
                                    }
                                    this.b = null;
                                } else {
                                    a("check transition is not matched!");
                                }
                            } else {
                                str = isEmpty ? "external" : "internal and external";
                                a("isValid the found beacon from DB: " + beacon2);
                                a("isValid the beacon is not in the personal list of " + str + " ids");
                                if (isEmpty) {
                                    a("isValid the personal list of external ids: " + bVar.g());
                                } else {
                                    a("isValid the personal list of internal ids: " + bVar.f());
                                }
                            }
                        }
                    }
                    continue;
                }
            } else {
                Log.warn("BeaconCheck|check: there is no any filter arguments ?");
            }
        }
        return false;
    }

    public a a_() {
        return this.b;
    }
}
