package com.github.thibstars.btsd.desktop.liveboard;

import com.github.lgooddatepicker.components.TimePicker;
import com.github.thibstars.btsd.desktop.components.CaptionedLabel;
import com.github.thibstars.btsd.desktop.components.RefreshPanel;
import com.github.thibstars.btsd.desktop.i18n.I18NController;
import com.github.thibstars.btsd.desktop.listeners.LocaleChangeListener;
import com.github.thibstars.btsd.desktop.stations.StationPanel;
import com.github.thibstars.jirail.model.Departure;
import com.github.thibstars.jirail.model.Departures;
import com.github.thibstars.jirail.model.LiveBoard;
import com.github.thibstars.jirail.model.Occupancy;
import com.github.thibstars.jirail.model.Station;
import com.github.thibstars.jirail.model.Vehicle;
import com.github.thibstars.jirail.model.Platform;
import java.awt.BorderLayout;
import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Date;
import java.util.Locale;
import java.util.stream.IntStream;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ScrollPaneConstants;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

/**
 * @author Thibault Helsmoortel
 */
public class LiveBoardPanel extends JPanel implements LocaleChangeListener {

    private static final int MILLISECONDS_IN_SECOND = 1000;

    private static final int SECONDS_IN_MINUTE = 60;

    private final transient LiveBoardController liveBoardController;

    private final StationPanel pnlStation;

    private final RefreshPanel pnlRefresh;

    private final TimePicker timePicker;

    private final CaptionedLabel lblDepartureNumber;

    private final DefaultTableModel departuresModel;

    private final JTable tblDepartures;

    public LiveBoardPanel(LiveBoardController liveBoardController, LiveBoard liveBoard, LiveBoardFrame liveBoardFrame) {
        this.liveBoardController = liveBoardController;
        setBorder(new EmptyBorder(10, 10, 10, 10));
        setLayout(new BorderLayout());
        this.pnlStation = new StationPanel(liveBoard.stationInfo());

        add(pnlStation, BorderLayout.PAGE_START);

        this.pnlRefresh = new RefreshPanel(liveBoardFrame);
        pnlRefresh.setLastRefresh(liveBoardController.formatDateTime(LocalDateTime.now()));

        SpinnerNumberModel hourMinuteModel = new SpinnerNumberModel();
        hourMinuteModel.setMinimum(0);
        hourMinuteModel.setMaximum(2359);
        LocalDateTime now = LocalDateTime.now();
        hourMinuteModel.setValue(Integer.valueOf(now.getHour() + "" + now.getMinute()));

        this.timePicker = new TimePicker();

        timePicker.setTime(LocalTime.now());
        timePicker.addTimeChangeListener(event -> liveBoardController.refreshLiveBoard(this, liveBoard.stationInfo().id(), event.getNewTime()));
        timePicker.setToolTipText(liveBoardController.getMessage("live.board.time.picker.tooltip"));
        pnlRefresh.addRefreshListener(actionEvent -> {
            LocalTime rightNow = LocalTime.now();
            timePicker.setTime(rightNow);
            liveBoardController.refreshLiveBoard(this, liveBoard.stationInfo().id(), rightNow);
        });

        JPanel pnlContent = new JPanel(new BorderLayout());
        Departures departures = liveBoard.departures();
        lblDepartureNumber = new CaptionedLabel();
        setDepartureNumber(departures);
        JPanel pnlTop = new JPanel();
        pnlTop.add(lblDepartureNumber);
        pnlTop.add(pnlRefresh);
        pnlTop.add(timePicker);
        pnlContent.add(pnlTop, BorderLayout.LINE_START);

        this.tblDepartures = new JTable();
        departuresModel = new DefaultTableModel();
        Arrays.stream(Departure.class.getDeclaredFields()).forEach(field -> departuresModel.addColumn(field.getName()));
        initDepartures(departures);
        tblDepartures.setModel(departuresModel);

        pnlContent.add(new JScrollPane(tblDepartures, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED), BorderLayout.PAGE_END);

        add(pnlContent, BorderLayout.PAGE_END);

        setVisible(true);
    }

    private void initDepartures(Departures departures) {
        departures.departures().forEach(departure -> {
            Station station = departure.stationInfo();
            Date time = new Date(Long.parseLong(departure.time()) * MILLISECONDS_IN_SECOND);
            int delay = departure.delay() / SECONDS_IN_MINUTE;
            Vehicle vehicle = departure.vehicleInfo();
            Platform platform = departure.platformInfo();
            Occupancy occupancy = departure.occupancy();
            departuresModel.addRow(new Object[] {
                    departure.id(),
                    delay,
                    departure.station(),
                    station != null ? station.id() : "",
                    liveBoardController.formatDateTime(LocalDateTime.ofInstant(time.toInstant(), ZoneId.systemDefault())),
                    departure.vehicle(),
                    vehicle != null ? vehicle.name() : "",
                    departure.platform(),
                    platform != null ? platform.name() : "",
                    departure.canceled(),
                    departure.left(),
                    departure.departureConnection(),
                    occupancy != null ? occupancy.name() : ""
            });
        });
    }

    private void setDepartureNumber(Departures departures) {
        lblDepartureNumber.setText(String.valueOf(departures.number()));
    }

    private void setDepartures(Departures departures) {
        departuresModel.setRowCount(0);
        initDepartures(departures);
        departuresModel.fireTableDataChanged();
    }

    @Override
    public void localeChanged(Locale locale, I18NController i18NController) {
        pnlStation.localeChanged(locale, i18NController);
        pnlRefresh.localeChanged(locale, i18NController);
        timePicker.setToolTipText(i18NController.getMessage("live.board.time.picker.tooltip"));
        departuresModel.setColumnIdentifiers(
                Arrays.stream(Departure.class.getDeclaredFields())
                        .map(Field::getName)
                        .map(fieldName -> i18NController.getMessage("live.board.departures." + fieldName))
                        .toArray()
        );
        lblDepartureNumber.setCaption(i18NController.getMessage("live.board.departures"));
    }

    public void update(LiveBoard liveBoard) {
        int selectedRowCount = tblDepartures.getSelectedRowCount();
        int selectedRow = selectedRowCount > 0 ? tblDepartures.getSelectedRow() : -1;
        String vehicle = selectedRowCount > 0 ? (String) departuresModel.getValueAt(selectedRow, 6) : null;

        pnlStation.update(liveBoard.stationInfo());
        pnlRefresh.setLastRefresh(liveBoardController.formatDateTime(LocalDateTime.now()));
        Departures departures = liveBoard.departures();
        setDepartureNumber(departures);
        setDepartures(departures);

        if (selectedRowCount > 0 && vehicle != null) {
            selectRowOfVehicleOrClear(vehicle);
        }
    }

    private void selectRowOfVehicleOrClear(String vehicle) {
        IntStream.rangeClosed(0, departuresModel.getRowCount())
                .filter(rowIndex -> {
                    try {
                        String vehicleInRow = (String) departuresModel.getValueAt(rowIndex, 6);

                        return vehicleInRow != null && vehicleInRow.equals(vehicle);
                    } catch (ArrayIndexOutOfBoundsException e) {
                        // This might occur when a train has departed, which would mean there is a record less
                        return false;
                    }
                })
                .findFirst()
                .ifPresentOrElse(
                        rowIndex -> tblDepartures.changeSelection(rowIndex, 0, false, false),
                        tblDepartures::clearSelection
                );
    }
}
