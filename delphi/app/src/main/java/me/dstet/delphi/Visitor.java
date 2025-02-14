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
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'visitChildren'");
    }

    @Override
    public T visitErrorNode(ErrorNode arg0) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'visitErrorNode'");
    }

    @Override
    public T visitTerminal(TerminalNode arg0) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'visitTerminal'");
    }

    @Override
    public T visitProgram(ProgramContext ctx) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'visitProgram'");
    }

    @Override
    public T visitProgramHeading(ProgramHeadingContext ctx) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'visitProgramHeading'");
    }

    @Override
    public T visitIdentifier(IdentifierContext ctx) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'visitIdentifier'");
    }

    @Override
    public T visitBlock(BlockContext ctx) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'visitBlock'");
    }

    @Override
    public T visitClassBlock(ClassBlockContext ctx) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'visitClassBlock'");
    }

    @Override
    public T visitUsesUnitsPart(UsesUnitsPartContext ctx) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'visitUsesUnitsPart'");
    }

    @Override
    public T visitLabelDeclarationPart(LabelDeclarationPartContext ctx) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'visitLabelDeclarationPart'");
    }

    @Override
    public T visitLabel(LabelContext ctx) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'visitLabel'");
    }

    @Override
    public T visitConstantDefinitionPart(ConstantDefinitionPartContext ctx) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'visitConstantDefinitionPart'");
    }

    @Override
    public T visitConstantDefinition(ConstantDefinitionContext ctx) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'visitConstantDefinition'");
    }

    @Override
    public T visitConstantChr(ConstantChrContext ctx) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'visitConstantChr'");
    }

    @Override
    public T visitConstant(ConstantContext ctx) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'visitConstant'");
    }

    @Override
    public T visitUnsignedNumber(UnsignedNumberContext ctx) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'visitUnsignedNumber'");
    }

    @Override
    public T visitUnsignedInteger(UnsignedIntegerContext ctx) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'visitUnsignedInteger'");
    }

    @Override
    public T visitUnsignedReal(UnsignedRealContext ctx) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'visitUnsignedReal'");
    }

    @Override
    public T visitSign(SignContext ctx) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'visitSign'");
    }

    @Override
    public T visitBool_(Bool_Context ctx) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'visitBool_'");
    }

    @Override
    public T visitString(StringContext ctx) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'visitString'");
    }

    @Override
    public T visitTypeDefinitionPart(TypeDefinitionPartContext ctx) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'visitTypeDefinitionPart'");
    }

    @Override
    public T visitTypeDefinition(TypeDefinitionContext ctx) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'visitTypeDefinition'");
    }

    @Override
    public T visitVisibility(VisibilityContext ctx) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'visitVisibility'");
    }

    @Override
    public T visitClassType(ClassTypeContext ctx) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'visitClassType'");
    }

    @Override
    public T visitFunctionType(FunctionTypeContext ctx) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'visitFunctionType'");
    }

    @Override
    public T visitProcedureType(ProcedureTypeContext ctx) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'visitProcedureType'");
    }

    @Override
    public T visitType_(Type_Context ctx) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'visitType_'");
    }

    @Override
    public T visitSimpleType(SimpleTypeContext ctx) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'visitSimpleType'");
    }

    @Override
    public T visitScalarType(ScalarTypeContext ctx) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'visitScalarType'");
    }

    @Override
    public T visitSubrangeType(SubrangeTypeContext ctx) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'visitSubrangeType'");
    }

    @Override
    public T visitTypeIdentifier(TypeIdentifierContext ctx) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'visitTypeIdentifier'");
    }

    @Override
    public T visitStructuredType(StructuredTypeContext ctx) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'visitStructuredType'");
    }

    @Override
    public T visitUnpackedStructuredType(UnpackedStructuredTypeContext ctx) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'visitUnpackedStructuredType'");
    }

    @Override
    public T visitStringtype(StringtypeContext ctx) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'visitStringtype'");
    }

    @Override
    public T visitArrayType(ArrayTypeContext ctx) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'visitArrayType'");
    }

    @Override
    public T visitTypeList(TypeListContext ctx) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'visitTypeList'");
    }

    @Override
    public T visitIndexType(IndexTypeContext ctx) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'visitIndexType'");
    }

    @Override
    public T visitComponentType(ComponentTypeContext ctx) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'visitComponentType'");
    }

    @Override
    public T visitRecordType(RecordTypeContext ctx) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'visitRecordType'");
    }

    @Override
    public T visitFieldList(FieldListContext ctx) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'visitFieldList'");
    }

    @Override
    public T visitFixedPart(FixedPartContext ctx) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'visitFixedPart'");
    }

    @Override
    public T visitRecordSection(RecordSectionContext ctx) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'visitRecordSection'");
    }

    @Override
    public T visitVariantPart(VariantPartContext ctx) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'visitVariantPart'");
    }

    @Override
    public T visitTag(TagContext ctx) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'visitTag'");
    }

    @Override
    public T visitVariant(VariantContext ctx) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'visitVariant'");
    }

    @Override
    public T visitSetType(SetTypeContext ctx) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'visitSetType'");
    }

    @Override
    public T visitBaseType(BaseTypeContext ctx) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'visitBaseType'");
    }

    @Override
    public T visitFileType(FileTypeContext ctx) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'visitFileType'");
    }

    @Override
    public T visitPointerType(PointerTypeContext ctx) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'visitPointerType'");
    }

    @Override
    public T visitVariableDeclarationPart(VariableDeclarationPartContext ctx) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'visitVariableDeclarationPart'");
    }

    @Override
    public T visitVariableDeclaration(VariableDeclarationContext ctx) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'visitVariableDeclaration'");
    }

    @Override
    public T visitClassProcedureAndFunctionDeclarationPart(ClassProcedureAndFunctionDeclarationPartContext ctx) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'visitClassProcedureAndFunctionDeclarationPart'");
    }

    @Override
    public T visitProcedureAndFunctionDeclarationPart(ProcedureAndFunctionDeclarationPartContext ctx) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'visitProcedureAndFunctionDeclarationPart'");
    }

    @Override
    public T visitClassProcedureOrFunctionDeclaration(ClassProcedureOrFunctionDeclarationContext ctx) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'visitClassProcedureOrFunctionDeclaration'");
    }

    @Override
    public T visitClassConstructorDeclaration(ClassConstructorDeclarationContext ctx) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'visitClassConstructorDeclaration'");
    }

    @Override
    public T visitClassDestructorDeclaration(ClassDestructorDeclarationContext ctx) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'visitClassDestructorDeclaration'");
    }

    @Override
    public T visitProcedureOrFunctionDeclaration(ProcedureOrFunctionDeclarationContext ctx) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'visitProcedureOrFunctionDeclaration'");
    }

    @Override
    public T visitClassProcedureDeclaration(ClassProcedureDeclarationContext ctx) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'visitClassProcedureDeclaration'");
    }

    @Override
    public T visitProcedureDeclaration(ProcedureDeclarationContext ctx) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'visitProcedureDeclaration'");
    }

    @Override
    public T visitFormalParameterList(FormalParameterListContext ctx) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'visitFormalParameterList'");
    }

    @Override
    public T visitFormalParameterSection(FormalParameterSectionContext ctx) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'visitFormalParameterSection'");
    }

    @Override
    public T visitParameterGroup(ParameterGroupContext ctx) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'visitParameterGroup'");
    }

    @Override
    public T visitIdentifierList(IdentifierListContext ctx) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'visitIdentifierList'");
    }

    @Override
    public T visitConstList(ConstListContext ctx) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'visitConstList'");
    }

    @Override
    public T visitClassFunctionDeclaration(ClassFunctionDeclarationContext ctx) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'visitClassFunctionDeclaration'");
    }

    @Override
    public T visitFunctionDeclaration(FunctionDeclarationContext ctx) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'visitFunctionDeclaration'");
    }

    @Override
    public T visitResultType(ResultTypeContext ctx) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'visitResultType'");
    }

    @Override
    public T visitStatement(StatementContext ctx) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'visitStatement'");
    }

    @Override
    public T visitUnlabelledStatement(UnlabelledStatementContext ctx) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'visitUnlabelledStatement'");
    }

    @Override
    public T visitSimpleStatement(SimpleStatementContext ctx) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'visitSimpleStatement'");
    }

    @Override
    public T visitAssignmentStatement(AssignmentStatementContext ctx) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'visitAssignmentStatement'");
    }

    @Override
    public T visitVariable(VariableContext ctx) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'visitVariable'");
    }

    @Override
    public T visitExpression(ExpressionContext ctx) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'visitExpression'");
    }

    @Override
    public T visitRelationaloperator(RelationaloperatorContext ctx) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'visitRelationaloperator'");
    }

    @Override
    public T visitSimpleExpression(SimpleExpressionContext ctx) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'visitSimpleExpression'");
    }

    @Override
    public T visitAdditiveoperator(AdditiveoperatorContext ctx) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'visitAdditiveoperator'");
    }

    @Override
    public T visitTerm(TermContext ctx) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'visitTerm'");
    }

    @Override
    public T visitMultiplicativeoperator(MultiplicativeoperatorContext ctx) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'visitMultiplicativeoperator'");
    }

    @Override
    public T visitSignedFactor(SignedFactorContext ctx) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'visitSignedFactor'");
    }

    @Override
    public T visitFactor(FactorContext ctx) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'visitFactor'");
    }

    @Override
    public T visitUnsignedConstant(UnsignedConstantContext ctx) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'visitUnsignedConstant'");
    }

    @Override
    public T visitFunctionDesignator(FunctionDesignatorContext ctx) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'visitFunctionDesignator'");
    }

    @Override
    public T visitParameterList(ParameterListContext ctx) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'visitParameterList'");
    }

    @Override
    public T visitSet_(Set_Context ctx) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'visitSet_'");
    }

    @Override
    public T visitElementList(ElementListContext ctx) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'visitElementList'");
    }

    @Override
    public T visitElement(ElementContext ctx) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'visitElement'");
    }

    @Override
    public T visitProcedureStatement(ProcedureStatementContext ctx) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'visitProcedureStatement'");
    }

    @Override
    public T visitActualParameter(ActualParameterContext ctx) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'visitActualParameter'");
    }

    @Override
    public T visitParameterwidth(ParameterwidthContext ctx) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'visitParameterwidth'");
    }

    @Override
    public T visitGotoStatement(GotoStatementContext ctx) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'visitGotoStatement'");
    }

    @Override
    public T visitEmptyStatement_(EmptyStatement_Context ctx) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'visitEmptyStatement_'");
    }

    @Override
    public T visitEmpty_(Empty_Context ctx) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'visitEmpty_'");
    }

    @Override
    public T visitStructuredStatement(StructuredStatementContext ctx) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'visitStructuredStatement'");
    }

    @Override
    public T visitCompoundStatement(CompoundStatementContext ctx) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'visitCompoundStatement'");
    }

    @Override
    public T visitStatements(StatementsContext ctx) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'visitStatements'");
    }

    @Override
    public T visitConditionalStatement(ConditionalStatementContext ctx) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'visitConditionalStatement'");
    }

    @Override
    public T visitIfStatement(IfStatementContext ctx) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'visitIfStatement'");
    }

    @Override
    public T visitCaseStatement(CaseStatementContext ctx) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'visitCaseStatement'");
    }

    @Override
    public T visitCaseListElement(CaseListElementContext ctx) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'visitCaseListElement'");
    }

    @Override
    public T visitRepetetiveStatement(RepetetiveStatementContext ctx) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'visitRepetetiveStatement'");
    }

    @Override
    public T visitWhileStatement(WhileStatementContext ctx) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'visitWhileStatement'");
    }

    @Override
    public T visitRepeatStatement(RepeatStatementContext ctx) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'visitRepeatStatement'");
    }

    @Override
    public T visitForStatement(ForStatementContext ctx) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'visitForStatement'");
    }

    @Override
    public T visitForList(ForListContext ctx) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'visitForList'");
    }

    @Override
    public T visitInitialValue(InitialValueContext ctx) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'visitInitialValue'");
    }

    @Override
    public T visitFinalValue(FinalValueContext ctx) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'visitFinalValue'");
    }

    @Override
    public T visitWithStatement(WithStatementContext ctx) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'visitWithStatement'");
    }

    @Override
    public T visitRecordVariableList(RecordVariableListContext ctx) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'visitRecordVariableList'");
    }
    
}
