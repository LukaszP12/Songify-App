package com.songify.song.infrastructure.controller.dto.response;


import com.songify.song.domain.entities.Song;

public record CreateSongResponseDto(Song song) {
}
