package com.ad4screen.sdk.activities;

import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceActivity;

import com.ad4screen.sdk.A4S;
import com.ad4screen.sdk.common.annotations.API;

@API
public class A4SPreferenceActivity extends PreferenceActivity {
    public A4S getA4S() {
        return A4S.get(this);
    }

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
    }

    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        getA4S().setIntent(intent);
    }

    protected void onPause() {
        super.onPause();
        getA4S().stopActivity(this);
    }

    protected void onResume() {
        super.onResume();
        getA4S().startActivity(this);
    }
}
