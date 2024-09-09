/*
 * Course: CSC1120
 * Spring 2024
 * Lab 14 - HashTable
 * Name: Victor Barbulescu
 */
package barbulescuv.tests;

import barbulescuv.AutoCompleter;
import barbulescuv.structures.BinarySearchTree;
import barbulescuv.structures.HashTable;
import barbulescuv.structures.OrderedList;
import barbulescuv.structures.Trie;
import barbulescuv.structures.UnorderedList;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Tests the autocompleter
 */
class AutoCompleterTest {
    private UnorderedList unorderedList;
    private OrderedList orderedList;
    private BinarySearchTree binarySearchTree;
    private Trie trie;
    private HashTable hashTable;

    @BeforeEach
    void setUp(){
        // Add a random set of words
        List<String> words = new ArrayList<>(Arrays.asList("apple", "banana", "orange", "grape",
                                                            "kiwi", "pineapple", "strawberry",
                                                            "melon", "peach", "pear"));

        // add words with the prefix "ap"
        words.addAll(Arrays.asList("apple", "apricot", "avocado", "apartment", "ape",
                                    "apple", "apricot", "apron", "ape", "apple",
                                    "apex", "appetite", "apology", "apple", "apex"));

        unorderedList = new UnorderedList(words);
        orderedList = new OrderedList(words);
        binarySearchTree = new BinarySearchTree(words);
        trie = new Trie(words);
        hashTable = new HashTable(words);
    }

    @Test
    void testDuplicates(){
        assertTrue(unorderedList.areAllUnique());
        assertTrue(orderedList.areAllUnique());
        assertTrue(binarySearchTree.areAllUnique());
        assertTrue(trie.areAllUnique());
        assertTrue(hashTable.areAllUnique());
    }

    @Test
    void testAdd(){
        // Test UnorderedList
        assertThrows(IllegalArgumentException.class, () -> unorderedList.add(null));
        assertThrows(IllegalArgumentException.class, () -> unorderedList.add(""));
        assertThrows(IllegalArgumentException.class, () -> unorderedList.add("  "));
        assertTrue(unorderedList.add("potato"));
        assertFalse(unorderedList.add("potato"));

        // Test OrderedList
        assertThrows(IllegalArgumentException.class, () -> orderedList.add(null));
        assertThrows(IllegalArgumentException.class, () -> orderedList.add(""));
        assertThrows(IllegalArgumentException.class, () -> orderedList.add("  "));
        assertTrue(orderedList.add("potato"));
        assertFalse(orderedList.add("potato"));

        // Test BinarySearchTree
        assertThrows(IllegalArgumentException.class, () -> binarySearchTree.add(null));
        assertThrows(IllegalArgumentException.class, () -> binarySearchTree.add(""));
        assertThrows(IllegalArgumentException.class, () -> binarySearchTree.add("  "));
        assertTrue(binarySearchTree.add("potato"));
        assertFalse(binarySearchTree.add("potato"));

        // Test Trie
        assertThrows(IllegalArgumentException.class, () -> trie.add(null));
        assertThrows(IllegalArgumentException.class, () -> trie.add(""));
        assertThrows(IllegalArgumentException.class, () -> trie.add("  "));
        assertTrue(trie.add("potato"));
        assertFalse(trie.add("potato"));

        // Test HashTable
        assertThrows(IllegalArgumentException.class, () -> hashTable.add(null));
        assertThrows(IllegalArgumentException.class, () -> hashTable.add(""));
        assertThrows(IllegalArgumentException.class, () -> hashTable.add("  "));
        assertTrue(hashTable.add("potato"));
        assertFalse(hashTable.add("potato"));
    }

