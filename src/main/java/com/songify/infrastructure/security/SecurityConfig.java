package com.songify.infrastructure.security;

import com.songify.domain.usercrud.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;

@Configuration
class SecurityConfig {

//    @Bean
//    public UserDetailsManager userDetailsService() {
//        var manager = new InMemoryUserDetailsManager();
//        var user1 = User.withUsername("albert")
//                .password("12345")
//                .roles("USER")
//                .build();
//
//        var user2 = User.withUsername("tadeusz")
//                .password("12345")
//                .roles("USER", "ADMIN")
//                .build();
//        manager.createUser(user1);
//        manager.createUser(user2);
//        return manager;
//    }

    @Bean
    public UserDetailsService userDetailsService(UserRepository userRepository) {
        return new UserDetailsServiceImpl(userRepository);
    }

    @Bean
    PasswordEncoder passwordEncoder() {
//        return NoOpPasswordEncoder.getInstance();
        return new BCryptPasswordEncoder();
    }
}
