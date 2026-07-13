package com.etcc.csc.common;

public class IntEnum {
    private int value;
    
    protected IntEnum() {
    }
    
    protected IntEnum(int x){
        value = x;
    }
    
    public int getValue(){
        return value;
    }
    
    public boolean equals(int x){
        return value ==  x;
    }
}
