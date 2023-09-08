package com.github.thibstars.btsd.desktop.launch.tasks;

import com.github.thibstars.btsd.internal.PropertiesService;
import com.github.thibstars.btsd.irail.client.LiveBoardService;
import com.github.thibstars.btsd.irail.client.StationService;

/**
 * @author Thibault Helsmoortel
 */
public record Services(
        PropertiesService propertiesService,

        LiveBoardService liveBoardService,

        StationService stationService
) {

}
