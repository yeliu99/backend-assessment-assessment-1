package com.katanox.api.booking;

import java.time.LocalDate;

public record BookingRequest(LocalDate checkin,
                             LocalDate checkout,
                             long hotelId,
                             long roomId,
                             double priceBeforeTax,
                             double priceAfterTax) {
}
