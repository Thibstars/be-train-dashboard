package com.github.thibstars.btsd.desktop;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.thibstars.btsd.irail.client.StationService;
import com.github.thibstars.btsd.irail.client.StationServiceImpl;
import com.github.thibstars.btsd.irail.model.Station;
import java.awt.HeadlessException;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import javax.swing.JTable;
import javax.swing.RowSorter;
import javax.swing.RowSorter.SortKey;
import javax.swing.SortOrder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import okhttp3.OkHttpClient;

/**
 * @author Thibault Helsmoortel
 */
public class StationsTable extends JTable {

    public StationsTable() throws HeadlessException {
        StationService stationService = new StationServiceImpl(new OkHttpClient(), new ObjectMapper());
        Set<Station> stations;
        DefaultTableModel model = new DefaultTableModel();
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
        setEnabled(false);
    }
}
