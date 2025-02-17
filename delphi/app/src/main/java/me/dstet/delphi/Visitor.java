package me.dstet.delphi;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Scanner;
import java.util.regex.Pattern;

import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.RuleNode;
import org.antlr.v4.runtime.tree.TerminalNode;

import me.dstet.delphi.delphiParser.*;
import me.dstet.delphi.interpreter.IInterpreterSymbol;
import me.dstet.delphi.interpreter.InterpreterFunction;
import me.dstet.delphi.interpreter.InterpreterFunctionParameter;
import me.dstet.delphi.interpreter.InterpreterFunctionSymbol;
import me.dstet.delphi.interpreter.InterpreterObject;
import me.dstet.delphi.interpreter.InterpreterObjectTemplate;
import me.dstet.delphi.interpreter.InterpreterPrimitiveSymbol;
import me.dstet.delphi.interpreter.InterpreterTemplateSymbol;
import me.dstet.delphi.interpreter.Primitive;
import me.dstet.delphi.interpreter.PrimitiveUtils;
import me.dstet.delphi.interpreter.Visibility;

public class Visitor<T> implements delphiVisitor<T> {
    boolean isUnit;
    SymbolTable table;

    boolean readingClass = false;
    String currentClassname = null;
    Visibility currentClassVisibility = Visibility.PRIVATE;

    public Visitor(boolean isUnit) {
        this.isUnit = isUnit;
        table = new SymbolTable(this);
    }

    public Visitor(boolean isUnit, SymbolTable existingSymbolTable) {
        this.isUnit = isUnit;
        this.table = existingSymbolTable;
    }

    public SymbolTable getSymbolTable() {
        return table;
    }

    static void criticalError(String msg) {
        System.err.println(msg);
        System.exit(1);
    }

    @Override
    public T visit(ParseTree arg0) {
        return visitChildren((RuleNode) arg0);
    }

    @Override
    public T visitChildren(RuleNode arg0) {
        System.out.println("Visited... something! :)");
        return null;
    }

    @Override
    public T visitErrorNode(ErrorNode arg0) {
        System.out.println("Visited... something! :)");
        return null;
    }

    @Override
    public T visitTerminal(TerminalNode arg0) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public T visitProgram(ProgramContext ctx) {
        // System.out.println("Visited program!");
        
        // table.printSymbolTable();

        return visitBlock(ctx.block());
    }

    @Override
    public T visitProgramHeading(ProgramHeadingContext ctx) {
        System.out.println("Visited... something! :)");
        return null;
    }

    @Override
    public T visitIdentifier(IdentifierContext ctx) {
        System.out.println("Visited... something! :)");
        return null;
    }

    @Override
    public T visitBlock(BlockContext ctx) {
        // System.out.println("Visited block!");

        if (ctx.typeDefinitionPart() != null && !ctx.typeDefinitionPart().isEmpty()) {
            for (TypeDefinitionPartContext typeDefinitionPartContext : ctx.typeDefinitionPart()) {
                visitTypeDefinitionPart(typeDefinitionPartContext);
            }
        }

        if (ctx.constantDefinitionPart() != null && !ctx.constantDefinitionPart().isEmpty()) {
            for (ConstantDefinitionPartContext definitionPartContext : ctx.constantDefinitionPart()) {
                visitConstantDefinitionPart(definitionPartContext);
            }
        }

        if (ctx.variableDeclarationPart() != null && !ctx.variableDeclarationPart().isEmpty()) {
            for (VariableDeclarationPartContext variableDeclarationPartContext : ctx.variableDeclarationPart()) {
                visitVariableDeclarationPart(variableDeclarationPartContext);
            }
        }

        if (ctx.procedureAndFunctionDeclarationPart() != null && !ctx.procedureAndFunctionDeclarationPart().isEmpty()) {
            for (ProcedureAndFunctionDeclarationPartContext procedureAndFunctionDeclarationPartContext : ctx.procedureAndFunctionDeclarationPart()) {
                visitProcedureAndFunctionDeclarationPart(procedureAndFunctionDeclarationPartContext);
            }
        }

        if (ctx.compoundStatement() != null) {
            visitCompoundStatement(ctx.compoundStatement());
        }

        return null;
    }

    @Override
    public T visitClassBlock(ClassBlockContext ctx) {
        // System.out.println("Visited Class block!");
        for (ParseTree tree : ctx.children) {
            if (tree instanceof ConstantDefinitionContext) {
                visitConstantDefinition((ConstantDefinitionContext) tree);
            } else if (tree instanceof TypeDefinitionPartContext) {
                visitTypeDefinitionPart((TypeDefinitionPartContext) tree);
            } else if (tree instanceof VariableDeclarationContext) {
                visitVariableDeclaration((VariableDeclarationContext) tree);
            } else if (tree instanceof ClassProcedureAndFunctionDeclarationPartContext) {
                visitClassProcedureAndFunctionDeclarationPart((ClassProcedureAndFunctionDeclarationPartContext) tree);
            }
        }
        return null;
    }

    @Override
    public T visitUsesUnitsPart(UsesUnitsPartContext ctx) {
        System.out.println("Visited... something! :)");
        return null;
    }

    @Override
    public T visitLabelDeclarationPart(LabelDeclarationPartContext ctx) {
        System.out.println("Visited... something! :)");
        return null;
    }

    @Override
    public T visitLabel(LabelContext ctx) {
        System.out.println("Visited... something! :)");
        return null;
    }

    @Override
    public T visitConstantDefinitionPart(ConstantDefinitionPartContext ctx) {
        // System.out.println("Visited constant definition part");

        for (ConstantDefinitionContext definitionContext : ctx.constantDefinition()) {
            visitConstantDefinition(definitionContext);
        }

        return null;
    }

    // TODO
    Object evaluateConstantValue(ConstantContext ctx) {
        return null;
    }

    @Override
    public T visitConstantDefinition(ConstantDefinitionContext ctx) {
        // System.out.println("Visited Constant Definition!");
        for (ParseTree child : ctx.children) {
            if (child instanceof ConstantDefinitionContext) {
                ConstantDefinitionContext cdc = (ConstantDefinitionContext) child;
                String constantName = cdc.identifier().IDENT().getText();
                
                // Either add to class template or constants table
                if (readingClass) {
                    if (!table.addConstantToObjectTemplate(currentClassname, constantName, evaluateConstantValue(cdc.constant()), currentClassVisibility)) {
                        criticalError("ERROR: Failed to add constant to class constant table!");
                    }
                } else {
                    if (!table.addConstantToConstants(constantName, evaluateConstantValue(cdc.constant()))) {
                        criticalError("ERROR: Failed to add constant to constant table!");
                    }
                }
            }
        }
        return null;
    }

    @Override
    public T visitConstantChr(ConstantChrContext ctx) {
        System.out.println("Visited... something! :)");
        return null;
    }

    @Override
    public T visitConstant(ConstantContext ctx) {
        System.out.println("Visited... something! :)");
        return null;
    }

    @Override
    public T visitUnsignedNumber(UnsignedNumberContext ctx) {
        System.out.println("Visited... something! :)");
        return null;
    }

    @Override
    public T visitUnsignedInteger(UnsignedIntegerContext ctx) {
        System.out.println("Visited... something! :)");
        return null;
    }

    @Override
    public T visitUnsignedReal(UnsignedRealContext ctx) {
        System.out.println("Visited... something! :)");
        return null;
    }

    @Override
    public T visitSign(SignContext ctx) {
        System.out.println("Visited... something! :)");
        return null;
    }

    @Override
    public T visitBool_(Bool_Context ctx) {
        System.out.println("Visited... something! :)");
        return null;
    }

    @Override
    public T visitString(StringContext ctx) {
        System.out.println("Visited... something! :)");
        return null;
    }

    @Override
    public T visitTypeDefinitionPart(TypeDefinitionPartContext ctx) {
        // System.out.println("Visited TypeDefinitionPart!");
        for (TypeDefinitionContext t : ctx.typeDefinition()) {
            visitTypeDefinition(t);
        }
        return null;
    }

