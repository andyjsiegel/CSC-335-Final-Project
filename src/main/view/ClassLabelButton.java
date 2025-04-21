package main.view;

import main.model.Course;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ClassLabelButton extends JTextArea {

    public ClassLabelButton(CalendarView instructorView, Course course) {
        super(course.getName());
        setWrapStyleWord(true);
        setLineWrap(true);
        setEditable(false);
        setOpaque(false);
        setFocusable(false);
        setAlignmentX(Component.CENTER_ALIGNMENT);
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (SwingUtilities.isLeftMouseButton(e)) {
                    JOptionPane.showMessageDialog(null, "You clicked on the class label!");
                }
            }
        });
    }
}
