/*
 * Course: CSC1120
 * Spring 2024
 * Lab 5
 * Name: Victor Barbulescu
 */
package barbulescuv;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;

import javax.imageio.ImageIO;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Scanner;

/**
 * A helper class containing static methods required to
 * read and write ppm files, as well as calculate the
 * mean and median of multiple ppm files
 */
public class MeanImageMedian {
    
    /**
     * Maximum color value
     */
    public static final int MAX_COLOR = 255;

    private static final Transform TRANSFORM_MEAN = values -> (int) Arrays.stream(values)
                                                                  .average()
                                                                  .orElseThrow();

    private static final Transform TRANSFORM_MEDIAN_ODD = values -> Arrays.stream(values)
                                                                 .sorted()
                                                                 .skip(values.length/2)
                                                                 .findFirst()
                                                                 .orElseThrow();

    private static final Transform TRANSFORM_MEDIAN_EVEN = values -> (int) Arrays.stream(values)
                                                                        .sorted()
                                                                        .skip((values.length/2) - 1)
                                                                        .limit(2)
                                                                        .average()
                                                                        .orElseThrow();

    private static final Transform TRANSFORM_MAX = values -> Arrays.stream(values)
                                                            .max()
                                                            .orElseThrow();

    private static final Transform TRANSFORM_MIN = values -> Arrays.stream(values)
                                                            .min()
                                                            .orElseThrow();

    private static final Transform TRANSFORM_RANDOM = values -> Arrays.stream(values)
                                                    .skip((long)(Math.random() * values.length))
                                                    .findFirst()
                                                    .orElseThrow();

    /**
     * returns an image where the specified transformation is applied
     * to the input images to generate the output image.
     * @param images an array of Images
     * @param operation the desired operation (mean, median, max, min, random)
     * @return the product of the operation performed on the images
     * @throws IllegalArgumentException if the operation is an illegal type or
     * if the images array is null or empty
     */
    public static Image generateImage(Image[] images, String operation){
        // If the images array is null or empty, throw new IllegalArgumentException
        if (images == null || images.length == 0){
            throw new IllegalArgumentException();
        }

        // Check if the array is too small
        if (images.length < 2){
            throw new IllegalArgumentException();
        }

        // If any of the images contained are null, throw new IllegalArgumentException
        if (Arrays.stream(images).anyMatch(Objects::isNull)){
            throw new IllegalArgumentException();
        }


        // Apply a unique transformation based on the inputted operation
        switch (operation) {
            case "mean" -> {
                return applyTransform(images, TRANSFORM_MEAN);
            }
            case "median" -> {
                if (images.length % 2 == 0){
                    return applyTransform(images, TRANSFORM_MEDIAN_EVEN);
                } else {
                    return applyTransform(images, TRANSFORM_MEDIAN_ODD);
                }
            }
            case "max" -> {
                return applyTransform(images, TRANSFORM_MAX);
            }
            case "min" -> {
                return applyTransform(images, TRANSFORM_MIN);
            }
            case "random" -> {
                return applyTransform(images, TRANSFORM_RANDOM);
            }
            default -> throw new IllegalArgumentException();
        }
    }

