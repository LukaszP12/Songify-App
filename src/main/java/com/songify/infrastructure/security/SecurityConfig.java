package com.songify.infrastructure.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;

@Configuration
class SecurityConfig {

    @Bean
    public UserDetailsManager userDetailsService() {
        var manager = new InMemoryUserDetailsManager();
        var user1 = User.withUsername("albert")
                .password("12345")
                .roles("USER")
                .build();

        var user2 = User.withUsername("tadeusz")
                .password("12345")
                .roles("USER", "ADMIN")
                .build();
        manager.createUser(user1);
        manager.createUser(user2);
        return manager;
    }
}
