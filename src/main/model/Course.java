package main.model;

import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.stream.Collectors;

import javax.swing.*;
import java.awt.*;
import main.view.NumberField;

public class Course {
    private String code;
    private String name;
    private String description;
    private Instructor instructor;
    private ArrayList<Days> daysOfWeek;
    private StudentList studentList;
    
    private double weight;           // percentage of final grade
    private int dropLowest;          // how many to drop
    
    private CourseAssignments assignments;
    private HashMap<String, Category> categories;
    private HashMap<String, JLabel> assignmentLabels = new HashMap<String, JLabel>();

    
    public Course(String name, String code, String description,
                  Instructor instructor, ArrayList<Days> daysOfWeek) {
        
        this.name = name;
        this.code = code;
        
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
        ArrayList<Student> tempStudents = new ArrayList<Student>();
        ArrayList<JCheckBox> tempCheckboxes = new ArrayList<JCheckBox>();
        // constructor pulls from users.csv
        for(User user : UserDatabase.getInstance()) {
            if(user instanceof Student) {
                Student student = (Student) user;
                tempStudents.add(student);
                // this.studentList.add(student);
                // student.addCourse(this);
            }
        }
        JPanel addStudentsPanel = new JPanel();
        addStudentsPanel.setLayout(new GridLayout(0,2));
        for(Student student : tempStudents) {
            addStudentsPanel.add(new JLabel(student.getFirstName() + " " + student.getLastName()));
            JCheckBox checkbox = new JCheckBox();
            tempCheckboxes.add(checkbox);
            addStudentsPanel.add(checkbox);
        }

        JButton addButton = new JButton("Add Selected Students");
        addStudentsPanel.add(addButton);
        addButton.addActionListener(e -> {
            for(int i = 0; i < tempStudents.size(); i++) {
                if(tempCheckboxes.get(i).isSelected()) {
                    this.studentList.add(tempStudents.get(i));
                    tempStudents.get(i).addCourse(this);
                }
            }
        });
        JOptionPane.showMessageDialog(null, addStudentsPanel, "Add Students", JOptionPane.PLAIN_MESSAGE);
    }

    public void setDefaultCategories() {
        // Create default categories without adding assignments
        Category homeworks = new Category("Homeworks", 250, 25);
        categories.put("Homeworks", homeworks);
        Category projects = new Category("Projects", 250, 50);
        categories.put("Projects", projects);
        Category midtermExam = new Category("Midterm Exam", 250, 250);
        categories.put("Midterm Exam", midtermExam);
        Category finalExam = new Category("Final Exam", 250, 250);
        categories.put("Final Exam", finalExam);
    }
    
    public void promptAddAssignment() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
    
        JLabel titleLabel = new JLabel("Assignment Title:");
        JTextField titleField = new JTextField(20);
        panel.add(titleLabel);
        panel.add(titleField);
    
        JLabel descriptionLabel = new JLabel("Description:");
        JTextArea descriptionField = new JTextArea(5, 20);
        descriptionField.setLineWrap(true);
        descriptionField.setWrapStyleWord(true);
        panel.add(descriptionLabel);
        panel.add(new JScrollPane(descriptionField));
    
        JLabel categoryLabel = new JLabel("Category:");
        JComboBox<String> categoryField = new JComboBox<>(categories.keySet().toArray(new String[0]));
        panel.add(categoryLabel);
        panel.add(categoryField);
    
        JLabel maxPointsLabel = new JLabel("Max Points:");
        JTextField maxPointsField = new JTextField(5);
        panel.add(maxPointsLabel);
        panel.add(maxPointsField);
    
