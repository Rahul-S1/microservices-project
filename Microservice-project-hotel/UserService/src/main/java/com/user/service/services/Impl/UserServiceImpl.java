package com.user.service.services.Impl;

import com.user.service.config.HotelFeignConfig;
import com.user.service.entities.Hotel;
import com.user.service.entities.Rating;
import com.user.service.entities.User;
import com.user.service.exceptions.ResourceNotFoundException;
import com.user.service.repositories.UserRepository;
import com.user.service.services.UserService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService
{

   private Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    private final UserRepository userRepository;

    private final RestTemplate restTemplate;

    private final HotelFeignConfig hotelFeignConfig;

    @Override
    public User saveUser(User user) {
        return userRepository.save(user);

    }

    @CircuitBreaker(name = "ratinghotelbreaker", fallbackMethod = "fallback")
    public User getUser(String id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User With Given Id is Not Found with id " + id));

       // try {

            Rating[] ratingsOfUser = restTemplate.getForObject("http://RATING-SERVICE/api/ratings/user/" + user.getUserId(), Rating[].class);

            if (ratingsOfUser == null || ratingsOfUser.length == 0) {

                user.setRatings(null);
                return user;
            }

            // Convert the array to a list
            List<Rating> ratings = Arrays.asList(ratingsOfUser);

            // Fetch hotel details for each rating
            ratings = ratings.stream().map(rating -> {
              //  try {
//                    Hotel hotel = restTemplate.getForEntity("http://HOTEL-SERVICE/api/hotels/" + rating.getHotelId(), Hotel.class).getBody();
//                    rating.setHotel(hotel);

                    Hotel hotel = hotelFeignConfig.getHotel(rating.getHotelId());
                    rating.setHotel(hotel);

               // } catch (Exception e) {
               //     logger.error("Error fetching hotel details for rating {}: {}", rating.getUserId(), e.getMessage());
               // }
                return rating;
            }).collect(Collectors.toList());


            user.setRatings(ratings);

      //  } catch (Exception e) {
           // logger.error("Error fetching ratings for user {}: {}", user.getUserId(), e.getMessage());
            user.setRatings(null);
      //  }

        return user;
    }


//    public User fallback(String id, Exception ex)
//    {
//
//        logger.error("Fallback is executed because service is down : ",ex.getMessage());
//        User user = User.builder()
//                .email("abc@gmail.com")
//                .name("Dummy")
//                .about("This user is created dummy because some service is down")
//                .userId("1423444232")
//                .build();
//                 return user;
//
//    }


    @Override
    public List<User> getAllUsers() {
        List<User> users = userRepository.findAll();

        return users.stream().map(user -> {
            List<Rating> ratings = null;

            try {
                // Fetch ratings for the user
                Rating[] ratingsOfUser = restTemplate.getForObject("http://RATING-SERVICE/api/ratings/user/" + user.getUserId(), Rating[].class);

                // If no ratings found, assign an empty list
                if (ratingsOfUser == null || ratingsOfUser.length == 0) {
                    user.setRatings(new ArrayList<>());
                    return user;
                }

                // Convert the array to a list
                ratings = Arrays.asList(ratingsOfUser);

                // Fetch hotel details for each rating
                ratings = ratings.stream().map(rating -> {
                    try {
                        // Fetch hotel details for each rating
                        Hotel hotel = restTemplate.getForEntity("http://HOTEL-SERVICE/api/hotels/" + rating.getHotelId(), Hotel.class).getBody();
                        if (hotel != null) {
                            rating.setHotel(hotel); // Set the hotel details in the rating
                        }
                    } catch (Exception e) {
                        logger.error("Error fetching hotel details for rating {}: {}", rating.getUserId(), e.getMessage());
                    }
                    return rating;
                }).collect(Collectors.toList());

                // Set the ratings with hotel details to the user
                user.setRatings(ratings);

            } catch (Exception e) {
                logger.error("Error fetching ratings for user {}: {}", user.getUserId(), e.getMessage());
                user.setRatings(new ArrayList<>()); // Set an empty list if there’s an error
            }

            return user;
        }).collect(Collectors.toList());
    }

    @Override
    public void deleteUser(String id)
    {
        userRepository.deleteById(id);
    }

    @Override
    public User updateUserById(User user, String id)
    {
        User user1 = userRepository.findById(id).get();
       if(user1!=null)
       {
           user1.setUserId(user.getUserId());
           userRepository.save(user);
           return user1;
       }
        else
            throw new ResourceNotFoundException("User With Given Id is Not Found"+id);
    }
}
