package me.dstet.delphi.interpreter;

public class InterpreterPrimitiveSymbol implements IInterpreterSymbol {
    Visibility visibility;
    Primitive primitiveType;

    public Visibility getVisibility() {
        return visibility;
    }
}
