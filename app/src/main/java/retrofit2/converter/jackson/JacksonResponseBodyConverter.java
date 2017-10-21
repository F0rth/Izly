package retrofit2.converter.jackson;

import com.fasterxml.jackson.databind.ObjectReader;
import java.io.IOException;
import okhttp3.ResponseBody;
import retrofit2.Converter;

final class JacksonResponseBodyConverter<T> implements Converter<ResponseBody, T> {
    private final ObjectReader adapter;

    JacksonResponseBodyConverter(ObjectReader objectReader) {
        this.adapter = objectReader;
    }

    public final T convert(ResponseBody responseBody) throws IOException {
        try {
            T readValue = this.adapter.readValue(responseBody.charStream());
            return readValue;
        } finally {
            responseBody.close();
        }
    }
}
