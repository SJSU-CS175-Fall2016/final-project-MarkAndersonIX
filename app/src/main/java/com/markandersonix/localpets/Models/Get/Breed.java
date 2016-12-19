
package com.markandersonix.localpets.Models.Get;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Breed implements Serializable
{

    private String $t;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();
    private final static long serialVersionUID = -6696201230925725266L;

    /**
     * 
     * @return
     *     The $t
     */
    public String get$t() {
        return $t;
    }

    /**
     * 
     * @param $t
     *     The $t
     */
    public void set$t(String $t) {
        this.$t = $t;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append($t).append(additionalProperties).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof Breed) == false) {
            return false;
        }
        Breed rhs = ((Breed) other);
        return new EqualsBuilder().append($t, rhs.$t).append(additionalProperties, rhs.additionalProperties).isEquals();
    }

}
