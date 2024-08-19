package com.katanox.api.price;

import com.katanox.test.sql.tables.Prices;
import com.katanox.test.sql.tables.Rooms;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public class PriceRepository {

    @Autowired
    private DSLContext dsl;

    public List<Price> getAvailableRoomPriceAtHotelBetweenDates(LocalDate checkin, LocalDate checkout, long hotelId) {
        return dsl.select()
                .from(Prices.PRICES)
                .join(Rooms.ROOMS).on(Rooms.ROOMS.ID.eq(Prices.PRICES.ROOM_ID))
                .where(Rooms.ROOMS.HOTEL_ID.eq(hotelId))
                .and(Prices.PRICES.QUANTITY.greaterThan(0))
                .and(Prices.PRICES.DATE.between(checkin, checkout.minusDays(1)))
                .fetch()
                .map(Price::fromRecord);
    }
}
