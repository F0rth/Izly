package org.spongycastle.jce.provider;

import java.io.ByteArrayOutputStream;
import java.security.AlgorithmParameters;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;
import javax.crypto.BadPaddingException;
import javax.crypto.CipherSpi;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.interfaces.DHPrivateKey;
import org.spongycastle.crypto.CipherParameters;
import org.spongycastle.crypto.InvalidCipherTextException;
import org.spongycastle.crypto.agreement.DHBasicAgreement;
import org.spongycastle.crypto.agreement.ECDHBasicAgreement;
import org.spongycastle.crypto.digests.SHA1Digest;
import org.spongycastle.crypto.engines.IESEngine;
import org.spongycastle.crypto.generators.KDF2BytesGenerator;
import org.spongycastle.crypto.macs.HMac;
import org.spongycastle.crypto.params.IESParameters;
import org.spongycastle.jcajce.provider.asymmetric.ec.ECUtil;
import org.spongycastle.jce.interfaces.ECPrivateKey;
import org.spongycastle.jce.interfaces.ECPublicKey;
import org.spongycastle.jce.interfaces.IESKey;
import org.spongycastle.jce.spec.IESParameterSpec;

public class JCEIESCipher extends CipherSpi {
    private Class[] availableSpecs = new Class[]{IESParameterSpec.class};
    private ByteArrayOutputStream buffer = new ByteArrayOutputStream();
    private IESEngine cipher;
    private AlgorithmParameters engineParam = null;
    private IESParameterSpec engineParams = null;
    private int state = -1;

    public static class BrokenECIES extends JCEIESCipher {
        public BrokenECIES() {
            super(new IESEngine(new ECDHBasicAgreement(), new BrokenKDF2BytesGenerator(new SHA1Digest()), new HMac(new SHA1Digest())));
        }
    }

    public static class BrokenIES extends JCEIESCipher {
        public BrokenIES() {
            super(new IESEngine(new DHBasicAgreement(), new BrokenKDF2BytesGenerator(new SHA1Digest()), new HMac(new SHA1Digest())));
        }
    }

    public static class ECIES extends JCEIESCipher {
        public ECIES() {
            super(new IESEngine(new ECDHBasicAgreement(), new KDF2BytesGenerator(new SHA1Digest()), new HMac(new SHA1Digest())));
        }
    }

    public static class IES extends JCEIESCipher {
        public IES() {
            super(new IESEngine(new DHBasicAgreement(), new KDF2BytesGenerator(new SHA1Digest()), new HMac(new SHA1Digest())));
        }
    }

    public JCEIESCipher(IESEngine iESEngine) {
        this.cipher = iESEngine;
    }

    protected int engineDoFinal(byte[] bArr, int i, int i2, byte[] bArr2, int i3) throws IllegalBlockSizeException, BadPaddingException {
        if (i2 != 0) {
            this.buffer.write(bArr, i, i2);
        }
        try {
            byte[] toByteArray = this.buffer.toByteArray();
            this.buffer.reset();
            Object processBlock = this.cipher.processBlock(toByteArray, 0, toByteArray.length);
            System.arraycopy(processBlock, 0, bArr2, i3, processBlock.length);
            return processBlock.length;
        } catch (InvalidCipherTextException e) {
            throw new BadPaddingException(e.getMessage());
        }
    }

    protected byte[] engineDoFinal(byte[] bArr, int i, int i2) throws IllegalBlockSizeException, BadPaddingException {
        if (i2 != 0) {
            this.buffer.write(bArr, i, i2);
        }
        try {
            byte[] toByteArray = this.buffer.toByteArray();
            this.buffer.reset();
            return this.cipher.processBlock(toByteArray, 0, toByteArray.length);
        } catch (InvalidCipherTextException e) {
            throw new BadPaddingException(e.getMessage());
        }
    }

    protected int engineGetBlockSize() {
        return 0;
    }

    protected byte[] engineGetIV() {
        return null;
    }

    protected int engineGetKeySize(Key key) {
        if (key instanceof IESKey) {
            IESKey iESKey = (IESKey) key;
            if (iESKey.getPrivate() instanceof DHPrivateKey) {
                return ((DHPrivateKey) iESKey.getPrivate()).getX().bitLength();
            }
            if (iESKey.getPrivate() instanceof ECPrivateKey) {
                return ((ECPrivateKey) iESKey.getPrivate()).getD().bitLength();
            }
            throw new IllegalArgumentException("not an IE key!");
        }
        throw new IllegalArgumentException("must be passed IE key");
    }

    protected int engineGetOutputSize(int i) {
        if (this.state == 1 || this.state == 3) {
            return (this.buffer.size() + i) + 20;
        }
        if (this.state == 2 || this.state == 4) {
            return (this.buffer.size() + i) - 20;
        }
        throw new IllegalStateException("cipher not initialised");
    }

