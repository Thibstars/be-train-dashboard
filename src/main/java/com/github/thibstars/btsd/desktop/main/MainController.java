package com.github.thibstars.btsd.desktop.main;

import com.github.thibstars.btsd.desktop.about.AboutController;
import com.github.thibstars.btsd.desktop.stations.StationsController;
import java.awt.Dimension;
import javax.swing.JScrollPane;

/**
 * @author Thibault Helsmoortel
 */
public class MainController {

    private final MainFrame mainFrame;

    private final AboutController aboutController;

    private final StationsController stationsController;

    public MainController(MainFrame mainFrame, AboutController aboutController, StationsController stationsController) {
        this.mainFrame = mainFrame;
        this.aboutController = aboutController;
        this.stationsController = stationsController;

        mainFrame.init(this);
    }

    public void showView() {
        mainFrame.setVisible(true);
    }

    protected void setAppName(String appName) {
        aboutController.setAppName(appName);
    }

    protected void showAboutView() {
        aboutController.showView();
    }

    protected void initStationsTable(Dimension dimension) {
        stationsController.initStationsTable(dimension);
    }

    protected JScrollPane getStationsTableScrollPane() {
        return stationsController.getStationsTableScrollPane();
    }

    protected void filterStationsTableByName(String name) {
        stationsController.filterStationsTableByName(name);
    }
}
