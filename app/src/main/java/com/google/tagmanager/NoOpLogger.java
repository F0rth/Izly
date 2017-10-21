package com.google.tagmanager;

import com.google.tagmanager.Logger.LogLevel;

class NoOpLogger implements Logger {
    NoOpLogger() {
    }

    public void d(String str) {
    }

    public void d(String str, Throwable th) {
    }

    public void e(String str) {
    }

    public void e(String str, Throwable th) {
    }

    public LogLevel getLogLevel() {
        return LogLevel.NONE;
    }

    public void i(String str) {
    }

    public void i(String str, Throwable th) {
    }

    public void setLogLevel(LogLevel logLevel) {
    }

    public void v(String str) {
    }

    public void v(String str, Throwable th) {
    }

    public void w(String str) {
    }

    public void w(String str, Throwable th) {
    }
}
