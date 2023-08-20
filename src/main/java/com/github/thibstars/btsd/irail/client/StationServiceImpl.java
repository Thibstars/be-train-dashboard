package com.github.thibstars.btsd.irail.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.thibstars.btsd.irail.model.Station;
import com.github.thibstars.btsd.irail.model.Stations;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.jetbrains.annotations.NotNull;

/**
 * @author Thibault Helsmoortel
 */
public class StationServiceImpl implements StationService {

    private static final OkHttpClient CLIENT = new OkHttpClient();

    private static final String URL = "https://api.irail.be/stations?format=json&lang=en";

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    private final LoadingCache<String, Station> cache;

    public StationServiceImpl() {
        CacheLoader<String, Station> loader = new CacheLoader<>() {
            @NotNull
            @Override
            public Station load(@NotNull String key) throws Exception {
                return cache.get(key);
            }

            @NotNull
            @Override
            public Map<String, Station> loadAll(@NotNull Iterable<? extends String> keys) {
                return getStations().stream()
                        .collect(Collectors.toMap(Station::id, Function.identity()));
            }
        };

        cache = CacheBuilder.newBuilder()
                .build(loader);

        try {
            cache.putAll(
                    fetchStations().stream()
                            .collect(Collectors.toMap(Station::id, Function.identity()))
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Set<Station> getStations() {
        return new HashSet<>(cache.asMap().values());
    }

    private static Set<Station> fetchStations() throws IOException {
        Request request = new Request.Builder()
                .url(URL)
                .build();

        ResponseBody responseBody;
        Stations stations;
        try (Response response = CLIENT.newCall(request).execute()) {
            responseBody = Objects.requireNonNull(response.body());
            stations = OBJECT_MAPPER.readValue(responseBody.string(), Stations.class);
        }


        return stations != null ? stations.stations() : Collections.emptySet();
    }
}
