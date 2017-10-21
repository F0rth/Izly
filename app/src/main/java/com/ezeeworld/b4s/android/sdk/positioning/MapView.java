package com.ezeeworld.b4s.android.sdk.positioning;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.v4.internal.view.SupportMenu;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.util.Pair;
import android.util.TypedValue;
import android.view.View;

import com.ezeeworld.b4s.android.sdk.positioning.server.IndoorMap;
import com.ezeeworld.b4s.android.sdk.positioning.server.IndoorMap.BeaconPosition;
import com.ezeeworld.b4s.android.sdk.positioning.server.IndoorMap.IndoorArea;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

public final class MapView extends View {
    private List<PointF> A;
    private List<String> B;
    private PointF C;
    private List<PointF> D;
    private PositioningUpdate E;
    private List<PointF> F;
    private List<Float> G;
    private List<RectF> H;
    private RectF I;
    private List<PointF> J;
    private Paint a;
    private Paint b;
    private Paint c;
    private PointF d;
    private float e;
    private Paint f;
    private Paint g;
    private PointF h;
    private float i;
    private Paint j;
    private float k;
    private Paint l;
    private Paint m;
    private Paint n;
    private Paint o;
    private float p;
    private float q;
    private HashMap<String, Position> r;
    private Position s;
    private Position[] t;
    private List<Zone> u;
    private Zone v;
    private List<HistoricPosition> w;
    private Bitmap x;
    private Rect y;
    private RectF z;

    public MapView(Context context) {
        super(context);
        a();
    }

