package com.katanox.api.search;

import java.util.Currency;

public record SearchResult(long hotelId,
                           long roomId,
                           double priceBeforeTax,
                           double price,
                           Currency currency) {
}
