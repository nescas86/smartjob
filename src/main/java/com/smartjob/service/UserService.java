package com.smartjob.service;

import com.smartjob.dto.PhoneRequest;
import com.smartjob.dto.PhoneResponse;
import com.smartjob.dto.UserRequest;
import com.smartjob.dto.UserResponse;
import com.smartjob.model.Phone;
import com.smartjob.model.User;
import com.smartjob.repository.UserRepository;
import com.smartjob.util.JwtService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private JwtService jwtService;

    @Transactional
    public UserResponse createUser(UserRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("El correo ya registrado");
        }

        LocalDateTime now = LocalDateTime.now();
        String token = jwtService.generateToken(request.getEmail());

        User user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(request.getPassword())
                .created(now)
                .modified(now)
                .lastLogin(now)
                .isActive(true)
                .token(token)
                .phones(new ArrayList<>())
                .build();

        if (request.getPhones() != null) {
            List<Phone> phones = request.getPhones().stream()
                    .map(phoneRequest -> Phone.builder()
                            .number(phoneRequest.getNumber())
                            .cityCode(phoneRequest.getCityCode())
                            .countryCode(phoneRequest.getCountryCode())
                            .build())
                    .collect(Collectors.toList());
            user.setPhones(phones);
        }

        User savedUser = userRepository.save(user);
        return mapToResponse(savedUser);
    }

    @Transactional(readOnly = true)
    public List<UserResponse> getAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    private UserResponse mapToResponse(User user) {
        List<PhoneResponse> phoneResponses = user.getPhones() != null ? 
            user.getPhones().stream()
                .map(phone -> PhoneResponse.builder()
                        .number(phone.getNumber())
                        .cityCode(phone.getCityCode())
                        .countryCode(phone.getCountryCode())
                        .build())
                .collect(Collectors.toList()) :
            new ArrayList<>();

        return UserResponse.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .phones(phoneResponses)
                .created(user.getCreated())
                .modified(user.getModified())
                .lastLogin(user.getLastLogin())
                .token(user.getToken())
                .isActive(user.isActive())
                .build();
    }
} 