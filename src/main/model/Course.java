package main.model;

import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.*;
import java.awt.*;
import java.time.Duration;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Course {
    private ArrayList<Student> students;
    private ArrayList<Instructor> instructors;
    private HashMap<String, Double> categoryWeights;
    private String name;
    private String description;
    private int credits;
    private LocalTime startTime;
    private LocalTime endTime;
    private ArrayList<String> daysOfWeek;
    private String courseCode;

    public Course(String name, String description, int credits, ArrayList<String> daysOfWeek, LocalTime startTime, LocalTime endTime, HashMap<String, Double> categoryWeights, String courseCode) {
        this.name = name;
        this.description = description;
        this.courseCode = courseCode;
        this.credits = credits;
        this.students = new ArrayList<Student>();
        this.instructors = new ArrayList<Instructor>();
        this.categoryWeights = categoryWeights;
        this.daysOfWeek = new ArrayList<String>();
        this.startTime = startTime;
        this.endTime = endTime;
        this.daysOfWeek = daysOfWeek;
    }
    //TODO: delete this, it is for
    public Course(String name, LocalTime startTime, LocalTime endTime) {
        this.name = name;
        this.description = "";
        this.courseCode = "";
        this.credits = 0;
        this.students = new ArrayList<Student>();
        this.instructors = new ArrayList<Instructor>();
        this.categoryWeights = new HashMap<String, Double>();
        this.daysOfWeek = new ArrayList<String>();
        this.startTime = startTime;
        this.endTime = endTime;
        this.daysOfWeek = new ArrayList<String>();
    }

    @Override
    public String toString() {
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("h:mm a"); // Pattern for 12-hour format with AM/PM
        StringBuilder sb = new StringBuilder();
        sb.append("Course Code: ").append(courseCode).append("\n");
        sb.append("Course Name: ").append(name).append("\n");
        sb.append("Description: ").append(description).append("\n");
        sb.append("Credits: ").append(credits).append("\n");
        sb.append("Schedule: ").append(String.join(", ", daysOfWeek)).append("\n");
        sb.append("Time: ").append(startTime.format(timeFormatter)).append(" - ").append(endTime.format(timeFormatter)).append("\n");
        sb.append("Category Weights:\n");

        for (String category : categoryWeights.keySet()) {
            sb.append("  ").append(category).append(": ").append(categoryWeights.get(category)).append("\n");
        }

        return sb.toString();
    }

    public JPanel createEventPanel() {
        // Calculate duration in minutes
        long duration = Duration.between(startTime, endTime).toMinutes();
        // Set panel height based on duration
        int panelHeight = (int) duration * 2; // For example, 2 pixels per minute
        JPanel panel = new JPanel();
        panel.setPreferredSize(new Dimension(200, panelHeight));
        panel.setBackground(Color.BLUE);
        panel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        // Add label for course name
        JLabel label = new JLabel(name);
        label.setForeground(Color.WHITE);
        panel.add(label);

        return panel;
    }
    public String getName() {
        return name;
    }
    public LocalTime getStartTime() {
        return startTime;
    }
    public LocalTime getEndTime() {
        return endTime;
    }
    public long getDuration() {
        return Duration.between(startTime, endTime).toMinutes();
    }
}
