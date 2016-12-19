
package com.markandersonix.localpets.Models.Get;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.lang3.builder.ToStringBuilder;
public class Petfinder implements Serializable
{

    private Pet pet;
    private String xmlnsXsi;
    private Header header;
    private String xsiNoNamespaceSchemaLocation;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();
    private final static long serialVersionUID = -4347472759787192896L;

    public Pet getPet() {
        return pet;
    }

    public void setPet(Pet pet) {
        this.pet = pet;
    }

    public String getXmlnsXsi() {
        return xmlnsXsi;
    }

    public void setXmlnsXsi(String xmlnsXsi) {
        this.xmlnsXsi = xmlnsXsi;
    }

    public Header getHeader() {
        return header;
    }

    public void setHeader(Header header) {
        this.header = header;
    }

    public String getXsiNoNamespaceSchemaLocation() {
        return xsiNoNamespaceSchemaLocation;
    }

    public void setXsiNoNamespaceSchemaLocation(String xsiNoNamespaceSchemaLocation) {
        this.xsiNoNamespaceSchemaLocation = xsiNoNamespaceSchemaLocation;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
