package frontendantlr.UI;

import com.github.weisj.darklaf.LafManager;
import com.github.weisj.darklaf.theme.IntelliJTheme;
import frontendantlr.GrammarListener;
import frontendantlr.HelloLexer;
import frontendantlr.HelloParser;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.ATNConfigSet;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.apache.commons.io.FileUtils;
import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rsyntaxtextarea.SyntaxConstants;
import org.fife.ui.rtextarea.RTextScrollPane;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.BitSet;

import static frontendantlr.Helper.Helpers.getProgramOutput;
import static frontendantlr.Helper.Helpers.makeProgram;

public class MiniPythonIDLEPOC extends Frame implements WindowListener, ActionListener {
    RSyntaxTextArea output = new RSyntaxTextArea(12, 12);
    RSyntaxTextArea mpyCode = new RSyntaxTextArea( "class classA():\n" +
            "\tdef __init__(self,x):\n" +
            "\t\tprint('[classA] print from init')\n" +
            "\t\tprint(x)\n" +
            "\t#end\n" +
            "#end\n" +
            "\n" +
            "class classB(classA):\n" +
            "\tdef __init__(self,x):\n" +
            "super(x)" +
            "\t\tprint('[classB] print from init')\n" +
            "\t\tprint(x)\n" +
            "\t#end\n" +
            "#end\n" +
            "f = classB(1337)\n" +
            "#end");
    RTextScrollPane sp = new RTextScrollPane(mpyCode);
    RTextScrollPane outScrollPane = new RTextScrollPane(output);
    JToolBar toolBar = new JToolBar("Test");
    JButton bRunCBuilder, bRunProgram, test;
    String cbuilderOutput;
    ActionListener buildProgram = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            HelloLexer lexer = new HelloLexer(CharStreams.fromString(mpyCode.getText().replace("\n","")));

            CommonTokenStream tokens = new CommonTokenStream(lexer);
            HelloParser parser = new HelloParser(tokens);
            GrammarListener eval2 = new GrammarListener();

            parser.addErrorListener(new ANTLRErrorListener() {
                @Override
                public void syntaxError(Recognizer<?, ?> recognizer, Object o, int i, int i1, String s, RecognitionException e) {
                    output.setText("Grammar Error : \n" + s);
                    throw new RuntimeException("Has to be catched :)");
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

            try {
                ParseTree tree = parser.start();
                ParseTreeWalker.DEFAULT.walk(eval2, tree);
                output.setText(tree.toStringTree(parser) + "\n\n" + eval2.buildProgram());
                cbuilderOutput = eval2.buildProgram();
            } catch (Exception ex) {
            }
        }
    };
    ActionListener runProgram = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            HelloLexer lexer = new HelloLexer(CharStreams.fromString(mpyCode.getText().replace("\n","")));

            CommonTokenStream tokens = new CommonTokenStream(lexer);
            HelloParser parser = new HelloParser(tokens);
            GrammarListener eval2 = new GrammarListener();
            parser.addErrorListener(new ANTLRErrorListener() {
                @Override
                public void syntaxError(Recognizer<?, ?> recognizer, Object o, int i, int i1, String s, RecognitionException e) {
                    output.setText("Grammar Error : \n" + s);
                    throw new RuntimeException("Has to be catched :)");
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

            ParseTree tree = parser.start();

            try {
                ParseTreeWalker.DEFAULT.walk(eval2, tree);
            } catch (Exception ex) {
                return;
            }
            Path fileOutput =
                    FileSystems.getDefault().getPath("build/antlr/compilerOutput/");

            eval2.writeProgram(fileOutput);

            try {
                makeProgram(fileOutput);
            } catch (Exception ex) {
                output.setText(ex.getMessage());
                try {
                    FileUtils.deleteDirectory(new File(fileOutput.toUri()));
                } catch (IOException exc) {
                    throw new RuntimeException(exc);
                }
                throw new RuntimeException(ex.toString());
            }

            try {
                String programOutput = getProgramOutput(fileOutput);
                output.setText(programOutput);
            } catch (Exception ex) {
                output.setText(ex.getMessage());
                try {
                    FileUtils.deleteDirectory(new File(fileOutput.toUri()));
                } catch (IOException exc) {
                    throw new RuntimeException(exc);
                }
                throw new RuntimeException(ex.toString());
            }

            try {
                FileUtils.deleteDirectory(new File(fileOutput.toUri()));
            } catch (Exception ex) {
                output.setText(Arrays.toString(ex.getStackTrace()));
                throw new RuntimeException(ex.toString());
            }
        }
    };
    public MiniPythonIDLEPOC(String title) throws UnsupportedLookAndFeelException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        super(title);
        LafManager.setTheme(new IntelliJTheme());
        LafManager.install();
        setLayout(new BorderLayout());
        addWindowListener(this);

        JPanel cp = new JPanel(new BorderLayout());

        mpyCode.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_PYTHON);
        mpyCode.setCodeFoldingEnabled(true);

        output.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_C);
        output.setCodeFoldingEnabled(true);

        bRunCBuilder = new JButton("Run CBuilder");
        bRunCBuilder.setToolTipText("CONTROL + D");
        bRunCBuilder.addActionListener(buildProgram);

        bRunProgram = new JButton("Run C-Runtime");
        bRunProgram.setToolTipText("CONTROL + R");
        bRunProgram.addActionListener(runProgram);

        cp.registerKeyboardAction(buildProgram, KeyStroke.getKeyStroke(KeyEvent.VK_D, InputEvent.CTRL_DOWN_MASK), JComponent.WHEN_IN_FOCUSED_WINDOW);
        cp.registerKeyboardAction(runProgram, KeyStroke.getKeyStroke(KeyEvent.VK_R, InputEvent.CTRL_DOWN_MASK), JComponent.WHEN_IN_FOCUSED_WINDOW);

        toolBar.setFloatable(false);
        toolBar.add(bRunCBuilder);
        toolBar.add(bRunProgram);

        output.setEditable(false);

        cp.add(toolBar, BorderLayout.PAGE_START);
        //add(mpyCode, BorderLayout.CENTER);
        cp.add(sp, BorderLayout.CENTER);
        cp.add(outScrollPane, BorderLayout.PAGE_END);
        add(cp);
    }
    public static void main(String[] args) throws UnsupportedLookAndFeelException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        MiniPythonIDLEPOC myWindow = new MiniPythonIDLEPOC("MPyIDLEPOC");
        myWindow.setSize(1200, 800);
        myWindow.setVisible(true);
    }
    @Override
    public void actionPerformed(ActionEvent e) {

    }
    @Override
    public void windowOpened(WindowEvent e) {

    }
    @Override
    public void windowClosing(WindowEvent e) {
        System.exit(0);
    }
    @Override
    public void windowClosed(WindowEvent e) {

    }
    @Override
    public void windowIconified(WindowEvent e) {

    }
    @Override
    public void windowDeiconified(WindowEvent e) {

    }
    @Override
    public void windowActivated(WindowEvent e) {

    }
    @Override
    public void windowDeactivated(WindowEvent e) {

    }
}
