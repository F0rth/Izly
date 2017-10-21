package com.crashlytics.android.core;

import android.app.Activity;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.content.Context;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Environment;
import android.os.StatFs;
import android.text.TextUtils;
import com.crashlytics.android.answers.Answers;
import com.crashlytics.android.answers.AppMeasurementEventLogger;
import com.crashlytics.android.answers.EventLogger;
import com.crashlytics.android.core.LogFileManager.DirectoryProvider;
import com.crashlytics.android.core.internal.models.SessionEventData;
import defpackage.js;
import defpackage.jy;
import java.io.Closeable;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.Flushable;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.Thread.UncaughtExceptionHandler;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;
import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.json.JSONObject;

class CrashlyticsController {
    private static final int ANALYZER_VERSION = 1;
    private static final String COLLECT_CUSTOM_KEYS = "com.crashlytics.CollectCustomKeys";
    private static final String CRASHLYTICS_API_ENDPOINT = "com.crashlytics.ApiEndpoint";
    private static final String EVENT_TYPE_CRASH = "crash";
    private static final String EVENT_TYPE_LOGGED = "error";
    static final String FATAL_SESSION_DIR = "fatal-sessions";
    static final String FIREBASE_ANALYTICS_ORIGIN_CRASHLYTICS = "clx";
    static final String FIREBASE_APPLICATION_EXCEPTION = "_ae";
    static final String FIREBASE_CRASH_TYPE = "fatal";
    private static final int FIREBASE_CRASH_TYPE_FATAL = 1;
    static final String FIREBASE_REALTIME = "_r";
    static final String FIREBASE_TIMESTAMP = "timestamp";
    private static final String GENERATOR_FORMAT = "Crashlytics Android SDK/%s";
    private static final String[] INITIAL_SESSION_PART_TAGS = new String[]{SESSION_USER_TAG, SESSION_APP_TAG, SESSION_OS_TAG, SESSION_DEVICE_TAG};
    static final String INVALID_CLS_CACHE_DIR = "invalidClsFiles";
    static final Comparator<File> LARGEST_FILE_NAME_FIRST = new Comparator<File>() {
        public final int compare(File file, File file2) {
            return file2.getName().compareTo(file.getName());
        }
    };
    static final int MAX_INVALID_SESSIONS = 4;
    private static final int MAX_LOCAL_LOGGED_EXCEPTIONS = 64;
    static final int MAX_OPEN_SESSIONS = 8;
    static final int MAX_STACK_SIZE = 1024;
    static final String NONFATAL_SESSION_DIR = "nonfatal-sessions";
    static final int NUM_STACK_REPETITIONS_ALLOWED = 10;
    private static final Map<String, String> SEND_AT_CRASHTIME_HEADER = Collections.singletonMap("X-CRASHLYTICS-SEND-FLAGS", "1");
    static final String SESSION_APP_TAG = "SessionApp";
    static final FilenameFilter SESSION_BEGIN_FILE_FILTER = new FileNameContainsFilter(SESSION_BEGIN_TAG) {
        public final boolean accept(File file, String str) {
            return super.accept(file, str) && str.endsWith(ClsFileOutputStream.SESSION_FILE_EXTENSION);
        }
    };
    static final String SESSION_BEGIN_TAG = "BeginSession";
    static final String SESSION_DEVICE_TAG = "SessionDevice";
    static final FileFilter SESSION_DIRECTORY_FILTER = new FileFilter() {
        public final boolean accept(File file) {
            return file.isDirectory() && file.getName().length() == 35;
        }
    };
    static final String SESSION_EVENT_MISSING_BINARY_IMGS_TAG = "SessionMissingBinaryImages";
    static final String SESSION_FATAL_TAG = "SessionCrash";
    static final FilenameFilter SESSION_FILE_FILTER = new FilenameFilter() {
        public final boolean accept(File file, String str) {
            return str.length() == 39 && str.endsWith(ClsFileOutputStream.SESSION_FILE_EXTENSION);
        }
    };
    private static final Pattern SESSION_FILE_PATTERN = Pattern.compile("([\\d|A-Z|a-z]{12}\\-[\\d|A-Z|a-z]{4}\\-[\\d|A-Z|a-z]{4}\\-[\\d|A-Z|a-z]{12}).+");
    private static final int SESSION_ID_LENGTH = 35;
    static final String SESSION_JSON_SUFFIX = ".json";
    static final String SESSION_NON_FATAL_TAG = "SessionEvent";
    static final String SESSION_OS_TAG = "SessionOS";
    static final String SESSION_USER_TAG = "SessionUser";
    private static final boolean SHOULD_PROMPT_BEFORE_SENDING_REPORTS_DEFAULT = false;
    static final Comparator<File> SMALLEST_FILE_NAME_FIRST = new Comparator<File>() {
        public final int compare(File file, File file2) {
            return file.getName().compareTo(file2.getName());
        }
    };
    private final AppData appData;
    private final CrashlyticsBackgroundWorker backgroundWorker;
    private CrashlyticsUncaughtExceptionHandler crashHandler;
    private final CrashlyticsCore crashlyticsCore;
    private final DevicePowerStateListener devicePowerStateListener;
    private final AtomicInteger eventCounter = new AtomicInteger(0);
    private final ml fileStore;
    private final EventLogger firebaseAnalytics;
    private final boolean firebaseCrashlyticsEnabled;
    private final HandlingExceptionCheck handlingExceptionCheck;
    private final mh httpRequestFactory;
    private final kw idManager;
    private final LogFileDirectoryProvider logFileDirectoryProvider;
    private final LogFileManager logFileManager;
    private final PreferenceManager preferenceManager;
    private final ReportFilesProvider reportFilesProvider;
    private final StackTraceTrimmingStrategy stackTraceTrimmingStrategy;
    private final String unityVersion;

    interface CodedOutputStreamWriteAction {
        void writeTo(CodedOutputStream codedOutputStream) throws Exception;
    }

    interface FileOutputStreamWriteAction {
        void writeTo(FileOutputStream fileOutputStream) throws Exception;
    }

    static class FileNameContainsFilter implements FilenameFilter {
        private final String string;

        public FileNameContainsFilter(String str) {
            this.string = str;
        }

        public boolean accept(File file, String str) {
            return str.contains(this.string) && !str.endsWith(ClsFileOutputStream.IN_PROGRESS_SESSION_FILE_EXTENSION);
        }
    }

    static class AnySessionPartFileFilter implements FilenameFilter {
        private AnySessionPartFileFilter() {
        }

        public boolean accept(File file, String str) {
            return !CrashlyticsController.SESSION_FILE_FILTER.accept(file, str) && CrashlyticsController.SESSION_FILE_PATTERN.matcher(str).matches();
        }
    }

    static class InvalidPartFileFilter implements FilenameFilter {
        InvalidPartFileFilter() {
        }

        public boolean accept(File file, String str) {
            return ClsFileOutputStream.TEMP_FILENAME_FILTER.accept(file, str) || str.contains(CrashlyticsController.SESSION_EVENT_MISSING_BINARY_IMGS_TAG);
        }
    }