    protected AlgorithmParameters engineGetParameters() {
        if (this.engineParam == null && this.engineParams != null) {
            try {
                this.engineParam = AlgorithmParameters.getInstance("IES", BouncyCastleProvider.PROVIDER_NAME);
                this.engineParam.init(this.engineParams);
            } catch (Exception e) {
                throw new RuntimeException(e.toString());
            }
        }
        return this.engineParam;
    }

    protected void engineInit(int i, Key key, AlgorithmParameters algorithmParameters, SecureRandom secureRandom) throws InvalidKeyException, InvalidAlgorithmParameterException {
        AlgorithmParameterSpec parameterSpec;
        if (algorithmParameters != null) {
            int i2 = 0;
            while (i2 != this.availableSpecs.length) {
                try {
                    parameterSpec = algorithmParameters.getParameterSpec(this.availableSpecs[i2]);
                    break;
                } catch (Exception e) {
                    i2++;
                }
            }
            parameterSpec = null;
            if (parameterSpec == null) {
                throw new InvalidAlgorithmParameterException("can't handle parameter " + algorithmParameters.toString());
            }
        }
        parameterSpec = null;
        this.engineParam = algorithmParameters;
        engineInit(i, key, parameterSpec, secureRandom);
    }

    protected void engineInit(int i, Key key, SecureRandom secureRandom) throws InvalidKeyException {
        if (i == 1 || i == 3) {
            try {
                engineInit(i, key, null, secureRandom);
                return;
            } catch (InvalidAlgorithmParameterException e) {
            }
        }
        throw new IllegalArgumentException("can't handle null parameter spec in IES");
    }

    protected void engineInit(int i, Key key, AlgorithmParameterSpec algorithmParameterSpec, SecureRandom secureRandom) throws InvalidKeyException, InvalidAlgorithmParameterException {
        if (key instanceof IESKey) {
            IESParameterSpec iESParameterSpec;
            CipherParameters generatePublicKeyParameter;
            CipherParameters generatePrivateKeyParameter;
            if (algorithmParameterSpec == null && (i == 1 || i == 3)) {
                byte[] bArr = new byte[16];
                byte[] bArr2 = new byte[16];
                if (secureRandom == null) {
                    secureRandom = new SecureRandom();
                }
                secureRandom.nextBytes(bArr);
                secureRandom.nextBytes(bArr2);
                iESParameterSpec = new IESParameterSpec(bArr, bArr2, 128);
            } else if (algorithmParameterSpec instanceof IESParameterSpec) {
                AlgorithmParameterSpec algorithmParameterSpec2 = algorithmParameterSpec;
            } else {
                throw new InvalidAlgorithmParameterException("must be passed IES parameters");
            }
            IESKey iESKey = (IESKey) key;
            if (iESKey.getPublic() instanceof ECPublicKey) {
                generatePublicKeyParameter = ECUtil.generatePublicKeyParameter(iESKey.getPublic());
                generatePrivateKeyParameter = ECUtil.generatePrivateKeyParameter(iESKey.getPrivate());
            } else {
                generatePublicKeyParameter = DHUtil.generatePublicKeyParameter(iESKey.getPublic());
                generatePrivateKeyParameter = DHUtil.generatePrivateKeyParameter(iESKey.getPrivate());
            }
            this.engineParams = iESParameterSpec;
            CipherParameters iESParameters = new IESParameters(this.engineParams.getDerivationV(), this.engineParams.getEncodingV(), this.engineParams.getMacKeySize());
            this.state = i;
            this.buffer.reset();
            switch (i) {
                case 1:
                case 3:
                    this.cipher.init(true, generatePrivateKeyParameter, generatePublicKeyParameter, iESParameters);
                    return;
                case 2:
                case 4:
                    this.cipher.init(false, generatePrivateKeyParameter, generatePublicKeyParameter, iESParameters);
                    return;
                default:
                    System.out.println("eeek!");
                    return;
            }
        }
        throw new InvalidKeyException("must be passed IES key");
    }

    protected void engineSetMode(String str) {
        throw new IllegalArgumentException("can't support mode " + str);
    }

    protected void engineSetPadding(String str) throws NoSuchPaddingException {
        throw new NoSuchPaddingException(str + " unavailable with RSA.");
    }

    protected int engineUpdate(byte[] bArr, int i, int i2, byte[] bArr2, int i3) {
        this.buffer.write(bArr, i, i2);
        return 0;
    }

    protected byte[] engineUpdate(byte[] bArr, int i, int i2) {
        this.buffer.write(bArr, i, i2);
        return null;
    }
}
