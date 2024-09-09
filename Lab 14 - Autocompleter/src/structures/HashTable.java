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

/**
 * HashTable Tree of Strings
 */
public class HashTable implements AutoCompleter {

    private final HashSet<String> items;

    /**
     * HashTable constructor
     * @param items a List of String items
     */
    public HashTable(List<String> items) {
        this.items = new HashSet<>(items);
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

        List<String> list = items.stream().filter(x -> x.startsWith(prefix)).toList();
        return list.toArray(String[]::new);
    }

    /**
     * Helper method.
     * Checks if every element in the items hashSet is unique
     * This should always be true due to the nature of hashSets,
     * but the method exists to fit the testing precedent established
     * in the AutoCompleterTest class.
     * @return true if unique, false if not
     */
    public boolean areAllUnique(){
        Set<String> uniqueStrings = new HashSet<>();

        for (String str : items) {
            if (!uniqueStrings.add(str)) {
                return false; // If we encounter a duplicate, return false
            }
        }

        return true; // If no duplicates were found, return true
    }

    /**
     * Helper method.
     * Returns the backing list as an array of strings
     * @return an array of strings
     */
    public String[] toArray() {
        return items.toArray(String[]::new);
    }
}
