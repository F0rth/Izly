package org.spongycastle.jcajce.provider.asymmetric.ec;

import java.io.IOException;
import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.ECPublicKey;
import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Encoding;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.DERInteger;
import org.spongycastle.asn1.DERSequence;
import org.spongycastle.asn1.x509.SubjectPublicKeyInfo;
import org.spongycastle.crypto.CipherParameters;
import org.spongycastle.crypto.DSA;
import org.spongycastle.crypto.Digest;
import org.spongycastle.crypto.digests.NullDigest;
import org.spongycastle.crypto.digests.RIPEMD160Digest;
import org.spongycastle.crypto.digests.SHA1Digest;
import org.spongycastle.crypto.digests.SHA224Digest;
import org.spongycastle.crypto.digests.SHA256Digest;
import org.spongycastle.crypto.digests.SHA384Digest;
import org.spongycastle.crypto.digests.SHA512Digest;
import org.spongycastle.crypto.params.ParametersWithRandom;
import org.spongycastle.crypto.signers.ECDSASigner;
import org.spongycastle.crypto.signers.ECNRSigner;
import org.spongycastle.jcajce.provider.asymmetric.util.DSABase;
import org.spongycastle.jcajce.provider.asymmetric.util.DSAEncoder;
import org.spongycastle.jce.interfaces.ECKey;
import org.spongycastle.jce.provider.BouncyCastleProvider;

public class SignatureSpi extends DSABase {

    static class CVCDSAEncoder implements DSAEncoder {
        private CVCDSAEncoder() {
        }

        private byte[] makeUnsigned(BigInteger bigInteger) {
            Object toByteArray = bigInteger.toByteArray();
            if (toByteArray[0] != (byte) 0) {
                return toByteArray;
            }
            Object obj = new byte[(toByteArray.length - 1)];
            System.arraycopy(toByteArray, 1, obj, 0, obj.length);
            return obj;
        }

        public BigInteger[] decode(byte[] bArr) throws IOException {
            Object obj = new byte[(bArr.length / 2)];
            Object obj2 = new byte[(bArr.length / 2)];
            System.arraycopy(bArr, 0, obj, 0, obj.length);
            System.arraycopy(bArr, obj.length, obj2, 0, obj2.length);
            return new BigInteger[]{new BigInteger(1, obj), new BigInteger(1, obj2)};
        }

        public byte[] encode(BigInteger bigInteger, BigInteger bigInteger2) throws IOException {
            Object makeUnsigned = makeUnsigned(bigInteger);
            Object makeUnsigned2 = makeUnsigned(bigInteger2);
            Object obj = makeUnsigned.length > makeUnsigned2.length ? new byte[(makeUnsigned.length * 2)] : new byte[(makeUnsigned2.length * 2)];
            System.arraycopy(makeUnsigned, 0, obj, (obj.length / 2) - makeUnsigned.length, makeUnsigned.length);
            System.arraycopy(makeUnsigned2, 0, obj, obj.length - makeUnsigned2.length, makeUnsigned2.length);
            return obj;
        }
    }

    static class StdDSAEncoder implements DSAEncoder {
        private StdDSAEncoder() {
        }

        public BigInteger[] decode(byte[] bArr) throws IOException {
            ASN1Sequence aSN1Sequence = (ASN1Sequence) ASN1Primitive.fromByteArray(bArr);
            return new BigInteger[]{((DERInteger) aSN1Sequence.getObjectAt(0)).getValue(), ((DERInteger) aSN1Sequence.getObjectAt(1)).getValue()};
        }

        public byte[] encode(BigInteger bigInteger, BigInteger bigInteger2) throws IOException {
            ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector();
            aSN1EncodableVector.add(new DERInteger(bigInteger));
            aSN1EncodableVector.add(new DERInteger(bigInteger2));
            return new DERSequence(aSN1EncodableVector).getEncoded(ASN1Encoding.DER);
        }
    }

    public static class ecCVCDSA224 extends SignatureSpi {
        public ecCVCDSA224() {
            super(new SHA224Digest(), new ECDSASigner(), new CVCDSAEncoder());
        }
    }

    public static class ecCVCDSA256 extends SignatureSpi {
        public ecCVCDSA256() {
            super(new SHA256Digest(), new ECDSASigner(), new CVCDSAEncoder());
        }
    }

