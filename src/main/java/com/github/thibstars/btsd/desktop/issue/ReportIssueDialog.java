package com.github.thibstars.btsd.desktop.issue;

import com.github.thibstars.btsd.desktop.components.HyperLink;
import com.github.thibstars.btsd.desktop.i18n.I18NController;
import com.github.thibstars.btsd.desktop.listeners.LocaleChangeListener;
import java.awt.BorderLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URI;
import java.util.Locale;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

/**
 * @author Thibault Helsmoortel
 */
public class ReportIssueDialog extends JDialog implements LocaleChangeListener {

    private final JLabel lblContent;

    private final HyperLink gitHubIssueLink;

    public ReportIssueDialog() {
        JPanel pnlContent = new JPanel(new BorderLayout());
        pnlContent.setBorder(new EmptyBorder(10, 10, 10, 10));
        lblContent = new JLabel();
        pnlContent.add(lblContent, BorderLayout.PAGE_START);
        this.gitHubIssueLink = new HyperLink();
        this.gitHubIssueLink.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                dispose();
            }
        });
        pnlContent.add(gitHubIssueLink, BorderLayout.PAGE_END);

        setContentPane(pnlContent);

        pack();
        setLocationRelativeTo(null);
        setAlwaysOnTop(true);
    }

    protected void setGitHubIssueUri(URI gitHubIssueUri) {
        this.gitHubIssueLink.setUri(gitHubIssueUri);
    }

    @Override
    public void localeChanged(Locale locale, I18NController i18NController) {
        setTitle(i18NController.getMessage("report.issue.title"));
        lblContent.setText(i18NController.getMessage("report.issue.content"));
        gitHubIssueLink.setText(i18NController.getMessage("report.issue.link"));
    }
}
