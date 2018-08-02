package com.conductor.test;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SearchEngineConfiguration {

    @Value("${search.engine.url}")
    private String url;

    public String getURL() {
        return url;
    }
}
