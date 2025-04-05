package com.songify.infrastructure.songplayer.controller.dto.response;

import com.songify.domain.crud.dto.AlbumDto;

import java.util.Set;

public record GetAllAlbumsResponseDto(Set<AlbumDto> albums) {
}
