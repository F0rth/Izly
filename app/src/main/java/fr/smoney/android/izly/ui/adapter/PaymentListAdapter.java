package fr.smoney.android.izly.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import fr.smoney.android.izly.R;
import fr.smoney.android.izly.ui.PaymentInfoActivity;

public final class PaymentListAdapter extends BaseAdapter {
    private Context a;

    class PlaceHolder {
        final /* synthetic */ PaymentListAdapter a;
        @Bind({2131755815})
        TextView paymentDesc;
        @Bind({2131755816})
        ImageView paymentInfos;
        @Bind({2131755315})
        ImageView paymentLogo;
        @Bind({2131755813})
        TextView paymentTitle;

        public PlaceHolder(PaymentListAdapter paymentListAdapter, View view) {
            this.a = paymentListAdapter;
            ButterKnife.bind(this, view);
        }
    }

    public PaymentListAdapter(Context context) {
        this.a = context;
    }

    public final int getCount() {
        return ch.values().length;
    }

    public final /* synthetic */ Object getItem(int i) {
        return ch.values()[i];
    }

    public final long getItemId(int i) {
        return (long) i;
    }

    public final View getView(int i, View view, ViewGroup viewGroup) {
        PlaceHolder placeHolder;
        final ch chVar = ch.values()[i];
        LayoutInflater from = LayoutInflater.from(this.a);
        if (view == null) {
            view = from.inflate(R.layout.payement_type_cell, viewGroup, false);
            placeHolder = new PlaceHolder(this, view);
            view.setTag(placeHolder);
        } else {
            placeHolder = (PlaceHolder) view.getTag();
        }
        if (chVar.compareTo(ch.d) == 0) {
            placeHolder.paymentLogo.setImageResource(chVar.e);
            placeHolder.paymentLogo.setOnClickListener(new OnClickListener(this) {
                final /* synthetic */ PaymentListAdapter b;

                public final void onClick(View view) {
                    Intent intent = new Intent(this.b.a, PaymentInfoActivity.class);
                    intent.putExtra("info_extra", chVar.h);
                    intent.putExtra("title_extra", chVar.f);
                    this.b.a.startActivity(intent);
                }
            });
            placeHolder.paymentTitle.setText(chVar.f);
            placeHolder.paymentTitle.setOnClickListener(new OnClickListener(this) {
                final /* synthetic */ PaymentListAdapter b;

                public final void onClick(View view) {
                    Intent intent = new Intent(this.b.a, PaymentInfoActivity.class);
                    intent.putExtra("info_extra", chVar.h);
                    intent.putExtra("title_extra", chVar.f);
                    this.b.a.startActivity(intent);
                }
            });
            placeHolder.paymentDesc.setText(chVar.g);
            placeHolder.paymentDesc.setOnClickListener(new OnClickListener(this) {
                final /* synthetic */ PaymentListAdapter b;

                public final void onClick(View view) {
                    Intent intent = new Intent(this.b.a, PaymentInfoActivity.class);
                    intent.putExtra("info_extra", chVar.h);
                    intent.putExtra("title_extra", chVar.f);
                    this.b.a.startActivity(intent);
                }
            });
            placeHolder.paymentInfos.setOnClickListener(new OnClickListener(this) {
                final /* synthetic */ PaymentListAdapter b;

                public final void onClick(View view) {
                    Intent intent = new Intent(this.b.a, PaymentInfoActivity.class);
                    intent.putExtra("info_extra", chVar.h);
                    intent.putExtra("title_extra", chVar.f);
                    this.b.a.startActivity(intent);
                }
            });
        } else {
            placeHolder.paymentLogo.setImageResource(chVar.e);
            placeHolder.paymentTitle.setText(chVar.f);
            placeHolder.paymentDesc.setText(chVar.g);
            placeHolder.paymentInfos.setOnClickListener(new OnClickListener(this) {
                final /* synthetic */ PaymentListAdapter b;

                public final void onClick(View view) {
                    Intent intent = new Intent(this.b.a, PaymentInfoActivity.class);
                    intent.putExtra("info_extra", chVar.h);
                    intent.putExtra("title_extra", chVar.f);
                    this.b.a.startActivity(intent);
                }
            });
        }
        return view;
    }
}
