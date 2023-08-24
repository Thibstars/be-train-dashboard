package com.github.thibstars.btsd.desktop;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.thibstars.btsd.irail.client.LiveBoardService;
import com.github.thibstars.btsd.irail.client.LiveBoardServiceImpl;
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
import javax.swing.border.EmptyBorder;
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
        contentPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        setContentPane(contentPanel);
        contentPanel.setLayout(new BorderLayout());

        StationsTable stationTable = new StationsTable();
        DefaultListSelectionModel selectionModel = new DefaultListSelectionModel();
        selectionModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        LiveBoardService liveBoardService = new LiveBoardServiceImpl(new OkHttpClient(), new ObjectMapper());
        selectionModel.addListSelectionListener(event -> {
            if (event.getValueIsAdjusting()) {
                stationTable.getStationInRow(stationTable.getSelectedRow())
                        .flatMap(station -> liveBoardService.getForStation(station.id()))
                        .ifPresent(liveBoard -> {
                            LiveBoardPanel liveBoardPanel = new LiveBoardPanel(liveBoard);

                            JFrame liveBoardFrame = new JFrame("Live Board - " + liveBoard.station());

                            liveBoardFrame.setPreferredSize(new Dimension(875, 575));
                            liveBoardFrame.add(liveBoardPanel);
                            liveBoardFrame.pack();
                            liveBoardFrame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
                            liveBoardFrame.setLocationRelativeTo(null);
                            liveBoardFrame.setVisible(true);
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
