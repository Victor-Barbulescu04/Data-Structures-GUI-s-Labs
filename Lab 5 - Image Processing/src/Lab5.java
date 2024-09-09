/*
 * Course: CSC1120
 * Spring 2024
 * Lab 5
 * Name: Victor Barbulescu
 */
package barbulescuv;

import javafx.application.Application;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

/**
 * Lab2 Driver class. This class validates user inputs for the
 * desired operation and the images passed into it.
 * <p>
 * The user can request for as many files as they'd like to have
 * the desired operation performed on them.
 */
public class Lab5 extends Application {

    @FXML
    private HBox inputImageBox;
    @FXML
    private ImageView outputImageView;
    @FXML
    private Label debugLabel;

    private final Alert alert = new Alert(Alert.AlertType.ERROR);

    private final ArrayList<Image> imagesArray = new ArrayList<>();
    private Image outputImage;


    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass()
                                .getResource("lab5gui.fxml")));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Adds an image or images to the list of images
     */
    @FXML
    public void addImage() {
        // Declare a new file chooser
        FileChooser fileChooser = new FileChooser();
        // Declare default directory to open
        fileChooser.setInitialDirectory(new File("images"));
        // File manager title
        fileChooser.setTitle("Select Desired Images");

        // Declare a list of selectedFiles
        List<File> selectedFiles = fileChooser.showOpenMultipleDialog(new Stage());
        // Read each selected file and add to an arrayList of images
        if (selectedFiles != null){
            for (File selected : selectedFiles) {
                try {
                    Image image = MeanImageMedian.readImage(selected.toPath());
                    imagesArray.add(image);
                    inputImageBox.getChildren()
                                 .add(addInputImageToDisplay(image, selected.getName()));
                } catch (IOException e){
                    // Blank File
                    alert.setHeaderText("ERROR");
                    alert.setContentText("Unable to read " + selected.getName() + "!");
                    alert.showAndWait();
                } catch (NoSuchElementException e){
                    // PPM or MSOE file with faulty dimensions
                    alert.setHeaderText("ERROR");
                    alert.setContentText(selected.getName()
                                        + " Described dimensions disagree with actual!");
                    alert.showAndWait();
                } catch (IllegalArgumentException e){
                    // Illegal file
                    alert.setHeaderText("ERROR");
                    alert.setContentText("Illegal file type!");
                    alert.showAndWait();
                }
            }
        }

    }

    /**
     * Takes the mean of the images
     */
    @FXML
    public void mean() {
        try {
            // If there are too few images, throw exception
            if (imagesArray.size() < 2){
                throw new IllegalStateException();
            }

            // Convert arrayList of images to array of images
            Image[] images = MeanImageMedian.arrayListToArray(imagesArray);
            // Create the output image
            outputImage = MeanImageMedian.generateImage(images, "mean");
            // Show the output image
            outputImageView.setImage(outputImage);

            // Update debug label
            debugLabel.setTextFill(Color.GREEN);
            debugLabel.setText("Successfully Averaged!");
        } catch (IllegalArgumentException e){
            alert.setHeaderText("ERROR");
            alert.setContentText("Invalid argument or images not equal in size!");
            alert.showAndWait();
        } catch (IllegalStateException e){
            alert.setHeaderText("ERROR");
            alert.setContentText("Too few arguments!");
            alert.showAndWait();
        }
    }

    /**
     * takes the median of the images
     */
    @FXML
    public void median() {
        // If there are too few images, throw exception
        try {
            if (imagesArray.size() < 2){
                throw new IllegalStateException();
            }

            // Convert arrayList of images to array of images
            Image[] images = MeanImageMedian.arrayListToArray(imagesArray);
            // Create the output image
            outputImage = MeanImageMedian.generateImage(images, "median");
            // Show the output image
            outputImageView.setImage(outputImage);

            // Update debug label
            debugLabel.setTextFill(Color.GREEN);
            debugLabel.setText("Successfully Medianized!");
        } catch (IllegalArgumentException e){
            alert.setHeaderText("ERROR");
            alert.setContentText("Invalid argument or images not equal in size!");
            alert.showAndWait();
        } catch (IllegalStateException e){
            alert.setHeaderText("ERROR");
            alert.setContentText("Too few arguments!");
            alert.showAndWait();
        }
    }

    /**
     * Takes the maximum of all inputted images
     */
    @FXML
    public void max() {
        try {
            if (imagesArray.size() < 2){
                throw new IllegalStateException();
            }

            // Convert arrayList of images to array of images
            Image[] images = MeanImageMedian.arrayListToArray(imagesArray);
            // Create the output image
            outputImage = MeanImageMedian.generateImage(images, "max");
            // Show the output image
            outputImageView.setImage(outputImage);

            // Update debug label
            debugLabel.setTextFill(Color.GREEN);
            debugLabel.setText("Successfully Maximized!");
        } catch (IllegalArgumentException e){
            alert.setHeaderText("ERROR");
            alert.setContentText("Invalid argument or images not equal in size!");
            alert.showAndWait();
        } catch (IllegalStateException e){
            alert.setHeaderText("ERROR");
            alert.setContentText("Too few arguments!");
            alert.showAndWait();
        }
    }

    /**
     * Takes the minimum of all inputted images
     */
    @FXML
    public void min() {
        try {
            if (imagesArray.size() < 2){
                throw new IllegalStateException();
            }

            // Convert arrayList of images to array of images
            Image[] images = MeanImageMedian.arrayListToArray(imagesArray);
            // Create the output image
            outputImage = MeanImageMedian.generateImage(images, "min");
            // Show the output image
            outputImageView.setImage(outputImage);

            // Update debug label
            debugLabel.setTextFill(Color.GREEN);
            debugLabel.setText("Successfully Minimized!");
        } catch (IllegalArgumentException e){
            alert.setHeaderText("ERROR");
            alert.setContentText("Invalid argument or images not equal in size!");
            alert.showAndWait();
        } catch (IllegalStateException e){
            alert.setHeaderText("ERROR");
            alert.setContentText("Too few arguments!");
            alert.showAndWait();
        }
    }

    /**
     * Randomizes the pixels in all the inputted images
     */
    @FXML
    public void random() {
        try {
            if (imagesArray.size() < 2){
                throw new IllegalStateException();
            }

            // Convert arrayList of images to array of images
            Image[] images = MeanImageMedian.arrayListToArray(imagesArray);
            // Create the output image
            outputImage = MeanImageMedian.generateImage(images, "random");
            // Show the output image
            outputImageView.setImage(outputImage);

            // Update debug label
            debugLabel.setTextFill(Color.GREEN);
            debugLabel.setText("Successfully Randomized!");
        } catch (IllegalArgumentException e){
            alert.setHeaderText("ERROR");
            alert.setContentText("Invalid argument or images not equal in size!");
            alert.showAndWait();
        } catch (IllegalStateException e){
            alert.setHeaderText("ERROR");
            alert.setContentText("Too few arguments!");
            alert.showAndWait();
        }
    }

    /**
     * Saves the output image to a file
     */
    @FXML
    public void saveAs() {
        // Declare a new FileChooser
        FileChooser fileChooser = new FileChooser();
        // File manager title
        fileChooser.setTitle("Save");
        // Set save file type
        FileChooser.ExtensionFilter png = new FileChooser.ExtensionFilter("PNG", "*.png");
        FileChooser.ExtensionFilter jpg = new FileChooser.ExtensionFilter("JPG", "*.jpg");
        FileChooser.ExtensionFilter ppm = new FileChooser.ExtensionFilter("PPM", "*.ppm");
        FileChooser.ExtensionFilter msoe = new FileChooser.ExtensionFilter("MSOE", "*.msoe");
        fileChooser.getExtensionFilters().addAll(png, jpg, ppm, msoe);

        // Set initial directory to access
        fileChooser.setInitialDirectory(new File("images"));

        // Open file explorer save dialog and save to a file
        if (outputImage != null) {
            File output = fileChooser.showSaveDialog(new Stage());
            if (output != null){
                switch (MeanImageMedian.getFileType(output.toPath())) {
                    case "png" ->{
                        try {
                            ImageIO.write(SwingFXUtils.fromFXImage(outputImage,
                                                                null),
                                                                "png", output);
                            debugLabel.setTextFill(Color.GREEN);
                            debugLabel.setText("Image saved successfully!");
                        } catch (IOException e) {
                            alert.setHeaderText("ERROR");
                            alert.setContentText("Unable to save image!");
                            alert.showAndWait();
                        }
                    }
                    case "jpg" ->{
                        try {
                            ImageIO.write(SwingFXUtils.fromFXImage(outputImage,
                                                                null),
                                                                "jpg", output);
                            debugLabel.setTextFill(Color.GREEN);
                            debugLabel.setText("Image saved successfully!");
                        } catch (IOException e) {
                            alert.setHeaderText("ERROR");
                            alert.setContentText("Unable to save image!");
                            alert.showAndWait();
                        }
                    }
                    case "ppm", "msoe" ->{
                        try {
                            MeanImageMedian.writeImage(output.toPath(), outputImage);
                            debugLabel.setTextFill(Color.GREEN);
                            debugLabel.setText("Image saved successfully!");
                        } catch (IOException e){
                            alert.setHeaderText("ERROR");
                            alert.setContentText("Unable to save image!");
                            alert.showAndWait();
                        }
                    }
                }
            }
        } else {
            alert.setHeaderText("ERROR");
            alert.setContentText("No image to Save!");
            alert.showAndWait();
        }
    }

    /**
     * Adds an inputted image to the display window at the top of the screen
     * @param image an input image
     * @param fileName the name of image
     * @return a VBox containing the images name, a picture, and a button to remove it
     */
    public VBox addInputImageToDisplay(Image image, String fileName){
        // Sizing constants
        final int padding = 5;
        final int spacing = 10;
        final int prefHeight = 63;
        final int prefWidth = 112;

        // Declare a new VBox and its attributes
        VBox vBox = new VBox();
        vBox.setPadding(new Insets(padding, padding, padding, padding));
        vBox.setSpacing(spacing);

        // Declare a new Label containing the file name
        Label name = new Label(fileName + " ("
                                           + (int)image.getWidth() + ", "
                                           + (int)image.getHeight() + ")");
        name.setPrefWidth(Region.USE_COMPUTED_SIZE);

        // Declare a new ImageView and its attributes
        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(prefHeight);
        imageView.setFitWidth(prefWidth);

        // Declare the remove button and its functionality
        Button removeButton = new Button("Remove");
        removeButton.setPrefWidth(prefWidth);
        removeButton.setOnAction(actionEvent -> {
            imagesArray.remove(image);
            inputImageBox.getChildren().remove(vBox);
        });

        // Add nodes to parent and return a completed VBox
        vBox.getChildren().addAll(name, imageView, removeButton);
        return vBox;
    }


}
