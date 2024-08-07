package com.github.thibstars.btsd.desktop.liveboard;

import com.github.thibstars.btsd.desktop.i18n.I18NController;
import com.github.thibstars.btsd.desktop.stations.StationsTable;
import com.github.thibstars.jirail.client.LiveBoardService;
import java.awt.Dimension;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Thibault Helsmoortel
 */
public class LiveBoardController {

    private static final Logger LOGGER = LoggerFactory.getLogger(LiveBoardController.class);

    private final LiveBoardService liveBoardService;

    private final I18NController i18NController;

    public LiveBoardController(LiveBoardService liveBoardService, I18NController i18NController) {
        this.liveBoardService = liveBoardService;
        this.i18NController = i18NController;
    }

    public void showLiveBoardForStation(StationsTable stationsTable, String id, Dimension dimension) {
        liveBoardService.getForStation(id, i18NController.getPreferredLocale().getLanguage())
                .ifPresentOrElse(
                        liveBoard -> {
                            LiveBoardFrame liveBoardFrame = new LiveBoardFrame(this, liveBoard, dimension);
                            i18NController.addListener(liveBoardFrame);
                            i18NController.initLocale();
                            liveBoardFrame.setVisible(true);
                        }, () -> SwingUtilities.invokeLater(
                                () -> JOptionPane.showInternalMessageDialog(
                                        JOptionPane.getDesktopPaneForComponent(stationsTable),
                                        i18NController.getMessage("live.board.warning.failed.fetch.message"),
                                        i18NController.getMessage("live.board.warning.failed.fetch.title"),
                                        JOptionPane.WARNING_MESSAGE
                                )
                        ));
    }

    protected void refreshLiveBoard(LiveBoardPanel liveBoardPanel, String id) {
        liveBoardService.getForStation(id, i18NController.getPreferredLocale().getLanguage())
                .ifPresentOrElse(
                        liveBoardPanel::update,
                        () -> LOGGER.warn("Unable to refresh live board.")
                );
    }

    public void refreshLiveBoard(LiveBoardPanel liveBoardPanel, String id, LocalTime localTime) {
        liveBoardService.getForStation(id, i18NController.getPreferredLocale().getLanguage(), localTime)
                .ifPresentOrElse(
                        liveBoardPanel::update,
                        () -> LOGGER.warn("Unable to refresh live board.")
                );
    }

    protected String getMessage(String key) {
        return i18NController.getMessage(key);
    }

    protected String formatDateTime(LocalDateTime localDateTime) {
        return localDateTime.format(DateTimeFormatter.ofPattern(i18NController.getPreferredDateTimeFormat(), i18NController.getPreferredLocale()));
    }
}