    static final class LogFileDirectoryProvider implements DirectoryProvider {
        private static final String LOG_FILES_DIR = "log-files";
        private final ml rootFileStore;

        public LogFileDirectoryProvider(ml mlVar) {
            this.rootFileStore = mlVar;
        }

        public final File getLogFileDir() {
            File file = new File(this.rootFileStore.a(), LOG_FILES_DIR);
            if (!file.exists()) {
                file.mkdirs();
            }
            return file;
        }
    }

    static final class PrivacyDialogCheck implements SendCheck {
        private final jy kit;
        private final PreferenceManager preferenceManager;
        private final nd promptData;

        public PrivacyDialogCheck(jy jyVar, PreferenceManager preferenceManager, nd ndVar) {
            this.kit = jyVar;
            this.preferenceManager = preferenceManager;
            this.promptData = ndVar;
        }

        public final boolean canSendReports() {
            js fabric = this.kit.getFabric();
            Activity activity = fabric.e != null ? (Activity) fabric.e.get() : null;
            if (activity == null || activity.isFinishing()) {
                return true;
            }
            final CrashPromptDialog create = CrashPromptDialog.create(activity, this.promptData, new AlwaysSendCallback() {
                public void sendUserReportsWithoutPrompting(boolean z) {
                    PrivacyDialogCheck.this.preferenceManager.setShouldAlwaysSendReports(z);
                }
            });
            activity.runOnUiThread(new Runnable() {
                public void run() {
                    create.show();
                }
            });
            js.a().a(CrashlyticsCore.TAG, "Waiting for user opt-in.");
            create.await();
            return create.getOptIn();
        }
    }

    final class ReportUploaderFilesProvider implements ReportFilesProvider {
        private ReportUploaderFilesProvider() {
        }

        public final File[] getCompleteSessionFiles() {
            return CrashlyticsController.this.listCompleteSessionFiles();
        }

        public final File[] getInvalidSessionFiles() {
            return CrashlyticsController.this.getInvalidFilesDir().listFiles();
        }
    }

    final class ReportUploaderHandlingExceptionCheck implements HandlingExceptionCheck {
        private ReportUploaderHandlingExceptionCheck() {
        }

        public final boolean isHandlingException() {
            return CrashlyticsController.this.isHandlingException();
        }
    }

    static final class SendReportRunnable implements Runnable {
        private final Context context;
        private final Report report;
        private final ReportUploader reportUploader;

        public SendReportRunnable(Context context, Report report, ReportUploader reportUploader) {
            this.context = context;
            this.report = report;
            this.reportUploader = reportUploader;
        }

        public final void run() {
            if (kp.l(this.context)) {
                js.a().a(CrashlyticsCore.TAG, "Attempting to send crash report at time of crash...");
                this.reportUploader.forceUpload(this.report);
            }
        }
    }

    static class SessionPartFileFilter implements FilenameFilter {
        private final String sessionId;

        public SessionPartFileFilter(String str) {
            this.sessionId = str;
        }

        public boolean accept(File file, String str) {
            return (str.equals(new StringBuilder().append(this.sessionId).append(ClsFileOutputStream.SESSION_FILE_EXTENSION).toString()) || !str.contains(this.sessionId) || str.endsWith(ClsFileOutputStream.IN_PROGRESS_SESSION_FILE_EXTENSION)) ? false : true;
        }
    }

    CrashlyticsController(CrashlyticsCore crashlyticsCore, CrashlyticsBackgroundWorker crashlyticsBackgroundWorker, mh mhVar, kw kwVar, PreferenceManager preferenceManager, ml mlVar, AppData appData, UnityVersionProvider unityVersionProvider, boolean z) {
        this.crashlyticsCore = crashlyticsCore;
        this.backgroundWorker = crashlyticsBackgroundWorker;
        this.httpRequestFactory = mhVar;
        this.idManager = kwVar;
        this.preferenceManager = preferenceManager;
        this.fileStore = mlVar;
        this.appData = appData;
        this.unityVersion = unityVersionProvider.getUnityVersion();
        this.firebaseCrashlyticsEnabled = z;
        Context context = crashlyticsCore.getContext();
        this.logFileDirectoryProvider = new LogFileDirectoryProvider(mlVar);
        this.logFileManager = new LogFileManager(context, this.logFileDirectoryProvider);
        this.reportFilesProvider = new ReportUploaderFilesProvider();
        this.handlingExceptionCheck = new ReportUploaderHandlingExceptionCheck();
        this.devicePowerStateListener = new DevicePowerStateListener(context);
        this.stackTraceTrimmingStrategy = new MiddleOutFallbackStrategy(1024, new RemoveRepeatsStrategy(10));
        this.firebaseAnalytics = AppMeasurementEventLogger.getEventLogger(context);
    }

    private void closeOpenSessions(File[] fileArr, int i, int i2) {
        js.a().a(CrashlyticsCore.TAG, "Closing open sessions.");
        while (i < fileArr.length) {
            File file = fileArr[i];
            String sessionIdFromSessionFile = getSessionIdFromSessionFile(file);
            js.a().a(CrashlyticsCore.TAG, "Closing session: " + sessionIdFromSessionFile);
            writeSessionPartsToSessionFile(file, sessionIdFromSessionFile, i2);
            i++;
        }
    }

    private void closeWithoutRenamingOrLog(ClsFileOutputStream clsFileOutputStream) {
        if (clsFileOutputStream != null) {
            try {
                clsFileOutputStream.closeInProgressStream();
            } catch (Throwable e) {
                js.a().c(CrashlyticsCore.TAG, "Error closing session file stream in the presence of an exception", e);
            }
        }
    }

    private static void copyToCodedOutputStream(InputStream inputStream, CodedOutputStream codedOutputStream, int i) throws IOException {
        byte[] bArr = new byte[i];
        int i2 = 0;
        while (i2 < i) {
            int read = inputStream.read(bArr, i2, i - i2);
            if (read < 0) {
                break;
            }
            i2 += read;
        }
        codedOutputStream.writeRawBytes(bArr);
    }

    private void deleteSessionPartFilesFor(String str) {
        for (File delete : listSessionPartFilesFor(str)) {
            delete.delete();
        }
    }

    private void doCloseSessions(ne neVar, boolean z) throws Exception {
        int i = z ? 1 : 0;
        trimOpenSessions(i + 8);
        File[] listSortedSessionBeginFiles = listSortedSessionBeginFiles();
        if (listSortedSessionBeginFiles.length <= i) {
            js.a().a(CrashlyticsCore.TAG, "No open sessions to be closed.");
            return;
        }
        writeSessionUser(getSessionIdFromSessionFile(listSortedSessionBeginFiles[i]));
        if (neVar == null) {
            js.a().a(CrashlyticsCore.TAG, "Unable to close session. Settings are not loaded.");
        } else {
            closeOpenSessions(listSortedSessionBeginFiles, i, neVar.c);
        }
    }

    private void doOpenSession() throws Exception {
        Date date = new Date();
        String clsuuid = new CLSUUID(this.idManager).toString();
        js.a().a(CrashlyticsCore.TAG, "Opening a new session with ID " + clsuuid);
        writeBeginSession(clsuuid, date);
        writeSessionApp(clsuuid);
        writeSessionOS(clsuuid);
        writeSessionDevice(clsuuid);
        this.logFileManager.setCurrentSession(clsuuid);
    }

