package me.dstet.delphi.interpreter;

// All functions will be made up of/can be decomposed a series of actions
enum InterpreterAction {
    WRITE,
    ADD,
    SUBTRACT,
    DIVIDE,
    MOD,
    MULTIPLY,
    ASSIGN,
    AND,
    OR,
    NOT
}

enum Visibility {
    PUBLIC,
    PRIVATE,
    PUBLISHED,
    PROTECTED
}