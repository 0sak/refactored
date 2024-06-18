package ANTLR.DeclarationAndAssignment;

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

public class TestDeclarationAndAssignment {
    String testClass = '[' + this.getClass().getSimpleName().toUpperCase() + "]\n";

    private static Stream<Arguments> sources() {
        return Stream.of(
                Arguments.of("a=133 print(a)#end", "133\n"),
                Arguments.of("a=-133 print(a)#end", "-133\n"),
                Arguments.of("a='stringVal' print(a)#end", "stringVal\n"),
                Arguments.of("a=True print(a)#end", "True\n"),
                Arguments.of("a=False print(a)#end", "False\n"),
                Arguments.of("a=123 b=a print(b)#end", "123\n"),
                Arguments.of("def testFunky():print(123)#enda=testFunky()print(id(a))#end", "123\n104\n"));
    }

    @ParameterizedTest
    @MethodSource("sources")
    void declaration_and_assignment(String input, String expected, @TempDir Path workDirectory) throws IOException, InterruptedException {
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
