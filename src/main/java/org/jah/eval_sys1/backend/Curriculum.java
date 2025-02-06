package org.jah.eval_sys1.backend;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Curriculum {
    Map<String, Map<String, List<Subject>>> years;

    public Curriculum(String xmlFilePath) {
        years = new HashMap<>();
        initializeCurriculum(xmlFilePath);
    }

    private void initializeCurriculum(String xmlFilePath) {
        try {
            File xmlFile = new File(xmlFilePath);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(xmlFile);
            doc.getDocumentElement().normalize();

            NodeList yearList = doc.getDocumentElement().getChildNodes();

            for (int i = 0; i < yearList.getLength(); i++) {
                Node yearNode = yearList.item(i);
                if (yearNode.getNodeType() == Node.ELEMENT_NODE) {
                    String yearName = yearNode.getNodeName();
                    Map<String, List<Subject>> semesters = new HashMap<>();

                    NodeList semesterList = yearNode.getChildNodes();
                    for (int j = 0; j < semesterList.getLength(); j++) {
                        Node semesterNode = semesterList.item(j);
                        if (semesterNode.getNodeType() == Node.ELEMENT_NODE) {
                            String semesterName = semesterNode.getNodeName();
                            List<Subject> subjects = new ArrayList<>();

                            NodeList subjectList = semesterNode.getChildNodes();
                            for (int k = 0; k < subjectList.getLength(); k++) {
                                Node subjectNode = subjectList.item(k);
                                if (subjectNode.getNodeType() == Node.ELEMENT_NODE) {
                                    Element subjectElement = (Element) subjectNode;
                                    String subjectCode = subjectElement.getAttribute("subjectCode");
                                    int units = Integer.parseInt(subjectElement.getAttribute("units"));

                                    List<String> prerequisites = new ArrayList<>();
                                    NodeList prereqList = subjectElement.getElementsByTagName("prerequisite");
                                    for (int l = 0; l < prereqList.getLength(); l++) {
                                        prerequisites.add(prereqList.item(l).getTextContent());
                                    }

                                    subjects.add(new Subject(subjectCode, units, prerequisites));
                                }
                            }

                            semesters.put(semesterName, subjects);
                        }
                    }

                    years.put(yearName, semesters);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
