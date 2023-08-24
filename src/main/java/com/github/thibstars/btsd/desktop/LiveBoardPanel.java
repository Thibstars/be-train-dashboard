package com.github.thibstars.btsd.desktop;

import com.github.thibstars.btsd.irail.model.Departure;
import com.github.thibstars.btsd.irail.model.Departures;
import com.github.thibstars.btsd.irail.model.LiveBoard;
import com.github.thibstars.btsd.irail.model.Station;
import java.awt.BorderLayout;
import java.util.Arrays;
import java.util.Date;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

/**
 * @author Thibault Helsmoortel
 */
public class LiveBoardPanel extends JPanel {

    private static final int MILLISECONDS_IN_SECOND = 1000;

    public LiveBoardPanel(LiveBoard liveBoard) {
        setBorder(new EmptyBorder(10, 10, 10, 10));
        setLayout(new BorderLayout());
        StationPanel pnlStation = new StationPanel(liveBoard.stationInfo());

        add(pnlStation, BorderLayout.PAGE_START);

        JPanel pnlContent = new JPanel(new BorderLayout());
        Departures departures = liveBoard.departures();
        JLabel lblDepartureNumber = new JLabel("Departures: " + departures.number());
        pnlContent.add(lblDepartureNumber, BorderLayout.PAGE_START);

        JTable tblDepartures = new JTable();
        DefaultTableModel model = new DefaultTableModel();
        Arrays.stream(Departure.class.getDeclaredFields()).forEach(field -> model.addColumn(field.getName()));
        departures.departures().forEach(departure -> {
            Station station = departure.stationInfo();
            Date time = new Date(Long.parseLong(departure.time()) * MILLISECONDS_IN_SECOND);
            model.addRow(new Object[] {departure.id(), departure.delay(), departure.station(), station != null ? station.id() : "",
                    time, departure.vehicle(), departure.platform(), departure.canceled(), departure.left()});
        });
        tblDepartures.setModel(model);

        pnlContent.add(new JScrollPane(tblDepartures, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED), BorderLayout.PAGE_END);

        add(pnlContent, BorderLayout.PAGE_END);

        setVisible(true);
    }
}
