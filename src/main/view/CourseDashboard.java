package main.view;

import javax.swing.*;
import main.model.Assignment;

import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import main.controller.UserViewController;
import main.model.Course;
import main.model.FinalGrades;
import main.model.Student;
import main.model.StudentGradebook;
import main.model.StudentList;

public class CourseDashboard extends JPanel {

    private CardLayout cardLayout;
    private JPanel cardPanel;
    private JTextArea infoArea;
    private JButton gradeStudentBtn;
    private JButton sideAddAssignmentBtn;
    private JButton addStudentBtn;
    private JButton setFinalGradesBtn;
    private JPanel contentPanel;
    
    private JButton removeStudentBtn;
    private JLabel classStatsLabel;

    
    private UserViewController controller;

    public CourseDashboard(ActionListener backToInstructorViewAction, UserViewController controller) {
        
    	this.controller = controller;
        Course course = controller.getSelectedCourse();
        setLayout(new BorderLayout());

        // === Initialize card layout ===
        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);

        // === MAIN VIEW PANEL ===
        JPanel mainViewPanel = new JPanel(new BorderLayout());

        infoArea = new JTextArea("Welcome to the course dashboard.");
        infoArea.setEditable(false);

        JScrollPane scrollPane = new JScrollPane(infoArea);

        contentPanel = new JPanel(new BorderLayout());
        contentPanel.add(scrollPane, BorderLayout.CENTER);

        // this will be a button to add the students but ill add it later.. too much code :)
        sideAddAssignmentBtn = new JButton("Add Assignment");
        sideAddAssignmentBtn.setVisible(false);

        gradeStudentBtn = new JButton("Grade a Student!");
        gradeStudentBtn.setVisible(false);
        
        setFinalGradesBtn = new JButton("Set Final Grades");
        setFinalGradesBtn.setVisible(false);

        addStudentBtn = new JButton("Add Student");
        addStudentBtn.setVisible(false); // only visible in Classlist view

        removeStudentBtn = new JButton("Remove Student");
        removeStudentBtn.setVisible(false);  // only show on Classlist view

        
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
        
        rightPanel.add(Box.createVerticalStrut(100));
        rightPanel.add(sideAddAssignmentBtn);
        rightPanel.add(gradeStudentBtn);
        rightPanel.add(setFinalGradesBtn);
        rightPanel.add(addStudentBtn);
        rightPanel.add(removeStudentBtn);
        rightPanel.add(Box.createVerticalGlue());


        contentPanel.add(rightPanel, BorderLayout.EAST);

        mainViewPanel.add(contentPanel, BorderLayout.CENTER);

        // === TOP SECTION ===
        JPanel upperInfo = new JPanel(new BorderLayout());
        upperInfo.setBackground(Color.WHITE);
        upperInfo.setPreferredSize(new Dimension(800, 60));

        // === LEFT SIDE: Logo + Course Name ===
        ImageIcon icon = new ImageIcon("assets/UOFAD2L.png");
        Image scaledImage = icon.getImage().getScaledInstance(280, 100, Image.SCALE_SMOOTH);
        icon = new ImageIcon(scaledImage);
        JLabel imageLabel = new JLabel(icon);

        // Panel to group logo + course name
        JPanel leftInfoPanel = new JPanel();
        leftInfoPanel.setOpaque(false);
        leftInfoPanel.setLayout(new BoxLayout(leftInfoPanel, BoxLayout.X_AXIS));

