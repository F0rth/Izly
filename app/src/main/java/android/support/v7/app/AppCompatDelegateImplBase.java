package android.support.v7.app;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.ActionBarDrawerToggle.Delegate;
import android.support.v7.appcompat.R;
import android.support.v7.internal.app.WindowDecorActionBar;
import android.support.v7.internal.view.SupportMenuInflater;
import android.support.v7.internal.view.WindowCallbackWrapper;
import android.support.v7.internal.view.menu.MenuBuilder;
import android.support.v7.internal.widget.TintTypedArray;
import android.support.v7.view.ActionMode;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.Window;
import android.view.Window.Callback;

abstract class AppCompatDelegateImplBase extends AppCompatDelegate {
    private ActionBar mActionBar;
    final AppCompatCallback mAppCompatCallback;
    final Context mContext;
    boolean mHasActionBar;
    private boolean mIsDestroyed;
    boolean mIsFloating;
    private MenuInflater mMenuInflater;
    final Callback mOriginalWindowCallback = this.mWindow.getCallback();
    boolean mOverlayActionBar;
    boolean mOverlayActionMode;
    private CharSequence mTitle;
    final Window mWindow;
    boolean mWindowNoTitle;

    class ActionBarDrawableToggleImpl implements Delegate {
        private ActionBarDrawableToggleImpl() {
        }

        public Context getActionBarThemedContext() {
            return AppCompatDelegateImplBase.this.getActionBarThemedContext();
        }

        public Drawable getThemeUpIndicator() {
            TintTypedArray obtainStyledAttributes = TintTypedArray.obtainStyledAttributes(getActionBarThemedContext(), null, new int[]{R.attr.homeAsUpIndicator});
            Drawable drawable = obtainStyledAttributes.getDrawable(0);
            obtainStyledAttributes.recycle();
            return drawable;
        }

        public boolean isNavigationVisible() {
            ActionBar supportActionBar = AppCompatDelegateImplBase.this.getSupportActionBar();
            return (supportActionBar == null || (supportActionBar.getDisplayOptions() & 4) == 0) ? false : true;
        }

        public void setActionBarDescription(int i) {
            ActionBar supportActionBar = AppCompatDelegateImplBase.this.getSupportActionBar();
            if (supportActionBar != null) {
                supportActionBar.setHomeActionContentDescription(i);
            }
        }

        public void setActionBarUpIndicator(Drawable drawable, int i) {
            ActionBar supportActionBar = AppCompatDelegateImplBase.this.getSupportActionBar();
            if (supportActionBar != null) {
                supportActionBar.setHomeAsUpIndicator(drawable);
                supportActionBar.setHomeActionContentDescription(i);
            }
        }
    }

    class AppCompatWindowCallbackBase extends WindowCallbackWrapper {
        AppCompatWindowCallbackBase(Callback callback) {
            super(callback);
        }

        public boolean dispatchKeyEvent(KeyEvent keyEvent) {
            return super.dispatchKeyEvent(keyEvent) ? true : AppCompatDelegateImplBase.this.dispatchKeyEvent(keyEvent);
        }

        public boolean dispatchKeyShortcutEvent(KeyEvent keyEvent) {
            return AppCompatDelegateImplBase.this.onKeyShortcut(keyEvent.getKeyCode(), keyEvent) ? true : super.dispatchKeyShortcutEvent(keyEvent);
        }

        public void onContentChanged() {
        }

        public boolean onCreatePanelMenu(int i, Menu menu) {
            return (i != 0 || (menu instanceof MenuBuilder)) ? super.onCreatePanelMenu(i, menu) : false;
        }

        public boolean onMenuOpened(int i, Menu menu) {
            return AppCompatDelegateImplBase.this.onMenuOpened(i, menu) ? true : super.onMenuOpened(i, menu);
        }

        public void onPanelClosed(int i, Menu menu) {
            if (!AppCompatDelegateImplBase.this.onPanelClosed(i, menu)) {
                super.onPanelClosed(i, menu);
            }
        }

        public boolean onPreparePanel(int i, View view, Menu menu) {
            MenuBuilder menuBuilder = menu instanceof MenuBuilder ? (MenuBuilder) menu : null;
            if (i == 0 && menuBuilder == null) {
                return false;
            }
            if (menuBuilder != null) {
                menuBuilder.setOverrideVisibleItems(true);
            }
            boolean onPreparePanel = super.onPreparePanel(i, view, menu);
            if (menuBuilder == null) {
                return onPreparePanel;
            }
            menuBuilder.setOverrideVisibleItems(false);
            return onPreparePanel;
        }
    }

