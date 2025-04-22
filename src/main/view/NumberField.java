package main.view;
import javax.swing.*;
import java.awt.event.*;

public class NumberField extends JTextField {
    public NumberField() {
        super();
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                // Allow only digits and control characters (like backspace)
                if (!Character.isDigit(c) && c != KeyEvent.VK_BACK_SPACE && c != KeyEvent.VK_PERIOD) {
                    e.consume(); // Ignore the event
                }
            }
        });
    }
    public double getDouble() {
        return Double.parseDouble(getText());
    }

    public int getInt() {
        return Integer.parseInt(getText());
    }
}

