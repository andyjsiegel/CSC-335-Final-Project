package main.view;

import main.controller.LoginController;

import javax.swing.*;
import java.awt.*;

public class RegisterScreen extends JFrame {

    private JTextField usernameField, firstnameField, lastnameField, emailField;
    private JPasswordField passwordField;
    private JLabel messageLabel;

    public RegisterScreen(LoginController loginController) {
        setTitle("Register");
        setSize(400, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        Font font = new Font("Tahoma", Font.PLAIN, 14);

        // Title
        JLabel titleLabel = new JLabel("CSC 335 Gradebook Register");
        titleLabel.setFont(new Font("Tahoma", Font.BOLD, 20));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(10, 10, 20, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(titleLabel, gbc);
        
        // Username label
        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setFont(font);
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.insets = new Insets(5, 10, 5, 10);
        gbc.anchor = GridBagConstraints.EAST;
        add(usernameLabel, gbc);

        // Username field
        usernameField = new JTextField(15);
        usernameField.setFont(font);
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        add(usernameField, gbc);

        // Password label
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(font);
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.EAST;
        add(passwordLabel, gbc);

        // Password field
        passwordField = new JPasswordField(15);
        passwordField.setFont(font);
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        add(passwordField, gbc);

        // First Name label
        JLabel firstnameLabel = new JLabel("First Name:");
        firstnameLabel.setFont(font);
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.EAST;
        add(firstnameLabel, gbc);

        // First Name field
        firstnameField = new JTextField(15);
        firstnameField.setFont(font);
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        add(firstnameField, gbc);

        // Last Name label
        JLabel lastnameLabel = new JLabel("Last Name:");
        lastnameLabel.setFont(font);
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.anchor = GridBagConstraints.EAST;
        add(lastnameLabel, gbc);

        // Last Name field
        lastnameField = new JTextField(15);
        lastnameField.setFont(font);
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        add(lastnameField, gbc);

        // Email label
        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setFont(font);
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.anchor = GridBagConstraints.EAST;
        add(emailLabel, gbc);

        // Email field
        emailField = new JTextField(15);
        emailField.setFont(font);
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        add(emailField, gbc);

        // User Type Checkboxes
        JCheckBox studentCheckBox = new JCheckBox("Student");
        JCheckBox instructorCheckBox = new JCheckBox("Instructor");

        ButtonGroup userTypeGroup = new ButtonGroup();
        userTypeGroup.add(studentCheckBox);
        userTypeGroup.add(instructorCheckBox);

        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.anchor = GridBagConstraints.EAST;
        add(studentCheckBox, gbc);

        gbc.gridx = 1;
        gbc.gridy = 6;
        gbc.anchor = GridBagConstraints.WEST;
        add(instructorCheckBox, gbc);

        ColoredButton registerButton = new ColoredButton("Register");

        registerButton.addActionListener(e -> {
            Boolean isInstructor = null;
            if (instructorCheckBox.isSelected()) {
                isInstructor = true;
            }
            if (studentCheckBox.isSelected()) {
                isInstructor = false;
            }

            String password = new String(passwordField.getPassword());

            loginController.register(
                    usernameField.getText(),
                    password,
                    firstnameField.getText(),
                    lastnameField.getText(),
                    emailField.getText(),
                    isInstructor
            );

            JOptionPane.showMessageDialog(this, "Registered successfully!");
            new LoginScreen();
            this.dispose();;
        });

        gbc.gridx = 0;
        gbc.gridy = 7;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(5, 10, 5, 10);
        gbc.anchor = GridBagConstraints.CENTER;

        add(registerButton, gbc);

        // Message Label
        gbc.gridy++;
        messageLabel = new JLabel("", SwingConstants.CENTER);
        messageLabel.setFont(font);
        add(messageLabel, gbc);

        setVisible(true);
    }
}