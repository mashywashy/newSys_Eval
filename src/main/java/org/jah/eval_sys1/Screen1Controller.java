package org.jah.eval_sys1;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.jah.eval_sys1.backend.*;

import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;

public class Screen1Controller implements Initializable {
    @FXML
    private AnchorPane rootPane;

    @FXML
    private TextField nameField;

    @FXML
    private TextField idNumberField;

    @FXML
    private ComboBox<String> newStudentComboBox;

    @FXML
    private TextField subjectsField;

    @FXML
    private ComboBox<String> programComboBox;

    @FXML
    private Button nextButton;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Initialize ComboBoxes
        newStudentComboBox.getItems().addAll("Yes", "No");
        programComboBox.getItems().addAll("BSIT", "BSA", "BSN", "BSMT");

        // Add listeners
        setupListeners();

        // Set initial state
        subjectsField.setDisable(true);
    }

    private void setupListeners() {
        // Listen for changes in the new student selection
        newStudentComboBox.valueProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                boolean isNewStudent = newVal.equals("Yes");
                subjectsField.setDisable(isNewStudent);

                // Automatically set the field to "0" for new students
                if (isNewStudent) {
                    subjectsField.setText("0");
                } else {
                    subjectsField.clear();
                }
            }
        });

        // Add listener for the Next button
        nextButton.setOnAction(event -> handleNextButton());
    }


    private void handleNextButton() {
        if (!validateInputs()) {
            return;
        }

        try {
            String name = nameField.getText();
            boolean isNewStudent = newStudentComboBox.getValue().equals("Yes");
            String program = programComboBox.getValue();
            int numSubjects = isNewStudent ? 0 : Integer.parseInt(subjectsField.getText());

            String xmlFilePath = getXmlFilePath(program);
            Curriculum curriculum = new Curriculum(xmlFilePath);
            Program selectedProgram = new Program(program, curriculum);
            Student student = new Student(isNewStudent, new HashMap<>(), selectedProgram);
            School school = new School();

            List<Subject> subs = school.getRecommendedSubjects(student);
            int totalUnits = school.calculateTotalUnits(subs, student);

            // Load the correct screen based on student type
            String fxmlFile = isNewStudent ? "recSchedScreen.fxml" : "subjectInput.fxml";
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
            Parent root = loader.load();

            // Pass data to the appropriate controller
            if (isNewStudent) {
                RecSchedScreenController recSchedController = loader.getController();
                recSchedController.setRecommendedSubjects(subs, totalUnits);
                recSchedController.setProgram(program); // Pass program data
            } else {
                SubjectInputController subjectInputController = loader.getController();
                subjectInputController.setNumSubjects(numSubjects);
                subjectInputController.setProgram(program); // Pass program data
            }

            // Switch scenes
            Stage currentStage = (Stage) rootPane.getScene().getWindow();
            currentStage.setScene(new Scene(root));

        } catch (Exception e) {
            showError("Error", "An error occurred while processing: " + e.getMessage());
        }
    }





    private String getXmlFilePath(String program) {
        String basePath = "C:\\Users\\zach\\Documents\\Midterm Datastruct\\Eval_Sys1\\src\\main\\resources\\org\\jah\\eval_sys1\\";
        return switch (program) {
            case "BSIT" -> basePath + "bsit_curriculum.xml";
            case "BSA" -> basePath + "bsa_curriculum.xml";
            case "BSN" -> basePath + "bsn_curriculum.xml";
            case "BSMT" -> basePath + "bsmt_curriculum.xml";
            default -> throw new IllegalArgumentException("Invalid program selected");
        };
    }

    private boolean validateInputs() {
        if (nameField.getText().isEmpty()) {
            showError("Validation Error", "Please enter your name");
            return false;
        }

        if (idNumberField.getText().isEmpty()) {
            showError("Validation Error", "Please enter your ID number");
            return false;
        }

        if (newStudentComboBox.getValue() == null) {
            showError("Validation Error", "Please select whether you are a new student");
            return false;
        }

        if (programComboBox.getValue() == null) {
            showError("Validation Error", "Please select your program");
            return false;
        }

        if (!newStudentComboBox.getValue().equals("Yes") && subjectsField.getText().isEmpty()) {
            showError("Validation Error", "Please enter the number of subjects taken");
            return false;
        }

        return true;
    }

    private void showError(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();

        System.out.println(content);
    }
}