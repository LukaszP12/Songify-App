package com.songify.infrastructure.songplayer.controller.dto.response;

import lombok.Builder;

@Builder
public record SongDto(Long id, String name) {
}
