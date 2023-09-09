package com.github.thibstars.btsd.desktop.about;

import com.github.thibstars.btsd.desktop.exceptions.DesktopException;
import com.github.thibstars.btsd.internal.PropertiesService;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Properties;

/**
 * @author Thibault Helsmoortel
 */
public class AboutController {

    private final PropertiesService propertiesService;

    private final AboutDialog aboutDialog;

    public AboutController(PropertiesService propertiesService, AboutDialog aboutDialog) {
        this.propertiesService = propertiesService;
        this.aboutDialog = aboutDialog;

        initStaticValues(aboutDialog);
    }

    private void initStaticValues(AboutDialog aboutDialog) {
        Properties applicationProperties = getApplicationProperties();
        if (!applicationProperties.isEmpty()) {
            aboutDialog.setAppVersion(applicationProperties.getProperty("application.version"));
            try {
                URI gitHubRepoUri = new URI(applicationProperties.getProperty("github.repo.uri"));
                String gitHubUserTag = applicationProperties.getProperty("github.user.tag");
                URI gitHubUserUri = new URI(applicationProperties.getProperty("github.user.uri"));
                aboutDialog.setGitHubRepoUri(gitHubRepoUri);
                aboutDialog.setGitHubUserTag(gitHubUserTag);
                aboutDialog.setGitHubUserUri(gitHubUserUri);
            } catch (URISyntaxException e) {
                throw new DesktopException(e);
            }
        }
    }

    private Properties getApplicationProperties() {
        return propertiesService.getApplicationProperties()
                .orElseThrow(() -> new DesktopException("Could not retrieve application properties."));
    }

    public void setAppName(String appName) {
        aboutDialog.setAppName(appName);
    }

    public void showView() {
        aboutDialog.pack();
        aboutDialog.setVisible(true);
    }
}
