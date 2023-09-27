package com.github.thibstars.btsd.desktop.components;

import com.github.thibstars.btsd.desktop.i18n.I18NController;
import com.github.thibstars.btsd.desktop.listeners.LocaleChangeListener;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Arrays;
import java.util.Locale;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.Timer;

/**
 * @author Thibault Helsmoortel
 */
public class RefreshPanel extends JPanel implements LocaleChangeListener {

    private static final int DEFAULT_DELAY = 30000; // 30 seconds

    private final CaptionedLabel lblLastRefresh;

    private final JButton btnRefresh;

    private final JCheckBox cbAutoRefresh;

    private final Timer timer;

    public RefreshPanel(JFrame host) {
        this.lblLastRefresh = new CaptionedLabel();
        this.btnRefresh = new JButton();
        this.cbAutoRefresh = new JCheckBox();

        cbAutoRefresh.setSelected(true);
        cbAutoRefresh.setHorizontalTextPosition(SwingConstants.LEFT);

        timer = new Timer(DEFAULT_DELAY, actionEvent -> Arrays.stream(btnRefresh.getActionListeners())
                .forEach(listener -> listener.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, null) {
                    // Nothing needs to go here, this will trigger the respective listener
                })));

        cbAutoRefresh.addActionListener(event -> {
            boolean selected = cbAutoRefresh.isSelected();

            if (!selected && timer.isRunning()) {
                timer.stop();
            } else {
                timer.restart();
            }
        });

        host.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                super.windowClosed(e);
                timer.stop();
            }
        });

        timer.start();

        setLayout(new BorderLayout());

        add(lblLastRefresh, BorderLayout.LINE_START);
        add(btnRefresh, BorderLayout.CENTER);
        add(cbAutoRefresh, BorderLayout.LINE_END);
    }

    @Override
    public void localeChanged(Locale locale, I18NController i18NController) {
        lblLastRefresh.setCaption(i18NController.getMessage("refresh.last.refresh"));
        btnRefresh.setText(i18NController.getMessage("refresh.refresh"));
        cbAutoRefresh.setText(i18NController.getMessage("refresh.automatic"));
        cbAutoRefresh.setToolTipText(i18NController.getMessage("refresh.automatic.tooltip"));
    }

    public void addRefreshListener(ActionListener listener) {
        btnRefresh.addActionListener(listener);
    }

    public void setLastRefresh(String timeStamp) {
        lblLastRefresh.setText(timeStamp);
    }
}
