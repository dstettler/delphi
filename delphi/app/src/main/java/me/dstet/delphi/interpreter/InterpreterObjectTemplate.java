package me.dstet.delphi.interpreter;

import java.util.LinkedHashMap;
import java.util.Map.Entry;

import me.dstet.delphi.SymbolTable;

public class InterpreterObjectTemplate {
    LinkedHashMap<String, IInterpreterSymbol> objectSymbols;

    public IInterpreterSymbol getInterpreterSymbol(String symbolName) {
        return objectSymbols.get(symbolName);
    }

    public boolean isInterpreterSymbolOnTable(String symbolName) {
        return objectSymbols.containsKey(symbolName);
    }

    public boolean addInterpreterSymbol(String name, IInterpreterSymbol symbol) {
        if (objectSymbols.containsKey(name)) {
            return false;
        }
        
        objectSymbols.put(name, symbol);
        return true;
    }

    public void printObjectSymbols() {
        for (Entry<String, IInterpreterSymbol> entry : objectSymbols.entrySet()) {
            System.out.print(String.format("\t\t%s:\t", entry.getKey()));
            entry.getValue().printSymbolInfo();
            System.out.println("");
        }
    }

    public InterpreterObject instantiateObject(SymbolTable table) {
        InterpreterObject obj = new InterpreterObject(this);

        for (Entry<String, IInterpreterSymbol> entry : objectSymbols.entrySet()) {
            if (entry.getValue() instanceof InterpreterPrimitiveSymbol) {
                InterpreterPrimitiveSymbol symbol = (InterpreterPrimitiveSymbol) entry.getValue();
                Object primitiveObject = PrimitiveUtils.getDefaultFromPrimitive(symbol.primitiveType);
                obj.addObjectSymbol(entry.getKey(), primitiveObject);
            } else if (entry.getValue() instanceof InterpreterTemplateSymbol) {
                InterpreterTemplateSymbol symbol = (InterpreterTemplateSymbol) entry.getValue();
                InterpreterObjectTemplate symbolTemplate = table.getTemplateDeclaredOnTable(symbol.templateName);
                if (symbolTemplate != null) {
                    obj.addObjectSymbol(entry.getKey(), null);
                }
            }
        }

        return obj;
    }

    public InterpreterObjectTemplate() {
        objectSymbols = new LinkedHashMap<>();
    }
}
