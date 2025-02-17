package me.dstet.delphi;

import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;

import me.dstet.delphi.delphiParser.ProgramContext;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.LinkedHashMap;

public class Delphi {
    public static void main(String[] args) throws Exception {
        // Help text
        if (args.length == 0 || args[0] == "--help" || args[0] == "-h") {
            System.out.println("Usage: java -jar delphi.jar <unit1> <unit2> ... <main_program>");
            System.out.println("Unit files need to match their names, so EchoNumber needs to be named EchoNumber.pas");
            return;
        }

        ArrayList<String> fileContents = new ArrayList<>();
        String mainProgramString = "";

        try {
            for (int i = 0; i < args.length; i++) {
                Path filePath = Path.of(args[i]);
                String content = Files.readString(filePath);

                if (i == args.length - 1) { // Last file is the main program
                    mainProgramString = content;
                } else {
                    fileContents.add(content);
                }
            }
        } catch (IOException e) {
            System.out.println("Working Directory = " + System.getProperty("user.dir"));
            System.err.println("Error reading files: " + e.getMessage());
            return;
        }

        SymbolTable reusedTable = null;
        for (String unitStr : fileContents) {
            Visitor unitVisitor = null;

            if (reusedTable == null) {
                unitVisitor = new Visitor<>(true);
            } else {
                unitVisitor = new Visitor<>(false, reusedTable);
            }

            CodePointCharStream unitStream = CharStreams.fromString(unitStr);
            delphiLexer lexer = new delphiLexer(unitStream);
    
            CommonTokenStream tokenStream = new CommonTokenStream(lexer);
            delphiParser parser = new delphiParser(tokenStream);  
            
            try {
                System.out.println("Reading unit");
                ProgramContext unitContext = parser.program();
                unitVisitor.visitProgram(unitContext);
            } catch (Exception e) {
                System.err.println("ERROR: Encountered exception parsing unit: " + e);
                return;
            }

            reusedTable = unitVisitor.getSymbolTable();
        }

        CodePointCharStream prgrmStream = CharStreams.fromString(mainProgramString);
        delphiLexer lexer = new delphiLexer(prgrmStream);

        CommonTokenStream tokenStream = new CommonTokenStream(lexer);
        delphiParser parser = new delphiParser(tokenStream);
        try {
            System.out.println("Reading program");
            
        } catch (Exception e) {
            System.err.println("ERROR: Encountered exception parsing program: " + e);
            return;
        }
        ProgramContext program = parser.program();
    
            System.out.println("Executing program...\n\n---------------\n\n");
    
            Visitor visitor = new Visitor<Object>(false, reusedTable);
            visitor.visitProgram(program);
    }
}
