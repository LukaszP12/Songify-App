package com.songify;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class SongifyApplication extends SpringBootServletInitializer {
    public static void main(String[] args) {
        SpringApplication.run(SongifyApplication.class, args);
    }
}
