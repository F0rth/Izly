package fr.smoney.android.izly.data.service;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Parcelable;

import com.foxykeep.datadroid.service.WorkerService;
import com.slidingmenu.lib.BuildConfig;

import defpackage.cq;
import defpackage.cr;
import defpackage.cs;
import defpackage.ct;
import defpackage.cu;
import defpackage.cv;
import defpackage.cw;
import defpackage.cx;
import defpackage.cy;
import defpackage.cz;
import defpackage.da;
import defpackage.db;
import defpackage.dc;
import defpackage.dd;
import defpackage.de;
import defpackage.df;
import defpackage.dg;
import defpackage.dh;
import defpackage.di;
import defpackage.dj;
import defpackage.dk;
import defpackage.dl;
import defpackage.dm;
import defpackage.dn;
import defpackage.do;
import defpackage.dp;
import defpackage.dq;
import defpackage.dr;
import defpackage.ds;
import defpackage.dt;
import defpackage.du;
import defpackage.dv;
import defpackage.dw;
import defpackage.dx;
import defpackage.dy;
import defpackage.dz;
import defpackage.ea;
import defpackage.eb;
import defpackage.ec;
import defpackage.ed;
import defpackage.ee;
import defpackage.ef;
import defpackage.eg;
import defpackage.eh;
import defpackage.ei;
import defpackage.ej;
import defpackage.ek;
import defpackage.el;
import defpackage.em;
import defpackage.en;
import defpackage.eo;
import defpackage.ep;
import defpackage.eq;
import defpackage.er;
import defpackage.es;
import defpackage.et;
import defpackage.eu;
import defpackage.ev;
import defpackage.ew;
import defpackage.ex;
import defpackage.ey;
import defpackage.ez;
import defpackage.fa;
import defpackage.fb;
import defpackage.fc;
import defpackage.fd;
import defpackage.fe;
import defpackage.ff;
import defpackage.fg;
import defpackage.fh;
import defpackage.fi;
import defpackage.fj;
import defpackage.fk;
import defpackage.fl;
import defpackage.fm;
import defpackage.fn;
import defpackage.fo;
import defpackage.fp;
import defpackage.fq;
import defpackage.fr;
import defpackage.fs;
import defpackage.ft;
import defpackage.fu;
import defpackage.fv;
import defpackage.fw;
import defpackage.fx;
import defpackage.fy;
import defpackage.fy$a;
import defpackage.fz;
import defpackage.ga;
import defpackage.gb;
import defpackage.gc;
import defpackage.jd;
import defpackage.jk;
import fr.smoney.android.izly.SmoneyApplication;
import fr.smoney.android.izly.data.model.GetNewsFeedData;
import fr.smoney.android.izly.data.model.NewsFeedItem;
import fr.smoney.android.izly.data.model.OAuthData;
import fr.smoney.android.izly.data.model.P2PPayCommerceInfos;
import fr.smoney.android.izly.data.model.P2PPayConfirmData;
import fr.smoney.android.izly.data.model.P2PPayData;
import fr.smoney.android.izly.data.model.PreAuthorizationContainerData;
import fr.smoney.android.izly.data.model.ServerError;
import fr.smoney.android.izly.data.model.UserSubscribingValues;

import java.io.IOException;
import java.io.StringReader;
import java.text.ParseException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Locale;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;

import org.json.JSONException;
import org.json.JSONObject;
import org.kxml2.wap.Wbxml;
import org.spongycastle.asn1.eac.EACTags;
import org.spongycastle.crypto.tls.CipherSuite;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xmlpull.v1.XmlPullParserException;

public class SmoneyService extends WorkerService {
    private static final String a = SmoneyService.class.getSimpleName();

    public SmoneyService() {
        super(1);
    }

    private void a(Intent intent, ServerError serverError, int i) {
        Bundle bundle = new Bundle();
        bundle.putParcelable("fr.smoney.android.izly.extras.serverError", serverError);
        super.sendResult(intent, bundle, i);
    }

