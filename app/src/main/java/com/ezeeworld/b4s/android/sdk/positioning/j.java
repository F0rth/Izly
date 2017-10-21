package com.ezeeworld.b4s.android.sdk.positioning;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

final class j {
    private Map<Zone, Boolean> a;
    private Map<String, Long> b;
    private Map<String, Long> c;

    j() {
    }

    final UserZoneChange a(Position position) {
        UserZoneChange userZoneChange;
        synchronized (this) {
            for (Entry entry : this.a.entrySet()) {
                Zone zone = (Zone) entry.getKey();
                if (zone.contains(position)) {
                    if (((Boolean) entry.getValue()).booleanValue() || this.b.containsKey(zone.id)) {
                        if (!((Boolean) entry.getValue()).booleanValue() && ((Long) this.b.get(zone.id)).longValue() <= System.currentTimeMillis() - 2000) {
                            entry.setValue(Boolean.valueOf(true));
                            this.b.remove(zone.id);
                            userZoneChange = new UserZoneChange(true, zone, position);
                            break;
                        }
                    }
                    this.b.put(zone.id, Long.valueOf(System.currentTimeMillis()));
                    this.c.remove(zone.id);
                } else {
                    if (!((Boolean) entry.getValue()).booleanValue() || this.c.containsKey(zone.id)) {
                        if (((Boolean) entry.getValue()).booleanValue() && ((Long) this.c.get(zone.id)).longValue() <= System.currentTimeMillis() - 2000) {
                            entry.setValue(Boolean.valueOf(false));
                            this.c.remove(zone.id);
                            userZoneChange = new UserZoneChange(false, zone, position);
                            break;
                        }
                    }
                    this.c.put(zone.id, Long.valueOf(System.currentTimeMillis()));
                    this.b.remove(zone.id);
                }
            }
            userZoneChange = null;
        }
        return userZoneChange;
    }

    final Zone a() {
        for (Entry entry : this.a.entrySet()) {
            if (((Boolean) entry.getValue()).booleanValue()) {
                return (Zone) entry.getKey();
            }
        }
        return null;
    }

    final void a(List<Zone> list) {
        synchronized (this) {
            this.a = new HashMap();
            for (Zone put : list) {
                this.a.put(put, Boolean.valueOf(false));
            }
            this.b = new HashMap();
            this.c = new HashMap();
        }
    }
}
