package com.github.thibstars.btsd.desktop;

import com.github.thibstars.btsd.irail.model.Station;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * @author Thibault Helsmoortel
 */
public class StationPanel extends JPanel {

    public StationPanel(Station station) {
        JLabel lblId = new JLabel(station.id());
        JLabel lblName = new JLabel(station.name());
        JLabel lblStandardName = new JLabel(station.standardName());
        JLabel lblCoordinates = new JLabel("(" + station.locationX() + ", " + station.locationY() + ")");

        add(lblId);
        add(lblName);
        add(lblStandardName);
        add(lblCoordinates);

        setVisible(true);
    }
}
