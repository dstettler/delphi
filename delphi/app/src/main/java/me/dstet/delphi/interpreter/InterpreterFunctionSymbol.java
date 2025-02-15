package me.dstet.delphi.interpreter;

public class InterpreterFunctionSymbol implements IInterpreterSymbol {
    Visibility visibility;
    InterpreterFunction function;

    public Visibility getVisibility() {
        return visibility;
    }
}
