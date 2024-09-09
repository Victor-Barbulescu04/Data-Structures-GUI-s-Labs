/*
 * Course: CSC1120
 * Spring 2024
 * Lab 14 - HashTable
 * Name: Victor Barbulescu
 */
package barbulescuv.controller;

import barbulescuv.AutoCompleter;
import barbulescuv.structures.BinarySearchTree;
import barbulescuv.structures.HashTable;
import barbulescuv.structures.OrderedList;
import barbulescuv.structures.Trie;
import barbulescuv.structures.UnorderedList;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

/**
 * Controller Class for AutoCompleter GUI
 */
public class AutoCompleterController extends Application {

    private UnorderedList unorderedList = new UnorderedList(readFile(null));
    private OrderedList orderedList = new OrderedList(readFile(null));
    private BinarySearchTree binarySearchTree = new BinarySearchTree(readFile(null));
    private Trie trie = new Trie(readFile(null));
    private HashTable hashTable = new HashTable(readFile(null));

    @FXML
    private TextField searchBar;
    @FXML
    private TextArea queryOutput;
    @FXML
    private TextField unsortedExactTime;
    @FXML
    private TextField unsortedAllMatchTime;
    @FXML
    private TextField sortedExactTime;
    @FXML
    private TextField sortedAllMatchTime;
    @FXML
    private TextField binaryExactTime;
    @FXML
    private TextField binaryAllMatchTime;
    @FXML
    private TextField trieExactTime;
    @FXML
    private TextField trieAllMatchTime;
    @FXML
    private TextField hashExactTime;
    @FXML
    private TextField hashAllMatchTime;


    @Override
    public void start(Stage stage) throws Exception {
        // JavaFX
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass()
                                             .getResource("AutoCompleterControllerFXML.fxml")));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Method that handles updating text outputs when the input is changed
     * @param keyEvent the event that occurs
     */
    public void textChanged(KeyEvent keyEvent) {
        String input = searchBar.getText();

        // Exact match search time
        exactMatchTimes(input);

        // All matches search time
        String[] unorderedMatches = allMatchTimes(input);

        StringBuilder output = new StringBuilder();
        for (String word:unorderedMatches) {
            output.append(word).append("\n");
        }

        queryOutput.setText(output.toString());
    }

    /**
     * Helper method that updates text outputs after
     * measuring the runtime of the exactMatch() method
     * @param input the string input to search for
     */
    private void exactMatchTimes(String input) {

        // Unordered benchmark
        long unorderedExactStartTime = System.nanoTime();
        unorderedList.exactMatch(input);
        long unorderedExactEndTime = System.nanoTime();
        unsortedExactTime.setText(AutoCompleter.format(
                unorderedExactEndTime - unorderedExactStartTime));

        // Ordered benchmark
        long orderedExactStartTime = System.nanoTime();
        orderedList.exactMatch(input);
        long orderedExactEndTime = System.nanoTime();
        sortedExactTime.setText(AutoCompleter.format(orderedExactEndTime - orderedExactStartTime));

        // Binary benchmark
        long binaryExactStartTime = System.nanoTime();
        binarySearchTree.exactMatch(input);
        long binaryExactEndTime = System.nanoTime();
        binaryExactTime.setText(AutoCompleter.format(binaryExactEndTime - binaryExactStartTime));

        // Trie benchmark
        long trieExactStartTime = System.nanoTime();
        trie.exactMatch(input);
        long trieExactEndTime = System.nanoTime();
        trieExactTime.setText(AutoCompleter.format(trieExactEndTime - trieExactStartTime));

        // HashTable benchmark
        long hashExactStartTime = System.nanoTime();
        hashTable.exactMatch(input);
        long hastExactEndTime = System.nanoTime();
        hashExactTime.setText(AutoCompleter.format(hastExactEndTime - hashExactStartTime));

    }

    /**
     * Helper method that updates text outputs after
     * measuring the runtime of the allMatches() method
     * @param input the string input to search for
     * @return an array of all words that start with the
     * input
     */
    private String[] allMatchTimes(String input) {
        // Unordered benchmark
        long unorderedStartTime = System.nanoTime();
        unorderedList.allMatches(input);
        long unorderedEndTime = System.nanoTime();
        unsortedAllMatchTime.setText(AutoCompleter.format(unorderedEndTime - unorderedStartTime));

        // Ordered benchmark
        long orderedStartTime = System.nanoTime();
        orderedList.allMatches(input);
        long orderedEndTime = System.nanoTime();
        sortedAllMatchTime.setText(AutoCompleter.format(orderedEndTime - orderedStartTime));

        // Binary benchmark
        long binaryStartTime = System.nanoTime();
        binarySearchTree.allMatches(input);
        long binaryEndTime = System.nanoTime();
        binaryAllMatchTime.setText(AutoCompleter.format(binaryEndTime - binaryStartTime));

        // Trie benchmark
        long trieStartTime = System.nanoTime();
        trie.allMatches(input);
        long trieEndTime = System.nanoTime();
        trieAllMatchTime.setText(AutoCompleter.format(trieEndTime - trieStartTime));

        // HashTable benchmark
        long hashStartTime = System.nanoTime();
        hashTable.exactMatch(input);
        long hashEndTime = System.nanoTime();
        hashAllMatchTime.setText(AutoCompleter.format(hashEndTime - hashStartTime));

        return hashTable.allMatches(input);
    }

    /**
     * Method for selecting files
     */
    public void buttonPressed(){
        // Declare a new file chooser
        FileChooser fileChooser = new FileChooser();
        // Declare default directory to open
        fileChooser.setInitialDirectory(new File("data"));
        // File manager title
        fileChooser.setTitle("Select Data");

        // Declare a list of selectedFiles
        File selectedFile = fileChooser.showOpenDialog(new Stage());
        if (selectedFile != null){
            ArrayList<String> fileRead = readFile(selectedFile);
            unorderedList = new UnorderedList(fileRead);
            orderedList = new OrderedList(fileRead);
            binarySearchTree = new BinarySearchTree(fileRead);
            trie = new Trie(fileRead);
            hashTable = new HashTable(fileRead);
        }
    }

    /**
     * A helper method that reads a file and
     * converts it to a LinkedList of data
     * @param file the desired file to read. Defaults
     *             to data/words.txt if null
     * @return an ArrayList containing the read words
     * in the file
     */
    private static ArrayList<String> readFile(File file) {
        ArrayList<String> items = null;
        if (file == null){
            try {
                Scanner s = new Scanner(new File("data/words.txt"));
                items = new ArrayList<>();
                while (s.hasNextLine()){
                    items.add(s.nextLine());

                }
                s.close();
            } catch (FileNotFoundException e){
                System.out.println("File not found");
            }
        } else {
            try {
                Scanner s = new Scanner(file);
                items = new ArrayList<>();
                while (s.hasNextLine()){
                    items.add(s.nextLine());

                }
                s.close();
            } catch (FileNotFoundException e){
                System.out.println("File not found");
            }
        }

        return items;
    }
}
