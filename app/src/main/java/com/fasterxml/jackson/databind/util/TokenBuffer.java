package com.fasterxml.jackson.databind.util;

import com.fasterxml.jackson.core.Base64Variant;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonGenerator.Feature;
import com.fasterxml.jackson.core.JsonLocation;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonParser.NumberType;
import com.fasterxml.jackson.core.JsonStreamContext;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.core.SerializableString;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.core.base.ParserMinimalBase;
import com.fasterxml.jackson.core.json.JsonReadContext;
import com.fasterxml.jackson.core.json.JsonWriteContext;
import com.fasterxml.jackson.core.util.ByteArrayBuilder;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.cfg.PackageVersion;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.TreeMap;

public class TokenBuffer extends JsonGenerator {
    protected static final int DEFAULT_GENERATOR_FEATURES = Feature.collectDefaults();
    protected int _appendAt;
    protected boolean _closed;
    protected Segment _first;
    protected boolean _forceBigDecimal;
    protected int _generatorFeatures;
    protected boolean _hasNativeId;
    protected boolean _hasNativeObjectIds;
    protected boolean _hasNativeTypeIds;
    protected Segment _last;
    protected boolean _mayHaveNativeIds;
    protected ObjectCodec _objectCodec;
    protected Object _objectId;
    protected Object _typeId;
    protected JsonWriteContext _writeContext;

    public static final class Parser extends ParserMinimalBase {
        protected transient ByteArrayBuilder _byteBuilder;
        protected boolean _closed;
        protected ObjectCodec _codec;
        protected final boolean _hasNativeIds;
        protected final boolean _hasNativeObjectIds;
        protected final boolean _hasNativeTypeIds;
        protected JsonLocation _location = null;
        protected JsonReadContext _parsingContext;
        protected Segment _segment;
        protected int _segmentPtr;

        public Parser(Segment segment, ObjectCodec objectCodec, boolean z, boolean z2) {
            super(0);
            this._segment = segment;
            this._segmentPtr = -1;
            this._codec = objectCodec;
            this._parsingContext = JsonReadContext.createRootContext(null);
            this._hasNativeTypeIds = z;
            this._hasNativeObjectIds = z2;
            this._hasNativeIds = z | z2;
        }

        protected final void _checkIsNumber() throws JsonParseException {
            if (this._currToken == null || !this._currToken.isNumeric()) {
                throw _constructError("Current token (" + this._currToken + ") not numeric, can not use numeric value accessors");
            }
        }

        protected final Object _currentObject() {
            return this._segment.get(this._segmentPtr);
        }

        protected final void _handleEOF() throws JsonParseException {
            _throwInternal();
        }

        public final boolean canReadObjectId() {
            return this._hasNativeObjectIds;
        }

        public final boolean canReadTypeId() {
            return this._hasNativeTypeIds;
        }

        public final void close() throws IOException {
            if (!this._closed) {
                this._closed = true;
            }
        }

        public final BigInteger getBigIntegerValue() throws IOException {
            Number numberValue = getNumberValue();
            return numberValue instanceof BigInteger ? (BigInteger) numberValue : getNumberType() == NumberType.BIG_DECIMAL ? ((BigDecimal) numberValue).toBigInteger() : BigInteger.valueOf(numberValue.longValue());
        }

        public final byte[] getBinaryValue(Base64Variant base64Variant) throws IOException, JsonParseException {
            if (this._currToken == JsonToken.VALUE_EMBEDDED_OBJECT) {
                Object _currentObject = _currentObject();
                if (_currentObject instanceof byte[]) {
                    return (byte[]) _currentObject;
                }
            }
            if (this._currToken != JsonToken.VALUE_STRING) {
                throw _constructError("Current token (" + this._currToken + ") not VALUE_STRING (or VALUE_EMBEDDED_OBJECT with byte[]), can not access as binary");
            }
            String text = getText();
            if (text == null) {
                return null;
            }
            ByteArrayBuilder byteArrayBuilder = this._byteBuilder;
            if (byteArrayBuilder == null) {
                byteArrayBuilder = new ByteArrayBuilder(100);
                this._byteBuilder = byteArrayBuilder;
            } else {
                this._byteBuilder.reset();
            }
            _decodeBase64(text, byteArrayBuilder, base64Variant);
            return byteArrayBuilder.toByteArray();
        }

        public final ObjectCodec getCodec() {
            return this._codec;
        }

        public final JsonLocation getCurrentLocation() {
            return this._location == null ? JsonLocation.NA : this._location;
        }

        public final String getCurrentName() {
            return (this._currToken == JsonToken.START_OBJECT || this._currToken == JsonToken.START_ARRAY) ? this._parsingContext.getParent().getCurrentName() : this._parsingContext.getCurrentName();
        }

        public final BigDecimal getDecimalValue() throws IOException {
            Number numberValue = getNumberValue();
            if (numberValue instanceof BigDecimal) {
                return (BigDecimal) numberValue;
            }
            switch (getNumberType()) {
                case INT:
                case LONG:
                    return BigDecimal.valueOf(numberValue.longValue());
                case BIG_INTEGER:
                    return new BigDecimal((BigInteger) numberValue);
                default:
                    return BigDecimal.valueOf(numberValue.doubleValue());
            }
        }

        public final double getDoubleValue() throws IOException {
            return getNumberValue().doubleValue();
        }

        public final Object getEmbeddedObject() {
            return this._currToken == JsonToken.VALUE_EMBEDDED_OBJECT ? _currentObject() : null;
        }

        public final float getFloatValue() throws IOException {
            return getNumberValue().floatValue();
        }

