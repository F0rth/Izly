package com.ad4screen.sdk.d;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import com.ad4screen.sdk.A4S.Callback;
import com.ad4screen.sdk.Log;
import com.ad4screen.sdk.common.g;
import com.ad4screen.sdk.plugins.LocationPlugin;

public final class a implements g {
    private static a g;
    private boolean a = false;
    private final Context b;
    private Location c;
    private boolean d;
    private LocationPlugin e;
    private final LocationListener f = new LocationListener(this) {
        final /* synthetic */ a a;

        {
            this.a = r1;
        }

        public void onLocationChanged(Location location) {
            if (location != null) {
                Log.debug("Geolocation|Location ping from provider '" + location.getProvider() + "' -> lat:" + location.getLatitude() + " long:" + location.getLongitude() + " (accuracy: " + location.getAccuracy() + "m)");
                this.a.b(location);
            }
        }

        public void onProviderDisabled(String str) {
            Log.debug("Geolocation|Provider '" + str + "' was disabled by user");
        }

        public void onProviderEnabled(String str) {
            Log.debug("Geolocation|Provider '" + str + "' was enabled by user");
        }

        public void onStatusChanged(String str, int i, Bundle bundle) {
            switch (i) {
                case 0:
                    Log.debug("Geolocation|Provider '" + str + "' is out of service");
                    return;
                case 1:
                    Log.debug("Geolocation|Provider '" + str + "' is temporary unavailable");
                    return;
                case 2:
                    Log.debug("Geolocation|Provider '" + str + "' is now available");
                    return;
                default:
                    return;
            }
        }
    };

    public static final class a implements com.ad4screen.sdk.d.f.a<b> {
        public final void a(b bVar) {
            bVar.a();
        }
    }

    public interface b {
        void a();
    }

    private a(Context context) {
        this.b = context;
        this.d = false;
    }

    public static a a(Context context) {
        a aVar;
        synchronized (a.class) {
            try {
                if (g == null) {
                    g = new a(context.getApplicationContext());
                }
                aVar = g;
            } catch (Throwable th) {
                Class cls = a.class;
            }
        }
        return aVar;
    }

    private boolean a(Location location, Location location2) {
        if (location == null) {
            return false;
        }
        if (location2 == null) {
            Log.debug("Geolocation|Previous location was unknown");
            return true;
        }
        long time = location.getTime() - location2.getTime();
        boolean z = time > 180000;
        boolean z2 = time < -180000;
        boolean z3 = time > 0;
        if (z) {
            Log.debug("Geolocation|New location is significantly newer");
            return true;
        } else if (z2) {
            Log.debug("Geolocation|New location is significantly older");
            return false;
        } else {
            int accuracy = (int) (location.getAccuracy() - location2.getAccuracy());
            boolean z4 = accuracy > 0;
            z = accuracy < 0;
            z2 = accuracy > 200;
            boolean a = a(location.getProvider(), location2.getProvider());
            if (z) {
                Log.debug("Geolocation|New location is more accurate");
                return true;
            } else if (z3 && !z4) {
                Log.debug("Geolocation|New location is more accurate");
                return true;
            } else if (z3 && !z2 && a) {
                Log.debug("Geolocation|New location is newer despite less accurate");
                return true;
            } else {
                Log.debug("Geolocation|Location is inaccurate");
                return false;
            }
        }
    }

    private boolean a(String str, String str2) {
        return str == null ? str2 == null : str.equals(str2);
    }

    private void b(Location location) {
        if (a(location, this.c)) {
            Log.debug("Geolocation|Current location updated to lat:" + location.getLatitude() + " long:" + location.getLongitude() + " (accuracy: " + location.getAccuracy() + "m)");
            a(location);
            f.a().a(new a());
        }
    }

    public final void a(Location location) {
        this.c = location;
        Editor edit = this.b.getSharedPreferences("com.ad4screen.sdk.systems.GeoLocation", 0).edit();
        edit.putFloat("com.ad4screen.sdk.location.last.longitude", (float) location.getLongitude());
        edit.putFloat("com.ad4screen.sdk.location.last.latitude", (float) location.getLatitude());
        edit.putFloat("com.ad4screen.sdk.location.last.accuracy", location.getAccuracy());
        edit.putFloat("com.ad4screen.sdk.location.last.altitude", (float) location.getAltitude());
        edit.putLong("com.ad4screen.sdk.location.last.time", location.getTime());
        edit.commit();
    }

    public final boolean a() {
        return this.d;
    }

    public final void b() {
        Log.debug("Geolocation|Starting location updates");
        this.e = com.ad4screen.sdk.common.d.b.f();
        if (this.e == null) {
            this.a = true;
        }
        if (!(this.a || this.e.getPluginVersion() == 1)) {
            Log.error("Geolocation|Google Play Services Location Plugin version is too old ! Please update it");
            this.a = true;
        }
        if (!this.a) {
            Log.debug("Geolocation|Google Play Services Location Plugin Found.");
            this.a = !this.e.connect(this.b, 180000, 60000, new Callback<Location>(this) {
                final /* synthetic */ a a;

                {
                    this.a = r1;
                }

                public void a(Location location) {
                    this.a.f.onLocationChanged(location);
                }

                public void onError(int i, String str) {
                }

                public /* synthetic */ void onResult(Object obj) {
                    a((Location) obj);
                }
            });
        }
        if (this.a) {
            try {
                LocationManager locationManager = (LocationManager) this.b.getSystemService("location");
                b(locationManager.getLastKnownLocation("network"));
                b(locationManager.getLastKnownLocation("gps"));
                locationManager.requestLocationUpdates("network", 180000, 25.0f, this.f);
                locationManager.requestLocationUpdates("gps", 180000, 25.0f, this.f);
                this.d = true;
                return;
            } catch (Throwable e) {
                Log.error("Geolocation|Could not start location updates", e);
                c();
                return;
            }
        }
        Log.debug("Geolocation|Connected to Google Play Services Location.");
        this.d = true;
    }

    public final void c() {
        if (!this.a && this.e != null) {
            Log.debug("Geolocation|Stopping location updates and disconnecting to Google Play Services Location");
            this.e.disconnect();
        } else if (this.b != null) {
            try {
                Log.debug("Geolocation|Stopping location updates");
                ((LocationManager) this.b.getSystemService("location")).removeUpdates(this.f);
            } catch (Throwable e) {
                Log.error("Geolocation|Could not stop location updates", e);
            }
        }
        this.d = false;
    }

    public final Location d() {
        if (this.c != null) {
            return this.c;
        }
        SharedPreferences sharedPreferences = this.b.getSharedPreferences("com.ad4screen.sdk.systems.GeoLocation", 0);
        if (sharedPreferences.contains("com.ad4screen.sdk.location.last.longitude")) {
            Location location = new Location("fused");
            location.setLongitude((double) sharedPreferences.getFloat("com.ad4screen.sdk.location.last.longitude", 0.0f));
            location.setLatitude((double) sharedPreferences.getFloat("com.ad4screen.sdk.location.last.latitude", 0.0f));
            location.setAccuracy(sharedPreferences.getFloat("com.ad4screen.sdk.location.last.accuracy", 0.0f));
            location.setAltitude((double) sharedPreferences.getFloat("com.ad4screen.sdk.location.last.altitude", 0.0f));
            location.setTime(sharedPreferences.getLong("com.ad4screen.sdk.location.last.time", 0));
            if (g.e().b() - location.getTime() < 300000) {
                Log.info("DefaultGeolocationProvider|use last location detected");
                return location;
            }
        }
        Log.info("DefaultGeolocationProvider can not return a location");
        return null;
    }
}
