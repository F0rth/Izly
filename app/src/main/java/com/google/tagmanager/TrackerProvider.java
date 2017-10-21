package com.google.tagmanager;

import android.content.Context;
import com.google.analytics.tracking.android.GoogleAnalytics;
import com.google.analytics.tracking.android.Logger;
import com.google.analytics.tracking.android.Logger.LogLevel;
import com.google.analytics.tracking.android.Tracker;
import com.google.android.gms.common.util.VisibleForTesting;

class TrackerProvider {
    private Context mContext;
    private GoogleAnalytics mGoogleAnalytics;

    static class LoggerImpl implements Logger {
        LoggerImpl() {
        }

        private static LogLevel toAnalyticsLogLevel(Logger.LogLevel logLevel) {
            switch (logLevel) {
                case NONE:
                case ERROR:
                    return LogLevel.ERROR;
                case WARNING:
                    return LogLevel.WARNING;
                case INFO:
                case DEBUG:
                    return LogLevel.INFO;
                case VERBOSE:
                    return LogLevel.VERBOSE;
                default:
                    return LogLevel.ERROR;
            }
        }

        public void error(Exception exception) {
            Log.e("", exception);
        }

        public void error(String str) {
            Log.e(str);
        }

        public LogLevel getLogLevel() {
            Logger.LogLevel logLevel = Log.getLogLevel();
            return logLevel == null ? LogLevel.ERROR : toAnalyticsLogLevel(logLevel);
        }

        public void info(String str) {
            Log.i(str);
        }

        public void setLogLevel(LogLevel logLevel) {
            Log.w("GA uses GTM logger. Please use TagManager.getLogger().setLogLevel(LogLevel) instead.");
        }

        public void verbose(String str) {
            Log.v(str);
        }

        public void warn(String str) {
            Log.w(str);
        }
    }

    TrackerProvider(Context context) {
        this.mContext = context;
    }

    @VisibleForTesting
    TrackerProvider(GoogleAnalytics googleAnalytics) {
        this.mGoogleAnalytics = googleAnalytics;
        this.mGoogleAnalytics.setLogger(new LoggerImpl());
    }

    private void initTrackProviderIfNecessary() {
        synchronized (this) {
            if (this.mGoogleAnalytics == null) {
                this.mGoogleAnalytics = GoogleAnalytics.getInstance(this.mContext);
                this.mGoogleAnalytics.setLogger(new LoggerImpl());
            }
        }
    }

    public void close(Tracker tracker) {
        this.mGoogleAnalytics.closeTracker(tracker.getName());
    }

    public Tracker getTracker(String str) {
        initTrackProviderIfNecessary();
        return this.mGoogleAnalytics.getTracker(str);
    }
}
