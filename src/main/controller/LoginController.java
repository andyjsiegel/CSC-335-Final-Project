
package main.controller;

import main.model.Instructor;
import main.model.Student;
import main.model.User;
import main.model.UserDatabase;
import main.view.LoginScreen;

public class LoginController {

    private final LoginScreen ls;
    private UserDatabase userDatabase = UserDatabase.getInstance();

    public LoginController(LoginScreen ls) {
        this.ls = ls;
    }

    public void login(String username, String password) {

        User user = userDatabase.getUser(username, password);

        if (user != null) {
            if (user instanceof Instructor) {
                ls.navigateToInstructorView((Instructor) user);
            } else if (user instanceof Student) {
                ls.navigateToStudentView((Student) user);
            }
        } else {
            ls.updateMessageLabel("Username or password is incorrect.", false);
        }
    }

    public void register(String username, String password, String firstname, String lastname, String email, Boolean userType) {

        if (username == null || password == null || firstname == null || lastname == null || email == null || userType == null) {
            ls.updateMessageLabel("All fields are required. Please fill out all fields and try again.", false);
            return;
        }
        if (userType) {
            userDatabase.addInstructor(username, password, firstname, lastname, email, false);
        }
        if (!userType) {
            userDatabase.addStudent(username, password, firstname, lastname, email, false);
        }
        ls.updateMessageLabel("Registration successful! You can now log in.", true);
    }
}