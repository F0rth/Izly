package fr.smoney.android.izly.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.text.Html;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import defpackage.is;
import fr.smoney.android.izly.R;
import fr.smoney.android.izly.SmoneyABSActivity;

public class P2PGetChoiceActivity extends SmoneyABSActivity implements OnClickListener {
    private View b;
    private View c;

    public void onClick(View view) {
        Intent intent = null;
        if (view == this.b) {
            intent = is.a(this, P2PGetSimpleActivity.class);
        } else if (view == this.c) {
            intent = is.a(this, P2PGetMultActivity.class);
        }
        if (intent != null) {
            a(intent, true);
        }
    }

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.p2p_get_choice);
        ActionBar supportActionBar = getSupportActionBar();
        supportActionBar.setHomeButtonEnabled(true);
        supportActionBar.setDisplayHomeAsUpEnabled(true);
        this.b = findViewById(R.id.rl_p2p_get_choice_simple);
        this.c = findViewById(R.id.rl_p2p_get_choice_mult);
        CharSequence fromHtml = Html.fromHtml(getString(R.string.p2p_get_choice_simple_description));
        CharSequence fromHtml2 = Html.fromHtml(getString(R.string.p2p_get_choice_mult_description));
        this.b.setOnClickListener(this);
        this.c.setOnClickListener(this);
        ((TextView) findViewById(R.id.tv_p2p_choice_m_desc)).setText(fromHtml2);
        ((TextView) findViewById(R.id.tv_p2p_choice_s_desc)).setText(fromHtml);
    }
}
