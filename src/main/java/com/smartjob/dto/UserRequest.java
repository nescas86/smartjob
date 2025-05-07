package com.smartjob.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.List;

@Data
@Schema(description = "Datos requeridos para el registro de un usuario")
public class UserRequest {
    
    @NotBlank(message = "El nombre es obligatorio")
    @Schema(description = "Nombre completo del usuario", example = "Juan Rodriguez")
    private String name;
    
    @NotBlank(message = "El email es obligatorio")
    @Email(message = "El formato del correo debe ser aaaaaaa@dominio.cl")
    @Pattern(regexp = "^[a-zA-Z]{7}@dominio\\.cl$", message = "El formato del correo debe ser aaaaaaa@dominio.cl")
    @Schema(description = "Email del usuario (debe tener el formato aaaaaaa@dominio.cl)", example = "aaaaaaa@dominio.cl")
    private String email;
    
    @NotBlank(message = "La contraseña es obligatoria")
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*\\d).{5,}$", 
            message = "La contraseña debe tener al menos 5 caracteres, una mayúscula y un número")
    @Schema(description = "Contraseña del usuario (mínimo 5 caracteres, una mayúscula y un número)", example = "Abc12")
    private String password;
    
    @Valid
    @Schema(description = "Lista de teléfonos del usuario")
    private List<PhoneRequest> phones;
} 