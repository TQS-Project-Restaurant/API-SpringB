package tqs.project.api.services.impl;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import tqs.project.api.dao.SignInRequest;
import tqs.project.api.dao.SignInResponse;
import tqs.project.api.models.Utilizador;
import tqs.project.api.repositories.UtilizadorRepository;
import tqs.project.api.services.AuthenticationService;
import tqs.project.api.services.JwtService;
import tqs.project.api.services.CustomUserDetailsService;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final UtilizadorRepository utilizadorRepository;
    private final JwtService jwtService;
    private final CustomUserDetailsService userService;

    public AuthenticationServiceImpl(AuthenticationManager authenticationManager, 
                                        UtilizadorRepository utilizadorRepository,
                                        JwtService jwtService,
                                        CustomUserDetailsService userService){
        this.authenticationManager = authenticationManager;
        this.utilizadorRepository = utilizadorRepository;
        this.jwtService = jwtService;
        this.userService = userService;
    }

    @Override
    public SignInResponse signin(SignInRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));

        Utilizador utilizador = utilizadorRepository.findByEmail(request.getEmail()).orElseThrow(() -> new IllegalArgumentException("Invalid email or password."));

        String jwt = jwtService.generateToken(userService.userDetailsService().loadUserByUsername(request.getEmail()));
        
        return SignInResponse.builder().token(jwt).email(utilizador.getEmail()).role(utilizador.getRole()).build();
    }
}
