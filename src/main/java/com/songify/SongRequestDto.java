package com.songify;

import jakarta.validation.constraints.NotNull;

public record SongRequestDto(
        @NotNull(message = "songName must not be null")
        String songName
) {
}