    @Override
    public T visitTypeDefinition(TypeDefinitionContext ctx) {
        // System.out.println("Visited TypeDefinition!");
        String typeName = ctx.identifier().IDENT().getText();
        // System.out.println("Got type: " + typeName);


        if (ctx.type_() != null) {
            // SIMPLE TYPE
            visitType_(ctx.type_());
        } else if (ctx.functionType() != null) {
            // FUNCTION
            visitFunctionType(ctx.functionType());
        } else if (ctx.procedureType() != null) {
            // PROCEDURE
            visitProcedureType(ctx.procedureType());
        } else if (ctx.classType() != null) {
            // CLASS
            readingClass = true;
            currentClassname = typeName;

            if (!table.addObjectTemplate(typeName)) {
                criticalError("ERROR: Class already declared with this name");
            }

            visitClassType(ctx.classType());

            // Reset class reader values to default
            readingClass = false;
            currentClassname = null;
            currentClassVisibility = Visibility.PRIVATE;
        } else {
            criticalError("Attempted to declare type without valid type"); // what?
        }

        return null;
    }

    @Override
    public T visitVisibility(VisibilityContext ctx) {
        System.out.println("Visited... something! :)");
        return null;
    }

    /**
     * Returns Visibility enum from a VisibilityContext.
     * @param ctx Context to check.
     * @return Visibility corresponding to the non-null ctx value, or private if all are null.
     */
    static Visibility geVisibilityFromContext(VisibilityContext ctx) {
        if (ctx.PRIVATE() != null) {
            return Visibility.PRIVATE;
        } else if (ctx.PUBLIC() != null) {
            return Visibility.PUBLIC;
        } else if (ctx.PUBLISHED() != null) {
            return Visibility.PUBLISHED;
        } else if (ctx.PROTECTED() != null) {
            return Visibility.PROTECTED;
        }

        return Visibility.PRIVATE;
    }

    @Override
    public T visitClassType(ClassTypeContext ctx) {
        // System.out.println("Visited ClassType!");
        for (ParseTree child : ctx.children) {
            if (child instanceof VisibilityContext) {
                currentClassVisibility = geVisibilityFromContext((VisibilityContext) child);
            } else if (child instanceof ClassBlockContext) {
                visitClassBlock((ClassBlockContext) child);
            }
        }
        return null;
    }

    @Override
    public T visitFunctionType(FunctionTypeContext ctx) {
        System.out.println("Visited function type!");
        return null;
    }

    @Override
    public T visitProcedureType(ProcedureTypeContext ctx) {
        System.out.println("Visited procedure type!");
        return null;
    }

    @Override
    public T visitType_(Type_Context ctx) {
        System.out.println("Visited type_! :)");
        return null;
    }

    @Override
    public T visitSimpleType(SimpleTypeContext ctx) {
        System.out.println("Visited... something! :)");
        return null;
    }

    @Override
    public T visitScalarType(ScalarTypeContext ctx) {
        System.out.println("Visited... something! :)");
        return null;
    }

    @Override
    public T visitSubrangeType(SubrangeTypeContext ctx) {
        System.out.println("Visited... something! :)");
        return null;
    }

    @Override
    public T visitTypeIdentifier(TypeIdentifierContext ctx) {
        System.out.println("Visited... something! :)");
        return null;
    }

    @Override
    public T visitStructuredType(StructuredTypeContext ctx) {
        System.out.println("Visited... something! :)");
        return null;
    }

    @Override
    public T visitUnpackedStructuredType(UnpackedStructuredTypeContext ctx) {
        System.out.println("Visited... something! :)");
        return null;
    }

    @Override
    public T visitStringtype(StringtypeContext ctx) {
        System.out.println("Visited... something! :)");
        return null;
    }

    @Override
    public T visitArrayType(ArrayTypeContext ctx) {
        System.out.println("Visited... something! :)");
        return null;
    }

    @Override
    public T visitTypeList(TypeListContext ctx) {
        System.out.println("Visited... something! :)");
        return null;
    }

    @Override
    public T visitIndexType(IndexTypeContext ctx) {
        System.out.println("Visited... something! :)");
        return null;
    }

    @Override
    public T visitComponentType(ComponentTypeContext ctx) {
        System.out.println("Visited... something! :)");
        return null;
    }

    @Override
    public T visitRecordType(RecordTypeContext ctx) {
        System.out.println("Visited... something! :)");
        return null;
    }

    @Override
    public T visitFieldList(FieldListContext ctx) {
        System.out.println("Visited... something! :)");
        return null;
    }

    @Override
    public T visitFixedPart(FixedPartContext ctx) {
        System.out.println("Visited... something! :)");
        return null;
    }

    @Override
    public T visitRecordSection(RecordSectionContext ctx) {
        System.out.println("Visited... something! :)");
        return null;
    }

    @Override
    public T visitVariantPart(VariantPartContext ctx) {
        System.out.println("Visited... something! :)");
        return null;
    }

    @Override
    public T visitTag(TagContext ctx) {
        System.out.println("Visited... something! :)");
        return null;
    }

    @Override
    public T visitVariant(VariantContext ctx) {
        System.out.println("Visited... something! :)");
        return null;
    }

    @Override
    public T visitSetType(SetTypeContext ctx) {
        System.out.println("Visited... something! :)");
        return null;
    }

    @Override
    public T visitBaseType(BaseTypeContext ctx) {
        System.out.println("Visited... something! :)");
        return null;
    }

    @Override
    public T visitFileType(FileTypeContext ctx) {
        System.out.println("Visited... something! :)");
        return null;
    }

    @Override
    public T visitPointerType(PointerTypeContext ctx) {
        System.out.println("Visited... something! :)");
        return null;
    }

    @Override
    public T visitVariableDeclarationPart(VariableDeclarationPartContext ctx) {
        // System.out.println("Visited variable declaration part!");

        for (VariableDeclarationContext vContext : ctx.variableDeclaration()) {
            visitVariableDeclaration(vContext);
        }

        return null;
    }

    private Primitive getTypeFromTypeIdentContext(TypeIdentifierContext ctx) {
        if (ctx.BOOLEAN() != null) {
            return Primitive.Boolean;
        } else if (ctx.CHAR() != null) {
            return Primitive.Char;
        } else if (ctx.INTEGER() != null) {
            return Primitive.Integer;
        } else  if (ctx.REAL() != null) {
            return Primitive.Real;
        } else if (ctx.STRING() != null) {
            return Primitive.String;
        } else if (ctx.identifier() != null) {
            String type = ctx.identifier().IDENT().getText();
            InterpreterObjectTemplate template = table.getTemplateDeclaredOnTable(type);
            if (template == null) {
                criticalError(String.format("ERROR: Attempted to declare variable of invalid type: %s", type));
            }
        }

        return Primitive.NotPrimitive;
    }

    @Override
    public T visitVariableDeclaration(VariableDeclarationContext ctx) {     
        // System.out.println("Visited Variable Declaration!");
        for (IdentifierContext id : ctx.identifierList().identifier()) {
            // Get name and type of each variable declared.
            String variableName = id.IDENT().getText();
            Primitive primitiveType = getTypeFromTypeIdentContext(ctx.type_().simpleType().typeIdentifier());
            String typeString = null;
            if (primitiveType == Primitive.NotPrimitive) {
                typeString = ctx.type_().simpleType().typeIdentifier().identifier().IDENT().getText();
            }

            if (readingClass) {
                // Create symbol and add to object template if this is a class variable.
                IInterpreterSymbol symbol;
                if (primitiveType != Primitive.NotPrimitive) {
                    symbol = new InterpreterPrimitiveSymbol(currentClassVisibility, primitiveType);
                } else {
                    symbol = new InterpreterTemplateSymbol(currentClassVisibility, typeString);
                }

                InterpreterObjectTemplate template = table.getTemplateDeclaredOnTable(currentClassname);
                if (!template.addInterpreterSymbol(variableName, symbol)) {
                    criticalError("ERROR: Attempted to add duplicate symbol to object template");
                }
            } else {
                if (primitiveType != Primitive.NotPrimitive) {
                    //  If the variable is a primitive add that to the symbol table directly with a default value.
                    Object defaultPrimitive = PrimitiveUtils.getDefaultFromPrimitive(primitiveType);
                    if (!table.addVariableToVars(variableName, defaultPrimitive)) {
                        criticalError(String.format("ERROR: Error declaring variable of type: %s", typeString));
                    }
                } else {
                    //  If the variable is an instance of a class add it to the symbol table with a null value. Do not instantiate until constructor is called.
                    InterpreterObjectTemplate template = table.getTemplateDeclaredOnTable(typeString);
                    if (template != null) {
                        // Add a template symbol to the symbol table until the variable is actually instantiated via its constructor.
                        if (!table.addVariableToVars(variableName, new InterpreterTemplateSymbol(Visibility.PUBLIC, typeString))) {
                            criticalError(String.format("ERROR: Attempted to declare duplicate variable: %s", variableName));
                        }
                    } else {
                        criticalError(String.format("ERROR: Attempted to declare var of invalid type: %s", typeString));
                    }
                }
            }
        }

        return null;
    }

