package com.songify.infrastructure.crud.song.controller.dto.request;

import com.songify.domain.crud.song.dto.SongLanguageDto;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.time.Instant;

public record SongRequestDto(
        String name,
        Instant releaseDate,
        Long duration,
        SongLanguageDto language
) {
}
