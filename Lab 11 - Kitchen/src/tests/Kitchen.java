/*
 * Course: CSC1110A
 * Fall 2023
 * Lab 11 - Interfaces
 * Name: Victor Barbulescu
 * Created: 11/10/2023
 */

package test;
import barbulescuv.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.Scanner;
/**
 * The driver class: Builds recipes out of ingredients
 */
public class Kitchen {
    private static final Ingredient BUTTER =
            new SimpleIngredient(810, 0.5, false, "Butter");
    private static final Ingredient CREAM =
            new SimpleIngredient(104, 1/8., false, "Cream");
    private static final Ingredient MILK =
            new SimpleIngredient(103, 1, false, "Milk");
    private static final Ingredient SUGAR =
            new SimpleIngredient(773, 1, true, "Sugar");
    private static final Ingredient FLOUR =
            new SimpleIngredient(455, 1, true, "Flour");
    /**
     * The ratio of the dried milk to the original milk's volume
     */
    public static final double DEHYDRATION_FACTOR = 0.2;
    /**
     * The multiplier indicating how the cake's volume expands while baking
     */
    public static final double CAKE_EXPANSION_FACTOR = 1.2;
    public static void main(String[] args) throws FileNotFoundException {
        {
            Scanner in = new Scanner(System.in);
            String command = solicitCommand(in);
            while (!command.equals("quit")) {
                switch (command) {
                    case "dry milk":
                        makeDryMilk();
                        break;
                    case "ice cream":
                        makeIceCream();
                        break;
                    case "cookies":
                        makeCookies();
                        break;
                    case "cake":
                        makeCake();
                        break;
                    default:
                        System.out.println("Unrecognized command: " + command);
                }
                command = solicitCommand(in);
            }
        }
    }
    /**
     * Prints out the main menu and allows the user to choose an
     * option from that menu
     * @param in A scanner which should point to System.in
     * @return The keyword the user typed
     */
    private static String solicitCommand(Scanner in) {
        System.out.println("Please enter an option:");
        System.out.println(" dry milk - Make dehydrate milk");
        System.out.println(" ice cream - Make ice cream");
        System.out.println(" cookies - Make cookies");
        System.out.println(" cake - Make a cake");
        System.out.println(" quit - exit the program");
        return in.nextLine();
    }
    private static void makeDryMilk() {
        Ingredient evaporatedMilk = new BakedIngredient(MILK, DEHYDRATION_FACTOR);
        evaporatedMilk.printRecipe();
    }
    private static void makeIceCream() {
/*
https://cooking.nytimes.com/recipes/1016605-the-only-ice-cream-recipe-youll-ever-
need
2 cups heavy cream
1 cup whole milk
2/3 cup sugar
1/8 teaspoon fine sea salt
6 large egg yolks
*/
        Mix iceCream = new Mix("Ice Cream");
        iceCream.addIngredient(CREAM);
        iceCream.addIngredient(MILK);
        iceCream.addIngredient(SUGAR);
        iceCream.printRecipe();
    }
    private static void makeCookies() {
        Mix cookies = new Mix("Cookies");
// https://joyfoodsunshine.com/the-most-amazing-chocolate-chip-cookies/
        cookies.addIngredient(new Measure(1, BUTTER));
        cookies.addIngredient(new Measure(3, SUGAR));
        cookies.addIngredient(new Measure(3, FLOUR));
        cookies.printRecipe();
    }
    private static void makeCake() {
// https://www.allrecipes.com/recipe/17481/simple-white-cake/
        Mix batter = new Mix("Batter");
        batter.addIngredient(new Measure(1, SUGAR));
        batter.addIngredient(new Measure(1, 2, BUTTER));
        batter.addIngredient(new Measure(3, 2, FLOUR));
        batter.addIngredient(new Measure(1, 2, MILK));
        Mix frosting = new Mix("Frosting");
        frosting.addIngredient(new Measure(4, SUGAR));
        frosting.addIngredient(BUTTER); // two sticks of butter...
        frosting.addIngredient(BUTTER);
        Mix cake = new Mix("Cake");
        cake.addIngredient(new BakedIngredient(batter, CAKE_EXPANSION_FACTOR));
        cake.addIngredient(new Measure(3, 2, frosting));
        cake.printRecipe();
    }
}
