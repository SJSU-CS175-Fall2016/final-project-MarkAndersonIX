
package com.markandersonix.localpets.Models.Search;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class Petfinder implements Serializable
{

    private String xmlnsXsi;
    private LastOffset lastOffset;
    private Pets pets;
    private Header header;
    private String xsiNoNamespaceSchemaLocation;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();
    private final static long serialVersionUID = -8164279394079798211L;

    /**
     * 
     * @return
     *     The xmlnsXsi
     */
    public String getXmlnsXsi() {
        return xmlnsXsi;
    }

    /**
     * 
     * @param xmlnsXsi
     *     The @xmlns:xsi
     */
    public void setXmlnsXsi(String xmlnsXsi) {
        this.xmlnsXsi = xmlnsXsi;
    }

    /**
     * 
     * @return
     *     The lastOffset
     */
    public LastOffset getLastOffset() {
        return lastOffset;
    }

    /**
     * 
     * @param lastOffset
     *     The lastOffset
     */
    public void setLastOffset(LastOffset lastOffset) {
        this.lastOffset = lastOffset;
    }

    /**
     * 
     * @return
     *     The pets
     */
    public Pets getPets() {
        return pets;
    }

    /**
     * 
     * @param pets
     *     The pets
     */
    public void setPets(Pets pets) {
        this.pets = pets;
    }

    /**
     * 
     * @return
     *     The header
     */
    public Header getHeader() {
        return header;
    }

    /**
     * 
     * @param header
     *     The header
     */
    public void setHeader(Header header) {
        this.header = header;
    }

    /**
     * 
     * @return
     *     The xsiNoNamespaceSchemaLocation
     */
    public String getXsiNoNamespaceSchemaLocation() {
        return xsiNoNamespaceSchemaLocation;
    }

    /**
     * 
     * @param xsiNoNamespaceSchemaLocation
     *     The @xsi:noNamespaceSchemaLocation
     */
    public void setXsiNoNamespaceSchemaLocation(String xsiNoNamespaceSchemaLocation) {
        this.xsiNoNamespaceSchemaLocation = xsiNoNamespaceSchemaLocation;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(xmlnsXsi).append(lastOffset).append(pets).append(header).append(xsiNoNamespaceSchemaLocation).append(additionalProperties).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof Petfinder) == false) {
            return false;
        }
        Petfinder rhs = ((Petfinder) other);
        return new EqualsBuilder().append(xmlnsXsi, rhs.xmlnsXsi).append(lastOffset, rhs.lastOffset).append(pets, rhs.pets).append(header, rhs.header).append(xsiNoNamespaceSchemaLocation, rhs.xsiNoNamespaceSchemaLocation).append(additionalProperties, rhs.additionalProperties).isEquals();
    }

}
