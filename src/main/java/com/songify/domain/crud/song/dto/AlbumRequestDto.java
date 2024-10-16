package com.songify.domain.crud.song.dto;

import java.time.Instant;

public record AlbumRequestDto(
        String title,
        Instant releaseDate,
        Long songId) {
}
