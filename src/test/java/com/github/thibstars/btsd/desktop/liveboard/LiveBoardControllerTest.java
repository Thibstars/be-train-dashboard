package com.github.thibstars.btsd.desktop.liveboard;

import com.github.thibstars.btsd.irail.client.LiveBoardService;
import java.awt.Dimension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * @author Thibault Helsmoortel
 */
@ExtendWith(MockitoExtension.class)
class LiveBoardControllerTest {

    @Mock
    private LiveBoardService liveBoardService;

    @InjectMocks
    private LiveBoardController liveBoardController;

    @Test
    void shouldShowLiveBoardForStation() {
        String stationId = "myAwesomeStation";

        liveBoardController.showLiveBoardForStation(stationId, new Dimension());

        Mockito.verify(liveBoardService).getForStation(stationId);
    }
}