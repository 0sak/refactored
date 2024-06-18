package frontendantlr;

import CBuilder.Expression;
import CBuilder.ProgramBuilder;
import CBuilder.Reference;
import CBuilder.Statement;
import CBuilder.conditions.IfThenElseStatement;
import CBuilder.conditions.conditionalStatement.ElifStatement;
import CBuilder.conditions.conditionalStatement.ElseStatement;
import CBuilder.conditions.conditionalStatement.IfStatement;
import CBuilder.conditions.conditionalStatement.WhileStatement;
import CBuilder.keywords.bool.AndKeyword;
import CBuilder.keywords.bool.NotKeyword;
import CBuilder.keywords.bool.OrKeyword;
import CBuilder.literals.BoolLiteral;
import CBuilder.literals.IntLiteral;
import CBuilder.literals.StringLiteral;
import CBuilder.objects.*;
import CBuilder.objects.functions.Argument;
import CBuilder.objects.functions.Function;
import CBuilder.objects.functions.ReturnStatement;
import CBuilder.variables.Assignment;
import CBuilder.variables.VariableDeclaration;
import frontendantlr.Helper.Scope;
import frontendantlr.Helper.Symbol;
import frontendantlr.Helper.Variable;

import java.nio.file.Path;
import java.util.*;

import static frontendantlr.Helper.Helpers.is_true_or_false;

public class GrammarListener extends HelloBaseListener {
    private ProgramBuilder builder;
    Scope scope;
    int counter = 0;

    public void enterStart(HelloParser.StartContext ctx) {
        System.out.println("enterStart");
        scope = new Scope();
        builder = new ProgramBuilder();
    }
    public void exitStart(HelloParser.StartContext ctx) {
        System.out.println("exitStart\n");
        //scope.printSymbols();
        //System.out.println(buildProgram());
    }
    public void enterStatement(HelloParser.StatementContext ctx) {
        System.out.println("enterStatement");

    }
    public void exitStatement(HelloParser.StatementContext ctx) {
        System.out.println("exitStatement");
    }
    public void enterDecdef(HelloParser.DecdefContext ctx) {
        System.out.println("enterDecDef");
    }
    public void exitDecdef(HelloParser.DecdefContext ctx) {
        System.out.println("exitDecdef");

        if (!scope.symbolStack.isEmpty()) {

            Variable rhs = (Variable) scope.symbolStack.pop();
            //Calls can be treated together. Maybe another abstraction level possible with Expression ?

            if(ctx.ID().getText().contains("self.")){

                String attributeReferenceName = ctx.ID().getText().replace("self.","");

                if(ctx.depth() == 3)
                    builder.addStatement(new AttributeAssignment(new AttributeReference(attributeReferenceName, new Reference("self")), ((Variable<Expression>) rhs).getValue()));
                else{
                    scope.symbolStack.push(new Variable<Statement>("statementAttributeAssignment" + counter , scope, new AttributeAssignment(new AttributeReference(attributeReferenceName, new Reference("self")), ((Variable<Expression>) rhs).getValue()),"ATTRIBUTEASSIGNMENT"));
                    counter++;
                }
                return;
            }

            if (rhs.getDescription().equals("CASTSTR") || rhs.getDescription().equals("CASTINT") ||
                rhs.getDescription().equals("BOOLCALL") || rhs.getDescription().equals("INTCALL")
                || rhs.getDescription().equals("STRCALL") || rhs.getDescription().equals("CALLID")
                || rhs.getDescription().equals("CALLINPUT") || rhs.getDescription().equals("NOTKEYWORD")
                || rhs.getDescription().equals("ANDKEYWORD") || rhs.getDescription().equals("ORKEYWORD")
                || rhs.getDescription().equals("INTLITERAL") || rhs.getDescription().equals("STRINGLITERAL")
                || rhs.getDescription().equals("BOOLLITERAL") || rhs.getDescription().equals("BOOLLINT")
                || rhs.getDescription().equals("REFERENCE") || rhs.getDescription().equals("FUNCCALL")
            ) {
                if (ctx.depth() == 3) {
                    if (!scope.referencesInScope.containsKey(ctx.ID().getText())) {
                        builder.addVariable(new VariableDeclaration(ctx.ID().getText()));
                    }

                    builder.addStatement(new Assignment(new Reference(ctx.ID().getText()), ((((Variable<Expression>) rhs).getValue()))));
                    scope.referencesInScope.put(ctx.ID().getText(), rhs.getDescription());
                } else {
                    if (!scope.referencesInScope.containsKey(ctx.ID().getText()) && scope.getEnclosing_scope()==null) {
                        builder.addVariable(new VariableDeclaration(ctx.ID().getText()));
                        scope.referencesInScope.put(ctx.ID().getText(), rhs.getDescription());
                    }

                    scope.symbolStack.push(new Variable<Statement>(ctx.ID().getText(), scope, new Assignment(new Reference(ctx.ID().getText()), ((((Variable<Expression>) rhs).getValue()))), "ASSIGNMENT"));
                    counter++;
                }
            }
        }
    }
    public void enterBlock(HelloParser.BlockContext ctx) {
        System.out.println("enterBlock");
        scope = new Scope(scope);
    }
    public void exitBlock(HelloParser.BlockContext ctx) {
        System.out.println("exitBlock");
        scope = scope.getEnclosing_scope();
    }
    /*
    public void enterComment(HelloParser.CommentContext ctx){
        System.out.println("enterComment");
    }
    */

