package com.github.thibstars.btsd.irail.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.thibstars.btsd.irail.exceptions.ClientException;
import com.github.thibstars.btsd.irail.model.LiveBoard;
import java.io.IOException;
import java.util.Objects;
import java.util.Optional;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Thibault Helsmoortel
 */
public class LiveBoardServiceImpl implements LiveBoardService {

    private static final Logger LOGGER = LoggerFactory.getLogger(LiveBoardServiceImpl.class);

    private static final String URL = "https://api.irail.be/liveboard/?id=${id}&arrdep=departure&lang=en&format=json&alerts=false";

    private final OkHttpClient client;

    private final ObjectMapper objectMapper;

    public LiveBoardServiceImpl(OkHttpClient client, ObjectMapper objectMapper) {
        this.client = client;
        this.objectMapper = objectMapper;
    }

    @Override
    public Optional<LiveBoard> getForStation(String id) {
        try {
            return Optional.of(fetchLiveBoard(id));
        } catch (IOException e) {
            throw new ClientException(e);
        }
    }

    private LiveBoard fetchLiveBoard(String id) throws IOException {
        LOGGER.info("Fetching live board for station: {}", id);

        Request request = new Request.Builder()
                .url(URL.replace("${id}", id))
                .build();

        ResponseBody responseBody;
        LiveBoard liveBoard;
        try (Response response = client.newCall(request).execute()) {
            responseBody = Objects.requireNonNull(response.body());
            liveBoard = objectMapper.readValue(responseBody.string(), LiveBoard.class);
        }

        return liveBoard;
    }
}
