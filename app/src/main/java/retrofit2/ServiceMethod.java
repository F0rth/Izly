package retrofit2;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.net.URI;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import okhttp3.Call.Factory;
import okhttp3.Headers;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.HEAD;
import retrofit2.http.HTTP;
import retrofit2.http.Header;
import retrofit2.http.HeaderMap;
import retrofit2.http.Multipart;
import retrofit2.http.OPTIONS;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;

final class ServiceMethod<T> {
    static final String PARAM = "[a-zA-Z][a-zA-Z0-9_-]*";
    static final Pattern PARAM_NAME_REGEX = Pattern.compile(PARAM);
    static final Pattern PARAM_URL_REGEX = Pattern.compile("\\{([a-zA-Z][a-zA-Z0-9_-]*)\\}");
    private final HttpUrl baseUrl;
    final CallAdapter<?> callAdapter;
    final Factory callFactory;
    private final MediaType contentType;
    private final boolean hasBody;
    private final Headers headers;
    private final String httpMethod;
    private final boolean isFormEncoded;
    private final boolean isMultipart;
    private final ParameterHandler<?>[] parameterHandlers;
    private final String relativeUrl;
    private final Converter<ResponseBody, T> responseConverter;

    static final class Builder<T> {
        CallAdapter<?> callAdapter;
        MediaType contentType;
        boolean gotBody;
        boolean gotField;
        boolean gotPart;
        boolean gotPath;
        boolean gotQuery;
        boolean gotUrl;
        boolean hasBody;
        Headers headers;
        String httpMethod;
        boolean isFormEncoded;
        boolean isMultipart;
        final Method method;
        final Annotation[] methodAnnotations;
        final Annotation[][] parameterAnnotationsArray;
        ParameterHandler<?>[] parameterHandlers;
        final Type[] parameterTypes;
        String relativeUrl;
        Set<String> relativeUrlParamNames;
        Converter<ResponseBody, T> responseConverter;
        Type responseType;
        final Retrofit retrofit;

        public Builder(Retrofit retrofit, Method method) {
            this.retrofit = retrofit;
            this.method = method;
            this.methodAnnotations = method.getAnnotations();
            this.parameterTypes = method.getGenericParameterTypes();
            this.parameterAnnotationsArray = method.getParameterAnnotations();
        }

        private CallAdapter<?> createCallAdapter() {
            Type genericReturnType = this.method.getGenericReturnType();
            if (Utils.hasUnresolvableType(genericReturnType)) {
                throw methodError("Method return type must not include a type variable or wildcard: %s", genericReturnType);
            } else if (genericReturnType == Void.TYPE) {
                throw methodError("Service methods cannot return void.", new Object[0]);
            } else {
                try {
                    return this.retrofit.callAdapter(genericReturnType, this.method.getAnnotations());
                } catch (Throwable e) {
                    throw methodError(e, "Unable to create call adapter for %s", genericReturnType);
                }
            }
        }

        private Converter<ResponseBody, T> createResponseConverter() {
            try {
                return this.retrofit.responseBodyConverter(this.responseType, this.method.getAnnotations());
            } catch (Throwable e) {
                throw methodError(e, "Unable to create converter for %s", this.responseType);
            }
        }

        private RuntimeException methodError(String str, Object... objArr) {
            return methodError(null, str, objArr);
        }

        private RuntimeException methodError(Throwable th, String str, Object... objArr) {
            return new IllegalArgumentException(String.format(str, objArr) + "\n    for method " + this.method.getDeclaringClass().getSimpleName() + "." + this.method.getName(), th);
        }

        private RuntimeException parameterError(int i, String str, Object... objArr) {
            return methodError(str + " (parameter #" + (i + 1) + ")", objArr);
        }

        private RuntimeException parameterError(Throwable th, int i, String str, Object... objArr) {
            return methodError(th, str + " (parameter #" + (i + 1) + ")", objArr);
        }

        private Headers parseHeaders(String[] strArr) {
            okhttp3.Headers.Builder builder = new okhttp3.Headers.Builder();
            for (String str : strArr) {
                String str2;
                int indexOf = str2.indexOf(58);
                if (indexOf == -1 || indexOf == 0 || indexOf == str2.length() - 1) {
                    throw methodError("@Headers value must be in the form \"Name: Value\". Found: \"%s\"", str2);
                }
                String substring = str2.substring(0, indexOf);
                str2 = str2.substring(indexOf + 1).trim();
                if ("Content-Type".equalsIgnoreCase(substring)) {
                    MediaType parse = MediaType.parse(str2);
                    if (parse == null) {
                        throw methodError("Malformed content type: %s", str2);
                    }
                    this.contentType = parse;
                } else {
                    builder.add(substring, str2);
                }
            }
            return builder.build();
        }

