package okhttp3.internal.framed;

import defpackage.nw;
import defpackage.ny;
import defpackage.nz;
import defpackage.oc;
import defpackage.of;
import defpackage.og;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.DataFormatException;
import java.util.zip.Inflater;
import org.spongycastle.asn1.cmp.PKIFailureInfo;

class NameValueBlockReader {
    private int compressedLimit;
    private final of inflaterSource;
    private final ny source = og.a(this.inflaterSource);

    public NameValueBlockReader(ny nyVar) {
        this.inflaterSource = new of(new oc(nyVar) {
            public long read(nw nwVar, long j) throws IOException {
                if (NameValueBlockReader.this.compressedLimit == 0) {
                    return -1;
                }
                long read = super.read(nwVar, Math.min(j, (long) NameValueBlockReader.this.compressedLimit));
                if (read == -1) {
                    return -1;
                }
                NameValueBlockReader.this.compressedLimit = (int) (((long) NameValueBlockReader.this.compressedLimit) - read);
                return read;
            }
        }, new Inflater() {
            public int inflate(byte[] bArr, int i, int i2) throws DataFormatException {
                int inflate = super.inflate(bArr, i, i2);
                if (inflate != 0 || !needsDictionary()) {
                    return inflate;
                }
                setDictionary(Spdy3.DICTIONARY);
                return super.inflate(bArr, i, i2);
            }
        });
    }

    private void doneReading() throws IOException {
        if (this.compressedLimit > 0) {
            this.inflaterSource.a();
            if (this.compressedLimit != 0) {
                throw new IOException("compressedLimit > 0: " + this.compressedLimit);
            }
        }
    }

    private nz readByteString() throws IOException {
        return this.source.d((long) this.source.h());
    }

    public void close() throws IOException {
        this.source.close();
    }

    public List<Header> readNameValueBlock(int i) throws IOException {
        this.compressedLimit += i;
        int h = this.source.h();
        if (h < 0) {
            throw new IOException("numberOfPairs < 0: " + h);
        } else if (h > PKIFailureInfo.badRecipientNonce) {
            throw new IOException("numberOfPairs > 1024: " + h);
        } else {
            List<Header> arrayList = new ArrayList(h);
            for (int i2 = 0; i2 < h; i2++) {
                nz d = readByteString().d();
                nz readByteString = readByteString();
                if (d.e() == 0) {
                    throw new IOException("name.size == 0");
                }
                arrayList.add(new Header(d, readByteString));
            }
            doneReading();
            return arrayList;
        }
    }
}
