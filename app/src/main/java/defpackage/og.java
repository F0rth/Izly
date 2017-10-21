package defpackage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.logging.Logger;

public final class og {
    static final Logger a = Logger.getLogger(og.class.getName());

    private og() {
    }

    public static nx a(om omVar) {
        if (omVar != null) {
            return new oh(omVar);
        }
        throw new IllegalArgumentException("sink == null");
    }

    public static ny a(on onVar) {
        if (onVar != null) {
            return new oi(onVar);
        }
        throw new IllegalArgumentException("source == null");
    }

    private static om a(OutputStream outputStream) {
        return og.a(outputStream, new oo());
    }

    private static om a(OutputStream outputStream, oo ooVar) {
        if (outputStream == null) {
            throw new IllegalArgumentException("out == null");
        } else if (ooVar != null) {
            return new og$1(ooVar, outputStream);
        } else {
            throw new IllegalArgumentException("timeout == null");
        }
    }

    public static om a(Socket socket) throws IOException {
        if (socket == null) {
            throw new IllegalArgumentException("socket == null");
        }
        oo c = og.c(socket);
        return c.sink(og.a(socket.getOutputStream(), c));
    }

    public static on a(File file) throws FileNotFoundException {
        if (file != null) {
            return og.a(new FileInputStream(file), new oo());
        }
        throw new IllegalArgumentException("file == null");
    }

    private static on a(InputStream inputStream, oo ooVar) {
        if (inputStream == null) {
            throw new IllegalArgumentException("in == null");
        } else if (ooVar != null) {
            return new og$2(ooVar, inputStream);
        } else {
            throw new IllegalArgumentException("timeout == null");
        }
    }

    static boolean a(AssertionError assertionError) {
        return (assertionError.getCause() == null || assertionError.getMessage() == null || !assertionError.getMessage().contains("getsockname failed")) ? false : true;
    }

    public static om b(File file) throws FileNotFoundException {
        if (file != null) {
            return og.a(new FileOutputStream(file));
        }
        throw new IllegalArgumentException("file == null");
    }

    public static on b(Socket socket) throws IOException {
        if (socket == null) {
            throw new IllegalArgumentException("socket == null");
        }
        oo c = og.c(socket);
        return c.source(og.a(socket.getInputStream(), c));
    }

    private static nu c(Socket socket) {
        return new og$3(socket);
    }

    public static om c(File file) throws FileNotFoundException {
        if (file != null) {
            return og.a(new FileOutputStream(file, true));
        }
        throw new IllegalArgumentException("file == null");
    }
}
