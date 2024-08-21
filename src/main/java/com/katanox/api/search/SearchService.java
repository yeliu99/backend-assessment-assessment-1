package com.katanox.api.search;

import com.katanox.api.extra_charges.ExtraChargeService;
import com.katanox.api.hotel.HotelService;
import com.katanox.api.price.PriceService;
import com.katanox.impl.price.Price;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Currency;
import java.util.List;
import java.util.Map;

@Service
public class SearchService {
    private final PriceService priceService;
    private final ExtraChargeService extraChargeService;
    private final HotelService hotelService;

    public SearchService(PriceService priceService, ExtraChargeService extraChargeService, HotelService hotelService) {
        this.priceService = priceService;
        this.extraChargeService = extraChargeService;
        this.hotelService = hotelService;
    }

    public SearchResponse search(SearchRequest searchRequest) {
        if (!validateRequest(searchRequest)) {
            throw new IllegalArgumentException("Illegal checkin or checkout date selected: "
                    + searchRequest.checkin().toString() + "-" + searchRequest.checkout().toString());
        }

        int days = (int) searchRequest.checkin().until(searchRequest.checkout(), ChronoUnit.DAYS);
        long hotelId = searchRequest.hotelId();

        Map<Long, List<Price>> roomPrices =
                priceService.getAvailableRoomPrices(searchRequest.checkin(), searchRequest.checkout(), days, hotelId);
        List<SearchResult> searchResults = roomPrices.entrySet().stream()
                .map(entry -> calculateTotalPrice(entry, hotelId, days))
                .toList();

        return new SearchResponse(searchResults);
    }

    private SearchResult calculateTotalPrice(Map.Entry<Long, List<Price>> roomPrices, long hotelId, int days) {
        double roomPrice = roomPrices.getValue().stream().mapToDouble(Price::priceAfterTax).sum();
        double vat = hotelService.getVAT(hotelId);
        double firstNightPriceBeforeTax =
                priceService.calculatePriceWithoutVAT(roomPrices.getValue().get(0).priceAfterTax(), vat);
        double roomPriceBeforeTax = priceService.calculatePriceWithoutVAT(roomPrice, vat);
        double extraCharges =
                extraChargeService.calculateExtraCharges(hotelId, days, firstNightPriceBeforeTax, roomPriceBeforeTax);
        double totalPrice = roomPrice + extraCharges;
        Currency currency = roomPrices.getValue().iterator().next().currency();
        return new SearchResult(hotelId, roomPrices.getKey(), roomPriceBeforeTax, totalPrice, currency);
    }

    private boolean validateRequest(SearchRequest searchRequest) {
        return searchRequest.checkout().isAfter(searchRequest.checkin())
                && searchRequest.checkin().isAfter(LocalDate.now().minusDays(1));
    }
}
