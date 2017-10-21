package fr.smoney.android.izly.data.requestmanager;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.util.SparseArray;

import com.foxykeep.datadroid.requestmanager.RequestManager;
import com.foxykeep.datadroid.service.WorkerService;

import defpackage.cl;
import fr.smoney.android.izly.data.model.NewsFeedItem;
import fr.smoney.android.izly.data.service.SmoneyService;

import java.util.ArrayList;
import java.util.Random;

public final class SmoneyRequestManager extends RequestManager {
    public static Random a = new Random();
    public SparseArray<Intent> b = new SparseArray();
    public Context c;
    ArrayList<a> d = new ArrayList();
    public EvalReceiver e = new EvalReceiver(this, this.g);
    public cl f;
    private Handler g = new Handler();

    public SmoneyRequestManager(Context context, cl clVar) {
        this.c = context;
        this.f = clVar;
    }

    public final int a(String str) {
        int size = this.b.size();
        for (int i = 0; i < size; i++) {
            Intent intent = (Intent) this.b.valueAt(i);
            if (intent.getIntExtra(WorkerService.INTENT_EXTRA_WORKER_TYPE, -1) == 228 && intent.getStringExtra("fr.smoney.android.izly.extras.GetLogonInfosUserId").equals(str)) {
                return this.b.keyAt(i);
            }
        }
        int nextInt = a.nextInt(1000000);
        Intent intent2 = new Intent(this.c, SmoneyService.class);
        intent2.putExtra(WorkerService.INTENT_EXTRA_WORKER_TYPE, 228);
        intent2.putExtra(WorkerService.INTENT_EXTRA_RECEIVER, this.e);
        intent2.putExtra("com.foxykeep.datadroid.extras.requestId", nextInt);
        intent2.putExtra("fr.smoney.android.izly.extras.GetLogonInfosUserId", str);
        this.c.startService(intent2);
        this.b.append(nextInt, intent2);
        this.f.b = null;
        this.f.d = null;
        return nextInt;
    }

    public final int a(String str, String str2) {
        int size = this.b.size();
        for (int i = 0; i < size; i++) {
            Intent intent = (Intent) this.b.valueAt(i);
            if (intent.getIntExtra(WorkerService.INTENT_EXTRA_WORKER_TYPE, -1) == 105 && intent.getStringExtra("fr.smoney.android.izly.extras.getBookmarksUserId").equals(str) && intent.getStringExtra("fr.smoney.android.izly.extras.getBookmarksSessionId").equals(str2)) {
                return this.b.keyAt(i);
            }
        }
        int nextInt = a.nextInt(1000000);
        Intent intent2 = new Intent(this.c, SmoneyService.class);
        intent2.putExtra(WorkerService.INTENT_EXTRA_WORKER_TYPE, 105);
        intent2.putExtra(WorkerService.INTENT_EXTRA_RECEIVER, this.e);
        intent2.putExtra("com.foxykeep.datadroid.extras.requestId", nextInt);
        intent2.putExtra("fr.smoney.android.izly.extras.getBookmarksUserId", str);
        intent2.putExtra("fr.smoney.android.izly.extras.getBookmarksSessionId", str2);
        this.c.startService(intent2);
        this.b.append(nextInt, intent2);
        this.f.ao = null;
        this.f.ap = null;
        return nextInt;
    }

    public final int a(String str, String str2, double d, double d2, int i, int i2) {
        int size = this.b.size();
        for (int i3 = 0; i3 < size; i3++) {
            Intent intent = (Intent) this.b.valueAt(i3);
            if (intent.getIntExtra(WorkerService.INTENT_EXTRA_WORKER_TYPE, -1) == 213 && intent.getStringExtra("fr.smoney.android.izly.extras.GetNearProListUserId").equals(str) && intent.getStringExtra("fr.smoney.android.izly.extras.GetNearProListSessionId").equals(str2) && intent.getDoubleExtra("fr.smoney.android.izly.extras.GetNearProListLatitude", 0.0d) == d && intent.getDoubleExtra("fr.smoney.android.izly.extras.GetNearProListLongitude", 0.0d) == d2 && intent.getIntExtra("fr.smoney.android.izly.extras.GetNearProListCategory", -1) == i && intent.getIntExtra("fr.smoney.android.izly.extras.GetNearProListRadius", -1) == 0) {
                return this.b.keyAt(i3);
            }
        }
        int nextInt = a.nextInt(1000000);
        Intent intent2 = new Intent(this.c, SmoneyService.class);
        intent2.putExtra(WorkerService.INTENT_EXTRA_WORKER_TYPE, 213);
        intent2.putExtra(WorkerService.INTENT_EXTRA_RECEIVER, this.e);
        intent2.putExtra("com.foxykeep.datadroid.extras.requestId", nextInt);
        intent2.putExtra("fr.smoney.android.izly.extras.GetNearProListUserId", str);
        intent2.putExtra("fr.smoney.android.izly.extras.GetNearProListSessionId", str2);
        intent2.putExtra("fr.smoney.android.izly.extras.GetNearProListLatitude", d);
        intent2.putExtra("fr.smoney.android.izly.extras.GetNearProListLongitude", d2);
        intent2.putExtra("fr.smoney.android.izly.extras.GetNearProListCategory", i);
        intent2.putExtra("fr.smoney.android.izly.extras.GetNearProListRadius", 0);
        this.c.startService(intent2);
        this.b.append(nextInt, intent2);
        this.f.aW = null;
        this.f.aX = null;
        return nextInt;
    }

