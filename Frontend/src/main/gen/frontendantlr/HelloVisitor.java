// Generated from /Users/f0gel/IdeaProjects/MiniPython/Frontend/src/main/java/frontendantlr/Hello.g4 by ANTLR 4.13.1
package frontendantlr;
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link HelloParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface HelloVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link HelloParser#start}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStart(HelloParser.StartContext ctx);
	/**
	 * Visit a parse tree produced by {@link HelloParser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStatement(HelloParser.StatementContext ctx);
	/**
	 * Visit a parse tree produced by the {@code print}
	 * labeled alternative in {@link HelloParser#builtin}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPrint(HelloParser.PrintContext ctx);
	/**
	 * Visit a parse tree produced by the {@code castStr}
	 * labeled alternative in {@link HelloParser#builtin}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCastStr(HelloParser.CastStrContext ctx);
	/**
	 * Visit a parse tree produced by the {@code castInt}
	 * labeled alternative in {@link HelloParser#builtin}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCastInt(HelloParser.CastIntContext ctx);
	/**
	 * Visit a parse tree produced by the {@code callId}
	 * labeled alternative in {@link HelloParser#builtin}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCallId(HelloParser.CallIdContext ctx);
	/**
	 * Visit a parse tree produced by the {@code callInput}
	 * labeled alternative in {@link HelloParser#builtin}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCallInput(HelloParser.CallInputContext ctx);
	/**
	 * Visit a parse tree produced by the {@code callSuper}
	 * labeled alternative in {@link HelloParser#builtin}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCallSuper(HelloParser.CallSuperContext ctx);
	/**
	 * Visit a parse tree produced by {@link HelloParser#function}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFunction(HelloParser.FunctionContext ctx);
	/**
	 * Visit a parse tree produced by {@link HelloParser#functionparameterdeclarationlist}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFunctionparameterdeclarationlist(HelloParser.FunctionparameterdeclarationlistContext ctx);
	/**
	 * Visit a parse tree produced by {@link HelloParser#functionstatementlist}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFunctionstatementlist(HelloParser.FunctionstatementlistContext ctx);
	/**
	 * Visit a parse tree produced by {@link HelloParser#functionCall}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFunctionCall(HelloParser.FunctionCallContext ctx);
	/**
	 * Visit a parse tree produced by {@link HelloParser#functionparameterpasslist}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFunctionparameterpasslist(HelloParser.FunctionparameterpasslistContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ANDOR}
	 * labeled alternative in {@link HelloParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitANDOR(HelloParser.ANDORContext ctx);
	/**
	 * Visit a parse tree produced by the {@code expressionSelf}
	 * labeled alternative in {@link HelloParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpressionSelf(HelloParser.ExpressionSelfContext ctx);
	/**
	 * Visit a parse tree produced by the {@code expressionReturn}
	 * labeled alternative in {@link HelloParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpressionReturn(HelloParser.ExpressionReturnContext ctx);
	/**
	 * Visit a parse tree produced by the {@code EQUALS}
	 * labeled alternative in {@link HelloParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEQUALS(HelloParser.EQUALSContext ctx);
	/**
	 * Visit a parse tree produced by the {@code LESSEQ}
	 * labeled alternative in {@link HelloParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLESSEQ(HelloParser.LESSEQContext ctx);
	/**
	 * Visit a parse tree produced by the {@code GREATER}
	 * labeled alternative in {@link HelloParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitGREATER(HelloParser.GREATERContext ctx);
	/**
	 * Visit a parse tree produced by the {@code NOTEQUALS}
	 * labeled alternative in {@link HelloParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNOTEQUALS(HelloParser.NOTEQUALSContext ctx);
	/**
	 * Visit a parse tree produced by the {@code NOT}
	 * labeled alternative in {@link HelloParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNOT(HelloParser.NOTContext ctx);
	/**
	 * Visit a parse tree produced by the {@code expressionBoolean}
	 * labeled alternative in {@link HelloParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpressionBoolean(HelloParser.ExpressionBooleanContext ctx);
	/**
	 * Visit a parse tree produced by the {@code expressionID}
	 * labeled alternative in {@link HelloParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpressionID(HelloParser.ExpressionIDContext ctx);
	/**
	 * Visit a parse tree produced by the {@code GREATEREQ}
	 * labeled alternative in {@link HelloParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitGREATEREQ(HelloParser.GREATEREQContext ctx);
	/**
	 * Visit a parse tree produced by the {@code expressionNumber}
	 * labeled alternative in {@link HelloParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpressionNumber(HelloParser.ExpressionNumberContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ADDSUB}
	 * labeled alternative in {@link HelloParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitADDSUB(HelloParser.ADDSUBContext ctx);
	/**
	 * Visit a parse tree produced by the {@code expressionNested}
	 * labeled alternative in {@link HelloParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpressionNested(HelloParser.ExpressionNestedContext ctx);
	/**
	 * Visit a parse tree produced by the {@code expressionString}
	 * labeled alternative in {@link HelloParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpressionString(HelloParser.ExpressionStringContext ctx);
	/**
	 * Visit a parse tree produced by the {@code LESS}
	 * labeled alternative in {@link HelloParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLESS(HelloParser.LESSContext ctx);
	/**
	 * Visit a parse tree produced by the {@code MULDIV}
	 * labeled alternative in {@link HelloParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMULDIV(HelloParser.MULDIVContext ctx);
	/**
	 * Visit a parse tree produced by {@link HelloParser#decdef}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDecdef(HelloParser.DecdefContext ctx);
	/**
	 * Visit a parse tree produced by {@link HelloParser#class}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitClass(HelloParser.ClassContext ctx);
	/**
	 * Visit a parse tree produced by {@link HelloParser#while}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitWhile(HelloParser.WhileContext ctx);
	/**
	 * Visit a parse tree produced by {@link HelloParser#ifThenElseStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIfThenElseStatement(HelloParser.IfThenElseStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link HelloParser#if}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIf(HelloParser.IfContext ctx);
	/**
	 * Visit a parse tree produced by {@link HelloParser#elif}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitElif(HelloParser.ElifContext ctx);
	/**
	 * Visit a parse tree produced by {@link HelloParser#else}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitElse(HelloParser.ElseContext ctx);
	/**
	 * Visit a parse tree produced by {@link HelloParser#block}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBlock(HelloParser.BlockContext ctx);
}