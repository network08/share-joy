/*package cn.com.swpu.network08.converter;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;

*//**
 * 
 * @author franklin.li
 *
 *//*
public class ObjectMapperFactory {
	private ObjectMapper mapper;

	public ObjectMapperFactory() {
		super();
		this.mapper = new ObjectMapper();
		this.mapper.writerWithDefaultPrettyPrinter();
		SimpleModule module = new SimpleModule("Module");
		this.mapper.registerModule(module);
		this.mapper.setSerializationInclusion(Include.NON_NULL);
		this.mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
	}

	public ObjectMapper getMapper() {
		return mapper;
	}

	public void setMapper(ObjectMapper mapper) {
		this.mapper = mapper;
	}
}
*/