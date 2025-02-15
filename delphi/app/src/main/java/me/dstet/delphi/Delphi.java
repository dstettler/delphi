package me.dstet.delphi;

import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;

import me.dstet.delphi.delphiParser.ProgramContext;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedHashMap;

public class Delphi {
    public static void main(String[] args) throws Exception {
        // Help text
        if (args.length == 0 || args[0] == "--help" || args[0] == "-h") {
            System.out.println("Usage: java -jar delphi.jar <unit1> <unit2> ... <main_program>");
            System.out.println("Unit files need to match their names, so EchoNumber needs to be named EchoNumber.pas");
            return;
        }

        LinkedHashMap<String, String> fileContents = new LinkedHashMap<>();
        String mainProgramString = "";

        try {
            for (int i = 0; i < args.length; i++) {
                Path filePath = Path.of(args[i]);
                String content = Files.readString(filePath);
                fileContents.put(filePath.getFileName().toString().split(".pas")[0], content);

                if (i == args.length - 1) { // Last file is the main program
                    mainProgramString = content;
                }
            }
        } catch (IOException e) {
            System.out.println("Working Directory = " + System.getProperty("user.dir"));
            System.err.println("Error reading files: " + e.getMessage());
            return;
        }

        CodePointCharStream prgrmStream = CharStreams.fromString(mainProgramString);
        delphiLexer lexer = new delphiLexer(prgrmStream);

        CommonTokenStream tokenStream = new CommonTokenStream(lexer);
        delphiParser parser = new delphiParser(tokenStream);

        ProgramContext program = parser.program();

        Visitor visitor = new Visitor<Object>(false);
        try {
            visitor.visitProgram(program);
        }
        catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
}
