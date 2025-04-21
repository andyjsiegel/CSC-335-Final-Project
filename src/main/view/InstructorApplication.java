package main.view;


import main.model.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class InstructorApplication extends JFrame {
    private Instructor currentInstructor;
    private ArrayList<Course> courses;
    private ArrayList<Student> allStudents;
    private JTabbedPane tabbedPane;

    public InstructorApplication(Instructor instructor) {
        this.currentInstructor = instructor;
        this.courses = new ArrayList<>();
        this.allStudents = new ArrayList<>();
        
        // Initialize some sample students (in the real app, these would come from a database or from the read csv file)
        initializeSampleStudents();
        
        setTitle("Instructor Dashboard - " + instructor.getName());
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        tabbedPane = new JTabbedPane();
        add(tabbedPane);
        
        // Create tabs
        createCoursesTab();
        createStudentsTab();
        
        setVisible(true);
    }
    
    // just the default students that are there, would be to see how to add
    private void initializeSampleStudents() {
        allStudents.add(new Student("student1", "pass1", "marvin beak", false));
        allStudents.add(new Student("student2", "pass2", "tim cheese", false));
        allStudents.add(new Student("student3", "pass3", "john pork", false));
        allStudents.add(new Student("student4", "pass4", "agent 5.5", false));
    }
    
    private void createCoursesTab() {
        JPanel coursesPanel = new JPanel(new BorderLayout());
        
        // Course list
        DefaultListModel<Course> courseListModel = new DefaultListModel<>();
        JList<Course> courseList = new JList<>(courseListModel);
        courseList.setCellRenderer(new CourseListRenderer());
        
        // Buttons for course management
        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton createCourseBtn = new JButton("Create New Course");
        JButton addStudentBtn = new JButton("Add Student to Course");
        JButton addAssignmentBtn = new JButton("Add Assignment to Course");
        JButton viewCurrentCalendarBtn = new JButton("View Calendar");
        
        buttonPanel.add(createCourseBtn);
        buttonPanel.add(addStudentBtn);
        buttonPanel.add(addAssignmentBtn);
        buttonPanel.add(viewCurrentCalendarBtn);
        
        // Add components to panel
        coursesPanel.add(new JScrollPane(courseList), BorderLayout.CENTER);
        coursesPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        tabbedPane.addTab("Courses", coursesPanel);
        
        // Button actions
        createCourseBtn.addActionListener(e -> {
            createNewCourseDialog();
            courseListModel.clear();
            courses.forEach(courseListModel::addElement);
        });
        
        addStudentBtn.addActionListener(e -> {
            Course selectedCourse = courseList.getSelectedValue();
            if (selectedCourse != null) {
                addStudentToCourseDialog(selectedCourse);
            } else {
                JOptionPane.showMessageDialog(this, "Please select a course first");
            }
        });
        
        addAssignmentBtn.addActionListener(e -> {
            Course selectedCourse = courseList.getSelectedValue();
            if (selectedCourse != null) {
                addAssignmentToCourseDialog(selectedCourse);
            } else {
                JOptionPane.showMessageDialog(this, "Please select a course first");
            }
        });
        
        viewCurrentCalendarBtn.addActionListener(e -> {


            	openCalendarView();

        });
        
        courseList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {  // Double click
                    Course selectedCourse = courseList.getSelectedValue();
                    if (selectedCourse != null) {
                    	//openCalendarView();
                        showCourseDetailsDialog(selectedCourse);
                    }
                }
            }
        });
    }
    
    private void openCalendarView() {
        CalendarView calendarView = new CalendarView(currentInstructor);
        calendarView.setVisible(true);
    }
    private void createStudentsTab() {
        JPanel studentsPanel = new JPanel(new BorderLayout());
        
        // Student list
        DefaultListModel<Student> studentListModel = new DefaultListModel<>();
        allStudents.forEach(studentListModel::addElement);
        JList<Student> studentList = new JList<>(studentListModel);
        
        studentsPanel.add(new JScrollPane(studentList), BorderLayout.CENTER);
        
        tabbedPane.addTab("Students", studentsPanel);
    }
    
    private void createNewCourseDialog() {
        JDialog dialog = new JDialog(this, "Create New Course", true);
        dialog.setLayout(new GridLayout(0, 2));
        dialog.setSize(400, 400);
        
        // Form fields
        JTextField nameField = new JTextField();
        JTextArea descriptionArea = new JTextArea(3, 20);
        JScrollPane descriptionScroll = new JScrollPane(descriptionArea);
        JTextField creditsField = new JTextField();
        JTextField courseCodeField = new JTextField();
        
        // Day selection checkboxes
        JCheckBox monCheck = new JCheckBox("Monday");
        JCheckBox tueCheck = new JCheckBox("Tuesday");
        JCheckBox wedCheck = new JCheckBox("Wednesday");
        JCheckBox thuCheck = new JCheckBox("Thursday");
        JCheckBox friCheck = new JCheckBox("Friday");
        
        JPanel daysPanel = new JPanel(new GridLayout(2, 4));
        daysPanel.add(monCheck);
        daysPanel.add(tueCheck);
        daysPanel.add(wedCheck);
        daysPanel.add(thuCheck);
        daysPanel.add(friCheck);
        
        // Add fields to dialog
        dialog.add(new JLabel("Course Name:"));
        dialog.add(nameField);
        dialog.add(new JLabel("Description:"));
        dialog.add(descriptionScroll);
        dialog.add(new JLabel("Credits:"));
        dialog.add(creditsField);
        dialog.add(new JLabel("Course Code:"));
        dialog.add(courseCodeField);
        dialog.add(new JLabel("Days:"));
        dialog.add(daysPanel);
        
        // Buttons
        JButton createBtn = new JButton("Create");
        JButton cancelBtn = new JButton("Cancel");
        
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(createBtn);
        buttonPanel.add(cancelBtn);
        
        dialog.add(new JLabel());
        dialog.add(buttonPanel);
        
        // Button actions
        createBtn.addActionListener(e -> {
            try {
                String name = nameField.getText();
                String description = descriptionArea.getText();
                int credits = Integer.parseInt(creditsField.getText());
                String courseCode = courseCodeField.getText();
                
                Course newCourse = new Course(name, description, courseCode);
                
                // Add only selected days
                ArrayList<Days> selectedDays = new ArrayList<>();
                if (monCheck.isSelected()) selectedDays.add(Days.MONDAY);
                if (tueCheck.isSelected()) selectedDays.add(Days.TUESDAY);
                if (wedCheck.isSelected()) selectedDays.add(Days.WEDNESDAY);
                if (thuCheck.isSelected()) selectedDays.add(Days.THURSDAY);
                if (friCheck.isSelected()) selectedDays.add(Days.FRIDAY);
                
                newCourse.addDays(selectedDays);
                
                courses.add(newCourse);
                currentInstructor.addCourse(newCourse);
                currentInstructor.addDetailsClass();
                
                dialog.dispose();
                JOptionPane.showMessageDialog(this, "Course created successfully!");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(dialog, "Please enter valid numbers for credits");
            }
        });
        
        cancelBtn.addActionListener(e -> dialog.dispose());
        
        dialog.setVisible(true);
    }
    
    private void addStudentToCourseDialog(Course course) {
        JDialog dialog = new JDialog(this, "Add Student to Course", true);
        dialog.setLayout(new BorderLayout());
        
        // Student selection
        DefaultListModel<Student> studentListModel = new DefaultListModel<>();
        allStudents.forEach(studentListModel::addElement);
        JList<Student> studentList = new JList<>(studentListModel);
        studentList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        
        // Buttons
        JButton addBtn = new JButton("Add Selected Students");
        JButton cancelBtn = new JButton("Cancel");
        
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addBtn);
        buttonPanel.add(cancelBtn);
        
        // Add components to dialog
        dialog.add(new JScrollPane(studentList), BorderLayout.CENTER);
        dialog.add(buttonPanel, BorderLayout.SOUTH);
        
        // Button actions
        addBtn.addActionListener(e -> {
            for (Student student : studentList.getSelectedValuesList()) {
                course.addStudent(student);
            }
            dialog.dispose();
            JOptionPane.showMessageDialog(this, "Students added to course!");
        });
        
        cancelBtn.addActionListener(e -> dialog.dispose());
        
        dialog.setSize(400, 300);
        dialog.setVisible(true);
    }
    
    private void addAssignmentToCourseDialog(Course course) {
        JDialog dialog = new JDialog(this, "Add Assignment to Course", true);
        dialog.setLayout(new GridLayout(0, 2));
        
        // Form fields
        JTextField nameField = new JTextField();
        JTextArea descriptionArea = new JTextArea(3, 20);
        JScrollPane descriptionScroll = new JScrollPane(descriptionArea);
        JTextField totalPointsField = new JTextField();
        
        // Add fields to dialog
        dialog.add(new JLabel("Assignment Name:"));
        dialog.add(nameField);
        dialog.add(new JLabel("Description:"));
        dialog.add(descriptionScroll);
        dialog.add(new JLabel("Total Points:"));
        dialog.add(totalPointsField);
        
        // Buttons
        JButton addBtn = new JButton("Add Assignment");
        JButton cancelBtn = new JButton("Cancel");
        
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addBtn);
        buttonPanel.add(cancelBtn);
        
        dialog.add(new JLabel());
        dialog.add(buttonPanel);
        
        // Button actions
        addBtn.addActionListener(e -> {
            try {
                String name = nameField.getText();
                String description = descriptionArea.getText();
                Double totalPoints = Double.parseDouble(totalPointsField.getText());
                
                Assignment newAssignment = new Assignment(name, description, totalPoints);
                course.addAssignment(newAssignment);
                
                dialog.dispose();
                JOptionPane.showMessageDialog(this, "Assignment added successfully!");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(dialog, "Error creating assignment");
            }
        });
        
        cancelBtn.addActionListener(e -> dialog.dispose());
        
        dialog.setSize(400, 300);
        dialog.setVisible(true);
    }
    
    // Custom renderer for course list
    private class CourseListRenderer extends DefaultListCellRenderer {
        @Override
        public Component getListCellRendererComponent(JList<?> list, Object value, int index, 
                                                     boolean isSelected, boolean cellHasFocus) {
            super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
            if (value instanceof Course) {
                Course course = (Course) value;
                setText(course.getName() + " (" + course.getCourseCode() + ")");
            }
            return this;
        }
    }
    
    private void showCourseDetailsDialog(Course course) {
        JDialog dialog = new JDialog(this, "Course Details - " + course.getName(), true);
        dialog.setSize(500, 400);
        dialog.setLayout(new BorderLayout());

        JTextArea infoArea = new JTextArea();
        infoArea.setEditable(false);

        StringBuilder sb = new StringBuilder();
        sb.append("Course Name: ").append(course.getName()).append("\n");
        sb.append("Course Code: ").append(course.getCourseCode()).append("\n");
        sb.append("Description: ").append(course.getDescription()).append("\n");

        sb.append("Students:\n");
        for (Student student : course.getStudents()) {
            sb.append("- ").append(student.getName()).append("\n");
        }

        sb.append("\nAssignments:\n");
        System.out.println(course.getAssignments().size());
        for (Assignment assignment : course.getAssignments()) {
            sb.append("- ").append(assignment.getName())
              .append(" (").append(assignment.getMaxPoints()).append(" points)\n");
        }

        infoArea.setText(sb.toString());

        dialog.add(new JScrollPane(infoArea), BorderLayout.CENTER);

        JButton closeBtn = new JButton("Close");
        closeBtn.addActionListener(e -> dialog.dispose());

        JPanel btnPanel = new JPanel();
        btnPanel.add(closeBtn);

        dialog.add(btnPanel, BorderLayout.SOUTH);
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }

    //private static Instructor instructor;
    public static void main(String[] args) {

        //Instructor instructor = new Instructor("instructor1", "pass123", "Dr. Smith", false);
    	Instructor instructor = new Instructor("instructor1", "pass123", "Dr. Smith", false);
        
        SwingUtilities.invokeLater(() -> new InstructorApplication(instructor));
    }
}