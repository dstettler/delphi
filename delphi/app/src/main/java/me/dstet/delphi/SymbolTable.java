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
    public LinkedHashMap<String, Object> functionCallArgs;

    private Visitor parent;

    public SymbolTable(Visitor parent) {
        objectTemplates = new LinkedHashMap<>();
        variables = new LinkedHashMap<>();
        topLevelFunctions = new LinkedHashMap<>();
        constants = new LinkedHashMap<>();
        functionCallArgs = null;
        this.parent = parent;
    }

    public SymbolTable(Visitor parent, LinkedHashMap<String, Object> variables, LinkedHashMap<String, Object> functionCallArgs) {
        this.variables = variables;
        this.parent = parent;
        this.functionCallArgs = functionCallArgs;
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
        if (objectTemplates.containsKey(objectName)) {
            return false;
        }

        objectTemplates.put(objectName, new InterpreterObjectTemplate());
        
        return true;
    }

    public boolean addConstantToObjectTemplate(String templateName, String constantName, Object value, Visibility visibility) {
        InterpreterConstantSymbol symbol = new InterpreterConstantSymbol(visibility, value);
        
        InterpreterObjectTemplate template = objectTemplates.get(templateName);
        if (template == null || template.isInterpreterSymbolOnTable(constantName)) {
            return false;
        }

        return template.addInterpreterSymbol(constantName, symbol);
    }

    public boolean addConstantToConstants(String constantName, Object constantValue) {
        if (constants.containsKey(constantName)) {
            return false;
        }

        constants.put(constantName, constantValue);
        
        return true;
    }

    public boolean addVariableToVars(String variableName, Object varObject) {
        System.out.println(variables);
        if (variables.containsKey(variableName)) {
            return false;
        }

        variables.put(variableName, varObject);
        System.out.println(variables);

        return true;
    }

    public Object getVariable(String variableName) {
        return variables.get(variableName);
    }

    public boolean setVariable(String variableName, Object modifiedValue) {
        if (!variables.containsKey(variableName)) {
            return false;
        }

        variables.put(variableName, modifiedValue);
        return true;
    }

    public InterpreterObjectTemplate getTemplateDeclaredOnTable(String type) {
        return objectTemplates.get(type);
    }

    public boolean setClassVariableValue(String classVar, String variableName, Object newValue) {
        Object var = variables.get(classVar);
        if (var instanceof InterpreterObject) {
            InterpreterObject obj = (InterpreterObject) var;
            return obj.setObjectValue(variableName, newValue);
        } else {
            return false;
        }
    }

    public Object getClassVariableValue (String classVar, String variableName) {
        Object var = variables.get(classVar);
        if (var instanceof InterpreterObject) {
            InterpreterObject obj = (InterpreterObject) var;
            return obj.getObjectValue(variableName);
        } else {
            return null;
        }
    }

    public void printSymbolTable() {
        System.out.println("Object Templates:");
        for (Entry<String, InterpreterObjectTemplate> entry : objectTemplates.entrySet()) {
            System.out.println(String.format("\t%s:", entry.getKey()));
            InterpreterObjectTemplate template = entry.getValue();
            template.printObjectSymbols();
        }
    }

    public boolean addFunctionToTable(String name, InterpreterFunction function) {
        if (topLevelFunctions.containsKey(name)) {
            return false;
        }

        topLevelFunctions.put(name, function);
        return true;
    }

    public LinkedHashMap<String, Object> getFunctionArgs() {
        return this.functionCallArgs;
    }

    public void setFunctionArgs(LinkedHashMap<String, Object> args) {
        this.functionCallArgs = args;
    }

    public void resetFunctionArgs() {
        this.functionCallArgs = null;
    }
}
