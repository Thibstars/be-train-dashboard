package com.github.thibstars.btsd.desktop.stations;

import javax.swing.table.DefaultTableModel;

/**
 * @author Thibault Helsmoortel
 */
public class StationsTableModel extends DefaultTableModel {

    @Override
    public boolean isCellEditable(int row, int column) {
        return false;
    }

}
