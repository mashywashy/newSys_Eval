// SubjectInputController.java
package org.jah.eval_sys1;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.jah.eval_sys1.backend.Subject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.*;

public class SubjectInputController {

    @FXML
    private VBox subjectsContainer;

    @FXML
    private Button submitButton;

    private int numSubjects;
    private String program;
    private List<Subject> enteredSubjects = new ArrayList<>();
    private Set<String> subjectCodes = new HashSet<>();

    public void setNumSubjects(int numSubjects) {
        this.numSubjects = numSubjects;
    }

    public void setProgram(String program) {
        this.program = program;
        System.out.println("Program Set: " + program);
        loadSubjectData();
        loadSubjectFields();
    }

    @FXML
    public void initialize() {
        submitButton.setOnAction(event -> handleSubmit());
    }

    private void loadSubjectData() {
        try {
            String xmlPath = getXmlFilePath(program);
            File xmlFile = new File(xmlPath);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(xmlFile);
            doc.getDocumentElement().normalize();

            NodeList subjectNodes = doc.getElementsByTagName("subject");
            subjectCodes.clear();

            for (int i = 0; i < subjectNodes.getLength(); i++) {
                Element subjectElement = (Element) subjectNodes.item(i);
                String subjectCode = subjectElement.getAttribute("subjectCode");
                subjectCodes.add(subjectCode);
            }
        } catch (Exception e) {
            System.err.println("Error loading subject data: " + e.getMessage());
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

    private void loadSubjectFields() {
        subjectsContainer.getChildren().clear();

        for (int i = 0; i < numSubjects; i++) {
            HBox subjectRow = new HBox(10);

            ComboBox<String> subjectComboBox = new ComboBox<>();
            subjectComboBox.getItems().addAll(subjectCodes);
            subjectComboBox.setPromptText("Select Subject");
            subjectComboBox.setPrefWidth(150);

            ComboBox<String> statusComboBox = new ComboBox<>();
            statusComboBox.getItems().addAll("Pass", "Fail");
            statusComboBox.setPromptText("Status");

            subjectRow.getChildren().addAll(subjectComboBox, statusComboBox);
            subjectsContainer.getChildren().add(subjectRow);
        }
    }

    private void handleSubmit() {
        enteredSubjects.clear();
        Map<String, String> subjectStatus = new HashMap<>();

        // Collect all subjects and their status
        for (var node : subjectsContainer.getChildren()) {
            if (node instanceof HBox subjectRow) {
                ComboBox<String> subjectComboBox = (ComboBox<String>) subjectRow.getChildren().get(0);
                ComboBox<String> statusComboBox = (ComboBox<String>) subjectRow.getChildren().get(1);

                String subjectCode = subjectComboBox.getValue();
                String status = statusComboBox.getValue();

                if (subjectCode != null && status != null) {
                    subjectStatus.put(subjectCode, status);
                    enteredSubjects.add(new Subject(subjectCode, 0, new ArrayList<>()));
                }
            }
        }

        try {
            // Load the RecSchedScreen
            FXMLLoader loader = new FXMLLoader(getClass().getResource("recSchedScreen.fxml"));
            Parent root = loader.load();

            // Get the controller and set the data
            RecSchedScreenController recSchedController = loader.getController();
            recSchedController.setProgram(program);
            recSchedController.setRecommendedSubjects(enteredSubjects, subjectStatus.size());

            // Switch to the new scene
            Stage stage = (Stage) submitButton.getScene().getWindow();
            stage.setScene(new Scene(root));

        } catch (Exception e) {
            System.err.println("Error loading RecSchedScreen: " + e.getMessage());
            e.printStackTrace();
        }
    }
}