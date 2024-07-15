package com.example.final_project.entity.desirializer;

import com.example.final_project.entity.enums.Delivery;
import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;

public class DeliveryDeserializer extends JsonDeserializer<Delivery> {
    @Override
    public Delivery deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JacksonException {
        ObjectCodec oc = jsonParser.getCodec();
        JsonNode node = oc.readTree(jsonParser);
        if (node == null) {
            return null;
        }
        String text = node.textValue();
        if (node == null) {
            return null;
        }
        return Delivery.fromText(text);
    }
}
