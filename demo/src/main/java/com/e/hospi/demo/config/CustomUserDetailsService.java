package com.e.hospi.demo.config;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.e.hospi.demo.Domain.User;
import com.e.hospi.demo.Repositories.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    
        User u = userRepository.findByEmailUser(email)
                .orElseThrow(() -> {
                    return new UsernameNotFoundException("Usuario no encontrado: " + email);
                });
    
        return org.springframework.security.core.userdetails.User.withUsername(u.getEmailUser())
                .password(u.getPasswordUser())
                .authorities("ROLE_" + u.getRoleUser().getNameRole())
                .build();
    }
}