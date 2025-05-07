package com.smartjob.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@Schema(description = "Datos del teléfono del usuario")
public class PhoneRequest {
    
    @NotBlank(message = "El número de teléfono es obligatorio")
    @Schema(description = "Número de teléfono", example = "1234567")
    private String number;
    
    @NotBlank(message = "El código de ciudad es obligatorio")
    @Schema(description = "Código de ciudad", example = "1")
    private String cityCode;
    
    @NotBlank(message = "El código de país es obligatorio")
    @Schema(description = "Código de país", example = "57")
    private String countryCode;
} 