package fr.smoney.android.izly.data.requestmanager;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;

import fr.smoney.android.izly.SmoneyApplication;
import fr.smoney.android.izly.data.model.ActivateUserData;
import fr.smoney.android.izly.data.model.AddBookmarkData;
import fr.smoney.android.izly.data.model.AssociatePhoneNumberData;
import fr.smoney.android.izly.data.model.BlockAccountData;
import fr.smoney.android.izly.data.model.CardPaymentsData;
import fr.smoney.android.izly.data.model.CbChangeAliasData;
import fr.smoney.android.izly.data.model.CbDeleteData;
import fr.smoney.android.izly.data.model.CheckMoneyDemandForProData;
import fr.smoney.android.izly.data.model.CheckMoneyInBankAccountData;
import fr.smoney.android.izly.data.model.CheckUserActivationData;
import fr.smoney.android.izly.data.model.ChooseDefaultCbData;
import fr.smoney.android.izly.data.model.ClientUserStatus;
import fr.smoney.android.izly.data.model.CounterData;
import fr.smoney.android.izly.data.model.CounterListData;
import fr.smoney.android.izly.data.model.DealsData;
import fr.smoney.android.izly.data.model.DeleteBankAccountData;
import fr.smoney.android.izly.data.model.DeleteUserPictureData;
import fr.smoney.android.izly.data.model.GetActiveMandateData;
import fr.smoney.android.izly.data.model.GetAttachmentData;
import fr.smoney.android.izly.data.model.GetBankAccountData;
import fr.smoney.android.izly.data.model.GetBilendiBannerData;
import fr.smoney.android.izly.data.model.GetBookmarksData;
import fr.smoney.android.izly.data.model.GetConfidentialitySettingsData;
import fr.smoney.android.izly.data.model.GetContactDetailsData;
import fr.smoney.android.izly.data.model.GetCrousContactListData;
import fr.smoney.android.izly.data.model.GetMyCbListData;
import fr.smoney.android.izly.data.model.GetMySupportListData;
import fr.smoney.android.izly.data.model.GetNearProListData;
import fr.smoney.android.izly.data.model.GetNewEventNumberData;
import fr.smoney.android.izly.data.model.GetNewsFeedData;
import fr.smoney.android.izly.data.model.GetNewsFeedDetailsData;
import fr.smoney.android.izly.data.model.GetProCashingModelsData;
import fr.smoney.android.izly.data.model.GetSecretQuestionData;
import fr.smoney.android.izly.data.model.GetTaxListData;
import fr.smoney.android.izly.data.model.GetUserActivationData;
import fr.smoney.android.izly.data.model.GoodDealsArray;
import fr.smoney.android.izly.data.model.InitiatePasswordRecoveryData;
import fr.smoney.android.izly.data.model.IsEnrollmentOpenData;
import fr.smoney.android.izly.data.model.IsSessionValidData;
import fr.smoney.android.izly.data.model.LoginData;
import fr.smoney.android.izly.data.model.LoginLightData;
import fr.smoney.android.izly.data.model.MakeBankAccountUpdateData;
import fr.smoney.android.izly.data.model.MakeMoneyDemandForProData;
import fr.smoney.android.izly.data.model.MakeMoneyDemandRelaunchData;
import fr.smoney.android.izly.data.model.MakeMoneyInBankAccountData;
import fr.smoney.android.izly.data.model.MoneyInCbAndPayConfirmData;
import fr.smoney.android.izly.data.model.MoneyInCbAndPayData;
import fr.smoney.android.izly.data.model.MoneyInCbAndPayRequestConfirmData;
import fr.smoney.android.izly.data.model.MoneyInCbAndPayRequestData;
import fr.smoney.android.izly.data.model.MoneyInCbCbListData;
import fr.smoney.android.izly.data.model.MoneyInCbConfirmData;
import fr.smoney.android.izly.data.model.MoneyInCbData;
import fr.smoney.android.izly.data.model.MoneyOutTransferAccountData;
import fr.smoney.android.izly.data.model.MoneyOutTransferConfirmData;
import fr.smoney.android.izly.data.model.MoneyOutTransferData;
import fr.smoney.android.izly.data.model.OAuthData;
import fr.smoney.android.izly.data.model.P2PGetListData;
import fr.smoney.android.izly.data.model.P2PGetMultConfirmData;
import fr.smoney.android.izly.data.model.P2PGetMultData;
import fr.smoney.android.izly.data.model.P2PPayConfirmData;
import fr.smoney.android.izly.data.model.P2PPayData;
import fr.smoney.android.izly.data.model.P2PPayRequestConfirmData;
import fr.smoney.android.izly.data.model.P2PPayRequestData;
import fr.smoney.android.izly.data.model.P2PPayRequestListData;
import fr.smoney.android.izly.data.model.PostDealsCodeResponse;
import fr.smoney.android.izly.data.model.PreAuthorizationContainerData;
import fr.smoney.android.izly.data.model.RemoveDeviceTokenData;
import fr.smoney.android.izly.data.model.SecretQuestionAnswer;
import fr.smoney.android.izly.data.model.SendAttachmentData;
import fr.smoney.android.izly.data.model.SendChatMessageData;
import fr.smoney.android.izly.data.model.ServerError;
import fr.smoney.android.izly.data.model.SetConfidentialitySettingsData;
import fr.smoney.android.izly.data.model.SetDeviceTokenData;
import fr.smoney.android.izly.data.model.TransactionListData;
import fr.smoney.android.izly.data.model.UpdatePasswordData;
import fr.smoney.android.izly.data.model.UpdateUserPictureData;
import fr.smoney.android.izly.data.model.UpdateUserProfileData;
import fr.smoney.android.izly.data.model.UserData;

import java.util.ArrayList;

import org.kxml2.wap.Wbxml;
import org.spongycastle.asn1.eac.EACTags;
import org.spongycastle.crypto.tls.CipherSuite;

class SmoneyRequestManager$EvalReceiver extends ResultReceiver {
    final /* synthetic */ SmoneyRequestManager a;