        private void parseHttpMethodAndPath(String str, String str2, boolean z) {
            if (this.httpMethod != null) {
                throw methodError("Only one HTTP method is allowed. Found: %s and %s.", this.httpMethod, str);
            }
            this.httpMethod = str;
            this.hasBody = z;
            if (!str2.isEmpty()) {
                int indexOf = str2.indexOf(63);
                if (indexOf != -1 && indexOf < str2.length() - 1) {
                    if (ServiceMethod.PARAM_URL_REGEX.matcher(str2.substring(indexOf + 1)).find()) {
                        throw methodError("URL query string \"%s\" must not have replace block. For dynamic query parameters use @Query.", str2.substring(indexOf + 1));
                    }
                }
                this.relativeUrl = str2;
                this.relativeUrlParamNames = ServiceMethod.parsePathParameters(str2);
            }
        }

        private void parseMethodAnnotation(Annotation annotation) {
            if (annotation instanceof DELETE) {
                parseHttpMethodAndPath("DELETE", ((DELETE) annotation).value(), false);
            } else if (annotation instanceof GET) {
                parseHttpMethodAndPath("GET", ((GET) annotation).value(), false);
            } else if (annotation instanceof HEAD) {
                parseHttpMethodAndPath("HEAD", ((HEAD) annotation).value(), false);
                if (!Void.class.equals(this.responseType)) {
                    throw methodError("HEAD method must use Void as response type.", new Object[0]);
                }
            } else if (annotation instanceof PATCH) {
                parseHttpMethodAndPath("PATCH", ((PATCH) annotation).value(), true);
            } else if (annotation instanceof POST) {
                parseHttpMethodAndPath("POST", ((POST) annotation).value(), true);
            } else if (annotation instanceof PUT) {
                parseHttpMethodAndPath("PUT", ((PUT) annotation).value(), true);
            } else if (annotation instanceof OPTIONS) {
                parseHttpMethodAndPath("OPTIONS", ((OPTIONS) annotation).value(), false);
            } else if (annotation instanceof HTTP) {
                HTTP http = (HTTP) annotation;
                parseHttpMethodAndPath(http.method(), http.path(), http.hasBody());
            } else if (annotation instanceof retrofit2.http.Headers) {
                String[] value = ((retrofit2.http.Headers) annotation).value();
                if (value.length == 0) {
                    throw methodError("@Headers annotation is empty.", new Object[0]);
                }
                this.headers = parseHeaders(value);
            } else if (annotation instanceof Multipart) {
                if (this.isFormEncoded) {
                    throw methodError("Only one encoding annotation is allowed.", new Object[0]);
                }
                this.isMultipart = true;
            } else if (!(annotation instanceof FormUrlEncoded)) {
            } else {
                if (this.isMultipart) {
                    throw methodError("Only one encoding annotation is allowed.", new Object[0]);
                }
                this.isFormEncoded = true;
            }
        }

        private ParameterHandler<?> parseParameter(int i, Type type, Annotation[] annotationArr) {
            ParameterHandler<?> parameterHandler = null;
            int length = annotationArr.length;
            int i2 = 0;
            while (i2 < length) {
                ParameterHandler<?> parseParameterAnnotation = parseParameterAnnotation(i, type, annotationArr, annotationArr[i2]);
                if (parseParameterAnnotation == null) {
                    parseParameterAnnotation = parameterHandler;
                } else if (parameterHandler != null) {
                    throw parameterError(i, "Multiple Retrofit annotations found, only one allowed.", new Object[0]);
                }
                i2++;
                parameterHandler = parseParameterAnnotation;
            }
            if (parameterHandler != null) {
                return parameterHandler;
            }
            throw parameterError(i, "No Retrofit annotation found.", new Object[0]);
        }

