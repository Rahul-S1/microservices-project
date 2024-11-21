package com.hotel.service.payload;

import lombok.*;
import org.springframework.http.HttpStatus;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ApiResponse
{
    private String message;
    private Boolean sucesss;
    private HttpStatus httpStatus;

}
