package org.jah.eval_sys1.backend;

import java.util.Map;

public class Student {
    boolean isNew;
    Map<String, Boolean> completedSubjects;
    Program program;

    public Student(boolean isNew, Map<String, Boolean> completedSubjects, Program program) {
        this.isNew = isNew;
        this.completedSubjects = completedSubjects;
        this.program = program;
    }
}
