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


    public void setRecommendedSubjects(List<Subject> subjects, int units) {
        subjectsContainer.getChildren().clear();

        for (Subject subject : subjects) {
            HBox subjectRow = new HBox(20);

            Label subjectLabel = new Label(subject.getSubjectCode());
            Label unitsLabel = new Label(new String(String.valueOf(units)));

            subjectLabel.setStyle("-fx-font-size: 14px;");
            unitsLabel.setStyle("-fx-font-size: 14px;");

            subjectRow.getChildren().addAll(subjectLabel, unitsLabel);
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