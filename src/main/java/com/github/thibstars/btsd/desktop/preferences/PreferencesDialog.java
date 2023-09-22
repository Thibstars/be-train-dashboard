package com.github.thibstars.btsd.desktop.preferences;

import com.github.thibstars.btsd.desktop.components.PlaceholderTextField;
import com.github.thibstars.btsd.desktop.i18n.I18NController;
import com.github.thibstars.btsd.desktop.i18n.LocaleComboBox;
import com.github.thibstars.btsd.desktop.listeners.LocaleChangeListener;
import com.github.thibstars.btsd.internal.SupportedLocale;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.ItemSelectable;
import java.util.Locale;
import java.util.Objects;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

/**
 * @author Thibault Helsmoortel
 */
public class PreferencesDialog extends JDialog implements LocaleChangeListener {

    private final LocaleComboBox localeComboBox;

    private final JLabel lblSelectLocale;

    private final JLabel lblDateTimeFormat;

    private final JTextField tfDateTimeFormat;

    private final JButton btnSave;

    private final JButton btnCancel;

    public PreferencesDialog(PreferencesController preferencesController) {
        setTitle(preferencesController.getMessage("preferences.title"));

        this.localeComboBox = new LocaleComboBox();
        localeComboBox.addItemListener(event -> {
            ItemSelectable itemSelectable = event.getItemSelectable();
            SupportedLocale selected = (SupportedLocale) itemSelectable.getSelectedObjects()[0];
            localeChanged(selected.getLocale(), preferencesController.getI18NController());
        });
        JPanel contentPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        contentPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        this.lblSelectLocale = new JLabel(preferencesController.getMessage("preferences.locale.select"));
        contentPanel.add(lblSelectLocale);
        contentPanel.add(localeComboBox);

        this.lblDateTimeFormat = new JLabel(preferencesController.getMessage("preferences.date.time.format"));
        this.tfDateTimeFormat = new PlaceholderTextField();

        contentPanel.add(lblDateTimeFormat);
        contentPanel.add(tfDateTimeFormat);

        this.btnSave = new JButton(preferencesController.getMessage("preferences.save"));
        btnSave.addActionListener(event -> {
            SupportedLocale selected = (SupportedLocale) localeComboBox.getSelectedItem();
            preferencesController.changeLocale(Objects.requireNonNull(selected).getLocale());

            preferencesController.changeDateTimeFormat(tfDateTimeFormat.getText());

            dispose();
        });

        this.btnCancel = new JButton(preferencesController.getMessage("preferences.cancel"));
        btnCancel.addActionListener(event -> dispose());

        contentPanel.add(btnCancel);
        contentPanel.add(btnSave);

        setPreferredSize(new Dimension(300, 150));
        setContentPane(contentPanel);
        pack();
        setLocationRelativeTo(null);
        setAlwaysOnTop(true);
    }

    protected void setSelectedLocale(SupportedLocale supportedLocale) {
        this.localeComboBox.setSelectedLocale(supportedLocale);
    }

    public void setPreferredDateTimeFormat(String dateTimeFormat) {
        this.tfDateTimeFormat.setText(dateTimeFormat);
    }

    @Override
    public void localeChanged(Locale locale, I18NController i18NController) {
        setTitle(i18NController.getMessage("preferences.title", locale));
        lblSelectLocale.setText(i18NController.getMessage("preferences.locale.select", locale));
        lblDateTimeFormat.setText(i18NController.getMessage("preferences.date.time.format", locale));
        btnCancel.setText(i18NController.getMessage("preferences.cancel", locale));
        btnSave.setText(i18NController.getMessage("preferences.save", locale));
    }
}
