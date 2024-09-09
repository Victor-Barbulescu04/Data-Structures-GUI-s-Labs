/*
 * Course: CSC1110A
 * Fall 2023
 * Lab 11 - Interfaces
 * Name: Victor Barbulescu
 * Created: 11/10/2023
 */
package test;

import barbulescuv.BakedIngredient;
import barbulescuv.Ingredient;
import barbulescuv.Measure;
import barbulescuv.SimpleIngredient;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Scanner;

/**
 * Test Suite for the Kitchen Lab
 */
public class Lab11UnitTests {
    @Test
    @DisplayName("Testing Ingredient Interface")
    void ingredient() {
        Ingredient i = new Ingredient() {
            @Override
            public double getCalories() {
                return 0;
            }
            @Override
            public double getCups() {
                return 0;
            }
            @Override
            public String getName() {
                return null;
            }
            @Override
            public boolean isDry() {
                return false;
            }
            @Override
            public void printRecipe() {
            }
        };
        Assertions.assertTrue(i instanceof Ingredient);
    }

    @Test
    @DisplayName("Testing Simple Ingredient")
    void simpleIngredient() throws FileNotFoundException {
        // test constructor
        Ingredient simple = new SimpleIngredient(1, 1, true, "flour");
        // test getters
        Assertions.assertEquals(1, simple.getCalories());
        Assertions.assertEquals(1, simple.getCups());
        Assertions.assertTrue(simple.isDry());
        Assertions.assertEquals("flour", simple.getName());
        // test printRecipe
        OutputStream oldOut = System.out;
        System.setOut(new PrintStream("output.txt"));
        simple.printRecipe();
        System.setOut(new PrintStream(oldOut));
        Scanner read = new Scanner(new File("output.txt"));
        StringBuilder sb = new StringBuilder();
        while(read.hasNextLine()) {
            sb.append(read.nextLine()).append("\n");
        }
        Assertions.assertEquals("""
                ====================================================
                flour
                ====================================================
                Cups: 1 Cups
                Energy: 1 Calories""", sb.toString().trim());
    }

    @Test
    @DisplayName("Testing Baked Ingredient")
    void bakedIngredient() throws FileNotFoundException {
        final double expansionFactor = 0.5;
        // test constructor
        Ingredient flour = new SimpleIngredient(1, 1, true, "flour");
        Ingredient baked = new BakedIngredient(flour, expansionFactor);
        // test getters
        Assertions.assertEquals(1, baked.getCalories());
        Assertions.assertEquals(expansionFactor, baked.getCups());
        Assertions.assertTrue(baked.isDry());
        Assertions.assertEquals("Baked flour", baked.getName());
        // test printRecipe
        OutputStream oldOut = System.out;
        System.setOut(new PrintStream("output.txt"));
        baked.printRecipe();
        System.setOut(new PrintStream(oldOut));
        Scanner read = new Scanner(new File("output.txt"));
        StringBuilder sb = new StringBuilder();
        while(read.hasNextLine()) {
            sb.append(read.nextLine()).append("\n");
        }
        Assertions.assertEquals("""
                ====================================================
                Baked flour
                ====================================================
                Ingredient to be baked: flour
                Cups: 0.5 Cups
                Energy: 1 Calories
                                
                ====================================================
                flour
                ====================================================
                Cups: 1 Cups
                Energy: 1 Calories""", sb.toString().trim());
    }

    @Test
    @DisplayName("Testing Measure")
    void measure() throws FileNotFoundException {
        final int numerator = 1;
        final int denominator = 2;
        final double halfCup = 0.5;
        Ingredient flour = new SimpleIngredient(1, 1, true, "flour");
        // test constructor
        Ingredient measure = new Measure(denominator, numerator, flour);
        Ingredient measure2 = new Measure(denominator, flour);
        // test whole value
        Assertions.assertEquals(denominator, measure.getCalories());
        Assertions.assertEquals(denominator, measure.getCups());
        Assertions.assertEquals(measure.getCups(), measure2.getCups());
        Assertions.assertTrue(measure.isDry());
        Assertions.assertEquals("2 Cups flour", measure.getName());
        // Test fraction
        measure = new Measure(numerator, denominator, flour);
        Assertions.assertEquals(halfCup, measure.getCalories());
        Assertions.assertEquals(halfCup, measure.getCups());
        Assertions.assertEquals("1/2 Cup flour", measure.getName());
        // test printRecipe
        OutputStream oldOut = System.out;
        System.setOut(new PrintStream("output.txt"));
        measure.printRecipe();
        System.setOut(new PrintStream(oldOut));
        Scanner read = new Scanner(new File("output.txt"));
        StringBuilder sb = new StringBuilder();
        while(read.hasNextLine()) {
            sb.append(read.nextLine()).append("\n");
        }
        Assertions.assertEquals("""
                ====================================================
                1/2 Cup flour
                ====================================================
                Measured ingredient: flour
                Quantity: 1/2 Cup (0.5 Cups)
                Energy: 1 Calories
                                
                ====================================================
                flour
                ====================================================
                Cups: 1 Cups
                Energy: 1 Calories""", sb.toString().trim());
    }


}
