package com.nostra13.universalimageloader.core;

import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory.Options;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.BitmapDisplayer;
import com.nostra13.universalimageloader.core.process.BitmapProcessor;

public class DisplayImageOptions$Builder {
    private boolean cacheInMemory = false;
    private boolean cacheOnDisk = false;
    private boolean considerExifParams = false;
    private Options decodingOptions = new Options();
    private int delayBeforeLoading = 0;
    private BitmapDisplayer displayer = DefaultConfigurationFactory.createBitmapDisplayer();
    private Object extraForDownloader = null;
    private Handler handler = null;
    private Drawable imageForEmptyUri = null;
    private Drawable imageOnFail = null;
    private Drawable imageOnLoading = null;
    private int imageResForEmptyUri = 0;
    private int imageResOnFail = 0;
    private int imageResOnLoading = 0;
    private ImageScaleType imageScaleType = ImageScaleType.IN_SAMPLE_POWER_OF_2;
    private boolean isSyncLoading = false;
    private BitmapProcessor postProcessor = null;
    private BitmapProcessor preProcessor = null;
    private boolean resetViewBeforeLoading = false;

    public DisplayImageOptions$Builder bitmapConfig(Config config) {
        if (config == null) {
            throw new IllegalArgumentException("bitmapConfig can't be null");
        }
        this.decodingOptions.inPreferredConfig = config;
        return this;
    }

    public DisplayImageOptions build() {
        return new DisplayImageOptions(this, null);
    }

    @Deprecated
    public DisplayImageOptions$Builder cacheInMemory() {
        this.cacheInMemory = true;
        return this;
    }

    public DisplayImageOptions$Builder cacheInMemory(boolean z) {
        this.cacheInMemory = z;
        return this;
    }

    @Deprecated
    public DisplayImageOptions$Builder cacheOnDisc() {
        return cacheOnDisk(true);
    }

    @Deprecated
    public DisplayImageOptions$Builder cacheOnDisc(boolean z) {
        return cacheOnDisk(z);
    }

    public DisplayImageOptions$Builder cacheOnDisk(boolean z) {
        this.cacheOnDisk = z;
        return this;
    }

    public DisplayImageOptions$Builder cloneFrom(DisplayImageOptions displayImageOptions) {
        this.imageResOnLoading = DisplayImageOptions.access$1900(displayImageOptions);
        this.imageResForEmptyUri = DisplayImageOptions.access$2000(displayImageOptions);
        this.imageResOnFail = DisplayImageOptions.access$2100(displayImageOptions);
        this.imageOnLoading = DisplayImageOptions.access$2200(displayImageOptions);
        this.imageForEmptyUri = DisplayImageOptions.access$2300(displayImageOptions);
        this.imageOnFail = DisplayImageOptions.access$2400(displayImageOptions);
        this.resetViewBeforeLoading = DisplayImageOptions.access$2500(displayImageOptions);
        this.cacheInMemory = DisplayImageOptions.access$2600(displayImageOptions);
        this.cacheOnDisk = DisplayImageOptions.access$2700(displayImageOptions);
        this.imageScaleType = DisplayImageOptions.access$2800(displayImageOptions);
        this.decodingOptions = DisplayImageOptions.access$2900(displayImageOptions);
        this.delayBeforeLoading = DisplayImageOptions.access$3000(displayImageOptions);
        this.considerExifParams = DisplayImageOptions.access$3100(displayImageOptions);
        this.extraForDownloader = DisplayImageOptions.access$3200(displayImageOptions);
        this.preProcessor = DisplayImageOptions.access$3300(displayImageOptions);
        this.postProcessor = DisplayImageOptions.access$3400(displayImageOptions);
        this.displayer = DisplayImageOptions.access$3500(displayImageOptions);
        this.handler = DisplayImageOptions.access$3600(displayImageOptions);
        this.isSyncLoading = DisplayImageOptions.access$3700(displayImageOptions);
        return this;
    }

    public DisplayImageOptions$Builder considerExifParams(boolean z) {
        this.considerExifParams = z;
        return this;
    }

    public DisplayImageOptions$Builder decodingOptions(Options options) {
        if (options == null) {
            throw new IllegalArgumentException("decodingOptions can't be null");
        }
        this.decodingOptions = options;
        return this;
    }

    public DisplayImageOptions$Builder delayBeforeLoading(int i) {
        this.delayBeforeLoading = i;
        return this;
    }

    public DisplayImageOptions$Builder displayer(BitmapDisplayer bitmapDisplayer) {
        if (bitmapDisplayer == null) {
            throw new IllegalArgumentException("displayer can't be null");
        }
        this.displayer = bitmapDisplayer;
        return this;
    }

    public DisplayImageOptions$Builder extraForDownloader(Object obj) {
        this.extraForDownloader = obj;
        return this;
    }

    public DisplayImageOptions$Builder handler(Handler handler) {
        this.handler = handler;
        return this;
    }

    public DisplayImageOptions$Builder imageScaleType(ImageScaleType imageScaleType) {
        this.imageScaleType = imageScaleType;
        return this;
    }

    public DisplayImageOptions$Builder postProcessor(BitmapProcessor bitmapProcessor) {
        this.postProcessor = bitmapProcessor;
        return this;
    }

    public DisplayImageOptions$Builder preProcessor(BitmapProcessor bitmapProcessor) {
        this.preProcessor = bitmapProcessor;
        return this;
    }

    public DisplayImageOptions$Builder resetViewBeforeLoading() {
        this.resetViewBeforeLoading = true;
        return this;
    }

    public DisplayImageOptions$Builder resetViewBeforeLoading(boolean z) {
        this.resetViewBeforeLoading = z;
        return this;
    }

    public DisplayImageOptions$Builder showImageForEmptyUri(int i) {
        this.imageResForEmptyUri = i;
        return this;
    }

    public DisplayImageOptions$Builder showImageForEmptyUri(Drawable drawable) {
        this.imageForEmptyUri = drawable;
        return this;
    }

    public DisplayImageOptions$Builder showImageOnFail(int i) {
        this.imageResOnFail = i;
        return this;
    }

    public DisplayImageOptions$Builder showImageOnFail(Drawable drawable) {
        this.imageOnFail = drawable;
        return this;
    }

    public DisplayImageOptions$Builder showImageOnLoading(int i) {
        this.imageResOnLoading = i;
        return this;
    }

    public DisplayImageOptions$Builder showImageOnLoading(Drawable drawable) {
        this.imageOnLoading = drawable;
        return this;
    }

    @Deprecated
    public DisplayImageOptions$Builder showStubImage(int i) {
        this.imageResOnLoading = i;
        return this;
    }

    DisplayImageOptions$Builder syncLoading(boolean z) {
        this.isSyncLoading = z;
        return this;
    }
}
