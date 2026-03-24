package com.broers.users.services;

import com.broers.users.api.dto.CreateUserRequest;
import com.broers.users.config.jwt.JwtUtil;
import com.broers.users.enums.RoleList;
import com.broers.users.error.NotFoundException;
import com.broers.users.repositories.RolRepository;
import com.broers.users.repositories.entities.RolEntity;
import com.broers.users.repositories.entities.UserEntity;

import java.time.Instant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserService userService;
    private final RolRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    @Autowired
    public AuthService(UserService userService, RolRepository roleRepository, PasswordEncoder passwordEncoder,
            JwtUtil jwtUtil, AuthenticationManagerBuilder authenticationManagerBuilder) {
        this.userService = userService;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
        this.authenticationManagerBuilder = authenticationManagerBuilder;
    }

    public String authenticate(String username, String password) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username,
                password);
        Authentication authResult = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authResult);
        return jwtUtil.generateToken(authResult);
    }

    public void registerUser(CreateUserRequest newUserDto) {
        if (userService.existsByUserName(newUserDto.userName())) {
            throw new NotFoundException("El nombre de usuario ya existe");
        }

        RolEntity roleUser = roleRepository.findByName(RoleList.ROLE_USER)
                .orElseThrow(() -> new NotFoundException("Rol no encontrado"));
        UserEntity user = new UserEntity(newUserDto.name(), newUserDto.email(), newUserDto.userName(),
                passwordEncoder.encode(newUserDto.password()),
                roleUser, "CREATED", Instant.now(), Instant.now());
        userService.save(user);
    }

    public void createRol(RoleList rol) {
        RolEntity newRol = new RolEntity(rol);
        roleRepository.save(newRol);

    }
}