    private void doWriteExternalCrashEvent(SessionEventData sessionEventData) throws IOException {
        Flushable newInstance;
        Throwable e;
        Throwable th;
        Throwable th2;
        Object obj = 1;
        Closeable closeable = null;
        Closeable clsFileOutputStream;
        try {
            String previousSessionId = getPreviousSessionId();
            if (previousSessionId == null) {
                js.a().c(CrashlyticsCore.TAG, "Tried to write a native crash while no session was open.", null);
                kp.a(null, "Failed to flush to session begin file.");
                kp.a(null, "Failed to close fatal exception file output stream.");
                return;
            }
            recordFatalExceptionAnswersEvent(previousSessionId, String.format(Locale.US, "<native-crash [%s (%s)]>", new Object[]{sessionEventData.signal.code, sessionEventData.signal.name}));
            if (sessionEventData.binaryImages == null || sessionEventData.binaryImages.length <= 0) {
                obj = null;
            }
            clsFileOutputStream = new ClsFileOutputStream(getFilesDir(), previousSessionId + (obj != null ? SESSION_FATAL_TAG : SESSION_EVENT_MISSING_BINARY_IMGS_TAG));
            try {
                newInstance = CodedOutputStream.newInstance((OutputStream) clsFileOutputStream);
                try {
                    NativeCrashWriter.writeNativeCrash(sessionEventData, new LogFileManager(this.crashlyticsCore.getContext(), this.logFileDirectoryProvider, previousSessionId), new MetaDataStore(getFilesDir()).readKeyData(previousSessionId), newInstance);
                    kp.a(newInstance, "Failed to flush to session begin file.");
                    kp.a(clsFileOutputStream, "Failed to close fatal exception file output stream.");
                } catch (Exception e2) {
                    e = e2;
                    try {
                        js.a().c(CrashlyticsCore.TAG, "An error occurred in the native crash logger", e);
                        kp.a(newInstance, "Failed to flush to session begin file.");
                        kp.a(clsFileOutputStream, "Failed to close fatal exception file output stream.");
                    } catch (Throwable e3) {
                        th = e3;
                        closeable = clsFileOutputStream;
                        th2 = th;
                        kp.a(newInstance, "Failed to flush to session begin file.");
                        kp.a(closeable, "Failed to close fatal exception file output stream.");
                        throw th2;
                    }
                }
            } catch (Throwable e4) {
                th = e4;
                newInstance = null;
                e3 = th;
                js.a().c(CrashlyticsCore.TAG, "An error occurred in the native crash logger", e3);
                kp.a(newInstance, "Failed to flush to session begin file.");
                kp.a(clsFileOutputStream, "Failed to close fatal exception file output stream.");
            } catch (Throwable e42) {
                th = e42;
                newInstance = null;
                closeable = clsFileOutputStream;
                th2 = th;
                kp.a(newInstance, "Failed to flush to session begin file.");
                kp.a(closeable, "Failed to close fatal exception file output stream.");
                throw th2;
            }
        } catch (Throwable e422) {
            clsFileOutputStream = null;
            e3 = e422;
            newInstance = null;
            js.a().c(CrashlyticsCore.TAG, "An error occurred in the native crash logger", e3);
            kp.a(newInstance, "Failed to flush to session begin file.");
            kp.a(clsFileOutputStream, "Failed to close fatal exception file output stream.");
        } catch (Throwable e4222) {
            th2 = e4222;
            newInstance = null;
            kp.a(newInstance, "Failed to flush to session begin file.");
            kp.a(closeable, "Failed to close fatal exception file output stream.");
            throw th2;
        }
    }

    private void doWriteNonFatal(Date date, Thread thread, Throwable th) {
        Throwable th2;
        Throwable e;
        Throwable th3;
        Object obj;
        Closeable closeable;
        Flushable flushable = null;
        String currentSessionId = getCurrentSessionId();
        if (currentSessionId == null) {
            js.a().c(CrashlyticsCore.TAG, "Tried to write a non-fatal exception while no session was open.", null);
            return;
        }
        recordLoggedExceptionAnswersEvent(currentSessionId, th.getClass().getName());
        try {
            Flushable newInstance;
            js.a().a(CrashlyticsCore.TAG, "Crashlytics is logging non-fatal exception \"" + th + "\" from thread " + thread.getName());
            OutputStream clsFileOutputStream = new ClsFileOutputStream(getFilesDir(), currentSessionId + SESSION_NON_FATAL_TAG + kp.a(this.eventCounter.getAndIncrement()));
            try {
                newInstance = CodedOutputStream.newInstance(clsFileOutputStream);
            } catch (Throwable e2) {
                th2 = e2;
                newInstance = null;
                th3 = th2;
                obj = clsFileOutputStream;
                th2 = th3;
                flushable = newInstance;
                e2 = th2;
                try {
                    js.a().c(CrashlyticsCore.TAG, "An error occurred in the non-fatal exception logger", e2);
                    kp.a(flushable, "Failed to flush to non-fatal file.");
                    kp.a(closeable, "Failed to close non-fatal file output stream.");
                    trimSessionEventFiles(currentSessionId, 64);
                } catch (Throwable th4) {
                    e2 = th4;
                    kp.a(flushable, "Failed to flush to non-fatal file.");
                    kp.a(closeable, "Failed to close non-fatal file output stream.");
                    throw e2;
                }
            } catch (Throwable th5) {
                e2 = th5;
                obj = clsFileOutputStream;
                kp.a(flushable, "Failed to flush to non-fatal file.");
                kp.a(closeable, "Failed to close non-fatal file output stream.");
                throw e2;
            }
            try {
                writeSessionEvent(newInstance, date, thread, th, EVENT_TYPE_LOGGED, false);
                kp.a(newInstance, "Failed to flush to non-fatal file.");
                kp.a(clsFileOutputStream, "Failed to close non-fatal file output stream.");
            } catch (Exception e3) {
                th3 = e3;
                obj = clsFileOutputStream;
                th2 = th3;
                flushable = newInstance;
                e2 = th2;
                js.a().c(CrashlyticsCore.TAG, "An error occurred in the non-fatal exception logger", e2);
                kp.a(flushable, "Failed to flush to non-fatal file.");
                kp.a(closeable, "Failed to close non-fatal file output stream.");
                trimSessionEventFiles(currentSessionId, 64);
            } catch (Throwable th32) {
                th2 = th32;
                flushable = newInstance;
                e2 = th2;
                obj = clsFileOutputStream;
                kp.a(flushable, "Failed to flush to non-fatal file.");
                kp.a(closeable, "Failed to close non-fatal file output stream.");
                throw e2;
            }
        } catch (Exception e4) {
            e2 = e4;
            closeable = null;
            js.a().c(CrashlyticsCore.TAG, "An error occurred in the non-fatal exception logger", e2);
            kp.a(flushable, "Failed to flush to non-fatal file.");
            kp.a(closeable, "Failed to close non-fatal file output stream.");
            trimSessionEventFiles(currentSessionId, 64);
        } catch (Throwable th6) {
            e2 = th6;
            closeable = null;
            kp.a(flushable, "Failed to flush to non-fatal file.");
            kp.a(closeable, "Failed to close non-fatal file output stream.");
            throw e2;
        }
        try {
            trimSessionEventFiles(currentSessionId, 64);
        } catch (Throwable th322) {
            js.a().c(CrashlyticsCore.TAG, "An error occurred when trimming non-fatal files.", th322);
        }
    }