        private ParameterHandler<?> parseParameterAnnotation(int i, Type type, Annotation[] annotationArr, Annotation annotation) {
            if (annotation instanceof Url) {
                if (this.gotUrl) {
                    throw parameterError(i, "Multiple @Url method annotations found.", new Object[0]);
                } else if (this.gotPath) {
                    throw parameterError(i, "@Path parameters may not be used with @Url.", new Object[0]);
                } else if (this.gotQuery) {
                    throw parameterError(i, "A @Url parameter must not come after a @Query", new Object[0]);
                } else if (this.relativeUrl != null) {
                    throw parameterError(i, "@Url cannot be used with @%s URL", this.httpMethod);
                } else {
                    this.gotUrl = true;
                    if (type == HttpUrl.class || type == String.class || type == URI.class || ((type instanceof Class) && "android.net.Uri".equals(((Class) type).getName()))) {
                        return new RelativeUrl();
                    }
                    throw parameterError(i, "@Url must be okhttp3.HttpUrl, String, java.net.URI, or android.net.Uri type.", new Object[0]);
                }
            } else if (annotation instanceof Path) {
                if (this.gotQuery) {
                    throw parameterError(i, "A @Path parameter must not come after a @Query.", new Object[0]);
                } else if (this.gotUrl) {
                    throw parameterError(i, "@Path parameters may not be used with @Url.", new Object[0]);
                } else if (this.relativeUrl == null) {
                    throw parameterError(i, "@Path can only be used with relative url on @%s", this.httpMethod);
                } else {
                    this.gotPath = true;
                    Path path = (Path) annotation;
                    r1 = path.value();
                    validatePathName(i, r1);
                    return new Path(r1, this.retrofit.stringConverter(type, annotationArr), path.encoded());
                }
            } else if (annotation instanceof Query) {
                Query query = (Query) annotation;
                r1 = query.value();
                r2 = query.encoded();
                r0 = Utils.getRawType(type);
                this.gotQuery = true;
                if (Iterable.class.isAssignableFrom(r0)) {
                    if (type instanceof ParameterizedType) {
                        return new Query(r1, this.retrofit.stringConverter(Utils.getParameterUpperBound(0, (ParameterizedType) type), annotationArr), r2).iterable();
                    }
                    throw parameterError(i, r0.getSimpleName() + " must include generic type (e.g., " + r0.getSimpleName() + "<String>)", new Object[0]);
                } else if (!r0.isArray()) {
                    return new Query(r1, this.retrofit.stringConverter(type, annotationArr), r2);
                } else {
                    return new Query(r1, this.retrofit.stringConverter(ServiceMethod.boxIfPrimitive(r0.getComponentType()), annotationArr), r2).array();
                }
            } else if (annotation instanceof QueryMap) {
                r0 = Utils.getRawType(type);
                if (Map.class.isAssignableFrom(r0)) {
                    r0 = Utils.getSupertype(type, r0, Map.class);
                    if (r0 instanceof ParameterizedType) {
                        r0 = (ParameterizedType) r0;
                        r1 = Utils.getParameterUpperBound(0, r0);
                        if (String.class != r1) {
                            throw parameterError(i, "@QueryMap keys must be of type String: " + r1, new Object[0]);
                        }
                        return new QueryMap(this.retrofit.stringConverter(Utils.getParameterUpperBound(1, r0), annotationArr), ((QueryMap) annotation).encoded());
                    }
                    throw parameterError(i, "Map must include generic types (e.g., Map<String, String>)", new Object[0]);
                }
                throw parameterError(i, "@QueryMap parameter type must be Map.", new Object[0]);
            } else if (annotation instanceof Header) {
                r1 = ((Header) annotation).value();
                r0 = Utils.getRawType(type);
                if (Iterable.class.isAssignableFrom(r0)) {
                    if (type instanceof ParameterizedType) {
                        return new Header(r1, this.retrofit.stringConverter(Utils.getParameterUpperBound(0, (ParameterizedType) type), annotationArr)).iterable();
                    }
                    throw parameterError(i, r0.getSimpleName() + " must include generic type (e.g., " + r0.getSimpleName() + "<String>)", new Object[0]);
                } else if (!r0.isArray()) {
                    return new Header(r1, this.retrofit.stringConverter(type, annotationArr));
                } else {
                    return new Header(r1, this.retrofit.stringConverter(ServiceMethod.boxIfPrimitive(r0.getComponentType()), annotationArr)).array();
                }
            } else if (annotation instanceof HeaderMap) {
                r0 = Utils.getRawType(type);
                if (Map.class.isAssignableFrom(r0)) {
                    r0 = Utils.getSupertype(type, r0, Map.class);
                    if (r0 instanceof ParameterizedType) {
                        r0 = (ParameterizedType) r0;
                        r1 = Utils.getParameterUpperBound(0, r0);
                        if (String.class != r1) {
                            throw parameterError(i, "@HeaderMap keys must be of type String: " + r1, new Object[0]);
                        }
                        return new HeaderMap(this.retrofit.stringConverter(Utils.getParameterUpperBound(1, r0), annotationArr));
                    }
                    throw parameterError(i, "Map must include generic types (e.g., Map<String, String>)", new Object[0]);
                }
                throw parameterError(i, "@HeaderMap parameter type must be Map.", new Object[0]);
            } else if (annotation instanceof Field) {
                if (this.isFormEncoded) {
                    Field field = (Field) annotation;
                    r1 = field.value();
                    r2 = field.encoded();
                    this.gotField = true;
                    r0 = Utils.getRawType(type);
                    if (Iterable.class.isAssignableFrom(r0)) {
                        if (type instanceof ParameterizedType) {
                            return new Field(r1, this.retrofit.stringConverter(Utils.getParameterUpperBound(0, (ParameterizedType) type), annotationArr), r2).iterable();
                        }
                        throw parameterError(i, r0.getSimpleName() + " must include generic type (e.g., " + r0.getSimpleName() + "<String>)", new Object[0]);
                    } else if (!r0.isArray()) {
                        return new Field(r1, this.retrofit.stringConverter(type, annotationArr), r2);
                    } else {
                        return new Field(r1, this.retrofit.stringConverter(ServiceMethod.boxIfPrimitive(r0.getComponentType()), annotationArr), r2).array();
                    }
                }
                throw parameterError(i, "@Field parameters can only be used with form encoding.", new Object[0]);
            } else if (annotation instanceof FieldMap) {
                if (this.isFormEncoded) {
                    r0 = Utils.getRawType(type);
                    if (Map.class.isAssignableFrom(r0)) {
                        r0 = Utils.getSupertype(type, r0, Map.class);
                        if (r0 instanceof ParameterizedType) {
                            r0 = (ParameterizedType) r0;
                            r1 = Utils.getParameterUpperBound(0, r0);
                            if (String.class != r1) {
                                throw parameterError(i, "@FieldMap keys must be of type String: " + r1, new Object[0]);
                            }
                            r1 = this.retrofit.stringConverter(Utils.getParameterUpperBound(1, r0), annotationArr);
                            this.gotField = true;
                            return new FieldMap(r1, ((FieldMap) annotation).encoded());
                        }
                        throw parameterError(i, "Map must include generic types (e.g., Map<String, String>)", new Object[0]);
                    }
                    throw parameterError(i, "@FieldMap parameter type must be Map.", new Object[0]);
                }
                throw parameterError(i, "@FieldMap parameters can only be used with form encoding.", new Object[0]);
            } else if (annotation instanceof Part) {
                if (this.isMultipart) {
                    Part part = (Part) annotation;
                    this.gotPart = true;
                    String value = part.value();
                    r1 = Utils.getRawType(type);
                    if (!value.isEmpty()) {
                        Headers of = Headers.of("Content-Disposition", "form-data; name=\"" + value + "\"", "Content-Transfer-Encoding", part.encoding());
                        if (Iterable.class.isAssignableFrom(r1)) {
                            if (type instanceof ParameterizedType) {
                                r0 = Utils.getParameterUpperBound(0, (ParameterizedType) type);
                                if (!MultipartBody.Part.class.isAssignableFrom(Utils.getRawType(r0))) {
                                    return new Part(of, this.retrofit.requestBodyConverter(r0, annotationArr, this.methodAnnotations)).iterable();
                                }
                                throw parameterError(i, "@Part parameters using the MultipartBody.Part must not include a part name in the annotation.", new Object[0]);
                            }
                            throw parameterError(i, r1.getSimpleName() + " must include generic type (e.g., " + r1.getSimpleName() + "<String>)", new Object[0]);
                        } else if (r1.isArray()) {
                            r0 = ServiceMethod.boxIfPrimitive(r1.getComponentType());
                            if (!MultipartBody.Part.class.isAssignableFrom(r0)) {
                                return new Part(of, this.retrofit.requestBodyConverter(r0, annotationArr, this.methodAnnotations)).array();
                            }
                            throw parameterError(i, "@Part parameters using the MultipartBody.Part must not include a part name in the annotation.", new Object[0]);
                        } else if (!MultipartBody.Part.class.isAssignableFrom(r1)) {
                            return new Part(of, this.retrofit.requestBodyConverter(type, annotationArr, this.methodAnnotations));
                        } else {
                            throw parameterError(i, "@Part parameters using the MultipartBody.Part must not include a part name in the annotation.", new Object[0]);
                        }
                    } else if (Iterable.class.isAssignableFrom(r1)) {
                        if (!(type instanceof ParameterizedType)) {
                            throw parameterError(i, r1.getSimpleName() + " must include generic type (e.g., " + r1.getSimpleName() + "<String>)", new Object[0]);
                        } else if (MultipartBody.Part.class.isAssignableFrom(Utils.getRawType(Utils.getParameterUpperBound(0, (ParameterizedType) type)))) {
                            return RawPart.INSTANCE.iterable();
                        } else {
                            throw parameterError(i, "@Part annotation must supply a name or use MultipartBody.Part parameter type.", new Object[0]);
                        }
                    } else if (r1.isArray()) {
                        if (MultipartBody.Part.class.isAssignableFrom(r1.getComponentType())) {
                            return RawPart.INSTANCE.array();
                        }
                        throw parameterError(i, "@Part annotation must supply a name or use MultipartBody.Part parameter type.", new Object[0]);
                    } else if (MultipartBody.Part.class.isAssignableFrom(r1)) {
                        return RawPart.INSTANCE;
                    } else {
                        throw parameterError(i, "@Part annotation must supply a name or use MultipartBody.Part parameter type.", new Object[0]);
                    }
                }
                throw parameterError(i, "@Part parameters can only be used with multipart encoding.", new Object[0]);
            } else if (annotation instanceof PartMap) {
                if (this.isMultipart) {
                    this.gotPart = true;
                    r0 = Utils.getRawType(type);
                    if (Map.class.isAssignableFrom(r0)) {
                        r0 = Utils.getSupertype(type, r0, Map.class);
                        if (r0 instanceof ParameterizedType) {
                            r0 = (ParameterizedType) r0;
                            r1 = Utils.getParameterUpperBound(0, r0);
                            if (String.class != r1) {
                                throw parameterError(i, "@PartMap keys must be of type String: " + r1, new Object[0]);
                            }
                            Type parameterUpperBound = Utils.getParameterUpperBound(1, r0);
                            if (!MultipartBody.Part.class.isAssignableFrom(Utils.getRawType(parameterUpperBound))) {
                                return new PartMap(this.retrofit.requestBodyConverter(parameterUpperBound, annotationArr, this.methodAnnotations), ((PartMap) annotation).encoding());
                            }
                            throw parameterError(i, "@PartMap values cannot be MultipartBody.Part. Use @Part List<Part> or a different value type instead.", new Object[0]);
                        }
                        throw parameterError(i, "Map must include generic types (e.g., Map<String, String>)", new Object[0]);
                    }
                    throw parameterError(i, "@PartMap parameter type must be Map.", new Object[0]);
                }
                throw parameterError(i, "@PartMap parameters can only be used with multipart encoding.", new Object[0]);
            } else if (!(annotation instanceof Body)) {
                return null;
            } else {
                if (this.isFormEncoded || this.isMultipart) {
                    throw parameterError(i, "@Body parameters cannot be used with form or multi-part encoding.", new Object[0]);
                } else if (this.gotBody) {
                    throw parameterError(i, "Multiple @Body method annotations found.", new Object[0]);
                } else {
                    try {
                        r1 = this.retrofit.requestBodyConverter(type, annotationArr, this.methodAnnotations);
                        this.gotBody = true;
                        return new Body(r1);
                    } catch (Throwable e) {
                        throw parameterError(e, i, "Unable to create @Body converter for %s", type);
                    }
                }
            }
        }

