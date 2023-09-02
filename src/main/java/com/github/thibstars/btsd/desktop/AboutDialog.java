package com.github.thibstars.btsd.desktop;

import com.github.thibstars.btsd.desktop.exceptions.DesktopException;
import com.github.thibstars.btsd.internal.PropertiesService;
import com.github.thibstars.btsd.internal.PropertiesServiceImpl;
import java.awt.BorderLayout;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Properties;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * @author Thibault Helsmoortel
 */
public class AboutDialog extends JDialog {

    public AboutDialog(String appName) {
        setTitle("About " + appName);

        JPanel pnlContent = new JPanel(new BorderLayout());

        PropertiesService propertiesService = new PropertiesServiceImpl();
        Properties applicationProperties = propertiesService.getApplicationProperties().orElseThrow();

        String applicationVersion = applicationProperties.getProperty("application.version");
        pnlContent.add(new CaptionedLabel("Version", applicationVersion), BorderLayout.PAGE_START);

        JPanel pnlGitHub = createGitHubPanel(applicationProperties);

        pnlContent.add(pnlGitHub, BorderLayout.PAGE_END);

        setContentPane(pnlContent);

        pack();
        setLocationRelativeTo(null);
    }

    private static JPanel createGitHubPanel(Properties applicationProperties) {
        JPanel pnlGitHub = new JPanel();
        try {
            URI repoUri = new URI(applicationProperties.getProperty("github.repo.uri"));
            HyperLink gitHubRepoLink = new HyperLink("GitHub Repository", repoUri);
            pnlGitHub.add(gitHubRepoLink);
        } catch (URISyntaxException e) {
            throw new DesktopException(e);
        }
        try {
            JPanel pnlMaintainedBy = new JPanel();
            URI userUri = new URI(applicationProperties.getProperty("github.user.uri"));
            HyperLink gitHubUserLink = new HyperLink(applicationProperties.getProperty("github.user.tag"), userUri);
            pnlMaintainedBy.add(new JLabel("Maintained by: "));
            pnlMaintainedBy.add(gitHubUserLink);
            pnlGitHub.add(pnlMaintainedBy);
        } catch (URISyntaxException e) {
            throw new DesktopException(e);
        }

        return pnlGitHub;
    }
}
