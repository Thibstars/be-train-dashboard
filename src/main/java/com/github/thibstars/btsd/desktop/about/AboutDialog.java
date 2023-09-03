package com.github.thibstars.btsd.desktop.about;

import com.github.thibstars.btsd.desktop.components.CaptionedLabel;
import com.github.thibstars.btsd.desktop.components.HyperLink;
import java.awt.BorderLayout;
import java.net.URI;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * @author Thibault Helsmoortel
 */
public class AboutDialog extends JDialog {

    private HyperLink gitHubRepoLink;

    private HyperLink gitHubUserLink;

    private final CaptionedLabel lblVersion;

    public AboutDialog() {
        setTitle("About");

        JPanel pnlContent = new JPanel(new BorderLayout());

        lblVersion = new CaptionedLabel("Version");
        pnlContent.add(lblVersion, BorderLayout.PAGE_START);

        JPanel pnlGitHub = createGitHubPanel();

        pnlContent.add(pnlGitHub, BorderLayout.PAGE_END);

        setContentPane(pnlContent);

        pack();
        setLocationRelativeTo(null);
    }

    private JPanel createGitHubPanel() {
        JPanel pnlGitHub = new JPanel();
        this.gitHubRepoLink = new HyperLink("GitHub Repository");
        pnlGitHub.add(gitHubRepoLink);
        JPanel pnlMaintainedBy = new JPanel();
        this.gitHubUserLink = new HyperLink();
        pnlMaintainedBy.add(new JLabel("Maintained by: "));
        pnlMaintainedBy.add(gitHubUserLink);
        pnlGitHub.add(pnlMaintainedBy);

        return pnlGitHub;
    }

    protected void setAppName(String appName) {
        setTitle("About " + appName);
    }

    protected void setAppVersion(String appVersion) {
        lblVersion.setText(appVersion);
    }

    protected void setGitHubRepoUri(URI gitHubRepoUri) {
        gitHubRepoLink.setUri(gitHubRepoUri);
    }

    protected void setGithubUserTag(String githubUserTag) {
        gitHubUserLink.setText(githubUserTag);
    }

    protected void setGitHubUserUri(URI gitHubUserUri) {
        gitHubUserLink.setUri(gitHubUserUri);
    }
}
