package main.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Arrays;

import javax.swing.*;
import java.awt.*;
import main.view.NumberField;

public class Course {
    private String code;
    private String name;
    private String credits;
    private String description;
    private Instructor instructor;
    private ArrayList<Days> daysOfWeek;
    private StudentList studentList;

    private CourseAssignments assignments;
    private HashMap<String, Category> categories;

    
    public Course(String name, String code, String credits, String description,
                  Instructor instructor, ArrayList<Days> daysOfWeek) {
        
        this.name = name;
        this.code = code;
        this.credits = credits;
        
    	this.description = description;
        this.instructor = instructor;
        this.daysOfWeek = new ArrayList<Days>();
        this.daysOfWeek = daysOfWeek;
    	this.studentList = new StudentList();
    	this.categories = new HashMap<String, Category>();
        this.assignments = new CourseAssignments();
    }

    //NOT USED ATM
    public Course(Course other) {
        this.name = other.name;
        this.code = other.code;
        this.description = other.description;
        this.instructor = new Instructor(other.instructor);
        this.daysOfWeek = new ArrayList<Days>(other.daysOfWeek);
        this.studentList = new StudentList(other.studentList);
        //this.assignments = new CourseAssignments(other.assignments);
    }

    public void addAllStudentsFromPool() {
        // constructor pulls from users.csv
        for(User user : new UserDatabase()) {
            if(user instanceof Student) {
                this.studentList.add((Student) user);
            }
        }
    }

    public void setDefaultCategories() {
        // new Category(name, points, expPointsPerAssignment)
        Category homeworks = new Category("Homeworks", 250, 25);
        homeworks.addDefaultAssignments();
        categories.put("Homeworks", homeworks);
        Category projects = new Category("Projects", 250, 50);
        projects.addDefaultAssignments();
        categories.put("Projects", projects);
        Category midtermExam = new Category("Midterm Exam", 250, 250);
        midtermExam.addDefaultAssignments();
        categories.put("Midterm Exam", midtermExam);
        Category finalExam = new Category("Final Exam", 250, 250);
        finalExam.addDefaultAssignments();
        categories.put("Final Exam", finalExam);
    }

    public CourseAssignments getAssignments() {
        return assignments;
    }
    
    public StudentList getStudents() {
        return studentList;
    }

    public void addStudent(Student student) {
    	this.studentList.add(student);
    }
    
    public void removeStudent(Student student) {
    	this.studentList.remove(student);
    }
    
    public void addAssignment(Assignment assignment) {
    	this.assignments.add(assignment);
    }
    
    public void removeAssignment(Assignment assignment) {
    	this.assignments.remove(assignment);
    }
    
    public void addDays(ArrayList<Days> daysOfWeek) {
    	this.daysOfWeek = daysOfWeek;
    }

    public String getName() {
        return name;
    }

    public String getCode() {
        return code;
    }

    public String getCourseCode() {
    	return this.code;
    }
    public String getDescription() {
        return description;
    }

    public Instructor getInstructor() {
        return instructor;
    }

    public ArrayList<Days> getDays() {
        return daysOfWeek;
    }