    @Test
    void testExactMatch(){
        // Test UnorderedList
        assertFalse(unorderedList.exactMatch(null));
        assertFalse(unorderedList.exactMatch(""));
        assertFalse(unorderedList.exactMatch("  "));

        assertFalse(unorderedList.exactMatch("Cornucopia"));
        assertFalse(unorderedList.exactMatch("avocado."));
        assertTrue(unorderedList.exactMatch("avocado"));

        // Test OrderedList
        assertFalse(orderedList.exactMatch(null));
        assertFalse(orderedList.exactMatch(""));
        assertFalse(orderedList.exactMatch("  "));

        assertFalse(orderedList.exactMatch("Cornucopia"));
        assertFalse(orderedList.exactMatch("avocado."));
        assertTrue(orderedList.exactMatch("avocado"));

        //Test BinarySearchTree
        assertFalse(binarySearchTree.exactMatch(null));
        assertFalse(binarySearchTree.exactMatch(""));
        assertFalse(binarySearchTree.exactMatch("  "));

        assertFalse(binarySearchTree.exactMatch("Cornucopia"));
        assertFalse(binarySearchTree.exactMatch("avocado."));
        assertTrue(binarySearchTree.exactMatch("avocado"));

        // Test Trie
        assertFalse(trie.exactMatch(null));
        assertFalse(trie.exactMatch(""));
        assertFalse(trie.exactMatch("  "));

        assertFalse(trie.exactMatch("Cornucopia"));
        assertFalse(trie.exactMatch("avocado."));
        assertTrue(trie.exactMatch("avocado"));

        // Test HashTable
        assertFalse(hashTable.exactMatch(null));
        assertFalse(hashTable.exactMatch(""));
        assertFalse(hashTable.exactMatch("  "));

        assertFalse(hashTable.exactMatch("Cornucopia"));
        assertFalse(hashTable.exactMatch("avocado."));
        assertTrue(hashTable.exactMatch("avocado"));
    }

    @Test
    void testSize(){

        final int initialSize = 18;

        // Test UnorderedList
        int size1 = initialSize;
        Assertions.assertEquals(size1, unorderedList.size());
        unorderedList.add("test");
        Assertions.assertEquals(++size1, unorderedList.size());

        // Test OrderedList
        int size2 = initialSize;
        Assertions.assertEquals(++size2, orderedList.size());
        orderedList.add("test2");
        Assertions.assertEquals(++size2, orderedList.size());

        //Test BinarySearchTree
        int size3 = initialSize;
        Assertions.assertEquals(size3, binarySearchTree.size());
        binarySearchTree.add("test3");
        Assertions.assertEquals(++size3, binarySearchTree.size());

        //Test Trie
        int size4 = initialSize;
        Assertions.assertEquals(size4, trie.size());
        trie.add("test4");
        Assertions.assertEquals(++size4, trie.size());

        //Test HashTable
        int size5 = initialSize;
        Assertions.assertEquals(size5, hashTable.size());
        hashTable.add("test5");
        Assertions.assertEquals(++size5, hashTable.size());

    }

    @Test
    void testBackingClass(){
        assertEquals("java.util.ArrayList", unorderedList.getBackingClass());
        assertEquals("java.util.ArrayList", orderedList.getBackingClass());
        assertEquals("java.util.TreeSet", binarySearchTree.getBackingClass());
        assertEquals("barbulescuv.listMap", trie.getBackingClass());
        assertEquals("java.util.HashSet", hashTable.getBackingClass());
    }

