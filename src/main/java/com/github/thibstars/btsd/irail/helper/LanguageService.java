package com.github.thibstars.btsd.irail.helper;

import java.util.Set;

/**
 * @author Thibault Helsmoortel
 */
public interface LanguageService {

    Set<String> getSupportedLanguages();

    String getLanguageOrFallback(String language);

}
