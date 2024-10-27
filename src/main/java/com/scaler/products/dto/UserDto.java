package com.scaler.products.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class UserDto {

    private String name;
    private String email;
    private List<RoleDetails> roles;
}