        private void validatePathName(int i, String str) {
            if (!ServiceMethod.PARAM_NAME_REGEX.matcher(str).matches()) {
                throw parameterError(i, "@Path parameter name must match %s. Found: %s", ServiceMethod.PARAM_URL_REGEX.pattern(), str);
            } else if (!this.relativeUrlParamNames.contains(str)) {
                throw parameterError(i, "URL \"%s\" does not contain \"{%s}\".", this.relativeUrl, str);
            }
        }

        public final ServiceMethod build() {
            this.callAdapter = createCallAdapter();
            this.responseType = this.callAdapter.responseType();
            if (this.responseType == Response.class || this.responseType == Response.class) {
                throw methodError("'" + Utils.getRawType(this.responseType).getName() + "' is not a valid response body type. Did you mean ResponseBody?", new Object[0]);
            }
            int i;
            this.responseConverter = createResponseConverter();
            for (Annotation parseMethodAnnotation : this.methodAnnotations) {
                parseMethodAnnotation(parseMethodAnnotation);
            }
            if (this.httpMethod == null) {
                throw methodError("HTTP method annotation is required (e.g., @GET, @POST, etc.).", new Object[0]);
            }
            if (!this.hasBody) {
                if (this.isMultipart) {
                    throw methodError("Multipart can only be specified on HTTP methods with request body (e.g., @POST).", new Object[0]);
                } else if (this.isFormEncoded) {
                    throw methodError("FormUrlEncoded can only be specified on HTTP methods with request body (e.g., @POST).", new Object[0]);
                }
            }
            int length = this.parameterAnnotationsArray.length;
            this.parameterHandlers = new ParameterHandler[length];
            for (i = 0; i < length; i++) {
                Type type = this.parameterTypes[i];
                if (Utils.hasUnresolvableType(type)) {
                    throw parameterError(i, "Parameter type must not include a type variable or wildcard: %s", type);
                }
                Annotation[] annotationArr = this.parameterAnnotationsArray[i];
                if (annotationArr == null) {
                    throw parameterError(i, "No Retrofit annotation found.", new Object[0]);
                }
                this.parameterHandlers[i] = parseParameter(i, type, annotationArr);
            }
            if (this.relativeUrl == null && !this.gotUrl) {
                throw methodError("Missing either @%s URL or @Url parameter.", this.httpMethod);
            } else if (!this.isFormEncoded && !this.isMultipart && !this.hasBody && this.gotBody) {
                throw methodError("Non-body HTTP method cannot contain @Body.", new Object[0]);
            } else if (this.isFormEncoded && !this.gotField) {
                throw methodError("Form-encoded method must contain at least one @Field.", new Object[0]);
            } else if (!this.isMultipart || this.gotPart) {
                return new ServiceMethod(this);
            } else {
                throw methodError("Multipart method must contain at least one @Part.", new Object[0]);
            }
        }
    }

