package com.github.thibstars.btsd.desktop;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.thibstars.btsd.irail.client.StationService;
import com.github.thibstars.btsd.irail.client.StationServiceImpl;
import com.github.thibstars.btsd.irail.model.Station;
import java.awt.HeadlessException;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import javax.swing.JTable;
import javax.swing.RowSorter;
import javax.swing.RowSorter.SortKey;
import javax.swing.SortOrder;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import okhttp3.OkHttpClient;

/**
 * @author Thibault Helsmoortel
 */
public class StationsTable extends JTable {

    private transient Set<Station> stations;

    public StationsTable() throws HeadlessException {
        StationService stationService = new StationServiceImpl(new OkHttpClient(), new ObjectMapper());
        StationsTableModel model = new StationsTableModel();
        model.addColumn("id");
        model.addColumn("name");

        try {
            stations = stationService.getStations();
        } catch (IOException e) {
            stations = Collections.emptySet();
        }

        stations.forEach(station -> model.addRow(new Object[] {station.id(), station.name()}));

        setModel(model);

        TableRowSorter<TableModel> sorter = new TableRowSorter<>(model);
        setRowSorter(sorter);

        List<SortKey> sortKeys = List.of(new RowSorter.SortKey(1, SortOrder.ASCENDING));
        sorter.setSortKeys(sortKeys);

        setFillsViewportHeight(true);
    }

    public Optional<Station> getStationInRow(int rowIndex) {
        String stationId;
        try {
            stationId = super.getValueAt(rowIndex, 0).toString();
        } catch (ArrayIndexOutOfBoundsException e) {
            return Optional.empty();
        }

        return stations.stream()
                .filter(station -> station.id().equals(stationId))
                .findFirst();
    }
}
