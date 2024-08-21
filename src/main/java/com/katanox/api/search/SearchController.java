package com.katanox.api.search;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.logging.Logger;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("search")
public class SearchController {
    private static final Logger LOGGER = Logger.getLogger(SearchController.class.getName() );

    @Value("${env}")
    String environment;

    private final SearchService searchService;

    public SearchController(SearchService searchService) {
        this.searchService = searchService;
    }

    @PostMapping(
            path = "/",
            consumes = APPLICATION_JSON_VALUE,
            produces = APPLICATION_JSON_VALUE)
    ResponseEntity<SearchResponse> search(
            @RequestBody SearchRequest request
    ) {
        var result = searchService.search(request);

        if (environment.equals("local")) {
            LOGGER.info(result.toString());
        }
        return new ResponseEntity<>(result, HttpStatus.ACCEPTED);
    }

}
