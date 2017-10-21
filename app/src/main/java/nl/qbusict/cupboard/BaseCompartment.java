package nl.qbusict.cupboard;

import nl.qbusict.cupboard.convert.EntityConverter;

class BaseCompartment {
    protected final Cupboard mCupboard;

    protected BaseCompartment(Cupboard cupboard) {
        this.mCupboard = cupboard;
    }

    protected <T> EntityConverter<T> getConverter(Class<T> cls) {
        return this.mCupboard.getEntityConverter(cls);
    }
}
