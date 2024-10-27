package com.scaler.products.authenticate;

import com.scaler.products.dto.UserDto;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class AuthenticateProduct {

    private RestTemplate restTemplate;

    public AuthenticateProduct(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public UserDto authenticateProduct(String token) {
        UserDto dto=restTemplate.getForObject("http://localhost:8082/users/validate/" + token, UserDto.class);
        return dto;
    }
}
