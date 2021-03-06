package com.acme.fitness.webmvc.json;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

public class JsonManager<T> {

	public ObjectMapper mapper;

	public JsonManager(ObjectMapper mapper) {
		super();
		this.mapper = mapper;
	}

	public String wrapJsonToString(Map<String, T> map) {
		String json = null;
		try {
			json = mapper.writeValueAsString(map);
		} catch (JsonGenerationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return json;
	}

	public Map<String, T> unwrapFromJsonString(String json) {
		Map<String, T> map = new HashMap<String, T>();
		if (json != null) {
			try {
				map = mapper.readValue(json,
						new TypeReference<Map<String, T>>() {
						});
			} catch (JsonParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JsonMappingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return map;
	}
}
