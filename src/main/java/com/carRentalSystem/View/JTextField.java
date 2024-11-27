package com.carRentalSystem.View;

import java.awt.*;

public class JTextField extends javax.swing.JTextField {
    public JTextField(int textSize) {
        super();
        setFont(new Font("Calibri", Font.BOLD, textSize));
        setHorizontalAlignment(JLabel.CENTER);
        setBorder(null);
    }
}
