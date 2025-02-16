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
        if (objects.containsKey(name) || template.getInterpreterSymbol(name) == null) {
            return false;
        }

        objects.put(name, object);
        return true;
    }

    public boolean setObjectValue(String name, Object newVal) {
        if (!objects.containsKey(name)) {
            return false;
        }

        objects.put(name, newVal);
        return true;
    }

    public Object getObjectValue(String name) {
        return objects.get(name);
    }

    public LinkedHashMap<String, Object> getObjectsTable() {
        return this.objects;
    }

    public InterpreterObjectTemplate getTemplate() {
        return template;
    }
}
