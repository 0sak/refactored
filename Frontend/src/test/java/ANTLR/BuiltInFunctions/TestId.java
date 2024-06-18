package ANTLR.BuiltInFunctions;

import frontendantlr.GrammarListener;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.io.TempDir;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.IOException;
import java.nio.file.Path;
import java.util.stream.Stream;

import static ANTLR.TestHelpers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@Disabled
public class TestId {
    String testClass = '[' + this.getClass().getSimpleName().toUpperCase() + "]\n";

    private static Stream<Arguments> sources() {
        return Stream.of(
                Arguments.of("print(id(True))#end", "61\n"),
                Arguments.of("print(id(False))#end", "61\n"),
                Arguments.of("print(id('testString'))#end", "61\n"),
                Arguments.of("print(id(23))#end", "61\n"),
                Arguments.of("print(id(id(True)))#end", "70\n"),
                Arguments.of("def testFunc1():print(123)#endid(testFunc1())#end", "123\n103\n"));
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

        System.out.println("Result : " + result);

        assertEquals(expected, result);
    }
}