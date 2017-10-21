package defpackage;

final class kj$1 extends ko {
    final /* synthetic */ ki a;
    final /* synthetic */ kj b;

    kj$1(kj kjVar, ki kiVar) {
        this.b = kjVar;
        this.a = kiVar;
    }

    public final void onRun() {
        ki b = this.b.b();
        if (!this.a.equals(b)) {
            js.a().a("Fabric", "Asychronously getting Advertising Info and storing it to preferences");
            this.b.a(b);
        }
    }
}
