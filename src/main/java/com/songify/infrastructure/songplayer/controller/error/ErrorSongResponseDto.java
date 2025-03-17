package com.songify.infrastructure.songplayer.controller.error;

import org.springframework.http.HttpStatus;

public record ErrorSongResponseDto(String message, HttpStatus status) {
}