    @Override
    public T visitClassProcedureAndFunctionDeclarationPart(ClassProcedureAndFunctionDeclarationPartContext ctx) {
        visitClassProcedureOrFunctionDeclaration((ClassProcedureOrFunctionDeclarationContext) ctx.classProcedureOrFunctionDeclaration());
        return null;
    }

    @Override
    public T visitProcedureAndFunctionDeclarationPart(ProcedureAndFunctionDeclarationPartContext ctx) {
        visitProcedureOrFunctionDeclaration(ctx.procedureOrFunctionDeclaration());

        return null;
    }

    @Override
    public T visitClassProcedureOrFunctionDeclaration(ClassProcedureOrFunctionDeclarationContext ctx) {
        if (ctx.classFunctionDeclaration() != null) {
            visitClassFunctionDeclaration(ctx.classFunctionDeclaration());
        } else if (ctx.classDestructorDeclaration() != null) {
            visitClassDestructorDeclaration(ctx.classDestructorDeclaration());
        } else if (ctx.classConstructorDeclaration() != null) {
            visitClassConstructorDeclaration(ctx.classConstructorDeclaration());
        } else if (ctx.classProcedureDeclaration() != null) {
            visitClassProcedureDeclaration(ctx.classProcedureDeclaration());
        }

        return null;
    }

    @Override
    public T visitClassConstructorDeclaration(ClassConstructorDeclarationContext ctx) {
        // System.out.println("Visited class constructor!");

        InterpreterObjectTemplate template = table.getTemplateDeclaredOnTable(currentClassname);

        String functionName = ctx.identifier().IDENT().getText();
        
        ArrayList<InterpreterFunctionParameter> params = null;
        // Only add parameters if they have been provided
        if (ctx.formalParameterList() != null) {
            params = new ArrayList<>();
            
            // Iterate through and add parameters. All idents in a section context will have the same type
            for (FormalParameterSectionContext sectionContext : ctx.formalParameterList().formalParameterSection()) {
                Primitive paramType = getTypeFromTypeIdentContext(sectionContext.parameterGroup().typeIdentifier());

                for (IdentifierContext identifierContext : sectionContext.parameterGroup().identifierList().identifier()) {
                    InterpreterFunctionParameter param = new InterpreterFunctionParameter(paramType, identifierContext.IDENT().getText());
                    params.add(param);
                }
            }
        }

        InterpreterFunctionSymbol symbol = new InterpreterFunctionSymbol(currentClassVisibility, params, Primitive.Void);
        if (!template.addInterpreterSymbol(functionName, symbol)) {
            criticalError("ERROR: Attempted to add duplicate symbol to object template");
        }

        return null;
    }

    @Override
    public T visitClassDestructorDeclaration(ClassDestructorDeclarationContext ctx) {
        // System.out.println("Visited class destructor!");

        InterpreterObjectTemplate template = table.getTemplateDeclaredOnTable(currentClassname);

        String functionName = ctx.identifier().IDENT().getText();

        // Destructor will never have parameters so leave that argument null.
        InterpreterFunctionSymbol symbol = new InterpreterFunctionSymbol(currentClassVisibility, null, Primitive.Void);
        if (!template.addInterpreterSymbol(functionName, symbol)) {
            criticalError("ERROR: Attempted to add duplicate symbol to object template");
        }
        return null;
    }

    @Override
    public T visitProcedureOrFunctionDeclaration(ProcedureOrFunctionDeclarationContext ctx) {
        if (ctx.procedureDeclaration() != null) {
            visitProcedureDeclaration(ctx.procedureDeclaration());
        } else if (ctx.functionDeclaration() != null) {
            visitFunctionDeclaration(ctx.functionDeclaration());
        }

        return null;
    }

    @Override
    public T visitClassProcedureDeclaration(ClassProcedureDeclarationContext ctx) {
        // System.out.println("Visited class procedure declaration!");

        InterpreterObjectTemplate template = table.getTemplateDeclaredOnTable(currentClassname);

        String functionName = ctx.identifier().IDENT().getText();
        ArrayList<InterpreterFunctionParameter> params = null;
        // Only add parameters if they have been provided
        if (ctx.formalParameterList() != null) {
            params = new ArrayList<>();
            
            // Iterate through and add parameters. All idents in a section context will have the same type
            for (FormalParameterSectionContext sectionContext : ctx.formalParameterList().formalParameterSection()) {
                Primitive paramType = getTypeFromTypeIdentContext(sectionContext.parameterGroup().typeIdentifier());

                for (IdentifierContext identifierContext : sectionContext.parameterGroup().identifierList().identifier()) {
                    InterpreterFunctionParameter param = new InterpreterFunctionParameter(paramType, identifierContext.IDENT().getText());
                    params.add(param);
                }
            }
        }

        InterpreterFunctionSymbol symbol = new InterpreterFunctionSymbol(currentClassVisibility, params, Primitive.Void);
        if (!template.addInterpreterSymbol(functionName, symbol)) {
            criticalError("ERROR: Attempted to add duplicate symbol to object template");
        }

        return null;
    }

    @Override
    public T visitProcedureDeclaration(ProcedureDeclarationContext ctx) {
        // System.out.println("Visited procedure declaration!");
        
        if (!ctx.identifier().DOT().isEmpty()) {
            // Treat this as a class method
            String className = ctx.identifier().IDENT().getText();
            // System.out.println("Classname: " + className);
            String methodName = ctx.identifier().identifier().get(0).IDENT().getText();
            // System.out.println("Procedure is: " + className + "." + methodName);

            InterpreterObjectTemplate template = table.getTemplateDeclaredOnTable(className);
            if (template == null) {
                criticalError("ERROR: Attempted to declare class method for undeclared class: " + className);
            }

            IInterpreterSymbol symbolInterface = template.getInterpreterSymbol(methodName) ;
            if (symbolInterface == null || !(symbolInterface instanceof InterpreterFunctionSymbol)) {
                criticalError("ERROR: Method " + methodName + " does not exist in class or is not declared as a function");
            }

            InterpreterFunctionSymbol symbol = (InterpreterFunctionSymbol) symbolInterface;
            InterpreterFunction function = new InterpreterFunction();

            for (StatementContext statement : ctx.block().compoundStatement().statements().statement()) {
                function.addStatementToFunction(statement);
            }

            symbol.setInterpreterFunction(function);
        } else {
            // Treat this as a new function declaration
            String procedureName = ctx.identifier().IDENT().getText();
            // System.out.println("Procedure is: " + procedureName);

            InterpreterFunction function = new InterpreterFunction();
            ArrayList<InterpreterFunctionParameter> params = null;

            for (StatementContext statement : ctx.block().compoundStatement().statements().statement()) {
                function.addStatementToFunction(statement);
            }

            for (FormalParameterSectionContext paramCtx : ctx.formalParameterList().formalParameterSection()) {
                if (params == null) {
                    params = new ArrayList<>();
                }

                // Leaving complex type parameter boilerplate commented for when I add the ability to use classes as params
                // String complexTypeString = null;
                Primitive primitiveType;

                primitiveType = getTypeFromTypeIdentContext(paramCtx.parameterGroup().typeIdentifier());
                // if (primitiveType == Primitive.NotPrimitive) {
                //     complexTypeString = paramCtx.parameterGroup().typeIdentifier().identifier().IDENT().getText();
                // }

                for (IdentifierContext identCtx : paramCtx.parameterGroup().identifierList().identifier()) {
                    InterpreterFunctionParameter param = new InterpreterFunctionParameter(primitiveType, identCtx.IDENT().getText());
                    params.add(param);
                }
            }

            // As a procedure return type will always be void.
            InterpreterFunctionSymbol symbol = new InterpreterFunctionSymbol(Visibility.PUBLIC, params, Primitive.Void);
            symbol.setInterpreterFunction(function);

            if (!table.addFunctionToTable(procedureName, symbol)) {
                criticalError("ERROR: Unable to add " + procedureName + " to symbol table");
            }
        }

        return null;
    }

