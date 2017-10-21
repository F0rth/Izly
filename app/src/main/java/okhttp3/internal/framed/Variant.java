package okhttp3.internal.framed;

import defpackage.nx;
import defpackage.ny;
import okhttp3.Protocol;

public interface Variant {
    Protocol getProtocol();

    FrameReader newReader(ny nyVar, boolean z);

    FrameWriter newWriter(nx nxVar, boolean z);
}
