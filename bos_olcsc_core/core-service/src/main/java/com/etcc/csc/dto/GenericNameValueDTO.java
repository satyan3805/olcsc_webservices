package com.etcc.csc.dto;

public class GenericNameValueDTO {
    private String name;
    private String value;
    
    public GenericNameValueDTO(String name,String value) {
        this.name=name;
        this.value=value;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
    
    public String toString(){
        return getValue();
    }
}
