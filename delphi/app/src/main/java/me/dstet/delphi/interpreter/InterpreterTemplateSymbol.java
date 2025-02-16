package me.dstet.delphi.interpreter;

public class InterpreterTemplateSymbol implements IInterpreterSymbol {
    Visibility visibility;
    String templateName;

    public Visibility getVisibility() {
        return visibility;
    }

    public InterpreterTemplateSymbol(Visibility visibility, String symbolString) {
        this.visibility = visibility;
        this.templateName = symbolString;
    }

    public String getTemplateName() {
        return templateName;
    }

    public void printSymbolInfo() {
        System.out.print("\tVisibility: ");
        System.out.print(visibility);
        System.out.print(String.format("\tType: %s", templateName));
    }
}
