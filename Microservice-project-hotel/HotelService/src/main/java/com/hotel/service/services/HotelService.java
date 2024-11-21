package com.hotel.service.services;

import com.hotel.service.entities.Hotel;

import java.util.List;

public interface HotelService
{
    public Hotel CreateHotel(Hotel hotel);

    public List<Hotel> getAll();

    public Hotel getHotel(String id);
}
