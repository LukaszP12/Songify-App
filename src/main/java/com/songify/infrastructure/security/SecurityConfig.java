package com.songify.infrastructure.security;

import com.songify.domain.usercrud.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.CorsConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.List;

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
    UserDetailsManager userDetailsService(UserRepository userRepository) {
        return new UserDetailsServiceImpl(userRepository, passwordEncoder());
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(c -> c.disable());
        http.cors(corsConfigurerCustomizer());
        http.formLogin(c -> c.disable());
        http.httpBasic(c -> c.disable());
        http.sessionManagement(c->c.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        http.authorizeHttpRequests(authorize -> authorize
                .requestMatchers("/swagger-ui/**").permitAll()
                .requestMatchers("/swagger-resources/**").permitAll()
                .requestMatchers("/v3/api-docs/**").permitAll()
                .requestMatchers("/register/**").permitAll()
                .requestMatchers(HttpMethod.POST,"/token").permitAll()
                .requestMatchers(HttpMethod.GET, "/songs/**").permitAll()
                .requestMatchers(HttpMethod.GET, "/message").hasRole("ADMIN")
                .requestMatchers(HttpMethod.GET, "/artists/**").permitAll()
                .requestMatchers(HttpMethod.GET, "/albums/**").permitAll()
                .requestMatchers(HttpMethod.GET, "/genres/**").permitAll()
                .requestMatchers(HttpMethod.POST, "/songs/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.PATCH, "/songs/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/songs/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.PUT, "/songs/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.POST, "/artists/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.PATCH, "/artists/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/artists/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.PUT, "/artists/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.POST, "/albums/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.PUT, "/albums/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.POST, "/genres/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.POST, "/users/**").hasRole("ADMIN")
                .anyRequest().authenticated());
        return http.build();
    }

    public Customizer<CorsConfigurer<HttpSecurity>> corsConfigurerCustomizer() {
        return c -> {
            CorsConfigurationSource source = request -> {
                CorsConfiguration config = new CorsConfiguration();
                config.setAllowedOrigins(
                        List.of("http://localhost:3000"));
                config.setAllowedMethods(
                        List.of("GET", "POST", "PUT", "DELETE","PATCH"));
                config.setAllowedHeaders(List.of("*"));
                return config;
            };
            c.configurationSource(source);
        };
    }

    @Bean
    PasswordEncoder passwordEncoder() {
//        return NoOpPasswordEncoder.getInstance();
        return new BCryptPasswordEncoder();
    }
}