    /**
     * returns an image that is generated by applying the
     * transformation to each component of each pixel in the images.
     * @param images an array of Images
     * @param transformation the desired transformation (a function)
     * @return the product of the transformation performed on the images
     */
    private static Image applyTransform(Image[] images, Transform transformation){

        final int imageHeight = (int) images[0].getHeight();
        final int imageWidth = (int) images[0].getWidth();

        // Check if there are any inconsistencies in image sizes
        // Essentially, if any image isn't equal in dimensions to the first image,
        // throw an exception
        for (Image image : images){
            if (image.getWidth() != imageWidth || image.getHeight() != imageHeight){
                throw new IllegalArgumentException();
            }
        }

        WritableImage output = new WritableImage(imageWidth, imageHeight);

        // For each point in the images, create an array of a, r, g, and b values.
        // Then fill them with their corresponding values from each image at that point.
        // Then apply the passed transformation to those images.
        // Finally, write the transformed pixel to the output.
        for (int row = 0; row < imageHeight; row++) {
            for (int column = 0; column < imageWidth; column++) {

                final int[] r = new int[images.length];
                final int[] g = new int[images.length];
                final int[] b = new int[images.length];
                final int[] a = new int[images.length];

                for (int image = 0; image < images.length; image++) {
                    r[image] = argbToRed(images[image].getPixelReader().getArgb(column, row));
                    g[image] = argbToGreen(images[image].getPixelReader().getArgb(column, row));
                    b[image] = argbToBlue(images[image].getPixelReader().getArgb(column, row));
                    a[image] = argbToAlpha(images[image].getPixelReader().getArgb(column, row));
                }

                int rTransform = transformation.apply(r);
                int gTransform = transformation.apply(g);
                int bTransform = transformation.apply(b);
                int aTransform = transformation.apply(a);

                int transformedColor = argbToInt(aTransform, rTransform, gTransform, bTransform);
                output.getPixelWriter().setArgb(column, row, transformedColor);
            }
        }

        return output;
    }

    /**
     * Calculates the median of all the images passed to the method.
     * <br />
     * Each pixel in the output image is calculated as the median
     * red, green, and blue components of the input images at the same location.
     * @param inputImages Images to be used as input
     * @return An image containing the median color value for each pixel in the input images
     * @throws IllegalArgumentException Thrown if inputImages or any element of inputImages is null,
     * the length of the array is less than two, or  if any of the input images differ in size.
     * @deprecated use {@link #generateImage(Image[], String)} instead
     */
    public static Image calculateMedianImage(Image[] inputImages) {
        // Check for null images
        for (Image image : inputImages) {
            if (image == null){
                throw new IllegalArgumentException();
            }
        }

        // Check if the array is too small
        if (inputImages.length < 2){
            throw new IllegalArgumentException();
        }

        final int imageHeight = (int) inputImages[0].getHeight();
        final int imageWidth = (int) inputImages[0].getWidth();

        // Check if there are any inconsistencies in image sizes
        // Essentially, if any image isn't equal in dimensions to the first image,
        // throw an exception
        for (Image image : inputImages){
            if (image.getWidth() != imageWidth || image.getHeight() != imageHeight){
                throw new IllegalArgumentException();
            }
        }

        final WritableImage outputImage = new WritableImage(imageWidth, imageHeight);

        // CALCULATING MEDIAN

        // Store every pixel value at it's corresponding index in a 3D array,
        // Then take the median of the third dimension to convert to 2D array.
        final int[][][] allPixels = new int[imageHeight][imageWidth][inputImages.length];

        // Populate the allPixels array with pixel values, with the third
        // dimension indicating which image the pixel belongs to.
        // Imagine it as sheets of paper in a filing cabinet
        for (int image = 0; image < inputImages.length; image++) {
            for (int row = 0; row < imageHeight; row++) {
                for (int column = 0; column < imageWidth; column++) {
                    int pixel = inputImages[image].getPixelReader().getArgb(column, row);
                    allPixels[row][column][image] = pixel;
                }
            }
        }

        // take the median of every pixel in the third dimension to create
        // a flat, 2D image that consists of the median of all pixels.
        // It's like combining every sheet of paper in that cabinet into one flat sheet
        for (int row = 0; row < imageHeight; row++) {
            for (int column = 0; column < imageWidth; column++) {
                int medianPixel = medianPixel(allPixels[row][column]);
                outputImage.getPixelWriter().setArgb(column, row, medianPixel);
            }
        }

        return outputImage;
    }

