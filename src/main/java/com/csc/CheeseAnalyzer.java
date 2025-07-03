package com.csc;

import java.io.*;
import java.util.*;

public class CheeseAnalyzer {
    public static void main(String[] args) {
        String inputFile = "cheese.csv";
        String outputFile = "output.txt";

        int pasteurizedCount = 0;
        int rawCount = 0;
        int organicHighMoistureCount = 0;
        Map<String, Integer> milkTypeCounts = new HashMap<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile));
             PrintWriter writer = new PrintWriter(new FileWriter(outputFile))) {

            String header = reader.readLine(); // skip header
            String line;

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");

                if (parts.length < 10) continue; // Skip invalid/missing data rows

                String milkTreatment = parts[4].trim().toLowerCase();
                String organic = parts[5].trim();
                String moistureStr = parts[6].trim();
                String milkType = parts[3].trim().toLowerCase();

                if (milkTreatment.contains("pasteurized")) pasteurizedCount++;
                else if (milkTreatment.contains("raw")) rawCount++;

                if (organic.equals("1")) {
                    try {
                        double moisture = Double.parseDouble(moistureStr);
                        if (moisture > 41.0) organicHighMoistureCount++;
                    } catch (NumberFormatException ignored) {}
                }

                if (!milkType.isEmpty()) {
                    milkTypeCounts.put(milkType, milkTypeCounts.getOrDefault(milkType, 0) + 1);
                }
            }

            // Find most common milk type
            String mostCommonMilk = "";
            int mostCount = 0;
            for (String type : milkTypeCounts.keySet()) {
                if (milkTypeCounts.get(type) > mostCount) {
                    mostCount = milkTypeCounts.get(type);
                    mostCommonMilk = type;
                }
            }

            // Write results to output.txt
            writer.println("Pasteurized Milk Count: " + pasteurizedCount);
            writer.println("Raw Milk Count: " + rawCount);
            writer.println("Organic cheeses with >41% moisture: " + organicHighMoistureCount);
            writer.println("Most common milk type: " + mostCommonMilk);

            System.out.println("Analysis complete! See output.txt.");

        } catch (IOException e) {
            System.out.println("Error reading or writing files: " + e.getMessage());
        }
    }
}
