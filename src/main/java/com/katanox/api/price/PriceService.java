package com.katanox.api.price;

import com.katanox.impl.price.Price;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class PriceService {
    private final PriceRepository priceRepository;

    public PriceService(PriceRepository priceRepository) {
        this.priceRepository = priceRepository;
    }

    public Map<Long, List<Price>> getAvailableRoomPrices(LocalDate checkin, LocalDate checkout, int days, long hotelId) {
        List<Price> prices = priceRepository.getAvailableRoomPriceAtHotelBetweenDates(checkin, checkout, hotelId);

        // filter rooms always available between selected dates
        return prices.stream()
                .collect(Collectors.collectingAndThen(Collectors.groupingBy(Price::roomId),
                        entry -> entry.entrySet().stream()
                                .filter(e -> e.getValue().size() == days)
                                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue))));
    }

    public double calculatePriceWithoutVAT(double price, double vat) {
        return price / (1 + vat / 100);
    }}
