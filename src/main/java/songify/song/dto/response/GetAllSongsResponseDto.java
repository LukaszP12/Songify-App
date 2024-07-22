package songify.song.dto.response;

import songify.song.controller.Song;

import java.util.Map;

public record GetAllSongsResponseDto(Map<Integer, Song> songs) {
}
