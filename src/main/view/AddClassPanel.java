package main.view;

import javax.swing.*;

import main.controller.UserViewController;
import main.model.Days;
import main.model.User;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class AddClassPanel extends JPanel {

    private User user;
    private UserViewController controller;

    private JTextField classNameField;
    private JTextField creditsField;
    private JTextField classCodeField;
    private JTextArea descriptionArea;
    private JCheckBox[] dayCheckboxes;
    private JButton submitButton;

    public AddClassPanel(User user, UserViewController controller) {
        this.user = user;
        this.controller = controller; // Use the one passed in


        setLayout(new BorderLayout());

        // Form Panel (holds form fields)
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridLayout(8, 1, 5, 5));

        // Class Name
        formPanel.add(new JLabel("Class Name:"));
        classNameField = new JTextField();
        formPanel.add(classNameField);

        formPanel.add(new JLabel("Class Code:"));
        classCodeField = new JTextField();
        formPanel.add(classCodeField);

        // Credits
        
        formPanel.add(new JLabel("Credits:"));
        creditsField = new NumberField();
        formPanel.add(creditsField);
        
        // Description
        formPanel.add(new JLabel("Description:"));
        descriptionArea = new JTextArea(3, 20);
        descriptionArea.setLineWrap(true);
        descriptionArea.setWrapStyleWord(true);
        formPanel.add(new JScrollPane(descriptionArea));

        // Days Checkboxes
        formPanel.add(new JLabel("Days Held:"));
        JPanel checkboxPanel = new JPanel(new GridLayout(1, 5));
        String[] days = {"Mon", "Tue", "Wed", "Thu", "Fri"};
        dayCheckboxes = new JCheckBox[days.length];
        for (int i = 0; i < days.length; i++) {
            dayCheckboxes[i] = new JCheckBox(days[i]);
            checkboxPanel.add(dayCheckboxes[i]);
        }
        formPanel.add(checkboxPanel);

        // Submit Button
        submitButton = new JButton("Submit");
        submitButton.addActionListener(e -> handleSubmit());
        formPanel.add(submitButton);

        add(formPanel, BorderLayout.NORTH);
    }

    private void handleSubmit() {
        String className = classNameField.getText();
        String classCode = classCodeField.getText();
        String description = descriptionArea.getText();
        String credits = creditsField.getText();

        ArrayList<String> selectedDays = new ArrayList<>();

        for (JCheckBox checkbox : dayCheckboxes) {
            if (checkbox.isSelected()) {
                try {
                    //Days dayEnum = Days.valueOf(checkbox.getText().toUpperCase()); // turn "Mon" into "MONDAY"
                    String day = checkbox.getText();
                    //System.out.println("Selected ->" + day);
                	selectedDays.add(day);
                } catch (IllegalArgumentException e) {
                    System.out.println("Invalid day: " + checkbox.getText());
                }
            }
        }

        controller.addCourse(className, classCode, credits, description, selectedDays);

        System.out.println("Class Name: " + className);
        System.out.println("Description: " + description);
        System.out.println("Days: " + selectedDays);
    }

}
