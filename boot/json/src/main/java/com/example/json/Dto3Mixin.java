package com.example.json;

import com.example.json.model.Dto3;
import com.fasterxml.jackson.annotation.JsonProperty;

import org.springframework.boot.jackson.JsonMixin;

@JsonMixin(Dto3.class)
abstract class Dto3Mixin {

	@JsonProperty("mixin-field")
	String field;

}
