package me.dstet.delphi;

import java.util.ArrayList;
import java.util.Arrays;

import me.dstet.delphi.interpreter.InterpreterAction;
import me.dstet.delphi.interpreter.Visibility;

public class LanguageFunction {
    InterpreterAction[] actions;
    Visibility visibility;
    String[] operators;

    public LanguageFunction(InterpreterAction[] actions, Visibility visibility, String... opStrings)
    {
        this.actions = actions;
        this.visibility = visibility;
        this.operators = opStrings;
    }
}