    protected void onHandleIntent(Intent intent) {
        String stringExtra;
        String stringExtra2;
        HashMap linkedHashMap;
        String a;
        XMLReader xMLReader;
        Bundle bundle;
        String stringExtra3;
        double doubleExtra;
        String stringExtra4;
        String stringExtra5;
        String stringExtra6;
        double doubleExtra2;
        P2PPayCommerceInfos p2PPayCommerceInfos;
        JSONObject jSONObject;
        JSONObject jSONObject2;
        Parcelable parcelable;
        Parcelable parcelable2;
        switch (intent.getIntExtra("com.foxykeep.datadroid.extras.workerType", -1)) {
            case 1:
                stringExtra = intent.getStringExtra("fr.smoney.android.izly.extras.loginUserId");
                stringExtra2 = intent.getStringExtra("fr.smoney.android.izly.extras.loginPassword");
                Object obj = intent.getIntExtra("fr.smoney.android.izly.extras.loginWithHotp", 0) == 1 ? 1 : null;
                boolean booleanExtra = intent.getBooleanExtra("fr.smoney.android.izly.extras.loginRooted", false);
                intent.getBooleanExtra("fr.smoney.android.izly.extras.loginForActivation", false);
                linkedHashMap = new LinkedHashMap();
                linkedHashMap.put("language", Locale.getDefault().getLanguage());
                linkedHashMap.put("user", stringExtra);
                linkedHashMap.put("password", stringExtra2);
                if (ad.a) {
                    linkedHashMap.put("smoneyClientType", "PRO");
                } else {
                    linkedHashMap.put("smoneyClientType", "PART");
                }
                linkedHashMap.put("rooted", booleanExtra ? "1" : "0");
                if (obj != null) {
                    linkedHashMap.put("passOTP", SmoneyApplication.c.a(stringExtra, stringExtra2));
                }
                a = cr.a("https://soap.izly.fr/Service.asmx", "Service", "Logon", "Service/Logon", "0.22", linkedHashMap);
                xMLReader = SAXParserFactory.newInstance().newSAXParser().getXMLReader();
                av avVar = new av();
                xMLReader.setContentHandler(avVar);
                xMLReader.parse(new InputSource(new StringReader(a)));
                bundle = new Bundle();
                if (avVar.a != null) {
                    bundle.putParcelable("fr.smoney.android.izly.extras.serverError", avVar.a);
                }
                if (avVar.b != null) {
                    bundle.putParcelable("fr.smoney.android.izly.extras.loginData", avVar.b);
                }
                if (avVar.c != null) {
                    bundle.putParcelable("fr.smoney.android.izly.extras.userData", avVar.c);
                }
                if (avVar.f != null) {
                    bundle.putParcelable("fr.smoney.android.izly.extras.oAuthData", avVar.f);
                }
                sendSuccess(intent, bundle);
                return;
            case 2:
                a = intent.getStringExtra("fr.smoney.android.izly.extras.logoutUserId");
                stringExtra3 = intent.getStringExtra("fr.smoney.android.izly.extras.logoutSessionId");
                cq cqVar = new cq("https://rest.izly.fr/Service/PublicService.svc/rest/Logout");
                cqVar.a(BuildConfig.VERSION_NAME);
                cqVar.a("language", Locale.getDefault().getLanguage());
                cqVar.a("username", a);
                cqVar.a("sessionId", stringExtra3);
                cqVar.a(0);
                return;
            case 11:
                a = intent.getStringExtra("fr.smoney.android.izly.extras.p2pPayUserId");
                stringExtra3 = intent.getStringExtra("fr.smoney.android.izly.extras.p2pPayRecipient");
                doubleExtra = intent.getDoubleExtra("fr.smoney.android.izly.extras.p2pPayAmount", -1.0d);
                stringExtra4 = intent.getStringExtra("fr.smoney.android.izly.extras.p2pPayMessage");
                stringExtra5 = intent.getStringExtra("fr.smoney.android.izly.extras.p2pPaySessionId");
                linkedHashMap = new HashMap();
                linkedHashMap.put("language", Locale.getDefault().getLanguage());
                linkedHashMap.put("userId", a);
                linkedHashMap.put("recipient", stringExtra3);
                linkedHashMap.put("amount", String.valueOf(doubleExtra));
                linkedHashMap.put("message", stringExtra4);
                linkedHashMap.put("sessionId", stringExtra5);
                a = cr.a("https://soap.izly.fr/Service.asmx", "Service", "Pay", "Service/Pay", "3.0", linkedHashMap);
                xMLReader = SAXParserFactory.newInstance().newSAXParser().getXMLReader();
                bq bqVar = new bq();
                xMLReader.setContentHandler(bqVar);
                xMLReader.parse(new InputSource(new StringReader(a)));
                bundle = new Bundle();
                if (bqVar.a != null) {
                    bundle.putParcelable("fr.smoney.android.izly.extras.serverError", bqVar.a);
                }
                if (bqVar.b != null) {
                    bundle.putParcelable("fr.smoney.android.izly.extras.p2pPayData", bqVar.b);
                }
                sendSuccess(intent, bundle);
                return;
            case 12:
                a = intent.getStringExtra("fr.smoney.android.izly.extras.p2pPayConfirmUserId");
                stringExtra3 = intent.getStringExtra("fr.smoney.android.izly.extras.p2pPayConfirmRecipient");
                doubleExtra = intent.getDoubleExtra("fr.smoney.android.izly.extras.p2pPayConfirmAmount", -1.0d);
                stringExtra4 = intent.getStringExtra("fr.smoney.android.izly.extras.p2pPayConfirmMessage");
                stringExtra5 = intent.getStringExtra("fr.smoney.android.izly.extras.p2pPayConfirmSessionId");
                stringExtra6 = intent.getStringExtra("fr.smoney.android.izly.extras.p2pPayConfirmPassword");
                linkedHashMap = new HashMap();
                StringBuilder stringBuilder = new StringBuilder();
                stringExtra6 = SmoneyApplication.c.a(a, stringExtra6);
                linkedHashMap.put("userId", a);
                linkedHashMap.put("passOTP", stringExtra6);
                linkedHashMap.put("recipient", stringExtra3);
                linkedHashMap.put("amount", String.valueOf(doubleExtra));
                linkedHashMap.put("message", stringExtra4);
                linkedHashMap.put("language", Locale.getDefault().getLanguage());
                stringBuilder.append(a).append(",").append(stringExtra6).append(",").append(stringExtra3).append(",").append(doubleExtra).append(",").append(stringExtra4);
                if (stringExtra5 != null) {
                    linkedHashMap.put("sessionId", stringExtra5);
                    stringBuilder.append(",").append(stringExtra5);
                }
                ac acVar = SmoneyApplication.c;
                linkedHashMap.put("print", ac.b(stringExtra6, stringBuilder.toString()));
                a = cr.a("https://soap.izly.fr/Service.asmx", "Service", "PayConfirm", "Service/PayConfirm", "2.0", linkedHashMap);
                xMLReader = SAXParserFactory.newInstance().newSAXParser().getXMLReader();
                bp bpVar = new bp();
                xMLReader.setContentHandler(bpVar);
                xMLReader.parse(new InputSource(new StringReader(a)));
                bundle = new Bundle();
                if (bpVar.a != null) {
                    bundle.putParcelable("fr.smoney.android.izly.extras.serverError", bpVar.a);
                }
                if (bpVar.b != null) {
                    bundle.putParcelable("fr.smoney.android.izly.extras.p2pPayConfirmData", bpVar.b);
                }
                sendSuccess(intent, bundle);
                return;
            case 13:
                a = intent.getStringExtra("fr.smoney.android.izly.extras.moneyInCbAndPayUserId");
                stringExtra3 = intent.getStringExtra("fr.smoney.android.izly.extras.moneyInCbAndPaySessionId");
                stringExtra = intent.getStringExtra("fr.smoney.android.izly.extras.moneyInCbAndPayCardId");
                long longExtra = intent.getLongExtra("fr.smoney.android.izly.extras.moneyInCbAndPayEngagementId", -1);
                double doubleExtra3 = intent.getDoubleExtra("fr.smoney.android.izly.extras.moneyInCbAndPayCbAmount", -1.0d);
                stringExtra2 = intent.getStringExtra("fr.smoney.android.izly.extras.moneyInCbAndPayRecipient");
                double doubleExtra4 = intent.getDoubleExtra("fr.smoney.android.izly.extras.moneyInCbAndPayAmount", -1.0d);
                stringExtra4 = intent.getStringExtra("fr.smoney.android.izly.extras.moneyInCbAndPayMessage");
                linkedHashMap = new HashMap();
                linkedHashMap.put("language", Locale.getDefault().getLanguage());
                linkedHashMap.put("userId", a);
                linkedHashMap.put("sessionId", stringExtra3);
                linkedHashMap.put("cardId", stringExtra);
                if (longExtra != -1) {
                    linkedHashMap.put("engagementId", String.valueOf(longExtra));
                }
                linkedHashMap.put("cbAmount", String.valueOf(doubleExtra3));
                linkedHashMap.put("recipient", stringExtra2);
                linkedHashMap.put("amount", String.valueOf(doubleExtra4));
                linkedHashMap.put("message", stringExtra4);
                sendSuccess(intent, az.a(cr.a("https://soap.izly.fr/Service.asmx", "Service", "MoneyInCbAndPay", "Service/MoneyInCbAndPay", "2.0", linkedHashMap)));
                return;
            case 14:
                sendSuccess(intent, ew.a(intent.getStringExtra("fr.smoney.android.izly.extras.moneyInCbAndPayConfirmUserId"), intent.getStringExtra("fr.smoney.android.izly.extras.moneyInCbAndPayConfirmSessionId"), intent.getStringExtra("fr.smoney.android.izly.extras.moneyInCbAndPayConfirmCardId"), intent.getLongExtra("fr.smoney.android.izly.extras.moneyInCbAndPayConfirmEngagementId", -1), intent.getDoubleExtra("fr.smoney.android.izly.extras.moneyInCbAndPayConfirmCbAmount", -1.0d), intent.getStringExtra("fr.smoney.android.izly.extras.moneyInCbAndPayConfirmRecipient"), intent.getDoubleExtra("fr.smoney.android.izly.extras.moneyInCbAndPayConfirmAmount", -1.0d), intent.getStringExtra("fr.smoney.android.izly.extras.moneyInCbAndPayConfirmMessage"), intent.getStringExtra("fr.smoney.android.izly.extras.moneyInCbAndPayConfirmPassword")));
                return;
            case 21:
                a = intent.getStringExtra("fr.smoney.android.izly.extras.transactionListUserId");
                stringExtra3 = intent.getStringExtra("fr.smoney.android.izly.extras.transactionListSessionId");
                long longExtra2 = intent.getLongExtra("fr.smoney.android.izly.extras.transactionListFirstId", -1);
                int intExtra = intent.getIntExtra("fr.smoney.android.izly.extras.transactionListNbItems", -1);
                int intExtra2 = intent.getIntExtra("fr.smoney.android.izly.extras.transactionListFilter", -1);
                linkedHashMap = new HashMap();
                linkedHashMap.put("language", Locale.getDefault().getLanguage());
                linkedHashMap.put("userId", a);
                linkedHashMap.put("sessionId", stringExtra3);
                linkedHashMap.put("firstId", String.valueOf(longExtra2));
                linkedHashMap.put("nbItems", String.valueOf(intExtra));
                linkedHashMap.put("filter", String.valueOf(intExtra2));
                sendSuccess(intent, by.a(cr.a("https://soap.izly.fr/Service.asmx", "Service", "GetStatement", "Service/GetStatement", "6.0", linkedHashMap)));
                return;
            case 22:
                sendSuccess(intent, fw.a(intent.getStringExtra("fr.smoney.android.izly.extras.sendChatMessageUserId"), intent.getStringExtra("fr.smoney.android.izly.extras.sendChatMessageSessionId"), intent.getLongExtra("fr.smoney.android.izly.extras.sendChatMessageOperationId", -1), intent.getStringExtra("fr.smoney.android.izly.extras.sendChatMessageText")));
                return;
            case 31:
                sendSuccess(intent, ez.a(intent.getStringExtra("fr.smoney.android.izly.extras.moneyInCbCbListUserId"), intent.getStringExtra("fr.smoney.android.izly.extras.moneyInCbCbListSessionId")));
                return;
            case 32:
                sendSuccess(intent, fb.a(intent.getStringExtra("fr.smoney.android.izly.extras.moneyInCbUserId"), intent.getStringExtra("fr.smoney.android.izly.extras.moneyInCbSessionId"), intent.getStringExtra("fr.smoney.android.izly.extras.moneyInCbCardId"), intent.getDoubleExtra("fr.smoney.android.izly.extras.moneyInCbAmount", -1.0d), intent.getLongExtra("fr.smoney.android.izly.extras.moneyInCbEgagementId", -1)));
                return;
            case 33:
                sendSuccess(intent, fa.a(intent.getStringExtra("fr.smoney.android.izly.extras.moneyInCbConfirmUserId"), intent.getStringExtra("fr.smoney.android.izly.extras.moneyInCbConfirmSessionId"), intent.getStringExtra("fr.smoney.android.izly.extras.moneyInCbConfirmCardId"), intent.getDoubleExtra("fr.smoney.android.izly.extras.moneyInCbConfirmAmount", -1.0d), intent.getStringExtra("fr.smoney.android.izly.extras.moneyInCbConfirmPassword"), intent.getLongExtra("fr.smoney.android.izly.extras.moneyInCbConfirmEngagementId", -1)));
                return;
            case 41:
                sendSuccess(intent, er.a((UserSubscribingValues) intent.getParcelableExtra("fr.smoney.android.izly.extras.userSubscribingValues")));
                return;
            case 42:
                sendSuccess(intent, de.a((UserSubscribingValues) intent.getParcelableExtra("fr.smoney.android.izly.extras.userSubscribingValuesCheck")));
                return;
            case 51:
                sendSuccess(intent, fc.a(intent.getStringExtra("fr.smoney.android.izly.extras.moneyOutTransferAccountUserId"), intent.getStringExtra("fr.smoney.android.izly.extras.moneyOutTransferAccountSessionId")));
                return;
            case 52:
                sendSuccess(intent, fe.a(intent.getStringExtra("fr.smoney.android.izly.extras.moneyOutTransferUserId"), intent.getStringExtra("fr.smoney.android.izly.extras.moneyOutTransferSessionId"), intent.getDoubleExtra("fr.smoney.android.izly.extras.moneyOutTransferAmount", -1.0d), intent.getStringExtra("fr.smoney.android.izly.extras.moneyOutTransferLabel")));
                return;
            case 53:
                sendSuccess(intent, fd.a(intent.getStringExtra("fr.smoney.android.izly.extras.moneyInCbConfirmUserId"), intent.getStringExtra("fr.smoney.android.izly.extras.moneyInCbConfirmSessionId"), intent.getDoubleExtra("fr.smoney.android.izly.extras.moneyInCbConfirmAmount", -1.0d), intent.getStringExtra("fr.smoney.android.izly.extras.moneyOutTransferLabel"), intent.getStringExtra("fr.smoney.android.izly.extras.moneyInCbConfirmPassword")));
                return;
            case 61:
                sendSuccess(intent, fh.a(intent.getStringExtra("fr.smoney.android.izly.extras.p2pGetListUserId"), intent.getStringExtra("fr.smoney.android.izly.extras.p2pGetListSessionId"), intent.getLongExtra("fr.smoney.android.izly.extras.p2pGetListFirstId", -1), intent.getIntExtra("fr.smoney.android.izly.extras.p2pGetListNbItems", -1), intent.getIntExtra("fr.smoney.android.izly.extras.p2pGetListFilter", -1)));
                return;
            case 62:
                sendSuccess(intent, fg.a(intent.getStringExtra("fr.smoney.android.izly.extras.p2pGetHideUserId"), intent.getStringExtra("fr.smoney.android.izly.extras.p2pGetHideSessionId"), intent.getLongExtra("fr.smoney.android.izly.extras.p2pGetHideGetId", -1), intent.getIntExtra("fr.smoney.android.izly.extras.p2pGetHideGetType", -1)));
                return;
            case 63:
                sendSuccess(intent, ff.a(intent.getStringExtra("fr.smoney.android.izly.extras.p2pGetCancelUserId"), intent.getStringExtra("fr.smoney.android.izly.extras.p2pGetCancelSessionId"), intent.getLongExtra("fr.smoney.android.izly.extras.p2pGetCancelGetId", -1), intent.getIntExtra("fr.smoney.android.izly.extras.p2pGetCancelGetType", -1)));
                return;
            case 66:
                sendSuccess(intent, fj.a(intent.getStringExtra("fr.smoney.android.izly.extras.p2pGetMultUserId"), intent.getStringExtra("fr.smoney.android.izly.extras.p2pGetMultSessionId"), intent.getStringExtra("fr.smoney.android.izly.extras.p2pGetMultRecipientsAndAmountsCSV"), intent.getStringExtra("fr.smoney.android.izly.extras.p2pGetMessage")));
                return;
            case 67:
                sendSuccess(intent, fi.a(intent.getStringExtra("fr.smoney.android.izly.extras.p2pGetMultConfirmUserId"), intent.getStringExtra("fr.smoney.android.izly.extras.p2pGetMultConfirmSessionId"), intent.getStringExtra("fr.smoney.android.izly.extras.p2pGetMultConfirmRecipientsAndAmountsCSV"), intent.getStringExtra("fr.smoney.android.izly.extras.p2pGetMultConfirmMessage"), intent.getStringExtra("fr.smoney.android.izly.extras.p2pGetMultConfirmPassword")));
                return;
            case 71:
                sendSuccess(intent, fm.a(intent.getStringExtra("fr.smoney.android.izly.extras.p2pPayRequestListUserId"), intent.getStringExtra("fr.smoney.android.izly.extras.p2pPayRequestListSessionId"), intent.getIntExtra("fr.smoney.android.izly.extras.p2pPayRequestListNbItems", -1), intent.getLongExtra("fr.smoney.android.izly.extras.p2pPayRequestListFirstId", -1), intent.getIntExtra("fr.smoney.android.izly.extras.p2pPayRequestListFilter", -1)));
                return;
            case 72:
                sendSuccess(intent, fl.a(intent.getStringExtra("fr.smoney.android.izly.extras.p2pPayRequestHideUserId"), intent.getStringExtra("fr.smoney.android.izly.extras.p2pPayRequestHideSessionId"), intent.getLongExtra("fr.smoney.android.izly.extras.p2pPayRequestHidePayRequestId", -1)));
                return;
            case 73:
                sendSuccess(intent, fn.a(intent.getStringExtra("fr.smoney.android.izly.extras.p2pPayRequestUserId"), intent.getStringExtra("fr.smoney.android.izly.extras.p2pPayRequestSessionId"), intent.getLongExtra("fr.smoney.android.izly.extras.p2pPayRequestPayRequestId", -1), intent.getIntExtra("fr.smoney.android.izly.extras.p2pPayRequestResponseStatus", -1), intent.getStringExtra("fr.smoney.android.izly.extras.p2pPayRequestResponseMessage"), intent.getDoubleExtra("fr.smoney.android.izly.extras.p2pPayRequestAmount", -1.0d)));
                return;
            case 74:
                sendSuccess(intent, fk.a(intent.getStringExtra("fr.smoney.android.izly.extras.p2pPayRequestConfirmUserId"), intent.getStringExtra("fr.smoney.android.izly.extras.p2pPayRequestConfirmSessionId"), intent.getLongExtra("fr.smoney.android.izly.extras.p2pPayRequestConfirmPayRequestId", -1), intent.getIntExtra("fr.smoney.android.izly.extras.p2pPayRequestConfirmResponseStatus", -1), intent.getStringExtra("fr.smoney.android.izly.extras.p2pPayRequestConfirmResponseMessage"), intent.getDoubleExtra("fr.smoney.android.izly.extras.p2pPayRequestConfirmAmount", -1.0d), intent.getStringExtra("fr.smoney.android.izly.extras.p2pPayRequestConfirmPassword")));
                return;
            case 75:
                sendSuccess(intent, ey.a(intent.getStringExtra("fr.smoney.android.izly.extras.moneyInCbAndPayRequestUserId"), intent.getStringExtra("fr.smoney.android.izly.extras.moneyInCbAndPayRequestSessionId"), intent.getStringExtra("fr.smoney.android.izly.extras.moneyInCbAndPayRequestCardId"), intent.getLongExtra("fr.smoney.android.izly.extras.moneyInCbAndPayRequestEngagementId", -1), intent.getDoubleExtra("fr.smoney.android.izly.extras.moneyInCbAndPayRequestCbAmount", -1.0d), intent.getDoubleExtra("fr.smoney.android.izly.extras.moneyInCbAndPayRequestAmount", -1.0d), intent.getLongExtra("fr.smoney.android.izly.extras.moneyInCbAndPayRequestPayRequestId", -1), intent.getIntExtra("fr.smoney.android.izly.extras.moneyInCbAndPayRequestResponseStatus", -1), intent.getStringExtra("fr.smoney.android.izly.extras.moneyInCbAndPayRequestResponseMessage")));
                return;
            case 76:
                sendSuccess(intent, ex.a(intent.getStringExtra("fr.smoney.android.izly.extras.moneyInCbAndPayRequestConfirmUserId"), intent.getStringExtra("fr.smoney.android.izly.extras.moneyInCbAndPayRequestConfirmSessionId"), intent.getStringExtra("fr.smoney.android.izly.extras.moneyInCbAndPayRequestConfirmCardId"), intent.getLongExtra("fr.smoney.android.smoney.extras.moneyInCbAndPayRequestConfirmEngagementId", -1), intent.getDoubleExtra("fr.smoney.android.izly.extras.moneyInCbAndPayRequestConfirmCbAmount", -1.0d), intent.getDoubleExtra("fr.smoney.android.izly.extras.moneyInCbAndPayRequestConfirmAmount", -1.0d), intent.getLongExtra("fr.smoney.android.izly.extras.moneyInCbAndPayRequestConfirmPayRequestId", -1), intent.getIntExtra("fr.smoney.android.izly.extras.moneyInCbAndPayRequestConfirmResponseStatus", -1), intent.getStringExtra("fr.smoney.android.izly.extras.moneyInCbAndPayRequestConfirmResponseMessage"), intent.getStringExtra("fr.smoney.android.izly.extras.moneyInCbAndPayRequestConfirmPassword")));
                return;
            case 81:
                sendSuccess(intent, dc.a(intent.getStringExtra("fr.smoney.android.izly.extras.cbChangeAliasUserId"), intent.getStringExtra("fr.smoney.android.izly.extras.cbChangeAliasSessionId"), intent.getStringExtra("fr.smoney.android.izly.extras.cbChangeAliasCardId"), intent.getStringExtra("fr.smoney.android.izly.extras.cbChangeAliasNewAlias")));
                return;
            case 82:
                sendSuccess(intent, dd.a(intent.getStringExtra("fr.smoney.android.izly.extras.cbDeleteUserId"), intent.getStringExtra("fr.smoney.android.izly.extras.cbDeleteSessionId"), intent.getStringExtra("fr.smoney.android.izly.extras.cbDeletCardId")));
                return;
            case 91:
                sendSuccess(intent, eq.a(intent.getStringExtra("fr.smoney.android.izly.extras.makeBankAccountUpdateUserId"), intent.getStringExtra("fr.smoney.android.izly.extras.makeBankAccountUpdateSessionId"), intent.getStringExtra("fr.smoney.android.izly.extras.makeBankAccountUpdateAlias"), intent.getStringExtra("fr.smoney.android.izly.extras.makeBankAccountUpdateIban"), intent.getStringExtra("fr.smoney.android.izly.extras.makeBankAccountUpdateBic"), intent.getStringExtra("fr.smoney.android.izly.extras.makeBankAccountUpdatePassword")));
                return;
            case 93:
                sendSuccess(intent, dm.a(intent.getStringExtra("fr.smoney.android.izly.extras.deleteBankAccountUserId"), intent.getStringExtra("fr.smoney.android.izly.extras.deleteBankAccountSessionId")));
                return;
            case 102:
                sendSuccess(intent, cz.a(intent.getStringExtra("fr.smoney.android.izly.extras.bookmarkContactUserId"), intent.getStringExtra("fr.smoney.android.izly.extras.bookmarkContactSessionId"), intent.getStringExtra("fr.smoney.android.izly.extras.bookmarkContactContactId"), intent.getBooleanExtra("fr.smoney.android.izly.extras.bookmarkContactBookmarksContact", false)));
                return;
            case 103:
                sendSuccess(intent, cy.a(intent.getStringExtra("fr.smoney.android.izly.extras.blockContactUserId"), intent.getStringExtra("fr.smoney.android.izly.extras.blockContactSessionId"), intent.getStringExtra("fr.smoney.android.izly.extras.blockContactContactId"), intent.getBooleanExtra("fr.smoney.android.izly.extras.blockContactBlocksContact", false)));
                return;
            case 104:
                sendSuccess(intent, cv.a(intent.getStringExtra("fr.smoney.android.izly.extras.addBookmarkUserId"), intent.getStringExtra("fr.smoney.android.izly.extras.addBookmarkSessionId"), intent.getStringExtra("fr.smoney.android.izly.extras.addBookmarkBookmarkId"), intent.getStringExtra("fr.smoney.android.izly.extras.addBookmarkBookmarkFName"), intent.getStringExtra("fr.smoney.android.izly.extras.addBookmarkBookmkarLName")));
                return;
            case 105:
                sendSuccess(intent, dr.a(intent.getStringExtra("fr.smoney.android.izly.extras.getBookmarksUserId"), intent.getStringExtra("fr.smoney.android.izly.extras.getBookmarksSessionId")));
                return;
            case EACTags.COEXISTANT_TAG_ALLOCATION_AUTHORITY /*121*/:
                sendSuccess(intent, ei.a(intent.getStringExtra("fr.smoney.android.izly.extras.getSecretQuestionUserId")));
                return;
            case EACTags.SECURITY_SUPPORT_TEMPLATE /*122*/:
                sendSuccess(intent, fu.a(intent.getStringExtra("fr.smoney.android.izly.extras.secretQuestionAnwserUserId"), intent.getStringExtra("fr.smoney.android.izly.extras.secretQuestionAnwserSecretAnswer"), intent.getBooleanExtra("fr.smoney.android.izly.extras.secretQuestionAnwserUnlockAccount", false)));
                return;
            case Wbxml.STR_T /*131*/:
                sendSuccess(intent, fy.a(this, intent.getStringExtra("fr.smoney.android.izly.extras.setDeviceTokenUserId"), intent.getStringExtra("fr.smoney.android.izly.extras.setDeviceTokenSessionId"), (fy$a) intent.getSerializableExtra("fr.smoney.android.izly.extras.setDeviceTokenType")));
                return;
            case Wbxml.LITERAL_A /*132*/:
                sendSuccess(intent, fs.a(this, intent.getStringExtra("fr.smoney.android.izly.extras.removeDeviceTokenUserId"), intent.getStringExtra("fr.smoney.android.izly.extras.removeDeviceTokenSessionId")));
                return;
            case CipherSuite.TLS_PSK_WITH_AES_256_CBC_SHA /*141*/:
                sendSuccess(intent, fv.a(intent.getStringExtra("fr.smoney.android.izly.extras.sendAttachmentUserId"), intent.getStringExtra("fr.smoney.android.izly.extras.sendAttachmentSessionId"), intent.getStringExtra("fr.smoney.android.izly.extras.sendAttachmentOperationId"), intent.getStringExtra("fr.smoney.android.izly.extras.sendAttachmentFileName"), intent.getStringExtra("fr.smoney.android.izly.extras.sendAttachmentAttachement")));
                return;
            case CipherSuite.TLS_DHE_PSK_WITH_RC4_128_SHA /*142*/:
                sendSuccess(intent, dp.a(this, intent.getStringExtra("fr.smoney.android.izly.extras.getAttachmentUserId"), intent.getStringExtra("fr.smoney.android.izly.extras.getAttachmentSessionId"), intent.getStringExtra("fr.smoney.android.izly.extras.getAttachmentOperationId")));
                return;
            case 151:
                sendSuccess(intent, ee.a(intent.getStringExtra("fr.smoney.android.izly.extras.getBadgesUserId"), intent.getStringExtra("fr.smoney.android.izly.extras.getBadgesSessionId")));
                return;
            case 161:
                sendSuccess(intent, ga.a(intent.getStringExtra("fr.smoney.android.izly.extras.updatePasswordUserId"), intent.getStringExtra("fr.smoney.android.izly.extras.updatePasswordSessionId"), intent.getStringExtra("fr.smoney.android.izly.extras.updatePasswordNewPassword"), intent.getStringExtra("fr.smoney.android.izly.extras.updatePasswordPassword")));
                return;
            case 171:
                sendSuccess(intent, dt.a(intent.getStringExtra("fr.smoney.android.izly.extras.getConfidentialitySettingsUserId"), intent.getStringExtra("fr.smoney.android.izly.extras.getConfidentialitySettingsSessionId")));
                return;
            case 172:
                sendSuccess(intent, fx.a(intent.getStringExtra("fr.smoney.android.izly.extras.setConfidentialitySettingsUserId"), intent.getStringExtra("fr.smoney.android.izly.extras.setConfidentialitySettingsSessionId"), intent.getIntExtra("fr.smoney.android.izly.extras.setConfidentialitySettingsPhoto", -1), intent.getIntExtra("fr.smoney.android.izly.extras.setConfidentialitySettingsName", -1), intent.getIntExtra("fr.smoney.android.izly.extras.setConfidentialitySettingsPhoneNumber", -1), intent.getIntExtra("fr.smoney.android.izly.extras.setConfidentialitySettingsNickname", -1), intent.getIntExtra("fr.smoney.android.izly.extras.setConfidentialitySettingsAddress", -1), intent.getIntExtra("fr.smoney.android.izly.extras.setConfidentialitySettingsEmail", -1)));
                return;
            case 173:
                a = intent.getStringExtra("fr.smoney.android.izly.extras.updateUserProfileUserId");
                stringExtra3 = intent.getStringExtra("fr.smoney.android.izly.extras.updateUserProfileSessionId");
                int intExtra3 = intent.getIntExtra("fr.smoney.android.izly.extras.updateUserProfileCivility", 0);
                stringExtra2 = intent.getStringExtra("fr.smoney.android.izly.extras.updateUserProfileFirstName");
                stringExtra4 = intent.getStringExtra("fr.smoney.android.izly.extras.updateUserProfileLastName");
                String stringExtra7 = intent.getStringExtra("fr.smoney.android.izly.extras.updateUserProfileBirthDate");
                stringExtra5 = intent.getStringExtra("fr.smoney.android.izly.extras.updateUserProfileActivity");
                stringExtra6 = intent.getStringExtra("fr.smoney.android.izly.extras.updateUserProfileAlias");
                intent.getStringExtra("fr.smoney.android.izly.extras.updateUserProfilePhone");
                sendSuccess(intent, gc.a(a, stringExtra3, intExtra3, stringExtra2, stringExtra4, stringExtra7, stringExtra5, stringExtra6, intent.getStringExtra("fr.smoney.android.izly.extras.updateUserProfileEmail"), intent.getStringExtra("fr.smoney.android.izly.extras.updateUserProfileWebsite"), intent.getStringExtra("fr.smoney.android.izly.extras.updateUserProfileAddress"), intent.getStringExtra("fr.smoney.android.izly.extras.updateUserProfilePostCode"), intent.getStringExtra("fr.smoney.android.izly.extras.updateUserProfileCity"), intent.getIntExtra("fr.smoney.android.izly.extras.updateUserProfileCountry", 0), intent.getStringExtra("fr.smoney.android.izly.extras.updateUserProfileCommercialMessage"), intent.getIntExtra("fr.smoney.android.izly.extras.updateUserProfileOptIn", -1), intent.getIntExtra("fr.smoney.android.izly.extras.updateUserProfileOptInPartners", -1), intent.getStringExtra("fr.smoney.android.izly.extras.updateUserProfileTwitter")));
                return;
            case 174:
                sendSuccess(intent, gb.a(intent.getStringExtra("fr.smoney.android.izly.extras.updateUserPictureUserId"), intent.getStringExtra("fr.smoney.android.izly.extras.updateUserPictureSessionId"), (Bitmap) intent.getParcelableExtra("fr.smoney.android.izly.extras.updateUserPictureUserPicturePath")));
                return;
            case 181:
                sendSuccess(intent, cx.a(intent.getStringExtra("fr.smoney.android.izly.extras.blockAccountUserId"), intent.getStringExtra("fr.smoney.android.izly.extras.blockAccountSessionId"), intent.getStringExtra("fr.smoney.android.izly.extras.blockAccountMessage"), intent.getStringExtra("fr.smoney.android.izly.extras.blockAccountPassword")));
                return;
            case 191:
                sendSuccess(intent, em.a(intent.getStringExtra("fr.smoney.android.izly.extras.initiatePasswordRecoveryUserId"), intent.getBooleanExtra("fr.smoney.android.izly.extras.initiatePasswordRecoveryUnlockAccount", false)));
                return;
            case 211:
                sendSuccess(intent, dj.a(intent.getStringExtra("fr.smoney.android.izly.extras.chooseDefaultCbUserId"), intent.getStringExtra("fr.smoney.android.izly.extras.chooseDefaultCbSessionId"), intent.getStringExtra("fr.smoney.android.izly.extras.chooseDefaultCbCardId")));
                return;
            case 212:
                sendSuccess(intent, dn.a(intent.getStringExtra("fr.smoney.android.izly.extras.deleteUserPictureUserId"), intent.getStringExtra("fr.smoney.android.izly.extras.deleteUserPictureSessionId")));
                return;
            case 213:
                sendSuccess(intent, ed.a(intent.getStringExtra("fr.smoney.android.izly.extras.GetNearProListUserId"), intent.getStringExtra("fr.smoney.android.izly.extras.GetNearProListSessionId"), intent.getDoubleExtra("fr.smoney.android.izly.extras.GetNearProListLatitude", 0.0d), intent.getDoubleExtra("fr.smoney.android.izly.extras.GetNearProListLongitude", 0.0d), intent.getIntExtra("fr.smoney.android.izly.extras.GetNearProListCategory", 0), intent.getIntExtra("fr.smoney.android.izly.extras.GetNearProListRadius", 0)));
                return;
            case 214:
                sendSuccess(intent, eh.a(intent.getStringExtra("fr.smoney.android.izly.extras.GetProCashingModelsUserId"), intent.getStringExtra("fr.smoney.android.izly.extras.GetProCashingModelsSessionsId"), intent.getIntExtra("fr.smoney.android.izly.extras.GetProCashingModelsVersion", -1)));
                return;
            case 215:
                sendSuccess(intent, df.a(intent.getStringExtra("fr.smoney.android.izly.extras.CheckMoneyDemandForProUserId"), intent.getStringExtra("fr.smoney.android.izly.extras.CheckMoneyDemandForProSessionsId"), intent.getParcelableArrayListExtra("fr.smoney.android.izly.extras.CheckMoneyDemandForProRecipientValues"), intent.getStringExtra("fr.smoney.android.izly.extras.CheckMoneyDemandForProTax"), intent.getStringExtra("fr.smoney.android.izly.extras.CheckMoneyDemandForProMessage"), intent.getStringExtra("fr.smoney.android.izly.extras.CheckMoneyDemandForProModelId")));
                return;
            case 216:
                sendSuccess(intent, es.a(intent.getStringExtra("fr.smoney.android.izly.extras.MakeMoneyDemandForProUserId"), intent.getStringExtra("fr.smoney.android.izly.extras.MakeMoneyDemandForProSessionsId"), intent.getParcelableArrayListExtra("fr.smoney.android.izly.extras.MakeMoneyDemandForProRecipientValues"), intent.getStringExtra("fr.smoney.android.izly.extras.MakeMoneyDemandForProTax"), intent.getStringExtra("fr.smoney.android.izly.extras.MakeMoneyDemandForProMessage"), intent.getStringExtra("fr.smoney.android.izly.extras.MakeMoneyDemandForProModelId")));
                return;
            case 217:
                sendSuccess(intent, ej.a(intent.getStringExtra("fr.smoney.android.izly.extras.GetProCashingModelsUserId"), intent.getStringExtra("fr.smoney.android.izly.extras.GetProCashingModelsSessionsId")));
                return;
            case 218:
                sendSuccess(intent, en.a());
                return;
            case 219:
                sendSuccess(intent, et.a(intent.getStringExtra("fr.smoney.android.izly.extras.MakeMoneyDemandRelaunchUserId"), intent.getStringExtra("fr.smoney.android.izly.extras.MakeMoneyDemandRelaunchSessionId"), intent.getLongExtra("fr.smoney.android.izly.extras.MakeMoneyDemandRelaunchDemandMultId", -1), intent.getLongArrayExtra("fr.smoney.android.izly.extras.MakeMoneyDemandRelaunchDemandSimpleIds")));
                return;
            case 220:
                sendSuccess(intent, fz.a(intent.getStringExtra("fr.smoney.android.izly.extras.updateMyAddressUserId"), intent.getStringExtra("fr.smoney.android.izly.extras.updateMyAddressSessionId"), intent.getStringExtra("fr.smoney.android.izly.extras.updateMyAddressWay"), intent.getStringExtra("fr.smoney.android.izly.extras.updateMyAddressCode"), intent.getStringExtra("fr.smoney.android.izly.extras.updateMyAddressCity"), intent.getStringExtra("fr.smoney.android.izly.extras.updateMyAddressCountry")));
                return;
            case 221:
                sendSuccess(intent, ds.a(intent.getStringExtra("fr.smoney.android.izly.extras.getUserStatusUserId"), intent.getStringExtra("fr.smoney.android.izly.extras.getUserStatusSessionId")));
                return;
            case 224:
                sendSuccess(intent, du.a(intent.getStringExtra("fr.smoney.android.izly.extras.GetContactDetailsUserId"), intent.getStringExtra("fr.smoney.android.izly.extras.GetContactDetailsSessionId"), intent.getStringExtra("fr.smoney.android.izly.extras.GetContactDetailsContactId"), intent.getStringExtra("fr.smoney.android.izly.extras.GetContactDetailsLatitude"), intent.getStringExtra("fr.smoney.android.izly.extras.GetContactDetailsLongitude")));
                return;
            case 225:
                sendSuccess(intent, dz.a(intent.getStringExtra("fr.smoney.android.izly.extras.GetBankAccountUserId"), intent.getStringExtra("fr.smoney.android.izly.extras.GetBankAccountSessionId")));
                return;
            case 226:
                sendSuccess(intent, eg.a(intent.getStringExtra("fr.smoney.android.izly.extras.GetNewsFeedUserId"), intent.getStringExtra("fr.smoney.android.izly.extras.GetNewsFeedSessionId"), intent.getIntExtra("fr.smoney.android.izly.extras.GetNewsFeedCurrentPage", 0), intent.getIntExtra("fr.smoney.android.izly.extras.GetNewsFeedItemPerPage", 0), intent.getLongExtra("fr.smoney.android.izly.extras.GetNewsFeedFromDate", -1), intent.getLongExtra("fr.smoney.android.izly.extras.GetNewsFeedToDate", -1), intent.getBooleanExtra("fr.smoney.android.izly.extras.GetNewsFeedIsRefresh", false), (GetNewsFeedData) intent.getParcelableExtra("fr.smoney.android.izly.extras.GetNewsFeedOldData")));
                return;
            case 227:
                sendSuccess(intent, ef.a(intent.getStringExtra("fr.smoney.android.izly.extras.GetNewsFeedDetailsUserId"), intent.getStringExtra("fr.smoney.android.izly.extras.GetNewsFeedDetailsSessionId"), (NewsFeedItem) intent.getParcelableExtra("fr.smoney.android.izly.extras.GetNewsFeedDetailsOperationItem")));
                return;
            case 228:
                sendSuccess(intent, dy.a(intent.getStringExtra("fr.smoney.android.izly.extras.GetLogonInfosUserId")));
                return;
            case 229:
                sendSuccess(intent, eo.a(intent.getStringExtra("fr.smoney.android.izly.extras.IsSessionValidUserId"), intent.getStringExtra("fr.smoney.android.izly.extras.IsSessionValidSessionId")));
                return;
            case 230:
                sendSuccess(intent, ea.a(intent.getStringExtra("fr.smoney.android.izly.extras.GetMyCbListUserId"), intent.getStringExtra("fr.smoney.android.izly.extras.GetMyCbListSessionId")));
                return;
            case 231:
                sendSuccess(intent, fq.a(intent.getStringExtra("fr.smoney.android.izly.extras.refreshOAuthTokenUserId"), intent.getStringExtra("fr.smoney.android.izly.extras.refreshOAuthTokenRefreshToken")));
                return;
            case 232:
                sendSuccess(intent, ep.a(intent.getStringExtra("fr.smoney.android.izly.extras.loginLightUserId"), intent.getStringExtra("fr.smoney.android.izly.extras.loginLightPassword")));
                return;
            case 233:
                sendSuccess(intent, cs.a(intent.getStringExtra("fr.smoney.android.izly.extras.acceptCGUUserId"), intent.getStringExtra("fr.smoney.android.izly.extras.acceptCGUSessionId")));
                return;
            case 234:
                stringExtra3 = intent.getStringExtra("fr.smoney.android.izly.extras.checkEcommerceUserId");
                stringExtra = intent.getStringExtra("fr.smoney.android.izly.extras.checkEcommerceRecipient");
                doubleExtra2 = intent.getDoubleExtra("fr.smoney.android.izly.extras.checkEcommerceAmount", -1.0d);
                stringExtra2 = intent.getStringExtra("fr.smoney.android.izly.extras.checkEcommerceMessage");
                p2PPayCommerceInfos = (P2PPayCommerceInfos) intent.getParcelableExtra("fr.smoney.android.izly.extras.checkEcommercePayInfos");
                stringExtra5 = intent.getStringExtra("fr.smoney.android.izly.extras.checkEcommerceSessionId");
                cq cqVar2 = new cq("https://rest.izly.fr/Service/PublicService.svc/rest/CheckPaymentThirdParty");
                cqVar2.a(BuildConfig.VERSION_NAME);
                cqVar2.a("language", Locale.getDefault().getLanguage());
                cqVar2.a("userId", stringExtra3);
                cqVar2.a("sessionId", stringExtra5);
                jSONObject = new JSONObject();
                jSONObject2 = new JSONObject();
                jSONObject2.put("Identifier", stringExtra);
                jSONObject.put("receiver", jSONObject2);
                jSONObject.put("amount", doubleExtra2);
                jSONObject.put("message", stringExtra2);
                jSONObject.put("appId", p2PPayCommerceInfos.d);
                jSONObject.put("transactionId", p2PPayCommerceInfos.e);
                jSONObject.put("editableFields", p2PPayCommerceInfos.q);
                jSONObject.put("signature", p2PPayCommerceInfos.g);
                cqVar2.b(jSONObject.toString());
                stringExtra2 = cqVar2.a(1);
                parcelable = null;
                parcelable2 = null;
                Bundle bundle2 = new Bundle();
                JSONObject jSONObject3 = new JSONObject(stringExtra2);
                if (jSONObject3.isNull("ErrorMessage")) {
                    JSONObject optJSONObject = jSONObject3.optJSONObject("CheckPaymentThirdPartyResult").optJSONObject("Result");
                    if (optJSONObject != null) {
                        parcelable2 = new P2PPayData();
                        parcelable2.a.k = optJSONObject.optDouble("Commission");
                        parcelable2.a.j = optJSONObject.optDouble("Credit");
                        jSONObject3 = optJSONObject.getJSONObject("Receiver");
                        parcelable2.a.f = jSONObject3.optBoolean("IsSmoneyUser");
                        parcelable2.a.o = jSONObject3.optBoolean("IsSmoneyPro");
                        parcelable2.a.h = jd.a(jSONObject3, "DisplayName");
                        parcelable2.a.g = "";
                        parcelable2.a.d = jd.a(jSONObject3, "Identifier");
                        parcelable2.a.e = stringExtra;
                        parcelable2.a.m = jd.a(optJSONObject, "Message");
                        parcelable2.a.b = jk.a(optJSONObject.getString("OperationDate"));
                    }
                } else {
                    parcelable = new ServerError();
                    parcelable.b = jSONObject3.getInt("Code");
                    parcelable.c = jSONObject3.getString("ErrorMessage");
                    parcelable.e = jSONObject3.getInt("Priority");
                    parcelable.d = jSONObject3.getString("Title");
                }
                if (parcelable != null) {
                    bundle2.putParcelable("fr.smoney.android.izly.extras.serverError", parcelable);
                }
                if (parcelable2 != null) {
                    bundle2.putParcelable("fr.smoney.android.izly.extras.p2pPayData", parcelable2);
                }
                sendSuccess(intent, bundle2);
                return;
            case 235:
                stringExtra3 = intent.getStringExtra("fr.smoney.android.izly.extras.makeEcommerceUserId");
                stringExtra = intent.getStringExtra("fr.smoney.android.izly.extras.makeEcommerceRecipient");
                doubleExtra2 = intent.getDoubleExtra("fr.smoney.android.izly.extras.makeEcommerceAmount", -1.0d);
                stringExtra2 = intent.getStringExtra("fr.smoney.android.izly.extras.makeEcommerceMessage");
                p2PPayCommerceInfos = (P2PPayCommerceInfos) intent.getParcelableExtra("fr.smoney.android.izly.extras.makeEcommercePayInfos");
                stringExtra5 = intent.getStringExtra("fr.smoney.android.izly.extras.makeEcommerceSessionId");
                stringExtra6 = SmoneyApplication.c.a(stringExtra3, intent.getStringExtra("fr.smoney.android.izly.extras.makeEcommercePassword"));
                cq cqVar3 = new cq("https://rest.izly.fr/Service/PublicService.svc/rest/MakePaymentThirdParty");
                cqVar3.a(BuildConfig.VERSION_NAME);
                cqVar3.a("language", Locale.getDefault().getLanguage());
                cqVar3.a("userId", stringExtra3);
                cqVar3.a("sessionId", stringExtra5);
                cqVar3.a("passOTP", stringExtra6);
                jSONObject = new JSONObject();
                jSONObject2 = new JSONObject();
                jSONObject2.put("Identifier", stringExtra);
                jSONObject.put("receiver", jSONObject2);
                jSONObject.put("amount", doubleExtra2);
                jSONObject.put("message", stringExtra2);
                jSONObject.put("appId", p2PPayCommerceInfos.d);
                jSONObject.put("transactionId", p2PPayCommerceInfos.e);
                jSONObject.put("editableFields", p2PPayCommerceInfos.q);
                jSONObject.put("signature", p2PPayCommerceInfos.g);
                cqVar3.b(jSONObject.toString());
                stringExtra = cqVar3.a(1);
                parcelable = null;
                parcelable2 = null;
                Bundle bundle3 = new Bundle();
                JSONObject jSONObject4 = new JSONObject(stringExtra);
                if (jSONObject4.isNull("ErrorMessage")) {
                    JSONObject jSONObject5 = jSONObject4.getJSONObject("MakePaymentThirdPartyResult").getJSONObject("Result");
                    if (jSONObject5 != null) {
                        parcelable2 = new P2PPayConfirmData();
                        parcelable2.a.k = jSONObject5.optDouble("Commission");
                        parcelable2.a.j = jSONObject5.optDouble("Credit");
                        jSONObject4 = jSONObject5.getJSONObject("Receiver");
                        parcelable2.a.f = jSONObject4.optBoolean("IsSmoneyUser");
                        parcelable2.a.o = jSONObject4.optBoolean("IsSmoneyPro");
                        parcelable2.a.h = jd.a(jSONObject4, "DisplayName");
                        parcelable2.a.g = "";
                        parcelable2.a.d = jd.a(jSONObject4, "Identifier");
                        parcelable2.a.m = jd.a(jSONObject5, "Message");
                        parcelable2.a.b = jk.a(jSONObject5.getString("OperationDate"));
                        if (!jSONObject5.isNull("SessionId")) {
                            parcelable2.c = jSONObject5.getString("SessionId");
                        }
                    }
                } else {
                    parcelable = new ServerError();
                    parcelable.b = jSONObject4.getInt("Code");
                    parcelable.c = jSONObject4.getString("ErrorMessage");
                    parcelable.e = jSONObject4.getInt("Priority");
                    parcelable.d = jSONObject4.getString("Title");
                }
                if (parcelable != null) {
                    bundle3.putParcelable("fr.smoney.android.izly.extras.serverError", parcelable);
                }
                if (parcelable2 != null) {
                    bundle3.putParcelable("fr.smoney.android.izly.extras.p2pPayConfirmData", parcelable2);
                }
                sendSuccess(intent, bundle3);
                return;
            case 236:
                sendSuccess(intent, dh.a(intent.getStringExtra("fr.smoney.android.izly.extras.checkPreAuthorizationUserId"), intent.getStringExtra("fr.smoney.android.izly.extras.checkPreAuthorizationSessionId"), (PreAuthorizationContainerData) intent.getParcelableExtra("fr.smoney.android.izly.extras.checkPreAuthorizationInfos")));
                return;
            case 237:
                sendSuccess(intent, ev.a(intent.getStringExtra("fr.smoney.android.izly.extras.makePreAuthorizationUserId"), intent.getStringExtra("fr.smoney.android.izly.extras.makePreAuthorizationSessionId"), intent.getStringExtra("fr.smoney.android.izly.extras.makePreAuthorizationPassword"), (PreAuthorizationContainerData) intent.getParcelableExtra("fr.smoney.android.izly.extras.makePreAuthorizationInfos")));
                return;
            case 238:
                stringExtra3 = intent.getStringExtra("fr.smoney.android.izly.extras.cancelPreAuthorizationUserId");
                stringExtra = intent.getStringExtra("fr.smoney.android.izly.extras.cancelPreAuthorizationSessionId");
                intent.getStringExtra("fr.smoney.android.izly.extras.cancelPreAuthorizationPassword");
                sendSuccess(intent, da.a(stringExtra3, stringExtra, (PreAuthorizationContainerData) intent.getParcelableExtra("fr.smoney.android.izly.extras.cancelPreAuthorizationInfos")));
                return;
            case 241:
                sendSuccess(intent, fr.a(intent.getStringExtra("fr.smoney.android.izly.extras.MoneyInTiersUserId"), intent.getStringExtra("fr.smoney.android.izly.extras.MoneyInTiersSessionId"), intent.getStringExtra("fr.smoney.android.izly.extras.MoneyInTiersEmail"), intent.getStringExtra("fr.smoney.android.izly.extras.MoneyInTiersMessage"), intent.getStringExtra("fr.smoney.android.izly.extras.MoneyInTiersAmount")));
                return;
            case 251:
                sendSuccess(intent, eb.a(intent.getStringExtra("fr.smoney.android.izly.extras.GetMySupportListUserId"), intent.getStringExtra("fr.smoney.android.izly.extras.GetMySupportListSessionId")));
                return;
            case 252:
                sendSuccess(intent, fp.a(intent.getStringExtra("fr.smoney.android.izly.extras.PutOppositionUserId"), intent.getStringExtra("fr.smoney.android.izly.extras.PutOppositionSessionId"), intent.getLongExtra("fr.smoney.android.izly.extras.PutOppositionSupportId", -1), intent.getIntExtra("fr.smoney.android.izly.extras.PutOppositionMotif", 0), intent.getStringExtra("fr.smoney.android.izly.extras.PutOppositionPassword")));
                return;
            case 253:
                sendSuccess(intent, ft.a(intent.getStringExtra("fr.smoney.android.izly.extras.RiseOppositionUserId"), intent.getStringExtra("fr.smoney.android.izly.extras.RiseOppositionSessionId"), intent.getLongExtra("fr.smoney.android.izly.extras.RiseOppositionSupportId", -1), intent.getStringExtra("fr.smoney.android.izly.extras.RiseOppositionPassword")));
                return;
            case 261:
                sendSuccess(intent, dw.a(intent.getStringExtra("fr.smoney.android.izly.extras.GetMyCounterListUserId"), intent.getStringExtra("fr.smoney.android.izly.extras.GetMyCounterListSessionId")));
                return;
            case 262:
                sendSuccess(intent, dv.a(intent.getStringExtra("fr.smoney.android.izly.extras.GetMyCounterDetailUserId"), intent.getStringExtra("fr.smoney.android.izly.extras.GetMyCounterDetailSessionId"), intent.getLongExtra("fr.smoney.android.izly.extras.GetMyCounterDetailId", -1)));
                return;
            case 263:
                sendSuccess(intent, db.a(intent.getStringExtra("fr.smoney.android.izly.extras.cardPaymentsUserId"), intent.getStringExtra("fr.smoney.android.izly.extras.cardPaymentsSessionId"), intent.getStringExtra("fr.smoney.android.izly.extras.cardPaymentsAmount"), intent.getStringExtra("fr.smoney.android.izly.extras.cardPaymentsAvailableCards")));
                return;
            case 264:
                sendSuccess(intent, do.a(intent.getStringExtra("fr.smoney.android.izly.extras.getMandatesUserId"), intent.getStringExtra("fr.smoney.android.izly.extras.getMandatesSessionId")));
                return;
            case 265:
                sendSuccess(intent, dg.a(intent.getStringExtra("fr.smoney.android.izly.extras.checkMoneyInBankAccountUserId"), intent.getStringExtra("fr.smoney.android.izly.extras.checkMoneyInBankAccountSessionId"), intent.getDoubleExtra("fr.smoney.android.izly.extras.checkMoneyInBankAccountAmount", -1.0d)));
                return;
            case 266:
                sendSuccess(intent, eu.a(intent.getStringExtra("fr.smoney.android.izly.extras.makeMoneyInBankAccountUserId"), intent.getStringExtra("fr.smoney.android.izly.extras.makeMoneyInBankAccountSessionId"), intent.getDoubleExtra("fr.smoney.android.izly.extras.makeMoneyInBankAccountAmount", -1.0d), intent.getStringExtra("fr.smoney.android.izly.extras.makeMoneyInBankAccountPassword")));
                return;
            case 267:
                sendSuccess(intent, ct.a(intent.getStringExtra("fr.smoney.android.izly.extras.acceptServicesCGUUserId"), intent.getStringExtra("fr.smoney.android.izly.extras.acceptServicesCGUSessionId"), intent.getStringExtra("fr.smoney.android.izly.extras.acceptServicesCGUServiceType"), intent.getStringExtra("fr.smoney.android.izly.extras.acceptServicesCGUOptin"), intent.getStringExtra("fr.smoney.android.izly.extras.acceptServicesCGUOptinPartners")));
                return;
            case 268:
                sendSuccess(intent, dx.a(intent.getStringExtra("fr.smoney.android.izly.extras.getCrousContactListUserId"), intent.getStringExtra("fr.smoney.android.izly.extras.getCrousContactListSessionId")));
                return;
            case 269:
                sendSuccess(intent, dk.a(intent.getStringExtra("fr.smoney.android.izly.extras.contactCrousSupportUserId"), intent.getStringExtra("fr.smoney.android.izly.extras.contactCrousSupportSessionId"), intent.getStringExtra("fr.smoney.android.izly.extras.contactCrousSupportUserEmail"), intent.getStringExtra("fr.smoney.android.izly.extras.contactCrousSupportUserPhone"), intent.getStringExtra("fr.smoney.android.izly.extras.contactCrousSupportCrousNumber"), intent.getStringExtra("fr.smoney.android.izly.extras.contactCrousSupportMessage")));
                return;
            case 270:
                sendSuccess(intent, el.a());
                return;
            case 271:
                sendSuccess(intent, dl.a(intent.getStringExtra("fr.smoney.android.izly.extras.urlDeals")));
                return;
            case 272:
                sendSuccess(intent, fo.a(intent.getStringExtra("fr.smoney.android.izly.extras.dealsSubscriptionUrl"), intent.getStringExtra("fr.smoney.android.izly.extras.dealsSubscriptionMail"), intent.getStringExtra("fr.smoney.android.izly.extras.dealsSubscriptionName"), intent.getStringExtra("fr.smoney.android.izly.extras.dealsSubscriptionFirstName"), intent.getStringExtra("fr.smoney.android.izly.extras.dealsSubscriptionIdIzly")));
                return;
            case 273:
                sendSuccess(intent, dq.a(this, intent.getStringExtra("fr.smoney.android.izly.extras.bannerBilendiWidth"), intent.getStringExtra("fr.smoney.android.izly.extras.bannerBilendiHeight"), intent.getStringExtra("fr.smoney.android.izly.extras.bannerBilendiLocation"), intent.getStringExtra("fr.smoney.android.izly.extras.bannerBilendiZipCode"), intent.getStringExtra("fr.smoney.android.izly.extras.bannerBilendiCrousId"), intent.getStringExtra("fr.smoney.android.izly.extras.bannerBilendiBankId")));
                return;
            case 274:
                sendSuccess(intent, ek.a(intent.getStringExtra("fr.smoney.android.izly.extras.getUserActivationDataEmail"), intent.getStringExtra("fr.smoney.android.izly.extras.getUserActivationDataActivationCode")));
                return;
            case 275:
                sendSuccess(intent, di.a(intent.getStringExtra("fr.smoney.android.izly.extras.checkUserActivationDataEmail"), intent.getStringExtra("fr.smoney.android.izly.extras.checkUserActivationDataActivationCode"), intent.getIntExtra("fr.smoney.android.izly.extras.checkUserActivationDataCivility", -1), intent.getStringExtra("fr.smoney.android.izly.extras.checkUserActivationDataBirthdate"), intent.getIntExtra("fr.smoney.android.izly.extras.checkUserActivationDataCountry", -1)));
                return;
            case 276:
                sendSuccess(intent, cu.a(intent.getStringExtra("fr.smoney.android.izly.extras.activateUserEmail"), intent.getStringExtra("fr.smoney.android.izly.extras.activateUserActivationCode"), intent.getIntExtra("fr.smoney.android.izly.extras.activateUserCivility", -1), intent.getStringExtra("fr.smoney.android.izly.extras.activateUserBirthdate"), intent.getIntExtra("fr.smoney.android.izly.extras.activateUserCountry", -1), intent.getStringExtra("fr.smoney.android.izly.extras.activateUserPassword"), intent.getStringExtra("fr.smoney.android.izly.extras.activateUserSecretQuestion"), intent.getStringExtra("fr.smoney.android.izly.extras.activateUserSecretAnswer"), intent.getIntExtra("fr.smoney.android.izly.extras.activateUserCgu", -1)));
                return;
            case 277:
                sendSuccess(intent, cw.a(intent.getStringExtra("fr.smoney.android.izly.extras.associatePhoneNumberUserPhoneNumber")));
                return;
            case 9923:
                sendSuccess(intent, ec.a(intent.getStringExtra("fr.smoney.android.izly.extras.getNbPromoOffersUserId"), intent.getStringExtra("fr.smoney.android.izly.extras.getNbPromoOffersSessionId"), intent.getDoubleExtra("fr.smoney.android.izly.extras.getNbPromoOffersLatitude", 0.0d), intent.getDoubleExtra("fr.smoney.android.izly.extras.getNbPromoOffersLongitude", 0.0d)));
                return;
            default:
                try {
                    sendFailure(intent, null);
                    return;
                } catch (IOException e) {
                    sendConnexionFailure(intent, null);
                    return;
                } catch (XmlPullParserException e2) {
                    sendDataFailure(intent, null);
                    return;
                } catch (ParserConfigurationException e3) {
                    sendDataFailure(intent, null);
                    return;
                } catch (SAXException e4) {
                    sendDataFailure(intent, null);
                    return;
                } catch (RuntimeException e5) {
                    sendDataFailure(intent, null);
                    return;
                } catch (JSONException e6) {
                    sendDataFailure(intent, null);
                    return;
                } catch (ParseException e7) {
                    sendDataFailure(intent, null);
                    return;
                }
        }
    }

