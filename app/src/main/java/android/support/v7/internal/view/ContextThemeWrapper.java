package android.support.v7.internal.view;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.res.Resources.Theme;
import android.support.v7.appcompat.R;
import android.view.LayoutInflater;

public class ContextThemeWrapper extends ContextWrapper {
    private LayoutInflater mInflater;
    private Theme mTheme;
    private int mThemeResource;

    public ContextThemeWrapper(Context context, int i) {
        super(context);
        this.mThemeResource = i;
    }

    private void initializeTheme() {
        boolean z = this.mTheme == null;
        if (z) {
            this.mTheme = getResources().newTheme();
            Theme theme = getBaseContext().getTheme();
            if (theme != null) {
                this.mTheme.setTo(theme);
            }
        }
        onApplyThemeResource(this.mTheme, this.mThemeResource, z);
    }

    public Object getSystemService(String str) {
        if (!"layout_inflater".equals(str)) {
            return getBaseContext().getSystemService(str);
        }
        if (this.mInflater == null) {
            this.mInflater = LayoutInflater.from(getBaseContext()).cloneInContext(this);
        }
        return this.mInflater;
    }

    public Theme getTheme() {
        if (this.mTheme != null) {
            return this.mTheme;
        }
        if (this.mThemeResource == 0) {
            this.mThemeResource = R.style.Theme_AppCompat_Light;
        }
        initializeTheme();
        return this.mTheme;
    }

    public int getThemeResId() {
        return this.mThemeResource;
    }

    protected void onApplyThemeResource(Theme theme, int i, boolean z) {
        theme.applyStyle(i, true);
    }

    public void setTheme(int i) {
        this.mThemeResource = i;
        initializeTheme();
    }
}
