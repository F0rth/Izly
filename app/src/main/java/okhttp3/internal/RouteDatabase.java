package okhttp3.internal;

import java.util.LinkedHashSet;
import java.util.Set;
import okhttp3.Route;

public final class RouteDatabase {
    private final Set<Route> failedRoutes = new LinkedHashSet();

    public final void connected(Route route) {
        synchronized (this) {
            this.failedRoutes.remove(route);
        }
    }

    public final void failed(Route route) {
        synchronized (this) {
            this.failedRoutes.add(route);
        }
    }

    public final int failedRoutesCount() {
        int size;
        synchronized (this) {
            size = this.failedRoutes.size();
        }
        return size;
    }

    public final boolean shouldPostpone(Route route) {
        boolean contains;
        synchronized (this) {
            contains = this.failedRoutes.contains(route);
        }
        return contains;
    }
}
