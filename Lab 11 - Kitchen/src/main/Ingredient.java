/*
 * Course: CSC1110A
 * Fall 2023
 * Lab 11 - Interfaces
 * Name: Victor Barbulescu
 * Created: 11/10/2023
 */
package barbulescuv;

import java.text.DecimalFormat;

/**
 * Ingredient interface
 */
public interface Ingredient {
    /**
     * formats cup outputs
     */
    DecimalFormat CUP_FORMAT = new DecimalFormat("##.##");

    /**
     * returns calories
     * @return calories
     */
    double getCalories();

    /**
     * returns cups
     * @return cups
     */
    double getCups();

    /**
     * returns name
     * @return name
     */
    String getName();

    /**
     * returns if the ingredient is dry
     * @return if it's dry
     */
    boolean isDry();

    /**
     * prints the recipe
     */
    void printRecipe();
}
