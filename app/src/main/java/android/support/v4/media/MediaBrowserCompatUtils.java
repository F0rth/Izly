package android.support.v4.media;

import android.os.Bundle;
import android.support.v4.media.MediaBrowserCompat.MediaItem;
import java.util.List;

public class MediaBrowserCompatUtils {
    public static List<MediaItem> applyOptions(List<MediaItem> list, Bundle bundle) {
        int i = bundle.getInt(MediaBrowserCompat.EXTRA_PAGE, -1);
        int i2 = bundle.getInt(MediaBrowserCompat.EXTRA_PAGE_SIZE, -1);
        if (i == -1 && i2 == -1) {
            return list;
        }
        int i3 = i2 * (i - 1);
        int i4 = i3 + i2;
        if (i <= 0 || i2 <= 0 || i3 >= list.size()) {
            return null;
        }
        if (i4 > list.size()) {
            i4 = list.size();
        }
        return list.subList(i3, i4);
    }

    public static boolean areSameOptions(Bundle bundle, Bundle bundle2) {
        if (bundle != bundle2) {
            if (bundle == null) {
                if (bundle2.getInt(MediaBrowserCompat.EXTRA_PAGE, -1) != -1) {
                    return false;
                }
                if (bundle2.getInt(MediaBrowserCompat.EXTRA_PAGE_SIZE, -1) != -1) {
                    return false;
                }
            } else if (bundle2 == null) {
                if (bundle.getInt(MediaBrowserCompat.EXTRA_PAGE, -1) != -1) {
                    return false;
                }
                if (bundle.getInt(MediaBrowserCompat.EXTRA_PAGE_SIZE, -1) != -1) {
                    return false;
                }
            } else if (bundle.getInt(MediaBrowserCompat.EXTRA_PAGE, -1) != bundle2.getInt(MediaBrowserCompat.EXTRA_PAGE, -1)) {
                return false;
            } else {
                if (bundle.getInt(MediaBrowserCompat.EXTRA_PAGE_SIZE, -1) != bundle2.getInt(MediaBrowserCompat.EXTRA_PAGE_SIZE, -1)) {
                    return false;
                }
            }
        }
        return true;
    }

    public static boolean hasDuplicatedItems(Bundle bundle, Bundle bundle2) {
        boolean z;
        boolean z2;
        boolean z3;
        int i = bundle == null ? -1 : bundle.getInt(MediaBrowserCompat.EXTRA_PAGE, -1);
        int i2 = bundle2 == null ? -1 : bundle2.getInt(MediaBrowserCompat.EXTRA_PAGE, -1);
        int i3 = bundle == null ? -1 : bundle.getInt(MediaBrowserCompat.EXTRA_PAGE_SIZE, -1);
        int i4 = bundle2 == null ? -1 : bundle2.getInt(MediaBrowserCompat.EXTRA_PAGE_SIZE, -1);
        if (i == -1 || i3 == -1) {
            z = false;
            z2 = true;
        } else {
            boolean z4 = (i - 1) * i3;
            z2 = (i3 + z4) - 1;
            z = z4;
        }
        if (i2 == -1 || i4 == -1) {
            z4 = ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED;
            z3 = false;
        } else {
            z3 = (i2 - 1) * i4;
            z4 = (z3 + i4) - 1;
        }
        if (z > z3 || z3 > z2) {
            if (z > z4) {
                return false;
            }
            if (z4 > z2) {
                return false;
            }
        }
        return true;
    }
}
