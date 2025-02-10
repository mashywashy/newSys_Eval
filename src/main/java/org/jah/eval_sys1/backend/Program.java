package org.jah.eval_sys1.backend;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Program {
    String programName;
    Curriculum curriculum;
    private static final int MAX_UNITS = 26;

    public Program(String programName, Curriculum curriculum) {
        this.programName = programName;
        this.curriculum = curriculum;
    }

    public List<Subject> recommendSubjects(Student student) {
        List<Subject> recommended = new ArrayList<>();
        int currentUnits = 0;

        if (student.isNew) {
            for (Subject subject : curriculum.years.get("firstYear").get("firstSem")) {
                if (currentUnits + subject.units <= MAX_UNITS) {
                    recommended.add(subject);
                    currentUnits += subject.units;
                }
            }
        } else {
            for (Map<String, List<Subject>> year : curriculum.years.values()) {
                for (List<Subject> semester : year.values()) {
                    for (Subject subject : semester) {
                        if (!student.completedSubjects.containsKey(subject.subjectCode) || !student.completedSubjects.get(subject.subjectCode)) {
                            boolean canTake = true;
                            for (String prereq : subject.prerequisites) {
                                if (!student.completedSubjects.containsKey(prereq) || !student.completedSubjects.get(prereq)) {
                                    canTake = false;
                                    break;
                                }
                            }
                            if (canTake && currentUnits + subject.units <= MAX_UNITS) {
                                recommended.add(subject);
                                currentUnits += subject.units;
                            }
                        } else {
                            // If the subject was completed but failed, recommend it for retake
                            if (!subject.status && currentUnits + subject.units <= MAX_UNITS) {
                                recommended.add(subject);
                                currentUnits += subject.units;
                            }
                        }
                    }
                }
            }
        }

        return recommended;
    }


    public int calculateTotalUnits(List<Subject> subjects) {
        int totalUnits = 0;
        for (Subject subject : subjects) {
            totalUnits += subject.units;
        }
        return totalUnits;
    }
}
