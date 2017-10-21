package com.crashlytics.android.core;

import com.fasterxml.jackson.core.util.MinimalPrettyPrinter;
import defpackage.js;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;

class QueueFileLogStore implements FileLogStore {
    private ky logFile;
    private final int maxLogSize;
    private final File workingFile;

    public QueueFileLogStore(File file, int i) {
        this.workingFile = file;
        this.maxLogSize = i;
    }

    private void doWriteToLog(long j, String str) {
        if (this.logFile != null) {
            String str2 = str == null ? "null" : str;
            try {
                int i = this.maxLogSize / 4;
                if (str2.length() > i) {
                    str2 = "..." + str2.substring(str2.length() - i);
                }
                str2 = str2.replaceAll("\r", MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR).replaceAll("\n", MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR);
                this.logFile.a(String.format(Locale.US, "%d %s%n", new Object[]{Long.valueOf(j), str2}).getBytes("UTF-8"));
                while (!this.logFile.b() && this.logFile.a() > this.maxLogSize) {
                    this.logFile.c();
                }
            } catch (Throwable e) {
                js.a().c(CrashlyticsCore.TAG, "There was a problem writing to the Crashlytics log.", e);
            }
        }
    }

    private void openLogFile() {
        if (this.logFile == null) {
            try {
                this.logFile = new ky(this.workingFile);
            } catch (Throwable e) {
                js.a().c(CrashlyticsCore.TAG, "Could not open log file: " + this.workingFile, e);
            }
        }
    }

    public void closeLogFile() {
        kp.a(this.logFile, "There was a problem closing the Crashlytics log file.");
        this.logFile = null;
    }

    public void deleteLogFile() {
        closeLogFile();
        this.workingFile.delete();
    }

    public ByteString getLogAsByteString() {
        if (this.workingFile.exists()) {
            openLogFile();
            if (this.logFile != null) {
                final int[] iArr = new int[]{0};
                final byte[] bArr = new byte[this.logFile.a()];
                try {
                    this.logFile.a(new c() {
                        public void read(InputStream inputStream, int i) throws IOException {
                            try {
                                inputStream.read(bArr, iArr[0], i);
                                int[] iArr = iArr;
                                iArr[0] = iArr[0] + i;
                            } finally {
                                inputStream.close();
                            }
                        }
                    });
                } catch (Throwable e) {
                    js.a().c(CrashlyticsCore.TAG, "A problem occurred while reading the Crashlytics log file.", e);
                }
                return ByteString.copyFrom(bArr, 0, iArr[0]);
            }
        }
        return null;
    }

    public void writeToLog(long j, String str) {
        openLogFile();
        doWriteToLog(j, str);
    }
}
