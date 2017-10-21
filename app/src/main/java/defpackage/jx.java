package defpackage;

final class jx<Result> extends lh<Void, Void, Result> {
    final jy<Result> a;

    public jx(jy<Result> jyVar) {
        this.a = jyVar;
    }

    private lb a(String str) {
        lb lbVar = new lb(this.a.getIdentifier() + "." + str, "KitInitialization");
        lbVar.a();
        return lbVar;
    }

    protected final /* synthetic */ Object a(Object[] objArr) {
        lb a = a("doInBackground");
        Object obj = null;
        if (!this.e.get()) {
            obj = this.a.doInBackground();
        }
        a.b();
        return obj;
    }

    protected final void a() {
        super.a();
        lb a = a("onPreExecute");
        try {
            boolean onPreExecute = this.a.onPreExecute();
            a.b();
            if (!onPreExecute) {
                a(true);
            }
        } catch (lo e) {
            throw e;
        } catch (Throwable e2) {
            js.a().c("Fabric", "Failure onPreExecute()", e2);
            a.b();
            a(true);
        } catch (Throwable th) {
            a.b();
            a(true);
        }
    }

    protected final void a(Result result) {
        this.a.onPostExecute(result);
        this.a.initializationCallback.a();
    }

    protected final void b(Result result) {
        this.a.onCancelled(result);
        this.a.initializationCallback.a(new jw(this.a.getIdentifier() + " Initialization was cancelled"));
    }

    public final int getPriority$16699175() {
        return lg.c;
    }
}
