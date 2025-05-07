package com.smartjob.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Datos del teléfono del usuario")
public class PhoneResponse {
    @Schema(description = "Número de teléfono", example = "1234567")
    private String number;
    
    @Schema(description = "Código de ciudad", example = "1")
    private String cityCode;
    
    @Schema(description = "Código de país", example = "57")
    private String countryCode;
} 