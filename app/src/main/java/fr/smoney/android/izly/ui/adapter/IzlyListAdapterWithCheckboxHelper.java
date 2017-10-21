package fr.smoney.android.izly.ui.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import defpackage.kh;
import fr.smoney.android.izly.R;

public final class IzlyListAdapterWithCheckboxHelper extends BaseAdapter {
    private Context a;
    private ce[] b;

    class PlaceHolder {
        final /* synthetic */ IzlyListAdapterWithCheckboxHelper a;
        @Bind({2131755494})
        CheckBox checkbox;
        @Nullable
        @Bind({2131755493})
        TextView desc;
        @Nullable
        @Bind({2131755491})
        ImageView logo;
        @Bind({2131755131})
        TextView title;

        public PlaceHolder(IzlyListAdapterWithCheckboxHelper izlyListAdapterWithCheckboxHelper, View view) {
            this.a = izlyListAdapterWithCheckboxHelper;
            ButterKnife.bind(this, view);
        }
    }

    public IzlyListAdapterWithCheckboxHelper(Context context, ce[] ceVarArr) {
        this.a = context;
        this.b = ceVarArr;
    }

    public final int getCount() {
        return this.b.length;
    }

    public final /* bridge */ /* synthetic */ Object getItem(int i) {
        return this.b[i];
    }

    public final long getItemId(int i) {
        return (long) i;
    }

    public final View getView(int i, View view, ViewGroup viewGroup) {
        PlaceHolder placeHolder;
        ce ceVar = this.b[i];
        LayoutInflater from = LayoutInflater.from(this.a);
        if (view == null) {
            View inflate = ceVar.d() == 0 ? from.inflate(R.layout.default_cell_with_checkbox_izly_helper, viewGroup, false) : from.inflate(ceVar.d(), viewGroup, false);
            PlaceHolder placeHolder2 = new PlaceHolder(this, inflate);
            inflate.setTag(placeHolder2);
            view = inflate;
            placeHolder = placeHolder2;
        } else {
            placeHolder = (PlaceHolder) view.getTag();
        }
        if (ceVar.c() != 0) {
            placeHolder.logo.setImageResource(ceVar.c());
        } else if (placeHolder.logo != null) {
            placeHolder.logo.setVisibility(8);
        }
        if (ceVar.a() != 0) {
            placeHolder.title.setText(ceVar.a());
        }
        if (ceVar.b() != 0) {
            placeHolder.desc.setText(ceVar.b());
        } else if (placeHolder.desc != null) {
            placeHolder.desc.setVisibility(8);
        }
        placeHolder.checkbox.setButtonDrawable(Resources.getSystem().getIdentifier("btn_check_holo_light", "drawable", kh.ANDROID_CLIENT_TYPE));
        placeHolder.checkbox.setChecked(false);
        return view;
    }
}
