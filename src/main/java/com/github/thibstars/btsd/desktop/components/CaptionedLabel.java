package com.github.thibstars.btsd.desktop.components;

import java.awt.FlowLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * @author Thibault Helsmoortel
 */
public class CaptionedLabel extends JPanel {

    private final JLabel lblCaption;

    private final JLabel lblText;

    public CaptionedLabel() {
        this("", "");
    }

    public CaptionedLabel(String caption, String text) {
        setLayout(new FlowLayout());
        this.lblCaption = new JLabel(caption);
        this.lblText = new JLabel(text);

        add(lblCaption);
        add(lblText);
    }

    public void setCaption(String caption) {
        lblCaption.setText(caption);
    }

    public void setText(String text) {
        lblText.setText(text);
    }
}
