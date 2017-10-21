package com.google.android.gms.location.places.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;
import com.google.android.gms.R;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete.IntentBuilder;
import com.google.android.gms.maps.model.LatLngBounds;

public class SupportPlaceAutocompleteFragment extends Fragment {
    private View zzaRh;
    private View zzaRi;
    private EditText zzaRj;
    @Nullable
    private LatLngBounds zzaRk;
    @Nullable
    private AutocompleteFilter zzaRl;
    @Nullable
    private PlaceSelectionListener zzaRm;

    private void zzzF() {
        int i = 0;
        int i2 = !this.zzaRj.getText().toString().isEmpty() ? 1 : 0;
        View view = this.zzaRi;
        if (i2 == 0) {
            i = 8;
        }
        view.setVisibility(i);
    }

    private void zzzG() {
        int i;
        int connectionStatusCode;
        try {
            startActivityForResult(new IntentBuilder(2).setBoundsBias(this.zzaRk).setFilter(this.zzaRl).zzeq(this.zzaRj.getText().toString()).zzig(1).build(getActivity()), 1);
            i = -1;
        } catch (Throwable e) {
            connectionStatusCode = e.getConnectionStatusCode();
            Log.e("Places", "Could not open autocomplete activity", e);
            i = connectionStatusCode;
        } catch (Throwable e2) {
            connectionStatusCode = e2.errorCode;
            Log.e("Places", "Could not open autocomplete activity", e2);
            i = connectionStatusCode;
        }
        if (i != -1) {
            GoogleApiAvailability.getInstance().showErrorDialogFragment(getActivity(), i, 2);
        }
    }

    public void onActivityResult(int i, int i2, Intent intent) {
        if (i == 1) {
            if (i2 == -1) {
                Place place = PlaceAutocomplete.getPlace(getActivity(), intent);
                if (this.zzaRm != null) {
                    this.zzaRm.onPlaceSelected(place);
                }
                setText(place.getName().toString());
            } else if (i2 == 2) {
                Status status = PlaceAutocomplete.getStatus(getActivity(), intent);
                if (this.zzaRm != null) {
                    this.zzaRm.onError(status);
                }
            }
        }
        super.onActivityResult(i, i2, intent);
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.place_autocomplete_fragment, viewGroup, false);
        this.zzaRh = inflate.findViewById(R.id.place_autocomplete_search_button);
        this.zzaRi = inflate.findViewById(R.id.place_autocomplete_clear_button);
        this.zzaRj = (EditText) inflate.findViewById(R.id.place_autocomplete_search_input);
        OnClickListener anonymousClass1 = new OnClickListener(this) {
            final /* synthetic */ SupportPlaceAutocompleteFragment zzaRo;

            {
                this.zzaRo = r1;
            }

            public void onClick(View view) {
                this.zzaRo.zzzG();
            }
        };
        this.zzaRh.setOnClickListener(anonymousClass1);
        this.zzaRj.setOnClickListener(anonymousClass1);
        this.zzaRi.setOnClickListener(new OnClickListener(this) {
            final /* synthetic */ SupportPlaceAutocompleteFragment zzaRo;

            {
                this.zzaRo = r1;
            }

            public void onClick(View view) {
                this.zzaRo.setText("");
            }
        });
        zzzF();
        return inflate;
    }

    public void onDestroyView() {
        this.zzaRh = null;
        this.zzaRi = null;
        this.zzaRj = null;
        super.onDestroyView();
    }

    public void setBoundsBias(@Nullable LatLngBounds latLngBounds) {
        this.zzaRk = latLngBounds;
    }

    public void setFilter(@Nullable AutocompleteFilter autocompleteFilter) {
        this.zzaRl = autocompleteFilter;
    }

    public void setHint(CharSequence charSequence) {
        this.zzaRj.setHint(charSequence);
        this.zzaRh.setContentDescription(charSequence);
    }

    public void setOnPlaceSelectedListener(PlaceSelectionListener placeSelectionListener) {
        this.zzaRm = placeSelectionListener;
    }

    public void setText(CharSequence charSequence) {
        this.zzaRj.setText(charSequence);
        zzzF();
    }
}
