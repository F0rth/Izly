package com.slidingmenu.lib.app;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import com.slidingmenu.lib.R;
import com.slidingmenu.lib.SlidingMenu;

public class SlidingActivityHelper {
    private Activity mActivity;
    private boolean mBroadcasting = false;
    private boolean mEnableSlide = true;
    Handler mHandler = new Handler();
    private boolean mOnPostCreateCalled = false;
    private SlidingMenu mSlidingMenu;
    private View mViewAbove;
    private View mViewBehind;

    public SlidingActivityHelper(Activity activity) {
        this.mActivity = activity;
    }

    public View findViewById(int i) {
        if (this.mSlidingMenu != null) {
            View findViewById = this.mSlidingMenu.findViewById(i);
            if (findViewById != null) {
                return findViewById;
            }
        }
        return null;
    }

    public SlidingMenu getSlidingMenu() {
        return this.mSlidingMenu;
    }

    public void onCreate(Bundle bundle) {
        this.mSlidingMenu = (SlidingMenu) LayoutInflater.from(this.mActivity).inflate(R.layout.slidingmenumain, null);
    }

    public boolean onKeyUp(int i, KeyEvent keyEvent) {
        if (i != 4 || !this.mSlidingMenu.isMenuShowing()) {
            return false;
        }
        showContent();
        return true;
    }

    public void onPostCreate(Bundle bundle) {
        int i = 1;
        boolean z = false;
        if (this.mViewBehind == null || this.mViewAbove == null) {
            throw new IllegalStateException("Both setBehindContentView must be called in onCreate in addition to setContentView.");
        }
        boolean z2;
        this.mOnPostCreateCalled = true;
        SlidingMenu slidingMenu = this.mSlidingMenu;
        Activity activity = this.mActivity;
        if (this.mEnableSlide) {
            i = 0;
        }
        slidingMenu.attachToActivity(activity, i);
        if (bundle != null) {
            z = bundle.getBoolean("SlidingActivityHelper.open");
            z2 = bundle.getBoolean("SlidingActivityHelper.secondary");
        } else {
            z2 = false;
        }
        this.mHandler.post(new Runnable() {
            public void run() {
                if (!z) {
                    SlidingActivityHelper.this.mSlidingMenu.showContent(false);
                } else if (z2) {
                    SlidingActivityHelper.this.mSlidingMenu.showSecondaryMenu(false);
                } else {
                    SlidingActivityHelper.this.mSlidingMenu.showMenu(false);
                }
            }
        });
    }

    public void onSaveInstanceState(Bundle bundle) {
        bundle.putBoolean("SlidingActivityHelper.open", this.mSlidingMenu.isMenuShowing());
        bundle.putBoolean("SlidingActivityHelper.secondary", this.mSlidingMenu.isSecondaryMenuShowing());
    }

    public void registerAboveContentView(View view, LayoutParams layoutParams) {
        if (!this.mBroadcasting) {
            this.mViewAbove = view;
        }
    }

    public void setBehindContentView(View view, LayoutParams layoutParams) {
        this.mViewBehind = view;
        this.mSlidingMenu.setMenu(this.mViewBehind);
    }

    public void setContentView(View view) {
        this.mBroadcasting = true;
        this.mActivity.setContentView(view);
    }

    public void setSlidingActionBarEnabled(boolean z) {
        if (this.mOnPostCreateCalled) {
            throw new IllegalStateException("enableSlidingActionBar must be called in onCreate.");
        }
        this.mEnableSlide = z;
    }

    public void showContent() {
        this.mSlidingMenu.showContent();
    }

    public void showMenu() {
        this.mSlidingMenu.showMenu();
    }

    public void showSecondaryMenu() {
        this.mSlidingMenu.showSecondaryMenu();
    }

    public void toggle() {
        this.mSlidingMenu.toggle();
    }
}
