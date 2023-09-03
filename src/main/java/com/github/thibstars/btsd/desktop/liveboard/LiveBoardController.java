package com.github.thibstars.btsd.desktop.liveboard;

import com.github.thibstars.btsd.irail.client.LiveBoardService;
import java.awt.Dimension;

/**
 * @author Thibault Helsmoortel
 */
public class LiveBoardController {

    private final LiveBoardService liveBoardService;

    public LiveBoardController(LiveBoardService liveBoardService) {
        this.liveBoardService = liveBoardService;
    }

    public void showLiveBoardForStation(String id, Dimension dimension) {
        liveBoardService.getForStation(id)
                .ifPresent(liveBoard -> {
                    LiveBoardFrame liveBoardFrame = new LiveBoardFrame(liveBoard, dimension);
                    liveBoardFrame.setVisible(true);
                });
    }

}
