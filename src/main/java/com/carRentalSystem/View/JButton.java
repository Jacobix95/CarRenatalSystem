package com.carRentalSystem.View;

import java.awt.*;

public class JButton extends javax.swing.JButton {

    public JButton(String text, int textSize) {
        super(text);
        setBackground(Color.black);
        setFont(new Font("Calibri", Font.BOLD, textSize));
        setForeground(Color.white);
        setBorder(null);
    }
}
