package okhttp3.internal;

import defpackage.nw;
import defpackage.ob;
import defpackage.om;
import java.io.IOException;

class FaultHidingSink extends ob {
    private boolean hasErrors;

    public FaultHidingSink(om omVar) {
        super(omVar);
    }

    public void close() throws IOException {
        if (!this.hasErrors) {
            try {
                super.close();
            } catch (IOException e) {
                this.hasErrors = true;
                onException(e);
            }
        }
    }

    public void flush() throws IOException {
        if (!this.hasErrors) {
            try {
                super.flush();
            } catch (IOException e) {
                this.hasErrors = true;
                onException(e);
            }
        }
    }

    protected void onException(IOException iOException) {
    }

    public void write(nw nwVar, long j) throws IOException {
        if (this.hasErrors) {
            nwVar.g(j);
            return;
        }
        try {
            super.write(nwVar, j);
        } catch (IOException e) {
            this.hasErrors = true;
            onException(e);
        }
    }
}
