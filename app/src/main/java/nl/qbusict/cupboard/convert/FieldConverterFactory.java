package nl.qbusict.cupboard.convert;

import java.lang.reflect.Type;
import nl.qbusict.cupboard.Cupboard;

public interface FieldConverterFactory {
    FieldConverter<?> create(Cupboard cupboard, Type type);
}
