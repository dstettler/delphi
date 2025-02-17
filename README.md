# Toy Delphi Interpreter
For the purposes of the assignment, I have implemented classes and objects, constructors and destructors, encapsulation, and a
very small subset of Pascal. Anything I didn't think was directly required for these things was not implemented.
## Functionalities Implemented
- Object/unit declaration and implementation
- Object field permissions/encapsulation
- Function/procedure declaration and implementation
- Constructor/destructor declaration and implementation
- Variable declaration, assignment, and manipulation
- Arithmetic expressions
- Terminal I/O via `writeln` and `readln`

## Functionalities *Not* Implemented
- Object inheritance
- Object interfaces
- Loops
- Goto statements and labels
- Conditional statements
- Comparative expressions
- `With x Do y` statements
- `x in y` expressions
- Packed, pointer, string, subrange, and scalar (`( ident, ident, ident )`) variable declarations
- Sets
- Forward-declaration of functions (class methods and declaration in-place are ok)
- Array variables


## How to Run
I have supplied a copy of the source code (in a zip file) for you to peruse that is directly generated from the Git repo I've been using to back the project up.
If you extract and run the `build.bat` (Windows) or `build.sh` (*NIX) script, it should run a Gradle build and copy the generated packed `delphi.jar` jar file to the root directory.

Otherwise, you can run my packaged `delphi.jar` via command line. Running it without arguments gives a small help message that looks like this:
```
Usage: java -jar delphi.jar <unit1> <unit2> ... <main_program>
Unit files need to match their names, so EchoNumber needs to be named EchoNumber.pas
```

For testing each of the main functionalities mentioned
