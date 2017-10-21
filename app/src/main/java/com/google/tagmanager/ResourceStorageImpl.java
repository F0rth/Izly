package com.google.tagmanager;

import android.content.Context;
import android.content.res.AssetManager;
import com.google.android.gms.common.util.VisibleForTesting;
import com.google.tagmanager.ResourceUtil.ExpandedResource;
import com.google.tagmanager.proto.Resource.ResourceWithMetadata;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.json.JSONException;

class ResourceStorageImpl implements ResourceStorage {
    private static final String SAVED_RESOURCE_FILENAME_PREFIX = "resource_";
    private static final String SAVED_RESOURCE_SUB_DIR = "google_tagmanager";
    private LoadCallback<ResourceWithMetadata> mCallback;
    private final String mContainerId;
    private final Context mContext;
    private final ExecutorService mExecutor = Executors.newSingleThreadExecutor();

    ResourceStorageImpl(Context context, String str) {
        this.mContext = context;
        this.mContainerId = str;
    }

    private String stringFromInputStream(InputStream inputStream) throws IOException {
        Writer stringWriter = new StringWriter();
        char[] cArr = new char[1024];
        Reader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
        while (true) {
            int read = bufferedReader.read(cArr);
            if (read == -1) {
                return stringWriter.toString();
            }
            stringWriter.write(cArr, 0, read);
        }
    }

    public void close() {
        synchronized (this) {
            this.mExecutor.shutdown();
        }
    }

    @VisibleForTesting
    File getResourceFile() {
        return new File(this.mContext.getDir(SAVED_RESOURCE_SUB_DIR, 0), new StringBuilder(SAVED_RESOURCE_FILENAME_PREFIX).append(this.mContainerId).toString());
    }

