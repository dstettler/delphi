package me.dstet.delphi.interpreter;

public class InterpreterFunctionParameter {
    public Primitive paramType;
    public String paramName;

    public InterpreterFunctionParameter(Primitive type, String name) {
        this.paramType = type;
        this.paramName = name;
    }
}