        public final int getIntValue() throws IOException {
            return this._currToken == JsonToken.VALUE_NUMBER_INT ? ((Number) _currentObject()).intValue() : getNumberValue().intValue();
        }

        public final long getLongValue() throws IOException {
            return getNumberValue().longValue();
        }

        public final NumberType getNumberType() throws IOException {
            Number numberValue = getNumberValue();
            return numberValue instanceof Integer ? NumberType.INT : numberValue instanceof Long ? NumberType.LONG : numberValue instanceof Double ? NumberType.DOUBLE : numberValue instanceof BigDecimal ? NumberType.BIG_DECIMAL : numberValue instanceof BigInteger ? NumberType.BIG_INTEGER : numberValue instanceof Float ? NumberType.FLOAT : numberValue instanceof Short ? NumberType.INT : null;
        }

        public final Number getNumberValue() throws IOException {
            _checkIsNumber();
            Object _currentObject = _currentObject();
            if (_currentObject instanceof Number) {
                return (Number) _currentObject;
            }
            if (_currentObject instanceof String) {
                String str = (String) _currentObject;
                return str.indexOf(46) >= 0 ? Double.valueOf(Double.parseDouble(str)) : Long.valueOf(Long.parseLong(str));
            } else if (_currentObject == null) {
                return null;
            } else {
                throw new IllegalStateException("Internal error: entry should be a Number, but is of type " + _currentObject.getClass().getName());
            }
        }

        public final Object getObjectId() {
            return this._segment.findObjectId(this._segmentPtr);
        }

        public final JsonStreamContext getParsingContext() {
            return this._parsingContext;
        }

        public final String getText() {
            Object _currentObject;
            if (this._currToken == JsonToken.VALUE_STRING || this._currToken == JsonToken.FIELD_NAME) {
                _currentObject = _currentObject();
                return _currentObject instanceof String ? (String) _currentObject : _currentObject == null ? null : _currentObject.toString();
            } else if (this._currToken == null) {
                return null;
            } else {
                switch (this._currToken) {
                    case VALUE_NUMBER_INT:
                    case VALUE_NUMBER_FLOAT:
                        _currentObject = _currentObject();
                        return _currentObject == null ? null : _currentObject.toString();
                    default:
                        return this._currToken.asString();
                }
            }
        }

        public final char[] getTextCharacters() {
            String text = getText();
            return text == null ? null : text.toCharArray();
        }

        public final int getTextLength() {
            String text = getText();
            return text == null ? 0 : text.length();
        }

        public final int getTextOffset() {
            return 0;
        }

        public final JsonLocation getTokenLocation() {
            return getCurrentLocation();
        }

        public final Object getTypeId() {
            return this._segment.findTypeId(this._segmentPtr);
        }

        public final boolean hasTextCharacters() {
            return false;
        }

        public final boolean isClosed() {
            return this._closed;
        }

        public final String nextFieldName() throws IOException {
            if (!(this._closed || this._segment == null)) {
                int i = this._segmentPtr + 1;
                if (i < 16 && this._segment.type(i) == JsonToken.FIELD_NAME) {
                    this._segmentPtr = i;
                    Object obj = this._segment.get(i);
                    String obj2 = obj instanceof String ? (String) obj : obj.toString();
                    this._parsingContext.setCurrentName(obj2);
                    return obj2;
                } else if (nextToken() == JsonToken.FIELD_NAME) {
                    return getCurrentName();
                }
            }
            return null;
        }

        public final JsonToken nextToken() throws IOException {
            if (this._closed || this._segment == null) {
                return null;
            }
            int i = this._segmentPtr + 1;
            this._segmentPtr = i;
            if (i >= 16) {
                this._segmentPtr = 0;
                this._segment = this._segment.next();
                if (this._segment == null) {
                    return null;
                }
            }
            this._currToken = this._segment.type(this._segmentPtr);
            if (this._currToken == JsonToken.FIELD_NAME) {
                Object _currentObject = _currentObject();
                this._parsingContext.setCurrentName(_currentObject instanceof String ? (String) _currentObject : _currentObject.toString());
            } else if (this._currToken == JsonToken.START_OBJECT) {
                this._parsingContext = this._parsingContext.createChildObjectContext(-1, -1);
            } else if (this._currToken == JsonToken.START_ARRAY) {
                this._parsingContext = this._parsingContext.createChildArrayContext(-1, -1);
            } else if (this._currToken == JsonToken.END_OBJECT || this._currToken == JsonToken.END_ARRAY) {
                this._parsingContext = this._parsingContext.getParent();
                if (this._parsingContext == null) {
                    this._parsingContext = JsonReadContext.createRootContext(null);
                }
            }
            return this._currToken;
        }

        public final void overrideCurrentName(String str) {
            JsonReadContext jsonReadContext = this._parsingContext;
            if (this._currToken == JsonToken.START_OBJECT || this._currToken == JsonToken.START_ARRAY) {
                jsonReadContext = jsonReadContext.getParent();
            }
            try {
                jsonReadContext.setCurrentName(str);
            } catch (Throwable e) {
                throw new RuntimeException(e);
            }
        }

        public final JsonToken peekNextToken() throws IOException {
            if (this._closed) {
                return null;
            }
            Segment segment = this._segment;
            int i = this._segmentPtr + 1;
            if (i >= 16) {
                segment = segment == null ? null : segment.next();
                i = 0;
            }
            return segment != null ? segment.type(i) : null;
        }

