package org.spongycastle.jce.provider;

import java.security.Permission;
import javax.crypto.spec.DHParameterSpec;
import org.spongycastle.jcajce.provider.asymmetric.ec.EC5Util;
import org.spongycastle.jcajce.provider.config.ConfigurableProvider;
import org.spongycastle.jcajce.provider.config.ProviderConfiguration;
import org.spongycastle.jcajce.provider.config.ProviderConfigurationPermission;
import org.spongycastle.jce.spec.ECParameterSpec;

class BouncyCastleProviderConfiguration implements ProviderConfiguration {
    private static Permission BC_DH_LOCAL_PERMISSION = new ProviderConfigurationPermission(BouncyCastleProvider.PROVIDER_NAME, ConfigurableProvider.THREAD_LOCAL_DH_DEFAULT_PARAMS);
    private static Permission BC_DH_PERMISSION = new ProviderConfigurationPermission(BouncyCastleProvider.PROVIDER_NAME, ConfigurableProvider.DH_DEFAULT_PARAMS);
    private static Permission BC_EC_LOCAL_PERMISSION = new ProviderConfigurationPermission(BouncyCastleProvider.PROVIDER_NAME, ConfigurableProvider.THREAD_LOCAL_EC_IMPLICITLY_CA);
    private static Permission BC_EC_PERMISSION = new ProviderConfigurationPermission(BouncyCastleProvider.PROVIDER_NAME, ConfigurableProvider.EC_IMPLICITLY_CA);
    private volatile DHParameterSpec dhDefaultParams;
    private ThreadLocal dhThreadSpec = new ThreadLocal();
    private volatile ECParameterSpec ecImplicitCaParams;
    private ThreadLocal ecThreadSpec = new ThreadLocal();

    BouncyCastleProviderConfiguration() {
    }

    public DHParameterSpec getDHDefaultParameters() {
        DHParameterSpec dHParameterSpec = (DHParameterSpec) this.dhThreadSpec.get();
        return dHParameterSpec != null ? dHParameterSpec : this.dhDefaultParams;
    }

    public ECParameterSpec getEcImplicitlyCa() {
        ECParameterSpec eCParameterSpec = (ECParameterSpec) this.ecThreadSpec.get();
        return eCParameterSpec != null ? eCParameterSpec : this.ecImplicitCaParams;
    }

    void setParameter(String str, Object obj) {
        SecurityManager securityManager = System.getSecurityManager();
        if (str.equals(ConfigurableProvider.THREAD_LOCAL_EC_IMPLICITLY_CA)) {
            if (securityManager != null) {
                securityManager.checkPermission(BC_EC_LOCAL_PERMISSION);
            }
            obj = ((obj instanceof ECParameterSpec) || obj == null) ? (ECParameterSpec) obj : EC5Util.convertSpec((java.security.spec.ECParameterSpec) obj, false);
            if (obj == null) {
                this.ecThreadSpec.remove();
            } else {
                this.ecThreadSpec.set(obj);
            }
        } else if (str.equals(ConfigurableProvider.EC_IMPLICITLY_CA)) {
            if (securityManager != null) {
                securityManager.checkPermission(BC_EC_PERMISSION);
            }
            if ((obj instanceof ECParameterSpec) || obj == null) {
                this.ecImplicitCaParams = (ECParameterSpec) obj;
            } else {
                this.ecImplicitCaParams = EC5Util.convertSpec((java.security.spec.ECParameterSpec) obj, false);
            }
        } else if (str.equals(ConfigurableProvider.THREAD_LOCAL_DH_DEFAULT_PARAMS)) {
            if (securityManager != null) {
                securityManager.checkPermission(BC_DH_LOCAL_PERMISSION);
            }
            if ((obj instanceof DHParameterSpec) || obj == null) {
                DHParameterSpec dHParameterSpec = (DHParameterSpec) obj;
                if (dHParameterSpec == null) {
                    this.dhThreadSpec.remove();
                    return;
                } else {
                    this.dhThreadSpec.set(dHParameterSpec);
                    return;
                }
            }
            throw new IllegalArgumentException("not a valid DHParameterSpec");
        } else if (str.equals(ConfigurableProvider.DH_DEFAULT_PARAMS)) {
            if (securityManager != null) {
                securityManager.checkPermission(BC_DH_PERMISSION);
            }
            if ((obj instanceof DHParameterSpec) || obj == null) {
                this.dhDefaultParams = (DHParameterSpec) obj;
                return;
            }
            throw new IllegalArgumentException("not a valid DHParameterSpec");
        }
    }
}
