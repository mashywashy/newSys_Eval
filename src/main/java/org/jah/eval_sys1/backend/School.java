package org.jah.eval_sys1.backend;

import java.util.List;

public class School {

    public int calculateTotalUnits(List<Subject> subjects, Student student) {
        return student.program.calculateTotalUnits(subjects);
    }
}
