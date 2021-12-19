/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.pahappa.systems.constants;

/**
 *
 * @author HP
 */
public enum FieldType {
    
    TEXT("Short Text"),
    TEXTAREA("Large Text"),
    RADIO("Radio button"),
    DATE("Date"),
    NUMBER("Number"),
    SECRET("Password"),
    BOOLEAN("True/False"),
    SELECTMANY("Multiple select"),
    SELECTONE("Select one"),
    ATTACHMENT("Attachment");
            
    
    private final String description;
    
    private FieldType(String name){
    this.description=name;
    }
    
    @Override
    public String toString() {
        return this.description;
    }
}