    /**
     * Calculates the mean of all the images passed to the method.
     * <br />
     * Each pixel in the output image is calculated as the average of the
     * red, green, and blue components of the input images at the same location.
     * @param inputImages Images to be used as input
     * @return An image containing the mean color value for each pixel in the input images
     * @throws IllegalArgumentException Thrown if inputImages or any element of inputImages is null,
     * the length of the array is less than two, or  if any of the input images differ in size.
     * @deprecated use {@link #generateImage(Image[], String)} instead
     */
    public static Image calculateMeanImage(Image[] inputImages) {
        // Check for null images
        for (Image image : inputImages) {
            if (image == null){
                throw new IllegalArgumentException();
            }
        }
        // Check if the array is too small
        if (inputImages.length < 2){
            throw new IllegalArgumentException();
        }

        final int imageHeight = (int) inputImages[0].getHeight();
        final int imageWidth = (int) inputImages[0].getWidth();

        // Check if there are any inconsistencies in image sizes
        // Essentially, if any image isn't equal in dimensions to the first image,
        // throw an exception
        for (Image image : inputImages){
            if (image.getWidth() != imageWidth || image.getHeight() != imageHeight){
                throw new IllegalArgumentException();
            }
        }

        WritableImage outputImage = new WritableImage(imageWidth, imageHeight);

        // CALCULATING MEAN

        // Store every pixel value at it's corresponding index in a 3D array,
        // Then take the mean of the third dimension to convert to 2D array.
        final int[][][] allPixels = new int[imageHeight][imageWidth][inputImages.length];

        // Populate the allPixels array with pixel values, with the third
        // dimension indicating which image the pixel belongs to.
        // Imagine it as sheets of paper in a filing cabinet
        for (int image = 0; image < inputImages.length; image++) {
            for (int row = 0; row < imageHeight; row++) {
                for (int column = 0; column < imageWidth; column++) {
                    int pixel = inputImages[image].getPixelReader().getArgb(column, row);
                    allPixels[row][column][image] = pixel;
                }
            }
        }

        // take the mean of every pixel in the third dimension to create
        // a flat, 2D image that consists of the mean of all pixels.
        // It's like combining every sheet of paper in that cabinet into one flat sheet
        for (int row = 0; row < imageHeight; row++) {
            for (int column = 0; column < imageWidth; column++) {
                int meanPixel = meanPixel(allPixels[row][column]);
                outputImage.getPixelWriter().setArgb(column, row, meanPixel);
            }
        }

        return outputImage;
    }

    /**
     * Reads an image in PPM, PNG, or JPG format.
     * The method does not support comments in the image file.
     * @param imagePath the path to the image to be read
     * @return An image object containing the image read from the file.
     *
     * @throws IllegalArgumentException Thrown if imagePath is null.
     * @throws IOException Thrown if the image format is invalid or
     * there was trouble reading the file.
     */
    public static Image readImage(Path imagePath) throws IOException{

        // Check if the imagePath is null
        if (imagePath == null){
            throw new IllegalArgumentException();
        }

        Image image;

        String fileType = getFileType(imagePath);

        // Determines the type of file, then reads the file correctly
        switch (fileType) {
            case "ppm" -> {
                return readPPMImage(imagePath);
            }
            case "png", "jpg" -> image = new Image(new FileInputStream(String.valueOf(imagePath)));
            case "msoe" -> {
                return readMSOEImage(imagePath);
            }
            default -> throw new IllegalArgumentException();
        }

        return image;
    }

