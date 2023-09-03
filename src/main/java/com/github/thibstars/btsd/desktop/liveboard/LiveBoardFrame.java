package com.github.thibstars.btsd.desktop.liveboard;

import com.github.thibstars.btsd.irail.model.LiveBoard;
import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.WindowConstants;

/**
 * @author Thibault Helsmoortel
 */
public class LiveBoardFrame extends JFrame {
    
    public LiveBoardFrame(LiveBoard liveBoard, Dimension dimension) {
        setTitle("Live Board - " + liveBoard.station());
        setPreferredSize(dimension);
        add(new LiveBoardPanel(liveBoard));
        pack();
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
    }

}
