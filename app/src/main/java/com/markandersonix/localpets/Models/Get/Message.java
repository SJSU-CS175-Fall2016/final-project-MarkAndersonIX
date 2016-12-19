
package com.markandersonix.localpets.Models.Get;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Message implements Serializable
{

    private Map<String, Object> additionalProperties = new HashMap<String, Object>();
    private final static long serialVersionUID = 1834529589147657359L;

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
