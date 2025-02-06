package org.jah.eval_sys1;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.jah.eval_sys1.backend.Subject;

import java.util.List;

public class RecSchedScreenController {

    @FXML
    private VBox subjectsContainer;

    @FXML
    private Label totalUnitsLabel;

    public void setRecommendedSubjects(List<Subject> subjects, int totalUnits) {
        subjectsContainer.getChildren().clear(); // Clear previous data

        for (Subject subject : subjects) {
            // Create labels for subject and units
            Label subjectLabel = new Label(subject.getSubjectCode());
            Label unitsLabel = new Label(subject.getUnits() + " units");

            // Set alignment and spacing
            subjectLabel.setMinWidth(250);  // Adjust width to align properly
            unitsLabel.setMinWidth(100);    // Align with headers
            subjectLabel.setStyle("-fx-font-size: 14px;");
            unitsLabel.setStyle("-fx-font-size: 14px; -fx-text-alignment: right;");

            // HBox to hold a subject row (subject + units)
            HBox subjectRow = new HBox(100, subjectLabel, unitsLabel); // Spacing between columns
            subjectsContainer.getChildren().add(subjectRow);
        }

        // Set total units
        totalUnitsLabel.setText("Total Units: " + totalUnits);
    }
}
