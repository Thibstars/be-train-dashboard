package com.github.thibstars.btsd.desktop;

import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URI;
import javax.swing.JLabel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Thibault Helsmoortel
 */
public class HyperLink extends JLabel {

    private static final Logger LOGGER = LoggerFactory.getLogger(HyperLink.class);

    public HyperLink(String text, URI uri) {
        setText("<HTML><FONT color=\"#000099\">" + text + "</FONT></HTML>");
        setToolTipText("Open in browser.");
        setCursor(new Cursor(Cursor.HAND_CURSOR));
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent event) {
                if (Desktop.isDesktopSupported()) {
                    try {
                        Desktop.getDesktop().browse(uri);
                    } catch (Exception e) {
                        LOGGER.error(e.getMessage(), e);
                    }
                }
            }
        });
    }
}
