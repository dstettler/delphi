package me.dstet.delphi.interpreter;

public class InterpreterPrimitiveSymbol implements IInterpreterSymbol {
    Visibility visibility;
    Primitive primitiveType;

    public Visibility getVisibility() {
        return visibility;
    }

    public InterpreterPrimitiveSymbol(Visibility visibility, Primitive primitiveType) {
        this.visibility = visibility;
        this.primitiveType = primitiveType;
    }

    public void printSymbolInfo() {
        System.out.print("\tVisibility: ");
        System.out.print(visibility);
        System.out.print("\tType: ");
        System.out.print(primitiveType);
    }
}