    @Test
    void testAllMatches(){
        // Test UnorderedList

        // Test for special cases
        assertArrayEquals(unorderedList.toArray(), unorderedList.allMatches(""));
        assertArrayEquals(unorderedList.toArray(), unorderedList.allMatches(" "));
        assertArrayEquals(new String[0], unorderedList.allMatches(null));

        // Test for the prefix "ap"
        LinkedList<String> apPrefixes1 = new LinkedList<>();
        String[] listArray1 = unorderedList.toArray();
        for (String string : listArray1) {
            if (string.startsWith("ap")) {
                apPrefixes1.add(string);
            }
        }
        String[] apPrefixesArray1 = apPrefixes1.toArray(String[]::new);

        assertArrayEquals(apPrefixesArray1, unorderedList.allMatches("ap"));

        // Test OrderedList

        // Test for special cases
        assertArrayEquals(orderedList.toArray(), orderedList.allMatches(""));
        assertArrayEquals(orderedList.toArray(), orderedList.allMatches(" "));
        assertArrayEquals(new String[0], orderedList.allMatches(null));

        // Test for the prefix "ap"
        LinkedList<String> apPrefixes2 = new LinkedList<>();
        String[] listArray2 = orderedList.toArray();
        for (String string : listArray2) {
            if (string.startsWith("ap")) {
                apPrefixes2.add(string);
            }
        }
        String[] apPrefixesArray2 = apPrefixes2.toArray(String[]::new);

        assertArrayEquals(apPrefixesArray2, orderedList.allMatches("ap"));

        // Test BinarySearchTree

        // Test for special cases
        assertArrayEquals(binarySearchTree.toArray(), binarySearchTree.allMatches(""));
        assertArrayEquals(binarySearchTree.toArray(), binarySearchTree.allMatches(" "));
        assertArrayEquals(new String[0], binarySearchTree.allMatches(null));

        // Test for the prefix "ap"
        LinkedList<String> apPrefixes3 = new LinkedList<>();
        String[] listArray3 = binarySearchTree.toArray();
        for (String string : listArray3) {
            if (string.startsWith("ap")) {
                apPrefixes3.add(string);
            }
        }
        String[] apPrefixesArray3 = apPrefixes3.toArray(String[]::new);

        assertArrayEquals(apPrefixesArray3, binarySearchTree.allMatches("ap"));

        // Test Trie

        // Test for special cases
        assertArrayEquals(trie.toArray(), trie.allMatches(""));
        assertArrayEquals(trie.toArray(), trie.allMatches(" "));
        assertArrayEquals(new String[0], trie.allMatches(null));

        // Test for the prefix "ap"
        LinkedList<String> apPrefixes4 = new LinkedList<>();
        String[] listArray4 = trie.toArray();
        for (String string : listArray4) {
            if (string.startsWith("ap")) {
                apPrefixes4.add(string);
            }
        }
        Collections.sort(apPrefixes4);
        String[] apPrefixesArray4 = apPrefixes4.toArray(String[]::new);
        System.out.println(Arrays.toString(apPrefixesArray4));

        assertArrayEquals(apPrefixesArray4, trie.allMatches("ap"));

        // Test HashTable

        // Test for special cases
        assertArrayEquals(hashTable.toArray(), hashTable.allMatches(""));
        assertArrayEquals(hashTable.toArray(), hashTable.allMatches(" "));
        assertArrayEquals(new String[0], hashTable.allMatches(null));

        // Test for the prefix "ap"
        LinkedList<String> apPrefixes5 = new LinkedList<>();
        String[] listArray5 = trie.toArray();
        for (String string : listArray5) {
            if (string.startsWith("ap")) {
                apPrefixes5.add(string);
            }
        }
        Collections.sort(apPrefixes5);
        String[] apPrefixesArray5 = apPrefixes5.toArray(String[]::new);
        System.out.println(Arrays.toString(apPrefixesArray5));

        // Just a way to sort the output, doesn't affect what is returned by the allMatches method
        ArrayList<String> allMatchesTemp = new ArrayList<>(List.of(hashTable.allMatches("ap")));
        Collections.sort(allMatchesTemp);

        assertArrayEquals(apPrefixesArray5, allMatchesTemp.toArray());

    }

    @Test
    void testNanoseconds(){
        final long test1 = 10000000000000L;
        final long test2 = 720000000000000L;
        final long test3 = 4320000000000000L;
        final long test4 = 483729104758234L;
        final long test5 = 2575300000000L;
        final long test6 = 18800000000L;
        final long test7 = 998800000;
        final long test8 = 318800L;
        final long test9 = 7L;

        Assertions.assertEquals("2 hour(s) 46 minute(s) 40 second(s)", AutoCompleter.format(test1));
        assertEquals("8 day(s) 8 hour(s) ", AutoCompleter.format(test2));
        assertEquals("50 day(s) ", AutoCompleter.format(test3));
        assertEquals("5 day(s) 14 hour(s) 22 minute(s)", AutoCompleter.format(test4));
        assertEquals("42 minute(s) 55.3 second(s)", AutoCompleter.format(test5));
        assertEquals("18.8 second(s)", AutoCompleter.format(test6));
        assertEquals("998.8 millisecond(s)", AutoCompleter.format(test7));
        assertEquals("318.8 microsecond(s)", AutoCompleter.format(test8));
        assertEquals("7 nanosecond(s)", AutoCompleter.format(test9));
    }

}

/*
  Discussion: What method did you find most difficult to test? Why?

  The most challenging to test for me was the allMatches method. Although the
  format method was more tedious to test, the logic behind it is ultimately super basic.
  AllMatches required me to make an array of the words starting with the prefix "ap", and
  then compare that array to the returned array from the allMatches method. There was also
  an error in my if-logic within the method that I found during testing and had to go back
  and resolve.
*/
