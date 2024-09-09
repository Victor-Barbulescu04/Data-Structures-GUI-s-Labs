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
import java.util.Map;
import java.util.Set;

/**
 * Trie implementation of the AutoCompleter interface. Stores
 * words in a Tree-type structure where each node has an
 * indeterminate number of children and represents a single character
 * in a word. Words are determined when a node is marked as "True".
 * Then, all the previous characters are concatenated into a word.
 */
public class Trie implements AutoCompleter {

    private final Node root = new Node(false, new ListMap<>());
    private final LinkedList<String> allMatches = new LinkedList<>();
    
    /**
     * Trie constructor
     * @param items a List of words to add to the Trie
     */
    public Trie(List<String> items){
        for (String word : items) {
            add(word);
        }
    }

    private static class Node {
        private boolean endsWord;
        private final Map<Character, Node> edges;

        private Node(boolean endsWord, ListMap<Character, Node> edges){
            this.endsWord = endsWord;
            this.edges = edges;
        }
    }

    @Override
    public boolean add(String word) {
        if (word == null || word.isBlank()){
            throw new IllegalArgumentException();
        }
        return add(word, root);
    }

    private boolean add(String word, Node node){
        // If the recursive call has rendered the word empty
        // and the current node does not mark the end of the
        // word, mark it as the end of the word.
        if (word.isEmpty()){
            if (!node.endsWord){
                node.endsWord = true;
                return true;
            } else {
                return false;
            }
        }

        // First character in the word
        char c = word.charAt(0);
        // If none of the current nodes edges contain the character,
        // put the character into the ListMap of child characters
        if (!node.edges.containsKey(c)){
            node.edges.put(c, new Node(false, new ListMap<>()));
        }

        // Recurse to the next character in the word until all characters
        // have been given a node
        return add(word.substring(1), node.edges.get(c));

    }



    @Override
    public boolean exactMatch(String target) {
        if (target == null || target.isBlank()){
            return false;
        }
        return exactMatch(target, root);
    }

    private boolean exactMatch(String target, Node node){
        // If the recursive call has rendered the word empty,
        // return if the current node marks the end of a word.
        if (target.isEmpty()){
            return node.endsWord;
        }

        // First character in the word
        char c = target.charAt(0);
        // If none of the edges contain this character,
        // the word is not contained and return false.
        if (!node.edges.containsKey(c)){
            return false;
        }

        // Recurse to the next character of the word
        return exactMatch(target.substring(1), node.edges.get(c));
    }

    @Override
    public int size() {
        return size(root);
    }

    private int size(Node node){
        // Break condition
        if (node == null){
            return 0;
        }

        // Current word count of this subroot
        int count = 0;
        // If a node marks the end of a word,
        // the word count increases
        if (node.endsWord){
            count++;
        }

        // For each character connected to this node, add their count
        // recursively
        for (Map.Entry<Character, Node> entry : node.edges.entrySet()) {
            count += size(entry.getValue());
        }

        return count;

    }

    @Override
    public String getBackingClass() {
        return "barbulescuv.listMap";
    }

    @Override
    public String[] allMatches(String prefix) {
        if (prefix == null) {
            return new String[0];
        } else if (prefix.isBlank()) {
            return toArray();
        }

        // Reset the allMatches LinkedList
        allMatches.clear();
        // Call private facing allMatchesMethod
        allMatches(prefix, root, "");
        // Sort allMatches and convert to an array
        Collections.sort(allMatches);
        return allMatches.toArray(String[]::new);
    }

    /**
     * A private facing recursive method for the public allMatches
     * method. This method tracks the prefix, the current node,
     * and the word that's being built up as we traverse the nodes.
     * <p>
     * To find where to start building words, the program first recursively
     * traverses to a point in the tree where the wordBuilder is equal to the
     * prefix. In the program, this renders the prefix empty because we've
     * been doing substring calls on it. Once the prefix is empty, we
     * begin recursive calls over each entry in the current nodes ListMap
     * and, if a node marks the end of the word, adds the wordBuilder to
     * the LinkedList allMatches. The recursion terminates when all nodes
     * have no more edges.
     * <p>
     * This method is O(n) because in order to build every word starting with
     * the prefix, it needs to touch every node along the way. The operations
     * within the method, such as entrySet(), containsKey(), and get() are also
     * technically O(n) operations, but considering that the max number of letters
     * that can be stored in any given edges ListMap is 26, plus a few extra
     * characters here and there, these effectively become constant time
     * operations. There's too little data within the ListMaps to
     * realistically consider them O(n) operations. Thus, we treat them
     * as O(1) and therefore the method overall is O(n)
     *
     * @param prefix the prefix we're searching for
     * @param node the current node we're searching at
     * @param wordBuilder the running word built up through recursion
     */
    private void allMatches(String prefix, Node node, String wordBuilder){
        // Prefix has been found and the parameter is now empty. Every word
        // from now on will start with the prefix.
        if (prefix.isEmpty()){
            // If we're at the end of a word, add the wordBuilder to the allMatches List
            if (node.endsWord){
                allMatches.add(wordBuilder);
            }

            // If the node has edges, check each edge for the above condition.
            // wordBuilder is updated with the key of each entry. When a node
            // no longer has any edges, it stops updating the allMatches List.
            if (!(node.edges == null)){
                for (Map.Entry<Character, Node> entry : node.edges.entrySet()) {
                    allMatches(prefix, entry.getValue(), wordBuilder + entry.getKey());
                }
            }

        } else {
            // Traverse to the designated prefix in the Trie
            char c = prefix.charAt(0);
            // If none of the edges contain a letter in the prefix, then the prefix is not present
            // in the data. Return.
            if (!node.edges.containsKey(c)){
                return;
            }

            // The first character in the prefix has been found, recurse to the next character
            // and update wordBuilder
            allMatches(prefix.substring(1), node.edges.get(c), wordBuilder + c);
        }
    }

    /**
     * Helper method
     * Returns the trie as an array of strings
     * @return an array of strings
     */
    public String[] toArray(){
        LinkedList<String> words = new LinkedList<>();
        toArray(root, "", words);
        return words.toArray(String[]::new);
    }

    private void toArray(Node node, String wordBuilder, LinkedList<String> words){
        // If we're at the end of a word, add the wordBuilder to the words List
        if (node.endsWord){
            words.add(wordBuilder);
        }

        // If the node has edges, check each edge for the above condition.
        // wordBuilder is updated with the key of each entry. When a node
        // no longer has any edges, it stops updating the allMatches List.
        if (!(node.edges == null)){
            for (Map.Entry<Character, Node> entry : node.edges.entrySet()) {
                toArray(entry.getValue(), wordBuilder + entry.getKey(), words);
            }
        }
    }

    /**
     * Helper method
     * Checks if every element in the trie is unique
     * Because of the behavior of trie datastructures,
     * this method should always return true if
     * trie was implemented properly.
     * @return true if unique, false if not
     */
    public boolean areAllUnique(){
        String[] words = toArray();
        Set<String> uniqueStrings = new HashSet<>();

        for (String str : words) {
            if (!uniqueStrings.add(str)) {
                return false; // If we encounter a duplicate, return false
            }
        }

        return true; // If no duplicates were found, return true
    }

}
