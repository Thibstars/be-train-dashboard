package com.github.thibstars.btsd.desktop.stations;

import com.github.thibstars.btsd.desktop.liveboard.LiveBoardController;
import com.github.thibstars.jirail.client.StationService;
import java.awt.Dimension;
import javax.swing.DefaultListSelectionModel;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * @author Thibault Helsmoortel
 */
@ExtendWith(MockitoExtension.class)
class StationsControllerTest {

    @Mock
    private StationsTable stationsTable;

    @SuppressWarnings("unused") // Necessary mock
    @Mock
    private StationService stationService;

    @SuppressWarnings("unused") // Necessary mock
    @Mock
    private LiveBoardController liveBoardController;

    @InjectMocks
    private StationsController stationsController;

    @Test
    void shouldInitStationsTable() {
        stationsController.initStationsTable(new Dimension());

        Mockito.verify(stationsTable).setSelectionModel(ArgumentMatchers.any(DefaultListSelectionModel.class));
    }

    @Test
    void shouldGetStationsTableScrollPane() {
        stationsController.getStationsTableScrollPane();

        Mockito.verify(stationsTable).getScrollPane();
    }

    @Test
    void shouldFilterStationsTableByName() {
        String stationName = "Antwerp";
        stationsController.filterStationsTableByName(stationName);

        Mockito.verify(stationsTable).filterStationsTableByName(stationName);
    }
}