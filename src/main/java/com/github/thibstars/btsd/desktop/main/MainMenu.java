package com.github.thibstars.btsd.desktop.main;

import com.github.thibstars.btsd.desktop.i18n.I18NController;
import com.github.thibstars.btsd.desktop.listeners.LocaleChangeListener;
import java.util.Locale;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

/**
 * @author Thibault Helsmoortel
 */
public class MainMenu extends JMenu implements LocaleChangeListener {

    private final JMenuItem miAbout;

    private final JMenuItem miReportIssue;

    private final JMenuItem miPreferences;
    
    public MainMenu(MainController mainController) {
        super(mainController.getMessage("main.menu.general"));
        miAbout = new JMenuItem(mainController.getMessage("main.menu.general.about"));
        miAbout.addActionListener(event -> mainController.showAboutView());
        miReportIssue = new JMenuItem(mainController.getMessage("main.menu.general.issue"));
        miReportIssue.addActionListener(event -> mainController.showReportIssueView());
        miPreferences = new JMenuItem(mainController.getMessage("main.menu.general.preferences"));
        miPreferences.addActionListener(event -> mainController.showPreferencesView());
        add(miAbout);
        add(miReportIssue);
        add(miPreferences);
    }

    @Override
    public void localeChanged(Locale locale, I18NController i18NController) {
        setText(i18NController.getMessage("main.menu.general"));
        miAbout.setText(i18NController.getMessage("main.menu.general.about"));
        miReportIssue.setText(i18NController.getMessage("main.menu.general.issue"));
        miPreferences.setText(i18NController.getMessage("main.menu.general.preferences"));
    }
}
