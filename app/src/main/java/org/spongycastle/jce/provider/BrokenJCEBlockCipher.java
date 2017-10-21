package org.spongycastle.jce.provider;

import java.security.AlgorithmParameters;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEParameterSpec;
import javax.crypto.spec.RC2ParameterSpec;
import javax.crypto.spec.RC5ParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import org.spongycastle.crypto.BlockCipher;
import org.spongycastle.crypto.BufferedBlockCipher;
import org.spongycastle.crypto.CipherParameters;
import org.spongycastle.crypto.DataLengthException;
import org.spongycastle.crypto.InvalidCipherTextException;
import org.spongycastle.crypto.engines.DESEngine;
import org.spongycastle.crypto.engines.DESedeEngine;
import org.spongycastle.crypto.engines.TwofishEngine;
import org.spongycastle.crypto.modes.CBCBlockCipher;
import org.spongycastle.crypto.modes.CFBBlockCipher;
import org.spongycastle.crypto.modes.CTSBlockCipher;
import org.spongycastle.crypto.modes.OFBBlockCipher;
import org.spongycastle.crypto.paddings.PaddedBufferedBlockCipher;
import org.spongycastle.crypto.params.KeyParameter;
import org.spongycastle.crypto.params.ParametersWithIV;
import org.spongycastle.crypto.params.RC2Parameters;
import org.spongycastle.crypto.params.RC5Parameters;
import org.spongycastle.jcajce.provider.symmetric.util.BCPBEKey;
import org.spongycastle.jce.provider.BrokenPBE.Util;
import org.spongycastle.util.Strings;

public class BrokenJCEBlockCipher implements BrokenPBE {
    private Class[] availableSpecs;
    private BufferedBlockCipher cipher;
    private AlgorithmParameters engineParams;
    private int ivLength;
    private ParametersWithIV ivParam;
    private int pbeHash;
    private int pbeIvSize;
    private int pbeKeySize;
    private int pbeType;

    public static class BrokePBEWithMD5AndDES extends BrokenJCEBlockCipher {
        public BrokePBEWithMD5AndDES() {
            super(new CBCBlockCipher(new DESEngine()), 0, 0, 64, 64);
        }
    }

    public static class BrokePBEWithSHA1AndDES extends BrokenJCEBlockCipher {
        public BrokePBEWithSHA1AndDES() {
            super(new CBCBlockCipher(new DESEngine()), 0, 1, 64, 64);
        }
    }

    public static class BrokePBEWithSHAAndDES2Key extends BrokenJCEBlockCipher {
        public BrokePBEWithSHAAndDES2Key() {
            super(new CBCBlockCipher(new DESedeEngine()), 2, 1, 128, 64);
        }
    }

    public static class BrokePBEWithSHAAndDES3Key extends BrokenJCEBlockCipher {
        public BrokePBEWithSHAAndDES3Key() {
            super(new CBCBlockCipher(new DESedeEngine()), 2, 1, 192, 64);
        }
    }

    public static class OldPBEWithSHAAndDES3Key extends BrokenJCEBlockCipher {
        public OldPBEWithSHAAndDES3Key() {
            super(new CBCBlockCipher(new DESedeEngine()), 3, 1, 192, 64);
        }
    }

    public static class OldPBEWithSHAAndTwofish extends BrokenJCEBlockCipher {
        public OldPBEWithSHAAndTwofish() {
            super(new CBCBlockCipher(new TwofishEngine()), 3, 1, 256, 128);
        }
    }

    protected BrokenJCEBlockCipher(BlockCipher blockCipher) {
        this.availableSpecs = new Class[]{IvParameterSpec.class, PBEParameterSpec.class, RC2ParameterSpec.class, RC5ParameterSpec.class};
        this.pbeType = 2;
        this.pbeHash = 1;
        this.ivLength = 0;
        this.engineParams = null;
        this.cipher = new PaddedBufferedBlockCipher(blockCipher);
    }

