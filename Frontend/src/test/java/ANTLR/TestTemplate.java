package ANTLR;

import frontendantlr.GrammarListener;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Path;

import static ANTLR.TestHelpers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestTemplate {
    @Test
    void wuppie(@TempDir Path workDirectory) throws IOException, InterruptedException {

        String result = "";

        GrammarListener eval2 = getGrammarListener("1+1#end");

        eval2.writeProgram(workDirectory);

        makeProgram(workDirectory);

        result = getProgramOutput(workDirectory);

        assertEquals(result,"2\n");
    }

}
