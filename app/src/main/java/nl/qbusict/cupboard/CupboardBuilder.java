package nl.qbusict.cupboard;

import nl.qbusict.cupboard.convert.EntityConverterFactory;
import nl.qbusict.cupboard.convert.FieldConverter;
import nl.qbusict.cupboard.convert.FieldConverterFactory;

public class CupboardBuilder {
    private Cupboard mCupboard = new Cupboard();

    public CupboardBuilder(Cupboard cupboard) {
        for (Class register : cupboard.getRegisteredEntities()) {
            this.mCupboard.register(register);
        }
    }

    public Cupboard build() {
        return this.mCupboard;
    }

    public CupboardBuilder registerEntityConverterFactory(EntityConverterFactory entityConverterFactory) {
        this.mCupboard.registerEntityConverterFactory(entityConverterFactory);
        return this;
    }

    public <T> CupboardBuilder registerFieldConverter(Class<T> cls, FieldConverter<T> fieldConverter) {
        this.mCupboard.registerFieldConverter(cls, fieldConverter);
        return this;
    }

    public CupboardBuilder registerFieldConverterFactory(FieldConverterFactory fieldConverterFactory) {
        this.mCupboard.registerFieldConverterFactory(fieldConverterFactory);
        return this;
    }

    public CupboardBuilder useAnnotations() {
        this.mCupboard.setUseAnnotations(true);
        return this;
    }
}
