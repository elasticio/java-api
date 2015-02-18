package io.elastic.api;

import com.google.gson.JsonObject;

public interface DynamicMetadataProvider {
	
	  /**
     * Allows a component to provide dynamic metadata.
     * 
     * @param configuration data needed to execute the method
     * @return The metadata model for this component
     */
    JsonObject getMetaModel(JsonObject configuration);
}
