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

public class TestClassMembers {
    String testClass = '[' + this.getClass().getSimpleName().toUpperCase() + "]\n";
    private static Stream<Arguments> sources() {
        return Stream.of(
                Arguments.of("class ClassA():\n" +
                        "\tdef __init__(self,x):\n" +
                        "\t\tself.param1=x \n" +
                        "\t\tprint('[ClassA] Print from __init__')\n" +
                        "\t#end\n" +
                        "\tdef setParam1(self,val):\n" +
                        "\t\tself.param1=val\n" +
                        "\t#end\n" +
                        "\tdef getParam1(self):\n" +
                        "\t\treturn self.param1\n" +
                        "\t#end\n" +
                        "#end\n" +
                        "a=ClassA(123)\n" +
                        "print(a.getParam1())\n" +
                        "a.setParam1(1337)\n" +
                        "print(a.getParam1())\n" +
                        "#end", "[ClassA] Print from __init__\n123\n1337\n")
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
