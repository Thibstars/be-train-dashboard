package com.github.thibstars.btsd.desktop;

import java.awt.FlowLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * @author Thibault Helsmoortel
 */
public class CaptionedLabel extends JPanel {

    public CaptionedLabel(String caption, String text) {
        setLayout(new FlowLayout());
        JLabel lblCaption = new JLabel(caption);
        JLabel lblText = new JLabel(text);

        add(lblCaption);
        add(lblText);
    }
}
