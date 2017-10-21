package fr.smoney.android.izly.ui;

import android.os.Bundle;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import fr.smoney.android.izly.R;
import fr.smoney.android.izly.SmoneyABSActivity;

public class PaymentInfoActivity extends SmoneyABSActivity {
    @Bind({2131755814})
    TextView infos;
    @Bind({2131755813})
    TextView title;

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.payement_info_activity);
        ButterKnife.bind(this);
        int intExtra = getIntent().getIntExtra("info_extra", -1);
        int intExtra2 = getIntent().getIntExtra("title_extra", -1);
        if (intExtra == -1 || intExtra2 == -1) {
            finish();
            return;
        }
        this.title.setText(intExtra2);
        this.infos.setText(intExtra);
    }
}
