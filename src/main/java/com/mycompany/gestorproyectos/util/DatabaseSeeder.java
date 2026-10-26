package com.mycompany.gestorproyectos.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.Statement;
import java.util.stream.Collectors;

public class DatabaseSeeder {
    public static void executeScript(String scriptPath) {
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement()) {
            
            InputStream inputStream = DatabaseSeeder.class.getClassLoader().getResourceAsStream(scriptPath);
            if (inputStream == null) {
                throw new RuntimeException("Script not found: " + scriptPath);
            }

            String script = new BufferedReader(new InputStreamReader(inputStream))
                .lines()
                .collect(Collectors.joining("\n"));

            String[] statements = script.split(";\\s*");
            for (String statement : statements) {
                if (!statement.trim().isEmpty()) {
                    stmt.execute(statement);
                }
            }

            System.out.println("Script executed successfully: " + scriptPath);
        } catch (Exception e) {
            System.err.println("Error executing script: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        System.out.println("Executing schema.sql...");
        executeScript("schema.sql");
        
        System.out.println("\nExecuting seed_data.sql...");
        executeScript("seed_data.sql");
        
        System.out.println("\nDatabase seeding completed.");
    }
}
