package com.github.thibstars.btsd.desktop.components;

import com.github.thibstars.btsd.desktop.i18n.I18NController;
import com.github.thibstars.btsd.desktop.listeners.LocaleChangeListener;
import java.awt.event.ActionListener;
import java.util.Locale;
import javax.swing.JButton;
import javax.swing.JPanel;

/**
 * @author Thibault Helsmoortel
 */
public class RefreshPanel extends JPanel implements LocaleChangeListener {

    private CaptionedLabel lblLastRefresh;

    private JButton btnRefresh;

    public RefreshPanel() {
        this.lblLastRefresh = new CaptionedLabel();
        this.btnRefresh = new JButton();

        add(lblLastRefresh);
        add(btnRefresh);
    }

    @Override
    public void localeChanged(Locale locale, I18NController i18NController) {
        lblLastRefresh.setCaption(i18NController.getMessage("refresh.last.refresh"));
        btnRefresh.setText(i18NController.getMessage("refresh.refresh"));
    }

    public void addRefreshListener(ActionListener listener) {
        btnRefresh.addActionListener(listener);
    }

    public void setLastRefresh(String timeStamp) {
        lblLastRefresh.setText(timeStamp);
    }
}