    /**
     * Writes an image in PNG.
     * The method does not support comments in the image file.
     * @param imagePath the path to where the file should be written
     * @param image the image containing the pixels to be written to the file
     *
     * @throws IllegalArgumentException Thrown if imagePath is null.
     * @throws IOException Thrown if the image format is invalid or
     * there was trouble reading the file.
     */
    public static void writeImage(Path imagePath, Image image) throws IOException{

        // Checks if the imagePath is null
        if (imagePath == null){
            throw new IllegalArgumentException();
        }

        String fileType = getFileType(imagePath);

        // Writes the file based on which kind of file the desired output is
        switch (fileType) {
            case "ppm" -> writePPMImage(imagePath, image);
            case "msoe" -> writeMSOEImage(imagePath, image);
            case "png" -> ImageIO.write(SwingFXUtils
                          .fromFXImage(image, null), "png", new File(String.valueOf(imagePath)));
            case "jpg" -> ImageIO.write(SwingFXUtils
                          .fromFXImage(image, null), "jpg", new File(String.valueOf(imagePath)));
        }
    }

    /**
     * Reads an image in PPM format. The method only supports the plain PPM (P3) format
     * with 24-bit color and does not support comments in the image file.
     * @param imagePath the path to the image to be read
     * @return An image object containing the image read from the file.
     *
     * @throws IllegalArgumentException Thrown if imagePath is null.
     * @throws IOException Thrown if the image format is invalid or there was
     * trouble reading the file.
     * @throws NoSuchElementException Thrown if the described and actual image sizes disagree
     */
    private static Image readPPMImage(Path imagePath) throws IOException {

        // Is the imagePath null?
        if (imagePath == null){
            throw new IllegalArgumentException();
        }

        final Scanner read = new Scanner(imagePath);

        //Is the file blank?
        if (!read.hasNext()){
            throw new IOException();
        }

        // Move down 1 Line
        read.nextLine();

        // READING THE FILE

        // From the ppm file, read the dimensions of the image
        // Split the line into two strings, one for width and one for height.
        // Position 0 indicates width, position 1 indicates height
        String[] dimensions = read.nextLine().split(" ");
        final int imageWidth = Integer.parseInt(dimensions[0]);
        final int imageHeight = Integer.parseInt(dimensions[1]);

        // declare a new WritableImage with specified dimensions
        final WritableImage image = new WritableImage(imageWidth, imageHeight);

        // skip down to the first line of pixels
        read.nextLine();

        // for each row in the ppm file, each pixel (3 values) is only
        // represented by 1 unit of width, so you can call nextInt() 3 times without
        // breaking the pattern

        /*
        Index:       0          1          2
        Pixels: 241 181 62  32 98 140  197 40 25 ...
         */

        // When the columns are all finished, row iterates and the process restarts
        for (int row = 0; row < imageHeight; row++){
            for(int column = 0; column < imageWidth; column++){
                try {
                    int r = read.nextInt();
                    int g = read.nextInt();
                    int b = read.nextInt();

                    int argb = argbToInt(MAX_COLOR, r, g, b);
                    image.getPixelWriter().setArgb(column, row, argb);
                } catch (NoSuchElementException e){
                    throw new NoSuchElementException();
                }
            }
        }

        return image;
    }

    /**
     * Writes an image in PPM format. The method only supports the plain PPM (P3) format
     * with 24-bit color and does not support comments in the image file.
     * @param imagePath the path to where the file should be written
     * @param image the image containing the pixels to be written to the file
     *
     * @throws IllegalArgumentException Thrown if imagePath is null.
     * @throws IOException Thrown if the image format is invalid or there was
     * trouble reading the file.
     */
    private static void writePPMImage(Path imagePath, Image image) throws IOException {

        // Is the image path or image null?
        if (imagePath == null || image == null){
            throw new IllegalArgumentException();
        }

        // get the width and height of the image
        final int imageWidth = (int)(image.getWidth());
        final int imageHeight = (int)(image.getHeight());

        final BufferedWriter writer = new BufferedWriter(new FileWriter(String.valueOf(imagePath)));

        // Write the header to the file
        writer.write("P3 \n");
        writer.write(imageWidth + " " + imageHeight + " \n");
        writer.write(MAX_COLOR + " \n");

        // WRITING PIXELS TO A FILE

        // Get the single integer color at each position and convert it
        // to strings of R, G, and B values. Then write to the output file
        for (int row = 0; row < imageHeight; row++) {
            for (int column = 0; column < imageWidth; column++) {
                //Convert the color at each index to RGB format
                int color = image.getPixelReader().getArgb(column, row);

                // This can be simplified, but this format makes it clear exactly where
                // each r, g, and b are going in the 3D array
                String r = String.valueOf(argbToRed(color));
                String g = String.valueOf(argbToGreen(color));
                String b = String.valueOf(argbToBlue(color));
                String pixel = r + " " + g + " " + b;

                // Write pixels to the file
                if (column == imageWidth - 1){
                    writer.write(pixel + " \t\n");
                } else {
                    writer.write(pixel + "\t");
                }
            }
        }

        writer.close();
    }