    protected BrokenJCEBlockCipher(BlockCipher blockCipher, int i, int i2, int i3, int i4) {
        this.availableSpecs = new Class[]{IvParameterSpec.class, PBEParameterSpec.class, RC2ParameterSpec.class, RC5ParameterSpec.class};
        this.pbeType = 2;
        this.pbeHash = 1;
        this.ivLength = 0;
        this.engineParams = null;
        this.cipher = new PaddedBufferedBlockCipher(blockCipher);
        this.pbeType = i;
        this.pbeHash = i2;
        this.pbeKeySize = i3;
        this.pbeIvSize = i4;
    }

    protected int engineDoFinal(byte[] bArr, int i, int i2, byte[] bArr2, int i3) throws IllegalBlockSizeException, BadPaddingException {
        int i4 = 0;
        if (i2 != 0) {
            i4 = this.cipher.processBytes(bArr, i, i2, bArr2, i3);
        }
        try {
            return i4 + this.cipher.doFinal(bArr2, i3 + i4);
        } catch (DataLengthException e) {
            throw new IllegalBlockSizeException(e.getMessage());
        } catch (InvalidCipherTextException e2) {
            throw new BadPaddingException(e2.getMessage());
        }
    }

    protected byte[] engineDoFinal(byte[] bArr, int i, int i2) throws IllegalBlockSizeException, BadPaddingException {
        Object obj = new byte[engineGetOutputSize(i2)];
        int processBytes = i2 != 0 ? this.cipher.processBytes(bArr, i, i2, obj, 0) : 0;
        try {
            processBytes += this.cipher.doFinal(obj, processBytes);
            Object obj2 = new byte[processBytes];
            System.arraycopy(obj, 0, obj2, 0, processBytes);
            return obj2;
        } catch (DataLengthException e) {
            throw new IllegalBlockSizeException(e.getMessage());
        } catch (InvalidCipherTextException e2) {
            throw new BadPaddingException(e2.getMessage());
        }
    }

    protected int engineGetBlockSize() {
        return this.cipher.getBlockSize();
    }

    protected byte[] engineGetIV() {
        return this.ivParam != null ? this.ivParam.getIV() : null;
    }

    protected int engineGetKeySize(Key key) {
        return key.getEncoded().length;
    }

    protected int engineGetOutputSize(int i) {
        return this.cipher.getOutputSize(i);
    }

    protected AlgorithmParameters engineGetParameters() {
        if (this.engineParams == null && this.ivParam != null) {
            String algorithmName = this.cipher.getUnderlyingCipher().getAlgorithmName();
            if (algorithmName.indexOf(47) >= 0) {
                algorithmName = algorithmName.substring(0, algorithmName.indexOf(47));
            }
            try {
                this.engineParams = AlgorithmParameters.getInstance(algorithmName, BouncyCastleProvider.PROVIDER_NAME);
                this.engineParams.init(this.ivParam.getIV());
            } catch (Exception e) {
                throw new RuntimeException(e.toString());
            }
        }
        return this.engineParams;
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
        this.engineParams = algorithmParameters;
        engineInit(i, key, parameterSpec, secureRandom);
    }