    protected void sendResult(Intent intent, Bundle bundle, int i) {
        Intent intent2;
        OAuthData oAuthData;
        if (intent.getIntExtra("com.foxykeep.datadroid.extras.workerType", -1) == 231) {
            intent2 = (Intent) intent.getParcelableExtra("fr.smoney.android.izly.extras.refreshOAuthTokenWsToRecallIntent");
            ServerError serverError = (ServerError) bundle.getParcelable("fr.smoney.android.izly.extras.serverError");
            if (i == 0) {
                oAuthData = (OAuthData) bundle.getParcelable("fr.smoney.android.izly.extras.RefreshToken");
                if (serverError != null || oAuthData == null) {
                    a(intent2, serverError, i);
                    return;
                }
                SmoneyApplication.c.a(oAuthData);
                onHandleIntent(intent2);
                return;
            }
            a(intent2, serverError, i);
            return;
        }
        if (i == 0 && bundle != null) {
            ServerError serverError2 = (ServerError) bundle.getParcelable("fr.smoney.android.izly.extras.serverError");
            if (serverError2 != null && serverError2.b == 569) {
                intent2 = new Intent(this, SmoneyService.class);
                String f = SmoneyApplication.c.f();
                oAuthData = SmoneyApplication.c.a();
                if (!(f == null || oAuthData == null)) {
                    intent2.putExtra("com.foxykeep.datadroid.extras.workerType", 231);
                    intent2.putExtra("fr.smoney.android.izly.extras.refreshOAuthTokenWsToRecallIntent", intent);
                    intent2.putExtra("fr.smoney.android.izly.extras.refreshOAuthTokenUserId", f);
                    intent2.putExtra("fr.smoney.android.izly.extras.refreshOAuthTokenRefreshToken", oAuthData.b);
                    onHandleIntent(intent2);
                    return;
                }
            }
        }
        super.sendResult(intent, bundle, i);
    }
}
