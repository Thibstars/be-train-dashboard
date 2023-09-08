package com.github.thibstars.btsd.desktop.launch.tasks;

import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.OkHttpClient;

/**
 * @author Thibault Helsmoortel
 */
public record Prerequisites(
        OkHttpClient okHttpClient,

        ObjectMapper objectMapper
) {

}
