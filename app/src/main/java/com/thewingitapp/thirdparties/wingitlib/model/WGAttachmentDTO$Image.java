package com.thewingitapp.thirdparties.wingitlib.model;

public enum WGAttachmentDTO$Image {
    VERTICAL_IMAGE_URL("VerticalImageUrl"),
    IMAGE_URL("ImageUrl"),
    SQUARE_IMAGE_URL("SquareImageUrl"),
    HORIZONTAL_IMAGE_URL("HorizontalImageUrl");
    
    String name;

    private WGAttachmentDTO$Image(String str) {
        this.name = str;
    }

    public final String value() {
        return this.name;
    }
}
