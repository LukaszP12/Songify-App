package songify.song.dto;

import org.springframework.http.HttpStatus;

public record DeleteSongResponseDto(String message, HttpStatus httpStatus) {
}