    private static Image readMSOEImage(Path imagePath) throws IOException {
        // Is the imagePath null?
        if (imagePath == null){
            throw new IllegalArgumentException();
        }

        DataInputStream inputStream = new DataInputStream(Files.newInputStream(imagePath));

        // Skip hardcoded value
        inputStream.readInt();

        // Read width and height
        int imageWidth = inputStream.readInt();
        int imageHeight = inputStream.readInt();

        WritableImage image = new WritableImage(imageWidth, imageHeight);

        for (int row = 0; row < imageHeight; row++){
            for(int column = 0; column < imageWidth; column++){
                try {
                    int color = inputStream.readInt();

                    int a = argbToAlpha(color);
                    int r = argbToRed(color);
                    int g = argbToGreen(color);
                    int b = argbToBlue(color);

                    int argb = argbToInt(a, r, g, b);
                    image.getPixelWriter().setArgb(column, row, argb);
                } catch (NoSuchElementException e){
                    throw new NoSuchElementException();
                }
            }
        }

        inputStream.close();

        return image;
    }

    private static void writeMSOEImage(Path imagePath, Image image) throws IOException{

        final int constant = 1297305413;

        // Is the image path or image null?
        if (imagePath == null || image == null){
            throw new IllegalArgumentException();
        }

        // get the width and height of the image
        final int imageWidth = (int)(image.getWidth());
        final int imageHeight = (int)(image.getHeight());

        DataOutputStream outputStream = new DataOutputStream(Files.newOutputStream(imagePath));

        outputStream.writeInt(constant);
        outputStream.writeInt(imageWidth);
        outputStream.writeInt(imageHeight);
        for (int row = 0; row < imageHeight; row++){
            for (int column = 0; column < imageWidth; column++) {
                outputStream.writeInt(image.getPixelReader().getArgb(column, row));
            }
        }

    }

    /**
     * Extract 8-bit Alpha value of color from 32-bit representation of the color in the format
     * described by the INT_ARGB PixelFormat type.
     * @param argb the 32-bit representation of the color
     * @return the 8-bit Alpha value of the color.
     */
    private static int argbToAlpha(int argb) {
        final int bitShift = 24;
        return argb >> bitShift;
    }

    /**
     * Extract 8-bit Red value of color from 32-bit representation of the color in the format
     * described by the INT_ARGB PixelFormat type.
     * @param argb the 32-bit representation of the color
     * @return the 8-bit Red value of the color.
     */
    private static int argbToRed(int argb) {
        final int bitShift = 16;
        final int mask = 0xff;
        return (argb >> bitShift) & mask;
    }

    /**
     * Extract 8-bit Green value of color from 32-bit representation of the color in the format
     * described by the INT_ARGB PixelFormat type.
     * @param argb the 32-bit representation of the color
     * @return the 8-bit Green value of the color.
     */
    private static int argbToGreen(int argb) {
        final int bitShift = 8;
        final int mask = 0xff;
        return (argb >> bitShift) & mask;
    }

