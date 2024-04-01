package org.example.Utilidades;

import java.util.regex.*;

public class PatternValidator {
    public static boolean validarPattern(String pattern) {
        // Expresión regular para validar el patrón
        String regex = "(6-[0-7]|7-6)(,(6-[0-7]|7-6)){0,9}";
        Pattern patternValidator = Pattern.compile(regex);
        Matcher matcher = patternValidator.matcher(pattern);

        // Comprobar si el patrón coincide con la expresión regular
        if (matcher.matches()) {
            // Verificar la lógica adicional
            boolean isValid = true;
            String[] parts = pattern.split(",");
            for (String part : parts) {
                String[] numbers = part.split("-");
                if (numbers.length == 2) {
                    int firstNumber = Integer.parseInt(numbers[0]);
                    int secondNumber = Integer.parseInt(numbers[1]);
                    if (firstNumber == 7 && secondNumber != 6) {
                        isValid = false;
                        break;
                    }
                } else {
                    isValid = false;
                    break;
                }
            }
            return isValid;
        } else {
            return false;
        }
    }

    public static void main(String[] args) {
        String[] patterns = {"7-6,6-7,6-3", "6-2,6-1", "6-5,6-7,6-4", "7-7,7-2,6-1", "6-5,3-7"};

        for (String pattern : patterns) {
            System.out.println("Pattern: " + pattern + " es válido: " + validarPattern(pattern));
        }
    }
}
