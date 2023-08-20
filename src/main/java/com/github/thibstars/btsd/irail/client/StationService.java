package com.github.thibstars.btsd.irail.client;

import com.github.thibstars.btsd.irail.model.Station;
import java.io.IOException;
import java.util.Set;

/**
 * @author Thibault Helsmoortel
 */
public interface StationService {

    Set<Station> getStations() throws IOException;

}
