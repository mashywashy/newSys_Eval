package org.jah.eval_sys1.backend;

import java.util.*;

public class CurriculumEvaluator {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter your program (BSIT, BSA, BSN, BSMT): ");
        String programInput = scanner.nextLine().trim().toUpperCase();

        String xmlFilePath = "";
        switch (programInput) {
            case "BSIT":
                xmlFilePath = "src/main/resources/org/jah/eval_sys1/bsit_curriculum.xml";
                break;
            case "BSA":
                xmlFilePath = "src/main/resources/org/jah/eval_sys1/bsa_curriculum.xml";
                break;
            case "BSN":
                xmlFilePath = "src/main/resources/org/jah/eval_sys1/bsn_curriculum.xml";
                break;
            case "BSMT":
                xmlFilePath = "src/main/resources/org/jah/eval_sys1/bsmt_curriculum.xml";
                break;
            default:
                System.out.println("Invalid program entered.");
                return;
        }

        Curriculum curriculum = new Curriculum(xmlFilePath);
        Program program = new Program(programInput, curriculum);
        School school = new School();
    }
}
