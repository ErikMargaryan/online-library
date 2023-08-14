package com.library.configuration.security;

import com.library.dto.mapper.Mapper;
import com.library.dto.request.LoginRequestDto;
import com.library.dto.request.UserRequestDto;
import com.library.dto.response.AuthResponseDto;
import com.library.persistence.entity.Role;
import com.library.persistence.entity.User;
import com.library.persistence.entity.composite.UserRoleKey;
import com.library.persistence.entity.joinEntity.UserRole;
import com.library.persistence.repository.RoleRepository;
import com.library.persistence.repository.UserRepository;
import com.library.persistence.repository.UserRoleRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserRoleRepository userRoleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JWTGenerator jwtGenerator;
    private final Mapper mapper;

    @PostMapping("login")
    public ResponseEntity<AuthResponseDto> login(@RequestBody LoginRequestDto loginDto) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginDto.getEmail(),
                        loginDto.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String accessToken = jwtGenerator.generateAccessToken(authentication);
        String refreshToken = jwtGenerator.generateRefreshToken(authentication);

        return new ResponseEntity<>(new AuthResponseDto(accessToken, refreshToken), HttpStatus.OK);
    }

    @PostMapping("register")
    public ResponseEntity<String> register(@Valid @RequestBody UserRequestDto userRequestDto) {
        User user = mapper.toEntity(userRequestDto);
        user.setPassword(passwordEncoder.encode((userRequestDto.getPassword())));
        User savedUser = userRepository.save(user);
        if (savedUser.getUserRoles() == null) {
            Long roleIdOfUSER = roleRepository.findIdByRoleName("USER");
            UserRoleKey userRoleKey = new UserRoleKey();
            userRoleKey.setUserId(savedUser.getId());
            userRoleKey.setRoleId(roleIdOfUSER);
            Role role = roleRepository.findById(roleIdOfUSER).orElseThrow();
            UserRole userRole = new UserRole(userRoleKey, savedUser, role);
            userRoleRepository.save(userRole);
        }
        return new ResponseEntity<>("User registered success!", HttpStatus.OK);
    }
}
