package com.ezeeworld.b4s.android.sdk.monitor;

import com.ezeeworld.b4s.android.sdk.B4SLog;
import com.ezeeworld.b4s.android.sdk.ibeacon.IBeaconData;
import com.ezeeworld.b4s.android.sdk.monitor.InteractionHistory.EventType;
import com.ezeeworld.b4s.android.sdk.server.Beacon;
import com.ezeeworld.b4s.android.sdk.server.Interaction;
import com.ezeeworld.b4s.android.sdk.server.Interaction.Condition;
import com.ezeeworld.b4s.android.sdk.server.Shop;

import java.util.Calendar;
import java.util.Date;

class d {
    final Interaction a;
    a b = a.Undetermined;
    Long c;
    Long d;
    Long e;

    enum a {
        Undetermined,
        Entered,
        Exited
    }

    private d(Interaction interaction) {
        this.a = interaction;
    }

    public static a a(int i) {
        switch (i) {
            case 1:
                return a.Entered;
            case 2:
                return a.Exited;
            default:
                return a.Undetermined;
        }
    }

    static d a(d dVar, Interaction interaction) {
        d dVar2 = new d(interaction);
        dVar2.b = dVar.b;
        dVar2.c = dVar.c;
        dVar2.e = dVar.e;
        return dVar2;
    }

    static d a(Interaction interaction) {
        return new d(interaction);
    }

    private boolean k() {
        return this.a.oConditions != null && this.a.oConditions.aConditions != null && this.a.oConditions.aConditions.length > 0 && this.a.oConditions.nVersion == 1;
    }

    public boolean a() {
        B4SLog.d((Object) this, "rangeState:" + this.b);
        return (this.a.bIncomingInteraction && this.b == a.Entered) || ((this.a.bOutgoingInteraction && this.b == a.Exited) || !(this.a.bIncomingInteraction || this.a.bOutgoingInteraction || this.b != a.Entered));
    }

    public boolean a(long j) {
        return !this.a.bSendOnlyIfNotOpened || InteractionHistory.get().findEventRecent(EventType.InteractionNotificationOpened, this.a.sInteractionId, j) == null;
    }

    public boolean a(IBeaconData iBeaconData) {
        boolean z = iBeaconData != null && ((double) this.a.nRangeMin) <= iBeaconData.getDistance() * 100.0d && iBeaconData.getDistance() * 100.0d <= ((double) this.a.nRangeMax);
        boolean z2 = iBeaconData == null || ((double) this.a.nRangeMin) > iBeaconData.getDistance() * 100.0d || iBeaconData.getDistance() * 100.0d > ((double) this.a.nRangeOut);
        B4SLog.d((Object) this, "nowInRange=" + z + " nowOutRange=" + z2);
        if (z && this.b != a.Entered) {
            this.b = a.Entered;
            this.c = Long.valueOf(System.currentTimeMillis());
            this.d = this.c;
            this.e = null;
            return true;
        } else if (!z2 || this.b != a.Entered) {
            return false;
        } else {
            this.b = a.Exited;
            this.e = Long.valueOf(System.currentTimeMillis());
            return true;
        }
    }

    public boolean a(a aVar) {
        if (this.b == aVar) {
            return false;
        }
        this.b = aVar;
        if (aVar == a.Entered) {
            this.c = Long.valueOf(System.currentTimeMillis());
            this.d = this.c;
            this.e = null;
        } else if (aVar == a.Exited) {
            this.e = Long.valueOf(System.currentTimeMillis());
        }
        return true;
    }

    public boolean a(Beacon beacon) {
        for (String str : this.a.aCategoryIds) {
            if (str == null) {
                B4SLog.e((Object) this, "Null categoryId found on:" + this.a.sInteractionId);
            } else if (str.equals("none") || str.equals(beacon.sCategoryId)) {
                return true;
            }
        }
        return false;
    }

    public boolean a(Shop shop) {
        for (String str : this.a.aShopIds) {
            if (str.equals("none") || str.equals(shop.sShopId)) {
                return true;
            }
        }
        return false;
    }

    public boolean a(String str) {
        return this.a.sCampaignType == null || this.a.sCampaignType.equals(str);
    }

    public boolean a(int... iArr) {
        for (int i : iArr) {
            if (this.a.nDistModel == i) {
                return true;
            }
        }
        return false;
    }

