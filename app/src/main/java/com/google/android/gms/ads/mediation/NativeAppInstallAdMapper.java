package com.google.android.gms.ads.mediation;

import com.google.android.gms.ads.formats.NativeAd.Image;
import java.util.List;

public abstract class NativeAppInstallAdMapper extends NativeAdMapper {
    private Image zzOo;
    private String zzxW;
    private List<Image> zzxX;
    private String zzxY;
    private String zzya;
    private double zzyb;
    private String zzyc;
    private String zzyd;

    public final String getBody() {
        return this.zzxY;
    }

    public final String getCallToAction() {
        return this.zzya;
    }

    public final String getHeadline() {
        return this.zzxW;
    }

    public final Image getIcon() {
        return this.zzOo;
    }

    public final List<Image> getImages() {
        return this.zzxX;
    }

    public final String getPrice() {
        return this.zzyd;
    }

    public final double getStarRating() {
        return this.zzyb;
    }

    public final String getStore() {
        return this.zzyc;
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

    public final void setIcon(Image image) {
        this.zzOo = image;
    }

    public final void setImages(List<Image> list) {
        this.zzxX = list;
    }

    public final void setPrice(String str) {
        this.zzyd = str;
    }

    public final void setStarRating(double d) {
        this.zzyb = d;
    }

    public final void setStore(String str) {
        this.zzyc = str;
    }
}
