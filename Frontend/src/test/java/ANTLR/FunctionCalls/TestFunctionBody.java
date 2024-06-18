package ANTLR.FunctionCalls;

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

public class TestFunctionBody {
    String testClass = '[' + this.getClass().getSimpleName().toUpperCase() + "]\n";
    private static Stream<Arguments> sources() {
        return Stream.of(
                Arguments.of("def function1():return ''#endprint(function1())#end", "\n"),
                Arguments.of("def function1():print('Print from function body')return ''#endprint(function1())#end", "Print from function body\n\n"),
                Arguments.of("def function1():y=133 return y#endprint(function1())#end", "133\n"),
                Arguments.of("def function1(param1):return param1#endprint(function1(133))#end", "133\n"),
                Arguments.of("def function1(param1, param2):return param1+param2#endprint(function1(1,2))#end", "3\n"),
                Arguments.of("def function1(param1, param2):if(param1>0):return param1+param2#end#endprint(function1(1,3))#end", "4\n"));
    }
    @ParameterizedTest
    @MethodSource("sources")
    void function_params(String input, String expected, @TempDir Path workDirectory) throws IOException, InterruptedException {
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
