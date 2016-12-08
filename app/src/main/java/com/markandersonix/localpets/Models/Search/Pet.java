
package com.markandersonix.localpets.Models.Search;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class Pet implements Serializable
{

    private Options options;
    private Status status;
    private Contact contact;
    private Age age;
    private Size size;
    private Media media;
    private Id id;
    private ShelterPetId shelterPetId;
    private Breeds breeds;
    private Name name;
    private Sex sex;
    private Description description;
    private Mix mix;
    private ShelterId shelterId;
    private LastUpdate lastUpdate;
    private Animal animal;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();
    private final static long serialVersionUID = 3362768445705480583L;

    /**
     * 
     * @return
     *     The options
     */
    public Options getOptions() {
        return options;
    }

    /**
     * 
     * @param options
     *     The options
     */
    public void setOptions(Options options) {
        this.options = options;
    }

    /**
     * 
     * @return
     *     The status
     */
    public Status getStatus() {
        return status;
    }

    /**
     * 
     * @param status
     *     The status
     */
    public void setStatus(Status status) {
        this.status = status;
    }

    /**
     * 
     * @return
     *     The contact
     */
    public Contact getContact() {
        return contact;
    }

    /**
     * 
     * @param contact
     *     The contact
     */
    public void setContact(Contact contact) {
        this.contact = contact;
    }

    /**
     * 
     * @return
     *     The age
     */
    public Age getAge() {
        return age;
    }

    /**
     * 
     * @param age
     *     The age
     */
    public void setAge(Age age) {
        this.age = age;
    }

    /**
     * 
     * @return
     *     The size
     */
    public Size getSize() {
        return size;
    }

    /**
     * 
     * @param size
     *     The size
     */
    public void setSize(Size size) {
        this.size = size;
    }

    /**
     * 
     * @return
     *     The media
     */
    public Media getMedia() {
        return media;
    }

    /**
     * 
     * @param media
     *     The media
     */
    public void setMedia(Media media) {
        this.media = media;
    }

    /**
     * 
     * @return
     *     The id
     */
    public Id getId() {
        return id;
    }

    /**
     * 
     * @param id
     *     The id
     */
    public void setId(Id id) {
        this.id = id;
    }

    /**
     * 
     * @return
     *     The shelterPetId
     */
    public ShelterPetId getShelterPetId() {
        return shelterPetId;
    }

    /**
     * 
     * @param shelterPetId
     *     The shelterPetId
     */
    public void setShelterPetId(ShelterPetId shelterPetId) {
        this.shelterPetId = shelterPetId;
    }

    /**
     * 
     * @return
     *     The breeds
     */
    public Breeds getBreeds() {
        return breeds;
    }

    /**
     * 
     * @param breeds
     *     The breeds
     */
    public void setBreeds(Breeds breeds) {
        this.breeds = breeds;
    }

    /**
     * 
     * @return
     *     The name
     */
    public Name getName() {
        return name;
    }

    /**
     * 
     * @param name
     *     The name
     */
    public void setName(Name name) {
        this.name = name;
    }

    /**
     * 
     * @return
     *     The sex
     */
    public Sex getSex() {
        return sex;
    }

    /**
     * 
     * @param sex
     *     The sex
     */
    public void setSex(Sex sex) {
        this.sex = sex;
    }

    /**
     * 
     * @return
     *     The description
     */
    public Description getDescription() {
        return description;
    }

    /**
     * 
     * @param description
     *     The description
     */
    public void setDescription(Description description) {
        this.description = description;
    }

    /**
     * 
     * @return
     *     The mix
     */
    public Mix getMix() {
        return mix;
    }

    /**
     * 
     * @param mix
     *     The mix
     */
    public void setMix(Mix mix) {
        this.mix = mix;
    }

    /**
     * 
     * @return
     *     The shelterId
     */
    public ShelterId getShelterId() {
        return shelterId;
    }

    /**
     * 
     * @param shelterId
     *     The shelterId
     */
    public void setShelterId(ShelterId shelterId) {
        this.shelterId = shelterId;
    }

    /**
     * 
     * @return
     *     The lastUpdate
     */
    public LastUpdate getLastUpdate() {
        return lastUpdate;
    }

    /**
     * 
     * @param lastUpdate
     *     The lastUpdate
     */
    public void setLastUpdate(LastUpdate lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    /**
     * 
     * @return
     *     The animal
     */
    public Animal getAnimal() {
        return animal;
    }

    /**
     * 
     * @param animal
     *     The animal
     */
    public void setAnimal(Animal animal) {
        this.animal = animal;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(options).append(status).append(contact).append(age).append(size).append(media).append(id).append(shelterPetId).append(breeds).append(name).append(sex).append(description).append(mix).append(shelterId).append(lastUpdate).append(animal).append(additionalProperties).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof Pet) == false) {
            return false;
        }
        Pet rhs = ((Pet) other);
        return new EqualsBuilder().append(options, rhs.options).append(status, rhs.status).append(contact, rhs.contact).append(age, rhs.age).append(size, rhs.size).append(media, rhs.media).append(id, rhs.id).append(shelterPetId, rhs.shelterPetId).append(breeds, rhs.breeds).append(name, rhs.name).append(sex, rhs.sex).append(description, rhs.description).append(mix, rhs.mix).append(shelterId, rhs.shelterId).append(lastUpdate, rhs.lastUpdate).append(animal, rhs.animal).append(additionalProperties, rhs.additionalProperties).isEquals();
    }

}
