package net.falconstudy.api.server.infrastructure.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.afterburner.AfterburnerModule;

@Component
public class JsonConfig {
	
	@Bean
	public ObjectMapper objectMapper(Jackson2ObjectMapperBuilder jacksonBuilder) {
		var objectMapper = jacksonBuilder.createXmlMapper(false).build();
		objectMapper.registerModules(List.of(
				new Jdk8Module(),
				new JavaTimeModule(),
				new AfterburnerModule()));
		objectMapper.disable(
				SerializationFeature.WRITE_DATES_AS_TIMESTAMPS,
				SerializationFeature.WRITE_DATE_TIMESTAMPS_AS_NANOSECONDS);
		objectMapper.disable(
				DeserializationFeature.READ_DATE_TIMESTAMPS_AS_NANOSECONDS);
		return objectMapper;
	}
	
}
