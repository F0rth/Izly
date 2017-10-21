package com.foxykeep.datadroid.network;

import android.net.http.AndroidHttpClient;
import android.util.Log;
import com.foxykeep.datadroid.exception.CompulsoryParameterException;
import com.foxykeep.datadroid.exception.RestClientException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.http.Header;
import org.apache.http.HeaderIterator;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicNameValuePair;

public class NetworkConnection {
    private static final String LOG_TAG = NetworkConnection.class.getSimpleName();
    public static final int METHOD_DELETE = 3;
    public static final int METHOD_GET = 0;
    public static final int METHOD_POST = 1;
    public static final int METHOD_PUT = 2;

    public static class NetworkConnectionResult {
        public Header[] headerArray;
        public String wsResponse;

        public NetworkConnectionResult(Header[] headerArr, String str) {
            this.headerArray = headerArr;
            this.wsResponse = str;
        }
    }

    private static java.lang.String convertStreamToString(java.io.InputStream r6, boolean r7, int r8, int r9) throws java.io.IOException {
        /* JADX: method processing error */
/*
Error: java.util.NoSuchElementException
	at java.base/java.util.HashMap$HashIterator.nextNode(HashMap.java:1491)
	at java.base/java.util.HashMap$KeyIterator.next(HashMap.java:1512)
	at jadx.core.dex.visitors.blocksmaker.BlockFinallyExtract.applyRemove(BlockFinallyExtract.java:535)
	at jadx.core.dex.visitors.blocksmaker.BlockFinallyExtract.extractFinally(BlockFinallyExtract.java:175)
	at jadx.core.dex.visitors.blocksmaker.BlockFinallyExtract.processExceptionHandler(BlockFinallyExtract.java:79)
	at jadx.core.dex.visitors.blocksmaker.BlockFinallyExtract.visit(BlockFinallyExtract.java:51)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
        /*
        if (r7 == 0) goto L_0x0083;
    L_0x0002:
        r0 = new java.util.zip.GZIPInputStream;
        r0.<init>(r6);
        r1 = r0;
    L_0x0008:
        switch(r8) {
            case 0: goto L_0x0015;
            case 1: goto L_0x0058;
            case 2: goto L_0x0015;
            case 3: goto L_0x0015;
            default: goto L_0x000b;
        };
    L_0x000b:
        r1.close();
        if (r7 == 0) goto L_0x0013;
    L_0x0010:
        r6.close();
    L_0x0013:
        r0 = 0;
    L_0x0014:
        return r0;
    L_0x0015:
        r0 = new java.io.BufferedReader;	 Catch:{ all -> 0x0041 }
        r2 = new java.io.InputStreamReader;	 Catch:{ all -> 0x0041 }
        r2.<init>(r1);	 Catch:{ all -> 0x0041 }
        r0.<init>(r2);	 Catch:{ all -> 0x0041 }
        r2 = new java.lang.StringBuilder;	 Catch:{ all -> 0x0041 }
        r2.<init>();	 Catch:{ all -> 0x0041 }
    L_0x0024:
        r3 = r0.readLine();	 Catch:{ all -> 0x0041 }
        if (r3 == 0) goto L_0x004b;	 Catch:{ all -> 0x0041 }
    L_0x002a:
        r4 = new java.lang.StringBuilder;	 Catch:{ all -> 0x0041 }
        r4.<init>();	 Catch:{ all -> 0x0041 }
        r3 = r4.append(r3);	 Catch:{ all -> 0x0041 }
        r4 = "\n";	 Catch:{ all -> 0x0041 }
        r3 = r3.append(r4);	 Catch:{ all -> 0x0041 }
        r3 = r3.toString();	 Catch:{ all -> 0x0041 }
        r2.append(r3);	 Catch:{ all -> 0x0041 }
        goto L_0x0024;
    L_0x0041:
        r0 = move-exception;
        r1.close();
        if (r7 == 0) goto L_0x004a;
    L_0x0047:
        r6.close();
    L_0x004a:
        throw r0;
    L_0x004b:
        r0 = r2.toString();	 Catch:{ all -> 0x0041 }
        r1.close();
        if (r7 == 0) goto L_0x0014;
    L_0x0054:
        r6.close();
        goto L_0x0014;
    L_0x0058:
        if (r9 >= 0) goto L_0x005c;
    L_0x005a:
        r9 = 4096; // 0x1000 float:5.74E-42 double:2.0237E-320;
    L_0x005c:
        r0 = new java.io.InputStreamReader;	 Catch:{ all -> 0x0041 }
        r0.<init>(r1);	 Catch:{ all -> 0x0041 }
        r2 = new org.apache.http.util.CharArrayBuffer;	 Catch:{ all -> 0x0041 }
        r2.<init>(r9);	 Catch:{ all -> 0x0041 }
        r3 = 1024; // 0x400 float:1.435E-42 double:5.06E-321;	 Catch:{ all -> 0x0041 }
        r3 = new char[r3];	 Catch:{ all -> 0x0041 }
    L_0x006a:
        r4 = r0.read(r3);	 Catch:{ all -> 0x0041 }
        r5 = -1;	 Catch:{ all -> 0x0041 }
        if (r4 == r5) goto L_0x0076;	 Catch:{ all -> 0x0041 }
    L_0x0071:
        r5 = 0;	 Catch:{ all -> 0x0041 }
        r2.append(r3, r5, r4);	 Catch:{ all -> 0x0041 }
        goto L_0x006a;	 Catch:{ all -> 0x0041 }
    L_0x0076:
        r0 = r2.toString();	 Catch:{ all -> 0x0041 }
        r1.close();
        if (r7 == 0) goto L_0x0014;
    L_0x007f:
        r6.close();
        goto L_0x0014;
    L_0x0083:
        r1 = r6;
        goto L_0x0008;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.foxykeep.datadroid.network.NetworkConnection.convertStreamToString(java.io.InputStream, boolean, int, int):java.lang.String");
    }

    public static NetworkConnectionResult retrieveResponseFromService(String str) throws IllegalStateException, IOException, URISyntaxException, RestClientException {
        return retrieveResponseFromService(str, 0);
    }

    public static NetworkConnectionResult retrieveResponseFromService(String str, int i) throws IllegalStateException, IOException, URISyntaxException, RestClientException {
        return retrieveResponseFromService(str, i, null);
    }

    public static NetworkConnectionResult retrieveResponseFromService(String str, int i, Map<String, String> map) throws IllegalStateException, IOException, URISyntaxException, RestClientException {
        return retrieveResponseFromService(str, i, map, null);
    }

    public static NetworkConnectionResult retrieveResponseFromService(String str, int i, Map<String, String> map, ArrayList<Header> arrayList) throws IllegalStateException, IOException, URISyntaxException, RestClientException {
        return retrieveResponseFromService(str, i, map, arrayList, false);
    }

    public static NetworkConnectionResult retrieveResponseFromService(String str, int i, Map<String, String> map, ArrayList<Header> arrayList, boolean z) throws IllegalStateException, IOException, URISyntaxException, RestClientException {
        return retrieveResponseFromService(str, i, map, arrayList, z, null);
    }

    public static NetworkConnectionResult retrieveResponseFromService(String str, int i, Map<String, String> map, ArrayList<Header> arrayList, boolean z, String str2) throws IllegalStateException, IOException, URISyntaxException, RestClientException {
        return retrieveResponseFromService(str, i, map, arrayList, z, str2, null);
    }

    public static NetworkConnectionResult retrieveResponseFromService(String str, int i, Map<String, String> map, ArrayList<Header> arrayList, boolean z, String str2, String str3) throws IllegalStateException, IOException, URISyntaxException, RestClientException {
        if (str == null) {
            Log.e(LOG_TAG, "retrieveResponseFromService - Compulsory Parameter : request URL has not been set");
            throw new CompulsoryParameterException("Request URL has not been set");
        }
        Log.d(LOG_TAG, "retrieveResponseFromService - Request url : " + str);
        if (i == 0 || i == 1 || i == 2 || i == 3) {
            int i2;
            String str4;
            int i3;
            Log.d(LOG_TAG, "retrieveResponseFromService - Request method : " + i);
            Log.d(LOG_TAG, "retrieveResponseFromService - Request parameters (number) : " + (map != null ? Integer.valueOf(map.size()) : ""));
            Log.d(LOG_TAG, "retrieveResponseFromService - Request headers (number) : " + (arrayList != null ? Integer.valueOf(arrayList.size()) : ""));
            AndroidHttpClient newInstance = AndroidHttpClient.newInstance(str2);
            Log.d(LOG_TAG, "retrieveResponseFromService - Request user agent : " + str2);
            HttpUriRequest httpUriRequest = null;
            switch (i) {
                case 0:
                case 2:
                case 3:
                    StringBuffer stringBuffer = new StringBuffer();
                    stringBuffer.append(str);
                    if (!(map == null || map.isEmpty())) {
                        stringBuffer.append("?");
                        ArrayList arrayList2 = new ArrayList(map.keySet());
                        int size = arrayList2.size();
                        for (i2 = 0; i2 < size; i2++) {
                            str4 = (String) arrayList2.get(i2);
                            stringBuffer.append(URLEncoder.encode(str4, "UTF-8"));
                            stringBuffer.append("=");
                            stringBuffer.append(URLEncoder.encode((String) map.get(str4), "UTF-8"));
                            stringBuffer.append("&");
                        }
                    }
                    Log.i(LOG_TAG, "retrieveResponseFromService - GET Request - complete URL with parameters if any : ");
                    String stringBuffer2 = stringBuffer.toString();
                    int length = stringBuffer2.length();
                    for (int i4 = 0; i4 < length; i4 += 120) {
                        Log.i(LOG_TAG, stringBuffer2.substring(i4, Math.min(length - 1, i4 + 120)));
                    }
                    URI uri = new URI(stringBuffer.toString());
                    if (i != 0) {
                        if (i != 2) {
                            if (i == 3) {
                                httpUriRequest = new HttpDelete(uri);
                                break;
                            }
                        }
                        httpUriRequest = new HttpPut(uri);
                        break;
                    }
                    httpUriRequest = new HttpGet(uri);
                    break;
                    break;
                case 1:
                    httpUriRequest = new HttpPost(new URI(str));
                    if (map == null || map.isEmpty()) {
                        if (str3 != null) {
                            ((HttpPost) httpUriRequest).setEntity(new StringEntity(str3));
                            break;
                        }
                    }
                    List arrayList3 = new ArrayList();
                    ArrayList arrayList4 = new ArrayList(map.keySet());
                    int size2 = arrayList4.size();
                    for (i3 = 0; i3 < size2; i3++) {
                        str4 = (String) arrayList4.get(i3);
                        arrayList3.add(new BasicNameValuePair(str4, (String) map.get(str4)));
                    }
                    Log.i(LOG_TAG, "retrieveResponseFromService - POST Request - parameters list (key => value) : ");
                    i3 = arrayList3.size();
                    for (i2 = 0; i2 < i3; i2++) {
                        NameValuePair nameValuePair = (NameValuePair) arrayList3.get(i2);
                        Log.i(LOG_TAG, "- " + nameValuePair.getName() + " => " + nameValuePair.getValue());
                    }
                    httpUriRequest.setHeader("Content-Type", "application/x-www-form-urlencoded");
                    ((HttpPost) httpUriRequest).setEntity(new UrlEncodedFormEntity(arrayList3, "UTF-8"));
                    break;
                    break;
                default:
                    try {
                        Log.e(LOG_TAG, "retrieveResponseFromService - Request method must be METHOD_GET, METHOD_POST, METHOD_PUT or METHOD_DELETE");
                        throw new IllegalArgumentException("retrieveResponseFromService - Request method must be METHOD_GET, METHOD_POST, METHOD_PUT or METHOD_DELETE");
                    } catch (Throwable th) {
                        newInstance.close();
                    }
            }
            if (z) {
                AndroidHttpClient.modifyRequestToAcceptGzipResponse(httpUriRequest);
            }
            Log.i(LOG_TAG, "retrieveResponseFromService - Request - headers list (name => value) : ");
            HeaderIterator headerIterator = httpUriRequest.headerIterator();
            while (headerIterator.hasNext()) {
                Header nextHeader = headerIterator.nextHeader();
                Log.i(LOG_TAG, "- " + nextHeader.getName() + " => " + nextHeader.getValue());
            }
            if (!(arrayList == null || arrayList.isEmpty())) {
                i3 = arrayList.size();
                for (i2 = 0; i2 < i3; i2++) {
                    httpUriRequest.addHeader((Header) arrayList.get(i2));
                }
            }
            str4 = null;
            Log.d(LOG_TAG, "retrieveResponseFromService - Executing the request");
            HttpResponse execute = newInstance.execute(httpUriRequest);
            StatusLine statusLine = execute.getStatusLine();
            Log.d(LOG_TAG, "retrieveResponseFromService - Response status : " + statusLine.getStatusCode());
            if (statusLine.getStatusCode() != 200) {
                Log.e(LOG_TAG, "retrieveResponseFromService - Invalid response from server : " + statusLine.toString());
                if (statusLine.getStatusCode() == 302) {
                    Header firstHeader = execute.getFirstHeader("Location");
                    Log.i(LOG_TAG, "retrieveResponseFromService - New location : " + firstHeader.getValue());
                    throw new RestClientException("New location : " + firstHeader, firstHeader.getValue());
                }
                throw new RestClientException("Invalid response from server : " + statusLine.toString());
            }
            HttpEntity entity = execute.getEntity();
            Header firstHeader2 = execute.getFirstHeader("Content-Encoding");
            if (entity != null) {
                InputStream content = entity.getContent();
                boolean z2 = firstHeader2 != null && firstHeader2.getValue().equalsIgnoreCase("gzip");
                str4 = convertStreamToString(content, z2, i, (int) entity.getContentLength());
            }
            Log.i(LOG_TAG, "retrieveResponseFromService - Result from webservice : " + str4);
            NetworkConnectionResult networkConnectionResult = new NetworkConnectionResult(execute.getAllHeaders(), str4);
            newInstance.close();
            return networkConnectionResult;
        }
        Log.e(LOG_TAG, "retrieveResponseFromService - Request method must be METHOD_GET, METHOD_POST, METHOD_PUT or METHOD_DELETE");
        throw new IllegalArgumentException("retrieveResponseFromService - Request method must be METHOD_GET, METHOD_POST, METHOD_PUT or METHOD_DELETE");
    }
}
