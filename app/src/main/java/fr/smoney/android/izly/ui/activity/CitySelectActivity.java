package fr.smoney.android.izly.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.SearchView.OnQueryTextListener;
import android.support.v7.widget.SearchView.SearchAutoComplete;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import butterknife.Bind;
import butterknife.ButterKnife;

import com.thewingitapp.thirdparties.wingitlib.WINGiTDataHolder;
import com.thewingitapp.thirdparties.wingitlib.model.WGCity;
import com.thewingitapp.thirdparties.wingitlib.network.api.WINGiTManager;

import defpackage.jg;
import fr.smoney.android.izly.R;
import fr.smoney.android.izly.SmoneyABSActivity;
import fr.smoney.android.izly.adapter.CityAdapter;
import fr.smoney.android.izly.adapter.CityAdapter.b;

public class CitySelectActivity extends SmoneyABSActivity implements b {
    private static final String b = CitySelectActivity.class.getName();
    private CityAdapter c;
    private boolean d = false;
    @Bind({2131755264})
    RecyclerView mRecyclerView;
    @Bind({2131755238})
    Toolbar mToolbar;

    public static Intent a(Context context, boolean z) {
        Intent intent = new Intent(context, CitySelectActivity.class);
        intent.putExtra("INTENT_BACK_ENABLED", z);
        return intent;
    }

    public final void a(WGCity wGCity) {
        if (this.d) {
            Intent intent = new Intent();
            intent.putExtra("INTENT_CITY", wGCity);
            setResult(-1, intent);
        } else {
            startActivity(CityEventActivity.a((Context) this, wGCity));
        }
        jg.a((Context) this, wGCity);
        finish();
    }

    public void onBackPressed() {
        if (this.d) {
            super.onBackPressed();
        } else {
            Toast.makeText(this, getString(R.string.wingit_select_city_toast_message_select_city), 0).show();
        }
    }

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.activity_select_city);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        if (intent != null) {
            this.d = intent.getBooleanExtra("INTENT_BACK_ENABLED", false);
        }
        setSupportActionBar(this.mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(this.d);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle(R.string.wingit_select_city_label_navbar_title);
        this.c = new CityAdapter(WINGiTDataHolder.getCities());
        this.c.a = this;
        this.mRecyclerView.setLayoutManager(new LinearLayoutManager(this, 1, false));
        this.mRecyclerView.setAdapter(this.c);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_city_select, menu);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(menu.findItem(R.id.search_item));
        searchView.setQueryHint(getString(R.string.wingit_select_city_textfield_placeholder_search));
        SearchAutoComplete searchAutoComplete = (SearchAutoComplete) searchView.findViewById(2131755160);
        if (searchAutoComplete != null) {
            searchAutoComplete.setHintTextColor(getResources().getColor(R.color.grey600));
        }
        searchView.setMaxWidth(Integer.MAX_VALUE);
        searchView.setOnQueryTextListener(new OnQueryTextListener(this) {
            final /* synthetic */ CitySelectActivity a;

            {
                this.a = r1;
            }

            public final boolean onQueryTextChange(String str) {
                this.a.c.getFilter().filter(str);
                return false;
            }

            public final boolean onQueryTextSubmit(String str) {
                if (this.a.c.getItemCount() > 0) {
                    this.a.mRecyclerView.findViewHolderForAdapterPosition(0).itemView.performClick();
                }
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case 16908332:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(menuItem);
        }
    }

    protected void onStart() {
        super.onStart();
        WINGiTManager.trackDisplay("citylist", null);
    }
}
