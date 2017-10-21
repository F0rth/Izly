package com.squareup.picasso;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import java.util.List;

final class Picasso$1 extends Handler {
    Picasso$1(Looper looper) {
        super(looper);
    }

    public final void handleMessage(Message message) {
        List list;
        int size;
        int i;
        switch (message.what) {
            case 3:
                Action action = (Action) message.obj;
                if (action.getPicasso().loggingEnabled) {
                    Utils.log("Main", "canceled", action.request.logId(), "target got garbage collected");
                }
                Picasso.access$000(action.picasso, action.getTarget());
                return;
            case 8:
                list = (List) message.obj;
                size = list.size();
                for (i = 0; i < size; i++) {
                    BitmapHunter bitmapHunter = (BitmapHunter) list.get(i);
                    bitmapHunter.picasso.complete(bitmapHunter);
                }
                return;
            case 13:
                list = (List) message.obj;
                size = list.size();
                for (i = 0; i < size; i++) {
                    Action action2 = (Action) list.get(i);
                    action2.picasso.resumeAction(action2);
                }
                return;
            default:
                throw new AssertionError("Unknown handler message received: " + message.what);
        }
    }
}
