package fr.smoney.android.izly.ui;

import android.os.Bundle;
import android.view.KeyEvent;

import butterknife.Bind;
import butterknife.ButterKnife;
import fr.smoney.android.izly.R;
import fr.smoney.android.izly.SmoneyABSActivity;
import fr.smoney.android.izly.data.model.MakeBankAccountUpdateData;
import fr.smoney.android.izly.ui.widget.DetailTwoText;

public class AddChangeTransferAccountResultActivity extends SmoneyABSActivity {
    private int b;
    private String c;
    private String d;
    private String e;
    @Bind({2131755289})
    DetailTwoText mTextViewAlias;
    @Bind({2131755290})
    DetailTwoText mTextViewBic;
    @Bind({2131755291})
    DetailTwoText mTextViewIban;

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.add_transfer_account_result);
        ButterKnife.bind(this);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            this.b = extras.getInt("fr.smoney.android.izly.intentExtrasMode");
            if (this.b == 0 || this.b == 1) {
                MakeBankAccountUpdateData makeBankAccountUpdateData = (MakeBankAccountUpdateData) extras.getParcelable("fr.smoney.android.izly.extras.addTransferAccountData");
                if (makeBankAccountUpdateData != null) {
                    this.e = makeBankAccountUpdateData.a;
                    this.c = makeBankAccountUpdateData.e;
                    this.d = makeBankAccountUpdateData.f;
                }
            }
            String str = this.e;
            String str2 = this.c;
            String str3 = this.d;
            this.mTextViewAlias.setRightText(str);
            this.mTextViewIban.setRightText(str2);
            this.mTextViewBic.setRightText(str3);
            return;
        }
        throw new RuntimeException("AddTransferAccountActivity.INTENT_EXTRAS_MODE is mandatory!");
    }

    public boolean onKeyUp(int i, KeyEvent keyEvent) {
        switch (i) {
            case 4:
                setResult(-1);
                finish();
                return true;
            default:
                return super.onKeyUp(i, keyEvent);
        }
    }
}
