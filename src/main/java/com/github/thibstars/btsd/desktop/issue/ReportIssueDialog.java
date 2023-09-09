package com.github.thibstars.btsd.desktop.issue;

import com.github.thibstars.btsd.desktop.components.HyperLink;
import java.awt.BorderLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URI;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

/**
 * @author Thibault Helsmoortel
 */
public class ReportIssueDialog extends JDialog {

    private final HyperLink gitHubIssueLink;

    public ReportIssueDialog() {
        setTitle("Report an issue");

        JPanel pnlContent = new JPanel(new BorderLayout());
        pnlContent.setBorder(new EmptyBorder(10, 10, 10, 10));
        pnlContent.add(new JLabel("Experiencing an issue? Let us know!"), BorderLayout.PAGE_START);
        this.gitHubIssueLink = new HyperLink("Report an issue");
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
    }

    protected void setGitHubIssueUri(URI gitHubIssueUri) {
        this.gitHubIssueLink.setUri(gitHubIssueUri);
    }
}
