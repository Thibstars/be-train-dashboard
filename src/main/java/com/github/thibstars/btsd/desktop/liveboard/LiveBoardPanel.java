package com.github.thibstars.btsd.desktop.liveboard;

import com.github.thibstars.btsd.desktop.components.CaptionedLabel;
import com.github.thibstars.btsd.desktop.i18n.I18NController;
import com.github.thibstars.btsd.desktop.listeners.LocaleChangeListener;
import com.github.thibstars.btsd.desktop.stations.StationPanel;
import com.github.thibstars.btsd.irail.model.Departure;
import com.github.thibstars.btsd.irail.model.Departures;
import com.github.thibstars.btsd.irail.model.LiveBoard;
import com.github.thibstars.btsd.irail.model.Occupancy;
import com.github.thibstars.btsd.irail.model.Platform;
import com.github.thibstars.btsd.irail.model.Station;
import com.github.thibstars.btsd.irail.model.Vehicle;
import java.awt.BorderLayout;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Date;
import java.util.Locale;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

/**
 * @author Thibault Helsmoortel
 */
public class LiveBoardPanel extends JPanel implements LocaleChangeListener {

    private static final int MILLISECONDS_IN_SECOND = 1000;

    private static final int SECONDS_IN_MINUTE = 60;

    private final StationPanel pnlStation;

    private final CaptionedLabel lblDepartureNumber;

    private final DefaultTableModel departuresModel;

    public LiveBoardPanel(LiveBoard liveBoard) {
        setBorder(new EmptyBorder(10, 10, 10, 10));
        setLayout(new BorderLayout());
        this.pnlStation = new StationPanel(liveBoard.stationInfo());

        add(pnlStation, BorderLayout.PAGE_START);

        JPanel pnlContent = new JPanel(new BorderLayout());
        Departures departures = liveBoard.departures();
        lblDepartureNumber = new CaptionedLabel();
        lblDepartureNumber.setText(String.valueOf(departures.number()));
        pnlContent.add(lblDepartureNumber, BorderLayout.LINE_START);

        JTable tblDepartures = new JTable();
        departuresModel = new DefaultTableModel();
        Arrays.stream(Departure.class.getDeclaredFields()).forEach(field -> departuresModel.addColumn(field.getName()));
        departures.departures().forEach(departure -> {
            Station station = departure.stationInfo();
            Date time = new Date(Long.parseLong(departure.time()) * MILLISECONDS_IN_SECOND);
            int delay = departure.delay() / SECONDS_IN_MINUTE;
            Vehicle vehicle = departure.vehicleInfo();
            Platform platform = departure.platformInfo();
            Occupancy occupancy = departure.occupancy();
            departuresModel.addRow(new Object[] {
                    departure.id(),
                    delay,
                    departure.station(),
                    station != null ? station.id() : "",
                    time,
                    departure.vehicle(),
                    vehicle != null ? vehicle.name() : "",
                    departure.platform(),
                    platform != null ? platform.name() : "",
                    departure.canceled(),
                    departure.left(),
                    departure.departureConnection(),
                    occupancy != null ? occupancy.name() : ""
            });
        });
        tblDepartures.setModel(departuresModel);

        pnlContent.add(new JScrollPane(tblDepartures, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED), BorderLayout.PAGE_END);

        add(pnlContent, BorderLayout.PAGE_END);

        setVisible(true);
    }

    @Override
    public void localeChanged(Locale locale, I18NController i18NController) {
        pnlStation.localeChanged(locale, i18NController);
        lblDepartureNumber.setCaption(i18NController.getMessage("live.board.departures"));
        departuresModel.setColumnIdentifiers(
                Arrays.stream(Departure.class.getDeclaredFields())
                        .map(Field::getName)
                        .map(fieldName -> i18NController.getMessage("live.board.departures." + fieldName))
                        .toArray()
        );
    }
}
