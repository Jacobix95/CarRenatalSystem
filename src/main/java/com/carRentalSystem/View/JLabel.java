package com.carRentalSystem.View;

import java.awt.*;

public class JLabel extends javax.swing.JLabel {

    public JLabel(String text, int fontSize) {
        super(text);
        setFont(new Font("SansSerif", Font.BOLD, fontSize));
        setBackground(null);
        setHorizontalAlignment(JLabel.CENTER);
    }
}
