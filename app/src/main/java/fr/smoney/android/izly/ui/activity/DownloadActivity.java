package fr.smoney.android.izly.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;

import com.squareup.picasso.Picasso;
import com.thewingitapp.thirdparties.wingitlib.model.WGDownloadPage;

import fr.smoney.android.izly.R;

public class DownloadActivity extends AppCompatActivity {
    private WGDownloadPage a;
    @Bind({2131755242})
    ImageView mContentImage;
    @Bind({2131755261})
    TextView mDetailedTextView;
    @Bind({2131755238})
    Toolbar mToolbar;

    public static Intent a(Context context, WGDownloadPage wGDownloadPage) {
        Intent intent = new Intent(context, DownloadActivity.class);
        intent.putExtra("ARG_DOWNLOAD_PAGE", wGDownloadPage);
        return intent;
    }

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_download);
        ButterKnife.bind(this);
        setSupportActionBar(this.mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("ARG_DOWNLOAD_PAGE")) {
            this.a = (WGDownloadPage) intent.getSerializableExtra("ARG_DOWNLOAD_PAGE");
            getSupportActionBar().setTitle(this.a.getTitle());
            Picasso.with(this).load(this.a.getImageURL()).into(this.mContentImage);
            this.mDetailedTextView.setText(Html.fromHtml(this.a.getDescription()));
        }
    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {
        super.onOptionsItemSelected(menuItem);
        switch (menuItem.getItemId()) {
            case 16908332:
                onBackPressed();
                return true;
            default:
                return false;
        }
    }
}