    private File[] ensureFileArrayNotNull(File[] fileArr) {
        return fileArr == null ? new File[0] : fileArr;
    }

    private boolean firebaseCrashExists() {
        try {
            Class.forName("com.google.firebase.crash.FirebaseCrash");
            return true;
        } catch (ClassNotFoundException e) {
            return false;
        }
    }

    private CreateReportSpiCall getCreateReportSpiCall(String str) {
        return new DefaultCreateReportSpiCall(this.crashlyticsCore, kp.c(this.crashlyticsCore.getContext(), CRASHLYTICS_API_ENDPOINT), str, this.httpRequestFactory);
    }

    private String getCurrentSessionId() {
        File[] listSortedSessionBeginFiles = listSortedSessionBeginFiles();
        return listSortedSessionBeginFiles.length > 0 ? getSessionIdFromSessionFile(listSortedSessionBeginFiles[0]) : null;
    }

    private String getPreviousSessionId() {
        File[] listSortedSessionBeginFiles = listSortedSessionBeginFiles();
        return listSortedSessionBeginFiles.length > 1 ? getSessionIdFromSessionFile(listSortedSessionBeginFiles[1]) : null;
    }

    static String getSessionIdFromSessionFile(File file) {
        return file.getName().substring(0, 35);
    }

    private File[] getTrimmedNonFatalFiles(String str, File[] fileArr, int i) {
        if (fileArr.length <= i) {
            return fileArr;
        }
        js.a().a(CrashlyticsCore.TAG, String.format(Locale.US, "Trimming down to %d logged exceptions.", new Object[]{Integer.valueOf(i)}));
        trimSessionEventFiles(str, i);
        return listFilesMatching(new FileNameContainsFilter(str + SESSION_NON_FATAL_TAG));
    }

    private UserMetaData getUserMetaData(String str) {
        return isHandlingException() ? new UserMetaData(this.crashlyticsCore.getUserIdentifier(), this.crashlyticsCore.getUserName(), this.crashlyticsCore.getUserEmail()) : new MetaDataStore(getFilesDir()).readUserData(str);
    }

    private File[] listFiles(File file) {
        return ensureFileArrayNotNull(file.listFiles());
    }

    private File[] listFilesMatching(File file, FilenameFilter filenameFilter) {
        return ensureFileArrayNotNull(file.listFiles(filenameFilter));
    }

    private File[] listFilesMatching(FilenameFilter filenameFilter) {
        return listFilesMatching(getFilesDir(), filenameFilter);
    }

    private File[] listSessionPartFilesFor(String str) {
        return listFilesMatching(new SessionPartFileFilter(str));
    }

    private File[] listSortedSessionBeginFiles() {
        File[] listSessionBeginFiles = listSessionBeginFiles();
        Arrays.sort(listSessionBeginFiles, LARGEST_FILE_NAME_FIRST);
        return listSessionBeginFiles;
    }

    private static void recordFatalExceptionAnswersEvent(String str, String str2) {
        Answers answers = (Answers) js.a(Answers.class);
        if (answers == null) {
            js.a().a(CrashlyticsCore.TAG, "Answers is not available");
        } else {
            answers.onException(new a(str, str2));
        }
    }

    private void recordFatalFirebaseEvent(long j) {
        if (firebaseCrashExists()) {
            js.a().a(CrashlyticsCore.TAG, "Skipping logging Crashlytics event to Firebase, FirebaseCrash exists");
        } else if (!this.firebaseCrashlyticsEnabled) {
        } else {
            if (this.firebaseAnalytics != null) {
                js.a().a(CrashlyticsCore.TAG, "Logging Crashlytics event to Firebase");
                Bundle bundle = new Bundle();
                bundle.putInt(FIREBASE_REALTIME, 1);
                bundle.putInt(FIREBASE_CRASH_TYPE, 1);
                bundle.putLong(FIREBASE_TIMESTAMP, j);
                this.firebaseAnalytics.logEvent(FIREBASE_ANALYTICS_ORIGIN_CRASHLYTICS, FIREBASE_APPLICATION_EXCEPTION, bundle);
                return;
            }
            js.a().a(CrashlyticsCore.TAG, "Skipping logging Crashlytics event to Firebase, no Firebase Analytics");
        }
    }

    private static void recordLoggedExceptionAnswersEvent(String str, String str2) {
        Answers answers = (Answers) js.a(Answers.class);
        if (answers == null) {
            js.a().a(CrashlyticsCore.TAG, "Answers is not available");
        } else {
            answers.onException(new b(str, str2));
        }
    }

    private void recursiveDelete(File file) {
        if (file.isDirectory()) {
            for (File recursiveDelete : file.listFiles()) {
                recursiveDelete(recursiveDelete);
            }
        }
        file.delete();
    }

    private void recursiveDelete(Set<File> set) {
        for (File recursiveDelete : set) {
            recursiveDelete(recursiveDelete);
        }
    }

    private void retainSessions(File[] fileArr, Set<String> set) {
        for (File file : fileArr) {
            String name = file.getName();
            Matcher matcher = SESSION_FILE_PATTERN.matcher(name);
            if (!matcher.matches()) {
                js.a().a(CrashlyticsCore.TAG, "Deleting unknown file: " + name);
                file.delete();
            } else if (!set.contains(matcher.group(1))) {
                js.a().a(CrashlyticsCore.TAG, "Trimming session file: " + name);
                file.delete();
            }
        }
    }

    private void sendSessionReports(ni niVar) {
        if (niVar == null) {
            js.a().d(CrashlyticsCore.TAG, "Cannot send reports. Settings are unavailable.");
            return;
        }
        Context context = this.crashlyticsCore.getContext();
        ReportUploader reportUploader = new ReportUploader(this.appData.apiKey, getCreateReportSpiCall(niVar.a.d), this.reportFilesProvider, this.handlingExceptionCheck);
        for (File sessionReport : listCompleteSessionFiles()) {
            this.backgroundWorker.submit(new SendReportRunnable(context, new SessionReport(sessionReport, SEND_AT_CRASHTIME_HEADER), reportUploader));
        }
    }

    private boolean shouldPromptUserBeforeSendingCrashReports(ni niVar) {
        return (niVar == null || !niVar.d.a || this.preferenceManager.shouldAlwaysSendReports()) ? false : true;
    }

