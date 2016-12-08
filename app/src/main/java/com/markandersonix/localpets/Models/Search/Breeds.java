
package com.markandersonix.localpets.Models.Search;

import java.io.Serializable;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class Breeds implements Serializable
{
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();
    private final static long serialVersionUID = 9038031747005093067L;
	private List<Breed> breed;

    public Breeds(Breed ... breed){
        this.breed = Arrays.asList(breed);
    }
    /**
     * 
     * @return
     *     The breed
     */
    public List<Breed> getBreed() {
        return breed;
    }

    @Override
    public String toString() {
        String result = "";
        for(Breed b: breed){
            result+=b.get$t().toString()+" ";
        }
        return result;
    }

    /**
     * 
     * @param breed
     *     The breed
     */
    public void setBreed(Breed breed) {
        this.breed.add(breed);
    }
    public void setBreed(List<Breed> breed) {
        this.breed = breed;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(breed).append(additionalProperties).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof Breeds) == false) {
            return false;
        }
        Breeds rhs = ((Breeds) other);
        return new EqualsBuilder().append(breed, rhs.breed).append(additionalProperties, rhs.additionalProperties).isEquals();
    }

}
