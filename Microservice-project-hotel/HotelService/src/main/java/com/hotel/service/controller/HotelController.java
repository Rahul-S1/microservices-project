package com.hotel.service.controller;

import com.hotel.service.entities.Hotel;
import com.hotel.service.services.HotelService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/hotels")
@RequiredArgsConstructor
public class HotelController {

    private final HotelService hotelService;


    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Hotel> createHotel(@RequestBody Hotel hotel)
    {
        hotel.setId(UUID.randomUUID().toString());
        return ResponseEntity.ok(hotelService.CreateHotel(hotel));
    }

    @GetMapping("/all")
    public ResponseEntity<List<Hotel>> getAllHotels() {
        List<Hotel> hotels = hotelService.getAll();
        return new ResponseEntity<>(hotels, HttpStatus.OK);
    }


    @GetMapping("/{id}")
    public ResponseEntity<Hotel> getHotelById(@PathVariable String id) {
        Hotel hotel = hotelService.getHotel(id);
        return new ResponseEntity<>(hotel, HttpStatus.OK);
    }
}
