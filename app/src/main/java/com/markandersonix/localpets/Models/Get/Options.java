
package com.markandersonix.localpets.Models.Get;

import com.markandersonix.localpets.Models.Get.Option;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.io.Serializable;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Options implements Serializable
{

    private List<com.markandersonix.localpets.Models.Get.Option> option = null;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();
    private final static long serialVersionUID = -2340386023595997890L;

    public Options(com.markandersonix.localpets.Models.Get.Option... option){
        this.option = Arrays.asList(option);
    }

    @Override
    public String toString() {
        String result = "";
        for(com.markandersonix.localpets.Models.Get.Option b: option){
            result+=b.get$t().toString()+" ";
        }
        return result;
    }
    public List<com.markandersonix.localpets.Models.Get.Option> getOption() {
        return option;
    }

    /**
     *
     * @param option
     *     The option
     */
    public void setOption(List<com.markandersonix.localpets.Models.Get.Option> option) {
        this.option = option;
    }
    public void setOption(Option option) {
        this.option.add(option);
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(option).append(additionalProperties).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof Options) == false) {
            return false;
        }
        Options rhs = ((Options) other);
        return new EqualsBuilder().append(option, rhs.option).append(additionalProperties, rhs.additionalProperties).isEquals();
    }

}
