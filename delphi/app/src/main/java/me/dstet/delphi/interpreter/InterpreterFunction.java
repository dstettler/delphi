package me.dstet.delphi.interpreter;

import java.lang.reflect.Array;
import java.util.ArrayList;

import me.dstet.delphi.SymbolTable;
import me.dstet.delphi.Visitor;
import me.dstet.delphi.delphiParser.StatementContext;

public class InterpreterFunction {
    ArrayList<StatementContext> functionStatements;

    public InterpreterFunction() {
        functionStatements = new ArrayList<>();
    }

    public void addStatementToFunction(StatementContext statement) {
        functionStatements.add(statement);
    }

    public void run(SymbolTable symbolTable) {
        for (StatementContext ctx : functionStatements) {
            symbolTable.getParent().visitStatement(ctx);
        }
    }
}
