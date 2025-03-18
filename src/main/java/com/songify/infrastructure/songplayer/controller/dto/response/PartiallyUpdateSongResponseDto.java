package com.songify.infrastructure.songplayer.controller.dto.response;

import com.songify.domain.crud.dto.SongDto;

public record PartiallyUpdateSongResponseDto(SongDto updatedSong) {
}
