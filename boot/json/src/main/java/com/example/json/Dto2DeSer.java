package com.example.json;

import java.io.IOException;

import com.example.json.model.Dto2;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import org.springframework.boot.jackson.JsonComponent;

@JsonComponent
public class Dto2DeSer {

	public static class Serializer extends JsonSerializer<Dto2> {

		@Override
		public void serialize(Dto2 value, JsonGenerator jgen, SerializerProvider serializers) throws IOException {
			jgen.writeStartObject();
			jgen.writeStringField("customized-field", value.getField());
			jgen.writeEndObject();
		}

	}

	public static class Deserializer extends JsonDeserializer<Dto2> {

		@Override
		public Dto2 deserialize(JsonParser jsonParser, DeserializationContext ctxt) throws IOException {
			ObjectCodec codec = jsonParser.getCodec();
			JsonNode tree = codec.readTree(jsonParser);
			String field = tree.get("customized-field").textValue();
			return new Dto2(field);
		}

	}

}
