package com.google.tagmanager;

import android.net.Uri;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

class PreviewManager {
    static final String BASE_DEBUG_QUERY = "&gtm_debug=x";
    private static final String CONTAINER_BASE_PATTERN = "^tagmanager.c.\\S+:\\/\\/preview\\/p\\?id=\\S+&";
    private static final String CONTAINER_DEBUG_STRING_PATTERN = ".*?&gtm_debug=x$";
    private static final String CONTAINER_PREVIEW_EXIT_URL_PATTERN = "^tagmanager.c.\\S+:\\/\\/preview\\/p\\?id=\\S+&gtm_preview=$";
    private static final String CONTAINER_PREVIEW_URL_PATTERN = "^tagmanager.c.\\S+:\\/\\/preview\\/p\\?id=\\S+&gtm_auth=\\S+&gtm_preview=\\d+(&gtm_debug=x)?$";
    static final String CTFE_URL_PATH_PREFIX = "/r?";
    private static PreviewManager sInstance;
    private volatile String mCTFEUrlPath;
    private volatile String mCTFEUrlQuery;
    private volatile String mContainerId;
    private volatile PreviewMode mPreviewMode;

    enum PreviewMode {
        NONE,
        CONTAINER,
        CONTAINER_DEBUG
    }

    PreviewManager() {
        clear();
    }

    private String getContainerId(String str) {
        return str.split("&")[0].split("=")[1];
    }

    static PreviewManager getInstance() {
        PreviewManager previewManager;
        synchronized (PreviewManager.class) {
            try {
                if (sInstance == null) {
                    sInstance = new PreviewManager();
                }
                previewManager = sInstance;
            } catch (Throwable th) {
                Class cls = PreviewManager.class;
            }
        }
        return previewManager;
    }

    private String getQueryWithoutDebugParameter(Uri uri) {
        return uri.getQuery().replace(BASE_DEBUG_QUERY, "");
    }

    void clear() {
        this.mPreviewMode = PreviewMode.NONE;
        this.mCTFEUrlPath = null;
        this.mContainerId = null;
        this.mCTFEUrlQuery = null;
    }

    String getCTFEUrlDebugQuery() {
        return this.mCTFEUrlQuery;
    }

    String getCTFEUrlPath() {
        return this.mCTFEUrlPath;
    }

    String getContainerId() {
        return this.mContainerId;
    }

    PreviewMode getPreviewMode() {
        return this.mPreviewMode;
    }

    boolean setPreviewData(Uri uri) {
        boolean z = true;
        synchronized (this) {
            try {
                String decode = URLDecoder.decode(uri.toString(), "UTF-8");
                if (decode.matches(CONTAINER_PREVIEW_URL_PATTERN)) {
                    Log.v("Container preview url: " + decode);
                    if (decode.matches(CONTAINER_DEBUG_STRING_PATTERN)) {
                        this.mPreviewMode = PreviewMode.CONTAINER_DEBUG;
                    } else {
                        this.mPreviewMode = PreviewMode.CONTAINER;
                    }
                    this.mCTFEUrlQuery = getQueryWithoutDebugParameter(uri);
                    if (this.mPreviewMode == PreviewMode.CONTAINER || this.mPreviewMode == PreviewMode.CONTAINER_DEBUG) {
                        this.mCTFEUrlPath = new StringBuilder(CTFE_URL_PATH_PREFIX).append(this.mCTFEUrlQuery).toString();
                    }
                    this.mContainerId = getContainerId(this.mCTFEUrlQuery);
                } else {
                    if (!decode.matches(CONTAINER_PREVIEW_EXIT_URL_PATTERN)) {
                        Log.w("Invalid preview uri: " + decode);
                        z = false;
                    } else if (getContainerId(uri.getQuery()).equals(this.mContainerId)) {
                        Log.v("Exit preview mode for container: " + this.mContainerId);
                        this.mPreviewMode = PreviewMode.NONE;
                        this.mCTFEUrlPath = null;
                    } else {
                        z = false;
                    }
                }
            } catch (UnsupportedEncodingException e) {
                z = false;
            }
        }
        return z;
    }
}