        public final int readBinaryValue(Base64Variant base64Variant, OutputStream outputStream) throws IOException {
            byte[] binaryValue = getBinaryValue(base64Variant);
            if (binaryValue == null) {
                return 0;
            }
            outputStream.write(binaryValue, 0, binaryValue.length);
            return binaryValue.length;
        }

        public final void setCodec(ObjectCodec objectCodec) {
            this._codec = objectCodec;
        }

        public final void setLocation(JsonLocation jsonLocation) {
            this._location = jsonLocation;
        }

        public final Version version() {
            return PackageVersion.VERSION;
        }
    }

    public static final class Segment {
        public static final int TOKENS_PER_SEGMENT = 16;
        private static final JsonToken[] TOKEN_TYPES_BY_INDEX = new JsonToken[16];
        protected TreeMap<Integer, Object> _nativeIds;
        protected Segment _next;
        protected long _tokenTypes;
        protected final Object[] _tokens = new Object[16];

        static {
            Object values = JsonToken.values();
            System.arraycopy(values, 1, TOKEN_TYPES_BY_INDEX, 1, Math.min(15, values.length - 1));
        }

        private final int _objectIdIndex(int i) {
            return (i + i) + 1;
        }

        private final int _typeIdIndex(int i) {
            return i + i;
        }

        private final void assignNativeIds(int i, Object obj, Object obj2) {
            if (this._nativeIds == null) {
                this._nativeIds = new TreeMap();
            }
            if (obj != null) {
                this._nativeIds.put(Integer.valueOf(_objectIdIndex(i)), obj);
            }
            if (obj2 != null) {
                this._nativeIds.put(Integer.valueOf(_typeIdIndex(i)), obj2);
            }
        }

        private void set(int i, int i2, Object obj) {
            this._tokens[i] = obj;
            long j = (long) i2;
            if (i > 0) {
                j <<= i << 2;
            }
            this._tokenTypes = j | this._tokenTypes;
        }

        private void set(int i, int i2, Object obj, Object obj2, Object obj3) {
            this._tokens[i] = obj;
            long j = (long) i2;
            if (i > 0) {
                j <<= i << 2;
            }
            this._tokenTypes = j | this._tokenTypes;
            assignNativeIds(i, obj2, obj3);
        }

        private void set(int i, JsonToken jsonToken) {
            long ordinal = (long) jsonToken.ordinal();
            if (i > 0) {
                ordinal <<= i << 2;
            }
            this._tokenTypes = ordinal | this._tokenTypes;
        }

        private void set(int i, JsonToken jsonToken, Object obj) {
            this._tokens[i] = obj;
            long ordinal = (long) jsonToken.ordinal();
            if (i > 0) {
                ordinal <<= i << 2;
            }
            this._tokenTypes = ordinal | this._tokenTypes;
        }

        private void set(int i, JsonToken jsonToken, Object obj, Object obj2) {
            long ordinal = (long) jsonToken.ordinal();
            if (i > 0) {
                ordinal <<= i << 2;
            }
            this._tokenTypes = ordinal | this._tokenTypes;
            assignNativeIds(i, obj, obj2);
        }

        private void set(int i, JsonToken jsonToken, Object obj, Object obj2, Object obj3) {
            this._tokens[i] = obj;
            long ordinal = (long) jsonToken.ordinal();
            if (i > 0) {
                ordinal <<= i << 2;
            }
            this._tokenTypes = ordinal | this._tokenTypes;
            assignNativeIds(i, obj2, obj3);
        }

        public final Segment append(int i, JsonToken jsonToken) {
            if (i < 16) {
                set(i, jsonToken);
                return null;
            }
            this._next = new Segment();
            this._next.set(0, jsonToken);
            return this._next;
        }

        public final Segment append(int i, JsonToken jsonToken, Object obj) {
            if (i < 16) {
                set(i, jsonToken, obj);
                return null;
            }
            this._next = new Segment();
            this._next.set(0, jsonToken, obj);
            return this._next;
        }

        public final Segment append(int i, JsonToken jsonToken, Object obj, Object obj2) {
            if (i < 16) {
                set(i, jsonToken, obj, obj2);
                return null;
            }
            this._next = new Segment();
            this._next.set(0, jsonToken, obj, obj2);
            return this._next;
        }

        public final Segment append(int i, JsonToken jsonToken, Object obj, Object obj2, Object obj3) {
            if (i < 16) {
                set(i, jsonToken, obj, obj2, obj3);
                return null;
            }
            this._next = new Segment();
            this._next.set(0, jsonToken, obj, obj2, obj3);
            return this._next;
        }

        public final Segment appendRaw(int i, int i2, Object obj) {
            if (i < 16) {
                set(i, i2, obj);
                return null;
            }
            this._next = new Segment();
            this._next.set(0, i2, obj);
            return this._next;
        }

        public final Segment appendRaw(int i, int i2, Object obj, Object obj2, Object obj3) {
            if (i < 16) {
                set(i, i2, obj, obj2, obj3);
                return null;
            }
            this._next = new Segment();
            this._next.set(0, i2, obj, obj2, obj3);
            return this._next;
        }

        public final Object findObjectId(int i) {
            return this._nativeIds == null ? null : this._nativeIds.get(Integer.valueOf(_objectIdIndex(i)));
        }

        public final Object findTypeId(int i) {
            return this._nativeIds == null ? null : this._nativeIds.get(Integer.valueOf(_typeIdIndex(i)));
        }

        public final Object get(int i) {
            return this._tokens[i];
        }

        public final boolean hasIds() {
            return this._nativeIds != null;
        }

        public final Segment next() {
            return this._next;
        }

