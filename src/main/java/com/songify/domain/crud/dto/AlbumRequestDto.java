package com.songify.domain.crud.dto;

import java.time.Instant;

public record AlbumRequestDto(Long id, String title, Instant releaseDate) {
}
