package com.github.thibstars.btsd.desktop.components;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import javax.swing.JTextField;

/**
 * @author Thibault Helsmoortel
 */
public class PlaceholderTextField extends JTextField {

    private String placeholder;

    public PlaceholderTextField() {
        this.placeholder = "";

        this.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (getText().equals(placeholder)) {
                    setText("");
                    setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (getText().isEmpty()) {
                    setForeground(Color.GRAY);
                    setText(placeholder);
                }
            }
        });
    }

    @Override
    protected void paintComponent(final Graphics graphics) {
        super.paintComponent(graphics);

        if (placeholder == null || placeholder.isEmpty() || !getText().isEmpty()) {
            return;
        }

        final Graphics2D graphics2D = (Graphics2D) graphics;
        graphics2D.setRenderingHint(
                RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        graphics2D.setColor(getDisabledTextColor());
        graphics2D.drawString(placeholder, getInsets().left, graphics.getFontMetrics()
                .getMaxAscent() + getInsets().top);
    }

    public void setPlaceholder(final String placeholder) {
        this.placeholder = placeholder;
    }

}