    public final int a(String str, String str2, double d, String str3, String str4) {
        int size = this.b.size();
        for (int i = 0; i < size; i++) {
            Intent intent = (Intent) this.b.valueAt(i);
            if (intent.getIntExtra(WorkerService.INTENT_EXTRA_WORKER_TYPE, -1) == 11 && intent.getStringExtra("fr.smoney.android.izly.extras.p2pPayUserId").equals(str) && intent.getStringExtra("fr.smoney.android.izly.extras.p2pPayRecipient").equals(str2) && intent.getDoubleExtra("fr.smoney.android.izly.extras.p2pPayAmount", -1.0d) == d && intent.getStringExtra("fr.smoney.android.izly.extras.p2pPayMessage").equals(str3) && intent.getStringExtra("fr.smoney.android.izly.extras.p2pPaySessionId").equals(str4)) {
                return this.b.keyAt(i);
            }
        }
        int nextInt = a.nextInt(1000000);
        Intent intent2 = new Intent(this.c, SmoneyService.class);
        intent2.putExtra(WorkerService.INTENT_EXTRA_WORKER_TYPE, 11);
        intent2.putExtra(WorkerService.INTENT_EXTRA_RECEIVER, this.e);
        intent2.putExtra("com.foxykeep.datadroid.extras.requestId", nextInt);
        intent2.putExtra("fr.smoney.android.izly.extras.p2pPayUserId", str);
        intent2.putExtra("fr.smoney.android.izly.extras.p2pPayRecipient", str2);
        intent2.putExtra("fr.smoney.android.izly.extras.p2pPayAmount", d);
        intent2.putExtra("fr.smoney.android.izly.extras.p2pPayMessage", str3);
        intent2.putExtra("fr.smoney.android.izly.extras.p2pPaySessionId", str4);
        this.c.startService(intent2);
        this.b.append(nextInt, intent2);
        this.f.f = null;
        this.f.g = null;
        return nextInt;
    }

    public final int a(String str, String str2, long j) {
        int size = this.b.size();
        for (int i = 0; i < size; i++) {
            Intent intent = (Intent) this.b.valueAt(i);
            if (intent.getIntExtra(WorkerService.INTENT_EXTRA_WORKER_TYPE, -1) == 72 && intent.getStringExtra("fr.smoney.android.izly.extras.p2pPayRequestHideUserId").equals(str) && intent.getStringExtra("fr.smoney.android.izly.extras.p2pPayRequestHideSessionId").equals(str2) && ((long) intent.getIntExtra("fr.smoney.android.izly.extras.p2pPayRequestHidePayRequestId", -1)) == j) {
                return this.b.keyAt(i);
            }
        }
        int nextInt = a.nextInt(1000000);
        Intent intent2 = new Intent(this.c, SmoneyService.class);
        intent2.putExtra(WorkerService.INTENT_EXTRA_WORKER_TYPE, 72);
        intent2.putExtra(WorkerService.INTENT_EXTRA_RECEIVER, this.e);
        intent2.putExtra("com.foxykeep.datadroid.extras.requestId", nextInt);
        intent2.putExtra("fr.smoney.android.izly.extras.p2pPayRequestHideUserId", str);
        intent2.putExtra("fr.smoney.android.izly.extras.p2pPayRequestHideSessionId", str2);
        intent2.putExtra("fr.smoney.android.izly.extras.p2pPayRequestHidePayRequestId", j);
        this.c.startService(intent2);
        this.b.append(nextInt, intent2);
        this.f.S = -1;
        this.f.T = null;
        return nextInt;
    }

    public final int a(String str, String str2, long j, int i) {
        int size = this.b.size();
        for (int i2 = 0; i2 < size; i2++) {
            Intent intent = (Intent) this.b.valueAt(i2);
            if (intent.getIntExtra(WorkerService.INTENT_EXTRA_WORKER_TYPE, -1) == 63 && intent.getStringExtra("fr.smoney.android.izly.extras.p2pGetCancelUserId").equals(str) && intent.getStringExtra("fr.smoney.android.izly.extras.p2pGetCancelSessionId").equals(str2) && ((long) intent.getIntExtra("fr.smoney.android.izly.extras.p2pGetCancelGetId", -1)) == j && intent.getIntExtra("fr.smoney.android.izly.extras.p2pGetCancelGetType", -1) == i) {
                return this.b.keyAt(i2);
            }
        }
        int nextInt = a.nextInt(1000000);
        Intent intent2 = new Intent(this.c, SmoneyService.class);
        intent2.putExtra(WorkerService.INTENT_EXTRA_WORKER_TYPE, 63);
        intent2.putExtra(WorkerService.INTENT_EXTRA_RECEIVER, this.e);
        intent2.putExtra("com.foxykeep.datadroid.extras.requestId", nextInt);
        intent2.putExtra("fr.smoney.android.izly.extras.p2pGetCancelUserId", str);
        intent2.putExtra("fr.smoney.android.izly.extras.p2pGetCancelSessionId", str2);
        intent2.putExtra("fr.smoney.android.izly.extras.p2pGetCancelGetId", j);
        intent2.putExtra("fr.smoney.android.izly.extras.p2pGetCancelGetType", i);
        this.c.startService(intent2);
        this.b.append(nextInt, intent2);
        this.f.K = -1;
        this.f.L = null;
        return nextInt;
    }

    public final int a(String str, String str2, long j, int i, int i2, boolean z) {
        int size = this.b.size();
        for (int i3 = 0; i3 < size; i3++) {
            Intent intent = (Intent) this.b.valueAt(i3);
            if (intent.getIntExtra(WorkerService.INTENT_EXTRA_WORKER_TYPE, -1) == 21 && intent.getStringExtra("fr.smoney.android.izly.extras.transactionListUserId").equals(str) && intent.getStringExtra("fr.smoney.android.izly.extras.transactionListSessionId").equals(str2) && intent.getLongExtra("fr.smoney.android.izly.extras.transactionListFirstId", -1) == j && intent.getIntExtra("fr.smoney.android.izly.extras.transactionListNbItems", -1) == i && intent.getIntExtra("fr.smoney.android.izly.extras.transactionListFilter", -1) == i2) {
                return this.b.keyAt(i3);
            }
        }
        int nextInt = a.nextInt(1000000);
        Intent intent2 = new Intent(this.c, SmoneyService.class);
        intent2.putExtra(WorkerService.INTENT_EXTRA_WORKER_TYPE, 21);
        intent2.putExtra(WorkerService.INTENT_EXTRA_RECEIVER, this.e);
        intent2.putExtra("com.foxykeep.datadroid.extras.requestId", nextInt);
        intent2.putExtra("fr.smoney.android.izly.extras.transactionListUserId", str);
        intent2.putExtra("fr.smoney.android.izly.extras.transactionListSessionId", str2);
        intent2.putExtra("fr.smoney.android.izly.extras.transactionListFirstId", j);
        intent2.putExtra("fr.smoney.android.izly.extras.transactionListNbItems", i);
        intent2.putExtra("fr.smoney.android.izly.extras.transactionListFilter", i2);
        this.c.startService(intent2);
        this.b.append(nextInt, intent2);
        if (z) {
            this.f.n = null;
        }
        this.f.o = null;
        return nextInt;
    }

