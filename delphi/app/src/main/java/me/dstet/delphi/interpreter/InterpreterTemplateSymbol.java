package me.dstet.delphi.interpreter;

public class InterpreterTemplateSymbol implements IInterpreterSymbol {
    Visibility visibility;
    String templateName;

    public Visibility getVisibility() {
        return visibility;
    }
}
