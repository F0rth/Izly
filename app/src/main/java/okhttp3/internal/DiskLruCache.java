package okhttp3.internal;

import com.tekle.oss.android.animation.FlipAnimation;
import defpackage.nw;
import defpackage.nx;
import defpackage.og;
import defpackage.om;
import defpackage.on;
import defpackage.oo;
import java.io.Closeable;
import java.io.EOFException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.Flushable;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.NoSuchElementException;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;
import okhttp3.internal.io.FileSystem;

public final class DiskLruCache implements Closeable, Flushable {
    static final /* synthetic */ boolean $assertionsDisabled = (!DiskLruCache.class.desiredAssertionStatus());
    static final long ANY_SEQUENCE_NUMBER = -1;
    private static final String CLEAN = "CLEAN";
    private static final String DIRTY = "DIRTY";
    static final String JOURNAL_FILE = "journal";
    static final String JOURNAL_FILE_BACKUP = "journal.bkp";
    static final String JOURNAL_FILE_TEMP = "journal.tmp";
    static final Pattern LEGAL_KEY_PATTERN = Pattern.compile("[a-z0-9_-]{1,120}");
    static final String MAGIC = "libcore.io.DiskLruCache";
    private static final om NULL_SINK = new om() {
        public final void close() throws IOException {
        }

        public final void flush() throws IOException {
        }

        public final oo timeout() {
            return oo.NONE;
        }

        public final void write(nw nwVar, long j) throws IOException {
            nwVar.g(j);
        }
    };
    private static final String READ = "READ";
    private static final String REMOVE = "REMOVE";
    static final String VERSION_1 = "1";
    private final int appVersion;
    private final Runnable cleanupRunnable = new Runnable() {
        public void run() {
            int i = 0;
            synchronized (DiskLruCache.this) {
                if (!DiskLruCache.this.initialized) {
                    i = 1;
                }
                if ((i | DiskLruCache.this.closed) != 0) {
                    return;
                }
                try {
                    DiskLruCache.this.trimToSize();
                } catch (IOException e) {
                    DiskLruCache.this.mostRecentTrimFailed = true;
                }
                try {
                    if (DiskLruCache.this.journalRebuildRequired()) {
                        DiskLruCache.this.rebuildJournal();
                        DiskLruCache.this.redundantOpCount = 0;
                    }
                } catch (IOException e2) {
                    DiskLruCache.this.mostRecentRebuildFailed = true;
                    DiskLruCache.this.journalWriter = og.a(DiskLruCache.NULL_SINK);
                }
            }
        }
    };
    private boolean closed;
    private final File directory;
    private final Executor executor;
    private final FileSystem fileSystem;
    private boolean hasJournalErrors;
    private boolean initialized;
    private final File journalFile;
    private final File journalFileBackup;
    private final File journalFileTmp;
    private nx journalWriter;
    private final LinkedHashMap<String, Entry> lruEntries = new LinkedHashMap(0, FlipAnimation.SCALE_DEFAULT, true);
    private long maxSize;
    private boolean mostRecentRebuildFailed;
    private boolean mostRecentTrimFailed;
    private long nextSequenceNumber = 0;
    private int redundantOpCount;
    private long size = 0;
    private final int valueCount;

    public final class Editor {
        private boolean done;
        private final Entry entry;
        private final boolean[] written;

        private Editor(Entry entry) {
            this.entry = entry;
            this.written = entry.readable ? null : new boolean[DiskLruCache.this.valueCount];
        }

        public final void abort() throws IOException {
            synchronized (DiskLruCache.this) {
                if (this.done) {
                    throw new IllegalStateException();
                }
                if (this.entry.currentEditor == this) {
                    DiskLruCache.this.completeEdit(this, false);
                }
                this.done = true;
            }
        }

        public final void abortUnlessCommitted() {
            synchronized (DiskLruCache.this) {
                if (!this.done && this.entry.currentEditor == this) {
                    try {
                        DiskLruCache.this.completeEdit(this, false);
                    } catch (IOException e) {
                    }
                }
            }
        }

        public final void commit() throws IOException {
            synchronized (DiskLruCache.this) {
                if (this.done) {
                    throw new IllegalStateException();
                }
                if (this.entry.currentEditor == this) {
                    DiskLruCache.this.completeEdit(this, true);
                }
                this.done = true;
            }
        }

