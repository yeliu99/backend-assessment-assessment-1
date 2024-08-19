package com.katanox.api.flat_charges;

import com.katanox.test.sql.tables.ExtraChargesFlat;
import org.jooq.Record;

import java.util.Currency;

public record FlatCharge(FlatChargeType flatChargeType,
                         double price,
                         Currency currency,
                         long hotelId) {

    public static FlatCharge fromRecord(Record record) {
        return new FlatCharge(
                FlatChargeType.valueOf(record.get(ExtraChargesFlat.EXTRA_CHARGES_FLAT.CHARGE_TYPE).getLiteral()),
                record.get(ExtraChargesFlat.EXTRA_CHARGES_FLAT.PRICE),
                Currency.getInstance(record.get(ExtraChargesFlat.EXTRA_CHARGES_FLAT.CURRENCY)),
                record.get(ExtraChargesFlat.EXTRA_CHARGES_FLAT.HOTEL_ID));
    }
}