    public final int a(String str, String str2, long j, int i, String str3, double d) {
        int size = this.b.size();
        for (int i2 = 0; i2 < size; i2++) {
            Intent intent = (Intent) this.b.valueAt(i2);
            if (intent.getIntExtra(WorkerService.INTENT_EXTRA_WORKER_TYPE, -1) == 73 && intent.getStringExtra("fr.smoney.android.izly.extras.p2pPayRequestUserId").equals(str) && intent.getStringExtra("fr.smoney.android.izly.extras.p2pPayRequestSessionId").equals(str2) && intent.getLongExtra("fr.smoney.android.izly.extras.p2pPayRequestPayRequestId", -1) == j && intent.getIntExtra("fr.smoney.android.izly.extras.p2pPayRequestResponseStatus", -1) == i && intent.getStringExtra("fr.smoney.android.izly.extras.p2pPayRequestResponseMessage").equals(str3) && intent.getDoubleExtra("fr.smoney.android.izly.extras.p2pPayRequestAmount", -1.0d) == d) {
                return this.b.keyAt(i2);
            }
        }
        int nextInt = a.nextInt(1000000);
        Intent intent2 = new Intent(this.c, SmoneyService.class);
        intent2.putExtra(WorkerService.INTENT_EXTRA_WORKER_TYPE, 73);
        intent2.putExtra(WorkerService.INTENT_EXTRA_RECEIVER, this.e);
        intent2.putExtra("com.foxykeep.datadroid.extras.requestId", nextInt);
        intent2.putExtra("fr.smoney.android.izly.extras.p2pPayRequestUserId", str);
        intent2.putExtra("fr.smoney.android.izly.extras.p2pPayRequestSessionId", str2);
        intent2.putExtra("fr.smoney.android.izly.extras.p2pPayRequestPayRequestId", j);
        intent2.putExtra("fr.smoney.android.izly.extras.p2pPayRequestResponseStatus", i);
        intent2.putExtra("fr.smoney.android.izly.extras.p2pPayRequestResponseMessage", str3);
        intent2.putExtra("fr.smoney.android.izly.extras.p2pPayRequestAmount", d);
        this.c.startService(intent2);
        this.b.append(nextInt, intent2);
        this.f.U = null;
        this.f.V = null;
        return nextInt;
    }

    public final int a(String str, String str2, long j, long[] jArr) {
        int size = this.b.size();
        for (int i = 0; i < size; i++) {
            Intent intent = (Intent) this.b.valueAt(i);
            if (intent.getIntExtra(WorkerService.INTENT_EXTRA_WORKER_TYPE, -1) == 219 && intent.getStringExtra("fr.smoney.android.izly.extras.MakeMoneyDemandRelaunchUserId").equals(str) && intent.getStringExtra("fr.smoney.android.izly.extras.MakeMoneyDemandRelaunchSessionId").equals(str2) && intent.getStringExtra("fr.smoney.android.izly.extras.MakeMoneyDemandRelaunchDemandMultId").equals(Long.valueOf(j)) && intent.getLongArrayExtra("fr.smoney.android.izly.extras.MakeMoneyDemandRelaunchDemandSimpleIds").equals(jArr)) {
                return this.b.keyAt(i);
            }
        }
        int nextInt = a.nextInt(1000000);
        Intent intent2 = new Intent(this.c, SmoneyService.class);
        intent2.putExtra(WorkerService.INTENT_EXTRA_WORKER_TYPE, 219);
        intent2.putExtra("fr.smoney.android.izly.extras.MakeMoneyDemandRelaunchUserId", str);
        intent2.putExtra("fr.smoney.android.izly.extras.MakeMoneyDemandRelaunchSessionId", str2);
        intent2.putExtra("fr.smoney.android.izly.extras.MakeMoneyDemandRelaunchDemandMultId", j);
        intent2.putExtra("fr.smoney.android.izly.extras.MakeMoneyDemandRelaunchDemandSimpleIds", jArr);
        intent2.putExtra(WorkerService.INTENT_EXTRA_RECEIVER, this.e);
        intent2.putExtra("com.foxykeep.datadroid.extras.requestId", nextInt);
        this.c.startService(intent2);
        this.b.append(nextInt, intent2);
        this.f.bk = null;
        this.f.bl = null;
        return nextInt;
    }

    public final int a(String str, String str2, NewsFeedItem newsFeedItem) {
        int size = this.b.size();
        for (int i = 0; i < size; i++) {
            Intent intent = (Intent) this.b.valueAt(i);
            if (intent.getIntExtra(WorkerService.INTENT_EXTRA_WORKER_TYPE, -1) == 227 && intent.getStringExtra("fr.smoney.android.izly.extras.GetNewsFeedDetailsUserId").equals(str) && intent.getStringExtra("fr.smoney.android.izly.extras.GetNewsFeedDetailsSessionId").equals(str2) && intent.getParcelableExtra("fr.smoney.android.izly.extras.GetNewsFeedDetailsOperationItem").equals(newsFeedItem)) {
                return this.b.keyAt(i);
            }
        }
        int nextInt = a.nextInt(1000000);
        Intent intent2 = new Intent(this.c, SmoneyService.class);
        intent2.putExtra(WorkerService.INTENT_EXTRA_WORKER_TYPE, 227);
        intent2.putExtra(WorkerService.INTENT_EXTRA_RECEIVER, this.e);
        intent2.putExtra("com.foxykeep.datadroid.extras.requestId", nextInt);
        intent2.putExtra("fr.smoney.android.izly.extras.GetNewsFeedDetailsUserId", str);
        intent2.putExtra("fr.smoney.android.izly.extras.GetNewsFeedDetailsSessionId", str2);
        intent2.putExtra("fr.smoney.android.izly.extras.GetNewsFeedDetailsOperationItem", newsFeedItem);
        this.c.startService(intent2);
        this.b.append(nextInt, intent2);
        this.f.bx = null;
        this.f.by = null;
        return nextInt;
    }

