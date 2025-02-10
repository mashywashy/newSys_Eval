package org.jah.eval_sys1.backend;

import java.util.Map;

public class Student {
    public boolean isNew;
    public Map<String, Boolean> completedSubjects;
    public Program program;

    public Student(boolean isNew, Map<String, Boolean> completedSubjects, Program program) {
        this.isNew = isNew;
        this.completedSubjects = completedSubjects;
        this.program = program;
    }
}
