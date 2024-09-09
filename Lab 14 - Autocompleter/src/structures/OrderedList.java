/*
 * Course: CSC1120
 * Spring 2024
 * Lab 14 - HashTable
 * Name: Victor Barbulescu
 */
package barbulescuv.structures;

import barbulescuv.AutoCompleter;

import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Set;

/**
 * An ordered list of Strings
 */
public class OrderedList implements AutoCompleter {

    private final List<String> items;

    /**
     * OrderedList constructor
     * @param items a list of String items
     */
    public OrderedList(List<String> items){
        this.items = items;
        Set<String> unique = new HashSet<>(items);
        items.clear();
        items.addAll(unique);

        // Sort items
        Collections.sort(items);
    }

    @Override
    public boolean add(String word) {
        if (word == null || word.isBlank()){
            throw new IllegalArgumentException();
        }

        ListIterator<String> iterator = items.listIterator();

        while (iterator.hasNext()) {
            String nextWord = iterator.next();
            int compare = word.compareTo(nextWord);
            if (compare == 0) {
                // Word already exists in the list, so we don't add it again.
                return false;
            } else if (compare < 0) {
                // Current word is less than the next word,
                // so we add the new word before the next word.
                iterator.previous();
                iterator.add(word);
                return true;
            }
        }

        // If the word is greater than all existing words, add it at the end.
        iterator.add(word);
        return true;
    }

    @Override
    public boolean exactMatch(String target) {
        if (target == null || target.isBlank()){
            return false;
        }

        return Collections.binarySearch(items, target) >= 0;
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

        LinkedList<String> matches = new LinkedList<>();
        int index = Collections.binarySearch(items, prefix);

        // If index < 0, then the prefix doesn't exist in the list

        if (index < 0) {
            index = -index - 1; // Conversion to insertion point
        }

        // Find all words starting with the prefix
        // Search only the words after the insertion point
        // Stop searching once words don't start with the prefix,
        // or we've reached the end of the loop
        while (items.get(index).startsWith(prefix) && index < items.size() - 1){
            matches.add(items.get(index));
            index++;
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
