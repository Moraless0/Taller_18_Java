package com.mycompany.gestorproyectos.util;

import java.util.regex.Pattern;

public class InputValidator {
    private static final Pattern EMAIL_PATTERN = Pattern.compile(
        "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$"
    );

    public static boolean isValidEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            return false;
        }
        return EMAIL_PATTERN.matcher(email).matches();
    }

    public static boolean isValidHours(double hours) {
        return hours > 0 && hours <= 40;
    }

    public static boolean isValidProjectState(String state) {
        return state != null && (
            state.equals("PLANIFICADO") ||
            state.equals("EN_CURSO") ||
            state.equals("FINALIZADO")
        );
    }

    public static boolean isNotEmpty(String value) {
        return value != null && !value.trim().isEmpty();
    }

    public static boolean isValidId(int id) {
        return id > 0;
    }
}
