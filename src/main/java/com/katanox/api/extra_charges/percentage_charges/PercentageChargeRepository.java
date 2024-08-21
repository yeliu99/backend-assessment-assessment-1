package com.katanox.api.extra_charges.percentage_charges;

import com.katanox.impl.percentage_charges.PercentageCharge;
import com.katanox.test.sql.tables.ExtraChargesPercentage;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PercentageChargeRepository {

    @Autowired
    private DSLContext dsl;

    public List<PercentageCharge> getExtraPercentageChargesWithHotelID(long hotelId) {
        return dsl.select()
                .from(ExtraChargesPercentage.EXTRA_CHARGES_PERCENTAGE)
                .where(ExtraChargesPercentage.EXTRA_CHARGES_PERCENTAGE.HOTEL_ID.eq(hotelId))
                .fetch()
                .map(PercentageCharge::fromRecord);
    }
}
