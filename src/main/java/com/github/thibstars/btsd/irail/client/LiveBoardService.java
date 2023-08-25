package com.github.thibstars.btsd.irail.client;

import com.github.thibstars.btsd.irail.model.LiveBoard;
import java.util.Optional;

/**
 * @author Thibault Helsmoortel
 */
public interface LiveBoardService {

    Optional<LiveBoard> getForStation(String id);
}
