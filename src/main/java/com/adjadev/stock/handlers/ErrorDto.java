package com.adjadev.stock.handlers;

import com.adjadev.stock.exception.ErrorCodes;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

//Gestionnaire globale des exceptions
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ErrorDto {
    private Integer httpCode;
    private ErrorCodes errorCode;
    private String message;
    List<String> errors = new ArrayList<>();
}
