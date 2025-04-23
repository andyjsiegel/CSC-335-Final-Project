package main.view;

import main.controller.InstructorViewController;
import main.controller.LoginController;
import main.model.Instructor;
import main.model.UserDatabase;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;

/**
 * A simple launcher to demonstrate all UI views in one place.
 */
public class DemoLauncher extends JFrame {
    private LoginScreen loginScreen;
    private LoginController loginController;
    private Instructor dummyInstructor;
    private InstructorViewController instructorController;

    public DemoLauncher() {
        super("CSC 335 Gradebook UI Demo");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(350, 550);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(0, 1, 5, 5));

        // Initialize a single LoginScreen and its controller
        loginScreen = new LoginScreen();
        loginController = new LoginController(loginScreen);

        // Prepare a dummy instructor and controller for instructor views
        dummyInstructor = new Instructor(
            "demoUser", "pass", "Demo", "Instructor", "demo@ua.edu", false
        );
        instructorController = new InstructorViewController(dummyInstructor);

        // Add buttons for each screen/panel
        addButton("1. Login Screen", () -> new LoginScreen());
        addButton("2. Register Screen", () -> new RegisterScreen(loginController));
        addButton("3. Instructor Dashboard", () -> new InstructorView(dummyInstructor));
        addButton("4. Add Class Panel", () -> showInFrame(
            new AddClassPanel(dummyInstructor, instructorController), "Add Class"
        ));
        addButton("5. List View Classes", () -> showInFrame(
            new ListViewClassesPanel(instructorController), "List Classes"
        ));
        addButton("6. Class Details View", () -> new ClassDetailsView(
            // ensure at least one course exists
            createDemoCourse(), instructorController
        ));
        addButton("7. Calendar View", () -> new CalendarView(dummyInstructor));
        addButton("8. Colored Button Demo", () -> showInFrame(
            coloredButtonPanel(), "Colored Button Demo"
        ));
        addButton("9. ClassLabelButton Demo", () -> showInFrame(
            classLabelPanel(), "Class Label Demo"
        ));
        addButton("10. Exit",() -> System.exit(0));

        setVisible(true);
    }

    private void addButton(String text, Runnable action) {
        JButton btn = new JButton(text);
        btn.addActionListener(e -> action.run());
        add(btn);
    }

    private void showInFrame(JComponent comp, String title) {
        JFrame f = new JFrame(title);
        f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        f.setSize(500, 400);
        f.setLocationRelativeTo(this);
        f.setContentPane(comp);
        f.setVisible(true);
    }

    private JComponent coloredButtonPanel() {
        JPanel p = new JPanel(new GridBagLayout());
        p.add(new ColoredButton("Click Me"));
        return p;
    }

    private JComponent classLabelPanel() {
        // use any demo course
        return new ClassLabelButton(null, createDemoCourse());
    }

    private main.model.Course createDemoCourse() {
        // If instructor has no courses yet, add one
        if (dummyInstructor.getCoursesManaged().isEmpty()) {
            instructorController.addCourse(
                "DemoCourse", "DEM101", "A demo course.", Arrays.asList("Mon","Wed","Fri")
            );
        }
        return dummyInstructor.getCoursesManaged().get(0);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(DemoLauncher::new);
    }
}
