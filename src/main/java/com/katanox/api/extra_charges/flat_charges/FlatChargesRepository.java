package com.katanox.api.extra_charges.flat_charges;

import com.katanox.impl.flat_charges.FlatCharge;
import com.katanox.test.sql.tables.ExtraChargesFlat;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class FlatChargesRepository {

    @Autowired
    private DSLContext dsl;

    public List<FlatCharge> getExtraFlatChargesWithHotelID(long hotelId) {
        return dsl.select()
                .from(ExtraChargesFlat.EXTRA_CHARGES_FLAT)
                .where(ExtraChargesFlat.EXTRA_CHARGES_FLAT.HOTEL_ID.eq(hotelId))
                .fetch()
                .map(FlatCharge::fromRecord);
    }
}
