package com.github.thibstars.btsd.desktop;

import com.github.thibstars.btsd.irail.client.StationService;
import com.github.thibstars.btsd.irail.client.StationServiceImpl;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.io.IOException;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ScrollPaneConstants;
import javax.swing.table.DefaultTableModel;

/**
 * @author Thibault Helsmoortel
 */
public class BelgianTrainStationDashboard {

    public static void main(String[] args) {
        JFrame mainFrame = new JFrame("Belgian Train Station Dashboard");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel contentPanel = new JPanel();
        contentPanel.setPreferredSize(new Dimension(900, 600));
        mainFrame.setContentPane(contentPanel);
        contentPanel.setLayout(new BorderLayout());

        StationService stationService = new StationServiceImpl();
        try {
            DefaultTableModel model = new DefaultTableModel();
            model.addColumn("id");
            model.addColumn("name");
            stationService.getStations().forEach(station -> model.addRow(new Object[] {station.id(), station.name()}));
            JTable stationTable = new JTable(model);
            stationTable.setFillsViewportHeight(true);
            stationTable.setEnabled(false);
            JScrollPane spTable = new JScrollPane(stationTable, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
            spTable.setViewportView(stationTable);

            contentPanel.add(spTable);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        mainFrame.pack();
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setVisible(true);
    }

}
