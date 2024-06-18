package frontendantlr;

import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

public class Main {

    public static void main(String[] args) {
        String in = "a=123#end";

        in = in.replace("\n", "");
        in = in.replace("\t","");

        System.out.println(in);

        HelloLexer lexer = new HelloLexer(CharStreams.fromString(in));

        CommonTokenStream tokens = new CommonTokenStream(lexer);
        HelloParser parser = new HelloParser(tokens);
        GrammarListener eval2 = new GrammarListener();
//        parser.addErrorListener(new ANTLRErrorListener() {
//            @Override
//            public void syntaxError(Recognizer<?, ?> recognizer, Object o, int i, int i1, String s, RecognitionException e) {
//                System.out.println("Input mismatches grammar! SyntaxError\n");
//                throw new RuntimeException("Syntax Error!");
//            }
//
//            @Override
//            public void reportAmbiguity(Parser parser, DFA dfa, int i, int i1, boolean b, BitSet bitSet, ATNConfigSet atnConfigSet) {
//
//            }
//
//            @Override
//            public void reportAttemptingFullContext(Parser parser, DFA dfa, int i, int i1, BitSet bitSet, ATNConfigSet atnConfigSet) {
//
//            }
//
//            @Override
//            public void reportContextSensitivity(Parser parser, DFA dfa, int i, int i1, int i2, ATNConfigSet atnConfigSet) {
//
//            }
//        });

        ParseTree tree = parser.start(); // Start-Regel
        System.out.println(tree.toStringTree(parser));


        ParseTreeWalker.DEFAULT.walk(eval2, tree);

        System.out.println(eval2.buildProgram());
    }
}
