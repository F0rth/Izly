package android.support.v7.app;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.os.Build.VERSION;
import android.support.v4.app.NotificationBuilderWithBuilderAccessor;
import android.support.v4.app.NotificationCompat.BuilderExtender;
import android.support.v4.app.NotificationCompat.Style;
import android.support.v4.media.session.MediaSessionCompat.Token;
import android.support.v7.internal.app.NotificationCompatImpl21;
import android.support.v7.internal.app.NotificationCompatImplBase;

public class NotificationCompat extends android.support.v4.app.NotificationCompat {

    public static class Builder extends android.support.v4.app.NotificationCompat.Builder {
        public Builder(Context context) {
            super(context);
        }

        protected BuilderExtender getExtender() {
            return VERSION.SDK_INT >= 21 ? new LollipopExtender() : VERSION.SDK_INT >= 16 ? new JellybeanExtender() : VERSION.SDK_INT >= 14 ? new IceCreamSandwichExtender() : super.getExtender();
        }
    }

    static class IceCreamSandwichExtender extends BuilderExtender {
        private IceCreamSandwichExtender() {
        }

        public Notification build(android.support.v4.app.NotificationCompat.Builder builder, NotificationBuilderWithBuilderAccessor notificationBuilderWithBuilderAccessor) {
            NotificationCompat.addMediaStyleToBuilderIcs(notificationBuilderWithBuilderAccessor, builder);
            return notificationBuilderWithBuilderAccessor.build();
        }
    }

    static class JellybeanExtender extends BuilderExtender {
        private JellybeanExtender() {
        }

        public Notification build(android.support.v4.app.NotificationCompat.Builder builder, NotificationBuilderWithBuilderAccessor notificationBuilderWithBuilderAccessor) {
            NotificationCompat.addMediaStyleToBuilderIcs(notificationBuilderWithBuilderAccessor, builder);
            Notification build = notificationBuilderWithBuilderAccessor.build();
            NotificationCompat.addBigMediaStyleToBuilderJellybean(build, builder);
            return build;
        }
    }

    static class LollipopExtender extends BuilderExtender {
        private LollipopExtender() {
        }

        public Notification build(android.support.v4.app.NotificationCompat.Builder builder, NotificationBuilderWithBuilderAccessor notificationBuilderWithBuilderAccessor) {
            NotificationCompat.addMediaStyleToBuilderLollipop(notificationBuilderWithBuilderAccessor, builder.mStyle);
            return notificationBuilderWithBuilderAccessor.build();
        }
    }

    public static class MediaStyle extends Style {
        int[] mActionsToShowInCompact = null;
        PendingIntent mCancelButtonIntent;
        boolean mShowCancelButton;
        Token mToken;

        public MediaStyle(android.support.v4.app.NotificationCompat.Builder builder) {
            setBuilder(builder);
        }

        public void setCancelButtonIntent(PendingIntent pendingIntent) {
            this.mCancelButtonIntent = pendingIntent;
        }

        public MediaStyle setMediaSession(Token token) {
            this.mToken = token;
            return this;
        }

        public MediaStyle setShowActionsInCompactView(int... iArr) {
            this.mActionsToShowInCompact = iArr;
            return this;
        }

        public void setShowCancelButton(boolean z) {
            this.mShowCancelButton = z;
        }
    }

    private static void addBigMediaStyleToBuilderJellybean(Notification notification, android.support.v4.app.NotificationCompat.Builder builder) {
        if (builder.mStyle instanceof MediaStyle) {
            MediaStyle mediaStyle = (MediaStyle) builder.mStyle;
            NotificationCompatImplBase.overrideBigContentView(notification, builder.mContext, builder.mContentTitle, builder.mContentText, builder.mContentInfo, builder.mNumber, builder.mLargeIcon, builder.mSubText, builder.mUseChronometer, builder.mNotification.when, builder.mActions, mediaStyle.mShowCancelButton, mediaStyle.mCancelButtonIntent);
        }
    }

    private static void addMediaStyleToBuilderIcs(NotificationBuilderWithBuilderAccessor notificationBuilderWithBuilderAccessor, android.support.v4.app.NotificationCompat.Builder builder) {
        if (builder.mStyle instanceof MediaStyle) {
            MediaStyle mediaStyle = (MediaStyle) builder.mStyle;
            NotificationCompatImplBase.overrideContentView(notificationBuilderWithBuilderAccessor, builder.mContext, builder.mContentTitle, builder.mContentText, builder.mContentInfo, builder.mNumber, builder.mLargeIcon, builder.mSubText, builder.mUseChronometer, builder.mNotification.when, builder.mActions, mediaStyle.mActionsToShowInCompact, mediaStyle.mShowCancelButton, mediaStyle.mCancelButtonIntent);
        }
    }

    private static void addMediaStyleToBuilderLollipop(NotificationBuilderWithBuilderAccessor notificationBuilderWithBuilderAccessor, Style style) {
        if (style instanceof MediaStyle) {
            MediaStyle mediaStyle = (MediaStyle) style;
            NotificationCompatImpl21.addMediaStyle(notificationBuilderWithBuilderAccessor, mediaStyle.mActionsToShowInCompact, mediaStyle.mToken != null ? mediaStyle.mToken.getToken() : null);
        }
    }
}
