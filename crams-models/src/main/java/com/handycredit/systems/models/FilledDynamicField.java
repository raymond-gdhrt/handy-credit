/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.handycredit.systems.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.pahappa.systems.constants.FieldType;
import org.sers.webutils.model.BaseEntity;

/**
 *
 * @author RayGdhrt
 */
@Entity
@Table(name = "filled_dynamic_field")
public class FilledDynamicField extends BaseEntity {

    private DynamicField dynamicField;
    private String fieldName;
    private String fieldValue;
    private FieldType fieldType;

    @ManyToOne
    @JoinColumn(name = "dynamic_field_id")
    public DynamicField getDynamicField() {
        return dynamicField;
    }

    public void setDynamicField(DynamicField dynamicField) {
        this.dynamicField = dynamicField;
    }

    @Column(name = "field_name")
    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    @Column(name = "field_value")
    public String getFieldValue() {
        return fieldValue;
    }

    public void setFieldValue(String fieldValue) {
        this.fieldValue = fieldValue;
    }

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "field_type")
    public FieldType getFieldType() {
        return fieldType;
    }

    public void setFieldType(FieldType fieldType) {
        this.fieldType = fieldType;
    }

}
