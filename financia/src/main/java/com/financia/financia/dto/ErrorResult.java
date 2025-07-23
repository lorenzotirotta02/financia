package com.financia.financia.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResult {
    String code;
    String message;
    String messageCode;
}

