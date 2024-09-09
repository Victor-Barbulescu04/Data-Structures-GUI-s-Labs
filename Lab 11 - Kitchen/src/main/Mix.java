/*
 * Course: CSC1110A
 * Fall 2023
 * Lab 11 - Interfaces
 * Name: Victor Barbulescu
 * Created: 11/10/2023
 */
package barbulescuv;

import java.util.ArrayList;

/**
 * Mixed ingredient class
 */
public class Mix implements Ingredient {

    private final ArrayList<Ingredient> ingredients;
    private final String name;

    /**
     * mix constructor
     * @param name of mixed ingredient
     */
    public Mix(String name){
        ingredients = new ArrayList<>();
        this.name = name;
    }

    /**
     * adds ingredient to arrayList
     * @param ingredient ingredient being added
     */
    public void addIngredient(Ingredient ingredient){
        ingredients.add(ingredient);
    }

    /**
     * has a dry ingredient
     * @return if it has a dry ingredient
     */
    public boolean hasDryIngredient(){
        for (Ingredient o:ingredients) {
            if(o.isDry()){
                return true;
            }
        }

        return false;
    }

    /**
     * has a wet ingredient
     * @return if it has a wet ingredient
     */
    public boolean hasWetIngredient(){
        for (Ingredient i:ingredients) {
            if(!i.isDry()){
                return true;
            }
        }

        return false;
    }

    @Override
    public double getCalories() {
        double sumCalories = 0;
        for (Ingredient i:ingredients) {
            sumCalories += i.getCalories();
        }

        return sumCalories;
    }

    @Override
    public double getCups() {
        double sumCups = 0;
        for (Ingredient i:ingredients) {
            sumCups += i.getCups();
        }

        return sumCups;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public boolean isDry() {
        return !hasWetIngredient();
    }

    @Override
    public void printRecipe() {
        System.out.println("====================================================");
        System.out.println(getName());
        System.out.println("====================================================");

        System.out.println("Dry Ingredients:");
        for (Ingredient i:ingredients) {
            if(i.isDry()){
                System.out.println(i.getName());
            }
        }
        System.out.println();

        System.out.println("Wet Ingredients:");
        for (Ingredient i:ingredients) {
            if(!i.isDry()){
                System.out.println(i.getName());
            }
        }
        System.out.println();

        System.out.println("Cups: " + CUP_FORMAT.format(getCups()) + " Cups");
        System.out.println("Energy: " + Math.round(getCalories()) + " Calories");

        for (Ingredient i:ingredients) {
            i.printRecipe();
        }


    }
}