    protected void engineInit(int i, Key key, SecureRandom secureRandom) throws InvalidKeyException {
        try {
            engineInit(i, key, null, secureRandom);
        } catch (InvalidAlgorithmParameterException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    protected void engineInit(int i, Key key, AlgorithmParameterSpec algorithmParameterSpec, SecureRandom secureRandom) throws InvalidKeyException, InvalidAlgorithmParameterException {
        CipherParameters cipherParameters;
        ParametersWithIV parametersWithIV;
        CipherParameters makePBEParameters;
        if (key instanceof BCPBEKey) {
            makePBEParameters = Util.makePBEParameters((BCPBEKey) key, algorithmParameterSpec, this.pbeType, this.pbeHash, this.cipher.getUnderlyingCipher().getAlgorithmName(), this.pbeKeySize, this.pbeIvSize);
            if (this.pbeIvSize != 0) {
                this.ivParam = (ParametersWithIV) makePBEParameters;
                cipherParameters = makePBEParameters;
            } else {
                cipherParameters = makePBEParameters;
            }
        } else if (algorithmParameterSpec == null) {
            r0 = new KeyParameter(key.getEncoded());
        } else if (algorithmParameterSpec instanceof IvParameterSpec) {
            if (this.ivLength != 0) {
                parametersWithIV = new ParametersWithIV(new KeyParameter(key.getEncoded()), ((IvParameterSpec) algorithmParameterSpec).getIV());
                this.ivParam = parametersWithIV;
                r0 = parametersWithIV;
            } else {
                r0 = new KeyParameter(key.getEncoded());
            }
        } else if (algorithmParameterSpec instanceof RC2ParameterSpec) {
            RC2ParameterSpec rC2ParameterSpec = (RC2ParameterSpec) algorithmParameterSpec;
            r2 = new RC2Parameters(key.getEncoded(), ((RC2ParameterSpec) algorithmParameterSpec).getEffectiveKeyBits());
            if (rC2ParameterSpec.getIV() == null || this.ivLength == 0) {
                makePBEParameters = r2;
            } else {
                parametersWithIV = new ParametersWithIV(r2, rC2ParameterSpec.getIV());
                this.ivParam = parametersWithIV;
            }
            r0 = parametersWithIV;
        } else if (algorithmParameterSpec instanceof RC5ParameterSpec) {
            RC5ParameterSpec rC5ParameterSpec = (RC5ParameterSpec) algorithmParameterSpec;
            r2 = new RC5Parameters(key.getEncoded(), ((RC5ParameterSpec) algorithmParameterSpec).getRounds());
            if (rC5ParameterSpec.getWordSize() != 32) {
                throw new IllegalArgumentException("can only accept RC5 word size 32 (at the moment...)");
            }
            if (rC5ParameterSpec.getIV() == null || this.ivLength == 0) {
                makePBEParameters = r2;
            } else {
                parametersWithIV = new ParametersWithIV(r2, rC5ParameterSpec.getIV());
                this.ivParam = parametersWithIV;
            }
            r0 = parametersWithIV;
        } else {
            throw new InvalidAlgorithmParameterException("unknown parameter type.");
        }
        if (!(this.ivLength == 0 || (cipherParameters instanceof ParametersWithIV))) {
            if (secureRandom == null) {
                secureRandom = new SecureRandom();
            }
            if (i == 1 || i == 3) {
                byte[] bArr = new byte[this.ivLength];
                secureRandom.nextBytes(bArr);
                parametersWithIV = new ParametersWithIV(cipherParameters, bArr);
                this.ivParam = parametersWithIV;
                cipherParameters = parametersWithIV;
            } else {
                throw new InvalidAlgorithmParameterException("no IV set when one expected");
            }
        }
        switch (i) {
            case 1:
            case 3:
                this.cipher.init(true, cipherParameters);
                return;
            case 2:
            case 4:
                this.cipher.init(false, cipherParameters);
                return;
            default:
                System.out.println("eeek!");
                return;
        }
    }

    protected void engineSetMode(String str) {
        String toUpperCase = Strings.toUpperCase(str);
        if (toUpperCase.equals("ECB")) {
            this.ivLength = 0;
            this.cipher = new PaddedBufferedBlockCipher(this.cipher.getUnderlyingCipher());
        } else if (toUpperCase.equals("CBC")) {
            this.ivLength = this.cipher.getUnderlyingCipher().getBlockSize();
            this.cipher = new PaddedBufferedBlockCipher(new CBCBlockCipher(this.cipher.getUnderlyingCipher()));
        } else if (toUpperCase.startsWith("OFB")) {
            this.ivLength = this.cipher.getUnderlyingCipher().getBlockSize();
            if (toUpperCase.length() != 3) {
                this.cipher = new PaddedBufferedBlockCipher(new OFBBlockCipher(this.cipher.getUnderlyingCipher(), Integer.parseInt(toUpperCase.substring(3))));
                return;
            }
            this.cipher = new PaddedBufferedBlockCipher(new OFBBlockCipher(this.cipher.getUnderlyingCipher(), this.cipher.getBlockSize() * 8));
        } else if (toUpperCase.startsWith("CFB")) {
            this.ivLength = this.cipher.getUnderlyingCipher().getBlockSize();
            if (toUpperCase.length() != 3) {
                this.cipher = new PaddedBufferedBlockCipher(new CFBBlockCipher(this.cipher.getUnderlyingCipher(), Integer.parseInt(toUpperCase.substring(3))));
                return;
            }
            this.cipher = new PaddedBufferedBlockCipher(new CFBBlockCipher(this.cipher.getUnderlyingCipher(), this.cipher.getBlockSize() * 8));
        } else {
            throw new IllegalArgumentException("can't support mode " + str);
        }
    }

    protected void engineSetPadding(String str) throws NoSuchPaddingException {
        String toUpperCase = Strings.toUpperCase(str);
        if (toUpperCase.equals("NOPADDING")) {
            this.cipher = new BufferedBlockCipher(this.cipher.getUnderlyingCipher());
        } else if (toUpperCase.equals("PKCS5PADDING") || toUpperCase.equals("PKCS7PADDING") || toUpperCase.equals("ISO10126PADDING")) {
            this.cipher = new PaddedBufferedBlockCipher(this.cipher.getUnderlyingCipher());
        } else if (toUpperCase.equals("WITHCTS")) {
            this.cipher = new CTSBlockCipher(this.cipher.getUnderlyingCipher());
        } else {
            throw new NoSuchPaddingException("Padding " + str + " unknown.");
        }
    }

    protected Key engineUnwrap(byte[] bArr, String str, int i) throws InvalidKeyException {
        try {
            byte[] engineDoFinal = engineDoFinal(bArr, 0, bArr.length);
            if (i == 3) {
                return new SecretKeySpec(engineDoFinal, str);
            }
            try {
                KeyFactory instance = KeyFactory.getInstance(str, BouncyCastleProvider.PROVIDER_NAME);
                if (i == 1) {
                    return instance.generatePublic(new X509EncodedKeySpec(engineDoFinal));
                }
                if (i == 2) {
                    return instance.generatePrivate(new PKCS8EncodedKeySpec(engineDoFinal));
                }
                throw new InvalidKeyException("Unknown key type " + i);
            } catch (NoSuchProviderException e) {
                throw new InvalidKeyException("Unknown key type " + e.getMessage());
            } catch (NoSuchAlgorithmException e2) {
                throw new InvalidKeyException("Unknown key type " + e2.getMessage());
            } catch (InvalidKeySpecException e3) {
                throw new InvalidKeyException("Unknown key type " + e3.getMessage());
            }
        } catch (BadPaddingException e4) {
            throw new InvalidKeyException(e4.getMessage());
        } catch (IllegalBlockSizeException e5) {
            throw new InvalidKeyException(e5.getMessage());
        }
    }

    protected int engineUpdate(byte[] bArr, int i, int i2, byte[] bArr2, int i3) {
        return this.cipher.processBytes(bArr, i, i2, bArr2, i3);
    }

    protected byte[] engineUpdate(byte[] bArr, int i, int i2) {
        int updateOutputSize = this.cipher.getUpdateOutputSize(i2);
        if (updateOutputSize > 0) {
            byte[] bArr2 = new byte[updateOutputSize];
            this.cipher.processBytes(bArr, i, i2, bArr2, 0);
            return bArr2;
        }
        this.cipher.processBytes(bArr, i, i2, null, 0);
        return null;
    }

    protected byte[] engineWrap(Key key) throws IllegalBlockSizeException, InvalidKeyException {
        byte[] encoded = key.getEncoded();
        if (encoded == null) {
            throw new InvalidKeyException("Cannot wrap key, null encoding.");
        }
        try {
            return engineDoFinal(encoded, 0, encoded.length);
        } catch (BadPaddingException e) {
            throw new IllegalBlockSizeException(e.getMessage());
        }
    }
}