    /**
     * Extract 8-bit Blue value of color from 32-bit representation of the color in the format
     * described by the INT_ARGB PixelFormat type.
     * @param argb the 32-bit representation of the color
     * @return the 8-bit Blue value of the color.
     */
    private static int argbToBlue(int argb) {
        final int bitShift = 0;
        final int mask = 0xff;
        return (argb >> bitShift) & mask;
    }

    /**
     * Converts argb components into a single int that represents the argb value of a color.
     * @param a the 8-bit Alpha channel value of the color
     * @param r the 8-bit Red channel value of the color
     * @param g the 8-bit Green channel value of the color
     * @param b the 8-bit Blue channel value of the color
     * @return a 32-bit representation of the color in the
     * format described by the INT_ARGB PixelFormat type.
     */
    private static int argbToInt(int a, int r, int g, int b) {
        final int alphaShift = 24;
        final int redShift = 16;
        final int greenShift = 8;
        final int mask = 0xff;
        return a << alphaShift | ((r & mask) << redShift) | (g & mask) << greenShift | b & mask;
    }

    /**
     * Converts an array of integer pixels to a 2D array of R G B pixels,
     * then takes the median of the R G and B values in each column
     * @param pixels an array of single integer pixel values
     * @return the median of all pixels represented as a single
     * integer value
     */
    private static int medianPixel(int[] pixels){

        /*
                R  G  B  a
       Pixel 1  [] [] [] []
       Pixel 2  [] [] [] []
       Pixel 3  [] [] [] []
       Pixel 4  [] [] [] []

       Take the median of column R, column G, column B, and column a,
       then convert the median R G B and a back to a single int
       using argbToInt()
        */

        // Convert pixels to aRGB format and store in 2D array
        final int[][] rgbPixels = new int[pixels.length][4];

        for (int pixel = 0; pixel < pixels.length; pixel++){
            // Temporary 1D array of a single RGB value
            int[] rgb = new int[4];
            rgb[0] = argbToRed(pixels[pixel]);
            rgb[1] = argbToGreen(pixels[pixel]);
            rgb[2] = argbToBlue(pixels[pixel]);
            rgb[3] = argbToAlpha(pixels[pixel]);

            // fills row
            rgbPixels[pixel] = rgb;
        }

        // Populate and sort the R G B and a pixels into their own arrays
        // The size of the R G B and a arrays are the same as the number of pixels
        // There are as many R values as there are pixels, so we use rgbPixels.length
        final int[] r = new int[rgbPixels.length];
        final int[] g = new int[rgbPixels.length];
        final int[] b = new int[rgbPixels.length];
        final int[] a = new int[rgbPixels.length];

        for (int pixel = 0; pixel < rgbPixels.length; pixel++) {
            for (int color = 0; color < rgbPixels[0].length; color++){

                switch (color) {
                    // Color is 0 (R), so we add to R array
                    case 0 -> r[pixel] = rgbPixels[pixel][0];
                    // Color is 1 (G), so we add to G array
                    case 1 -> g[pixel] = rgbPixels[pixel][1];
                    // Color is 2 (B), so we add to B array
                    case 2 -> b[pixel] = rgbPixels[pixel][2];
                    // Color is 3 (a), so we add to the a array
                    case 3 -> a[pixel] = rgbPixels[pixel][3];
                }

            }
        }

        Arrays.sort(r);
        Arrays.sort(g);
        Arrays.sort(b);
        Arrays.sort(a);

        // Take the median of the R, G, B, and a arrays
        final int medianR = getMedian(r);
        final int medianG = getMedian(g);
        final int medianB = getMedian(b);
        final int medianA = getMedian(a);


        // return the median of the row of pixels
        return argbToInt(medianA, medianR, medianG, medianB);
    }

