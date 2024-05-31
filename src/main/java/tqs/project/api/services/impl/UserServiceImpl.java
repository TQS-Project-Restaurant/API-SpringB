package tqs.project.api.services.impl;

import java.util.Collections;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import tqs.project.api.models.Utilizador;
import tqs.project.api.repositories.UtilizadorRepository;
import tqs.project.api.services.UserService;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UtilizadorRepository utilizadorRepository;

    @Override
    public UserDetailsService userDetailsService() {
        return new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String username) {
                Utilizador utilizador = utilizadorRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));

                return new User(
                    utilizador.getEmail(),
                    utilizador.getPassword(),
                    Collections.singleton(new SimpleGrantedAuthority("ROLE_" + utilizador.getRole()))
                );
            }
        };
    }
}