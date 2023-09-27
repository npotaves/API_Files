package com.challenge.challenge_backend.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CustomErrorResponse {

    private LocalDateTime timestamp;
    private int status;
    private String message;
    private String path;
}
