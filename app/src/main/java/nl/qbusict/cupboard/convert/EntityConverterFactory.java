package nl.qbusict.cupboard.convert;

import nl.qbusict.cupboard.Cupboard;

public interface EntityConverterFactory {
    <T> EntityConverter<T> create(Cupboard cupboard, Class<T> cls);
}
