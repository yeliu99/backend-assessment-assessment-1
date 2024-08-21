package com.katanox.api.extra_charges;

import com.katanox.api.extra_charges.flat_charges.FlatChargeService;
import com.katanox.api.extra_charges.percentage_charges.PercentageChargeService;
import org.springframework.stereotype.Service;

@Service
public class ExtraChargeService {
    private final FlatChargeService flatChargeService;
    private final PercentageChargeService percentageChargeService;

    public ExtraChargeService(FlatChargeService flatChargeService, PercentageChargeService percentageChargeService) {
        this.flatChargeService = flatChargeService;
        this.percentageChargeService = percentageChargeService;
    }

    public double calculateExtraCharges(long hotelId, int days, double firstNightPrice, double totalPrice) {
        double flatCharges = flatChargeService.calculateFlatCharges(hotelId, days);
        double percentageCharges =
                percentageChargeService.calculatePercentageCharges(hotelId, firstNightPrice, totalPrice);
        return flatCharges + percentageCharges;
    }
}