        final void detach() {
            if (this.entry.currentEditor == this) {
                for (int i = 0; i < DiskLruCache.this.valueCount; i++) {
                    try {
                        DiskLruCache.this.fileSystem.delete(this.entry.dirtyFiles[i]);
                    } catch (IOException e) {
                    }
                }
                this.entry.currentEditor = null;
            }
        }

        public final om newSink(int i) throws IOException {
            om access$900;
            synchronized (DiskLruCache.this) {
                if (this.done) {
                    throw new IllegalStateException();
                } else if (this.entry.currentEditor != this) {
                    access$900 = DiskLruCache.NULL_SINK;
                } else {
                    if (!this.entry.readable) {
                        this.written[i] = true;
                    }
                    try {
                        access$900 = new FaultHidingSink(DiskLruCache.this.fileSystem.sink(this.entry.dirtyFiles[i])) {
                            protected void onException(IOException iOException) {
                                synchronized (DiskLruCache.this) {
                                    Editor.this.detach();
                                }
                            }
                        };
                    } catch (FileNotFoundException e) {
                        access$900 = DiskLruCache.NULL_SINK;
                    }
                }
            }
            return access$900;
        }

        public final on newSource(int i) throws IOException {
            on onVar = null;
            synchronized (DiskLruCache.this) {
                if (this.done) {
                    throw new IllegalStateException();
                } else if (this.entry.readable && this.entry.currentEditor == this) {
                    try {
                        onVar = DiskLruCache.this.fileSystem.source(this.entry.cleanFiles[i]);
                    } catch (FileNotFoundException e) {
                    }
                }
            }
            return onVar;
        }
    }

    final class Entry {
        private final File[] cleanFiles;
        private Editor currentEditor;
        private final File[] dirtyFiles;
        private final String key;
        private final long[] lengths;
        private boolean readable;
        private long sequenceNumber;

        private Entry(String str) {
            this.key = str;
            this.lengths = new long[DiskLruCache.this.valueCount];
            this.cleanFiles = new File[DiskLruCache.this.valueCount];
            this.dirtyFiles = new File[DiskLruCache.this.valueCount];
            StringBuilder append = new StringBuilder(str).append('.');
            int length = append.length();
            for (int i = 0; i < DiskLruCache.this.valueCount; i++) {
                append.append(i);
                this.cleanFiles[i] = new File(DiskLruCache.this.directory, append.toString());
                append.append(".tmp");
                this.dirtyFiles[i] = new File(DiskLruCache.this.directory, append.toString());
                append.setLength(length);
            }
        }

        private IOException invalidLengths(String[] strArr) throws IOException {
            throw new IOException("unexpected journal line: " + Arrays.toString(strArr));
        }

        private void setLengths(String[] strArr) throws IOException {
            if (strArr.length != DiskLruCache.this.valueCount) {
                throw invalidLengths(strArr);
            }
            int i = 0;
            while (i < strArr.length) {
                try {
                    this.lengths[i] = Long.parseLong(strArr[i]);
                    i++;
                } catch (NumberFormatException e) {
                    throw invalidLengths(strArr);
                }
            }
        }

        final Snapshot snapshot() {
            int i = 0;
            if (Thread.holdsLock(DiskLruCache.this)) {
                on[] onVarArr = new on[DiskLruCache.this.valueCount];
                long[] jArr = (long[]) this.lengths.clone();
                int i2 = 0;
                while (i2 < DiskLruCache.this.valueCount) {
                    try {
                        onVarArr[i2] = DiskLruCache.this.fileSystem.source(this.cleanFiles[i2]);
                        i2++;
                    } catch (FileNotFoundException e) {
                        while (i < DiskLruCache.this.valueCount && onVarArr[i] != null) {
                            Util.closeQuietly(onVarArr[i]);
                            i++;
                        }
                        try {
                            DiskLruCache.this.removeEntry(this);
                        } catch (IOException e2) {
                        }
                        return null;
                    }
                }
                return new Snapshot(this.key, this.sequenceNumber, onVarArr, jArr);
            }
            throw new AssertionError();
        }

