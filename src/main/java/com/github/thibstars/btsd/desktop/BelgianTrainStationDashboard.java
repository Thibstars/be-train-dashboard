package com.github.thibstars.btsd.desktop;

import com.github.thibstars.btsd.irail.client.StationService;
import com.github.thibstars.btsd.irail.client.StationServiceImpl;
import com.github.thibstars.btsd.irail.model.Station;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.io.IOException;
import java.util.Set;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListModel;
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
            stationTable.setEnabled(false);
            JPanel pnlTable = new JPanel();
            JScrollPane spTable = new JScrollPane(pnlTable, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
            pnlTable.add(stationTable);

            contentPanel.add(spTable);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        mainFrame.pack();
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setVisible(true);
    }

}
