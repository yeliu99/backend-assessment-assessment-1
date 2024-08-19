package com.katanox.api.search;

import java.util.List;

public record SearchResponse(List<SearchResult> searchResults) {
}
