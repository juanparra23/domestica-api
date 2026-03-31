package com.domesticas.auth.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class LoginResponse {

    private Long id;
    private String nombre;
    private String email;
    private String token;
    private String mensaje;
}