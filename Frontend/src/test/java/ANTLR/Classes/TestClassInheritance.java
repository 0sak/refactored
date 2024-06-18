package ANTLR.Classes;

import frontendantlr.GrammarListener;
import org.junit.jupiter.api.io.TempDir;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.IOException;
import java.nio.file.Path;
import java.util.stream.Stream;

import static ANTLR.TestHelpers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestClassInheritance {
    String testClass = '[' + this.getClass().getSimpleName().toUpperCase() + "]\n";
    private static Stream<Arguments> sources() {
        return Stream.of(
                Arguments.of("class classA():\n" +
                        "\tdef __init__(self,x):\n" +
                        "\t\tprint('[classA] print from __init__')\n" +
                        "\t\tprint(x)\n" +
                        "\t#end\n" +
                        "#end\n" +
                        "\n" +
                        "class classB(classA):\n" +
                        "\tdef __init__(self,x):\n" +
                        "super(x)\t\tprint('[classB] print from __init__')\n" +
                        "\t\tprint(x)\n" +
                        "\t#end\n" +
                        "#end\n" +
                        "f = classB(1337)\n" +
                        "#end" +
                        "#end", "[classA] print from __init__\n1337\n[classB] print from __init__\n1337\n")
        );
    }
    @ParameterizedTest
    @MethodSource("sources")
    void function_params(String input, String expected, @TempDir Path workDirectory) throws IOException, InterruptedException {
        String result = "";

        System.out.println("Input : " + input);
        System.out.println("Expected : " + expected);

        input = input.replace("\n", "");
        input = input.replace("\t","");

        GrammarListener grammarListener = getGrammarListener(input);

        grammarListener.writeProgram(workDirectory);

        makeProgram(workDirectory);

        result = getProgramOutput(workDirectory);

        System.out.println("Result : " + result);

        assertEquals(expected, result);
    }
}
