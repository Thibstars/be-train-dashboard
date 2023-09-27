package com.github.thibstars.btsd.desktop.liveboard;

import com.github.thibstars.btsd.desktop.i18n.I18NController;
import com.github.thibstars.btsd.desktop.listeners.LocaleChangeListener;
import com.github.thibstars.btsd.irail.model.LiveBoard;
import java.awt.Dimension;
import java.util.Locale;
import javax.swing.JFrame;
import javax.swing.WindowConstants;

/**
 * @author Thibault Helsmoortel
 */
public class LiveBoardFrame extends JFrame implements LocaleChangeListener {

    private final LiveBoardPanel liveBoardPanel;

    public LiveBoardFrame(LiveBoardController liveBoardController, LiveBoard liveBoard, Dimension dimension) {
        setTitle("Live Board - " + liveBoard.station());
        setPreferredSize(dimension);
        liveBoardPanel = new LiveBoardPanel(liveBoardController, liveBoard, this);
        add(liveBoardPanel);
        pack();
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    @Override
    public void localeChanged(Locale locale, I18NController i18NController) {
        liveBoardPanel.localeChanged(locale, i18NController);
    }
}