    @Override
    public T visitFormalParameterList(FormalParameterListContext ctx) {
        System.out.println("Visited... something! :)");
        return null;
    }

    @Override
    public T visitFormalParameterSection(FormalParameterSectionContext ctx) {
        System.out.println("Visited... something! :)");
        return null;
    }

    @Override
    public T visitParameterGroup(ParameterGroupContext ctx) {
        System.out.println("Visited... something! :)");
        return null;
    }

    @Override
    public T visitIdentifierList(IdentifierListContext ctx) {
        System.out.println("Visited... something! :)");
        return null;
    }

    @Override
    public T visitConstList(ConstListContext ctx) {
        System.out.println("Visited... something! :)");
        return null;
    }

    @Override
    public T visitClassFunctionDeclaration(ClassFunctionDeclarationContext ctx) {
        // System.out.println("Visited class function declaration!");

        InterpreterObjectTemplate template = table.getTemplateDeclaredOnTable(currentClassname);

        String functionName = ctx.identifier().IDENT().getText();
        Primitive returnType =  getTypeFromTypeIdentContext(ctx.resultType().typeIdentifier());
        String complexReturn = null;
        if (returnType == Primitive.NotPrimitive) {
            complexReturn = ctx.resultType().typeIdentifier().identifier().IDENT().getText();
        }

        ArrayList<InterpreterFunctionParameter> params = null;
        // Only add parameters if they have been provided
        if (ctx.formalParameterList() != null) {
            params = new ArrayList<>();
            
            // Iterate through and add parameters. All idents in a section context will have the same type
            for (FormalParameterSectionContext sectionContext : ctx.formalParameterList().formalParameterSection()) {
                Primitive paramType = getTypeFromTypeIdentContext(sectionContext.parameterGroup().typeIdentifier());

                for (IdentifierContext identifierContext : sectionContext.parameterGroup().identifierList().identifier()) {
                    InterpreterFunctionParameter param = new InterpreterFunctionParameter(paramType, identifierContext.IDENT().getText());
                    params.add(param);
                }
            }
        }

        InterpreterFunctionSymbol symbol;
        if (returnType == Primitive.NotPrimitive) {
            symbol = new InterpreterFunctionSymbol(currentClassVisibility, params, complexReturn);
        } else {
            symbol = new InterpreterFunctionSymbol(currentClassVisibility, params, returnType);
        }
        if (!template.addInterpreterSymbol(functionName, symbol)) {
            criticalError("ERROR: Attempted to add duplicate symbol to object template");
        }

        return null;
    }

    @Override
    public T visitFunctionDeclaration(FunctionDeclarationContext ctx) {
        // System.out.println("Visited function declaration!");

        if (!ctx.identifier().DOT().isEmpty()) {
            // Treat this as a class method
            String className = ctx.identifier().IDENT().getText();
            // System.out.println("Classname: " + className);
            String methodName = ctx.identifier().identifier().get(0).IDENT().getText();
            // System.out.println("Function is: " + className + "." + methodName);

            InterpreterObjectTemplate template = table.getTemplateDeclaredOnTable(className);
            if (template == null) {
                criticalError("ERROR: Attempted to declare class method for undeclared class: " + className);
            }

            IInterpreterSymbol symbolInterface = template.getInterpreterSymbol(methodName) ;
            if (symbolInterface == null || !(symbolInterface instanceof InterpreterFunctionSymbol)) {
                criticalError("ERROR: Method " + methodName + " does not exist in class or is not declared as a function");
            }

            InterpreterFunctionSymbol symbol = (InterpreterFunctionSymbol) symbolInterface;
            InterpreterFunction function = new InterpreterFunction();

            for (StatementContext statement : ctx.block().compoundStatement().statements().statement()) {
                function.addStatementToFunction(statement);
            }

            symbol.setInterpreterFunction(function);
        } else {
            // Treat this as a new function declaration
            String functionName = ctx.identifier().IDENT().getText();
            // System.out.println("Function is: " + functionName);

            InterpreterFunction function = new InterpreterFunction();
            Primitive returnType =  getTypeFromTypeIdentContext(ctx.resultType().typeIdentifier());
            String complexReturn = null;
            ArrayList<InterpreterFunctionParameter> params = null;
            
            if (returnType == Primitive.NotPrimitive) {
                complexReturn = ctx.resultType().typeIdentifier().identifier().IDENT().getText();
            }

            for (StatementContext statement : ctx.block().compoundStatement().statements().statement()) {
                function.addStatementToFunction(statement);
            }

            for (FormalParameterSectionContext paramCtx : ctx.formalParameterList().formalParameterSection()) {
                if (params == null) {
                    params = new ArrayList<>();
                }

                // Leaving complex type parameter boilerplate commented for when I add the ability to use classes as params
                // String complexTypeString = null;
                Primitive primitiveType;

                primitiveType = getTypeFromTypeIdentContext(paramCtx.parameterGroup().typeIdentifier());
                // if (primitiveType == Primitive.NotPrimitive) {
                //     complexTypeString = paramCtx.parameterGroup().typeIdentifier().identifier().IDENT().getText();
                // }

                for (IdentifierContext identCtx : paramCtx.parameterGroup().identifierList().identifier()) {
                    InterpreterFunctionParameter param = new InterpreterFunctionParameter(primitiveType, identCtx.IDENT().getText());
                    params.add(param);
                }
            }

            InterpreterFunctionSymbol symbol;
            if (returnType == Primitive.NotPrimitive) {
                symbol = new InterpreterFunctionSymbol(Visibility.PUBLIC, params, complexReturn);
            } else {
                symbol = new InterpreterFunctionSymbol(Visibility.PUBLIC, params, returnType);
            }

            symbol.setInterpreterFunction(function);

            if (!table.addFunctionToTable(functionName, symbol)) {
                criticalError("ERROR: Unable to add " + functionName + " to symbol table");
            }
        }

        return null;
    }

    @Override
    public T visitResultType(ResultTypeContext ctx) {
        System.out.println("Visited... something! :)");
        return null;
    }

    @Override
    public T visitStatement(StatementContext ctx) {
        // System.out.println("Visited a statement!");

        // TODO (for fun) I have not yet implemented labels so assume any statement is unlabelled
        visitUnlabelledStatement(ctx.unlabelledStatement());

        return null;
    }

    @Override
    public T visitUnlabelledStatement(UnlabelledStatementContext ctx) {
        // System.out.println("Visited an unlabelled statement!");
        
        if (ctx.simpleStatement() != null) {
            visitSimpleStatement(ctx.simpleStatement());
        }

        return null;
    }

