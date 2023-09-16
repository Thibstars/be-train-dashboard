package com.github.thibstars.btsd.desktop.i18n;

import com.github.thibstars.btsd.internal.SupportedLocale;
import java.util.Arrays;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JLabel;

/**
 * @author Thibault Helsmoortel
 */
public class LocaleComboBox extends JComboBox<SupportedLocale> {

    private final DefaultComboBoxModel<SupportedLocale> model;

    public LocaleComboBox() {
        model = new DefaultComboBoxModel<>();
        model.addAll(Arrays.stream(SupportedLocale.values()).toList());

        setRenderer((list, value, index, isSelected, cellHasFocus) -> new JLabel(value.getLocale().getDisplayName(value.getLocale())));
        setModel(model);
    }

    public void setSelectedLocale(SupportedLocale supportedLocale) {
        model.setSelectedItem(supportedLocale);
    }
}
