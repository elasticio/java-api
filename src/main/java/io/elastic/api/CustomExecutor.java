package io.elastic.api;

import io.elastic.api.DynamicMetadataProvider;

import java.lang.reflect.Constructor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.JsonObject;

public class CustomExecutor {

	private static Logger logger = LoggerFactory.getLogger(CustomExecutor.class.getName());
	
	private String className;
	
	public CustomExecutor(String className) {
		this.className = className;
	}
	
	public JsonObject getMetaModel(JsonObject configuration) {
		
		try {
			DynamicMetadataProvider provider = createExecutable();
			return provider.getMetaModel(configuration);
		} catch (Exception e) {
			return reportError(e);
		}
		
	}
	
	public JsonObject getSelectModel(JsonObject configuration) {
		
		try {
			SelectModelProvider provider = createExecutable();
			return provider.getSelectModel(configuration);
		} catch (Exception e) {
			return reportError(e);
		}
	}
	
	private JsonObject reportError(Throwable e) {
		logger.error("An error occured while fetching dynamic metadata for class {}", className);
		JsonObject error = new JsonObject();
		
		StringBuffer stackContainer = new StringBuffer();
		
		for(StackTraceElement elem : e.getStackTrace()) {
			stackContainer.append(elem.toString() + "\n");
		}
		
		error.addProperty("error", e.getMessage());
		error.addProperty("stack", stackContainer.toString());
		
		return error;
	}
	
	private <T> T createExecutable() throws Exception {
		Class clazz = Class.forName(this.className);
		
		Constructor constructor = clazz.getConstructor();
		
		return (T) constructor.newInstance();
	}
}