        int result = JOptionPane.showConfirmDialog(null, panel, "Add Assignment", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            String title = titleField.getText();
            String description = descriptionField.getText();
            String category = (String) categoryField.getSelectedItem();
            int maxPoints;
            try {
                maxPoints = Integer.parseInt(maxPointsField.getText());
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Invalid max points value. Please enter a valid number.");
                return;
            }
    
            Assignment assignment = new Assignment(title, description, maxPoints);
            categories.get(category).addAssignment(assignment);
            this.addAssignment(assignment);
    
            // Add the assignment to all students
            for (Student student : studentList) {
                student.getGradebookForCourse(this).addAssignment(new Assignment(assignment));
            }
    
            JOptionPane.showMessageDialog(null, "Assignment added successfully!");
        }
    }
      
    public HashMap<String, Category> getCategories(){
    	return this.categories;
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
    
    /** Add a brand-new category with its weight and how many to drop. */
    public void addCategory(String name, int weightPts, int dropLowest) {
      Category c = new Category(name, weightPts, dropLowest);
      categories.put(name, c);
    }
    /** Remove one of the existing categories. */
    public void removeCategory(String name) {
      categories.remove(name);
    }
    
    /** called by the Manage Categories dialog */
    public void setCategoryWeight(String categoryName, int weight) {
      Category cat = categories.get(categoryName);
      if (cat != null) cat.setPoints(weight);
    }

    /** called by the Manage Categories dialog */
    public void setCategoryDropLowest(String categoryName, int dropLowest) {
      Category cat = categories.get(categoryName);
      if (cat != null) cat.setDropLowest(dropLowest);
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

        categoryField.addActionListener(e -> {
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
    
    public JTabbedPane getCourseView(User user) {
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
        gbc.gridy = 1;
        panel.add(emptyLabel, gbc);
    
        JLabel instructorLabel = new JLabel("Instructors:");
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        panel.add(instructorLabel, gbc);
    
        JLabel instructorName = new JLabel(instructor.getFullName());
        gbc.gridx = 1;
        panel.add(instructorName, gbc);
    
        JLabel categoryLabel = new JLabel("Category Weights:");
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        panel.add(categoryLabel, gbc);
    
        int gridYIterator = 4;
        for (String category : categories.keySet()) {
            JLabel categoryName = new JLabel(category);
            gbc.gridx = 0;
            gbc.gridy = gridYIterator;
            gbc.gridwidth = 1;
            panel.add(categoryName, gbc);
    
            JLabel categoryWeight = new JLabel(categories.get(category).getPoints() + " pts");
            gbc.gridx = 1;
            panel.add(categoryWeight, gbc);
            gridYIterator++;
        }
    
        // Create tabbed pane and add course info panel
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Course Info", panel);
    
        // Assignments Panel
        JPanel assignmentsPanel = new JPanel();
        assignmentsPanel.setLayout(new BoxLayout(assignmentsPanel, BoxLayout.Y_AXIS));
    
        JButton addAssignmentButton = new JButton("Add Assignment");
        addAssignmentButton.addActionListener(e -> {
            JOptionPane.showMessageDialog(null, this.getAssignmentAddPanel(), this.getCourseCode(), JOptionPane.PLAIN_MESSAGE);
        });
        assignmentsPanel.add(addAssignmentButton);
    
        // Classlist Panel
        JPanel classListPanel = new JPanel();
        classListPanel.setLayout(new GridLayout(Math.max(studentList.size(), 1), 1));
        if (studentList.isEmpty()) {
            classListPanel.add(new JLabel("No students enrolled."));
        } else {
            for (Student student : studentList) {
                classListPanel.add(new JLabel(student.getFirstName() + " " + student.getLastName()));
            }
        }
    
        // Wrap assignmentsPanel in scroll pane
        JScrollPane scrollPane = new JScrollPane(assignmentsPanel);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
    
        tabbedPane.addTab("Assignments", scrollPane);
        tabbedPane.addTab("Classlist", new JScrollPane(classListPanel));
    
        return tabbedPane;
    }

    private ArrayList<Assignment> getAssignmentsByName(String assignmentName) {
        ArrayList<Assignment> assignments = new ArrayList<>();
        for(Student s: getStudents()) {
            StudentGradebook gb = s.getGradebookForCourse(this);
            if(gb == null) continue;
            List<Assignment> filteredAssignments = gb.getAssignments().stream()
                .filter(assignment -> assignment.getTitle().equals(assignmentName))
                .collect(Collectors.toList());
            assignments.addAll(filteredAssignments);
        }
        System.out.println(assignments.size());
        return assignments;
    }

    private void updateAssignmentLabels(String assignmentName) {
        ArrayList<Assignment> assignments = getAssignmentsByName(assignmentName);
        List<Assignment> gradedAssignments = assignments.stream().filter(assignment -> assignment.isGraded()).collect(Collectors.toList());
        System.out.println(gradedAssignments.size());
        double mean = gradedAssignments.stream().mapToDouble(Assignment::getPointsEarned)
            .average()
            .orElse(0.0); 
        double median = gradedAssignments.stream().mapToDouble(Assignment::getPointsEarned)
            .sorted()
            .boxed()
            .collect(Collectors.toList())
            .get(gradedAssignments.size() / 2);
        assignmentLabels.get(assignmentName).setText("Mean: " + String.format("%.2f", mean) + " Median: " + median);
    }
    
    
    
    public enum CalculationMode { POINTS_SUM, WEIGHTED }

	// add to Course fields:
	private CalculationMode calculationMode = CalculationMode.WEIGHTED;
	
	// getters / setters:
	public CalculationMode getCalculationMode() { return calculationMode; }
	public void setCalculationMode(CalculationMode m) { this.calculationMode = m; }

    
}