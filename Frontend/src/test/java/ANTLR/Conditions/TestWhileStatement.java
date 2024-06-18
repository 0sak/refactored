package ANTLR.Conditions;

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

public class TestWhileStatement {
    String testClass = '[' + this.getClass().getSimpleName().toUpperCase() + "]\n";
    private static Stream<Arguments> sources() {
        return Stream.of(
                Arguments.of("a=0while(a < 3):a = a + 3print('Went into while Statement')#end#end", "Went into while Statement\n"));
    }
    @ParameterizedTest
    @MethodSource("sources")
    void while_statement(String input, String expected, @TempDir Path workDirectory) throws IOException, InterruptedException {
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
