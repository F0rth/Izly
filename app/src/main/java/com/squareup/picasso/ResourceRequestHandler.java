package com.squareup.picasso;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import com.squareup.picasso.RequestHandler.Result;
import java.io.IOException;

class ResourceRequestHandler extends RequestHandler {
    private final Context context;

    ResourceRequestHandler(Context context) {
        this.context = context;
    }

    private static Bitmap decodeResource(Resources resources, int i, Request request) {
        Options createBitmapOptions = RequestHandler.createBitmapOptions(request);
        if (RequestHandler.requiresInSampleSize(createBitmapOptions)) {
            BitmapFactory.decodeResource(resources, i, createBitmapOptions);
            RequestHandler.calculateInSampleSize(request.targetWidth, request.targetHeight, createBitmapOptions, request);
        }
        return BitmapFactory.decodeResource(resources, i, createBitmapOptions);
    }

    public boolean canHandleRequest(Request request) {
        return request.resourceId != 0 ? true : "android.resource".equals(request.uri.getScheme());
    }

    public Result load(Request request, int i) throws IOException {
        Resources resources = Utils.getResources(this.context, request);
        return new Result(decodeResource(resources, Utils.getResourceId(resources, request), request), Picasso$LoadedFrom.DISK);
    }
}
