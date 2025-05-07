package com.smartjob.service;

import com.smartjob.dto.PhoneRequest;
import com.smartjob.dto.UserRequest;
import com.smartjob.dto.UserResponse;
import com.smartjob.model.Phone;
import com.smartjob.model.User;
import com.smartjob.repository.UserRepository;
import com.smartjob.util.JwtService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private JwtService jwtService;

    @InjectMocks
    private UserService userService;

    private UserRequest userRequest;
    private User user;
    private String testToken;

    @BeforeEach
    void setUp() {
        testToken = "test.jwt.token";
        
        userRequest = new UserRequest();
        userRequest.setName("Juan Rodriguez");
        userRequest.setEmail("aaaaaaa@dominio.cl");
        userRequest.setPassword("hunter2");
        
        PhoneRequest phoneRequest = new PhoneRequest();
        phoneRequest.setNumber("1234567");
        phoneRequest.setCityCode("1");
        phoneRequest.setCountryCode("57");
        userRequest.setPhones(Arrays.asList(phoneRequest));

        user = new User();
        user.setId(UUID.randomUUID());
        user.setName(userRequest.getName());
        user.setEmail(userRequest.getEmail());
        user.setPassword(userRequest.getPassword());
        user.setToken(testToken);
        
        Phone phone = new Phone();
        phone.setNumber(phoneRequest.getNumber());
        phone.setCityCode(phoneRequest.getCityCode());
        phone.setCountryCode(phoneRequest.getCountryCode());
        user.setPhones(Arrays.asList(phone));
    }

    @Test
    void createUser_Success() {
        when(userRepository.existsByEmail(userRequest.getEmail())).thenReturn(false);
        when(jwtService.generateToken(userRequest.getEmail())).thenReturn(testToken);
        when(userRepository.save(any(User.class))).thenReturn(user);

        UserResponse response = userService.createUser(userRequest);

        assertNotNull(response);
        assertEquals(user.getId(), response.getId());
        assertEquals(user.getName(), response.getName());
        assertEquals(user.getEmail(), response.getEmail());
        assertEquals(testToken, response.getToken());
        assertTrue(response.isActive());
        assertNotNull(response.getPhones());
        assertEquals(1, response.getPhones().size());
        
        verify(userRepository).existsByEmail(userRequest.getEmail());
        verify(jwtService).generateToken(userRequest.getEmail());
        verify(userRepository).save(any(User.class));
    }

    @Test
    void createUser_EmailAlreadyExists() {
        when(userRepository.existsByEmail(userRequest.getEmail())).thenReturn(true);

        Exception exception = assertThrows(RuntimeException.class, () -> {
            userService.createUser(userRequest);
        });

        assertEquals("El correo ya registrado", exception.getMessage());
        verify(userRepository).existsByEmail(userRequest.getEmail());
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void getAllUsers_Success() {
        List<User> users = Arrays.asList(user);
        when(userRepository.findAll()).thenReturn(users);

        List<UserResponse> responses = userService.getAllUsers();

        assertNotNull(responses);
        assertEquals(1, responses.size());
        UserResponse response = responses.get(0);
        assertEquals(user.getId(), response.getId());
        assertEquals(user.getName(), response.getName());
        assertEquals(user.getEmail(), response.getEmail());
        assertEquals(testToken, response.getToken());
        assertTrue(response.isActive());
        assertNotNull(response.getPhones());
        assertEquals(1, response.getPhones().size());
        
        verify(userRepository).findAll();
    }
} 