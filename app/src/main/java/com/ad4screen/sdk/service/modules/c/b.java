package com.ad4screen.sdk.service.modules.c;

import android.os.Bundle;

import com.ad4screen.sdk.A4S.Callback;
import com.ad4screen.sdk.A4SService.a;
import com.ad4screen.sdk.Constants;
import com.ad4screen.sdk.Log;
import com.ad4screen.sdk.contract.A4SContract.BeaconsColumns;
import com.ad4screen.sdk.d.f;
import com.ad4screen.sdk.d.i;
import com.ad4screen.sdk.plugins.BeaconPlugin;
import com.ad4screen.sdk.plugins.beacons.BindBeaconService;
import com.ad4screen.sdk.plugins.beacons.IBeaconService;
import com.ad4screen.sdk.plugins.model.Beacon;
import com.ad4screen.sdk.provider.A4SBeaconResolver;
import com.ad4screen.sdk.service.modules.inapp.e.e;
import com.ad4screen.sdk.service.modules.inapp.k;

import java.util.Date;

public class b {
    private final a a;
    private Bundle b;
    private final BindBeaconService c = new BindBeaconService(this.a.a().getApplicationContext());

    public b(a aVar) {
        this.a = aVar;
    }

    private boolean a(Beacon beacon, Date date) {
        return date.getTime() - beacon.getNotifiedTime().getTime() > 3600000;
    }

    public void a() {
        if (this.c != null) {
            this.c.unbindService();
        }
    }

    public void a(Bundle bundle) {
        this.b = bundle;
        if (this.b == null) {
            Log.error("BeaconManager|No Beacon information found, aborting...");
            return;
        }
        Object obj;
        Bundle bundle2 = bundle.getBundle(Constants.EXTRA_BEACON_PAYLOAD);
        if (bundle2 != null) {
            Date date = new Date();
            A4SBeaconResolver a4SBeaconResolver = new A4SBeaconResolver(this.a.a());
            k a = k.a(i.a(this.a.a()));
            obj = null;
            for (String bundle3 : bundle2.keySet()) {
                Beacon beacon;
                Object obj2;
                Bundle bundle4 = bundle2.getBundle(bundle3);
                String string = bundle4.getString("id");
                String string2 = bundle4.getString("acc");
                String str = bundle4.getInt("transition") == 1 ? "enter" : "exit";
                String toUpperCase = bundle4.getString(BeaconsColumns.UUID).toUpperCase();
                double d = bundle4.getDouble("dist");
                int i = bundle4.getInt("maj");
                int i2 = bundle4.getInt("min");
                Beacon beacon2;
                if (string == null) {
                    Log.verbose("BeaconManager|Received unknown beacon uuid=" + toUpperCase);
                    beacon2 = a4SBeaconResolver.getBeacon("uuid=? AND major=? AND minor=?", new String[]{toUpperCase, String.valueOf(i), String.valueOf(i2)});
                    if (beacon2 == null) {
                        beacon = new Beacon(0, toUpperCase, i, i2);
                        beacon.setDetectedTime(date);
                        beacon.setNotifiedTime(date);
                        a4SBeaconResolver.insertBeacon(beacon);
                        beacon = beacon2;
                        obj2 = 1;
                    } else {
                        if (a(beacon2, date)) {
                            obj = 1;
                        }
                        beacon = beacon2;
                        obj2 = obj;
                    }
                } else {
                    Log.verbose("BeaconManager|Received a beacon client_id=" + string);
                    a.a(new com.ad4screen.sdk.service.modules.inapp.a.a(string, string2, str, d));
                    beacon2 = a4SBeaconResolver.getBeaconByClientId(string);
                    int i3;
                    if (beacon2 == null) {
                        Log.warn("BeaconManager|Beacon with client_id=" + string + " is not found in the database");
                        Beacon beacon3 = new Beacon(string, toUpperCase, i, i2);
                        beacon3.setDetectedTime(date);
                        beacon3.setNotifiedTime(date);
                        a4SBeaconResolver.insertBeacon(beacon3);
                        beacon = beacon2;
                        i3 = 1;
                    } else if (a(beacon2, date)) {
                        beacon = beacon2;
                        i3 = 1;
                    } else {
                        beacon = beacon2;
                        obj2 = obj;
                    }
                }
                if (beacon != null) {
                    if (obj2 != null) {
                        beacon.setNotifiedTime(date);
                    }
                    beacon.setDetectedTime(date);
                    a4SBeaconResolver.updateBeacon(beacon);
                }
                obj = obj2;
            }
            a.b(i.a(this.a.a()));
        } else {
            obj = null;
        }
        if (obj == null) {
            Log.debug("BeaconManager|Some beacons have been triggered but were all already sent to server on this session");
            f.a().a(new e(true));
            return;
        }
        new d(this.a.a(), this.b).run();
        this.a.d().a(this.b, true);
    }

    public void a(final Beacon[] beaconArr) {
        if (beaconArr != null && beaconArr.length != 0) {
            BeaconPlugin e = com.ad4screen.sdk.common.d.b.e();
            if (e != null && e.isBeaconServiceDeclared(this.a.a())) {
                this.c.getService(new Callback<IBeaconService>(this) {
                    final /* synthetic */ b b;

                    public void a(IBeaconService iBeaconService) {
                        if (iBeaconService != null) {
                            try {
                                iBeaconService.add(this.b.a.a().getPackageName(), beaconArr);
                            } catch (Throwable e) {
                                Log.error("BeaconManager|Error while calling BeaconService methods", e);
                            }
                        }
                    }

                    public void onError(int i, String str) {
                    }

                    public /* synthetic */ void onResult(Object obj) {
                        a((IBeaconService) obj);
                    }
                });
            }
        }
    }

    public void a(final String[] strArr) {
        if (strArr != null && strArr.length != 0) {
            BeaconPlugin e = com.ad4screen.sdk.common.d.b.e();
            if (e != null && e.isBeaconServiceDeclared(this.a.a())) {
                this.c.getService(new Callback<IBeaconService>(this) {
                    final /* synthetic */ b b;

                    public void a(IBeaconService iBeaconService) {
                        if (iBeaconService != null) {
                            try {
                                iBeaconService.remove(this.b.a.a().getPackageName(), strArr);
                            } catch (Throwable e) {
                                Log.error("BeaconManager|Error while calling BeaconService methods", e);
                            }
                        }
                    }

                    public void onError(int i, String str) {
                    }

                    public /* synthetic */ void onResult(Object obj) {
                        a((IBeaconService) obj);
                    }
                });
            }
        }
    }
}