        final void writeLengths(nx nxVar) throws IOException {
            for (long k : this.lengths) {
                nxVar.h(32).k(k);
            }
        }
    }

    public final class Snapshot implements Closeable {
        private final String key;
        private final long[] lengths;
        private final long sequenceNumber;
        private final on[] sources;

        private Snapshot(String str, long j, on[] onVarArr, long[] jArr) {
            this.key = str;
            this.sequenceNumber = j;
            this.sources = onVarArr;
            this.lengths = jArr;
        }

        public final void close() {
            for (Closeable closeQuietly : this.sources) {
                Util.closeQuietly(closeQuietly);
            }
        }

        public final Editor edit() throws IOException {
            return DiskLruCache.this.edit(this.key, this.sequenceNumber);
        }

        public final long getLength(int i) {
            return this.lengths[i];
        }

        public final on getSource(int i) {
            return this.sources[i];
        }

        public final String key() {
            return this.key;
        }
    }

    DiskLruCache(FileSystem fileSystem, File file, int i, int i2, long j, Executor executor) {
        this.fileSystem = fileSystem;
        this.directory = file;
        this.appVersion = i;
        this.journalFile = new File(file, JOURNAL_FILE);
        this.journalFileTmp = new File(file, JOURNAL_FILE_TEMP);
        this.journalFileBackup = new File(file, JOURNAL_FILE_BACKUP);
        this.valueCount = i2;
        this.maxSize = j;
        this.executor = executor;
    }

    private void checkNotClosed() {
        synchronized (this) {
            if (isClosed()) {
                throw new IllegalStateException("cache is closed");
            }
        }
    }

    private void completeEdit(Editor editor, boolean z) throws IOException {
        int i = 0;
        synchronized (this) {
            Entry access$2100 = editor.entry;
            if (access$2100.currentEditor != editor) {
                throw new IllegalStateException();
            }
            if (z) {
                if (!access$2100.readable) {
                    int i2 = 0;
                    while (i2 < this.valueCount) {
                        if (!editor.written[i2]) {
                            editor.abort();
                            throw new IllegalStateException("Newly created entry didn't create value for index " + i2);
                        } else if (!this.fileSystem.exists(access$2100.dirtyFiles[i2])) {
                            editor.abort();
                            break;
                        } else {
                            i2++;
                        }
                    }
                }
            }
            while (i < this.valueCount) {
                File file = access$2100.dirtyFiles[i];
                if (!z) {
                    this.fileSystem.delete(file);
                } else if (this.fileSystem.exists(file)) {
                    File file2 = access$2100.cleanFiles[i];
                    this.fileSystem.rename(file, file2);
                    long j = access$2100.lengths[i];
                    long size = this.fileSystem.size(file2);
                    access$2100.lengths[i] = size;
                    this.size = (this.size - j) + size;
                }
                i++;
            }
            this.redundantOpCount++;
            access$2100.currentEditor = null;
            if ((access$2100.readable | z) != 0) {
                access$2100.readable = true;
                this.journalWriter.b(CLEAN).h(32);
                this.journalWriter.b(access$2100.key);
                access$2100.writeLengths(this.journalWriter);
                this.journalWriter.h(10);
                if (z) {
                    long j2 = this.nextSequenceNumber;
                    this.nextSequenceNumber = 1 + j2;
                    access$2100.sequenceNumber = j2;
                }
            } else {
                this.lruEntries.remove(access$2100.key);
                this.journalWriter.b(REMOVE).h(32);
                this.journalWriter.b(access$2100.key);
                this.journalWriter.h(10);
            }
            this.journalWriter.flush();
            if (this.size > this.maxSize || journalRebuildRequired()) {
                this.executor.execute(this.cleanupRunnable);
            }
        }
    }

    public static DiskLruCache create(FileSystem fileSystem, File file, int i, int i2, long j) {
        if (j <= 0) {
            throw new IllegalArgumentException("maxSize <= 0");
        } else if (i2 <= 0) {
            throw new IllegalArgumentException("valueCount <= 0");
        } else {
            return new DiskLruCache(fileSystem, file, i, i2, j, new ThreadPoolExecutor(0, 1, 60, TimeUnit.SECONDS, new LinkedBlockingQueue(), Util.threadFactory("OkHttp DiskLruCache", true)));
        }
    }