    ServiceMethod(Builder<T> builder) {
        this.callFactory = builder.retrofit.callFactory();
        this.callAdapter = builder.callAdapter;
        this.baseUrl = builder.retrofit.baseUrl();
        this.responseConverter = builder.responseConverter;
        this.httpMethod = builder.httpMethod;
        this.relativeUrl = builder.relativeUrl;
        this.headers = builder.headers;
        this.contentType = builder.contentType;
        this.hasBody = builder.hasBody;
        this.isFormEncoded = builder.isFormEncoded;
        this.isMultipart = builder.isMultipart;
        this.parameterHandlers = builder.parameterHandlers;
    }

    static Class<?> boxIfPrimitive(Class<?> cls) {
        return Boolean.TYPE == cls ? Boolean.class : Byte.TYPE == cls ? Byte.class : Character.TYPE == cls ? Character.class : Double.TYPE == cls ? Double.class : Float.TYPE == cls ? Float.class : Integer.TYPE == cls ? Integer.class : Long.TYPE == cls ? Long.class : Short.TYPE == cls ? Short.class : cls;
    }

    static Set<String> parsePathParameters(String str) {
        Matcher matcher = PARAM_URL_REGEX.matcher(str);
        Set<String> linkedHashSet = new LinkedHashSet();
        while (matcher.find()) {
            linkedHashSet.add(matcher.group(1));
        }
        return linkedHashSet;
    }

    final Request toRequest(Object... objArr) throws IOException {
        int i = 0;
        RequestBuilder requestBuilder = new RequestBuilder(this.httpMethod, this.baseUrl, this.relativeUrl, this.headers, this.contentType, this.hasBody, this.isFormEncoded, this.isMultipart);
        ParameterHandler[] parameterHandlerArr = this.parameterHandlers;
        int length = objArr != null ? objArr.length : 0;
        if (length != parameterHandlerArr.length) {
            throw new IllegalArgumentException("Argument count (" + length + ") doesn't match expected count (" + parameterHandlerArr.length + ")");
        }
        while (i < length) {
            parameterHandlerArr[i].apply(requestBuilder, objArr[i]);
            i++;
        }
        return requestBuilder.build();
    }

    final T toResponse(ResponseBody responseBody) throws IOException {
        return this.responseConverter.convert(responseBody);
    }
}
