package io.elastic.api;

import com.google.gson.JsonObject;


/**
 * 
 * Interface to be implemented by components which want to provide dynamic metadata.
 * 
 * @author Nenad Nikolic nenad@elastic.io
 *
 */
public interface DynamicMetadataProvider {

	/**
	 * Allows a component to provide dynamic metadata.
	 * 
	 * @param configuration
	 *            data needed to execute the method
	 * @return The metadata model for this component
	 */
	JsonObject getMetaModel(JsonObject configuration);
}
