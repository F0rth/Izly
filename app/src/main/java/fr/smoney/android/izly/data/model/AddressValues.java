package fr.smoney.android.izly.data.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.util.SparseArray;

import fr.smoney.android.izly.R;

import java.util.EnumSet;
import java.util.Iterator;

public class AddressValues implements Parcelable {
    public static final Creator<AddressValues> CREATOR = new Creator<AddressValues>() {
        public final /* synthetic */ Object createFromParcel(Parcel parcel) {
            return new AddressValues(parcel);
        }

        public final /* bridge */ /* synthetic */ Object[] newArray(int i) {
            return new AddressValues[i];
        }
    };
    public String a;
    public String b;
    public String c;
    public a d;

    public enum a {
        France(R.string.country_france, 1),
        Allemagne(R.string.country_allemagne, 2),
        Autriche(R.string.country_autriche, 3),
        Belgique(R.string.country_belgique, 4),
        Bulgarie(R.string.country_bulgarie, 5),
        Chypre(R.string.country_chypre, 6),
        Danemark(R.string.country_danemark, 7),
        Espagne(R.string.country_espagne, 8),
        Estonie(R.string.country_estonie, 9),
        Finlande(R.string.country_finlande, 10),
        Grece(R.string.country_grece, 11),
        Hongrie(R.string.country_hongrie, 12),
        Irlande(R.string.country_irlande, 13),
        Islande(R.string.country_islande, 14),
        Italie(R.string.country_italie, 15),
        Lettonie(R.string.country_lettonie, 16),
        Liechtenstein(R.string.country_liechtenstein, 17),
        Lituanie(R.string.country_lituanie, 18),
        Luxembourg(R.string.country_luxembourg, 19),
        Malte(R.string.country_malte, 20),
        Norvege(R.string.country_norvege, 21),
        PaysBas(R.string.country_pays_bas, 22),
        Pologne(R.string.country_pologne, 23),
        Portugal(R.string.country_portugal, 24),
        RepubliqueTcheque(R.string.country_rep_tcheque, 25),
        Roumanie(R.string.country_roumanie, 26),
        RoyaumeUni(R.string.country_royaume_uni, 27),
        Slovaquie(R.string.country_slovaquie, 28),
        Slovenie(R.string.country_slovenie, 29),
        Suede(R.string.country_suede, 30),
        Other(R.string.country_other, 31);

        private static final SparseArray<a> H = null;
        public int F;
        public int G;

        static {
            H = new SparseArray();
            Iterator it = EnumSet.allOf(a.class).iterator();
            while (it.hasNext()) {
                a aVar = (a) it.next();
                H.put(aVar.F, aVar);
            }
        }

        private a(int i, int i2) {
            this.F = i2;
            this.G = i;
        }

        public static a a(int i) {
            return (a) H.get(i);
        }
    }

    public AddressValues(Parcel parcel) {
        this.a = parcel.readString();
        this.b = parcel.readString();
        this.c = parcel.readString();
        this.d = a.a(parcel.readInt());
    }

    public AddressValues(String str, String str2, String str3, a aVar) {
        this.a = str;
        this.b = str2;
        this.c = str3;
        this.d = aVar;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.a);
        parcel.writeString(this.b);
        parcel.writeString(this.c);
        parcel.writeInt(this.d.F);
    }
}
