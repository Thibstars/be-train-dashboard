package com.github.thibstars.btsd.desktop.liveboard;

import com.github.thibstars.btsd.desktop.i18n.I18NController;
import com.github.thibstars.btsd.irail.client.LiveBoardService;
import java.awt.Dimension;

/**
 * @author Thibault Helsmoortel
 */
public class LiveBoardController {

    private final LiveBoardService liveBoardService;

    private final I18NController i18NController;

    public LiveBoardController(LiveBoardService liveBoardService, I18NController i18NController) {
        this.liveBoardService = liveBoardService;
        this.i18NController = i18NController;
    }

    public void showLiveBoardForStation(String id, Dimension dimension) {
        liveBoardService.getForStation(id)
                .ifPresent(liveBoard -> {
                    LiveBoardFrame liveBoardFrame = new LiveBoardFrame(liveBoard, dimension);
                    i18NController.addListener(liveBoardFrame);
                    i18NController.initLocale();
                    liveBoardFrame.setVisible(true);
                });
    }

    protected String getMessage(String key) {
        return i18NController.getMessage(key);
    }

}