    private Editor edit(String str, long j) throws IOException {
        Editor editor;
        synchronized (this) {
            initialize();
            checkNotClosed();
            validateKey(str);
            Entry entry = (Entry) this.lruEntries.get(str);
            if (j == ANY_SEQUENCE_NUMBER || (entry != null && entry.sequenceNumber == j)) {
                if (entry != null) {
                    if (entry.currentEditor != null) {
                        editor = null;
                    }
                }
                if (this.mostRecentTrimFailed || this.mostRecentRebuildFailed) {
                    this.executor.execute(this.cleanupRunnable);
                    editor = null;
                } else {
                    this.journalWriter.b(DIRTY).h(32).b(str).h(10);
                    this.journalWriter.flush();
                    if (this.hasJournalErrors) {
                        editor = null;
                    } else {
                        Entry entry2;
                        if (entry == null) {
                            entry = new Entry(str);
                            this.lruEntries.put(str, entry);
                            entry2 = entry;
                        } else {
                            entry2 = entry;
                        }
                        editor = new Editor(entry2);
                        entry2.currentEditor = editor;
                    }
                }
            } else {
                editor = null;
            }
        }
        return editor;
    }

    private boolean journalRebuildRequired() {
        return this.redundantOpCount >= 2000 && this.redundantOpCount >= this.lruEntries.size();
    }

    private nx newJournalWriter() throws FileNotFoundException {
        return og.a(new FaultHidingSink(this.fileSystem.appendingSink(this.journalFile)) {
            static final /* synthetic */ boolean $assertionsDisabled = (!DiskLruCache.class.desiredAssertionStatus());

            protected void onException(IOException iOException) {
                if ($assertionsDisabled || Thread.holdsLock(DiskLruCache.this)) {
                    DiskLruCache.this.hasJournalErrors = true;
                    return;
                }
                throw new AssertionError();
            }
        });
    }

    private void processJournal() throws IOException {
        this.fileSystem.delete(this.journalFileTmp);
        Iterator it = this.lruEntries.values().iterator();
        while (it.hasNext()) {
            Entry entry = (Entry) it.next();
            int i;
            if (entry.currentEditor == null) {
                for (i = 0; i < this.valueCount; i++) {
                    this.size += entry.lengths[i];
                }
            } else {
                entry.currentEditor = null;
                for (i = 0; i < this.valueCount; i++) {
                    this.fileSystem.delete(entry.cleanFiles[i]);
                    this.fileSystem.delete(entry.dirtyFiles[i]);
                }
                it.remove();
            }
        }
    }

    private void readJournal() throws IOException {
        int i;
        Closeable a = og.a(this.fileSystem.source(this.journalFile));
        try {
            String o = a.o();
            String o2 = a.o();
            String o3 = a.o();
            String o4 = a.o();
            String o5 = a.o();
            if (MAGIC.equals(o) && VERSION_1.equals(o2) && Integer.toString(this.appVersion).equals(o3) && Integer.toString(this.valueCount).equals(o4) && "".equals(o5)) {
                i = 0;
                while (true) {
                    readJournalLine(a.o());
                    i++;
                }
            } else {
                throw new IOException("unexpected journal header: [" + o + ", " + o2 + ", " + o4 + ", " + o5 + "]");
            }
        } catch (EOFException e) {
            this.redundantOpCount = i - this.lruEntries.size();
            if (a.c()) {
                this.journalWriter = newJournalWriter();
            } else {
                rebuildJournal();
            }
            Util.closeQuietly(a);
        } catch (Throwable th) {
            Util.closeQuietly(a);
        }
    }

    private void readJournalLine(String str) throws IOException {
        int indexOf = str.indexOf(32);
        if (indexOf == -1) {
            throw new IOException("unexpected journal line: " + str);
        }
        String str2;
        int i = indexOf + 1;
        int indexOf2 = str.indexOf(32, i);
        if (indexOf2 == -1) {
            String substring = str.substring(i);
            if (indexOf == 6 && str.startsWith(REMOVE)) {
                this.lruEntries.remove(substring);
                return;
            }
            str2 = substring;
        } else {
            str2 = str.substring(i, indexOf2);
        }
        Entry entry = (Entry) this.lruEntries.get(str2);
        if (entry == null) {
            entry = new Entry(str2);
            this.lruEntries.put(str2, entry);
        }
        if (indexOf2 != -1 && indexOf == 5 && str.startsWith(CLEAN)) {
            String[] split = str.substring(indexOf2 + 1).split(" ");
            entry.readable = true;
            entry.currentEditor = null;
            entry.setLengths(split);
        } else if (indexOf2 == -1 && indexOf == 5 && str.startsWith(DIRTY)) {
            entry.currentEditor = new Editor(entry);
        } else if (indexOf2 != -1 || indexOf != 4 || !str.startsWith(READ)) {
            throw new IOException("unexpected journal line: " + str);
        }
    }

