package com.conductor.test;

import com.conductor.test.utils.HttpClient;
import com.conductor.test.utils.HttpClient.HttpResponse;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import javax.inject.Inject;
import javax.validation.constraints.NotBlank;
import java.util.*;

@ShellComponent
public class ShellCommands {

    private static final String DOCUMENT_URI    = "/document";
    private static final String SEARCH_URI      = "/document/search";

    private final HttpClient httpClient;
    private final SearchEngineConfiguration engineConfig;

    @Inject
    public ShellCommands(final HttpClient httpClient,  final SearchEngineConfiguration engineConfig) {
        this.httpClient = httpClient;
        this.engineConfig = engineConfig;
    }

    @ShellMethod("Upload Document.")
    public String upload(final @ShellOption(value = {"-K", "--key"}, help = "Document Key.") @NotBlank String key,
                         final @ShellOption(value = {"-D", "--document"}, help = "Document (list of words separated by whitespaces).") @NotBlank String words,
                         final @ShellOption(value = {"-U", "--url"}, help = "url=http://127.0.0.1:8080", defaultValue = ShellOption.NULL) String url) {
        final Map<String, String> keyDocumentMap = new HashMap<>();
        keyDocumentMap.put("key", key);
        keyDocumentMap.put("document", words);

        return getResultMessage(httpClient.post(getUri(url, DOCUMENT_URI), keyDocumentMap));
    }

    @ShellMethod("Get Document.")
    public String get(final @ShellOption(value = {"-K", "--key"}, help = "Document Key.") @NotBlank String key,
                      final @ShellOption(value = {"-U", "--url"}, help = "url=http://127.0.0.1:8080", defaultValue = ShellOption.NULL) String url) {
        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
        queryParams.put("key", Arrays.asList(new String[] {key}));
        return getResultMessage(httpClient.get(getUri(url, DOCUMENT_URI), queryParams));
    }

    @ShellMethod("Search Documents.")
    public String search(final @ShellOption(value = {"-T", "--tokens"}, help = "Tokens (list of words separated by whitespaces).") @NotBlank String words,
                         final @ShellOption(value = {"-U", "--url"}, help = "url=http://127.0.0.1:8080", defaultValue = ShellOption.NULL) String url) {
        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
        queryParams.put("tokens", Arrays.asList(new String[] {words}));
        return getResultMessage(httpClient.get(getUri(url, SEARCH_URI), queryParams));
    }

    private String getUri(final String host, final String uri) {
        final String baseUrl = Optional.ofNullable(host).orElse(engineConfig.getURL());
        return baseUrl + uri;
    }

    private static String getResultMessage(final HttpResponse httpResponse) {
        if (httpResponse.getSuccess()) {
            return "Success. " + httpResponse.getBody();
        }
        return "Failed. " + httpResponse.getBody();
    }
}