    /**
     * Converts an array of pixels to a 2D array of R G B pixels,
     * then takes the mean of the R G and B values in each column,
     * @param pixels an array of single integer pixel values
     * @return the mean of all pixels represented as a single
     * integer value
     */
    private static int meanPixel(int[] pixels){

        /*
                R  G  B  a
       Pixel 1  [] [] [] []
       Pixel 2  [] [] [] []
       Pixel 3  [] [] [] []
       Pixel 4  [] [] [] []

       Take the mean of column R, column G, column B, and column a,
       then convert the mean R G B and a back to a single int
       using argbToInt()
        */

        // Convert pixels to aRGB format and store in array
        final int[][] rgbPixels = new int[pixels.length][4];

        for (int pixel = 0; pixel < pixels.length; pixel++){
            int[] rgb = new int[4];
            rgb[0] = argbToRed(pixels[pixel]);
            rgb[1] = argbToGreen(pixels[pixel]);
            rgb[2] = argbToBlue(pixels[pixel]);
            rgb[3] = argbToAlpha(pixels[pixel]);

            // fills row
            rgbPixels[pixel] = rgb;
        }

        // Populate the R G B and a pixels into their own arrays
        // The size of the R G B and a arrays are the same as the number of pixels
        // There are as many R values as there are pixels, so we use rgbPixels.length
        final int[] r = new int[rgbPixels.length];
        final int[] g = new int[rgbPixels.length];
        final int[] b = new int[rgbPixels.length];
        final int[] a = new int[rgbPixels.length];

        for (int pixel = 0; pixel < rgbPixels.length; pixel++) {
            for (int color = 0; color < rgbPixels[0].length; color++){

                switch (color) {
                    // Color is 0 (R), so we add to R array
                    case 0 -> r[pixel] = rgbPixels[pixel][0];
                    // Color is 1 (G), so we add to G array
                    case 1 -> g[pixel] = rgbPixels[pixel][1];
                    // Color is 2 (B), so we add to B array
                    case 2 -> b[pixel] = rgbPixels[pixel][2];
                    // Color is 3 (a), so we add to a array
                    case 3 -> a[pixel] = rgbPixels[pixel][3];
                }

            }
        }

        // Take the mean of the R, G, B, and a arrays
        final int meanR = getMean(r);
        final int meanG = getMean(g);
        final int meanB = getMean(b);
        final int meanA = getMean(a);

        // return the mean of the row of pixels
        return argbToInt(meanA, meanR, meanG, meanB);
    }

    /**
     * Helper method to calculate the mean of all numbers in an array
     * @param numbers an array of integers
     * @return the average of all integers
     */
    private static int getMean(int[] numbers){
        final int totalNumbers = numbers.length;
        int sum = 0;

        for (int number : numbers) {
            sum += number;
        }

        return sum/totalNumbers;
    }

    /**
     * Helper method to calculate the median of all numbers in an array
     * @param numbers an array of integers
     * @return the median of all integers
     */
    private static int getMedian(int[] numbers){
        int median;

        if (numbers.length % 2 == 0) {
            // Divide the two numbers surrounding the mathematical center
            // to get the true median
            median = (numbers[numbers.length / 2] + numbers[numbers.length / 2 - 1]) / 2;
        } else {
            //true median already has a value, so just divide by 2
            median = numbers[numbers.length / 2];
        }

        return median;
    }

    /**
     * Helper method to determine the file type of Path.
     * @param path the path of the file
     * @return a string representation of the file type
     */
    public static String getFileType(Path path){
        String extension = "";
        int i = String.valueOf(path).lastIndexOf('.');
        if (i >= 0) {
            extension = String.valueOf(path).substring(i + 1);
        }

        return extension;
    }

    /**
     * Helper method to convert an arrayList of Images to
     * an array of Images
     * @param imagesList arrayList of Images
     * @return an array of Images
     */
    public static Image[] arrayListToArray(ArrayList<Image> imagesList){
        Image[] output = new Image[imagesList.size()];
        for (int i = 0; i < output.length; i++) {
            output[i] = imagesList.get(i);
        }

        return output;
    }
}
