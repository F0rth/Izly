package com.ezeeworld.b4s.android.sdk.positioning.a;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Pair;

import com.ezeeworld.b4s.android.sdk.EventBus;
import com.ezeeworld.b4s.android.sdk.positioning.Position;
import com.ezeeworld.b4s.android.sdk.positioning.PositioningUpdate;
import com.ezeeworld.b4s.android.sdk.positioning.Zone;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

public final class c implements SensorEventListener {
    private int a = 18;
    private SensorManager b;
    private boolean c;
    private Random d;
    private Map<String, Position> e;
    private Map<String, Pair<Double, Long>> f;
    private Map<String, LinkedHashMap<String, Double>> g;
    private boolean h = false;
    private double i;
    private Position j;
    private Position k;
    private b[] l;
    private float m;
    private a n = null;
    private a o = null;
    private Position p = null;
    private double q;
    private a r = new a(0.0d, 0.5d, 1.2d);
    private a s = new a(0.0d, 0.5d, 1.2d);
    private a t = new a(0.0d, 0.5d, 1.2d);

    public final PositioningUpdate a() {
        if (this.f == null || this.f.size() == 0) {
            return null;
        }
        System.currentTimeMillis();
        long currentTimeMillis = System.currentTimeMillis();
        Iterator it = this.f.entrySet().iterator();
        Entry entry = null;
        while (it.hasNext()) {
            Entry entry2 = (Entry) it.next();
            if (((Long) ((Pair) entry2.getValue()).second).longValue() < currentTimeMillis - 60000) {
                it.remove();
            } else {
                if (entry != null && ((Double) ((Pair) entry.getValue()).first).doubleValue() <= ((Double) ((Pair) entry2.getValue()).first).doubleValue()) {
                    entry2 = entry;
                }
                entry = entry2;
            }
        }
        if (entry == null) {
            return null;
        }
        int i;
        double d;
        double d2;
        double d3;
        Position position;
        Position position2 = (Position) this.e.get(entry.getKey());
        System.currentTimeMillis();
        double d4 = (this.c && this.h) ? 3.0d : this.c ? this.m < 0.75f ? 0.25d : 0.007d : this.m < 0.75f ? 1.0d : 0.007d;
        for (b bVar : this.l) {
            d = 0.0d;
            d2 = 0.0d;
            if (((Double) ((Pair) entry.getValue()).first).doubleValue() < 5.0d) {
                d = 0.06d * (((position2.x - bVar.a) * Math.abs(this.d.nextGaussian())) * d4);
                d2 = (((position2.y - bVar.b) * Math.abs(this.d.nextGaussian())) * d4) * 0.06d;
            }
            bVar.a = (d + bVar.a) + (this.d.nextGaussian() * d4);
            bVar.b = (bVar.b + d2) + (this.d.nextGaussian() * d4);
        }
        System.currentTimeMillis();
        Map hashMap = new HashMap(this.f.size());
        LinkedHashMap linkedHashMap = (LinkedHashMap) this.g.get(entry.getKey());
        for (Entry entry3 : this.f.entrySet()) {
            if (((String) entry3.getKey()).equals(entry.getKey()) || ((Double) linkedHashMap.get(entry3.getKey())).doubleValue() < 4.800000190734863d) {
                hashMap.put(entry3.getKey(), ((Pair) entry3.getValue()).first);
            }
        }
        b[] bVarArr = this.l;
        int length = bVarArr.length;
        int i2 = 0;
        d = 0.0d;
        while (i2 < length) {
            b bVar2 = bVarArr[i2];
            d3 = 0.0d;
            for (Entry entry22 : hashMap.entrySet()) {
                d3 = ((this.i - Math.abs(((Double) entry22.getValue()).doubleValue() - bVar2.a((Position) this.e.get(entry22.getKey())))) / this.i) + d3;
            }
            double size = d3 / ((double) hashMap.size());
            bVar2.a(size);
            i2++;
            d = Math.max(d, size);
        }
        System.currentTimeMillis();
        b[] bVarArr2 = this.l;
        i2 = bVarArr2.length;
        int i3 = 0;
        double d5 = 0.0d;
        while (i3 < i2) {
            b bVar3 = bVarArr2[i3];
            bVar3.b(d);
            i3++;
            d5 = bVar3.c + d5;
        }
        System.currentTimeMillis();
        b[] bVarArr3 = new b[500];
        for (int i4 = 0; i4 < 500; i4++) {
            d3 = this.d.nextDouble() * d5;
            i3 = -1;
            do {
                i3++;
                d3 -= this.l[i3].c;
                if (d3 <= 0.0d) {
                    break;
                }
            } while (i3 < 499);
            bVarArr3[i4] = new b(this.l[i3].a, this.l[i3].b, this.l[i3].c);
        }
        this.l = bVarArr3;
        System.currentTimeMillis();
        bVarArr2 = this.l;
        int length2 = bVarArr2.length;
        i3 = 0;
        d2 = 0.0d;
        d = 0.0d;
        d4 = 0.0d;
        while (i3 < length2) {
            b bVar4 = bVarArr2[i3];
            i3++;
            d2 += bVar4.c;
            d = (bVar4.c * bVar4.a) + d;
            d4 = (bVar4.c * bVar4.b) + d4;
        }
        d3 = d / d2;
        d2 = d4 / d2;
        if (this.n == null || this.o == null) {
            this.n = new a(d3, 0.2d, 6.0d);
            this.o = new a(d2, 0.2d, 6.0d);
        } else {
            this.n.a(d3);
            this.o.a(d2);
        }
        Position position3 = new Position(this.n.a(), this.o.a());
        System.currentTimeMillis();
        Object obj = new Position[this.l.length];
        Zone zone = new Zone("", position3.x - 1.15d, position3.x + 1.15d, position3.y + 1.15d, position3.y - 1.15d);
        int i5 = 0;
        for (i = 0; i < this.l.length; i++) {
            obj[i] = new Position(this.l[i].a, this.l[i].b);
            if (zone.contains(obj[i])) {
                i5++;
            }
        }
        this.m = ((float) i5) / 500.0f;
        EventBus.get().post(obj);
        System.currentTimeMillis();
        if (this.p == null || this.m <= 0.75f) {
            this.p = position3;
            position = position3;
        } else {
            position = this.p;
        }
        this.a--;
        if (this.a >= 0) {
            return null;
        }
        return new PositioningUpdate(position, this.f, this.m, this.m > 0.75f, this.h, this.q);
    }

