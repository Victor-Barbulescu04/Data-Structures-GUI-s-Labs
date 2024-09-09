/*
 * Course: CSC1110A
 * Fall 2023
 * Lab 11 - Interfaces
 * Name: Victor Barbulescu
 * Created: 11/10/2023
 */
package barbulescuv;

/**
 * simplest form of the ingredient
 */
public class SimpleIngredient implements Ingredient {

    private final double calories;
    private final double cups;
    private final boolean isDry;
    private final String name;

    /**
     * SimpleIngredient Constructor
     * @param calories number of calories
     * @param cups number of cups
     * @param isDry if it's dry
     * @param name name of the ingredient
     */
    public SimpleIngredient(double calories, double cups, boolean isDry, String name){
        this.calories = calories;
        this.cups = cups;
        this.isDry = isDry;
        this.name = name;
    }

    @Override
    public double getCalories() {
        return calories;
    }

    @Override
    public double getCups() {
        return cups;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public boolean isDry() {
        return isDry;
    }

    @Override
    public void printRecipe() {
        System.out.println("====================================================");
        System.out.println(getName());
        System.out.println("====================================================");
        System.out.println("Cups: " + CUP_FORMAT.format(getCups()) + " Cups");
        System.out.println("Energy: " + (int)getCalories() + " Calories");
        System.out.println();
    }
}