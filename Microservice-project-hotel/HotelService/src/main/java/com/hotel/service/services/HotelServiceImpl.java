package com.hotel.service.services;

import com.hotel.service.entities.Hotel;
import com.hotel.service.exceptions.ResourceNotFoundException;
import com.hotel.service.repositories.HotelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class HotelServiceImpl implements HotelService
{

    private final HotelRepository hotelRepository;

    @Override
    public Hotel CreateHotel(Hotel hotel) {
        return hotelRepository.save(hotel);
    }

    @Override
    public List<Hotel> getAll() {
        return hotelRepository.findAll();
    }

    @Override
    public Hotel getHotel(String id)
    {

        return hotelRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("Hotel Not Found With this Id"+id));

    }
}