    private void rebuildJournal() throws IOException {
        synchronized (this) {
            if (this.journalWriter != null) {
                this.journalWriter.close();
            }
            nx a = og.a(this.fileSystem.sink(this.journalFileTmp));
            try {
                a.b(MAGIC).h(10);
                a.b(VERSION_1).h(10);
                a.k((long) this.appVersion).h(10);
                a.k((long) this.valueCount).h(10);
                a.h(10);
                for (Entry entry : this.lruEntries.values()) {
                    if (entry.currentEditor != null) {
                        a.b(DIRTY).h(32);
                        a.b(entry.key);
                        a.h(10);
                    } else {
                        a.b(CLEAN).h(32);
                        a.b(entry.key);
                        entry.writeLengths(a);
                        a.h(10);
                    }
                }
                a.close();
                if (this.fileSystem.exists(this.journalFile)) {
                    this.fileSystem.rename(this.journalFile, this.journalFileBackup);
                }
                this.fileSystem.rename(this.journalFileTmp, this.journalFile);
                this.fileSystem.delete(this.journalFileBackup);
                this.journalWriter = newJournalWriter();
                this.hasJournalErrors = false;
                this.mostRecentRebuildFailed = false;
            } catch (Throwable th) {
                a.close();
            }
        }
    }

    private boolean removeEntry(Entry entry) throws IOException {
        if (entry.currentEditor != null) {
            entry.currentEditor.detach();
        }
        for (int i = 0; i < this.valueCount; i++) {
            this.fileSystem.delete(entry.cleanFiles[i]);
            this.size -= entry.lengths[i];
            entry.lengths[i] = 0;
        }
        this.redundantOpCount++;
        this.journalWriter.b(REMOVE).h(32).b(entry.key).h(10);
        this.lruEntries.remove(entry.key);
        if (journalRebuildRequired()) {
            this.executor.execute(this.cleanupRunnable);
        }
        return true;
    }

    private void trimToSize() throws IOException {
        while (this.size > this.maxSize) {
            removeEntry((Entry) this.lruEntries.values().iterator().next());
        }
        this.mostRecentTrimFailed = false;
    }

    private void validateKey(String str) {
        if (!LEGAL_KEY_PATTERN.matcher(str).matches()) {
            throw new IllegalArgumentException("keys must match regex [a-z0-9_-]{1,120}: \"" + str + "\"");
        }
    }

    public final void close() throws IOException {
        synchronized (this) {
            if (!this.initialized || this.closed) {
                this.closed = true;
            } else {
                for (Entry entry : (Entry[]) this.lruEntries.values().toArray(new Entry[this.lruEntries.size()])) {
                    if (entry.currentEditor != null) {
                        entry.currentEditor.abort();
                    }
                }
                trimToSize();
                this.journalWriter.close();
                this.journalWriter = null;
                this.closed = true;
            }
        }
    }

    public final void delete() throws IOException {
        close();
        this.fileSystem.deleteContents(this.directory);
    }

    public final Editor edit(String str) throws IOException {
        return edit(str, ANY_SEQUENCE_NUMBER);
    }

    public final void evictAll() throws IOException {
        synchronized (this) {
            initialize();
            for (Entry removeEntry : (Entry[]) this.lruEntries.values().toArray(new Entry[this.lruEntries.size()])) {
                removeEntry(removeEntry);
            }
            this.mostRecentTrimFailed = false;
        }
    }

    public final void flush() throws IOException {
        synchronized (this) {
            if (this.initialized) {
                checkNotClosed();
                trimToSize();
                this.journalWriter.flush();
            }
        }
    }

