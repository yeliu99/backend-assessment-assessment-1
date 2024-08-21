package com.katanox.impl.hotel;

import com.katanox.test.sql.tables.Hotels;
import org.jooq.Record;

public record Hotel(long hotelId,
                    double vat) {

    public static Hotel fromRecord(Record record) {
        return new Hotel(record.get(Hotels.HOTELS.ID),
                record.get(Hotels.HOTELS.VAT).doubleValue());
    }
}
