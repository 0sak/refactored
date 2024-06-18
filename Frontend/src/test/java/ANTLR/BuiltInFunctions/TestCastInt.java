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
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TestCastInt {
    String testClass = '[' + this.getClass().getSimpleName().toUpperCase() + "]\n";

    private static Stream<Arguments> sources() {
        return Stream.of(
                Arguments.of("print(int('23'))#end", "23\n"),
                Arguments.of("print(int(True))#end", "1\n"),
                Arguments.of("print(int(False))#end", "0\n"),
                Arguments.of("print(int('0'))#end", "0\n"),
                Arguments.of("print(int('-23'))#end", "-23\n"));
    }

    private static Stream<Arguments> sources_throws(){
        return Stream.of(
                Arguments.of("print(int(123))#end", "\n"),
                Arguments.of("print(int('false'))#end", "\n"));
    }

    @ParameterizedTest
    @MethodSource("sources")
    void cast_int(String input, String expected, @TempDir Path workDirectory) throws IOException, InterruptedException {
        String result = "";

        System.out.println("Input : " + input);
        System.out.println("Expected : " + expected);

        GrammarListener eval2 = getGrammarListener(input);

        eval2.writeProgram(workDirectory);

        makeProgram(workDirectory);

        result = getProgramOutput(workDirectory);

        assertEquals(expected, result);
    }

    @ParameterizedTest
    @MethodSource("sources_throws")
    void cast_int_throws(String input, String expected, @TempDir Path workDirectory) throws IOException, InterruptedException {
        String result = "";

        System.out.println("Input : " + input);
        System.out.println("Expected : " + expected);

        GrammarListener eval2 = getGrammarListener(input);

        eval2.writeProgram(workDirectory);

        makeProgram(workDirectory);

        assertThrows(RuntimeException.class, ()-> getProgramOutput(workDirectory));
    }
}