    private void synthesizeSessionFile(File file, String str, File[] fileArr, File file2) {
        ClsFileOutputStream clsFileOutputStream;
        Throwable e;
        Throwable th;
        Closeable closeable;
        boolean z = file2 != null;
        File fatalSessionFilesDir = z ? getFatalSessionFilesDir() : getNonFatalSessionFilesDir();
        if (!fatalSessionFilesDir.exists()) {
            fatalSessionFilesDir.mkdirs();
        }
        try {
            clsFileOutputStream = new ClsFileOutputStream(fatalSessionFilesDir, str);
            try {
                Flushable newInstance = CodedOutputStream.newInstance((OutputStream) clsFileOutputStream);
                js.a().a(CrashlyticsCore.TAG, "Collecting SessionStart data for session ID " + str);
                writeToCosFromFile(newInstance, file);
                newInstance.writeUInt64(4, new Date().getTime() / 1000);
                newInstance.writeBool(5, z);
                newInstance.writeUInt32(11, 1);
                newInstance.writeEnum(12, 3);
                writeInitialPartsTo(newInstance, str);
                writeNonFatalEventsTo(newInstance, fileArr, str);
                if (z) {
                    writeToCosFromFile(newInstance, file2);
                }
                kp.a(newInstance, "Error flushing session file stream");
                kp.a(clsFileOutputStream, "Failed to close CLS file");
            } catch (Exception e2) {
                e = e2;
                try {
                    js.a().c(CrashlyticsCore.TAG, "Failed to write session file for session ID: " + str, e);
                    kp.a(null, "Error flushing session file stream");
                    closeWithoutRenamingOrLog(clsFileOutputStream);
                } catch (Throwable e3) {
                    Throwable th2 = e3;
                    Object obj = clsFileOutputStream;
                    th = th2;
                    kp.a(null, "Error flushing session file stream");
                    kp.a(closeable, "Failed to close CLS file");
                    throw th;
                }
            }
        } catch (Exception e4) {
            e3 = e4;
            clsFileOutputStream = null;
            js.a().c(CrashlyticsCore.TAG, "Failed to write session file for session ID: " + str, e3);
            kp.a(null, "Error flushing session file stream");
            closeWithoutRenamingOrLog(clsFileOutputStream);
        } catch (Throwable e32) {
            th = e32;
            closeable = null;
            kp.a(null, "Error flushing session file stream");
            kp.a(closeable, "Failed to close CLS file");
            throw th;
        }
    }

    private void trimInvalidSessionFiles() {
        File invalidFilesDir = getInvalidFilesDir();
        if (invalidFilesDir.exists()) {
            File[] listFilesMatching = listFilesMatching(invalidFilesDir, new InvalidPartFileFilter());
            Arrays.sort(listFilesMatching, Collections.reverseOrder());
            Set hashSet = new HashSet();
            for (int i = 0; i < listFilesMatching.length && hashSet.size() < 4; i++) {
                hashSet.add(getSessionIdFromSessionFile(listFilesMatching[i]));
            }
            retainSessions(listFiles(invalidFilesDir), hashSet);
        }
    }

    private void trimOpenSessions(int i) {
        Set hashSet = new HashSet();
        File[] listSortedSessionBeginFiles = listSortedSessionBeginFiles();
        int min = Math.min(i, listSortedSessionBeginFiles.length);
        for (int i2 = 0; i2 < min; i2++) {
            hashSet.add(getSessionIdFromSessionFile(listSortedSessionBeginFiles[i2]));
        }
        this.logFileManager.discardOldLogFiles(hashSet);
        retainSessions(listFilesMatching(new AnySessionPartFileFilter()), hashSet);
    }

    private void trimSessionEventFiles(String str, int i) {
        Utils.capFileCount(getFilesDir(), new FileNameContainsFilter(str + SESSION_NON_FATAL_TAG), i, SMALLEST_FILE_NAME_FIRST);
    }

    private void writeBeginSession(String str, Date date) throws Exception {
        final String format = String.format(Locale.US, GENERATOR_FORMAT, new Object[]{this.crashlyticsCore.getVersion()});
        final long time = date.getTime() / 1000;
        final String str2 = str;
        writeSessionPartFile(str, SESSION_BEGIN_TAG, new CodedOutputStreamWriteAction() {
            public void writeTo(CodedOutputStream codedOutputStream) throws Exception {
                SessionProtobufHelper.writeBeginSession(codedOutputStream, str2, format, time);
            }
        });
        str2 = str;
        writeFile(str, "BeginSession.json", new FileOutputStreamWriteAction() {
            public void writeTo(FileOutputStream fileOutputStream) throws Exception {
                fileOutputStream.write(new JSONObject(new HashMap<String, Object>() {
                    {
                        put("session_id", str2);
                        put("generator", format);
                        put("started_at_seconds", Long.valueOf(time));
                    }
                }).toString().getBytes());
            }
        });
    }

    private void writeFatal(Date date, Thread thread, Throwable th) {
        Throwable th2;
        Throwable e;
        Throwable th3;
        Object obj;
        Closeable closeable;
        Flushable flushable = null;
        try {
            String currentSessionId = getCurrentSessionId();
            if (currentSessionId == null) {
                js.a().c(CrashlyticsCore.TAG, "Tried to write a fatal exception while no session was open.", null);
                kp.a(null, "Failed to flush to session begin file.");
                kp.a(null, "Failed to close fatal exception file output stream.");
                return;
            }
            Flushable newInstance;
            recordFatalExceptionAnswersEvent(currentSessionId, th.getClass().getName());
            recordFatalFirebaseEvent(date.getTime());
            OutputStream clsFileOutputStream = new ClsFileOutputStream(getFilesDir(), currentSessionId + SESSION_FATAL_TAG);
            try {
                newInstance = CodedOutputStream.newInstance(clsFileOutputStream);
            } catch (Throwable e2) {
                th2 = e2;
                newInstance = null;
                th3 = th2;
                obj = clsFileOutputStream;
                th2 = th3;
                flushable = newInstance;
                e2 = th2;
                try {
                    js.a().c(CrashlyticsCore.TAG, "An error occurred in the fatal exception logger", e2);
                    kp.a(flushable, "Failed to flush to session begin file.");
                    kp.a(closeable, "Failed to close fatal exception file output stream.");
                } catch (Throwable th4) {
                    e2 = th4;
                    kp.a(flushable, "Failed to flush to session begin file.");
                    kp.a(closeable, "Failed to close fatal exception file output stream.");
                    throw e2;
                }
            } catch (Throwable th5) {
                e2 = th5;
                obj = clsFileOutputStream;
                kp.a(flushable, "Failed to flush to session begin file.");
                kp.a(closeable, "Failed to close fatal exception file output stream.");
                throw e2;
            }
            try {
                writeSessionEvent(newInstance, date, thread, th, EVENT_TYPE_CRASH, true);
                kp.a(newInstance, "Failed to flush to session begin file.");
                kp.a(clsFileOutputStream, "Failed to close fatal exception file output stream.");
            } catch (Exception e3) {
                th3 = e3;
                obj = clsFileOutputStream;
                th2 = th3;
                flushable = newInstance;
                e2 = th2;
                js.a().c(CrashlyticsCore.TAG, "An error occurred in the fatal exception logger", e2);
                kp.a(flushable, "Failed to flush to session begin file.");
                kp.a(closeable, "Failed to close fatal exception file output stream.");
            } catch (Throwable th32) {
                th2 = th32;
                flushable = newInstance;
                e2 = th2;
                obj = clsFileOutputStream;
                kp.a(flushable, "Failed to flush to session begin file.");
                kp.a(closeable, "Failed to close fatal exception file output stream.");
                throw e2;
            }
        } catch (Exception e4) {
            e2 = e4;
            closeable = null;
            js.a().c(CrashlyticsCore.TAG, "An error occurred in the fatal exception logger", e2);
            kp.a(flushable, "Failed to flush to session begin file.");
            kp.a(closeable, "Failed to close fatal exception file output stream.");
        } catch (Throwable th6) {
            e2 = th6;
            closeable = null;
            kp.a(flushable, "Failed to flush to session begin file.");
            kp.a(closeable, "Failed to close fatal exception file output stream.");
            throw e2;
        }
    }

