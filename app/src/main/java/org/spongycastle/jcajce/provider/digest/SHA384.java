package org.spongycastle.jcajce.provider.digest;

import org.spongycastle.asn1.nist.NISTObjectIdentifiers;
import org.spongycastle.asn1.pkcs.PKCSObjectIdentifiers;
import org.spongycastle.crypto.CipherKeyGenerator;
import org.spongycastle.crypto.digests.SHA384Digest;
import org.spongycastle.crypto.macs.HMac;
import org.spongycastle.jcajce.provider.config.ConfigurableProvider;
import org.spongycastle.jcajce.provider.symmetric.util.BaseKeyGenerator;
import org.spongycastle.jce.provider.JCEMac;

public class SHA384 {

    public static class Digest extends BCMessageDigest implements Cloneable {
        public Digest() {
            super(new SHA384Digest());
        }

        public Object clone() throws CloneNotSupportedException {
            Digest digest = (Digest) super.clone();
            digest.digest = new SHA384Digest((SHA384Digest) this.digest);
            return digest;
        }
    }

    public static class HashMac extends JCEMac {
        public HashMac() {
            super(new HMac(new SHA384Digest()));
        }
    }

    public static class KeyGenerator extends BaseKeyGenerator {
        public KeyGenerator() {
            super("HMACSHA384", 384, new CipherKeyGenerator());
        }
    }

    public static class Mappings extends DigestAlgorithmProvider {
        private static final String PREFIX = SHA384.class.getName();

        public void configure(ConfigurableProvider configurableProvider) {
            configurableProvider.addAlgorithm("MessageDigest.SHA-384", PREFIX + "$Digest");
            configurableProvider.addAlgorithm("Alg.Alias.MessageDigest.SHA384", "SHA-384");
            configurableProvider.addAlgorithm("Alg.Alias.MessageDigest." + NISTObjectIdentifiers.id_sha384, "SHA-384");
            addHMACAlgorithm(configurableProvider, "SHA384", PREFIX + "$HashMac", PREFIX + "$KeyGenerator");
            addHMACAlias(configurableProvider, "SHA384", PKCSObjectIdentifiers.id_hmacWithSHA384);
        }
    }
}
