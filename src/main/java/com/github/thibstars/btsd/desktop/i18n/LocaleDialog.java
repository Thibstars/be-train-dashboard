package com.github.thibstars.btsd.desktop.i18n;

import com.github.thibstars.btsd.desktop.listeners.LocaleChangeListener;
import com.github.thibstars.btsd.internal.SupportedLocale;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.ItemSelectable;
import java.util.Locale;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

/**
 * @author Thibault Helsmoortel
 */
public class LocaleDialog extends JDialog implements LocaleChangeListener {

    private final LocaleComboBox localeComboBox;

    private final JLabel lblSelectLocale;

    public LocaleDialog(I18NController i18NController) {
        setTitle(i18NController.getMessage("locale.select.title"));

        this.localeComboBox = new LocaleComboBox();
        localeComboBox.addItemListener(event -> {
            ItemSelectable itemSelectable = event.getItemSelectable();
            SupportedLocale selected = (SupportedLocale) itemSelectable.getSelectedObjects()[0];
            i18NController.changeLocale(selected.getLocale());
        });
        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        lblSelectLocale = new JLabel(i18NController.getMessage("locale.select"));
        contentPanel.add(lblSelectLocale, BorderLayout.PAGE_START);
        contentPanel.add(localeComboBox, BorderLayout.PAGE_END);

        setPreferredSize(new Dimension(250, 100));
        setContentPane(contentPanel);
        pack();
        setLocationRelativeTo(null);
        setAlwaysOnTop(true);
    }

    protected void setSelectedLocale(SupportedLocale supportedLocale) {
        this.localeComboBox.setSelectedLocale(supportedLocale);
    }

    @Override
    public void localeChanged(Locale locale, I18NController i18NController) {
        setTitle(i18NController.getMessage("locale.select.title"));
        lblSelectLocale.setText(i18NController.getMessage("locale.select"));
    }
}
