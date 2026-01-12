package com.github.thibstars.btsd.desktop.stations;

import com.github.thibstars.btsd.desktop.components.CaptionedLabel;
import com.github.thibstars.btsd.desktop.i18n.I18NController;
import com.github.thibstars.btsd.desktop.listeners.LocaleChangeListener;
import com.github.thibstars.jirail.model.StationInfo;
import java.awt.GridLayout;
import java.util.Locale;
import javax.swing.BorderFactory;
import javax.swing.JPanel;

/**
 * @author Thibault Helsmoortel
 */
public class StationPanel extends JPanel implements LocaleChangeListener {

    private final transient StationInfo stationInfo;

    private final CaptionedLabel lblId;

    private final CaptionedLabel lblName;

    private final CaptionedLabel lblDefaultName;

    private final CaptionedLabel lblCoordinates;

    public StationPanel(StationInfo stationInfo) {
        this.stationInfo = stationInfo;

        setLayout(new GridLayout(2, 2));

        this.lblId = new CaptionedLabel();
        lblId.setText(stationInfo.id());
        this.lblName = new CaptionedLabel();
        lblName.setText(stationInfo.name());
        this.lblDefaultName = new CaptionedLabel();
        lblDefaultName.setText(stationInfo.standardName());
        this.lblCoordinates = new CaptionedLabel();
        setCoordinates(stationInfo.locationX(), stationInfo.locationY());
        add(lblId);
        add(lblName);
        add(lblDefaultName);
        add(lblCoordinates);

        setVisible(true);
    }

    private void setCoordinates(String x, String y) {
        lblCoordinates.setText("(" + x + ", " + y + ")");
    }

    @Override
    public void localeChanged(Locale locale, I18NController i18NController) {
        setBorder(BorderFactory.createTitledBorder(i18NController.getMessage("station.border.title") + " - " + stationInfo.name()));
        lblId.setCaption(i18NController.getMessage("station.id") + ":");
        lblName.setCaption(i18NController.getMessage("station.name") + ":");
        lblDefaultName.setCaption(i18NController.getMessage("station.default.name") + ":");
        lblCoordinates.setCaption(i18NController.getMessage("station.coordinates") + ":");
    }

    public void update(StationInfo station) {
        lblId.setText(station.id());
        lblName.setText(station.name());
        setCoordinates(station.locationX(), station.locationY());
        lblDefaultName.setText(station.standardName());
    }
}