    public final int a(String str, String str2, a aVar) {
        int size = this.b.size();
        for (int i = 0; i < size; i++) {
            Intent intent = (Intent) this.b.valueAt(i);
            if (intent.getIntExtra(WorkerService.INTENT_EXTRA_WORKER_TYPE, -1) == 131 && intent.getStringExtra("fr.smoney.android.izly.extras.setDeviceTokenUserId").equals(str) && intent.getStringExtra("fr.smoney.android.izly.extras.setDeviceTokenSessionId").equals(str2) && intent.getSerializableExtra("fr.smoney.android.izly.extras.setDeviceTokenType").equals(aVar)) {
                return this.b.keyAt(i);
            }
        }
        int nextInt = a.nextInt(1000000);
        Intent intent2 = new Intent(this.c, SmoneyService.class);
        intent2.putExtra(WorkerService.INTENT_EXTRA_WORKER_TYPE, 131);
        intent2.putExtra(WorkerService.INTENT_EXTRA_RECEIVER, this.e);
        intent2.putExtra("com.foxykeep.datadroid.extras.requestId", nextInt);
        intent2.putExtra("fr.smoney.android.izly.extras.setDeviceTokenUserId", str);
        intent2.putExtra("fr.smoney.android.izly.extras.setDeviceTokenSessionId", str2);
        intent2.putExtra("fr.smoney.android.izly.extras.setDeviceTokenType", aVar);
        this.c.startService(intent2);
        this.b.append(nextInt, intent2);
        this.f.au = null;
        this.f.av = null;
        return nextInt;
    }

    public final int a(String str, String str2, String str3) {
        int size = this.b.size();
        for (int i = 0; i < size; i++) {
            Intent intent = (Intent) this.b.valueAt(i);
            if (intent.getIntExtra(WorkerService.INTENT_EXTRA_WORKER_TYPE, -1) == 142 && intent.getStringExtra("fr.smoney.android.izly.extras.getAttachmentUserId").equals(str) && intent.getStringExtra("fr.smoney.android.izly.extras.getAttachmentSessionId").equals(str2) && intent.getStringExtra("fr.smoney.android.izly.extras.getAttachmentOperationId").equals(str3)) {
                return this.b.keyAt(i);
            }
        }
        int nextInt = a.nextInt(1000000);
        Intent intent2 = new Intent(this.c, SmoneyService.class);
        intent2.putExtra(WorkerService.INTENT_EXTRA_WORKER_TYPE, 142);
        intent2.putExtra(WorkerService.INTENT_EXTRA_RECEIVER, this.e);
        intent2.putExtra("com.foxykeep.datadroid.extras.requestId", nextInt);
        intent2.putExtra("fr.smoney.android.izly.extras.getAttachmentUserId", str);
        intent2.putExtra("fr.smoney.android.izly.extras.getAttachmentSessionId", str2);
        intent2.putExtra("fr.smoney.android.izly.extras.getAttachmentOperationId", str3);
        this.c.startService(intent2);
        this.b.append(nextInt, intent2);
        this.f.aA = null;
        this.f.aB = null;
        return nextInt;
    }

    public final int a(String str, String str2, String str3, String str4) {
        int size = this.b.size();
        for (int i = 0; i < size; i++) {
            Intent intent = (Intent) this.b.valueAt(i);
            if (intent.getIntExtra(WorkerService.INTENT_EXTRA_WORKER_TYPE, -1) == 66 && intent.getStringExtra("fr.smoney.android.izly.extras.p2pGetMultUserId").equals(str) && intent.getStringExtra("fr.smoney.android.izly.extras.p2pGetMultSessionId").equals(str2) && intent.getStringExtra("fr.smoney.android.izly.extras.p2pGetMultRecipientsAndAmountsCSV").equals(str3) && intent.getStringExtra("fr.smoney.android.izly.extras.p2pGetMessage").equals(str4)) {
                return this.b.keyAt(i);
            }
        }
        int nextInt = a.nextInt(1000000);
        Intent intent2 = new Intent(this.c, SmoneyService.class);
        intent2.putExtra(WorkerService.INTENT_EXTRA_WORKER_TYPE, 66);
        intent2.putExtra(WorkerService.INTENT_EXTRA_RECEIVER, this.e);
        intent2.putExtra("com.foxykeep.datadroid.extras.requestId", nextInt);
        intent2.putExtra("fr.smoney.android.izly.extras.p2pGetMultUserId", str);
        intent2.putExtra("fr.smoney.android.izly.extras.p2pGetMultSessionId", str2);
        intent2.putExtra("fr.smoney.android.izly.extras.p2pGetMultRecipientsAndAmountsCSV", str3);
        intent2.putExtra("fr.smoney.android.izly.extras.p2pGetMessage", str4);
        this.c.startService(intent2);
        this.b.append(nextInt, intent2);
        this.f.M = null;
        this.f.N = null;
        return nextInt;
    }

