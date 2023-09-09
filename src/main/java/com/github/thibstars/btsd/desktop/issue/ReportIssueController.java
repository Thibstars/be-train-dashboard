package com.github.thibstars.btsd.desktop.issue;

import com.github.thibstars.btsd.desktop.exceptions.DesktopException;
import com.github.thibstars.btsd.internal.PropertiesService;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Properties;

/**
 * @author Thibault Helsmoortel
 */
public class ReportIssueController {

    private final PropertiesService propertiesService;

    private final ReportIssueDialog reportIssueDialog;

    public ReportIssueController(PropertiesService propertiesService, ReportIssueDialog reportIssueDialog) {
        this.propertiesService = propertiesService;
        this.reportIssueDialog = reportIssueDialog;

        initStaticValues(reportIssueDialog);
    }

    public void showView() {
        reportIssueDialog.pack();
        reportIssueDialog.setVisible(true);
    }

    private void initStaticValues(ReportIssueDialog reportIssueDialog) {
        Properties applicationProperties = getApplicationProperties();
        if (!applicationProperties.isEmpty()) {
            try {
                reportIssueDialog.setGitHubIssueUri(new URI(applicationProperties.getProperty("github.issue.uri")));
            } catch (URISyntaxException e) {
                throw new DesktopException(e);
            }
        }
    }

    private Properties getApplicationProperties() {
        return propertiesService.getApplicationProperties()
                .orElseThrow(() -> new DesktopException("Could not retrieve application properties."));
    }
}
