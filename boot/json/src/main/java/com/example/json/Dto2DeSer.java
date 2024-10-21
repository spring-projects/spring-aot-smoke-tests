/*
 * Copyright 2022-2024 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
