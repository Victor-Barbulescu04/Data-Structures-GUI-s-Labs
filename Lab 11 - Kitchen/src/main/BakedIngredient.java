/*
 * Course: CSC1110A
 * Fall 2023
 * Lab 11 - Interfaces
 * Name: Victor Barbulescu
 * Created: 11/10/2023
 */
package barbulescuv;

/**
 * Baked Ingredient Class
 */
public class BakedIngredient implements Ingredient {

    private final Ingredient baseIngredient;
    private final double expansionFactor;

    public BakedIngredient(Ingredient baseIngredient, double expansionFactor){
        this.baseIngredient = baseIngredient;
        this.expansionFactor = expansionFactor;

    }

    @Override
    public double getCalories() {
        return baseIngredient.getCalories();
    }

    @Override
    public double getCups() {
        return baseIngredient.getCups() * expansionFactor;
    }

    @Override
    public String getName() {
        return "Baked " + baseIngredient.getName();
    }

    @Override
    public boolean isDry() {
        return true;
    }

    @Override
    public void printRecipe() {
        System.out.println("====================================================");
        System.out.println(getName());
        System.out.println("====================================================");
        System.out.println("Ingredient to be baked: " + baseIngredient.getName());
        System.out.println("Cups: " + CUP_FORMAT.format(getCups()) + " Cups");
        System.out.println("Energy: " + (int)getCalories() + " Calories\n");
        baseIngredient.printRecipe();
        System.out.println();

    }
}
