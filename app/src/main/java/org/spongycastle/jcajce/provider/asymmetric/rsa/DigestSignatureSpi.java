package org.spongycastle.jcajce.provider.asymmetric.rsa;

import java.io.IOException;
import java.security.AlgorithmParameters;
import java.security.InvalidKeyException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SignatureException;
import java.security.SignatureSpi;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.AlgorithmParameterSpec;
import org.spongycastle.asn1.ASN1Encoding;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.DERNull;
import org.spongycastle.asn1.nist.NISTObjectIdentifiers;
import org.spongycastle.asn1.oiw.OIWObjectIdentifiers;
import org.spongycastle.asn1.pkcs.PKCSObjectIdentifiers;
import org.spongycastle.asn1.teletrust.TeleTrusTObjectIdentifiers;
import org.spongycastle.asn1.x509.AlgorithmIdentifier;
import org.spongycastle.asn1.x509.DigestInfo;
import org.spongycastle.crypto.AsymmetricBlockCipher;
import org.spongycastle.crypto.CipherParameters;
import org.spongycastle.crypto.Digest;
import org.spongycastle.crypto.digests.MD2Digest;
import org.spongycastle.crypto.digests.MD4Digest;
import org.spongycastle.crypto.digests.MD5Digest;
import org.spongycastle.crypto.digests.NullDigest;
import org.spongycastle.crypto.digests.RIPEMD128Digest;
import org.spongycastle.crypto.digests.RIPEMD160Digest;
import org.spongycastle.crypto.digests.RIPEMD256Digest;
import org.spongycastle.crypto.digests.SHA1Digest;
import org.spongycastle.crypto.digests.SHA224Digest;
import org.spongycastle.crypto.digests.SHA256Digest;
import org.spongycastle.crypto.digests.SHA384Digest;
import org.spongycastle.crypto.digests.SHA512Digest;
import org.spongycastle.crypto.encodings.PKCS1Encoding;
import org.spongycastle.crypto.engines.RSABlindedEngine;

public class DigestSignatureSpi extends SignatureSpi {
    private AlgorithmIdentifier algId;
    private AsymmetricBlockCipher cipher;
    private Digest digest;

    public static class MD2 extends DigestSignatureSpi {
        public MD2() {
            super(PKCSObjectIdentifiers.md2, new MD2Digest(), new PKCS1Encoding(new RSABlindedEngine()));
        }
    }

    public static class MD4 extends DigestSignatureSpi {
        public MD4() {
            super(PKCSObjectIdentifiers.md4, new MD4Digest(), new PKCS1Encoding(new RSABlindedEngine()));
        }
    }

    public static class MD5 extends DigestSignatureSpi {
        public MD5() {
            super(PKCSObjectIdentifiers.md5, new MD5Digest(), new PKCS1Encoding(new RSABlindedEngine()));
        }
    }

    public static class RIPEMD128 extends DigestSignatureSpi {
        public RIPEMD128() {
            super(TeleTrusTObjectIdentifiers.ripemd128, new RIPEMD128Digest(), new PKCS1Encoding(new RSABlindedEngine()));
        }
    }

    public static class RIPEMD160 extends DigestSignatureSpi {
        public RIPEMD160() {
            super(TeleTrusTObjectIdentifiers.ripemd160, new RIPEMD160Digest(), new PKCS1Encoding(new RSABlindedEngine()));
        }
    }

    public static class RIPEMD256 extends DigestSignatureSpi {
        public RIPEMD256() {
            super(TeleTrusTObjectIdentifiers.ripemd256, new RIPEMD256Digest(), new PKCS1Encoding(new RSABlindedEngine()));
        }
    }

    public static class SHA1 extends DigestSignatureSpi {
        public SHA1() {
            super(OIWObjectIdentifiers.idSHA1, new SHA1Digest(), new PKCS1Encoding(new RSABlindedEngine()));
        }
    }

    public static class SHA224 extends DigestSignatureSpi {
        public SHA224() {
            super(NISTObjectIdentifiers.id_sha224, new SHA224Digest(), new PKCS1Encoding(new RSABlindedEngine()));
        }
    }

    public static class SHA256 extends DigestSignatureSpi {
        public SHA256() {
            super(NISTObjectIdentifiers.id_sha256, new SHA256Digest(), new PKCS1Encoding(new RSABlindedEngine()));
        }
    }

    public static class SHA384 extends DigestSignatureSpi {
        public SHA384() {
            super(NISTObjectIdentifiers.id_sha384, new SHA384Digest(), new PKCS1Encoding(new RSABlindedEngine()));
        }
    }

