package main.view;

import javax.swing.*;
import main.model.Assignment;
import main.model.Category;

import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import main.controller.UserViewController;
import main.model.Course;
import main.model.FinalGrades;
import main.model.Instructor;
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
    private JButton addClasslistBtn;  // moved this to the class level for back button logic
    

    
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
        
        JButton groupBtn = new JButton("Make Groups");
        groupBtn.addActionListener(e -> {
          String input = JOptionPane.showInputDialog(this, "Max students per group?", "Make Student Groups", JOptionPane.QUESTION_MESSAGE);
          if (input == null) return;
          int size;
          try { size = Integer.parseInt(input); }
          catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Enter a positive integer.", "Invalid", JOptionPane.ERROR_MESSAGE);
            return;
          }
          List<StudentList> groups = controller.getSelectedCourse()
                                              .getStudents()
                                              .partition(size);
          StringBuilder out = new StringBuilder();
          for (int i = 0; i < groups.size(); i++) {
            out.append("Group ").append(i+1).append(":\n");
            for (Student s : groups.get(i)) {
              out.append(" • ").append(s.getFullName())
                 .append(" (").append(s.getUsername()).append(")\n");
            }
            out.append("\n");
          }
          JTextArea ta = new JTextArea(out.toString());
          ta.setFont(UIManager.getFont("Label.font"));
          ta.setEditable(false);
          JScrollPane scroll = new JScrollPane(ta);
          scroll.setPreferredSize(new Dimension(400,300));
          JOptionPane.showMessageDialog(this, scroll, "Student Groups", JOptionPane.PLAIN_MESSAGE);
          
          
        });
        rightPanel.add(groupBtn);
        
        groupBtn.setVisible(false);
        


        
        JButton manageCatsBtn = new JButton("Manage Categories");
        manageCatsBtn.addActionListener(e -> {
            Course c = controller.getSelectedCourse();
            Map<String,Category> cats = c.getCategories();

            JPanel form = new JPanel(new GridBagLayout());
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(4,4,4,4);
            gbc.gridy = 0;

            List<JTextField> weightFields = new ArrayList<>();
            List<JTextField> dropFields   = new ArrayList<>();
            List<String>      names        = new ArrayList<>();

            // column headers
            gbc.gridx = 0; form.add(new JLabel("Category"),       gbc);
            gbc.gridx = 1; form.add(new JLabel("Weight (pts)"),   gbc);
            gbc.gridx = 2; form.add(new JLabel("Drop Lowest"),    gbc);
            gbc.gridy++;

            // one row per existing category
            for (String name : cats.keySet()) {
              Category cat = cats.get(name);
              gbc.gridx = 0; form.add(new JLabel(name),        gbc);
              gbc.gridx = 1; 
              JTextField w = new JTextField(""+cat.getPoints(), 5);
              form.add(w, gbc);
              gbc.gridx = 2; 
              JTextField d = new JTextField(""+cat.getDropLowest(), 3);
              form.add(d, gbc);

              names.add(name);
              weightFields.add(w);
              dropFields.add(d);
              gbc.gridy++;
            }

            // “New category” row
            gbc.gridx = 0;
            JTextField newName   = new JTextField(10);
            form.add(newName,    gbc);
            gbc.gridx = 1;
            JTextField newWeight = new JTextField("100", 5);
            form.add(newWeight,  gbc);
            gbc.gridx = 2;
            JTextField newDrop   = new JTextField("0", 3);
            form.add(newDrop,    gbc);
            gbc.gridy++;

            int result = JOptionPane.showConfirmDialog(
              this, form, "Manage Categories",
              JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE
            );
            if (result == JOptionPane.OK_OPTION) {
              // 1) update existing cats
              for (int i = 0; i < names.size(); i++) {
                String nm = names.get(i);
                int pts = Integer.parseInt(weightFields.get(i).getText());
                int dr  = Integer.parseInt(dropFields.get(i).getText());
                c.setCategoryWeight(nm, pts);
                c.setCategoryDropLowest(nm, dr);
              }
              // 2) maybe add a new one
              String nn = newName.getText().trim();
              if (!nn.isEmpty()) {
                int pts = Integer.parseInt(newWeight.getText());
                int dr  = Integer.parseInt(newDrop.getText());
                c.addCategory(nn, pts, dr);       
              }
              // 3) refresh UI
              updateAssignmentDisplay();
            }
        });
        rightPanel.add(manageCatsBtn);

        if (!(controller.getUser() instanceof Instructor)) {
        	System.out.println("erm");
            sideAddAssignmentBtn.setVisible(false);
            manageCatsBtn.setVisible(false);
            addStudentBtn.setVisible(false);
            removeStudentBtn.setVisible(false);
            groupBtn.setVisible(false);
            gradeStudentBtn.setVisible(false);
            setFinalGradesBtn.setVisible(false);
        } else {
        	System.out.println("erm 2");
        }


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
        JLabel overlayText = new JLabel("" + controller.getUser().getFirstName().charAt(0) + "" + controller.getUser().getLastName().charAt(0));
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
        JLabel userNameLabel = new JLabel(controller.getUser().getFullName());
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
        addClasslistBtn = new JButton("Classlist");
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
            
            if (controller.getUser() instanceof Instructor) {
                gradeStudentBtn.setVisible(true);
                setFinalGradesBtn.setVisible(true);
                classStatsLabel.setVisible(true);  
            }

            addStudentBtn.setVisible(false);
            groupBtn.setVisible(false);    // hide here

            Course selectedCourse = controller.getSelectedCourse();
            StudentList classlist = selectedCourse.getStudents();
            double avg = classlist.getClassAverage(selectedCourse);
            double median = classlist.getClassMedian(selectedCourse);

            classStatsLabel.setText(String.format("Class Average: %.2f%%   Median: %.2f%%", avg, median));
            removeStudentBtn.setVisible(false);
            

        });
        
        
        /** 
         * === NOW THIS JUST CALLS OUR HELPER WHICH HAS IDENTICAL 
         * LOGIC TO PREVIOUS IMPLEMENTATION ===
        */
        addClasslistBtn.addActionListener(e -> {
        	showClasslistView();           // your helper
            if (controller.getUser() instanceof Instructor) {
            	groupBtn.setVisible(true);     // show here
            } else {
                sideAddAssignmentBtn.setVisible(false);
                gradeStudentBtn.setVisible(false);
                setFinalGradesBtn.setVisible(false);
                addStudentBtn.setVisible(false);
                removeStudentBtn.setVisible(false);
                classStatsLabel.setVisible(false);
            }
            
        });

        addAssignmentBtn.addActionListener(e -> {
       
            updateAssignmentDisplay();
            if (controller.getUser() instanceof Instructor) {
            sideAddAssignmentBtn.setVisible(true);
            }

            JPanel assignmentPanel = controller.getSelectedCourse().getAssignmentAddPanel();
            
            // set the other clickers to false
            gradeStudentBtn.setVisible(false);
            setFinalGradesBtn.setVisible(false);
            addStudentBtn.setVisible(false);
            classStatsLabel.setVisible(false);  
            removeStudentBtn.setVisible(false);
            groupBtn.setVisible(false);    
           
  
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
                StudentGradebook gb = student.getGradebookForCourse(course);
                double pct = gb.calculateFinalGrade();         // 0–100, already picks weighted vs. points‐sum
                FinalGrades letter = calculateFinalGrade(pct); // your existing helper

                gb.setFinalGrade(letter);
                resultMessage.append(student.getFullName())
                             .append(": ")
                             .append(String.format("%.2f%% - %s\n", pct, letter));
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
    
    private String getGradesInfo() {
        Course course = controller.getSelectedCourse();
        StudentList classlist = course.getStudents();

        StringBuilder sb = new StringBuilder();
        sb.append("Grades for ").append(course.getName()).append(":\n\n");

        if (controller.getUser() instanceof Instructor) {
            // Show everyone's grades
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

                double percentage = gb.calculateFinalGrade();
                sb.append("  Total: ").append(String.format("%.2f", totalEarned)).append("/")
                  .append(String.format("%.2f", totalPossible))
                  .append(" (").append(String.format("%.2f", percentage)).append("%)\n");

                if (gb.getFinalGrade() != null) {
                    sb.append("  Final Grade: ").append(gb.getFinalGrade()).append("\n");
                }

                sb.append("\n");
            }
        } else {
            // Show only THIS student's grades
            Student currentStudent = (Student) controller.getUser();
            sb.append(currentStudent.getFirstName()).append(" ").append(currentStudent.getLastName()).append(":\n");

            StudentGradebook gb = currentStudent.getGradebookForCourse(course);
            if (gb == null || gb.getAssignments().isEmpty()) {
                sb.append("  No grades available.\n");
            } else {
                double totalEarned = 0;
                double totalPossible = 0;

                for (Assignment a : gb.getAssignments()) {
                    sb.append("  ").append(a.getTitle())
                      .append(" - ").append(a.getPointsEarned())
                      .append("/").append(a.getMaxPoints()).append("\n");
                    totalEarned += a.getPointsEarned();
                    totalPossible += a.getMaxPoints();
                }

                double percentage = gb.calculateFinalGrade();
                sb.append("  Total: ").append(String.format("%.2f", totalEarned)).append("/")
                  .append(String.format("%.2f", totalPossible))
                  .append(" (").append(String.format("%.2f", percentage)).append("%)\n");

                if (gb.getFinalGrade() != null) {
                    sb.append("  Final Grade: ").append(gb.getFinalGrade()).append("\n");
                }
            }
        }

        return sb.toString();
    }

    /**
     * == CENTRALIZED TO SIMPLIFY BACK BUTTON LOGIC AND NOT REBUILD THE VIEW BUT JUST CALL THIS ==
     * Centralized routine to show the Classlist view with sorting dropdown.
     */
    
    private void studentShowClassListView() {
        // 1) Populate the infoArea with the unsorted classlist
        updateSortedClasslist(0);

        // 2) Adjust button visibility
        sideAddAssignmentBtn.setVisible(false);
        gradeStudentBtn.setVisible(false);
        setFinalGradesBtn.setVisible(false);
        addStudentBtn.setVisible(false);
        removeStudentBtn.setVisible(false);
        classStatsLabel.setVisible(false);
        
        // 3) Remove any old JComboBox
        for (Component comp : contentPanel.getComponents()) {
            if (comp instanceof JComboBox) {
                contentPanel.remove(comp);
            }
        }
        
    }
    private void showClasslistView() {
        // 1) Populate the infoArea with the unsorted classlist
        updateSortedClasslist(0);

        // 2) Adjust button visibility
        sideAddAssignmentBtn.setVisible(false);
        gradeStudentBtn.setVisible(false);
        setFinalGradesBtn.setVisible(false);
        addStudentBtn.setVisible(true);
        removeStudentBtn.setVisible(true);
        classStatsLabel.setVisible(false);

        // 3) Remove any old JComboBox
        for (Component comp : contentPanel.getComponents()) {
            if (comp instanceof JComboBox) {
                contentPanel.remove(comp);
            }
        }

        // 4) Create and add the sort-by dropdown
        JComboBox<String> sortOptions = new JComboBox<>(new String[] {
            "Sort by First Name",
            "Sort by Last Name",
            "Sort by Username",
            "Sort by grades on an assignment"
        });
        sortOptions.addActionListener(evt -> {
            int idx = sortOptions.getSelectedIndex();
            if (idx == 3) {
                // --- Show assignment-selection dialog ---
                ArrayList<String> allAssignments = new ArrayList<>();
                for (String cat : controller.getSelectedCourse().getCategories().keySet()) {
                    for (Assignment a : controller.getSelectedCourse()
                                                 .getCategories().get(cat)
                                                 .getAssignments()) {
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
                    showAssignmentGradesDialog(assignmentName);
                }
            } else {
                updateSortedClasslist(idx);
            }
        });

        contentPanel.add(sortOptions, BorderLayout.NORTH);
        contentPanel.revalidate();
        contentPanel.repaint();
    }

   /**
    * === CREATES A POP UP FOR SHOW ASSIGNMENT GRADES SORTING ===
   */
    private void showAssignmentGradesDialog(String assignmentName) {
        Course course = controller.getSelectedCourse();

        // 1) Copy & sort the students descending by that assignment’s grade
        List<Student> sorted = new ArrayList<>();
        for (Student s : course.getStudents()) {
            sorted.add(s);
        }
        Comparator<Student> byGradeDesc =
            Student.sortByGradeOnAssignment(assignmentName).reversed();
        sorted.sort(byGradeDesc);

        // 2) Build the text content
        StringBuilder sb = new StringBuilder();
        sb.append("Grades for “").append(assignmentName).append("” in ")
          .append(course.getName()).append(":\n\n");
        for (Student s : sorted) {
            sb.append(s.getFirstName()).append(" ").append(s.getLastName())
              .append(" – ").append(s.getEmail());

            StudentGradebook gb = s.getGradebookForCourse(course);
            Assignment a = (gb == null) ? null :
                gb.getAssignments().stream()
                  .filter(x -> x.getTitle().equals(assignmentName))
                  .findFirst().orElse(null);

            if (a != null) {
                double pct = a.getPointsEarned() / a.getMaxPoints() * 100;
                sb.append(String.format(" → %.2f%% (%s)", pct, calculateFinalGrade(pct)));
            } else {
                sb.append(" → No grade");
            }
            sb.append("\n");
        }

        // 3) Create and style the text area
        JTextArea ta = new JTextArea(sb.toString());
        ta.setEditable(false);
        Font uiFont = UIManager.getFont("Label.font");
        ta.setFont(uiFont);
        ta.setLineWrap(true);
        ta.setWrapStyleWord(true);

        // 4) Wrap in a scroll pane at a reasonable size
        JScrollPane scroll = new JScrollPane(ta);
        scroll.setPreferredSize(new Dimension(500, 400));

        // 5) Fire off the modal dialog
        JOptionPane.showMessageDialog(
            this,
            scroll,
            "Grades for “" + assignmentName + "”",
            JOptionPane.PLAIN_MESSAGE
        );
    }

    
    /** 
     * === ONLY NEED ONE UPDATEDSORTEDCLASSLIST HELPER ===
     * Sorts the in-place classlist by the given option (0–2). */
    private void updateSortedClasslist(int sortOption) {
        Course course = controller.getSelectedCourse();
        StudentList students = new StudentList(course.getStudents());

        switch (sortOption) {
            case 0: students.sortByFirstName(); break;
            case 1: students.sortByLastName();  break;
            case 2: students.sortByUsername();  break;
        }

        StringBuilder sb = new StringBuilder();
        sb.append("Classlist for ").append(course.getName()).append(":\n\n");
        for (Student s : students) {
            sb.append(s.getFirstName()).append(" ").append(s.getLastName())
              .append(" – ").append(s.getEmail()).append("\n");
        }
        infoArea.setText(sb.toString());
    }
    
    // Helper method to convert percentage to grade
    private FinalGrades calculateFinalGrade(double percentage) {
        if (percentage >= 90) return FinalGrades.A;
        if (percentage >= 80) return FinalGrades.B;
        if (percentage >= 70) return FinalGrades.C;
        if (percentage >= 60) return FinalGrades.D;
        return FinalGrades.E;
    }

}