package com.github.thibstars.btsd.desktop.launch;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.HeadlessException;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.SwingConstants;

/**
 * @author Thibault Helsmoortel
 */
public class LaunchFrame extends JFrame {

    private final JLabel lblFeedback;

    private final JProgressBar progressBar;

    public LaunchFrame(int taskCount) throws HeadlessException {
        this.lblFeedback = new JLabel("Getting started...");
        lblFeedback.setHorizontalAlignment(SwingConstants.HORIZONTAL);

        this.progressBar = new JProgressBar();
        this.progressBar.setMinimum(0);
        this.progressBar.setMaximum(taskCount);
        this.progressBar.setStringPainted(true);
        this.progressBar.setToolTipText("Loading...");

        setLocationRelativeTo(null);
        setUndecorated(true);
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BorderLayout());
        contentPanel.add(lblFeedback, BorderLayout.PAGE_START);
        contentPanel.add(progressBar, BorderLayout.PAGE_END);
        setContentPane(contentPanel);
        setPreferredSize(new Dimension(200, 40));
        pack();
    }

    public void updateProgress(int progress) {
        this.progressBar.setValue(progress);
    }

    public void updateProgressName(String name) {
        if (name != null) {
            lblFeedback.setText("Loading: " + name);
        } else {
            lblFeedback.setText("Finished!");
        }
    }
}
