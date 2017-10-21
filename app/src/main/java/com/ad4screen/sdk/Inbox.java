package com.ad4screen.sdk;

import android.content.Context;

import com.ad4screen.sdk.A4S.Callback;
import com.ad4screen.sdk.A4S.MessageCallback;
import com.ad4screen.sdk.common.annotations.API;

@API
public class Inbox {
    protected Message[] a;
    private Context b;

    protected Inbox(Context context, Message[] messageArr) {
        this.a = messageArr;
        this.b = context;
    }

    public int countMessages() {
        return this.a.length;
    }

    public int countMessagesOfCategory(String str) {
        int i = 0;
        for (Message category : this.a) {
            if (category.getCategory().equals(str)) {
                i++;
            }
        }
        return i;
    }

    public int countReadMessages() {
        int i = 0;
        for (Message isRead : this.a) {
            if (isRead.isRead()) {
                i++;
            }
        }
        return i;
    }

    public int countUnReadMessages() {
        int i = 0;
        for (Message isRead : this.a) {
            if (!isRead.isRead()) {
                i++;
            }
        }
        return i;
    }

    public void getMessage(final int i, final MessageCallback messageCallback) {
        if (this.a[i].isDownloaded()) {
            messageCallback.onResult(this.a[i], i);
        } else {
            A4S.get(this.b).a(i, new Callback<Inbox>(this) {
                final /* synthetic */ Inbox c;

                public void a(Inbox inbox) {
                    this.c.a = inbox.a;
                    messageCallback.onResult(this.c.a[i], i);
                }

                public void onError(int i, String str) {
                    messageCallback.onError(i, str);
                }

                public /* synthetic */ void onResult(Object obj) {
                    a((Inbox) obj);
                }
            }, false);
        }
    }
}
