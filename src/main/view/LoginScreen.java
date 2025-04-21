package main.view;

import main.model.*;

import javax.swing.*;
import java.awt.*;
import java.util.Calendar;

public class LoginScreen extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JLabel messageLabel;

    public LoginScreen() {
        // Set up the frame
        setTitle("Login");
        setSize(400, 250);
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
        passwordField.addActionListener(e -> handleLogin());
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        add(passwordField, gbc);

        // Login button
        ColoredButton loginButton = new ColoredButton("Login");
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(20, 10, 10, 10);
        gbc.anchor = GridBagConstraints.CENTER;
        add(loginButton, gbc);

        // Message label
        messageLabel = new JLabel("", SwingConstants.CENTER);
        messageLabel.setFont(font);
        gbc.gridy = 4;
        gbc.insets = new Insets(10, 10, 10, 10);
        add(messageLabel, gbc);

        // Add action listener for the login button
        loginButton.addActionListener(e -> handleLogin());

        setVisible(true);
    }

    private void handleLogin() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());

        UserDatabase userDatabase = new UserDatabase();
        // userDatabase.addUser(new Instructor("pelier", "password", "pelier@arizona.edu", "Pelier", false));
        // userDatabase.addUser(new Instructor("andy", "password", "andy@arizona.edu", "Andy", false));
        User user = userDatabase.getUser(username, password);

        if (user != null) {
            messageLabel.setForeground(new Color(0, 128, 0));
            messageLabel.setText("Login successful!");
            if (user instanceof Instructor) {
                // new CalendarView((Instructor) user);
                new InstructorApplication((Instructor) user);
            } else if (user instanceof Student) {
                new CalendarView((Student) user);
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