    public final int a(String str, String str2, String str3, String str4, String str5) {
        int size = this.b.size();
        for (int i = 0; i < size; i++) {
            Intent intent = (Intent) this.b.valueAt(i);
            if (intent.getIntExtra(WorkerService.INTENT_EXTRA_WORKER_TYPE, -1) == 67 && intent.getStringExtra("fr.smoney.android.izly.extras.p2pGetMultConfirmUserId").equals(str) && intent.getStringExtra("fr.smoney.android.izly.extras.p2pGetMultConfirmSessionId").equals(str2) && intent.getStringExtra("fr.smoney.android.izly.extras.p2pGetMultConfirmRecipientsAndAmountsCSV").equals(str3) && intent.getStringExtra("fr.smoney.android.izly.extras.p2pGetMultConfirmMessage").equals(str4) && intent.getStringExtra("fr.smoney.android.izly.extras.p2pGetMultConfirmPassword").equals(str5)) {
                return this.b.keyAt(i);
            }
        }
        int nextInt = a.nextInt(1000000);
        Intent intent2 = new Intent(this.c, SmoneyService.class);
        intent2.putExtra(WorkerService.INTENT_EXTRA_WORKER_TYPE, 67);
        intent2.putExtra(WorkerService.INTENT_EXTRA_RECEIVER, this.e);
        intent2.putExtra("com.foxykeep.datadroid.extras.requestId", nextInt);
        intent2.putExtra("fr.smoney.android.izly.extras.p2pGetMultConfirmUserId", str);
        intent2.putExtra("fr.smoney.android.izly.extras.p2pGetMultConfirmSessionId", str2);
        intent2.putExtra("fr.smoney.android.izly.extras.p2pGetMultConfirmRecipientsAndAmountsCSV", str3);
        intent2.putExtra("fr.smoney.android.izly.extras.p2pGetMultConfirmMessage", str4);
        intent2.putExtra("fr.smoney.android.izly.extras.p2pGetMultConfirmPassword", str5);
        this.c.startService(intent2);
        this.b.append(nextInt, intent2);
        this.f.O = null;
        this.f.P = null;
        return nextInt;
    }

    public final int a(String str, String str2, String str3, String str4, String str5, String str6) {
        int size = this.b.size();
        for (int i = 0; i < size; i++) {
            Intent intent = (Intent) this.b.valueAt(i);
            if (intent.getIntExtra(WorkerService.INTENT_EXTRA_WORKER_TYPE, -1) == 273 && intent.getStringExtra("fr.smoney.android.izly.extras.bannerBilendiWidth").equals(str) && intent.getStringExtra("fr.smoney.android.izly.extras.bannerBilendiHeight").equals(str2) && intent.getStringExtra("fr.smoney.android.izly.extras.bannerBilendiLocation").equals(str3) && intent.getStringExtra("fr.smoney.android.izly.extras.bannerBilendiZipCode").equals(str4) && intent.getStringExtra("fr.smoney.android.izly.extras.bannerBilendiCrousId").equals(str5) && intent.getStringExtra("fr.smoney.android.izly.extras.bannerBilendiBankId").equals(str6)) {
                return this.b.keyAt(i);
            }
        }
        int nextInt = a.nextInt(1000000);
        Intent intent2 = new Intent(this.c, SmoneyService.class);
        intent2.putExtra(WorkerService.INTENT_EXTRA_WORKER_TYPE, 273);
        intent2.putExtra(WorkerService.INTENT_EXTRA_RECEIVER, this.e);
        intent2.putExtra("com.foxykeep.datadroid.extras.requestId", nextInt);
        intent2.putExtra("fr.smoney.android.izly.extras.bannerBilendiWidth", str);
        intent2.putExtra("fr.smoney.android.izly.extras.bannerBilendiHeight", str2);
        intent2.putExtra("fr.smoney.android.izly.extras.bannerBilendiLocation", str3);
        intent2.putExtra("fr.smoney.android.izly.extras.bannerBilendiZipCode", str4);
        intent2.putExtra("fr.smoney.android.izly.extras.bannerBilendiCrousId", str5);
        intent2.putExtra("fr.smoney.android.izly.extras.bannerBilendiBankId", str6);
        this.c.startService(intent2);
        this.b.append(nextInt, intent2);
        this.f.ci = null;
        return nextInt;
    }

    public final int a(String str, String str2, boolean z, boolean z2, boolean z3) {
        int i;
        int size = this.b.size();
        for (int i2 = 0; i2 < size; i2++) {
            Intent intent = (Intent) this.b.valueAt(i2);
            if (intent.getIntExtra(WorkerService.INTENT_EXTRA_WORKER_TYPE, -1) == 1 && intent.getStringExtra("fr.smoney.android.izly.extras.loginUserId").equals(str) && intent.getStringExtra("fr.smoney.android.izly.extras.loginPassword").equals(str2)) {
                int intExtra = intent.getIntExtra("fr.smoney.android.izly.extras.loginWithHotp", -1);
                if (z) {
                    i = 1;
                } else {
                    boolean z4 = false;
                }
                if (!(intExtra == i || intent.getBooleanExtra("fr.smoney.android.izly.extras.loginRooted", false) == z2 || intent.getBooleanExtra("fr.smoney.android.izly.extras.loginForActivation", false) == z3)) {
                    return this.b.keyAt(i2);
                }
            }
        }
        i = a.nextInt(1000000);
        Intent intent2 = new Intent(this.c, SmoneyService.class);
        intent2.putExtra(WorkerService.INTENT_EXTRA_WORKER_TYPE, 1);
        intent2.putExtra(WorkerService.INTENT_EXTRA_RECEIVER, this.e);
        intent2.putExtra("com.foxykeep.datadroid.extras.requestId", i);
        intent2.putExtra("fr.smoney.android.izly.extras.loginUserId", str);
        intent2.putExtra("fr.smoney.android.izly.extras.loginPassword", str2);
        intent2.putExtra("fr.smoney.android.izly.extras.loginWithHotp", z ? 1 : 0);
        intent2.putExtra("fr.smoney.android.izly.extras.loginRooted", z2);
        intent2.putExtra("fr.smoney.android.izly.extras.loginForActivation", z3);
        this.c.startService(intent2);
        this.b.append(i, intent2);
        this.f.b = null;
        this.f.d = null;
        return i;
    }

    public final void a(a aVar) {
        synchronized (this.d) {
            if (!this.d.contains(aVar)) {
                this.d.add(aVar);
            }
        }
    }

