package com.katanox.api.extra_charges.percentage_charges;

import com.katanox.impl.percentage_charges.PercentageCharge;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PercentageChargeService {

    private final PercentageChargeRepository percentageChargeRepository;

    public PercentageChargeService(PercentageChargeRepository repository) {
        this.percentageChargeRepository = repository;
    }

    public double calculatePercentageCharges(long hotelId, double firstNightPrice, double totalPrice) {
        List<PercentageCharge> percentageCharges =
                percentageChargeRepository.getExtraPercentageChargesWithHotelID(hotelId);
        double result = 0d;
        for (PercentageCharge percentageCharge : percentageCharges) {
            switch (percentageCharge.percentageChargeType()) {
                case FIRST_NIGHT -> result += (percentageCharge.percentage() / 100.0) * firstNightPrice;
                case TOTAL_AMOUNT -> result += (percentageCharge.percentage() / 100.0) * totalPrice ;
            }
        }
        return result;
    }
}
