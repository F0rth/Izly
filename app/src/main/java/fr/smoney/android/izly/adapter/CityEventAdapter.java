package fr.smoney.android.izly.adapter;

import android.content.Context;
import android.os.Build.VERSION;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatButton;
import android.text.format.DateFormat;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;

import com.squareup.picasso.Picasso;
import com.thewingitapp.thirdparties.wingitlib.model.WGEvent;
import com.thewingitapp.thirdparties.wingitlib.util.WINGiTUtil;

import fr.smoney.android.izly.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public final class CityEventAdapter extends ab {
    public boolean c = false;

    static class LoadingViewHolder extends android.support.v7.widget.RecyclerView.ViewHolder {
        @Bind({2131755612})
        ProgressBar mProgressBar;

        public LoadingViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    static class TomorrowViewHolder extends android.support.v7.widget.RecyclerView.ViewHolder {
        @Bind({2131755267})
        AppCompatButton mTomorrowButton;
    }

    static class ViewHolder extends android.support.v7.widget.RecyclerView.ViewHolder {
        @Nullable
        @Bind({2131755260})
        AppCompatButton mBookTicketButton;
        @Bind({2131755242})
        ImageView mContentImage;
        @Bind({2131755607})
        TextView mContentView;
        @Nullable
        @Bind({2131755243})
        TextView mPartnerView;
        @Nullable
        @Bind({2131755611})
        TextView mPlaceText;
        @Nullable
        @Bind({2131755250})
        RelativeLayout mPoweredByWINGiTView;
        @Bind({2131755608})
        TextView mSubTextView;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    public final void a() {
        this.c = false;
        super.a();
    }

    public final void a(boolean z) {
        this.c = z;
        new Handler().post(new Runnable(this) {
            final /* synthetic */ CityEventAdapter a;

            {
                this.a = r1;
            }

            public final void run() {
                this.a.notifyDataSetChanged();
            }
        });
    }

    public final int getItemCount() {
        return (this.c ? 1 : 0) + super.getItemCount();
    }

    public final int getItemViewType(int i) {
        return i == 0 ? 0 : i < this.a.size() ? 1 : this.c ? 2 : 3;
    }

    public final void onBindViewHolder(android.support.v7.widget.RecyclerView.ViewHolder viewHolder, int i) {
        Context context = viewHolder.itemView.getContext();
        if (viewHolder instanceof ViewHolder) {
            final WGEvent wGEvent = (WGEvent) this.a.get(i);
            ViewHolder viewHolder2 = (ViewHolder) viewHolder;
            if (!(getItemViewType(i) != 0 || viewHolder2.mPartnerView == null || viewHolder2.mPlaceText == null || viewHolder2.mBookTicketButton == null)) {
                viewHolder2.mPartnerView.setVisibility(wGEvent.isPromoted().booleanValue() ? 0 : 8);
                viewHolder2.mBookTicketButton.setVisibility(wGEvent.isTicketCompliant() ? 0 : 8);
                viewHolder2.mBookTicketButton.setOnClickListener(new OnClickListener(this) {
                    final /* synthetic */ CityEventAdapter b;

                    public final void onClick(View view) {
                        if (this.b.b != null) {
                            this.b.b.b(wGEvent);
                        }
                    }
                });
                if (wGEvent.getPlace().getName() == null || wGEvent.getPlace().getName().isEmpty()) {
                    viewHolder2.mPlaceText.setText(wGEvent.getPlace().getAddress());
                } else {
                    viewHolder2.mPlaceText.setText(wGEvent.getPlace().getName());
                }
            }
            if (viewHolder2.mPoweredByWINGiTView != null) {
                viewHolder2.mPoweredByWINGiTView.setOnClickListener(new OnClickListener(this) {
                    final /* synthetic */ CityEventAdapter a;

                    {
                        this.a = r1;
                    }

                    public final void onClick(View view) {
                        if (this.a.b != null) {
                            this.a.b.a();
                        }
                    }
                });
            }
            viewHolder2.mContentView.setVisibility(0);
            if (!wGEvent.getTitle().isEmpty()) {
                viewHolder2.mContentView.setText(wGEvent.getTitle());
            } else if (wGEvent.getEventDescription().isEmpty()) {
                viewHolder2.mContentView.setVisibility(8);
            } else {
                viewHolder2.mContentView.setText(wGEvent.getEventDescription());
            }
            TextView textView = viewHolder2.mSubTextView;
            Date eventDate = wGEvent.getEventDate();
            textView.setText(DateUtils.getRelativeTimeSpanString(eventDate.getTime(), System.currentTimeMillis(), WINGiTUtil.ONE_DAY).toString() + " " + (DateFormat.is24HourFormat(context) ? new SimpleDateFormat("HH:mm", Locale.getDefault()) : new SimpleDateFormat("hh:mma", Locale.getDefault())).format(eventDate));
            if (wGEvent.getSquareImage() != null) {
                Picasso.with(context).load(wGEvent.getSquareImage()).error(wGEvent.getDefaultPlaceholder()).into(viewHolder2.mContentImage);
            } else if (VERSION.SDK_INT >= 16) {
                viewHolder2.mContentImage.setBackground(null);
            } else {
                viewHolder2.mContentImage.setBackgroundDrawable(null);
            }
            viewHolder.itemView.setOnClickListener(new OnClickListener(this) {
                final /* synthetic */ CityEventAdapter b;

                public final void onClick(View view) {
                    if (this.b.b != null) {
                        this.b.b.a(wGEvent);
                    }
                }
            });
        } else if (viewHolder instanceof LoadingViewHolder) {
            ((LoadingViewHolder) viewHolder).mProgressBar.setIndeterminate(true);
        }
    }

    public final android.support.v7.widget.RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return i == 0 ? new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item_event_city_header, viewGroup, false)) : i == 1 ? new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item_event_city, viewGroup, false)) : new LoadingViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item_loading, viewGroup, false));
    }
}
