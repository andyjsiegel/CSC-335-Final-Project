# Java Swing Gradebook Application

## Description

This project is a desktop application designed to manage courses, students, instructors, assignments, and grades. It provides separate functionalities for students and instructors, allowing instructors to create and manage courses, add assignments, and grade students, while students can view their enrolled courses, assignments, and grades. The application features a Graphical User Interface (GUI) built with Java Swing and stores user data persistently in a CSV file.

## Features

* **User Roles:** Supports distinct `Student` and `Instructor` roles with different permissions.
* **User Authentication:** Secure login and registration system.
    * Passwords are securely hashed using SHA-256 with a predefined salt (`User.hashPassword`).
* **Course Management (Instructor):**
    * Create new courses with name, code, description, and meeting days.
    * Add students to courses from the existing user pool.
* **Assignment Management (Instructor):**
    * Add assignments to specific categories within a course.
    * Define assignment title, description, and maximum points that a student can earn. 
    * Note: grading is not capped by this maximum points value in case an instructor wants to provide extra credit.
    * Grade assignments for enrolled students.
* **Grade Viewing (Student & Instructor):**
    * Students can view their grades for assignments in each course.
    * Instructors can view grades for all students in their courses.
    * Calculation of average and median grades (`StudentGradebook`).
    * Final letter grade calculation based on overall percentage (`FinalGrades` enum).
* **Grading Schemes:** Supports complex grading calculations including:
    * Simple total points calculation (`GradingScheme.calculateFinalGradeOption1`).
    * Weighted category averages with support for dropping the lowest score(s) in a category (`GradingScheme.calculateFinalGradeOption2`).
* **User Interface (Java Swing):**
    * Login and Registration screens (`LoginScreen`, `RegisterScreen`).
    * Main dashboard (`MainView`) for navigation after login.
    * Calendar view displaying courses scheduled for each day (`CalendarView`).
    * List view displaying course information cards (`ListViewClassesPanel`).
    * Detailed course dashboard with tabs/sections for info, assignments, and classlist (`CourseDashboard`).
    * Forms for adding classes and assignments (`AddClassPanel`, `Course.getAssignmentAddPanel`).
    * Custom UI components (`ColoredButton`, `NumberField`) to make codebase cleaner and more maintainable.
* **Data Persistence:**
    * User account information (username, hashed password, name, email, role) is stored in a CSV file (defaults to `src/main/controller/users.csv`).
    * CSV Importer utility (`CSVImporter`) to load student data from external files.
* **Testing:** Includes a comprehensive suite of unit tests using JUnit 5 (`main.tests` package) covering models, utilities, and core logic.

## Project Structure

The project separates areas into different packages:

* `main.model`: Contains the core data models and logic (e.g., `User`, `Student`, `Instructor`, `Course`, `Assignment`, `Gradebook`, `UserDatabase`).
* `main.view`: Contains all GUI components built with Java Swing (e.g., `LoginScreen`, `MainView`, `CourseDashboard`).
* `main.controller`: Contains controller classes that mediate between the view and the model (e.g., `LoginController`, `UserViewController`).
* `main.util`: Contains utility classes, specifically the `CSVImporter`.
* `main.tests`: Contains JUnit 5 unit tests for all of the backend (the model) with 90% coverage.

## Running the Code

1.  **Prerequisites:**
    * Java Development Kit (JDK) 8 or higher.
    * An IDE like Eclipse, IntelliJ IDEA, or VS Code (recommended)
2.  **Clone the Repository:**
    ```bash
    git clone https://github.com/andyjsiegel/CSC-335-Final-Project
    cd CSC-335-Final-Project
    ```
3.  **Compile:**
    * Import the project and use the IDE's build functionality.
4.  **Run:**
    * Run the `main.view.LoginScreen` class.
5.  **User Data File:** The application will attempt to create/use a `users.csv` file at `src/main/controller/users.csv` by default (see `UserDatabase`). Ensure the application has write permissions to this location or modify the path in `UserDatabase` if needed.

## Usage

1.  Launch the application by running the `main.view.LoginScreen` class.
2.  **Register:** If you are a new user, click the "Register" button and fill in the required details (username, password, name, email, role).
3.  **Login:** Enter your username and password and click "Login".
4.  **Navigation:** After logging in, you will be presented with the `MainView` dashboard.
    * Use the buttons at the bottom or the menu bar to navigate between different views (Add Class [only available to instructors], List View, Calendar View).
5.  **Instructors:**
    * Can use "Add Class" to create new courses.
    * Can view created courses in the List or Calendar view.
    * Clicking on a course opens the `CourseDashboard` where assignments can be added/managed, and students can be added/viewed and graded.
6.  **Students:**
    * Can view enrolled courses in the List or Calendar view.
    * Clicking on a course opens the `CourseDashboard` (student view) to see assignments and grades.

## Testing

The project includes JUnit 5 tests in the `main.tests` package.

* **Running Tests (IDE):** Most IDEs allow you to right-click on the `main.tests` package or individual test files/methods and select "Run Tests".
* **Running Tests (Build Tools):** If using a build tool like Maven or Gradle (not explicitly shown in provided code), use the standard test commands (e.g., `mvn test`, `gradle test`).*

![Class Diagram](FinalProjectUML.svg)
![Class Diagram](GradebookComposition.svg)
![Class Diagram](CourseComposition.svg)
![Class Diagram](IteratorComposition.svg)