        public final int rawType(int i) {
            long j = this._tokenTypes;
            if (i > 0) {
                j >>= i << 2;
            }
            return ((int) j) & 15;
        }

        public final JsonToken type(int i) {
            long j = this._tokenTypes;
            if (i > 0) {
                j >>= i << 2;
            }
            return TOKEN_TYPES_BY_INDEX[((int) j) & 15];
        }
    }

    public TokenBuffer(JsonParser jsonParser) {
        this(jsonParser, null);
    }

    public TokenBuffer(JsonParser jsonParser, DeserializationContext deserializationContext) {
        boolean z = false;
        this._hasNativeId = false;
        this._objectCodec = jsonParser.getCodec();
        this._generatorFeatures = DEFAULT_GENERATOR_FEATURES;
        this._writeContext = JsonWriteContext.createRootContext(null);
        Segment segment = new Segment();
        this._last = segment;
        this._first = segment;
        this._appendAt = 0;
        this._hasNativeTypeIds = jsonParser.canReadTypeId();
        this._hasNativeObjectIds = jsonParser.canReadObjectId();
        this._mayHaveNativeIds = this._hasNativeTypeIds | this._hasNativeObjectIds;
        if (deserializationContext != null) {
            z = deserializationContext.isEnabled(DeserializationFeature.USE_BIG_DECIMAL_FOR_FLOATS);
        }
        this._forceBigDecimal = z;
    }

    @Deprecated
    public TokenBuffer(ObjectCodec objectCodec) {
        this(objectCodec, false);
    }

    public TokenBuffer(ObjectCodec objectCodec, boolean z) {
        this._hasNativeId = false;
        this._objectCodec = objectCodec;
        this._generatorFeatures = DEFAULT_GENERATOR_FEATURES;
        this._writeContext = JsonWriteContext.createRootContext(null);
        Segment segment = new Segment();
        this._last = segment;
        this._first = segment;
        this._appendAt = 0;
        this._hasNativeTypeIds = z;
        this._hasNativeObjectIds = z;
        this._mayHaveNativeIds = this._hasNativeTypeIds | this._hasNativeObjectIds;
    }

    private final void _appendNativeIds(StringBuilder stringBuilder) {
        Object findObjectId = this._last.findObjectId(this._appendAt - 1);
        if (findObjectId != null) {
            stringBuilder.append("[objectId=").append(String.valueOf(findObjectId)).append(']');
        }
        findObjectId = this._last.findTypeId(this._appendAt - 1);
        if (findObjectId != null) {
            stringBuilder.append("[typeId=").append(String.valueOf(findObjectId)).append(']');
        }
    }

    private final void _checkNativeIds(JsonParser jsonParser) throws IOException {
        Object typeId = jsonParser.getTypeId();
        this._typeId = typeId;
        if (typeId != null) {
            this._hasNativeId = true;
        }
        typeId = jsonParser.getObjectId();
        this._objectId = typeId;
        if (typeId != null) {
            this._hasNativeId = true;
        }
    }

    protected final void _append(JsonToken jsonToken) {
        Segment append = this._hasNativeId ? this._last.append(this._appendAt, jsonToken, this._objectId, this._typeId) : this._last.append(this._appendAt, jsonToken);
        if (append == null) {
            this._appendAt++;
            return;
        }
        this._last = append;
        this._appendAt = 1;
    }

    protected final void _append(JsonToken jsonToken, Object obj) {
        Segment append;
        if (this._hasNativeId) {
            append = this._last.append(this._appendAt, jsonToken, obj, this._objectId, this._typeId);
        } else {
            append = this._last.append(this._appendAt, jsonToken, obj);
        }
        if (append == null) {
            this._appendAt++;
            return;
        }
        this._last = append;
        this._appendAt = 1;
    }

    protected final void _appendRaw(int i, Object obj) {
        Segment appendRaw;
        if (this._hasNativeId) {
            appendRaw = this._last.appendRaw(this._appendAt, i, obj, this._objectId, this._typeId);
        } else {
            appendRaw = this._last.appendRaw(this._appendAt, i, obj);
        }
        if (appendRaw == null) {
            this._appendAt++;
            return;
        }
        this._last = appendRaw;
        this._appendAt = 1;
    }

    protected final void _appendValue(JsonToken jsonToken) {
        this._writeContext.writeValue();
        Segment append = this._hasNativeId ? this._last.append(this._appendAt, jsonToken, this._objectId, this._typeId) : this._last.append(this._appendAt, jsonToken);
        if (append == null) {
            this._appendAt++;
            return;
        }
        this._last = append;
        this._appendAt = 1;
    }

    protected final void _appendValue(JsonToken jsonToken, Object obj) {
        Segment append;
        this._writeContext.writeValue();
        if (this._hasNativeId) {
            append = this._last.append(this._appendAt, jsonToken, obj, this._objectId, this._typeId);
        } else {
            append = this._last.append(this._appendAt, jsonToken, obj);
        }
        if (append == null) {
            this._appendAt++;
            return;
        }
        this._last = append;
        this._appendAt = 1;
    }

    protected void _reportUnsupportedOperation() {
        throw new UnsupportedOperationException("Called operation not supported for TokenBuffer");
    }

    public TokenBuffer append(TokenBuffer tokenBuffer) throws IOException {
        if (!this._hasNativeTypeIds) {
            this._hasNativeTypeIds = tokenBuffer.canWriteTypeId();
        }
        if (!this._hasNativeObjectIds) {
            this._hasNativeObjectIds = tokenBuffer.canWriteObjectId();
        }
        this._mayHaveNativeIds = this._hasNativeTypeIds | this._hasNativeObjectIds;
        JsonParser asParser = tokenBuffer.asParser();
        while (asParser.nextToken() != null) {
            copyCurrentStructure(asParser);
        }
        return this;
    }

