package com.github.thibstars.btsd.desktop.components;

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

    private URI uri;

    public HyperLink() {
        this("", null);
    }

    public HyperLink(String text) {
        this(text, null);
    }

    public HyperLink(String text, URI uri) {
        this.uri = uri;
        setText(text);
        setToolTipText("Open in browser.");
        setCursor(new Cursor(Cursor.HAND_CURSOR));
        addMouseListener(getMouseAdapter());
    }

    @Override
    public void setText(String text) {
        super.setText("<HTML><FONT color=\"#000099\">" + text + "</FONT></HTML>");
    }

    public void setUri(URI uri) {
        this.uri = uri;
    }

    private MouseAdapter getMouseAdapter() {
        return new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent event) {
                if (uri != null && Desktop.isDesktopSupported()) {
                    try {
                        Desktop.getDesktop().browse(uri);
                    } catch (Exception e) {
                        LOGGER.error(e.getMessage(), e);
                    }
                }
            }
        };
    }
}
