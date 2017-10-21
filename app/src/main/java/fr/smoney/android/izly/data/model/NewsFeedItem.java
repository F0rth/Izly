package fr.smoney.android.izly.data.model;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.SparseArray;

import fr.smoney.android.izly.R;

import java.util.Locale;

@SuppressLint({"ParcelCreator"})
public abstract class NewsFeedItem implements Parcelable {
    private static final SparseArray<a> a;
    public int b;
    public long c;
    public a d;
    public NewsFeedContactLight e;
    public long f;

    public enum a {
        P2PCredit(0),
        P2PDebit(1),
        MoneyIn(2),
        MoneyOut(3),
        ToPayRequest(4),
        MoneyDemandeRequest(5),
        PaymentRefund(6),
        PaymentEcommerce(7),
        PaymentDistributor(8),
        Commission(9),
        MoneyInRefund(10),
        MoneyOutRefund(11),
        Chat(12),
        Promo(13),
        EcommerceRefund(14),
        CommissionRefund(15),
        PaymentAuthorization(16),
        AchatPass(17),
        PayInCard(18),
        PayInCardRefund(19),
        MoneyInCash(20),
        MoneyInCashRefund(21),
        MoneyInVme(27);

        public int x;

        private a(int i) {
            this.x = i;
        }
    }

    static {
        SparseArray sparseArray = new SparseArray();
        for (a aVar : a.values()) {
            sparseArray.append(aVar.x, aVar);
        }
        a = sparseArray;
    }

    public NewsFeedItem() {
        this.b = a();
    }

    public NewsFeedItem(Parcel parcel) {
        this();
        a(parcel);
    }

    protected static String a(Context context, String str, double d) {
        if (d < 0.0d) {
            return null;
        }
        Object[] objArr = new Object[1];
        objArr[0] = String.format(Locale.getDefault(), "%1$.2f%2$s", new Object[]{Double.valueOf(d), str});
        return context.getString(R.string.home_news_feed_price_part, objArr);
    }

    public static a b(int i) {
        return (a) a.get(i);
    }

    protected abstract int a();

    public void a(Parcel parcel) {
        this.c = parcel.readLong();
        this.d = (a) parcel.readSerializable();
        this.f = parcel.readLong();
        this.e = (NewsFeedContactLight) parcel.readParcelable(NewsFeedChatItem.class.getClassLoader());
    }

    public final boolean b() {
        return this.d == a.Promo;
    }

    public final boolean c() {
        switch (this.d) {
            case P2PCredit:
            case P2PDebit:
            case MoneyIn:
            case MoneyOut:
            case PaymentDistributor:
            case MoneyOutRefund:
            case Chat:
            case PaymentRefund:
            case PaymentEcommerce:
            case MoneyInRefund:
            case EcommerceRefund:
            case Commission:
            case CommissionRefund:
            case AchatPass:
            case PayInCard:
            case PayInCardRefund:
            case MoneyInCash:
            case MoneyInCashRefund:
            case MoneyInVme:
                return true;
            default:
                return false;
        }
    }

    public final boolean d() {
        return this.d == a.ToPayRequest;
    }

    public int describeContents() {
        return 0;
    }

    public final boolean e() {
        return this.d == a.MoneyDemandeRequest;
    }

    public final boolean f() {
        return this.d == a.PaymentAuthorization;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(this.c);
        parcel.writeSerializable(this.d);
        parcel.writeLong(this.f);
        parcel.writeParcelable(this.e, i);
    }
}
