package me.dstet.delphi;

import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.RuleNode;
import org.antlr.v4.runtime.tree.TerminalNode;

import me.dstet.delphi.delphiParser.*;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link delphiParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public class Visitor<T> implements delphiVisitor<T> {

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
        return visitProgram((ProgramContext) arg0.getChild(0) );
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
        return null;
    }

    @Override
    public T visitClassBlock(ClassBlockContext ctx) {
        System.out.println("Visited... something! :)");
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
        System.out.println("Visited... something! :)");
        return null;
    }

    @Override
    public T visitConstantDefinition(ConstantDefinitionContext ctx) {
        System.out.println("Visited... something! :)");
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
        System.out.println("Visited... something! :)");
        return null;
    }

    @Override
    public T visitTypeDefinition(TypeDefinitionContext ctx) {
        System.out.println("Visited... something! :)");
        return null;
    }

    @Override
    public T visitVisibility(VisibilityContext ctx) {
        System.out.println("Visited... something! :)");
        return null;
    }

    @Override
    public T visitClassType(ClassTypeContext ctx) {
        System.out.println("Visited... something! :)");
        return null;
    }

    @Override
    public T visitFunctionType(FunctionTypeContext ctx) {
        System.out.println("Visited... something! :)");
        return null;
    }

    @Override
    public T visitProcedureType(ProcedureTypeContext ctx) {
        System.out.println("Visited... something! :)");
        return null;
    }

    @Override
    public T visitType_(Type_Context ctx) {
        System.out.println("Visited... something! :)");
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
        System.out.println("Visited... something! :)");
        return null;
    }

    @Override
    public T visitVariableDeclaration(VariableDeclarationContext ctx) {
        System.out.println("Visited... something! :)");
        return null;
    }

    @Override
    public T visitClassProcedureAndFunctionDeclarationPart(ClassProcedureAndFunctionDeclarationPartContext ctx) {
        System.out.println("Visited... something! :)");
        return null;
    }

    @Override
    public T visitProcedureAndFunctionDeclarationPart(ProcedureAndFunctionDeclarationPartContext ctx) {
        System.out.println("Visited... something! :)");
        return null;
    }

    @Override
    public T visitClassProcedureOrFunctionDeclaration(ClassProcedureOrFunctionDeclarationContext ctx) {
        System.out.println("Visited... something! :)");
        return null;
    }

    @Override
    public T visitClassConstructorDeclaration(ClassConstructorDeclarationContext ctx) {
        System.out.println("Visited... something! :)");
        return null;
    }

    @Override
    public T visitClassDestructorDeclaration(ClassDestructorDeclarationContext ctx) {
        System.out.println("Visited... something! :)");
        return null;
    }

    @Override
    public T visitProcedureOrFunctionDeclaration(ProcedureOrFunctionDeclarationContext ctx) {
        System.out.println("Visited... something! :)");
        return null;
    }

    @Override
    public T visitClassProcedureDeclaration(ClassProcedureDeclarationContext ctx) {
        System.out.println("Visited... something! :)");
        return null;
    }

    @Override
    public T visitProcedureDeclaration(ProcedureDeclarationContext ctx) {
        System.out.println("Visited... something! :)");
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
        System.out.println("Visited... something! :)");
        return null;
    }

    @Override
    public T visitFunctionDeclaration(FunctionDeclarationContext ctx) {
        System.out.println("Visited... something! :)");
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
    
}
