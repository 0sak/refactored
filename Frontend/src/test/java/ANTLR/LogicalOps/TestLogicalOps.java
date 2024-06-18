package ANTLR.LogicalOps;

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

public class TestLogicalOps {
    String testClass = '[' + this.getClass().getSimpleName().toUpperCase() + "]\n";

    private static Stream<Arguments> sources() {
        return Stream.of(
                Arguments.of("True and True#end", "True\n"),
                Arguments.of("True and False#end", "False\n"),
                Arguments.of("False and True#end", "False\n"),
                Arguments.of("False and False#end", "False\n"),
                Arguments.of("True or True#end", "True\n"),
                Arguments.of("True or False#end", "True\n"),
                Arguments.of("False or True#end", "True\n"),
                Arguments.of("False or False#end", "False\n"),
                Arguments.of("not True#end", "False\n"),
                Arguments.of("not False#end", "True\n"));
    }

    @ParameterizedTest
    @MethodSource("sources")
    void logical_operation(String input, String expected, @TempDir Path workDirectory) throws IOException, InterruptedException {
        String result = "";

        System.out.println("Input : " + input);
        System.out.println("Expected : " + expected);

        GrammarListener eval2 = getGrammarListener(input);

        eval2.writeProgram(workDirectory);

        makeProgram(workDirectory);

        result = getProgramOutput(workDirectory);

        assertEquals(expected, result);
    }
}
