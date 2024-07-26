package com.example.final_project.entity.deserializer;

import com.example.final_project.entity.enums.Delivery;
import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;

public class DeliveryDeserializer extends JsonDeserializer<Delivery> {
    /**
     * Method to deserialize a JSON value into a Delivery enum.
     *
     * @param jsonParser             the parser used to parse the JSON content.
     * @param deserializationContext the context for the process of deserialization.
     * @return Delivery              the deserialized Delivery enum value.
     * @throws IOException           if an I/O error occurs during parsing.
     * @throws JacksonException      if a parsing exception occurs during deserialization.
     */
    @Override
    public Delivery deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JacksonException {
        ObjectCodec oc = jsonParser.getCodec();
        JsonNode node = oc.readTree(jsonParser);
        if (node == null) {
            return null;
        }
        String text = node.textValue();
        return Delivery.fromText(text);
    }
}
