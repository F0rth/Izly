package android.support.v8.renderscript;

import android.renderscript.BaseObj;
import android.renderscript.Sampler;
import android.support.v8.renderscript.Sampler.Value;

class SamplerThunker extends Sampler {
    Sampler mN;

    public static class Builder {
        float mAniso;
        Value mMag = Value.NEAREST;
        Value mMin = Value.NEAREST;
        RenderScriptThunker mRS;
        Value mWrapR = Value.WRAP;
        Value mWrapS = Value.WRAP;
        Value mWrapT = Value.WRAP;

        public Builder(RenderScriptThunker renderScriptThunker) {
            this.mRS = renderScriptThunker;
        }

        public Sampler create() {
            this.mRS.validate();
            android.renderscript.Sampler.Builder builder = new android.renderscript.Sampler.Builder(this.mRS.mN);
            builder.setMinification(SamplerThunker.convertValue(this.mMin));
            builder.setMagnification(SamplerThunker.convertValue(this.mMag));
            builder.setWrapS(SamplerThunker.convertValue(this.mWrapS));
            builder.setWrapT(SamplerThunker.convertValue(this.mWrapT));
            builder.setAnisotropy(this.mAniso);
            Sampler create = builder.create();
            Sampler samplerThunker = new SamplerThunker(0, this.mRS);
            samplerThunker.mMin = this.mMin;
            samplerThunker.mMag = this.mMag;
            samplerThunker.mWrapS = this.mWrapS;
            samplerThunker.mWrapT = this.mWrapT;
            samplerThunker.mWrapR = this.mWrapR;
            samplerThunker.mAniso = this.mAniso;
            samplerThunker.mN = create;
            return samplerThunker;
        }

        public void setAnisotropy(float f) {
            if (f >= 0.0f) {
                this.mAniso = f;
                return;
            }
            throw new IllegalArgumentException("Invalid value");
        }

        public void setMagnification(Value value) {
            if (value == Value.NEAREST || value == Value.LINEAR) {
                this.mMag = value;
                return;
            }
            throw new IllegalArgumentException("Invalid value");
        }

        public void setMinification(Value value) {
            if (value == Value.NEAREST || value == Value.LINEAR || value == Value.LINEAR_MIP_LINEAR || value == Value.LINEAR_MIP_NEAREST) {
                this.mMin = value;
                return;
            }
            throw new IllegalArgumentException("Invalid value");
        }

        public void setWrapS(Value value) {
            if (value == Value.WRAP || value == Value.CLAMP || value == Value.MIRRORED_REPEAT) {
                this.mWrapS = value;
                return;
            }
            throw new IllegalArgumentException("Invalid value");
        }

        public void setWrapT(Value value) {
            if (value == Value.WRAP || value == Value.CLAMP || value == Value.MIRRORED_REPEAT) {
                this.mWrapT = value;
                return;
            }
            throw new IllegalArgumentException("Invalid value");
        }
    }

    protected SamplerThunker(int i, RenderScript renderScript) {
        super(i, renderScript);
    }

    static Sampler.Value convertValue(Value value) {
        switch (value) {
            case NEAREST:
                return Sampler.Value.NEAREST;
            case LINEAR:
                return Sampler.Value.LINEAR;
            case LINEAR_MIP_LINEAR:
                return Sampler.Value.LINEAR_MIP_LINEAR;
            case LINEAR_MIP_NEAREST:
                return Sampler.Value.LINEAR_MIP_NEAREST;
            case WRAP:
                return Sampler.Value.WRAP;
            case CLAMP:
                return Sampler.Value.CLAMP;
            case MIRRORED_REPEAT:
                return Sampler.Value.MIRRORED_REPEAT;
            default:
                return null;
        }
    }

    BaseObj getNObj() {
        return this.mN;
    }
}
