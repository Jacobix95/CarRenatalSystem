package com.carRentalSystem.View;

import java.awt.*;

public class JPasswordField extends javax.swing.JPasswordField {

    public JPasswordField(int textSize) {
        super();
        setFont(new Font("SansSerif", Font.BOLD, textSize));
        setHorizontalAlignment(JLabel.CENTER);
        setBorder(null);
    }
}
