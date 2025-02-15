package me.dstet.delphi;

import java.util.LinkedHashMap;

public class LanguageObject {
    String objectType;
    Object value;

    public LanguageObject(String type, Object value) {
        objectType = type;
        this.value = value;
    }
}
