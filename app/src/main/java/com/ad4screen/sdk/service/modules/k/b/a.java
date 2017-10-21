package com.ad4screen.sdk.service.modules.k.b;

import android.os.Bundle;

import com.ad4screen.sdk.analytics.Cart;
import com.ad4screen.sdk.analytics.Lead;
import com.ad4screen.sdk.analytics.Purchase;

public interface a {
    String a();

    void a(long j, Bundle bundle, String... strArr);

    void a(Cart cart, Bundle bundle);

    void a(Lead lead, Bundle bundle);

    void a(Purchase purchase, Bundle bundle);

    void a(String str);

    int b();

    void c();

    void d();

    void e();
}