    public static class SHA512 extends DigestSignatureSpi {
        public SHA512() {
            super(NISTObjectIdentifiers.id_sha512, new SHA512Digest(), new PKCS1Encoding(new RSABlindedEngine()));
        }
    }

    public static class noneRSA extends DigestSignatureSpi {
        public noneRSA() {
            super(new NullDigest(), new PKCS1Encoding(new RSABlindedEngine()));
        }
    }

    protected DigestSignatureSpi(ASN1ObjectIdentifier aSN1ObjectIdentifier, Digest digest, AsymmetricBlockCipher asymmetricBlockCipher) {
        this.digest = digest;
        this.cipher = asymmetricBlockCipher;
        this.algId = new AlgorithmIdentifier(aSN1ObjectIdentifier, DERNull.INSTANCE);
    }

    protected DigestSignatureSpi(Digest digest, AsymmetricBlockCipher asymmetricBlockCipher) {
        this.digest = digest;
        this.cipher = asymmetricBlockCipher;
        this.algId = null;
    }

    private byte[] derEncode(byte[] bArr) throws IOException {
        return this.algId == null ? bArr : new DigestInfo(this.algId, bArr).getEncoded(ASN1Encoding.DER);
    }

    private String getType(Object obj) {
        return obj == null ? null : obj.getClass().getName();
    }

    protected Object engineGetParameter(String str) {
        return null;
    }

    protected AlgorithmParameters engineGetParameters() {
        return null;
    }

    protected void engineInitSign(PrivateKey privateKey) throws InvalidKeyException {
        if (privateKey instanceof RSAPrivateKey) {
            CipherParameters generatePrivateKeyParameter = RSAUtil.generatePrivateKeyParameter((RSAPrivateKey) privateKey);
            this.digest.reset();
            this.cipher.init(true, generatePrivateKeyParameter);
            return;
        }
        throw new InvalidKeyException("Supplied key (" + getType(privateKey) + ") is not a RSAPrivateKey instance");
    }

    protected void engineInitVerify(PublicKey publicKey) throws InvalidKeyException {
        if (publicKey instanceof RSAPublicKey) {
            CipherParameters generatePublicKeyParameter = RSAUtil.generatePublicKeyParameter((RSAPublicKey) publicKey);
            this.digest.reset();
            this.cipher.init(false, generatePublicKeyParameter);
            return;
        }
        throw new InvalidKeyException("Supplied key (" + getType(publicKey) + ") is not a RSAPublicKey instance");
    }

    protected void engineSetParameter(String str, Object obj) {
        throw new UnsupportedOperationException("engineSetParameter unsupported");
    }

    protected void engineSetParameter(AlgorithmParameterSpec algorithmParameterSpec) {
        throw new UnsupportedOperationException("engineSetParameter unsupported");
    }

    protected byte[] engineSign() throws SignatureException {
        byte[] bArr = new byte[this.digest.getDigestSize()];
        this.digest.doFinal(bArr, 0);
        try {
            bArr = derEncode(bArr);
            return this.cipher.processBlock(bArr, 0, bArr.length);
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new SignatureException("key too small for signature type");
        } catch (Exception e2) {
            throw new SignatureException(e2.toString());
        }
    }

    protected void engineUpdate(byte b) throws SignatureException {
        this.digest.update(b);
    }

    protected void engineUpdate(byte[] bArr, int i, int i2) throws SignatureException {
        this.digest.update(bArr, i, i2);
    }

    protected boolean engineVerify(byte[] bArr) throws SignatureException {
        byte[] bArr2 = new byte[this.digest.getDigestSize()];
        this.digest.doFinal(bArr2, 0);
        try {
            byte[] processBlock = this.cipher.processBlock(bArr, 0, bArr.length);
            byte[] derEncode = derEncode(bArr2);
            int i;
            if (processBlock.length == derEncode.length) {
                for (i = 0; i < processBlock.length; i++) {
                    if (processBlock[i] != derEncode[i]) {
                        return false;
                    }
                }
            } else if (processBlock.length != derEncode.length - 2) {
                return false;
            } else {
                int length = (processBlock.length - bArr2.length) - 2;
                int length2 = derEncode.length;
                int length3 = bArr2.length;
                derEncode[1] = (byte) (derEncode[1] - 2);
                derEncode[3] = (byte) (derEncode[3] - 2);
                for (i = 0; i < bArr2.length; i++) {
                    if (processBlock[length + i] != derEncode[((length2 - length3) - 2) + i]) {
                        return false;
                    }
                }
                for (i = 0; i < length; i++) {
                    if (processBlock[i] != derEncode[i]) {
                        return false;
                    }
                }
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
