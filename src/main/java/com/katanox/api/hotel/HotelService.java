package com.katanox.api.hotel;

import org.springframework.stereotype.Service;

@Service
public class HotelService {

    private final HotelRepository hotelRepository;

    public HotelService(HotelRepository hotelRepository) {
        this.hotelRepository = hotelRepository;
    }

    public double getVAT(long hotelId) {
        return hotelRepository.getVAT(hotelId);
    }
}
