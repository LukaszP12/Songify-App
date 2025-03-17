package com.songify.infrastructure.songplayer.controller.dto.response;

import lombok.Builder;

@Builder
public record SongControllerResponseDto(Long id, String name, String artist) {
}
