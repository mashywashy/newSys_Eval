// RecSchedScreenController.java
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

    private String program;

    public void setRecommendedSubjects(List<Subject> subjects, int subjectStatus) {
        subjectsContainer.getChildren().clear();

        for (Subject subject : subjects) {
            HBox subjectRow = new HBox(20); // 20px spacing between elements

            Label subjectLabel = new Label(subject.getSubjectCode());
            Label statusLabel = new Label();

            subjectLabel.setStyle("-fx-font-size: 14px;");
            statusLabel.setStyle("-fx-font-size: 14px;");

            subjectRow.getChildren().addAll(subjectLabel, statusLabel);
            subjectsContainer.getChildren().add(subjectRow);
        }

        int totalSubjects = subjects.size();
        totalUnitsLabel.setText("Total Subjects: " + totalSubjects);
    }

    public void setProgram(String program) {
        this.program = program;
        System.out.println("Program Set: " + program);
    }
}