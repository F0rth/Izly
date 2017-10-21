package com.fasterxml.jackson.databind.deser.std;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonParser.NumberType;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.jsontype.TypeDeserializer;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.util.RawValue;
import java.io.IOException;

abstract class BaseNodeDeserializer<T extends JsonNode> extends StdDeserializer<T> {
    public BaseNodeDeserializer(Class<T> cls) {
        super((Class) cls);
    }

    protected final JsonNode _fromEmbedded(JsonParser jsonParser, DeserializationContext deserializationContext, JsonNodeFactory jsonNodeFactory) throws IOException {
        Object embeddedObject = jsonParser.getEmbeddedObject();
        return embeddedObject == null ? jsonNodeFactory.nullNode() : embeddedObject.getClass() == byte[].class ? jsonNodeFactory.binaryNode((byte[]) embeddedObject) : embeddedObject instanceof RawValue ? jsonNodeFactory.rawValueNode((RawValue) embeddedObject) : embeddedObject instanceof JsonNode ? (JsonNode) embeddedObject : jsonNodeFactory.pojoNode(embeddedObject);
    }

    protected final JsonNode _fromFloat(JsonParser jsonParser, DeserializationContext deserializationContext, JsonNodeFactory jsonNodeFactory) throws IOException {
        return (jsonParser.getNumberType() == NumberType.BIG_DECIMAL || deserializationContext.isEnabled(DeserializationFeature.USE_BIG_DECIMAL_FOR_FLOATS)) ? jsonNodeFactory.numberNode(jsonParser.getDecimalValue()) : jsonNodeFactory.numberNode(jsonParser.getDoubleValue());
    }

    protected final JsonNode _fromInt(JsonParser jsonParser, DeserializationContext deserializationContext, JsonNodeFactory jsonNodeFactory) throws IOException {
        int deserializationFeatures = deserializationContext.getDeserializationFeatures();
        NumberType numberType = (F_MASK_INT_COERCIONS & deserializationFeatures) != 0 ? DeserializationFeature.USE_BIG_INTEGER_FOR_INTS.enabledIn(deserializationFeatures) ? NumberType.BIG_INTEGER : DeserializationFeature.USE_LONG_FOR_INTS.enabledIn(deserializationFeatures) ? NumberType.LONG : jsonParser.getNumberType() : jsonParser.getNumberType();
        return numberType == NumberType.INT ? jsonNodeFactory.numberNode(jsonParser.getIntValue()) : numberType == NumberType.LONG ? jsonNodeFactory.numberNode(jsonParser.getLongValue()) : jsonNodeFactory.numberNode(jsonParser.getBigIntegerValue());
    }

    protected void _handleDuplicateField(JsonParser jsonParser, DeserializationContext deserializationContext, JsonNodeFactory jsonNodeFactory, String str, ObjectNode objectNode, JsonNode jsonNode, JsonNode jsonNode2) throws JsonProcessingException {
        if (deserializationContext.isEnabled(DeserializationFeature.FAIL_ON_READING_DUP_TREE_KEY)) {
            _reportProblem(jsonParser, "Duplicate field '" + str + "' for ObjectNode: not allowed when FAIL_ON_READING_DUP_TREE_KEY enabled");
        }
    }

    protected void _reportProblem(JsonParser jsonParser, String str) throws JsonMappingException {
        throw JsonMappingException.from(jsonParser, str);
    }

    protected final JsonNode deserializeAny(JsonParser jsonParser, DeserializationContext deserializationContext, JsonNodeFactory jsonNodeFactory) throws IOException {
        switch (jsonParser.getCurrentTokenId()) {
            case 1:
            case 2:
            case 5:
                return deserializeObject(jsonParser, deserializationContext, jsonNodeFactory);
            case 3:
                return deserializeArray(jsonParser, deserializationContext, jsonNodeFactory);
            case 6:
                return jsonNodeFactory.textNode(jsonParser.getText());
            case 7:
                return _fromInt(jsonParser, deserializationContext, jsonNodeFactory);
            case 8:
                return _fromFloat(jsonParser, deserializationContext, jsonNodeFactory);
            case 9:
                return jsonNodeFactory.booleanNode(true);
            case 10:
                return jsonNodeFactory.booleanNode(false);
            case 11:
                return jsonNodeFactory.nullNode();
            case 12:
                return _fromEmbedded(jsonParser, deserializationContext, jsonNodeFactory);
            default:
                throw deserializationContext.mappingException(handledType());
        }
    }

