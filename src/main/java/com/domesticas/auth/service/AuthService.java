package com.domesticas.auth.service;

import com.domesticas.auth.dto.request.LoginRequest;
import com.domesticas.auth.dto.request.RegisterRequest;
import com.domesticas.auth.dto.response.LoginResponse;
import com.domesticas.usuario.dto.response.UsuarioResponse;
import com.domesticas.exception.BadRequestException;
import com.domesticas.exception.UnauthorizedException;
import com.domesticas.usuario.model.Usuario;
import com.domesticas.usuario.repository.UsuarioRepository;
import com.domesticas.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UsuarioRepository usuarioRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public UsuarioResponse registrar(RegisterRequest request) {

        if (usuarioRepository.existsByEmail(request.getEmail())) {
            throw new BadRequestException("El correo ya está registrado");
        }

        Usuario usuario = Usuario.builder()
                .nombre(request.getNombre())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .build();

        Usuario usuarioGuardado = usuarioRepository.save(usuario);

        return UsuarioResponse.builder()
                .id(usuarioGuardado.getId())
                .nombre(usuarioGuardado.getNombre())
                .email(usuarioGuardado.getEmail())
                .mensaje("Usuario registrado exitosamente")
                .build();
    }

    public LoginResponse login(LoginRequest request) {

        Usuario usuario = usuarioRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new UnauthorizedException("Credenciales inválidas"));

        boolean passwordCorrecta = passwordEncoder.matches(
                request.getPassword(),
                usuario.getPassword()
        );

        if (!passwordCorrecta) {
            throw new UnauthorizedException("Credenciales inválidas");
        }

        String token = jwtService.generateToken(usuario.getEmail());

        return LoginResponse.builder()
                .id(usuario.getId())
                .nombre(usuario.getNombre())
                .email(usuario.getEmail())
                .token(token)
                .mensaje("Inicio de sesión exitoso")
                .build();
    }
}