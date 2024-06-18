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

public class TestIfStatement {
    String testClass = '[' + this.getClass().getSimpleName().toUpperCase() + "]\n";
    private static Stream<Arguments> sources() {
        return Stream.of(
                Arguments.of("if(1):print('Went into if Statement')#end#end", "Went into if Statement\n"),
                Arguments.of("if(0):print('Went into if Statement')#end#end", ""),
                Arguments.of("if(True):print('Went into if Statement')#end#end", "Went into if Statement\n"),
                Arguments.of("if(False):print('Went into if Statement')#end#end", ""));
    }
    @ParameterizedTest
    @MethodSource("sources")
    void if_statement(String input, String expected, @TempDir Path workDirectory) throws IOException, InterruptedException {
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