    public JPanel getAssignmentAddPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createTitledBorder("Add Assignment"));
        
        JLabel titleLabel = new JLabel("Title:");
        panel.add(titleLabel);
        JTextField titleField = new JTextField();
        titleField.setColumns(20);
        panel.add(titleField);

        JLabel descriptionLabel = new JLabel("Description:");
        panel.add(descriptionLabel);
        JTextArea descriptionField = new JTextArea();
        descriptionField.setColumns(20);
        descriptionField.setRows(5);
        descriptionField.setLineWrap(true);
        descriptionField.setWrapStyleWord(true);
        panel.add(descriptionField);

        JLabel categoryLabel = new JLabel("Category:");
        panel.add(categoryLabel);
        JComboBox<String> categoryField = new JComboBox<String>(categories.keySet().toArray(new String[0]));
        categoryField.setSelectedIndex(0);
        
        panel.add(categoryField);

        JLabel maxPointsLabel = new JLabel("Max Points:");
        panel.add(maxPointsLabel);
        NumberField maxPointsField = new NumberField();
        maxPointsField.setColumns(5);
        maxPointsField.setText(categories.get(categoryField.getSelectedItem().toString()).getExpectedPoints() + "");
        panel.add(maxPointsField);

        categoryField.addActionListener(_ -> {
            String selectedCategory = (String) categoryField.getSelectedItem();
            maxPointsField.setText(categories.get(selectedCategory).getExpectedPoints() + "");
        });

        JButton addButton = new JButton("Add");
        addButton.addActionListener(e -> {
            String title = titleField.getText();
            String description = descriptionField.getText();
            String category = categoryField.getSelectedItem().toString();
            int maxPoints = Integer.parseInt(maxPointsField.getText());
            Assignment assignment = new Assignment(title, description, maxPoints);
            categories.get(category).addAssignment(assignment);
            this.addAssignment(assignment);
            titleField.setText("");
            descriptionField.setText("");
            categoryField.setSelectedIndex(0);
        });
        panel.add(addButton);
        return panel;
    }
    
    public JTabbedPane getCourseView(boolean isInstructor) {
        // Main panel with course details
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
    
        JLabel nameLabel = new JLabel(name);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(5, 10, 5, 10);
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(nameLabel, gbc);
        panel.setBackground(Color.GRAY);
        panel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
        panel.setOpaque(false);
    
        JLabel emptyLabel = new JLabel("");
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(5, 10, 5, 10);
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(emptyLabel, gbc);
    
        JLabel instructorLabel = new JLabel("Instructors:");
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        gbc.insets = new Insets(5, 10, 5, 10);
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(instructorLabel, gbc);
    
        //for (Instructor instructor : instructors) {
            JLabel instructorName = new JLabel(instructor.getFullName());
            gbc.gridx = 1;
            gbc.gridy = 2;
            gbc.gridwidth = 1;
            gbc.insets = new Insets(5, 10, 5, 10);
            gbc.anchor = GridBagConstraints.WEST;
            panel.add(instructorName, gbc);
        //}
    
        JLabel categoryLabel = new JLabel("Category Weights:");
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(5, 10, 5, 10);
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(categoryLabel, gbc);
    
        int gridYIterator = 4;
        for (String category : categories.keySet()) {
            JLabel categoryName = new JLabel(category);
            gbc.gridx = 0;
            gbc.gridy = gridYIterator;
            gbc.gridwidth = 1;
            gbc.insets = new Insets(5, 10, 5, 10);
            gbc.anchor = GridBagConstraints.WEST;
            panel.add(categoryName, gbc);
    
            JLabel categoryWeight = new JLabel(String.valueOf(categories.get(category).getPoints()) + " pts");
            gbc.gridx = 1;
            gbc.gridy = gridYIterator;
            gbc.gridwidth = 1;
            gbc.insets = new Insets(5, 10, 5, 10);
            gbc.anchor = GridBagConstraints.WEST;
            panel.add(categoryWeight, gbc);
            gridYIterator++;
        }
    
        // Create tabbed pane and add course detail panel as one tab
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Course Info", panel);
    
        JPanel assignmentsPanel = new JPanel();
        assignmentsPanel.setLayout(new BoxLayout(assignmentsPanel, BoxLayout.Y_AXIS));
        
        JButton addAssignmentButton = new JButton("Add Assignment");
        addAssignmentButton.addActionListener(_ -> {
            JOptionPane.showMessageDialog(null, this.getAssignmentAddPanel(), this.getCode(), JOptionPane.PLAIN_MESSAGE);
        });
        assignmentsPanel.add(addAssignmentButton);
        // keep a list of JCheckboxes in order
        ArrayList<JCheckBox> studentCheckboxes = new ArrayList<JCheckBox>();

        ArrayList<String> categoryTitles = new ArrayList<String>(categories.keySet());
        Collections.sort(categoryTitles);
        for (String category : categoryTitles) {
            ArrayList<Assignment> assignments = categories.get(category).getAssignments();

            // Header button for collapsible section
            JToggleButton toggleButton = new JToggleButton(category + " [" + categories.get(category).getPoints() + " pts]");
            toggleButton.setAlignmentX(Component.LEFT_ALIGNMENT);

            // Panel to hold assignments for this category
            JPanel assignmentsListPanel = new JPanel();
            JLabel assignmentsGraded = new JLabel();
            assignmentsGraded.setBorder(BorderFactory.createEmptyBorder(2, 20, 2, 2));
            assignmentsListPanel.setLayout(new BoxLayout(assignmentsListPanel, BoxLayout.Y_AXIS));
            assignmentsListPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
            assignmentsListPanel.add(assignmentsGraded);

            // Add each assignment as a label or more complex component
            int numGraded = 0;
            int pointsEarned = 0;
            int pointsPossible = categories.get(category).getPoints();
            for (Assignment assignment : assignments) {
                // assignment.setGradeTo100();
                if(assignment.getGrade() >= 0) {
                    pointsEarned += assignment.getGrade();
                    numGraded++;
                }
                JLabel assignmentLabel = new JLabel(assignment.getTitle() + " : " + (assignment.getGrade() >= 0 ? assignment.getGrade() + "/" + assignment.getMaxPoints() : "--/" + assignment.getMaxPoints()));
                assignmentLabel.setBorder(BorderFactory.createEmptyBorder(2, 20, 2, 2));
                assignmentsListPanel.add(assignmentLabel);
                JButton gradeAssignmentButton = new JButton("Grade");
                gradeAssignmentButton.addActionListener(_ -> {
                    String grade = JOptionPane.showInputDialog("How many points did the selected users get?"); 
                    double gradeEarned = Double.parseDouble(grade);
                    for(int i = 0; i < studentCheckboxes.size(); i++) {
                        JCheckBox checkbox = studentCheckboxes.get(i);
                        if(checkbox.isSelected()) {
                            Student student = this.getStudents().get(i);
                            student.getGradebook().addAssignment(assignment, gradeEarned);
                        }
                    }
                    // this.getStudents().get();  
                });
                assignmentsListPanel.add(gradeAssignmentButton);
            }
            
            assignmentsGraded.setText(numGraded + "/" + assignments.size() + " " + category + " graded: " + pointsEarned + "/" + pointsPossible + " points earned");
            // Initially collapsed
            assignmentsListPanel.setVisible(false);

            // Toggle visibility on button click
            toggleButton.addActionListener(e -> {
                assignmentsListPanel.setVisible(toggleButton.isSelected());
                // Revalidate and repaint to update UI
                assignmentsPanel.revalidate();
                assignmentsPanel.repaint();
            });

            // Add header and assignments list to main assignments panel
            assignmentsPanel.add(toggleButton);
            assignmentsPanel.add(assignmentsListPanel);
        }

        JPanel classListPanel = new JPanel();
        if(studentList.isEmpty()) {
            classListPanel.add(new JLabel("No students enrolled."));
        }
        classListPanel.setLayout(new GridLayout(studentList.size(), 2));
        for(Student student : studentList) {
            classListPanel.add(new JLabel(student.getFirstName() + " " + student.getLastName()));
            if(isInstructor) {
                JCheckBox checkbox = new JCheckBox();
                studentCheckboxes.add(checkbox);
                classListPanel.add(checkbox);
            } 
        }

        // Wrap assignmentsPanel in a JScrollPane in case of many assignments/categories
        JScrollPane scrollPane = new JScrollPane(assignmentsPanel);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        tabbedPane.addTab("Assignments", scrollPane);
        tabbedPane.addTab("Classlist", new JScrollPane(classListPanel));

        return tabbedPane;
    }
}