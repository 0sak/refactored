package ANTLR.BuiltInFunctions;

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

public class TestCastStr {
    String testClass = '[' + this.getClass().getSimpleName().toUpperCase() + "]\n";

    private static Stream<Arguments> sources() {
        return Stream.of(
                Arguments.of("print(str(True))#end", "True\n"),
                Arguments.of("print(str(False))#end", "False\n"),
                Arguments.of("print(str('testString'))#end", "testString\n"),
                Arguments.of("print(str(0))#end", "0\n"),
                Arguments.of("print(str(-23))#end", "-23\n"),
                Arguments.of("print(str(23))#end", "23\n"));
    }

    @ParameterizedTest
    @MethodSource("sources")
    void cast_str(String input, String expected, @TempDir Path workDirectory) throws IOException, InterruptedException {
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
