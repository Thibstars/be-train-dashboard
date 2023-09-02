package com.github.thibstars.btsd.irail.client;

import com.github.thibstars.btsd.irail.model.LiveBoard;
import java.util.Optional;

/**
 * @author Thibault Helsmoortel
 */
public interface LiveBoardService {

    /**
     * Retrieves a Live Board for a given Station.
     *
     * @param id the Station's id
     * @return a Live Board for a given Station
     */
    Optional<LiveBoard> getForStation(String id);
}
