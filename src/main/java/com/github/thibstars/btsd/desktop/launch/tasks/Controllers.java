package com.github.thibstars.btsd.desktop.launch.tasks;

import com.github.thibstars.btsd.desktop.about.AboutController;
import com.github.thibstars.btsd.desktop.liveboard.LiveBoardController;
import com.github.thibstars.btsd.desktop.stations.StationsController;

/**
 * @author Thibault Helsmoortel
 */
public record Controllers(
        AboutController aboutController,

        LiveBoardController liveBoardController,

        StationsController stationsController
) {

}
