package com.google.analytics.tracking.android;

import android.text.TextUtils;
import java.util.List;

class NoopDispatcher implements Dispatcher {
    NoopDispatcher() {
    }

    public void close() {
    }

    public int dispatchHits(List<Hit> list) {
        if (list == null) {
            return 0;
        }
        int min = Math.min(list.size(), 40);
        if (Log.isVerbose()) {
            Log.v("Hits not actually being sent as dispatch is false...");
            for (int i = 0; i < min; i++) {
                String postProcessHit = TextUtils.isEmpty(((Hit) list.get(i)).getHitParams()) ? "" : HitBuilder.postProcessHit((Hit) list.get(i), System.currentTimeMillis());
                String str = TextUtils.isEmpty(postProcessHit) ? "Hit couldn't be read, wouldn't be sent:" : postProcessHit.length() <= 2036 ? "GET would be sent:" : postProcessHit.length() > 8192 ? "Would be too big:" : "POST would be sent:";
                Log.v(str + postProcessHit);
            }
        }
        return min;
    }

    public boolean okToDispatch() {
        return true;
    }

    public void overrideHostUrl(String str) {
    }
}
