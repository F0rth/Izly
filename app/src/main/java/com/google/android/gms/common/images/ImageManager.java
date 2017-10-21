package com.google.android.gms.common.images;

import android.annotation.TargetApi;
import android.app.ActivityManager;
import android.content.ComponentCallbacks2;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.ParcelFileDescriptor;
import android.os.ResultReceiver;
import android.os.SystemClock;
import android.support.v4.util.LruCache;
import android.util.Log;
import android.widget.ImageView;
import com.google.android.gms.common.annotation.KeepName;
import com.google.android.gms.internal.zzmd;
import com.google.android.gms.internal.zzne;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public final class ImageManager {
    private static HashSet<Uri> zzajA = new HashSet();
    private static ImageManager zzajB;
    private static ImageManager zzajC;
    private static final Object zzajz = new Object();
    private final Context mContext;
    private final Handler mHandler = new Handler(Looper.getMainLooper());
    private final ExecutorService zzajD = Executors.newFixedThreadPool(4);
    private final zzb zzajE;
    private final zzmd zzajF;
    private final Map<zza, ImageReceiver> zzajG;
    private final Map<Uri, ImageReceiver> zzajH;
    private final Map<Uri, Long> zzajI;

    @KeepName
    final class ImageReceiver extends ResultReceiver {
        private final Uri mUri;
        private final ArrayList<zza> zzajJ = new ArrayList();
        final /* synthetic */ ImageManager zzajK;

        ImageReceiver(ImageManager imageManager, Uri uri) {
            this.zzajK = imageManager;
            super(new Handler(Looper.getMainLooper()));
            this.mUri = uri;
        }

        public final void onReceiveResult(int i, Bundle bundle) {
            this.zzajK.zzajD.execute(new zzc(this.zzajK, this.mUri, (ParcelFileDescriptor) bundle.getParcelable("com.google.android.gms.extra.fileDescriptor")));
        }

        public final void zzb(zza com_google_android_gms_common_images_zza) {
            com.google.android.gms.common.internal.zzb.zzcD("ImageReceiver.addImageRequest() must be called in the main thread");
            this.zzajJ.add(com_google_android_gms_common_images_zza);
        }

        public final void zzc(zza com_google_android_gms_common_images_zza) {
            com.google.android.gms.common.internal.zzb.zzcD("ImageReceiver.removeImageRequest() must be called in the main thread");
            this.zzajJ.remove(com_google_android_gms_common_images_zza);
        }

        public final void zzqm() {
            Intent intent = new Intent("com.google.android.gms.common.images.LOAD_IMAGE");
            intent.putExtra("com.google.android.gms.extras.uri", this.mUri);
            intent.putExtra("com.google.android.gms.extras.resultReceiver", this);
            intent.putExtra("com.google.android.gms.extras.priority", 3);
            this.zzajK.mContext.sendBroadcast(intent);
        }
    }

    public interface OnImageLoadedListener {
        void onImageLoaded(Uri uri, Drawable drawable, boolean z);
    }

    @TargetApi(11)
    static final class zza {
        static int zza(ActivityManager activityManager) {
            return activityManager.getLargeMemoryClass();
        }
    }

    static final class zzb extends LruCache<zza, Bitmap> {
        public zzb(Context context) {
            super(zzas(context));
        }

        @TargetApi(11)
        private static int zzas(Context context) {
            ActivityManager activityManager = (ActivityManager) context.getSystemService("activity");
            int memoryClass = (((context.getApplicationInfo().flags & 1048576) != 0 ? 1 : null) == null || !zzne.zzsd()) ? activityManager.getMemoryClass() : zza.zza(activityManager);
            return (int) (((float) (memoryClass * 1048576)) * 0.33f);
        }

        protected final /* synthetic */ void entryRemoved(boolean z, Object obj, Object obj2, Object obj3) {
            zza(z, (zza) obj, (Bitmap) obj2, (Bitmap) obj3);
        }

        protected final /* synthetic */ int sizeOf(Object obj, Object obj2) {
            return zza((zza) obj, (Bitmap) obj2);
        }

        protected final int zza(zza com_google_android_gms_common_images_zza_zza, Bitmap bitmap) {
            return bitmap.getHeight() * bitmap.getRowBytes();
        }

        protected final void zza(boolean z, zza com_google_android_gms_common_images_zza_zza, Bitmap bitmap, Bitmap bitmap2) {
            super.entryRemoved(z, com_google_android_gms_common_images_zza_zza, bitmap, bitmap2);
        }
    }

    final class zzc implements Runnable {
        private final Uri mUri;
        final /* synthetic */ ImageManager zzajK;
        private final ParcelFileDescriptor zzajL;

        public zzc(ImageManager imageManager, Uri uri, ParcelFileDescriptor parcelFileDescriptor) {
            this.zzajK = imageManager;
            this.mUri = uri;
            this.zzajL = parcelFileDescriptor;
        }

        public final void run() {
            Bitmap bitmap = null;
            boolean z = false;
            com.google.android.gms.common.internal.zzb.zzcE("LoadBitmapFromDiskRunnable can't be executed in the main thread");
            if (this.zzajL != null) {
                try {
                    bitmap = BitmapFactory.decodeFileDescriptor(this.zzajL.getFileDescriptor());
                } catch (Throwable e) {
                    Log.e("ImageManager", "OOM while loading bitmap for uri: " + this.mUri, e);
                    z = true;
                }
                try {
                    this.zzajL.close();
                } catch (Throwable e2) {
                    Log.e("ImageManager", "closed failed", e2);
                }
            }
            CountDownLatch countDownLatch = new CountDownLatch(1);
            this.zzajK.mHandler.post(new zzf(this.zzajK, this.mUri, bitmap, z, countDownLatch));
            try {
                countDownLatch.await();
            } catch (InterruptedException e3) {
                Log.w("ImageManager", "Latch interrupted while posting " + this.mUri);
            }
        }
    }

    final class zzd implements Runnable {
        final /* synthetic */ ImageManager zzajK;
        private final zza zzajM;

        public zzd(ImageManager imageManager, zza com_google_android_gms_common_images_zza) {
            this.zzajK = imageManager;
            this.zzajM = com_google_android_gms_common_images_zza;
        }

        public final void run() {
            com.google.android.gms.common.internal.zzb.zzcD("LoadImageRunnable must be executed on the main thread");
            ImageReceiver imageReceiver = (ImageReceiver) this.zzajK.zzajG.get(this.zzajM);
            if (imageReceiver != null) {
                this.zzajK.zzajG.remove(this.zzajM);
                imageReceiver.zzc(this.zzajM);
            }
            zza com_google_android_gms_common_images_zza_zza = this.zzajM.zzajO;
            if (com_google_android_gms_common_images_zza_zza.uri == null) {
                this.zzajM.zza(this.zzajK.mContext, this.zzajK.zzajF, true);
                return;
            }
            Bitmap zza = this.zzajK.zza(com_google_android_gms_common_images_zza_zza);
            if (zza != null) {
                this.zzajM.zza(this.zzajK.mContext, zza, true);
                return;
            }
            Long l = (Long) this.zzajK.zzajI.get(com_google_android_gms_common_images_zza_zza.uri);
            if (l != null) {
                if (SystemClock.elapsedRealtime() - l.longValue() < 3600000) {
                    this.zzajM.zza(this.zzajK.mContext, this.zzajK.zzajF, true);
                    return;
                }
                this.zzajK.zzajI.remove(com_google_android_gms_common_images_zza_zza.uri);
            }
            this.zzajM.zza(this.zzajK.mContext, this.zzajK.zzajF);
            imageReceiver = (ImageReceiver) this.zzajK.zzajH.get(com_google_android_gms_common_images_zza_zza.uri);
            if (imageReceiver == null) {
                imageReceiver = new ImageReceiver(this.zzajK, com_google_android_gms_common_images_zza_zza.uri);
                this.zzajK.zzajH.put(com_google_android_gms_common_images_zza_zza.uri, imageReceiver);
            }
            imageReceiver.zzb(this.zzajM);
            if (!(this.zzajM instanceof com.google.android.gms.common.images.zza.zzc)) {
                this.zzajK.zzajG.put(this.zzajM, imageReceiver);
            }
            synchronized (ImageManager.zzajz) {
                if (!ImageManager.zzajA.contains(com_google_android_gms_common_images_zza_zza.uri)) {
                    ImageManager.zzajA.add(com_google_android_gms_common_images_zza_zza.uri);
                    imageReceiver.zzqm();
                }
            }
        }
    }

    @TargetApi(14)
    static final class zze implements ComponentCallbacks2 {
        private final zzb zzajE;

        public zze(zzb com_google_android_gms_common_images_ImageManager_zzb) {
            this.zzajE = com_google_android_gms_common_images_ImageManager_zzb;
        }

        public final void onConfigurationChanged(Configuration configuration) {
        }

        public final void onLowMemory() {
            this.zzajE.evictAll();
        }

        public final void onTrimMemory(int i) {
            if (i >= 60) {
                this.zzajE.evictAll();
            } else if (i >= 20) {
                this.zzajE.trimToSize(this.zzajE.size() / 2);
            }
        }
    }

    final class zzf implements Runnable {
        private final Bitmap mBitmap;
        private final Uri mUri;
        final /* synthetic */ ImageManager zzajK;
        private boolean zzajN;
        private final CountDownLatch zzpJ;

        public zzf(ImageManager imageManager, Uri uri, Bitmap bitmap, boolean z, CountDownLatch countDownLatch) {
            this.zzajK = imageManager;
            this.mUri = uri;
            this.mBitmap = bitmap;
            this.zzajN = z;
            this.zzpJ = countDownLatch;
        }

        private void zza(ImageReceiver imageReceiver, boolean z) {
            ArrayList zza = imageReceiver.zzajJ;
            int size = zza.size();
            for (int i = 0; i < size; i++) {
                zza com_google_android_gms_common_images_zza = (zza) zza.get(i);
                if (z) {
                    com_google_android_gms_common_images_zza.zza(this.zzajK.mContext, this.mBitmap, false);
                } else {
                    this.zzajK.zzajI.put(this.mUri, Long.valueOf(SystemClock.elapsedRealtime()));
                    com_google_android_gms_common_images_zza.zza(this.zzajK.mContext, this.zzajK.zzajF, false);
                }
                if (!(com_google_android_gms_common_images_zza instanceof com.google.android.gms.common.images.zza.zzc)) {
                    this.zzajK.zzajG.remove(com_google_android_gms_common_images_zza);
                }
            }
        }

        public final void run() {
            com.google.android.gms.common.internal.zzb.zzcD("OnBitmapLoadedRunnable must be executed in the main thread");
            boolean z = this.mBitmap != null;
            if (this.zzajK.zzajE != null) {
                if (this.zzajN) {
                    this.zzajK.zzajE.evictAll();
                    System.gc();
                    this.zzajN = false;
                    this.zzajK.mHandler.post(this);
                    return;
                } else if (z) {
                    this.zzajK.zzajE.put(new zza(this.mUri), this.mBitmap);
                }
            }
            ImageReceiver imageReceiver = (ImageReceiver) this.zzajK.zzajH.remove(this.mUri);
            if (imageReceiver != null) {
                zza(imageReceiver, z);
            }
            this.zzpJ.countDown();
            synchronized (ImageManager.zzajz) {
                ImageManager.zzajA.remove(this.mUri);
            }
        }
    }

    private ImageManager(Context context, boolean z) {
        this.mContext = context.getApplicationContext();
        if (z) {
            this.zzajE = new zzb(this.mContext);
            if (zzne.zzsg()) {
                zzqj();
            }
        } else {
            this.zzajE = null;
        }
        this.zzajF = new zzmd();
        this.zzajG = new HashMap();
        this.zzajH = new HashMap();
        this.zzajI = new HashMap();
    }

    public static ImageManager create(Context context) {
        return zzc(context, false);
    }

    private Bitmap zza(zza com_google_android_gms_common_images_zza_zza) {
        return this.zzajE == null ? null : (Bitmap) this.zzajE.get(com_google_android_gms_common_images_zza_zza);
    }

    public static ImageManager zzc(Context context, boolean z) {
        if (z) {
            if (zzajC == null) {
                zzajC = new ImageManager(context, true);
            }
            return zzajC;
        }
        if (zzajB == null) {
            zzajB = new ImageManager(context, false);
        }
        return zzajB;
    }

    @TargetApi(14)
    private void zzqj() {
        this.mContext.registerComponentCallbacks(new zze(this.zzajE));
    }

    public final void loadImage(ImageView imageView, int i) {
        zza(new com.google.android.gms.common.images.zza.zzb(imageView, i));
    }

    public final void loadImage(ImageView imageView, Uri uri) {
        zza(new com.google.android.gms.common.images.zza.zzb(imageView, uri));
    }

    public final void loadImage(ImageView imageView, Uri uri, int i) {
        zza com_google_android_gms_common_images_zza_zzb = new com.google.android.gms.common.images.zza.zzb(imageView, uri);
        com_google_android_gms_common_images_zza_zzb.zzbM(i);
        zza(com_google_android_gms_common_images_zza_zzb);
    }

    public final void loadImage(OnImageLoadedListener onImageLoadedListener, Uri uri) {
        zza(new com.google.android.gms.common.images.zza.zzc(onImageLoadedListener, uri));
    }

    public final void loadImage(OnImageLoadedListener onImageLoadedListener, Uri uri, int i) {
        zza com_google_android_gms_common_images_zza_zzc = new com.google.android.gms.common.images.zza.zzc(onImageLoadedListener, uri);
        com_google_android_gms_common_images_zza_zzc.zzbM(i);
        zza(com_google_android_gms_common_images_zza_zzc);
    }

    public final void zza(zza com_google_android_gms_common_images_zza) {
        com.google.android.gms.common.internal.zzb.zzcD("ImageManager.loadImage() must be called in the main thread");
        new zzd(this, com_google_android_gms_common_images_zza).run();
    }
}
