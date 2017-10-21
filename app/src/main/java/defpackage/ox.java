package defpackage;

public enum ox {
    Top(0),
    Right(-90),
    Bottom(180),
    Left(90);
    
    final int e;

    private ox(int i) {
        this.e = i;
    }
}