    public MapView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        a();
    }

    public MapView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        a();
    }

    private float a(int i) {
        return TypedValue.applyDimension(1, (float) i, getResources().getDisplayMetrics());
    }

    private void a() {
        this.w = new ArrayList();
        this.a = new Paint();
        this.b = new Paint(1);
        this.b.setStyle(Style.FILL);
        this.b.setColor(SupportMenu.CATEGORY_MASK);
        this.c = new Paint(1);
        this.c.setColor(ViewCompat.MEASURED_STATE_MASK);
        this.c.setTextSize(a(12));
        this.e = a(8);
        this.f = new Paint(1);
        this.f.setStyle(Style.FILL);
        this.f.setColor(-16776961);
        this.g = new Paint(1);
        this.g.setColor(-1);
        this.g.setTextSize(a(12));
        this.i = a(8);
        this.j = new Paint(1);
        this.j.setStyle(Style.FILL);
        this.j.setColor(-2139654281);
        this.k = a(2);
        this.l = new Paint(1);
        this.l.setStyle(Style.STROKE);
        this.l.setColor(ViewCompat.MEASURED_STATE_MASK);
        this.l.setStrokeWidth(a(1));
        this.l.setPathEffect(new DashPathEffect(new float[]{10.0f, 10.0f}, 0.0f));
        this.m = new Paint(1);
        this.m.setStyle(Style.FILL);
        this.m.setColor(-2134061876);
        this.n = new Paint(1);
        this.n.setStyle(Style.FILL);
        this.n.setColor(-2130725888);
        this.o = new Paint(1);
        this.o.setStyle(Style.STROKE);
        this.o.setColor(-13931738);
        this.o.setStrokeWidth(a(2));
        Rect rect = new Rect();
        this.c.getTextBounds("B4S:0000:0000", 0, 13, rect);
        this.d = new PointF((float) rect.width(), (float) rect.height());
        rect = new Rect();
        this.g.getTextBounds("X", 0, 1, rect);
        this.h = new PointF((float) rect.width(), (float) rect.height());
        if (isInEditMode()) {
            this.r = new HashMap();
            this.r.put("B4S:0000:0000", new Position(-2.0d, -2.0d));
            this.r.put("B4S:0000:0000", new Position(2.0d, -2.0d));
            this.r.put("B4S:0000:0000", new Position(2.0d, 2.0d));
            this.r.put("B4S:0000:0000", new Position(-3.0d, 3.0d));
            this.s = new Position(0.0d, 0.0d);
            this.t = new Position[2];
            this.t[0] = new Position(1.0d, 1.0d);
            this.t[1] = new Position(1.5d, 1.5d);
        }
    }

    private void a(int i, int i2) {
        if (i != 0 && i2 != 0) {
            if (this.r == null) {
                IndoorMap map = PositioningManager.get().getMap();
                if (map != null) {
                    a(map);
                } else {
                    return;
                }
            }
            float min = Math.min(((float) i) / this.p, ((float) i2) / this.q);
            float f = (((float) i) - (this.p * min)) / 2.0f;
            float f2 = (((float) i2) - (this.q * min)) / 2.0f;
            float f3 = ((float) i) - f;
            float f4 = ((float) i2) - f2;
            if (this.z == null) {
                this.z = new RectF(f, f2, f3, f4);
            }
            if (this.D == null && this.t != null) {
                this.D = new ArrayList(this.t.length);
                for (Position position : this.t) {
                    this.D.add(new PointF(f3 - (((float) position.x) * min), f4 - (((float) position.y) * min)));
                }
            }
            if (this.A == null) {
                this.A = new ArrayList();
                this.B = new ArrayList();
                for (Entry entry : this.r.entrySet()) {
                    Position position2 = (Position) entry.getValue();
                    this.A.add(new PointF(f3 - (((float) position2.x) * min), f4 - (((float) position2.y) * min)));
                    this.B.add(entry.getKey());
                }
            }
            if (this.C == null && this.s != null) {
                this.C = new PointF(f3 - (((float) this.s.x) * min), f4 - (((float) this.s.y) * min));
            }
            if (this.G == null && this.E != null) {
                this.F = new ArrayList();
                this.G = new ArrayList();
                for (Entry entry2 : this.r.entrySet()) {
                    this.F.add(new PointF(f3 - (((float) ((Position) entry2.getValue()).x) * min), f4 - (((float) ((Position) entry2.getValue()).y) * min)));
                    Pair pair = (Pair) this.E.beaconDistances.get(entry2.getKey());
                    if (pair != null) {
                        this.G.add(Float.valueOf(((Double) pair.first).floatValue() * min));
                    }
                }
            }
            if (this.J == null && !this.w.isEmpty()) {
                this.J = new ArrayList();
                for (HistoricPosition historicPosition : this.w) {
                    this.J.add(new PointF(f3 - (((float) historicPosition.x) * min), f4 - (((float) historicPosition.y) * min)));
                }
            }
            if ((this.H == null && this.u != null) || (this.I == null && this.v != null)) {
                this.H = new ArrayList();
                this.I = null;
                for (Zone zone : this.u) {
                    RectF rectF = new RectF(f3 - (((float) zone.left) * min), f4 - (((float) zone.top) * min), f3 - (((float) zone.right) * min), f4 - (((float) zone.bottom) * min));
                    if (this.v == null || !this.v.equals(zone)) {
                        this.H.add(rectF);
                    } else {
                        this.I = rectF;
                    }
                }
            }
        }
    }

    private void a(IndoorMap indoorMap) {
        this.p = indoorMap.size.nW;
        this.q = indoorMap.size.nH;
        this.r = new HashMap();
        for (BeaconPosition beaconPosition : indoorMap.beacons) {
            this.r.put(beaconPosition.sInnerName, new Position((double) beaconPosition.nX, (double) beaconPosition.nY));
        }
        this.u = new ArrayList();
        for (IndoorArea indoorArea : indoorMap.areas) {
            this.u.add(new Zone(indoorArea.sName, (double) (indoorArea.nX + indoorArea.nW), (double) indoorArea.nX, (double) (indoorArea.nY + indoorArea.nH), (double) indoorArea.nY));
        }
        this.z = null;
        this.A = null;
        this.B = null;
        this.H = null;
        this.I = null;
    }

    public final void clearMap() {
        this.r = null;
        this.u = null;
        this.A = null;
        this.B = null;
        this.C = null;
        this.D = null;
        this.F = null;
        this.G = null;
        this.H = null;
        this.I = null;
        a(getWidth(), getHeight());
        postInvalidate();
    }

    protected final void onDraw(Canvas canvas) {
        if (!(this.x == null || this.z == null)) {
            canvas.drawBitmap(this.x, this.y, this.z, this.a);
        }
        if (this.I != null) {
            canvas.drawRect(this.I, this.n);
        }
        if (this.H != null) {
            for (RectF drawRect : this.H) {
                canvas.drawRect(drawRect, this.m);
            }
        }
        if (this.A != null) {
            for (PointF pointF : this.A) {
                canvas.drawCircle(pointF.x, pointF.y, this.e, this.b);
            }
        }
        if (this.D != null) {
            for (PointF pointF2 : this.D) {
                canvas.drawCircle(pointF2.x, pointF2.y, this.k, this.j);
            }
        }
        if (!(this.G == null || this.F == null)) {
            for (int i = 0; i < this.G.size(); i++) {
                canvas.drawCircle(((PointF) this.F.get(i)).x, ((PointF) this.F.get(i)).y, ((Float) this.G.get(i)).floatValue(), this.l);
            }
        }
        if (this.J != null) {
            PointF pointF3 = null;
            for (PointF pointF4 : this.J) {
                if (pointF3 != null) {
                    canvas.drawLine(pointF3.x, pointF3.y, pointF4.x, pointF4.y, this.o);
                }
                pointF3 = pointF4;
            }
        }
        if (this.C != null) {
            canvas.drawCircle(this.C.x, this.C.y, this.i, this.f);
            canvas.drawText("X", this.C.x - (this.h.x / 2.0f), this.C.y + (this.h.y / 2.0f), this.g);
        }
    }

    protected final void onSizeChanged(int i, int i2, int i3, int i4) {
        this.A = null;
        this.B = null;
        this.C = null;
        this.D = null;
        this.F = null;
        this.G = null;
        this.H = null;
        this.I = null;
        a(i, i2);
    }

    public final void updateMapGraphic(Bitmap bitmap) {
        this.x = bitmap;
        this.y = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        a(getWidth(), getHeight());
        postInvalidate();
    }

    public final void updateParticles(Position[] positionArr) {
        this.t = positionArr;
        this.D = null;
        a(getWidth(), getHeight());
        postInvalidate();
    }

    public final void updateUserPosition(PositioningUpdate positioningUpdate) {
        this.s = positioningUpdate.position;
        this.C = null;
        this.E = positioningUpdate;
        this.F = null;
        this.G = null;
        if (positioningUpdate.position != null) {
            this.w.add(new HistoricPosition(positioningUpdate.position.x, positioningUpdate.position.y, new Date(), positioningUpdate.zone));
        }
        this.J = null;
        a(getWidth(), getHeight());
        postInvalidate();
    }

    public final void updateUserZone(UserZoneChange userZoneChange) {
        this.v = userZoneChange.isEnterChange ? userZoneChange.triggerZone : null;
        this.H = null;
        this.I = null;
        a(getWidth(), getHeight());
        postInvalidate();
    }
}
