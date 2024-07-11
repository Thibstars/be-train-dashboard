package com.github.thibstars.btsd.desktop.launch.tasks;

import com.github.thibstars.btsd.internal.PropertiesService;
import com.github.thibstars.jirail.client.LiveBoardService;
import com.github.thibstars.jirail.client.StationService;

/**
 * @author Thibault Helsmoortel
 */
public record Services(
        PropertiesService propertiesService,

        LiveBoardService liveBoardService,

        StationService stationService,
        com.github.thibstars.btsd.internal.PreferencesService preferencesService,
        com.github.thibstars.btsd.internal.I18NService i18NService) {

}
