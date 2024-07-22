package songify.song.infrastructure.controller.dto.response;

import songify.song.domain.model.Song;

import java.util.Map;

public record GetAllSongsResponseDto(Map<Integer, Song> songs) {
}
