package android.support.v7.internal.widget;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.res.Resources;
import android.content.res.Resources.NotFoundException;
import android.graphics.drawable.Drawable;

public class TintContextWrapper extends ContextWrapper {
    private Resources mResources;

    static class TintResources extends ResourcesWrapper {
        private final TintManager mTintManager;

        public TintResources(Resources resources, TintManager tintManager) {
            super(resources);
            this.mTintManager = tintManager;
        }

        public Drawable getDrawable(int i) throws NotFoundException {
            Drawable drawable = super.getDrawable(i);
            if (drawable != null) {
                this.mTintManager.tintDrawableUsingColorFilter(i, drawable);
            }
            return drawable;
        }
    }

    private TintContextWrapper(Context context) {
        super(context);
    }

    public static Context wrap(Context context) {
        return !(context instanceof TintContextWrapper) ? new TintContextWrapper(context) : context;
    }

    public Resources getResources() {
        if (this.mResources == null) {
            this.mResources = new TintResources(super.getResources(), TintManager.get(this));
        }
        return this.mResources;
    }
}
