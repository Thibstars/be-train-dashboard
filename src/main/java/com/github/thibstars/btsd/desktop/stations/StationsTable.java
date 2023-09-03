package com.github.thibstars.btsd.desktop.stations;

import com.github.thibstars.btsd.irail.model.Station;
import java.awt.HeadlessException;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.RowFilter;
import javax.swing.RowSorter;
import javax.swing.RowSorter.SortKey;
import javax.swing.ScrollPaneConstants;
import javax.swing.SortOrder;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

/**
 * @author Thibault Helsmoortel
 */
public class StationsTable extends JTable {

    private static final String FILTER_IGNORE_CASE_REGEX = "(?i)";

    private final StationsTableModel model;

    private transient Set<Station> stations;

    public StationsTable() throws HeadlessException {
        model = new StationsTableModel();
        model.addColumn("id");
        model.addColumn("name");

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

    protected void setStations(Set<Station> stations) {
        this.stations = stations;
        model.setRowCount(0);
        stations.forEach(station -> model.addRow(new Object[] {station.id(), station.name()}));
    }

    protected JScrollPane getScrollPane() {
        JScrollPane spTable = new JScrollPane(this, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        spTable.setViewportView(this);

        return spTable;
    }

    protected void filterStationsTableByName(String name) {
        ((TableRowSorter<StationsTableModel>) getRowSorter())
                .setRowFilter(RowFilter.regexFilter(FILTER_IGNORE_CASE_REGEX + name));
    }
}
