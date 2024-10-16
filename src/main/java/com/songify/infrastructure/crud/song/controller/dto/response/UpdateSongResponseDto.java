package com.songify.infrastructure.crud.song.controller.dto.response;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record UpdateSongResponseDto(
        @NotNull(message = "songName must not be null")
        @NotEmpty(message = "songName must not be empty")
        String song,

        @NotNull(message = "artistName must not be null")
        @NotEmpty(message = "artistName must not be empty")
        String artist) {
}
