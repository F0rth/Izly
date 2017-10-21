package fr.smoney.android.izly.ui.fragment;

import android.view.View;
import android.widget.LinearLayout;

import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;
import fr.smoney.android.izly.R;

public class SettingsFragment$$ViewBinder<T extends SettingsFragment> implements ViewBinder<T> {
    public void bind(Finder finder, T t, Object obj) {
        t.mUserListLayout = (LinearLayout) finder.castView((View) finder.findRequiredView(obj, R.id.user_account_list, "field 'mUserListLayout'"), R.id.user_account_list, "field 'mUserListLayout'");
        t.mSecurityLayout = (LinearLayout) finder.castView((View) finder.findRequiredView(obj, R.id.security_account_list, "field 'mSecurityLayout'"), R.id.security_account_list, "field 'mSecurityLayout'");
        t.mInfoLayout = (LinearLayout) finder.castView((View) finder.findRequiredView(obj, R.id.other_info_list, "field 'mInfoLayout'"), R.id.other_info_list, "field 'mInfoLayout'");
    }

    public void unbind(T t) {
        t.mUserListLayout = null;
        t.mSecurityLayout = null;
        t.mInfoLayout = null;
    }
}
