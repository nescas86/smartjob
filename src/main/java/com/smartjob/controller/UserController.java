package com.smartjob.controller;

import com.smartjob.dto.UserRequest;
import com.smartjob.dto.UserResponse;
import com.smartjob.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api")
@Tag(name = "Users", description = "APIs para el registro y consulta de usuarios")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/registro")
    @Operation(
        summary = "Registrar un nuevo usuario",
        description = "Crea un nuevo usuario con sus datos personales y teléfonos. El email debe tener el formato aaaaaaa@dominio.cl"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "201",
            description = "Usuario creado exitosamente",
            content = @Content(schema = @Schema(implementation = UserResponse.class))
        ),
        @ApiResponse(
            responseCode = "400",
            description = "Error en la validación de datos o email ya registrado",
            content = @Content(schema = @Schema(implementation = Map.class))
        )
    })
    public ResponseEntity<?> registro(@Valid @RequestBody UserRequest request) {
        try {
            UserResponse response = userService.createUser(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (RuntimeException e) {
            log.error("Error en registro: {}", e.getMessage());
            Map<String, String> error = new HashMap<>();
            error.put("mensaje", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
    }

    @GetMapping("/usuarios")
    @Operation(
        summary = "Obtener todos los usuarios",
        description = "Retorna la lista de todos los usuarios registrados en el sistema"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Lista de usuarios obtenida exitosamente",
            content = @Content(schema = @Schema(implementation = UserResponse.class))
        )
    })
    public ResponseEntity<List<UserResponse>> getAllUsers() {
        List<UserResponse> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }
} 