    public final void a(Context context, Map<String, Position> map) {
        this.e = map;
        this.f = new HashMap(map.size());
        this.b = (SensorManager) context.getSystemService("sensor");
        this.c = this.b.registerListener(this, this.b.getDefaultSensor(10), 3);
        this.g = new HashMap();
        for (Entry entry : map.entrySet()) {
            List<Pair> arrayList = new ArrayList();
            for (Entry entry2 : map.entrySet()) {
                if (!entry.equals(entry2)) {
                    arrayList.add(Pair.create(entry2.getKey(), Double.valueOf(((Position) entry.getValue()).distanceTo((Position) entry2.getValue()))));
                }
            }
            Collections.sort(arrayList, new d(this));
            LinkedHashMap linkedHashMap = new LinkedHashMap();
            for (Pair pair : arrayList) {
                linkedHashMap.put(pair.first, pair.second);
            }
            this.g.put(entry.getKey(), linkedHashMap);
        }
        for (Position position : map.values()) {
            if (this.j == null || this.k == null) {
                this.j = new Position(position.x, position.y);
                this.k = new Position(position.x, position.y);
            } else {
                if (position.x < this.j.x) {
                    this.j.x = position.x;
                }
                if (position.x > this.k.x) {
                    this.k.x = position.x;
                }
                if (position.y > this.j.y) {
                    this.j.y = position.y;
                }
                if (position.y < this.k.y) {
                    this.k.y = position.y;
                }
            }
        }
        double d = this.k.x - this.j.x;
        double d2 = this.j.y - this.k.y;
        Position position2 = this.j;
        position2.x -= 0.2d * d;
        position2 = this.k;
        position2.x = (d * 0.2d) + position2.x;
        Position position3 = this.j;
        position3.y += 0.2d * d2;
        position3 = this.k;
        position3.y -= d2 * 0.2d;
        this.i = 100.0d;
        this.d = new Random();
        this.l = new b[500];
        for (int i = 0; i < 500; i++) {
            this.l[i] = new b(this.j.x + (this.d.nextDouble() * (this.k.x - this.j.x)), this.k.y + (this.d.nextDouble() * (this.j.y - this.k.y)), 1.0d);
        }
    }

    public final void a(String str, double d) {
        if (this.f != null) {
            this.f.put(str, new Pair(Double.valueOf(0.8d * d), Long.valueOf(System.currentTimeMillis())));
        }
    }

    public final void b() {
        if (this.b != null) {
            this.b.unregisterListener(this);
        }
    }

    public final void onAccuracyChanged(Sensor sensor, int i) {
    }

    public final void onSensorChanged(SensorEvent sensorEvent) {
        boolean z = false;
        float f = sensorEvent.values[0];
        float f2 = sensorEvent.values[1];
        float f3 = sensorEvent.values[2];
        this.q = Math.sqrt((Math.pow(((double) f) - this.r.a(), 2.0d) + Math.pow(((double) f2) - this.s.a(), 2.0d)) + Math.pow(((double) f3) - this.t.a(), 2.0d));
        if (this.q > 0.9d) {
            z = true;
        }
        this.h = z;
        this.r.a((double) f);
        this.s.a((double) f2);
        this.t.a((double) f3);
    }
}
