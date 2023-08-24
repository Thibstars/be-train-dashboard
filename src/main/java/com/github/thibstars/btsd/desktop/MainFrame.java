package com.github.thibstars.btsd.desktop;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.HeadlessException;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.DefaultListSelectionModel;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.RowFilter;
import javax.swing.ScrollPaneConstants;
import javax.swing.WindowConstants;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

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

        StationsTable stationTable = new StationsTable();
        DefaultListSelectionModel selectionModel = new DefaultListSelectionModel();
        selectionModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        selectionModel.addListSelectionListener(event -> {
            if (event.getValueIsAdjusting()) {
                stationTable.getStationInRow(stationTable.getSelectedRow())
                        .ifPresent(station -> {
                            StationPanel stationPanel = new StationPanel(station);

                            JFrame stationFrame = new JFrame(station.name());

                            stationFrame.add(stationPanel);
                            stationFrame.pack();
                            stationFrame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
                            stationFrame.setLocationRelativeTo(null);
                            stationFrame.setVisible(true);
                        });
            }
        });
        stationTable.setSelectionModel(selectionModel);

        JScrollPane spTable = new JScrollPane(stationTable, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        spTable.setViewportView(stationTable);

        JTextField tfNameFilter = new JTextField();
        tfNameFilter.setPreferredSize(new Dimension(250, 20));

        tfNameFilter.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                super.keyReleased(e);
                ((TableRowSorter<TableModel>) stationTable.getRowSorter())
                        .setRowFilter(RowFilter.regexFilter("(?i)" + tfNameFilter.getText()));
            }
        });

        contentPanel.add(tfNameFilter, BorderLayout.PAGE_START);
        contentPanel.add(spTable, BorderLayout.CENTER);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
