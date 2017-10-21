package fr.smoney.android.izly.ui.fragment;

import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import defpackage.jb;
import defpackage.jh;
import fr.smoney.android.izly.R;
import fr.smoney.android.izly.ui.AboutActivity;

public class AboutFragment extends aa {
    private static final String e = AboutActivity.class.getSimpleName();
    private TextView f;

    public static AboutFragment n() {
        return new AboutFragment();
    }

    protected final String k() {
        return getString(R.string.about_title);
    }

    public void onActivityCreated(Bundle bundle) {
        super.onActivityCreated(bundle);
        b(true);
        setHasOptionsMenu(true);
        this.f = (TextView) getView().findViewById(R.id.tv_about_info_version);
        try {
            String str = getActivity().getPackageManager().getPackageInfo(getActivity().getPackageName(), 0).versionName;
            this.f.setText(getString(R.string.about_editor_info_text, new Object[]{str}));
        } catch (NameNotFoundException e) {
        }
        getActivity();
        jh.a();
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
    }

    public void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
        super.onCreateOptionsMenu(menu, menuInflater);
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.about, viewGroup, false);
        jb.a(getActivity(), R.string.screen_name_about_activity);
        return inflate;
    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case 16908332:
                e();
                break;
        }
        return true;
    }

    public void onResume() {
        super.onResume();
        this.d.getSupportActionBar().setTitle(R.string.about_title);
    }
}
