package main.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import main.model.Course;
import main.model.Student;
import main.model.StudentList;

public class CourseDashboard extends JPanel {

    private CardLayout cardLayout;
    private JPanel cardPanel;
    private Course course;

    public CourseDashboard(ActionListener backToInstructorViewAction, Course course) {
        this.course = course;
        setLayout(new BorderLayout());

        // === Initialize card layout ===
        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);

        // === MAIN VIEW PANEL ===
        JPanel mainViewPanel = new JPanel(new BorderLayout());
        JTextArea infoArea = new JTextArea("Welcome to the course dashboard.");
        infoArea.setEditable(false);
        mainViewPanel.add(new JScrollPane(infoArea), BorderLayout.CENTER);

        // === TOP SECTION ===
        JPanel upperInfo = new JPanel(new BorderLayout());
        upperInfo.setBackground(Color.LIGHT_GRAY);
        upperInfo.setPreferredSize(new Dimension(800, 100));

        // Add Student Button
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        buttonPanel.setOpaque(false);
        JButton addStudentBtn = new JButton("Add Student");
        buttonPanel.add(addStudentBtn);
        upperInfo.add(buttonPanel, BorderLayout.CENTER);

        // Button Row
        JPanel mainButtonPanel = new JPanel();
        JButton addAssignmentBtn = new JButton("Assignments");
        JButton addClasslistBtn = new JButton("Classlist");
        JButton viewGradesBtn = new JButton("grades?");
        mainButtonPanel.add(addAssignmentBtn);
        mainButtonPanel.add(addClasslistBtn);
        mainButtonPanel.add(viewGradesBtn);

        // Stack Top
        JPanel topContainer = new JPanel();
        topContainer.setLayout(new BoxLayout(topContainer, BoxLayout.Y_AXIS));
        topContainer.add(upperInfo);
        topContainer.add(mainButtonPanel);

        mainViewPanel.add(topContainer, BorderLayout.NORTH);

        // === GRADES PANEL ===
        JPanel gradesPanel = new JPanel(new BorderLayout());
        JTextArea gradesArea = new JTextArea("Grades content here...");
        gradesArea.setEditable(false);
        gradesPanel.add(new JScrollPane(gradesArea), BorderLayout.CENTER);

        JPanel gradesButtonPanel = new JPanel();
        JButton backToMainBtn = new JButton("Back");
        gradesButtonPanel.add(backToMainBtn);
        gradesPanel.add(gradesButtonPanel, BorderLayout.SOUTH);

        // === CLASSLIST PANEL ===
        JPanel classlistPanel = new JPanel(new BorderLayout());
        JTextArea classlistArea = new JTextArea("Classlist content here...");
        classlistArea.setEditable(false);
        classlistPanel.add(new JScrollPane(classlistArea), BorderLayout.CENTER);

        JPanel classlistButtonPanel = new JPanel();
        JButton backFromClasslistBtn = new JButton("Back");
        classlistButtonPanel.add(backFromClasslistBtn);
        classlistPanel.add(classlistButtonPanel, BorderLayout.SOUTH);

        // === Add all panels to cardPanel ===
        cardPanel.add(mainViewPanel, "main");
        cardPanel.add(gradesPanel, "grades");
        cardPanel.add(classlistPanel, "classlist");

        // Set default view
        cardLayout.show(cardPanel, "main");

        // Add card panel to main layout
        add(cardPanel, BorderLayout.CENTER);

        // this is not needed, clicking List View/Add Class/Calendar View already works so this is redundant. 
        /* === BOTTOM BACK BUTTON ===
           JPanel bottomPanel = new JPanel();
           JButton backBtn = new JButton("Back to Dashboard");
           backBtn.addActionListener(backToInstructorViewAction);
           bottomPanel.add(backBtn)
           add(bottomPanel, BorderLayout.SOUTH); 
        */

        // === BUTTON LOGIC ===

        viewGradesBtn.addActionListener(e -> {
            gradesArea.setText(getGradesInfo());
            cardLayout.show(cardPanel, "grades");
            cardPanel.revalidate();
            cardPanel.repaint();
        });

        addClasslistBtn.addActionListener(e -> {
//            classlistArea.setText(getClasslistInfo());
//            cardLayout.show(cardPanel, "classlist");
//            cardPanel.revalidate();
//            cardPanel.repaint();
            classlistArea.setText(getClasslistInfo());
            cardLayout.show(cardPanel, "classlist");
            cardPanel.revalidate();
            cardPanel.repaint();
        });

        backToMainBtn.addActionListener(e -> {
            cardLayout.show(cardPanel, "main");
            cardPanel.revalidate();
            cardPanel.repaint();
        });

        backFromClasslistBtn.addActionListener(e -> {
            cardLayout.show(cardPanel, "main");
            cardPanel.revalidate();
            cardPanel.repaint();
        });
    }

    private String getClasslistInfo() {
        StringBuilder sb = new StringBuilder();
        StudentList classlist = course.getStudents();

        sb.append("Classlist for ").append(course.getName()).append(":\n\n");

        for (Student student : classlist) {
            sb.append(student.getFirstName()).append(" ").append(student.getLastName());
            sb.append(" - ").append(student.getEmail()).append("\n");
        }

        return sb.toString();
    }

    private String getGradesInfo() {
        StringBuilder sb = new StringBuilder();
        StudentList classlist = course.getStudents();

        sb.append("Grades for ").append(course.getName()).append(":\n\n");

        for (Student student : classlist) {
            sb.append(student.getFirstName()).append(" - ");
            try {

                // double avg = student.getGradebook().calculateAverageForCourse(course);
                // sb.append(String.format("GPA: %.2f", avg));
                sb.append("Grade info placeholder");
            } catch (Exception e) {
                sb.append("Grade info not available");
            }
            sb.append("\n");
        }

        return sb.toString();
    }
}
