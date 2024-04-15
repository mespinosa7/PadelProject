package org.example.Utilidades;

import java.util.regex.*;

public class PatternValidator {
    /**
     * Valida un patrón de resultados de juegos de padel según ciertas reglas.
     *
     * @param pattern El patrón de resultados a validar.
     * @return true si el patrón cumple con las reglas, false de lo contrario.
     */
    public static boolean validarPattern(String pattern) {
        // Expresión regular para validar el patrón
        String regex = "^((6-[0-4])|(7-[5-6])|([5-7]-7)|([0-4]-6)) ((6-[0-4])|(7-[5-6])|([5-7]-7)|([0-4]-6)) ((6-[0-4])|(7-[5-6])|([5-7]-7)|([0-4]-6))";
        Pattern patternValidator = Pattern.compile(regex);
        Matcher matcher = patternValidator.matcher(pattern);

        // Comprobar si el patrón coincide con la expresión regular
        if (matcher.matches()) {
            return true;
        } else {
            return false;
        }
    }
    /*
    public static void main(String[] args) {
        String[] patterns = {"7-6 6-7 6-3", "6-2 6-1", "6-5 6-7 6-4", "7-6 7-2 6-1", "6-5 3-7"};

        for (String pattern : patterns) {
            System.out.println("Pattern: " + pattern + " es válido: " + validarPattern(pattern));
        }
    }*/
}
