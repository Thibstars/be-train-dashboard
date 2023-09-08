package com.github.thibstars.btsd.desktop.main;

import com.github.thibstars.btsd.desktop.components.PlaceholderTextField;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;

/**
 * @author Thibault Helsmoortel
 */
public class MainFrame extends JFrame {

    private static final Dimension PREFERRED_FRAME_SIZE = new Dimension(900, 600);

    private static final EmptyBorder INSETS = new EmptyBorder(10, 10, 10, 10);

    private static final int SUBFRAME_BOUND_DIFF = 25;

    private transient MainController mainController;

    protected void init(MainController mainController) {
        this.mainController = mainController;

        setTitle("Belgian Train Station Dashboard");
        mainController.setAppName(getTitle());

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        createJMenuBar();

        JPanel contentPanel = new JPanel();
        contentPanel.setPreferredSize(PREFERRED_FRAME_SIZE);
        contentPanel.setBorder(INSETS);
        setContentPane(contentPanel);
        contentPanel.setLayout(new BorderLayout());

        mainController.initStationsTable(new Dimension(PREFERRED_FRAME_SIZE.width - SUBFRAME_BOUND_DIFF, PREFERRED_FRAME_SIZE.height - SUBFRAME_BOUND_DIFF));

        PlaceholderTextField tfNameFilter = new PlaceholderTextField();
        tfNameFilter.setPlaceholder("Search...");

        tfNameFilter.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                super.keyReleased(e);
                mainController.filterStationsTableByName(tfNameFilter.getText());
            }
        });

        contentPanel.add(tfNameFilter, BorderLayout.PAGE_START);
        contentPanel.add(mainController.getStationsTableScrollPane(), BorderLayout.CENTER);

        pack();
        setLocationRelativeTo(null);
    }

    private void createJMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        JMenu mainMenu = new JMenu("General");
        JMenuItem about = new JMenuItem("About");
        about.addActionListener(event -> mainController.showAboutView());
        mainMenu.add(about);
        menuBar.add(mainMenu);
        setJMenuBar(menuBar);
    }
}
