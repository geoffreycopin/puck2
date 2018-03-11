package app;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ConfigurationUI extends Application {
    private final int HEIGHT = 175;
    private Button runButton;
    private TextField programDirField;
    private Button openFileChooserButton;
    private TextField outputDirField;
    private Button openOutputDirChooserButton;
    private TextField outputFileField;

    @Override
    public void start(Stage primaryStage) {
        initUi(primaryStage);
        primaryStage.show();
    }
    

    private void initUi(Stage primaryStage) {
        primaryStage.setMaxHeight(HEIGHT);
        primaryStage.setMinHeight(HEIGHT);
        primaryStage.setMinWidth(300);

        initViews(primaryStage);

        GridPane form = createForm();

        VBox root = new VBox();
        root.setSpacing(12);
        root.setFillWidth(true);
        root.getChildren().addAll(form, runButton);
        root.setPadding(new Insets(0, 16, 0, 16));

        primaryStage.setScene(new Scene(root, 500, HEIGHT));
    }

    private void initViews(Stage primaryStage) {
        initRunButton();
        initOpenFileChooserButton(primaryStage);
        initOutputDirChooser(primaryStage);
        initProgramDirField();
        initOutputDirField();
        initOutputFileField();
    }

    private GridPane createForm() {
        GridPane form = new GridPane();

        form.setHgap(6);
        form.setVgap(8);

        ColumnConstraints column1 = new ColumnConstraints();
        column1.setFillWidth(false);
        ColumnConstraints column2 = new ColumnConstraints();
        column2.setHgrow(Priority.ALWAYS);
        column2.setFillWidth(true);
        form.getColumnConstraints().addAll(column1, column2);

        Label programDirLabel = new Label("Program Directory");
        form.addRow(1, programDirLabel, programDirField, openFileChooserButton);

        Label outputDirLabel = new Label("Output Directory");
        form.addRow(2, outputDirLabel, outputDirField, openOutputDirChooserButton);

        Label outputFileLabel = new Label("Output File");
        form.addRow(3, outputFileLabel, outputFileField);

        return form;
    }

    private void initRunButton() {
        runButton = new Button("Run");
        runButton.setMaxWidth(Double.MAX_VALUE);
        runButton.setOnMouseClicked((e) -> {
            try {
                run();
                displaySuccess();
            } catch (Exception err) {
                displayError(err.toString());
            }
        });
    }

    private void initProgramDirField() {
        programDirField = new TextField();
    }

    private void initOutputDirField() {
        outputDirField = new TextField();
    }

    private void initOutputFileField() {
        outputFileField = new TextField("out.xml");
        GridPane.setColumnSpan(outputFileField, 2);
    }

    private void initOpenFileChooserButton(Stage stage) {
        openFileChooserButton = new Button("...");
        openFileChooserButton.setOnMouseClicked((e) ->
            directoryChooserHandler(programDirField.getText(), programDirField, stage));
    }

    private void initOutputDirChooser(Stage stage) {
        openOutputDirChooserButton = new Button("...");
        openOutputDirChooserButton.setOnMouseClicked((e) ->
            directoryChooserHandler(outputDirField.getText(), outputDirField, stage));
    }

    private void directoryChooserHandler(String initialPath, TextField targetField, Stage stage) {
        if (initialPath.isEmpty()) {
            initialPath = getUserDirectory();
        }

        DirectoryChooser chooser = new DirectoryChooser();
        chooser.setInitialDirectory(new File(initialPath));

        File f = chooser.showDialog(stage);
        if (f != null) {
            targetField.setText(f.getAbsolutePath());
        }
    }

    private String getUserDirectory() {
        Path p = Paths.get("");
        return p.toAbsolutePath().toString();
    }

    private void displaySuccess() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Success");
        alert.setHeaderText("Success");
        alert.showAndWait();
    }

    private void displayError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(message);
        alert.showAndWait();
    }

    private void run() throws Exception {
        checkInputValidity();

        Puck2Runner runner = new Puck2Runner(programDirField.getText());
        runner.run();
        runner.outputToFile(getOutputFilePath());
    }
     
  
    
    private String getOutputFilePath() throws Exception {
        String directory = outputDirField.getText();
        String file = outputFileField.getText();
        
        /* new File(directory+"/output").mkdir();
        return Paths.get(directory+"/output", file).toString();*/
        return Paths.get(directory, file).toString();
    }

    private void checkInputValidity() throws Exception {
        if (! fileExists(programDirField.getText()) || programDirField.getText().isEmpty()) {
            throw new Exception("Program directory does not exists.");
        }

        if (outputFileField.getText().isEmpty()) {
            throw new Exception("Output file cannot be empty.");
        }

        if (outputDirField.getText().isEmpty()) {
            throw new Exception("Output dir cannot be empty");
        }
    }

    private boolean fileExists(String path) {
        File f = new File(path);
        return f.exists();
    }
}