    public JsonParser asParser() {
        return asParser(this._objectCodec);
    }

    public JsonParser asParser(JsonParser jsonParser) {
        JsonParser parser = new Parser(this._first, jsonParser.getCodec(), this._hasNativeTypeIds, this._hasNativeObjectIds);
        parser.setLocation(jsonParser.getTokenLocation());
        return parser;
    }

    public JsonParser asParser(ObjectCodec objectCodec) {
        return new Parser(this._first, objectCodec, this._hasNativeTypeIds, this._hasNativeObjectIds);
    }

    public boolean canWriteBinaryNatively() {
        return true;
    }

    public boolean canWriteObjectId() {
        return this._hasNativeObjectIds;
    }

    public boolean canWriteTypeId() {
        return this._hasNativeTypeIds;
    }

    public void close() throws IOException {
        this._closed = true;
    }

    public void copyCurrentEvent(JsonParser jsonParser) throws IOException {
        if (this._mayHaveNativeIds) {
            _checkNativeIds(jsonParser);
        }
        switch (jsonParser.getCurrentToken()) {
            case START_OBJECT:
                writeStartObject();
                return;
            case END_OBJECT:
                writeEndObject();
                return;
            case START_ARRAY:
                writeStartArray();
                return;
            case END_ARRAY:
                writeEndArray();
                return;
            case FIELD_NAME:
                writeFieldName(jsonParser.getCurrentName());
                return;
            case VALUE_STRING:
                if (jsonParser.hasTextCharacters()) {
                    writeString(jsonParser.getTextCharacters(), jsonParser.getTextOffset(), jsonParser.getTextLength());
                    return;
                } else {
                    writeString(jsonParser.getText());
                    return;
                }
            case VALUE_NUMBER_INT:
                switch (jsonParser.getNumberType()) {
                    case INT:
                        writeNumber(jsonParser.getIntValue());
                        return;
                    case BIG_INTEGER:
                        writeNumber(jsonParser.getBigIntegerValue());
                        return;
                    default:
                        writeNumber(jsonParser.getLongValue());
                        return;
                }
            case VALUE_NUMBER_FLOAT:
                if (this._forceBigDecimal) {
                    writeNumber(jsonParser.getDecimalValue());
                    return;
                }
                switch (jsonParser.getNumberType()) {
                    case BIG_DECIMAL:
                        writeNumber(jsonParser.getDecimalValue());
                        return;
                    case FLOAT:
                        writeNumber(jsonParser.getFloatValue());
                        return;
                    default:
                        writeNumber(jsonParser.getDoubleValue());
                        return;
                }
            case VALUE_TRUE:
                writeBoolean(true);
                return;
            case VALUE_FALSE:
                writeBoolean(false);
                return;
            case VALUE_NULL:
                writeNull();
                return;
            case VALUE_EMBEDDED_OBJECT:
                writeObject(jsonParser.getEmbeddedObject());
                return;
            default:
                throw new RuntimeException("Internal error: should never end up through this code path");
        }
    }

    public void copyCurrentStructure(JsonParser jsonParser) throws IOException {
        JsonToken currentToken = jsonParser.getCurrentToken();
        if (currentToken == JsonToken.FIELD_NAME) {
            if (this._mayHaveNativeIds) {
                _checkNativeIds(jsonParser);
            }
            writeFieldName(jsonParser.getCurrentName());
            currentToken = jsonParser.nextToken();
        }
        if (this._mayHaveNativeIds) {
            _checkNativeIds(jsonParser);
        }
        switch (currentToken) {
            case START_OBJECT:
                writeStartObject();
                while (jsonParser.nextToken() != JsonToken.END_OBJECT) {
                    copyCurrentStructure(jsonParser);
                }
                writeEndObject();
                return;
            case START_ARRAY:
                writeStartArray();
                while (jsonParser.nextToken() != JsonToken.END_ARRAY) {
                    copyCurrentStructure(jsonParser);
                }
                writeEndArray();
                return;
            default:
                copyCurrentEvent(jsonParser);
                return;
        }
    }