    public boolean b() {
        int i = Calendar.getInstance().get(7);
        return (i == 2 && this.a.bMondayEnabled) ? true : (i == 3 && this.a.bTuesdayEnabled) ? true : (i == 4 && this.a.bWednesdayEnabled) ? true : (i == 5 && this.a.bThursdayEnabled) ? true : (i == 6 && this.a.bFridayEnabled) ? true : (i == 7 && this.a.bSaturdayEnabled) ? true : i == 1 && this.a.bSundayEnabled;
    }

    public boolean b(long j) {
        return this.a.nNotificationsMaxCount == 0 || k() || InteractionHistory.get().findEventsRecent(EventType.InteractionMatched, this.a.sInteractionId, j).size() < this.a.nNotificationsMaxCount;
    }

    public boolean b(Beacon beacon) {
        return (this.a.bGeoNotifyIfBeacon && beacon == null) ? false : true;
    }

    public boolean c() {
        if (this.a.nEndTime > 0) {
            Calendar instance = Calendar.getInstance();
            long timeInMillis = instance.getTimeInMillis();
            instance.set(11, 0);
            instance.set(12, 0);
            instance.set(13, 0);
            instance.set(14, 0);
            timeInMillis = (timeInMillis - instance.getTimeInMillis()) / 1000;
            if (((long) this.a.nStartTime) > timeInMillis || ((long) this.a.nEndTime) < timeInMillis) {
                return false;
            }
        }
        int i = Calendar.getInstance().get(11);
        if (this.a.nHoursFrom > i) {
            return false;
        }
        if (this.a.nHoursTo < i) {
            return false;
        }
        return true;
    }

    public boolean d() {
        return this.a.dStartDate.before(new Date());
    }

    public boolean e() {
        return this.a.dEndDate.after(new Date());
    }

    public boolean f() {
        return this.a.nAfterScanTimeout == 0 || k() || InteractionHistory.get().findEventRecent(EventType.InteractionMatched, this.a.sInteractionId, System.currentTimeMillis() - (((long) this.a.nAfterScanTimeout) * 1000)) == null;
    }

    public boolean g() {
        if (this.a.nNextWaveDelay > 0 && !k()) {
            Calendar instance = Calendar.getInstance();
            instance.set(11, 0);
            instance.set(12, 0);
            instance.set(13, 0);
            instance.add(5, -(this.a.nNextWaveDelay - 1));
            if (InteractionHistory.get().findEventRecent(EventType.InteractionMatched, this.a.sInteractionId, instance.getTimeInMillis()) != null) {
                return false;
            }
        }
        return true;
    }

    public boolean h() {
        return !this.a.bOutgoingInteraction || InteractionHistory.get().findEventRecent(EventType.InteractionMatched, this.a.sInteractionId, this.c.longValue()) == null;
    }

    public boolean i() {
        boolean z = true;
        if (this.a.nNotificationPresenceDuration != 0) {
            long longValue;
            if (this.a.bOutgoingInteraction) {
                longValue = this.e.longValue();
            } else if (this.d != null) {
                longValue = this.d.longValue();
            } else {
                longValue = this.c.longValue();
                this.d = Long.valueOf(longValue);
            }
            if (longValue + (((long) this.a.nNotificationPresenceDuration) * 1000) > System.currentTimeMillis()) {
                z = false;
            }
            if (z) {
                this.d = Long.valueOf(this.d.longValue() + (((long) this.a.nNotificationPresenceDuration) * 1000));
            }
        }
        return z;
    }

    public boolean j() {
        if (k()) {
            boolean equals = this.a.oConditions.sScheme.equals("OR");
            Condition[] conditionArr = this.a.oConditions.aConditions;
            int length = conditionArr.length;
            int i = 0;
            while (i < length) {
                Condition condition = conditionArr[i];
                if (condition.nTimeout != 0) {
                    boolean z = InteractionHistory.get().findEventRecent(EventType.values()[condition.nType], condition.sObjectId, condition.nTimeout == 0 ? 0 : System.currentTimeMillis() - (((long) condition.nTimeout) * 1000)) != null;
                    boolean z2 = (!condition.bNot && z) || (condition.bNot && !z);
                    z = !equals || condition.bDefaultCondition;
                    if (equals && z2) {
                        break;
                    } else if (z && !z2) {
                        return false;
                    } else {
                        i++;
                    }
                } else {
                    break;
                }
            }
            if (equals) {
                return false;
            }
        }
        return true;
    }
}
