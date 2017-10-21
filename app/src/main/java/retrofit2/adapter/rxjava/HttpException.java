package retrofit2.adapter.rxjava;

import retrofit2.Response;

public final class HttpException extends Exception {
    private final int code;
    private final String message;
    private final transient Response<?> response;

    public HttpException(Response<?> response) {
        super("HTTP " + response.code() + " " + response.message());
        this.code = response.code();
        this.message = response.message();
        this.response = response;
    }

    public final int code() {
        return this.code;
    }

    public final String message() {
        return this.message;
    }

    public final Response<?> response() {
        return this.response;
    }
}
