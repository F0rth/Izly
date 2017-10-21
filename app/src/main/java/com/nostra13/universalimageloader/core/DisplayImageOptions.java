package com.nostra13.universalimageloader.core;

import android.content.res.Resources;
import android.graphics.BitmapFactory.Options;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.BitmapDisplayer;
import com.nostra13.universalimageloader.core.process.BitmapProcessor;

public final class DisplayImageOptions {
    private final boolean cacheInMemory;
    private final boolean cacheOnDisk;
    private final boolean considerExifParams;
    private final Options decodingOptions;
    private final int delayBeforeLoading;
    private final BitmapDisplayer displayer;
    private final Object extraForDownloader;
    private final Handler handler;
    private final Drawable imageForEmptyUri;
    private final Drawable imageOnFail;
    private final Drawable imageOnLoading;
    private final int imageResForEmptyUri;
    private final int imageResOnFail;
    private final int imageResOnLoading;
    private final ImageScaleType imageScaleType;
    private final boolean isSyncLoading;
    private final BitmapProcessor postProcessor;
    private final BitmapProcessor preProcessor;
    private final boolean resetViewBeforeLoading;

    private DisplayImageOptions(Builder builder) {
        this.imageResOnLoading = Builder.access$000(builder);
        this.imageResForEmptyUri = Builder.access$100(builder);
        this.imageResOnFail = Builder.access$200(builder);
        this.imageOnLoading = Builder.access$300(builder);
        this.imageForEmptyUri = Builder.access$400(builder);
        this.imageOnFail = Builder.access$500(builder);
        this.resetViewBeforeLoading = Builder.access$600(builder);
        this.cacheInMemory = Builder.access$700(builder);
        this.cacheOnDisk = Builder.access$800(builder);
        this.imageScaleType = Builder.access$900(builder);
        this.decodingOptions = Builder.access$1000(builder);
        this.delayBeforeLoading = Builder.access$1100(builder);
        this.considerExifParams = Builder.access$1200(builder);
        this.extraForDownloader = Builder.access$1300(builder);
        this.preProcessor = Builder.access$1400(builder);
        this.postProcessor = Builder.access$1500(builder);
        this.displayer = Builder.access$1600(builder);
        this.handler = Builder.access$1700(builder);
        this.isSyncLoading = Builder.access$1800(builder);
    }

    public static DisplayImageOptions createSimple() {
        return new Builder().build();
    }

    public final Options getDecodingOptions() {
        return this.decodingOptions;
    }

    public final int getDelayBeforeLoading() {
        return this.delayBeforeLoading;
    }

    public final BitmapDisplayer getDisplayer() {
        return this.displayer;
    }

    public final Object getExtraForDownloader() {
        return this.extraForDownloader;
    }

    public final Handler getHandler() {
        return this.handler;
    }

    public final Drawable getImageForEmptyUri(Resources resources) {
        return this.imageResForEmptyUri != 0 ? resources.getDrawable(this.imageResForEmptyUri) : this.imageForEmptyUri;
    }

    public final Drawable getImageOnFail(Resources resources) {
        return this.imageResOnFail != 0 ? resources.getDrawable(this.imageResOnFail) : this.imageOnFail;
    }

    public final Drawable getImageOnLoading(Resources resources) {
        return this.imageResOnLoading != 0 ? resources.getDrawable(this.imageResOnLoading) : this.imageOnLoading;
    }

    public final ImageScaleType getImageScaleType() {
        return this.imageScaleType;
    }

    public final BitmapProcessor getPostProcessor() {
        return this.postProcessor;
    }

    public final BitmapProcessor getPreProcessor() {
        return this.preProcessor;
    }

    public final boolean isCacheInMemory() {
        return this.cacheInMemory;
    }

    public final boolean isCacheOnDisk() {
        return this.cacheOnDisk;
    }

    public final boolean isConsiderExifParams() {
        return this.considerExifParams;
    }

    public final boolean isResetViewBeforeLoading() {
        return this.resetViewBeforeLoading;
    }

    final boolean isSyncLoading() {
        return this.isSyncLoading;
    }

    public final boolean shouldDelayBeforeLoading() {
        return this.delayBeforeLoading > 0;
    }

    public final boolean shouldPostProcess() {
        return this.postProcessor != null;
    }

    public final boolean shouldPreProcess() {
        return this.preProcessor != null;
    }

    public final boolean shouldShowImageForEmptyUri() {
        return (this.imageForEmptyUri == null && this.imageResForEmptyUri == 0) ? false : true;
    }

    public final boolean shouldShowImageOnFail() {
        return (this.imageOnFail == null && this.imageResOnFail == 0) ? false : true;
    }

    public final boolean shouldShowImageOnLoading() {
        return (this.imageOnLoading == null && this.imageResOnLoading == 0) ? false : true;
    }
}
