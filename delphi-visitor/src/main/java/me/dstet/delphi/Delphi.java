package me.dstet.delphi;

import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedHashMap;

/**
 * Hello world!
 */
public class Delphi {
    public static void main(String[] args) throws Exception {
        if (args.length == 0) {
            System.out.println("Usage: java -jar delphi.jar <unit1> <unit2> ... <main_program>");
            return;
        }

        LinkedHashMap<String, String> fileContents = new LinkedHashMap<>();
        String mainProgramString = "";

        try {
            for (int i = 0; i < args.length; i++) {
                Path filePath = Path.of(args[i]);
                String content = Files.readString(filePath);
                fileContents.put(filePath.getFileName().toString(), content);

                if (i == args.length - 1) { // Last file is the main program
                    mainProgramString = content;
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading files: " + e.getMessage());
            return;
        }

        System.out.println("File Map: " + fileContents);
        System.out.println("Main Program:");
        System.out.println(mainProgramString);
    }
}
