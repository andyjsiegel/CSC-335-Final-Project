package main.view;

import main.controller.LoginController;
import main.model.*;

import javax.swing.*;
import java.awt.*;

public class LoginScreen extends JFrame {
  
    private LoginController loginController;

    private JTextField usernameField;
    private JPasswordField passwordField;
    private JLabel messageLabel;

    public LoginScreen() {
        // Set up the frame
        loginController = new LoginController(this);

        setTitle("Login");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        Font font = new Font("Tahoma", Font.PLAIN, 14);

        // Title label
        JLabel titleLabel = new JLabel("CSC 335 Gradebook Login");
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

        // Message label
        messageLabel = new JLabel("", SwingConstants.CENTER);
        messageLabel.setFont(font);
        gbc.gridx = 0;
        gbc.gridwidth = 2; // to center across both columns
        gbc.gridy = 3;
        gbc.insets = new Insets(10, 10, 10, 10);
        add(messageLabel, gbc);

        // Login button
        ColoredButton loginButton = new ColoredButton("Login");
        loginButton.addActionListener(e -> {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());
            loginController.login(username, password);
        });
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(5, 10, 5, 10);
        gbc.anchor = GridBagConstraints.CENTER;
        add(loginButton, gbc);

        // Register button
        ColoredButton registerButton = new ColoredButton("Register");
        registerButton.addActionListener(e -> {
            new RegisterScreen(loginController);
            this.dispose();
        });
        gbc.gridy = 5; // Place below the login button
        add(registerButton, gbc);

        setVisible(true);
    }

    public void updateMessageLabel(String message, boolean success) {
        messageLabel.setForeground(success ? new Color(0, 128, 0) : Color.RED);
        messageLabel.setText(message);
    }

    public void navigateToInstructorView(Instructor instructor) {
        updateMessageLabel("Login successful!", true);
        new InstructorView(instructor);
        this.dispose();
    }

    public void navigateToStudentView(Student student) {
        updateMessageLabel("Login successful!", true);
        //new StudentView(student);
        this.dispose();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LoginScreen());
    }
}