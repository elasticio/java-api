package io.elastic.api;

import com.google.gson.JsonObject;


/**
 * 
 * Interface to be implemented by components which want to provide dynamic metadata
 * 
 * @author Nenad Nikolic nenad@elastic.io
 *
 */
public interface SelectModelProvider {
	
	 /**
     * Allows a component to dynamically populate select boxes
     * 
     * @param configuration Config data needed to execute the method
     * @param identifier If the component has multiple select boxes to be populated the identifier helps determine which one requested data
     * @return the select model for a particular select box
     */
    JsonObject getSelectModel(JsonObject configuration, String identifier);
}
