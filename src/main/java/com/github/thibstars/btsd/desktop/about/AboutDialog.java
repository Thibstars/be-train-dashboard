package com.github.thibstars.btsd.desktop.about;

import com.github.thibstars.btsd.desktop.components.CaptionedLabel;
import com.github.thibstars.btsd.desktop.components.HyperLink;
import com.github.thibstars.btsd.desktop.i18n.I18NController;
import com.github.thibstars.btsd.desktop.listeners.LocaleChangeListener;
import java.awt.BorderLayout;
import java.net.URI;
import java.util.Locale;
import java.util.Optional;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * @author Thibault Helsmoortel
 */
public class AboutDialog extends JDialog implements LocaleChangeListener {

    private HyperLink gitHubRepoLink;

    private HyperLink gitHubUserLink;

    private final CaptionedLabel lblVersion;

    private JLabel lblMaintainedBy;

    private String appName;

    public AboutDialog() {
        JPanel pnlContent = new JPanel(new BorderLayout());

        lblVersion = new CaptionedLabel();
        pnlContent.add(lblVersion, BorderLayout.PAGE_START);

        JPanel pnlGitHub = createGitHubPanel();

        pnlContent.add(pnlGitHub, BorderLayout.PAGE_END);

        setContentPane(pnlContent);

        pack();
        setLocationRelativeTo(null);
        setAlwaysOnTop(true);
    }

    private JPanel createGitHubPanel() {
        JPanel pnlGitHub = new JPanel();
        this.gitHubRepoLink = new HyperLink();
        pnlGitHub.add(gitHubRepoLink);
        JPanel pnlMaintainedBy = new JPanel();
        this.gitHubUserLink = new HyperLink();
        lblMaintainedBy = new JLabel();
        pnlMaintainedBy.add(lblMaintainedBy);
        pnlMaintainedBy.add(gitHubUserLink);
        pnlGitHub.add(pnlMaintainedBy);

        return pnlGitHub;
    }

    protected void setAppName(String appName, I18NController i18NController) {
        this.appName = appName;
        String aboutTitle = i18NController.getMessage("about.title");
        String frameTitle = Optional.ofNullable(this.appName)
                .map(title -> aboutTitle + " " + this.appName)
                .orElse(aboutTitle);
        setTitle(frameTitle);
    }

    protected void setAppVersion(String appVersion) {
        lblVersion.setText(appVersion);
    }

    protected void setGitHubRepoUri(URI gitHubRepoUri) {
        gitHubRepoLink.setUri(gitHubRepoUri);
    }

    protected void setGitHubUserTag(String gitHubUserTag) {
        gitHubUserLink.setText(gitHubUserTag);
    }

    protected void setGitHubUserUri(URI gitHubUserUri) {
        gitHubUserLink.setUri(gitHubUserUri);
    }

    @Override
    public void localeChanged(Locale locale, I18NController i18NController) {
        setAppName(this.appName, i18NController);
        lblVersion.setCaption(i18NController.getMessage("about.version"));
        gitHubRepoLink.setText(i18NController.getMessage("about.github.repository"));
        lblMaintainedBy.setText(i18NController.getMessage("about.maintained.by"));
    }
}
