package com.songify.infrastructure.songplayer.controller.dto.response;

import com.songify.domain.crud.dto.GenreDto;

import java.util.Set;

public record GetAllGenresResponseDto(Set<GenreDto> genres) {
}