    public ExpandedResource loadExpandedResourceFromJsonAsset(String str) {
        InputStream open;
        Throwable th;
        Object e;
        ExpandedResource expandedResource = null;
        Log.v("loading default container from " + str);
        AssetManager assets = this.mContext.getAssets();
        if (assets == null) {
            Log.w("Looking for default JSON container in package, but no assets were found.");
        } else {
            try {
                open = assets.open(str);
                try {
                    expandedResource = JsonUtils.expandedResourceFromJsonString(stringFromInputStream(open));
                    if (open != null) {
                        try {
                            open.close();
                        } catch (IOException e2) {
                        }
                    }
                } catch (IOException e3) {
                    try {
                        Log.w("No asset file: " + str + " found (or errors reading it).");
                        if (open != null) {
                            try {
                                open.close();
                            } catch (IOException e4) {
                            }
                        }
                        return expandedResource;
                    } catch (Throwable th2) {
                        th = th2;
                        if (open != null) {
                            try {
                                open.close();
                            } catch (IOException e5) {
                            }
                        }
                        throw th;
                    }
                } catch (JSONException e6) {
                    e = e6;
                    try {
                        Log.w("Error parsing JSON file" + str + " : " + e);
                        if (open != null) {
                            try {
                                open.close();
                            } catch (IOException e7) {
                            }
                        }
                        return expandedResource;
                    } catch (Throwable th3) {
                        th = th3;
                        if (open != null) {
                            open.close();
                        }
                        throw th;
                    }
                }
            } catch (IOException e8) {
                open = expandedResource;
                Log.w("No asset file: " + str + " found (or errors reading it).");
                if (open != null) {
                    open.close();
                }
                return expandedResource;
            } catch (JSONException e9) {
                e = e9;
                open = expandedResource;
                Log.w("Error parsing JSON file" + str + " : " + e);
                if (open != null) {
                    open.close();
                }
                return expandedResource;
            } catch (Throwable th4) {
                ExpandedResource expandedResource2 = expandedResource;
                th = th4;
                open = expandedResource2;
                if (open != null) {
                    open.close();
                }
                throw th;
            }
        }
        return expandedResource;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public com.google.analytics.containertag.proto.Serving.Resource loadResourceFromContainerAsset(java.lang.String r6) {
        /*
        r5 = this;
        r0 = 0;
        r1 = new java.lang.StringBuilder;
        r2 = "Loading default container from ";
        r1.<init>(r2);
        r1 = r1.append(r6);
        r1 = r1.toString();
        com.google.tagmanager.Log.v(r1);
        r1 = r5.mContext;
        r1 = r1.getAssets();
        if (r1 != 0) goto L_0x0021;
    L_0x001b:
        r1 = "No assets found in package";
        com.google.tagmanager.Log.e(r1);
    L_0x0020:
        return r0;
    L_0x0021:
        r2 = r1.open(r6);	 Catch:{ IOException -> 0x004c }
        r1 = new java.io.ByteArrayOutputStream;	 Catch:{ IOException -> 0x0066 }
        r1.<init>();	 Catch:{ IOException -> 0x0066 }
        com.google.tagmanager.ResourceUtil.copyStream(r2, r1);	 Catch:{ IOException -> 0x0066 }
        r1 = r1.toByteArray();	 Catch:{ IOException -> 0x0066 }
        r1 = com.google.analytics.containertag.proto.Serving.Resource.parseFrom(r1);	 Catch:{ IOException -> 0x0066 }
        r3 = new java.lang.StringBuilder;	 Catch:{ IOException -> 0x0066 }
        r4 = "Parsed default container: ";
        r3.<init>(r4);	 Catch:{ IOException -> 0x0066 }
        r3 = r3.append(r1);	 Catch:{ IOException -> 0x0066 }
        r3 = r3.toString();	 Catch:{ IOException -> 0x0066 }
        com.google.tagmanager.Log.v(r3);	 Catch:{ IOException -> 0x0066 }
        r2.close();	 Catch:{ IOException -> 0x0084 }
    L_0x004a:
        r0 = r1;
        goto L_0x0020;
    L_0x004c:
        r1 = move-exception;
        r1 = new java.lang.StringBuilder;
        r2 = "No asset file: ";
        r1.<init>(r2);
        r1 = r1.append(r6);
        r2 = " found.";
        r1 = r1.append(r2);
        r1 = r1.toString();
        com.google.tagmanager.Log.w(r1);
        goto L_0x0020;
    L_0x0066:
        r1 = move-exception;
        r1 = new java.lang.StringBuilder;	 Catch:{ all -> 0x007f }
        r3 = "Error when parsing: ";
        r1.<init>(r3);	 Catch:{ all -> 0x007f }
        r1 = r1.append(r6);	 Catch:{ all -> 0x007f }
        r1 = r1.toString();	 Catch:{ all -> 0x007f }
        com.google.tagmanager.Log.w(r1);	 Catch:{ all -> 0x007f }
        r2.close();	 Catch:{ IOException -> 0x007d }
        goto L_0x0020;
    L_0x007d:
        r1 = move-exception;
        goto L_0x0020;
    L_0x007f:
        r0 = move-exception;
        r2.close();	 Catch:{ IOException -> 0x0086 }
    L_0x0083:
        throw r0;
    L_0x0084:
        r0 = move-exception;
        goto L_0x004a;
    L_0x0086:
        r1 = move-exception;
        goto L_0x0083;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.tagmanager.ResourceStorageImpl.loadResourceFromContainerAsset(java.lang.String):com.google.analytics.containertag.proto.Serving$Resource");
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    @com.google.android.gms.common.util.VisibleForTesting
    void loadResourceFromDisk() {
        /*
        r3 = this;
        r0 = r3.mCallback;
        if (r0 != 0) goto L_0x000c;
    L_0x0004:
        r0 = new java.lang.IllegalStateException;
        r1 = "callback must be set before execute";
        r0.<init>(r1);
        throw r0;
    L_0x000c:
        r0 = r3.mCallback;
        r0.startLoad();
        r0 = "Start loading resource from disk ...";
        com.google.tagmanager.Log.v(r0);
        r0 = com.google.tagmanager.PreviewManager.getInstance();
        r0 = r0.getPreviewMode();
        r1 = com.google.tagmanager.PreviewManager.PreviewMode.CONTAINER;
        if (r0 == r1) goto L_0x002e;
    L_0x0022:
        r0 = com.google.tagmanager.PreviewManager.getInstance();
        r0 = r0.getPreviewMode();
        r1 = com.google.tagmanager.PreviewManager.PreviewMode.CONTAINER_DEBUG;
        if (r0 != r1) goto L_0x0046;
    L_0x002e:
        r0 = r3.mContainerId;
        r1 = com.google.tagmanager.PreviewManager.getInstance();
        r1 = r1.getContainerId();
        r0 = r0.equals(r1);
        if (r0 == 0) goto L_0x0046;
    L_0x003e:
        r0 = r3.mCallback;
        r1 = com.google.tagmanager.LoadCallback.Failure.NOT_AVAILABLE;
        r0.onFailure(r1);
    L_0x0045:
        return;
    L_0x0046:
        r1 = new java.io.FileInputStream;	 Catch:{ FileNotFoundException -> 0x006d }
        r0 = r3.getResourceFile();	 Catch:{ FileNotFoundException -> 0x006d }
        r1.<init>(r0);	 Catch:{ FileNotFoundException -> 0x006d }
        r0 = new java.io.ByteArrayOutputStream;	 Catch:{ IOException -> 0x0082 }
        r0.<init>();	 Catch:{ IOException -> 0x0082 }
        com.google.tagmanager.ResourceUtil.copyStream(r1, r0);	 Catch:{ IOException -> 0x0082 }
        r2 = r3.mCallback;	 Catch:{ IOException -> 0x0082 }
        r0 = r0.toByteArray();	 Catch:{ IOException -> 0x0082 }
        r0 = com.google.tagmanager.proto.Resource.ResourceWithMetadata.parseFrom(r0);	 Catch:{ IOException -> 0x0082 }
        r2.onSuccess(r0);	 Catch:{ IOException -> 0x0082 }
        r1.close();	 Catch:{ IOException -> 0x007b }
    L_0x0067:
        r0 = "Load resource from disk finished.";
        com.google.tagmanager.Log.v(r0);
        goto L_0x0045;
    L_0x006d:
        r0 = move-exception;
        r0 = "resource not on disk";
        com.google.tagmanager.Log.d(r0);
        r0 = r3.mCallback;
        r1 = com.google.tagmanager.LoadCallback.Failure.NOT_AVAILABLE;
        r0.onFailure(r1);
        goto L_0x0045;
    L_0x007b:
        r0 = move-exception;
        r0 = "error closing stream for reading resource from disk";
        com.google.tagmanager.Log.w(r0);
        goto L_0x0067;
    L_0x0082:
        r0 = move-exception;
        r0 = "error reading resource from disk";
        com.google.tagmanager.Log.w(r0);	 Catch:{ all -> 0x009a }
        r0 = r3.mCallback;	 Catch:{ all -> 0x009a }
        r2 = com.google.tagmanager.LoadCallback.Failure.IO_ERROR;	 Catch:{ all -> 0x009a }
        r0.onFailure(r2);	 Catch:{ all -> 0x009a }
        r1.close();	 Catch:{ IOException -> 0x0093 }
        goto L_0x0067;
    L_0x0093:
        r0 = move-exception;
        r0 = "error closing stream for reading resource from disk";
        com.google.tagmanager.Log.w(r0);
        goto L_0x0067;
    L_0x009a:
        r0 = move-exception;
        r1.close();	 Catch:{ IOException -> 0x009f }
    L_0x009e:
        throw r0;
    L_0x009f:
        r1 = move-exception;
        r1 = "error closing stream for reading resource from disk";
        com.google.tagmanager.Log.w(r1);
        goto L_0x009e;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.tagmanager.ResourceStorageImpl.loadResourceFromDisk():void");
    }

    public void loadResourceFromDiskInBackground() {
        this.mExecutor.execute(new Runnable() {
            public void run() {
                ResourceStorageImpl.this.loadResourceFromDisk();
            }
        });
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    @com.google.android.gms.common.util.VisibleForTesting
    boolean saveResourceToDisk(com.google.tagmanager.proto.Resource.ResourceWithMetadata r5) {
        /*
        r4 = this;
        r0 = 0;
        r1 = r4.getResourceFile();
        r2 = new java.io.FileOutputStream;	 Catch:{ FileNotFoundException -> 0x0016 }
        r2.<init>(r1);	 Catch:{ FileNotFoundException -> 0x0016 }
        r3 = com.google.tagmanager.protobuf.nano.MessageNano.toByteArray(r5);	 Catch:{ IOException -> 0x0024 }
        r2.write(r3);	 Catch:{ IOException -> 0x0024 }
        r2.close();	 Catch:{ IOException -> 0x001d }
    L_0x0014:
        r0 = 1;
    L_0x0015:
        return r0;
    L_0x0016:
        r1 = move-exception;
        r1 = "Error opening resource file for writing";
        com.google.tagmanager.Log.e(r1);
        goto L_0x0015;
    L_0x001d:
        r0 = move-exception;
        r0 = "error closing stream for writing resource to disk";
        com.google.tagmanager.Log.w(r0);
        goto L_0x0014;
    L_0x0024:
        r3 = move-exception;
        r3 = "Error writing resource to disk. Removing resource from disk.";
        com.google.tagmanager.Log.w(r3);	 Catch:{ all -> 0x0038 }
        r1.delete();	 Catch:{ all -> 0x0038 }
        r2.close();	 Catch:{ IOException -> 0x0031 }
        goto L_0x0015;
    L_0x0031:
        r1 = move-exception;
        r1 = "error closing stream for writing resource to disk";
        com.google.tagmanager.Log.w(r1);
        goto L_0x0015;
    L_0x0038:
        r0 = move-exception;
        r2.close();	 Catch:{ IOException -> 0x003d }
    L_0x003c:
        throw r0;
    L_0x003d:
        r1 = move-exception;
        r1 = "error closing stream for writing resource to disk";
        com.google.tagmanager.Log.w(r1);
        goto L_0x003c;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.tagmanager.ResourceStorageImpl.saveResourceToDisk(com.google.tagmanager.proto.Resource$ResourceWithMetadata):boolean");
    }

    public void saveResourceToDiskInBackground(final ResourceWithMetadata resourceWithMetadata) {
        this.mExecutor.execute(new Runnable() {
            public void run() {
                ResourceStorageImpl.this.saveResourceToDisk(resourceWithMetadata);
            }
        });
    }

    public void setLoadCallback(LoadCallback<ResourceWithMetadata> loadCallback) {
        this.mCallback = loadCallback;
    }
}
