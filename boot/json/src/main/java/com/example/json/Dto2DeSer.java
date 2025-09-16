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

import com.example.json.model.Dto2;
import tools.jackson.core.JsonGenerator;
import tools.jackson.core.JsonParser;
import tools.jackson.databind.DeserializationContext;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.SerializationContext;

import org.springframework.boot.jackson.JsonComponent;
import org.springframework.boot.jackson.ObjectValueDeserializer;
import org.springframework.boot.jackson.ObjectValueSerializer;

@JsonComponent
public class Dto2DeSer {

	public static class Serializer extends ObjectValueSerializer<Dto2> {

		@Override
		protected void serializeObject(Dto2 value, JsonGenerator jgen, SerializationContext context) {
			jgen.writeStringProperty("customized-field", value.getField());
		}

	}

	public static class Deserializer extends ObjectValueDeserializer<Dto2> {

		@Override
		protected Dto2 deserializeObject(JsonParser jsonParser, DeserializationContext context, JsonNode tree) {
			String field = nullSafeValue(tree.get("customized-field"), String.class);
			return new Dto2(field);
		}

	}

}
