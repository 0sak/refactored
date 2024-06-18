package ANTLR;

import frontendantlr.GrammarListener;
import frontendantlr.HelloLexer;
import frontendantlr.HelloParser;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.ATNConfigSet;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

import java.io.*;
import java.nio.file.Path;
import java.util.BitSet;

public class TestHelpers {
    public static void makeProgram(Path workDirectory) throws IOException, InterruptedException {
        ProcessBuilder builder = new ProcessBuilder("make");
        builder.directory(workDirectory.toFile());
        //builder.inheritIO();

        Process p = builder.start();

        if (p.waitFor() != 0) {
            System.out.println("Could not make program !");
            throw new RuntimeException("Could not make program !");
        }
    }

    public static String getProgramOutput(Path workDirectory)
            throws IOException, InterruptedException {
        String line;
        String result ="";
        ProcessBuilder runner = new ProcessBuilder("./bin/program");
        runner.directory(workDirectory.toFile());
        //runner.inheritIO(); //You can not redirect the stdout/stderr because the Reader will not be
        // able to get the output. :)

        Process rp = runner.start();

        InputStream processStdOutput = rp.getInputStream();
        Reader r = new InputStreamReader(processStdOutput);
        BufferedReader br = new BufferedReader(r);

        while ((line = br.readLine()) != null) {
            result += line + "\n";
        }

        // String result = new String(rp.getInputStream().readAllBytes());

        if (rp.waitFor() != 0) {
            throw new RuntimeException("Program returned with value != 0");
        }
        return result;
    }

    public static GrammarListener getGrammarListener(String input) {
        HelloLexer lexer = new HelloLexer(CharStreams.fromString(
                input));

        CommonTokenStream tokens = new CommonTokenStream(lexer);
        HelloParser parser = new HelloParser(tokens);
        GrammarListener eval2 = new GrammarListener();

        parser.addErrorListener(new ANTLRErrorListener() {
            @Override
            public void syntaxError(Recognizer<?, ?> recognizer, Object o, int i, int i1, String s, RecognitionException e) {
                System.out.println("Input mismatches grammar! SyntaxError\n");
                throw new RuntimeException("Syntax Error!");
            }

            @Override
            public void reportAmbiguity(Parser parser, DFA dfa, int i, int i1, boolean b, BitSet bitSet, ATNConfigSet atnConfigSet) {

            }

            @Override
            public void reportAttemptingFullContext(Parser parser, DFA dfa, int i, int i1, BitSet bitSet, ATNConfigSet atnConfigSet) {

            }

            @Override
            public void reportContextSensitivity(Parser parser, DFA dfa, int i, int i1, int i2, ATNConfigSet atnConfigSet) {

            }
        });

        ParseTree tree = parser.start(); // Start-Regel
        //System.out.println(tree.toStringTree(parser));

        ParseTreeWalker.DEFAULT.walk(eval2, tree);
        return eval2;
    }

}
