package me.dstet.delphi;

import org.junit.Test;

import me.dstet.delphi.interpreter.InterpreterObject;
import me.dstet.delphi.interpreter.InterpreterObjectTemplate;
import me.dstet.delphi.interpreter.InterpreterPrimitiveSymbol;
import me.dstet.delphi.interpreter.InterpreterTemplateSymbol;
import me.dstet.delphi.interpreter.Primitive;
import me.dstet.delphi.interpreter.PrimitiveUtils;
import me.dstet.delphi.interpreter.Visibility;

import static org.junit.Assert.*;

public class SymbolTableTest {
    @Test public void TestSymbolTableConstructor() {
        Visitor visitor =  new Visitor<>(true);
        SymbolTable table = new SymbolTable(visitor);
        
        table.addObjectTemplate("obj");
        assertNotNull(table.getTemplateDeclaredOnTable("obj"));
    }

    @Test public void TestAddDuplicateTemplate() {
        Visitor visitor =  new Visitor<>(true);
        SymbolTable table = new SymbolTable(visitor);

        table.addObjectTemplate("obj");
        
        assertFalse(table.addObjectTemplate("obj"));
    }

    @Test public void TestAddDuplicateSymbolToTemplate() {
        Visitor visitor =  new Visitor<>(true);
        SymbolTable table = new SymbolTable(visitor);

        table.addObjectTemplate("obj");
        InterpreterObjectTemplate template = table.getTemplateDeclaredOnTable("obj");
        
        assertTrue(template.addInterpreterSymbol("obj", null));
        assertFalse(template.addInterpreterSymbol("obj", null));
    }

    @Test public void TestUseObjectScopeForSymbolTable() {
        Visitor visitor = new Visitor<>(true);
        SymbolTable table = new SymbolTable(visitor);
        String objectName = "obj";
        String objectVariableName = "objInstance";
        String objectSymbolName = "intVal";

        table.addObjectTemplate(objectName);
        InterpreterObjectTemplate template = table.getTemplateDeclaredOnTable(objectName);
        template.addInterpreterSymbol(objectSymbolName, new InterpreterPrimitiveSymbol(Visibility.PUBLIC, Primitive.Integer));
        table.addVariableToVars(objectVariableName, new InterpreterTemplateSymbol(Visibility.PUBLIC, objectName));
        
        // Get template in the fashion it would be retrieved in the Visitor class
        template = table.getTemplateDeclaredOnTable(((InterpreterTemplateSymbol)table.getVariable(objectVariableName)).getTemplateName());
        InterpreterObject obj = template.instantiateObject(table);
        table.setVariable(objectVariableName, obj);

        Object preCollectedValue = table.getClassVariableValue(objectVariableName, objectSymbolName);

        table.setClassVariableValue(objectVariableName, objectSymbolName, Integer.valueOf(5));

        SymbolTable newScopedTable = new SymbolTable(visitor, obj.getObjectsTable(), null);
        Object newCollectedValue = newScopedTable.getVariable(objectSymbolName);

        assertEquals(((Integer) preCollectedValue).intValue(), ((Integer) PrimitiveUtils.getDefaultFromPrimitive(Primitive.Integer)).intValue());
        assertEquals(((Integer) newCollectedValue).intValue(), Integer.valueOf(5).intValue());
    }
}