    public final int b(String str) {
        int size = this.b.size();
        for (int i = 0; i < size; i++) {
            Intent intent = (Intent) this.b.valueAt(i);
            if (intent.getIntExtra(WorkerService.INTENT_EXTRA_WORKER_TYPE, -1) == 277 && intent.getStringExtra("fr.smoney.android.izly.extras.associatePhoneNumberUserPhoneNumber").equals(str)) {
                return this.b.keyAt(i);
            }
        }
        int nextInt = a.nextInt(1000000);
        Intent intent2 = new Intent(this.c, SmoneyService.class);
        intent2.putExtra(WorkerService.INTENT_EXTRA_WORKER_TYPE, 277);
        intent2.putExtra(WorkerService.INTENT_EXTRA_RECEIVER, this.e);
        intent2.putExtra("com.foxykeep.datadroid.extras.requestId", nextInt);
        intent2.putExtra("fr.smoney.android.izly.extras.associatePhoneNumberUserPhoneNumber", str);
        this.c.startService(intent2);
        this.b.append(nextInt, intent2);
        this.f.co = null;
        return nextInt;
    }

    public final int b(String str, String str2) {
        int size = this.b.size();
        for (int i = 0; i < size; i++) {
            Intent intent = (Intent) this.b.valueAt(i);
            if (intent.getIntExtra(WorkerService.INTENT_EXTRA_WORKER_TYPE, -1) == 132 && intent.getStringExtra("fr.smoney.android.izly.extras.removeDeviceTokenUserId").equals(str) && intent.getStringExtra("fr.smoney.android.izly.extras.removeDeviceTokenSessionId").equals(str2)) {
                return this.b.keyAt(i);
            }
        }
        int nextInt = a.nextInt(1000000);
        Intent intent2 = new Intent(this.c, SmoneyService.class);
        intent2.putExtra(WorkerService.INTENT_EXTRA_WORKER_TYPE, 132);
        intent2.putExtra(WorkerService.INTENT_EXTRA_RECEIVER, this.e);
        intent2.putExtra("com.foxykeep.datadroid.extras.requestId", nextInt);
        intent2.putExtra("fr.smoney.android.izly.extras.removeDeviceTokenUserId", str);
        intent2.putExtra("fr.smoney.android.izly.extras.removeDeviceTokenSessionId", str2);
        this.c.startService(intent2);
        this.b.append(nextInt, intent2);
        this.f.aw = null;
        this.f.ax = null;
        return nextInt;
    }

    public final int b(String str, String str2, long j, int i, int i2, boolean z) {
        int size = this.b.size();
        for (int i3 = 0; i3 < size; i3++) {
            Intent intent = (Intent) this.b.valueAt(i3);
            if (intent.getIntExtra(WorkerService.INTENT_EXTRA_WORKER_TYPE, -1) == 61 && intent.getStringExtra("fr.smoney.android.izly.extras.p2pGetListUserId").equals(str) && intent.getStringExtra("fr.smoney.android.izly.extras.p2pGetListSessionId").equals(str2) && intent.getLongExtra("fr.smoney.android.izly.extras.p2pGetListFirstId", -1) == j && intent.getIntExtra("fr.smoney.android.izly.extras.p2pGetListNbItems", -1) == i && intent.getIntExtra("fr.smoney.android.izly.extras.p2pGetListFilter", -1) == i2) {
                return this.b.keyAt(i3);
            }
        }
        int nextInt = a.nextInt(1000000);
        Intent intent2 = new Intent(this.c, SmoneyService.class);
        intent2.putExtra(WorkerService.INTENT_EXTRA_WORKER_TYPE, 61);
        intent2.putExtra(WorkerService.INTENT_EXTRA_RECEIVER, this.e);
        intent2.putExtra("com.foxykeep.datadroid.extras.requestId", nextInt);
        intent2.putExtra("fr.smoney.android.izly.extras.p2pGetListUserId", str);
        intent2.putExtra("fr.smoney.android.izly.extras.p2pGetListSessionId", str2);
        intent2.putExtra("fr.smoney.android.izly.extras.p2pGetListFirstId", j);
        intent2.putExtra("fr.smoney.android.izly.extras.p2pGetListNbItems", i);
        intent2.putExtra("fr.smoney.android.izly.extras.p2pGetListFilter", i2);
        this.c.startService(intent2);
        this.b.append(nextInt, intent2);
        if (z) {
            this.f.G = null;
        }
        this.f.H = null;
        return nextInt;
    }

    public final int b(String str, String str2, String str3, String str4) {
        int size = this.b.size();
        for (int i = 0; i < size; i++) {
            Intent intent = (Intent) this.b.valueAt(i);
            if (intent.getIntExtra(WorkerService.INTENT_EXTRA_WORKER_TYPE, -1) == 241 && intent.getStringExtra("fr.smoney.android.izly.extras.MoneyInTiersUserId").equals(str) && intent.getStringExtra("fr.smoney.android.izly.extras.MoneyInTiersSessionId").equals(str2)) {
                return this.b.keyAt(i);
            }
        }
        int nextInt = a.nextInt(1000000);
        Intent intent2 = new Intent(this.c, SmoneyService.class);
        intent2.putExtra(WorkerService.INTENT_EXTRA_WORKER_TYPE, 263);
        intent2.putExtra(WorkerService.INTENT_EXTRA_RECEIVER, this.e);
        intent2.putExtra("com.foxykeep.datadroid.extras.requestId", nextInt);
        intent2.putExtra("fr.smoney.android.izly.extras.cardPaymentsUserId", str);
        intent2.putExtra("fr.smoney.android.izly.extras.cardPaymentsSessionId", str2);
        intent2.putExtra("fr.smoney.android.izly.extras.cardPaymentsAmount", str3);
        intent2.putExtra("fr.smoney.android.izly.extras.cardPaymentsAvailableCards", str4);
        this.c.startService(intent2);
        this.b.append(nextInt, intent2);
        this.f.v = null;
        return nextInt;
    }

