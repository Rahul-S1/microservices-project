package com.hotel.service.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/staffs")
public class HotelStaffs
{

    List<String> staffs = Arrays.asList("Rohit","shubham","yash","ankit","sumit");

    @GetMapping
    public List<String> getSfaffs()
    {
        return staffs;
    }

}
