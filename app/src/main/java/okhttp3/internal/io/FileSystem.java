package okhttp3.internal.io;

import defpackage.og;
import defpackage.om;
import defpackage.on;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public interface FileSystem {
    public static final FileSystem SYSTEM = new FileSystem() {
        public final om appendingSink(File file) throws FileNotFoundException {
            try {
                return og.c(file);
            } catch (FileNotFoundException e) {
                file.getParentFile().mkdirs();
                return og.c(file);
            }
        }

        public final void delete(File file) throws IOException {
            if (!file.delete() && file.exists()) {
                throw new IOException("failed to delete " + file);
            }
        }

        public final void deleteContents(File file) throws IOException {
            File[] listFiles = file.listFiles();
            if (listFiles == null) {
                throw new IOException("not a readable directory: " + file);
            }
            int length = listFiles.length;
            int i = 0;
            while (i < length) {
                File file2 = listFiles[i];
                if (file2.isDirectory()) {
                    deleteContents(file2);
                }
                if (file2.delete()) {
                    i++;
                } else {
                    throw new IOException("failed to delete " + file2);
                }
            }
        }

        public final boolean exists(File file) {
            return file.exists();
        }

        public final void rename(File file, File file2) throws IOException {
            delete(file2);
            if (!file.renameTo(file2)) {
                throw new IOException("failed to rename " + file + " to " + file2);
            }
        }

        public final om sink(File file) throws FileNotFoundException {
            try {
                return og.b(file);
            } catch (FileNotFoundException e) {
                file.getParentFile().mkdirs();
                return og.b(file);
            }
        }

        public final long size(File file) {
            return file.length();
        }

        public final on source(File file) throws FileNotFoundException {
            return og.a(file);
        }
    };

    om appendingSink(File file) throws FileNotFoundException;

    void delete(File file) throws IOException;

    void deleteContents(File file) throws IOException;

    boolean exists(File file);

    void rename(File file, File file2) throws IOException;

    om sink(File file) throws FileNotFoundException;

    long size(File file);

    on source(File file) throws FileNotFoundException;
}
