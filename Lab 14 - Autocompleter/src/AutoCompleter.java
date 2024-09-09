/*
 * Course: CSC1120
 * Spring 2024
 * Lab 13 - Trie
 * Name: Victor Barbulescu
 */
package barbulescuv;

import java.text.DecimalFormat;
import java.util.Random;

/**
 * An interface defining attributes for auto-completing Strings
 */
public interface AutoCompleter {
    /**
     * Add a string to a List
     * @param word the word to add
     * @return true if added, false if not
     */
    boolean add(String word);

    /**
     * Determine if the List contains an exact word
     * @param target the target word
     * @return true if present, false it not
     */
    boolean exactMatch(String target);

    /**
     * Size of the List
     * @return the size of the List
     */
    int size();

    /**
     * Returns the backing class of the UnorderedList
     * @return String representation of the class
     */
    String getBackingClass();

    /**
     * Searches the list for all words starting with a set prefix.
     * @param prefix the prefix we're searching for
     * @return an array containing all words with the correct prefix.
     */
    String[] allMatches(String prefix);

    /**
     * Formats an inputted number of nanoseconds into
     * an easier to visualize description of the amount of time
     * @param nanoseconds number of nanoseconds
     * @return a formatted string describing the time
     */
    static String format(long nanoseconds) {
        DecimalFormat formatter = new DecimalFormat("##.#");
        final long daysConstant = (long) (8.64 * Math.pow(10, 13));
        final long hoursConstant = (long) (3.6 * Math.pow(10, 12));
        final long minutesConstant = (long) (6 * Math.pow(10, 10));
        final long secondsConstant = (long) (Math.pow(10, 9));
        final long millisecondConstant = (long) (Math.pow(10, 6));
        final long microsecondConstant = 1000;

        if (nanoseconds > daysConstant) {
            int days = (int) (nanoseconds / daysConstant);
            int hours = (int) ((nanoseconds % daysConstant) / hoursConstant);
            int minutes = (int) (((nanoseconds % daysConstant) % hoursConstant) / minutesConstant);

            return days + " day(s) "
                    + (hours == 0 ? "" : hours + " hour(s) ")
                    + (minutes == 0 ? "" : minutes + " minute(s)");
        } else if (nanoseconds > hoursConstant) {
            int hours = (int) (nanoseconds / hoursConstant);
            int minutes = (int) ((nanoseconds % hoursConstant) / minutesConstant);
            int seconds = (int) (((nanoseconds % hoursConstant) % minutesConstant)
                                                                / secondsConstant);
            return hours + " hour(s) "
                    + (minutes == 0 ? "" : minutes + " minute(s) ")
                    + (seconds == 0 ? "" : seconds + " second(s)");
        } else if (nanoseconds > minutesConstant) {
            int minutes = (int) (nanoseconds / minutesConstant);
            double seconds = ((nanoseconds % minutesConstant)) / (double) secondsConstant;

            return minutes + " minute(s) "
                    + (seconds == 0 ? "" : seconds + " second(s)");
        } else if (nanoseconds > secondsConstant) {
            double seconds = (double) nanoseconds / secondsConstant;
            return formatter.format(seconds) + " second(s)";
        } else if (nanoseconds > millisecondConstant) {
            double milliseconds = nanoseconds / (double) millisecondConstant;
            return formatter.format(milliseconds) + " millisecond(s)";
        } else if (nanoseconds > microsecondConstant) {
            double microseconds = nanoseconds / (double) microsecondConstant;
            return formatter.format(microseconds) + " microsecond(s)";
        } else {
            return formatter.format(nanoseconds) + " nanosecond(s)";
        }
    }

    /**
     * Generates a String of random characters
     * at the specified length
     * @param length the desired length of the String
     * @return the random String
     */
    static String getString(int length){
        StringBuilder word = new StringBuilder();
        Random r = new Random();
        for (int i = 0; i < length; i++) {
            char c = (char)(r.nextInt(26) + 'a');
            word.append(c);
        }

        return word.toString();
    }

}
