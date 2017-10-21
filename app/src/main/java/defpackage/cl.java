package defpackage;

import fr.smoney.android.izly.data.model.ActivateUserData;
import fr.smoney.android.izly.data.model.ActivationProcessData;
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
import fr.smoney.android.izly.data.model.NewsFeedItem;
import fr.smoney.android.izly.data.model.P2PGetListData;
import fr.smoney.android.izly.data.model.P2PGetMult;
import fr.smoney.android.izly.data.model.P2PGetMultConfirmData;
import fr.smoney.android.izly.data.model.P2PGetMultData;
import fr.smoney.android.izly.data.model.P2PPayConfirmData;
import fr.smoney.android.izly.data.model.P2PPayData;
import fr.smoney.android.izly.data.model.P2PPayRequest;
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

public final class cl {
    public MoneyOutTransferAccountData A;
    public ServerError B;
    public MoneyOutTransferData C;
    public ServerError D;
    public MoneyOutTransferConfirmData E;
    public ServerError F;
    public P2PGetListData G;
    public ServerError H;
    public long I;
    public ServerError J;
    public long K;
    public ServerError L;
    public P2PGetMultData M;
    public ServerError N;
    public P2PGetMultConfirmData O;
    public ServerError P;
    public P2PPayRequestListData Q;
    public ServerError R;
    public long S;
    public ServerError T;
    public P2PPayRequestData U;
    public ServerError V;
    public P2PPayRequestConfirmData W;
    public ServerError X;
    public MoneyInCbAndPayRequestData Y;
    public ServerError Z;
    public String a;
    public GetAttachmentData aA;
    public ServerError aB;
    public DeleteBankAccountData aC;
    public ServerError aD;
    public GetNewEventNumberData aE;
    public ServerError aF;
    public UpdatePasswordData aG;
    public ServerError aH;
    public GetConfidentialitySettingsData aI;
    public ServerError aJ;
    public SetConfidentialitySettingsData aK;
    public ServerError aL;
    public UpdateUserProfileData aM;
    public ServerError aN;
    public UpdateUserPictureData aO;
    public ServerError aP;
    public BlockAccountData aQ;
    public ServerError aR;
    public InitiatePasswordRecoveryData aS;
    public ServerError aT;
    public ChooseDefaultCbData aU;
    public ServerError aV;
    public GetNearProListData aW;
    public ServerError aX;
    public DeleteUserPictureData aY;
    public ServerError aZ;
    public MoneyInCbAndPayRequestConfirmData aa;
    public ServerError ab;
    public CbChangeAliasData ac;
    public ServerError ad;
    public CbDeleteData ae;
    public ServerError af;
    public MakeBankAccountUpdateData ag;
    public ServerError ah;
    public GetContactDetailsData ai;
    public ServerError aj;
    public GetContactDetailsData ak;
    public ServerError al;
    public AddBookmarkData am;
    public ServerError an;
    public GetBookmarksData ao;
    public ServerError ap;
    public GetSecretQuestionData aq;
    public ServerError ar;
    public SecretQuestionAnswer as;
    public ServerError at;
    public SetDeviceTokenData au;
    public ServerError av;
    public RemoveDeviceTokenData aw;
    public ServerError ax;
    public SendAttachmentData ay;
    public ServerError az;
    public LoginData b;
    public ServerError bA;
    public CounterListData bB;
    public ServerError bC;
    public CounterData bD;
    public ServerError bE;
    public GetMySupportListData bF;
    public ServerError bG;
    public ServerError bH;
    public ServerError bI;
    public LoginLightData bJ;
    public ServerError bK;
    public PreAuthorizationContainerData bL;
    public ServerError bM;
    public PreAuthorizationContainerData bN;
    public ServerError bO;
    public PreAuthorizationContainerData bP;
    public ServerError bQ;
    public CardPaymentsData bR;
    public ServerError bS;
    public GetActiveMandateData bT;
    public ServerError bU;
    public CheckMoneyInBankAccountData bV;
    public ServerError bW;
    public MakeMoneyInBankAccountData bX;
    public ServerError bY;
    public ServerError bZ;
    public GetProCashingModelsData ba;
    public ServerError bb;
    public CheckMoneyDemandForProData bc;
    public ServerError bd;
    public MakeMoneyDemandForProData be;
    public ServerError bf;
    public GetTaxListData bg;
    public ServerError bh;
    public IsEnrollmentOpenData bi;
    public ServerError bj;
    public MakeMoneyDemandRelaunchData bk;
    public ServerError bl;
    public ServerError bm;
    public ServerError bn;
    public int bo;
    public ServerError bp;
    public GetContactDetailsData bq;
    public ServerError br;
    public ServerError bs;
    public GetBankAccountData bt;
    public ServerError bu;
    public GetNewsFeedData bv;
    public ServerError bw;
    public GetNewsFeedDetailsData bx;
    public ServerError by;
    public GetMyCbListData bz;
    public UserData c;
    public GetCrousContactListData ca;
    public ServerError cb;
    public ServerError cc;
    public GoodDealsArray cd;
    public ServerError ce;
    public DealsData cf;
    public PostDealsCodeResponse cg;
    public GetBilendiBannerData ch;
    public ServerError ci;
    public GetUserActivationData cj;
    public ServerError ck;
    public CheckUserActivationData cl;
    public ServerError cm;
    public ActivateUserData cn;
    public ServerError co;
    public ActivationProcessData cp;
    public AssociatePhoneNumberData cq;
    public ServerError cr;
    public boolean cs = false;
    public boolean ct = false;
    public boolean cu = false;
    public boolean cv = false;
    public boolean cw = false;
    public boolean cx = false;
    public boolean cy = false;
    public boolean cz = false;
    public ServerError d;
    public ServerError e;
    public P2PPayData f;
    public ServerError g;
    public P2PPayConfirmData h;
    public ServerError i;
    public MoneyInCbAndPayData j;
    public ServerError k;
    public MoneyInCbAndPayConfirmData l;
    public ServerError m;
    public TransactionListData n;
    public ServerError o;
    public SendChatMessageData p;
    public ServerError q;
    public MoneyInCbCbListData r;
    public ServerError s;
    public MoneyInCbData t;
    public ServerError u;
    public ServerError v;
    public MoneyInCbConfirmData w;
    public ServerError x;
    public UserData y;
    public ServerError z;

    private void c(long j) {
        if (this.bv != null) {
            int size = this.bv.f.size();
            for (int i = 0; i < size; i++) {
                if (((NewsFeedItem) this.bv.f.get(i)).c == j) {
                    this.bv.f.remove(i);
                    return;
                }
            }
        }
    }

    public final void a() {
        this.n = null;
        this.o = null;
        this.G = null;
        this.H = null;
        this.Q = null;
        this.R = null;
        this.ct = true;
        this.cu = true;
        this.cv = true;
    }

    public final void a(long j) {
        if (this.Q != null) {
            int size = this.Q.a.size();
            for (int i = 0; i < size; i++) {
                if (((P2PPayRequest) this.Q.a.get(i)).a == j) {
                    this.Q.a.remove(i);
                    P2PPayRequestListData p2PPayRequestListData = this.Q;
                    p2PPayRequestListData.c--;
                    break;
                }
            }
        }
        c(j);
    }

    public final void b(long j) {
        if (this.G != null) {
            int size = this.G.a.size();
            for (int i = 0; i < size; i++) {
                if (((P2PGetMult) this.G.a.get(i)).c == j) {
                    this.G.a.remove(i);
                    P2PGetListData p2PGetListData = this.G;
                    p2PGetListData.d--;
                    break;
                }
            }
        }
        c(j);
    }
}
