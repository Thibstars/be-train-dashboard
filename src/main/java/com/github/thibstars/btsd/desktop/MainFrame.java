package com.github.thibstars.btsd.desktop;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.thibstars.btsd.irail.client.StationService;
import com.github.thibstars.btsd.irail.client.StationServiceImpl;
import com.github.thibstars.btsd.irail.exceptions.ClientException;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.HeadlessException;
import java.io.IOException;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.RowSorter;
import javax.swing.RowSorter.SortKey;
import javax.swing.ScrollPaneConstants;
import javax.swing.SortOrder;
import javax.swing.WindowConstants;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import okhttp3.OkHttpClient;

/**
 * @author Thibault Helsmoortel
 */
public class MainFrame extends JFrame {

    public MainFrame() throws HeadlessException {
        setTitle("Belgian Train Station Dashboard");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        JPanel contentPanel = new JPanel();
        contentPanel.setPreferredSize(new Dimension(900, 600));
        setContentPane(contentPanel);
        contentPanel.setLayout(new BorderLayout());

        StationService stationService = new StationServiceImpl(new OkHttpClient(), new ObjectMapper());
        try {
            DefaultTableModel model = new DefaultTableModel();
            model.addColumn("id");
            model.addColumn("name");
            stationService.getStations().forEach(station -> model.addRow(new Object[] {station.id(), station.name()}));
            JTable stationTable = new JTable(model);
            TableRowSorter<TableModel> sorter = new TableRowSorter<>(stationTable.getModel());
            stationTable.setRowSorter(sorter);

            List<SortKey> sortKeys = List.of(new RowSorter.SortKey(1, SortOrder.ASCENDING));
            sorter.setSortKeys(sortKeys);
            stationTable.setFillsViewportHeight(true);
            stationTable.setEnabled(false);
            JScrollPane spTable = new JScrollPane(stationTable, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
            spTable.setViewportView(stationTable);

            contentPanel.add(spTable);
        } catch (IOException e) {
            throw new ClientException(e);
        }

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
