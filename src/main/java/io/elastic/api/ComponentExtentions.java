package io.elastic.api;

import com.google.gson.JsonObject;

public interface ComponentExtentions {
	  /**
     * Allows a component to provide dynamic metadata.
     * 
     * @param configuration data needed to execute the method
     * @return The metadata model for this component
     */
    abstract public JsonObject getMetaModel(JsonObject configuration);
    
    /**
     * Allows a component to dynamically populate select boxes
     * 
     * @param configuration Config data needed to execute the method
     * @param identifier If the component has multiple select boxes to be populated the identifier helps determine which one requested data
     * @return the select model for a particular select box
     */
    abstract public JsonObject getSelectModel(JsonObject configuration, String identifier);
}
