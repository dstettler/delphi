package me.dstet.delphi;

import me.dstet.delphi.interpreter.Visibility;

public class LanguageObjectProperty {
    Object value;
    Visibility visibility;

    public LanguageObjectProperty(Visibility visibility, Object propertyObject) {
        this.value = propertyObject;
        this.visibility = visibility;
    }

    public void setValue(Object newPropertyValue) {
        value = newPropertyValue;
    }

    public Object getPropertyValue() {
        return value;
    }
}
