
package com.markandersonix.localpets.Models.Search;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class Contact implements Serializable
{

    private Phone phone;
    private State state;
    private Address2 address2;
    private Email email;
    private City city;
    private Zip zip;
    private Fax fax;
    private Address1 address1;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();
    private final static long serialVersionUID = -12230019681364734L;

    /**
     * 
     * @return
     *     The phone
     */
    public Phone getPhone() {
        return phone;
    }

    /**
     * 
     * @param phone
     *     The phone
     */
    public void setPhone(Phone phone) {
        this.phone = phone;
    }

    /**
     * 
     * @return
     *     The state
     */
    public State getState() {
        return state;
    }

    /**
     * 
     * @param state
     *     The state
     */
    public void setState(State state) {
        this.state = state;
    }

    /**
     * 
     * @return
     *     The address2
     */
    public Address2 getAddress2() {
        return address2;
    }

    /**
     * 
     * @param address2
     *     The address2
     */
    public void setAddress2(Address2 address2) {
        this.address2 = address2;
    }

    /**
     * 
     * @return
     *     The email
     */
    public Email getEmail() {
        return email;
    }

    /**
     * 
     * @param email
     *     The email
     */
    public void setEmail(Email email) {
        this.email = email;
    }

    /**
     * 
     * @return
     *     The city
     */
    public City getCity() {
        return city;
    }

    /**
     * 
     * @param city
     *     The city
     */
    public void setCity(City city) {
        this.city = city;
    }

    /**
     * 
     * @return
     *     The zip
     */
    public Zip getZip() {
        return zip;
    }

    /**
     * 
     * @param zip
     *     The zip
     */
    public void setZip(Zip zip) {
        this.zip = zip;
    }

    /**
     * 
     * @return
     *     The fax
     */
    public Fax getFax() {
        return fax;
    }

    /**
     * 
     * @param fax
     *     The fax
     */
    public void setFax(Fax fax) {
        this.fax = fax;
    }

    /**
     * 
     * @return
     *     The address1
     */
    public Address1 getAddress1() {
        return address1;
    }

    /**
     * 
     * @param address1
     *     The address1
     */
    public void setAddress1(Address1 address1) {
        this.address1 = address1;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(phone).append(state).append(address2).append(email).append(city).append(zip).append(fax).append(address1).append(additionalProperties).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof Contact) == false) {
            return false;
        }
        Contact rhs = ((Contact) other);
        return new EqualsBuilder().append(phone, rhs.phone).append(state, rhs.state).append(address2, rhs.address2).append(email, rhs.email).append(city, rhs.city).append(zip, rhs.zip).append(fax, rhs.fax).append(address1, rhs.address1).append(additionalProperties, rhs.additionalProperties).isEquals();
    }

}
