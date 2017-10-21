package fr.smoney.android.izly.ui.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Path.Direction;
import android.graphics.PorterDuff.Mode;
import android.graphics.RectF;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.PathShape;
import android.support.v8.renderscript.Allocation;
import android.support.v8.renderscript.Element;
import android.support.v8.renderscript.RenderScript;
import android.support.v8.renderscript.ScriptIntrinsicBlur;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;

import fr.smoney.android.izly.R;

public class BitmapSpotlightView extends View {
    private Bitmap a;
    private float b;
    private float c;
    private float d;
    private float e;
    private float f = 1.0f;
    private float g = 1.0f;
    private Paint h = new Paint();
    private a i;

    public interface a {
    }

    public BitmapSpotlightView(Context context) {
        super(context);
        a(new RectF(0.0f, 0.0f, 10.0f, 10.0f), 10.0f);
    }

    public BitmapSpotlightView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.BitmapSpotlightView, 0, 0);
        this.f = obtainStyledAttributes.getFloat(5, 1.0f);
        this.g = obtainStyledAttributes.getFloat(4, 1.0f);
        this.b = obtainStyledAttributes.getDimension(0, 0.0f) - 10.0f;
        this.c = obtainStyledAttributes.getDimension(1, 0.0f) - 10.0f;
        this.d = obtainStyledAttributes.getDimension(2, 10.0f);
        this.e = obtainStyledAttributes.getDimension(3, 10.0f);
        a(new RectF(0.0f, 0.0f, this.d, this.e), 15.0f);
        obtainStyledAttributes.recycle();
    }

    private void a(RectF rectF, float f) {
        if (!isInEditMode()) {
            ShapeDrawable shapeDrawable = new ShapeDrawable();
            Path path = new Path();
            path.addRoundRect(rectF, f, f, Direction.CW);
            shapeDrawable.setColorFilter(-65536, Mode.SRC);
            shapeDrawable.setShape(new PathShape(path, rectF.right, rectF.bottom));
            shapeDrawable.setBounds((int) 1101004800, (int) 1101004800, (int) rectF.right, (int) rectF.bottom);
            Bitmap createBitmap = Bitmap.createBitmap(shapeDrawable.getBounds().right + 20, shapeDrawable.getBounds().bottom + 20, Config.ARGB_8888);
            shapeDrawable.draw(new Canvas(createBitmap));
            Bitmap createBitmap2 = Bitmap.createBitmap(createBitmap);
            RenderScript create = RenderScript.create(getContext());
            Allocation createFromBitmap = Allocation.createFromBitmap(create, createBitmap);
            Allocation createFromBitmap2 = Allocation.createFromBitmap(create, createBitmap2);
            ScriptIntrinsicBlur create2 = ScriptIntrinsicBlur.create(create, Element.U8_4(create));
            create2.setInput(createFromBitmap);
            create2.setRadius(20.0f);
            create2.forEach(createFromBitmap2);
            createFromBitmap2.copyTo(createBitmap2);
            this.a = createBitmap2;
        }
    }

    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        getViewTreeObserver().addOnGlobalLayoutListener(new OnGlobalLayoutListener(this) {
            final /* synthetic */ BitmapSpotlightView a;

            {
                this.a = r1;
            }

            public final void onGlobalLayout() {
                if (this.a.i != null) {
                    this.a.i;
                }
                this.a.getViewTreeObserver().removeGlobalOnLayoutListener(this);
            }
        });
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float f = this.b;
        float f2 = this.c;
        int saveLayer = canvas.saveLayer(0.0f, 0.0f, (float) getWidth(), (float) getHeight(), null, 31);
        canvas.save();
        canvas.translate(f, f2);
        canvas.scale(this.g, this.f);
        canvas.drawBitmap(this.a, 0.0f, 0.0f, this.h);
        canvas.restore();
        canvas.drawColor(-872415232, Mode.SRC_OUT);
        canvas.restoreToCount(saveLayer);
    }

    public void setAnimationSetupCallback(a aVar) {
        this.i = aVar;
    }

    public void setMaskPosition(float f, float f2) {
        this.b = f;
        this.c = f2;
        invalidate();
    }

    public void setMaskScale(float f, float f2) {
        this.g = f;
        this.f = f2;
        invalidate();
    }

    public void setMaskX(float f) {
        this.b = f;
        invalidate();
    }

    public void setMaskY(float f) {
        this.c = f;
        invalidate();
    }
}
