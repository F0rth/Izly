package fr.smoney.android.izly.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff.Mode;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import com.thewingitapp.thirdparties.wingitlib.WINGiTDataHolder;
import com.thewingitapp.thirdparties.wingitlib.model.WGCategory;
import com.thewingitapp.thirdparties.wingitlib.model.WGDetectedCity;
import com.thewingitapp.thirdparties.wingitlib.model.WGTimelineParameters;
import com.thewingitapp.thirdparties.wingitlib.model.WGTimelineParameters.Day;
import com.thewingitapp.thirdparties.wingitlib.model.WGTimelineParameters.SortType;

import defpackage.iz;
import defpackage.jm;
import defpackage.kh;
import defpackage.lv;
import fr.smoney.android.izly.R;
import fr.smoney.android.izly.adapter.CategoryAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import org.spongycastle.asn1.x509.DisplayText;

public class FilterActivity extends AppCompatActivity {
    private static Integer[] e = new Integer[]{Integer.valueOf(100), Integer.valueOf(100), Integer.valueOf(100), Integer.valueOf(100), Integer.valueOf(100), Integer.valueOf(100), Integer.valueOf(100), Integer.valueOf(100), Integer.valueOf(100), Integer.valueOf(DisplayText.DISPLAY_TEXT_MAXIMUM_SIZE), Integer.valueOf(DisplayText.DISPLAY_TEXT_MAXIMUM_SIZE), Integer.valueOf(DisplayText.DISPLAY_TEXT_MAXIMUM_SIZE), Integer.valueOf(DisplayText.DISPLAY_TEXT_MAXIMUM_SIZE), Integer.valueOf(DisplayText.DISPLAY_TEXT_MAXIMUM_SIZE), Integer.valueOf(DisplayText.DISPLAY_TEXT_MAXIMUM_SIZE), Integer.valueOf(DisplayText.DISPLAY_TEXT_MAXIMUM_SIZE), Integer.valueOf(500), Integer.valueOf(500), Integer.valueOf(500), Integer.valueOf(500), Integer.valueOf(500), Integer.valueOf(1000), Integer.valueOf(1000), Integer.valueOf(1000), Integer.valueOf(2000), Integer.valueOf(2000), Integer.valueOf(2000), Integer.valueOf(2000), Integer.valueOf(3000), Integer.valueOf(3000), Integer.valueOf(3000), Integer.valueOf(4000), Integer.valueOf(4000), Integer.valueOf(BaseImageDownloader.DEFAULT_HTTP_CONNECT_TIMEOUT), Integer.valueOf(BaseImageDownloader.DEFAULT_HTTP_CONNECT_TIMEOUT), Integer.valueOf(BaseImageDownloader.DEFAULT_HTTP_CONNECT_TIMEOUT), Integer.valueOf(6000), Integer.valueOf(7000), Integer.valueOf(7000), Integer.valueOf(lv.MAX_BYTE_SIZE_PER_FILE), Integer.valueOf(lv.MAX_BYTE_SIZE_PER_FILE), Integer.valueOf(9000), Integer.valueOf(kh.DEFAULT_TIMEOUT), Integer.valueOf(kh.DEFAULT_TIMEOUT), Integer.valueOf(11000), Integer.valueOf(12000), Integer.valueOf(13000), Integer.valueOf(14000), Integer.valueOf(15000), Integer.valueOf(16000), Integer.valueOf(17000), Integer.valueOf(18000), Integer.valueOf(19000), Integer.valueOf(20000), Integer.valueOf(22000), Integer.valueOf(23000), Integer.valueOf(24000), Integer.valueOf(26000), Integer.valueOf(28000), Integer.valueOf(30000), Integer.valueOf(30000), Integer.valueOf(32000), Integer.valueOf(35000), Integer.valueOf(36000), Integer.valueOf(37000), Integer.valueOf(39000), Integer.valueOf(40000), Integer.valueOf(43000), Integer.valueOf(45000), Integer.valueOf(47000), Integer.valueOf(50000), Integer.valueOf(52000), Integer.valueOf(55000), Integer.valueOf(57000), Integer.valueOf(60000), Integer.valueOf(62000), Integer.valueOf(65000), Integer.valueOf(67000), Integer.valueOf(70000), Integer.valueOf(73000), Integer.valueOf(75000), Integer.valueOf(80000), Integer.valueOf(85000), Integer.valueOf(87000), Integer.valueOf(90000), Integer.valueOf(92000), Integer.valueOf(95000), Integer.valueOf(100000), Integer.valueOf(102000), Integer.valueOf(105000), Integer.valueOf(110000), Integer.valueOf(115000), Integer.valueOf(120000), Integer.valueOf(123000), Integer.valueOf(125000), Integer.valueOf(130000), Integer.valueOf(135000), Integer.valueOf(140000), Integer.valueOf(145000), Integer.valueOf(150000), Integer.valueOf(150000)};
    private WGTimelineParameters a;
    private WGDetectedCity b;
    private CategoryAdapter c;
    private List<WGCategory> d;
    private OnSeekBarChangeListener f = new OnSeekBarChangeListener(this) {
        final /* synthetic */ FilterActivity a;

        {
            this.a = r1;
        }

        public final void onProgressChanged(SeekBar seekBar, int i, boolean z) {
            int intValue = FilterActivity.e[this.a.mCustomSeekBar.getProgress()].intValue();
            if (Locale.getDefault().getLanguage().startsWith("fr")) {
                this.a.mToDistanceLabel.setText(jm.a(this.a, (double) intValue));
            } else {
                this.a.mToDistanceLabel.setText(jm.b(this.a, (double) intValue));
            }
        }

        public final void onStartTrackingTouch(SeekBar seekBar) {
        }

        public final void onStopTrackingTouch(SeekBar seekBar) {
        }
    };
    @Bind({2131755277})
    RecyclerView mCategoriesRecyclerView;
    @Bind({2131755270})
    SeekBar mCustomSeekBar;
    @Bind({2131755269})
    ImageView mLocationIcon;
    @Bind({2131755275})
    AppCompatButton mSortDistanceButton;
    @Bind({2131755273})
    AppCompatButton mSortPopularButton;
    @Bind({2131755274})
    AppCompatButton mSortTimeButton;
    @Bind({2131755271})
    TextView mToDistanceLabel;
    @Bind({2131755266})
    AppCompatButton mTodayButton;
    @Bind({2131755267})
    AppCompatButton mTomorrowButton;
    @Bind({2131755238})
    Toolbar mToolbar;

