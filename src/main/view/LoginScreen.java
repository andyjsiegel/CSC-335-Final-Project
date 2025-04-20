package main.view;

import main.model.*;

import javax.swing.*;
import java.awt.*;

public class LoginScreen extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JLabel messageLabel;

    public LoginScreen() {
        // Set up the frame
        setTitle("Login");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(4, 1));

        // Create components
        usernameField = new JTextField();
        passwordField = new JPasswordField();
        JButton loginButton = new JButton("Login");
        messageLabel = new JLabel("", SwingConstants.CENTER);
        messageLabel.setForeground(Color.RED);

        // Add components to the frame
        add(new JLabel("Username:"));
        add(usernameField);
        add(new JLabel("Password:"));
        add(passwordField);
        add(loginButton);
        add(messageLabel);

        // Add action listener for the login button
        loginButton.addActionListener(e -> handleLogin());

        setVisible(true);
    }

    private void handleLogin() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());

        UserDatabase userDatabase = new UserDatabase();
        userDatabase.addUser(new Instructor("pelier", "password", "siegela1@arizona.edu", false));
        User user = userDatabase.getUser(username, password);

        if (user != null) {
            messageLabel.setForeground(Color.GREEN);
            messageLabel.setText("Login successful!");
            if (user instanceof Instructor) {
                new InstructorView((Instructor) user);
            } else if (user instanceof Student) {
                //TODO: This should be similar to the instructor view but for students
                //new StudentView(user);
            }
        } else {
            messageLabel.setForeground(Color.RED);
            messageLabel.setText("Username or password is incorrect.");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LoginScreen());
    }
}
