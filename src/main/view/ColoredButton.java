package main.view;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ColoredButton extends JButton {
    private Color normalBorderColor = new Color(70, 130, 180);
    private Color pressedBorderColor = new Color(30, 90, 140);

    public ColoredButton(String text) {
        super(text);
        init();
    }

    public ColoredButton(String text, Color normalBorderColor, Color pressedBorderColor) {
        super(text);
        this.normalBorderColor = normalBorderColor;
        this.pressedBorderColor = pressedBorderColor;
        init();
    }

    private void init() {
        setFont(new Font("Tahoma", Font.BOLD, 16));
        setBackground(new Color(70, 130, 180));
        setForeground(Color.WHITE);
        setFocusPainted(false);
        setOpaque(true);
        setContentAreaFilled(true);
        setBorderPainted(true);
        setBorder(new LineBorder(normalBorderColor, 2));
        setPreferredSize(new Dimension(100, 35));

        // Change border color on press/release
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                setBorder(new LineBorder(pressedBorderColor, 2));
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                setBorder(new LineBorder(normalBorderColor, 2));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                setBorder(new LineBorder(normalBorderColor, 2));
            }
        });
    }

    // Optional: setters to customize colors if needed
    public void setNormalBorderColor(Color color) {
        this.normalBorderColor = color;
        setBorder(new LineBorder(normalBorderColor, 2));
    }

    public void setPressedBorderColor(Color color) {
        this.pressedBorderColor = color;
    }
}