    AppCompatDelegateImplBase(Context context, Window window, AppCompatCallback appCompatCallback) {
        this.mContext = context;
        this.mWindow = window;
        this.mAppCompatCallback = appCompatCallback;
        if (this.mOriginalWindowCallback instanceof AppCompatWindowCallbackBase) {
            throw new IllegalStateException("AppCompat has already installed itself into the Window");
        }
        this.mWindow.setCallback(wrapWindowCallback(this.mOriginalWindowCallback));
    }

    abstract ActionBar createSupportActionBar();

    abstract boolean dispatchKeyEvent(KeyEvent keyEvent);

    final Context getActionBarThemedContext() {
        Context context = null;
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            context = supportActionBar.getThemedContext();
        }
        return context == null ? this.mContext : context;
    }

    public final Delegate getDrawerToggleDelegate() {
        return new ActionBarDrawableToggleImpl();
    }

    public MenuInflater getMenuInflater() {
        if (this.mMenuInflater == null) {
            this.mMenuInflater = new SupportMenuInflater(getActionBarThemedContext());
        }
        return this.mMenuInflater;
    }

    public ActionBar getSupportActionBar() {
        if (this.mHasActionBar) {
            if (this.mActionBar == null) {
                this.mActionBar = createSupportActionBar();
            }
        } else if (this.mActionBar instanceof WindowDecorActionBar) {
            this.mActionBar = null;
        }
        return this.mActionBar;
    }

    final CharSequence getTitle() {
        return this.mOriginalWindowCallback instanceof Activity ? ((Activity) this.mOriginalWindowCallback).getTitle() : this.mTitle;
    }

    final Callback getWindowCallback() {
        return this.mWindow.getCallback();
    }

    final boolean isDestroyed() {
        return this.mIsDestroyed;
    }

    public boolean isHandleNativeActionModesEnabled() {
        return false;
    }

    public void onCreate(Bundle bundle) {
        TypedArray obtainStyledAttributes = this.mContext.obtainStyledAttributes(R.styleable.Theme);
        if (obtainStyledAttributes.hasValue(R.styleable.Theme_windowActionBar)) {
            if (obtainStyledAttributes.getBoolean(R.styleable.Theme_windowActionBar, false)) {
                this.mHasActionBar = true;
            }
            if (obtainStyledAttributes.getBoolean(R.styleable.Theme_windowActionBarOverlay, false)) {
                this.mOverlayActionBar = true;
            }
            if (obtainStyledAttributes.getBoolean(R.styleable.Theme_windowActionModeOverlay, false)) {
                this.mOverlayActionMode = true;
            }
            this.mIsFloating = obtainStyledAttributes.getBoolean(R.styleable.Theme_android_windowIsFloating, false);
            this.mWindowNoTitle = obtainStyledAttributes.getBoolean(R.styleable.Theme_windowNoTitle, false);
            obtainStyledAttributes.recycle();
            return;
        }
        obtainStyledAttributes.recycle();
        throw new IllegalStateException("You need to use a Theme.AppCompat theme (or descendant) with this activity.");
    }

    public final void onDestroy() {
        this.mIsDestroyed = true;
    }

    abstract boolean onKeyShortcut(int i, KeyEvent keyEvent);

    abstract boolean onMenuOpened(int i, Menu menu);

    abstract boolean onPanelClosed(int i, Menu menu);

    abstract void onTitleChanged(CharSequence charSequence);

    final ActionBar peekSupportActionBar() {
        return this.mActionBar;
    }

    public void setHandleNativeActionModesEnabled(boolean z) {
    }

    final void setSupportActionBar(ActionBar actionBar) {
        this.mActionBar = actionBar;
    }

    public final void setTitle(CharSequence charSequence) {
        this.mTitle = charSequence;
        onTitleChanged(charSequence);
    }

    abstract ActionMode startSupportActionModeFromWindow(ActionMode.Callback callback);

    Callback wrapWindowCallback(Callback callback) {
        return new AppCompatWindowCallbackBase(callback);
    }
}
