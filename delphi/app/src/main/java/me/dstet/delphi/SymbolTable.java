package me.dstet.delphi;

import java.util.LinkedHashMap;
import java.util.Map.Entry;

import org.stringtemplate.v4.compiler.STParser.templateAndEOF_return;

import me.dstet.delphi.interpreter.InterpreterConstantSymbol;
import me.dstet.delphi.interpreter.InterpreterFunction;
import me.dstet.delphi.interpreter.InterpreterObject;
import me.dstet.delphi.interpreter.InterpreterObjectTemplate;
import me.dstet.delphi.interpreter.Visibility;

public class SymbolTable {
    public LinkedHashMap<String, InterpreterObjectTemplate> objectTemplates;
    public LinkedHashMap<String, InterpreterFunction> topLevelFunctions;
    public LinkedHashMap<String, Object> constants;
    public LinkedHashMap<String, Object> variables;
    public LinkedHashMap<String, String> functionCallStackMappings;

    private Visitor parent;

    public SymbolTable(Visitor parent) {
        objectTemplates = new LinkedHashMap<>();
        variables = new LinkedHashMap<>();
        topLevelFunctions = new LinkedHashMap<>();
        constants = new LinkedHashMap<>();
        this.parent =  parent;
    }

    public Visitor getParent() {
        return parent;
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

        objectTemplates.put(objectName, new InterpreterObjectTemplate());
        
        return true;
    }

    public boolean addConstantToObjectTemplate(String templateName, String constantName, Object value, Visibility visibility) {
        InterpreterConstantSymbol symbol = new InterpreterConstantSymbol(visibility, value);
        
        InterpreterObjectTemplate template = objectTemplates.get(templateName);
        if (template == null || template.getInterpreterSymbol(constantName) != null) {
            return false;
        }

        template.addInterpreterSymbol(constantName, symbol);
        
        return true;
    }

    public boolean addConstantToConstants(String constantName, Object constantValue) {
        if (constants.get(constantName) != null) {
            return false;
        }

        constants.put(constantName, constantValue);
        
        return true;
    }

    public boolean addVariableToVars(String variableName, Object varObject) {
        if (variables.get(variableName) != null) {
            return false;
        }

        variables.put(variableName, varObject);
        
        return true;
    }

    public InterpreterObjectTemplate getTemplateDeclaredOnTable(String type) {
        return objectTemplates.get(type);
    }

    /* public LinkedHashMap<String, InterpreterObjectTemplate> objectTemplates;
    public LinkedHashMap<String, InterpreterObject> objects;
    public LinkedHashMap<String, InterpreterFunction> topLevelFunctions;
    public LinkedHashMap<String, Object> constants;
    public LinkedHashMap<String, Object> variables;
    public LinkedHashMap<String, String> functionCallStackMappings; */

    public void printSymbolTable() {
        System.out.println("Object Templates:");
        for (Entry<String, InterpreterObjectTemplate> entry : objectTemplates.entrySet()) {
            System.out.println(String.format("\t%s:", entry.getKey()));
            InterpreterObjectTemplate template = entry.getValue();
            template.printObjectSymbols();
        }
    }
}
