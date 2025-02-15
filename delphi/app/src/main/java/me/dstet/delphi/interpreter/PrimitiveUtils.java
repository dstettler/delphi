package me.dstet.delphi.interpreter;

public class PrimitiveUtils {
    public static Object getDefaultFromPrimitive(Primitive primitive) {
        return switch(primitive) {
            case Integer -> Integer.valueOf(0);
            case Boolean -> Boolean.FALSE;
            case Char -> Character.valueOf('\0');
            case Real -> Double.valueOf(0);
            case String -> new String();
            default -> null;
        };
    }
}
