package fr.smoney.android.izly.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;

import defpackage.hd;
import defpackage.hf;
import defpackage.jb;
import fr.smoney.android.izly.R;

public class HelpActivity extends TabSwipeActivity {
    public int b = 1;
    private int c = 0;

    protected final void d(int i) {
        jb.a(getApplicationContext(), i == this.c ? R.string.screen_name_help_faq_activity : R.string.screen_name_help_contact_activity);
    }

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        ActionBar supportActionBar = getSupportActionBar();
        supportActionBar.setHomeButtonEnabled(true);
        supportActionBar.setDisplayHomeAsUpEnabled(true);
        a(R.string.help_faq_title, hf.class, null);
        a(R.string.help_contactus_title, hd.class, null);
    }

    protected void onNewIntent(Intent intent) {
        if (intent.getScheme().equalsIgnoreCase("faq")) {
            this.mViewPager.setCurrentItem(this.c);
        } else {
            super.onNewIntent(intent);
        }
    }
}
