package me.dstet.delphi;

import java.util.LinkedHashMap;

public class LanguageObjectTemplate {
    LinkedHashMap<String, LanguageFunction> methods;
    LinkedHashMap<String, LanguageFunction> staticMethods;

    LinkedHashMap<String, LanguageObjectProperty> baseProperties;
    LinkedHashMap<String, LanguageObjectProperty> constants;

    public LanguageObjectTemplate() {
        methods = new LinkedHashMap<>();
        staticMethods = new LinkedHashMap<>();
        baseProperties = new LinkedHashMap<>();
        constants = new LinkedHashMap<>();
    }
}
