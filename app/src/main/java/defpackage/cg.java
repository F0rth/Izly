package defpackage;

public enum cg {
    Cadeau(2130838253, 2131231521),
    BarRestau(2130838252, 2131231520),
    SortieWeekEnd(2130838258, 2131231527),
    Voyage(2130838259, 2131231530),
    EnterrementVie(2130838255, 2131231523),
    PotDepart(2130838256, 2131231525),
    Colocation(2130838254, 2131231522),
    Soiree(2130838257, 2131231526),
    AUTRE(0, 2131231457);
    
    public int j;
    public int k;

    private cg(int i, int i2) {
        this.j = i;
        this.k = i2;
    }
}
