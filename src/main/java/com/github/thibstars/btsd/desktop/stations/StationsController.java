package com.github.thibstars.btsd.desktop.stations;

import com.github.thibstars.btsd.desktop.i18n.I18NController;
import com.github.thibstars.btsd.desktop.liveboard.LiveBoardController;
import com.github.thibstars.jirail.client.StationService;
import java.awt.Dimension;
import javax.swing.DefaultListSelectionModel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

/**
 * @author Thibault Helsmoortel
 */
public class StationsController {

    private final StationsTable stationsTable;

    private final StationService stationService;

    private final LiveBoardController liveBoardController;

    private final I18NController i18NController;

    public StationsController(StationsTable stationsTable, StationService stationService,
            LiveBoardController liveBoardController, I18NController i18NController) {
        this.stationsTable = stationsTable;
        this.stationService = stationService;
        this.liveBoardController = liveBoardController;
        this.i18NController = i18NController;

        stationsTable.init(this);
    }

    public void initStationsTable(Dimension dimension) {
        DefaultListSelectionModel selectionModel = new DefaultListSelectionModel();
        selectionModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        selectionModel.addListSelectionListener(event -> {
            if (event.getValueIsAdjusting()) {
                stationsTable.getStationInRow(stationsTable.getSelectedRow())
                        .ifPresent(station -> liveBoardController.showLiveBoardForStation(stationsTable, station.id(), dimension));
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

    protected void fetchStations() {
        stationsTable.setStations(stationService.getStations(i18NController.getPreferredLocale().getLanguage()));
    }
}
