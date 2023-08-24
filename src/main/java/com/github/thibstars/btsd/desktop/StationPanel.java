package com.github.thibstars.btsd.desktop;

import com.github.thibstars.btsd.irail.model.Station;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.JPanel;

/**
 * @author Thibault Helsmoortel
 */
public class StationPanel extends JPanel {

    public StationPanel(Station station) {
        setLayout(new GridLayout(2, 2));
        setBorder(BorderFactory.createTitledBorder("Station - " + station.name()));

        CaptionedLabel lblId = new CaptionedLabel("Id:", station.id());
        CaptionedLabel lblName = new CaptionedLabel("Name:", station.name());
        CaptionedLabel lblStandardName = new CaptionedLabel("Default name:", station.standardName());
        CaptionedLabel lblCoordinates = new CaptionedLabel("Coordinates:", "(" + station.locationX() + ", " + station.locationY() + ")");

        add(lblId);
        add(lblName);
        add(lblStandardName);
        add(lblCoordinates);

        setVisible(true);
    }
}
