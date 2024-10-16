package com.songify.domain.crud.song.dto;

import com.songify.domain.crud.SongLanguage;

import java.time.Instant;

public record SongRequestDto(
        String name,
        Long artistId,
        Instant releaseDate,
        Long duration,
        SongLanguage language) {
}
