package main.observer;

import main.model.Instructor;
import main.model.Student;

public interface LoginViewObserver {
    void updateMessageLabel(String message, boolean success);
    void navigateToInstructorApplication(Instructor instructor);
    void navigateToStudentApplication(Student student);
}