/*
 * Course: CSC1120
 * Spring 2024
 * Lab 14 - HashTable
 * Name: Victor Barbulescu
 */
package barbulescuv.structures;

import barbulescuv.AutoCompleter;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * An unordered list of Strings
 */
public class UnorderedList implements AutoCompleter {

    private final List<String> items;

    /**
     * UnorderedList constructor
     * @param items a list of String items
     */
    public UnorderedList(List<String> items) {
        this.items = items;
        Set<String> unique = new HashSet<>(items);
        items.clear();
        items.addAll(unique);
    }

    @Override
    public boolean add(String word) {
        if (word == null || word.isBlank()){
            throw new IllegalArgumentException();
        }

        if (!items.contains(word)){
            items.add(word);
            return true;
        }

        return false;
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
        if (prefix == null){
            return new String[0];
        } else if (prefix.isBlank()){
            return items.toArray(String[]::new);
        }

        List<String> matches = new LinkedList<>();
        for (String word:items) {
            if (word.startsWith(prefix)){
                matches.add(word);
            }
        }

        return matches.toArray(String[]::new);
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
        String[] array = new String[items.size()];

        for (int i = 0; i < items.size(); i++) {
            array[i] = items.get(i);
        }

        return array;
    }

}