    SmoneyRequestManager$EvalReceiver(SmoneyRequestManager smoneyRequestManager, Handler handler) {
        this.a = smoneyRequestManager;
        super(handler);
    }

    public void onReceiveResult(int i, Bundle bundle) {
        int i2 = 0;
        SmoneyRequestManager smoneyRequestManager = this.a;
        int i3 = bundle.getInt("com.foxykeep.datadroid.extras.requestId");
        Intent intent = (Intent) smoneyRequestManager.b.get(i3);
        if (intent != null) {
            int intExtra = intent.getIntExtra("com.foxykeep.datadroid.extras.workerType", -1);
            if (i == 0) {
                OAuthData oAuthData;
                ArrayList arrayList;
                switch (intExtra) {
                    case 1:
                        smoneyRequestManager.f.b = (LoginData) bundle.getParcelable("fr.smoney.android.izly.extras.loginData");
                        smoneyRequestManager.f.c = (UserData) bundle.getParcelable("fr.smoney.android.izly.extras.userData");
                        smoneyRequestManager.f.d = (ServerError) bundle.getParcelable("fr.smoney.android.izly.extras.serverError");
                        oAuthData = (OAuthData) bundle.getParcelable("fr.smoney.android.izly.extras.oAuthData");
                        if (oAuthData != null) {
                            SmoneyApplication.c.a(oAuthData);
                            break;
                        }
                        break;
                    case 11:
                        smoneyRequestManager.f.f = (P2PPayData) bundle.getParcelable("fr.smoney.android.izly.extras.p2pPayData");
                        smoneyRequestManager.f.g = (ServerError) bundle.getParcelable("fr.smoney.android.izly.extras.serverError");
                        break;
                    case 12:
                        smoneyRequestManager.f.h = (P2PPayConfirmData) bundle.getParcelable("fr.smoney.android.izly.extras.p2pPayConfirmData");
                        smoneyRequestManager.f.i = (ServerError) bundle.getParcelable("fr.smoney.android.izly.extras.serverError");
                        if (smoneyRequestManager.f.i == null && smoneyRequestManager.f.h != null) {
                            if (smoneyRequestManager.f.h.c != null) {
                                smoneyRequestManager.f.b.c = smoneyRequestManager.f.h.c;
                            }
                            smoneyRequestManager.f.cs = true;
                            smoneyRequestManager.f.ct = true;
                            smoneyRequestManager.f.cx = true;
                            ca.a(smoneyRequestManager.c);
                            break;
                        }
                    case 13:
                        smoneyRequestManager.f.j = (MoneyInCbAndPayData) bundle.getParcelable("fr.smoney.android.izly.extras.moneyInCbAndPayData");
                        smoneyRequestManager.f.k = (ServerError) bundle.getParcelable("fr.smoney.android.izly.extras.serverError");
                        break;
                    case 14:
                        smoneyRequestManager.f.l = (MoneyInCbAndPayConfirmData) bundle.getParcelable("fr.smoney.android.izly.extras.moneyInCbAndPayConfirmData");
                        smoneyRequestManager.f.m = (ServerError) bundle.getParcelable("fr.smoney.android.izly.extras.serverError");
                        if (smoneyRequestManager.f.m == null && smoneyRequestManager.f.l != null) {
                            if (smoneyRequestManager.f.l.f != null) {
                                smoneyRequestManager.f.b.c = smoneyRequestManager.f.l.f;
                            }
                            smoneyRequestManager.f.cs = true;
                            smoneyRequestManager.f.ct = true;
                            smoneyRequestManager.f.cx = true;
                            ca.a(smoneyRequestManager.c);
                            break;
                        }
                    case 21:
                        TransactionListData transactionListData = (TransactionListData) bundle.getParcelable("fr.smoney.android.izly.extras.transactionListData");
                        smoneyRequestManager.f.o = (ServerError) bundle.getParcelable("fr.smoney.android.izly.extras.serverError");
                        if (smoneyRequestManager.f.o == null && transactionListData != null) {
                            arrayList = new ArrayList();
                            if (smoneyRequestManager.f.n != null) {
                                arrayList.addAll(smoneyRequestManager.f.n.b);
                            } else if (smoneyRequestManager.f.b.o > 0) {
                                smoneyRequestManager.f.cx = true;
                                ca.a(smoneyRequestManager.c);
                            }
                            arrayList.addAll(transactionListData.b);
                            transactionListData.b = arrayList;
                            smoneyRequestManager.f.n = transactionListData;
                            break;
                        }
                    case 22:
                        smoneyRequestManager.f.p = (SendChatMessageData) bundle.getParcelable("fr.smoney.android.izly.extras.sendChatMessageData");
                        smoneyRequestManager.f.q = (ServerError) bundle.getParcelable("fr.smoney.android.izly.extras.serverError");
                        if (smoneyRequestManager.f.q == null && smoneyRequestManager.f.p != null) {
                            smoneyRequestManager.f.cs = true;
                            smoneyRequestManager.f.ct = true;
                            break;
                        }
                    case 31:
                        smoneyRequestManager.f.r = (MoneyInCbCbListData) bundle.getParcelable("fr.smoney.android.izly.extras.moneyInCbCbListData");
                        smoneyRequestManager.f.s = (ServerError) bundle.getParcelable("fr.smoney.android.izly.extras.serverError");
                        break;
                    case 32:
                        smoneyRequestManager.f.t = (MoneyInCbData) bundle.getParcelable("fr.smoney.android.izly.extras.moneyInCbData");
                        smoneyRequestManager.f.u = (ServerError) bundle.getParcelable("fr.smoney.android.izly.extras.serverError");
                        break;
                    case 33:
                        smoneyRequestManager.f.w = (MoneyInCbConfirmData) bundle.getParcelable("fr.smoney.android.izly.extras.moneyInCbConfirmData");
                        smoneyRequestManager.f.x = (ServerError) bundle.getParcelable("fr.smoney.android.izly.extras.serverError");
                        if (smoneyRequestManager.f.x == null && smoneyRequestManager.f.w != null) {
                            if (smoneyRequestManager.f.w.f != null) {
                                smoneyRequestManager.f.b.c = smoneyRequestManager.f.w.f;
                            }
                            smoneyRequestManager.f.cs = true;
                            smoneyRequestManager.f.ct = true;
                            smoneyRequestManager.f.cx = true;
                            ca.a(smoneyRequestManager.c);
                            break;
                        }
                    case 41:
                        smoneyRequestManager.f.y = (UserData) bundle.getParcelable("fr.smoney.android.izly.extras.userData");
                        smoneyRequestManager.f.z = (ServerError) bundle.getParcelable("fr.smoney.android.izly.extras.serverError");
                        break;
                    case 42:
                        smoneyRequestManager.f.bs = (ServerError) bundle.getParcelable("fr.smoney.android.izly.extras.serverError");
                        break;
                    case 51:
                        smoneyRequestManager.f.A = (MoneyOutTransferAccountData) bundle.getParcelable("fr.smoney.android.izly.extras.moneyOutTransferAccountData");
                        smoneyRequestManager.f.B = (ServerError) bundle.getParcelable("fr.smoney.android.izly.extras.serverError");
                        break;
                    case 52:
                        smoneyRequestManager.f.C = (MoneyOutTransferData) bundle.getParcelable("fr.smoney.android.izly.extras.moneyOutTransferData");
                        smoneyRequestManager.f.D = (ServerError) bundle.getParcelable("fr.smoney.android.izly.extras.serverError");
                        break;
                    case 53:
                        smoneyRequestManager.f.E = (MoneyOutTransferConfirmData) bundle.getParcelable("fr.smoney.android.izly.extras.moneyOutTransferConfirmData");
                        smoneyRequestManager.f.F = (ServerError) bundle.getParcelable("fr.smoney.android.izly.extras.serverError");
                        if (smoneyRequestManager.f.F == null && smoneyRequestManager.f.E != null) {
                            if (smoneyRequestManager.f.E.j != null) {
                                smoneyRequestManager.f.b.c = smoneyRequestManager.f.E.j;
                            }
                            smoneyRequestManager.f.cs = true;
                            smoneyRequestManager.f.ct = true;
                            smoneyRequestManager.f.cx = true;
                            ca.a(smoneyRequestManager.c);
                            break;
                        }
                    case 61:
                        P2PGetListData p2PGetListData = (P2PGetListData) bundle.getParcelable("fr.smoney.android.izly.extras.p2pGetListData");
                        smoneyRequestManager.f.H = (ServerError) bundle.getParcelable("fr.smoney.android.izly.extras.serverError");
                        if (smoneyRequestManager.f.H == null && p2PGetListData != null) {
                            arrayList = new ArrayList();
                            if (smoneyRequestManager.f.G != null) {
                                arrayList.addAll(smoneyRequestManager.f.G.a);
                            } else if (smoneyRequestManager.f.b.m > 0) {
                                smoneyRequestManager.f.cx = true;
                                ca.a(smoneyRequestManager.c);
                            }
                            arrayList.addAll(p2PGetListData.a);
                            p2PGetListData.a = arrayList;
                            smoneyRequestManager.f.G = p2PGetListData;
                            break;
                        }
                    case 62:
                        smoneyRequestManager.f.I = bundle.getLong("fr.smoney.android.izly.extras.p2pGetHideIdToHide");
                        smoneyRequestManager.f.J = (ServerError) bundle.getParcelable("fr.smoney.android.izly.extras.serverError");
                        if (smoneyRequestManager.f.J == null && smoneyRequestManager.f.I != -1) {
                            smoneyRequestManager.f.b(smoneyRequestManager.f.I);
                            break;
                        }
                    case 63:
                        smoneyRequestManager.f.K = bundle.getLong("fr.smoney.android.izly.extras.p2pGetCancelIdToCancel");
                        smoneyRequestManager.f.L = (ServerError) bundle.getParcelable("fr.smoney.android.izly.extras.serverError");
                        if (smoneyRequestManager.f.L == null && smoneyRequestManager.f.K != -1) {
                            smoneyRequestManager.f.cs = true;
                            smoneyRequestManager.f.cu = true;
                            break;
                        }
                    case 66:
                        smoneyRequestManager.f.M = (P2PGetMultData) bundle.getParcelable("fr.smoney.android.izly.extras.p2pGetMultData");
                        smoneyRequestManager.f.N = (ServerError) bundle.getParcelable("fr.smoney.android.izly.extras.serverError");
                        break;
                    case 67:
                        smoneyRequestManager.f.O = (P2PGetMultConfirmData) bundle.getParcelable("fr.smoney.android.izly.extras.p2pGetMultConfirmData");
                        smoneyRequestManager.f.P = (ServerError) bundle.getParcelable("fr.smoney.android.izly.extras.serverError");
                        if (smoneyRequestManager.f.P == null && smoneyRequestManager.f.O != null) {
                            if (smoneyRequestManager.f.O.i != null) {
                                smoneyRequestManager.f.b.c = smoneyRequestManager.f.O.i;
                            }
                            smoneyRequestManager.f.cs = true;
                            smoneyRequestManager.f.cu = true;
                            smoneyRequestManager.f.cx = true;
                            ca.a(smoneyRequestManager.c);
                            break;
                        }
                    case 71:
                        P2PPayRequestListData p2PPayRequestListData = (P2PPayRequestListData) bundle.getParcelable("fr.smoney.android.izly.extras.p2pPayRequestListData");
                        smoneyRequestManager.f.R = (ServerError) bundle.getParcelable("fr.smoney.android.izly.extras.serverError");
                        if (smoneyRequestManager.f.R == null && p2PPayRequestListData != null) {
                            arrayList = new ArrayList();
                            if (smoneyRequestManager.f.Q != null) {
                                arrayList.addAll(smoneyRequestManager.f.Q.a);
                            } else if (smoneyRequestManager.f.b.n > 0) {
                                smoneyRequestManager.f.cx = true;
                                ca.a(smoneyRequestManager.c);
                            }
                            arrayList.addAll(p2PPayRequestListData.a);
                            p2PPayRequestListData.a = arrayList;
                            smoneyRequestManager.f.Q = p2PPayRequestListData;
                            break;
                        }
                    case 72:
                        smoneyRequestManager.f.S = bundle.getLong("fr.smoney.android.izly.extras.p2pPayRequestHideIdToHide");
                        smoneyRequestManager.f.T = (ServerError) bundle.getParcelable("fr.smoney.android.izly.extras.serverError");
                        if (smoneyRequestManager.f.T == null && smoneyRequestManager.f.S != -1) {
                            smoneyRequestManager.f.a(smoneyRequestManager.f.S);
                            smoneyRequestManager.f.cv = true;
                            break;
                        }
                    case 73:
                        smoneyRequestManager.f.U = (P2PPayRequestData) bundle.getParcelable("fr.smoney.android.izly.extras.p2pPayRequestData");
                        smoneyRequestManager.f.V = (ServerError) bundle.getParcelable("fr.smoney.android.izly.extras.serverError");
                        break;
                    case 74:
                        smoneyRequestManager.f.W = (P2PPayRequestConfirmData) bundle.getParcelable("fr.smoney.android.izly.extras.p2pPayRequestConfirmData");
                        smoneyRequestManager.f.X = (ServerError) bundle.getParcelable("fr.smoney.android.izly.extras.serverError");
                        if (smoneyRequestManager.f.X == null && smoneyRequestManager.f.W != null) {
                            if (smoneyRequestManager.f.W.c != null) {
                                smoneyRequestManager.f.b.c = smoneyRequestManager.f.W.c;
                            }
                            smoneyRequestManager.f.cs = true;
                            smoneyRequestManager.f.ct = true;
                            smoneyRequestManager.f.cv = true;
                            smoneyRequestManager.f.cx = true;
                            ca.a(smoneyRequestManager.c);
                            break;
                        }
                    case 75:
                        smoneyRequestManager.f.Y = (MoneyInCbAndPayRequestData) bundle.getParcelable("fr.smoney.android.izly.extras.moneyInCbAndPayRequestData");
                        smoneyRequestManager.f.Z = (ServerError) bundle.getParcelable("fr.smoney.android.izly.extras.serverError");
                        break;
                    case 76:
                        smoneyRequestManager.f.aa = (MoneyInCbAndPayRequestConfirmData) bundle.getParcelable("fr.smoney.android.izly.extras.moneyInCbAndPayRequestConfirmData");
                        smoneyRequestManager.f.ab = (ServerError) bundle.getParcelable("fr.smoney.android.izly.extras.serverError");
                        if (smoneyRequestManager.f.ab == null && smoneyRequestManager.f.aa != null) {
                            if (smoneyRequestManager.f.aa.g != null) {
                                smoneyRequestManager.f.b.c = smoneyRequestManager.f.aa.g;
                            }
                            smoneyRequestManager.f.cs = true;
                            smoneyRequestManager.f.ct = true;
                            smoneyRequestManager.f.cx = true;
                            ca.a(smoneyRequestManager.c);
                            break;
                        }
                    case 81:
                        smoneyRequestManager.f.ac = (CbChangeAliasData) bundle.getParcelable("fr.smoney.android.izly.extras.cbChangeAliasData");
                        smoneyRequestManager.f.ad = (ServerError) bundle.getParcelable("fr.smoney.android.izly.extras.serverError");
                        break;
                    case 82:
                        smoneyRequestManager.f.ae = (CbDeleteData) bundle.getParcelable("fr.smoney.android.izly.extras.cbDeleteData");
                        smoneyRequestManager.f.af = (ServerError) bundle.getParcelable("fr.smoney.android.izly.extras.serverError");
                        break;
                    case 91:
                        smoneyRequestManager.f.ag = (MakeBankAccountUpdateData) bundle.getParcelable("fr.smoney.android.izly.extras.changeTransferAccountData");
                        smoneyRequestManager.f.ah = (ServerError) bundle.getParcelable("fr.smoney.android.izly.extras.serverError");
                        if (!(smoneyRequestManager.f.ah != null || smoneyRequestManager.f.ag == null || smoneyRequestManager.f.ag.k == null)) {
                            smoneyRequestManager.f.b.c = smoneyRequestManager.f.ag.k;
                            break;
                        }
                    case 93:
                        smoneyRequestManager.f.aC = (DeleteBankAccountData) bundle.getParcelable("fr.smoney.android.izly.extras.deleteBankAccountData");
                        smoneyRequestManager.f.aD = (ServerError) bundle.getParcelable("fr.smoney.android.izly.extras.serverError");
                        break;
                    case 102:
                        smoneyRequestManager.f.ai = (GetContactDetailsData) bundle.getParcelable("fr.smoney.android.izly.extras.bookmarkContactData");
                        smoneyRequestManager.f.aj = (ServerError) bundle.getParcelable("fr.smoney.android.izly.extras.serverError");
                        if (smoneyRequestManager.f.aj == null && smoneyRequestManager.f.ai != null) {
                            smoneyRequestManager.f.cw = true;
                            break;
                        }
                    case 103:
                        smoneyRequestManager.f.ak = (GetContactDetailsData) bundle.getParcelable("fr.smoney.android.izly.extras.blockContactData");
                        smoneyRequestManager.f.al = (ServerError) bundle.getParcelable("fr.smoney.android.izly.extras.serverError");
                        if (smoneyRequestManager.f.al == null && smoneyRequestManager.f.ak != null) {
                            smoneyRequestManager.f.cw = true;
                            break;
                        }
                    case 104:
                        smoneyRequestManager.f.am = (AddBookmarkData) bundle.getParcelable("fr.smoney.android.izly.extras.addBookmarkData");
                        smoneyRequestManager.f.an = (ServerError) bundle.getParcelable("fr.smoney.android.izly.extras.serverError");
                        if (smoneyRequestManager.f.an == null && smoneyRequestManager.f.am != null) {
                            smoneyRequestManager.f.cw = true;
                            break;
                        }
                    case 105:
                        smoneyRequestManager.f.ao = (GetBookmarksData) bundle.getParcelable("fr.smoney.android.izly.extras.getBookmarksData");
                        smoneyRequestManager.f.ap = (ServerError) bundle.getParcelable("fr.smoney.android.izly.extras.serverError");
                        if (smoneyRequestManager.f.ap == null && smoneyRequestManager.f.ao != null) {
                            smoneyRequestManager.f.cw = false;
                            break;
                        }
                    case EACTags.COEXISTANT_TAG_ALLOCATION_AUTHORITY /*121*/:
                        smoneyRequestManager.f.aq = (GetSecretQuestionData) bundle.getParcelable("fr.smoney.android.izly.extras.getSecretQuestionData");
                        smoneyRequestManager.f.ar = (ServerError) bundle.getParcelable("fr.smoney.android.izly.extras.serverError");
                        break;
                    case EACTags.SECURITY_SUPPORT_TEMPLATE /*122*/:
                        smoneyRequestManager.f.as = (SecretQuestionAnswer) bundle.getParcelable("fr.smoney.android.izly.extras.secretQuestionAnswerData");
                        smoneyRequestManager.f.at = (ServerError) bundle.getParcelable("fr.smoney.android.izly.extras.serverError");
                        break;
                    case Wbxml.STR_T /*131*/:
                        smoneyRequestManager.f.au = (SetDeviceTokenData) bundle.getParcelable("fr.smoney.android.izly.extras.setDeviceTokenData");
                        smoneyRequestManager.f.av = (ServerError) bundle.getParcelable("fr.smoney.android.izly.extras.serverError");
                        break;
                    case Wbxml.LITERAL_A /*132*/:
                        smoneyRequestManager.f.aw = (RemoveDeviceTokenData) bundle.getParcelable("fr.smoney.android.izly.extras.removeDeviceTokenData");
                        smoneyRequestManager.f.ax = (ServerError) bundle.getParcelable("fr.smoney.android.izly.extras.serverError");
                        break;
                    case CipherSuite.TLS_PSK_WITH_AES_256_CBC_SHA /*141*/:
                        smoneyRequestManager.f.ay = (SendAttachmentData) bundle.getParcelable("fr.smoney.android.izly.extras.sendAttachementData");
                        smoneyRequestManager.f.az = (ServerError) bundle.getParcelable("fr.smoney.android.izly.extras.serverError");
                        break;
                    case CipherSuite.TLS_DHE_PSK_WITH_RC4_128_SHA /*142*/:
                        smoneyRequestManager.f.aA = (GetAttachmentData) bundle.getParcelable("fr.smoney.android.izly.extras.getAttachementData");
                        smoneyRequestManager.f.aB = (ServerError) bundle.getParcelable("fr.smoney.android.izly.extras.serverError");
                        break;
                    case 151:
                        smoneyRequestManager.f.aE = (GetNewEventNumberData) bundle.getParcelable("fr.smoney.android.izly.extras.getBadgesData");
                        smoneyRequestManager.f.aF = (ServerError) bundle.getParcelable("fr.smoney.android.izly.extras.serverError");
                        if (!(smoneyRequestManager.f.b == null || smoneyRequestManager.f.aE == null)) {
                            smoneyRequestManager.f.b.m = smoneyRequestManager.f.aE.a;
                            smoneyRequestManager.f.b.n = smoneyRequestManager.f.aE.b;
                            smoneyRequestManager.f.b.o = smoneyRequestManager.f.aE.c;
                            smoneyRequestManager.f.cx = false;
                        }
                        smoneyRequestManager.c.sendBroadcast(new Intent("fr.smoney.android.izly.notifications.NOTIFICATION_UPDATER"));
                        break;
                    case 161:
                        smoneyRequestManager.f.aG = (UpdatePasswordData) bundle.getParcelable("fr.smoney.android.izly.extras.updatePasswordData");
                        smoneyRequestManager.f.aH = (ServerError) bundle.getParcelable("fr.smoney.android.izly.extras.serverError");
                        break;
                    case 171:
                        smoneyRequestManager.f.aI = (GetConfidentialitySettingsData) bundle.getParcelable("fr.smoney.android.izly.extras.getConfidentialitySettingsData");
                        smoneyRequestManager.f.aJ = (ServerError) bundle.getParcelable("fr.smoney.android.izly.extras.serverError");
                        break;
                    case 172:
                        smoneyRequestManager.f.aK = (SetConfidentialitySettingsData) bundle.getParcelable("fr.smoney.android.izly.extras.setConfidentialitySettingsData");
                        smoneyRequestManager.f.aL = (ServerError) bundle.getParcelable("fr.smoney.android.izly.extras.serverError");
                        break;
                    case 173:
                        smoneyRequestManager.f.aM = (UpdateUserProfileData) bundle.getParcelable("fr.smoney.android.izly.extras.updateUserProfileData");
                        smoneyRequestManager.f.aN = (ServerError) bundle.getParcelable("fr.smoney.android.izly.extras.serverError");
                        if (smoneyRequestManager.f.aM != null) {
                            smoneyRequestManager.f.b.E = smoneyRequestManager.f.aM.b;
                            break;
                        }
                        break;
                    case 174:
                        smoneyRequestManager.f.aO = (UpdateUserPictureData) bundle.getParcelable("fr.smoney.android.izly.extras.updateUserPictureData");
                        smoneyRequestManager.f.aP = (ServerError) bundle.getParcelable("fr.smoney.android.izly.extras.serverError");
                        break;
                    case 181:
                        smoneyRequestManager.f.aQ = (BlockAccountData) bundle.getParcelable("fr.smoney.android.izly.extras.blockAccountData");
                        smoneyRequestManager.f.aP = (ServerError) bundle.getParcelable("fr.smoney.android.izly.extras.serverError");
                        break;
                    case 191:
                        smoneyRequestManager.f.aS = (InitiatePasswordRecoveryData) bundle.getParcelable("fr.smoney.android.izly.extras.initiatePasswordRecoveryData");
                        smoneyRequestManager.f.aT = (ServerError) bundle.getParcelable("fr.smoney.android.izly.extras.serverError");
                        break;
                    case 211:
                        smoneyRequestManager.f.aU = (ChooseDefaultCbData) bundle.getParcelable("fr.smoney.android.izly.extras.chooseDefaultIdData");
                        smoneyRequestManager.f.aV = (ServerError) bundle.getParcelable("fr.smoney.android.izly.extras.serverError");
                        break;
                    case 212:
                        smoneyRequestManager.f.aY = (DeleteUserPictureData) bundle.getParcelable("fr.smoney.android.izly.extras.deleteUserPictureData");
                        smoneyRequestManager.f.aZ = (ServerError) bundle.getParcelable("fr.smoney.android.izly.extras.serverError");
                        break;
                    case 213:
                        smoneyRequestManager.f.aW = (GetNearProListData) bundle.getParcelable("fr.smoney.android.izly.extras.GetNearProList");
                        smoneyRequestManager.f.aX = (ServerError) bundle.getParcelable("fr.smoney.android.izly.extras.serverError");
                        break;
                    case 214:
                        GetProCashingModelsData getProCashingModelsData = (GetProCashingModelsData) bundle.getParcelable("fr.smoney.android.izly.extras.GetProCashingModels");
                        if (getProCashingModelsData != null && getProCashingModelsData.a.size() > 0) {
                            smoneyRequestManager.f.ba = getProCashingModelsData;
                        }
                        smoneyRequestManager.f.bb = (ServerError) bundle.getParcelable("fr.smoney.android.izly.extras.serverError");
                        break;
                    case 215:
                        smoneyRequestManager.f.bc = (CheckMoneyDemandForProData) bundle.getParcelable("fr.smoney.android.izly.extras.CheckMoneyDemandForPro");
                        smoneyRequestManager.f.bd = (ServerError) bundle.getParcelable("fr.smoney.android.izly.extras.serverError");
                        break;
                    case 216:
                        smoneyRequestManager.f.be = (MakeMoneyDemandForProData) bundle.getParcelable("fr.smoney.android.izly.extras.MakeMoneyDemandForPro");
                        smoneyRequestManager.f.bf = (ServerError) bundle.getParcelable("fr.smoney.android.izly.extras.serverError");
                        break;
                    case 217:
                        smoneyRequestManager.f.bg = (GetTaxListData) bundle.getParcelable("fr.smoney.android.izly.extras.GetTaxList");
                        smoneyRequestManager.f.bh = (ServerError) bundle.getParcelable("fr.smoney.android.izly.extras.serverError");
                        break;
                    case 218:
                        smoneyRequestManager.f.bi = (IsEnrollmentOpenData) bundle.getParcelable("fr.smoney.android.izly.extras.IsEnrollementOpen");
                        smoneyRequestManager.f.bj = (ServerError) bundle.getParcelable("fr.smoney.android.izly.extras.serverError");
                        break;
                    case 219:
                        smoneyRequestManager.f.bk = (MakeMoneyDemandRelaunchData) bundle.getParcelable("fr.smoney.android.izly.extras.MakeMoneyDemandRelaunch");
                        smoneyRequestManager.f.bl = (ServerError) bundle.getParcelable("fr.smoney.android.izly.extras.serverError");
                        if (smoneyRequestManager.f.bl == null && smoneyRequestManager.f.bk != null) {
                            smoneyRequestManager.f.cs = true;
                            smoneyRequestManager.f.cu = true;
                            break;
                        }
                    case 220:
                        smoneyRequestManager.f.bm = (ServerError) bundle.getParcelable("fr.smoney.android.izly.extras.serverError");
                        break;
                    case 221:
                        smoneyRequestManager.f.bn = (ServerError) bundle.getParcelable("fr.smoney.android.izly.extras.serverError");
                        if (smoneyRequestManager.f.bn == null && smoneyRequestManager.f.b != null) {
                            ClientUserStatus clientUserStatus = (ClientUserStatus) bundle.getParcelable("fr.smoney.android.izly.extras.ClientUserStatus");
                            if (clientUserStatus != null) {
                                smoneyRequestManager.f.b.E = clientUserStatus.a;
                                smoneyRequestManager.f.b.g = clientUserStatus.e;
                                smoneyRequestManager.f.b.f = clientUserStatus.d;
                                smoneyRequestManager.f.b.i = clientUserStatus.g;
                                smoneyRequestManager.f.b.h = clientUserStatus.f;
                                smoneyRequestManager.f.b.e = clientUserStatus.c;
                                smoneyRequestManager.f.b.d = clientUserStatus.b;
                                break;
                            }
                        }
                        break;
                    case 224:
                        smoneyRequestManager.f.bq = (GetContactDetailsData) bundle.getParcelable("fr.smoney.android.izly.extras.GetContactDetails");
                        smoneyRequestManager.f.br = (ServerError) bundle.getParcelable("fr.smoney.android.izly.extras.serverError");
                        break;
                    case 225:
                        smoneyRequestManager.f.bt = (GetBankAccountData) bundle.getParcelable("fr.smoney.android.izly.extras.getBankAccountData");
                        smoneyRequestManager.f.bu = (ServerError) bundle.getParcelable("fr.smoney.android.izly.extras.serverError");
                        break;
                    case 226:
                        GetNewsFeedData getNewsFeedData = (GetNewsFeedData) bundle.getParcelable("fr.smoney.android.izly.extras.GetNewsFeed");
                        smoneyRequestManager.f.bw = (ServerError) bundle.getParcelable("fr.smoney.android.izly.extras.serverError");
                        smoneyRequestManager.f.bv = getNewsFeedData;
                        break;
                    case 227:
                        smoneyRequestManager.f.bx = (GetNewsFeedDetailsData) bundle.getParcelable("fr.smoney.android.izly.extras.GetNewsFeedDetails");
                        smoneyRequestManager.f.by = (ServerError) bundle.getParcelable("fr.smoney.android.izly.extras.serverError");
                        break;
                    case 228:
                        smoneyRequestManager.f.b = (LoginData) bundle.getParcelable("fr.smoney.android.izly.extras.GetLogonInfos");
                        smoneyRequestManager.f.d = (ServerError) bundle.getParcelable("fr.smoney.android.izly.extras.serverError");
                        break;
                    case 229:
                        IsSessionValidData isSessionValidData = (IsSessionValidData) bundle.getParcelable("fr.smoney.android.izly.extras.IsSessionValid");
                        ServerError serverError = (ServerError) bundle.getParcelable("fr.smoney.android.izly.extras.serverError");
                        if (!(smoneyRequestManager.f.b == null || serverError != null || isSessionValidData == null || isSessionValidData.b == null)) {
                            smoneyRequestManager.f.b.B = isSessionValidData.b;
                        }
                        if (!(serverError == null && (isSessionValidData == null || isSessionValidData.a))) {
                            smoneyRequestManager.f.a();
                            intent = new Intent("fr.smoney.android.izly.notifications.NOTIFICATION_SESSION_STATE_CHANGE_INTENT_URI");
                            intent.putExtra("fr.smoney.android.izly.sessionState", 0);
                            smoneyRequestManager.c.sendBroadcast(intent);
                            break;
                        }
                    case 230:
                        smoneyRequestManager.f.bz = (GetMyCbListData) bundle.getParcelable("fr.smoney.android.izly.extras.GetMyCbList");
                        smoneyRequestManager.f.bA = (ServerError) bundle.getParcelable("fr.smoney.android.izly.extras.serverError");
                        break;
                    case 232:
                        smoneyRequestManager.f.bJ = (LoginLightData) bundle.getParcelable("fr.smoney.android.izly.extras.LoginLight");
                        smoneyRequestManager.f.bK = (ServerError) bundle.getParcelable("fr.smoney.android.izly.extras.serverError");
                        oAuthData = (OAuthData) bundle.getParcelable("fr.smoney.android.izly.extras.oAuthData");
                        if (smoneyRequestManager.f.bK == null) {
                            if (smoneyRequestManager.f.bJ != null) {
                                if (smoneyRequestManager.f.b == null) {
                                    smoneyRequestManager.f.b = new LoginData();
                                }
                                LoginData loginData = smoneyRequestManager.f.b;
                                LoginLightData loginLightData = smoneyRequestManager.f.bJ;
                                loginData.c = loginLightData.a;
                                loginData.E = loginLightData.b;
                                if (loginLightData.c != null) {
                                    loginData.B = loginLightData.c;
                                }
                            }
                            if (oAuthData != null) {
                                SmoneyApplication.c.a(oAuthData);
                                break;
                            }
                        }
                        break;
                    case 234:
                        smoneyRequestManager.f.f = (P2PPayData) bundle.getParcelable("fr.smoney.android.izly.extras.p2pPayData");
                        smoneyRequestManager.f.g = (ServerError) bundle.getParcelable("fr.smoney.android.izly.extras.serverError");
                        break;
                    case 235:
                        smoneyRequestManager.f.h = (P2PPayConfirmData) bundle.getParcelable("fr.smoney.android.izly.extras.p2pPayConfirmData");
                        smoneyRequestManager.f.i = (ServerError) bundle.getParcelable("fr.smoney.android.izly.extras.serverError");
                        if (smoneyRequestManager.f.i == null && smoneyRequestManager.f.h != null) {
                            if (smoneyRequestManager.f.h.c != null) {
                                smoneyRequestManager.f.b.c = smoneyRequestManager.f.h.c;
                            }
                            smoneyRequestManager.f.cs = true;
                            smoneyRequestManager.f.ct = true;
                            smoneyRequestManager.f.cx = true;
                            ca.a(smoneyRequestManager.c);
                            break;
                        }
                    case 236:
                        smoneyRequestManager.f.bL = (PreAuthorizationContainerData) bundle.getParcelable("fr.smoney.android.izly.extras.RECEIVER_EXTRA_PRE_AUTHORIZATION_DATA");
                        smoneyRequestManager.f.bM = (ServerError) bundle.getParcelable("fr.smoney.android.izly.extras.serverError");
                        break;
                    case 237:
                        smoneyRequestManager.f.bN = (PreAuthorizationContainerData) bundle.getParcelable("fr.smoney.android.izly.extras.RECEIVER_EXTRA_PRE_AUTHORIZATION_DATA");
                        smoneyRequestManager.f.bO = (ServerError) bundle.getParcelable("fr.smoney.android.izly.extras.serverError");
                        if (smoneyRequestManager.f.bO == null && smoneyRequestManager.f.bN != null) {
                            smoneyRequestManager.f.cs = true;
                            smoneyRequestManager.f.ct = true;
                            smoneyRequestManager.f.cx = true;
                            ca.a(smoneyRequestManager.c);
                            break;
                        }
                    case 238:
                        smoneyRequestManager.f.bP = (PreAuthorizationContainerData) bundle.getParcelable("fr.smoney.android.izly.extras.RECEIVER_EXTRA_PRE_AUTHORIZATION_DATA");
                        smoneyRequestManager.f.bQ = (ServerError) bundle.getParcelable("fr.smoney.android.izly.extras.serverError");
                        smoneyRequestManager.f.bv = null;
                        smoneyRequestManager.f.cs = true;
                        smoneyRequestManager.f.ct = true;
                        smoneyRequestManager.f.cx = true;
                        ca.a(smoneyRequestManager.c);
                        break;
                    case 241:
                        smoneyRequestManager.f.v = (ServerError) bundle.getParcelable("fr.smoney.android.izly.extras.serverError");
                        break;
                    case 251:
                        smoneyRequestManager.f.bF = (GetMySupportListData) bundle.getParcelable("fr.smoney.android.izly.extras.GetMySupportList");
                        smoneyRequestManager.f.bG = (ServerError) bundle.getParcelable("fr.smoney.android.izly.extras.serverError");
                        break;
                    case 252:
                        smoneyRequestManager.f.bI = (ServerError) bundle.getParcelable("fr.smoney.android.izly.extras.serverError");
                        break;
                    case 253:
                        smoneyRequestManager.f.bH = (ServerError) bundle.getParcelable("fr.smoney.android.izly.extras.serverError");
                        break;
                    case 261:
                        smoneyRequestManager.f.bB = (CounterListData) bundle.getParcelable("fr.smoney.android.izly.extras.GetMyCounterList");
                        smoneyRequestManager.f.bC = (ServerError) bundle.getParcelable("fr.smoney.android.izly.extras.serverError");
                        break;
                    case 262:
                        smoneyRequestManager.f.bD = (CounterData) bundle.getParcelable("fr.smoney.android.izly.extras.GetMyCounterDetail");
                        smoneyRequestManager.f.bE = (ServerError) bundle.getParcelable("fr.smoney.android.izly.extras.serverError");
                        break;
                    case 263:
                        smoneyRequestManager.f.bR = (CardPaymentsData) bundle.getParcelable("fr.smoney.android.izly.extras.cardPayments");
                        smoneyRequestManager.f.bS = (ServerError) bundle.getParcelable("fr.smoney.android.izly.extras.serverError");
                        break;
                    case 264:
                        smoneyRequestManager.f.bT = (GetActiveMandateData) bundle.getParcelable("fr.smoney.android.izly.extras.GetActiveMandate");
                        smoneyRequestManager.f.bU = (ServerError) bundle.getParcelable("fr.smoney.android.izly.extras.serverError");
                        break;
                    case 265:
                        smoneyRequestManager.f.bV = (CheckMoneyInBankAccountData) bundle.getParcelable("fr.smoney.android.izly.extras.CheckMoneyInBankAccount");
                        smoneyRequestManager.f.bW = (ServerError) bundle.getParcelable("fr.smoney.android.izly.extras.serverError");
                        break;
                    case 266:
                        smoneyRequestManager.f.bX = (MakeMoneyInBankAccountData) bundle.getParcelable("fr.smoney.android.izly.extras.MakeMoneyInBankAccountData");
                        smoneyRequestManager.f.bY = (ServerError) bundle.getParcelable("fr.smoney.android.izly.extras.serverError");
                        break;
                    case 267:
                        smoneyRequestManager.f.e = (ServerError) bundle.getParcelable("fr.smoney.android.izly.extras.serverError");
                        break;
                    case 268:
                        smoneyRequestManager.f.ca = (GetCrousContactListData) bundle.getParcelable("fr.smoney.android.izly.extras.GetCrousContactListData");
                        smoneyRequestManager.f.cb = (ServerError) bundle.getParcelable("fr.smoney.android.izly.extras.serverError");
                        break;
                    case 269:
                        smoneyRequestManager.f.cc = (ServerError) bundle.getParcelable("fr.smoney.android.izly.extras.serverError");
                        break;
                    case 270:
                        smoneyRequestManager.f.cd = (GoodDealsArray) bundle.getParcelable("fr.smoney.android.izly.extras.GoodDealsData");
                        smoneyRequestManager.f.ce = (ServerError) bundle.getParcelable("fr.smoney.android.izly.extras.serverError");
                        break;
                    case 271:
                        smoneyRequestManager.f.cf = (DealsData) bundle.getParcelable("fr.smoney.android.izly.extra.DealsData");
                        smoneyRequestManager.f.ce = (ServerError) bundle.getParcelable("fr.smoney.android.izly.extras.serverError");
                        break;
                    case 272:
                        smoneyRequestManager.f.cg = (PostDealsCodeResponse) bundle.getParcelable("fr.smoney.android.izly.extra.PostDealsData");
                        smoneyRequestManager.f.ce = (ServerError) bundle.getParcelable("fr.smoney.android.izly.extras.serverError");
                        break;
                    case 273:
                        smoneyRequestManager.f.ch = (GetBilendiBannerData) bundle.getParcelable("fr.smoney.android.izly.extra.BilendiBannerData");
                        smoneyRequestManager.f.ci = (ServerError) bundle.getParcelable("fr.smoney.android.izly.extras.serverError");
                        break;
                    case 274:
                        smoneyRequestManager.f.cj = (GetUserActivationData) bundle.getParcelable("fr.smoney.android.izly.extras.GetUserActivationData");
                        smoneyRequestManager.f.ck = (ServerError) bundle.getParcelable("fr.smoney.android.izly.extras.serverError");
                        break;
                    case 275:
                        smoneyRequestManager.f.cl = (CheckUserActivationData) bundle.getParcelable("fr.smoney.android.izly.extras.CheckUserActivationData");
                        smoneyRequestManager.f.cm = (ServerError) bundle.getParcelable("fr.smoney.android.izly.extras.serverError");
                        break;
                    case 276:
                        smoneyRequestManager.f.cn = (ActivateUserData) bundle.getParcelable("fr.smoney.android.izly.extras.ActivateUserData");
                        smoneyRequestManager.f.co = (ServerError) bundle.getParcelable("fr.smoney.android.izly.extras.serverError");
                        break;
                    case 277:
                        smoneyRequestManager.f.cq = (AssociatePhoneNumberData) bundle.getParcelable("fr.smoney.android.izly.extras.AssociatePhoneNumberData");
                        smoneyRequestManager.f.cr = (ServerError) bundle.getParcelable("fr.smoney.android.izly.extras.serverError");
                        break;
                    case 9923:
                        smoneyRequestManager.f.bo = bundle.getInt("fr.smoney.android.izly.extras.NbNewPromoOffers");
                        smoneyRequestManager.f.bp = (ServerError) bundle.getParcelable("fr.smoney.android.izly.extras.serverError");
                        break;
                }
            } else if (i == -1) {
                if (bundle.getInt("com.foxykeep.datadroid.extras.error") == 1) {
                    intent = new Intent("fr.smoney.android.izly.notifications.NOTIFICATION_SESSION_STATE_CHANGE_INTENT_URI");
                    intent.putExtra("fr.smoney.android.izly.sessionState", 2);
                    smoneyRequestManager.c.sendBroadcast(intent);
                }
            }
            smoneyRequestManager.b.remove(i3);
            synchronized (smoneyRequestManager.d) {
                while (i2 < smoneyRequestManager.d.size()) {
                    ((SmoneyRequestManager$a) smoneyRequestManager.d.get(i2)).a_(i3, intExtra, i, bundle);
                    i2++;
                }
            }
        }
    }
}
