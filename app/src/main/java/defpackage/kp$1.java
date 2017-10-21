package defpackage;

import java.io.File;
import java.util.Comparator;

final class kp$1 implements Comparator<File> {
    kp$1() {
    }

    public final /* synthetic */ int compare(Object obj, Object obj2) {
        return (int) (((File) obj).lastModified() - ((File) obj2).lastModified());
    }
}
