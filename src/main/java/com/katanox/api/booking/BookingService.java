package com.katanox.api.booking;

import com.katanox.api.price.PriceService;
import org.springframework.stereotype.Service;

@Service
public class BookingService {

    private final PriceService priceService;

    public BookingService(PriceService priceService) {
        this.priceService = priceService;
    }

    public boolean book(BookingRequest bookingRequest) {
        // todo: validate booking request
        priceService.reduceRoomQuantity(bookingRequest.roomId(), bookingRequest.checkin(), bookingRequest.checkout());
        return true;
    }
}