    // Pseudo-visitor syntax since visitor overrides are T (ugh).
    public Object evaluateExpression(ExpressionContext ctx) {
        if (ctx.relationaloperator() != null) {
            Object expRS = evaluateExpression(ctx.expression());
            Object expLS = evaluateSimpleExpression(ctx.simpleExpression());

            if (expLS instanceof Integer && expRS instanceof Integer) {
                if (ctx.relationaloperator().EQUAL() != null) {
                    return ((Integer) expLS) == ((Integer) expRS);
                } else if (ctx.relationaloperator().NOT_EQUAL() != null) {
                    return ((Integer) expLS) != ((Integer) expRS);
                } else if (ctx.relationaloperator().LT() != null) {
                    return ((Integer) expLS) < ((Integer) expRS);
                } else if (ctx.relationaloperator().LE() != null) {
                    return ((Integer) expLS) <= ((Integer) expRS);
                } else if (ctx.relationaloperator().GT() != null) {
                    return ((Integer) expLS) > ((Integer) expRS);
                } else if (ctx.relationaloperator().GE() != null) {
                    return ((Integer) expLS) >= ((Integer) expRS);
                } else {
                    criticalError("ERROR: Type mismatch or invalid multiplicative operator in term: " + ctx.getText());
                }
            } else if (expLS instanceof Double && expRS instanceof Double) {
                if (ctx.relationaloperator().EQUAL() != null) {
                    return ((Double) expLS) == ((Double) expRS);
                } else if (ctx.relationaloperator().NOT_EQUAL() != null) {
                    return ((Double) expLS) != ((Double) expRS);
                } else if (ctx.relationaloperator().LT() != null) {
                    return ((Double) expLS) < ((Double) expRS);
                } else if (ctx.relationaloperator().LE() != null) {
                    return ((Double) expLS) <= ((Double) expRS);
                } else if (ctx.relationaloperator().GT() != null) {
                    return ((Double) expLS) > ((Double) expRS);
                } else if (ctx.relationaloperator().GE() != null) {
                    return ((Double) expLS) >= ((Double) expRS);
                } else {
                    criticalError("ERROR: Type mismatch or invalid multiplicative operator in term: " + ctx.getText());
                }
            } else if (expLS instanceof Boolean && expRS instanceof Boolean) {
                if (ctx.relationaloperator().EQUAL() != null) {
                    return ((Boolean) expLS) == ((Boolean) expRS);
                } else if (ctx.relationaloperator().NOT_EQUAL() != null) {
                    return ((Boolean) expLS) != ((Boolean) expRS);
                } else {
                    criticalError("ERROR: Type mismatch or invalid multiplicative operator in term: " + ctx.getText());
                }
            } else {
                criticalError("ERROR: Type mismatch or invalid multiplicative operator in term: " + ctx.getText());
            }
        } else {
            return evaluateSimpleExpression(ctx.simpleExpression());
        }

        return null;
    }

    public Object evaluateSimpleExpression(SimpleExpressionContext ctx) {
        if (ctx.additiveoperator() != null) {
            Object expRS = evaluateSimpleExpression(ctx.simpleExpression());
            Object expLS = evaluateTerm(ctx.term());

            if (expLS instanceof Integer && expRS instanceof Integer) {
                if (ctx.additiveoperator().PLUS() != null) {
                    return ((Integer) expLS) + ((Integer) expRS);
                } else if (ctx.additiveoperator().MINUS() != null) {
                    return ((Integer) expLS) - ((Integer) expRS);
                } else {
                    criticalError("ERROR: Type mismatch or invalid multiplicative operator in term: " + ctx.getText());
                }
            } else if (expLS instanceof Double && expRS instanceof Double) {
                if (ctx.additiveoperator().PLUS() != null) {
                    return ((Double) expLS) + ((Double) expRS);
                } else if (ctx.additiveoperator().MINUS() != null) {
                    return ((Double) expLS) - ((Double) expRS);
                } else {
                    criticalError("ERROR: Type mismatch or invalid multiplicative operator in term: " + ctx.getText());
                }
            } else if (expLS instanceof Boolean && expRS instanceof Boolean) {
                if (ctx.additiveoperator().OR() != null) {
                    return ((Boolean) expLS) || ((Boolean) expRS);
                } else {
                    criticalError("ERROR: Type mismatch or invalid multiplicative operator in term: " + ctx.getText());
                }
            } else {
                criticalError("ERROR: Type mismatch or invalid multiplicative operator in term: " + ctx.getText());
            }
        } else {
            return evaluateTerm(ctx.term());
        }
        
        return null;
    }

    public Object evaluateTerm(TermContext ctx) {
        if (ctx.multiplicativeoperator() != null) {
            Object expRS = evaluateTerm(ctx.term());
            Object expLS = evaluateSignedFactor(ctx.signedFactor());
            
            if (expLS instanceof Integer && expRS instanceof Integer) {
                if (ctx.multiplicativeoperator().STAR() != null) {
                    return ((Integer) expLS) * ((Integer) expRS);
                } else if (ctx.multiplicativeoperator().SLASH() != null || ctx.multiplicativeoperator().DIV() != null) {
                    return ((Integer) expLS) / ((Integer) expRS);
                } else if (ctx.multiplicativeoperator().MOD() != null) {
                    return ((Integer) expLS) % ((Integer) expRS);
                } else {
                    criticalError("ERROR: Type mismatch or invalid multiplicative operator in term: " + ctx.getText());
                }
            } else if (expLS instanceof Double && expRS instanceof Double) {
                if (ctx.multiplicativeoperator().STAR() != null) {
                    return ((Double) expLS) * ((Double) expRS);
                } else if (ctx.multiplicativeoperator().SLASH() != null || ctx.multiplicativeoperator().DIV() != null) {
                    return ((Double) expLS) / ((Double) expRS);
                } else if (ctx.multiplicativeoperator().MOD() != null) {
                    return ((Double) expLS) % ((Double) expRS);
                } else {
                    criticalError("ERROR: Type mismatch or invalid multiplicative operator in term: " + ctx.getText());
                }
            } else if (expLS instanceof Boolean && expRS instanceof Boolean) {
                if (ctx.multiplicativeoperator().AND() != null) {
                    return ((Boolean) expLS) && ((Boolean) expRS);
                } else {
                    criticalError("ERROR: Type mismatch or invalid multiplicative operator in term: " + ctx.getText());
                }
            } else {
                criticalError("ERROR: Type mismatch or invalid multiplicative operator in term: " + ctx.getText());
            }
        } else {
            return evaluateSignedFactor(ctx.signedFactor());
        }

        return null;
    }

    public Object evaluateSignedFactor(SignedFactorContext ctx) {
        Object evaluated = evaluateFactor(ctx.factor());
        if (ctx.PLUS() != null) {
            if (evaluated instanceof Integer || evaluated instanceof Double) {
                return evaluated;
            } else {
                criticalError("ERROR: Attempted to use sign on value that cannot have sign: " + ctx.getText());
            }
        } else if (ctx.MINUS() != null) {
            if (evaluated instanceof Integer) {
                return -((Integer) evaluated);
            } else if (evaluated instanceof Double) {
                return -((Double) evaluated);
            } else {
                criticalError("ERROR: Attempted to use sign on value that cannot have sign: " + ctx.getText());
            }
        } else {
            return evaluated;
        }

        return null;
    }

    public Object evaluateFactor(FactorContext ctx) {
        if (ctx.expression() != null) {
            return evaluateExpression(ctx.expression());
        } else if (ctx.NOT() != null) {
            Object presumedBool = evaluateFactor(ctx.factor());
            if (presumedBool instanceof Boolean) {
                return !((Boolean) presumedBool);
            } else {
                criticalError("ERROR: Attempted to use NOT on value that cannot be inverted: " + ctx.getText());
            }
        } else if (ctx.bool_() != null) {
            if (ctx.bool_().TRUE() != null) {
                return Boolean.TRUE;
            } else {
                return Boolean.FALSE;
            }
        } else if (ctx.variable() != null) {
            return evaluateVariable(ctx.variable());
        } else if (ctx.unsignedConstant() != null) {
            return evaluateUnsignedConstant(ctx.unsignedConstant());
        } else if (ctx.functionDesignator() != null) {
            return evaluateFunctionDesignator(ctx.functionDesignator());
        } else {
            // Catchall for unimplemented types/expressions.
            return null;
        }

        return null;
    }

