package com.ad4screen.sdk;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

import com.ad4screen.sdk.A4S.Callback;
import com.ad4screen.sdk.common.annotations.API;
import com.ad4screen.sdk.common.i;

import java.util.Date;

@API
public class Message implements Parcelable {
    public static final Creator<Message> CREATOR = new Creator<Message>() {
        public final Message a(Parcel parcel) {
            return new Message(parcel);
        }

        public final Message[] a(int i) {
            return new Message[i];
        }

        public final /* synthetic */ Object createFromParcel(Parcel parcel) {
            return a(parcel);
        }

        public final /* synthetic */ Object[] newArray(int i) {
            return a(i);
        }
    };
    protected String a;
    private String b;
    private Date c;
    private String d;
    private String e;
    private String f;
    private String g;
    private MessageContentType h;
    private boolean i;
    private boolean j;
    private boolean k;
    private boolean l;
    private String m;
    private Button[] n;

    @API
    public static class Button implements Parcelable {
        public static final Creator<Button> CREATOR = new Creator<Button>() {
            public final Button a(Parcel parcel) {
                return new Button(parcel);
            }

            public final Button[] a(int i) {
                return new Button[i];
            }

            public final /* synthetic */ Object createFromParcel(Parcel parcel) {
                return a(parcel);
            }

            public final /* synthetic */ Object[] newArray(int i) {
                return a(i);
            }
        };
        protected String a;
        private String b;

        private Button(Parcel parcel) {
            this.a = parcel.readString();
            this.b = parcel.readString();
        }

        protected Button(String str, String str2) {
            this.a = str;
            this.b = str2;
        }

        public void click(Context context) {
            A4S.get(context).a(this.a);
        }

        public int describeContents() {
            return 0;
        }

        public String getTitle() {
            return this.b;
        }

        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeString(this.a);
            parcel.writeString(this.b);
        }
    }

    @API
    public enum MessageContentType {
        Text,
        Web,
        System,
        Event,
        Url,
        Push
    }

    @API
    public interface onIconDownloadedListener {
        void onIconDownloaded(Bitmap bitmap);
    }

    private Message(Parcel parcel) {
        this.n = new Button[0];
        this.a = parcel.readString();
        this.b = parcel.readString();
        this.c = new Date(parcel.readLong());
        this.d = parcel.readString();
        this.e = parcel.readString();
        this.f = parcel.readString();
        this.g = parcel.readString();
        this.h = MessageContentType.valueOf(parcel.readString());
        this.m = parcel.readString();
        boolean[] zArr = new boolean[4];
        parcel.readBooleanArray(zArr);
        this.i = zArr[0];
        this.j = zArr[1];
        this.k = zArr[2];
        this.l = zArr[3];
        Object readArray = parcel.readArray(getClass().getClassLoader());
        if (readArray != null) {
            this.n = new Button[readArray.length];
            System.arraycopy(readArray, 0, this.n, 0, readArray.length);
        }
    }

    protected Message(String str, String str2, Date date, String str3, String str4, String str5, String str6, MessageContentType messageContentType, boolean z, boolean z2, boolean z3, boolean z4, String str7, Button[] buttonArr, boolean z5) {
        this.n = new Button[0];
        this.a = str;
        this.b = str2;
        this.c = date;
        this.d = str3;
        this.e = str4;
        this.f = str5;
        this.g = str6;
        this.h = messageContentType;
        this.i = z;
        this.j = z2;
        this.k = z3;
        this.m = str7;
        this.n = buttonArr;
        this.l = z5;
    }

    public int countButtons() {
        return this.n.length;
    }

    public int describeContents() {
        return 0;
    }

    public void display(Context context, Callback<Message> callback) {
        A4S.get(context).a(this.a, callback);
    }

    public String getBody() {
        return this.d;
    }

    public Button getButton(int i) {
        return this.n[i];
    }

    public String getCategory() {
        return this.f;
    }

    public MessageContentType getContentType() {
        return this.h;
    }

    public void getIcon(final onIconDownloadedListener com_ad4screen_sdk_Message_onIconDownloadedListener) {
        i.a(this.m, new Callback<Bitmap>(this) {
            final /* synthetic */ Message b;

            public void a(Bitmap bitmap) {
                if (com_ad4screen_sdk_Message_onIconDownloadedListener != null) {
                    com_ad4screen_sdk_Message_onIconDownloadedListener.onIconDownloaded(bitmap);
                }
            }

            public void onError(int i, String str) {
            }

            public /* synthetic */ void onResult(Object obj) {
                a((Bitmap) obj);
            }
        }, false);
    }

    public Date getSendDate() {
        return this.c;
    }

    public String getSender() {
        return this.e;
    }

    public String getText() {
        return this.g;
    }

    public String getTitle() {
        return this.b;
    }

    public String getUrlIcon() {
        return this.m;
    }

    public boolean isArchived() {
        return this.k;
    }

    public boolean isDownloaded() {
        return this.l;
    }

    public boolean isOutdated() {
        return this.i;
    }

    public boolean isRead() {
        return this.j;
    }

    public void setArchived(boolean z) {
        this.k = z;
    }

    public void setRead(boolean z) {
        this.j = z;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.a);
        parcel.writeString(this.b);
        parcel.writeLong(this.c.getTime());
        parcel.writeString(this.d);
        parcel.writeString(this.e);
        parcel.writeString(this.f);
        parcel.writeString(this.g);
        parcel.writeString(this.h.name());
        parcel.writeString(this.m);
        parcel.writeBooleanArray(new boolean[]{this.i, this.j, this.k, this.l});
        parcel.writeArray(this.n);
    }
}
