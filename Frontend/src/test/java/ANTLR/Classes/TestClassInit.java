package ANTLR.Classes;

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

public class TestClassInit {
    String testClass = '[' + this.getClass().getSimpleName().toUpperCase() + "]\n";
    private static Stream<Arguments> sources() {
        return Stream.of(
                Arguments.of("class ClassA():def __init__(self):print('[ClassA] Print from __init__')#end#enda=ClassA()#end", "[ClassA] Print from __init__\n"),
                Arguments.of("class ClassA():def __init__(self, param1):print('[ClassA] Print from __init__')print(param1)#end#enda=ClassA(123)#end", "[ClassA] Print from __init__\n123\n"),
                Arguments.of("class ClassA():def __init__(self, param1, param2):print('[ClassA] Print from __init__')print(param1)print(param2)#end#enda=ClassA('test',123)#end", "[ClassA] Print from __init__\ntest\n123\n")
                );
    }
    @ParameterizedTest
    @MethodSource("sources")
    void function_params(String input, String expected, @TempDir Path workDirectory) throws IOException, InterruptedException {
        String result = "";

        System.out.println("Input : " + input);
        System.out.println("Expected : " + expected);

        GrammarListener grammarListener = getGrammarListener(input);

        grammarListener.writeProgram(workDirectory);

        makeProgram(workDirectory);

        result = getProgramOutput(workDirectory);

        System.out.println("Result : " + result);

        assertEquals(expected, result);
    }
}