    public final Snapshot get(String str) throws IOException {
        Snapshot snapshot;
        synchronized (this) {
            initialize();
            checkNotClosed();
            validateKey(str);
            Entry entry = (Entry) this.lruEntries.get(str);
            if (entry == null || !entry.readable) {
                snapshot = null;
            } else {
                snapshot = entry.snapshot();
                if (snapshot == null) {
                    snapshot = null;
                } else {
                    this.redundantOpCount++;
                    this.journalWriter.b(READ).h(32).b(str).h(10);
                    if (journalRebuildRequired()) {
                        this.executor.execute(this.cleanupRunnable);
                    }
                }
            }
        }
        return snapshot;
    }

    public final File getDirectory() {
        return this.directory;
    }

    public final long getMaxSize() {
        long j;
        synchronized (this) {
            j = this.maxSize;
        }
        return j;
    }

    public final void initialize() throws IOException {
        synchronized (this) {
            if ($assertionsDisabled || Thread.holdsLock(this)) {
                if (!this.initialized) {
                    if (this.fileSystem.exists(this.journalFileBackup)) {
                        if (this.fileSystem.exists(this.journalFile)) {
                            this.fileSystem.delete(this.journalFileBackup);
                        } else {
                            this.fileSystem.rename(this.journalFileBackup, this.journalFile);
                        }
                    }
                    if (this.fileSystem.exists(this.journalFile)) {
                        try {
                            readJournal();
                            processJournal();
                            this.initialized = true;
                        } catch (Throwable e) {
                            Platform.get().log(5, "DiskLruCache " + this.directory + " is corrupt: " + e.getMessage() + ", removing", e);
                            delete();
                            this.closed = false;
                        }
                    }
                    rebuildJournal();
                    this.initialized = true;
                }
            } else {
                throw new AssertionError();
            }
        }
    }

    public final boolean isClosed() {
        boolean z;
        synchronized (this) {
            z = this.closed;
        }
        return z;
    }

    public final boolean remove(String str) throws IOException {
        boolean z;
        synchronized (this) {
            initialize();
            checkNotClosed();
            validateKey(str);
            Entry entry = (Entry) this.lruEntries.get(str);
            if (entry == null) {
                z = false;
            } else {
                z = removeEntry(entry);
                if (z && this.size <= this.maxSize) {
                    this.mostRecentTrimFailed = false;
                }
            }
        }
        return z;
    }

    public final void setMaxSize(long j) {
        synchronized (this) {
            this.maxSize = j;
            if (this.initialized) {
                this.executor.execute(this.cleanupRunnable);
            }
        }
    }

    public final long size() throws IOException {
        long j;
        synchronized (this) {
            initialize();
            j = this.size;
        }
        return j;
    }

    public final Iterator<Snapshot> snapshots() throws IOException {
        Iterator anonymousClass3;
        synchronized (this) {
            initialize();
            anonymousClass3 = new Iterator<Snapshot>() {
                final Iterator<Entry> delegate = new ArrayList(DiskLruCache.this.lruEntries.values()).iterator();
                Snapshot nextSnapshot;
                Snapshot removeSnapshot;

                public boolean hasNext() {
                    if (this.nextSnapshot != null) {
                        return true;
                    }
                    synchronized (DiskLruCache.this) {
                        if (DiskLruCache.this.closed) {
                            return false;
                        }
                        while (this.delegate.hasNext()) {
                            Snapshot snapshot = ((Entry) this.delegate.next()).snapshot();
                            if (snapshot != null) {
                                this.nextSnapshot = snapshot;
                                return true;
                            }
                        }
                        return false;
                    }
                }

                public Snapshot next() {
                    if (hasNext()) {
                        this.removeSnapshot = this.nextSnapshot;
                        this.nextSnapshot = null;
                        return this.removeSnapshot;
                    }
                    throw new NoSuchElementException();
                }

                public void remove() {
                    if (this.removeSnapshot == null) {
                        throw new IllegalStateException("remove() before next()");
                    }
                    try {
                        DiskLruCache.this.remove(this.removeSnapshot.key);
                    } catch (IOException e) {
                    } finally {
                        this.removeSnapshot = null;
                    }
                }
            };
        }
        return anonymousClass3;
    }
}
