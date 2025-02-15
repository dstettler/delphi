package me.dstet.delphi.interpreter;

public class InterpreterConstantSymbol implements IInterpreterSymbol {
    Visibility visibility;
    Object value;

    public Visibility getVisibility() {
        return visibility;
    }

    public InterpreterConstantSymbol(Visibility visibility, Object value) {
        this.visibility = visibility;
        this.value = value;
    }

    public void printSymbolInfo() {
        System.out.print("\tVisibility: ");
        System.out.print(visibility);
        System.out.print("\tValue: ");
        System.out.print(value);
    }
}
