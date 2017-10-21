package com.squareup.picasso;

public interface Picasso$RequestTransformer {
    public static final Picasso$RequestTransformer IDENTITY = new Picasso$RequestTransformer() {
        public final Request transformRequest(Request request) {
            return request;
        }
    };

    Request transformRequest(Request request);
}
