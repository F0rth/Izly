package org.spongycastle.asn1.eac;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.SimpleTimeZone;
import org.spongycastle.crypto.tls.CipherSuite;
import org.spongycastle.util.Arrays;

public class PackedDate {
    private byte[] time;

    public PackedDate(String str) {
        this.time = convert(str);
    }

    public PackedDate(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyMMdd'Z'");
        simpleDateFormat.setTimeZone(new SimpleTimeZone(0, "Z"));
        this.time = convert(simpleDateFormat.format(date));
    }

    PackedDate(byte[] bArr) {
        this.time = bArr;
    }

    private byte[] convert(String str) {
        char[] toCharArray = str.toCharArray();
        byte[] bArr = new byte[6];
        for (int i = 0; i != 6; i++) {
            bArr[i] = (byte) (toCharArray[i] - 48);
        }
        return bArr;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof PackedDate)) {
            return false;
        }
        return Arrays.areEqual(this.time, ((PackedDate) obj).time);
    }

    public Date getDate() throws ParseException {
        return new SimpleDateFormat("yyyyMMdd").parse("20" + toString());
    }

    public byte[] getEncoding() {
        return this.time;
    }

    public int hashCode() {
        return Arrays.hashCode(this.time);
    }

    public String toString() {
        char[] cArr = new char[this.time.length];
        for (int i = 0; i != cArr.length; i++) {
            cArr[i] = (char) ((this.time[i] & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV) + 48);
        }
        return new String(cArr);
    }
}
