
package com.markandersonix.localpets.Models.Search;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class SearchData implements Serializable
{

    private String encoding;
    private String version;
    private Petfinder petfinder;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();
    private final static long serialVersionUID = 3876351087073114230L;

    /**
     * 
     * @return
     *     The encoding
     */
    public String getEncoding() {
        return encoding;
    }

    /**
     * 
     * @param encoding
     *     The @encoding
     */
    public void setEncoding(String encoding) {
        this.encoding = encoding;
    }

    /**
     * 
     * @return
     *     The version
     */
    public String getVersion() {
        return version;
    }

    /**
     * 
     * @param version
     *     The @version
     */
    public void setVersion(String version) {
        this.version = version;
    }

    /**
     * 
     * @return
     *     The petfinder
     */
    public Petfinder getPetfinder() {
        return petfinder;
    }

    /**
     * 
     * @param petfinder
     *     The petfinder
     */
    public void setPetfinder(Petfinder petfinder) {
        this.petfinder = petfinder;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(encoding).append(version).append(petfinder).append(additionalProperties).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof SearchData) == false) {
            return false;
        }
        SearchData rhs = ((SearchData) other);
        return new EqualsBuilder().append(encoding, rhs.encoding).append(version, rhs.version).append(petfinder, rhs.petfinder).append(additionalProperties, rhs.additionalProperties).isEquals();
    }

}