    private void writeFile(String str, String str2, FileOutputStreamWriteAction fileOutputStreamWriteAction) throws Exception {
        Throwable th;
        Closeable fileOutputStream;
        try {
            fileOutputStream = new FileOutputStream(new File(getFilesDir(), str + str2));
            try {
                fileOutputStreamWriteAction.writeTo(fileOutputStream);
                kp.a(fileOutputStream, "Failed to close " + str2 + " file.");
            } catch (Throwable th2) {
                th = th2;
                kp.a(fileOutputStream, "Failed to close " + str2 + " file.");
                throw th;
            }
        } catch (Throwable th3) {
            th = th3;
            fileOutputStream = null;
            kp.a(fileOutputStream, "Failed to close " + str2 + " file.");
            throw th;
        }
    }

    private void writeInitialPartsTo(CodedOutputStream codedOutputStream, String str) throws IOException {
        for (String str2 : INITIAL_SESSION_PART_TAGS) {
            File[] listFilesMatching = listFilesMatching(new FileNameContainsFilter(str + str2 + ClsFileOutputStream.SESSION_FILE_EXTENSION));
            if (listFilesMatching.length == 0) {
                js.a().c(CrashlyticsCore.TAG, "Can't find " + str2 + " data for session ID " + str, null);
            } else {
                js.a().a(CrashlyticsCore.TAG, "Collecting " + str2 + " data for session ID " + str);
                writeToCosFromFile(codedOutputStream, listFilesMatching[0]);
            }
        }
    }

    private static void writeNonFatalEventsTo(CodedOutputStream codedOutputStream, File[] fileArr, String str) {
        Arrays.sort(fileArr, kp.a);
        for (File name : fileArr) {
            try {
                js.a().a(CrashlyticsCore.TAG, String.format(Locale.US, "Found Non Fatal for session ID %s in %s ", new Object[]{str, name.getName()}));
                writeToCosFromFile(codedOutputStream, name);
            } catch (Throwable e) {
                js.a().c(CrashlyticsCore.TAG, "Error writting non-fatal to session.", e);
            }
        }
    }

    private void writeSessionApp(String str) throws Exception {
        final String str2 = this.idManager.b;
        final String str3 = this.appData.versionCode;
        final String str4 = this.appData.versionName;
        final String a = this.idManager.a();
        final int i = ks.a(this.appData.installerPackageName).e;
        writeSessionPartFile(str, SESSION_APP_TAG, new CodedOutputStreamWriteAction() {
            public void writeTo(CodedOutputStream codedOutputStream) throws Exception {
                SessionProtobufHelper.writeSessionApp(codedOutputStream, str2, CrashlyticsController.this.appData.apiKey, str3, str4, a, i, CrashlyticsController.this.unityVersion);
            }
        });
        writeFile(str, "SessionApp.json", new FileOutputStreamWriteAction() {
            public void writeTo(FileOutputStream fileOutputStream) throws Exception {
                fileOutputStream.write(new JSONObject(new HashMap<String, Object>() {
                    {
                        put("app_identifier", str2);
                        put("api_key", CrashlyticsController.this.appData.apiKey);
                        put("version_code", str3);
                        put("version_name", str4);
                        put("install_uuid", a);
                        put("delivery_mechanism", Integer.valueOf(i));
                        put("unity_version", TextUtils.isEmpty(CrashlyticsController.this.unityVersion) ? "" : CrashlyticsController.this.unityVersion);
                    }
                }).toString().getBytes());
            }
        });
    }

    private void writeSessionDevice(String str) throws Exception {
        Context context = this.crashlyticsCore.getContext();
        StatFs statFs = new StatFs(Environment.getDataDirectory().getPath());
        final int a = kp.a();
        final int availableProcessors = Runtime.getRuntime().availableProcessors();
        final long b = kp.b();
        final long blockCount = ((long) statFs.getBlockCount()) * ((long) statFs.getBlockSize());
        final boolean e = kp.e(context);
        final Map c = this.idManager.c();
        final int g = kp.g(context);
        writeSessionPartFile(str, SESSION_DEVICE_TAG, new CodedOutputStreamWriteAction() {
            public void writeTo(CodedOutputStream codedOutputStream) throws Exception {
                SessionProtobufHelper.writeSessionDevice(codedOutputStream, a, Build.MODEL, availableProcessors, b, blockCount, e, c, g, Build.MANUFACTURER, Build.PRODUCT);
            }
        });
        writeFile(str, "SessionDevice.json", new FileOutputStreamWriteAction() {
            public void writeTo(FileOutputStream fileOutputStream) throws Exception {
                fileOutputStream.write(new JSONObject(new HashMap<String, Object>() {
                    {
                        put("arch", Integer.valueOf(a));
                        put("build_model", Build.MODEL);
                        put("available_processors", Integer.valueOf(availableProcessors));
                        put("total_ram", Long.valueOf(b));
                        put("disk_space", Long.valueOf(blockCount));
                        put("is_emulator", Boolean.valueOf(e));
                        put("ids", c);
                        put("state", Integer.valueOf(g));
                        put("build_manufacturer", Build.MANUFACTURER);
                        put("build_product", Build.PRODUCT);
                    }
                }).toString().getBytes());
            }
        });
    }

    private void writeSessionEvent(CodedOutputStream codedOutputStream, Date date, Thread thread, Throwable th, String str, boolean z) throws Exception {
        Thread[] threadArr;
        Map treeMap;
        TrimmedThrowableData trimmedThrowableData = new TrimmedThrowableData(th, this.stackTraceTrimmingStrategy);
        Context context = this.crashlyticsCore.getContext();
        long time = date.getTime() / 1000;
        Float c = kp.c(context);
        int a = kp.a(context, this.devicePowerStateListener.isPowerConnected());
        boolean d = kp.d(context);
        int i = context.getResources().getConfiguration().orientation;
        long b = kp.b();
        long b2 = kp.b(context);
        long c2 = kp.c(Environment.getDataDirectory().getPath());
        RunningAppProcessInfo a2 = kp.a(context.getPackageName(), context);
        List linkedList = new LinkedList();
        StackTraceElement[] stackTraceElementArr = trimmedThrowableData.stacktrace;
        String str2 = this.appData.buildId;
        String str3 = this.idManager.b;
        if (z) {
            Map allStackTraces = Thread.getAllStackTraces();
            threadArr = new Thread[allStackTraces.size()];
            int i2 = 0;
            for (Entry entry : allStackTraces.entrySet()) {
                threadArr[i2] = (Thread) entry.getKey();
                linkedList.add(this.stackTraceTrimmingStrategy.getTrimmedStackTrace((StackTraceElement[]) entry.getValue()));
                i2++;
            }
        } else {
            threadArr = new Thread[0];
        }
        if (kp.a(context, COLLECT_CUSTOM_KEYS, true)) {
            allStackTraces = this.crashlyticsCore.getAttributes();
            treeMap = (allStackTraces == null || allStackTraces.size() <= 1) ? allStackTraces : new TreeMap(allStackTraces);
        } else {
            treeMap = new TreeMap();
        }
        SessionProtobufHelper.writeSessionEvent(codedOutputStream, time, str, trimmedThrowableData, thread, stackTraceElementArr, threadArr, linkedList, treeMap, this.logFileManager, a2, i, str3, str2, c, a, d, b - b2, c2);
    }

