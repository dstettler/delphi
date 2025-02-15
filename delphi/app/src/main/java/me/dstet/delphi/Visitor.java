package me.dstet.delphi;

import java.util.ArrayList;

import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.RuleNode;
import org.antlr.v4.runtime.tree.TerminalNode;

import me.dstet.delphi.delphiParser.*;
import me.dstet.delphi.interpreter.IInterpreterSymbol;
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
        System.out.println("Visited program!");
        
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
        System.out.println("Visited block!");

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

        return null;
    }

    @Override
    public T visitClassBlock(ClassBlockContext ctx) {
        System.out.println("Visited Class block!");
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
        System.out.println("Visited constant definition part");

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
        System.out.println("Visited Constant Definition!");
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
        System.out.println("Visited TypeDefinitionPart!");
        for (TypeDefinitionContext t : ctx.typeDefinition()) {
            visitTypeDefinition(t);
        }
        table.printSymbolTable();
        return null;
    }

    @Override
    public T visitTypeDefinition(TypeDefinitionContext ctx) {
        System.out.println("Visited TypeDefinition!");
        String typeName = ctx.identifier().IDENT().getText();
        System.out.println("Got type: " + typeName);


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
        System.out.println("Visited ClassType!");
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
        System.out.println("Visited variable declaration part!");

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
        System.out.println("Visited Variable Declaration!");
        for (IdentifierContext id : ctx.identifierList().identifier()) {
            String propertyName = id.IDENT().getText();
            Primitive primitiveType = getTypeFromTypeIdentContext(ctx.type_().simpleType().typeIdentifier());
            String typeString = null;
            if (primitiveType == Primitive.NotPrimitive) {
                typeString = ctx.type_().simpleType().typeIdentifier().identifier().IDENT().getText();
            }

            if (readingClass) {
                IInterpreterSymbol symbol;
                if (primitiveType != Primitive.NotPrimitive) {
                    symbol = new InterpreterPrimitiveSymbol(currentClassVisibility, primitiveType);
                } else {
                    symbol = new InterpreterTemplateSymbol(currentClassVisibility, typeString);
                }

                InterpreterObjectTemplate template = table.getTemplateDeclaredOnTable(currentClassname);
                template.addInterpreterSymbol(propertyName, symbol);
            } else {
                if (primitiveType != Primitive.NotPrimitive) {
                    Object defaultPrimitive = PrimitiveUtils.getDefaultFromPrimitive(primitiveType);
                    if (!table.addVariableToVars(propertyName, defaultPrimitive)) {
                        criticalError(String.format("ERROR: Error declaring variable of type: %s", typeString));
                    }
                } else {
                    InterpreterObjectTemplate template = table.getTemplateDeclaredOnTable(typeString);
                    if (template != null) {
                        if (!table.addVariableToVars(propertyName, null)) {
                            criticalError(String.format("ERROR: Attempted to declare var of invalid type: %s", typeString));
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
        System.out.println("Visited class constructor!");

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
        template.addInterpreterSymbol(functionName, symbol);

        return null;
    }

    @Override
    public T visitClassDestructorDeclaration(ClassDestructorDeclarationContext ctx) {
        System.out.println("Visited class destructor!");

        InterpreterObjectTemplate template = table.getTemplateDeclaredOnTable(currentClassname);

        String functionName = ctx.identifier().IDENT().getText();

        // Destructor will never have parameters so leave that argument null.
        InterpreterFunctionSymbol symbol = new InterpreterFunctionSymbol(currentClassVisibility, null, Primitive.Void);
        template.addInterpreterSymbol(functionName, symbol);
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
        System.out.println("Visited class procedure declaration!");

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
        template.addInterpreterSymbol(functionName, symbol);

        return null;
    }

    @Override
    public T visitProcedureDeclaration(ProcedureDeclarationContext ctx) {
        System.out.println("Visited procedure declaration!");
        // TODO
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
        System.out.println("Visited class function declaration!");

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
        template.addInterpreterSymbol(functionName, symbol);

        return null;
    }

    @Override
    public T visitFunctionDeclaration(FunctionDeclarationContext ctx) {
        System.out.println("Visited function declaration!");

        // TODO

        return null;
    }

    @Override
    public T visitResultType(ResultTypeContext ctx) {
        System.out.println("Visited... something! :)");
        return null;
    }

    @Override
    public T visitStatement(StatementContext ctx) {
        System.out.println("Visited... something! :)");
        return null;
    }

    @Override
    public T visitUnlabelledStatement(UnlabelledStatementContext ctx) {
        System.out.println("Visited... something! :)");
        return null;
    }

    @Override
    public T visitSimpleStatement(SimpleStatementContext ctx) {
        System.out.println("Visited... something! :)");
        return null;
    }

    @Override
    public T visitAssignmentStatement(AssignmentStatementContext ctx) {
        System.out.println("Visited... something! :)");
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

    @Override
    public T visitProcedureStatement(ProcedureStatementContext ctx) {
        System.out.println("Visited... something! :)");
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
        System.out.println("Visited... something! :)");
        return null;
    }

    @Override
    public T visitStatements(StatementsContext ctx) {
        System.out.println("Visited... something! :)");
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
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'visitInheritedStatement'");
    }
    
}