    /*
    public void exitComment(HelloParser.CommentContext ctx){
        System.out.println("exitComment");
    }*/
    public void enterADDSUB(HelloParser.ADDSUBContext ctx) {
        System.out.println("enterADDSUB");
    }
    public void exitADDSUB(HelloParser.ADDSUBContext ctx) {

        System.out.println("exitADDSUB");

        if (!scope.symbolStack.isEmpty()) {
            Variable rhs = (Variable) scope.symbolStack.pop();
            Variable lhs = (Variable) scope.symbolStack.pop();

            System.out.println(lhs.getDescription() + " --- " + rhs.getDescription());

            Call call;

            if (ctx.op.getType() == HelloLexer.ADD) {

                call = new Call(new AttributeReference("__add__", ((Variable<Expression>) lhs).getValue()), List.of(new Expression[]{((Variable<Expression>) rhs).getValue()}));

                if (ctx.depth() == 3) {
                    builder.addStatement(new Call(new Reference("print"), List.of(new Expression[]{
                        call
                    })));

                    return;
                }

                scope.symbolStack.push(new Variable<Expression>("Call" + counter, scope, call, "INTCALL"));
                counter++;
            } else {
                call = new Call(new AttributeReference("__sub__", ((Variable<Expression>) lhs).getValue()), List.of(new Expression[]{((Variable<Expression>) rhs).getValue()}));

                if (ctx.depth() == 3) {
                    builder.addStatement(new Call(new Reference("print"), List.of(new Expression[]{
                        call
                    })));

                    return;
                }

                scope.symbolStack.push(new Variable<Expression>("Call" + counter, scope, call, "INTCALL"));
                counter++;
            }
        }
    }
    public void enterMULDIV(HelloParser.MULDIVContext ctx) {
        System.out.println("enterMULDIV");
    }
    public void exitMULDIV(HelloParser.MULDIVContext ctx) {
        System.out.println("exitMULDIV");

        if (!scope.symbolStack.isEmpty()) {
            Variable rhs = (Variable) scope.symbolStack.pop();
            Variable lhs = (Variable) scope.symbolStack.pop();

            Call call;

            System.out.println(lhs.getDescription() + " --- " + rhs.getDescription());

            if (ctx.op.getType() == HelloLexer.MUL) {

                call = new Call(new AttributeReference("__mul__", ((Variable<Expression>) lhs).getValue()), List.of(new Expression[]{((Variable<Expression>) rhs).getValue()}));

                if (ctx.depth() == 3) {
                    builder.addStatement(new Call(new Reference("print"), List.of(new Expression[]{
                        call
                    })));
                    return;
                }
                scope.symbolStack.push((new Variable<Call>("Call" + counter, scope, call, "INTCALL")));
                counter++;
            } else {

                call = new Call(new AttributeReference("__div__", ((Variable<Expression>) lhs).getValue()), List.of(new Expression[]{((Variable<Expression>) rhs).getValue()}));

                if (ctx.depth() == 3) {
                    builder.addStatement(new Call(new Reference("print"), List.of(new Expression[]{
                        call
                    })));

                    return;
                }
                scope.symbolStack.push((new Variable<Expression>("Call" + counter, scope, call, "INTCALL")));
                counter++;
            }
        }
    }
    public void enterANDOR(HelloParser.ANDORContext ctx) {
        System.out.println("enterANDOR");
    }
    public void exitANDOR(HelloParser.ANDORContext ctx) {
        System.out.println("exitANDOR");

        if (!scope.symbolStack.isEmpty()) {
            Variable rhs = (Variable) scope.symbolStack.pop();
            Variable lhs = (Variable) scope.symbolStack.pop();

            System.out.println(lhs.getDescription() + " --- " + rhs.getDescription());

            if (ctx.op.getType() == HelloLexer.AND) {

                if (ctx.depth() == 3) {
                    builder.addStatement(new Call(new Reference("print"), List.of(new Expression[]{
                        new AndKeyword(((Variable<Expression>) lhs).getValue(), ((Variable<Expression>) rhs).getValue())
                    })));

                    return;
                }
            } else {

                if (ctx.depth() == 3) {
                    builder.addStatement(new Call(new Reference("print"), List.of(new Expression[]{
                        new OrKeyword(((Variable<Expression>) lhs).getValue(), ((Variable<Expression>) rhs).getValue())
                    })));

                    return;
                }
            }

            scope.symbolStack.push(ctx.op.getType() == HelloLexer.AND ? (new Variable<Expression>("AndKeyword" + counter, scope, new AndKeyword(((Variable<Expression>) lhs).getValue(), ((Variable<Expression>) rhs).getValue()), "ANDKEYWORD")) : (new Variable<Expression>("OrKeyword" + counter, scope, new OrKeyword(((Variable<Expression>) lhs).getValue(), ((Variable<Expression>) rhs).getValue()), "ORKEYWORD")));
            counter++;
        }

        /*Integer rhs = stack.pop();
        Integer lhs = stack.pop();

        if(ctx.op.getType() == HelloLexer.AND) {
            System.out.println(lhs & rhs);
            builder.addStatement(new AndKeyword(new IntLiteral(lhs), new IntLiteral(rhs)));
        }
        else {
            System.out.println(lhs | rhs);
            builder.addStatement(new OrKeyword(new IntLiteral(lhs), new IntLiteral(rhs)));
        }

        this.stack.push(ctx.op.getType() == HelloLexer.AND ? (lhs & rhs) : (lhs | rhs));
        */
    }
    public void enterNOT(HelloParser.NOTContext ctx) {
        System.out.println("enterNOT");
    }
    public void exitNOT(HelloParser.NOTContext ctx) {
        System.out.println("exitNOT");

        if (!scope.symbolStack.isEmpty()) {

            Variable notValue = (Variable) scope.symbolStack.pop();

            if (ctx.parent.depth() == 2) {
                builder.addStatement(new Call(new Reference("print"), List.of(new Expression[]{
                    new NotKeyword(((Variable<Expression>) notValue).getValue())
                })));

                return;
            }

            scope.symbolStack.push(new Variable<Expression>("NotKeyword" + counter, scope, new NotKeyword((((Variable<Expression>) notValue).getValue())), "NOTKEYWORD"));
            counter++;
/*
            if (Objects.equals(notValue.getOpType(), "ANDKEYWORD")) {

                NotKeyword not = new NotKeyword((((Variable<AndKeyword>) notValue).getValue()));

                if (ctx.parent.depth() == 2) {
                    builder.addStatement(new Call(new Reference("print"), List.of(new Expression[]{
                        not
                    })));

                    return;
                }

                //builder.addStatement(new NotKeyword(new IntLiteral(((Variable<Integer>) notValue).getValue())));

                scope.symbolStack.push(new Variable<NotKeyword>("NotKeyword" + counter, scope, not, "NOTKEYWORD"));
                counter++;
            }
            */
        }
    }
    public void enterGREATER(HelloParser.GREATERContext ctx) {
        System.out.println("enterGREATER");


    }
    public void exitGREATER(HelloParser.GREATERContext ctx) {
        System.out.println("exitGREATER");

        if (!scope.symbolStack.isEmpty()) {
            Variable rhs = (Variable) scope.symbolStack.pop();
            Variable lhs = (Variable) scope.symbolStack.pop();

            Call call;

            System.out.println(lhs.getDescription() + " --- " + rhs.getDescription());

            if (ctx.op.getType() == HelloLexer.GT) {

                call = new Call(new AttributeReference("__gt__", ((Variable<Expression>) lhs).getValue()), List.of(new Expression[]{((Variable<Expression>) rhs).getValue()}));

                if (ctx.parent.depth() == 2) {
                    builder.addStatement(new Call(new Reference("print"), List.of(new Expression[]{
                        call
                    })));

                    return;
                }

                scope.symbolStack.push(new Variable<Expression>("Call" + counter, scope, call, "INTCALL"));
                counter++;
            }
        }
    }
    public void enterGREATEREQ(HelloParser.GREATEREQContext ctx) {
        System.out.println("enterGREATEREQ");
    }
    public void exitGREATEREQ(HelloParser.GREATEREQContext ctx) {
        System.out.println("exitGREATEREQ");

        if (!scope.symbolStack.isEmpty()) {
            Variable rhs = (Variable) scope.symbolStack.pop();
            Variable lhs = (Variable) scope.symbolStack.pop();
            Call call;

            System.out.println(lhs.getDescription() + " --- " + rhs.getDescription());

            if (ctx.op.getType() == HelloLexer.GE) {
                //builder.addStatement(new Call(new AttributeReference("__add__", ((Variable<IntLiteral>)lhs).getValue()), List.of(new Expression[]{((Variable<IntLiteral>)rhs).getValue()})));

                call = new Call(new AttributeReference("__ge__", ((Variable<Expression>) lhs).getValue()), List.of(new Expression[]{((Variable<Expression>) rhs).getValue()}));

                if (ctx.parent.depth() == 2) {
                    builder.addStatement(new Call(new Reference("print"), List.of(new Expression[]{
                        call
                    })));

                    return;
                }

                scope.symbolStack.push(new Variable<Expression>("Call" + counter, scope, call, "INTCALL"));
                counter++;
            }
        }
    }
    public void enterLESS(HelloParser.LESSContext ctx) {
        System.out.println("enterLESS");
    }
    public void exitLESS(HelloParser.LESSContext ctx) {
        System.out.println("exitLESS");

        if (!scope.symbolStack.isEmpty()) {
            Variable rhs = (Variable) scope.symbolStack.pop();
            Variable lhs = (Variable) scope.symbolStack.pop();
            Call call;

            System.out.println(lhs.getDescription() + " --- " + rhs.getDescription());

            if (ctx.op.getType() == HelloLexer.LT) {
                //builder.addStatement(new Call(new AttributeReference("__add__", ((Variable<IntLiteral>)lhs).getValue()), List.of(new Expression[]{((Variable<IntLiteral>)rhs).getValue()})));

                call = new CBuilder.objects.Call(new CBuilder.objects.AttributeReference("__lt__", ((Variable<Expression>) lhs).getValue()), List.of(new Expression[]{((Variable<Expression>) rhs).getValue()}));

                if (ctx.parent.depth() == 2) {
                    builder.addStatement(new Call(new Reference("print"), List.of(new Expression[]{
                        call
                    })));

                    return;
                }

                scope.symbolStack.push(new Variable<Expression>("Call" + counter, scope, call, "INTCALL"));
                counter++;
            }
        }
    }
    public void enterLESSEQ(HelloParser.LESSEQContext ctx) {
        System.out.println("enterLESSEQ");
    }
    public void exitLESSEQ(HelloParser.LESSEQContext ctx) {
        System.out.println("exitLESSEQ");

        if (!scope.symbolStack.isEmpty()) {
            Variable rhs = (Variable) scope.symbolStack.pop();
            Variable lhs = (Variable) scope.symbolStack.pop();
            Call call;

            System.out.println(lhs.getDescription() + " --- " + rhs.getDescription());

            if (ctx.op.getType() == HelloLexer.LE) {
                //builder.addStatement(new Call(new AttributeReference("__add__", ((Variable<IntLiteral>)lhs).getValue()), List.of(new Expression[]{((Variable<IntLiteral>)rhs).getValue()})));

                call = new Call(new AttributeReference("__le__", ((Variable<Expression>) lhs).getValue()), List.of(new Expression[]{((Variable<Expression>) rhs).getValue()}));

                if (ctx.parent.depth() == 2) {
                    builder.addStatement(new Call(new Reference("print"), List.of(new Expression[]{
                        call
                    })));

                    return;
                }

                scope.symbolStack.push(new Variable<Expression>("Call" + counter, scope, call, "INTCALL"));
                counter++;
            }
        }
    }
    public void enterEQUALS(HelloParser.EQUALSContext ctx) {
        System.out.println("enterEQUALS");
    }
    public void exitEQUALS(HelloParser.EQUALSContext ctx) {
        System.out.println("exitEQUALS");

        if (!scope.symbolStack.isEmpty()) {
            Variable rhs = (Variable) scope.symbolStack.pop();
            Variable lhs = (Variable) scope.symbolStack.pop();

            Call call;

            System.out.println(lhs.getDescription() + " --- " + rhs.getDescription());

            if (ctx.op.getType() == HelloLexer.EQ) {
                //builder.addStatement(new Call(new AttributeReference("__add__", ((Variable<IntLiteral>)lhs).getValue()), List.of(new Expression[]{((Variable<IntLiteral>)rhs).getValue()})));

                call = new Call(new AttributeReference("__eq__", ((Variable<Expression>) lhs).getValue()), List.of(new Expression[]{((Variable<Expression>) rhs).getValue()}));

                if (ctx.parent.depth() == 2) {
                    builder.addStatement(new Call(new Reference("print"), List.of(new Expression[]{
                        call
                    })));

                    return;
                }

                scope.symbolStack.push(new Variable<Expression>("Call" + counter, scope, call, "INTCALL"));
                counter++;
            }
        }
    }
    public void enterNOTEQUALS(HelloParser.NOTEQUALSContext ctx) {
        System.out.println("enterNOTEQUALS");

    }
    public void exitNOTEQUALS(HelloParser.NOTEQUALSContext ctx) {
        System.out.println("exitNOTEQUALS");

        if (!scope.symbolStack.isEmpty()) {
            Variable rhs = (Variable) scope.symbolStack.pop();
            Variable lhs = (Variable) scope.symbolStack.pop();

            Call call;

            System.out.println(lhs.getDescription() + " --- " + rhs.getDescription());

            if (ctx.op.getType() == HelloLexer.NE) {
                //builder.addStatement(new Call(new AttributeReference("__add__", ((Variable<IntLiteral>)lhs).getValue()), List.of(new Expression[]{((Variable<IntLiteral>)rhs).getValue()})));

                call = new Call(new AttributeReference("__ne__", ((Variable<Expression>) lhs).getValue()), List.of(new Expression[]{((Variable<Expression>) rhs).getValue()}));

                if (ctx.parent.depth() == 2) {
                    builder.addStatement(new Call(new Reference("print"), List.of(new Expression[]{
                        call
                    })));

                    return;
                }

                scope.symbolStack.push(new Variable<Expression>("Call" + counter, scope, call, "INTCALL"));
                counter++;
            }
        }
    }
    public void enterWhile(HelloParser.WhileContext ctx) {
        System.out.println("enterWhile");

        scope.symbolStack.push(new Variable<String>("While" + counter, scope, "WHILEENTRY", "WHILEENTRY"));
        counter++;

    }
    public void exitWhile(HelloParser.WhileContext ctx) {
        System.out.println("exitWhile");

        System.out.println(scope.symbolStack);

        Iterator<Symbol> it = scope.symbolStack.iterator();

        while (it.hasNext()) {
            System.out.println(((Variable) it.next()).getDescription());
        }

        ArrayList<Variable> whileContextVars = new ArrayList<>();

        while (!scope.symbolStack.peek().getName().contains("While")) {
            whileContextVars.add((Variable) scope.symbolStack.pop());
        }

        scope.symbolStack.pop();

        Expression condition;

        if (Objects.equals(whileContextVars.getLast().getDescription(), "INTLITERAL"))
            condition = ((IntLiteral) whileContextVars.getLast().getValue());
        else
            condition = (Expression) whileContextVars.getLast().getValue();

        whileContextVars.remove(whileContextVars.getLast());

        ArrayList<Statement> statementList = new ArrayList<>();

        for (Variable i : whileContextVars) {
            statementList.addFirst((Statement) i.getValue());
        }

        if (ctx.parent.depth() > 2) {
            System.out.println("Nested While");

            scope.symbolStack.push(new Variable<WhileStatement>("WHILE" + counter, scope, new WhileStatement(condition, statementList), "WHILESTATEMENT"));
            counter++;

            return;
        }

        builder.addStatement(new WhileStatement(condition, statementList));

        //scope.symbolStack.push(new Variable<WhileStatement>("While" + counter, scope, new WhileStatement(condition, ), "INTCALL"));
    }
    public void enterIfThenElseStatement(HelloParser.IfThenElseStatementContext ctx) {
        System.out.println("enterIfThenElseStatement");

        scope.symbolStack.push(new Variable<String>("ifThenElseEnter" + counter, scope, "IFTHENELSE", "IFTHENELSE"));
        counter++;
    }
    public void exitIfThenElseStatement(HelloParser.IfThenElseStatementContext ctx) {
        System.out.println("exitIfThenElseStatement");

        ArrayList<Variable> ifThenElseContextVars = new ArrayList();

        if (!scope.symbolStack.isEmpty()) {

            while (!scope.symbolStack.peek().getName().contains("ifThenElseEnter")) {
                ifThenElseContextVars.add((Variable) scope.symbolStack.pop());
            }

            scope.symbolStack.pop();

            Collections.reverse(ifThenElseContextVars);

            if (ifThenElseContextVars.size() == 1) {

                if (ctx.depth() > 3) {
                    scope.symbolStack.push(new Variable<Statement>("IfThenElseStatement" + counter, scope, new IfThenElseStatement((IfStatement) ifThenElseContextVars.get(0).getValue(), Optional.empty(), Optional.empty()), "IFTHENELSESTATEMENT"));

                    counter++;
                    return; //IFTHENELSE can be nested if the statement is passed into the condition of the current if elif else block :)
                }

                builder.addStatement(new IfThenElseStatement((IfStatement) ifThenElseContextVars.get(0).getValue(), Optional.empty(), Optional.empty()));
            }

            if (ifThenElseContextVars.size() == 2) {
                if (Objects.equals(ifThenElseContextVars.get(1).getDescription(), "ELIFSTATEMENT")) {

                    if (ctx.depth() > 3) {
                        scope.symbolStack.push(new Variable<Statement>("IfThenElseStatement" + counter, scope, new IfThenElseStatement((IfStatement) ifThenElseContextVars.get(0).getValue(), Optional.of(List.of((ElifStatement) ifThenElseContextVars.get(1).getValue())), Optional.empty()), "IFTHENELSESTATEMENT"));
                        counter++;

                        return;
                    }

                    builder.addStatement(new IfThenElseStatement((IfStatement) ifThenElseContextVars.get(0).getValue(), Optional.of(List.of((ElifStatement) ifThenElseContextVars.get(1).getValue())), Optional.empty()));
                } else {

                    if (ctx.depth() > 3) {
                        scope.symbolStack.push(new Variable<Statement>("IfThenElseStatement" + counter, scope, new IfThenElseStatement((IfStatement) ifThenElseContextVars.get(0).getValue(), Optional.empty(), Optional.of((ElseStatement) ifThenElseContextVars.get(1).getValue())), "IFTHENELSESTATEMENT"));
                        counter++;

                        return;
                    }

                    builder.addStatement(new IfThenElseStatement((IfStatement) ifThenElseContextVars.get(0).getValue(), Optional.empty(), Optional.of((ElseStatement) ifThenElseContextVars.get(1).getValue())));
                }
            }

            if (ifThenElseContextVars.size() >= 3) {

                Iterator<Variable> it = ifThenElseContextVars.iterator();

                while (it.hasNext()) {
                    System.out.println(it.next().getDescription());
                }

                IfStatement startingIf = (IfStatement) ifThenElseContextVars.get(0).getValue();

                ElseStatement endingElse = (ElseStatement) ifThenElseContextVars.getLast().getValue();

                ifThenElseContextVars.remove(ifThenElseContextVars.getFirst());
                ifThenElseContextVars.remove(ifThenElseContextVars.getLast());

                ArrayList<ElifStatement> elifList = new ArrayList<>();

                for (Variable i : ifThenElseContextVars) {
                    elifList.add((ElifStatement) i.getValue());
                }

                if (ctx.depth() > 3) {
                    scope.symbolStack.push(new Variable<Statement>("IfThenElseStatement" + counter, scope, new IfThenElseStatement(startingIf, Optional.of(elifList), Optional.of(endingElse)), "IFTHENELSESTATEMENT"));
                    counter++;

                    return;
                }
                builder.addStatement(new IfThenElseStatement(startingIf, Optional.of(elifList), Optional.of(endingElse)));
            }
        }
    }
    public void enterIf(HelloParser.IfContext ctx) {
        System.out.println("enterIf");

        scope.symbolStack.push(new Variable<String>("ifEnter" + counter, scope, "IFSTATEMENT", "IFSTATEMENT"));
        counter++;

    }
    public void exitIf(HelloParser.IfContext ctx) {
        System.out.println("exitIf");

        ArrayList<Variable> ifContextVars = new ArrayList<>();

        while (!scope.symbolStack.peek().getName().contains("ifEnter")) {
            ifContextVars.add((Variable) scope.symbolStack.pop());
        }

        scope.symbolStack.pop();

        Expression condition = (Expression) ifContextVars.getLast().getValue();

        ifContextVars.remove(ifContextVars.getLast());

        ArrayList<Statement> statementList = new ArrayList<>();

        for (Variable i : ifContextVars) {
            statementList.addFirst((Statement) i.getValue());
        }

        if (ctx.parent.depth() > 3) {
            System.out.println("Nested if");

            scope.symbolStack.push(new Variable<Statement>("IF" + counter, scope, new IfStatement(condition, statementList), "IFSTATEMENT"));
            counter++;

            return;
        }

        scope.symbolStack.push(new Variable<Statement>("If" + counter, scope, new IfStatement(condition, statementList), "IFSTATEMENT"));

    }
    public void enterElif(HelloParser.ElifContext ctx) {
        System.out.println("enterELIF");

        scope.symbolStack.push(new Variable<String>("elifEnter" + counter, scope, "ELIFSTATEMENT", "ELIFSTATEMENT"));
        counter++;

    }
    public void exitElif(HelloParser.ElifContext ctx) {
        System.out.println("exitELIF");

        ArrayList<Variable> elifContextVars = new ArrayList<>();

        while (!scope.symbolStack.peek().getName().contains("elifEnter")) {
            elifContextVars.add((Variable) scope.symbolStack.pop());
        }

        scope.symbolStack.pop();

        Expression condition = (Expression) elifContextVars.getLast().getValue();

        elifContextVars.remove(elifContextVars.getLast());

        ArrayList<Statement> statementList = new ArrayList<>();

        for (Variable i : elifContextVars) {
            statementList.addFirst((Statement) i.getValue());
        }

        System.out.println(ctx.depth());

        if (ctx.depth() > 4) {
            System.out.println("Nested if");

            scope.symbolStack.push(new Variable<Statement>("ELIF" + counter, scope, new ElifStatement(condition, statementList), "ELIFSTATEMENT"));
            counter++;

            return;
        }

        scope.symbolStack.push(new Variable<Statement>("Elif" + counter, scope, new ElifStatement(condition, statementList), "ELIFSTATEMENT"));
        counter++;
    }
    public void enterElse(HelloParser.ElseContext ctx) {
        System.out.println("enterELSE");

        scope.symbolStack.push(new Variable<String>("elseEnter" + counter, scope, "ELSESTATEMENT", "ELSESTATEMENT"));
        counter++;
    }
    public void exitElse(HelloParser.ElseContext ctx) {
        System.out.println("exitELSE");

        ArrayList<Variable> elifContextVars = new ArrayList<>();

        while (!scope.symbolStack.peek().getName().contains("elseEnter")) {
            elifContextVars.add((Variable) scope.symbolStack.pop());
        }

        scope.symbolStack.pop();

        ArrayList<Statement> statementList = new ArrayList<>();

        for (Variable i : elifContextVars) {
            statementList.addFirst((Statement) i.getValue());
        }

        if (ctx.parent.depth() > 3) {
            System.out.println("Nested if");

            scope.symbolStack.push(new Variable<Statement>("ELSE" + counter, scope, new ElseStatement(statementList), "ELSESTATEMENT"));
            counter++;

            return;
        }

        scope.symbolStack.push(new Variable<Statement>("Else" + counter, scope, new ElseStatement(statementList), "ELSESTATEMENT"));

    }
    public void enterPrint(HelloParser.PrintContext ctx) {
        System.out.println("enterPrint");

        scope.symbolStack.push(new Variable<String>("printEnter" + counter, scope, "PRINTSTATEMENT", "PRINTSTATEMENT"));
        counter++;
    }
    public void exitPrint(HelloParser.PrintContext ctx) {
        System.out.println("exitPrint");

        if (!scope.symbolStack.isEmpty()) {
            ArrayList<Expression> printExpressions = new ArrayList<>();

            while (!scope.symbolStack.peek().getName().contains("printEnter")) {
                printExpressions.add((Expression) ((Variable) scope.symbolStack.pop()).getValue());
            }

            scope.symbolStack.pop();

            if (ctx.depth() == 3) {
                builder.addStatement(new Call(new Reference("print"), printExpressions));
            } else {
                scope.symbolStack.push(new Variable<Expression>("printCall", scope, new Call(new Reference("print"), printExpressions), "PRINTCALL"));
                counter++;
            }
        }

    }
    public void enterCastStr(HelloParser.CastStrContext ctx) {
        System.out.println("enterCastStr");

        scope.symbolStack.push(new Variable<String>("castStrEnter" + counter, scope, "CASTSTR", "CASTSTR"));
        counter++;
    }
    public void exitCastStr(HelloParser.CastStrContext ctx) {
        System.out.println("exitCastStr");

        if (!scope.symbolStack.isEmpty()) {
            //ArrayList<Expression> castStrExpression = new ArrayList<>();
            Expression castStrExpression = (Expression) ((Variable) scope.symbolStack.pop()).getValue();

            scope.symbolStack.pop();

            System.out.println(ctx.depth());
            if (ctx.depth() == 3) {
                builder.addStatement(new Call(new Reference("print"), List.of(new Call(new AttributeReference("__str__", castStrExpression), List.of(new Expression[]{})))));
            } else {
                scope.symbolStack.push(new Variable<Expression>("castStrCall", scope, new Call(new AttributeReference("__str__", castStrExpression), List.of(new Expression[]{})), "CASTSTR"));
                counter++;
            }
        }
    }
    public void enterCastInt(HelloParser.CastIntContext ctx) {
        System.out.println("enterCastInt");

        scope.symbolStack.push(new Variable<String>("castIntEnter" + counter, scope, "CASTINT", "CASTINT"));
        counter++;
    }
    public void exitCastInt(HelloParser.CastIntContext ctx) {
        System.out.println("exitCastInt");

        if (!scope.symbolStack.isEmpty()) {
            //ArrayList<Expression> castStrExpression = new ArrayList<>();
            Expression castStrExpression = ((Variable<Expression>) scope.symbolStack.pop()).getValue();

            scope.symbolStack.pop();

            System.out.println(ctx.depth());
            if (ctx.depth() == 3) {
                builder.addStatement(new Call(new Reference("print"), List.of(new Call(new AttributeReference("__int__", castStrExpression), List.of(new Expression[]{})))));
            } else {
                scope.symbolStack.push(new Variable<Expression>("castIntCall", scope, new Call(new AttributeReference("__int__", castStrExpression), List.of(new Expression[]{})), "CASTINT"));
                counter++;
            }
        }
    }
    public void enterCallId(HelloParser.CallIdContext ctx) {
        System.out.println("enterCallId");
        scope.symbolStack.push(new Variable<String>("callIdEnter" + counter, scope, "CALLID", "CALLID"));
        counter++;
    }
    public void exitCallId(HelloParser.CallIdContext ctx) {
        System.out.println("exitCallId");

        if (!scope.symbolStack.isEmpty()) {
            Expression callIdExpression = (Expression) ((Variable) scope.symbolStack.pop()).getValue();

            scope.symbolStack.pop();

            if (ctx.depth() == 3) {
                builder.addStatement(new Call(new Reference("print"), List.of(new Expression[]{
                    new Call(new Reference("id"), List.of(callIdExpression))
                })));
            } else {
                scope.symbolStack.push(new Variable<Expression>("callId", scope, new Call(new Reference("id"), List.of(callIdExpression)), "CALLID"));
                counter++;
            }
        }
    }
    public void enterCallInput(HelloParser.CallInputContext ctx) {
        System.out.println("enterCallInput");

        scope.symbolStack.push(new Variable<String>("callInputEnter" + counter, scope, "CALLINPUT", "CALLINPUT"));
        counter++;
    }
    public void exitCallInput(HelloParser.CallInputContext ctx) {
        System.out.println("exitCallInput");

        if (!scope.symbolStack.isEmpty()) {

            scope.symbolStack.pop();

            if (ctx.depth() == 3) {
                builder.addStatement(new Call(new Reference("print"), List.of(new Expression[]{
                    new Call(new Reference("input"), List.of())
                })));
            } else {
                scope.symbolStack.push(new Variable<Expression>("callInput", scope, new Call(new Reference("input"), List.of()), "CALLINPUT"));
                counter++;
            }
        }
    }
    public void enterCallSuper(HelloParser.CallSuperContext ctx){
        System.out.println("enterCallSuper");

        Variable<String> var = new Variable<>("enterSuperCall"+ counter, scope, "enterSuperCall", "ENTERSUPERCALL");

        scope.symbolStack.push(var);
        counter++;
    }
    public void exitCallSuper(HelloParser.CallSuperContext ctx){
        System.out.println("exitCallSuper");

        ArrayList<Expression> superParams = new ArrayList<>();

        while (!scope.symbolStack.peek().getName().contains("enterSuperCall")) {
            Variable<Expression> var = (Variable<Expression>)scope.symbolStack.pop();

            superParams.add(var.getValue());
        }

        scope.symbolStack.pop();

        Variable<SuperCall> var = new Variable<>("superCall"+ counter, scope, new SuperCall(superParams), "SUPERCALL");

        scope.symbolStack.push(var);
        counter++;
    }
    public void enterExpressionNumber(HelloParser.ExpressionNumberContext ctx) {
        System.out.println("enterExpressionNumber");

        Variable<Expression> var = new Variable<>("IntLiteral" + counter, scope, new IntLiteral(Integer.valueOf(ctx.getText())), "INTLITERAL");

        scope.symbolStack.push(var);

        counter++;
    }
    public void exitExpressionNumber(HelloParser.ExpressionNumberContext ctx) {
        System.out.println("exitExpressionNumber");
    }
    public void enterExpressionString(HelloParser.ExpressionStringContext ctx) {
        System.out.println("enterExpressionString");


        Variable<Expression> var = new Variable<>("StringLiteral" + counter, scope, new StringLiteral(ctx.getText().replace("'", "")), "STRINGLITERAL");

        scope.symbolStack.push(var);

        counter++;
    }
    public void exitExpressionString(HelloParser.ExpressionStringContext ctx) {
        System.out.println("exitExpressionString");
    }
    public void enterExpressionNested(HelloParser.ExpressionNestedContext ctx) {
        System.out.println("enterExpressionNested");
    }
    public void exitExpressionNested(HelloParser.ExpressionNestedContext ctx) {
        System.out.println("exitExpressionNested");
    }
    public void enterExpressionBoolean(HelloParser.ExpressionBooleanContext ctx) {
        System.out.println("enterExpressionBoolean");

        if (is_true_or_false(ctx.getText().toLowerCase())) {
            Variable<Expression> var = new Variable<>("BoolLiteral" + counter, scope, new BoolLiteral(Boolean.valueOf(ctx.getText().toLowerCase())), "BOOLLITERAL");

            scope.symbolStack.push(var);
            counter++;
        }
    }
    public void exitExpressionBoolean(HelloParser.ExpressionBooleanContext ctx) {
        System.out.println("exitExpressionBoolean");
    }
    public void enterExpressionID(HelloParser.ExpressionIDContext ctx) {
        System.out.println("enterExpressionID");

        if (ctx.ID().getText().contains("self.")) {
            String attributeReferenceName = ctx.ID().getText().replace("self.", "");
            Variable<AttributeReference> var = new Variable<>(ctx.ID().getText(), scope, new AttributeReference(attributeReferenceName, new Reference("self")), "ATTRIBUTEREFERENCE");

            scope.symbolStack.push(var);
        } else{
            Variable<Reference> var = new Variable<>(ctx.ID().getText(), scope, new Reference(ctx.getText()), "REFERENCE");

            scope.symbolStack.push(var);
        }
        counter++;
    }
    public void exitExpressionID(HelloParser.ExpressionIDContext ctx) {
        System.out.println("exitExpressionID");
    }
    public void enterExpressionSelf(HelloParser.ExpressionSelfContext ctx){
        System.out.println("enterExpressionSelf");

        Variable<Reference> var = new Variable<>(ctx.SELF().getText(), scope, new Reference(ctx.getText()), "REFERENCE");

        scope.symbolStack.push(var);
        counter++;
    }
    public void exitExpressionSelf(HelloParser.ExpressionSelfContext ctx){
        System.out.println("exitExpressionSelf");
    }
    public void enterExpressionReturn(HelloParser.ExpressionReturnContext ctx){
        System.out.println("enterExpressionReturn");
    }
    public void exitExpressionReturn(HelloParser.ExpressionReturnContext ctx){
        System.out.println("exitExpressionReturn");

        Variable<Expression> expr = (Variable<Expression>) scope.symbolStack.pop();

        Variable<ReturnStatement> var = new Variable<>("Return" + counter, scope, new ReturnStatement(expr.getValue()), "RETURNSTATEMENT");

        scope.symbolStack.push(var);
        counter++;

        Iterator<Symbol> it = scope.symbolStack.iterator();

        while(it.hasNext()){
            System.out.println(((Variable)it.next()).getDescription());
        }
    }
    public void enterFunction(HelloParser.FunctionContext ctx){
        System.out.println("enterFunction");

        this.scope = new Scope(scope);

        scope.symbolStack.push(new Variable<String>("defFunction" + counter, scope, "FUNCDEF", "FUNCDEF"));
        counter++;
    }
    public void exitFunction(HelloParser.FunctionContext ctx){
        System.out.println("exitFunction");

        String funcName = ctx.ID().getText();
        ArrayList<Statement> body = new ArrayList<>();
        ArrayList<Argument> positionalArgs = new ArrayList<>();
        ArrayList<Expression> posArgsSuperCall = new ArrayList<>();
        ArrayList<VariableDeclaration> localVariables = new ArrayList<>();
        int posArgsCounter = ctx.functionparameterdeclarationlist().getText().split(",").length - 1;

//        Iterator<Symbol> it = scope.symbolStack.iterator();
//
//        while(it.hasNext()){
//            System.out.println(((Variable)it.next()).getDescription());
//        }

        while (!scope.symbolStack.peek().getName().contains("exitFuncParamDecList")) {

            if(Objects.equals(((Variable) scope.symbolStack.peek()).getDescription(), "ASSIGNMENT")){
                Variable<Assignment> var = (Variable<Assignment>)scope.symbolStack.pop();

                if(!scope.referencesInScope.containsKey(var.getName()) && !this.scope.getEnclosing_scope().referencesInScope.containsKey(var.getName())){
                    scope.referencesInScope.put(var.getName(), var.getDescription());
                    localVariables.add(new VariableDeclaration(var.getName()));
                    body.add(var.getValue());
                    continue;
                }
                else{
                    body.add(var.getValue());
                    continue;
                }
            }

            if(Objects.equals(((Variable) scope.symbolStack.peek()).getDescription(), "SUPERCALL")){
                Variable<Expression> var = (Variable<Expression>)scope.symbolStack.pop();
                body.add(var.getValue());
                continue;
            }
//            else if(Objects.equals(((Variable) scope.symbolStack.peek()).getDescription(), "REFERENCE")){
//                Variable<Reference> var = (Variable<Reference>)scope.symbolStack.pop();
//
//                if(var.getName().contains("self")){
//                    classAttributesWithoutRHS.add(var.getValue());
//                    body.add(var.getValue());
//                }
//            }
            else{
                Variable<Statement> var = (Variable<Statement>)scope.symbolStack.pop();
                body.add(var.getValue());
            }
        }

        scope.symbolStack.pop();

        while (!scope.symbolStack.peek().getName().contains("defFunction")) {
            Variable<Reference> var = (Variable<Reference>)scope.symbolStack.pop();
            positionalArgs.add(new Argument(var.getValue().getName(), posArgsCounter));

            if(!var.getValue().getName().contains("self") && funcName.contains("__init__"))
                posArgsSuperCall.add(var.getValue());

            posArgsCounter--;
        }

        scope.symbolStack.pop();

//        if(ctx.retExpr != null)
//            body.set(0,new ReturnStatement((Expression)body.getFirst()));

        Collections.reverse(positionalArgs);
        Collections.reverse(body);
        Collections.reverse(localVariables);
        Collections.reverse(posArgsSuperCall);

//        boolean containsSuper = false;
//
//        for( Statement s : body){
//            if(s.getClass().getSimpleName().contains("SuperCall"))
//                containsSuper = true;
//        }

//        System.out.println("PositionalARgs" + positionalArgs);

//        if(funcName.contains("__init__") && !containsSuper)
//            body.addFirst(new SuperCall(posArgsSuperCall));

//        System.out.println("positionalArgs");
//        for(Argument arg : positionalArgs){
//            System.out.println(arg.buildArgExtraction());
//        }
//
//        System.out.println("body");
//        for(Statement stat : body){
//            System.out.println(stat.buildStatement());
//        }
//
//        System.out.println("localVariables");
//        for(VariableDeclaration local : localVariables){
//            System.out.println(local.buildInitialisation());
//        }

        scope.getEnclosing_scope().referencesInScope.put(funcName, "FUNC");

        if(ctx.depth() == 3) {
            builder.addFunction(new Function(funcName, body, positionalArgs, localVariables));

            this.scope = scope.getEnclosing_scope();
        }
        else{
            this.scope = scope.getEnclosing_scope();

            scope.symbolStack.push(new Variable<Expression>("functionDefinition" + counter, scope, new Function(funcName, body, positionalArgs, localVariables), "FUNCTIONDEFINITION"));

        }
    }
    public void enterFunctionparameterdeclarationlist(HelloParser.FunctionparameterdeclarationlistContext ctx){
        System.out.println("enterParameterDeclarationList");
    }
    public void exitFunctionparameterdeclarationlist(HelloParser.FunctionparameterdeclarationlistContext ctx){
        System.out.println("exitParameterDeclarationList");

        scope.symbolStack.push(new Variable<String>("exitFuncParamDecList" + counter, scope, "EXITFUNCPARAMDECLIST", "EXITFUNCPARAMDECLIST"));
        counter++;
    }
    public void enterFunctionstatementlist(HelloParser.FunctionstatementlistContext ctx) {
        System.out.println("enterFunctionStatementList");

    }
    public void exitFunctionstatementlist(HelloParser.FunctionstatementlistContext ctx) {
        System.out.println("exitFunctionStatementList");
    }
    public void enterFunctionCall(HelloParser.FunctionCallContext ctx){
        System.out.println("enterFunctionCall");

        scope.symbolStack.push(new Variable<String>("callFunc" + counter, scope, "FUNCCALL", "FUNCCALL"));
        counter++;
    }
    public void exitFunctionCall(HelloParser.FunctionCallContext ctx){
        System.out.println("exitFunctionCall");

        String funcName = ctx.funcName.getText();

        ArrayList<Expression> args = new ArrayList<>();

        while (!scope.symbolStack.peek().getName().contains("callFunc")) {
            args.add(((Variable<Expression>) scope.symbolStack.pop()).getValue());
        }

        scope.symbolStack.pop();

        Collections.reverse(args);

        if(ctx.depth() == 3) {
            if(ctx.classInstance != null)
                builder.addStatement(new Call(new AttributeReference(funcName, new Reference(ctx.classInstance.getText())), args));
            else
                builder.addStatement(new Call(new Reference(funcName), args));
        }
        else{
            if(ctx.classInstance != null){
                scope.symbolStack.push(new Variable<Expression>("Call"+counter, scope, new Call(new AttributeReference(funcName, new Reference(ctx.classInstance.getText())), args), "CLASSMEMBERCALL"));
            }
            else {
                scope.symbolStack.push(new Variable<Expression>("Call" + counter, scope, new Call(new Reference(funcName), args), "FUNCCALL"));
            }
            counter++;
        }

        System.out.println("FuncName : " + funcName);
        System.out.println("Args : " + args);
    }
    public void enterFunctionparameterpasslist(HelloParser.FunctionparameterpasslistContext ctx) {
        System.out.println("enterFunctionParameterPassList");
    }
    public void exitFunctionparameterpasslist(HelloParser.FunctionparameterpasslistContext ctx) {
        System.out.println("exitFunctionParameterPassList");
    }
    public void enterClass(HelloParser.ClassContext ctx){
        System.out.println("enterClass");

        scope = new Scope(scope);
        if(ctx.parentClass != null)
            scope.symbolStack.push(new Variable<String>("enterClass" + counter, scope, "CLASSINHERITANCE", "CLASSINHERITANCE"));
        else
            scope.symbolStack.push(new Variable<String>("enterClass" + counter, scope, "CLASS", "CLASS"));
        counter++;
    }
    public void exitClass(HelloParser.ClassContext ctx){
        System.out.println("exitClass");

        CBuilder.objects.MPyClass mpyClass;
        String className = ctx.className.getText();
        Reference parent;
        ArrayList<Function> funcList = new ArrayList<>();
        HashMap<Reference, Expression> classAttributes = new HashMap<>();

//        Iterator<Symbol> it = scope.symbolStack.iterator();
//
//        while(it.hasNext()){
//            System.out.println(((Variable)it.next()).getDescription());
//        }

        if(ctx.parentClass == null)
            parent = new Reference("__MPyType_Object");
        else
            parent = new Reference(ctx.parentClass.getText());

        while (!scope.symbolStack.peek().getName().contains("enterClass")) {
            if(Objects.equals(((Variable) scope.symbolStack.peek()).getDescription(), "FUNCTIONDEFINITION")) {
                Variable<Function> var = (Variable<Function>) scope.symbolStack.pop();

                funcList.add(var.getValue());
            }

//            if(Objects.equals(((Variable) scope.symbolStack.peek()).getDescription(), "REFERENCE")) {
//                Variable<Reference> var = (Variable<Reference>) scope.symbolStack.pop();
//
//                classAttributes.put(var.getValue(), new IntLiteral(0));
//            }
        }

        scope.symbolStack.pop();

//        System.out.println("FuncList : " + funcList);
//        System.out.println("ClassAttributes : " + classAttributes);

        Collections.reverse(funcList);

//        System.out.println(ctx.depth());
        if(ctx.depth() == 3){
            mpyClass = new MPyClass(className, parent, funcList, classAttributes);
            builder.addClass(mpyClass);
        }

    }
    public String buildProgram() {
        return builder.buildProgram();
    }
    public void writeProgram(Path destDir){ builder.writeProgram(destDir);}
}