    public Object evaluateFunctionDesignator (FunctionDesignatorContext ctx) {        
        LinkedHashMap<String, Object> functionArgs = null;

        if (ctx.identifier().identifier() != null && !ctx.identifier().identifier().isEmpty() && ctx.identifier().identifier(0).getText().equals("Create")) {
            // Special case for Constructors!
            InterpreterObjectTemplate template = table.getTemplateDeclaredOnTable(ctx.identifier().IDENT().getText());
            IInterpreterSymbol constructor = template.getInterpreterSymbol("Create");
            InterpreterObject createdObj = template.instantiateObject(table);
            InterpreterFunctionSymbol constructorSymbol = (InterpreterFunctionSymbol) constructor;

            ArrayList<InterpreterFunctionParameter> constructorParams = constructorSymbol.getParams();

            if (ctx.parameterList() != null && 
                !ctx.parameterList().isEmpty() && 
                constructorSymbol.getParams() != null && 
                ctx.parameterList().actualParameter().size() == constructorSymbol.getParams().size()
                ) {
                functionArgs = new LinkedHashMap<>();

                for (int i = 0; i < constructorParams.size(); i++) {
                    ActualParameterContext parameter = ctx.parameterList().actualParameter(i);
                    functionArgs.put(constructorParams.get(i).paramName, evaluateExpression(parameter.expression()));
                }

                SymbolTable constructorTable = new SymbolTable(null, table.topLevelFunctions, table.constants, createdObj.getObjectsTable(), functionArgs);
                Visitor constructorVisitor = new Visitor<>(true, constructorTable);
                constructorTable.setParent(constructorVisitor);

                constructorSymbol.run(constructorTable);
                return createdObj;
            } else if ( ctx.parameterList() != null &&
                        !ctx.parameterList().isEmpty() && 
                        (   constructorSymbol.getParams() == null || 
                            ctx.parameterList().actualParameter().size() != constructorSymbol.getParams().size()
                        )
                    ) {
                criticalError("ERROR: Mismatch in number of parameters in method call: " + ctx.getText());
            } else {
                SymbolTable constructorTable = new SymbolTable(null, table.topLevelFunctions, table.constants, createdObj.getObjectsTable(), null);
                Visitor constructorVisitor = new Visitor<>(true, constructorTable);
                constructorTable.setParent(constructorVisitor);
                constructorSymbol.run(constructorTable);
                return createdObj;
            }

        } else if (ctx.identifier().DOT().size() > 0) {
            // Object method call
            SymbolTable variableTable = table;
            String methodName, objectName;
            if (ctx.identifier().DOT().size() > 1) {
                methodName = ctx.identifier().identifier().get(ctx.identifier().identifier().size() - 1).IDENT().getText();
                objectName = ctx.identifier().identifier().get(ctx.identifier().identifier().size() - 2).IDENT().getText();
            } else {
                methodName = ctx.identifier().identifier().get(ctx.identifier().identifier().size() - 1).IDENT().getText();
                objectName = ctx.identifier().IDENT().getText();
            }

            // Loop through idents with new symbol table until reaching correct obj

            // If the current ident is not the last in the list, iterate symbol table one object deeper
            for (int i = 0; i < ctx.identifier().identifier().size() - 2; i++) {
                IdentifierContext identCtx = ctx.identifier().identifier().get(i);
                String currentIdentString = identCtx.IDENT().getText();
                Object varObj = variableTable.getVariable(currentIdentString);

                if (varObj instanceof InterpreterObject) {
                    InterpreterObject var = (InterpreterObject) varObj;
                    if (var.getTemplate().getInterpreterSymbol(currentIdentString).getVisibility() == Visibility.PUBLIC) {
                        variableTable = new SymbolTable(this, table.topLevelFunctions, table.constants, var.getObjectsTable(), null);
                    } else {
                        criticalError("ERROR: Attempted to access non-public field: " + currentIdentString);
                    }
                } else {
                    criticalError("ERROR: Attempted to access field of non-object" + currentIdentString);
                }
            }

            Object finalVar = variableTable.getVariable(objectName);
            if (finalVar instanceof InterpreterObject) {
                InterpreterObject finalVarObj = (InterpreterObject) finalVar;

                variableTable = new SymbolTable(null, table.topLevelFunctions, table.constants, finalVarObj.getObjectsTable(), null);
                Visitor varVisitor = new Visitor<>(false, variableTable);
                variableTable.setParent(varVisitor);

                IInterpreterSymbol methodISymbol = finalVarObj.getTemplate().getInterpreterSymbol(methodName);
                if (methodISymbol instanceof InterpreterFunctionSymbol) {
                    InterpreterFunctionSymbol methodSymbol = (InterpreterFunctionSymbol) methodISymbol;
                    ArrayList<InterpreterFunctionParameter> methodParams = methodSymbol.getParams();
                    
                    if (ctx.parameterList() != null && 
                        !ctx.parameterList().isEmpty() && 
                        methodSymbol.getParams() != null && 
                        ctx.parameterList().actualParameter().size() == methodSymbol.getParams().size()
                        ) {
                        functionArgs = new LinkedHashMap<>();
        
                        for (int i = 0; i < methodParams.size(); i++) {
                            ActualParameterContext parameter = ctx.parameterList().actualParameter(i);
                            functionArgs.put(methodParams.get(i).paramName, evaluateExpression(parameter.expression()));
                        }

                        variableTable.setFunctionArgs(functionArgs);

                        methodSymbol.run(variableTable);
                        return variableTable.getReturn();
                    } else if ( ctx.parameterList() != null &&
                                !ctx.parameterList().isEmpty() && 
                                (   methodSymbol.getParams() == null || 
                                    ctx.parameterList().actualParameter().size() != methodSymbol.getParams().size()
                                )
                            ) {
                        criticalError("ERROR: Mismatch in number of parameters in method call: " + ctx.getText());
                    } else {
                        methodSymbol.run(variableTable);
                        return variableTable.getReturn();
                    }
                }
            }
        } else {
            // Top-level function call
            String functionName = ctx.identifier().IDENT().getText();
            InterpreterFunctionSymbol func = table.getTopLevelFunction(functionName);

            if (!ctx.parameterList().isEmpty() && func.getParams() != null && ctx.parameterList().actualParameter().size() == func.getParams().size()) {
                ArrayList<InterpreterFunctionParameter> funcParams = func.getParams();
                functionArgs = new LinkedHashMap<>();

                for (int i = 0; i < funcParams.size(); i++) {
                    ActualParameterContext parameter = ctx.parameterList().actualParameter(i);
                    functionArgs.put(funcParams.get(i).paramName, evaluateExpression(parameter.expression()));
                }

                table.setFunctionArgs(functionArgs);
                func.run(table);
                Object returned = table.getReturn();
                table.resetFunctionArgs();
                return returned;
            } else if (!ctx.parameterList().isEmpty() && (func.getParams() == null || ctx.parameterList().actualParameter().size() != func.getParams().size())) {
                criticalError("ERROR: Mismatch in number of parameters in function call: " + functionName);
            } else {
                func.run(table);
            }
        }
        
        
        // if (ctx.identifier().identifier() != null) {
        //     ArrayList<IdentifierContext> secondaryIdentifierContext = (ArrayList<IdentifierContext>) ctx.identifier().identifier();
        //     if (secondaryIdentifierContext.get(0).getText().equals("Create")) {
        //         InterpreterObjectTemplate template = table.getTemplateDeclaredOnTable(ctx.identifier().IDENT().getText());
        //         return template.instantiateObject(table);
        //     } else {
                
        //     }
        // }
        
        return null;
    }

    public Object evaluateVariable(VariableContext ctx) {
        // Variable will always have a top level ident which contains the remaining idents.
        // If arrays, etc. are implemented this logic needs to be reworked, but I'm not gonna do that for now :P
        ArrayList<IdentifierContext> identifierContextList = null;
        if (!ctx.identifier().get(0).identifier().isEmpty()) {
            identifierContextList = (ArrayList<IdentifierContext>) ctx.identifier().get(0).identifier();
        }

        SymbolTable variableTable = table;
        String variableName;

        if (identifierContextList != null && identifierContextList.size() > 1) {
            // Loop through idents with new symbol table until reaching correct obj

            // If the current ident is not the last in the list, iterate symbol table one object deeper
            for (int i = 0; i < identifierContextList.size() - 1; i++) {
                IdentifierContext identCtx = identifierContextList.get(i);
                String currentIdentString = identCtx.IDENT().getText();
                Object varObj = variableTable.getVariable(currentIdentString);

                if (varObj instanceof InterpreterObject) {
                    InterpreterObject var = (InterpreterObject) varObj;
                    if (var.getTemplate().getInterpreterSymbol(currentIdentString).getVisibility() == Visibility.PUBLIC) {
                        variableTable = new SymbolTable(this, table.topLevelFunctions, table.constants, var.getObjectsTable(), null);
                    } else {
                        criticalError("ERROR: Attempted to access non-public field outside of class: " + currentIdentString);
                    }
                }
            }

            variableName = identifierContextList.get(identifierContextList.size() - 1).IDENT().getText();
        } else if (identifierContextList != null) {
            IdentifierContext identCtx = identifierContextList.get(0);
            String currentObjString = ctx.identifier(0).IDENT().getText();
            String currentIdentString = identCtx.IDENT().getText();
            Object varObj = variableTable.getVariable(currentObjString);

            if (varObj instanceof InterpreterObject) {
                InterpreterObject var = (InterpreterObject) varObj;
                if (var.getTemplate().getInterpreterSymbol(currentIdentString).getVisibility() == Visibility.PUBLIC) {
                    variableTable = new SymbolTable(this, table.topLevelFunctions, table.constants, var.getObjectsTable(), null);
                } else {
                    criticalError("ERROR: Attempted to access non-public field outside of class: " + currentIdentString);
                }
            }
            variableName = identifierContextList.get(0).IDENT().getText();
         } else {
            variableName = ctx.identifier(0).IDENT().getText();
        }

        return variableTable.getVariable(variableName);
    }