    private void writeSessionOS(String str) throws Exception {
        final boolean f = kp.f(this.crashlyticsCore.getContext());
        writeSessionPartFile(str, SESSION_OS_TAG, new CodedOutputStreamWriteAction() {
            public void writeTo(CodedOutputStream codedOutputStream) throws Exception {
                SessionProtobufHelper.writeSessionOS(codedOutputStream, VERSION.RELEASE, VERSION.CODENAME, f);
            }
        });
        writeFile(str, "SessionOS.json", new FileOutputStreamWriteAction() {
            public void writeTo(FileOutputStream fileOutputStream) throws Exception {
                fileOutputStream.write(new JSONObject(new HashMap<String, Object>() {
                    {
                        put("version", VERSION.RELEASE);
                        put("build_version", VERSION.CODENAME);
                        put("is_rooted", Boolean.valueOf(f));
                    }
                }).toString().getBytes());
            }
        });
    }

    private void writeSessionPartFile(String str, String str2, CodedOutputStreamWriteAction codedOutputStreamWriteAction) throws Exception {
        Flushable newInstance;
        Throwable th;
        Throwable th2;
        Closeable clsFileOutputStream;
        try {
            clsFileOutputStream = new ClsFileOutputStream(getFilesDir(), str + str2);
            try {
                newInstance = CodedOutputStream.newInstance((OutputStream) clsFileOutputStream);
                try {
                    codedOutputStreamWriteAction.writeTo(newInstance);
                    kp.a(newInstance, "Failed to flush to session " + str2 + " file.");
                    kp.a(clsFileOutputStream, "Failed to close session " + str2 + " file.");
                } catch (Throwable th3) {
                    th = th3;
                    kp.a(newInstance, "Failed to flush to session " + str2 + " file.");
                    kp.a(clsFileOutputStream, "Failed to close session " + str2 + " file.");
                    throw th;
                }
            } catch (Throwable th4) {
                th2 = th4;
                newInstance = null;
                th = th2;
                kp.a(newInstance, "Failed to flush to session " + str2 + " file.");
                kp.a(clsFileOutputStream, "Failed to close session " + str2 + " file.");
                throw th;
            }
        } catch (Throwable th5) {
            newInstance = null;
            th2 = th5;
            clsFileOutputStream = null;
            th = th2;
            kp.a(newInstance, "Failed to flush to session " + str2 + " file.");
            kp.a(clsFileOutputStream, "Failed to close session " + str2 + " file.");
            throw th;
        }
    }

    private void writeSessionPartsToSessionFile(File file, String str, int i) {
        js.a().a(CrashlyticsCore.TAG, "Collecting session parts for ID " + str);
        File[] listFilesMatching = listFilesMatching(new FileNameContainsFilter(str + SESSION_FATAL_TAG));
        boolean z = listFilesMatching != null && listFilesMatching.length > 0;
        js.a().a(CrashlyticsCore.TAG, String.format(Locale.US, "Session %s has fatal exception: %s", new Object[]{str, Boolean.valueOf(z)}));
        File[] listFilesMatching2 = listFilesMatching(new FileNameContainsFilter(str + SESSION_NON_FATAL_TAG));
        boolean z2 = listFilesMatching2 != null && listFilesMatching2.length > 0;
        js.a().a(CrashlyticsCore.TAG, String.format(Locale.US, "Session %s has non-fatal exceptions: %s", new Object[]{str, Boolean.valueOf(z2)}));
        if (z || z2) {
            synthesizeSessionFile(file, str, getTrimmedNonFatalFiles(str, listFilesMatching2, i), z ? listFilesMatching[0] : null);
        } else {
            js.a().a(CrashlyticsCore.TAG, "No events present for session ID " + str);
        }
        js.a().a(CrashlyticsCore.TAG, "Removing session part files for ID " + str);
        deleteSessionPartFilesFor(str);
    }

    private void writeSessionUser(String str) throws Exception {
        String str2 = SESSION_USER_TAG;
        final UserMetaData userMetaData = getUserMetaData(str);
        writeSessionPartFile(str, str2, new CodedOutputStreamWriteAction() {
            public void writeTo(CodedOutputStream codedOutputStream) throws Exception {
                SessionProtobufHelper.writeSessionUser(codedOutputStream, userMetaData.id, userMetaData.name, userMetaData.email);
            }
        });
    }

    private static void writeToCosFromFile(CodedOutputStream codedOutputStream, File file) throws IOException {
        Throwable th;
        if (file.exists()) {
            Closeable fileInputStream;
            try {
                fileInputStream = new FileInputStream(file);
                try {
                    copyToCodedOutputStream(fileInputStream, codedOutputStream, (int) file.length());
                    kp.a(fileInputStream, "Failed to close file input stream.");
                    return;
                } catch (Throwable th2) {
                    th = th2;
                    kp.a(fileInputStream, "Failed to close file input stream.");
                    throw th;
                }
            } catch (Throwable th3) {
                th = th3;
                fileInputStream = null;
                kp.a(fileInputStream, "Failed to close file input stream.");
                throw th;
            }
        }
        js.a().c(CrashlyticsCore.TAG, "Tried to include a file that doesn't exist: " + file.getName(), null);
    }

    void cacheKeyData(final Map<String, String> map) {
        this.backgroundWorker.submit(new Callable<Void>() {
            public Void call() throws Exception {
                new MetaDataStore(CrashlyticsController.this.getFilesDir()).writeKeyData(CrashlyticsController.this.getCurrentSessionId(), map);
                return null;
            }
        });
    }

    void cacheUserData(final String str, final String str2, final String str3) {
        this.backgroundWorker.submit(new Callable<Void>() {
            public Void call() throws Exception {
                new MetaDataStore(CrashlyticsController.this.getFilesDir()).writeUserData(CrashlyticsController.this.getCurrentSessionId(), new UserMetaData(str, str2, str3));
                return null;
            }
        });
    }

    void cleanInvalidTempFiles() {
        this.backgroundWorker.submit(new Runnable() {
            public void run() {
                CrashlyticsController.this.doCleanInvalidTempFiles(CrashlyticsController.this.listFilesMatching(new InvalidPartFileFilter()));
            }
        });
    }

