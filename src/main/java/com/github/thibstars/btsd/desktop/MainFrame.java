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
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
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

    private static final Dimension PREFERRED_FRAME_SIZE = new Dimension(900, 600);

    private static final EmptyBorder INSETS = new EmptyBorder(10, 10, 10, 10);

    private static final int SUBFRAME_BOUND_DIFF = 25;

    private static final String FILTER_IGNORE_CASE_REGEX = "(?i)";

    public MainFrame() throws HeadlessException {
        setTitle("Belgian Train Station Dashboard");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        JMenuBar menuBar = new JMenuBar();
        JMenu mainMenu = new JMenu("General");
        JMenuItem about = new JMenuItem("About");
        about.addActionListener(event -> {
            AboutDialog aboutDialog = new AboutDialog(getTitle());
            aboutDialog.setVisible(true);
        });
        mainMenu.add(about);
        menuBar.add(mainMenu);
        setJMenuBar(menuBar);

        JPanel contentPanel = new JPanel();
        contentPanel.setPreferredSize(PREFERRED_FRAME_SIZE);
        contentPanel.setBorder(INSETS);
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

                            liveBoardFrame.setPreferredSize(new Dimension(contentPanel.getWidth() - SUBFRAME_BOUND_DIFF, contentPanel.getHeight() - SUBFRAME_BOUND_DIFF));
                            liveBoardFrame.add(liveBoardPanel);
                            liveBoardFrame.pack();
                            liveBoardFrame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
                            liveBoardFrame.setLocationRelativeTo(null);
                            liveBoardFrame.setVisible(true);
                        });
                stationTable.clearSelection();
            }
        });
        stationTable.setSelectionModel(selectionModel);

        JScrollPane spTable = new JScrollPane(stationTable, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        spTable.setViewportView(stationTable);

        JTextField tfNameFilter = new JTextField();

        tfNameFilter.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                super.keyReleased(e);
                ((TableRowSorter<TableModel>) stationTable.getRowSorter())
                        .setRowFilter(RowFilter.regexFilter(FILTER_IGNORE_CASE_REGEX + tfNameFilter.getText()));
            }
        });

        contentPanel.add(tfNameFilter, BorderLayout.PAGE_START);
        contentPanel.add(spTable, BorderLayout.CENTER);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
