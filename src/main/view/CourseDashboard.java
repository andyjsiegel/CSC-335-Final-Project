package main.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

import main.controller.UserViewController;
import main.model.Course;
import main.model.Student;
import main.model.StudentList;

public class CourseDashboard extends JPanel {

    private CardLayout cardLayout;
    private JPanel cardPanel;
    private JTextArea infoArea;
    private JButton cookieBtn;
    private JPanel contentPanel;
    
    private UserViewController controller;

    public CourseDashboard(ActionListener backToInstructorViewAction, UserViewController controller) {
        
    	this.controller = controller;
        Course course = controller.getSelectedCourse();
        setLayout(new BorderLayout());

        // === Initialize card layout ===
        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);

        // === MAIN VIEW PANEL ===
        JPanel mainViewPanel = new JPanel(new BorderLayout());

        infoArea = new JTextArea("Welcome to the course dashboard.");
        infoArea.setEditable(false);

        JScrollPane scrollPane = new JScrollPane(infoArea);

        contentPanel = new JPanel(new BorderLayout());
        contentPanel.add(scrollPane, BorderLayout.CENTER);

        // this will be a button to add the students but ill add it later.. too much code :)
        cookieBtn = new JButton("cookie!");
        cookieBtn.setVisible(false);

        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
        
        rightPanel.add(Box.createVerticalStrut(100));
        rightPanel.add(cookieBtn);
        rightPanel.add(Box.createVerticalGlue());

        contentPanel.add(rightPanel, BorderLayout.EAST);

        mainViewPanel.add(contentPanel, BorderLayout.CENTER);

        // === TOP SECTION ===
        JPanel upperInfo = new JPanel(new BorderLayout());
        upperInfo.setBackground(Color.WHITE);
        upperInfo.setPreferredSize(new Dimension(800, 60));

        // === LEFT SIDE: Logo + Course Name ===
        ImageIcon icon = new ImageIcon("assets/UOFAD2L.png");
        Image scaledImage = icon.getImage().getScaledInstance(280, 100, Image.SCALE_SMOOTH);
        icon = new ImageIcon(scaledImage);
        JLabel imageLabel = new JLabel(icon);

        // Panel to group logo + course name
        JPanel leftInfoPanel = new JPanel();
        leftInfoPanel.setOpaque(false);
        leftInfoPanel.setLayout(new BoxLayout(leftInfoPanel, BoxLayout.X_AXIS));

        // Course name label
        JLabel courseNameLabel = new JLabel(course.getName() + " - " + course.getCourseCode());
        courseNameLabel.setFont(new Font("SansSerif", Font.PLAIN, 18));
        courseNameLabel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0)); // space between logo and text

        // Add both to the left panel
        leftInfoPanel.add(imageLabel);
        leftInfoPanel.add(courseNameLabel);

        // Add to left side of upperInfo
        upperInfo.add(leftInfoPanel, BorderLayout.WEST);

        // === PROFILE PIC + first name/lastname  OVERLAY ===
        ImageIcon pfpIcon = new ImageIcon("assets/PFPSQUARE.png");
        Image scalePFP = pfpIcon.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
        pfpIcon = new ImageIcon(scalePFP);

        JLabel pfpLabel = new JLabel(pfpIcon);
        pfpLabel.setAlignmentX(0.5f);
        pfpLabel.setAlignmentY(0.5f);

        // Text overlay on the picture
        JLabel overlayText = new JLabel("" + course.getInstructor().getFirstName().charAt(0) + "" + course.getInstructor().getLastName().charAt(0));
        overlayText.setFont(new Font("SansSerif", Font.BOLD, 12));
        overlayText.setForeground(Color.WHITE);
        overlayText.setHorizontalAlignment(SwingConstants.CENTER);
        overlayText.setVerticalAlignment(SwingConstants.CENTER);
        overlayText.setAlignmentX(0.5f);
        overlayText.setAlignmentY(0.5f);

        // Panel to layer the image and the overlay text
        JPanel overlayPanel = new JPanel();
        overlayPanel.setLayout(new OverlayLayout(overlayPanel));
        overlayPanel.setOpaque(false);
        overlayPanel.add(overlayText);   
        overlayPanel.add(pfpLabel);      

        // === USERNAME LABEL ===
        JLabel userNameLabel = new JLabel(course.getInstructor().getFullName());
        userNameLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
        userNameLabel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0)); // space from image

        // === Group name + image in one right-aligned panel
        JPanel userInfoPanel = new JPanel();
        userInfoPanel.setLayout(new BoxLayout(userInfoPanel, BoxLayout.X_AXIS));
        userInfoPanel.setOpaque(false);
        userInfoPanel.add(overlayPanel);
        userInfoPanel.add(Box.createHorizontalStrut(5));
        userInfoPanel.add(userNameLabel);

        // right-aligned 
        JPanel rightInfoPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 80, 10));
        rightInfoPanel.setOpaque(false);
        rightInfoPanel.add(userInfoPanel);

        // === Add to top bar
        upperInfo.add(rightInfoPanel, BorderLayout.EAST);

        // Button Row
        JPanel mainButtonPanel = new JPanel();
        JButton addAssignmentBtn = new JButton("Assignments");
        JButton addClasslistBtn = new JButton("Classlist");
        JButton viewGradesBtn = new JButton("grades?");
        mainButtonPanel.add(addAssignmentBtn);
        mainButtonPanel.add(addClasslistBtn);
        mainButtonPanel.add(viewGradesBtn);

        // Top Section Combined
        JPanel topContainer = new JPanel();
        topContainer.setLayout(new BoxLayout(topContainer, BoxLayout.Y_AXIS));
        topContainer.add(upperInfo);
        topContainer.add(mainButtonPanel);

        mainViewPanel.add(topContainer, BorderLayout.NORTH);

        // === Add only the main panel to cardPanel ===
        cardPanel.add(mainViewPanel, "main");
        cardLayout.show(cardPanel, "main");

        add(cardPanel, BorderLayout.CENTER);

        // === BUTTON LOGIC === (it will be placeholder for adding students and assignemnts, just testing
        // how it would look like)

        viewGradesBtn.addActionListener(e -> {
            infoArea.setText(getGradesInfo());
            cookieBtn.setVisible(true);  // show cookie
        });

        addClasslistBtn.addActionListener(e -> {
            infoArea.setText(getClasslistInfo());
            cookieBtn.setVisible(false);  // hide cookie
        });

        addAssignmentBtn.addActionListener(e -> {
            infoArea.setText("Assignments feature coming soon...");
            cookieBtn.setVisible(false);  // hide cookie
        });

        cookieBtn.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "should be an option to enter a student to add/ general");
        });
    }

    private String getClasslistInfo() {
        StringBuilder sb = new StringBuilder();
        StudentList classlist = controller.getSelectedCourse().getStudents();

        sb.append("Classlist for ").append(controller.getSelectedCourse().getName()).append(":\n\n");

        for (Student student : classlist) {
            sb.append(student.getFirstName()).append(" ").append(student.getLastName());
            sb.append(" - ").append(student.getEmail()).append("\n");
        }

        return sb.toString();
    }

    private String getGradesInfo() {
        StringBuilder sb = new StringBuilder();
        StudentList classlist = controller.getSelectedCourse().getStudents();

        sb.append("Grades for ").append(controller.getSelectedCourse().getName()).append(":\n\n");

        for (Student student : classlist) {
            sb.append(student.getFirstName()).append(" - ");
            try {
                sb.append("Grade info placeholder");
            } catch (Exception e) {
                sb.append("Grade info not available");
            }
            sb.append("\n");
        }

        return sb.toString();
    }
}
