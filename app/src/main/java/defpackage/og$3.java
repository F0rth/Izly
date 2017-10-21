package defpackage;

import java.io.IOException;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.logging.Level;

final class og$3 extends nu {
    final /* synthetic */ Socket a;

    og$3(Socket socket) {
        this.a = socket;
    }

    protected final IOException newTimeoutException(IOException iOException) {
        IOException socketTimeoutException = new SocketTimeoutException("timeout");
        if (iOException != null) {
            socketTimeoutException.initCause(iOException);
        }
        return socketTimeoutException;
    }

    protected final void timedOut() {
        try {
            this.a.close();
        } catch (Throwable e) {
            og.a.log(Level.WARNING, "Failed to close timed out socket " + this.a, e);
        } catch (AssertionError e2) {
            if (og.a(e2)) {
                og.a.log(Level.WARNING, "Failed to close timed out socket " + this.a, e2);
                return;
            }
            throw e2;
        }
    }
}
