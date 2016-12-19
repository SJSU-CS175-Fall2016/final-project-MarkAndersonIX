
package com.markandersonix.localpets.Models.Get;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class GetData implements Serializable
{

    private String encoding;
    private String version;
    private Petfinder petfinder;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();
    private final static long serialVersionUID = 6807639615447907030L;

    public String getEncoding() {
        return encoding;
    }

    public void setEncoding(String encoding) {
        this.encoding = encoding;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public Petfinder getPetfinder() {
        return petfinder;
    }

    public void setPetfinder(Petfinder petfinder) {
        this.petfinder = petfinder;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
