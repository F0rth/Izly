package org.spongycastle.jcajce.provider.asymmetric.gost;

import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.SignatureException;
import java.security.spec.AlgorithmParameterSpec;
import org.spongycastle.asn1.pkcs.PKCSObjectIdentifiers;
import org.spongycastle.asn1.x509.SubjectPublicKeyInfo;
import org.spongycastle.asn1.x509.X509ObjectIdentifiers;
import org.spongycastle.crypto.CipherParameters;
import org.spongycastle.crypto.DSA;
import org.spongycastle.crypto.Digest;
import org.spongycastle.crypto.digests.GOST3411Digest;
import org.spongycastle.crypto.params.ParametersWithRandom;
import org.spongycastle.crypto.signers.GOST3410Signer;
import org.spongycastle.jcajce.provider.asymmetric.ec.ECUtil;
import org.spongycastle.jce.interfaces.ECKey;
import org.spongycastle.jce.interfaces.ECPublicKey;
import org.spongycastle.jce.interfaces.GOST3410Key;
import org.spongycastle.jce.provider.BouncyCastleProvider;
import org.spongycastle.jce.provider.GOST3410Util;

public class SignatureSpi extends java.security.SignatureSpi implements PKCSObjectIdentifiers, X509ObjectIdentifiers {
    private Digest digest = new GOST3411Digest();
    private SecureRandom random;
    private DSA signer = new GOST3410Signer();

    protected Object engineGetParameter(String str) {
        throw new UnsupportedOperationException("engineSetParameter unsupported");
    }

    protected void engineInitSign(PrivateKey privateKey) throws InvalidKeyException {
        CipherParameters generatePrivateKeyParameter = privateKey instanceof ECKey ? ECUtil.generatePrivateKeyParameter(privateKey) : GOST3410Util.generatePrivateKeyParameter(privateKey);
        this.digest.reset();
        if (this.random != null) {
            this.signer.init(true, new ParametersWithRandom(generatePrivateKeyParameter, this.random));
        } else {
            this.signer.init(true, generatePrivateKeyParameter);
        }
    }

    protected void engineInitSign(PrivateKey privateKey, SecureRandom secureRandom) throws InvalidKeyException {
        this.random = secureRandom;
        engineInitSign(privateKey);
    }

    protected void engineInitVerify(PublicKey publicKey) throws InvalidKeyException {
        CipherParameters generatePublicKeyParameter;
        if (publicKey instanceof ECPublicKey) {
            generatePublicKeyParameter = ECUtil.generatePublicKeyParameter(publicKey);
        } else if (publicKey instanceof GOST3410Key) {
            generatePublicKeyParameter = GOST3410Util.generatePublicKeyParameter(publicKey);
        } else {
            try {
                PublicKey publicKey2 = BouncyCastleProvider.getPublicKey(SubjectPublicKeyInfo.getInstance(publicKey.getEncoded()));
                if (publicKey2 instanceof ECPublicKey) {
                    generatePublicKeyParameter = ECUtil.generatePublicKeyParameter(publicKey2);
                } else {
                    throw new InvalidKeyException("can't recognise key type in DSA based signer");
                }
            } catch (Exception e) {
                throw new InvalidKeyException("can't recognise key type in DSA based signer");
            }
        }
        this.digest.reset();
        this.signer.init(false, generatePublicKeyParameter);
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
            Object obj = new byte[64];
            BigInteger[] generateSignature = this.signer.generateSignature(bArr);
            Object toByteArray = generateSignature[0].toByteArray();
            Object toByteArray2 = generateSignature[1].toByteArray();
            if (toByteArray2[0] != (byte) 0) {
                System.arraycopy(toByteArray2, 0, obj, 32 - toByteArray2.length, toByteArray2.length);
            } else {
                System.arraycopy(toByteArray2, 1, obj, 32 - (toByteArray2.length - 1), toByteArray2.length - 1);
            }
            if (toByteArray[0] != (byte) 0) {
                System.arraycopy(toByteArray, 0, obj, 64 - toByteArray.length, toByteArray.length);
            } else {
                System.arraycopy(toByteArray, 1, obj, 64 - (toByteArray.length - 1), toByteArray.length - 1);
            }
            return obj;
        } catch (Exception e) {
            throw new SignatureException(e.toString());
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
            Object obj = new byte[32];
            System.arraycopy(bArr, 0, new byte[32], 0, 32);
            System.arraycopy(bArr, 32, obj, 0, 32);
            BigInteger[] bigIntegerArr = new BigInteger[]{new BigInteger(1, obj), new BigInteger(1, r2)};
            return this.signer.verifySignature(bArr2, bigIntegerArr[0], bigIntegerArr[1]);
        } catch (Exception e) {
            throw new SignatureException("error decoding signature bytes.");
        }
    }
}
