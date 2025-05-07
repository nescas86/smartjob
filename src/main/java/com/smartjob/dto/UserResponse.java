package com.smartjob.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@Schema(description = "Respuesta con los datos del usuario registrado")
public class UserResponse {
    @Schema(description = "ID único del usuario")
    private UUID id;
    
    @Schema(description = "Nombre completo del usuario")
    private String name;
    
    @Schema(description = "Email del usuario")
    private String email;
    
    @Schema(description = "Lista de teléfonos del usuario")
    private List<PhoneResponse> phones;
    
    @Schema(description = "Fecha de creación del usuario")
    private LocalDateTime created;
    
    @Schema(description = "Fecha de última modificación del usuario")
    private LocalDateTime modified;
    
    @Schema(description = "Fecha del último login del usuario")
    private LocalDateTime lastLogin;
    
    @Schema(description = "Token JWT del usuario")
    private String token;
    
    @Schema(description = "Estado del usuario")
    private boolean isActive;
} 