    public TokenBuffer deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        if (jsonParser.getCurrentTokenId() != JsonToken.FIELD_NAME.id()) {
            copyCurrentStructure(jsonParser);
        } else {
            JsonToken nextToken;
            writeStartObject();
            do {
                copyCurrentStructure(jsonParser);
                nextToken = jsonParser.nextToken();
            } while (nextToken == JsonToken.FIELD_NAME);
            if (nextToken != JsonToken.END_OBJECT) {
                throw deserializationContext.mappingException("Expected END_OBJECT after copying contents of a JsonParser into TokenBuffer, got " + nextToken);
            }
            writeEndObject();
        }
        return this;
    }

    public JsonGenerator disable(Feature feature) {
        this._generatorFeatures &= feature.getMask() ^ -1;
        return this;
    }

    public JsonGenerator enable(Feature feature) {
        this._generatorFeatures |= feature.getMask();
        return this;
    }

    public JsonToken firstToken() {
        return this._first != null ? this._first.type(0) : null;
    }

    public void flush() throws IOException {
    }

    public TokenBuffer forceUseOfBigDecimal(boolean z) {
        this._forceBigDecimal = z;
        return this;
    }

    public ObjectCodec getCodec() {
        return this._objectCodec;
    }

    public int getFeatureMask() {
        return this._generatorFeatures;
    }

    public final JsonWriteContext getOutputContext() {
        return this._writeContext;
    }

    public boolean isClosed() {
        return this._closed;
    }

    public boolean isEnabled(Feature feature) {
        return (this._generatorFeatures & feature.getMask()) != 0;
    }

    public JsonGenerator overrideStdFeatures(int i, int i2) {
        this._generatorFeatures = (getFeatureMask() & (i2 ^ -1)) | (i & i2);
        return this;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void serialize(com.fasterxml.jackson.core.JsonGenerator r12) throws java.io.IOException {
        /*
        r11 = this;
        r1 = 1;
        r2 = 0;
        r3 = r11._first;
        r6 = r11._mayHaveNativeIds;
        if (r6 == 0) goto L_0x0057;
    L_0x0008:
        r0 = r3.hasIds();
        if (r0 == 0) goto L_0x0057;
    L_0x000e:
        r0 = r1;
    L_0x000f:
        r5 = -1;
        r10 = r3;
        r3 = r0;
        r0 = r10;
    L_0x0013:
        r4 = r5 + 1;
        r5 = 16;
        if (r4 < r5) goto L_0x0168;
    L_0x0019:
        r3 = r0.next();
        if (r3 == 0) goto L_0x016d;
    L_0x001f:
        if (r6 == 0) goto L_0x0059;
    L_0x0021:
        r0 = r3.hasIds();
        if (r0 == 0) goto L_0x0059;
    L_0x0027:
        r0 = r1;
    L_0x0028:
        r4 = r0;
        r5 = r2;
    L_0x002a:
        r0 = r3.type(r5);
        if (r0 == 0) goto L_0x016d;
    L_0x0030:
        if (r4 == 0) goto L_0x0044;
    L_0x0032:
        r7 = r3.findObjectId(r5);
        if (r7 == 0) goto L_0x003b;
    L_0x0038:
        r12.writeObjectId(r7);
    L_0x003b:
        r7 = r3.findTypeId(r5);
        if (r7 == 0) goto L_0x0044;
    L_0x0041:
        r12.writeTypeId(r7);
    L_0x0044:
        r7 = com.fasterxml.jackson.databind.util.TokenBuffer.AnonymousClass1.$SwitchMap$com$fasterxml$jackson$core$JsonToken;
        r0 = r0.ordinal();
        r0 = r7[r0];
        switch(r0) {
            case 1: goto L_0x005b;
            case 2: goto L_0x0061;
            case 3: goto L_0x0067;
            case 4: goto L_0x006d;
            case 5: goto L_0x0073;
            case 6: goto L_0x008b;
            case 7: goto L_0x00a2;
            case 8: goto L_0x00e7;
            case 9: goto L_0x013d;
            case 10: goto L_0x0144;
            case 11: goto L_0x014b;
            case 12: goto L_0x0152;
            default: goto L_0x004f;
        };
    L_0x004f:
        r0 = new java.lang.RuntimeException;
        r1 = "Internal error: should never end up through this code path";
        r0.<init>(r1);
        throw r0;
    L_0x0057:
        r0 = r2;
        goto L_0x000f;
    L_0x0059:
        r0 = r2;
        goto L_0x0028;
    L_0x005b:
        r12.writeStartObject();
        r0 = r3;
        r3 = r4;
        goto L_0x0013;
    L_0x0061:
        r12.writeEndObject();
        r0 = r3;
        r3 = r4;
        goto L_0x0013;
    L_0x0067:
        r12.writeStartArray();
        r0 = r3;
        r3 = r4;
        goto L_0x0013;
    L_0x006d:
        r12.writeEndArray();
        r0 = r3;
        r3 = r4;
        goto L_0x0013;
    L_0x0073:
        r0 = r3.get(r5);
        r7 = r0 instanceof com.fasterxml.jackson.core.SerializableString;
        if (r7 == 0) goto L_0x0083;
    L_0x007b:
        r0 = (com.fasterxml.jackson.core.SerializableString) r0;
        r12.writeFieldName(r0);
    L_0x0080:
        r0 = r3;
        r3 = r4;
        goto L_0x0013;
    L_0x0083:
        r0 = (java.lang.String) r0;
        r12.writeFieldName(r0);
        r0 = r3;
        r3 = r4;
        goto L_0x0013;
    L_0x008b:
        r0 = r3.get(r5);
        r7 = r0 instanceof com.fasterxml.jackson.core.SerializableString;
        if (r7 == 0) goto L_0x0099;
    L_0x0093:
        r0 = (com.fasterxml.jackson.core.SerializableString) r0;
        r12.writeString(r0);
        goto L_0x0080;
    L_0x0099:
        r0 = (java.lang.String) r0;
        r12.writeString(r0);
        r0 = r3;
        r3 = r4;
        goto L_0x0013;
    L_0x00a2:
        r0 = r3.get(r5);
        r7 = r0 instanceof java.lang.Integer;
        if (r7 == 0) goto L_0x00b4;
    L_0x00aa:
        r0 = (java.lang.Integer) r0;
        r0 = r0.intValue();
        r12.writeNumber(r0);
        goto L_0x0080;
    L_0x00b4:
        r7 = r0 instanceof java.math.BigInteger;
        if (r7 == 0) goto L_0x00be;
    L_0x00b8:
        r0 = (java.math.BigInteger) r0;
        r12.writeNumber(r0);
        goto L_0x0080;
    L_0x00be:
        r7 = r0 instanceof java.lang.Long;
        if (r7 == 0) goto L_0x00cc;
    L_0x00c2:
        r0 = (java.lang.Long) r0;
        r8 = r0.longValue();
        r12.writeNumber(r8);
        goto L_0x0080;
    L_0x00cc:
        r7 = r0 instanceof java.lang.Short;
        if (r7 == 0) goto L_0x00da;
    L_0x00d0:
        r0 = (java.lang.Short) r0;
        r0 = r0.shortValue();
        r12.writeNumber(r0);
        goto L_0x0080;
    L_0x00da:
        r0 = (java.lang.Number) r0;
        r0 = r0.intValue();
        r12.writeNumber(r0);
        r0 = r3;
        r3 = r4;
        goto L_0x0013;
    L_0x00e7:
        r0 = r3.get(r5);
        r7 = r0 instanceof java.lang.Double;
        if (r7 == 0) goto L_0x00f9;
    L_0x00ef:
        r0 = (java.lang.Double) r0;
        r8 = r0.doubleValue();
        r12.writeNumber(r8);
        goto L_0x0080;
    L_0x00f9:
        r7 = r0 instanceof java.math.BigDecimal;
        if (r7 == 0) goto L_0x0104;
    L_0x00fd:
        r0 = (java.math.BigDecimal) r0;
        r12.writeNumber(r0);
        goto L_0x0080;
    L_0x0104:
        r7 = r0 instanceof java.lang.Float;
        if (r7 == 0) goto L_0x0113;
    L_0x0108:
        r0 = (java.lang.Float) r0;
        r0 = r0.floatValue();
        r12.writeNumber(r0);
        goto L_0x0080;
    L_0x0113:
        if (r0 != 0) goto L_0x011a;
    L_0x0115:
        r12.writeNull();
        goto L_0x0080;
    L_0x011a:
        r7 = r0 instanceof java.lang.String;
        if (r7 == 0) goto L_0x0125;
    L_0x011e:
        r0 = (java.lang.String) r0;
        r12.writeNumber(r0);
        goto L_0x0080;
    L_0x0125:
        r3 = new com.fasterxml.jackson.core.JsonGenerationException;
        r4 = "Unrecognized value type for VALUE_NUMBER_FLOAT: %s, can not serialize";
        r1 = new java.lang.Object[r1];
        r0 = r0.getClass();
        r0 = r0.getName();
        r1[r2] = r0;
        r0 = java.lang.String.format(r4, r1);
        r3.<init>(r0, r12);
        throw r3;
    L_0x013d:
        r12.writeBoolean(r1);
        r0 = r3;
        r3 = r4;
        goto L_0x0013;
    L_0x0144:
        r12.writeBoolean(r2);
        r0 = r3;
        r3 = r4;
        goto L_0x0013;
    L_0x014b:
        r12.writeNull();
        r0 = r3;
        r3 = r4;
        goto L_0x0013;
    L_0x0152:
        r0 = r3.get(r5);
        r7 = r0 instanceof com.fasterxml.jackson.databind.util.RawValue;
        if (r7 == 0) goto L_0x0161;
    L_0x015a:
        r0 = (com.fasterxml.jackson.databind.util.RawValue) r0;
        r0.serialize(r12);
        goto L_0x0080;
    L_0x0161:
        r12.writeObject(r0);
        r0 = r3;
        r3 = r4;
        goto L_0x0013;
    L_0x0168:
        r5 = r4;
        r4 = r3;
        r3 = r0;
        goto L_0x002a;
    L_0x016d:
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.fasterxml.jackson.databind.util.TokenBuffer.serialize(com.fasterxml.jackson.core.JsonGenerator):void");
    }

    public JsonGenerator setCodec(ObjectCodec objectCodec) {
        this._objectCodec = objectCodec;
        return this;
    }

    @Deprecated
    public JsonGenerator setFeatureMask(int i) {
        this._generatorFeatures = i;
        return this;
    }

    public String toString() {
        int i = 0;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("[TokenBuffer: ");
        JsonParser asParser = asParser();
        int i2 = (this._hasNativeTypeIds || this._hasNativeObjectIds) ? 1 : 0;
        while (true) {
            JsonToken nextToken = asParser.nextToken();
            if (nextToken == null) {
                break;
            }
            if (i2 != 0) {
                try {
                    _appendNativeIds(stringBuilder);
                } catch (Throwable e) {
                    throw new IllegalStateException(e);
                }
            }
            if (i < 100) {
                if (i > 0) {
                    stringBuilder.append(", ");
                }
                stringBuilder.append(nextToken.toString());
                if (nextToken == JsonToken.FIELD_NAME) {
                    stringBuilder.append('(');
                    stringBuilder.append(asParser.getCurrentName());
                    stringBuilder.append(')');
                }
            }
            i++;
        }
        if (i >= 100) {
            stringBuilder.append(" ... (truncated ").append(i - 100).append(" entries)");
        }
        stringBuilder.append(']');
        return stringBuilder.toString();
    }

    public JsonGenerator useDefaultPrettyPrinter() {
        return this;
    }

    public Version version() {
        return PackageVersion.VERSION;
    }

    public int writeBinary(Base64Variant base64Variant, InputStream inputStream, int i) {
        throw new UnsupportedOperationException();
    }

    public void writeBinary(Base64Variant base64Variant, byte[] bArr, int i, int i2) throws IOException {
        Object obj = new byte[i2];
        System.arraycopy(bArr, i, obj, 0, i2);
        writeObject(obj);
    }

    public void writeBoolean(boolean z) throws IOException {
        _appendValue(z ? JsonToken.VALUE_TRUE : JsonToken.VALUE_FALSE);
    }

    public final void writeEndArray() throws IOException {
        _append(JsonToken.END_ARRAY);
        JsonWriteContext parent = this._writeContext.getParent();
        if (parent != null) {
            this._writeContext = parent;
        }
    }

    public final void writeEndObject() throws IOException {
        _append(JsonToken.END_OBJECT);
        JsonWriteContext parent = this._writeContext.getParent();
        if (parent != null) {
            this._writeContext = parent;
        }
    }

    public void writeFieldName(SerializableString serializableString) throws IOException {
        _append(JsonToken.FIELD_NAME, serializableString);
        this._writeContext.writeFieldName(serializableString.getValue());
    }

    public final void writeFieldName(String str) throws IOException {
        _append(JsonToken.FIELD_NAME, str);
        this._writeContext.writeFieldName(str);
    }

    public void writeNull() throws IOException {
        _appendValue(JsonToken.VALUE_NULL);
    }

    public void writeNumber(double d) throws IOException {
        _appendValue(JsonToken.VALUE_NUMBER_FLOAT, Double.valueOf(d));
    }

    public void writeNumber(float f) throws IOException {
        _appendValue(JsonToken.VALUE_NUMBER_FLOAT, Float.valueOf(f));
    }

    public void writeNumber(int i) throws IOException {
        _appendValue(JsonToken.VALUE_NUMBER_INT, Integer.valueOf(i));
    }

    public void writeNumber(long j) throws IOException {
        _appendValue(JsonToken.VALUE_NUMBER_INT, Long.valueOf(j));
    }

    public void writeNumber(String str) throws IOException {
        _appendValue(JsonToken.VALUE_NUMBER_FLOAT, str);
    }

    public void writeNumber(BigDecimal bigDecimal) throws IOException {
        if (bigDecimal == null) {
            writeNull();
        } else {
            _appendValue(JsonToken.VALUE_NUMBER_FLOAT, bigDecimal);
        }
    }

    public void writeNumber(BigInteger bigInteger) throws IOException {
        if (bigInteger == null) {
            writeNull();
        } else {
            _appendValue(JsonToken.VALUE_NUMBER_INT, bigInteger);
        }
    }

    public void writeNumber(short s) throws IOException {
        _appendValue(JsonToken.VALUE_NUMBER_INT, Short.valueOf(s));
    }

    public void writeObject(Object obj) throws IOException {
        if (obj == null) {
            writeNull();
        } else if (obj.getClass() == byte[].class || (obj instanceof RawValue)) {
            _appendValue(JsonToken.VALUE_EMBEDDED_OBJECT, obj);
        } else if (this._objectCodec == null) {
            _appendValue(JsonToken.VALUE_EMBEDDED_OBJECT, obj);
        } else {
            this._objectCodec.writeValue(this, obj);
        }
    }

    public void writeObjectId(Object obj) {
        this._objectId = obj;
        this._hasNativeId = true;
    }

    public void writeRaw(char c) throws IOException {
        _reportUnsupportedOperation();
    }

    public void writeRaw(SerializableString serializableString) throws IOException {
        _reportUnsupportedOperation();
    }

    public void writeRaw(String str) throws IOException {
        _reportUnsupportedOperation();
    }

    public void writeRaw(String str, int i, int i2) throws IOException {
        _reportUnsupportedOperation();
    }

    public void writeRaw(char[] cArr, int i, int i2) throws IOException {
        _reportUnsupportedOperation();
    }

    public void writeRawUTF8String(byte[] bArr, int i, int i2) throws IOException {
        _reportUnsupportedOperation();
    }

    public void writeRawValue(String str) throws IOException {
        _appendValue(JsonToken.VALUE_EMBEDDED_OBJECT, new RawValue(str));
    }

    public void writeRawValue(String str, int i, int i2) throws IOException {
        if (i > 0 || i2 != str.length()) {
            str = str.substring(i, i + i2);
        }
        _appendValue(JsonToken.VALUE_EMBEDDED_OBJECT, new RawValue(str));
    }

    public void writeRawValue(char[] cArr, int i, int i2) throws IOException {
        _appendValue(JsonToken.VALUE_EMBEDDED_OBJECT, new String(cArr, i, i2));
    }

    public final void writeStartArray() throws IOException {
        _append(JsonToken.START_ARRAY);
        this._writeContext = this._writeContext.createChildArrayContext();
    }

    public final void writeStartObject() throws IOException {
        _append(JsonToken.START_OBJECT);
        this._writeContext = this._writeContext.createChildObjectContext();
    }

    public void writeString(SerializableString serializableString) throws IOException {
        if (serializableString == null) {
            writeNull();
        } else {
            _appendValue(JsonToken.VALUE_STRING, serializableString);
        }
    }

    public void writeString(String str) throws IOException {
        if (str == null) {
            writeNull();
        } else {
            _appendValue(JsonToken.VALUE_STRING, str);
        }
    }

    public void writeString(char[] cArr, int i, int i2) throws IOException {
        writeString(new String(cArr, i, i2));
    }

    public void writeTree(TreeNode treeNode) throws IOException {
        if (treeNode == null) {
            writeNull();
        } else if (this._objectCodec == null) {
            _appendValue(JsonToken.VALUE_EMBEDDED_OBJECT, treeNode);
        } else {
            this._objectCodec.writeTree(this, treeNode);
        }
    }

    public void writeTypeId(Object obj) {
        this._typeId = obj;
        this._hasNativeId = true;
    }

    public void writeUTF8String(byte[] bArr, int i, int i2) throws IOException {
        _reportUnsupportedOperation();
    }
}