    public Object evaluateUnsignedConstant(UnsignedConstantContext ctx) {
        if (ctx.unsignedNumber() != null) {
            if (ctx.unsignedNumber().unsignedInteger() != null) {
                return Integer.parseInt(ctx.unsignedNumber().unsignedInteger().getText());
            } else if (ctx.unsignedNumber().unsignedReal() != null) {
                return Double.parseDouble(ctx.unsignedNumber().unsignedInteger().getText());
            }
        } else if (ctx.constantChr() != null) {
            int charNum = Integer.parseInt(ctx.constantChr().unsignedInteger().getText());
            if (charNum >= 0 && charNum <= 255) {
                return (char) charNum;
            } else {
                criticalError("ERROR: Attempted to use Char outside of range 0-255");
            }
        } else if (ctx.string() != null) {
            return ctx.string().getText().replaceAll("^'(.*)'$", "$1");
        }

        return null;
    }

    @Override
    public T visitSimpleStatement(SimpleStatementContext ctx) {
        // System.out.println("Visited a simple statement!");

        if (ctx.assignmentStatement() != null) {
            visitAssignmentStatement(ctx.assignmentStatement());
        } else if (ctx.procedureStatement() != null) {
            visitProcedureStatement(ctx.procedureStatement());
        }

        return null;
    }

    @Override
    public T visitAssignmentStatement(AssignmentStatementContext ctx) {
        // System.out.println("Visited an assignment statement!");

        ArrayList<IdentifierContext> identifierContextList = null;
        if (ctx.variable().identifier() instanceof ArrayList) {
            identifierContextList = (ArrayList<IdentifierContext>) ctx.variable().identifier();
        }
        SymbolTable variableTable = table;
        String modifiedValueName;
        Object modifiedValue;

        if (identifierContextList != null && identifierContextList.size() > 1) {
            // Loop through idents with new symbol table until reaching correct obj

            // If the current ident is not the last in the list, iterate symbol table one object deeper
            for (int i = 0; i < identifierContextList.size() - 1; i++) {
                IdentifierContext identCtx = identifierContextList.get(i);
                String currentIdentString = identCtx.IDENT().getText();
                Object varObj = variableTable.getVariable(currentIdentString);

                if (varObj instanceof InterpreterObject) {
                    InterpreterObject var = (InterpreterObject) varObj;
                    if (var.getTemplate().getInterpreterSymbol(currentIdentString).getVisibility() == Visibility.PUBLIC) {
                        variableTable = new SymbolTable(this, table.topLevelFunctions, table.constants, var.getObjectsTable(), null);
                    } else {
                        criticalError("ERROR: Attempted to access non-public field: " + currentIdentString);
                    }
                }
            }

            modifiedValueName = identifierContextList.get(identifierContextList.size() - 1).IDENT().getText();
        } else if (identifierContextList != null) {
            modifiedValueName = identifierContextList.get(0).IDENT().getText();
        } else {
            modifiedValueName = ctx.variable().getText();
        }

        modifiedValue = evaluateExpression(ctx.expression());

        if (modifiedValueName.equals("Result")) {
            table.setReturn(modifiedValue);
        } else {
            variableTable.setVariable(modifiedValueName, modifiedValue);

            // System.out.print("Set: ");
            // System.out.print(modifiedValueName);
            // System.out.print(" to: ");
            // System.out.println(modifiedValue);
        }

        return null;
    }

    @Override
    public T visitVariable(VariableContext ctx) {
        System.out.println("Visited... something! :)");
        return null;
    }

    @Override
    public T visitExpression(ExpressionContext ctx) {
        System.out.println("Visited... something! :)");
        return null;
    }

    @Override
    public T visitRelationaloperator(RelationaloperatorContext ctx) {
        System.out.println("Visited... something! :)");
        return null;
    }

    @Override
    public T visitSimpleExpression(SimpleExpressionContext ctx) {
        System.out.println("Visited... something! :)");
        return null;
    }

    @Override
    public T visitAdditiveoperator(AdditiveoperatorContext ctx) {
        System.out.println("Visited... something! :)");
        return null;
    }

    @Override
    public T visitTerm(TermContext ctx) {
        System.out.println("Visited... something! :)");
        return null;
    }

    @Override
    public T visitMultiplicativeoperator(MultiplicativeoperatorContext ctx) {
        System.out.println("Visited... something! :)");
        return null;
    }

    @Override
    public T visitSignedFactor(SignedFactorContext ctx) {
        System.out.println("Visited... something! :)");
        return null;
    }

    @Override
    public T visitFactor(FactorContext ctx) {
        System.out.println("Visited... something! :)");
        return null;
    }

    @Override
    public T visitUnsignedConstant(UnsignedConstantContext ctx) {
        System.out.println("Visited... something! :)");
        return null;
    }

    @Override
    public T visitFunctionDesignator(FunctionDesignatorContext ctx) {
        System.out.println("Visited... something! :)");
        return null;
    }

    @Override
    public T visitParameterList(ParameterListContext ctx) {
        System.out.println("Visited... something! :)");
        return null;
    }

    @Override
    public T visitSet_(Set_Context ctx) {
        System.out.println("Visited... something! :)");
        return null;
    }

    @Override
    public T visitElementList(ElementListContext ctx) {
        System.out.println("Visited... something! :)");
        return null;
    }

    @Override
    public T visitElement(ElementContext ctx) {
        System.out.println("Visited... something! :)");
        return null;
    }

    void performWriteln(ParameterListContext params) {
        // System.out.println("Writing line.");
        for (ActualParameterContext paramCtx : params.actualParameter()) {
            System.out.print(evaluateExpression(paramCtx.expression()));
        }
        System.out.println("");
    }

    void performReadln(ParameterListContext params) {
        if (params.actualParameter().size() > 1) {
            criticalError("ERROR: Attempted to read to multiple vars: " + params.getText());
        }

        try {
            Scanner scanner = new Scanner(System.in);
            String inLine = scanner.nextLine();
            scanner.close();

            Object newVal;

            Pattern intPattern = Pattern.compile("^\\d*$");
            Pattern realPattern = Pattern.compile("^\\d+\\.\\d*$");
            if (intPattern.matcher(inLine).matches()) {
                newVal = Integer.parseInt(inLine);
            } else if (realPattern.matcher(inLine).matches()) {
                newVal = Double.parseDouble(inLine);
            } else {
                newVal = inLine;
            }

            // hardcoding bad bad bad
            VariableContext paramVariable = params.actualParameter(0).expression().simpleExpression().term().signedFactor().factor().variable();
            if (!paramVariable.identifier(0).identifier().isEmpty()) {
                // TODO get from table
            } else {
                String varToChange = paramVariable.identifier(0).IDENT().getText();

                if (table.getVariable(varToChange).getClass() == newVal.getClass()) {
                    table.setVariable(varToChange, newVal);
                } else {
                    criticalError("ERROR: Attempted to set variable to value of invalid type: " + varToChange + ", " + newVal.getClass().getName());
                }
            }

        } catch (Exception e) {
            System.err.println(e);
            criticalError("ERROR: Critical error while reading from terminal");
        }
    }