    public final int b(String str, String str2, String str3, String str4, String str5) {
        int size = this.b.size();
        for (int i = 0; i < size; i++) {
            Intent intent = (Intent) this.b.valueAt(i);
            if (intent.getIntExtra(WorkerService.INTENT_EXTRA_WORKER_TYPE, -1) == 141 && intent.getStringExtra("fr.smoney.android.izly.extras.sendAttachmentUserId").equals(str) && intent.getStringExtra("fr.smoney.android.izly.extras.sendAttachmentSessionId").equals(str2) && intent.getStringExtra("fr.smoney.android.izly.extras.sendAttachmentOperationId").equals(str3) && intent.getStringExtra("fr.smoney.android.izly.extras.sendAttachmentFileName").equals(str4)) {
                return this.b.keyAt(i);
            }
        }
        int nextInt = a.nextInt(1000000);
        Intent intent2 = new Intent(this.c, SmoneyService.class);
        intent2.putExtra(WorkerService.INTENT_EXTRA_WORKER_TYPE, 141);
        intent2.putExtra(WorkerService.INTENT_EXTRA_RECEIVER, this.e);
        intent2.putExtra("com.foxykeep.datadroid.extras.requestId", nextInt);
        intent2.putExtra("fr.smoney.android.izly.extras.sendAttachmentUserId", str);
        intent2.putExtra("fr.smoney.android.izly.extras.sendAttachmentSessionId", str2);
        intent2.putExtra("fr.smoney.android.izly.extras.sendAttachmentOperationId", str3);
        intent2.putExtra("fr.smoney.android.izly.extras.sendAttachmentFileName", str4);
        intent2.putExtra("fr.smoney.android.izly.extras.sendAttachmentAttachement", str5);
        this.c.startService(intent2);
        this.b.append(nextInt, intent2);
        this.f.ay = null;
        this.f.az = null;
        return nextInt;
    }

    public final void b(a aVar) {
        synchronized (this.d) {
            this.d.remove(aVar);
        }
    }

    public final int c(String str, String str2) {
        int size = this.b.size();
        for (int i = 0; i < size; i++) {
            Intent intent = (Intent) this.b.valueAt(i);
            if (intent.getIntExtra(WorkerService.INTENT_EXTRA_WORKER_TYPE, -1) == 229 && intent.getStringExtra("fr.smoney.android.izly.extras.IsSessionValidUserId").equals(str) && intent.getStringExtra("fr.smoney.android.izly.extras.IsSessionValidSessionId").equals(str2)) {
                return this.b.keyAt(i);
            }
        }
        int nextInt = a.nextInt(1000000);
        Intent intent2 = new Intent(this.c, SmoneyService.class);
        intent2.putExtra(WorkerService.INTENT_EXTRA_WORKER_TYPE, 229);
        intent2.putExtra(WorkerService.INTENT_EXTRA_RECEIVER, this.e);
        intent2.putExtra("com.foxykeep.datadroid.extras.requestId", nextInt);
        intent2.putExtra("fr.smoney.android.izly.extras.IsSessionValidUserId", str);
        intent2.putExtra("fr.smoney.android.izly.extras.IsSessionValidSessionId", str2);
        this.c.startService(intent2);
        this.b.append(nextInt, intent2);
        return nextInt;
    }

    public final int c(String str, String str2, long j, int i, int i2, boolean z) {
        int size = this.b.size();
        for (int i3 = 0; i3 < size; i3++) {
            Intent intent = (Intent) this.b.valueAt(i3);
            if (intent.getIntExtra(WorkerService.INTENT_EXTRA_WORKER_TYPE, -1) == 71 && intent.getStringExtra("fr.smoney.android.izly.extras.p2pPayRequestListUserId").equals(str) && intent.getStringExtra("fr.smoney.android.izly.extras.p2pPayRequestListSessionId").equals(str2) && intent.getIntExtra("fr.smoney.android.izly.extras.p2pPayRequestListNbItems", -1) == i && intent.getLongExtra("fr.smoney.android.izly.extras.p2pPayRequestListFirstId", -1) == j && intent.getIntExtra("fr.smoney.android.izly.extras.p2pPayRequestListFilter", -1) == i2) {
                return this.b.keyAt(i3);
            }
        }
        int nextInt = a.nextInt(1000000);
        Intent intent2 = new Intent(this.c, SmoneyService.class);
        intent2.putExtra(WorkerService.INTENT_EXTRA_WORKER_TYPE, 71);
        intent2.putExtra(WorkerService.INTENT_EXTRA_RECEIVER, this.e);
        intent2.putExtra("com.foxykeep.datadroid.extras.requestId", nextInt);
        intent2.putExtra("fr.smoney.android.izly.extras.p2pPayRequestListUserId", str);
        intent2.putExtra("fr.smoney.android.izly.extras.p2pPayRequestListSessionId", str2);
        intent2.putExtra("fr.smoney.android.izly.extras.p2pPayRequestListNbItems", i);
        intent2.putExtra("fr.smoney.android.izly.extras.p2pPayRequestListFirstId", j);
        intent2.putExtra("fr.smoney.android.izly.extras.p2pPayRequestListFilter", i2);
        this.c.startService(intent2);
        this.b.append(nextInt, intent2);
        if (z) {
            this.f.Q = null;
        }
        this.f.R = null;
        return nextInt;
    }

