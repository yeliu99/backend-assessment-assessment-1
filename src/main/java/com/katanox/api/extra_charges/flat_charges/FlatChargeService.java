package com.katanox.api.extra_charges.flat_charges;

import com.katanox.impl.flat_charges.FlatCharge;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FlatChargeService {
    private final FlatChargesRepository flatChargesRepository;

    public FlatChargeService(FlatChargesRepository flatChargesRepository) {
        this.flatChargesRepository = flatChargesRepository;
    }

    public double calculateFlatCharges(long hotelId, int days) {
        List<FlatCharge> flatCharges = flatChargesRepository.getExtraFlatChargesWithHotelID(hotelId);
        double result = 0d;
        for (FlatCharge flatCharge : flatCharges) {
            switch (flatCharge.flatChargeType()) {
                case ONCE -> result += flatCharge.price();
                case PER_NIGHT -> result += flatCharge.price() * days;
            }
        }
        return result;
    }
}
