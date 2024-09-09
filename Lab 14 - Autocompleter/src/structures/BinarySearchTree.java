/*
 * Course: CSC1120
 * Spring 2024
 * Lab 14 - HashTable
 * Name: Victor Barbulescu
 */
package barbulescuv.structures;

import barbulescuv.AutoCompleter;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * Binary Search Tree of Strings
 */
public class BinarySearchTree implements AutoCompleter {

    private final TreeSet<String> items;

    /**
     * BinarySearchTree constructor
     * @param items a List of String items
     */
    public BinarySearchTree(List<String> items){
        this.items = new TreeSet<>(items);
    }

    @Override
    public boolean add(String word) {
        if (word == null || word.isBlank()){
            throw new IllegalArgumentException();
        }
        return items.add(word);
    }

    @Override
    public boolean exactMatch(String target) {
        if (target == null || target.isBlank()){
            return false;
        }
        return items.contains(target);
    }

    @Override
    public int size() {
        return items.size();
    }

    @Override
    public String getBackingClass() {
        return items.getClass().getName();
    }

    @Override
    public String[] allMatches(String prefix) {
        if (prefix == null) {
            return new String[0];
        } else if (prefix.isBlank()) {
            return items.toArray(String[]::new);
        }

        // prefix + Character.MAXVALUE is used to ensure that nextPrefix is
        // greater than any word starting with the prefix
        String nextPrefix = prefix + Character.MAX_VALUE;
        // tailSet gives us a view of all values greater than or equal to the prefix
        SortedSet<String> tailSet = items.tailSet(prefix);

        // Find the ceiling of nextPrefix.
        // ceiling returns the smallest element in the set
        // greater than or equal to nextPrefix. This ensures that words
        // strictly starting with the prefix will be included in the matches list.
        // in a nutshell, it's an endpoint in the TreeSet.
        String ceiling = items.ceiling(nextPrefix);

        // If ceiling is not found, it means no word has prefix nextPrefix
        if (ceiling == null) {
            // If no words have prefix prefix, return an empty array
            if (tailSet.isEmpty() || !tailSet.first().startsWith(prefix)) {
                return new String[0];
            }
        }

        // Create sublist containing words starting with prefix
        SortedSet<String> matches = tailSet.headSet(ceiling);

        return matches.toArray(new String[0]);
    }

    /**
     * Helper method
     * Checks if every element in the items list is unique
     * @return true if unique, false if not
     */
    public boolean areAllUnique() {
        Set<String> uniqueStrings = new HashSet<>();

        for (String str : items) {
            if (!uniqueStrings.add(str)) {
                return false; // If we encounter a duplicate, return false
            }
        }

        return true; // If no duplicates were found, return true
    }

    /**
     * Helper method
     * Returns the backing list as an array of strings
     * @return an array of strings
     */
    public String[] toArray() {
        return items.toArray(String[]::new);
    }
}
