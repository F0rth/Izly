package defpackage;

public enum ch {
    CB(2130837970, 2131230878, 2131230861, 2131230877),
    SEPA(2130837994, 2131231722, 2131231716, 2131231718),
    TIERS(2130838000, 2131231839, 2131231836, 2131231838),
    CASH(2130837962, 2131230858, 2131230857, 2131230857);
    
    public int e;
    public int f;
    public int g;
    public int h;

    private ch(int i, int i2, int i3, int i4) {
        this.g = i3;
        this.e = i;
        this.f = i2;
        this.h = i4;
    }
}
