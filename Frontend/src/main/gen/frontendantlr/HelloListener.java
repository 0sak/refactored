// Generated from /Users/f0gel/IdeaProjects/MiniPython/Frontend/src/main/java/frontendantlr/Hello.g4 by ANTLR 4.13.1
package frontendantlr;
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link HelloParser}.
 */
public interface HelloListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link HelloParser#start}.
	 * @param ctx the parse tree
	 */
	void enterStart(HelloParser.StartContext ctx);
	/**
	 * Exit a parse tree produced by {@link HelloParser#start}.
	 * @param ctx the parse tree
	 */
	void exitStart(HelloParser.StartContext ctx);
	/**
	 * Enter a parse tree produced by {@link HelloParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterStatement(HelloParser.StatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link HelloParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitStatement(HelloParser.StatementContext ctx);
	/**
	 * Enter a parse tree produced by the {@code print}
	 * labeled alternative in {@link HelloParser#builtin}.
	 * @param ctx the parse tree
	 */
	void enterPrint(HelloParser.PrintContext ctx);
	/**
	 * Exit a parse tree produced by the {@code print}
	 * labeled alternative in {@link HelloParser#builtin}.
	 * @param ctx the parse tree
	 */
	void exitPrint(HelloParser.PrintContext ctx);
	/**
	 * Enter a parse tree produced by the {@code castStr}
	 * labeled alternative in {@link HelloParser#builtin}.
	 * @param ctx the parse tree
	 */
	void enterCastStr(HelloParser.CastStrContext ctx);
	/**
	 * Exit a parse tree produced by the {@code castStr}
	 * labeled alternative in {@link HelloParser#builtin}.
	 * @param ctx the parse tree
	 */
	void exitCastStr(HelloParser.CastStrContext ctx);
	/**
	 * Enter a parse tree produced by the {@code castInt}
	 * labeled alternative in {@link HelloParser#builtin}.
	 * @param ctx the parse tree
	 */
	void enterCastInt(HelloParser.CastIntContext ctx);
	/**
	 * Exit a parse tree produced by the {@code castInt}
	 * labeled alternative in {@link HelloParser#builtin}.
	 * @param ctx the parse tree
	 */
	void exitCastInt(HelloParser.CastIntContext ctx);
	/**
	 * Enter a parse tree produced by the {@code callId}
	 * labeled alternative in {@link HelloParser#builtin}.
	 * @param ctx the parse tree
	 */
	void enterCallId(HelloParser.CallIdContext ctx);
	/**
	 * Exit a parse tree produced by the {@code callId}
	 * labeled alternative in {@link HelloParser#builtin}.
	 * @param ctx the parse tree
	 */
	void exitCallId(HelloParser.CallIdContext ctx);
	/**
	 * Enter a parse tree produced by the {@code callInput}
	 * labeled alternative in {@link HelloParser#builtin}.
	 * @param ctx the parse tree
	 */
	void enterCallInput(HelloParser.CallInputContext ctx);
	/**
	 * Exit a parse tree produced by the {@code callInput}
	 * labeled alternative in {@link HelloParser#builtin}.
	 * @param ctx the parse tree
	 */
	void exitCallInput(HelloParser.CallInputContext ctx);
	/**
	 * Enter a parse tree produced by the {@code callSuper}
	 * labeled alternative in {@link HelloParser#builtin}.
	 * @param ctx the parse tree
	 */
	void enterCallSuper(HelloParser.CallSuperContext ctx);
	/**
	 * Exit a parse tree produced by the {@code callSuper}
	 * labeled alternative in {@link HelloParser#builtin}.
	 * @param ctx the parse tree
	 */
	void exitCallSuper(HelloParser.CallSuperContext ctx);
	/**
	 * Enter a parse tree produced by {@link HelloParser#function}.
	 * @param ctx the parse tree
	 */
	void enterFunction(HelloParser.FunctionContext ctx);
	/**
	 * Exit a parse tree produced by {@link HelloParser#function}.
	 * @param ctx the parse tree
	 */
	void exitFunction(HelloParser.FunctionContext ctx);
	/**
	 * Enter a parse tree produced by {@link HelloParser#functionparameterdeclarationlist}.
	 * @param ctx the parse tree
	 */
	void enterFunctionparameterdeclarationlist(HelloParser.FunctionparameterdeclarationlistContext ctx);
	/**
	 * Exit a parse tree produced by {@link HelloParser#functionparameterdeclarationlist}.
	 * @param ctx the parse tree
	 */
	void exitFunctionparameterdeclarationlist(HelloParser.FunctionparameterdeclarationlistContext ctx);
	/**
	 * Enter a parse tree produced by {@link HelloParser#functionstatementlist}.
	 * @param ctx the parse tree
	 */
	void enterFunctionstatementlist(HelloParser.FunctionstatementlistContext ctx);
	/**
	 * Exit a parse tree produced by {@link HelloParser#functionstatementlist}.
	 * @param ctx the parse tree
	 */
	void exitFunctionstatementlist(HelloParser.FunctionstatementlistContext ctx);
	/**
	 * Enter a parse tree produced by {@link HelloParser#functionCall}.
	 * @param ctx the parse tree
	 */
	void enterFunctionCall(HelloParser.FunctionCallContext ctx);
	/**
	 * Exit a parse tree produced by {@link HelloParser#functionCall}.
	 * @param ctx the parse tree
	 */
	void exitFunctionCall(HelloParser.FunctionCallContext ctx);
	/**
	 * Enter a parse tree produced by {@link HelloParser#functionparameterpasslist}.
	 * @param ctx the parse tree
	 */
	void enterFunctionparameterpasslist(HelloParser.FunctionparameterpasslistContext ctx);
	/**
	 * Exit a parse tree produced by {@link HelloParser#functionparameterpasslist}.
	 * @param ctx the parse tree
	 */
	void exitFunctionparameterpasslist(HelloParser.FunctionparameterpasslistContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ANDOR}
	 * labeled alternative in {@link HelloParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterANDOR(HelloParser.ANDORContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ANDOR}
	 * labeled alternative in {@link HelloParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitANDOR(HelloParser.ANDORContext ctx);
	/**
	 * Enter a parse tree produced by the {@code expressionSelf}
	 * labeled alternative in {@link HelloParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterExpressionSelf(HelloParser.ExpressionSelfContext ctx);
	/**
	 * Exit a parse tree produced by the {@code expressionSelf}
	 * labeled alternative in {@link HelloParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitExpressionSelf(HelloParser.ExpressionSelfContext ctx);
	/**
	 * Enter a parse tree produced by the {@code expressionReturn}
	 * labeled alternative in {@link HelloParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterExpressionReturn(HelloParser.ExpressionReturnContext ctx);
	/**
	 * Exit a parse tree produced by the {@code expressionReturn}
	 * labeled alternative in {@link HelloParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitExpressionReturn(HelloParser.ExpressionReturnContext ctx);
	/**
	 * Enter a parse tree produced by the {@code EQUALS}
	 * labeled alternative in {@link HelloParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterEQUALS(HelloParser.EQUALSContext ctx);
	/**
	 * Exit a parse tree produced by the {@code EQUALS}
	 * labeled alternative in {@link HelloParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitEQUALS(HelloParser.EQUALSContext ctx);
	/**
	 * Enter a parse tree produced by the {@code LESSEQ}
	 * labeled alternative in {@link HelloParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterLESSEQ(HelloParser.LESSEQContext ctx);
	/**
	 * Exit a parse tree produced by the {@code LESSEQ}
	 * labeled alternative in {@link HelloParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitLESSEQ(HelloParser.LESSEQContext ctx);
	/**
	 * Enter a parse tree produced by the {@code GREATER}
	 * labeled alternative in {@link HelloParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterGREATER(HelloParser.GREATERContext ctx);
	/**
	 * Exit a parse tree produced by the {@code GREATER}
	 * labeled alternative in {@link HelloParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitGREATER(HelloParser.GREATERContext ctx);
	/**
	 * Enter a parse tree produced by the {@code NOTEQUALS}
	 * labeled alternative in {@link HelloParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterNOTEQUALS(HelloParser.NOTEQUALSContext ctx);
	/**
	 * Exit a parse tree produced by the {@code NOTEQUALS}
	 * labeled alternative in {@link HelloParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitNOTEQUALS(HelloParser.NOTEQUALSContext ctx);
	/**
	 * Enter a parse tree produced by the {@code NOT}
	 * labeled alternative in {@link HelloParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterNOT(HelloParser.NOTContext ctx);
	/**
	 * Exit a parse tree produced by the {@code NOT}
	 * labeled alternative in {@link HelloParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitNOT(HelloParser.NOTContext ctx);
	/**
	 * Enter a parse tree produced by the {@code expressionBoolean}
	 * labeled alternative in {@link HelloParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterExpressionBoolean(HelloParser.ExpressionBooleanContext ctx);
	/**
	 * Exit a parse tree produced by the {@code expressionBoolean}
	 * labeled alternative in {@link HelloParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitExpressionBoolean(HelloParser.ExpressionBooleanContext ctx);
	/**
	 * Enter a parse tree produced by the {@code expressionID}
	 * labeled alternative in {@link HelloParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterExpressionID(HelloParser.ExpressionIDContext ctx);
	/**
	 * Exit a parse tree produced by the {@code expressionID}
	 * labeled alternative in {@link HelloParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitExpressionID(HelloParser.ExpressionIDContext ctx);
	/**
	 * Enter a parse tree produced by the {@code GREATEREQ}
	 * labeled alternative in {@link HelloParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterGREATEREQ(HelloParser.GREATEREQContext ctx);
	/**
	 * Exit a parse tree produced by the {@code GREATEREQ}
	 * labeled alternative in {@link HelloParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitGREATEREQ(HelloParser.GREATEREQContext ctx);
	/**
	 * Enter a parse tree produced by the {@code expressionNumber}
	 * labeled alternative in {@link HelloParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterExpressionNumber(HelloParser.ExpressionNumberContext ctx);
	/**
	 * Exit a parse tree produced by the {@code expressionNumber}
	 * labeled alternative in {@link HelloParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitExpressionNumber(HelloParser.ExpressionNumberContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ADDSUB}
	 * labeled alternative in {@link HelloParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterADDSUB(HelloParser.ADDSUBContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ADDSUB}
	 * labeled alternative in {@link HelloParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitADDSUB(HelloParser.ADDSUBContext ctx);
	/**
	 * Enter a parse tree produced by the {@code expressionNested}
	 * labeled alternative in {@link HelloParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterExpressionNested(HelloParser.ExpressionNestedContext ctx);
	/**
	 * Exit a parse tree produced by the {@code expressionNested}
	 * labeled alternative in {@link HelloParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitExpressionNested(HelloParser.ExpressionNestedContext ctx);
	/**
	 * Enter a parse tree produced by the {@code expressionString}
	 * labeled alternative in {@link HelloParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterExpressionString(HelloParser.ExpressionStringContext ctx);
	/**
	 * Exit a parse tree produced by the {@code expressionString}
	 * labeled alternative in {@link HelloParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitExpressionString(HelloParser.ExpressionStringContext ctx);
	/**
	 * Enter a parse tree produced by the {@code LESS}
	 * labeled alternative in {@link HelloParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterLESS(HelloParser.LESSContext ctx);
	/**
	 * Exit a parse tree produced by the {@code LESS}
	 * labeled alternative in {@link HelloParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitLESS(HelloParser.LESSContext ctx);
	/**
	 * Enter a parse tree produced by the {@code MULDIV}
	 * labeled alternative in {@link HelloParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterMULDIV(HelloParser.MULDIVContext ctx);
	/**
	 * Exit a parse tree produced by the {@code MULDIV}
	 * labeled alternative in {@link HelloParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitMULDIV(HelloParser.MULDIVContext ctx);
	/**
	 * Enter a parse tree produced by {@link HelloParser#decdef}.
	 * @param ctx the parse tree
	 */
	void enterDecdef(HelloParser.DecdefContext ctx);
	/**
	 * Exit a parse tree produced by {@link HelloParser#decdef}.
	 * @param ctx the parse tree
	 */
	void exitDecdef(HelloParser.DecdefContext ctx);
	/**
	 * Enter a parse tree produced by {@link HelloParser#class}.
	 * @param ctx the parse tree
	 */
	void enterClass(HelloParser.ClassContext ctx);
	/**
	 * Exit a parse tree produced by {@link HelloParser#class}.
	 * @param ctx the parse tree
	 */
	void exitClass(HelloParser.ClassContext ctx);
	/**
	 * Enter a parse tree produced by {@link HelloParser#while}.
	 * @param ctx the parse tree
	 */
	void enterWhile(HelloParser.WhileContext ctx);
	/**
	 * Exit a parse tree produced by {@link HelloParser#while}.
	 * @param ctx the parse tree
	 */
	void exitWhile(HelloParser.WhileContext ctx);
	/**
	 * Enter a parse tree produced by {@link HelloParser#ifThenElseStatement}.
	 * @param ctx the parse tree
	 */
	void enterIfThenElseStatement(HelloParser.IfThenElseStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link HelloParser#ifThenElseStatement}.
	 * @param ctx the parse tree
	 */
	void exitIfThenElseStatement(HelloParser.IfThenElseStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link HelloParser#if}.
	 * @param ctx the parse tree
	 */
	void enterIf(HelloParser.IfContext ctx);
	/**
	 * Exit a parse tree produced by {@link HelloParser#if}.
	 * @param ctx the parse tree
	 */
	void exitIf(HelloParser.IfContext ctx);
	/**
	 * Enter a parse tree produced by {@link HelloParser#elif}.
	 * @param ctx the parse tree
	 */
	void enterElif(HelloParser.ElifContext ctx);
	/**
	 * Exit a parse tree produced by {@link HelloParser#elif}.
	 * @param ctx the parse tree
	 */
	void exitElif(HelloParser.ElifContext ctx);
	/**
	 * Enter a parse tree produced by {@link HelloParser#else}.
	 * @param ctx the parse tree
	 */
	void enterElse(HelloParser.ElseContext ctx);
	/**
	 * Exit a parse tree produced by {@link HelloParser#else}.
	 * @param ctx the parse tree
	 */
	void exitElse(HelloParser.ElseContext ctx);
	/**
	 * Enter a parse tree produced by {@link HelloParser#block}.
	 * @param ctx the parse tree
	 */
	void enterBlock(HelloParser.BlockContext ctx);
	/**
	 * Exit a parse tree produced by {@link HelloParser#block}.
	 * @param ctx the parse tree
	 */
	void exitBlock(HelloParser.BlockContext ctx);
}