        // Course name label
        JLabel courseNameLabel = new JLabel(course.getName() + " - " + course.getCourseCode());
        courseNameLabel.setFont(new Font("SansSerif", Font.PLAIN, 18));
        courseNameLabel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0)); // space between logo and text

        // Add both to the left panel
        leftInfoPanel.add(imageLabel);
        leftInfoPanel.add(courseNameLabel);

        // Add to left side of upperInfo
        upperInfo.add(leftInfoPanel, BorderLayout.WEST);

        // === PROFILE PIC + first name/lastname  OVERLAY ===
        ImageIcon pfpIcon = new ImageIcon("assets/PFPSQUARE.png");
        Image scalePFP = pfpIcon.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
        pfpIcon = new ImageIcon(scalePFP);

        JLabel pfpLabel = new JLabel(pfpIcon);
        pfpLabel.setAlignmentX(0.5f);
        pfpLabel.setAlignmentY(0.5f);

        // Text overlay on the picture
        JLabel overlayText = new JLabel("" + course.getInstructor().getFirstName().charAt(0) + "" + course.getInstructor().getLastName().charAt(0));
        overlayText.setFont(new Font("SansSerif", Font.BOLD, 12));
        overlayText.setForeground(Color.WHITE);
        overlayText.setHorizontalAlignment(SwingConstants.CENTER);
        overlayText.setVerticalAlignment(SwingConstants.CENTER);
        overlayText.setAlignmentX(0.5f);
        overlayText.setAlignmentY(0.5f);

        // Panel to layer the image and the overlay text
        JPanel overlayPanel = new JPanel();
        overlayPanel.setLayout(new OverlayLayout(overlayPanel));
        overlayPanel.setOpaque(false);
        overlayPanel.add(overlayText);   
        overlayPanel.add(pfpLabel);      

        // === USERNAME LABEL ===
        JLabel userNameLabel = new JLabel(course.getInstructor().getFullName());
        userNameLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
        userNameLabel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0)); // space from image

        // === Group name + image in one right-aligned panel
        JPanel userInfoPanel = new JPanel();
        userInfoPanel.setLayout(new BoxLayout(userInfoPanel, BoxLayout.X_AXIS));
        userInfoPanel.setOpaque(false);
        userInfoPanel.add(overlayPanel);
        userInfoPanel.add(Box.createHorizontalStrut(5));
        userInfoPanel.add(userNameLabel);

        // right-aligned 
        JPanel rightInfoPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 80, 10));
        rightInfoPanel.setOpaque(false);
        rightInfoPanel.add(userInfoPanel);

        // === Add to top bar
        upperInfo.add(rightInfoPanel, BorderLayout.EAST);

        // Button Row
        JPanel mainButtonPanel = new JPanel();
        JButton addAssignmentBtn = new JButton("Assignments");
        JButton addClasslistBtn = new JButton("Classlist");
        JButton viewGradesBtn = new JButton("Grades");
        mainButtonPanel.add(addAssignmentBtn);
        mainButtonPanel.add(addClasslistBtn);
        mainButtonPanel.add(viewGradesBtn);

        // Top Section Combined
        JPanel topContainer = new JPanel();
        topContainer.setLayout(new BoxLayout(topContainer, BoxLayout.Y_AXIS));
        topContainer.add(upperInfo);
        topContainer.add(mainButtonPanel);

        mainViewPanel.add(topContainer, BorderLayout.NORTH);

        // show the class average and class mean
        classStatsLabel = new JLabel("Class Average: --   Median: --");
        classStatsLabel.setFont(new Font("SansSerif", Font.PLAIN, 12));
        classStatsLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        classStatsLabel.setVisible(false);  // initially hidden

        topContainer.add(classStatsLabel); // or wherever you stack layout it


        
        // === Add only the main panel to cardPanel ===
        cardPanel.add(mainViewPanel, "main");
        cardLayout.show(cardPanel, "main");

        add(cardPanel, BorderLayout.CENTER);

        
        viewGradesBtn.addActionListener(e -> {
            infoArea.setText(getGradesInfo());

            sideAddAssignmentBtn.setVisible(false);
            gradeStudentBtn.setVisible(true);
            setFinalGradesBtn.setVisible(true);
            addStudentBtn.setVisible(false);

            Course selectedCourse = controller.getSelectedCourse();
            StudentList classlist = selectedCourse.getStudents();
            double avg = classlist.getClassAverage(selectedCourse);
            double median = classlist.getClassMedian(selectedCourse);

            classStatsLabel.setText(String.format("Class Average: %.2f%%   Median: %.2f%%", avg, median));
            classStatsLabel.setVisible(true);  
            removeStudentBtn.setVisible(false);

        });
        
        addClasslistBtn.addActionListener(e -> {
            updateSortedClasslist(0); // default sort by first name
            sideAddAssignmentBtn.setVisible(false);
            gradeStudentBtn.setVisible(false);
            setFinalGradesBtn.setVisible(false);
            addStudentBtn.setVisible(true);
            classStatsLabel.setVisible(false);  
            removeStudentBtn.setVisible(true);

            // === SORT DROPDOWN ===
            JComboBox<String> sortOptions = new JComboBox<>(new String[] {
                "Sort by First Name", "Sort by Last Name", "Sort by Username", "Sort by grades on an assignment"
            });

            sortOptions.addActionListener(e2 -> {
                int selectedIndex = sortOptions.getSelectedIndex();
                if (selectedIndex == 3) { // Sort by grades on an assignment
                
                    ArrayList<String> allAssignments = new ArrayList<>();

                    for (String category : controller.getSelectedCourse().getCategories().keySet()) {
                        for (Assignment a : controller.getSelectedCourse().getCategories().get(category).getAssignments()) {
                            allAssignments.add(a.getTitle());
                        }
                    }

                    if (allAssignments.isEmpty()) {
                        JOptionPane.showMessageDialog(this, "No assignments found to sort by.");
                        return;
                    }

                    String assignmentName = (String) JOptionPane.showInputDialog(
                        this,
                        "Select an assignment to sort by grade:",
                        "Sort by Assignment Grade",
                        JOptionPane.PLAIN_MESSAGE,
                        null,
                        allAssignments.toArray(),
                        allAssignments.get(0)
                    );

                    if (assignmentName != null) {
                        updateSortedClasslist(3, assignmentName); 
                    }

                } else {
                    updateSortedClasslist(selectedIndex);
                }
            });


            // remove existing dropdowns and add the new one
            for (Component comp : contentPanel.getComponents()) {
                if (comp instanceof JComboBox) {
                    contentPanel.remove(comp);
                }
            }
            contentPanel.add(sortOptions, BorderLayout.NORTH);
            contentPanel.revalidate();
            contentPanel.repaint();
        });


        addAssignmentBtn.addActionListener(e -> {
            updateAssignmentDisplay();
            sideAddAssignmentBtn.setVisible(true);

            JPanel assignmentPanel = controller.getSelectedCourse().getAssignmentAddPanel();
            
            // set the other clickers to false
            gradeStudentBtn.setVisible(false);
            setFinalGradesBtn.setVisible(false);
            addStudentBtn.setVisible(false);
            classStatsLabel.setVisible(false);  
            removeStudentBtn.setVisible(false);
        });

        removeStudentBtn.addActionListener(e -> {
            Course insideCourse = controller.getSelectedCourse();
            StudentList studentList = insideCourse.getStudents();

            if (studentList.isEmpty()) {
                JOptionPane.showMessageDialog(this, "There are no students to remove.");
                return;
            }

            JPanel removePanel = new JPanel(new GridLayout(0, 1));
            ArrayList<JCheckBox> checkboxes = new ArrayList<>();
            ArrayList<Student> studentRefs = new ArrayList<>();

            for (Student student : studentList) {
                JCheckBox box = new JCheckBox(student.getFullName() + " (" + student.getUsername() + ")");
                checkboxes.add(box);
                studentRefs.add(student);
                removePanel.add(box);
            }

            int result = JOptionPane.showConfirmDialog(this, removePanel, "Remove Students", JOptionPane.OK_CANCEL_OPTION);
            if (result == JOptionPane.OK_OPTION) {
                for (int i = 0; i < checkboxes.size(); i++) {
                    if (checkboxes.get(i).isSelected()) {
                        course.removeStudent(studentRefs.get(i));
                    }
                }
                infoArea.setText(getClasslistInfo()); // refresh the classlist display
            }
        });

        
        addStudentBtn.addActionListener(e -> {
            controller.getSelectedCourse().addAllStudentsFromPool();
            infoArea.setText(getClasslistInfo()); // refresh after adding
        });
        
        sideAddAssignmentBtn.addActionListener(e -> {
            Course selectedCourse = controller.getSelectedCourse();
            JPanel assignmentPanel = selectedCourse.getAssignmentAddPanel();
            int result = JOptionPane.showConfirmDialog(this, assignmentPanel, selectedCourse.getCourseCode(), JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
            if (result == JOptionPane.OK_OPTION) {
                updateAssignmentDisplay();
            }
        });
       
        gradeStudentBtn.addActionListener(e -> {
            Course cur = controller.getSelectedCourse();
            StudentList studentList = cur.getStudents();

            if (studentList.isEmpty()) {
                JOptionPane.showMessageDialog(this, "No students to grade.");
                return;
            }

            // === STUDENT DROPDOWN ===
            JComboBox<Student> studentDropdown = new JComboBox<>();
            for (Student student : studentList) {
                studentDropdown.addItem(student);
            }

            // === CATEGORY DROPDOWN ===
            JComboBox<String> categoryDropdown = new JComboBox<>(cur.getCategories().keySet().toArray(new String[0]));

            // === ASSIGNMENT DROPDOWN (will be dynamically updated) ===
            JComboBox<String> assignmentDropdown = new JComboBox<>();
            categoryDropdown.addActionListener(e2 -> {
                assignmentDropdown.removeAllItems();
                String selectedCategory = (String) categoryDropdown.getSelectedItem();
                for (Assignment a : course.getCategories().get(selectedCategory).getAssignments()) {
                    assignmentDropdown.addItem(a.getTitle());
                }
            });

            // Trigger once at start
            if (categoryDropdown.getItemCount() > 0) {
                categoryDropdown.setSelectedIndex(0);
            }

            JTextField gradeField = new JTextField(5);

            // === GRADING PANEL ===
            JPanel gradingPanel = new JPanel(new GridLayout(0, 1));
            gradingPanel.add(new JLabel("Select Student:"));
            gradingPanel.add(studentDropdown);
            gradingPanel.add(new JLabel("Select Category:"));
            gradingPanel.add(categoryDropdown);
            gradingPanel.add(new JLabel("Select Assignment:"));
            gradingPanel.add(assignmentDropdown);
            gradingPanel.add(new JLabel("Enter Grade:"));
            gradingPanel.add(gradeField);

            int result = JOptionPane.showConfirmDialog(this, gradingPanel, "Grade Assignment", JOptionPane.OK_CANCEL_OPTION);
            if (result == JOptionPane.OK_OPTION) {
                try {
                    Student selectedStudent = (Student) studentDropdown.getSelectedItem();
                    String category = (String) categoryDropdown.getSelectedItem();
                    String assignmentTitle = (String) assignmentDropdown.getSelectedItem();
                    double grade = Double.parseDouble(gradeField.getText());

                    Assignment originalAssignment = cur.getCategories().get(category).getAssignments().stream()
                        .filter(a -> a.getTitle().equals(assignmentTitle))
                        .findFirst()
                        .orElse(null);

                    if (originalAssignment != null && selectedStudent != null) {
                        selectedStudent.getGradebookForCourse(course).addAssignment(new Assignment(originalAssignment), grade);
                     // Immediately refresh the display and stats
                        infoArea.setText(getGradesInfo());

                        Course selectedCourse = controller.getSelectedCourse();
                        StudentList classlist = selectedCourse.getStudents();
                        double avg = classlist.getClassAverage(selectedCourse);
                        double median = classlist.getClassMedian(selectedCourse);

                        classStatsLabel.setText(String.format("Class Average: %.2f%%   Median: %.2f%%", avg, median));
                    } else {
                        JOptionPane.showMessageDialog(this, "Invalid selection.");
                    }

                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
                }
            }
        });
        
        setFinalGradesBtn.addActionListener(e -> {
            Course course1 = controller.getSelectedCourse();
            StudentList students = course1.getStudents();
            
            if (students.isEmpty()) {
                JOptionPane.showMessageDialog(CourseDashboard.this, "No students in the course to grade.");
                return;
            }
            
            int count = 0;
            StringBuilder resultMessage = new StringBuilder("Final grades assigned:\n\n");
            
            for (Student student : students) {
                StudentGradebook gradebook = student.getGradebookForCourse(course1);
                if (gradebook == null) continue;
                
                double totalEarned = 0;
                double totalPossible = 0;
                
                for (Assignment assignment : gradebook.getAssignments()) {
                    totalEarned += assignment.getPointsEarned();
                    totalPossible += assignment.getMaxPoints();
                }
                
                if (totalPossible > 0) {
                    double percentage = (totalEarned / totalPossible) * 100;
                    FinalGrades grade = calculateFinalGrade(percentage);
                    gradebook.setFinalGrade(grade);
                    count++;
                    
                    resultMessage.append(student.getFullName())
                        .append(": ")
                        .append(String.format("%.2f", percentage))
                        .append("% - ")
                        .append(grade)
                        .append("\n");
                } else {
                    resultMessage.append(student.getFullName())
                        .append(": No assignments to grade\n");
                }
            }
            
            // Update the display
            infoArea.setText(getGradesInfo());
            
            // Show results
            if (count > 0) {
                JTextArea resultArea = new JTextArea(resultMessage.toString());
                resultArea.setEditable(false);
                JScrollPane scrollPane1 = new JScrollPane(resultArea);
                scrollPane1.setPreferredSize(new Dimension(400, 300));
                
                JOptionPane.showMessageDialog(
                    CourseDashboard.this,
                    scrollPane1,
                    "Final Grades Set",
                    JOptionPane.INFORMATION_MESSAGE
                );
            } else {
                JOptionPane.showMessageDialog(
                    CourseDashboard.this,
                    "No students had assignments to grade.",
                    "Final Grades",
                    JOptionPane.INFORMATION_MESSAGE
                );
            }
        });
    }

    // Helper method to convert percentage to grade
    private FinalGrades calculateFinalGrade(double percentage) {
        if (percentage >= 90) return FinalGrades.A;
        if (percentage >= 80) return FinalGrades.B;
        if (percentage >= 70) return FinalGrades.C;
        if (percentage >= 60) return FinalGrades.D;
        return FinalGrades.E;
    }

    private void updateAssignmentDisplay() {
        Course selectedCourse = controller.getSelectedCourse();

        StringBuilder sb = new StringBuilder();
        sb.append("Assignments for ").append(selectedCourse.getName()).append(":\n\n");

        for (String category : selectedCourse.getCategories().keySet()) {
            sb.append("Category: ").append(category).append("\n");
            for (main.model.Assignment a : selectedCourse.getCategories().get(category).getAssignments()) {
                sb.append("  - ").append(a.getTitle())
                  .append(" (Max Points: ").append(a.getMaxPoints()).append(")\n");
            }
            sb.append("\n");
        }

        infoArea.setText(sb.toString());
    }

    
    private String getClasslistInfo() {
        StringBuilder sb = new StringBuilder();
        StudentList classlist = controller.getSelectedCourse().getStudents();

        sb.append("Classlist for ").append(controller.getSelectedCourse().getName()).append(":\n\n");

        for (Student student : classlist) {
            sb.append(student.getFirstName()).append(" ").append(student.getLastName());
            sb.append(" - ").append(student.getEmail()).append("\n");
        }

        return sb.toString();
    }
    
    private void updateSortedClasslist(int sortOption) {
        Course course = controller.getSelectedCourse();
        StudentList students = new StudentList(course.getStudents()); 

        switch (sortOption) {
            case 0: students.sortByFirstName(); break;
            case 1: students.sortByLastName(); break;
            case 2: students.sortByUsername(); break;
            case 3:
            	students.sortByAssignmentGrade(controller.getSelectedCourse().getName());
            	break;
        }

        StringBuilder sb = new StringBuilder();
        sb.append("Classlist for ").append(course.getName()).append(":\n\n");

        for (Student student : students) {
            sb.append(student.getFirstName()).append(" ").append(student.getLastName())
              .append(" - ").append(student.getEmail()).append("\n");
        }

        infoArea.setText(sb.toString());
    }

    
    private String getGradesInfo() {
        Course course = controller.getSelectedCourse();
        StudentList classlist = course.getStudents();

        StringBuilder sb = new StringBuilder();
        sb.append("Grades for ").append(course.getName()).append(":\n\n");

        for (Student student : classlist) {
            sb.append(student.getFirstName()).append(" ").append(student.getLastName()).append(":\n");

            StudentGradebook gb = student.getGradebookForCourse(course);
            if (gb == null || gb.getAssignments().isEmpty()) {
                sb.append("  No grades available.\n\n");
                continue;
            }

            double totalEarned = 0;
            double totalPossible = 0;

            for (Assignment a : gb.getAssignments()) {
                sb.append("  ").append(a.getTitle())
                  .append(" - ").append(a.getPointsEarned())
                  .append("/").append(a.getMaxPoints()).append("\n");
                totalEarned += a.getPointsEarned();
                totalPossible += a.getMaxPoints();
            }

            double percentage = (totalPossible > 0) ? (100 * totalEarned / totalPossible) : 0;
            sb.append("  Total: ").append(String.format("%.2f", totalEarned)).append("/")
              .append(String.format("%.2f", totalPossible))
              .append(" (").append(String.format("%.2f", percentage)).append("%)\n");
              
            // Add final grade if it exists
            if (gb.getFinalGrade() != null) {
                sb.append("  Final Grade: ").append(gb.getFinalGrade()).append("\n");
            }
            
            sb.append("\n");
        }

        return sb.toString();
    }
    
    private void updateSortedClasslist(int sortOption, String assignmentName) {
        Course course = controller.getSelectedCourse();
        StudentList students = new StudentList(course.getStudents()); // copy

        if (sortOption == 3) {
            students.sortByAssignmentGrade(assignmentName);
        }

        StringBuilder sb = new StringBuilder();
        sb.append("Classlist for ").append(course.getName()).append(" (Sorted by grades on \"").append(assignmentName).append("\"):\n\n");

        for (Student student : students) {
            sb.append(student.getFirstName()).append(" ").append(student.getLastName())
              .append(" - ").append(student.getEmail()).append("\n");
        }

        infoArea.setText(sb.toString());
    }
}