    public final int c(String str, String str2, String str3, String str4, String str5) {
        int size = this.b.size();
        for (int i = 0; i < size; i++) {
            Intent intent = (Intent) this.b.valueAt(i);
            if (intent.getIntExtra(WorkerService.INTENT_EXTRA_WORKER_TYPE, -1) == 224 && intent.getStringExtra("fr.smoney.android.izly.extras.GetContactDetailsUserId").equals(str) && intent.getStringExtra("fr.smoney.android.izly.extras.GetContactDetailsSessionId").equals(str2) && intent.getStringExtra("fr.smoney.android.izly.extras.GetContactDetailsContactId").equals(str3)) {
                String stringExtra = intent.getStringExtra("fr.smoney.android.izly.extras.GetContactDetailsLatitude");
                if (stringExtra == null || stringExtra.equals(null)) {
                    String stringExtra2 = intent.getStringExtra("fr.smoney.android.izly.extras.GetContactDetailsLongitude");
                    if (stringExtra2 == null || stringExtra2.equals(null)) {
                        return this.b.keyAt(i);
                    }
                }
            }
        }
        int nextInt = a.nextInt(1000000);
        Intent intent2 = new Intent(this.c, SmoneyService.class);
        intent2.putExtra(WorkerService.INTENT_EXTRA_WORKER_TYPE, 224);
        intent2.putExtra(WorkerService.INTENT_EXTRA_RECEIVER, this.e);
        intent2.putExtra("com.foxykeep.datadroid.extras.requestId", nextInt);
        intent2.putExtra("fr.smoney.android.izly.extras.GetContactDetailsUserId", str);
        intent2.putExtra("fr.smoney.android.izly.extras.GetContactDetailsSessionId", str2);
        intent2.putExtra("fr.smoney.android.izly.extras.GetContactDetailsContactId", str3);
        intent2.putExtra("fr.smoney.android.izly.extras.GetContactDetailsLatitude", null);
        intent2.putExtra("fr.smoney.android.izly.extras.GetContactDetailsLongitude", null);
        this.c.startService(intent2);
        this.b.append(nextInt, intent2);
        this.f.bq = null;
        this.f.br = null;
        return nextInt;
    }

    public final int d(String str, String str2) {
        int size = this.b.size();
        for (int i = 0; i < size; i++) {
            Intent intent = (Intent) this.b.valueAt(i);
            if (intent.getIntExtra(WorkerService.INTENT_EXTRA_WORKER_TYPE, -1) == 230 && intent.getStringExtra("fr.smoney.android.izly.extras.GetMyCbListUserId").equals(str) && intent.getStringExtra("fr.smoney.android.izly.extras.GetMyCbListSessionId").equals(str2)) {
                return this.b.keyAt(i);
            }
        }
        int nextInt = a.nextInt(1000000);
        Intent intent2 = new Intent(this.c, SmoneyService.class);
        intent2.putExtra(WorkerService.INTENT_EXTRA_WORKER_TYPE, 230);
        intent2.putExtra(WorkerService.INTENT_EXTRA_RECEIVER, this.e);
        intent2.putExtra("com.foxykeep.datadroid.extras.requestId", nextInt);
        intent2.putExtra("fr.smoney.android.izly.extras.GetMyCbListUserId", str);
        intent2.putExtra("fr.smoney.android.izly.extras.GetMyCbListSessionId", str2);
        this.c.startService(intent2);
        this.b.append(nextInt, intent2);
        this.f.bz = null;
        this.f.bA = null;
        return nextInt;
    }

    public final int d(String str, String str2, String str3, String str4, String str5) {
        int size = this.b.size();
        for (int i = 0; i < size; i++) {
            Intent intent = (Intent) this.b.valueAt(i);
            if (intent.getIntExtra(WorkerService.INTENT_EXTRA_WORKER_TYPE, -1) == 272 && intent.getStringExtra("fr.smoney.android.izly.extras.dealsSubscriptionUrl").equals(str) && intent.getStringExtra("fr.smoney.android.izly.extras.dealsSubscriptionMail").equals(str2) && intent.getStringExtra("fr.smoney.android.izly.extras.dealsSubscriptionName").equals(str3) && intent.getStringExtra("fr.smoney.android.izly.extras.dealsSubscriptionFirstName").equals(str4) && intent.getStringExtra("fr.smoney.android.izly.extras.dealsSubscriptionIdIzly").equals(str5)) {
                return this.b.keyAt(i);
            }
        }
        int nextInt = a.nextInt(1000000);
        Intent intent2 = new Intent(this.c, SmoneyService.class);
        intent2.putExtra(WorkerService.INTENT_EXTRA_WORKER_TYPE, 272);
        intent2.putExtra(WorkerService.INTENT_EXTRA_RECEIVER, this.e);
        intent2.putExtra("com.foxykeep.datadroid.extras.requestId", nextInt);
        intent2.putExtra("fr.smoney.android.izly.extras.dealsSubscriptionUrl", str);
        intent2.putExtra("fr.smoney.android.izly.extras.dealsSubscriptionMail", str2);
        intent2.putExtra("fr.smoney.android.izly.extras.dealsSubscriptionName", str3);
        intent2.putExtra("fr.smoney.android.izly.extras.dealsSubscriptionFirstName", str4);
        intent2.putExtra("fr.smoney.android.izly.extras.dealsSubscriptionIdIzly", str5);
        this.c.startService(intent2);
        this.b.append(nextInt, intent2);
        this.f.cc = null;
        return nextInt;
    }

    public final int e(String str, String str2) {
        int size = this.b.size();
        for (int i = 0; i < size; i++) {
            Intent intent = (Intent) this.b.valueAt(i);
            if (intent.getIntExtra(WorkerService.INTENT_EXTRA_WORKER_TYPE, -1) == 261 && intent.getStringExtra("fr.smoney.android.izly.extras.GetMyCounterListUserId").equals(str) && intent.getStringExtra("fr.smoney.android.izly.extras.GetMyCounterListSessionId").equals(str2)) {
                return this.b.keyAt(i);
            }
        }
        int nextInt = a.nextInt(1000000);
        Intent intent2 = new Intent(this.c, SmoneyService.class);
        intent2.putExtra(WorkerService.INTENT_EXTRA_WORKER_TYPE, 261);
        intent2.putExtra(WorkerService.INTENT_EXTRA_RECEIVER, this.e);
        intent2.putExtra("com.foxykeep.datadroid.extras.requestId", nextInt);
        intent2.putExtra("fr.smoney.android.izly.extras.GetMyCounterListUserId", str);
        intent2.putExtra("fr.smoney.android.izly.extras.GetMyCounterListSessionId", str2);
        this.c.startService(intent2);
        this.b.append(nextInt, intent2);
        this.f.bB = null;
        this.f.bC = null;
        return nextInt;
    }
}
