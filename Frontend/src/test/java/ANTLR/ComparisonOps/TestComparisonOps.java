package ANTLR.ComparisonOps;

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

public class TestComparisonOps {
    String testClass = '[' + this.getClass().getSimpleName().toUpperCase() + "]\n";

    private static Stream<Arguments> sources() {
        return Stream.of(
                Arguments.of("1==1#end", "True\n"),
                Arguments.of("1==0#end", "False\n"),
                Arguments.of("1!=1#end", "False\n"),
                Arguments.of("1!=0#end", "True\n"),
                Arguments.of("1!=1#end", "False\n"),
                Arguments.of("1>=1#end", "True\n"),
                Arguments.of("2>=1#end", "True\n"),
                Arguments.of("1>=3#end", "False\n"),
                Arguments.of("1>1#end", "False\n"),
                Arguments.of("2>1#end", "True\n"),
                Arguments.of("1<=1#end", "True\n"),
                Arguments.of("1<=2#end", "True\n"),
                Arguments.of("3<=1#end", "False\n"),
                Arguments.of("1<1#end", "False\n"),
                Arguments.of("1<3#end", "True\n"));
    }

    @ParameterizedTest
    @MethodSource("sources")
    void comparison_operation(String input, String expected, @TempDir Path workDirectory) throws IOException, InterruptedException {
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
