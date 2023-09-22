package com.github.thibstars.btsd.desktop.launch.tasks;

import com.github.thibstars.btsd.desktop.about.AboutController;
import com.github.thibstars.btsd.desktop.i18n.I18NController;
import com.github.thibstars.btsd.desktop.issue.ReportIssueController;
import com.github.thibstars.btsd.desktop.liveboard.LiveBoardController;
import com.github.thibstars.btsd.desktop.preferences.PreferencesController;
import com.github.thibstars.btsd.desktop.stations.StationsController;

/**
 * @author Thibault Helsmoortel
 */
public record Controllers(
        AboutController aboutController,

        LiveBoardController liveBoardController,

        StationsController stationsController,

        ReportIssueController reportIssueController,

        I18NController i18NController,

        PreferencesController preferencesController
) {

}
