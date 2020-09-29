package com.fmi.bytecode.annotations.gui.businesslogic.model.searchfilters;

public enum FilterLevelType {
    CLASS("C"),
    METHOD("M"),
    FIELD("F"),
    CONSTRUCTOR("I"),
    PARAMETER("P");
    
    private String name;
    
    FilterLevelType(String _name) {
        name = _name;
    }
    
    public String toString() {
        return name;
    }
}
