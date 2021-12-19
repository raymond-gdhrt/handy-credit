package com.handycredit.systems.models;

import java.util.Set;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import org.pahappa.systems.constants.FieldType;
import org.sers.webutils.model.BaseEntity;

@Entity
@Table(name = "dynamic_fields")
public class DynamicField extends BaseEntity {

    private String label; // label of the field
    private int position; // position in form
    private String value; // the value of field
    private FieldType type; // can be input,radio,selectbox etc
    private Set<String> options;
    
    private Boolean required=false;
    private int maximumLength;
    private String placeHolder;
    private String defaultValue;
    private String minimumLength;
    private String validationRegex;

    // Getters + setters.
    @Column(name = "label")
    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    @Column(name = "position")
    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    @Column(name = "value")
    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "type")
    public FieldType getType() {
        return type;
    }

    public void setType(FieldType type) {
        this.type = type;
    }

    @ElementCollection(targetClass = String.class)
    @CollectionTable(name = "tags")
    public Set<String> getOptions() {
        return options;
    }

    public void setOptions(Set<String> options) {
        this.options = options;
    }

  
    @Column(name = "is_required")
    public Boolean getRequired() {
        return required;
    }

    public void setRequired(Boolean required) {
        this.required = required;
    }

    @Column(name = "max_length")
    public int getMaximumLength() {
        return maximumLength;
    }

    public void setMaximumLength(int maximumLength) {
        this.maximumLength = maximumLength;
    }

    @Column(name = "place_holder")
    public String getPlaceHolder() {
        return placeHolder;
    }

    public void setPlaceHolder(String placeHolder) {
        this.placeHolder = placeHolder;
    }

    @Column(name = "default_value")
    public String getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    @Column(name = "minimum_length")
    public String getMinimumLength() {
        return minimumLength;
    }

    public void setMinimumLength(String minimumLength) {
        this.minimumLength = minimumLength;
    }

    @Column(name = "validation_regex")
    public String getValidationRegex() {
        return validationRegex;
    }

    public void setValidationRegex(String validationRegex) {
        this.validationRegex = validationRegex;
    }

    public boolean checkFieldVisibility(FieldType fieldType, String fieldName) {
        if (FieldType.valueOf(fieldName) != null) {
            return FieldType.valueOf(fieldName).equals(fieldType);
        } else {
            return false;
        }
    }

    @Override
    public String toString() {
        return  label ;
    }

    
    
    @Override
    public boolean equals(Object object) {
        return object instanceof DynamicField && (super.getId() != null) ? super.getId().equals(((DynamicField) object).getId())
                : (object == this);
    }

    @Override
    public int hashCode() {
        return super.getId() != null ? this.getClass().hashCode() + super.getId().hashCode() : super.hashCode();
    }

}