    public static class ecCVCDSA extends SignatureSpi {
        public ecCVCDSA() {
            super(new SHA1Digest(), new ECDSASigner(), new CVCDSAEncoder());
        }
    }

    public static class ecDSA224 extends SignatureSpi {
        public ecDSA224() {
            super(new SHA224Digest(), new ECDSASigner(), new StdDSAEncoder());
        }
    }

    public static class ecDSA256 extends SignatureSpi {
        public ecDSA256() {
            super(new SHA256Digest(), new ECDSASigner(), new StdDSAEncoder());
        }
    }

    public static class ecDSA384 extends SignatureSpi {
        public ecDSA384() {
            super(new SHA384Digest(), new ECDSASigner(), new StdDSAEncoder());
        }
    }

    public static class ecDSA512 extends SignatureSpi {
        public ecDSA512() {
            super(new SHA512Digest(), new ECDSASigner(), new StdDSAEncoder());
        }
    }

    public static class ecDSA extends SignatureSpi {
        public ecDSA() {
            super(new SHA1Digest(), new ECDSASigner(), new StdDSAEncoder());
        }
    }

    public static class ecDSARipeMD160 extends SignatureSpi {
        public ecDSARipeMD160() {
            super(new RIPEMD160Digest(), new ECDSASigner(), new StdDSAEncoder());
        }
    }

    public static class ecDSAnone extends SignatureSpi {
        public ecDSAnone() {
            super(new NullDigest(), new ECDSASigner(), new StdDSAEncoder());
        }
    }

    public static class ecNR224 extends SignatureSpi {
        public ecNR224() {
            super(new SHA224Digest(), new ECNRSigner(), new StdDSAEncoder());
        }
    }

    public static class ecNR256 extends SignatureSpi {
        public ecNR256() {
            super(new SHA256Digest(), new ECNRSigner(), new StdDSAEncoder());
        }
    }

    public static class ecNR384 extends SignatureSpi {
        public ecNR384() {
            super(new SHA384Digest(), new ECNRSigner(), new StdDSAEncoder());
        }
    }

    public static class ecNR512 extends SignatureSpi {
        public ecNR512() {
            super(new SHA512Digest(), new ECNRSigner(), new StdDSAEncoder());
        }
    }

    public static class ecNR extends SignatureSpi {
        public ecNR() {
            super(new SHA1Digest(), new ECNRSigner(), new StdDSAEncoder());
        }
    }

    SignatureSpi(Digest digest, DSA dsa, DSAEncoder dSAEncoder) {
        super(digest, dsa, dSAEncoder);
    }

    protected void engineInitSign(PrivateKey privateKey) throws InvalidKeyException {
        if (privateKey instanceof ECKey) {
            CipherParameters generatePrivateKeyParameter = ECUtil.generatePrivateKeyParameter(privateKey);
            this.digest.reset();
            if (this.appRandom != null) {
                this.signer.init(true, new ParametersWithRandom(generatePrivateKeyParameter, this.appRandom));
                return;
            } else {
                this.signer.init(true, generatePrivateKeyParameter);
                return;
            }
        }
        throw new InvalidKeyException("can't recognise key type in ECDSA based signer");
    }

    protected void engineInitVerify(PublicKey publicKey) throws InvalidKeyException {
        CipherParameters generatePublicKeyParameter;
        if (publicKey instanceof ECPublicKey) {
            generatePublicKeyParameter = ECUtil.generatePublicKeyParameter(publicKey);
        } else {
            try {
                PublicKey publicKey2 = BouncyCastleProvider.getPublicKey(SubjectPublicKeyInfo.getInstance(publicKey.getEncoded()));
                if (publicKey2 instanceof ECPublicKey) {
                    generatePublicKeyParameter = ECUtil.generatePublicKeyParameter(publicKey2);
                } else {
                    throw new InvalidKeyException("can't recognise key type in ECDSA based signer");
                }
            } catch (Exception e) {
                throw new InvalidKeyException("can't recognise key type in ECDSA based signer");
            }
        }
        this.digest.reset();
        this.signer.init(false, generatePublicKeyParameter);
    }
}
