package com.google.android.gms.ads.mediation;

import com.google.android.gms.ads.formats.NativeAd.Image;
import java.util.List;

public abstract class NativeContentAdMapper extends NativeAdMapper {
    private Image zzOp;
    private String zzxW;
    private List<Image> zzxX;
    private String zzxY;
    private String zzya;
    private String zzyh;

    public final String getAdvertiser() {
        return this.zzyh;
    }

    public final String getBody() {
        return this.zzxY;
    }

    public final String getCallToAction() {
        return this.zzya;
    }

    public final String getHeadline() {
        return this.zzxW;
    }

    public final List<Image> getImages() {
        return this.zzxX;
    }

    public final Image getLogo() {
        return this.zzOp;
    }

    public final void setAdvertiser(String str) {
        this.zzyh = str;
    }

    public final void setBody(String str) {
        this.zzxY = str;
    }

    public final void setCallToAction(String str) {
        this.zzya = str;
    }

    public final void setHeadline(String str) {
        this.zzxW = str;
    }

    public final void setImages(List<Image> list) {
        this.zzxX = list;
    }

    public final void setLogo(Image image) {
        this.zzOp = image;
    }
}
