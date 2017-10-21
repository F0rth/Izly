package defpackage;

import android.content.Context;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.TreeSet;
import java.util.concurrent.CopyOnWriteArrayList;

public abstract class lv<T> {
    public static final int MAX_BYTE_SIZE_PER_FILE = 8000;
    public static final int MAX_FILES_IN_BATCH = 1;
    public static final int MAX_FILES_TO_KEEP = 100;
    public static final String ROLL_OVER_FILE_NAME_SEPARATOR = "_";
    protected final Context context;
    protected final kr currentTimeProvider;
    private final int defaultMaxFilesToKeep;
    protected final lw eventStorage;
    protected volatile long lastRollOverTime;
    protected final List<lx> rollOverListeners = new CopyOnWriteArrayList();
    protected final lu<T> transform;

    public lv(Context context, lu<T> luVar, kr krVar, lw lwVar, int i) throws IOException {
        this.context = context.getApplicationContext();
        this.transform = luVar;
        this.eventStorage = lwVar;
        this.currentTimeProvider = krVar;
        this.lastRollOverTime = this.currentTimeProvider.a();
        this.defaultMaxFilesToKeep = i;
    }

    private void rollFileOverIfNeeded(int i) throws IOException {
        if (!this.eventStorage.a(i, getMaxByteSizePerFile())) {
            kp.a(this.context, 4, String.format(Locale.US, "session analytics events file is %d bytes, new event is %d bytes, this is over flush limit of %d, rolling it over", new Object[]{Integer.valueOf(this.eventStorage.a()), Integer.valueOf(i), Integer.valueOf(getMaxByteSizePerFile())}));
            rollFileOver();
        }
    }

    private void triggerRollOverOnListeners(String str) {
        for (lx onRollOver : this.rollOverListeners) {
            try {
                onRollOver.onRollOver(str);
            } catch (Exception e) {
                kp.b(this.context, "One of the roll over listeners threw an exception");
            }
        }
    }

    public void deleteAllEventsFiles() {
        this.eventStorage.a(this.eventStorage.c());
        this.eventStorage.d();
    }

    public void deleteOldestInRollOverIfOverMax() {
        List<File> c = this.eventStorage.c();
        int maxFilesToKeep = getMaxFilesToKeep();
        if (c.size() > maxFilesToKeep) {
            int size = c.size() - maxFilesToKeep;
            kp.a(this.context, String.format(Locale.US, "Found %d files in  roll over directory, this is greater than %d, deleting %d oldest files", new Object[]{Integer.valueOf(c.size()), Integer.valueOf(maxFilesToKeep), Integer.valueOf(size)}));
            TreeSet treeSet = new TreeSet(new lv$1(this));
            for (File file : c) {
                treeSet.add(new lv$a(file, parseCreationTimestampFromFileName(file.getName())));
            }
            List arrayList = new ArrayList();
            Iterator it = treeSet.iterator();
            while (it.hasNext()) {
                arrayList.add(((lv$a) it.next()).a);
                if (arrayList.size() == size) {
                    break;
                }
            }
            this.eventStorage.a(arrayList);
        }
    }

    public void deleteSentFiles(List<File> list) {
        this.eventStorage.a((List) list);
    }

    public abstract String generateUniqueRollOverFileName();

    public List<File> getBatchOfFilesToSend() {
        return this.eventStorage.a(1);
    }

    public long getLastRollOverTime() {
        return this.lastRollOverTime;
    }

    public int getMaxByteSizePerFile() {
        return MAX_BYTE_SIZE_PER_FILE;
    }

    public int getMaxFilesToKeep() {
        return this.defaultMaxFilesToKeep;
    }

    public long parseCreationTimestampFromFileName(String str) {
        long j = 0;
        String[] split = str.split(ROLL_OVER_FILE_NAME_SEPARATOR);
        if (split.length == 3) {
            try {
                j = Long.valueOf(split[2]).longValue();
            } catch (NumberFormatException e) {
            }
        }
        return j;
    }

    public void registerRollOverListener(lx lxVar) {
        if (lxVar != null) {
            this.rollOverListeners.add(lxVar);
        }
    }

    public boolean rollFileOver() throws IOException {
        boolean z = true;
        String str = null;
        if (this.eventStorage.b()) {
            z = false;
        } else {
            str = generateUniqueRollOverFileName();
            this.eventStorage.a(str);
            kp.a(this.context, 4, String.format(Locale.US, "generated new file %s", new Object[]{str}));
            this.lastRollOverTime = this.currentTimeProvider.a();
        }
        triggerRollOverOnListeners(str);
        return z;
    }

    public void writeEvent(T t) throws IOException {
        byte[] toBytes = this.transform.toBytes(t);
        rollFileOverIfNeeded(toBytes.length);
        this.eventStorage.a(toBytes);
    }
}
