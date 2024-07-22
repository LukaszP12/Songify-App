package songify.song.infrastructure.controller.dto.response;

import songify.song.domain.model.Song;

public record GetSongResponseDto(Song song) {
}
