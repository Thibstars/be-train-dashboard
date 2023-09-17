package com.github.thibstars.btsd.irail.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.thibstars.btsd.irail.exceptions.ClientException;
import com.github.thibstars.btsd.irail.model.Station;
import com.github.thibstars.btsd.irail.model.Stations;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Thibault Helsmoortel
 */
public class StationServiceImpl implements StationService {

    private static final Logger LOGGER = LoggerFactory.getLogger(StationServiceImpl.class);

    private static final String LANG_PLACEHOLDER = "${lang}";

    private static final String URL = "https://api.irail.be/stations?format=json&lang=" + LANG_PLACEHOLDER;

    private static final List<String> SUPPORTED_LANGS = List.of("en", "nl", "fr", "de");

    private final OkHttpClient client;

    private final ObjectMapper objectMapper;

    private final LoadingCache<String, Map<String, Station>> cache;

    public StationServiceImpl(OkHttpClient client, ObjectMapper objectMapper) {
        this.client = client;
        this.objectMapper = objectMapper;
        CacheLoader<String, Map<String, Station>> loader = new CacheLoader<>() {
            @NotNull
            @Override
            public Map<String, Station> load(@NotNull String key) throws Exception {
                return cache.get(key);
            }

            @NotNull
            @Override
            public Map<String, Map<String, Station>> loadAll(@NotNull Iterable<? extends String> keys) {
                return getAllStations();
            }
        };

        cache = CacheBuilder.newBuilder()
                .build(loader);

        try {
            cache.putAll(fetchAllStations());
        } catch (IOException e) {
            throw new ClientException(e);
        }
    }

    @Override
    public Set<Station> getStations(String language) {
        String languageOrFallback = SUPPORTED_LANGS.stream()
                .filter(lang -> lang.equals(language))
                .findFirst()
                .orElse(SUPPORTED_LANGS.get(0));

        return new HashSet<>(cache.getUnchecked(languageOrFallback).values());
    }

    private Map<String, Map<String, Station>> getAllStations() {
        return cache.getAllPresent(SUPPORTED_LANGS);
    }

    private Map<String, Map<String, Station>> fetchAllStations() throws IOException {
        Map<String, Map<String, Station>> stationMap = new HashMap<>();

        for (String language : SUPPORTED_LANGS) {
            stationMap.put(language, fetchStations(language));
        }

        return stationMap;
    }

    private Map<String, Station> fetchStations(String language) throws IOException {
        LOGGER.info("Fetching stations for language: {}", language);

        Request request = new Request.Builder()
                .url(URL.replace(LANG_PLACEHOLDER, language))
                .build();

        ResponseBody responseBody;
        Stations stations;
        try (Response response = client.newCall(request).execute()) {
            responseBody = Objects.requireNonNull(response.body());
            stations = objectMapper.readValue(responseBody.string(), Stations.class);
        }

        return stations != null ?
                stations.stations().stream()
                        .collect(Collectors.toMap(Station::id, Function.identity())) :
                Collections.emptyMap();
    }
}
