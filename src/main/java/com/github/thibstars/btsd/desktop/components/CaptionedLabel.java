package com.github.thibstars.btsd.desktop.components;

import java.awt.FlowLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * @author Thibault Helsmoortel
 */
public class CaptionedLabel extends JPanel {

    private final JLabel lblText;

    public CaptionedLabel(String caption) {
        this(caption, "");
    }

    public CaptionedLabel(String caption, String text) {
        setLayout(new FlowLayout());
        JLabel lblCaption = new JLabel(caption);
        this.lblText = new JLabel(text);

        add(lblCaption);
        add(lblText);
    }

    public void setText(String text) {
        lblText.setText(text);
    }
}
