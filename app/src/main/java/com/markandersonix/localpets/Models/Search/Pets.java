
package com.markandersonix.localpets.Models.Search;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class Pets implements Serializable
{

    private List<Pet> pet = null;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();
    private final static long serialVersionUID = -3787277343602142300L;

    /**
     * 
     * @return
     *     The pet
     */
    public List<Pet> getPet() {
        return pet;
    }

    /**
     * 
     * @param pet
     *     The pet
     */
    public void setPet(List<Pet> pet) {
        this.pet = pet;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(pet).append(additionalProperties).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof Pets) == false) {
            return false;
        }
        Pets rhs = ((Pets) other);
        return new EqualsBuilder().append(pet, rhs.pet).append(additionalProperties, rhs.additionalProperties).isEquals();
    }

}
