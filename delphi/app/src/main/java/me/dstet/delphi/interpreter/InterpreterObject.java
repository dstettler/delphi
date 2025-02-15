package me.dstet.delphi.interpreter;

import java.util.LinkedHashMap;

public class InterpreterObject {
    InterpreterObjectTemplate template;

    LinkedHashMap<String, Object> objects;

    public InterpreterObject(InterpreterObjectTemplate template) {
        this.template = template;
        objects = new LinkedHashMap<>();
    }

    public boolean addObjectSymbol(String name, Object object) {
        if (objects.get(name) != null || template.getInterpreterSymbol(name) == null) {
            return false;
        }

        return true;
    }

    public InterpreterObjectTemplate getTemplate() {
        return template;
    }
}
