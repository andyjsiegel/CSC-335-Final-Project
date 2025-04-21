
package main.controller;

import main.model.Instructor;
import main.model.Student;
import main.model.User;
import main.model.UserDatabase;
import main.observer.LoginViewObserver;

public class LoginController {

    private final LoginViewObserver observer;

    public LoginController(LoginViewObserver observer) {
        this.observer = observer;
    }

    public void login(String username, String password) {

        UserDatabase userDatabase = new UserDatabase();
        userDatabase.addUser(new Instructor("pelier", "password", "pelier@arizona.edu", false));
        User user = userDatabase.getUser(username, password);

        if (user != null) {
            if (user instanceof Instructor) {
                observer.navigateToInstructorApplication((Instructor) user);
            } else if (user instanceof Student) {
                observer.navigateToStudentApplication((Student) user);
            }
        } else {
            observer.updateMessageLabel("Username or password is incorrect.", false);
        }
    }
}