
package com.markandersonix.localpets.Models.Get;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Code implements Serializable
{

    private String $t;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();
    private final static long serialVersionUID = 4161952244154373421L;

    public String get$t() {
        return $t;
    }

    public void set$t(String $t) {
        this.$t = $t;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
