package com.user.service.entities.model;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class JwtResponse
{
    private String username;
    private String jwtToken;
}