    @Override
    public T visitProcedureStatement(ProcedureStatementContext ctx) {
        // System.out.println("Visited procedure statement!");

        LinkedHashMap<String, Object> functionArgs = null;

        if (ctx.identifier() != null && ctx.identifier().IDENT().getText().equals("writeln")) {
            // Special case for writeln
            performWriteln(ctx.parameterList());
        } else if (ctx.identifier() != null && ctx.identifier().IDENT().getText().equals("readln")) {
            // Special case for readln
            performReadln(ctx.parameterList());
        } else if (ctx.identifier().DOT().size() > 0) {
            // Object method call
            SymbolTable variableTable = table;
            String methodName, objectName;
            if (ctx.identifier().DOT().size() > 1) {
                methodName = ctx.identifier().identifier().get(ctx.identifier().identifier().size() - 1).IDENT().getText();
                objectName = ctx.identifier().identifier().get(ctx.identifier().identifier().size() - 2).IDENT().getText();
            } else {
                methodName = ctx.identifier().identifier().get(ctx.identifier().identifier().size() - 1).IDENT().getText();
                objectName = ctx.identifier().IDENT().getText();
            }

            // Loop through idents with new symbol table until reaching correct obj

            // If the current ident is not the last in the list, iterate symbol table one object deeper
            for (int i = 0; i < ctx.identifier().identifier().size() - 2; i++) {
                IdentifierContext identCtx = ctx.identifier().identifier().get(i);
                String currentIdentString = identCtx.IDENT().getText();
                Object varObj = variableTable.getVariable(currentIdentString);

                if (varObj instanceof InterpreterObject) {
                    InterpreterObject var = (InterpreterObject) varObj;
                    if (var.getTemplate().getInterpreterSymbol(currentIdentString).getVisibility() == Visibility.PUBLIC) {
                        variableTable = new SymbolTable(this, table.topLevelFunctions, table.constants, var.getObjectsTable(), null);
                    } else {
                        criticalError("ERROR: Attempted to access non-public field: " + currentIdentString);
                    }
                } else {
                    criticalError("ERROR: Attempted to access field of non-object" + currentIdentString);
                }
            }

            Object finalVar = variableTable.getVariable(objectName);
            if (finalVar instanceof InterpreterObject) {
                InterpreterObject finalVarObj = (InterpreterObject) finalVar;

                variableTable = new SymbolTable(null, table.topLevelFunctions, table.constants, finalVarObj.getObjectsTable(), null);
                Visitor varVisitor = new Visitor<>(false, variableTable);
                variableTable.setParent(varVisitor);

                IInterpreterSymbol methodISymbol = finalVarObj.getTemplate().getInterpreterSymbol(methodName);
                if (methodISymbol instanceof InterpreterFunctionSymbol) {
                    InterpreterFunctionSymbol methodSymbol = (InterpreterFunctionSymbol) methodISymbol;
                    ArrayList<InterpreterFunctionParameter> methodParams = methodSymbol.getParams();
                    
                    if (ctx.parameterList() != null && 
                        !ctx.parameterList().isEmpty() && 
                        methodSymbol.getParams() != null && 
                        ctx.parameterList().actualParameter().size() == methodSymbol.getParams().size()
                        ) {
                        functionArgs = new LinkedHashMap<>();
        
                        for (int i = 0; i < methodParams.size(); i++) {
                            ActualParameterContext parameter = ctx.parameterList().actualParameter(i);
                            functionArgs.put(methodParams.get(i).paramName, evaluateExpression(parameter.expression()));
                        }

                        variableTable.setFunctionArgs(functionArgs);

                        methodSymbol.run(variableTable);
                    } else if ( ctx.parameterList() != null &&
                                !ctx.parameterList().isEmpty() && 
                                (   methodSymbol.getParams() == null || 
                                    ctx.parameterList().actualParameter().size() != methodSymbol.getParams().size()
                                )
                            ) {
                        criticalError("ERROR: Mismatch in number of parameters in method call: " + ctx.getText());
                    } else {
                        methodSymbol.run(variableTable);
                    }

                    if (methodName.equals("Free")) {
                        variableTable.removeVariableFromTable(objectName);
                    }
                } else {
                    criticalError("ERROR: Procedure not found on table: " + ctx.getText());
                }
            }
        } else {
            // Top-level function call
            String functionName = ctx.identifier().IDENT().getText();
            InterpreterFunctionSymbol func = table.getTopLevelFunction(functionName);

            if (!ctx.parameterList().isEmpty() && func.getParams() != null && ctx.parameterList().actualParameter().size() == func.getParams().size()) {
                ArrayList<InterpreterFunctionParameter> funcParams = func.getParams();
                functionArgs = new LinkedHashMap<>();

                for (int i = 0; i < funcParams.size(); i++) {
                    ActualParameterContext parameter = ctx.parameterList().actualParameter(i);
                    functionArgs.put(funcParams.get(i).paramName, evaluateExpression(parameter.expression()));
                }

                table.setFunctionArgs(functionArgs);
                func.run(table);
                table.resetFunctionArgs();
            } else if (!ctx.parameterList().isEmpty() && (func.getParams() == null || ctx.parameterList().actualParameter().size() != func.getParams().size())) {
                criticalError("ERROR: Mismatch in number of parameters in function call: " + functionName);
            } else {
                func.run(table);
            }
        }

        return null;
    }

    @Override
    public T visitActualParameter(ActualParameterContext ctx) {
        System.out.println("Visited... something! :)");
        return null;
    }

    @Override
    public T visitParameterwidth(ParameterwidthContext ctx) {
        System.out.println("Visited... something! :)");
        return null;
    }

    @Override
    public T visitGotoStatement(GotoStatementContext ctx) {
        System.out.println("Visited... something! :)");
        return null;
    }

    @Override
    public T visitEmptyStatement_(EmptyStatement_Context ctx) {
        System.out.println("Visited... something! :)");
        return null;
    }

    @Override
    public T visitEmpty_(Empty_Context ctx) {
        System.out.println("Visited... something! :)");
        return null;
    }

    @Override
    public T visitStructuredStatement(StructuredStatementContext ctx) {
        System.out.println("Visited... something! :)");
        return null;
    }

    @Override
    public T visitCompoundStatement(CompoundStatementContext ctx) {
        // System.out.println("Visited compound statement!");
        if (ctx.statements() != null) {
            visitStatements(ctx.statements());
        }
        return null;
    }

    @Override
    public T visitStatements(StatementsContext ctx) {
        // System.out.println("Visited statements!");

        for (StatementContext statement : ctx.statement()) {
            visitStatement(statement);
        }

        return null;
    }

    @Override
    public T visitConditionalStatement(ConditionalStatementContext ctx) {
        System.out.println("Visited... something! :)");
        return null;
    }

    @Override
    public T visitIfStatement(IfStatementContext ctx) {
        System.out.println("Visited... something! :)");
        return null;
    }

    @Override
    public T visitCaseStatement(CaseStatementContext ctx) {
        System.out.println("Visited... something! :)");
        return null;
    }

    @Override
    public T visitCaseListElement(CaseListElementContext ctx) {
        System.out.println("Visited... something! :)");
        return null;
    }

    @Override
    public T visitRepetetiveStatement(RepetetiveStatementContext ctx) {
        System.out.println("Visited... something! :)");
        return null;
    }

    @Override
    public T visitWhileStatement(WhileStatementContext ctx) {
        System.out.println("Visited... something! :)");
        return null;
    }

    @Override
    public T visitRepeatStatement(RepeatStatementContext ctx) {
        System.out.println("Visited... something! :)");
        return null;
    }

    @Override
    public T visitForStatement(ForStatementContext ctx) {
        System.out.println("Visited... something! :)");
        return null;
    }

    @Override
    public T visitForList(ForListContext ctx) {
        System.out.println("Visited... something! :)");
        return null;
    }

    @Override
    public T visitInitialValue(InitialValueContext ctx) {
        System.out.println("Visited... something! :)");
        return null;
    }

    @Override
    public T visitFinalValue(FinalValueContext ctx) {
        System.out.println("Visited... something! :)");
        return null;
    }

    @Override
    public T visitWithStatement(WithStatementContext ctx) {
        System.out.println("Visited... something! :)");
        return null;
    }

    @Override
    public T visitRecordVariableList(RecordVariableListContext ctx) {
        System.out.println("Visited... something! :)");
        return null;
    }

    @Override
    public T visitInheritedStatement(InheritedStatementContext ctx) {
        throw new UnsupportedOperationException("Unimplemented method 'visitInheritedStatement'");
    }
    
}
