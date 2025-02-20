package me.dstet.delphi.interpreter;

import java.util.ArrayList;

import me.dstet.delphi.SymbolTable;

public class InterpreterFunctionSymbol implements IInterpreterSymbol {
    Visibility visibility;
    ArrayList<InterpreterFunctionParameter> params;
    Primitive returnType;
    String complexReturnType;

    InterpreterFunction function;

    public Visibility getVisibility() {
        return visibility;
    }

    /**
     * Since functions are defined after being declared, constructor for the symbol will leave function as null for now.
     * @param visibility Function visibility.
     */
    public InterpreterFunctionSymbol(Visibility visibility, ArrayList<InterpreterFunctionParameter> params, Primitive returnType) {
        this.visibility = visibility;
        this.params = params;
        this.returnType = returnType;
        this.complexReturnType = null;
        function = null;
    }

    public InterpreterFunctionSymbol(Visibility visibility, ArrayList<InterpreterFunctionParameter> params, String complexReturnType) {
        this.visibility = visibility;
        this.params = params;
        this.returnType = Primitive.NotPrimitive;
        this.complexReturnType = complexReturnType;
        function = null;
    }

    public Object getReturnType() {
        if (returnType == Primitive.NotPrimitive) {
            return complexReturnType;
        } else {
            return returnType;
        }
    }

    public void setInterpreterFunction(InterpreterFunction function) {
        this.function = function;
    }

    public ArrayList<InterpreterFunctionParameter> getParams() {
        return params;
    }

    public void run(SymbolTable table) {
        function.run(table);
    }

    public void printSymbolInfo() {
        System.out.print("\tVisibility: ");
        System.out.print(visibility);
        System.out.print("\tReturn Type: ");
        if (returnType == Primitive.NotPrimitive) {
            System.out.print(complexReturnType);
        } else {
            System.out.print(returnType);
        }
        if (params != null && !params.isEmpty()) {
            System.out.print("\tParams: ");
            for (InterpreterFunctionParameter param : params) {
                System.out.print("\t");
                System.out.print(param.paramName);
                System.out.print(": ");
                System.out.print(param.paramType);
            }
        }
    }
}
