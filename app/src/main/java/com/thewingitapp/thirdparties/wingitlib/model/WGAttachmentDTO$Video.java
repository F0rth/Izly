package com.thewingitapp.thirdparties.wingitlib.model;

public enum WGAttachmentDTO$Video {
    HLS_VIDEO_URL("HLSVideoUrl");
    
    String name;

    private WGAttachmentDTO$Video(String str) {
        this.name = str;
    }

    public final String value() {
        return this.name;
    }
}
