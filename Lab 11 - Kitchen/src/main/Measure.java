/*
 * Course: CSC1110A
 * Fall 2023
 * Lab 11 - Interfaces
 * Name: Victor Barbulescu
 * Created: 11/10/2023
 */
package barbulescuv;

/**
 * Measured ingredient
 */
public class Measure implements Ingredient {

    private final Ingredient baseIngredient;
    private final int numerator;
    private final int denominator;

    /**
     * measure constructor
     * @param numerator numerator of measured fraction
     * @param denominator denominator of measured fraction
     * @param ingredient ingredient being measured
     */
    public Measure(int numerator, int denominator, Ingredient ingredient){
        this.numerator = numerator;
        this.denominator = denominator;
        this.baseIngredient = ingredient;
    }

    /**
     * measure constructor
     * @param numerator numerator of measured fraction
     * @param ingredient ingredient being measured
     */
    public Measure(int numerator, Ingredient ingredient){
        this.baseIngredient = ingredient;
        this.numerator = numerator;
        denominator = 1;
    }

    @Override
    public double getCalories() {
        return ((double) numerator / denominator) * (baseIngredient.getCalories() / baseIngredient.getCups());
    }

    @Override
    public double getCups() {
        return (double)numerator/denominator;
    }

    @Override
    public String getName() {
        return (denominator == 1 ? numerator : numerator + "/"  + denominator) +
                (numerator == 1 ? " Cup " : " Cups ") +
                baseIngredient.getName();
    }

    @Override
    public boolean isDry() {
        return baseIngredient.isDry();
    }

    @Override
    public void printRecipe() {
        System.out.println("====================================================");
        System.out.println(getName());
        System.out.println("====================================================");
        System.out.println("Measured ingredient: " + baseIngredient.getName());
        System.out.println("Quantity: " + formatQuantity());
        System.out.println("Energy: " + Math.round(getCalories()) + " Calories\n");
        baseIngredient.printRecipe();
    }

    /**
     * formats the output with the fitting grammar
     * @return number of cup(s)
     */
    public String formatQuantity(){
        return (denominator == 1 ? numerator : numerator + "/" + denominator)
                + (numerator == 1 ? " Cup" : " Cups")
                + " (" + CUP_FORMAT.format((double) numerator / denominator)+ " Cups)";


    }
}