    public static Intent a(Context context, WGTimelineParameters wGTimelineParameters, WGDetectedCity wGDetectedCity) {
        Intent intent = new Intent(context, FilterActivity.class);
        intent.putExtra("ARG_TIMELINE_PARAMETERS", wGTimelineParameters);
        intent.putExtra("ARG_DETECTED_CITY", wGDetectedCity);
        return intent;
    }

    public void onBackPressed() {
        setResult(0);
        finish();
    }

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_filter);
        ButterKnife.bind(this);
        setSupportActionBar(this.mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.close_icon);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle(R.string.wingit_filters_label_navbar_title);
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("ARG_TIMELINE_PARAMETERS")) {
            this.a = (WGTimelineParameters) intent.getSerializableExtra("ARG_TIMELINE_PARAMETERS");
            this.b = (WGDetectedCity) intent.getSerializableExtra("ARG_DETECTED_CITY");
            if (this.a.categories == null || this.a.categories.size() == 0) {
                this.d = new ArrayList(WINGiTDataHolder.getCategories());
            } else {
                this.d = new ArrayList(this.a.categories);
            }
            if (this.a.offsetDay == Day.TODAY) {
                onTodayButtonClick();
            } else {
                onTomorrowButtonClick();
            }
            switch (this.a.sortType) {
                case DATE:
                    this.mSortTimeButton.setSelected(true);
                    break;
                case DISTANCE:
                    this.mSortDistanceButton.setSelected(true);
                    break;
                case BUZZ:
                    this.mSortPopularButton.setSelected(true);
                    break;
            }
            this.c = new CategoryAdapter(WINGiTDataHolder.getCategories(), this.d);
            this.mCategoriesRecyclerView.setLayoutManager(new LinearLayoutManager(this, 1, false));
            this.mCategoriesRecyclerView.setFocusable(false);
            this.mCategoriesRecyclerView.setNestedScrollingEnabled(false);
            this.mCategoriesRecyclerView.setHasFixedSize(true);
            this.mCategoriesRecyclerView.setAdapter(this.c);
            this.mCustomSeekBar.setOnSeekBarChangeListener(this.f);
            this.mCustomSeekBar.setMax(100);
            this.mCustomSeekBar.setProgress(100);
            int color;
            if (this.b == null || !this.b.isInsideCity().booleanValue() || this.b.getDistance().doubleValue() > 150000.0d) {
                color = getResources().getColor(R.color.black_54);
                if (VERSION.SDK_INT >= 16) {
                    this.mCustomSeekBar.getThumb().setColorFilter(color, Mode.SRC_IN);
                }
                this.mCustomSeekBar.getProgressDrawable().setColorFilter(color, Mode.SRC_IN);
                this.mCustomSeekBar.setProgress(0);
                this.mCustomSeekBar.setEnabled(false);
                this.mToDistanceLabel.setVisibility(8);
                this.mLocationIcon.setEnabled(false);
                if (this.mSortDistanceButton.isSelected()) {
                    onSortTimeClick();
                }
                this.mSortDistanceButton.setEnabled(false);
                return;
            }
            color = getResources().getColor(R.color.wingit_primary_color);
            if (VERSION.SDK_INT >= 16) {
                this.mCustomSeekBar.getThumb().setColorFilter(color, Mode.SRC_IN);
            }
            this.mCustomSeekBar.getProgressDrawable().setColorFilter(color, Mode.SRC_IN);
            this.mCustomSeekBar.setEnabled(true);
            this.mToDistanceLabel.setVisibility(0);
            this.mLocationIcon.setEnabled(true);
            this.mSortDistanceButton.setEnabled(true);
            if (this.a.radius != null) {
                this.mCustomSeekBar.setProgress(Arrays.asList(e).indexOf(this.a.radius));
                if (Locale.getDefault().getLanguage().startsWith("fr")) {
                    this.mToDistanceLabel.setText(jm.a(this, (double) this.a.radius.intValue()));
                    return;
                } else {
                    this.mToDistanceLabel.setText(jm.b(this, (double) this.a.radius.intValue()));
                    return;
                }
            }
            this.mCustomSeekBar.setProgress(150000);
            if (Locale.getDefault().getLanguage().startsWith("fr")) {
                this.mToDistanceLabel.setText(jm.a(this, 150000.0d));
            } else {
                this.mToDistanceLabel.setText(jm.b(this, 150000.0d));
            }
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_filter, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case 16908332:
                onBackPressed();
                return true;
            case R.id.filter_apply /*2131755957*/:
                WGTimelineParameters wGTimelineParameters = iz.a != null ? new WGTimelineParameters(iz.a) : new WGTimelineParameters();
                if (this.mTodayButton.isSelected()) {
                    wGTimelineParameters.offsetDay = Day.TODAY;
                } else {
                    wGTimelineParameters.offsetDay = Day.TOMORROW;
                }
                if (this.mSortPopularButton.isSelected()) {
                    wGTimelineParameters.sortType = SortType.BUZZ;
                } else if (this.mSortTimeButton.isSelected()) {
                    wGTimelineParameters.sortType = SortType.DATE;
                } else {
                    wGTimelineParameters.sortType = SortType.DISTANCE;
                }
                wGTimelineParameters.categories = this.c.a;
                if (this.mCustomSeekBar.isEnabled()) {
                    wGTimelineParameters.radius = e[this.mCustomSeekBar.getProgress()];
                }
                Intent intent = new Intent();
                intent.putExtra("ARG_TIMELINE_PARAMETERS", wGTimelineParameters);
                setResult(-1, intent);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(menuItem);
        }
    }

    @OnClick({2131755275})
    public void onSortDistanceClick() {
        this.mSortTimeButton.setSelected(false);
        this.mSortPopularButton.setSelected(false);
        this.mSortDistanceButton.setSelected(true);
    }

    @OnClick({2131755273})
    public void onSortPopularClick() {
        this.mSortTimeButton.setSelected(false);
        this.mSortPopularButton.setSelected(true);
        this.mSortDistanceButton.setSelected(false);
    }

    @OnClick({2131755274})
    public void onSortTimeClick() {
        this.mSortTimeButton.setSelected(true);
        this.mSortPopularButton.setSelected(false);
        this.mSortDistanceButton.setSelected(false);
    }

    @OnClick({2131755266})
    public void onTodayButtonClick() {
        this.mTomorrowButton.setSelected(false);
        this.mTodayButton.setSelected(true);
    }

    @OnClick({2131755267})
    public void onTomorrowButtonClick() {
        this.mTomorrowButton.setSelected(true);
        this.mTodayButton.setSelected(false);
    }
}
