package ANTLR.ArithmeticOps;

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

public class TestArithmeticOps {
    String testClass = '[' + this.getClass().getSimpleName().toUpperCase() + "]\n";

    private static Stream<Arguments> sources() {
        return Stream.of(
                Arguments.of("1+2#end", "3\n"),
                Arguments.of(Integer.MAX_VALUE + "+0#end", Integer.MAX_VALUE + "\n"),
                Arguments.of(Integer.MIN_VALUE + "+0#end", Integer.MIN_VALUE + "\n"),
                Arguments.of("2--1#end", "3\n"),
                Arguments.of(Integer.MAX_VALUE + "--0#end", Integer.MAX_VALUE + "\n"),
                Arguments.of(Integer.MIN_VALUE + "--0#end", Integer.MIN_VALUE + "\n"),
                Arguments.of("2*2#end", "4\n"),
                Arguments.of(Integer.MAX_VALUE + "*0#end", "0\n"),
                Arguments.of(Integer.MIN_VALUE + "*0#end", "0\n"),
                Arguments.of("5/2#end", "2\n"),
                Arguments.of("0/" + Integer.MAX_VALUE + "#end", "0\n"),
                Arguments.of("0/" + Integer.MIN_VALUE + "#end", "0\n"));
    }

    @ParameterizedTest
    @MethodSource("sources")
    void arithmetic_operation(String input, String expected, @TempDir Path workDirectory) throws IOException, InterruptedException {
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
