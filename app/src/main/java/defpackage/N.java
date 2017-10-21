package defpackage;

import android.content.Context;
import android.hardware.Camera;
import android.hardware.Camera.CameraInfo;
import android.hardware.Camera.Parameters;
import android.os.Build.VERSION;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;

public final class N extends SurfaceView implements Callback {
    Camera a;
    Boolean b = Boolean.valueOf(false);
    private SurfaceHolder c = getHolder();
    private v d;

    public N(Context context, v vVar) {
        super(context);
        this.d = vVar;
        this.c.addCallback(this);
        if (VERSION.SDK_INT < 11) {
            this.c.setType(3);
        }
    }

    private Camera a() {
        if (VERSION.SDK_INT <= 8) {
            return Camera.open();
        }
        CameraInfo cameraInfo = new CameraInfo();
        int numberOfCameras = Camera.getNumberOfCameras();
        Camera camera = null;
        int i = 0;
        while (i < numberOfCameras) {
            try {
                camera = Camera.open(i);
                Camera.getCameraInfo(i, cameraInfo);
                if (cameraInfo.facing != 1) {
                    return camera;
                }
                this.b = Boolean.valueOf(true);
                return camera;
            } catch (RuntimeException e) {
                RuntimeException runtimeException = e;
                Camera camera2 = camera;
                Log.e("it.scanpay", "Camera failed to open: " + runtimeException.getLocalizedMessage());
                i++;
                camera = camera2;
            }
        }
        return camera;
    }

    public final void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i2, int i3) {
        if (this.a != null) {
            this.a.startPreview();
        }
        if (this.d != null) {
            this.d.a();
        }
    }

    public final void surfaceCreated(SurfaceHolder surfaceHolder) {
        try {
            if (this.a == null) {
                this.a = a();
            }
            Parameters parameters = this.a.getParameters();
            this.a.setDisplayOrientation(90);
            parameters.setPreviewSize(640, 480);
            if (parameters.getSupportedFocusModes().indexOf("auto") != -1) {
                parameters.setFocusMode("auto");
            }
            parameters.set("orientation", "portrait");
            parameters.set("rotation", 90);
            this.a.setParameters(parameters);
            this.a.setPreviewDisplay(this.c);
        } catch (Exception e) {
            Log.e("it.scanpay", "SurfaceCreated error : " + e.toString());
        }
    }

    public final void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        if (this.a != null) {
            this.a.cancelAutoFocus();
            this.a.setPreviewCallback(null);
            this.a.stopPreview();
            this.a.release();
            this.a = null;
        }
    }
}
