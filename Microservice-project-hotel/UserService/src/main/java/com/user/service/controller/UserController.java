package com.user.service.controller;
import com.user.service.entities.User;
import com.user.service.services.Impl.UserServiceImpl;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/user")
@Slf4j
public class UserController
{

    private  final RestTemplate restTemplate;
    private final Logger logger = LoggerFactory.getLogger(UserController.class);

    private final UserServiceImpl userService;
    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<User> createUser(@RequestBody User user)
    {
        user.setUserId(UUID.randomUUID().toString());


        return ResponseEntity.ok(userService.saveUser(user));
    }

    @GetMapping("/{id}")
    //@CircuitBreaker(name = "ratinghotelbreaker", fallbackMethod = "fallback")
    @RateLimiter(name= "ratinghotelratelimiter", fallbackMethod = "ratelimiterfallback")
    public ResponseEntity<User> getUserById(@PathVariable String id) {
        User user = userService.getUser(id);
        //Rating[] ratingsOfUser = restTemplate.getForObject("http://RATING-SERVICE/api/ratings/user/" + user.getUserId(), Rating[].class);

        return ResponseEntity.ok(user);
    }


//    public ResponseEntity<User> fallback(String id, Exception ex)
//    {
//        logger.error("Fallback is executed because service is down : ",ex.getMessage());
//
//        User user = User.builder()
//                .email("abc@gmail.com")
//                .name("Dummy")
//                .about("This user is created dummy because some service is down")
//                .userId("1423444232")
//                .build();
//        return new ResponseEntity<>(user,HttpStatus.OK);
//
//
//    }

    public ResponseEntity<String> ratelimiterfallback(Exception ex)
    {
        return ResponseEntity.status(429)
                .body("Rate limit exceeded. Please try again later after 5 secound.");

    }




    @GetMapping("/all")
    public List<User> getAllUsers()
    {
        logger.info("getting all users");
        return userService.getAllUsers();

    }


    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUserById(@PathVariable String id) {
        userService.deleteUser(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUserById(@RequestBody User user, @PathVariable String id) {
        User updatedUser = userService.updateUserById(user, id);
        return ResponseEntity.ok(updatedUser);
    }
}
