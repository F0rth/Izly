package fr.smoney.android.izly.ui;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;

import fr.smoney.android.izly.SmoneyABSActivity;
import fr.smoney.android.izly.data.requestmanager.SmoneyRequestManager$a;

import java.util.Currency;

public class AbstractMoneyInActivity extends SmoneyABSActivity implements TextWatcher, OnClickListener, OnItemSelectedListener, SmoneyRequestManager$a {
    protected int b = -1;
    protected String c;

    public final void a_(int i, int i2, int i3, Bundle bundle) {
    }

    public void afterTextChanged(Editable editable) {
    }

    public final void b_(int i) {
    }

    public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
    }

    public void onClick(View view) {
    }

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        ActionBar supportActionBar = getSupportActionBar();
        supportActionBar.setHomeButtonEnabled(true);
        supportActionBar.setDisplayHomeAsUpEnabled(true);
        this.c = Currency.getInstance(i().b.j).getSymbol();
    }

    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long j) {
    }

    public void onNothingSelected(AdapterView<?> adapterView) {
    }

    public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
    }
}
