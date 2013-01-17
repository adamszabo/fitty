package com.acme.fitness.webmvc.web;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

public class JsonManager {

	public ObjectMapper mapper;

	public JsonManager(ObjectMapper mapper) {
		super();
		this.mapper = mapper;
	}

	public String wrapToJsonString(Map<String, Integer> map) {
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

	public Map<String, Integer> unwrapFromJsonString(String json) {
		Map<String, Integer> map = new HashMap<String, Integer>();
		if (json != null) {
			try {
				map = mapper.readValue(json,
						new TypeReference<Map<String, Object>>() {
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
