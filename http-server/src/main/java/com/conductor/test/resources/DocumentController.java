package com.conductor.test.resources;

import com.conductor.test.domain.Document;
import com.conductor.test.index.SearchEngine;
import com.conductor.test.utils.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Optional;
import java.util.Set;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(produces = "application/json")
@Validated
public class DocumentController {

    private final SearchEngine searchEngine;

    @Inject
    public DocumentController(final SearchEngine searchEngine) {
        this.searchEngine = searchEngine;
    }

    @RequestMapping(value = "/document", method = RequestMethod.POST, consumes = APPLICATION_JSON_VALUE)
    public ResponseData<Boolean> putDocument(@Valid @RequestBody @NotNull final UploadDocumentRequest uploadDocumentRequest) {
        final boolean result = searchEngine.put(uploadDocumentRequest.key, uploadDocumentRequest.document);
        return new ResponseData<>(result);
    }

    @RequestMapping(value = "/document", method = RequestMethod.GET)
    public ResponseData<String> getDocument(@RequestParam(name = "key") @NotNull String key) throws UnsupportedEncodingException {
        final String decodedKey = URLDecoder.decode(key, "UTF-8");
        final String result = Optional.ofNullable(searchEngine.get(decodedKey)).map(Document::getWords).orElse("");
        return new ResponseData<>(result);
    }

    @RequestMapping(value = "/document/search", method = RequestMethod.GET)
    public ResponseData<String> searchDocuments(@RequestParam(name = "tokens") @NotEmpty String tokens) throws UnsupportedEncodingException {
        final Set<String> keys = searchEngine.search(StringUtils.splitByWords(URLDecoder.decode(tokens, "UTF-8")));
        return new ResponseData<>(StringUtils.combine(keys, " "));
    }

    private static final class UploadDocumentRequest {
        @NotNull
        private String key;
        @NotNull
        private Document document;

        public UploadDocumentRequest() {
        }

        public String getKey() {
            return key;
        }

        public void setKey(final String key) {
            this.key = key;
        }

        public Document getDocument() {
            return document;
        }

        public void setDocument(final Document document) {
            this.document = document;
        }
    }

    private static final class ResponseData<T> {
        private final T response;

        public ResponseData(final T response) {
            this.response = response;
        }

        public T getResponse() {
            return response;
        }
    }
}
