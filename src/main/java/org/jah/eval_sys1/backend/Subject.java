package org.jah.eval_sys1.backend;

import java.util.List;

public class Subject {
    public String subjectCode;
    public int units;
    public List<String> prerequisites;

    public Subject(String subjectCode, int units, List<String> prerequisites) {
        this.subjectCode = subjectCode;
        this.units = units;
        this.prerequisites = prerequisites;
    }

    // Getters
    public String getSubjectCode() {
        return subjectCode;
    }

    public int getUnits() {
        return units;
    }

    public List<String> getPrerequisites() {
        return prerequisites;
    }
}
