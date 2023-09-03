package com.github.thibstars.btsd.desktop.stations;

import com.github.thibstars.btsd.desktop.liveboard.LiveBoardController;
import com.github.thibstars.btsd.irail.client.StationService;
import java.awt.Dimension;
import javax.swing.DefaultListSelectionModel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

/**
 * @author Thibault Helsmoortel
 */
public class StationsController {

    private final StationsTable stationsTable;

    private final LiveBoardController liveBoardController;

    public StationsController(StationsTable stationsTable, StationService stationService,
            LiveBoardController liveBoardController) {
        this.stationsTable = stationsTable;
        this.liveBoardController = liveBoardController;

        stationsTable.setStations(stationService.getStations());
    }

    public void initStationsTable(Dimension dimension) {
        DefaultListSelectionModel selectionModel = new DefaultListSelectionModel();
        selectionModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        selectionModel.addListSelectionListener(event -> {
            if (event.getValueIsAdjusting()) {
                stationsTable.getStationInRow(stationsTable.getSelectedRow())
                        .ifPresent(station -> liveBoardController.showLiveBoardForStation(station.id(), dimension));
                stationsTable.clearSelection();
            }
        });
        stationsTable.setSelectionModel(selectionModel);
    }

    public JScrollPane getStationsTableScrollPane() {
        return stationsTable.getScrollPane();
    }

    public void filterStationsTableByName(String name) {
        stationsTable.filterStationsTableByName(name);
    }
}
