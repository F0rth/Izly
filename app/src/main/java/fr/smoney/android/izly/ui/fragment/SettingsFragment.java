package fr.smoney.android.izly.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;

import butterknife.Bind;
import butterknife.ButterKnife;
import defpackage.hr;
import defpackage.jb;
import fr.smoney.android.izly.R;
import fr.smoney.android.izly.ui.BlockAccountActivity;
import fr.smoney.android.izly.ui.ChangeConfidentialitySettingsActivity;
import fr.smoney.android.izly.ui.ChangePasswordActivity;
import fr.smoney.android.izly.ui.ListSupportActivity;
import fr.smoney.android.izly.ui.adapter.IzlyListAdapterHelper;

public class SettingsFragment extends aa implements OnItemClickListener {
    public static String e = "fr.smoney.android.izly.ui.OffersAndServicesActivity";
    private IzlyListAdapterHelper f;
    private IzlyListAdapterHelper g;
    private IzlyListAdapterHelper h;
    @Bind({2131755901})
    LinearLayout mInfoLayout;
    @Bind({2131755900})
    LinearLayout mSecurityLayout;
    @Bind({2131755899})
    LinearLayout mUserListLayout;

    static final /* synthetic */ class AnonymousClass4 {
        static final /* synthetic */ int[] a = new int[cc.values().length];
        static final /* synthetic */ int[] b = new int[cj.values().length];
        static final /* synthetic */ int[] c = new int[cd.values().length];

        static {
            try {
                c[cd.a.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                b[cj.a.ordinal()] = 1;
            } catch (NoSuchFieldError e2) {
            }
            try {
                b[cj.b.ordinal()] = 2;
            } catch (NoSuchFieldError e3) {
            }
            try {
                b[cj.c.ordinal()] = 3;
            } catch (NoSuchFieldError e4) {
            }
            try {
                a[cc.a.ordinal()] = 1;
            } catch (NoSuchFieldError e5) {
            }
            try {
                a[cc.b.ordinal()] = 2;
            } catch (NoSuchFieldError e6) {
            }
            try {
                a[cc.c.ordinal()] = 3;
            } catch (NoSuchFieldError e7) {
            }
        }
    }

    public static SettingsFragment n() {
        return new SettingsFragment();
    }

    protected final String k() {
        return getString(R.string.title_activity_pre_settings);
    }

    public void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
        super.onCreateOptionsMenu(menu, menuInflater);
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        int i;
        int i2 = 0;
        View inflate = layoutInflater.inflate(R.layout.settings_fragment, viewGroup, false);
        ButterKnife.bind(this, inflate);
        setHasOptionsMenu(true);
        this.f = new IzlyListAdapterHelper(this.d, cc.values());
        this.g = new IzlyListAdapterHelper(this.d, cj.values());
        this.h = new IzlyListAdapterHelper(this.d, cd.values());
        for (i = 0; i < this.f.getCount(); i++) {
            View view = this.f.getView(i, null, null);
            view.setOnClickListener(new OnClickListener(this) {
                final /* synthetic */ SettingsFragment b;

                public final void onClick(View view) {
                    switch (AnonymousClass4.a[cc.values()[i].ordinal()]) {
                        case 1:
                            this.b.a(R.id.content_fragment, hr.a(null), true);
                            return;
                        case 2:
                            this.b.a(R.id.content_fragment, CbListFragment.n(), true);
                            return;
                        case 3:
                            this.b.a(new Intent(this.b.d, ListSupportActivity.class), true);
                            return;
                        default:
                            return;
                    }
                }
            });
            this.mUserListLayout.addView(view, i);
        }
        for (i = 0; i < this.g.getCount(); i++) {
            view = this.g.getView(i, null, null);
            view.setOnClickListener(new OnClickListener(this) {
                final /* synthetic */ SettingsFragment b;

                public final void onClick(View view) {
                    switch (AnonymousClass4.b[cj.values()[i].ordinal()]) {
                        case 1:
                            this.b.a(new Intent(this.b.d, ChangePasswordActivity.class), true);
                            return;
                        case 2:
                            this.b.a(new Intent(this.b.d, ChangeConfidentialitySettingsActivity.class), true);
                            return;
                        case 3:
                            this.b.a(new Intent(this.b.d, BlockAccountActivity.class), true);
                            return;
                        default:
                            return;
                    }
                }
            });
            this.mSecurityLayout.addView(view, i);
        }
        while (i2 < this.h.getCount()) {
            View view2 = this.h.getView(i2, null, null);
            view2.setOnClickListener(new OnClickListener(this) {
                final /* synthetic */ SettingsFragment b;

                public final void onClick(View view) {
                    switch (AnonymousClass4.c[cd.values()[i2].ordinal()]) {
                        case 1:
                            this.b.a(R.id.content_fragment, AboutFragment.n(), true);
                            return;
                        default:
                            return;
                    }
                }
            });
            this.mInfoLayout.addView(view2, i2);
            i2++;
        }
        jb.a(getActivity(), R.string.screen_name_settings_activity);
        return inflate;
    }

    public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case 16908332:
                getActivity().finish();
                break;
        }
        return true;
    }

    public void onResume() {
        super.onResume();
        this.d.getSupportActionBar().setTitle(R.string.title_activity_pre_settings);
    }
}
