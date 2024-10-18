package com.microservice.microserviceauth.model.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.Value;

import java.io.Serializable;


@Data
public class LoginDto implements Serializable {
    @NotNull
    @Size(max = 50)
    String email;
    @NotNull
    @Size(max = 50)
    String password;
}