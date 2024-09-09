/*
 * Course: CSC1120
 * Spring 2024
 * Lab 5
 * Name: Victor Barbulescu
 */
package barbulescuv;

/**
 * Functional Interface that applies a transformation to an image
 */
public interface Transform {

    /**
     * Apply a transformation to an array of values
     * @param values an array of integer values
     * @return the transformed value
     */
    int apply(int[] values);
}
