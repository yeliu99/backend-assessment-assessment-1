package com.katanox.impl.percentage_charges;

import com.katanox.test.sql.tables.ExtraChargesPercentage;
import org.jooq.Record;

public record PercentageCharge(PercentageChargeType percentageChargeType,
                               double percentage,
                               long hotelId) {

    public static PercentageCharge fromRecord(Record record) {
        return new PercentageCharge(
                PercentageChargeType.valueOf(record.get(ExtraChargesPercentage.EXTRA_CHARGES_PERCENTAGE.APPLIED_ON).getLiteral()),
                record.get(ExtraChargesPercentage.EXTRA_CHARGES_PERCENTAGE.PERCENTAGE),
                record.get(ExtraChargesPercentage.EXTRA_CHARGES_PERCENTAGE.HOTEL_ID));
    }
}