    protected final ArrayNode deserializeArray(JsonParser jsonParser, DeserializationContext deserializationContext, JsonNodeFactory jsonNodeFactory) throws IOException {
        ArrayNode arrayNode = jsonNodeFactory.arrayNode();
        while (true) {
            JsonToken nextToken = jsonParser.nextToken();
            if (nextToken != null) {
                switch (nextToken.id()) {
                    case 1:
                        arrayNode.add(deserializeObject(jsonParser, deserializationContext, jsonNodeFactory));
                        break;
                    case 3:
                        arrayNode.add(deserializeArray(jsonParser, deserializationContext, jsonNodeFactory));
                        break;
                    case 4:
                        return arrayNode;
                    case 6:
                        arrayNode.add(jsonNodeFactory.textNode(jsonParser.getText()));
                        break;
                    case 7:
                        arrayNode.add(_fromInt(jsonParser, deserializationContext, jsonNodeFactory));
                        break;
                    case 9:
                        arrayNode.add(jsonNodeFactory.booleanNode(true));
                        break;
                    case 10:
                        arrayNode.add(jsonNodeFactory.booleanNode(false));
                        break;
                    case 11:
                        arrayNode.add(jsonNodeFactory.nullNode());
                        break;
                    case 12:
                        arrayNode.add(_fromEmbedded(jsonParser, deserializationContext, jsonNodeFactory));
                        break;
                    default:
                        arrayNode.add(deserializeAny(jsonParser, deserializationContext, jsonNodeFactory));
                        break;
                }
            }
            throw deserializationContext.mappingException("Unexpected end-of-input when binding data into ArrayNode");
        }
    }

    protected final ObjectNode deserializeObject(JsonParser jsonParser, DeserializationContext deserializationContext, JsonNodeFactory jsonNodeFactory) throws IOException {
        String nextFieldName;
        ObjectNode objectNode = jsonNodeFactory.objectNode();
        if (jsonParser.isExpectedStartObjectToken()) {
            nextFieldName = jsonParser.nextFieldName();
        } else {
            JsonToken currentToken = jsonParser.getCurrentToken();
            if (currentToken != JsonToken.END_OBJECT) {
                if (currentToken != JsonToken.FIELD_NAME) {
                    throw deserializationContext.mappingException(handledType(), jsonParser.getCurrentToken());
                }
                nextFieldName = jsonParser.getCurrentName();
            }
            return objectNode;
        }
        while (nextFieldName != null) {
            JsonNode deserializeObject;
            switch (jsonParser.nextToken().id()) {
                case 1:
                    deserializeObject = deserializeObject(jsonParser, deserializationContext, jsonNodeFactory);
                    break;
                case 3:
                    deserializeObject = deserializeArray(jsonParser, deserializationContext, jsonNodeFactory);
                    break;
                case 6:
                    deserializeObject = jsonNodeFactory.textNode(jsonParser.getText());
                    break;
                case 7:
                    deserializeObject = _fromInt(jsonParser, deserializationContext, jsonNodeFactory);
                    break;
                case 9:
                    deserializeObject = jsonNodeFactory.booleanNode(true);
                    break;
                case 10:
                    deserializeObject = jsonNodeFactory.booleanNode(false);
                    break;
                case 11:
                    deserializeObject = jsonNodeFactory.nullNode();
                    break;
                case 12:
                    deserializeObject = _fromEmbedded(jsonParser, deserializationContext, jsonNodeFactory);
                    break;
                default:
                    deserializeObject = deserializeAny(jsonParser, deserializationContext, jsonNodeFactory);
                    break;
            }
            JsonNode replace = objectNode.replace(nextFieldName, deserializeObject);
            if (replace != null) {
                _handleDuplicateField(jsonParser, deserializationContext, jsonNodeFactory, nextFieldName, objectNode, replace, deserializeObject);
            }
            nextFieldName = jsonParser.nextFieldName();
        }
        return objectNode;
    }

    public Object deserializeWithType(JsonParser jsonParser, DeserializationContext deserializationContext, TypeDeserializer typeDeserializer) throws IOException {
        return typeDeserializer.deserializeTypedFromAny(jsonParser, deserializationContext);
    }

    public boolean isCachable() {
        return true;
    }
}
