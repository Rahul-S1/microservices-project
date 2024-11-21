package com.rating.service.services;

import com.rating.service.entities.Rating;
import com.rating.service.exceptions.ResourceNotFoundException;
import com.rating.service.repository.RatingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class RratingServiceImpl implements RatingService
{
    private final RatingRepository RatingRepository;


    @Override
    public Rating create(Rating rating)
    {


        return RatingRepository.save(rating);
    }

    @Override
    public List<Rating> getAllRatings() {
        return RatingRepository.findAll();
    }

    @Override
    public List<Rating> getRatingsByUserId(String userId)
    {
        List<Rating> ratings = RatingRepository.findByUserId(userId);
        if (ratings.isEmpty())
        {
            throw new ResourceNotFoundException("No ratings found for user with ID: " + userId);
        }
        return ratings;
    }

    @Override
    public List<Rating> getRatingByHotelId(String hotelId)
    {
        List<Rating> ratings = RatingRepository.findByHotelId(hotelId);
        if (ratings.isEmpty())
        {
            throw new ResourceNotFoundException("No ratings found for user with ID: " + hotelId);
        }
        return ratings;
    }
}