    void doCleanInvalidTempFiles(File[] fileArr) {
        int length;
        File file;
        int i = 0;
        final Set hashSet = new HashSet();
        for (File file2 : fileArr) {
            js.a().a(CrashlyticsCore.TAG, "Found invalid session part file: " + file2);
            hashSet.add(getSessionIdFromSessionFile(file2));
        }
        if (!hashSet.isEmpty()) {
            File invalidFilesDir = getInvalidFilesDir();
            if (!invalidFilesDir.exists()) {
                invalidFilesDir.mkdir();
            }
            File[] listFilesMatching = listFilesMatching(new FilenameFilter() {
                public boolean accept(File file, String str) {
                    return str.length() < 35 ? false : hashSet.contains(str.substring(0, 35));
                }
            });
            length = listFilesMatching.length;
            while (i < length) {
                file2 = listFilesMatching[i];
                js.a().a(CrashlyticsCore.TAG, "Moving session file: " + file2);
                if (!file2.renameTo(new File(invalidFilesDir, file2.getName()))) {
                    js.a().a(CrashlyticsCore.TAG, "Could not move session file. Deleting " + file2);
                    file2.delete();
                }
                i++;
            }
            trimInvalidSessionFiles();
        }
    }

    void doCloseSessions(ne neVar) throws Exception {
        doCloseSessions(neVar, false);
    }

    void enableExceptionHandling(UncaughtExceptionHandler uncaughtExceptionHandler) {
        openSession();
        this.crashHandler = new CrashlyticsUncaughtExceptionHandler(new CrashListener() {
            public void onUncaughtException(Thread thread, Throwable th) {
                CrashlyticsController.this.handleUncaughtException(thread, th);
            }
        }, uncaughtExceptionHandler);
        Thread.setDefaultUncaughtExceptionHandler(this.crashHandler);
    }

    boolean finalizeSessions(final ne neVar) {
        return ((Boolean) this.backgroundWorker.submitAndWait(new Callable<Boolean>() {
            public Boolean call() throws Exception {
                if (CrashlyticsController.this.isHandlingException()) {
                    js.a().a(CrashlyticsCore.TAG, "Skipping session finalization because a crash has already occurred.");
                    return Boolean.FALSE;
                }
                js.a().a(CrashlyticsCore.TAG, "Finalizing previously open sessions.");
                CrashlyticsController.this.doCloseSessions(neVar, true);
                js.a().a(CrashlyticsCore.TAG, "Closed all previously open sessions");
                return Boolean.TRUE;
            }
        })).booleanValue();
    }

    File getFatalSessionFilesDir() {
        return new File(getFilesDir(), FATAL_SESSION_DIR);
    }

    File getFilesDir() {
        return this.fileStore.a();
    }

    File getInvalidFilesDir() {
        return new File(getFilesDir(), INVALID_CLS_CACHE_DIR);
    }

    File getNonFatalSessionFilesDir() {
        return new File(getFilesDir(), NONFATAL_SESSION_DIR);
    }

    void handleUncaughtException(final Thread thread, final Throwable th) {
        synchronized (this) {
            js.a().a(CrashlyticsCore.TAG, "Crashlytics is handling uncaught exception \"" + th + "\" from thread " + thread.getName());
            this.devicePowerStateListener.dispose();
            final Date date = new Date();
            this.backgroundWorker.submitAndWait(new Callable<Void>() {
                public Void call() throws Exception {
                    CrashlyticsController.this.crashlyticsCore.createCrashMarker();
                    CrashlyticsController.this.writeFatal(date, thread, th);
                    ni a = a.a().a();
                    ne neVar = a != null ? a.b : null;
                    CrashlyticsController.this.doCloseSessions(neVar);
                    CrashlyticsController.this.doOpenSession();
                    if (neVar != null) {
                        CrashlyticsController.this.trimSessionFiles(neVar.g);
                    }
                    if (!CrashlyticsController.this.shouldPromptUserBeforeSendingCrashReports(a)) {
                        CrashlyticsController.this.sendSessionReports(a);
                    }
                    return null;
                }
            });
        }
    }

    boolean hasOpenSession() {
        return listSessionBeginFiles().length > 0;
    }

    boolean isHandlingException() {
        return this.crashHandler != null && this.crashHandler.isHandlingException();
    }

    File[] listCompleteSessionFiles() {
        List linkedList = new LinkedList();
        Collections.addAll(linkedList, listFilesMatching(getFatalSessionFilesDir(), SESSION_FILE_FILTER));
        Collections.addAll(linkedList, listFilesMatching(getNonFatalSessionFilesDir(), SESSION_FILE_FILTER));
        Collections.addAll(linkedList, listFilesMatching(getFilesDir(), SESSION_FILE_FILTER));
        return (File[]) linkedList.toArray(new File[linkedList.size()]);
    }

    File[] listSessionBeginFiles() {
        return listFilesMatching(SESSION_BEGIN_FILE_FILTER);
    }

    void openSession() {
        this.backgroundWorker.submit(new Callable<Void>() {
            public Void call() throws Exception {
                CrashlyticsController.this.doOpenSession();
                return null;
            }
        });
    }

    void submitAllReports(float f, ni niVar) {
        if (niVar == null) {
            js.a().d(CrashlyticsCore.TAG, "Could not send reports. Settings are not available.");
            return;
        }
        new ReportUploader(this.appData.apiKey, getCreateReportSpiCall(niVar.a.d), this.reportFilesProvider, this.handlingExceptionCheck).uploadReports(f, shouldPromptUserBeforeSendingCrashReports(niVar) ? new PrivacyDialogCheck(this.crashlyticsCore, this.preferenceManager, niVar.c) : new AlwaysSendCheck());
    }

    void trimSessionFiles(int i) {
        int capFileCount = i - Utils.capFileCount(getFatalSessionFilesDir(), i, SMALLEST_FILE_NAME_FIRST);
        Utils.capFileCount(getFilesDir(), SESSION_FILE_FILTER, capFileCount - Utils.capFileCount(getNonFatalSessionFilesDir(), capFileCount, SMALLEST_FILE_NAME_FIRST), SMALLEST_FILE_NAME_FIRST);
    }

    void writeExternalCrashEvent(final SessionEventData sessionEventData) {
        this.backgroundWorker.submit(new Callable<Void>() {
            public Void call() throws Exception {
                if (!CrashlyticsController.this.isHandlingException()) {
                    CrashlyticsController.this.doWriteExternalCrashEvent(sessionEventData);
                }
                return null;
            }
        });
    }

    void writeNonFatalException(final Thread thread, final Throwable th) {
        final Date date = new Date();
        this.backgroundWorker.submit(new Runnable() {
            public void run() {
                if (!CrashlyticsController.this.isHandlingException()) {
                    CrashlyticsController.this.doWriteNonFatal(date, thread, th);
                }
            }
        });
    }

    void writeToLog(final long j, final String str) {
        this.backgroundWorker.submit(new Callable<Void>() {
            public Void call() throws Exception {
                if (!CrashlyticsController.this.isHandlingException()) {
                    CrashlyticsController.this.logFileManager.writeToLog(j, str);
                }
                return null;
            }
        });
    }
}
