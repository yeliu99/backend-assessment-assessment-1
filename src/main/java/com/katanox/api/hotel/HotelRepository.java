package com.katanox.api.hotel;

import com.katanox.impl.hotel.Hotel;
import com.katanox.test.sql.tables.Hotels;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class HotelRepository {

    @Autowired
    private DSLContext dsl;

    public double getVAT(long hotelId) {
        List<Hotel> results = dsl.select()
                .from(Hotels.HOTELS)
                .where(Hotels.HOTELS.ID.eq(hotelId))
                .fetch()
                .map(Hotel::fromRecord);

        return results.isEmpty() ? 0d : results.get(0).vat();
    }
}
