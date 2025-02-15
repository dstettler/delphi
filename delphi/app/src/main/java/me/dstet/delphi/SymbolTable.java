package me.dstet.delphi;

import java.util.LinkedHashMap;

import org.stringtemplate.v4.compiler.STParser.templateAndEOF_return;

public class SymbolTable {
    private LinkedHashMap<String, LanguageObjectTemplate> objectTemplates;
    private LinkedHashMap<String, LanguageObject> objects;
    private LinkedHashMap<String, LanguageFunction> topLevelFunctions;
    private LinkedHashMap<String, Object> constants;
    private LinkedHashMap<String, Object> variables;

    public SymbolTable() {
        objectTemplates = new LinkedHashMap<>();
        objects = new LinkedHashMap<>();
        topLevelFunctions = new LinkedHashMap<>();
        constants = new LinkedHashMap<>();
    }

    /**
     * Adds a blank object template of given name to the template table.
     * @param objectName Name of object to add to template table.
     * @return True if successful, false if name is duplicate.
     */
    public boolean addObjectTemplate(String objectName) {
        if (objectTemplates.get(objectName) != null) {
            return false;
        }

        objectTemplates.put(objectName, new LanguageObjectTemplate());
        
        return true;
    }

    public boolean addConstantToObjectTemplate(String templateName, String constantName, LanguageObjectProperty constantValue) {
        LanguageObjectTemplate template = objectTemplates.get(templateName);
        if (template == null || template.constants.get(constantName) != null) {
            return false;
        }

        template.constants.put(constantName, constantValue);
        
        return true;
    }

    public boolean addPropertyToObjectTemplate(String templateName, String propertyName, LanguageObjectProperty propertyValue) {
        LanguageObjectTemplate template = objectTemplates.get(templateName);
        if (template == null || template.baseProperties.get(propertyName) != null) {
            return false;
        }

        template.baseProperties.put(propertyName, propertyValue);
        
        return true;
    }

    public boolean addMethodToObjectTemplate(String templateName, String methodName, LanguageFunction methodFunction) {
        LanguageObjectTemplate template = objectTemplates.get(templateName);
        if (template == null || template.methods.get(methodName) != null || template.staticMethods.get(methodName) != null) {
            return false;
        }

        template.methods.put(methodName, methodFunction);
        
        return true;
    }

    public boolean addClassMethodToObjectTemplate(String templateName, String methodName, LanguageFunction methodFunction) {
        LanguageObjectTemplate template = objectTemplates.get(templateName);
        if (template == null || template.methods.get(methodName) != null || template.staticMethods.get(methodName) != null) {
            return false;
        }

        template.staticMethods.put(methodName, methodFunction);
        
        return true;
    }

    public boolean addConstantToConstants(String constantName, Object constantValue) {
        if (constants.get(constantName) != null) {
            return false;
        }

        constants.put(constantName, constantValue);
        
        return true;
    }

    public LanguageObjectTemplate getTemplateDeclaredOnTable(String type) {
        return objectTemplates.get(type);
    }
}
