package com.github.thibstars.btsd.desktop;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.thibstars.btsd.desktop.about.AboutController;
import com.github.thibstars.btsd.desktop.about.AboutDialog;
import com.github.thibstars.btsd.desktop.liveboard.LiveBoardController;
import com.github.thibstars.btsd.desktop.main.MainController;
import com.github.thibstars.btsd.desktop.main.MainFrame;
import com.github.thibstars.btsd.desktop.stations.StationsController;
import com.github.thibstars.btsd.desktop.stations.StationsTable;
import com.github.thibstars.btsd.internal.PropertiesService;
import com.github.thibstars.btsd.internal.PropertiesServiceImpl;
import com.github.thibstars.btsd.irail.client.LiveBoardServiceImpl;
import com.github.thibstars.btsd.irail.client.StationService;
import com.github.thibstars.btsd.irail.client.StationServiceImpl;
import okhttp3.OkHttpClient;

/**
 * @author Thibault Helsmoortel
 */
public class DesktopLauncher {

    protected void launch() {
        PropertiesService propertiesService = new PropertiesServiceImpl();
        AboutController aboutController = new AboutController(propertiesService, new AboutDialog());

        OkHttpClient okHttpClient = new OkHttpClient();
        ObjectMapper objectMapper = new ObjectMapper();
        StationService stationService = new StationServiceImpl(okHttpClient, objectMapper);

        LiveBoardServiceImpl liveBoardService = new LiveBoardServiceImpl(okHttpClient, objectMapper);
        LiveBoardController liveBoardController = new LiveBoardController(liveBoardService);

        StationsController stationsController = new StationsController(new StationsTable(), stationService, liveBoardController);

        MainController mainController = new MainController(new MainFrame(), aboutController, stationsController);
        mainController.showView();
    }

}
