package com.katanox.api.price;

import com.katanox.test.sql.tables.Prices;
import org.jooq.Record;

import java.time.LocalDate;
import java.util.Currency;

public record Price(LocalDate date,
                    int quantity,
                    long roomId,
                    double priceAfterTax,
                    Currency currency) {

    public static Price fromRecord(Record record) {
        return new Price(
                record.get(Prices.PRICES.DATE),
                record.get(Prices.PRICES.QUANTITY),
                record.get(Prices.PRICES.ROOM_ID),
                record.get(Prices.PRICES.PRICE_AFTER_TAX).doubleValue(),
                Currency.getInstance(record.get(Prices.PRICES.CURRENCY)));
    }
}
