package com.example.kafka;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Map;

import org.apache.avro.generic.GenericDatumWriter;
import org.apache.avro.generic.GenericRecord;
import org.apache.avro.io.BinaryEncoder;
import org.apache.avro.io.DatumReader;
import org.apache.avro.io.DatumWriter;
import org.apache.avro.io.Decoder;
import org.apache.avro.io.DecoderFactory;
import org.apache.avro.io.EncoderFactory;
import org.apache.avro.specific.SpecificDatumReader;
import org.apache.avro.specific.SpecificRecord;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.header.Headers;
import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.Serializer;

public class ThingAvroSerde implements Serializer<SpecificRecord>, Deserializer<Object> {

	@Override
	public Object deserialize(String topic, byte[] data) {
		return null;
	}

	@Override
	public void configure(Map<String, ?> configs, boolean isKey) {
		Serializer.super.configure(configs, isKey);
	}

	@Override
	public Object deserialize(String topic, Headers headers, byte[] data) {
		try {
			Object result = null;

			if (data != null) {
				DatumReader<GenericRecord> datumReader;
				switch (new String(headers.lastHeader("thing").value())) {
					case "Thing1":
						datumReader = new SpecificDatumReader<>(new Thing1().getSchema());
						break;
					case "Thing2":
						datumReader = new SpecificDatumReader<>(new Thing2().getSchema());
						break;
					case "Thing3":
						datumReader = new SpecificDatumReader<>(new Thing3().getSchema());
						break;
					case "Thing4":
						datumReader = new SpecificDatumReader<>(new Thing4().getSchema());
						break;
					case "Thing5":
						datumReader = new SpecificDatumReader<>(new Thing5().getSchema());
						break;
					case "Thing6":
						datumReader = new SpecificDatumReader<>(new Thing6().getSchema());
						break;
					case "Thing7":
						datumReader = new SpecificDatumReader<>(new Thing7().getSchema());
						break;
					case "Thing8":
						datumReader = new SpecificDatumReader<>(new Thing8().getSchema());
						break;
					default:
						datumReader = null;
				}
				Decoder decoder = DecoderFactory.get().binaryDecoder(data, null);

				result = datumReader.read(null, decoder);
			}
			return result;
		}
		catch (Exception ex) {
			throw new SerializationException(
					"Can't deserialize data '" + Arrays.toString(data) + "' from topic '" + topic + "'", ex);
		}
	}

	@Override
	public byte[] serialize(String topic, SpecificRecord data) {
		return new byte[0];
	}

	@Override
	public byte[] serialize(String topic, Headers headers, SpecificRecord data) {
		try {
			byte[] result = null;

			if (data != null) {
				ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
				BinaryEncoder binaryEncoder = EncoderFactory.get().binaryEncoder(byteArrayOutputStream, null);

				DatumWriter<SpecificRecord> datumWriter = new GenericDatumWriter<>(data.getSchema());
				datumWriter.write(data, binaryEncoder);

				binaryEncoder.flush();
				byteArrayOutputStream.close();

				result = byteArrayOutputStream.toByteArray();

				headers.add("thing", data.getClass().getSimpleName().getBytes());
			}
			return result;
		}
		catch (IOException ex) {
			throw new SerializationException("Can't serialize data='" + data + "' for topic='" + topic + "'", ex);
		}
	}

	@Override
	public void close() {
	}

}
