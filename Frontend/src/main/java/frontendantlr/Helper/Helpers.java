package frontendantlr.Helper;

import java.io.*;
import java.nio.file.Path;

public class Helpers {
    public static boolean is_true_or_false(String value){
        return "true".equals(value) || "false".equals(value);
    }

    public static String getProgramOutput(Path workDirectory)
        throws IOException, InterruptedException {
        String line;
        String result ="";
        String resultError = "";

        ProcessBuilder runner = new ProcessBuilder("./bin/program");
        runner.directory(workDirectory.toFile());
        //runner.inheritIO(); //You can not redirect the stdout/stderr because the Reader will not be
        // able to get the output. :)

        Process rp = runner.start();

        InputStream processStdOutput = rp.getInputStream();
        InputStream errorStdOutput = rp.getErrorStream();
        Reader r = new InputStreamReader(processStdOutput);
        Reader re = new InputStreamReader(errorStdOutput);
        BufferedReader br = new BufferedReader(r);
        BufferedReader bre = new BufferedReader(re);

        while ((line = br.readLine()) != null) {
            result += line + "\n";
        }

        while ((line = bre.readLine()) != null) {
            resultError += line + "\n";
        }
        // String result = new String(rp.getInputStream().readAllBytes());

        if (rp.waitFor() != 0) {
            throw new RuntimeException("Runtime Error : \n" + resultError);
        }

        return result;
    }

    public static void makeProgram(Path workDirectory) throws IOException, InterruptedException {
        String line;
        String result ="";

        ProcessBuilder builder = new ProcessBuilder("make");
        builder.directory(workDirectory.toFile());
        //builder.inheritIO();

        Process p = builder.start();

        InputStream processStdOutput = p.getErrorStream();
        Reader r = new InputStreamReader(processStdOutput);
        BufferedReader br = new BufferedReader(r);

        while ((line = br.readLine()) != null) {
            result += line + "\n";
        }

        if (p.waitFor() != 0) {
            throw new RuntimeException("Make Error : \n" + result);
        }
    }
}
